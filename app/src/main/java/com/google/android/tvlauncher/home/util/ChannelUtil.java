package com.google.android.tvlauncher.home.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DimenRes;
import android.util.SparseArray;
import android.widget.ImageView;
import androidx.leanback.widget.HorizontalGridView;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.home.util.ChannelStateSettings;

public class ChannelUtil {
    public static SparseArray<ChannelStateSettings> getDefaultChannelStateSettings(Context context) {
        SparseArray<ChannelStateSettings> settingsMap = new SparseArray<>();
        Resources r = context.getResources();
        ChannelStateSettings defaultSelectedSetting = new ChannelStateSettings.Builder().setItemHeight(getSize(r, C1188R.dimen.program_default_height)).setItemMarginTop(getSize(r, C1188R.dimen.program_selected_margin_vertical)).setItemMarginBottom(getSize(r, C1188R.dimen.program_selected_margin_vertical)).setMarginTop(getSize(r, C1188R.dimen.channel_selected_margin_top)).setMarginBottom(getSize(r, C1188R.dimen.channel_selected_margin_bottom)).setChannelLogoAlignmentOriginMargin(getSize(r, C1188R.dimen.channel_logo_selected_alignment_origin_margin)).setChannelLogoWidth(getSize(r, C1188R.dimen.channel_logo_size)).setChannelLogoHeight(getSize(r, C1188R.dimen.channel_logo_size)).setChannelLogoMarginStart(getSize(r, C1188R.dimen.channel_logo_margin_start)).setChannelLogoMarginEnd(getSize(r, C1188R.dimen.channel_logo_margin_end)).setChannelItemsTitleMarginBottom(getSize(r, C1188R.dimen.channel_items_title_selected_margin_bottom)).setEmptyChannelMessageMarginTop(getSize(r, C1188R.dimen.empty_channel_message_selected_margin_top)).build();
        settingsMap.put(0, defaultSelectedSetting);
        settingsMap.put(16, defaultSelectedSetting);
        settingsMap.put(22, defaultSelectedSetting);
        ChannelStateSettings defaultAboveSelectedLastRowSetting = new ChannelStateSettings(defaultSelectedSetting);
        defaultAboveSelectedLastRowSetting.setChannelItemsTitleMarginTop(getSize(r, C1188R.dimen.channel_items_title_default_above_selected_last_row_margin_top));
        defaultAboveSelectedLastRowSetting.setChannelItemsTitleMarginBottom(getSize(r, C1188R.dimen.f124xc9f967f0));
        settingsMap.put(15, defaultAboveSelectedLastRowSetting);
        settingsMap.put(29, defaultAboveSelectedLastRowSetting);
        ChannelStateSettings defaultSettings = new ChannelStateSettings.Builder().setItemHeight(getSize(r, C1188R.dimen.program_default_height)).setItemMarginTop(getSize(r, C1188R.dimen.program_default_margin_top)).setItemMarginBottom(getSize(r, C1188R.dimen.program_default_margin_bottom)).setChannelLogoAlignmentOriginMargin(getSize(r, C1188R.dimen.channel_logo_alignment_origin_margin)).setChannelLogoWidth(getSize(r, C1188R.dimen.channel_logo_size)).setChannelLogoHeight(getSize(r, C1188R.dimen.channel_logo_size)).setChannelLogoMarginStart(getSize(r, C1188R.dimen.channel_logo_margin_start)).setChannelLogoMarginEnd(getSize(r, C1188R.dimen.channel_logo_margin_end)).setChannelItemsTitleMarginBottom(getSize(r, C1188R.dimen.channel_items_title_default_margin_bottom)).setEmptyChannelMessageMarginTop(getSize(r, C1188R.dimen.empty_channel_message_default_margin_top)).build();
        settingsMap.put(1, defaultSettings);
        settingsMap.put(17, defaultSettings);
        settingsMap.put(23, defaultSettings);
        ChannelStateSettings settings = new ChannelStateSettings(defaultSettings);
        settings.setItemMarginTop(getSize(r, C1188R.dimen.program_fast_scrolling_margin_vertical));
        settings.setItemMarginBottom(getSize(r, C1188R.dimen.program_fast_scrolling_margin_vertical));
        settings.setChannelLogoAlignmentOriginMargin(getSize(r, C1188R.dimen.channel_logo_fast_scrolling_alignment_origin_margin));
        settingsMap.put(6, settings);
        settingsMap.put(7, settings);
        ChannelStateSettings settings2 = new ChannelStateSettings(defaultSettings);
        settings2.setItemHeight(getSize(r, C1188R.dimen.program_default_top_height));
        settings2.setItemMarginTop(getSize(r, C1188R.dimen.program_default_top_margin_top));
        settings2.setItemMarginBottom(getSize(r, C1188R.dimen.program_default_top_margin_bottom));
        settingsMap.put(4, settings2);
        settingsMap.put(5, settings2);
        settingsMap.put(20, settings2);
        settingsMap.put(21, settings2);
        ChannelStateSettings settings3 = new ChannelStateSettings(defaultSettings);
        settings3.setItemHeight(getSize(r, C1188R.dimen.program_default_height));
        settings3.setChannelLogoAlignmentOriginMargin(getSize(r, C1188R.dimen.channel_logo_above_selected_alignment_origin_margin));
        settings3.setEmptyChannelMessageMarginTop(getSize(r, C1188R.dimen.empty_channel_message_above_selected_margin_top));
        settingsMap.put(2, settings3);
        settingsMap.put(18, settings3);
        ChannelStateSettings settings4 = new ChannelStateSettings(settings3);
        settings4.setChannelLogoAlignmentOriginMargin(getSize(r, C1188R.dimen.channel_logo_below_selected_alignment_origin_margin));
        settings4.setEmptyChannelMessageMarginTop(getSize(r, C1188R.dimen.empty_channel_message_below_selected_margin_top));
        settings4.setChannelItemsTitleMarginBottom(getSize(r, C1188R.dimen.channel_items_title_below_selected_margin_bottom));
        settingsMap.put(3, settings4);
        settingsMap.put(19, settings4);
        ChannelStateSettings settings5 = new ChannelStateSettings.Builder().setItemHeight(getSize(r, C1188R.dimen.program_zoomed_out_height)).setItemMarginTop(getSize(r, C1188R.dimen.program_zoomed_out_margin_vertical)).setItemMarginBottom(getSize(r, C1188R.dimen.program_zoomed_out_margin_vertical)).setMarginBottom(getSize(r, C1188R.dimen.channel_zoomed_out_margin_bottom)).setChannelLogoWidth(getSize(r, C1188R.dimen.channel_logo_selected_size)).setChannelLogoHeight(getSize(r, C1188R.dimen.channel_logo_selected_size)).setChannelLogoMarginStart(getSize(r, C1188R.dimen.channel_logo_zoomed_out_selected_margin)).setChannelLogoMarginEnd(getSize(r, C1188R.dimen.channel_logo_zoomed_out_selected_margin)).setChannelItemsTitleMarginBottom(getSize(r, C1188R.dimen.channel_items_title_default_margin_bottom)).setEmptyChannelMessageMarginTop(getSize(r, C1188R.dimen.empty_channel_message_zoomed_out_margin_top)).build();
        settingsMap.put(8, settings5);
        settingsMap.put(24, settings5);
        ChannelStateSettings settings6 = new ChannelStateSettings(settings5);
        settings6.setChannelLogoWidth(getSize(r, C1188R.dimen.channel_action_button_size));
        settings6.setChannelLogoHeight(getSize(r, C1188R.dimen.channel_action_button_size));
        settings6.setChannelLogoMarginStart(getSize(r, C1188R.dimen.channel_logo_zoomed_out_margin));
        settings6.setChannelLogoMarginEnd(getSize(r, C1188R.dimen.channel_logo_zoomed_out_margin));
        settingsMap.put(9, settings6);
        settingsMap.put(10, settings6);
        settingsMap.put(11, settings6);
        settingsMap.put(12, settings6);
        settingsMap.put(13, settings6);
        settingsMap.put(14, settings6);
        settingsMap.put(25, settings6);
        settingsMap.put(26, settings6);
        settingsMap.put(27, settings6);
        settingsMap.put(28, settings6);
        return settingsMap;
    }

