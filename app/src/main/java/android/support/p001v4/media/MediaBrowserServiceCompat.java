package android.support.p001v4.media;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.media.MediaBrowserService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p001v4.app.BundleCompat;
import android.support.p001v4.media.session.IMediaSession;
import android.support.p001v4.media.session.MediaSessionCompat;
import android.support.p001v4.p003os.ResultReceiver;
import android.support.p001v4.util.ArrayMap;
import android.support.p001v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* renamed from: android.support.v4.media.MediaBrowserServiceCompat */
public abstract class MediaBrowserServiceCompat extends Service {
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String KEY_MEDIA_ITEM = "media_item";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String KEY_SEARCH_RESULTS = "search_results";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int RESULT_ERROR = -1;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int RESULT_OK = 0;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int RESULT_PROGRESS_UPDATE = 1;
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    static final int RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED = 2;
    static final int RESULT_FLAG_ON_SEARCH_NOT_IMPLEMENTED = 4;
    static final int RESULT_FLAG_OPTION_NOT_HANDLED = 1;
    static final String TAG = "MBServiceCompat";
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final float EPSILON = 1.0E-5f;
    final ConnectionRecord mConnectionFromFwk = new ConnectionRecord(MediaSessionManager.RemoteUserInfo.LEGACY_CONTROLLER, -1, -1, null, null);
    final ArrayMap<IBinder, ConnectionRecord> mConnections = new ArrayMap<>();
    final ServiceHandler mHandler = new ServiceHandler();
    final ArrayList<ConnectionRecord> mPendingConnections = new ArrayList<>();
    ConnectionRecord mCurConnection;
    MediaSessionCompat.Token mSession;
    private MediaBrowserServiceImpl mImpl;

    @Nullable
    public abstract BrowserRoot onGetRoot(@NonNull String str, int i, @Nullable Bundle bundle);

