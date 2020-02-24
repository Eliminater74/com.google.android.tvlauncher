package com.google.android.gms.internal;

import com.google.common.base.Ascii;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: CodedOutputStream */
public abstract class zzgnp extends zzgna {
    private static final Logger zzb = Logger.getLogger(zzgnp.class.getName());
    /* access modifiers changed from: private */
    public static final boolean zzc = zzgrj.zza();
    zzgnr zza;

    public static zzgnp zza(byte[] bArr) {
        return new zza(bArr, 0, bArr.length);
    }

    public abstract void zza() throws IOException;

    public abstract void zza(byte b) throws IOException;

    public abstract void zza(int i) throws IOException;

    public abstract void zza(int i, int i2) throws IOException;

    public abstract void zza(int i, long j) throws IOException;

    public abstract void zza(int i, zzgnb zzgnb) throws IOException;

    public abstract void zza(int i, zzgpt zzgpt) throws IOException;

    public abstract void zza(int i, String str) throws IOException;

    public abstract void zza(int i, boolean z) throws IOException;

    public abstract void zza(long j) throws IOException;

    public abstract void zza(zzgnb zzgnb) throws IOException;

    public abstract void zza(zzgpt zzgpt) throws IOException;

    public abstract void zza(String str) throws IOException;

    public abstract int zzb();

    public abstract void zzb(int i) throws IOException;

    public abstract void zzb(int i, int i2) throws IOException;

    public abstract void zzb(int i, zzgnb zzgnb) throws IOException;

    public abstract void zzb(int i, zzgpt zzgpt) throws IOException;

    public abstract void zzb(byte[] bArr, int i, int i2) throws IOException;

    public abstract void zzc(int i, int i2) throws IOException;

    public abstract void zzc(int i, long j) throws IOException;

    public abstract void zzc(long j) throws IOException;

    /* access modifiers changed from: package-private */
    public abstract void zzc(byte[] bArr, int i, int i2) throws IOException;

    public abstract void zzd(int i) throws IOException;

    public abstract void zze(int i, int i2) throws IOException;

