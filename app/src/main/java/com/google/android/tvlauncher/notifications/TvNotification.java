package com.google.android.tvlauncher.notifications;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;

public class TvNotification {
    public static final int COLUMN_INDEX_BIG_PICTURE = 11;
    public static final int COLUMN_INDEX_CHANNEL = 7;
    public static final int COLUMN_INDEX_CONTENT_BUTTON_LABEL = 12;
    public static final int COLUMN_INDEX_DISMISSIBLE = 4;
    public static final int COLUMN_INDEX_DISMISS_BUTTON_LABEL = 13;
    public static final int COLUMN_INDEX_HAS_CONTENT_INTENT = 10;
    public static final int COLUMN_INDEX_KEY = 0;
    public static final int COLUMN_INDEX_NOTIF_TEXT = 3;
    public static final int COLUMN_INDEX_NOTIF_TITLE = 2;
    public static final int COLUMN_INDEX_ONGOING = 5;
    public static final int COLUMN_INDEX_PACKAGE_NAME = 1;
    public static final int COLUMN_INDEX_PROGRESS = 8;
    public static final int COLUMN_INDEX_PROGRESS_MAX = 9;
    public static final int COLUMN_INDEX_SMALL_ICON = 6;
    public static final int COLUMN_INDEX_TAG = 14;
    public static final String[] PROJECTION = {"sbn_key", "package_name", "title", "text", NotificationsContract.COLUMN_DISMISSIBLE, NotificationsContract.COLUMN_ONGOING, NotificationsContract.COLUMN_SMALL_ICON, "channel", "progress", NotificationsContract.COLUMN_PROGRESS_MAX, NotificationsContract.COLUMN_HAS_CONTENT_INTENT, NotificationsContract.COLUMN_BIG_PICTURE, NotificationsContract.COLUMN_CONTENT_BUTTON_LABEL, NotificationsContract.COLUMN_DISMISS_BUTTON_LABEL, NotificationsContract.COLUMN_TAG};
    private Bitmap mBigPicture;
    private int mChannel;
    private String mContentButtonLabel;
    private String mDismissButtonLabel;
    private boolean mDismissible;
    private boolean mHasContentIntent;
    private boolean mIsOngoing;
    private String mNotificationKey;
    private String mPackageName;
    private int mProgress;
    private int mProgressMax;
    private Icon mSmallIcon;
    private String mTag;
    private String mText;
    private String mTitle;

    public TvNotification(String key, String packageName, String title, String text, boolean dismissible, boolean ongoing, Icon smallIcon, int channel, int progress, int progressMax, boolean hasContentIntent, Bitmap bigPicture, String contentButtonLabel, String dismissButtonLabel, String tag) {
        this.mNotificationKey = key;
        this.mPackageName = packageName;
        this.mTitle = title;
        this.mText = text;
        this.mDismissible = dismissible;
        this.mIsOngoing = ongoing;
        this.mSmallIcon = smallIcon;
        this.mChannel = channel;
        this.mProgress = progress;
        this.mProgressMax = progressMax;
        this.mHasContentIntent = hasContentIntent;
        this.mBigPicture = bigPicture;
        this.mContentButtonLabel = contentButtonLabel;
        this.mDismissButtonLabel = dismissButtonLabel;
        this.mTag = tag;
    }