    public abstract void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result);

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void attachToBaseContext(Context base) {
        attachBaseContext(base);
    }

    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 28) {
            this.mImpl = new MediaBrowserServiceImplApi28();
        } else if (Build.VERSION.SDK_INT >= 26) {
            this.mImpl = new MediaBrowserServiceImplApi26();
        } else if (Build.VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaBrowserServiceImplApi23();
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaBrowserServiceImplApi21();
        } else {
            this.mImpl = new MediaBrowserServiceImplBase();
        }
        this.mImpl.onCreate();
    }

    public IBinder onBind(Intent intent) {
        return this.mImpl.onBind(intent);
    }

    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
    }

    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result, @NonNull Bundle options) {
        result.setFlags(1);
        onLoadChildren(parentId, result);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void onSubscribe(String id, Bundle option) {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void onUnsubscribe(String id) {
    }

    public void onLoadItem(String itemId, @NonNull Result<MediaBrowserCompat.MediaItem> result) {
        result.setFlags(2);
        result.sendResult(null);
    }

    public void onSearch(@NonNull String query, Bundle extras, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.setFlags(4);
        result.sendResult(null);
    }

    public void onCustomAction(@NonNull String action, Bundle extras, @NonNull Result<Bundle> result) {
        result.sendError(null);
    }

    @Nullable
    public MediaSessionCompat.Token getSessionToken() {
        return this.mSession;
    }

    public void setSessionToken(MediaSessionCompat.Token token) {
        if (token == null) {
            throw new IllegalArgumentException("Session token may not be null.");
        } else if (this.mSession == null) {
            this.mSession = token;
            this.mImpl.setSessionToken(token);
        } else {
            throw new IllegalStateException("The session token has already been set.");
        }
    }

    public final Bundle getBrowserRootHints() {
        return this.mImpl.getBrowserRootHints();
    }

    @NonNull
    public final MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
        return this.mImpl.getCurrentBrowserInfo();
    }

    public void notifyChildrenChanged(@NonNull String parentId) {
        if (parentId != null) {
            this.mImpl.notifyChildrenChanged(parentId, null);
            return;
        }
        throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
    }

    public void notifyChildrenChanged(@NonNull String parentId, @NonNull Bundle options) {
        if (parentId == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        } else if (options != null) {
            this.mImpl.notifyChildrenChanged(parentId, options);
        } else {
            throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void notifyChildrenChanged(@NonNull MediaSessionManager.RemoteUserInfo remoteUserInfo, @NonNull String parentId, @NonNull Bundle options) {
        if (remoteUserInfo == null) {
            throw new IllegalArgumentException("remoteUserInfo cannot be null in notifyChildrenChanged");
        } else if (parentId == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        } else if (options != null) {
            this.mImpl.notifyChildrenChanged(remoteUserInfo, parentId, options);
        } else {
            throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isValidPackage(String pkg, int uid) {
        if (pkg == null) {
            return false;
        }
        for (String equals : getPackageManager().getPackagesForUid(uid)) {
            if (equals.equals(pkg)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void addSubscription(String id, ConnectionRecord connection, IBinder token, Bundle options) {
        List<Pair<IBinder, Bundle>> callbackList = connection.subscriptions.get(id);
        if (callbackList == null) {
            callbackList = new ArrayList<>();
        }
        for (Pair<IBinder, Bundle> callback : callbackList) {
            if (token == callback.first && MediaBrowserCompatUtils.areSameOptions(options, (Bundle) callback.second)) {
                return;
            }
        }
        callbackList.add(new Pair(token, options));
        connection.subscriptions.put(id, callbackList);
        performLoadChildren(id, connection, options, null);
        this.mCurConnection = connection;
        onSubscribe(id, options);
        this.mCurConnection = null;
    }

    /* access modifiers changed from: package-private */
    public boolean removeSubscription(String id, ConnectionRecord connection, IBinder token) {
        if (token == null) {
            try {
                return connection.subscriptions.remove(id) != null;
            } finally {
                this.mCurConnection = connection;
                onUnsubscribe(id);
                this.mCurConnection = null;
            }
        } else {
            boolean removed = false;
            List<Pair<IBinder, Bundle>> callbackList = connection.subscriptions.get(id);
            if (callbackList != null) {
                Iterator<Pair<IBinder, Bundle>> iter = callbackList.iterator();
                while (iter.hasNext()) {
                    if (token == ((Pair) iter.next()).first) {
                        removed = true;
                        iter.remove();
                    }
                }
                if (callbackList.size() == 0) {
                    connection.subscriptions.remove(id);
                }
            }
            this.mCurConnection = connection;
            onUnsubscribe(id);
            this.mCurConnection = null;
            return removed;
        }
    }

    /* access modifiers changed from: package-private */
    public void performLoadChildren(String parentId, ConnectionRecord connection, Bundle subscribeOptions, Bundle notifyChildrenChangedOptions) {
        final ConnectionRecord connectionRecord = connection;
        final String str = parentId;
        final Bundle bundle = subscribeOptions;
        final Bundle bundle2 = notifyChildrenChangedOptions;
        Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(parentId) {
            /* access modifiers changed from: package-private */
            public /* bridge */ /* synthetic */ void onResultSent(Object obj) {
                onResultSent((List<MediaBrowserCompat.MediaItem>) ((List) obj));
            }

            /* access modifiers changed from: package-private */
            public void onResultSent(List<MediaBrowserCompat.MediaItem> list) {
                if (MediaBrowserServiceCompat.this.mConnections.get(connectionRecord.callbacks.asBinder()) == connectionRecord) {
                    try {
                        connectionRecord.callbacks.onLoadChildren(str, (getFlags() & 1) != 0 ? MediaBrowserServiceCompat.this.applyOptions(list, bundle) : list, bundle, bundle2);
                    } catch (RemoteException e) {
                        Log.w(MediaBrowserServiceCompat.TAG, "Calling onLoadChildren() failed for id=" + str + " package=" + connectionRecord.pkg);
                    }
                } else if (MediaBrowserServiceCompat.DEBUG) {
                    Log.d(MediaBrowserServiceCompat.TAG, "Not sending onLoadChildren result for connection that has been disconnected. pkg=" + connectionRecord.pkg + " id=" + str);
                }
            }
        };
        this.mCurConnection = connection;
        if (subscribeOptions == null) {
            onLoadChildren(parentId, result);
        } else {
            onLoadChildren(parentId, result, subscribeOptions);
        }
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + connection.pkg + " id=" + parentId);
        }
    }

    /* access modifiers changed from: package-private */
    public List<MediaBrowserCompat.MediaItem> applyOptions(List<MediaBrowserCompat.MediaItem> list, Bundle options) {
        if (list == null) {
            return null;
        }
        int page = options.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int pageSize = options.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if (page == -1 && pageSize == -1) {
            return list;
        }
        int fromIndex = pageSize * page;
        int toIndex = fromIndex + pageSize;
        if (page < 0 || pageSize < 1 || fromIndex >= list.size()) {
            return Collections.emptyList();
        }
        if (toIndex > list.size()) {
            toIndex = list.size();
        }
        return list.subList(fromIndex, toIndex);
    }

    /* access modifiers changed from: package-private */
    public void performLoadItem(String itemId, ConnectionRecord connection, final ResultReceiver receiver) {
        Result<MediaBrowserCompat.MediaItem> result = new Result<MediaBrowserCompat.MediaItem>(itemId) {
            /* access modifiers changed from: package-private */
            public void onResultSent(MediaBrowserCompat.MediaItem item) {
                if ((getFlags() & 2) != 0) {
                    receiver.send(-1, null);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM, item);
                receiver.send(0, bundle);
            }
        };
        this.mCurConnection = connection;
        onLoadItem(itemId, result);
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + itemId);
        }
    }

    /* access modifiers changed from: package-private */
    public void performSearch(String query, Bundle extras, ConnectionRecord connection, final ResultReceiver receiver) {
        Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(query) {
            /* access modifiers changed from: package-private */
            public /* bridge */ /* synthetic */ void onResultSent(Object obj) {
                onResultSent((List<MediaBrowserCompat.MediaItem>) ((List) obj));
            }

            /* access modifiers changed from: package-private */
            public void onResultSent(List<MediaBrowserCompat.MediaItem> items) {
                if ((getFlags() & 4) != 0 || items == null) {
                    receiver.send(-1, null);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS, (Parcelable[]) items.toArray(new MediaBrowserCompat.MediaItem[0]));
                receiver.send(0, bundle);
            }
        };
        this.mCurConnection = connection;
        onSearch(query, extras, result);
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException("onSearch must call detach() or sendResult() before returning for query=" + query);
        }
    }

    /* access modifiers changed from: package-private */
    public void performCustomAction(String action, Bundle extras, ConnectionRecord connection, final ResultReceiver receiver) {
        Result<Bundle> result = new Result<Bundle>(action) {
            /* access modifiers changed from: package-private */
            public void onResultSent(Bundle result) {
                receiver.send(0, result);
            }

            /* access modifiers changed from: package-private */
            public void onProgressUpdateSent(Bundle data) {
                receiver.send(1, data);
            }

            /* access modifiers changed from: package-private */
            public void onErrorSent(Bundle data) {
                receiver.send(-1, data);
            }
        };
        this.mCurConnection = connection;
        onCustomAction(action, extras, result);
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException("onCustomAction must call detach() or sendResult() or sendError() before returning for action=" + action + " extras=" + extras);
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImpl */
    interface MediaBrowserServiceImpl {
        Bundle getBrowserRootHints();

        MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo();

        void notifyChildrenChanged(MediaSessionManager.RemoteUserInfo remoteUserInfo, String str, Bundle bundle);

        void notifyChildrenChanged(String str, Bundle bundle);

        IBinder onBind(Intent intent);

        void onCreate();

        void setSessionToken(MediaSessionCompat.Token token);
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$ServiceCallbacks */
    private interface ServiceCallbacks {
        IBinder asBinder();

        void onConnect(String str, MediaSessionCompat.Token token, Bundle bundle) throws RemoteException;

        void onConnectFailed() throws RemoteException;

        void onLoadChildren(String str, List<MediaBrowserCompat.MediaItem> list, Bundle bundle, Bundle bundle2) throws RemoteException;
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$Result */
    public static class Result<T> {
        private final Object mDebug;
        private boolean mDetachCalled;
        private int mFlags;
        private boolean mSendErrorCalled;
        private boolean mSendProgressUpdateCalled;
        private boolean mSendResultCalled;

        Result(Object debug) {
            this.mDebug = debug;
        }

        public void sendResult(T result) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                throw new IllegalStateException("sendResult() called when either sendResult() or sendError() had already been called for: " + this.mDebug);
            }
            this.mSendResultCalled = true;
            onResultSent(result);
        }

        public void sendProgressUpdate(Bundle extras) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                throw new IllegalStateException("sendProgressUpdate() called when either sendResult() or sendError() had already been called for: " + this.mDebug);
            }
            checkExtraFields(extras);
            this.mSendProgressUpdateCalled = true;
            onProgressUpdateSent(extras);
        }

        public void sendError(Bundle extras) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                throw new IllegalStateException("sendError() called when either sendResult() or sendError() had already been called for: " + this.mDebug);
            }
            this.mSendErrorCalled = true;
            onErrorSent(extras);
        }

        public void detach() {
            if (this.mDetachCalled) {
                throw new IllegalStateException("detach() called when detach() had already been called for: " + this.mDebug);
            } else if (this.mSendResultCalled) {
                throw new IllegalStateException("detach() called when sendResult() had already been called for: " + this.mDebug);
            } else if (!this.mSendErrorCalled) {
                this.mDetachCalled = true;
            } else {
                throw new IllegalStateException("detach() called when sendError() had already been called for: " + this.mDebug);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean isDone() {
            return this.mDetachCalled || this.mSendResultCalled || this.mSendErrorCalled;
        }

        /* access modifiers changed from: package-private */
        public int getFlags() {
            return this.mFlags;
        }

        /* access modifiers changed from: package-private */
        public void setFlags(int flags) {
            this.mFlags = flags;
        }

        /* access modifiers changed from: package-private */
        public void onResultSent(T t) {
        }

        /* access modifiers changed from: package-private */
        public void onProgressUpdateSent(Bundle extras) {
            throw new UnsupportedOperationException("It is not supported to send an interim update for " + this.mDebug);
        }

        /* access modifiers changed from: package-private */
        public void onErrorSent(Bundle extras) {
            throw new UnsupportedOperationException("It is not supported to send an error for " + this.mDebug);
        }

        private void checkExtraFields(Bundle extras) {
            if (extras != null && extras.containsKey(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS)) {
                float value = extras.getFloat(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS);
                if (value < -1.0E-5f || value > 1.00001f) {
                    throw new IllegalArgumentException("The value of the EXTRA_DOWNLOAD_PROGRESS field must be a float number within [0.0, 1.0].");
                }
            }
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$ServiceCallbacksCompat */
    private static class ServiceCallbacksCompat implements ServiceCallbacks {
        final Messenger mCallbacks;

        ServiceCallbacksCompat(Messenger callbacks) {
            this.mCallbacks = callbacks;
        }

        public IBinder asBinder() {
            return this.mCallbacks.getBinder();
        }

        public void onConnect(String root, MediaSessionCompat.Token session, Bundle extras) throws RemoteException {
            if (extras == null) {
                extras = new Bundle();
            }
            extras.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 2);
            Bundle data = new Bundle();
            data.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, root);
            data.putParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN, session);
            data.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, extras);
            sendRequest(1, data);
        }

        public void onConnectFailed() throws RemoteException {
            sendRequest(2, null);
        }

        public void onLoadChildren(String mediaId, List<MediaBrowserCompat.MediaItem> list, Bundle options, Bundle notifyChildrenChangedOptions) throws RemoteException {
            Bundle data = new Bundle();
            data.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, mediaId);
            data.putBundle(MediaBrowserProtocol.DATA_OPTIONS, options);
            data.putBundle(MediaBrowserProtocol.DATA_NOTIFY_CHILDREN_CHANGED_OPTIONS, notifyChildrenChangedOptions);
            if (list != null) {
                data.putParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST, list instanceof ArrayList ? (ArrayList) list : new ArrayList(list));
            }
            sendRequest(3, data);
        }

        private void sendRequest(int what, Bundle data) throws RemoteException {
            Message msg = Message.obtain();
            msg.what = what;
            msg.arg1 = 2;
            msg.setData(data);
            this.mCallbacks.send(msg);
        }
    }

    @RequiresApi(21)
    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$ResultWrapper */
    static class ResultWrapper<T> {
        MediaBrowserService.Result mResultFwk;

        ResultWrapper(MediaBrowserService.Result result) {
            this.mResultFwk = result;
        }

        public void sendResult(T result) {
            if (result instanceof List) {
                this.mResultFwk.sendResult(parcelListToItemList((List) result));
            } else if (result instanceof Parcel) {
                Parcel parcel = (Parcel) result;
                parcel.setDataPosition(0);
                this.mResultFwk.sendResult(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
            } else {
                this.mResultFwk.sendResult(null);
            }
        }

        public void detach() {
            this.mResultFwk.detach();
        }

        /* access modifiers changed from: package-private */
        public List<MediaBrowser.MediaItem> parcelListToItemList(List<Parcel> parcelList) {
            if (parcelList == null) {
                return null;
            }
            List<MediaBrowser.MediaItem> items = new ArrayList<>();
            for (Parcel parcel : parcelList) {
                parcel.setDataPosition(0);
                items.add(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
            }
            return items;
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$BrowserRoot */
    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
        @Deprecated
        public static final String EXTRA_SUGGESTION_KEYWORDS = "android.service.media.extra.SUGGESTION_KEYWORDS";
        /* access modifiers changed from: private */
        public final Bundle mExtras;
        /* access modifiers changed from: private */
        public final String mRootId;

        public BrowserRoot(@NonNull String rootId, @Nullable Bundle extras) {
            if (rootId != null) {
                this.mRootId = rootId;
                this.mExtras = extras;
                return;
            }
            throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
        }

        public String getRootId() {
            return this.mRootId;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImplBase */
    class MediaBrowserServiceImplBase implements MediaBrowserServiceImpl {
        private Messenger mMessenger;

        MediaBrowserServiceImplBase() {
        }

        public void onCreate() {
            this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
        }

        public IBinder onBind(Intent intent) {
            if (MediaBrowserServiceCompat.SERVICE_INTERFACE.equals(intent.getAction())) {
                return this.mMessenger.getBinder();
            }
            return null;
        }

        public void setSessionToken(final MediaSessionCompat.Token token) {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
                public void run() {
                    Iterator<ConnectionRecord> iter = MediaBrowserServiceCompat.this.mConnections.values().iterator();
                    while (iter.hasNext()) {
                        ConnectionRecord connection = iter.next();
                        try {
                            connection.callbacks.onConnect(connection.root.getRootId(), token, connection.root.getExtras());
                        } catch (RemoteException e) {
                            Log.w(MediaBrowserServiceCompat.TAG, "Connection for " + connection.pkg + " is no longer valid.");
                            iter.remove();
                        }
                    }
                }
            });
        }

        public void notifyChildrenChanged(@NonNull final String parentId, final Bundle options) {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
                public void run() {
                    for (IBinder binder : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                        ArrayMap<IBinder, ConnectionRecord> arrayMap = MediaBrowserServiceCompat.this.mConnections;
                        MediaBrowserServiceImplBase.this.notifyChildrenChangedOnHandler(arrayMap.get(binder), parentId, options);
                    }
                }
            });
        }

        public void notifyChildrenChanged(@NonNull final MediaSessionManager.RemoteUserInfo remoteUserInfo, @NonNull final String parentId, final Bundle options) {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
                public void run() {
                    for (int i = 0; i < MediaBrowserServiceCompat.this.mConnections.size(); i++) {
                        ConnectionRecord connection = MediaBrowserServiceCompat.this.mConnections.valueAt(i);
                        if (connection.browserInfo.equals(remoteUserInfo)) {
                            MediaBrowserServiceImplBase.this.notifyChildrenChangedOnHandler(connection, parentId, options);
                            return;
                        }
                    }
                }
            });
        }

        /* access modifiers changed from: package-private */
        public void notifyChildrenChangedOnHandler(ConnectionRecord connection, String parentId, Bundle options) {
            List<Pair<IBinder, Bundle>> callbackList = connection.subscriptions.get(parentId);
            if (callbackList != null) {
                for (Pair<IBinder, Bundle> callback : callbackList) {
                    if (MediaBrowserCompatUtils.hasDuplicatedItems(options, (Bundle) callback.second)) {
                        MediaBrowserServiceCompat.this.performLoadChildren(parentId, connection, (Bundle) callback.second, options);
                    }
                }
            }
        }

        public Bundle getBrowserRootHints() {
            if (MediaBrowserServiceCompat.this.mCurConnection == null) {
                throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            } else if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
                return null;
            } else {
                return new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
            }
        }

        public MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
            if (MediaBrowserServiceCompat.this.mCurConnection != null) {
                return MediaBrowserServiceCompat.this.mCurConnection.browserInfo;
            }
            throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
        }
    }

    @RequiresApi(21)
            /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImplApi21 */
    class MediaBrowserServiceImplApi21 implements MediaBrowserServiceImpl {
        final List<Bundle> mRootExtrasList = new ArrayList();
        Messenger mMessenger;
        MediaBrowserService mServiceFwk;

        MediaBrowserServiceImplApi21() {
        }

        public void onCreate() {
            this.mServiceFwk = new MediaBrowserServiceApi21(MediaBrowserServiceCompat.this);
            this.mServiceFwk.onCreate();
        }

        public IBinder onBind(Intent intent) {
            return this.mServiceFwk.onBind(intent);
        }

        public void setSessionToken(final MediaSessionCompat.Token token) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                public void run() {
                    if (!MediaBrowserServiceImplApi21.this.mRootExtrasList.isEmpty()) {
                        IMediaSession extraBinder = token.getExtraBinder();
                        if (extraBinder != null) {
                            for (Bundle rootExtras : MediaBrowserServiceImplApi21.this.mRootExtrasList) {
                                BundleCompat.putBinder(rootExtras, MediaBrowserProtocol.EXTRA_SESSION_BINDER, extraBinder.asBinder());
                            }
                        }
                        MediaBrowserServiceImplApi21.this.mRootExtrasList.clear();
                    }
                    MediaBrowserServiceImplApi21.this.mServiceFwk.setSessionToken((MediaSession.Token) token.getToken());
                }
            });
        }

        public void notifyChildrenChanged(String parentId, Bundle options) {
            notifyChildrenChangedForFramework(parentId, options);
            notifyChildrenChangedForCompat(parentId, options);
        }

        public void notifyChildrenChanged(MediaSessionManager.RemoteUserInfo remoteUserInfo, String parentId, Bundle options) {
            notifyChildrenChangedForCompat(remoteUserInfo, parentId, options);
        }

        public BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {
            IBinder iBinder;
            Bundle rootExtras = null;
            int clientPid = -1;
            if (!(rootHints == null || rootHints.getInt(MediaBrowserProtocol.EXTRA_CLIENT_VERSION, 0) == 0)) {
                rootHints.remove(MediaBrowserProtocol.EXTRA_CLIENT_VERSION);
                this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
                rootExtras = new Bundle();
                rootExtras.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 2);
                BundleCompat.putBinder(rootExtras, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER, this.mMessenger.getBinder());
                if (MediaBrowserServiceCompat.this.mSession != null) {
                    IMediaSession extraBinder = MediaBrowserServiceCompat.this.mSession.getExtraBinder();
                    if (extraBinder == null) {
                        iBinder = null;
                    } else {
                        iBinder = extraBinder.asBinder();
                    }
                    BundleCompat.putBinder(rootExtras, MediaBrowserProtocol.EXTRA_SESSION_BINDER, iBinder);
                } else {
                    this.mRootExtrasList.add(rootExtras);
                }
                clientPid = rootHints.getInt(MediaBrowserProtocol.EXTRA_CALLING_PID, -1);
                rootHints.remove(MediaBrowserProtocol.EXTRA_CALLING_PID);
            }
            ConnectionRecord connectionRecord = new ConnectionRecord(clientPackageName, clientPid, clientUid, rootHints, null);
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.mCurConnection = connectionRecord;
            BrowserRoot root = mediaBrowserServiceCompat.onGetRoot(clientPackageName, clientUid, rootHints);
            MediaBrowserServiceCompat mediaBrowserServiceCompat2 = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat2.mCurConnection = null;
            if (root == null) {
                return null;
            }
            if (this.mMessenger != null) {
                mediaBrowserServiceCompat2.mPendingConnections.add(connectionRecord);
            }
            if (rootExtras == null) {
                rootExtras = root.getExtras();
            } else if (root.getExtras() != null) {
                rootExtras.putAll(root.getExtras());
            }
            return new BrowserRoot(root.getRootId(), rootExtras);
        }

        public void onLoadChildren(String parentId, final ResultWrapper<List<Parcel>> resultWrapper) {
            Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(parentId) {
                /* access modifiers changed from: package-private */
                public /* bridge */ /* synthetic */ void onResultSent(Object obj) {
                    onResultSent((List<MediaBrowserCompat.MediaItem>) ((List) obj));
                }

                /* access modifiers changed from: package-private */
                public void onResultSent(List<MediaBrowserCompat.MediaItem> list) {
                    List<Parcel> parcelList = null;
                    if (list != null) {
                        parcelList = new ArrayList<>();
                        for (MediaBrowserCompat.MediaItem item : list) {
                            Parcel parcel = Parcel.obtain();
                            item.writeToParcel(parcel, 0);
                            parcelList.add(parcel);
                        }
                    }
                    resultWrapper.sendResult(parcelList);
                }

                public void detach() {
                    resultWrapper.detach();
                }
            };
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.mCurConnection = mediaBrowserServiceCompat.mConnectionFromFwk;
            MediaBrowserServiceCompat.this.onLoadChildren(parentId, result);
            MediaBrowserServiceCompat.this.mCurConnection = null;
        }

        /* access modifiers changed from: package-private */
        public void notifyChildrenChangedForFramework(String parentId, Bundle options) {
            this.mServiceFwk.notifyChildrenChanged(parentId);
        }

        /* access modifiers changed from: package-private */
        public void notifyChildrenChangedForCompat(final String parentId, final Bundle options) {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
                public void run() {
                    for (IBinder binder : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                        ArrayMap<IBinder, ConnectionRecord> arrayMap = MediaBrowserServiceCompat.this.mConnections;
                        MediaBrowserServiceImplApi21.this.notifyChildrenChangedForCompatOnHandler(arrayMap.get(binder), parentId, options);
                    }
                }
            });
        }

        /* access modifiers changed from: package-private */
        public void notifyChildrenChangedForCompat(final MediaSessionManager.RemoteUserInfo remoteUserInfo, final String parentId, final Bundle options) {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
                public void run() {
                    for (int i = 0; i < MediaBrowserServiceCompat.this.mConnections.size(); i++) {
                        ConnectionRecord connection = MediaBrowserServiceCompat.this.mConnections.valueAt(i);
                        if (connection.browserInfo.equals(remoteUserInfo)) {
                            MediaBrowserServiceImplApi21.this.notifyChildrenChangedForCompatOnHandler(connection, parentId, options);
                        }
                    }
                }
            });
        }

        /* access modifiers changed from: package-private */
        public void notifyChildrenChangedForCompatOnHandler(ConnectionRecord connection, String parentId, Bundle options) {
            List<Pair<IBinder, Bundle>> callbackList = connection.subscriptions.get(parentId);
            if (callbackList != null) {
                for (Pair<IBinder, Bundle> callback : callbackList) {
                    if (MediaBrowserCompatUtils.hasDuplicatedItems(options, (Bundle) callback.second)) {
                        MediaBrowserServiceCompat.this.performLoadChildren(parentId, connection, (Bundle) callback.second, options);
                    }
                }
            }
        }

        public Bundle getBrowserRootHints() {
            if (this.mMessenger == null) {
                return null;
            }
            if (MediaBrowserServiceCompat.this.mCurConnection == null) {
                throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            } else if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
                return null;
            } else {
                return new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
            }
        }

        public MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
            if (MediaBrowserServiceCompat.this.mCurConnection != null) {
                return MediaBrowserServiceCompat.this.mCurConnection.browserInfo;
            }
            throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
        }

        /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImplApi21$MediaBrowserServiceApi21 */
        class MediaBrowserServiceApi21 extends MediaBrowserService {
            MediaBrowserServiceApi21(Context context) {
                attachBaseContext(context);
            }

            @SuppressLint({"SyntheticAccessor"})
            public MediaBrowserService.BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {
                MediaSessionCompat.ensureClassLoader(rootHints);
                BrowserRoot browserRootCompat = MediaBrowserServiceImplApi21.this.onGetRoot(clientPackageName, clientUid, rootHints == null ? null : new Bundle(rootHints));
                if (browserRootCompat == null) {
                    return null;
                }
                return new MediaBrowserService.BrowserRoot(browserRootCompat.mRootId, browserRootCompat.mExtras);
            }

            public void onLoadChildren(String parentId, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result) {
                MediaBrowserServiceImplApi21.this.onLoadChildren(parentId, new ResultWrapper(result));
            }
        }
    }

    @RequiresApi(23)
            /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImplApi23 */
    class MediaBrowserServiceImplApi23 extends MediaBrowserServiceImplApi21 {
        MediaBrowserServiceImplApi23() {
            super();
        }

        public void onCreate() {
            this.mServiceFwk = new MediaBrowserServiceApi23(MediaBrowserServiceCompat.this);
            this.mServiceFwk.onCreate();
        }

        public void onLoadItem(String itemId, final ResultWrapper<Parcel> resultWrapper) {
            Result<MediaBrowserCompat.MediaItem> result = new Result<MediaBrowserCompat.MediaItem>(itemId) {
                /* access modifiers changed from: package-private */
                public void onResultSent(MediaBrowserCompat.MediaItem item) {
                    if (item == null) {
                        resultWrapper.sendResult(null);
                        return;
                    }
                    Parcel parcelItem = Parcel.obtain();
                    item.writeToParcel(parcelItem, 0);
                    resultWrapper.sendResult(parcelItem);
                }

                public void detach() {
                    resultWrapper.detach();
                }
            };
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.mCurConnection = mediaBrowserServiceCompat.mConnectionFromFwk;
            MediaBrowserServiceCompat.this.onLoadItem(itemId, result);
            MediaBrowserServiceCompat.this.mCurConnection = null;
        }

        /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImplApi23$MediaBrowserServiceApi23 */
        class MediaBrowserServiceApi23 extends MediaBrowserServiceImplApi21.MediaBrowserServiceApi21 {
            MediaBrowserServiceApi23(Context context) {
                super(context);
            }

            public void onLoadItem(String itemId, MediaBrowserService.Result<MediaBrowser.MediaItem> result) {
                MediaBrowserServiceImplApi23.this.onLoadItem(itemId, new ResultWrapper(result));
            }
        }
    }

    @RequiresApi(26)
            /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImplApi26 */
    class MediaBrowserServiceImplApi26 extends MediaBrowserServiceImplApi23 {
        MediaBrowserServiceImplApi26() {
            super();
        }

        public void onCreate() {
            this.mServiceFwk = new MediaBrowserServiceApi26(MediaBrowserServiceCompat.this);
            this.mServiceFwk.onCreate();
        }

        public void onLoadChildren(String parentId, final ResultWrapper<List<Parcel>> resultWrapper, final Bundle options) {
            Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(parentId) {
                /* access modifiers changed from: package-private */
                public /* bridge */ /* synthetic */ void onResultSent(Object obj) {
                    onResultSent((List<MediaBrowserCompat.MediaItem>) ((List) obj));
                }

                /* access modifiers changed from: package-private */
                public void onResultSent(List<MediaBrowserCompat.MediaItem> list) {
                    if (list == null) {
                        resultWrapper.sendResult(null);
                        return;
                    }
                    if ((getFlags() & 1) != 0) {
                        list = MediaBrowserServiceCompat.this.applyOptions(list, options);
                    }
                    List<Parcel> parcelList = new ArrayList<>();
                    for (MediaBrowserCompat.MediaItem item : list) {
                        Parcel parcel = Parcel.obtain();
                        item.writeToParcel(parcel, 0);
                        parcelList.add(parcel);
                    }
                    resultWrapper.sendResult(parcelList);
                }

                public void detach() {
                    resultWrapper.detach();
                }
            };
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.mCurConnection = mediaBrowserServiceCompat.mConnectionFromFwk;
            MediaBrowserServiceCompat.this.onLoadChildren(parentId, result, options);
            MediaBrowserServiceCompat.this.mCurConnection = null;
        }

        public Bundle getBrowserRootHints() {
            if (MediaBrowserServiceCompat.this.mCurConnection == null) {
                throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            } else if (MediaBrowserServiceCompat.this.mCurConnection == MediaBrowserServiceCompat.this.mConnectionFromFwk) {
                return this.mServiceFwk.getBrowserRootHints();
            } else {
                if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
                    return null;
                }
                return new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
            }
        }

        /* access modifiers changed from: package-private */
        public void notifyChildrenChangedForFramework(String parentId, Bundle options) {
            if (options != null) {
                this.mServiceFwk.notifyChildrenChanged(parentId, options);
            } else {
                super.notifyChildrenChangedForFramework(parentId, options);
            }
        }

        /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImplApi26$MediaBrowserServiceApi26 */
        class MediaBrowserServiceApi26 extends MediaBrowserServiceImplApi23.MediaBrowserServiceApi23 {
            MediaBrowserServiceApi26(Context context) {
                super(context);
            }

            public void onLoadChildren(String parentId, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result, Bundle options) {
                MediaSessionCompat.ensureClassLoader(options);
                MediaBrowserServiceCompat.this.mCurConnection = MediaBrowserServiceCompat.this.mConnectionFromFwk;
                MediaBrowserServiceImplApi26.this.onLoadChildren(parentId, new ResultWrapper(result), options);
                MediaBrowserServiceCompat.this.mCurConnection = null;
            }
        }
    }

    @RequiresApi(28)
            /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImplApi28 */
    class MediaBrowserServiceImplApi28 extends MediaBrowserServiceImplApi26 {
        MediaBrowserServiceImplApi28() {
            super();
        }

        public MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
            if (MediaBrowserServiceCompat.this.mCurConnection == null) {
                throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            } else if (MediaBrowserServiceCompat.this.mCurConnection == MediaBrowserServiceCompat.this.mConnectionFromFwk) {
                return new MediaSessionManager.RemoteUserInfo(this.mServiceFwk.getCurrentBrowserInfo());
            } else {
                return MediaBrowserServiceCompat.this.mCurConnection.browserInfo;
            }
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$ServiceHandler */
    private final class ServiceHandler extends Handler {
        private final ServiceBinderImpl mServiceBinderImpl = new ServiceBinderImpl();

        ServiceHandler() {
        }

        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            switch (msg.what) {
                case 1:
                    Bundle rootHints = data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS);
                    MediaSessionCompat.ensureClassLoader(rootHints);
                    this.mServiceBinderImpl.connect(data.getString(MediaBrowserProtocol.DATA_PACKAGE_NAME), data.getInt(MediaBrowserProtocol.DATA_CALLING_PID), data.getInt(MediaBrowserProtocol.DATA_CALLING_UID), rootHints, new ServiceCallbacksCompat(msg.replyTo));
                    return;
                case 2:
                    this.mServiceBinderImpl.disconnect(new ServiceCallbacksCompat(msg.replyTo));
                    return;
                case 3:
                    Bundle options = data.getBundle(MediaBrowserProtocol.DATA_OPTIONS);
                    MediaSessionCompat.ensureClassLoader(options);
                    this.mServiceBinderImpl.addSubscription(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN), options, new ServiceCallbacksCompat(msg.replyTo));
                    return;
                case 4:
                    this.mServiceBinderImpl.removeSubscription(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN), new ServiceCallbacksCompat(msg.replyTo));
                    return;
                case 5:
                    this.mServiceBinderImpl.getMediaItem(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new ServiceCallbacksCompat(msg.replyTo));
                    return;
                case 6:
                    Bundle rootHints2 = data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS);
                    MediaSessionCompat.ensureClassLoader(rootHints2);
                    this.mServiceBinderImpl.registerCallbacks(new ServiceCallbacksCompat(msg.replyTo), data.getString(MediaBrowserProtocol.DATA_PACKAGE_NAME), data.getInt(MediaBrowserProtocol.DATA_CALLING_PID), data.getInt(MediaBrowserProtocol.DATA_CALLING_UID), rootHints2);
                    return;
                case 7:
                    this.mServiceBinderImpl.unregisterCallbacks(new ServiceCallbacksCompat(msg.replyTo));
                    return;
                case 8:
                    Bundle searchExtras = data.getBundle(MediaBrowserProtocol.DATA_SEARCH_EXTRAS);
                    MediaSessionCompat.ensureClassLoader(searchExtras);
                    this.mServiceBinderImpl.search(data.getString(MediaBrowserProtocol.DATA_SEARCH_QUERY), searchExtras, (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new ServiceCallbacksCompat(msg.replyTo));
                    return;
                case 9:
                    Bundle customActionExtras = data.getBundle(MediaBrowserProtocol.DATA_CUSTOM_ACTION_EXTRAS);
                    MediaSessionCompat.ensureClassLoader(customActionExtras);
                    this.mServiceBinderImpl.sendCustomAction(data.getString(MediaBrowserProtocol.DATA_CUSTOM_ACTION), customActionExtras, (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new ServiceCallbacksCompat(msg.replyTo));
                    return;
                default:
                    Log.w(MediaBrowserServiceCompat.TAG, "Unhandled message: " + msg + "\n  Service version: " + 2 + "\n  Client version: " + msg.arg1);
                    return;
            }
        }

        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            Bundle data = msg.getData();
            data.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            data.putInt(MediaBrowserProtocol.DATA_CALLING_UID, Binder.getCallingUid());
            int pid = Binder.getCallingPid();
            if (pid > 0) {
                data.putInt(MediaBrowserProtocol.DATA_CALLING_PID, pid);
            } else if (!data.containsKey(MediaBrowserProtocol.DATA_CALLING_PID)) {
                data.putInt(MediaBrowserProtocol.DATA_CALLING_PID, -1);
            }
            return super.sendMessageAtTime(msg, uptimeMillis);
        }

        public void postOrRun(Runnable r) {
            if (Thread.currentThread() == getLooper().getThread()) {
                r.run();
            } else {
                post(r);
            }
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$ConnectionRecord */
    private class ConnectionRecord implements IBinder.DeathRecipient {
        public final MediaSessionManager.RemoteUserInfo browserInfo;
        public final ServiceCallbacks callbacks;
        public final int pid;
        public final String pkg;
        public final Bundle rootHints;
        public final HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions = new HashMap<>();
        public final int uid;
        public BrowserRoot root;

        ConnectionRecord(String pkg2, int pid2, int uid2, Bundle rootHints2, ServiceCallbacks callback) {
            this.pkg = pkg2;
            this.pid = pid2;
            this.uid = uid2;
            this.browserInfo = new MediaSessionManager.RemoteUserInfo(pkg2, pid2, uid2);
            this.rootHints = rootHints2;
            this.callbacks = callback;
        }

        public void binderDied() {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
                public void run() {
                    MediaBrowserServiceCompat.this.mConnections.remove(ConnectionRecord.this.callbacks.asBinder());
                }
            });
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$ServiceBinderImpl */
    private class ServiceBinderImpl {
        ServiceBinderImpl() {
        }

        public void connect(String pkg, int pid, int uid, Bundle rootHints, ServiceCallbacks callbacks) {
            if (MediaBrowserServiceCompat.this.isValidPackage(pkg, uid)) {
                final ServiceCallbacks serviceCallbacks = callbacks;
                final String str = pkg;
                final int i = pid;
                final int i2 = uid;
                final Bundle bundle = rootHints;
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                    public void run() {
                        IBinder b = serviceCallbacks.asBinder();
                        MediaBrowserServiceCompat.this.mConnections.remove(b);
                        ConnectionRecord connectionRecord = new ConnectionRecord(str, i, i2, bundle, serviceCallbacks);
                        MediaBrowserServiceCompat.this.mCurConnection = connectionRecord;
                        connectionRecord.root = MediaBrowserServiceCompat.this.onGetRoot(str, i2, bundle);
                        MediaBrowserServiceCompat.this.mCurConnection = null;
                        if (connectionRecord.root == null) {
                            Log.i(MediaBrowserServiceCompat.TAG, "No root for client " + str + " from service " + getClass().getName());
                            try {
                                serviceCallbacks.onConnectFailed();
                            } catch (RemoteException e) {
                                Log.w(MediaBrowserServiceCompat.TAG, "Calling onConnectFailed() failed. Ignoring. pkg=" + str);
                            }
                        } else {
                            try {
                                MediaBrowserServiceCompat.this.mConnections.put(b, connectionRecord);
                                b.linkToDeath(connectionRecord, 0);
                                if (MediaBrowserServiceCompat.this.mSession != null) {
                                    serviceCallbacks.onConnect(connectionRecord.root.getRootId(), MediaBrowserServiceCompat.this.mSession, connectionRecord.root.getExtras());
                                }
                            } catch (RemoteException e2) {
                                Log.w(MediaBrowserServiceCompat.TAG, "Calling onConnect() failed. Dropping client. pkg=" + str);
                                MediaBrowserServiceCompat.this.mConnections.remove(b);
                            }
                        }
                    }
                });
                return;
            }
            throw new IllegalArgumentException("Package/uid mismatch: uid=" + uid + " package=" + pkg);
        }

        public void disconnect(final ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                public void run() {
                    ConnectionRecord old = MediaBrowserServiceCompat.this.mConnections.remove(callbacks.asBinder());
                    if (old != null) {
                        old.callbacks.asBinder().unlinkToDeath(old, 0);
                    }
                }
            });
        }

        public void addSubscription(String id, IBinder token, Bundle options, ServiceCallbacks callbacks) {
            final ServiceCallbacks serviceCallbacks = callbacks;
            final String str = id;
            final IBinder iBinder = token;
            final Bundle bundle = options;
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                public void run() {
                    ConnectionRecord connection = MediaBrowserServiceCompat.this.mConnections.get(serviceCallbacks.asBinder());
                    if (connection == null) {
                        Log.w(MediaBrowserServiceCompat.TAG, "addSubscription for callback that isn't registered id=" + str);
                        return;
                    }
                    MediaBrowserServiceCompat.this.addSubscription(str, connection, iBinder, bundle);
                }
            });
        }

        public void removeSubscription(final String id, final IBinder token, final ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                public void run() {
                    ConnectionRecord connection = MediaBrowserServiceCompat.this.mConnections.get(callbacks.asBinder());
                    if (connection == null) {
                        Log.w(MediaBrowserServiceCompat.TAG, "removeSubscription for callback that isn't registered id=" + id);
                    } else if (!MediaBrowserServiceCompat.this.removeSubscription(id, connection, token)) {
                        Log.w(MediaBrowserServiceCompat.TAG, "removeSubscription called for " + id + " which is not subscribed");
                    }
                }
            });
        }

        public void getMediaItem(final String mediaId, final ResultReceiver receiver, final ServiceCallbacks callbacks) {
            if (!TextUtils.isEmpty(mediaId) && receiver != null) {
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                    public void run() {
                        ConnectionRecord connection = MediaBrowserServiceCompat.this.mConnections.get(callbacks.asBinder());
                        if (connection == null) {
                            Log.w(MediaBrowserServiceCompat.TAG, "getMediaItem for callback that isn't registered id=" + mediaId);
                            return;
                        }
                        MediaBrowserServiceCompat.this.performLoadItem(mediaId, connection, receiver);
                    }
                });
            }
        }

        public void registerCallbacks(ServiceCallbacks callbacks, String pkg, int pid, int uid, Bundle rootHints) {
            final ServiceCallbacks serviceCallbacks = callbacks;
            final int i = uid;
            final String str = pkg;
            final int i2 = pid;
            final Bundle bundle = rootHints;
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                public void run() {
                    IBinder b = serviceCallbacks.asBinder();
                    MediaBrowserServiceCompat.this.mConnections.remove(b);
                    ConnectionRecord connection = null;
                    Iterator<ConnectionRecord> iter = MediaBrowserServiceCompat.this.mPendingConnections.iterator();
                    while (iter.hasNext()) {
                        ConnectionRecord pendingConnection = iter.next();
                        if (pendingConnection.uid == i) {
                            if (TextUtils.isEmpty(str) || i2 <= 0) {
                                connection = new ConnectionRecord(pendingConnection.pkg, pendingConnection.pid, pendingConnection.uid, bundle, serviceCallbacks);
                            }
                            iter.remove();
                        }
                    }
                    if (connection == null) {
                        connection = new ConnectionRecord(str, i2, i, bundle, serviceCallbacks);
                    }
                    MediaBrowserServiceCompat.this.mConnections.put(b, connection);
                    try {
                        b.linkToDeath(connection, 0);
                    } catch (RemoteException e) {
                        Log.w(MediaBrowserServiceCompat.TAG, "IBinder is already dead.");
                    }
                }
            });
        }

        public void unregisterCallbacks(final ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                public void run() {
                    IBinder b = callbacks.asBinder();
                    ConnectionRecord old = MediaBrowserServiceCompat.this.mConnections.remove(b);
                    if (old != null) {
                        b.unlinkToDeath(old, 0);
                    }
                }
            });
        }

        public void search(String query, Bundle extras, ResultReceiver receiver, ServiceCallbacks callbacks) {
            if (!TextUtils.isEmpty(query) && receiver != null) {
                final ServiceCallbacks serviceCallbacks = callbacks;
                final String str = query;
                final Bundle bundle = extras;
                final ResultReceiver resultReceiver = receiver;
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                    public void run() {
                        ConnectionRecord connection = MediaBrowserServiceCompat.this.mConnections.get(serviceCallbacks.asBinder());
                        if (connection == null) {
                            Log.w(MediaBrowserServiceCompat.TAG, "search for callback that isn't registered query=" + str);
                            return;
                        }
                        MediaBrowserServiceCompat.this.performSearch(str, bundle, connection, resultReceiver);
                    }
                });
            }
        }

        public void sendCustomAction(String action, Bundle extras, ResultReceiver receiver, ServiceCallbacks callbacks) {
            if (!TextUtils.isEmpty(action) && receiver != null) {
                final ServiceCallbacks serviceCallbacks = callbacks;
                final String str = action;
                final Bundle bundle = extras;
                final ResultReceiver resultReceiver = receiver;
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                    public void run() {
                        ConnectionRecord connection = MediaBrowserServiceCompat.this.mConnections.get(serviceCallbacks.asBinder());
                        if (connection == null) {
                            Log.w(MediaBrowserServiceCompat.TAG, "sendCustomAction for callback that isn't registered action=" + str + ", extras=" + bundle);
                            return;
                        }
                        MediaBrowserServiceCompat.this.performCustomAction(str, bundle, connection, resultReceiver);
                    }
                });
            }
        }
    }
}
