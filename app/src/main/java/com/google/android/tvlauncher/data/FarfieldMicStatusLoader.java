package com.google.android.tvlauncher.data;

import android.content.Context;

import com.google.android.tvlauncher.util.FarfieldMicStatusContract;

public class FarfieldMicStatusLoader extends DataLoader<Integer> {
    private static final String[] PROJECTION = {FarfieldMicStatusContract.COLUMN_MIC_STATUS};
    private static final String TAG = "FarfieldMicStatusLdr";

    public FarfieldMicStatusLoader(Context context) {
        super(context, FarfieldMicStatusContract.FARFIELD_MIC_STATUS_CONTENT_URI);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002c, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0035, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Integer loadData() {
        /*
            r7 = this;
            r0 = 0
            r7.mData = r0
            android.content.Context r0 = r7.getContext()     // Catch:{ Exception -> 0x003c }
            android.content.ContentResolver r1 = r0.getContentResolver()     // Catch:{ Exception -> 0x003c }
            android.net.Uri r2 = com.google.android.tvlauncher.util.FarfieldMicStatusContract.FARFIELD_MIC_STATUS_CONTENT_URI     // Catch:{ Exception -> 0x003c }
            java.lang.String[] r3 = com.google.android.tvlauncher.data.FarfieldMicStatusLoader.PROJECTION     // Catch:{ Exception -> 0x003c }
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r0 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x003c }
            if (r0 == 0) goto L_0x0036
            boolean r1 = r0.moveToFirst()     // Catch:{ all -> 0x002a }
            if (r1 == 0) goto L_0x0036
            r1 = 0
            int r1 = r0.getInt(r1)     // Catch:{ all -> 0x002a }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x002a }
            r7.mData = r1     // Catch:{ all -> 0x002a }
            goto L_0x0036
        L_0x002a:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x002c }
        L_0x002c:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0031 }
            goto L_0x0035
        L_0x0031:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r1, r3)     // Catch:{ Exception -> 0x003c }
        L_0x0035:
            throw r2     // Catch:{ Exception -> 0x003c }
        L_0x0036:
            if (r0 == 0) goto L_0x003b
            r0.close()     // Catch:{ Exception -> 0x003c }
        L_0x003b:
            goto L_0x0044
        L_0x003c:
            r0 = move-exception
            java.lang.String r1 = "FarfieldMicStatusLdr"
            java.lang.String r2 = "Exception in loadInBackground()"
            android.util.Log.e(r1, r2, r0)
        L_0x0044:
            java.lang.Object r0 = r7.mData
            java.lang.Integer r0 = (java.lang.Integer) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.data.FarfieldMicStatusLoader.loadData():java.lang.Integer");
    }
}
