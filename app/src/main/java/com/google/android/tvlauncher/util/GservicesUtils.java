package com.google.android.tvlauncher.util;

import android.content.ContentResolver;
import com.google.android.gsf.Gservices;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GservicesUtils {
    private static final String TV_LAUNCHER_BLACKLIST_REMOVAL_KEY = "tvlauncher:blacklisted_from_removal";
    private static final String TV_LAUNCHER_BLACKLIST_WATCHLIST_KEY = "tvlauncher:blacklisted_from_watchlist";

    private static Set<String> readCommaSeparatedSet(ContentResolver contentResolver, String key) {
        String value = Gservices.getString(contentResolver, key, "");
        if (value.isEmpty()) {
            return Collections.emptySet();
        }
        return new HashSet(Arrays.asList(value.replaceAll("\\s", "").split(",")));
    }

    public static Set<String> retrievePackagesBlacklistedForWatchNext(ContentResolver contentResolver) {
        return readCommaSeparatedSet(contentResolver, TV_LAUNCHER_BLACKLIST_WATCHLIST_KEY);
    }

    public static Set<String> retrievePackagesBlacklistedForProgramRemoval(ContentResolver contentResolver) {
        return readCommaSeparatedSet(contentResolver, TV_LAUNCHER_BLACKLIST_REMOVAL_KEY);
    }
}
