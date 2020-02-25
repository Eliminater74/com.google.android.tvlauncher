package com.google.android.gms.internal;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

/* compiled from: CodedOutputByteBufferNano */
public final class zzgrz {
    private final ByteBuffer zza;
    private zzgnp zzb;
    private int zzc;

    private zzgrz(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    private zzgrz(ByteBuffer byteBuffer) {
        this.zza = byteBuffer;
        this.zza.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static zzgrz zza(byte[] bArr) {
        return zza(bArr, 0, bArr.length);
    }

    public static zzgrz zza(byte[] bArr, int i, int i2) {
        return new zzgrz(bArr, i, i2);
    }

    private static int zza(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char charAt = charSequence.charAt(i2);
            if (charAt < 2048) {
                i3 += (127 - charAt) >>> 31;
                i2++;
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char charAt2 = charSequence.charAt(i2);
                    if (charAt2 < 2048) {
                        i += (127 - charAt2) >>> 31;
                    } else {
                        i += 2;
                        if (55296 <= charAt2 && charAt2 <= 57343) {
                            if (Character.codePointAt(charSequence, i2) >= 65536) {
                                i2++;
                            } else {
                                StringBuilder sb = new StringBuilder(39);
                                sb.append("Unpaired surrogate at index ");
                                sb.append(i2);
                                throw new IllegalArgumentException(sb.toString());
                            }
                        }
                    }
                    i2++;
                }
                i3 += i;
            }
        }
        if (i3 >= length) {
            return i3;
        }
        StringBuilder sb2 = new StringBuilder(54);
        sb2.append("UTF-8 length does not fit in int: ");
        sb2.append(((long) i3) + 4294967296L);
        throw new IllegalArgumentException(sb2.toString());
    }