    public static SparseArray<ChannelStateSettings> getSponsoredChannelStateSettings(Context context) {
        SparseArray<ChannelStateSettings> settingsMap = new SparseArray<>();
        Resources r = context.getResources();
        ChannelStateSettings defaultSelectedSettings = new ChannelStateSettings.Builder().setItemHeight(getSize(r, C1188R.dimen.sponsored_program_selected_height)).setItemMarginTop(getSize(r, C1188R.dimen.sponsored_program_selected_margin_vertical)).setItemMarginBottom(getSize(r, C1188R.dimen.sponsored_program_selected_margin_vertical)).setMarginTop(getSize(r, C1188R.dimen.channel_selected_margin_top)).setMarginBottom(getSize(r, C1188R.dimen.sponsored_channel_margin_bottom)).setChannelLogoKeylineOffset(getSize(r, C1188R.dimen.sponsored_channel_logo_selected_keyline_offset)).setChannelLogoWidth(getSize(r, C1188R.dimen.sponsored_channel_logo_default_selected_width)).setChannelLogoHeight(getSize(r, C1188R.dimen.sponsored_channel_logo_default_selected_height)).setChannelLogoMarginStart(getSize(r, C1188R.dimen.sponsored_channel_logo_selected_margin_start)).setChannelLogoAlignmentOriginMargin(getSize(r, C1188R.dimen.sponsored_channel_logo_selected_margin_top)).setChannelLogoMarginEnd(getSize(r, C1188R.dimen.sponsored_channel_logo_selected_margin_end)).setChannelLogoTitleMarginBottom(getSize(r, C1188R.dimen.sponsored_channel_logo_title_default_selected_margin_bottom)).setChannelItemsTitleMarginBottom(getSize(r, C1188R.dimen.channel_items_title_selected_margin_bottom)).setEmptyChannelMessageMarginTop(getSize(r, C1188R.dimen.sponsored_channel_empty_message_margin_top)).build();
        settingsMap.put(0, defaultSelectedSettings);
        settingsMap.put(16, defaultSelectedSettings);
        settingsMap.put(6, defaultSelectedSettings);
        settingsMap.put(7, defaultSelectedSettings);
        settingsMap.put(22, defaultSelectedSettings);
        ChannelStateSettings defaultAboveSelectedLastRowSettings = new ChannelStateSettings(defaultSelectedSettings);
        defaultAboveSelectedLastRowSettings.setChannelItemsTitleMarginTop(getSize(r, C1188R.dimen.f126x69822176));
        defaultAboveSelectedLastRowSettings.setChannelItemsTitleMarginBottom(getSize(r, C1188R.dimen.f125xffc03a6a));
        settingsMap.put(15, defaultAboveSelectedLastRowSettings);
        settingsMap.put(29, defaultAboveSelectedLastRowSettings);
        ChannelStateSettings defaultSettings = new ChannelStateSettings.Builder().setItemHeight(getSize(r, C1188R.dimen.program_default_height)).setItemMarginTop(getSize(r, C1188R.dimen.program_default_margin_top)).setItemMarginBottom(getSize(r, C1188R.dimen.program_default_margin_bottom)).setChannelLogoKeylineOffset(getSize(r, C1188R.dimen.sponsored_channel_logo_keyline_offset)).setChannelLogoWidth(getSize(r, C1188R.dimen.sponsored_channel_logo_default_width)).setChannelLogoHeight(getSize(r, C1188R.dimen.sponsored_channel_logo_default_height)).setChannelLogoMarginStart(getSize(r, C1188R.dimen.channel_logo_margin_start)).setChannelLogoAlignmentOriginMargin(getSize(r, C1188R.dimen.sponsored_channel_logo_default_margin_top)).setChannelLogoMarginEnd(getSize(r, C1188R.dimen.channel_logo_margin_end)).setChannelLogoTitleMarginBottom(getSize(r, C1188R.dimen.sponsored_channel_logo_title_default_margin_bottom)).setChannelItemsTitleMarginBottom(getSize(r, C1188R.dimen.channel_items_title_default_margin_bottom)).setEmptyChannelMessageMarginTop(getSize(r, C1188R.dimen.empty_channel_message_default_margin_top)).build();
        settingsMap.put(1, defaultSettings);
        settingsMap.put(17, defaultSettings);
        settingsMap.put(23, defaultSettings);
        ChannelStateSettings settings = new ChannelStateSettings(defaultSettings);
        settings.setItemHeight(getSize(r, C1188R.dimen.program_default_top_height));
        settings.setItemMarginTop(getSize(r, C1188R.dimen.program_default_top_margin_top));
        settings.setItemMarginBottom(getSize(r, C1188R.dimen.program_default_top_margin_bottom));
        settingsMap.put(4, settings);
        settingsMap.put(5, settings);
        settingsMap.put(20, settings);
        settingsMap.put(21, settings);
        ChannelStateSettings settings2 = new ChannelStateSettings(defaultSettings);
        settings2.setItemHeight(getSize(r, C1188R.dimen.program_default_height));
        settings2.setEmptyChannelMessageMarginTop(getSize(r, C1188R.dimen.empty_channel_message_above_selected_margin_top));
        settingsMap.put(2, settings2);
        settingsMap.put(18, settings2);
        ChannelStateSettings settings3 = new ChannelStateSettings(settings2);
        settings3.setChannelItemsTitleMarginBottom(getSize(r, C1188R.dimen.channel_items_title_below_selected_margin_bottom));
        settings3.setEmptyChannelMessageMarginTop(getSize(r, C1188R.dimen.sponsored_channel_empty_message_below_selected_margin_top));
        settingsMap.put(3, settings3);
        settingsMap.put(19, settings3);
        ChannelStateSettings settings4 = new ChannelStateSettings.Builder().setItemHeight(getSize(r, C1188R.dimen.program_zoomed_out_height)).setItemMarginTop(getSize(r, C1188R.dimen.program_zoomed_out_margin_vertical)).setItemMarginBottom(getSize(r, C1188R.dimen.program_zoomed_out_margin_vertical)).setMarginBottom(getSize(r, C1188R.dimen.channel_zoomed_out_margin_bottom)).setChannelLogoWidth(getSize(r, C1188R.dimen.sponsored_channel_logo_zoomed_out_selected_width)).setChannelLogoHeight(getSize(r, C1188R.dimen.sponsored_channel_logo_zoomed_out_selected_height)).setChannelLogoKeylineOffset(getSize(r, C1188R.dimen.sponsored_channel_logo_zoomed_out_keyline_offset)).setChannelLogoMarginStart(getSize(r, C1188R.dimen.sponsored_channel_logo_zoomed_out_selected_margin_start)).setChannelLogoAlignmentOriginMargin(getSize(r, C1188R.dimen.sponsored_channel_logo_zoomed_out_selected_margin_top)).setChannelLogoMarginEnd(getSize(r, C1188R.dimen.sponsored_channel_logo_zoomed_out_selected_margin_end)).setChannelLogoTitleMarginBottom(getSize(r, C1188R.dimen.sponsored_channel_logo_title_zoomed_out_margin_bottom)).setChannelItemsTitleMarginBottom(getSize(r, C1188R.dimen.channel_items_title_default_margin_bottom)).setEmptyChannelMessageMarginTop(getSize(r, C1188R.dimen.empty_channel_message_zoomed_out_margin_top)).build();
        settingsMap.put(8, settings4);
        settingsMap.put(24, settings4);
        ChannelStateSettings settings5 = new ChannelStateSettings(settings4);
        settings5.setChannelLogoMarginStart(getSize(r, C1188R.dimen.channel_logo_zoomed_out_margin));
        settings5.setChannelLogoAlignmentOriginMargin(getSize(r, C1188R.dimen.sponsored_channel_logo_zoomed_out_margin_top));
        settings5.setChannelLogoWidth(getSize(r, C1188R.dimen.sponsored_channel_logo_zoomed_out_width));
        settings5.setChannelLogoHeight(getSize(r, C1188R.dimen.sponsored_channel_logo_zoomed_out_height));
        settings5.setChannelLogoMarginStart(getSize(r, C1188R.dimen.channel_logo_zoomed_out_margin));
        settings5.setChannelLogoMarginEnd(getSize(r, C1188R.dimen.channel_logo_zoomed_out_margin));
        settingsMap.put(9, settings5);
        settingsMap.put(10, settings5);
        settingsMap.put(11, settings5);
        settingsMap.put(12, settings5);
        settingsMap.put(13, settings5);
        settingsMap.put(14, settings5);
        settingsMap.put(25, settings5);
        settingsMap.put(26, settings5);
        settingsMap.put(27, settings5);
        settingsMap.put(28, settings5);
        return settingsMap;
    }

