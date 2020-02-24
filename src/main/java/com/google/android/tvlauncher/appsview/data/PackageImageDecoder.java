package com.google.android.tvlauncher.appsview.data;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.bumptech.glide.util.Util;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.appsview.data.PackageImageDataSource;
import java.io.IOException;

public class PackageImageDecoder implements ResourceDecoder<PackageImageDataSource, Drawable> {
    private final Context mContext;

    public PackageImageDecoder(Context context) {
        this.mContext = context;
    }

    public boolean handles(PackageImageDataSource source, Options options) throws IOException {
        return true;
    }

    @Nullable
    public Resource<Drawable> decode(PackageImageDataSource source, int width, int height, Options options) throws IOException {
        Drawable drawable;
        ResolveInfo info = source.getResolveInfo();
        int i = C12372.f134x982bd2bd[source.getImageType().ordinal()];
        if (i == 1) {
            drawable = getIcon(info, this.mContext.getPackageManager(), width, height);
        } else if (i != 2) {
            drawable = createIconBanner(info, this.mContext.getPackageManager(), width, height);
        } else {
            drawable = getBanner(info, this.mContext.getPackageManager(), width, height);
        }
        return new DrawableResource<Drawable>(this, drawable) {
            public Class<Drawable> getResourceClass() {
                return Drawable.class;
            }

            public int getSize() {
                if (this.drawable instanceof BitmapDrawable) {
                    return Util.getBitmapByteSize(((BitmapDrawable) this.drawable).getBitmap());
                }
                return 102400;
            }

            public void recycle() {
            }
        };
    }

