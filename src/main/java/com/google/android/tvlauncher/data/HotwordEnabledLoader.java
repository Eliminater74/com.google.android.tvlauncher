package com.google.android.tvlauncher.data;

import android.content.Context;
import android.net.Uri;

public class HotwordEnabledLoader extends DataLoader<Boolean> {
    private static final Uri HOTWORD_ENABLED_CONTENT_URI = Uri.parse("content://com.google.android.katniss.search.searchapi.VoiceInteractionProvider/sharedvalue");
    private static final String TAG = "HotwordEnabledLdr";

    public HotwordEnabledLoader(Context context) {
        super(context, HOTWORD_ENABLED_CONTENT_URI);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0038, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0041, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Boolean loadData() {
        /*
            r8 = this;
            r0 = 0
            r8.mData = r0
            android.content.Context r0 = r8.getContext()     // Catch:{ Exception -> 0x0048 }
            android.content.ContentResolver r1 = r0.getContentResolver()     // Catch:{ Exception -> 0x0048 }
            android.net.Uri r2 = com.google.android.tvlauncher.data.HotwordEnabledLoader.HOTWORD_ENABLED_CONTENT_URI     // Catch:{ Exception -> 0x0048 }
            r3 = 0
            java.lang.String r4 = "key = 'is_listening_for_hotword'"
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r0 = r1.query(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0048 }
            if (r0 == 0) goto L_0x0042
            boolean r1 = r0.moveToFirst()     // Catch:{ all -> 0x0036 }
            if (r1 == 0) goto L_0x0042
            java.lang.String r1 = "value"
            int r1 = r0.getColumnIndex(r1)     // Catch:{ all -> 0x0036 }
            int r1 = r0.getInt(r1)     // Catch:{ all -> 0x0036 }
            r2 = 1
            if (r1 != r2) goto L_0x002e
            goto L_0x002f
        L_0x002e:
            r2 = 0
        L_0x002f:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r2)     // Catch:{ all -> 0x0036 }
            r8.mData = r1     // Catch:{ all -> 0x0036 }
            goto L_0x0042
        L_0x0036:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0038 }
        L_0x0038:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x003d }
            goto L_0x0041
        L_0x003d:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r1, r3)     // Catch:{ Exception -> 0x0048 }
        L_0x0041:
            throw r2     // Catch:{ Exception -> 0x0048 }
        L_0x0042:
            if (r0 == 0) goto L_0x0047
            r0.close()     // Catch:{ Exception -> 0x0048 }
        L_0x0047:
            goto L_0x0050
        L_0x0048:
            r0 = move-exception
            java.lang.String r1 = "HotwordEnabledLdr"
            java.lang.String r2 = "Exception in loadInBackground()"
            android.util.Log.e(r1, r2, r0)
        L_0x0050:
            java.lang.Object r0 = r8.mData
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.data.HotwordEnabledLoader.loadData():java.lang.Boolean");
    }
}
