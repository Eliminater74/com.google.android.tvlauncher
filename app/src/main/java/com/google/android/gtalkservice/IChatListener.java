package com.google.android.gtalkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IChatListener extends IInterface {
    void callEnded() throws RemoteException;

    void chatClosed(String str) throws RemoteException;

    void chatRead(String str) throws RemoteException;

    void convertedToGroupChat(String str, String str2, long j) throws RemoteException;

    void missedCall() throws RemoteException;

    void newMessageReceived(String str, String str2, boolean z) throws RemoteException;

    void newMessageSent(String str) throws RemoteException;

    void participantJoined(String str, String str2) throws RemoteException;

    void participantLeft(String str, String str2) throws RemoteException;

    boolean useLightweightNotify() throws RemoteException;

    void willConvertToGroupChat(String str, String str2, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IChatListener {
        static final int TRANSACTION_callEnded = 4;
        static final int TRANSACTION_chatClosed = 6;
        static final int TRANSACTION_chatRead = 5;
        static final int TRANSACTION_convertedToGroupChat = 8;
        static final int TRANSACTION_missedCall = 3;
        static final int TRANSACTION_newMessageReceived = 1;
        static final int TRANSACTION_newMessageSent = 2;
        static final int TRANSACTION_participantJoined = 9;
        static final int TRANSACTION_participantLeft = 10;
        static final int TRANSACTION_useLightweightNotify = 11;
        static final int TRANSACTION_willConvertToGroupChat = 7;
        private static final String DESCRIPTOR = "com.google.android.gtalkservice.IChatListener";

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IChatListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IChatListener)) {
                return new Proxy(obj);
            }
            return (IChatListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        newMessageReceived(data.readString(), data.readString(), data.readInt() != 0);
                        reply.writeNoException();
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        newMessageSent(data.readString());
                        reply.writeNoException();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        missedCall();
                        reply.writeNoException();
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        callEnded();
                        reply.writeNoException();
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        chatRead(data.readString());
                        reply.writeNoException();
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        chatClosed(data.readString());
                        reply.writeNoException();
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        willConvertToGroupChat(data.readString(), data.readString(), data.readLong());
                        reply.writeNoException();
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        convertedToGroupChat(data.readString(), data.readString(), data.readLong());
                        reply.writeNoException();
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        participantJoined(data.readString(), data.readString());
                        reply.writeNoException();
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        participantLeft(data.readString(), data.readString());
                        reply.writeNoException();
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result = useLightweightNotify();
                        reply.writeNoException();
                        reply.writeInt(_result);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IChatListener {
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

            public void newMessageReceived(String from, String body, boolean notify) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(from);
                    _data.writeString(body);
                    _data.writeInt(notify ? 1 : 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void newMessageSent(String body) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(body);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void missedCall() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void callEnded() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void chatRead(String from) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(from);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void chatClosed(String from) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(from);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void willConvertToGroupChat(String oldJid, String groupChatRoom, long groupId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(oldJid);
                    _data.writeString(groupChatRoom);
                    _data.writeLong(groupId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void convertedToGroupChat(String oldJid, String groupChatRoom, long groupId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(oldJid);
                    _data.writeString(groupChatRoom);
                    _data.writeLong(groupId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void participantJoined(String groupChatRoom, String nickname) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(groupChatRoom);
                    _data.writeString(nickname);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void participantLeft(String groupChatRoom, String nickname) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(groupChatRoom);
                    _data.writeString(nickname);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean useLightweightNotify() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    this.mRemote.transact(11, _data, _reply, 0);
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
        }
    }
}
