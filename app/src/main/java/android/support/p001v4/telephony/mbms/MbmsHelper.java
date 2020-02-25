package android.support.p001v4.telephony.mbms;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.mbms.ServiceInfo;

import java.util.Locale;

/* renamed from: android.support.v4.telephony.mbms.MbmsHelper */
public final class MbmsHelper {
    private MbmsHelper() {
    }

    @Nullable
    @SuppressLint({"BanTargetApiAnnotation"})
    @TargetApi(28)
    public static CharSequence getBestNameForService(@NonNull Context context, @NonNull ServiceInfo serviceInfo) {
        if (Build.VERSION.SDK_INT < 28) {
            return null;
        }
        LocaleList localeList = context.getResources().getConfiguration().getLocales();
        int numLanguagesSupportedByService = serviceInfo.getNamedContentLocales().size();
        if (numLanguagesSupportedByService == 0) {
            return null;
        }
        String[] supportedLanguages = new String[numLanguagesSupportedByService];
        int i = 0;
        for (Locale l : serviceInfo.getNamedContentLocales()) {
            supportedLanguages[i] = l.toLanguageTag();
            i++;
        }
        Locale bestLocale = localeList.getFirstMatch(supportedLanguages);
        if (bestLocale == null) {
            return null;
        }
        return serviceInfo.getNameForLocale(bestLocale);
    }
}
