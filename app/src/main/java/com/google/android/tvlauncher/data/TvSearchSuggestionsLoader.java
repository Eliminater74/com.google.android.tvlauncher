package com.google.android.tvlauncher.data;

import android.content.Context;
import com.google.android.tvlauncher.home.util.SearchWidgetInfoContract;

public class TvSearchSuggestionsLoader extends DataLoader<String[]> {
    private static final String TAG = "TvSearchSuggestionsLdr";

    public TvSearchSuggestionsLoader(Context context) {
        super(context, SearchWidgetInfoContract.SUGGESTIONS_CONTENT_URI);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0041, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004a, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String[] loadData() {
        /*
            r7 = this;
            r0 = 0
            r7.mData = r0
            android.content.Context r0 = r7.getContext()     // Catch:{ Exception -> 0x0051 }
            android.content.ContentResolver r1 = r0.getContentResolver()     // Catch:{ Exception -> 0x0051 }
            android.net.Uri r2 = com.google.android.tvlauncher.home.util.SearchWidgetInfoContract.SUGGESTIONS_CONTENT_URI     // Catch:{ Exception -> 0x0051 }
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r0 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x0051 }
            if (r0 == 0) goto L_0x004b
            boolean r1 = r0.moveToFirst()     // Catch:{ all -> 0x003f }
            if (r1 == 0) goto L_0x004b
            int r1 = r0.getCount()     // Catch:{ all -> 0x003f }
            java.lang.String r2 = "suggestion"
            int r2 = r0.getColumnIndex(r2)     // Catch:{ all -> 0x003f }
            java.lang.String[] r3 = new java.lang.String[r1]     // Catch:{ all -> 0x003f }
            r7.mData = r3     // Catch:{ all -> 0x003f }
            r3 = 0
        L_0x002d:
            if (r3 >= r1) goto L_0x004b
            java.lang.Object r4 = r7.mData     // Catch:{ all -> 0x003f }
            java.lang.String[] r4 = (java.lang.String[]) r4     // Catch:{ all -> 0x003f }
            java.lang.String r5 = r0.getString(r2)     // Catch:{ all -> 0x003f }
            r4[r3] = r5     // Catch:{ all -> 0x003f }
            r0.moveToNext()     // Catch:{ all -> 0x003f }
            int r3 = r3 + 1
            goto L_0x002d
        L_0x003f:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0041 }
        L_0x0041:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0046 }
            goto L_0x004a
        L_0x0046:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r1, r3)     // Catch:{ Exception -> 0x0051 }
        L_0x004a:
            throw r2     // Catch:{ Exception -> 0x0051 }
        L_0x004b:
            if (r0 == 0) goto L_0x0050
            r0.close()     // Catch:{ Exception -> 0x0051 }
        L_0x0050:
            goto L_0x0059
        L_0x0051:
            r0 = move-exception
            java.lang.String r1 = "TvSearchSuggestionsLdr"
            java.lang.String r2 = "Exception in loadInBackground()"
            android.util.Log.e(r1, r2, r0)
        L_0x0059:
            java.lang.Object r0 = r7.mData
            java.lang.String[] r0 = (java.lang.String[]) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.data.TvSearchSuggestionsLoader.loadData():java.lang.String[]");
    }
}