    public String getNotificationKey() {
        return this.mNotificationKey;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getText() {
        return this.mText;
    }

    public boolean isDismissible() {
        return this.mDismissible;
    }

    public boolean isOngoing() {
        return this.mIsOngoing;
    }

    public Icon getSmallIcon() {
        return this.mSmallIcon;
    }

    public int getChannel() {
        return this.mChannel;
    }

    public int getProgress() {
        return this.mProgress;
    }

    public int getProgressMax() {
        return this.mProgressMax;
    }

    public boolean hasContentIntent() {
        return this.mHasContentIntent;
    }

    public Bitmap getBigPicture() {
        return this.mBigPicture;
    }

    public String getContentButtonLabel() {
        return this.mContentButtonLabel;
    }

    public String getDismissButtonLabel() {
        return this.mDismissButtonLabel;
    }

    public String getTag() {
        return this.mTag;
    }

    /* JADX INFO: Multiple debug info for r1v1 java.lang.String: [D('index' int), D('key' java.lang.String)] */
    /* JADX INFO: Multiple debug info for r2v1 java.lang.String: [D('index' int), D('packageName' java.lang.String)] */
    public static TvNotification fromCursor(Cursor cursor) {
        Cursor cursor2 = cursor;
        int index = 0 + 1;
        String key = cursor2.getString(0);
        int index2 = index + 1;
        String packageName = cursor2.getString(index);
        int index3 = index2 + 1;
        String title = cursor2.getString(index2);
        int index4 = index3 + 1;
        String text = cursor2.getString(index3);
        int index5 = index4 + 1;
        boolean dismissible = cursor2.getInt(index4) != 0;
        int index6 = index5 + 1;
        boolean ongoing = cursor2.getInt(index5) != 0;
        int index7 = index6 + 1;
        Icon smallIcon = getIconFromBytes(cursor2.getBlob(index6));
        int index8 = index7 + 1;
        int channel = cursor2.getInt(index7);
        int index9 = index8 + 1;
        int progress = cursor2.getInt(index8);
        int index10 = index9 + 1;
        int progressMax = cursor2.getInt(index9);
        int index11 = index10 + 1;
        boolean hasContentIntent = cursor2.getInt(index10) != 0;
        int index12 = index11 + 1;
        Bitmap bigPicture = getBitmapFromBytes(cursor2.getBlob(index11));
        int index13 = index12 + 1;
        return new TvNotification(key, packageName, title, text, dismissible, ongoing, smallIcon, channel, progress, progressMax, hasContentIntent, bigPicture, cursor2.getString(index12), cursor2.getString(index13), cursor2.getString(index13 + 1));
    }

    private static Bitmap getBitmapFromBytes(byte[] blob) {
        if (blob != null) {
            return BitmapFactory.decodeByteArray(blob, 0, blob.length);
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.graphics.drawable.Icon getIconFromBytes(byte[] r4) {
        /*
            android.os.Parcel r0 = android.os.Parcel.obtain()
            r1 = 0
            if (r4 == 0) goto L_0x001c
            int r2 = r4.length
            r3 = 0
            r0.unmarshall(r4, r3, r2)
            r0.setDataPosition(r3)
            java.lang.Class<android.graphics.drawable.Icon> r2 = android.graphics.drawable.Icon.class
            java.lang.ClassLoader r2 = r2.getClassLoader()
            android.os.Parcelable r2 = r0.readParcelable(r2)
            r1 = r2
            android.graphics.drawable.Icon r1 = (android.graphics.drawable.Icon) r1
        L_0x001c:
            r0.recycle()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.notifications.TvNotification.getIconFromBytes(byte[]):android.graphics.drawable.Icon");
    }

    /* JADX INFO: Multiple debug info for r1v1 java.lang.String: [D('index' int), D('key' java.lang.String)] */
    /* JADX INFO: Multiple debug info for r2v1 java.lang.String: [D('index' int), D('packageName' java.lang.String)] */
    /* JADX INFO: Multiple debug info for r3v1 java.lang.String: [D('title' java.lang.String), D('index' int)] */
    /* JADX INFO: Multiple debug info for r4v1 java.lang.String: [D('index' int), D('text' java.lang.String)] */
    /* JADX INFO: Multiple debug info for r5v1 int: [D('index' int), D('dismissible' int)] */
    /* JADX INFO: Multiple debug info for r6v1 int: [D('index' int), D('ongoing' int)] */
    /* JADX INFO: Multiple debug info for r7v1 byte[]: [D('smallIconData' byte[]), D('index' int)] */
    /* JADX INFO: Multiple debug info for r8v1 int: [D('index' int), D('channel' int)] */
    /* JADX INFO: Multiple debug info for r9v1 int: [D('progress' int), D('index' int)] */
    /* JADX INFO: Multiple debug info for r10v1 int: [D('index' int), D('progressMax' int)] */
    /* JADX INFO: Multiple debug info for r11v1 int: [D('hasContentIntent' int), D('index' int)] */
    /* JADX INFO: Multiple debug info for r12v1 byte[]: [D('index' int), D('bigPictureData' byte[])] */
    /* JADX INFO: Multiple debug info for r13v1 java.lang.String: [D('index' int), D('contentButtonLabel' java.lang.String)] */
    /* JADX INFO: Multiple debug info for r14v1 java.lang.String: [D('index' int), D('dismissButtonLabel' java.lang.String)] */
    public static Object[] getRowFromCursor(Cursor cursor) {
        Cursor cursor2 = cursor;
        if (cursor2 == null) {
            return null;
        }
        int index = 0 + 1;
        String key = cursor2.getString(0);
        int index2 = index + 1;
        String packageName = cursor2.getString(index);
        int index3 = index2 + 1;
        String title = cursor2.getString(index2);
        int index4 = index3 + 1;
        String text = cursor2.getString(index3);
        int index5 = index4 + 1;
        int dismissible = cursor2.getInt(index4);
        int index6 = index5 + 1;
        int ongoing = cursor2.getInt(index5);
        int index7 = index6 + 1;
        byte[] smallIconData = cursor2.getBlob(index6);
        int index8 = index7 + 1;
        int channel = cursor2.getInt(index7);
        int index9 = index8 + 1;
        int index10 = cursor2.getInt(index8);
        int index11 = index9 + 1;
        int progressMax = cursor2.getInt(index9);
        int index12 = index11 + 1;
        int index13 = cursor2.getInt(index11);
        int index14 = index12 + 1;
        int index15 = index14 + 1;
        int index16 = index15 + 1;
        return new Object[]{key, packageName, title, text, Integer.valueOf(dismissible), Integer.valueOf(ongoing), smallIconData, Integer.valueOf(channel), Integer.valueOf(index10), Integer.valueOf(progressMax), Integer.valueOf(index13), cursor2.getBlob(index12), cursor2.getString(index14), cursor2.getString(index15), cursor2.getString(index16)};
    }
}
