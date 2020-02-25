package com.google.android.gms.feedback;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.util.Pair;

import com.google.android.exoplayer2.C0841C;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileTeleporter extends zzbkv {
    public static final Parcelable.Creator<FileTeleporter> CREATOR = new zzi();
    final String zza;
    final String zzb;
    @VisibleForTesting
    byte[] zzc;
    private ParcelFileDescriptor zzd;
    private File zze;

    FileTeleporter(ParcelFileDescriptor parcelFileDescriptor, String str, String str2) {
        this.zzd = parcelFileDescriptor;
        this.zza = str;
        this.zzb = str2;
    }

    public FileTeleporter(byte[] bArr, String str, String str2) {
        this((ParcelFileDescriptor) null, str, str2);
        this.zzc = bArr;
    }

    @VisibleForTesting
    private static void zza(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            Log.w("FileTeleporter", "Could not close stream", e);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, android.os.ParcelFileDescriptor, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void
     arg types: [android.os.Parcel, int, java.lang.String, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.util.SparseArray<java.lang.String>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Boolean, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Double, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Float, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Integer, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Long, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.util.List<java.lang.Integer>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, double[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void */
    public void writeToParcel(Parcel parcel, int i) {
        if (this.zzd == null) {
            DataOutputStream dataOutputStream = new DataOutputStream(zza());
            try {
                dataOutputStream.writeInt(this.zzc.length);
                dataOutputStream.writeUTF(this.zza);
                dataOutputStream.writeUTF(this.zzb);
                dataOutputStream.write(this.zzc);
                zza(dataOutputStream);
            } catch (IOException e) {
                throw new IllegalStateException("Could not write into unlinked file", e);
            } catch (Throwable th) {
                zza(dataOutputStream);
                throw th;
            }
        }
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 2, (Parcelable) this.zzd, i, false);
        zzbky.zza(parcel, 3, this.zza, false);
        zzbky.zza(parcel, 4, this.zzb, false);
        zzbky.zza(parcel, zza2);
    }

    public Pair<String, Pair<String, byte[]>> get() {
        ParcelFileDescriptor parcelFileDescriptor = this.zzd;
        if (parcelFileDescriptor == null) {
            return Pair.create(this.zzb, Pair.create(this.zza, this.zzc));
        }
        DataInputStream dataInputStream = new DataInputStream(new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor));
        try {
            byte[] bArr = new byte[dataInputStream.readInt()];
            String readUTF = dataInputStream.readUTF();
            String readUTF2 = dataInputStream.readUTF();
            dataInputStream.read(bArr);
            zza(dataInputStream);
            return Pair.create(readUTF2, Pair.create(readUTF, bArr));
        } catch (IOException e) {
            throw new IllegalStateException("Could not read from parcel file descriptor", e);
        } catch (Throwable th) {
            zza(dataInputStream);
            throw th;
        }
    }

    public void setTempDir(File file) {
        if (file != null) {
            this.zze = file;
            return;
        }
        throw new NullPointerException("Cannot set null temp directory");
    }

    @VisibleForTesting
    private final FileOutputStream zza() {
        File file = this.zze;
        if (file != null) {
            try {
                File createTempFile = File.createTempFile("teleporter", ".tmp", file);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
                    this.zzd = ParcelFileDescriptor.open(createTempFile, C0841C.ENCODING_PCM_MU_LAW);
                    createTempFile.delete();
                    return fileOutputStream;
                } catch (FileNotFoundException e) {
                    throw new IllegalStateException("Temporary file is somehow already deleted.");
                }
            } catch (IOException e2) {
                throw new IllegalStateException("Could not create temporary file:", e2);
            }
        } else {
            throw new IllegalStateException("setTempDir() must be called before writing this object to a parcel.");
        }
    }
}
