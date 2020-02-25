package android.support.p001v4.media;

import android.content.Context;
import android.media.session.MediaSessionManager;
import android.support.annotation.RequiresApi;

@RequiresApi(28)
        /* renamed from: android.support.v4.media.MediaSessionManagerImplApi28 */
class MediaSessionManagerImplApi28 extends MediaSessionManagerImplApi21 {
    MediaSessionManager mObject;

    MediaSessionManagerImplApi28(Context context) {
        super(context);
        this.mObject = (MediaSessionManager) context.getSystemService("media_session");
    }

    public boolean isTrustedForMediaControl(MediaSessionManager.RemoteUserInfoImpl userInfo) {
        return super.isTrustedForMediaControl(userInfo);
    }

    /* renamed from: android.support.v4.media.MediaSessionManagerImplApi28$RemoteUserInfoImplApi28 */
    static final class RemoteUserInfoImplApi28 extends MediaSessionManagerImplBase.RemoteUserInfoImplBase {
        final MediaSessionManager.RemoteUserInfo mObject;

        RemoteUserInfoImplApi28(String packageName, int pid, int uid) {
            super(packageName, pid, uid);
            this.mObject = new MediaSessionManager.RemoteUserInfo(packageName, pid, uid);
        }

        RemoteUserInfoImplApi28(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            super(remoteUserInfo.getPackageName(), remoteUserInfo.getPid(), remoteUserInfo.getUid());
            this.mObject = remoteUserInfo;
        }
    }
}
