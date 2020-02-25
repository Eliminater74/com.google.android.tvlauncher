package com.google.android.gms.common.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.exoplayer2.C0841C;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

@Hide
public class BitmapTeleporter extends zzbkv implements ReflectedParcelable {
    public static final Parcelable.Creator<BitmapTeleporter> CREATOR = new zza();
    private final int zza;
    private final int zzc;
    private ParcelFileDescriptor zzb;
    private Bitmap zzd;
    private boolean zze;
    private File zzf;

    BitmapTeleporter(int i, ParcelFileDescriptor parcelFileDescriptor, int i2) {
        this.zza = i;
        this.zzb = parcelFileDescriptor;
        this.zzc = i2;
        this.zzd = null;
        this.zze = false;
    }

    public BitmapTeleporter(Bitmap bitmap) {
        this.zza = 1;
        this.zzb = null;
        this.zzc = 0;
        this.zzd = bitmap;
        this.zze = true;
    }

    private static void zza(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            Log.w("BitmapTeleporter", "Could not close stream", e);
        }
    }

    public Bitmap get() {
        if (!this.zze) {
            DataInputStream dataInputStream = new DataInputStream(new ParcelFileDescriptor.AutoCloseInputStream(this.zzb));
            try {
                byte[] bArr = new byte[dataInputStream.readInt()];
                int readInt = dataInputStream.readInt();
                int readInt2 = dataInputStream.readInt();
                Bitmap.Config valueOf = Bitmap.Config.valueOf(dataInputStream.readUTF());
                dataInputStream.read(bArr);
                zza(dataInputStream);
                ByteBuffer wrap = ByteBuffer.wrap(bArr);
                Bitmap createBitmap = Bitmap.createBitmap(readInt, readInt2, valueOf);
                createBitmap.copyPixelsFromBuffer(wrap);
                this.zzd = createBitmap;
                this.zze = true;
            } catch (IOException e) {
                throw new IllegalStateException("Could not read from parcel file descriptor", e);
            } catch (Throwable th) {
                zza(dataInputStream);
                throw th;
            }
        }
        return this.zzd;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, android.os.ParcelFileDescriptor, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    public void writeToParcel(Parcel parcel, int i) {
        if (this.zzb == null) {
            Bitmap bitmap = this.zzd;
            ByteBuffer allocate = ByteBuffer.allocate(bitmap.getRowBytes() * bitmap.getHeight());
            bitmap.copyPixelsToBuffer(allocate);
            byte[] array = allocate.array();
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(zza()));
            try {
                dataOutputStream.writeInt(array.length);
                dataOutputStream.writeInt(bitmap.getWidth());
                dataOutputStream.writeInt(bitmap.getHeight());
                dataOutputStream.writeUTF(bitmap.getConfig().toString());
                dataOutputStream.write(array);
                zza(dataOutputStream);
            } catch (IOException e) {
                throw new IllegalStateException("Could not write into unlinked file", e);
            } catch (Throwable th) {
                zza(dataOutputStream);
                throw th;
            }
        }
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zza);
        zzbky.zza(parcel, 2, (Parcelable) this.zzb, i | 1, false);
        zzbky.zza(parcel, 3, this.zzc);
        zzbky.zza(parcel, zza2);
        this.zzb = null;
    }

    public void release() {
        if (!this.zze) {
            try {
                this.zzb.close();
            } catch (IOException e) {
                Log.w("BitmapTeleporter", "Could not close PFD", e);
            }
        }
    }

    public void setTempDir(File file) {
        if (file != null) {
            this.zzf = file;
            return;
        }
        throw new NullPointerException("Cannot set null temp directory");
    }

    private final FileOutputStream zza() {
        File file = this.zzf;
        if (file != null) {
            try {
                File createTempFile = File.createTempFile("teleporter", ".tmp", file);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
                    this.zzb = ParcelFileDescriptor.open(createTempFile, C0841C.ENCODING_PCM_MU_LAW);
                    createTempFile.delete();
                    return fileOutputStream;
                } catch (FileNotFoundException e) {
                    throw new IllegalStateException("Temporary file is somehow already deleted");
                }
            } catch (IOException e2) {
                throw new IllegalStateException("Could not create temporary file", e2);
            }
        } else {
            throw new IllegalStateException("setTempDir() must be called before writing this object to a parcel");
        }
    }
}
