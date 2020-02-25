package com.google.android.gsf;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SubscribedFeeds {
    private static final String SELECT_FEEDS_BY_ID = "_id=?";
    private static final String SELECT_SUBSCRIBED_FEEDS_BY_ACCOUNT_AND_AUTHORITY = "_sync_account=? AND _sync_account_type=? AND authority=?";

    private SubscribedFeeds() {
    }

    public static boolean manageSubscriptions(ContentResolver contentResolver, Account account, String authority, String serviceName, Collection<String> expectedFeedUrls) {
        Account account2 = account;
        String str = authority;
        HashMap hashMap = new HashMap();
        Cursor c = contentResolver.query(Feeds.CONTENT_URI, new String[]{"_id", FeedColumns.FEED}, SELECT_SUBSCRIBED_FEEDS_BY_ACCOUNT_AND_AUTHORITY, new String[]{account2.name, account2.type, str}, null);
        if (c == null) {
            return false;
        }
        while (c.moveToNext()) {
            try {
                long id = c.getLong(0);
                String feed = c.getString(1);
                if (hashMap.containsKey(feed)) {
                    contentResolver.delete(Feeds.CONTENT_URI, SELECT_FEEDS_BY_ID, new String[]{Long.toString(id)});
                } else {
                    hashMap.put(feed, Long.valueOf(id));
                }
            } catch (Throwable th) {
                c.close();
                throw th;
            }
        }
        c.close();
        for (String expectedFeed : expectedFeedUrls) {
            if (!hashMap.containsKey(expectedFeed)) {
                ContentValues values = new ContentValues();
                values.put("_sync_account", account2.name);
                values.put("_sync_account_type", account2.type);
                values.put(FeedColumns.FEED, expectedFeed);
                values.put("service", serviceName);
                values.put(FeedColumns.AUTHORITY, str);
                try {
                    contentResolver.insert(Feeds.CONTENT_URI, values);
                } catch (IllegalArgumentException e) {
                    return false;
                }
            } else {
                hashMap.remove(expectedFeed);
            }
        }
        for (Map.Entry<String, Long> existing : hashMap.entrySet()) {
            try {
                contentResolver.delete(ContentUris.withAppendedId(Feeds.CONTENT_URI, ((Long) existing.getValue()).longValue()), null, null);
            } catch (IllegalArgumentException e2) {
                return false;
            }
        }
        return true;
    }

    public static boolean manageSubscriptions(ContentResolver contentResolver, Account account, String authority, String serviceName, String... expectedFeeds) {
        ArrayList<String> expectedFeedUrls = new ArrayList<>(expectedFeeds.length);
        for (String add : expectedFeeds) {
            expectedFeedUrls.add(add);
        }
        return manageSubscriptions(contentResolver, account, authority, serviceName, expectedFeedUrls);
    }

    public static Uri addFeed(ContentResolver resolver, String feed, Account account, String authority, String service) {
        ContentValues values = new ContentValues();
        values.put(FeedColumns.FEED, feed);
        values.put("_sync_account", account.name);
        values.put("_sync_account_type", account.type);
        values.put(FeedColumns.AUTHORITY, authority);
        values.put("service", service);
        return resolver.insert(Feeds.CONTENT_URI, values);
    }

    public static int deleteFeed(ContentResolver resolver, String feed, Account account, String authority) {
        return resolver.delete(Feeds.CONTENT_URI, "_sync_account=?" + " AND _sync_account_type=?" + " AND feed=?" + " AND authority=?", new String[]{account.name, account.type, feed, authority});
    }

    public static int deleteFeeds(ContentResolver resolver, Account account, String authority) {
        return resolver.delete(Feeds.CONTENT_URI, "_sync_account=?" + " AND _sync_account_type=?" + " AND authority=?", new String[]{account.name, account.type, authority});
    }

    public interface AccountColumns {
        public static final String _SYNC_ACCOUNT = "_sync_account";
        public static final String _SYNC_ACCOUNT_TYPE = "_sync_account_type";
    }

    public interface FeedColumns {
        public static final String AUTHORITY = "authority";
        public static final String FEED = "feed";
        public static final String SERVICE = "service";
        public static final String _SYNC_ACCOUNT = "_sync_account";
        public static final String _SYNC_ACCOUNT_TYPE = "_sync_account_type";
        public static final String _SYNC_DIRTY = "_sync_dirty";
        public static final String _SYNC_ID = "_sync_id";
        public static final String _SYNC_LOCAL_ID = "_sync_local_id";
        public static final String _SYNC_MARK = "_sync_mark";
        public static final String _SYNC_TIME = "_sync_time";
        public static final String _SYNC_VERSION = "_sync_version";
    }

    public static final class Feeds implements BaseColumns, FeedColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/subscribedfeed";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/subscribedfeeds";
        public static final Uri CONTENT_URI = Uri.parse("content://subscribedfeeds/feeds");
        public static final String DEFAULT_SORT_ORDER = "_SYNC_ACCOUNT_TYPE, _SYNC_ACCOUNT ASC";
        public static final Uri DELETED_CONTENT_URI = Uri.parse("content://subscribedfeeds/deleted_feeds");

        private Feeds() {
        }

        public static Cursor query(ContentResolver cr, String[] projection) {
            return cr.query(CONTENT_URI, projection, null, null, "_SYNC_ACCOUNT_TYPE, _SYNC_ACCOUNT ASC");
        }

        public static Cursor query(ContentResolver cr, String[] projection, String where, String[] whereArgs, String orderBy) {
            return cr.query(CONTENT_URI, projection, where, whereArgs, orderBy == null ? "_SYNC_ACCOUNT_TYPE, _SYNC_ACCOUNT ASC" : orderBy);
        }
    }

    public static final class Accounts implements BaseColumns, AccountColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/subscribedfeedaccount";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/subscribedfeedaccounts";
        public static final Uri CONTENT_URI = Uri.parse("content://subscribedfeeds/accounts");
        public static final String DEFAULT_SORT_ORDER = "_SYNC_ACCOUNT_TYPE, _SYNC_ACCOUNT ASC";

        private Accounts() {
        }

        public static Cursor query(ContentResolver cr, String[] projection) {
            return cr.query(CONTENT_URI, projection, null, null, "_SYNC_ACCOUNT_TYPE, _SYNC_ACCOUNT ASC");
        }

        public static Cursor query(ContentResolver cr, String[] projection, String where, String orderBy) {
            return cr.query(CONTENT_URI, projection, where, null, orderBy == null ? "_SYNC_ACCOUNT_TYPE, _SYNC_ACCOUNT ASC" : orderBy);
        }
    }
}
