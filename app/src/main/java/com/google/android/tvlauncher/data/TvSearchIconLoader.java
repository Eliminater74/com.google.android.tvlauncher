package com.google.android.tvlauncher.data;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.tvlauncher.home.util.SearchWidgetInfoContract;

public class TvSearchIconLoader extends DataLoader<Drawable> {
    private static final String TAG = "TvSearchIconLdr";

    public TvSearchIconLoader(Context context) {
        super(context, SearchWidgetInfoContract.ICON_CONTENT_URI);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0047, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0050, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.drawable.Drawable loadData() {
        /*
            r9 = this;
            java.lang.String r0 = "com.google.android.katniss"
            r1 = 0
            r9.mData = r1
            android.content.Context r2 = r9.getContext()     // Catch:{ Exception -> 0x0057 }
            android.content.ContentResolver r3 = r2.getContentResolver()     // Catch:{ Exception -> 0x0057 }
            android.net.Uri r4 = com.google.android.tvlauncher.home.util.SearchWidgetInfoContract.ICON_CONTENT_URI     // Catch:{ Exception -> 0x0057 }
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            android.database.Cursor r2 = r3.query(r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0057 }
            if (r2 == 0) goto L_0x0051
            boolean r3 = r2.moveToFirst()     // Catch:{ all -> 0x0045 }
            if (r3 == 0) goto L_0x0051
            r3 = 0
            java.lang.String r3 = r2.getString(r3)     // Catch:{ all -> 0x0045 }
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x0045 }
            if (r4 != 0) goto L_0x0051
            android.content.Context r4 = r9.getContext()     // Catch:{ all -> 0x0045 }
            android.content.pm.PackageManager r4 = r4.getPackageManager()     // Catch:{ all -> 0x0045 }
            android.content.res.Resources r5 = r4.getResourcesForApplication(r0)     // Catch:{ NameNotFoundException -> 0x0043 }
            java.lang.String r6 = "drawable"
            int r0 = r5.getIdentifier(r3, r6, r0)     // Catch:{ NameNotFoundException -> 0x0043 }
            android.graphics.drawable.Drawable r1 = r5.getDrawable(r0, r1)     // Catch:{ NameNotFoundException -> 0x0043 }
            r9.mData = r1     // Catch:{ NameNotFoundException -> 0x0043 }
            goto L_0x0051
        L_0x0043:
            r0 = move-exception
            goto L_0x0051
        L_0x0045:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x0047 }
        L_0x0047:
            r1 = move-exception
            r2.close()     // Catch:{ all -> 0x004c }
            goto L_0x0050
        L_0x004c:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r0, r3)     // Catch:{ Exception -> 0x0057 }
        L_0x0050:
            throw r1     // Catch:{ Exception -> 0x0057 }
        L_0x0051:
            if (r2 == 0) goto L_0x0056
            r2.close()     // Catch:{ Exception -> 0x0057 }
        L_0x0056:
            goto L_0x005f
        L_0x0057:
            r0 = move-exception
            java.lang.String r1 = "TvSearchIconLdr"
            java.lang.String r2 = "Exception in loadInBackground()"
            android.util.Log.e(r1, r2, r0)
        L_0x005f:
            java.lang.Object r0 = r9.mData
            android.graphics.drawable.Drawable r0 = (android.graphics.drawable.Drawable) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.data.TvSearchIconLoader.loadData():android.graphics.drawable.Drawable");
    }
}
