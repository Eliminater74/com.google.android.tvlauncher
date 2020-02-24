package com.google.android.gtalkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gtalkservice.IGTalkConnection;
import com.google.android.gtalkservice.IGTalkConnectionListener;
import com.google.android.gtalkservice.IImSession;
import java.util.List;

public interface IGTalkService extends IInterface {
    void createGTalkConnection(String str, IGTalkConnectionListener iGTalkConnectionListener) throws RemoteException;

    void dismissAllNotifications() throws RemoteException;

    void dismissNotificationFor(String str, long j) throws RemoteException;

    void dismissNotificationsForAccount(long j) throws RemoteException;

    List getActiveConnections() throws RemoteException;

    IGTalkConnection getConnectionForUser(String str) throws RemoteException;

    IGTalkConnection getDefaultConnection() throws RemoteException;

    boolean getDeviceStorageLow() throws RemoteException;

    IImSession getImSessionForAccountId(long j) throws RemoteException;

    String printDiagnostics() throws RemoteException;

    void setTalkForegroundState() throws RemoteException;

    public static abstract class Stub extends Binder implements IGTalkService {
        private static final String DESCRIPTOR = "com.google.android.gtalkservice.IGTalkService";
        static final int TRANSACTION_createGTalkConnection = 1;
        static final int TRANSACTION_dismissAllNotifications = 6;
        static final int TRANSACTION_dismissNotificationFor = 8;
        static final int TRANSACTION_dismissNotificationsForAccount = 7;
        static final int TRANSACTION_getActiveConnections = 2;
        static final int TRANSACTION_getConnectionForUser = 3;
        static final int TRANSACTION_getDefaultConnection = 4;
        static final int TRANSACTION_getDeviceStorageLow = 9;
        static final int TRANSACTION_getImSessionForAccountId = 5;
        static final int TRANSACTION_printDiagnostics = 10;
        static final int TRANSACTION_setTalkForegroundState = 11;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IGTalkService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IGTalkService)) {
                return new Proxy(obj);
            }
            return (IGTalkService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                IBinder iBinder = null;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        createGTalkConnection(data.readString(), IGTalkConnectionListener.Stub.asInterface(data.readStrongBinder()));
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        List _result = getActiveConnections();
                        reply.writeNoException();
                        reply.writeList(_result);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        IGTalkConnection _result2 = getConnectionForUser(data.readString());
                        reply.writeNoException();
                        if (_result2 != null) {
                            iBinder = _result2.asBinder();
                        }
                        reply.writeStrongBinder(iBinder);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        IGTalkConnection _result3 = getDefaultConnection();
                        reply.writeNoException();
                        if (_result3 != null) {
                            iBinder = _result3.asBinder();
                        }
                        reply.writeStrongBinder(iBinder);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        IImSession _result4 = getImSessionForAccountId(data.readLong());
                        reply.writeNoException();
                        if (_result4 != null) {
                            iBinder = _result4.asBinder();
                        }
                        reply.writeStrongBinder(iBinder);
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        dismissAllNotifications();
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        dismissNotificationsForAccount(data.readLong());
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        dismissNotificationFor(data.readString(), data.readLong());
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result5 = getDeviceStorageLow();
                        reply.writeNoException();
                        reply.writeInt(_result5);
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        String _result6 = printDiagnostics();
                        reply.writeNoException();
                        reply.writeString(_result6);
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        setTalkForegroundState();
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IGTalkService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void createGTalkConnection(String username, IGTalkConnectionListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(username);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            /* JADX INFO: Multiple debug info for r2v5 java.util.ArrayList: [D('cl' java.lang.ClassLoader), D('_result' java.util.List)] */
            public List getActiveConnections() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readArrayList(getClass().getClassLoader());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IGTalkConnection getConnectionForUser(String username) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(username);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    return IGTalkConnection.Stub.asInterface(_reply.readStrongBinder());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IGTalkConnection getDefaultConnection() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    return IGTalkConnection.Stub.asInterface(_reply.readStrongBinder());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImSession getImSessionForAccountId(long accountId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(accountId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    return IImSession.Stub.asInterface(_reply.readStrongBinder());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void dismissAllNotifications() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void dismissNotificationsForAccount(long accountId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(accountId);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void dismissNotificationFor(String contact, long accountId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(contact);
                    _data.writeLong(accountId);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public boolean getDeviceStorageLow() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String printDiagnostics() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTalkForegroundState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
