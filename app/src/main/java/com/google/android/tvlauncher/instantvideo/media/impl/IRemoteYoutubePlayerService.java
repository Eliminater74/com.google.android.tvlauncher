package com.google.android.tvlauncher.instantvideo.media.impl;

import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;
import com.google.android.aidl.BaseProxy;
import com.google.android.aidl.BaseStub;
import com.google.android.aidl.Codecs;
import com.google.android.tvlauncher.instantvideo.media.impl.IRemoteYoutubePlayerClient;

public interface IRemoteYoutubePlayerService extends IInterface {
    void createSession(Surface surface, int i, int i2, IRemoteYoutubePlayerClient iRemoteYoutubePlayerClient) throws RemoteException;

    void destroySession(String str) throws RemoteException;

    void setSize(String str, int i, int i2) throws RemoteException;

    void setVolume(String str, float f) throws RemoteException;

    void start(String str, Uri uri, float f) throws RemoteException;

    public static abstract class Stub extends BaseStub implements IRemoteYoutubePlayerService {
        private static final String DESCRIPTOR = "com.google.android.tvlauncher.instantvideo.media.impl.IRemoteYoutubePlayerService";
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_destroySession = 2;
        static final int TRANSACTION_setSize = 5;
        static final int TRANSACTION_setVolume = 4;
        static final int TRANSACTION_start = 3;

        public Stub() {
            super(DESCRIPTOR);
        }

        public static IRemoteYoutubePlayerService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin instanceof IRemoteYoutubePlayerService) {
                return (IRemoteYoutubePlayerService) iin;
            }
            return new Proxy(obj);
        }

        /* access modifiers changed from: protected */
        public boolean dispatchTransaction(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1) {
                createSession((Surface) Codecs.createParcelable(data, Surface.CREATOR), data.readInt(), data.readInt(), IRemoteYoutubePlayerClient.Stub.asInterface(data.readStrongBinder()));
            } else if (code == 2) {
                destroySession(data.readString());
            } else if (code == 3) {
                start(data.readString(), (Uri) Codecs.createParcelable(data, Uri.CREATOR), data.readFloat());
            } else if (code == 4) {
                setVolume(data.readString(), data.readFloat());
            } else if (code != 5) {
                return false;
            } else {
                setSize(data.readString(), data.readInt(), data.readInt());
            }
            reply.writeNoException();
            return true;
        }

        public static class Proxy extends BaseProxy implements IRemoteYoutubePlayerService {
            Proxy(IBinder remote) {
                super(remote, Stub.DESCRIPTOR);
            }

            public void createSession(Surface suface, int width, int height, IRemoteYoutubePlayerClient client) throws RemoteException {
                Parcel data = obtainAndWriteInterfaceToken();
                Codecs.writeParcelable(data, suface);
                data.writeInt(width);
                data.writeInt(height);
                Codecs.writeStrongBinder(data, client);
                transactAndReadExceptionReturnVoid(1, data);
            }

            public void destroySession(String sessionToken) throws RemoteException {
                Parcel data = obtainAndWriteInterfaceToken();
                data.writeString(sessionToken);
                transactAndReadExceptionReturnVoid(2, data);
            }

            public void start(String sessionToken, Uri youtubeUrl, float volume) throws RemoteException {
                Parcel data = obtainAndWriteInterfaceToken();
                data.writeString(sessionToken);
                Codecs.writeParcelable(data, youtubeUrl);
                data.writeFloat(volume);
                transactAndReadExceptionReturnVoid(3, data);
            }

            public void setVolume(String sessionToken, float volume) throws RemoteException {
                Parcel data = obtainAndWriteInterfaceToken();
                data.writeString(sessionToken);
                data.writeFloat(volume);
                transactAndReadExceptionReturnVoid(4, data);
            }

            public void setSize(String sessionToken, int width, int height) throws RemoteException {
                Parcel data = obtainAndWriteInterfaceToken();
                data.writeString(sessionToken);
                data.writeInt(width);
                data.writeInt(height);
                transactAndReadExceptionReturnVoid(5, data);
            }
        }
    }
}
