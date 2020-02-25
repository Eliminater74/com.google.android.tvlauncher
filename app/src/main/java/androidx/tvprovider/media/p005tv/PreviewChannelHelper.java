package androidx.tvprovider.media.p005tv;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.WorkerThread;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@WorkerThread
/* renamed from: androidx.tvprovider.media.tv.PreviewChannelHelper */
public class PreviewChannelHelper {
    private static final int DEFAULT_READ_TIMEOUT_MILLIS = 10000;
    private static final int DEFAULT_URL_CONNNECTION_TIMEOUT_MILLIS = 3000;
    private static final int INVALID_CONTENT_ID = -1;
    private static final String TAG = "PreviewChannelHelper";
    private final Context mContext;
    private final int mUrlConnectionTimeoutMillis;
    private final int mUrlReadTimeoutMillis;

    public PreviewChannelHelper(Context context) {
        this(context, 3000, 10000);
    }

    public PreviewChannelHelper(Context context, int urlConnectionTimeoutMillis, int urlReadTimeoutMillis) {
        this.mContext = context;
        this.mUrlConnectionTimeoutMillis = urlConnectionTimeoutMillis;
        this.mUrlReadTimeoutMillis = urlReadTimeoutMillis;
    }

    public long publishChannel(@NonNull PreviewChannel channel) throws IOException {
        try {
            Uri channelUri = this.mContext.getContentResolver().insert(TvContractCompat.Channels.CONTENT_URI, channel.toContentValues());
            if (channelUri == null || channelUri.equals(Uri.EMPTY)) {
                throw new NullPointerException("Channel insertion failed");
            }
            long channelId = ContentUris.parseId(channelUri);
            if (addChannelLogo(channelId, channel)) {
                return channelId;
            }
            deletePreviewChannel(channelId);
            throw new IOException("Failed to add logo, so channel (ID=" + channelId + ") was not created");
        } catch (SecurityException e) {
            Log.e(TAG, "Your app's ability to insert data into the TvProvider may have been revoked.", e);
            return -1;
        }
    }

    public long publishDefaultChannel(@NonNull PreviewChannel channel) throws IOException {
        long channelId = publishChannel(channel);
        TvContractCompat.requestChannelBrowsable(this.mContext, channelId);
        return channelId;
    }

    public List<PreviewChannel> getAllChannels() {
        Cursor cursor = this.mContext.getContentResolver().query(TvContractCompat.Channels.CONTENT_URI, PreviewChannel.Columns.PROJECTION, null, null, null);
        List<PreviewChannel> channels = new ArrayList<>();
        if (cursor == null || !cursor.moveToFirst()) {
            return channels;
        }
        do {
            channels.add(PreviewChannel.fromCursor(cursor));
        } while (cursor.moveToNext());
        return channels;
    }

