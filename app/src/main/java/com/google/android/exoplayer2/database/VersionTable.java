package com.google.android.exoplayer2.database;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.VisibleForTesting;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class VersionTable {
    public static final int FEATURE_CACHE_CONTENT_METADATA = 1;
    public static final int FEATURE_CACHE_FILE_METADATA = 2;
    public static final int FEATURE_OFFLINE = 0;
    public static final int VERSION_UNSET = -1;
    private static final String COLUMN_FEATURE = "feature";
    private static final String COLUMN_INSTANCE_UID = "instance_uid";
    private static final String COLUMN_VERSION = "version";
    private static final String PRIMARY_KEY = "PRIMARY KEY (feature, instance_uid)";
    private static final String SQL_CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ExoPlayerVersions (feature INTEGER NOT NULL,instance_uid TEXT NOT NULL,version INTEGER NOT NULL,PRIMARY KEY (feature, instance_uid))";
    private static final String TABLE_NAME = "ExoPlayerVersions";
    private static final String WHERE_FEATURE_AND_INSTANCE_UID_EQUALS = "feature = ? AND instance_uid = ?";

    private VersionTable() {
    }

    public static void setVersion(SQLiteDatabase writableDatabase, int feature, String instanceUid, int version) throws DatabaseIOException {
        try {
            writableDatabase.execSQL(SQL_CREATE_TABLE_IF_NOT_EXISTS);
            ContentValues values = new ContentValues();
            values.put(COLUMN_FEATURE, Integer.valueOf(feature));
            values.put(COLUMN_INSTANCE_UID, instanceUid);
            values.put(COLUMN_VERSION, Integer.valueOf(version));
            writableDatabase.replaceOrThrow(TABLE_NAME, null, values);
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    public static void removeVersion(SQLiteDatabase writableDatabase, int feature, String instanceUid) throws DatabaseIOException {
        try {
            if (tableExists(writableDatabase, TABLE_NAME)) {
                writableDatabase.delete(TABLE_NAME, WHERE_FEATURE_AND_INSTANCE_UID_EQUALS, featureAndInstanceUidArguments(feature, instanceUid));
            }
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003c, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003d, code lost:
        if (r0 != null) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0047, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getVersion(android.database.sqlite.SQLiteDatabase r11, int r12, java.lang.String r13) throws com.google.android.exoplayer2.database.DatabaseIOException {
        /*
            java.lang.String r0 = "ExoPlayerVersions"
            boolean r0 = tableExists(r11, r0)     // Catch:{ SQLException -> 0x0048 }
            r1 = -1
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            java.lang.String r3 = "ExoPlayerVersions"
            r0 = 1
            java.lang.String[] r4 = new java.lang.String[r0]     // Catch:{ SQLException -> 0x0048 }
            java.lang.String r0 = "version"
            r10 = 0
            r4[r10] = r0     // Catch:{ SQLException -> 0x0048 }
            java.lang.String r5 = "feature = ? AND instance_uid = ?"
            java.lang.String[] r6 = featureAndInstanceUidArguments(r12, r13)     // Catch:{ SQLException -> 0x0048 }
            r7 = 0
            r8 = 0
            r9 = 0
            r2 = r11
            android.database.Cursor r0 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ SQLException -> 0x0048 }
            int r2 = r0.getCount()     // Catch:{ all -> 0x003a }
            if (r2 != 0) goto L_0x002f
            r0.close()     // Catch:{ SQLException -> 0x0048 }
            return r1
        L_0x002f:
            r0.moveToNext()     // Catch:{ all -> 0x003a }
            int r1 = r0.getInt(r10)     // Catch:{ all -> 0x003a }
            r0.close()     // Catch:{ SQLException -> 0x0048 }
            return r1
        L_0x003a:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x003c }
        L_0x003c:
            r2 = move-exception
            if (r0 == 0) goto L_0x0047
            r0.close()     // Catch:{ all -> 0x0043 }
            goto L_0x0047
        L_0x0043:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r1, r3)     // Catch:{ SQLException -> 0x0048 }
        L_0x0047:
            throw r2     // Catch:{ SQLException -> 0x0048 }
        L_0x0048:
            r0 = move-exception
            com.google.android.exoplayer2.database.DatabaseIOException r1 = new com.google.android.exoplayer2.database.DatabaseIOException
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.database.VersionTable.getVersion(android.database.sqlite.SQLiteDatabase, int, java.lang.String):int");
    }

    @VisibleForTesting
    static boolean tableExists(SQLiteDatabase readableDatabase, String tableName) {
        if (DatabaseUtils.queryNumEntries(readableDatabase, "sqlite_master", "tbl_name = ?", new String[]{tableName}) > 0) {
            return true;
        }
        return false;
    }

    private static String[] featureAndInstanceUidArguments(int feature, String instance) {
        return new String[]{Integer.toString(feature), instance};
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface Feature {
    }
}
