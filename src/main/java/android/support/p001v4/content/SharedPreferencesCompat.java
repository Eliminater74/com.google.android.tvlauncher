package android.support.p001v4.content;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

@Deprecated
/* renamed from: android.support.v4.content.SharedPreferencesCompat */
public final class SharedPreferencesCompat {

    @Deprecated
    /* renamed from: android.support.v4.content.SharedPreferencesCompat$EditorCompat */
    public static final class EditorCompat {
        private static EditorCompat sInstance;
        private final Helper mHelper = new Helper();

        /* renamed from: android.support.v4.content.SharedPreferencesCompat$EditorCompat$Helper */
        private static class Helper {
            Helper() {
            }

            public void apply(@NonNull SharedPreferences.Editor editor) {
                try {
                    editor.apply();
                } catch (AbstractMethodError e) {
                    editor.commit();
                }
            }
        }

        private EditorCompat() {
        }

        @Deprecated
        public static EditorCompat getInstance() {
            if (sInstance == null) {
                sInstance = new EditorCompat();
            }
            return sInstance;
        }

        @Deprecated
        public void apply(@NonNull SharedPreferences.Editor editor) {
            this.mHelper.apply(editor);
        }
    }

    private SharedPreferencesCompat() {
    }
}
