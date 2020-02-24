package com.google.android.tvlauncher.home;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.appsview.LaunchItem;
import com.google.android.tvlauncher.appsview.LaunchItemImageLoader;
import com.google.android.tvlauncher.appsview.data.LaunchItemImageDataSource;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManager;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManagerProvider;
import com.google.android.tvlauncher.appsview.data.PackageImageDataSource;
import com.google.android.tvlauncher.model.Program;
import com.google.android.tvlauncher.util.Util;
import java.util.Locale;

public class WatchNextItemMetadataController {
    private static final double ASPECT_RATIO_16_9 = 1.7777777777777777d;
    /* access modifiers changed from: private */
    public final Context mContext;
    private final TextView mFirstRow;
    private final LaunchItemsManager mLaunchItemsManager = LaunchItemsManagerProvider.getInstance(this.mContext);
    private final ImageView mLogo;
    private final Drawable mPlaceholderBanner;
    private final TextView mSecondRow;
    private final View mSecondRowContainer;
    private final TextView mThirdRow;
    private final String mTypeContinueGameText;
    private final String mTypeContinueMusicText;
    private final String mTypeContinueVideoText;
    private final String mTypeNewChapterOnlyText;
    private final String mTypeNewEpisodeOnlyText;
    private final String mTypeNewNoSeasonSeriesItemText;
    private final String mTypeNewSeasonChapterText;
    private final String mTypeNewSeasonEpisodeText;
    private final String mTypeNewSeasonOnlyText;
    private final String mTypeNextChapterOnlyText;
    private final String mTypeNextEpisodeOnlyText;
    private final String mTypeNextNoSeasonSeriesItemText;
    private final String mTypeNextSeasonChapterText;
    private final String mTypeNextSeasonEpisodeText;
    private final String mTypeNextSeasonOnlyText;
    private final String mTypeWatchListText;

