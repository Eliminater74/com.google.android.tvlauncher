package androidx.tvprovider.media.p005tv;

import android.media.tv.TvContentRating;
import android.support.annotation.RestrictTo;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: androidx.tvprovider.media.tv.TvContractUtils */
public class TvContractUtils {
    private static final boolean DEBUG = false;
    private static final String DELIMITER = ",";
    static final TvContentRating[] EMPTY = new TvContentRating[0];
    private static final String TAG = "TvContractUtils";

    public static TvContentRating[] stringToContentRatings(String commaSeparatedRatings) {
        if (TextUtils.isEmpty(commaSeparatedRatings)) {
            return EMPTY;
        }
        String[] ratings = commaSeparatedRatings.split("\\s*,\\s*", -1);
        List<TvContentRating> contentRatings = new ArrayList<>(ratings.length);
        for (String rating : ratings) {
            try {
                contentRatings.add(TvContentRating.unflattenFromString(rating));
            } catch (IllegalArgumentException e) {
                Log.w(TAG, "Can't parse the content rating: '" + rating + "', skipping", e);
            }
        }
        if (contentRatings.size() == 0) {
            return EMPTY;
        }
        return (TvContentRating[]) contentRatings.toArray(new TvContentRating[contentRatings.size()]);
    }

    public static String contentRatingsToString(TvContentRating[] contentRatings) {
        if (contentRatings == null || contentRatings.length == 0) {
            return null;
        }
        StringBuilder ratings = new StringBuilder(contentRatings[0].flattenToString());
        for (int i = 1; i < contentRatings.length; i++) {
            ratings.append(DELIMITER);
            ratings.append(contentRatings[i].flattenToString());
        }
        return ratings.toString();
    }

    public static String[] stringToAudioLanguages(String commaSeparatedString) {
        if (TextUtils.isEmpty(commaSeparatedString)) {
            return null;
        }
        return commaSeparatedString.split("\\s*,\\s*");
    }

    public static String audioLanguagesToString(String[] audioLanguages) {
        if (audioLanguages == null || audioLanguages.length == 0) {
            return null;
        }
        StringBuilder ratings = new StringBuilder(audioLanguages[0]);
        for (int i = 1; i < audioLanguages.length; i++) {
            ratings.append(DELIMITER);
            ratings.append(audioLanguages[i]);
        }
        return ratings.toString();
    }

    private TvContractUtils() {
    }
}
