package com.google.android.exoplayer2.offline;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.util.ArrayList;
import java.util.List;

public final class DefaultDownloadIndex implements WritableDownloadIndex {
    @VisibleForTesting
    static final int TABLE_VERSION = 2;
    private static final String COLUMN_BYTES_DOWNLOADED = "bytes_downloaded";
    private static final String COLUMN_CONTENT_LENGTH = "content_length";
    private static final String COLUMN_CUSTOM_CACHE_KEY = "custom_cache_key";
    private static final String COLUMN_DATA = "data";
    private static final String COLUMN_FAILURE_REASON = "failure_reason";
    private static final String COLUMN_ID = "id";
    private static final int COLUMN_INDEX_BYTES_DOWNLOADED = 13;
    private static final int COLUMN_INDEX_CONTENT_LENGTH = 9;
    private static final int COLUMN_INDEX_CUSTOM_CACHE_KEY = 4;
    private static final int COLUMN_INDEX_DATA = 5;
    private static final int COLUMN_INDEX_FAILURE_REASON = 11;
    private static final int COLUMN_INDEX_ID = 0;
    private static final int COLUMN_INDEX_PERCENT_DOWNLOADED = 12;
    private static final int COLUMN_INDEX_START_TIME_MS = 7;
    private static final int COLUMN_INDEX_STATE = 6;
    private static final int COLUMN_INDEX_STOP_REASON = 10;
    private static final int COLUMN_INDEX_STREAM_KEYS = 3;
    private static final int COLUMN_INDEX_TYPE = 1;
    private static final int COLUMN_INDEX_UPDATE_TIME_MS = 8;
    private static final int COLUMN_INDEX_URI = 2;
    private static final String COLUMN_PERCENT_DOWNLOADED = "percent_downloaded";
    private static final String COLUMN_START_TIME_MS = "start_time_ms";
    private static final String COLUMN_STATE = "state";
    private static final String COLUMN_STOP_REASON = "stop_reason";
    private static final String COLUMN_STREAM_KEYS = "stream_keys";
    private static final String COLUMN_TYPE = "title";
    private static final String COLUMN_UPDATE_TIME_MS = "update_time_ms";
    private static final String[] COLUMNS = {"id", "title", "uri", COLUMN_STREAM_KEYS, COLUMN_CUSTOM_CACHE_KEY, "data", "state", COLUMN_START_TIME_MS, COLUMN_UPDATE_TIME_MS, COLUMN_CONTENT_LENGTH, COLUMN_STOP_REASON, COLUMN_FAILURE_REASON, COLUMN_PERCENT_DOWNLOADED, COLUMN_BYTES_DOWNLOADED};
    private static final String COLUMN_URI = "uri";
    private static final String TABLE_PREFIX = "ExoPlayerDownloads";
    private static final String TABLE_SCHEMA = "(id TEXT PRIMARY KEY NOT NULL,title TEXT NOT NULL,uri TEXT NOT NULL,stream_keys TEXT NOT NULL,custom_cache_key TEXT,data BLOB NOT NULL,state INTEGER NOT NULL,start_time_ms INTEGER NOT NULL,update_time_ms INTEGER NOT NULL,content_length INTEGER NOT NULL,stop_reason INTEGER NOT NULL,failure_reason INTEGER NOT NULL,percent_downloaded REAL NOT NULL,bytes_downloaded INTEGER NOT NULL)";
    private static final String TRUE = "1";
    private static final String WHERE_ID_EQUALS = "id = ?";
    private static final String WHERE_STATE_TERMINAL = getStateQuery(3, 4);
    private final DatabaseProvider databaseProvider;
    private final String name;
    private final String tableName;
    private boolean initialized;

    public DefaultDownloadIndex(DatabaseProvider databaseProvider2) {
        this(databaseProvider2, "");
    }