    WatchNextItemMetadataController(View view) {
        this.mContext = view.getContext();
        this.mFirstRow = (TextView) view.findViewById(C1188R.C1191id.first);
        this.mSecondRow = (TextView) view.findViewById(C1188R.C1191id.second);
        this.mThirdRow = (TextView) view.findViewById(C1188R.C1191id.third);
        this.mLogo = (ImageView) view.findViewById(C1188R.C1191id.program_logo);
        ViewGroup.LayoutParams layoutParams = this.mLogo.getLayoutParams();
        layoutParams.height = this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.program_meta_watch_next_logo_height);
        double d = (double) layoutParams.height;
        Double.isNaN(d);
        layoutParams.width = (int) (d * 1.7777777777777777d);
        this.mLogo.setLayoutParams(layoutParams);
        this.mLogo.setOutlineProvider(new ViewOutlineProvider() {
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (float) WatchNextItemMetadataController.this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.watch_next_logo_rounded_corner_radius));
            }
        });
        this.mLogo.setClipToOutline(true);
        this.mFirstRow.setTextAppearance(C1188R.style.ChannelItemMetadata_First);
        this.mSecondRow.setTextAppearance(C1188R.style.ChannelItemMetadata_WatchNext_Second);
        this.mSecondRowContainer = view.findViewById(C1188R.C1191id.second_row_container);
        LinearLayout.LayoutParams logoLayoutParams = (LinearLayout.LayoutParams) this.mLogo.getLayoutParams();
        logoLayoutParams.setMarginEnd(this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.program_meta_watch_next_logo_component_spacing));
        this.mLogo.setLayoutParams(logoLayoutParams);
        LinearLayout.LayoutParams secondRowLayoutParams = (LinearLayout.LayoutParams) this.mSecondRowContainer.getLayoutParams();
        secondRowLayoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.program_meta_watch_next_second_row_margin_top);
        this.mSecondRowContainer.setLayoutParams(secondRowLayoutParams);
        this.mTypeContinueVideoText = this.mContext.getResources().getString(C1188R.string.watch_next_type_continue_video_text);
        this.mTypeContinueMusicText = this.mContext.getResources().getString(C1188R.string.watch_next_type_continue_music_text);
        this.mTypeContinueGameText = this.mContext.getResources().getString(C1188R.string.watch_next_type_continue_game_text);
        this.mTypeNextNoSeasonSeriesItemText = this.mContext.getResources().getString(C1188R.string.watch_next_type_next_no_season_episode_text);
        this.mTypeNextSeasonEpisodeText = this.mContext.getResources().getString(C1188R.string.watch_next_type_next_season_episode_text);
        this.mTypeNextSeasonChapterText = this.mContext.getResources().getString(C1188R.string.watch_next_type_next_season_chapter_text);
        this.mTypeNextSeasonOnlyText = this.mContext.getResources().getString(C1188R.string.watch_next_type_next_season_only_text);
        this.mTypeNextEpisodeOnlyText = this.mContext.getResources().getString(C1188R.string.watch_next_type_next_episode_only_text);
        this.mTypeNextChapterOnlyText = this.mContext.getResources().getString(C1188R.string.watch_next_type_next_chapter_only_text);
        this.mTypeNewNoSeasonSeriesItemText = this.mContext.getResources().getString(C1188R.string.watch_next_type_new_no_season_episode_text);
        this.mTypeNewSeasonEpisodeText = this.mContext.getResources().getString(C1188R.string.watch_next_type_new_season_episode_text);
        this.mTypeNewSeasonChapterText = this.mContext.getResources().getString(C1188R.string.watch_next_type_new_season_chapter_text);
        this.mTypeNewSeasonOnlyText = this.mContext.getResources().getString(C1188R.string.watch_next_type_new_season_only_text);
        this.mTypeNewEpisodeOnlyText = this.mContext.getResources().getString(C1188R.string.watch_next_type_new_episode_only_text);
        this.mTypeNewChapterOnlyText = this.mContext.getResources().getString(C1188R.string.watch_next_type_new_chapter_only_text);
        this.mTypeWatchListText = this.mContext.getResources().getString(C1188R.string.play_next_type_watch_list_text);
        this.mPlaceholderBanner = new ColorDrawable(ContextCompat.getColor(this.mContext, C1188R.color.app_banner_background_color));
        this.mThirdRow.setAutoSizeTextTypeUniformWithConfiguration(this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.text_size_h5), this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.text_size_b1), this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.last_program_meta_third_row_text_auto_size_step_granularity), 0);
    }

    /* access modifiers changed from: package-private */
    public void bindView(@NonNull Program program) {
        String typeNewSeriesItemOnlyText;
        String typeNewSeasonSeriesItemText;
        String typeNextSeriesItemOnlyText;
        String typeNextSeasonSeriesItemText;
        String first = program.getTitle();
        String second = null;
        String seasonDisplayNumber = program.getSeasonDisplayNumber();
        String seriesItemDisplayNumber = program.getEpisodeDisplayNumber();
        boolean isSeasonEmpty = TextUtils.isEmpty(seasonDisplayNumber);
        boolean isSeriesItemEmpty = TextUtils.isEmpty(seriesItemDisplayNumber);
        if (program.getTvSeriesItemType() == 1) {
            typeNextSeasonSeriesItemText = this.mTypeNextSeasonChapterText;
            typeNextSeriesItemOnlyText = this.mTypeNextChapterOnlyText;
            typeNewSeasonSeriesItemText = this.mTypeNewSeasonChapterText;
            typeNewSeriesItemOnlyText = this.mTypeNewChapterOnlyText;
        } else {
            typeNextSeasonSeriesItemText = this.mTypeNextSeasonEpisodeText;
            typeNextSeriesItemOnlyText = this.mTypeNextEpisodeOnlyText;
            typeNewSeasonSeriesItemText = this.mTypeNewSeasonEpisodeText;
            typeNewSeriesItemOnlyText = this.mTypeNewEpisodeOnlyText;
        }
        int watchNextType = program.getWatchNextType();
        if (watchNextType == 0) {
            switch (program.getType()) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    second = this.mTypeContinueVideoText;
                    break;
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    second = this.mTypeContinueMusicText;
                    break;
                case 12:
                    second = this.mTypeContinueGameText;
                    break;
            }
        } else if (watchNextType != 1) {
            if (watchNextType != 2) {
                if (watchNextType == 3) {
                    second = this.mTypeWatchListText;
                }
            } else if (!isSeasonEmpty && !isSeriesItemEmpty) {
                second = String.format(Locale.getDefault(), typeNewSeasonSeriesItemText, seasonDisplayNumber, seriesItemDisplayNumber);
            } else if (!isSeasonEmpty) {
                second = String.format(Locale.getDefault(), this.mTypeNewSeasonOnlyText, seasonDisplayNumber);
            } else if (!isSeriesItemEmpty) {
                second = String.format(Locale.getDefault(), typeNewSeriesItemOnlyText, seriesItemDisplayNumber);
            } else {
                second = this.mTypeNewNoSeasonSeriesItemText;
            }
        } else if (!isSeasonEmpty && !isSeriesItemEmpty) {
            second = String.format(Locale.getDefault(), typeNextSeasonSeriesItemText, seasonDisplayNumber, seriesItemDisplayNumber);
        } else if (!isSeasonEmpty) {
            second = String.format(Locale.getDefault(), this.mTypeNextSeasonOnlyText, seasonDisplayNumber);
        } else if (!isSeriesItemEmpty) {
            second = String.format(Locale.getDefault(), typeNextSeriesItemOnlyText, seriesItemDisplayNumber);
        } else {
            second = this.mTypeNextNoSeasonSeriesItemText;
        }
        LaunchItem launchItem = this.mLaunchItemsManager.getLaunchItem(program.getPackageName());
        if (launchItem != null) {
            new LaunchItemImageLoader(this.mContext).setLaunchItemImageDataSource(new LaunchItemImageDataSource(launchItem, PackageImageDataSource.ImageType.BANNER, this.mLaunchItemsManager.getCurrentLocale())).setTargetImageView(this.mLogo).setPlaceholder(this.mPlaceholderBanner).loadLaunchItemImage();
        } else {
            this.mLogo.setImageDrawable(null);
        }
        this.mFirstRow.setText(Util.safeTrim(first));
        this.mSecondRow.setText(Util.safeTrim(second));
        this.mThirdRow.setText((CharSequence) null);
        updateVisibility();
    }

    public void clear() {
        this.mFirstRow.setText((CharSequence) null);
        this.mSecondRow.setText((CharSequence) null);
        this.mThirdRow.setText((CharSequence) null);
        this.mLogo.setImageDrawable(null);
        updateVisibility();
    }

    private void updateVisibility() {
        TextView textView = this.mFirstRow;
        boolean z = true;
        setVisibility(textView, textView.length() != 0);
        TextView textView2 = this.mSecondRow;
        setVisibility(textView2, textView2.length() != 0);
        TextView textView3 = this.mThirdRow;
        setVisibility(textView3, textView3.length() != 0);
        ImageView imageView = this.mLogo;
        if (imageView.getDrawable() == null) {
            z = false;
        }
        setVisibility(imageView, z);
    }

    private void setVisibility(View view, boolean visible) {
        int i = 0;
        if ((view.getVisibility() == 0) != visible) {
            if (!visible) {
                i = 8;
            }
            view.setVisibility(i);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public TextView getFirstRow() {
        return this.mFirstRow;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public TextView getSecondRow() {
        return this.mSecondRow;
    }

    @VisibleForTesting
    public ImageView getLogo() {
        return this.mLogo;
    }
}