    /* renamed from: com.google.android.tvlauncher.appsview.data.PackageImageDecoder$2 */
    static /* synthetic */ class C12372 {

        /* renamed from: $SwitchMap$com$google$android$tvlauncher$appsview$data$PackageImageDataSource$ImageType */
        static final /* synthetic */ int[] f134x982bd2bd = new int[PackageImageDataSource.ImageType.values().length];

        static {
            try {
                f134x982bd2bd[PackageImageDataSource.ImageType.ICON.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f134x982bd2bd[PackageImageDataSource.ImageType.BANNER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private Drawable getIcon(ResolveInfo info, PackageManager pm, int width, int height) {
        Drawable icon = null;
        if (info != null) {
            icon = info.loadIcon(pm);
        }
        if (icon == null) {
            icon = this.mContext.getDrawable(17301651);
        }
        if (icon instanceof BitmapDrawable) {
            return new BitmapDrawable(this.mContext.getResources(), getSizeCappedBitmap(((BitmapDrawable) icon).getBitmap(), width, height));
        }
        return icon;
    }

    private Drawable getBanner(ResolveInfo info, PackageManager pm, int width, int height) {
        Drawable banner = null;
        if (!(info == null || info.activityInfo == null)) {
            banner = info.activityInfo.loadBanner(pm);
        }
        if (banner == null) {
            return createIconBanner(info, pm, width, height);
        }
        if (banner instanceof BitmapDrawable) {
            return new BitmapDrawable(this.mContext.getResources(), getSizeCappedBitmap(((BitmapDrawable) banner).getBitmap(), width, height));
        }
        return banner;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.graphics.Bitmap.createBitmap(android.util.DisplayMetrics, int, int, android.graphics.Bitmap$Config, boolean):android.graphics.Bitmap}
     arg types: [?[OBJECT, ARRAY], int, int, android.graphics.Bitmap$Config, int]
     candidates:
      ClspMth{android.graphics.Bitmap.createBitmap(android.util.DisplayMetrics, int[], int, int, android.graphics.Bitmap$Config):android.graphics.Bitmap}
      ClspMth{android.graphics.Bitmap.createBitmap(android.graphics.Bitmap, int, int, int, int):android.graphics.Bitmap}
      ClspMth{android.graphics.Bitmap.createBitmap(int, int, android.graphics.Bitmap$Config, boolean, android.graphics.ColorSpace):android.graphics.Bitmap}
      ClspMth{android.graphics.Bitmap.createBitmap(android.util.DisplayMetrics, int, int, android.graphics.Bitmap$Config, boolean):android.graphics.Bitmap} */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.min(float, float):float}
     arg types: [int, float]
     candidates:
      ClspMth{java.lang.Math.min(double, double):double}
      ClspMth{java.lang.Math.min(long, long):long}
      ClspMth{java.lang.Math.min(int, int):int}
      ClspMth{java.lang.Math.min(float, float):float} */
    private Drawable createIconBanner(ResolveInfo info, PackageManager pm, int width, int height) {
        int i = width;
        int i2 = height;
        Bitmap iconBanner = null;
        try {
            iconBanner = Bitmap.createBitmap((DisplayMetrics) null, i, i2, Bitmap.Config.ARGB_8888, false);
            Canvas canvas = new Canvas(iconBanner);
            canvas.drawColor(this.mContext.getColor(C1188R.color.app_banner_background_color));
            float maxIconHeight = ((float) this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.app_banner_fallback_icon_height)) * this.mContext.getResources().getFraction(C1188R.fraction.home_app_banner_focused_scale, 1, 1);
            int iconSize = Math.round(((float) this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.app_banner_icon_size)) * (((float) i) / ((float) this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.app_banner_image_max_width))));
            try {
                Bitmap icon = getBitmapFromDrawable(getIcon(info, pm, iconSize, iconSize));
                float scale = Math.min(1.0f, maxIconHeight / ((float) icon.getHeight()));
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                matrix.postTranslate((((float) i) - (((float) icon.getWidth()) * scale)) / 2.0f, (((float) i2) - (((float) icon.getHeight()) * scale)) / 2.0f);
                canvas.drawBitmap(icon, matrix, null);
            } catch (IllegalAccessError e) {
            }
        } catch (IllegalAccessError e2) {
        }
        return new BitmapDrawable(this.mContext.getResources(), iconBanner);
    }

    @NonNull
    private Bitmap getBitmapFromDrawable(@NonNull Drawable drawable) {
        Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bmp;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.min(float, float):float}
     arg types: [int, float]
     candidates:
      ClspMth{java.lang.Math.min(double, double):double}
      ClspMth{java.lang.Math.min(long, long):long}
      ClspMth{java.lang.Math.min(int, int):int}
      ClspMth{java.lang.Math.min(float, float):float} */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.graphics.Bitmap.createBitmap(android.graphics.Bitmap, int, int, int, int, android.graphics.Matrix, boolean):android.graphics.Bitmap}
     arg types: [android.graphics.Bitmap, int, int, int, int, android.graphics.Matrix, int]
     candidates:
      ClspMth{android.graphics.Bitmap.createBitmap(android.util.DisplayMetrics, int[], int, int, int, int, android.graphics.Bitmap$Config):android.graphics.Bitmap}
      ClspMth{android.graphics.Bitmap.createBitmap(android.graphics.Bitmap, int, int, int, int, android.graphics.Matrix, boolean):android.graphics.Bitmap} */
    private static Bitmap getSizeCappedBitmap(Bitmap image, int maxWidth, int maxHeight) {
        if (image == null) {
            return null;
        }
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        if ((imgWidth <= maxWidth && imgHeight <= maxHeight) || imgWidth <= 0 || imgHeight <= 0) {
            return image;
        }
        float scale = Math.min(1.0f, ((float) maxHeight) / ((float) imgHeight));
        if (((double) scale) >= 1.0d && imgWidth <= maxWidth) {
            return image;
        }
        float deltaW = ((float) Math.max(Math.round(((float) imgWidth) * scale) - maxWidth, 0)) / scale;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap newImage = Bitmap.createBitmap(image, (int) (deltaW / 2.0f), 0, (int) (((float) imgWidth) - deltaW), imgHeight, matrix, true);
        if (newImage != null) {
            return newImage;
        }
        return image;
    }
}