    /* compiled from: CodedOutputStream */
    public static class zzc extends IOException {
        zzc() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        zzc(java.lang.String r3) {
            /*
                r2 = this;
                java.lang.String r0 = "CodedOutputStream was writing to a flat byte array and ran out of space.: "
                java.lang.String r0 = java.lang.String.valueOf(r0)
                java.lang.String r3 = java.lang.String.valueOf(r3)
                int r1 = r3.length()
                if (r1 == 0) goto L_0x0015
                java.lang.String r3 = r0.concat(r3)
                goto L_0x001a
            L_0x0015:
                java.lang.String r3 = new java.lang.String
                r3.<init>(r0)
            L_0x001a:
                r2.<init>(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgnp.zzc.<init>(java.lang.String):void");
        }

        zzc(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        zzc(java.lang.String r3, java.lang.Throwable r4) {
            /*
                r2 = this;
                java.lang.String r0 = "CodedOutputStream was writing to a flat byte array and ran out of space.: "
                java.lang.String r0 = java.lang.String.valueOf(r0)
                java.lang.String r3 = java.lang.String.valueOf(r3)
                int r1 = r3.length()
                if (r1 == 0) goto L_0x0015
                java.lang.String r3 = r0.concat(r3)
                goto L_0x001a
            L_0x0015:
                java.lang.String r3 = new java.lang.String
                r3.<init>(r0)
            L_0x001a:
                r2.<init>(r3, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgnp.zzc.<init>(java.lang.String, java.lang.Throwable):void");
        }
    }

    public static zzgnp zza(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return new zzb(byteBuffer);
        }
        if (!byteBuffer.isDirect() || byteBuffer.isReadOnly()) {
            throw new IllegalArgumentException("ByteBuffer is read-only");
        } else if (zzgrj.zzb()) {
            return new zze(byteBuffer);
        } else {
            return new zzd(byteBuffer);
        }
    }

    /* compiled from: CodedOutputStream */
    static final class zzd extends zzgnp {
        private final ByteBuffer zzb;
        private final ByteBuffer zzc;
        private final int zzd;

        zzd(ByteBuffer byteBuffer) {
            super();
            this.zzb = byteBuffer;
            this.zzc = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.zzd = byteBuffer.position();
        }

        public final void zza(int i, int i2) throws IOException {
            zzb((i << 3) | i2);
        }

        public final void zzb(int i, int i2) throws IOException {
            zza(i, 0);
            zza(i2);
        }

        public final void zzc(int i, int i2) throws IOException {
            zza(i, 0);
            zzb(i2);
        }

        public final void zze(int i, int i2) throws IOException {
            zza(i, 5);
            zzd(i2);
        }

        public final void zza(int i, long j) throws IOException {
            zza(i, 0);
            zza(j);
        }

        public final void zzc(int i, long j) throws IOException {
            zza(i, 1);
            zzc(j);
        }

        public final void zza(int i, boolean z) throws IOException {
            zza(i, 0);
            zza(z ? (byte) 1 : 0);
        }

        public final void zza(int i, String str) throws IOException {
            zza(i, 2);
            zza(str);
        }

        public final void zza(int i, zzgnb zzgnb) throws IOException {
            zza(i, 2);
            zza(zzgnb);
        }

        public final void zza(int i, zzgpt zzgpt) throws IOException {
            zza(i, 2);
            zza(zzgpt);
        }

        public final void zzb(int i, zzgpt zzgpt) throws IOException {
            zza(1, 3);
            zzc(2, i);
            zza(3, zzgpt);
            zza(1, 4);
        }

        public final void zzb(int i, zzgnb zzgnb) throws IOException {
            zza(1, 3);
            zzc(2, i);
            zza(3, zzgnb);
            zza(1, 4);
        }

        public final void zza(zzgpt zzgpt) throws IOException {
            zzb(zzgpt.zza());
            zzgpt.zza(this);
        }

        public final void zza(byte b) throws IOException {
            try {
                this.zzc.put(b);
            } catch (BufferOverflowException e) {
                throw new zzc(e);
            }
        }

        public final void zza(zzgnb zzgnb) throws IOException {
            zzb(zzgnb.zza());
            zzgnb.zza(this);
        }

        public final void zzc(byte[] bArr, int i, int i2) throws IOException {
            zzb(i2);
            zzb(bArr, 0, i2);
        }

        public final void zza(int i) throws IOException {
            if (i >= 0) {
                zzb(i);
            } else {
                zza((long) i);
            }
        }

        public final void zzb(int i) throws IOException {
            while ((i & -128) != 0) {
                this.zzc.put((byte) ((i & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128));
                i >>>= 7;
            }
            try {
                this.zzc.put((byte) i);
            } catch (BufferOverflowException e) {
                throw new zzc(e);
            }
        }

        public final void zzd(int i) throws IOException {
            try {
                this.zzc.putInt(i);
            } catch (BufferOverflowException e) {
                throw new zzc(e);
            }
        }

        public final void zza(long j) throws IOException {
            while ((-128 & j) != 0) {
                this.zzc.put((byte) ((((int) j) & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128));
                j >>>= 7;
            }
            try {
                this.zzc.put((byte) ((int) j));
            } catch (BufferOverflowException e) {
                throw new zzc(e);
            }
        }

        public final void zzc(long j) throws IOException {
            try {
                this.zzc.putLong(j);
            } catch (BufferOverflowException e) {
                throw new zzc(e);
            }
        }

        public final void zzb(byte[] bArr, int i, int i2) throws IOException {
            try {
                this.zzc.put(bArr, i, i2);
            } catch (IndexOutOfBoundsException e) {
                throw new zzc(e);
            } catch (BufferOverflowException e2) {
                throw new zzc(e2);
            }
        }

        public final void zza(byte[] bArr, int i, int i2) throws IOException {
            zzb(bArr, i, i2);
        }

        public final void zza(String str) throws IOException {
            int position = this.zzc.position();
            try {
                int zzg = zzg(str.length() * 3);
                int zzg2 = zzg(str.length());
                if (zzg2 == zzg) {
                    int position2 = this.zzc.position() + zzg2;
                    this.zzc.position(position2);
                    zzc(str);
                    int position3 = this.zzc.position();
                    this.zzc.position(position);
                    zzb(position3 - position2);
                    this.zzc.position(position3);
                    return;
                }
                zzb(zzgrl.zza(str));
                zzc(str);
            } catch (zzgro e) {
                this.zzc.position(position);
                zza(str, e);
            } catch (IllegalArgumentException e2) {
                throw new zzc(e2);
            }
        }

        public final void zza() {
            this.zzb.position(this.zzc.position());
        }

        public final int zzb() {
            return this.zzc.remaining();
        }

        private final void zzc(String str) throws IOException {
            try {
                zzgrl.zza(str, this.zzc);
            } catch (IndexOutOfBoundsException e) {
                throw new zzc(e);
            }
        }
    }

    /* compiled from: CodedOutputStream */
    static final class zzb extends zza {
        private final ByteBuffer zzb;
        private int zzc;

        zzb(ByteBuffer byteBuffer) {
            super(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            this.zzb = byteBuffer;
            this.zzc = byteBuffer.position();
        }

        public final void zza() {
            this.zzb.position(this.zzc + zze());
        }
    }

    /* compiled from: CodedOutputStream */
    static final class zze extends zzgnp {
        private final ByteBuffer zzb;
        private final ByteBuffer zzc;
        private final long zzd;
        private final long zze;
        private final long zzf;
        private final long zzg = (this.zzf - 10);
        private long zzh = this.zze;

        zze(ByteBuffer byteBuffer) {
            super();
            this.zzb = byteBuffer;
            this.zzc = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.zzd = zzgrj.zza(byteBuffer);
            this.zze = this.zzd + ((long) byteBuffer.position());
            this.zzf = this.zzd + ((long) byteBuffer.limit());
        }

        public final void zza(int i, int i2) throws IOException {
            zzb((i << 3) | i2);
        }

        public final void zzb(int i, int i2) throws IOException {
            zza(i, 0);
            zza(i2);
        }

        public final void zzc(int i, int i2) throws IOException {
            zza(i, 0);
            zzb(i2);
        }

        public final void zze(int i, int i2) throws IOException {
            zza(i, 5);
            zzd(i2);
        }

        public final void zza(int i, long j) throws IOException {
            zza(i, 0);
            zza(j);
        }

        public final void zzc(int i, long j) throws IOException {
            zza(i, 1);
            zzc(j);
        }

        public final void zza(int i, boolean z) throws IOException {
            zza(i, 0);
            zza(z ? (byte) 1 : 0);
        }

        public final void zza(int i, String str) throws IOException {
            zza(i, 2);
            zza(str);
        }

        public final void zza(int i, zzgnb zzgnb) throws IOException {
            zza(i, 2);
            zza(zzgnb);
        }

        public final void zza(int i, zzgpt zzgpt) throws IOException {
            zza(i, 2);
            zza(zzgpt);
        }

        public final void zzb(int i, zzgpt zzgpt) throws IOException {
            zza(1, 3);
            zzc(2, i);
            zza(3, zzgpt);
            zza(1, 4);
        }

        public final void zzb(int i, zzgnb zzgnb) throws IOException {
            zza(1, 3);
            zzc(2, i);
            zza(3, zzgnb);
            zza(1, 4);
        }

        public final void zza(zzgpt zzgpt) throws IOException {
            zzb(zzgpt.zza());
            zzgpt.zza(this);
        }

        public final void zza(byte b) throws IOException {
            long j = this.zzh;
            if (j < this.zzf) {
                this.zzh = 1 + j;
                zzgrj.zza(j, b);
                return;
            }
            throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(j), Long.valueOf(this.zzf), 1));
        }

        public final void zza(zzgnb zzgnb) throws IOException {
            zzb(zzgnb.zza());
            zzgnb.zza(this);
        }

        public final void zzc(byte[] bArr, int i, int i2) throws IOException {
            zzb(i2);
            zzb(bArr, 0, i2);
        }

        public final void zza(int i) throws IOException {
            if (i >= 0) {
                zzb(i);
            } else {
                zza((long) i);
            }
        }

        public final void zzb(int i) throws IOException {
            if (this.zzh <= this.zzg) {
                while ((i & -128) != 0) {
                    long j = this.zzh;
                    this.zzh = j + 1;
                    zzgrj.zza(j, (byte) ((i & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128));
                    i >>>= 7;
                }
                long j2 = this.zzh;
                this.zzh = 1 + j2;
                zzgrj.zza(j2, (byte) i);
                return;
            }
            while (true) {
                long j3 = this.zzh;
                if (j3 >= this.zzf) {
                    throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(j3), Long.valueOf(this.zzf), 1));
                } else if ((i & -128) == 0) {
                    this.zzh = 1 + j3;
                    zzgrj.zza(j3, (byte) i);
                    return;
                } else {
                    this.zzh = j3 + 1;
                    zzgrj.zza(j3, (byte) ((i & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128));
                    i >>>= 7;
                }
            }
        }

        public final void zzd(int i) throws IOException {
            this.zzc.putInt((int) (this.zzh - this.zzd), i);
            this.zzh += 4;
        }

        public final void zza(long j) throws IOException {
            if (this.zzh <= this.zzg) {
                while ((j & -128) != 0) {
                    long j2 = this.zzh;
                    this.zzh = j2 + 1;
                    zzgrj.zza(j2, (byte) ((((int) j) & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128));
                    j >>>= 7;
                }
                long j3 = this.zzh;
                this.zzh = 1 + j3;
                zzgrj.zza(j3, (byte) ((int) j));
                return;
            }
            while (true) {
                long j4 = this.zzh;
                if (j4 >= this.zzf) {
                    throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(j4), Long.valueOf(this.zzf), 1));
                } else if ((j & -128) == 0) {
                    this.zzh = 1 + j4;
                    zzgrj.zza(j4, (byte) ((int) j));
                    return;
                } else {
                    this.zzh = j4 + 1;
                    zzgrj.zza(j4, (byte) ((((int) j) & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128));
                    j >>>= 7;
                }
            }
        }

        public final void zzc(long j) throws IOException {
            this.zzc.putLong((int) (this.zzh - this.zzd), j);
            this.zzh += 8;
        }

        public final void zzb(byte[] bArr, int i, int i2) throws IOException {
            if (bArr != null && i >= 0 && i2 >= 0 && bArr.length - i2 >= i) {
                long j = (long) i2;
                long j2 = this.zzh;
                if (this.zzf - j >= j2) {
                    zzgrj.zza(bArr, (long) i, j2, j);
                    this.zzh += j;
                    return;
                }
            }
            if (bArr == null) {
                throw new NullPointerException("value");
            }
            throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(this.zzh), Long.valueOf(this.zzf), Integer.valueOf(i2)));
        }

        public final void zza(byte[] bArr, int i, int i2) throws IOException {
            zzb(bArr, i, i2);
        }

        public final void zza(String str) throws IOException {
            long j = this.zzh;
            try {
                int zzg2 = zzg(str.length() * 3);
                int zzg3 = zzg(str.length());
                if (zzg3 == zzg2) {
                    int i = ((int) (this.zzh - this.zzd)) + zzg3;
                    this.zzc.position(i);
                    zzgrl.zza(str, this.zzc);
                    int position = this.zzc.position() - i;
                    zzb(position);
                    this.zzh += (long) position;
                    return;
                }
                int zza = zzgrl.zza(str);
                zzb(zza);
                zzi(this.zzh);
                zzgrl.zza(str, this.zzc);
                this.zzh += (long) zza;
            } catch (zzgro e) {
                this.zzh = j;
                zzi(this.zzh);
                zza(str, e);
            } catch (IllegalArgumentException e2) {
                throw new zzc(e2);
            } catch (IndexOutOfBoundsException e3) {
                throw new zzc(e3);
            }
        }

        public final void zza() {
            this.zzb.position((int) (this.zzh - this.zzd));
        }

        public final int zzb() {
            return (int) (this.zzf - this.zzh);
        }

        private final void zzi(long j) {
            this.zzc.position((int) (j - this.zzd));
        }
    }

