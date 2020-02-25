package android.support.p001v4.media;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.util.Log;

/* renamed from: android.support.v4.media.MediaSessionManager */
public final class MediaSessionManager {
    static final String TAG = "MediaSessionManager";
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final Object sLock = new Object();
    private static volatile MediaSessionManager sSessionManager;
    MediaSessionManagerImpl mImpl;

    private MediaSessionManager(Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mImpl = new MediaSessionManagerImplApi28(context);
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaSessionManagerImplApi21(context);
        } else {
            this.mImpl = new MediaSessionManagerImplBase(context);
        }
    }

    @NonNull
    public static MediaSessionManager getSessionManager(@NonNull Context context) {
        MediaSessionManager mediaSessionManager;
        if (context != null) {
            synchronized (sLock) {
                if (sSessionManager == null) {
                    sSessionManager = new MediaSessionManager(context.getApplicationContext());
                }
                mediaSessionManager = sSessionManager;
            }
            return mediaSessionManager;
        }
        throw new IllegalArgumentException("context cannot be null");
    }

    public boolean isTrustedForMediaControl(@NonNull RemoteUserInfo userInfo) {
        if (userInfo != null) {
            return this.mImpl.isTrustedForMediaControl(userInfo.mImpl);
        }
        throw new IllegalArgumentException("userInfo should not be null");
    }

    /* access modifiers changed from: package-private */
    public Context getContext() {
        return this.mImpl.getContext();
    }

    /* renamed from: android.support.v4.media.MediaSessionManager$MediaSessionManagerImpl */
    interface MediaSessionManagerImpl {
        Context getContext();

        boolean isTrustedForMediaControl(RemoteUserInfoImpl remoteUserInfoImpl);
    }

    /* renamed from: android.support.v4.media.MediaSessionManager$RemoteUserInfoImpl */
    interface RemoteUserInfoImpl {
        String getPackageName();

        int getPid();

        int getUid();
    }

    /* renamed from: android.support.v4.media.MediaSessionManager$RemoteUserInfo */
    public static final class RemoteUserInfo {
        public static final String LEGACY_CONTROLLER = "android.media.session.MediaController";
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public static final int UNKNOWN_PID = -1;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public static final int UNKNOWN_UID = -1;
        RemoteUserInfoImpl mImpl;

        public RemoteUserInfo(@NonNull String packageName, int pid, int uid) {
            if (Build.VERSION.SDK_INT >= 28) {
                this.mImpl = new MediaSessionManagerImplApi28.RemoteUserInfoImplApi28(packageName, pid, uid);
            } else {
                this.mImpl = new MediaSessionManagerImplBase.RemoteUserInfoImplBase(packageName, pid, uid);
            }
        }

        @RequiresApi(28)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteUserInfo(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            this.mImpl = new MediaSessionManagerImplApi28.RemoteUserInfoImplApi28(remoteUserInfo);
        }

        @NonNull
        public String getPackageName() {
            return this.mImpl.getPackageName();
        }

        public int getPid() {
            return this.mImpl.getPid();
        }

        public int getUid() {
            return this.mImpl.getUid();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RemoteUserInfo)) {
                return false;
            }
            return this.mImpl.equals(((RemoteUserInfo) obj).mImpl);
        }

        public int hashCode() {
            return this.mImpl.hashCode();
        }
    }
}
