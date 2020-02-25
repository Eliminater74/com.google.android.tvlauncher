package android.support.p001v4.p003os;

import android.os.LocaleList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.util.Locale;

@RequiresApi(24)
/* renamed from: android.support.v4.os.LocaleListPlatformWrapper */
final class LocaleListPlatformWrapper implements LocaleListInterface {
    private final LocaleList mLocaleList;

    LocaleListPlatformWrapper(LocaleList localeList) {
        this.mLocaleList = localeList;
    }

    public Object getLocaleList() {
        return this.mLocaleList;
    }

    public Locale get(int index) {
        return this.mLocaleList.get(index);
    }

    public boolean isEmpty() {
        return this.mLocaleList.isEmpty();
    }

    public int size() {
        return this.mLocaleList.size();
    }

    public int indexOf(Locale locale) {
        return this.mLocaleList.indexOf(locale);
    }

    public boolean equals(Object other) {
        return this.mLocaleList.equals(((LocaleListInterface) other).getLocaleList());
    }

    public int hashCode() {
        return this.mLocaleList.hashCode();
    }

    public String toString() {
        return this.mLocaleList.toString();
    }

    public String toLanguageTags() {
        return this.mLocaleList.toLanguageTags();
    }

    @Nullable
    public Locale getFirstMatch(@NonNull String[] supportedLocales) {
        return this.mLocaleList.getFirstMatch(supportedLocales);
    }
}