    /* compiled from: CodedOutputStream */
    static class zza extends zzgnp {
        private final byte[] zzb;
        private final int zzc;
        private final int zzd;
        private int zze;

        zza(byte[] bArr, int i, int i2) {
            super();
            if (bArr != null) {
                int i3 = i + i2;
                if ((i | i2 | (bArr.length - i3)) >= 0) {
                    this.zzb = bArr;
                    this.zzc = i;
                    this.zze = i;
                    this.zzd = i3;
                    return;
                }
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
            }
            throw new NullPointerException("buffer");
        }

        public final void zza(int i, int i2) throws IOException {
            zzb((i << 3) | i2);
        }

        public final void zzb(int i, int i2) throws IOException {
            zza(i, 0);
            zza(i2);
        }

        public final void zzc(int i, int i2) throws IOException {
            zza(i, 0);
            zzb(i2);
        }

        public final void zze(int i, int i2) throws IOException {
            zza(i, 5);
            zzd(i2);
        }

        public final void zza(int i, long j) throws IOException {
            zza(i, 0);
            zza(j);
        }

        public final void zzc(int i, long j) throws IOException {
            zza(i, 1);
            zzc(j);
        }

        public final void zza(int i, boolean z) throws IOException {
            zza(i, 0);
            zza(z ? (byte) 1 : 0);
        }

