package com.google.android.tvlauncher.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.icu.text.MeasureFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.media.tv.TvContentRating;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.annotation.VisibleForTesting;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.data.CanonicalGenreUtil;
import com.google.android.tvlauncher.home.contentrating.ContentRatingsManager;
import com.google.android.tvlauncher.home.contentrating.ContentRatingsUtil;
import com.google.android.tvlauncher.model.Program;
import com.google.android.tvlauncher.util.Util;
import com.google.android.tvlauncher.widget.BarRatingView;
import com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ChannelItemMetadataController {
    @SuppressLint({"SimpleDateFormat"})
    private static final SimpleDateFormat DATETIME_PARSE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    @SuppressLint({"SimpleDateFormat"})
    private static final SimpleDateFormat DATE_PARSE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final boolean DEBUG = false;
    private static final int HOUR_IN_MINUTES = 60;
    private static final int MINUTE_IN_SECONDS = 60;
    private static final String TAG = "ItemMetadata";

    static {
        DATETIME_PARSE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private final int mAvailabilityDefaultColor;
    private final int mAvailabilityFreeColor;
    private final String mAvailabilityFreeText;
    private final String mAvailabilityFreeWithSubscriptionText;
    private final int mAvailabilityPurchasedColor;
    private final Drawable mAvailabilityPurchasedIcon;
    private final String mAvailabilityPurchasedText;
    private final CanonicalGenreUtil mCanonicalGenreUtil;
    private final String mChapterDisplayNumberFormat;
    private final TextView mContentRating;
    private final Context mContext;
    private final DateFormat mDateFormat;
    private final MeasureFormat mDurationFormat;
    private final String mEpisodeDisplayNumberFormat;
    private final TextView mFirstRow;
    private final String mInteractionReleaseDateAndDescriptionFormat;
    private final ImageView mLogo;
    private final String mMetadataItemSeparator;
    private final String mMetadataPrefix;
    private final String mMetadataSuffix;
    private final TextView mOldPrice;
    private final TextView mPrice;
    private final View mPriceContainer;
    private final TextView mRatingPercentage;
    private final String mSeasonDisplayNumberFormat;
    private final TextView mSecondRow;
    private final BarRatingView mStarRating;
    private final TextView mThirdRow;
    private final int mThirdRowDefaultMaxLines;
    private final TextView mThumbCountDown;
    private final TextView mThumbCountUp;
    private final ImageView mThumbDown;
    private final ImageView mThumbUp;
    private final Pattern mThumbsUpDownRatingPattern;
    private final String mTvSeriesItemTitleAndDescriptionFormat;
    private final String mTvSeriesItemTitleFormat;
    private boolean mIsStarRatingSet;
    private boolean mLegacy = false;
    private LogMetadata mLogMetadata;
    private String mLogoUri;

    ChannelItemMetadataController(View view) {
        this.mContext = view.getContext();
        this.mFirstRow = (TextView) view.findViewById(C1188R.C1191id.first);
        this.mSecondRow = (TextView) view.findViewById(C1188R.C1191id.second);
        this.mThirdRow = (TextView) view.findViewById(C1188R.C1191id.third);
        this.mThirdRowDefaultMaxLines = this.mThirdRow.getMaxLines();
        this.mThumbCountUp = (TextView) view.findViewById(C1188R.C1191id.thumbCountUp);
        this.mThumbCountDown = (TextView) view.findViewById(C1188R.C1191id.thumbCountDown);
        this.mThumbUp = (ImageView) view.findViewById(C1188R.C1191id.thumbUp);
        this.mThumbDown = (ImageView) view.findViewById(C1188R.C1191id.thumbDown);
        this.mRatingPercentage = (TextView) view.findViewById(C1188R.C1191id.rating_percentage);
        this.mStarRating = (BarRatingView) view.findViewById(C1188R.C1191id.star_rating);
        this.mPriceContainer = view.findViewById(C1188R.C1191id.price_container);
        this.mOldPrice = (TextView) view.findViewById(C1188R.C1191id.old_price);
        this.mPrice = (TextView) view.findViewById(C1188R.C1191id.price);
        this.mContentRating = (TextView) view.findViewById(C1188R.C1191id.content_rating);
        this.mLogo = (ImageView) view.findViewById(C1188R.C1191id.program_logo);
        TextView textView = this.mOldPrice;
        textView.setPaintFlags(textView.getPaintFlags() | 16);
        this.mMetadataItemSeparator = this.mContext.getString(C1188R.string.program_metadata_item_separator);
        this.mMetadataPrefix = this.mContext.getString(C1188R.string.program_metadata_prefix);
        this.mMetadataSuffix = this.mContext.getString(C1188R.string.program_metadata_suffix);
        this.mAvailabilityFreeWithSubscriptionText = this.mContext.getString(C1188R.string.program_availability_free_with_subscription);
        this.mAvailabilityPurchasedText = this.mContext.getString(C1188R.string.program_availability_purchased);
        this.mAvailabilityPurchasedIcon = view.getContext().getDrawable(C1188R.C1189drawable.ic_program_meta_purchased_black);
        this.mAvailabilityFreeText = this.mContext.getString(C1188R.string.program_availability_free);
        this.mAvailabilityDefaultColor = this.mContext.getColor(C1188R.color.program_meta_availability_default_color);
        this.mAvailabilityPurchasedColor = this.mContext.getColor(C1188R.color.program_meta_availability_purchased_color);
        this.mAvailabilityFreeColor = this.mContext.getColor(C1188R.color.program_meta_availability_free_color);
        this.mSeasonDisplayNumberFormat = this.mContext.getString(C1188R.string.program_season_display_number);
        this.mEpisodeDisplayNumberFormat = this.mContext.getString(C1188R.string.program_episode_display_number);
        this.mChapterDisplayNumberFormat = this.mContext.getString(C1188R.string.program_chapter_display_number);
        this.mTvSeriesItemTitleFormat = this.mContext.getString(C1188R.string.program_episode_title);
        this.mTvSeriesItemTitleAndDescriptionFormat = this.mContext.getString(C1188R.string.program_episode_title_and_short_description);
        this.mInteractionReleaseDateAndDescriptionFormat = this.mContext.getString(C1188R.string.program_interaction_release_date_and_short_description);
        this.mDateFormat = android.text.format.DateFormat.getLongDateFormat(this.mContext);
        this.mDurationFormat = MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.NARROW);
        this.mCanonicalGenreUtil = new CanonicalGenreUtil(this.mContext);
        this.mThumbsUpDownRatingPattern = Pattern.compile("^(\\d+),(\\d+)$");
    }

    /* JADX INFO: Multiple debug info for r9v2 int: [D('third' java.lang.CharSequence), D('type' int)] */
    /* access modifiers changed from: package-private */
    public void bindView(@NonNull Program program) {
        CharSequence third;
        this.mLogMetadata = new LogMetadata();
        String first = program.getTitle();
        String second = null;
        String price = null;
        Drawable priceIcon = null;
        int priceColor = this.mAvailabilityDefaultColor;
        String oldPrice = null;
        String contentRating = null;
        this.mLogoUri = null;
        if (this.mLegacy) {
            third = program.getShortDescription();
            this.mLogoUri = program.getLogoUri();
        } else {
            int type = program.getType();
            if (type == 4 || type == 10) {
                this.mLogoUri = program.getLogoUri();
            }
            if (type == 4 || type == 0 || type == 1 || type == 2 || type == 3 || type == 6 || type == 5) {
                contentRating = parseContentRating(program.getContentRating());
            }
            second = generateSecondRow(program);
            int availability = program.getAvailability();
            if (availability != 0) {
                if (availability == 1) {
                    price = this.mAvailabilityFreeWithSubscriptionText;
                } else if (availability == 2) {
                    oldPrice = program.getStartingPrice();
                    price = program.getOfferPrice();
                    if (TextUtils.isEmpty(price)) {
                        price = oldPrice;
                        oldPrice = null;
                    }
                } else if (availability == 3) {
                    price = this.mAvailabilityPurchasedText;
                    priceIcon = this.mAvailabilityPurchasedIcon;
                    priceColor = this.mAvailabilityPurchasedColor;
                } else if (availability == 4) {
                    oldPrice = program.getStartingPrice();
                    price = this.mAvailabilityFreeText;
                    priceColor = this.mAvailabilityFreeColor;
                }
            }
            third = generateThirdRow(program);
        }
        this.mFirstRow.setText(Util.safeTrim(first));
        this.mSecondRow.setText(Util.safeTrim(second));
        this.mThirdRow.setText(Util.safeTrim(third));
        this.mThirdRow.setMaxLines(this.mThirdRowDefaultMaxLines);
        updateRatingSystem(program.getReviewRating(), program.getReviewRatingStyle());
        this.mPrice.setText(Util.safeTrim(price));
        this.mPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(priceIcon, (Drawable) null, (Drawable) null, (Drawable) null);
        this.mPrice.setTextColor(priceColor);
        this.mOldPrice.setText(Util.safeTrim(oldPrice));
        this.mContentRating.setText(Util.safeTrim(contentRating));
        if (this.mContentRating.length() > 0) {
            this.mLogMetadata.hasContentRating = true;
        }
        if (this.mThirdRow.length() != 0) {
            this.mLogMetadata.hasDescription = true;
        }
        if (this.mLogoUri != null) {
            Glide.with(this.mContext).load(this.mLogoUri).into(this.mLogo);
        }
        boolean isInAccessibilityMode = Util.isAccessibilityEnabled(this.mContext);
        this.mLogo.setContentDescription(null);
        if (isInAccessibilityMode) {
            if (price == null || oldPrice == null) {
                this.mPriceContainer.setContentDescription(price);
            } else {
                this.mPriceContainer.setContentDescription(this.mContext.getResources().getString(C1188R.string.program_price_accessibility_description, price, oldPrice));
            }
            if (this.mLogoUri != null) {
                this.mLogo.setContentDescription(program.getLogoContentDescription());
            } else {
                this.mLogo.setContentDescription(null);
            }
        }
        updateVisibility();
    }

    /* JADX INFO: Multiple debug info for r5v1 float: [D('numberFormat' java.text.NumberFormat), D('rawScore' float)] */
    private void updateRatingSystem(String rating, int ratingStyle) {
        String str = rating;
        int i = ratingStyle;
        this.mIsStarRatingSet = false;
        this.mThumbCountUp.setText((CharSequence) null);
        this.mThumbCountDown.setText((CharSequence) null);
        this.mRatingPercentage.setText((CharSequence) null);
        if (!this.mLegacy && str != null) {
            if (i == 0) {
                try {
                    float rawScore = Float.parseFloat(rating);
                    this.mStarRating.setRating(rawScore);
                    this.mIsStarRatingSet = true;
                    this.mStarRating.setContentDescription(this.mContext.getResources().getString(C1188R.string.program_star_rating_accessibility_description, Float.valueOf(rawScore)));
                    this.mLogMetadata.hasStarRatingScore = true;
                    this.mLogMetadata.starRatingScore = rawScore;
                } catch (NumberFormatException e) {
                }
            } else if (i == 1) {
                Matcher matcher = this.mThumbsUpDownRatingPattern.matcher(str);
                if (matcher.find() && matcher.groupCount() == 2) {
                    long upCount = Long.parseLong(matcher.group(1));
                    long downCount = Long.parseLong(matcher.group(2));
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    this.mThumbCountUp.setText(numberFormat.format(upCount));
                    this.mThumbCountDown.setText(numberFormat.format(downCount));
                    this.mThumbCountUp.setContentDescription(this.mContext.getResources().getQuantityString(C1188R.plurals.program_thumbs_up_accessibility_description, (int) upCount, Long.valueOf(upCount)));
                    this.mThumbCountDown.setContentDescription(this.mContext.getResources().getQuantityString(C1188R.plurals.program_thumbs_down_accessibility_description, (int) downCount, Long.valueOf(downCount)));
                    LogMetadata logMetadata = this.mLogMetadata;
                    logMetadata.hasThumbCount = true;
                    logMetadata.thumbsUpCount = upCount;
                    logMetadata.thumbsDownCount = downCount;
                }
            } else if (i == 2) {
                try {
                    float percentage = Float.parseFloat(rating) / 100.0f;
                    NumberFormat percentageFormat = NumberFormat.getPercentInstance();
                    if (str.indexOf(46) == -1) {
                        percentageFormat.setMaximumFractionDigits(0);
                    } else {
                        percentageFormat.setMaximumFractionDigits(1);
                    }
                    this.mRatingPercentage.setText(percentageFormat.format((double) percentage));
                    this.mLogMetadata.hasRatingPercentage = true;
                    this.mLogMetadata.ratingPercentage = percentage;
                } catch (NumberFormatException e2) {
                }
            }
        }
    }

    @Nullable
    private String parseContentRating(@Nullable String ratingString) {
        try {
            TvContentRating[] ratings = ContentRatingsUtil.stringToContentRatings(ratingString);
            if (ratings == null || ratings.length <= 0) {
                return null;
            }
            return ContentRatingsManager.getInstance(this.mContext).getDisplayNameForRating(ratings[0]);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void appendGenreInformation(StringBuilder sb, Program program) {
        String genre = program.getGenre();
        if (TextUtils.isEmpty(genre)) {
            genre = this.mCanonicalGenreUtil.decodeGenres(program.getCanonicalGenres());
        }
        appendNonEmptyMetadataItem(sb, genre);
        this.mLogMetadata.genre = genre;
    }

    @Nullable
    private String generateSecondRow(@NonNull Program program) {
        int type = program.getType();
        StringBuilder sb = new StringBuilder(150);
        if (type == 4) {
            appendNonEmptyMetadataItem(sb, program.getAuthor());
            appendNonEmptyMetadataItem(sb, formatReleaseDate(program.getReleaseDate()));
            long interactionCount = program.getInteractionCount();
            if (interactionCount > 0) {
                int interactionType = program.getInteractionType();
                appendNonEmptyMetadataItem(sb, formatInteractions(interactionType, interactionCount));
                LogMetadata logMetadata = this.mLogMetadata;
                logMetadata.interactionType = interactionType;
                logMetadata.interactionCount = interactionCount;
            }
        } else if (type == 0) {
            appendNonEmptyMetadataItem(sb, formatReleaseDate(program.getReleaseDate()));
            appendGenreInformation(sb, program);
            appendNonEmptyMetadataItem(sb, formatDurationInHoursAndMinutes(program.getDuration()));
        } else if (type == 1) {
            appendNonEmptyMetadataItem(sb, formatReleaseDate(program.getReleaseDate()));
            appendGenreInformation(sb, program);
            int numberOfSeasons = program.getItemCount();
            if (numberOfSeasons > 0) {
                appendNonEmptyMetadataItem(sb, formatQuantity(C1188R.plurals.program_number_of_seasons, numberOfSeasons));
            }
        } else if (type == 2) {
            appendNonEmptyMetadataItem(sb, formatReleaseDate(program.getReleaseDate()));
            String seasonDisplayNumber = program.getSeasonDisplayNumber();
            if (!TextUtils.isEmpty(seasonDisplayNumber)) {
                appendNonEmptyMetadataItem(sb, String.format(Locale.getDefault(), this.mSeasonDisplayNumberFormat, seasonDisplayNumber));
            }
            int numberOfItems = program.getItemCount();
            if (numberOfItems > 0) {
                if (program.getTvSeriesItemType() == 1) {
                    appendNonEmptyMetadataItem(sb, formatQuantity(C1188R.plurals.program_number_of_chapters, numberOfItems));
                } else {
                    appendNonEmptyMetadataItem(sb, formatQuantity(C1188R.plurals.program_number_of_episodes, numberOfItems));
                }
            }
            appendGenreInformation(sb, program);
        } else if (type == 3) {
            appendNonEmptyMetadataItem(sb, formatReleaseDate(program.getReleaseDate()));
            String seasonDisplayNumber2 = program.getSeasonDisplayNumber();
            if (!TextUtils.isEmpty(seasonDisplayNumber2)) {
                appendNonEmptyMetadataItem(sb, String.format(Locale.getDefault(), this.mSeasonDisplayNumberFormat, seasonDisplayNumber2));
            }
            String episodeDisplayNumber = program.getEpisodeDisplayNumber();
            if (!TextUtils.isEmpty(episodeDisplayNumber)) {
                if (program.getTvSeriesItemType() == 1) {
                    appendNonEmptyMetadataItem(sb, String.format(Locale.getDefault(), this.mChapterDisplayNumberFormat, episodeDisplayNumber));
                } else {
                    appendNonEmptyMetadataItem(sb, String.format(Locale.getDefault(), this.mEpisodeDisplayNumberFormat, episodeDisplayNumber));
                }
            }
            appendNonEmptyMetadataItem(sb, formatDurationInHoursAndMinutes(program.getDuration()));
            appendGenreInformation(sb, program);
        } else if (type == 5) {
            appendNonEmptyMetadataItem(sb, formatReleaseDate(program.getReleaseDate()));
            appendNonEmptyMetadataItem(sb, formatDurationInHoursAndMinutes(program.getDuration()));
            long interactionCount2 = program.getInteractionCount();
            if (interactionCount2 > 0) {
                appendNonEmptyMetadataItem(sb, formatInteractions(program.getInteractionType(), interactionCount2));
            }
        } else if (type == 7) {
            appendNonEmptyMetadataItem(sb, program.getGenre());
            appendNonEmptyMetadataItem(sb, program.getAuthor());
            appendNonEmptyMetadataItem(sb, formatDurationInHoursMinutesAndSeconds(program.getDuration()));
        } else if (type == 8 || type == 10) {
            appendNonEmptyMetadataItem(sb, program.getGenre());
            appendNonEmptyMetadataItem(sb, program.getAuthor());
            int numberOfTracks = program.getItemCount();
            if (numberOfTracks > 0) {
                appendNonEmptyMetadataItem(sb, formatQuantity(C1188R.plurals.program_number_of_tracks, numberOfTracks));
            }
        } else if (type == 9) {
            appendNonEmptyMetadataItem(sb, program.getGenre());
        } else if (type == 11) {
            appendNonEmptyMetadataItem(sb, program.getGenre());
            appendNonEmptyMetadataItem(sb, program.getAuthor());
        } else if (type == 12) {
            appendNonEmptyMetadataItem(sb, program.getGenre());
            appendNonEmptyMetadataItem(sb, program.getAuthor());
            appendNonEmptyMetadataItem(sb, formatReleaseDate(program.getReleaseDate()));
        }
        if (sb.length() <= 0) {
            return null;
        }
        sb.append(this.mMetadataSuffix);
        return sb.toString();
    }

    @Nullable
    private CharSequence generateThirdRow(@NonNull Program program) {
        int type = program.getType();
        CharSequence shortDescription = Util.safeTrim(program.getShortDescription());
        boolean isShortDescriptionEmpty = TextUtils.isEmpty(shortDescription);
        if (type == 3) {
            return formatTvSeriesItemTitleAndDescription(program);
        }
        if (type == 7 || type == 8) {
            CharSequence releaseDate = formatReleaseDate(program.getReleaseDate());
            if (releaseDate != null && !isShortDescriptionEmpty) {
                return String.format(Locale.getDefault(), this.mInteractionReleaseDateAndDescriptionFormat, releaseDate, shortDescription);
            } else if (isShortDescriptionEmpty) {
                return releaseDate;
            }
        } else if (type == 9 || type == 10 || type == 11) {
            CharSequence formattedInteractions = formatInteractions(program.getInteractionType(), program.getInteractionCount());
            if (formattedInteractions != null && !isShortDescriptionEmpty) {
                return String.format(Locale.getDefault(), this.mInteractionReleaseDateAndDescriptionFormat, formattedInteractions, shortDescription);
            } else if (isShortDescriptionEmpty) {
                return formattedInteractions;
            }
        }
        return shortDescription;
    }

    @Nullable
    private CharSequence formatTvSeriesItemTitleAndDescription(@NonNull Program program) {
        CharSequence tvSeriesItemTitle = Util.safeTrim(program.getEpisodeTitle());
        CharSequence shortDescription = Util.safeTrim(program.getShortDescription());
        if (TextUtils.isEmpty(tvSeriesItemTitle)) {
            return shortDescription;
        }
        if (TextUtils.isEmpty(shortDescription)) {
            return Html.fromHtml(String.format(Locale.getDefault(), this.mTvSeriesItemTitleFormat, tvSeriesItemTitle), 0);
        }
        return Html.fromHtml(String.format(Locale.getDefault(), this.mTvSeriesItemTitleAndDescriptionFormat, tvSeriesItemTitle, shortDescription), 0);
    }

    private void appendNonEmptyMetadataItem(StringBuilder sb, CharSequence item) {
        CharSequence item2 = Util.safeTrim(item);
        if (!TextUtils.isEmpty(item2)) {
            if (sb.length() > 0) {
                sb.append(this.mMetadataItemSeparator);
            } else {
                sb.append(this.mMetadataPrefix);
            }
            sb.append(item2);
        }
    }

    private CharSequence formatReleaseDate(String releaseDate) {
        if (releaseDate == null) {
            return null;
        }
        try {
            if (releaseDate.length() == 4) {
                int releaseYear = Integer.parseInt(releaseDate);
                return String.format(Locale.getDefault(), "%d", Integer.valueOf(releaseYear));
            } else if (releaseDate.length() == 10) {
                return this.mDateFormat.format(DATE_PARSE_FORMAT.parse(releaseDate));
            } else {
                if (releaseDate.length() == 20) {
                    return DateUtils.getRelativeTimeSpanString(DATETIME_PARSE_FORMAT.parse(releaseDate).getTime(), System.currentTimeMillis(), 0, 0);
                }
                return null;
            }
        } catch (NumberFormatException | ParseException e) {
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    @VisibleForTesting
    public CharSequence formatDurationInHoursAndMinutes(long milliseconds) {
        long totalMinutes = milliseconds / DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS;
        if (totalMinutes >= 60) {
            long hours = totalMinutes / 60;
            if (hours > 23) {
                return null;
            }
            Long.signum(hours);
            long minutes = totalMinutes - (60 * hours);
            if (minutes <= 0) {
                return this.mDurationFormat.format(new Measure(Long.valueOf(hours), MeasureUnit.HOUR));
            }
            return this.mDurationFormat.formatMeasures(new Measure(Long.valueOf(hours), MeasureUnit.HOUR), new Measure(Long.valueOf(minutes), MeasureUnit.MINUTE));
        } else if (totalMinutes > 0) {
            return this.mDurationFormat.format(new Measure(Long.valueOf(totalMinutes), MeasureUnit.MINUTE));
        } else {
            if (milliseconds > 0) {
                return this.mDurationFormat.format(new Measure(1, MeasureUnit.MINUTE));
            }
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    @VisibleForTesting
    public CharSequence formatDurationInHoursMinutesAndSeconds(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        if (totalSeconds >= 3600) {
            return formatDurationInHoursAndMinutes(milliseconds);
        }
        if (totalSeconds >= 60) {
            long minutes = totalSeconds / 60;
            long seconds = totalSeconds - (60 * minutes);
            if (seconds <= 0) {
                return this.mDurationFormat.format(new Measure(Long.valueOf(minutes), MeasureUnit.MINUTE));
            }
            return this.mDurationFormat.formatMeasures(new Measure(Long.valueOf(minutes), MeasureUnit.MINUTE), new Measure(Long.valueOf(seconds), MeasureUnit.SECOND));
        } else if (totalSeconds > 0) {
            return this.mDurationFormat.format(new Measure(Long.valueOf(totalSeconds), MeasureUnit.SECOND));
        } else {
            if (milliseconds > 0) {
                return this.mDurationFormat.format(new Measure(1, MeasureUnit.SECOND));
            }
            return null;
        }
    }

    private CharSequence formatInteractions(int type, long count) {
        int stringId;
        if (count == 0) {
            return null;
        }
        switch (type) {
            case 0:
                stringId = C1188R.plurals.program_interactions_views;
                break;
            case 1:
                stringId = C1188R.plurals.program_interactions_listens;
                break;
            case 2:
                stringId = C1188R.plurals.program_interactions_followers;
                break;
            case 3:
                stringId = C1188R.plurals.program_interactions_fans;
                break;
            case 4:
                stringId = C1188R.plurals.program_interactions_likes;
                break;
            case 5:
                stringId = C1188R.plurals.program_interactions_thumbs;
                break;
            case 6:
                stringId = C1188R.plurals.program_interactions_viewers;
                break;
            default:
                stringId = 0;
                break;
        }
        if (stringId == 0) {
            return null;
        }
        return this.mContext.getResources().getQuantityString(stringId, (int) count, Long.valueOf(count));
    }

    private CharSequence formatQuantity(@PluralsRes int formatResId, int count) {
        return this.mContext.getResources().getQuantityString(formatResId, count, Integer.valueOf(count));
    }

    public void clear() {
        this.mFirstRow.setText((CharSequence) null);
        this.mSecondRow.setText((CharSequence) null);
        this.mThumbCountUp.setText((CharSequence) null);
        this.mThumbCountDown.setText((CharSequence) null);
        this.mRatingPercentage.setText((CharSequence) null);
        this.mIsStarRatingSet = false;
        this.mThirdRow.setText((CharSequence) null);
        this.mPrice.setText((CharSequence) null);
        this.mOldPrice.setText((CharSequence) null);
        this.mContentRating.setText((CharSequence) null);
        this.mLogoUri = null;
        this.mLogMetadata = null;
        updateVisibility();
    }

    /* access modifiers changed from: package-private */
    public void setLegacy(boolean legacy) {
        this.mLegacy = legacy;
    }

    private void updateVisibility() {
        TextView textView = this.mFirstRow;
        boolean z = true;
        setVisibility(textView, textView.length() != 0);
        TextView textView2 = this.mSecondRow;
        setVisibility(textView2, textView2.length() != 0);
        TextView textView3 = this.mThirdRow;
        setVisibility(textView3, textView3.length() != 0);
        setVisibility(this.mStarRating, this.mIsStarRatingSet);
        TextView textView4 = this.mThumbCountUp;
        setVisibility(textView4, textView4.length() != 0);
        TextView textView5 = this.mThumbCountDown;
        setVisibility(textView5, textView5.length() != 0);
        setVisibility(this.mThumbUp, this.mThumbCountUp.length() != 0);
        setVisibility(this.mThumbDown, this.mThumbCountDown.length() != 0);
        TextView textView6 = this.mRatingPercentage;
        setVisibility(textView6, textView6.length() != 0);
        TextView textView7 = this.mPrice;
        setVisibility(textView7, textView7.length() != 0);
        TextView textView8 = this.mOldPrice;
        setVisibility(textView8, textView8.length() != 0);
        TextView textView9 = this.mContentRating;
        setVisibility(textView9, textView9.length() != 0);
        ImageView imageView = this.mLogo;
        if (this.mLogoUri == null) {
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
    public void populateLogEvent(LogEvent event) {
        LogMetadata logMetadata = this.mLogMetadata;
        if (logMetadata != null) {
            logMetadata.populateLogEvent(event);
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

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public TextView getThirdRow() {
        return this.mThirdRow;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public BarRatingView getStarRatingView() {
        return this.mStarRating;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public TextView getOldPriceView() {
        return this.mOldPrice;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public TextView getPriceView() {
        return this.mPrice;
    }

    private static class LogMetadata {
        String genre;
        boolean hasContentRating;
        boolean hasDescription;
        boolean hasRatingPercentage;
        boolean hasStarRatingScore;
        boolean hasThumbCount;
        long interactionCount;
        int interactionType;
        float ratingPercentage;
        float starRatingScore;
        long thumbsDownCount;
        long thumbsUpCount;

        private LogMetadata() {
        }

        /* access modifiers changed from: package-private */
        public void populateLogEvent(LogEvent event) {
            TvlauncherClientLog.Program.Builder program = event.getProgram();
            if (!TextUtils.isEmpty(this.genre)) {
                program.setGenre(this.genre);
            }
            if (this.hasStarRatingScore || this.hasThumbCount || this.hasRatingPercentage) {
                TvlauncherClientLog.Program.Rating.Builder rating = TvlauncherClientLog.Program.Rating.newBuilder();
                if (this.hasStarRatingScore) {
                    rating.setStarCount(this.starRatingScore);
                } else if (this.hasRatingPercentage) {
                    rating.setPercentage(this.ratingPercentage);
                } else {
                    rating.setThumbsUpCount(this.thumbsUpCount);
                    rating.setThumbsDownCount(this.thumbsDownCount);
                }
                program.setRating(rating);
            }
            if (this.interactionCount != 0) {
                TvlauncherClientLog.Program.InteractionCount.Builder interaction = TvlauncherClientLog.Program.InteractionCount.newBuilder();
                TvlauncherClientLog.Program.InteractionCount.Type type = LogEvent.interactionType(this.interactionType);
                if (type != null) {
                    interaction.setType(type);
                }
                interaction.setCount(this.interactionCount);
                program.setInteractionCount(interaction);
            }
            program.setHasContentRating(this.hasContentRating);
            program.setHasDescription(this.hasDescription);
        }
    }
}