    public DefaultDownloadIndex(DatabaseProvider databaseProvider2, String name2) {
        this.name = name2;
        this.databaseProvider = databaseProvider2;
        String valueOf = String.valueOf(TABLE_PREFIX);
        String valueOf2 = String.valueOf(name2);
        this.tableName = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private static String getStateQuery(int... states) {
        if (states.length == 0) {
            return "1";
        }
        StringBuilder selectionBuilder = new StringBuilder();
        selectionBuilder.append("state");
        selectionBuilder.append(" IN (");
        for (int i = 0; i < states.length; i++) {
            if (i > 0) {
                selectionBuilder.append(',');
            }
            selectionBuilder.append(states[i]);
        }
        selectionBuilder.append(')');
        return selectionBuilder.toString();
    }

    /* access modifiers changed from: private */
    public static Download getDownloadForCurrentRow(Cursor cursor) {
        DownloadRequest request = new DownloadRequest(cursor.getString(0), cursor.getString(1), Uri.parse(cursor.getString(2)), decodeStreamKeys(cursor.getString(3)), cursor.getString(4), cursor.getBlob(5));
        DownloadProgress downloadProgress = new DownloadProgress();
        downloadProgress.bytesDownloaded = cursor.getLong(13);
        downloadProgress.percentDownloaded = cursor.getFloat(12);
        return new Download(request, cursor.getInt(6), cursor.getLong(7), cursor.getLong(8), cursor.getLong(9), cursor.getInt(10), cursor.getInt(11), downloadProgress);
    }

    private static String encodeStreamKeys(List<StreamKey> streamKeys) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < streamKeys.size(); i++) {
            StreamKey streamKey = streamKeys.get(i);
            stringBuilder.append(streamKey.periodIndex);
            stringBuilder.append('.');
            stringBuilder.append(streamKey.groupIndex);
            stringBuilder.append('.');
            stringBuilder.append(streamKey.trackIndex);
            stringBuilder.append(',');
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    private static List<StreamKey> decodeStreamKeys(String encodedStreamKeys) {
        ArrayList<StreamKey> streamKeys = new ArrayList<>();
        if (encodedStreamKeys.isEmpty()) {
            return streamKeys;
        }
        for (String streamKeysString : Util.split(encodedStreamKeys, ",")) {
            String[] indices = Util.split(streamKeysString, "\\.");
            Assertions.checkState(indices.length == 3);
            streamKeys.add(new StreamKey(Integer.parseInt(indices[0]), Integer.parseInt(indices[1]), Integer.parseInt(indices[2])));
        }
        return streamKeys;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0027, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0028, code lost:
        if (r0 != null) goto L_0x002a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0032, code lost:
        throw r2;
     */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.offline.Download getDownload(java.lang.String r5) throws com.google.android.exoplayer2.database.DatabaseIOException {
        /*
            r4 = this;
            r4.ensureInitialized()
            java.lang.String r0 = "id = ?"
            r1 = 1
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ SQLiteException -> 0x0033 }
            r2 = 0
            r1[r2] = r5     // Catch:{ SQLiteException -> 0x0033 }
            android.database.Cursor r0 = r4.getCursor(r0, r1)     // Catch:{ SQLiteException -> 0x0033 }
            int r1 = r0.getCount()     // Catch:{ all -> 0x0025 }
            if (r1 != 0) goto L_0x001a
            r1 = 0
            r0.close()     // Catch:{ SQLiteException -> 0x0033 }
            return r1
        L_0x001a:
            r0.moveToNext()     // Catch:{ all -> 0x0025 }
            com.google.android.exoplayer2.offline.Download r1 = getDownloadForCurrentRow(r0)     // Catch:{ all -> 0x0025 }
            r0.close()     // Catch:{ SQLiteException -> 0x0033 }
            return r1
        L_0x0025:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0027 }
        L_0x0027:
            r2 = move-exception
            if (r0 == 0) goto L_0x0032
            r0.close()     // Catch:{ all -> 0x002e }
            goto L_0x0032
        L_0x002e:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r1, r3)     // Catch:{ SQLiteException -> 0x0033 }
        L_0x0032:
            throw r2     // Catch:{ SQLiteException -> 0x0033 }
        L_0x0033:
            r0 = move-exception
            com.google.android.exoplayer2.database.DatabaseIOException r1 = new com.google.android.exoplayer2.database.DatabaseIOException
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.DefaultDownloadIndex.getDownload(java.lang.String):com.google.android.exoplayer2.offline.Download");
    }

    public DownloadCursor getDownloads(int... states) throws DatabaseIOException {
        ensureInitialized();
        return new DownloadCursorImpl(getCursor(getStateQuery(states), null));
    }

    public void putDownload(Download download) throws DatabaseIOException {
        ensureInitialized();
        ContentValues values = new ContentValues();
        values.put("id", download.request.f89id);
        values.put("title", download.request.type);
        values.put("uri", download.request.uri.toString());
        values.put(COLUMN_STREAM_KEYS, encodeStreamKeys(download.request.streamKeys));
        values.put(COLUMN_CUSTOM_CACHE_KEY, download.request.customCacheKey);
        values.put("data", download.request.data);
        values.put("state", Integer.valueOf(download.state));
        values.put(COLUMN_START_TIME_MS, Long.valueOf(download.startTimeMs));
        values.put(COLUMN_UPDATE_TIME_MS, Long.valueOf(download.updateTimeMs));
        values.put(COLUMN_CONTENT_LENGTH, Long.valueOf(download.contentLength));
        values.put(COLUMN_STOP_REASON, Integer.valueOf(download.stopReason));
        values.put(COLUMN_FAILURE_REASON, Integer.valueOf(download.failureReason));
        values.put(COLUMN_PERCENT_DOWNLOADED, Float.valueOf(download.getPercentDownloaded()));
        values.put(COLUMN_BYTES_DOWNLOADED, Long.valueOf(download.getBytesDownloaded()));
        try {
            this.databaseProvider.getWritableDatabase().replaceOrThrow(this.tableName, null, values);
        } catch (SQLiteException e) {
            throw new DatabaseIOException(e);
        }
    }

    public void removeDownload(String id) throws DatabaseIOException {
        ensureInitialized();
        try {
            this.databaseProvider.getWritableDatabase().delete(this.tableName, WHERE_ID_EQUALS, new String[]{id});
        } catch (SQLiteException e) {
            throw new DatabaseIOException(e);
        }
    }

    public void setStopReason(int stopReason) throws DatabaseIOException {
        ensureInitialized();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_STOP_REASON, Integer.valueOf(stopReason));
            this.databaseProvider.getWritableDatabase().update(this.tableName, values, WHERE_STATE_TERMINAL, null);
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    public void setStopReason(String id, int stopReason) throws DatabaseIOException {
        ensureInitialized();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_STOP_REASON, Integer.valueOf(stopReason));
            SQLiteDatabase writableDatabase = this.databaseProvider.getWritableDatabase();
            String str = this.tableName;
            String str2 = WHERE_STATE_TERMINAL;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 11);
            sb.append(str2);
            sb.append(" AND ");
            sb.append(WHERE_ID_EQUALS);
            writableDatabase.update(str, values, sb.toString(), new String[]{id});
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    private void ensureInitialized() throws DatabaseIOException {
        SQLiteDatabase writableDatabase;
        if (!this.initialized) {
            try {
                if (VersionTable.getVersion(this.databaseProvider.getReadableDatabase(), 0, this.name) != 2) {
                    writableDatabase = this.databaseProvider.getWritableDatabase();
                    writableDatabase.beginTransaction();
                    VersionTable.setVersion(writableDatabase, 0, this.name, 2);
                    String valueOf = String.valueOf(this.tableName);
                    writableDatabase.execSQL(valueOf.length() != 0 ? "DROP TABLE IF EXISTS ".concat(valueOf) : new String("DROP TABLE IF EXISTS "));
                    String str = this.tableName;
                    StringBuilder sb = new StringBuilder(String.valueOf(str).length() + ClientAnalytics.LogRequest.LogSource.KATNIP_IOS_PRIMES_VALUE);
                    sb.append("CREATE TABLE ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(TABLE_SCHEMA);
                    writableDatabase.execSQL(sb.toString());
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                }
                this.initialized = true;
            } catch (SQLException e) {
                throw new DatabaseIOException(e);
            } catch (Throwable th) {
                writableDatabase.endTransaction();
                throw th;
            }
        }
    }

    private Cursor getCursor(String selection, @Nullable String[] selectionArgs) throws DatabaseIOException {
        try {
            return this.databaseProvider.getReadableDatabase().query(this.tableName, COLUMNS, selection, selectionArgs, null, null, "start_time_ms ASC");
        } catch (SQLiteException e) {
            throw new DatabaseIOException(e);
        }
    }

    private static final class DownloadCursorImpl implements DownloadCursor {
        private final Cursor cursor;

        private DownloadCursorImpl(Cursor cursor2) {
            this.cursor = cursor2;
        }

        public boolean isAfterLast() {
            return DownloadCursor$$CC.isAfterLast$$dflt$$(this);
        }

        public boolean isBeforeFirst() {
            return DownloadCursor$$CC.isBeforeFirst$$dflt$$(this);
        }

        public boolean isFirst() {
            return DownloadCursor$$CC.isFirst$$dflt$$(this);
        }

        public boolean isLast() {
            return DownloadCursor$$CC.isLast$$dflt$$(this);
        }

        public boolean moveToFirst() {
            return DownloadCursor$$CC.moveToFirst$$dflt$$(this);
        }

        public boolean moveToLast() {
            return DownloadCursor$$CC.moveToLast$$dflt$$(this);
        }

        public boolean moveToNext() {
            return DownloadCursor$$CC.moveToNext$$dflt$$(this);
        }

        public boolean moveToPrevious() {
            return DownloadCursor$$CC.moveToPrevious$$dflt$$(this);
        }

        public Download getDownload() {
            return DefaultDownloadIndex.getDownloadForCurrentRow(this.cursor);
        }

        public int getCount() {
            return this.cursor.getCount();
        }

        public int getPosition() {
            return this.cursor.getPosition();
        }

        public boolean moveToPosition(int position) {
            return this.cursor.moveToPosition(position);
        }

        public void close() {
            this.cursor.close();
        }

        public boolean isClosed() {
            return this.cursor.isClosed();
        }
    }
}