    public PreviewChannel getPreviewChannel(long channelId) {
        Cursor cursor = this.mContext.getContentResolver().query(TvContractCompat.buildChannelUri(channelId), PreviewChannel.Columns.PROJECTION, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        return PreviewChannel.fromCursor(cursor);
    }

    public void updatePreviewChannel(long channelId, @NonNull PreviewChannel update) throws IOException {
        PreviewChannel curr = getPreviewChannel(channelId);
        if (curr != null && curr.hasAnyUpdatedValues(update)) {
            updatePreviewChannelInternal(channelId, update);
        }
        if (update.isLogoChanged() && !addChannelLogo(channelId, update)) {
            throw new IOException("Fail to update channel (ID=" + channelId + ") logo.");
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void updatePreviewChannelInternal(long channelId, @NonNull PreviewChannel upgrade) {
        this.mContext.getContentResolver().update(TvContractCompat.buildChannelUri(channelId), upgrade.toContentValues(), null, null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0038, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
        if (r3 != null) goto L_0x003b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0043, code lost:
        throw r5;
     */
    @android.support.annotation.WorkerThread
    @android.annotation.SuppressLint({"WrongThread"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean addChannelLogo(long r8, @android.support.annotation.NonNull androidx.tvprovider.media.p005tv.PreviewChannel r10) {
        /*
            r7 = this;
            r0 = 0
            boolean r1 = r10.isLogoChanged()
            if (r1 != 0) goto L_0x0008
            return r0
        L_0x0008:
            android.content.Context r1 = r7.mContext
            android.graphics.Bitmap r1 = r10.getLogo(r1)
            if (r1 != 0) goto L_0x0018
            android.net.Uri r2 = r10.getLogoUri()
            android.graphics.Bitmap r1 = r7.getLogoFromUri(r2)
        L_0x0018:
            android.net.Uri r2 = androidx.tvprovider.media.p005tv.TvContractCompat.buildChannelLogoUri(r8)
            android.content.Context r3 = r7.mContext     // Catch:{ SQLiteException -> 0x0048, IOException -> 0x0046, NullPointerException -> 0x0044 }
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch:{ SQLiteException -> 0x0048, IOException -> 0x0046, NullPointerException -> 0x0044 }
            java.io.OutputStream r3 = r3.openOutputStream(r2)     // Catch:{ SQLiteException -> 0x0048, IOException -> 0x0046, NullPointerException -> 0x0044 }
            android.graphics.Bitmap$CompressFormat r4 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ all -> 0x0036 }
            r5 = 100
            boolean r4 = r1.compress(r4, r5, r3)     // Catch:{ all -> 0x0036 }
            r0 = r4
            r3.flush()     // Catch:{ all -> 0x0036 }
            r3.close()     // Catch:{ SQLiteException -> 0x0048, IOException -> 0x0046, NullPointerException -> 0x0044 }
            goto L_0x0064
        L_0x0036:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x0038 }
        L_0x0038:
            r5 = move-exception
            if (r3 == 0) goto L_0x0043
            r3.close()     // Catch:{ all -> 0x003f }
            goto L_0x0043
        L_0x003f:
            r6 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r4, r6)     // Catch:{ SQLiteException -> 0x0048, IOException -> 0x0046, NullPointerException -> 0x0044 }
        L_0x0043:
            throw r5     // Catch:{ SQLiteException -> 0x0048, IOException -> 0x0046, NullPointerException -> 0x0044 }
        L_0x0044:
            r3 = move-exception
            goto L_0x0049
        L_0x0046:
            r3 = move-exception
            goto L_0x0049
        L_0x0048:
            r3 = move-exception
        L_0x0049:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Failed to add logo to the published channel (ID= "
            r4.append(r5)
            r4.append(r8)
            java.lang.String r5 = ")"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "PreviewChannelHelper"
            android.util.Log.i(r5, r4, r3)
        L_0x0064:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.tvprovider.media.p005tv.PreviewChannelHelper.addChannelLogo(long, androidx.tvprovider.media.tv.PreviewChannel):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x003b A[SYNTHETIC, Splitter:B:13:0x003b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.Bitmap getLogoFromUri(@android.support.annotation.NonNull android.net.Uri r8) {
        /*
            r7 = this;
            android.net.Uri r0 = r8.normalizeScheme()
            java.lang.String r0 = r0.getScheme()
            r1 = 0
            r2 = 0
            java.lang.String r3 = "android.resource"
            boolean r3 = r3.equals(r0)     // Catch:{ IOException -> 0x0043 }
            if (r3 != 0) goto L_0x0029
            java.lang.String r3 = "file"
            boolean r3 = r3.equals(r0)     // Catch:{ IOException -> 0x0043 }
            if (r3 != 0) goto L_0x0029
            java.lang.String r3 = "content"
            boolean r3 = r3.equals(r0)     // Catch:{ IOException -> 0x0043 }
            if (r3 == 0) goto L_0x0023
            goto L_0x0029
        L_0x0023:
            android.graphics.Bitmap r3 = r7.downloadBitmap(r8)     // Catch:{ IOException -> 0x0043 }
            r2 = r3
            goto L_0x0039
        L_0x0029:
            android.content.Context r3 = r7.mContext     // Catch:{ IOException -> 0x0043 }
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch:{ IOException -> 0x0043 }
            java.io.InputStream r3 = r3.openInputStream(r8)     // Catch:{ IOException -> 0x0043 }
            r1 = r3
            android.graphics.Bitmap r3 = android.graphics.BitmapFactory.decodeStream(r1)     // Catch:{ IOException -> 0x0043 }
            r2 = r3
        L_0x0039:
            if (r1 == 0) goto L_0x0061
            r1.close()     // Catch:{ IOException -> 0x003f }
        L_0x003e:
            goto L_0x0061
        L_0x003f:
            r3 = move-exception
            goto L_0x003e
        L_0x0041:
            r3 = move-exception
            goto L_0x0062
        L_0x0043:
            r3 = move-exception
            java.lang.String r4 = "PreviewChannelHelper"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0041 }
            r5.<init>()     // Catch:{ all -> 0x0041 }
            java.lang.String r6 = "Failed to get logo from the URI: "
            r5.append(r6)     // Catch:{ all -> 0x0041 }
            r5.append(r8)     // Catch:{ all -> 0x0041 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0041 }
            android.util.Log.e(r4, r5, r3)     // Catch:{ all -> 0x0041 }
            if (r1 == 0) goto L_0x0061
            r1.close()     // Catch:{ IOException -> 0x003f }
            goto L_0x003e
        L_0x0061:
            return r2
        L_0x0062:
            if (r1 == 0) goto L_0x0069
            r1.close()     // Catch:{ IOException -> 0x0068 }
            goto L_0x0069
        L_0x0068:
            r4 = move-exception
        L_0x0069:
            throw r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.tvprovider.media.p005tv.PreviewChannelHelper.getLogoFromUri(android.net.Uri):android.graphics.Bitmap");
    }

    /* access modifiers changed from: protected */
    public Bitmap downloadBitmap(@NonNull Uri logoUri) throws IOException {
        URLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = new URL(logoUri.toString()).openConnection();
            urlConnection.setConnectTimeout(this.mUrlConnectionTimeoutMillis);
            urlConnection.setReadTimeout(this.mUrlReadTimeoutMillis);
            inputStream = urlConnection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (urlConnection instanceof HttpURLConnection) {
                urlConnection.disconnect();
            }
        }
    }

    public void deletePreviewChannel(long channelId) {
        this.mContext.getContentResolver().delete(TvContractCompat.buildChannelUri(channelId), null, null);
    }

    public long publishPreviewProgram(@NonNull PreviewProgram program) {
        try {
            return ContentUris.parseId(this.mContext.getContentResolver().insert(TvContractCompat.PreviewPrograms.CONTENT_URI, program.toContentValues()));
        } catch (SecurityException e) {
            Log.e(TAG, "Your app's ability to insert data into the TvProvider may have been revoked.", e);
            return -1;
        }
    }

    public PreviewProgram getPreviewProgram(long programId) {
        Cursor cursor = this.mContext.getContentResolver().query(TvContractCompat.buildPreviewProgramUri(programId), null, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        return PreviewProgram.fromCursor(cursor);
    }

    public void updatePreviewProgram(long programId, @NonNull PreviewProgram update) {
        PreviewProgram curr = getPreviewProgram(programId);
        if (curr != null && curr.hasAnyUpdatedValues(update)) {
            updatePreviewProgramInternal(programId, update);
        }
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void updatePreviewProgramInternal(long programId, @NonNull PreviewProgram upgrade) {
        this.mContext.getContentResolver().update(TvContractCompat.buildPreviewProgramUri(programId), upgrade.toContentValues(), null, null);
    }

    public void deletePreviewProgram(long programId) {
        this.mContext.getContentResolver().delete(TvContractCompat.buildPreviewProgramUri(programId), null, null);
    }

    public long publishWatchNextProgram(@NonNull WatchNextProgram program) {
        try {
            return ContentUris.parseId(this.mContext.getContentResolver().insert(TvContractCompat.WatchNextPrograms.CONTENT_URI, program.toContentValues()));
        } catch (SecurityException e) {
            Log.e(TAG, "Your app's ability to insert data into the TvProvider may have been revoked.", e);
            return -1;
        }
    }

    public WatchNextProgram getWatchNextProgram(long programId) {
        Cursor cursor = this.mContext.getContentResolver().query(TvContractCompat.buildWatchNextProgramUri(programId), null, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        return WatchNextProgram.fromCursor(cursor);
    }

    public void updateWatchNextProgram(@NonNull WatchNextProgram upgrade, long programId) {
        WatchNextProgram curr = getWatchNextProgram(programId);
        if (curr != null && curr.hasAnyUpdatedValues(upgrade)) {
            updateWatchNextProgram(programId, upgrade);
        }
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void updateWatchNextProgram(long programId, @NonNull WatchNextProgram upgrade) {
        this.mContext.getContentResolver().update(TvContractCompat.buildWatchNextProgramUri(programId), upgrade.toContentValues(), null, null);
    }
}