        public final void zza(int i, String str) throws IOException {
            zza(i, 2);
            zza(str);
        }

        public final void zza(int i, zzgnb zzgnb) throws IOException {
            zza(i, 2);
            zza(zzgnb);
        }

        public final void zza(zzgnb zzgnb) throws IOException {
            zzb(zzgnb.zza());
            zzgnb.zza(this);
        }

        public final void zzc(byte[] bArr, int i, int i2) throws IOException {
            zzb(i2);
            zzb(bArr, 0, i2);
        }

        public final void zza(int i, zzgpt zzgpt) throws IOException {
            zza(i, 2);
            zza(zzgpt);
        }

        public final void zzb(int i, zzgpt zzgpt) throws IOException {
            zza(1, 3);
            zzc(2, i);
            zza(3, zzgpt);
            zza(1, 4);
        }

        public final void zzb(int i, zzgnb zzgnb) throws IOException {
            zza(1, 3);
            zzc(2, i);
            zza(3, zzgnb);
            zza(1, 4);
        }

        public final void zza(zzgpt zzgpt) throws IOException {
            zzb(zzgpt.zza());
            zzgpt.zza(this);
        }

        public final void zza(byte b) throws IOException {
            try {
                byte[] bArr = this.zzb;
                int i = this.zze;
                this.zze = i + 1;
                bArr[i] = b;
            } catch (IndexOutOfBoundsException e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1), e);
            }
        }

