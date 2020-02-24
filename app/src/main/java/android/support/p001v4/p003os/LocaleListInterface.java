package android.support.p001v4.p003os;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.media.MediaDescriptionCompat;
import java.util.Locale;

/* renamed from: android.support.v4.os.LocaleListInterface */
interface LocaleListInterface {
    Locale get(int i);

    @Nullable
    Locale getFirstMatch(@NonNull String[] strArr);

    Object getLocaleList();

    @IntRange(from = -1)
    int indexOf(Locale locale);

    boolean isEmpty();

    @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
    int size();

    String toLanguageTags();
}
