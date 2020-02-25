package com.google.android.exoplayer2.upstream.cache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.util.Assertions;

import java.util.Set;

final class CacheFileMetadataIndex {
    private static final int COLUMN_INDEX_LAST_TOUCH_TIMESTAMP = 2;
    private static final int COLUMN_INDEX_LENGTH = 1;
    private static final int COLUMN_INDEX_NAME = 0;
    private static final String COLUMN_LAST_TOUCH_TIMESTAMP = "last_touch_timestamp";
    private static final String COLUMN_LENGTH = "length";
    private static final String[] COLUMNS = {"name", COLUMN_LENGTH, COLUMN_LAST_TOUCH_TIMESTAMP};
    private static final String COLUMN_NAME = "name";
    private static final String TABLE_PREFIX = "ExoPlayerCacheFileMetadata";
    private static final String TABLE_SCHEMA = "(name TEXT PRIMARY KEY NOT NULL,length INTEGER NOT NULL,last_touch_timestamp INTEGER NOT NULL)";
    private static final int TABLE_VERSION = 1;
    private static final String WHERE_NAME_EQUALS = "0 = ?";
    private final DatabaseProvider databaseProvider;
    private String tableName;

    public CacheFileMetadataIndex(DatabaseProvider databaseProvider2) {
        this.databaseProvider = databaseProvider2;
    }

    public static void delete(DatabaseProvider databaseProvider2, long uid) throws DatabaseIOException {
        SQLiteDatabase writableDatabase;
        String hexUid = Long.toHexString(uid);
        try {
            String tableName2 = getTableName(hexUid);
            writableDatabase = databaseProvider2.getWritableDatabase();
            writableDatabase.beginTransaction();
            VersionTable.removeVersion(writableDatabase, 2, hexUid);
            dropTable(writableDatabase, tableName2);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }

    private static void dropTable(SQLiteDatabase writableDatabase, String tableName2) {
        String valueOf = String.valueOf(tableName2);
        writableDatabase.execSQL(valueOf.length() != 0 ? "DROP TABLE IF EXISTS ".concat(valueOf) : new String("DROP TABLE IF EXISTS "));
    }

    private static String getTableName(String hexUid) {
        String valueOf = String.valueOf(TABLE_PREFIX);
        String valueOf2 = String.valueOf(hexUid);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public void initialize(long uid) throws DatabaseIOException {
        SQLiteDatabase writableDatabase;
        try {
            String hexUid = Long.toHexString(uid);
            this.tableName = getTableName(hexUid);
            if (VersionTable.getVersion(this.databaseProvider.getReadableDatabase(), 2, hexUid) != 1) {
                writableDatabase = this.databaseProvider.getWritableDatabase();
                writableDatabase.beginTransaction();
                VersionTable.setVersion(writableDatabase, 2, hexUid, 1);
                dropTable(writableDatabase, this.tableName);
                String str = this.tableName;
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 108);
                sb.append("CREATE TABLE ");
                sb.append(str);
                sb.append(" ");
                sb.append(TABLE_SCHEMA);
                writableDatabase.execSQL(sb.toString());
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
            }
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0033, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0034, code lost:
        if (r0 != null) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003e, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Map<java.lang.String, com.google.android.exoplayer2.upstream.cache.CacheFileMetadata> getAll() throws com.google.android.exoplayer2.database.DatabaseIOException {
        /*
            r8 = this;
            android.database.Cursor r0 = r8.getCursor()     // Catch:{ SQLException -> 0x003f }
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ all -> 0x0031 }
            int r2 = r0.getCount()     // Catch:{ all -> 0x0031 }
            r1.<init>(r2)     // Catch:{ all -> 0x0031 }
        L_0x000d:
            boolean r2 = r0.moveToNext()     // Catch:{ all -> 0x0031 }
            if (r2 == 0) goto L_0x002c
            r2 = 0
            java.lang.String r2 = r0.getString(r2)     // Catch:{ all -> 0x0031 }
            r3 = 1
            long r3 = r0.getLong(r3)     // Catch:{ all -> 0x0031 }
            r5 = 2
            long r5 = r0.getLong(r5)     // Catch:{ all -> 0x0031 }
            com.google.android.exoplayer2.upstream.cache.CacheFileMetadata r7 = new com.google.android.exoplayer2.upstream.cache.CacheFileMetadata     // Catch:{ all -> 0x0031 }
            r7.<init>(r3, r5)     // Catch:{ all -> 0x0031 }
            r1.put(r2, r7)     // Catch:{ all -> 0x0031 }
            goto L_0x000d
        L_0x002c:
            r0.close()     // Catch:{ SQLException -> 0x003f }
            return r1
        L_0x0031:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0033 }
        L_0x0033:
            r2 = move-exception
            if (r0 == 0) goto L_0x003e
            r0.close()     // Catch:{ all -> 0x003a }
            goto L_0x003e
        L_0x003a:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r1, r3)     // Catch:{ SQLException -> 0x003f }
        L_0x003e:
            throw r2     // Catch:{ SQLException -> 0x003f }
        L_0x003f:
            r0 = move-exception
            com.google.android.exoplayer2.database.DatabaseIOException r1 = new com.google.android.exoplayer2.database.DatabaseIOException
            r1.<init>(r0)
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheFileMetadataIndex.getAll():java.util.Map");
    }

    public void set(String name, long length, long lastTouchTimestamp) throws DatabaseIOException {
        Assertions.checkNotNull(this.tableName);
        try {
            SQLiteDatabase writableDatabase = this.databaseProvider.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put(COLUMN_LENGTH, Long.valueOf(length));
            values.put(COLUMN_LAST_TOUCH_TIMESTAMP, Long.valueOf(lastTouchTimestamp));
            writableDatabase.replaceOrThrow(this.tableName, null, values);
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    public void remove(String name) throws DatabaseIOException {
        Assertions.checkNotNull(this.tableName);
        try {
            this.databaseProvider.getWritableDatabase().delete(this.tableName, WHERE_NAME_EQUALS, new String[]{name});
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    public void removeAll(Set<String> names) throws DatabaseIOException {
        SQLiteDatabase writableDatabase;
        Assertions.checkNotNull(this.tableName);
        try {
            writableDatabase = this.databaseProvider.getWritableDatabase();
            writableDatabase.beginTransaction();
            for (String name : names) {
                writableDatabase.delete(this.tableName, WHERE_NAME_EQUALS, new String[]{name});
            }
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }

    private Cursor getCursor() {
        Assertions.checkNotNull(this.tableName);
        return this.databaseProvider.getReadableDatabase().query(this.tableName, COLUMNS, null, null, null, null, null);
    }
}