    public static SparseArray<ChannelStateSettings> getAppsRowStateSettings(Context context) {
        SparseArray<ChannelStateSettings> settingsMap = new SparseArray<>();
        Resources r = context.getResources();
        ChannelStateSettings defaultSettings = new ChannelStateSettings.Builder().setItemHeight(getSize(r, C1188R.dimen.home_app_banner_height)).setItemMarginTop(getSize(r, C1188R.dimen.home_app_banner_default_margin_top)).setItemMarginBottom(getSize(r, C1188R.dimen.home_app_banner_default_margin_bottom)).setChannelLogoAlignmentOriginMargin(getSize(r, C1188R.dimen.home_app_channel_logo_default_alignment_origin_margin)).setChannelLogoWidth(getSize(r, C1188R.dimen.channel_logo_size)).setChannelLogoHeight(getSize(r, C1188R.dimen.channel_logo_size)).setChannelLogoMarginStart(getSize(r, C1188R.dimen.channel_logo_margin_start)).setChannelLogoMarginEnd(getSize(r, C1188R.dimen.channel_logo_margin_end)).build();
        settingsMap.put(0, defaultSettings);
        settingsMap.put(1, defaultSettings);
        ChannelStateSettings settings = new ChannelStateSettings(defaultSettings);
        settings.setChannelLogoAlignmentOriginMargin(getSize(r, C1188R.dimen.home_app_channel_logo_default_alignment_origin_margin));
        settingsMap.put(15, settings);
        ChannelStateSettings settings2 = new ChannelStateSettings(settings);
        settings2.setItemHeight(getSize(r, C1188R.dimen.home_app_banner_image_height));
        settings2.setChannelLogoAlignmentOriginMargin(getSize(r, C1188R.dimen.channel_logo_above_selected_alignment_origin_margin));
        settings2.setItemMarginBottom(getSize(r, C1188R.dimen.home_app_banner_default_above_selected_margin_bottom));
        settingsMap.put(2, settings2);
        ChannelStateSettings settings3 = new ChannelStateSettings.Builder().setItemHeight(getSize(r, C1188R.dimen.home_app_banner_image_height)).setItemMarginTop(getSize(r, C1188R.dimen.home_app_banner_zoomed_out_margin_top)).setItemMarginBottom(getSize(r, C1188R.dimen.home_app_banner_zoomed_out_margin_bottom)).setMarginTop(getSize(r, C1188R.dimen.home_apps_row_zoomed_out_margin_top)).setMarginBottom(getSize(r, C1188R.dimen.channel_zoomed_out_margin_bottom)).setChannelLogoWidth(getSize(r, C1188R.dimen.channel_logo_selected_size)).setChannelLogoHeight(getSize(r, C1188R.dimen.channel_logo_selected_size)).setChannelLogoMarginStart(getSize(r, C1188R.dimen.channel_logo_zoomed_out_selected_margin)).setChannelLogoMarginEnd(getSize(r, C1188R.dimen.channel_logo_zoomed_out_selected_margin)).build();
        settingsMap.put(8, settings3);
        ChannelStateSettings settings4 = new ChannelStateSettings(settings3);
        settings4.setChannelLogoWidth(getSize(r, C1188R.dimen.channel_action_button_size));
        settings4.setChannelLogoHeight(getSize(r, C1188R.dimen.channel_action_button_size));
        settings4.setChannelLogoMarginStart(getSize(r, C1188R.dimen.channel_logo_zoomed_out_margin));
        settings4.setChannelLogoMarginEnd(getSize(r, C1188R.dimen.channel_logo_zoomed_out_margin));
        settingsMap.put(9, settings4);
        settingsMap.put(10, settings4);
        settingsMap.put(11, settings4);
        settingsMap.put(12, settings4);
        settingsMap.put(13, settings4);
        settingsMap.put(14, settings4);
        return settingsMap;
    }

    private static int getSize(Resources resources, @DimenRes int dimension) {
        return resources.getDimensionPixelSize(dimension);
    }

    public static void configureItemsListAlignment(HorizontalGridView itemsList) {
        itemsList.setWindowAlignment(1);
        itemsList.setWindowAlignmentOffsetPercent(0.0f);
        itemsList.setWindowAlignmentOffset(itemsList.getContext().getResources().getDimensionPixelSize(C1188R.dimen.channel_items_list_padding_start));
        itemsList.setItemAlignmentOffsetPercent(0.0f);
    }

    public static void configureAppRowItemsListAlignment(HorizontalGridView itemsList) {
        itemsList.setWindowAlignment(3);
        itemsList.setWindowAlignmentOffsetPercent(0.0f);
    }

    public static void setWatchNextLogo(ImageView logoView) {
        Context context = logoView.getContext();
        logoView.setContentDescription(context.getString(C1188R.string.play_next_channel_title));
        logoView.setImageDrawable(context.getDrawable(C1188R.C1189drawable.watch_next_logo));
    }

    public static boolean isEmptyState(int state) {
        switch (state) {
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
                return true;
            default:
                return false;
        }
    }
}
