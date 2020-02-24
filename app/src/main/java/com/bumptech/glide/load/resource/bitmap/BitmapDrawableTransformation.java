package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.Preconditions;
import java.security.MessageDigest;

@Deprecated
public class BitmapDrawableTransformation implements Transformation<BitmapDrawable> {
    private final Transformation<Drawable> wrapped;

    public BitmapDrawableTransformation(Transformation<Bitmap> wrapped2) {
        this.wrapped = (Transformation) Preconditions.checkNotNull(new DrawableTransformation(wrapped2, false));
    }

    @NonNull
    public Resource<BitmapDrawable> transform(@NonNull Context context, @NonNull Resource<BitmapDrawable> drawableResourceToTransform, int outWidth, int outHeight) {
        return convertToBitmapDrawableResource(this.wrapped.transform(context, convertToDrawableResource(drawableResourceToTransform), outWidth, outHeight));
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [com.bumptech.glide.load.engine.Resource, com.bumptech.glide.load.engine.Resource<android.graphics.drawable.BitmapDrawable>, com.bumptech.glide.load.engine.Resource<android.graphics.drawable.Drawable>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.bumptech.glide.load.engine.Resource<android.graphics.drawable.BitmapDrawable> convertToBitmapDrawableResource(com.bumptech.glide.load.engine.Resource<android.graphics.drawable.Drawable> r4) {
        /*
            java.lang.Object r0 = r4.get()
            boolean r0 = r0 instanceof android.graphics.drawable.BitmapDrawable
            if (r0 == 0) goto L_0x0009
            return r4
        L_0x0009:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.Object r1 = r4.get()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            java.lang.String r2 = java.lang.String.valueOf(r1)
            int r2 = r2.length()
            int r2 = r2 + 76
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r2)
            java.lang.String r2 = "Wrapped transformation unexpectedly returned a non BitmapDrawable resource: "
            r3.append(r2)
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.bitmap.BitmapDrawableTransformation.convertToBitmapDrawableResource(com.bumptech.glide.load.engine.Resource):com.bumptech.glide.load.engine.Resource");
    }

    private static Resource<Drawable> convertToDrawableResource(Resource<BitmapDrawable> toConvert) {
        return toConvert;
    }

    public boolean equals(Object o) {
        if (o instanceof BitmapDrawableTransformation) {
            return this.wrapped.equals(((BitmapDrawableTransformation) o).wrapped);
        }
        return false;
    }

    public int hashCode() {
        return this.wrapped.hashCode();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.wrapped.updateDiskCacheKey(messageDigest);
    }
}
