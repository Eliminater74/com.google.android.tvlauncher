package com.bumptech.glide.request.transition;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;

public interface Transition<R> {

    boolean transition(R r, ViewAdapter viewAdapter);

    public interface ViewAdapter {
        @Nullable
        Drawable getCurrentDrawable();

        View getView();

        void setDrawable(Drawable drawable);
    }
}
