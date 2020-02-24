package com.google.android.tvlauncher.util;

import android.content.Intent;
import android.support.p001v4.app.ActivityOptionsCompat;
import android.view.View;
import com.google.android.exoplayer2.C0841C;

public final class LaunchUtil {
    public static void startActivityWithAnimation(Intent intent, View view) {
        intent.addFlags(C0841C.ENCODING_PCM_MU_LAW);
        view.getContext().startActivity(intent, ActivityOptionsCompat.makeClipRevealAnimation(view, 0, 0, (int) (((float) view.getMeasuredWidth()) * view.getScaleX()), (int) (((float) view.getMeasuredHeight()) * view.getScaleY())).toBundle());
    }
}
