package com.google.android.gsf;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGoogleLoginService extends IInterface {
    GoogleLoginCredentialsResult blockingGetCredentials(String str, String str2, boolean z) throws RemoteException;

    void deleteAllAccounts() throws RemoteException;

    void deleteOneAccount(String str) throws RemoteException;

    String getAccount(boolean z) throws RemoteException;

    String[] getAccounts() throws RemoteException;

    long getAndroidId() throws RemoteException;

    String getPrimaryAccount() throws RemoteException;

    void invalidateAuthToken(String str) throws RemoteException;

    String peekCredentials(String str, String str2) throws RemoteException;

    void saveAuthToken(String str, String str2, String str3) throws RemoteException;

    void saveNewAccount(LoginData loginData) throws RemoteException;

    void saveUsernameAndPassword(String str, String str2, int i) throws RemoteException;

    void tryNewAccount(LoginData loginData) throws RemoteException;

    void updatePassword(LoginData loginData) throws RemoteException;

    boolean verifyStoredPassword(String str, String str2) throws RemoteException;

    int waitForAndroidId() throws RemoteException;

    public static abstract class Stub extends Binder implements IGoogleLoginService {
        static final int TRANSACTION_blockingGetCredentials = 5;
        static final int TRANSACTION_deleteAllAccounts = 15;
        static final int TRANSACTION_deleteOneAccount = 14;
        static final int TRANSACTION_getAccount = 3;
        static final int TRANSACTION_getAccounts = 1;
        static final int TRANSACTION_getAndroidId = 7;
        static final int TRANSACTION_getPrimaryAccount = 2;
        static final int TRANSACTION_invalidateAuthToken = 6;
        static final int TRANSACTION_peekCredentials = 4;
        static final int TRANSACTION_saveAuthToken = 10;
        static final int TRANSACTION_saveNewAccount = 9;
        static final int TRANSACTION_saveUsernameAndPassword = 13;
        static final int TRANSACTION_tryNewAccount = 8;
        static final int TRANSACTION_updatePassword = 11;
        static final int TRANSACTION_verifyStoredPassword = 12;
        static final int TRANSACTION_waitForAndroidId = 16;
        private static final String DESCRIPTOR = "com.google.android.gsf.IGoogleLoginService";

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IGoogleLoginService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IGoogleLoginService)) {
                return new Proxy(obj);
            }
            return (IGoogleLoginService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            LoginData _arg0;
            LoginData _arg02;
            LoginData _arg03;
            if (code != 1598968902) {
                boolean _arg04 = false;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        String[] _result = getAccounts();
                        reply.writeNoException();
                        reply.writeStringArray(_result);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        String _result2 = getPrimaryAccount();
                        reply.writeNoException();
                        reply.writeString(_result2);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg04 = true;
                        }
                        String _result3 = getAccount(_arg04);
                        reply.writeNoException();
                        reply.writeString(_result3);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        String _result4 = peekCredentials(data.readString(), data.readString());
                        reply.writeNoException();
                        reply.writeString(_result4);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        GoogleLoginCredentialsResult _result5 = blockingGetCredentials(data.readString(), data.readString(), data.readInt() != 0);
                        reply.writeNoException();
                        if (_result5 != null) {
                            reply.writeInt(1);
                            _result5.writeToParcel(reply, 1);
                        } else {
                            reply.writeInt(0);
                        }
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        invalidateAuthToken(data.readString());
                        reply.writeNoException();
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        long _result6 = getAndroidId();
                        reply.writeNoException();
                        reply.writeLong(_result6);
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = LoginData.CREATOR.createFromParcel(data);
                        } else {
                            _arg0 = null;
                        }
                        tryNewAccount(_arg0);
                        reply.writeNoException();
                        if (_arg0 != null) {
                            reply.writeInt(1);
                            _arg0.writeToParcel(reply, 1);
                        } else {
                            reply.writeInt(0);
                        }
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg02 = LoginData.CREATOR.createFromParcel(data);
                        } else {
                            _arg02 = null;
                        }
                        saveNewAccount(_arg02);
                        reply.writeNoException();
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        saveAuthToken(data.readString(), data.readString(), data.readString());
                        reply.writeNoException();
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg03 = LoginData.CREATOR.createFromParcel(data);
                        } else {
                            _arg03 = null;
                        }
                        updatePassword(_arg03);
                        reply.writeNoException();
                        if (_arg03 != null) {
                            reply.writeInt(1);
                            _arg03.writeToParcel(reply, 1);
                        } else {
                            reply.writeInt(0);
                        }
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result7 = verifyStoredPassword(data.readString(), data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result7);
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        saveUsernameAndPassword(data.readString(), data.readString(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        deleteOneAccount(data.readString());
                        reply.writeNoException();
                        return true;
                    case 15:
                        data.enforceInterface(DESCRIPTOR);
                        deleteAllAccounts();
                        reply.writeNoException();
                        return true;
                    case 16:
                        data.enforceInterface(DESCRIPTOR);
                        int _result8 = waitForAndroidId();
                        reply.writeNoException();
                        reply.writeInt(_result8);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IGoogleLoginService {
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

            public String[] getAccounts() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createStringArray();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getPrimaryAccount() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getAccount(boolean requireGoogle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(requireGoogle ? 1 : 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String peekCredentials(String username, String service) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(username);
                    _data.writeString(service);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public GoogleLoginCredentialsResult blockingGetCredentials(String username, String service, boolean notifyAuthFailure) throws RemoteException {
                GoogleLoginCredentialsResult _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(username);
                    _data.writeString(service);
                    _data.writeInt(notifyAuthFailure ? 1 : 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = GoogleLoginCredentialsResult.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void invalidateAuthToken(String authTokenToInvalidate) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(authTokenToInvalidate);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getAndroidId() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readLong();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void tryNewAccount(LoginData data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        data.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void saveNewAccount(LoginData data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void saveAuthToken(String username, String service, String authToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(username);
                    _data.writeString(service);
                    _data.writeString(authToken);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updatePassword(LoginData data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        data.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean verifyStoredPassword(String username, String password) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(username);
                    _data.writeString(password);
                    boolean _result = false;
                    this.mRemote.transact(12, _data, _reply, 0);
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

            public void saveUsernameAndPassword(String username, String password, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(username);
                    _data.writeString(password);
                    _data.writeInt(flags);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deleteOneAccount(String username) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(username);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deleteAllAccounts() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int waitForAndroidId() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