        public final void zza(int i) throws IOException {
            if (i >= 0) {
                zzb(i);
            } else {
                zza((long) i);
            }
        }

        public final void zzb(int i) throws IOException {
            if (!zzgnp.zzc || zzb() < 10) {
                while ((i & -128) != 0) {
                    byte[] bArr = this.zzb;
                    int i2 = this.zze;
                    this.zze = i2 + 1;
                    bArr[i2] = (byte) ((i & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128);
                    i >>>= 7;
                }
                try {
                    byte[] bArr2 = this.zzb;
                    int i3 = this.zze;
                    this.zze = i3 + 1;
                    bArr2[i3] = (byte) i;
                } catch (IndexOutOfBoundsException e) {
                    throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1), e);
                }
            } else {
                while ((i & -128) != 0) {
                    byte[] bArr3 = this.zzb;
                    int i4 = this.zze;
                    this.zze = i4 + 1;
                    zzgrj.zza(bArr3, (long) i4, (byte) ((i & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128));
                    i >>>= 7;
                }
                byte[] bArr4 = this.zzb;
                int i5 = this.zze;
                this.zze = i5 + 1;
                zzgrj.zza(bArr4, (long) i5, (byte) i);
            }
        }

        public final void zzd(int i) throws IOException {
            try {
                byte[] bArr = this.zzb;
                int i2 = this.zze;
                this.zze = i2 + 1;
                bArr[i2] = (byte) i;
                byte[] bArr2 = this.zzb;
                int i3 = this.zze;
                this.zze = i3 + 1;
                bArr2[i3] = (byte) (i >> 8);
                byte[] bArr3 = this.zzb;
                int i4 = this.zze;
                this.zze = i4 + 1;
                bArr3[i4] = (byte) (i >> 16);
                byte[] bArr4 = this.zzb;
                int i5 = this.zze;
                this.zze = i5 + 1;
                bArr4[i5] = i >> Ascii.CAN;
            } catch (IndexOutOfBoundsException e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1), e);
            }
        }

        public final void zza(long j) throws IOException {
            if (!zzgnp.zzc || zzb() < 10) {
                while ((j & -128) != 0) {
                    byte[] bArr = this.zzb;
                    int i = this.zze;
                    this.zze = i + 1;
                    bArr[i] = (byte) ((((int) j) & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128);
                    j >>>= 7;
                }
                try {
                    byte[] bArr2 = this.zzb;
                    int i2 = this.zze;
                    this.zze = i2 + 1;
                    bArr2[i2] = (byte) ((int) j);
                } catch (IndexOutOfBoundsException e) {
                    throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1), e);
                }
            } else {
                while ((j & -128) != 0) {
                    byte[] bArr3 = this.zzb;
                    int i3 = this.zze;
                    this.zze = i3 + 1;
                    zzgrj.zza(bArr3, (long) i3, (byte) ((((int) j) & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128));
                    j >>>= 7;
                }
                byte[] bArr4 = this.zzb;
                int i4 = this.zze;
                this.zze = i4 + 1;
                zzgrj.zza(bArr4, (long) i4, (byte) ((int) j));
            }
        }

        public final void zzc(long j) throws IOException {
            try {
                byte[] bArr = this.zzb;
                int i = this.zze;
                this.zze = i + 1;
                bArr[i] = (byte) ((int) j);
                byte[] bArr2 = this.zzb;
                int i2 = this.zze;
                this.zze = i2 + 1;
                bArr2[i2] = (byte) ((int) (j >> 8));
                byte[] bArr3 = this.zzb;
                int i3 = this.zze;
                this.zze = i3 + 1;
                bArr3[i3] = (byte) ((int) (j >> 16));
                byte[] bArr4 = this.zzb;
                int i4 = this.zze;
                this.zze = i4 + 1;
                bArr4[i4] = (byte) ((int) (j >> 24));
                byte[] bArr5 = this.zzb;
                int i5 = this.zze;
                this.zze = i5 + 1;
                bArr5[i5] = (byte) ((int) (j >> 32));
                byte[] bArr6 = this.zzb;
                int i6 = this.zze;
                this.zze = i6 + 1;
                bArr6[i6] = (byte) ((int) (j >> 40));
                byte[] bArr7 = this.zzb;
                int i7 = this.zze;
                this.zze = i7 + 1;
                bArr7[i7] = (byte) ((int) (j >> 48));
                byte[] bArr8 = this.zzb;
                int i8 = this.zze;
                this.zze = i8 + 1;
                bArr8[i8] = (byte) ((int) (j >> 56));
            } catch (IndexOutOfBoundsException e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1), e);
            }
        }

        public final void zzb(byte[] bArr, int i, int i2) throws IOException {
            try {
                System.arraycopy(bArr, i, this.zzb, this.zze, i2);
                this.zze += i2;
            } catch (IndexOutOfBoundsException e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zze), Integer.valueOf(this.zzd), Integer.valueOf(i2)), e);
            }
        }

        public final void zza(byte[] bArr, int i, int i2) throws IOException {
            zzb(bArr, i, i2);
        }

        public final void zza(String str) throws IOException {
            int i = this.zze;
            try {
                int zzg = zzg(str.length() * 3);
                int zzg2 = zzg(str.length());
                if (zzg2 == zzg) {
                    this.zze = i + zzg2;
                    int zza = zzgrl.zza(str, this.zzb, this.zze, zzb());
                    this.zze = i;
                    zzb((zza - i) - zzg2);
                    this.zze = zza;
                    return;
                }
                zzb(zzgrl.zza(str));
                this.zze = zzgrl.zza(str, this.zzb, this.zze, zzb());
            } catch (zzgro e) {
                this.zze = i;
                zza(str, e);
            } catch (IndexOutOfBoundsException e2) {
                throw new zzc(e2);
            }
        }

        public void zza() {
        }

        public final int zzb() {
            return this.zzd - this.zze;
        }

        public final int zze() {
            return this.zze - this.zzc;
        }
    }

    private zzgnp() {
    }

    public final void zzd(int i, int i2) throws IOException {
        zzc(i, zzn(i2));
    }

    public final void zzb(int i, long j) throws IOException {
        zza(i, zzi(j));
    }

    public final void zza(int i, float f) throws IOException {
        zze(i, Float.floatToRawIntBits(f));
    }

    public final void zza(int i, double d) throws IOException {
        zzc(i, Double.doubleToRawLongBits(d));
    }

    public final void zzc(int i) throws IOException {
        zzb(zzn(i));
    }

    public final void zzb(long j) throws IOException {
        zza(zzi(j));
    }

    public final void zza(float f) throws IOException {
        zzd(Float.floatToRawIntBits(f));
    }

    public final void zza(double d) throws IOException {
        zzc(Double.doubleToRawLongBits(d));
    }

    public final void zza(boolean z) throws IOException {
        zza(z ? (byte) 1 : 0);
    }

    public static int zzf(int i, int i2) {
        return zze(i) + zzf(i2);
    }

    public static int zzg(int i, int i2) {
        return zze(i) + zzg(i2);
    }

    public static int zzh(int i, int i2) {
        return zze(i) + zzg(zzn(i2));
    }

    public static int zzi(int i, int i2) {
        return zze(i) + 4;
    }

    public static int zzj(int i, int i2) {
        return zze(i) + 4;
    }

    public static int zzd(int i, long j) {
        return zze(i) + zze(j);
    }

    public static int zze(int i, long j) {
        return zze(i) + zze(j);
    }

    public static int zzf(int i, long j) {
        return zze(i) + zze(zzi(j));
    }

    public static int zzg(int i, long j) {
        return zze(i) + 8;
    }

    public static int zzh(int i, long j) {
        return zze(i) + 8;
    }

    public static int zzb(int i, float f) {
        return zze(i) + 4;
    }

    public static int zzb(int i, double d) {
        return zze(i) + 8;
    }

    public static int zzb(int i, boolean z) {
        return zze(i) + 1;
    }

    public static int zzk(int i, int i2) {
        return zze(i) + zzf(i2);
    }

    public static int zzb(int i, String str) {
        return zze(i) + zzb(str);
    }

    public static int zzc(int i, zzgnb zzgnb) {
        int zze2 = zze(i);
        int zza2 = zzgnb.zza();
        return zze2 + zzg(zza2) + zza2;
    }

    public static int zza(int i, zzgpa zzgpa) {
        int zze2 = zze(i);
        int zzb2 = zzgpa.zzb();
        return zze2 + zzg(zzb2) + zzb2;
    }

    public static int zzc(int i, zzgpt zzgpt) {
        return zze(i) + zzb(zzgpt);
    }

    public static int zzd(int i, zzgpt zzgpt) {
        return (zze(1) << 1) + zzg(2, i) + zzc(3, zzgpt);
    }

    public static int zzd(int i, zzgnb zzgnb) {
        return (zze(1) << 1) + zzg(2, i) + zzc(3, zzgnb);
    }

    public static int zzb(int i, zzgpa zzgpa) {
        return (zze(1) << 1) + zzg(2, i) + zza(3, zzgpa);
    }

    public static int zze(int i) {
        return zzg(i << 3);
    }

    public static int zzf(int i) {
        if (i >= 0) {
            return zzg(i);
        }
        return 10;
    }

    public static int zzg(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        if ((i & -268435456) == 0) {
            return 4;
        }
        return 5;
    }

    public static int zzh(int i) {
        return zzg(zzn(i));
    }

    public static int zzi(int i) {
        return 4;
    }

    public static int zzj(int i) {
        return 4;
    }

    public static int zzd(long j) {
        return zze(j);
    }

    public static int zze(long j) {
        int i;
        if ((-128 & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        if ((-34359738368L & j) != 0) {
            i = 6;
            j >>>= 28;
        } else {
            i = 2;
        }
        if ((-2097152 & j) != 0) {
            i += 2;
            j >>>= 14;
        }
        if ((j & -16384) != 0) {
            return i + 1;
        }
        return i;
    }

    public static int zzf(long j) {
        return zze(zzi(j));
    }

    public static int zzg(long j) {
        return 8;
    }

    public static int zzh(long j) {
        return 8;
    }

    public static int zzb(float f) {
        return 4;
    }

    public static int zzb(double d) {
        return 8;
    }

    public static int zzb(boolean z) {
        return 1;
    }

    public static int zzk(int i) {
        return zzf(i);
    }

    public static int zzb(String str) {
        int i;
        try {
            i = zzgrl.zza(str);
        } catch (zzgro e) {
            i = str.getBytes(zzgon.zza).length;
        }
        return zzg(i) + i;
    }

    public static int zza(zzgpa zzgpa) {
        int zzb2 = zzgpa.zzb();
        return zzg(zzb2) + zzb2;
    }

    public static int zzb(zzgnb zzgnb) {
        int zza2 = zzgnb.zza();
        return zzg(zza2) + zza2;
    }

    public static int zzb(byte[] bArr) {
        int length = bArr.length;
        return zzg(length) + length;
    }

    public static int zzb(zzgpt zzgpt) {
        int zza2 = zzgpt.zza();
        return zzg(zza2) + zza2;
    }

    static int zzl(int i) {
        return zzg(i) + i;
    }

    private static int zzn(int i) {
        return (i >> 31) ^ (i << 1);
    }

    private static long zzi(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public final void zzc() {
        if (zzb() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void}
     arg types: [java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, com.google.android.gms.internal.zzgro]
     candidates:
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[]):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void} */
    /* access modifiers changed from: package-private */
    public final void zza(String str, zzgro zzgro) throws IOException {
        zzb.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", (Throwable) zzgro);
        byte[] bytes = str.getBytes(zzgon.zza);
        try {
            zzb(bytes.length);
            zza(bytes, 0, bytes.length);
        } catch (IndexOutOfBoundsException e) {
            throw new zzc(e);
        } catch (zzc e2) {
            throw e2;
        }
    }

    @Deprecated
    public final void zze(int i, zzgpt zzgpt) throws IOException {
        zza(i, 3);
        zzgpt.zza(this);
        zza(i, 4);
    }

    @Deprecated
    public static int zzf(int i, zzgpt zzgpt) {
        return (zze(i) << 1) + zzgpt.zza();
    }

    @Deprecated
    public static int zzc(zzgpt zzgpt) {
        return zzgpt.zza();
    }

    @Deprecated
    public static int zzm(int i) {
        return zzg(i);
    }
}
