package com.google.android.gtalkservice;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gtalkservice.IChatListener;

public interface IChatSession extends IInterface {
    void addRemoteChatListener(IChatListener iChatListener) throws RemoteException;

    void clearChatHistory(Uri uri) throws RemoteException;

    void ensureNonZeroLastMessageDate() throws RemoteException;

    boolean getLightweightNotify() throws RemoteException;

    String[] getParticipants() throws RemoteException;

    String getUnsentComposedMessage() throws RemoteException;

    void inviteContact(String str) throws RemoteException;

    boolean isGroupChat() throws RemoteException;

    boolean isOffTheRecord() throws RemoteException;

    void leave() throws RemoteException;

    void markAsRead() throws RemoteException;

    void removeRemoteChatListener(IChatListener iChatListener) throws RemoteException;

    void reportEndCause(String str, boolean z, int i) throws RemoteException;

    void reportMissedCall(String str, String str2, boolean z, boolean z2) throws RemoteException;

    void saveUnsentComposedMessage(String str) throws RemoteException;

    void sendChatMessage(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IChatSession {
        private static final String DESCRIPTOR = "com.google.android.gtalkservice.IChatSession";
        static final int TRANSACTION_addRemoteChatListener = 9;
        static final int TRANSACTION_clearChatHistory = 16;
        static final int TRANSACTION_ensureNonZeroLastMessageDate = 15;
        static final int TRANSACTION_getLightweightNotify = 12;
        static final int TRANSACTION_getParticipants = 3;
        static final int TRANSACTION_getUnsentComposedMessage = 8;
        static final int TRANSACTION_inviteContact = 4;
        static final int TRANSACTION_isGroupChat = 1;
        static final int TRANSACTION_isOffTheRecord = 11;
        static final int TRANSACTION_leave = 5;
        static final int TRANSACTION_markAsRead = 2;
        static final int TRANSACTION_removeRemoteChatListener = 10;
        static final int TRANSACTION_reportEndCause = 13;
        static final int TRANSACTION_reportMissedCall = 14;
        static final int TRANSACTION_saveUnsentComposedMessage = 7;
        static final int TRANSACTION_sendChatMessage = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IChatSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IChatSession)) {
                return new Proxy(obj);
            }
            return (IChatSession) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Uri _arg0;
            if (code != 1598968902) {
                boolean _arg3 = false;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result = isGroupChat();
                        reply.writeNoException();
                        reply.writeInt(_result);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        markAsRead();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        String[] _result2 = getParticipants();
                        reply.writeNoException();
                        reply.writeStringArray(_result2);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        inviteContact(data.readString());
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        leave();
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        sendChatMessage(data.readString());
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        saveUnsentComposedMessage(data.readString());
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        String _result3 = getUnsentComposedMessage();
                        reply.writeNoException();
                        reply.writeString(_result3);
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        addRemoteChatListener(IChatListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        removeRemoteChatListener(IChatListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result4 = isOffTheRecord();
                        reply.writeNoException();
                        reply.writeInt(_result4);
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result5 = getLightweightNotify();
                        reply.writeNoException();
                        reply.writeInt(_result5);
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg02 = data.readString();
                        if (data.readInt() != 0) {
                            _arg3 = true;
                        }
                        reportEndCause(_arg02, _arg3, data.readInt());
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg03 = data.readString();
                        String _arg1 = data.readString();
                        boolean _arg2 = data.readInt() != 0;
                        if (data.readInt() != 0) {
                            _arg3 = true;
                        }
                        reportMissedCall(_arg03, _arg1, _arg2, _arg3);
                        return true;
                    case 15:
                        data.enforceInterface(DESCRIPTOR);
                        ensureNonZeroLastMessageDate();
                        return true;
                    case 16:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = (Uri) Uri.CREATOR.createFromParcel(data);
                        } else {
                            _arg0 = null;
                        }
                        clearChatHistory(_arg0);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IChatSession {
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

            public boolean isGroupChat() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    this.mRemote.transact(1, _data, _reply, 0);
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

            public void markAsRead() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public String[] getParticipants() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createStringArray();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void inviteContact(String to) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(to);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void leave() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void sendChatMessage(String message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(message);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void saveUnsentComposedMessage(String unsentMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(unsentMessage);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public String getUnsentComposedMessage() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addRemoteChatListener(IChatListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeRemoteChatListener(IChatListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isOffTheRecord() throws RemoteException {
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

            public boolean getLightweightNotify() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
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

            public void reportEndCause(String nickname, boolean video, int endCause) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(nickname);
                    _data.writeInt(video ? 1 : 0);
                    _data.writeInt(endCause);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void reportMissedCall(String remoteBareJid, String nickname, boolean video, boolean noWifi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(remoteBareJid);
                    _data.writeString(nickname);
                    int i = 0;
                    _data.writeInt(video ? 1 : 0);
                    if (noWifi) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void ensureNonZeroLastMessageDate() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void clearChatHistory(Uri queryUri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (queryUri != null) {
                        _data.writeInt(1);
                        queryUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