    private static void zza(CharSequence charSequence, ByteBuffer byteBuffer) {
        int i;
        char charAt;
        if (!byteBuffer.isReadOnly()) {
            int i2 = 0;
            if (byteBuffer.hasArray()) {
                try {
                    byte[] array = byteBuffer.array();
                    int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
                    int remaining = byteBuffer.remaining();
                    int length = charSequence.length();
                    int i3 = remaining + arrayOffset;
                    while (i2 < length) {
                        int i4 = i2 + arrayOffset;
                        if (i4 >= i3 || (charAt = charSequence.charAt(i2)) >= 128) {
                            break;
                        }
                        array[i4] = (byte) charAt;
                        i2++;
                    }
                    if (i2 == length) {
                        i = arrayOffset + length;
                    } else {
                        i = arrayOffset + i2;
                        while (i2 < length) {
                            char charAt2 = charSequence.charAt(i2);
                            if (charAt2 < 128 && i < i3) {
                                array[i] = (byte) charAt2;
                                i++;
                            } else if (charAt2 < 2048 && i <= i3 - 2) {
                                int i5 = i + 1;
                                array[i] = (byte) ((charAt2 >>> 6) | ClientAnalytics.LogRequest.LogSource.NBU_GCONNECT_KIMCHI_VALUE);
                                i = i5 + 1;
                                array[i5] = (byte) ((charAt2 & '?') | 128);
                            } else if ((charAt2 < 55296 || 57343 < charAt2) && i <= i3 - 3) {
                                int i6 = i + 1;
                                array[i] = (byte) ((charAt2 >>> 12) | ClientAnalytics.LogRequest.LogSource.PIGEON_EXPERIMENTAL_VALUE);
                                int i7 = i6 + 1;
                                array[i6] = (byte) (((charAt2 >>> 6) & 63) | 128);
                                array[i7] = (byte) ((charAt2 & '?') | 128);
                                i = i7 + 1;
                            } else if (i <= i3 - 4) {
                                int i8 = i2 + 1;
                                if (i8 != charSequence.length()) {
                                    char charAt3 = charSequence.charAt(i8);
                                    if (Character.isSurrogatePair(charAt2, charAt3)) {
                                        int codePoint = Character.toCodePoint(charAt2, charAt3);
                                        int i9 = i + 1;
                                        array[i] = (byte) ((codePoint >>> 18) | 240);
                                        int i10 = i9 + 1;
                                        array[i9] = (byte) (((codePoint >>> 12) & 63) | 128);
                                        int i11 = i10 + 1;
                                        array[i10] = (byte) (((codePoint >>> 6) & 63) | 128);
                                        i = i11 + 1;
                                        array[i11] = (byte) ((codePoint & 63) | 128);
                                        i2 = i8;
                                    } else {
                                        i2 = i8;
                                    }
                                }
                                StringBuilder sb = new StringBuilder(39);
                                sb.append("Unpaired surrogate at index ");
                                sb.append(i2 - 1);
                                throw new IllegalArgumentException(sb.toString());
                            } else {
                                StringBuilder sb2 = new StringBuilder(37);
                                sb2.append("Failed writing ");
                                sb2.append(charAt2);
                                sb2.append(" at index ");
                                sb2.append(i);
                                throw new ArrayIndexOutOfBoundsException(sb2.toString());
                            }
                            i2++;
                        }
                    }
                    byteBuffer.position(i - byteBuffer.arrayOffset());
                } catch (ArrayIndexOutOfBoundsException e) {
                    BufferOverflowException bufferOverflowException = new BufferOverflowException();
                    bufferOverflowException.initCause(e);
                    throw bufferOverflowException;
                }
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char charAt4 = charSequence.charAt(i2);
                    if (charAt4 < 128) {
                        byteBuffer.put((byte) charAt4);
                    } else if (charAt4 < 2048) {
                        byteBuffer.put((byte) ((charAt4 >>> 6) | ClientAnalytics.LogRequest.LogSource.NBU_GCONNECT_KIMCHI_VALUE));
                        byteBuffer.put((byte) ((charAt4 & '?') | 128));
                    } else if (charAt4 < 55296 || 57343 < charAt4) {
                        byteBuffer.put((byte) ((charAt4 >>> 12) | ClientAnalytics.LogRequest.LogSource.PIGEON_EXPERIMENTAL_VALUE));
                        byteBuffer.put((byte) (((charAt4 >>> 6) & 63) | 128));
                        byteBuffer.put((byte) ((charAt4 & '?') | 128));
                    } else {
                        int i12 = i2 + 1;
                        if (i12 != charSequence.length()) {
                            char charAt5 = charSequence.charAt(i12);
                            if (Character.isSurrogatePair(charAt4, charAt5)) {
                                int codePoint2 = Character.toCodePoint(charAt4, charAt5);
                                byteBuffer.put((byte) ((codePoint2 >>> 18) | 240));
                                byteBuffer.put((byte) (((codePoint2 >>> 12) & 63) | 128));
                                byteBuffer.put((byte) (((codePoint2 >>> 6) & 63) | 128));
                                byteBuffer.put((byte) ((codePoint2 & 63) | 128));
                                i2 = i12;
                            } else {
                                i2 = i12;
                            }
                        }
                        StringBuilder sb3 = new StringBuilder(39);
                        sb3.append("Unpaired surrogate at index ");
                        sb3.append(i2 - 1);
                        throw new IllegalArgumentException(sb3.toString());
                    }
                    i2++;
                }
            }
        } else {
            throw new ReadOnlyBufferException();
        }
    }

    public static int zze(int i, long j) {
        return zzb(i) + zzb(j);
    }

    public static int zzf(int i, long j) {
        return zzb(i) + zzb(j);
    }

    public static int zzb(int i, int i2) {
        return zzb(i) + zza(i2);
    }

    public static int zzb(int i, String str) {
        return zzb(i) + zza(str);
    }

    public static int zzb(int i, zzgsh zzgsh) {
        int zzb2 = zzb(i);
        int serializedSize = zzgsh.getSerializedSize();
        return zzb2 + zzd(serializedSize) + serializedSize;
    }

    public static int zzb(int i, byte[] bArr) {
        return zzb(i) + zzc(bArr);
    }

    public static int zzg(int i, long j) {
        return zzb(i) + zzb(zzd(j));
    }

    public static int zza(int i) {
        if (i >= 0) {
            return zzd(i);
        }
        return 10;
    }

    public static int zza(String str) {
        int zza2 = zza((CharSequence) str);
        return zzd(zza2) + zza2;
    }

    public static int zzc(byte[] bArr) {
        return zzd(bArr.length) + bArr.length;
    }

    public static int zzb(int i) {
        return zzd(i << 3);
    }

    public static int zzd(int i) {
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

    public static int zzb(long j) {
        if ((-128 & j) == 0) {
            return 1;
        }
        if ((-16384 & j) == 0) {
            return 2;
        }
        if ((-2097152 & j) == 0) {
            return 3;
        }
        if ((-268435456 & j) == 0) {
            return 4;
        }
        if ((-34359738368L & j) == 0) {
            return 5;
        }
        if ((-4398046511104L & j) == 0) {
            return 6;
        }
        if ((-562949953421312L & j) == 0) {
            return 7;
        }
        if ((-72057594037927936L & j) == 0) {
            return 8;
        }
        if ((j & Long.MIN_VALUE) == 0) {
            return 9;
        }
        return 10;
    }

    public static int zze(int i) {
        return (i >> 31) ^ (i << 1);
    }

    private static long zzd(long j) {
        return (j >> 63) ^ (j << 1);
    }

    private final zzgnp zzb() throws IOException {
        if (this.zzb == null) {
            this.zzb = zzgnp.zza(this.zza);
            this.zzc = this.zza.position();
        } else if (this.zzc != this.zza.position()) {
            this.zzb.zzb(this.zza.array(), this.zzc, this.zza.position() - this.zzc);
            this.zzc = this.zza.position();
        }
        return this.zzb;
    }

    public final void zza(int i, double d) throws IOException {
        zzc(i, 1);
        zzc(Double.doubleToLongBits(d));
    }

    public final void zza(int i, float f) throws IOException {
        zzc(i, 5);
        int floatToIntBits = Float.floatToIntBits(f);
        if (this.zza.remaining() >= 4) {
            this.zza.putInt(floatToIntBits);
            return;
        }
        throw new zzgsa(this.zza.position(), this.zza.limit());
    }

    public final void zza(int i, long j) throws IOException {
        zzc(i, 0);
        zza(j);
    }

    public final void zzb(int i, long j) throws IOException {
        zzc(i, 0);
        zza(j);
    }

    public final void zza(int i, int i2) throws IOException {
        zzc(i, 0);
        if (i2 >= 0) {
            zzc(i2);
        } else {
            zza((long) i2);
        }
    }

    public final void zzc(int i, long j) throws IOException {
        zzc(i, 1);
        zzc(j);
    }

    public final void zza(int i, boolean z) throws IOException {
        zzc(i, 0);
        byte b = z ? (byte) 1 : 0;
        if (this.zza.hasRemaining()) {
            this.zza.put(b);
            return;
        }
        throw new zzgsa(this.zza.position(), this.zza.limit());
    }

    public final void zza(int i, String str) throws IOException {
        zzc(i, 2);
        try {
            int zzd = zzd(str.length());
            if (zzd == zzd(str.length() * 3)) {
                int position = this.zza.position();
                if (this.zza.remaining() >= zzd) {
                    this.zza.position(position + zzd);
                    zza(str, this.zza);
                    int position2 = this.zza.position();
                    this.zza.position(position);
                    zzc((position2 - position) - zzd);
                    this.zza.position(position2);
                    return;
                }
                throw new zzgsa(position + zzd, this.zza.limit());
            }
            zzc(zza((CharSequence) str));
            zza(str, this.zza);
        } catch (BufferOverflowException e) {
            zzgsa zzgsa = new zzgsa(this.zza.position(), this.zza.limit());
            zzgsa.initCause(e);
            throw zzgsa;
        }
    }

    public final void zza(int i, zzgsh zzgsh) throws IOException {
        zzc(i, 2);
        zza(zzgsh);
    }

    public final void zza(int i, zzgpt zzgpt) throws IOException {
        zzgnp zzb2 = zzb();
        zzb2.zza(i, zzgpt);
        zzb2.zza();
        this.zzc = this.zza.position();
    }

    public final void zza(int i, byte[] bArr) throws IOException {
        zzc(i, 2);
        zzb(bArr);
    }

    public final void zzd(int i, long j) throws IOException {
        zzc(i, 0);
        zza(zzd(j));
    }

    public final void zza(zzgsh zzgsh) throws IOException {
        zzc(zzgsh.getCachedSize());
        zzgsh.writeTo(this);
    }

    public final void zzb(byte[] bArr) throws IOException {
        zzc(bArr.length);
        zzd(bArr);
    }

    public final void zza() {
        if (this.zza.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", Integer.valueOf(this.zza.remaining())));
        }
    }

    private final void zzf(int i) throws IOException {
        byte b = (byte) i;
        if (this.zza.hasRemaining()) {
            this.zza.put(b);
            return;
        }
        throw new zzgsa(this.zza.position(), this.zza.limit());
    }

    public final void zzd(byte[] bArr) throws IOException {
        int length = bArr.length;
        if (this.zza.remaining() >= length) {
            this.zza.put(bArr, 0, length);
            return;
        }
        throw new zzgsa(this.zza.position(), this.zza.limit());
    }

    public final void zzc(int i, int i2) throws IOException {
        zzc((i << 3) | i2);
    }

    public final void zzc(int i) throws IOException {
        while ((i & -128) != 0) {
            zzf((i & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128);
            i >>>= 7;
        }
        zzf(i);
    }

    public final void zza(long j) throws IOException {
        while ((-128 & j) != 0) {
            zzf((((int) j) & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128);
            j >>>= 7;
        }
        zzf((int) j);
    }

    private final void zzc(long j) throws IOException {
        if (this.zza.remaining() >= 8) {
            this.zza.putLong(j);
            return;
        }
        throw new zzgsa(this.zza.position(), this.zza.limit());
    }
}
