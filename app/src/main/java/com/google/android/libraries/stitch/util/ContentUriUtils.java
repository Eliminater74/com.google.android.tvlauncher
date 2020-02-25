package com.google.android.libraries.stitch.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

public final class ContentUriUtils {
    private static final String ALL_MIME_TYPE = "*/*";
    private static final String GENERIC_IMAGE_MIME_TYPE = "image/*";
    private static final String GENERIC_VIDEO_MIME_TYPE = "video/*";
    private static final String GIF_MIME_TYPE = "image/gif";
    private static final String[] MEDIA_STORE_DATA_PROJECTION = {"_data"};
    private static final String PATH_MATCH_VIDEO = "/video/";
    private static final String TAG = "ContentUriUtils";
    private static final String WEBP_MIME_TYPE = "image/webp";

    private ContentUriUtils() {
    }

    public static boolean isFileUri(Uri uri) {
        return uri != null && "file".equals(uri.getScheme());
    }

    public static boolean doesFileExist(Context context, Uri uri) {
        return getFilePath(context.getContentResolver(), uri) != null;
    }

    public static boolean isMediaStoreUri(Uri uri) {
        return uri != null && "content".equals(uri.getScheme()) && "media".equals(uri.getAuthority());
    }

    public static boolean isMediaStoreVideo(Uri uri) {
        return isMediaStoreUri(uri) && uri.toString().contains(PATH_MATCH_VIDEO);
    }

    public static boolean isImageGifMimeType(String mimeType) {
        return GIF_MIME_TYPE.equals(mimeType);
    }

    public static boolean isImageWebPMimeType(String mimeType) {
        return WEBP_MIME_TYPE.equals(mimeType);
    }

    public static boolean isImageMimeType(String mimeType) {
        return mimeType != null && mimeType.startsWith("image/");
    }

    public static boolean isVideoMimeType(String mimeType) {
        return mimeType != null && mimeType.startsWith("video/");
    }

    public static String getFilePath(ContentResolver cr, Uri uri) {
        Cursor cursor = cr.query(uri, new String[]{"_data"}, null, null, null);
        if (cursor == null) {
            if (Log.isLoggable(TAG, 5)) {
                String valueOf = String.valueOf(uri);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 48);
                sb.append("getFilePath: query returned null cursor for uri=");
                sb.append(valueOf);
                Log.w(TAG, sb.toString());
            }
            return null;
        }
        try {
            if (!cursor.moveToFirst()) {
                if (Log.isLoggable(TAG, 5)) {
                    String valueOf2 = String.valueOf(uri);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 49);
                    sb2.append("getFilePath: query returned empty cursor for uri=");
                    sb2.append(valueOf2);
                    Log.w(TAG, sb2.toString());
                }
                return null;
            }
            String path = cursor.getString(0);
            if (TextUtils.isEmpty(path)) {
                if (Log.isLoggable(TAG, 5)) {
                    String valueOf3 = String.valueOf(uri);
                    StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 49);
                    sb3.append("getFilePath: MediaColumns.DATA was empty for uri=");
                    sb3.append(valueOf3);
                    Log.w(TAG, sb3.toString());
                }
                cursor.close();
                return null;
            }
            cursor.close();
            return path;
        } finally {
            cursor.close();
        }
    }

    public static String getBucketName(ContentResolver cr, Uri uri, String cameraSentinel) {
        String data;
        Cursor cursor = cr.query(uri, new String[]{"bucket_display_name", "_data"}, null, null, null);
        if (cursor == null || !cursor.moveToNext()) {
            if (Log.isLoggable(TAG, 5)) {
                String valueOf = String.valueOf(uri);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 49);
                sb.append("getBucketName: query returned no results for uri=");
                sb.append(valueOf);
                Log.w(TAG, sb.toString());
            }
            return null;
        }
        try {
            String bucketName = cursor.getString(0);
            if (!(cameraSentinel == null || (data = cursor.getString(1)) == null || !data.contains("/DCIM/"))) {
                bucketName = cameraSentinel;
            }
            if (TextUtils.isEmpty(bucketName)) {
                if (Log.isLoggable(TAG, 5)) {
                    String valueOf2 = String.valueOf(uri);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 66);
                    sb2.append("getBucketName: ImageColumns.BUCKET_DISPLAY_NAME was empty for uri=");
                    sb2.append(valueOf2);
                    Log.w(TAG, sb2.toString());
                }
                return null;
            }
            cursor.close();
            return bucketName;
        } finally {
            cursor.close();
        }
    }

    public static String getMimeType(ContentResolver resolver, Uri uri) {
        try {
            String mimeType = safeGetMimeType(resolver, uri);
            if (TextUtils.isEmpty(mimeType)) {
                mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(uri.toString()));
            }
            if (!ALL_MIME_TYPE.equals(mimeType) || !isMediaStoreUri(uri)) {
                return mimeType;
            }
            return getMediaStoreMimeType(resolver, uri);
        } catch (Exception e) {
            if (!Log.isLoggable(TAG, 5)) {
                return null;
            }
            String valueOf = String.valueOf(uri);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 27);
            sb.append("getMimeType failed for uri=");
            sb.append(valueOf);
            Log.w(TAG, sb.toString(), e);
            return null;
        }
    }

    private static String getMediaStoreMimeType(ContentResolver resolver, Uri uri) {
        String mimeType = null;
        Cursor data = resolver.query(uri, MEDIA_STORE_DATA_PROJECTION, null, null, null);
        if (data != null) {
            try {
                if (data.moveToFirst()) {
                    mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(data.getString(0)));
                }
            } catch (Throwable th) {
                data.close();
                throw th;
            }
        }
        if (data != null) {
            data.close();
        }
        if (!TextUtils.isEmpty(mimeType)) {
            return mimeType;
        }
        return isMediaStoreVideo(uri) ? GENERIC_VIDEO_MIME_TYPE : GENERIC_IMAGE_MIME_TYPE;
    }

    private static String safeGetMimeType(ContentResolver resolver, Uri uri) {
        try {
            return resolver.getType(uri);
        } catch (Exception e) {
            if (!Log.isLoggable(TAG, 5)) {
                return null;
            }
            String valueOf = String.valueOf(uri);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 31);
            sb.append("safeGetMimeType failed for uri=");
            sb.append(valueOf);
            Log.w(TAG, sb.toString(), e);
            return null;
        }
    }
}
