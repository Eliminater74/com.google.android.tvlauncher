package com.google.android.tvlauncher.home.contentrating;

import android.media.tv.TvContentRating;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class ContentRatingsUtil {
    @Nullable
    public static TvContentRating[] stringToContentRatings(@Nullable String commaSeparatedRatings) {
        if (TextUtils.isEmpty(commaSeparatedRatings)) {
            return null;
        }
        String[] ratings = commaSeparatedRatings.split("\\s*,\\s*");
        TvContentRating[] contentRatings = new TvContentRating[ratings.length];
        for (int i = 0; i < contentRatings.length; i++) {
            contentRatings[i] = TvContentRating.unflattenFromString(ratings[i]);
        }
        return contentRatings;
    }
}
