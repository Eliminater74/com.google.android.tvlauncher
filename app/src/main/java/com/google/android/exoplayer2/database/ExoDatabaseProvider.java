package com.google.android.exoplayer2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class ExoDatabaseProvider extends SQLiteOpenHelper implements DatabaseProvider {
    public static final String DATABASE_NAME = "exoplayer_internal.db";
    private static final String TAG = "ExoDatabaseProvider";
    private static final int VERSION = 1;

    public ExoDatabaseProvider(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        wipeDatabase(db);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0088, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0089, code lost:
        if (r1 != null) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x008f, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0090, code lost:
        com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r0, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0093, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void wipeDatabase(android.database.sqlite.SQLiteDatabase r12) {
        /*
            r0 = 2
            java.lang.String[] r3 = new java.lang.String[r0]
            r0 = 0
            java.lang.String r1 = "type"
            r3[r0] = r1
            r9 = 1
            java.lang.String r1 = "name"
            r3[r9] = r1
            java.lang.String r2 = "sqlite_master"
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r1 = r12
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8)
        L_0x001d:
            boolean r2 = r1.moveToNext()     // Catch:{ all -> 0x0086 }
            if (r2 == 0) goto L_0x0082
            java.lang.String r2 = r1.getString(r0)     // Catch:{ all -> 0x0086 }
            java.lang.String r4 = r1.getString(r9)     // Catch:{ all -> 0x0086 }
            java.lang.String r5 = "sqlite_sequence"
            boolean r5 = r5.equals(r4)     // Catch:{ all -> 0x0086 }
            if (r5 != 0) goto L_0x0081
            java.lang.String r5 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x0086 }
            int r5 = r5.length()     // Catch:{ all -> 0x0086 }
            int r5 = r5 + 16
            java.lang.String r6 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x0086 }
            int r6 = r6.length()     // Catch:{ all -> 0x0086 }
            int r5 = r5 + r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0086 }
            r6.<init>(r5)     // Catch:{ all -> 0x0086 }
            java.lang.String r5 = "DROP "
            r6.append(r5)     // Catch:{ all -> 0x0086 }
            r6.append(r2)     // Catch:{ all -> 0x0086 }
            java.lang.String r5 = " IF EXISTS "
            r6.append(r5)     // Catch:{ all -> 0x0086 }
            r6.append(r4)     // Catch:{ all -> 0x0086 }
            java.lang.String r5 = r6.toString()     // Catch:{ all -> 0x0086 }
            r12.execSQL(r5)     // Catch:{ SQLException -> 0x0064 }
            goto L_0x0081
        L_0x0064:
            r6 = move-exception
            java.lang.String r7 = "ExoDatabaseProvider"
            java.lang.String r8 = "Error executing "
            java.lang.String r10 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x0086 }
            int r11 = r10.length()     // Catch:{ all -> 0x0086 }
            if (r11 == 0) goto L_0x0078
            java.lang.String r8 = r8.concat(r10)     // Catch:{ all -> 0x0086 }
            goto L_0x007e
        L_0x0078:
            java.lang.String r10 = new java.lang.String     // Catch:{ all -> 0x0086 }
            r10.<init>(r8)     // Catch:{ all -> 0x0086 }
            r8 = r10
        L_0x007e:
            com.google.android.exoplayer2.util.Log.m27e(r7, r8, r6)     // Catch:{ all -> 0x0086 }
        L_0x0081:
            goto L_0x001d
        L_0x0082:
            r1.close()
            return
        L_0x0086:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x0088 }
        L_0x0088:
            r2 = move-exception
            if (r1 == 0) goto L_0x0093
            r1.close()     // Catch:{ all -> 0x008f }
            goto L_0x0093
        L_0x008f:
            r4 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r0, r4)
        L_0x0093:
            throw r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.database.ExoDatabaseProvider.wipeDatabase(android.database.sqlite.SQLiteDatabase):void");
    }
}
