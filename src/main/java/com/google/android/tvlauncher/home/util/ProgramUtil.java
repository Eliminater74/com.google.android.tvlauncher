package com.google.android.tvlauncher.home.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.tvlauncher.C1188R;

public class ProgramUtil {
    public static final double ASPECT_RATIO_16_9 = 1.7777777777777777d;
    private static final double ASPECT_RATIO_1_1 = 1.0d;
    public static final double ASPECT_RATIO_2_3 = 0.6666666666666666d;
    private static final double ASPECT_RATIO_3_2 = 1.5d;
    public static final double ASPECT_RATIO_4_1 = 4.0d;
    private static final double ASPECT_RATIO_4_3 = 1.3333333333333333d;
    public static final double ASPECT_RATIO_9_16 = 0.5625d;
    private static final double ASPECT_RATIO_MOVIE_POSTER = 0.6939625260235947d;

    public static ProgramSettings getProgramSettings(Context context) {
        Resources resources = context.getResources();
        ProgramSettings settings = new ProgramSettings();
        settings.defaultHeight = resources.getDimensionPixelSize(C1188R.dimen.program_default_height);
        settings.defaultMarginTop = resources.getDimensionPixelSize(C1188R.dimen.program_default_margin_top);
        settings.defaultMarginBottom = resources.getDimensionPixelSize(C1188R.dimen.program_default_margin_bottom);
        settings.defaultMarginHorizontal = resources.getDimensionPixelSize(C1188R.dimen.program_default_margin_horizontal);
        settings.defaultTopHeight = resources.getDimensionPixelSize(C1188R.dimen.program_default_top_height);
        settings.defaultTopMarginTop = resources.getDimensionPixelSize(C1188R.dimen.program_default_top_margin_top);
        settings.defaultTopMarginBottom = resources.getDimensionPixelSize(C1188R.dimen.program_default_top_margin_bottom);
        settings.selectedHeight = resources.getDimensionPixelSize(C1188R.dimen.program_default_height);
        settings.selectedMarginVertical = resources.getDimensionPixelSize(C1188R.dimen.program_selected_margin_vertical);
        settings.zoomedOutHeight = resources.getDimensionPixelSize(C1188R.dimen.program_zoomed_out_height);
        settings.zoomedOutMarginVertical = resources.getDimensionPixelSize(C1188R.dimen.program_zoomed_out_margin_vertical);
        settings.zoomedOutMarginHorizontal = resources.getDimensionPixelSize(C1188R.dimen.program_zoomed_out_margin_horizontal);
        settings.focusedAnimationDuration = resources.getInteger(C1188R.integer.program_focused_animation_duration_ms);
        settings.focusedScale = resources.getFraction(C1188R.fraction.program_focused_scale, 1, 1);
        settings.focusedElevation = resources.getDimension(C1188R.dimen.program_focused_elevation);
        return settings;
    }

    public static ProgramSettings getSponsoredProgramSettings(Context context) {
        Resources resources = context.getResources();
        ProgramSettings settings = new ProgramSettings();
        settings.defaultHeight = resources.getDimensionPixelSize(C1188R.dimen.program_default_height);
        settings.defaultMarginTop = resources.getDimensionPixelSize(C1188R.dimen.program_default_margin_top);
        settings.defaultMarginBottom = resources.getDimensionPixelSize(C1188R.dimen.program_default_margin_bottom);
        settings.defaultMarginHorizontal = resources.getDimensionPixelSize(C1188R.dimen.program_default_margin_horizontal);
        settings.defaultTopHeight = resources.getDimensionPixelSize(C1188R.dimen.program_default_top_height);
        settings.defaultTopMarginTop = resources.getDimensionPixelSize(C1188R.dimen.program_default_top_margin_top);
        settings.defaultTopMarginBottom = resources.getDimensionPixelSize(C1188R.dimen.program_default_top_margin_bottom);
        settings.selectedHeight = resources.getDimensionPixelSize(C1188R.dimen.sponsored_program_selected_height);
        settings.selectedMarginVertical = resources.getDimensionPixelSize(C1188R.dimen.sponsored_program_selected_margin_vertical);
        settings.zoomedOutHeight = resources.getDimensionPixelSize(C1188R.dimen.program_zoomed_out_height);
        settings.zoomedOutMarginVertical = resources.getDimensionPixelSize(C1188R.dimen.program_zoomed_out_margin_vertical);
        settings.zoomedOutMarginHorizontal = resources.getDimensionPixelSize(C1188R.dimen.program_zoomed_out_margin_horizontal);
        settings.focusedAnimationDuration = resources.getInteger(C1188R.integer.program_focused_animation_duration_ms);
        settings.focusedScale = resources.getFraction(C1188R.fraction.sponsored_program_focused_scale, 1, 1);
        settings.focusedElevation = resources.getDimension(C1188R.dimen.program_focused_elevation);
        return settings;
    }

    public static double getAspectRatio(int aspectRatio) {
        if (aspectRatio == 0) {
            return 1.7777777777777777d;
        }
        if (aspectRatio == 1) {
            return ASPECT_RATIO_3_2;
        }
        if (aspectRatio == 2) {
            return ASPECT_RATIO_4_3;
        }
        if (aspectRatio == 3) {
            return 1.0d;
        }
        if (aspectRatio == 4) {
            return 0.6666666666666666d;
        }
        if (aspectRatio != 5) {
            return 1.7777777777777777d;
        }
        return ASPECT_RATIO_MOVIE_POSTER;
    }

    public static void updateSize(View programView, int programState, double aspectRatio, ProgramSettings programSettings) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) programView.getLayoutParams();
        switch (programState) {
            case 0:
                lp.height = programSettings.defaultHeight;
                lp.setMargins(0, programSettings.defaultMarginTop, 0, programSettings.defaultMarginBottom);
                lp.setMarginEnd(programSettings.defaultMarginHorizontal);
                break;
            case 1:
            case 2:
                lp.height = programSettings.defaultTopHeight;
                lp.setMargins(0, programSettings.defaultTopMarginTop, 0, programSettings.defaultTopMarginBottom);
                lp.setMarginEnd(programSettings.defaultMarginHorizontal);
                break;
            case 3:
            case 4:
            case 12:
                lp.height = programSettings.selectedHeight;
                lp.setMargins(0, programSettings.selectedMarginVertical, 0, programSettings.selectedMarginVertical);
                lp.setMarginEnd(programSettings.defaultMarginHorizontal);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                lp.height = programSettings.zoomedOutHeight;
                lp.setMargins(0, programSettings.zoomedOutMarginVertical, 0, programSettings.zoomedOutMarginVertical);
                lp.setMarginEnd(programSettings.zoomedOutMarginHorizontal);
                break;
        }
        double d = (double) lp.height;
        Double.isNaN(d);
        lp.width = (int) Math.round(d * aspectRatio);
        programView.setLayoutParams(lp);
    }
}
