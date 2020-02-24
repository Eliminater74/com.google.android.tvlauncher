package com.google.android.tvlauncher.instantvideo.media.impl;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.aidl.BaseProxy;
import com.google.android.aidl.BaseStub;

public interface IRemoteYoutubePlayerClient extends IInterface {
    void onSessionCreated(String str) throws RemoteException;

    void onVideoAvailable() throws RemoteException;

    void onVideoEnded() throws RemoteException;

    void onVideoError() throws RemoteException;

    public static abstract class Stub extends BaseStub implements IRemoteYoutubePlayerClient {
        private static final String DESCRIPTOR = "com.google.android.tvlauncher.instantvideo.media.impl.IRemoteYoutubePlayerClient";
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onVideoAvailable = 2;
        static final int TRANSACTION_onVideoEnded = 4;
        static final int TRANSACTION_onVideoError = 3;

        public Stub() {
            super(DESCRIPTOR);
        }

        public static IRemoteYoutubePlayerClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin instanceof IRemoteYoutubePlayerClient) {
                return (IRemoteYoutubePlayerClient) iin;
            }
            return new Proxy(obj);
        }

        /* access modifiers changed from: protected */
        public boolean dispatchTransaction(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1) {
                onSessionCreated(data.readString());
            } else if (code == 2) {
                onVideoAvailable();
            } else if (code == 3) {
                onVideoError();
            } else if (code != 4) {
                return false;
            } else {
                onVideoEnded();
            }
            return true;
        }

        public static class Proxy extends BaseProxy implements IRemoteYoutubePlayerClient {
            Proxy(IBinder remote) {
                super(remote, Stub.DESCRIPTOR);
            }

            public void onSessionCreated(String sessionToken) throws RemoteException {
                Parcel data = obtainAndWriteInterfaceToken();
                data.writeString(sessionToken);
                transactOneway(1, data);
            }

            public void onVideoAvailable() throws RemoteException {
                transactOneway(2, obtainAndWriteInterfaceToken());
            }

            public void onVideoError() throws RemoteException {
                transactOneway(3, obtainAndWriteInterfaceToken());
            }

            public void onVideoEnded() throws RemoteException {
                transactOneway(4, obtainAndWriteInterfaceToken());
            }
        }
    }
}
