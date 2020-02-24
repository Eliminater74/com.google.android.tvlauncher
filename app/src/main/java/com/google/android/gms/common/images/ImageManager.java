package com.google.android.gms.common.images;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.p001v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbku;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager {
    /* access modifiers changed from: private */
    public static final Object zza = new Object();
    /* access modifiers changed from: private */
    public static HashSet<Uri> zzb = new HashSet<>();
    private static ImageManager zzc;
    /* access modifiers changed from: private */
    public final Context zzd;
    /* access modifiers changed from: private */
    public final Handler zze = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final ExecutorService zzf = Executors.newFixedThreadPool(4);
    /* access modifiers changed from: private */
    public final zza zzg = null;
    /* access modifiers changed from: private */
    public final zzbku zzh = new zzbku();
    /* access modifiers changed from: private */
    public final Map<zza, ImageReceiver> zzi = new HashMap();
    /* access modifiers changed from: private */
    public final Map<Uri, ImageReceiver> zzj = new HashMap();
    /* access modifiers changed from: private */
    public final Map<Uri, Long> zzk = new HashMap();

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    public static ImageManager create(Context context) {
        if (zzc == null) {
            zzc = new ImageManager(context, false);
        }
        return zzc;
    }

    static final class zza extends LruCache<zzb, Bitmap> {
        /* access modifiers changed from: protected */
        public final /* synthetic */ int sizeOf(Object obj, Object obj2) {
            Bitmap bitmap = (Bitmap) obj2;
            return bitmap.getHeight() * bitmap.getRowBytes();
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
            super.entryRemoved(z, (zzb) obj, (Bitmap) obj2, (Bitmap) obj3);
        }
    }

    final class zzc implements Runnable {
        private final zza zza;

        public zzc(zza zza2) {
            this.zza = zza2;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.android.gms.common.images.zza.zza(android.content.Context, com.google.android.gms.internal.zzbku, boolean):void
         arg types: [android.content.Context, com.google.android.gms.internal.zzbku, int]
         candidates:
          com.google.android.gms.common.images.zza.zza(android.content.Context, android.graphics.Bitmap, boolean):void
          com.google.android.gms.common.images.zza.zza(android.content.Context, com.google.android.gms.internal.zzbku, boolean):void */
        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.android.gms.common.images.zza.zza(android.content.Context, android.graphics.Bitmap, boolean):void
         arg types: [android.content.Context, android.graphics.Bitmap, int]
         candidates:
          com.google.android.gms.common.images.zza.zza(android.content.Context, com.google.android.gms.internal.zzbku, boolean):void
          com.google.android.gms.common.images.zza.zza(android.content.Context, android.graphics.Bitmap, boolean):void */
        public final void run() {
            com.google.android.gms.common.internal.zzc.zza("LoadImageRunnable must be executed on the main thread");
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.zzi.get(this.zza);
            if (imageReceiver != null) {
                ImageManager.this.zzi.remove(this.zza);
                imageReceiver.zzb(this.zza);
            }
            zzb zzb2 = this.zza.zza;
            if (zzb2.zza == null) {
                this.zza.zza(ImageManager.this.zzd, ImageManager.this.zzh, true);
                return;
            }
            Bitmap zza2 = ImageManager.this.zza(zzb2);
            if (zza2 != null) {
                this.zza.zza(ImageManager.this.zzd, zza2, true);
                return;
            }
            Long l = (Long) ImageManager.this.zzk.get(zzb2.zza);
            if (l != null) {
                if (SystemClock.elapsedRealtime() - l.longValue() < 3600000) {
                    this.zza.zza(ImageManager.this.zzd, ImageManager.this.zzh, true);
                    return;
                }
                ImageManager.this.zzk.remove(zzb2.zza);
            }
            this.zza.zza(ImageManager.this.zzd, ImageManager.this.zzh);
            ImageReceiver imageReceiver2 = (ImageReceiver) ImageManager.this.zzj.get(zzb2.zza);
            if (imageReceiver2 == null) {
                imageReceiver2 = new ImageReceiver(zzb2.zza);
                ImageManager.this.zzj.put(zzb2.zza, imageReceiver2);
            }
            imageReceiver2.zza(this.zza);
            if (!(this.zza instanceof zzd)) {
                ImageManager.this.zzi.put(this.zza, imageReceiver2);
            }
            synchronized (ImageManager.zza) {
                if (!ImageManager.zzb.contains(zzb2.zza)) {
                    ImageManager.zzb.add(zzb2.zza);
                    imageReceiver2.zza();
                }
            }
        }
    }

    final class zzb implements Runnable {
        private final Uri zza;
        private final ParcelFileDescriptor zzb;

        public zzb(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.zza = uri;
            this.zzb = parcelFileDescriptor;
        }

        public final void run() {
            boolean z;
            Bitmap bitmap;
            if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
                boolean z2 = false;
                Bitmap bitmap2 = null;
                ParcelFileDescriptor parcelFileDescriptor = this.zzb;
                if (parcelFileDescriptor != null) {
                    try {
                        bitmap2 = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
                    } catch (OutOfMemoryError e) {
                        String valueOf = String.valueOf(this.zza);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 34);
                        sb.append("OOM while loading bitmap for uri: ");
                        sb.append(valueOf);
                        Log.e("ImageManager", sb.toString(), e);
                        z2 = true;
                    }
                    try {
                        this.zzb.close();
                    } catch (IOException e2) {
                        Log.e("ImageManager", "closed failed", e2);
                    }
                    z = z2;
                    bitmap = bitmap2;
                } else {
                    bitmap = null;
                    z = false;
                }
                CountDownLatch countDownLatch = new CountDownLatch(1);
                ImageManager.this.zze.post(new zzd(this.zza, bitmap, z, countDownLatch));
                try {
                    countDownLatch.await();
                } catch (InterruptedException e3) {
                    String valueOf2 = String.valueOf(this.zza);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 32);
                    sb2.append("Latch interrupted while posting ");
                    sb2.append(valueOf2);
                    Log.w("ImageManager", sb2.toString());
                }
            } else {
                String valueOf3 = String.valueOf(Thread.currentThread());
                String valueOf4 = String.valueOf(Looper.getMainLooper().getThread());
                StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 56 + String.valueOf(valueOf4).length());
                sb3.append("checkNotMainThread: current thread ");
                sb3.append(valueOf3);
                sb3.append(" IS the main thread ");
                sb3.append(valueOf4);
                sb3.append("!");
                Log.e("Asserts", sb3.toString());
                throw new IllegalStateException("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            }
        }
    }

    @KeepName
    final class ImageReceiver extends ResultReceiver {
        private final Uri zza;
        /* access modifiers changed from: private */
        public final ArrayList<zza> zzb = new ArrayList<>();

        ImageReceiver(Uri uri) {
            super(new Handler(Looper.getMainLooper()));
            this.zza = uri;
        }

        public final void zza(zza zza2) {
            com.google.android.gms.common.internal.zzc.zza("ImageReceiver.addImageRequest() must be called in the main thread");
            this.zzb.add(zza2);
        }

        public final void zzb(zza zza2) {
            com.google.android.gms.common.internal.zzc.zza("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.zzb.remove(zza2);
        }

        public final void zza() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", this.zza);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            ImageManager.this.zzd.sendBroadcast(intent);
        }

        public final void onReceiveResult(int i, Bundle bundle) {
            ImageManager.this.zzf.execute(new zzb(this.zza, (ParcelFileDescriptor) bundle.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }
    }

    final class zzd implements Runnable {
        private final Uri zza;
        private final Bitmap zzb;
        private final CountDownLatch zzc;
        private boolean zzd;

        public zzd(Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.zza = uri;
            this.zzb = bitmap;
            this.zzd = z;
            this.zzc = countDownLatch;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.android.gms.common.images.zza.zza(android.content.Context, android.graphics.Bitmap, boolean):void
         arg types: [android.content.Context, android.graphics.Bitmap, int]
         candidates:
          com.google.android.gms.common.images.zza.zza(android.content.Context, com.google.android.gms.internal.zzbku, boolean):void
          com.google.android.gms.common.images.zza.zza(android.content.Context, android.graphics.Bitmap, boolean):void */
        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.android.gms.common.images.zza.zza(android.content.Context, com.google.android.gms.internal.zzbku, boolean):void
         arg types: [android.content.Context, com.google.android.gms.internal.zzbku, int]
         candidates:
          com.google.android.gms.common.images.zza.zza(android.content.Context, android.graphics.Bitmap, boolean):void
          com.google.android.gms.common.images.zza.zza(android.content.Context, com.google.android.gms.internal.zzbku, boolean):void */
        public final void run() {
            com.google.android.gms.common.internal.zzc.zza("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z = this.zzb != null;
            if (ImageManager.this.zzg != null) {
                if (this.zzd) {
                    ImageManager.this.zzg.evictAll();
                    System.gc();
                    this.zzd = false;
                    ImageManager.this.zze.post(this);
                    return;
                } else if (z) {
                    ImageManager.this.zzg.put(new zzb(this.zza), this.zzb);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.zzj.remove(this.zza);
            if (imageReceiver != null) {
                ArrayList zza2 = imageReceiver.zzb;
                int size = zza2.size();
                for (int i = 0; i < size; i++) {
                    zza zza3 = (zza) zza2.get(i);
                    if (z) {
                        zza3.zza(ImageManager.this.zzd, this.zzb, false);
                    } else {
                        ImageManager.this.zzk.put(this.zza, Long.valueOf(SystemClock.elapsedRealtime()));
                        zza3.zza(ImageManager.this.zzd, ImageManager.this.zzh, false);
                    }
                    if (!(zza3 instanceof zzd)) {
                        ImageManager.this.zzi.remove(zza3);
                    }
                }
            }
            this.zzc.countDown();
            synchronized (ImageManager.zza) {
                ImageManager.zzb.remove(this.zza);
            }
        }
    }

    private ImageManager(Context context, boolean z) {
        this.zzd = context.getApplicationContext();
    }

    public final void loadImage(ImageView imageView, Uri uri) {
        zza(new zzc(imageView, uri));
    }

    public final void loadImage(ImageView imageView, int i) {
        zza(new zzc(imageView, i));
    }

    public final void loadImage(ImageView imageView, Uri uri, int i) {
        zzc zzc2 = new zzc(imageView, uri);
        zzc2.zzb = i;
        zza(zzc2);
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        zza(new zzd(onImageLoadedListener, uri));
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri, int i) {
        zzd zzd2 = new zzd(onImageLoadedListener, uri);
        zzd2.zzb = i;
        zza(zzd2);
    }

    @Hide
    private final void zza(zza zza2) {
        com.google.android.gms.common.internal.zzc.zza("ImageManager.loadImage() must be called in the main thread");
        new zzc(zza2).run();
    }

    /* access modifiers changed from: private */
    public final Bitmap zza(zzb zzb2) {
        zza zza2 = this.zzg;
        if (zza2 == null) {
            return null;
        }
        return (Bitmap) zza2.get(zzb2);
    }
}
