package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public final class UdpDataSource extends BaseDataSource {
    public static final int DEFAULT_MAX_PACKET_SIZE = 2000;
    public static final int DEFAULT_SOCKET_TIMEOUT_MILLIS = 8000;
    private final DatagramPacket packet;
    private final byte[] packetBuffer;
    private final int socketTimeoutMillis;
    @Nullable
    private InetAddress address;
    @Nullable
    private MulticastSocket multicastSocket;
    private boolean opened;
    private int packetRemaining;
    @Nullable
    private DatagramSocket socket;
    @Nullable
    private InetSocketAddress socketAddress;
    @Nullable
    private Uri uri;

    public UdpDataSource() {
        this(2000);
    }

    public UdpDataSource(int maxPacketSize) {
        this(maxPacketSize, 8000);
    }

    public UdpDataSource(int maxPacketSize, int socketTimeoutMillis2) {
        super(true);
        this.socketTimeoutMillis = socketTimeoutMillis2;
        this.packetBuffer = new byte[maxPacketSize];
        this.packet = new DatagramPacket(this.packetBuffer, 0, maxPacketSize);
    }

    @Deprecated
    public UdpDataSource(@Nullable TransferListener listener) {
        this(listener, 2000);
    }

    @Deprecated
    public UdpDataSource(@Nullable TransferListener listener, int maxPacketSize) {
        this(listener, maxPacketSize, 8000);
    }

    @Deprecated
    public UdpDataSource(@Nullable TransferListener listener, int maxPacketSize, int socketTimeoutMillis2) {
        this(maxPacketSize, socketTimeoutMillis2);
        if (listener != null) {
            addTransferListener(listener);
        }
    }

    public long open(DataSpec dataSpec) throws UdpDataSourceException {
        this.uri = dataSpec.uri;
        String host = this.uri.getHost();
        int port = this.uri.getPort();
        transferInitializing(dataSpec);
        try {
            this.address = InetAddress.getByName(host);
            this.socketAddress = new InetSocketAddress(this.address, port);
            if (this.address.isMulticastAddress()) {
                this.multicastSocket = new MulticastSocket(this.socketAddress);
                this.multicastSocket.joinGroup(this.address);
                this.socket = this.multicastSocket;
            } else {
                this.socket = new DatagramSocket(this.socketAddress);
            }
            try {
                this.socket.setSoTimeout(this.socketTimeoutMillis);
                this.opened = true;
                transferStarted(dataSpec);
                return -1;
            } catch (SocketException e) {
                throw new UdpDataSourceException(e);
            }
        } catch (IOException e2) {
            throw new UdpDataSourceException(e2);
        }
    }

    public int read(byte[] buffer, int offset, int readLength) throws UdpDataSourceException {
        if (readLength == 0) {
            return 0;
        }
        if (this.packetRemaining == 0) {
            try {
                this.socket.receive(this.packet);
                this.packetRemaining = this.packet.getLength();
                bytesTransferred(this.packetRemaining);
            } catch (IOException e) {
                throw new UdpDataSourceException(e);
            }
        }
        int length = this.packet.getLength();
        int i = this.packetRemaining;
        int packetOffset = length - i;
        int bytesToRead = Math.min(i, readLength);
        System.arraycopy(this.packetBuffer, packetOffset, buffer, offset, bytesToRead);
        this.packetRemaining -= bytesToRead;
        return bytesToRead;
    }

    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    public void close() {
        this.uri = null;
        MulticastSocket multicastSocket2 = this.multicastSocket;
        if (multicastSocket2 != null) {
            try {
                multicastSocket2.leaveGroup(this.address);
            } catch (IOException e) {
            }
            this.multicastSocket = null;
        }
        DatagramSocket datagramSocket = this.socket;
        if (datagramSocket != null) {
            datagramSocket.close();
            this.socket = null;
        }
        this.address = null;
        this.socketAddress = null;
        this.packetRemaining = 0;
        if (this.opened) {
            this.opened = false;
            transferEnded();
        }
    }

    public static final class UdpDataSourceException extends IOException {
        public UdpDataSourceException(IOException cause) {
            super(cause);
        }
    }
}
