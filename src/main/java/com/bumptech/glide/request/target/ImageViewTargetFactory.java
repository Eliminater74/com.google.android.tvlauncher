package com.bumptech.glide.request.target;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

public class ImageViewTargetFactory {
    @NonNull
    public <Z> ViewTarget<ImageView, Z> buildTarget(@NonNull ImageView view, @NonNull Class<Z> clazz) {
        if (Bitmap.class.equals(clazz)) {
            return new BitmapImageViewTarget(view);
        }
        if (Drawable.class.isAssignableFrom(clazz)) {
            return new DrawableImageViewTarget(view);
        }
        String valueOf = String.valueOf(clazz);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 64);
        sb.append("Unhandled class: ");
        sb.append(valueOf);
        sb.append(", try .as*(Class).transcode(ResourceTranscoder)");
        throw new IllegalArgumentException(sb.toString());
    }
}
