package com.google.android.gms.common.util;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.google.android.gms.common.internal.zzau;
import com.google.common.primitives.UnsignedBytes;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public final class IOUtils {

    static final class zza extends ByteArrayOutputStream {
        private zza() {
        }

        /* access modifiers changed from: package-private */
        public final void zza(byte[] bArr, int i) {
            System.arraycopy(this.buf, 0, bArr, i, this.count);
        }
    }

    private IOUtils() {
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    static final class zzb {
        private final File zza;

        private zzb(File file) {
            this.zza = (File) zzau.zza(file);
        }

        public final byte[] zza() throws IOException {
            FileInputStream fileInputStream;
            Throwable th;
            try {
                fileInputStream = new FileInputStream(this.zza);
                try {
                    byte[] zza2 = IOUtils.zzb(fileInputStream, fileInputStream.getChannel().size());
                    IOUtils.closeQuietly(fileInputStream);
                    return zza2;
                } catch (Throwable th2) {
                    th = th2;
                    IOUtils.closeQuietly(fileInputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                fileInputStream = null;
                th = th4;
                IOUtils.closeQuietly(fileInputStream);
                throw th;
            }
        }
    }

    public static void close(Closeable closeable, String str, String str2) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.d(str, str2, e);
            }
        }
    }

    public static void closeQuietly(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
            }
        }
    }

    public static boolean isGzipByteBuffer(byte[] bArr) {
        if (bArr.length > 1) {
            if ((((bArr[1] & UnsignedBytes.MAX_VALUE) << 8) | (bArr[0] & UnsignedBytes.MAX_VALUE)) == 35615) {
                return true;
            }
        }
        return false;
    }

    public static long copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copyStream(inputStream, outputStream, false);
    }

    public static long copyStream(InputStream inputStream, OutputStream outputStream, boolean z) throws IOException {
        return copyStream(inputStream, outputStream, z, 1024);
    }

    public static long copyStream(InputStream inputStream, OutputStream outputStream, boolean z, int i) throws IOException {
        byte[] bArr = new byte[i];
        long j = 0;
        while (true) {
            try {
                int read = inputStream.read(bArr, 0, i);
                if (read == -1) {
                    break;
                }
                j += (long) read;
                outputStream.write(bArr, 0, read);
            } finally {
                if (z) {
                    closeQuietly(inputStream);
                    closeQuietly(outputStream);
                }
            }
        }
        return j;
    }

    public static byte[] readInputStreamFully(InputStream inputStream) throws IOException {
        return readInputStreamFully(inputStream, true);
    }

    public static byte[] readInputStreamFully(InputStream inputStream, boolean z) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copyStream(inputStream, byteArrayOutputStream, z);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] toByteArray(File file) throws IOException {
        return new zzb(file).zza();
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0042  */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void lockAndTruncateFile(java.io.File r4) throws java.io.IOException, java.nio.channels.OverlappingFileLockException {
        /*
            boolean r0 = r4.exists()
            if (r0 == 0) goto L_0x0046
            r0 = 0
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ all -> 0x0031 }
            java.lang.String r2 = "rw"
            r1.<init>(r4, r2)     // Catch:{ all -> 0x0031 }
            java.nio.channels.FileChannel r4 = r1.getChannel()     // Catch:{ all -> 0x002f }
            java.nio.channels.FileLock r0 = r4.lock()     // Catch:{ all -> 0x002f }
            r2 = 0
            r4.truncate(r2)     // Catch:{ all -> 0x002f }
            if (r0 == 0) goto L_0x002b
            boolean r4 = r0.isValid()
            if (r4 == 0) goto L_0x002b
            r0.release()     // Catch:{ IOException -> 0x002a }
            goto L_0x002b
        L_0x002a:
            r4 = move-exception
        L_0x002b:
            closeQuietly(r1)
            return
        L_0x002f:
            r4 = move-exception
            goto L_0x0033
        L_0x0031:
            r4 = move-exception
            r1 = r0
        L_0x0033:
            if (r0 == 0) goto L_0x0040
            boolean r2 = r0.isValid()
            if (r2 == 0) goto L_0x0040
            r0.release()     // Catch:{ IOException -> 0x003f }
            goto L_0x0040
        L_0x003f:
            r0 = move-exception
        L_0x0040:
            if (r1 == 0) goto L_0x0045
            closeQuietly(r1)
        L_0x0045:
            throw r4
        L_0x0046:
            java.io.FileNotFoundException r4 = new java.io.FileNotFoundException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.IOUtils.lockAndTruncateFile(java.io.File):void");
    }

    /* access modifiers changed from: private */
    public static byte[] zzb(InputStream inputStream, long j) throws IOException {
        if (j > 2147483647L) {
            StringBuilder sb = new StringBuilder(68);
            sb.append("file is too large to fit in a byte array: ");
            sb.append(j);
            sb.append(" bytes");
            throw new OutOfMemoryError(sb.toString());
        } else if (j == 0) {
            return toByteArray(inputStream);
        } else {
            int i = (int) j;
            byte[] bArr = new byte[i];
            int i2 = i;
            while (i2 > 0) {
                int i3 = i - i2;
                int read = inputStream.read(bArr, i3, i2);
                if (read == -1) {
                    return Arrays.copyOf(bArr, i3);
                }
                i2 -= read;
            }
            int read2 = inputStream.read();
            if (read2 == -1) {
                return bArr;
            }
            zza zza2 = new zza();
            zza2.write(read2);
            zza(inputStream, zza2);
            byte[] bArr2 = new byte[(bArr.length + zza2.size())];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            zza2.zza(bArr2, bArr.length);
            return bArr2;
        }
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        zza(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private static long zza(InputStream inputStream, OutputStream outputStream) throws IOException {
        zzau.zza(inputStream);
        zzau.zza(outputStream);
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }
}
