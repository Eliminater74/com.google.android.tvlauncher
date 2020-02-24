package com.google.android.gms.internal;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PrivateKey;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

@Hide
/* compiled from: SSLCertificateSocketFactory */
public final class zzbls extends SSLSocketFactory {
    private static final TrustManager[] zza = {new zzblt()};
    private SSLSocketFactory zzb = null;
    private SSLSocketFactory zzc = null;
    private TrustManager[] zzd = null;
    private KeyManager[] zze = null;
    private byte[] zzf = null;
    private byte[] zzg = null;
    private PrivateKey zzh = null;
    private final Context zzi;
    private final int zzj;
    private final boolean zzk;
    private final boolean zzl;
    private final String zzm;

    private zzbls(Context context, int i, boolean z, boolean z2, String str) {
        this.zzi = context.getApplicationContext();
        this.zzj = 60000;
        this.zzk = true;
        this.zzl = true;
        this.zzm = null;
    }

    public static SSLSocketFactory zza(int i, Context context) {
        return new zzbls(context, 60000, true, true, null);
    }

    @Hide
    private static void zza(Socket socket, String str) throws IOException {
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            sSLSocket.startHandshake();
            SSLSession session = sSLSocket.getSession();
            if (session == null) {
                throw new SSLException("Cannot verify SSL socket without session");
            } else if (!HttpsURLConnection.getDefaultHostnameVerifier().verify(str, session)) {
                String valueOf = String.valueOf(str);
                throw new SSLPeerUnverifiedException(valueOf.length() != 0 ? "Cannot verify hostname: ".concat(valueOf) : new String("Cannot verify hostname: "));
            }
        } else {
            throw new IllegalArgumentException("Attempt to verify non-SSL socket");
        }
    }

    private final synchronized SSLSocketFactory zza() {
        if (!this.zzl) {
            if (this.zzb == null) {
                Log.w("SSLCertificateSocketFactory", "Bypassing SSL security checks at caller's request");
                this.zzb = zzblu.zza().zza(this.zzi, null, zza, this.zzk);
            }
            return this.zzb;
        }
        if (this.zzc == null) {
            this.zzc = zzblu.zza().zza(this.zzi, null, null, this.zzk);
        }
        return this.zzc;
    }

    public final Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        Socket createSocket = zza().createSocket(socket, str, i, z);
        zza(createSocket, (byte[]) null);
        zzb(createSocket, null);
        zza(createSocket, this.zzj);
        zza(createSocket, (PrivateKey) null);
        if (this.zzl) {
            zza(createSocket, str);
        }
        return createSocket;
    }

    public final Socket createSocket() throws IOException {
        Socket createSocket = zza().createSocket();
        zza(createSocket, (byte[]) null);
        zzb(createSocket, null);
        zza(createSocket, this.zzj);
        zza(createSocket, (PrivateKey) null);
        return createSocket;
    }

    public final Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        Socket createSocket = zza().createSocket(inetAddress, i, inetAddress2, i2);
        zza(createSocket, (byte[]) null);
        zzb(createSocket, null);
        zza(createSocket, this.zzj);
        zza(createSocket, (PrivateKey) null);
        return createSocket;
    }

    public final Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        Socket createSocket = zza().createSocket(inetAddress, i);
        zza(createSocket, (byte[]) null);
        zzb(createSocket, null);
        zza(createSocket, this.zzj);
        zza(createSocket, (PrivateKey) null);
        return createSocket;
    }

    public final Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
        Socket createSocket = zza().createSocket(str, i, inetAddress, i2);
        zza(createSocket, (byte[]) null);
        zzb(createSocket, null);
        zza(createSocket, this.zzj);
        zza(createSocket, (PrivateKey) null);
        if (this.zzl) {
            zza(createSocket, str);
        }
        return createSocket;
    }

    public final Socket createSocket(String str, int i) throws IOException {
        Socket createSocket = zza().createSocket(str, i);
        zza(createSocket, (byte[]) null);
        zzb(createSocket, null);
        zza(createSocket, this.zzj);
        zza(createSocket, (PrivateKey) null);
        if (this.zzl) {
            zza(createSocket, str);
        }
        return createSocket;
    }

    public final String[] getDefaultCipherSuites() {
        return zza().getDefaultCipherSuites();
    }

    public final String[] getSupportedCipherSuites() {
        return zza().getSupportedCipherSuites();
    }

    private static void zza(Socket socket, byte[] bArr) {
        if (socket != null) {
            try {
                socket.getClass().getMethod("setNpnProtocols", byte[].class).invoke(socket, null);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                String valueOf = String.valueOf(socket.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 36);
                sb.append("Failed to invoke setNpnProtocols on ");
                sb.append(valueOf);
                throw new RuntimeException(sb.toString(), e);
            } catch (IllegalAccessException | NoSuchMethodException e2) {
                String valueOf2 = String.valueOf(socket.getClass());
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 35);
                sb2.append(valueOf2);
                sb2.append(" does not implement setNpnProtocols");
                throw new IllegalArgumentException(sb2.toString(), e2);
            }
        }
    }

    private static void zzb(Socket socket, byte[] bArr) {
        if (socket != null) {
            try {
                socket.getClass().getMethod("setAlpnProtocols", byte[].class).invoke(socket, null);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                String valueOf = String.valueOf(socket.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 37);
                sb.append("Failed to invoke setAlpnProtocols on ");
                sb.append(valueOf);
                throw new RuntimeException(sb.toString(), e);
            } catch (IllegalAccessException | NoSuchMethodException e2) {
                String valueOf2 = String.valueOf(socket.getClass());
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 36);
                sb2.append(valueOf2);
                sb2.append(" does not implement setAlpnProtocols");
                throw new IllegalArgumentException(sb2.toString(), e2);
            }
        }
    }

    private static void zza(Socket socket, int i) {
        if (socket != null) {
            try {
                socket.getClass().getMethod("setHandshakeTimeout", Integer.TYPE).invoke(socket, Integer.valueOf(i));
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                String valueOf = String.valueOf(socket.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 46);
                sb.append("Failed to invoke setSocketHandshakeTimeout on ");
                sb.append(valueOf);
                throw new RuntimeException(sb.toString(), e);
            } catch (IllegalAccessException | NoSuchMethodException e2) {
                String valueOf2 = String.valueOf(socket.getClass());
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 45);
                sb2.append(valueOf2);
                sb2.append(" does not implement setSocketHandshakeTimeout");
                throw new IllegalArgumentException(sb2.toString(), e2);
            }
        }
    }

    private static void zza(Socket socket, PrivateKey privateKey) {
        if (socket != null) {
            try {
                socket.getClass().getMethod("setChannelIdPrivateKey", PrivateKey.class).invoke(socket, null);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                String valueOf = String.valueOf(socket.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 43);
                sb.append("Failed to invoke setChannelIdPrivateKey on ");
                sb.append(valueOf);
                throw new RuntimeException(sb.toString(), e);
            } catch (IllegalAccessException | NoSuchMethodException e2) {
                String valueOf2 = String.valueOf(socket.getClass());
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 42);
                sb2.append(valueOf2);
                sb2.append(" does not implement setChannelIdPrivateKey");
                throw new IllegalArgumentException(sb2.toString(), e2);
            }
        }
    }
}
