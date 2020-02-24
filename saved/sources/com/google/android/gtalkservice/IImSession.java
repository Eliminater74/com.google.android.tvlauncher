package com.google.android.gtalkservice;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gtalkservice.IChatListener;
import com.google.android.gtalkservice.IChatSession;
import com.google.android.gtalkservice.IConnectionStateListener;
import com.google.android.gtalkservice.IGroupChatInvitationListener;
import com.google.android.gtalkservice.IJingleInfoStanzaListener;
import com.google.android.gtalkservice.IRosterListener;
import com.google.android.gtalkservice.ISessionStanzaListener;
import java.util.List;

public interface IImSession extends IInterface {
    void addConnectionStateListener(IConnectionStateListener iConnectionStateListener) throws RemoteException;

    void addContact(String str, String str2, String[] strArr) throws RemoteException;

    void addGroupChatInvitationListener(IGroupChatInvitationListener iGroupChatInvitationListener) throws RemoteException;

    void addRemoteChatListener(IChatListener iChatListener) throws RemoteException;

    void addRemoteJingleInfoStanzaListener(IJingleInfoStanzaListener iJingleInfoStanzaListener) throws RemoteException;

    void addRemoteRosterListener(IRosterListener iRosterListener) throws RemoteException;

    void addRemoteSessionStanzaListener(ISessionStanzaListener iSessionStanzaListener) throws RemoteException;

    void approveSubscriptionRequest(String str, String str2, String[] strArr) throws RemoteException;

    void blockContact(String str) throws RemoteException;

    void clearContactFlags(String str) throws RemoteException;

    void closeAllChatSessions() throws RemoteException;

    IChatSession createChatSession(String str) throws RemoteException;

    void createGroupChatSession(String str, String[] strArr) throws RemoteException;

    void declineGroupChatInvitation(String str, String str2) throws RemoteException;

    void declineSubscriptionRequest(String str) throws RemoteException;

    void editContact(String str, String str2, String[] strArr) throws RemoteException;

    long getAccountId() throws RemoteException;

    IChatSession getChatSession(String str) throws RemoteException;

    ConnectionState getConnectionState() throws RemoteException;

    String getJid() throws RemoteException;

    Presence getPresence() throws RemoteException;

    String getUsername() throws RemoteException;

    void goOffRecordInRoom(String str, boolean z) throws RemoteException;

    void goOffRecordWithContacts(List list, boolean z) throws RemoteException;

    void hideContact(String str) throws RemoteException;

    void inviteContactsToGroupchat(String str, String[] strArr) throws RemoteException;

    boolean isOffRecordWithContact(String str) throws RemoteException;

    void joinGroupChatSession(String str, String str2, String str3) throws RemoteException;

    void login(String str, boolean z) throws RemoteException;

    void logout() throws RemoteException;

    void pinContact(String str) throws RemoteException;

    void pruneOldChatSessions(long j, long j2, long j3, boolean z) throws RemoteException;

    void queryJingleInfo() throws RemoteException;

    void removeConnectionStateListener(IConnectionStateListener iConnectionStateListener) throws RemoteException;

    void removeContact(String str) throws RemoteException;

    void removeGroupChatInvitationListener(IGroupChatInvitationListener iGroupChatInvitationListener) throws RemoteException;

    void removeRemoteChatListener(IChatListener iChatListener) throws RemoteException;

    void removeRemoteJingleInfoStanzaListener(IJingleInfoStanzaListener iJingleInfoStanzaListener) throws RemoteException;

    void removeRemoteRosterListener(IRosterListener iRosterListener) throws RemoteException;

    void removeRemoteSessionStanzaListener(ISessionStanzaListener iSessionStanzaListener) throws RemoteException;

    void requestBatchedBuddyPresence() throws RemoteException;

    void sendCallPerfStatsStanza(String str) throws RemoteException;

    void sendSessionStanza(String str) throws RemoteException;

    void setPresence(Presence presence) throws RemoteException;

    void uploadAvatar(Bitmap bitmap) throws RemoteException;

    void uploadAvatarFromDb() throws RemoteException;

    public static abstract class Stub extends Binder implements IImSession {
        private static final String DESCRIPTOR = "com.google.android.gtalkservice.IImSession";
        static final int TRANSACTION_addConnectionStateListener = 7;
        static final int TRANSACTION_addContact = 13;
        static final int TRANSACTION_addGroupChatInvitationListener = 27;
        static final int TRANSACTION_addRemoteChatListener = 29;
        static final int TRANSACTION_addRemoteJingleInfoStanzaListener = 42;
        static final int TRANSACTION_addRemoteRosterListener = 31;
        static final int TRANSACTION_addRemoteSessionStanzaListener = 39;
        static final int TRANSACTION_approveSubscriptionRequest = 20;
        static final int TRANSACTION_blockContact = 16;
        static final int TRANSACTION_clearContactFlags = 19;
        static final int TRANSACTION_closeAllChatSessions = 36;
        static final int TRANSACTION_createChatSession = 22;
        static final int TRANSACTION_createGroupChatSession = 24;
        static final int TRANSACTION_declineGroupChatInvitation = 26;
        static final int TRANSACTION_declineSubscriptionRequest = 21;
        static final int TRANSACTION_editContact = 14;
        static final int TRANSACTION_getAccountId = 1;
        static final int TRANSACTION_getChatSession = 23;
        static final int TRANSACTION_getConnectionState = 6;
        static final int TRANSACTION_getJid = 3;
        static final int TRANSACTION_getPresence = 10;
        static final int TRANSACTION_getUsername = 2;
        static final int TRANSACTION_goOffRecordInRoom = 34;
        static final int TRANSACTION_goOffRecordWithContacts = 33;
        static final int TRANSACTION_hideContact = 18;
        static final int TRANSACTION_inviteContactsToGroupchat = 46;
        static final int TRANSACTION_isOffRecordWithContact = 35;
        static final int TRANSACTION_joinGroupChatSession = 25;
        static final int TRANSACTION_login = 4;
        static final int TRANSACTION_logout = 5;
        static final int TRANSACTION_pinContact = 17;
        static final int TRANSACTION_pruneOldChatSessions = 37;
        static final int TRANSACTION_queryJingleInfo = 41;
        static final int TRANSACTION_removeConnectionStateListener = 8;
        static final int TRANSACTION_removeContact = 15;
        static final int TRANSACTION_removeGroupChatInvitationListener = 28;
        static final int TRANSACTION_removeRemoteChatListener = 30;
        static final int TRANSACTION_removeRemoteJingleInfoStanzaListener = 43;
        static final int TRANSACTION_removeRemoteRosterListener = 32;
        static final int TRANSACTION_removeRemoteSessionStanzaListener = 40;
        static final int TRANSACTION_requestBatchedBuddyPresence = 44;
        static final int TRANSACTION_sendCallPerfStatsStanza = 45;
        static final int TRANSACTION_sendSessionStanza = 38;
        static final int TRANSACTION_setPresence = 9;
        static final int TRANSACTION_uploadAvatar = 11;
        static final int TRANSACTION_uploadAvatarFromDb = 12;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IImSession)) {
                return new Proxy(obj);
            }
            return (IImSession) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Presence _arg0;
            Bitmap _arg02;
            int i = code;
            Parcel parcel = data;
            Parcel parcel2 = reply;
            if (i != 1598968902) {
                IBinder iBinder = null;
                boolean _arg1 = false;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        long _result = getAccountId();
                        reply.writeNoException();
                        parcel2.writeLong(_result);
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        String _result2 = getUsername();
                        reply.writeNoException();
                        parcel2.writeString(_result2);
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        String _result3 = getJid();
                        reply.writeNoException();
                        parcel2.writeString(_result3);
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        String _arg03 = data.readString();
                        if (data.readInt() != 0) {
                            _arg1 = true;
                        }
                        login(_arg03, _arg1);
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        logout();
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        ConnectionState _result4 = getConnectionState();
                        reply.writeNoException();
                        if (_result4 != null) {
                            parcel2.writeInt(1);
                            _result4.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        addConnectionStateListener(IConnectionStateListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeConnectionStateListener(IConnectionStateListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = Presence.CREATOR.createFromParcel(parcel);
                        } else {
                            _arg0 = null;
                        }
                        setPresence(_arg0);
                        reply.writeNoException();
                        return true;
                    case 10:
                        parcel.enforceInterface(DESCRIPTOR);
                        Presence _result5 = getPresence();
                        reply.writeNoException();
                        if (_result5 != null) {
                            parcel2.writeInt(1);
                            _result5.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 11:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg02 = (Bitmap) Bitmap.CREATOR.createFromParcel(parcel);
                        } else {
                            _arg02 = null;
                        }
                        uploadAvatar(_arg02);
                        return true;
                    case 12:
                        parcel.enforceInterface(DESCRIPTOR);
                        uploadAvatarFromDb();
                        return true;
                    case 13:
                        parcel.enforceInterface(DESCRIPTOR);
                        addContact(data.readString(), data.readString(), data.createStringArray());
                        return true;
                    case 14:
                        parcel.enforceInterface(DESCRIPTOR);
                        editContact(data.readString(), data.readString(), data.createStringArray());
                        return true;
                    case 15:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeContact(data.readString());
                        return true;
                    case 16:
                        parcel.enforceInterface(DESCRIPTOR);
                        blockContact(data.readString());
                        return true;
                    case 17:
                        parcel.enforceInterface(DESCRIPTOR);
                        pinContact(data.readString());
                        return true;
                    case 18:
                        parcel.enforceInterface(DESCRIPTOR);
                        hideContact(data.readString());
                        return true;
                    case 19:
                        parcel.enforceInterface(DESCRIPTOR);
                        clearContactFlags(data.readString());
                        return true;
                    case 20:
                        parcel.enforceInterface(DESCRIPTOR);
                        approveSubscriptionRequest(data.readString(), data.readString(), data.createStringArray());
                        return true;
                    case 21:
                        parcel.enforceInterface(DESCRIPTOR);
                        declineSubscriptionRequest(data.readString());
                        return true;
                    case 22:
                        parcel.enforceInterface(DESCRIPTOR);
                        IChatSession _result6 = createChatSession(data.readString());
                        reply.writeNoException();
                        if (_result6 != null) {
                            iBinder = _result6.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder);
                        return true;
                    case 23:
                        parcel.enforceInterface(DESCRIPTOR);
                        IChatSession _result7 = getChatSession(data.readString());
                        reply.writeNoException();
                        if (_result7 != null) {
                            iBinder = _result7.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder);
                        return true;
                    case 24:
                        parcel.enforceInterface(DESCRIPTOR);
                        createGroupChatSession(data.readString(), data.createStringArray());
                        reply.writeNoException();
                        return true;
                    case 25:
                        parcel.enforceInterface(DESCRIPTOR);
                        joinGroupChatSession(data.readString(), data.readString(), data.readString());
                        reply.writeNoException();
                        return true;
                    case 26:
                        parcel.enforceInterface(DESCRIPTOR);
                        declineGroupChatInvitation(data.readString(), data.readString());
                        reply.writeNoException();
                        return true;
                    case 27:
                        parcel.enforceInterface(DESCRIPTOR);
                        addGroupChatInvitationListener(IGroupChatInvitationListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 28:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeGroupChatInvitationListener(IGroupChatInvitationListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 29:
                        parcel.enforceInterface(DESCRIPTOR);
                        addRemoteChatListener(IChatListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 30:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeRemoteChatListener(IChatListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 31:
                        parcel.enforceInterface(DESCRIPTOR);
                        addRemoteRosterListener(IRosterListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 32:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeRemoteRosterListener(IRosterListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 33:
                        parcel.enforceInterface(DESCRIPTOR);
                        List _arg04 = parcel.readArrayList(getClass().getClassLoader());
                        if (data.readInt() != 0) {
                            _arg1 = true;
                        }
                        goOffRecordWithContacts(_arg04, _arg1);
                        return true;
                    case 34:
                        parcel.enforceInterface(DESCRIPTOR);
                        String _arg05 = data.readString();
                        if (data.readInt() != 0) {
                            _arg1 = true;
                        }
                        goOffRecordInRoom(_arg05, _arg1);
                        reply.writeNoException();
                        return true;
                    case 35:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean _result8 = isOffRecordWithContact(data.readString());
                        reply.writeNoException();
                        parcel2.writeInt(_result8);
                        return true;
                    case 36:
                        parcel.enforceInterface(DESCRIPTOR);
                        closeAllChatSessions();
                        return true;
                    case 37:
                        parcel.enforceInterface(DESCRIPTOR);
                        pruneOldChatSessions(data.readLong(), data.readLong(), data.readLong(), data.readInt() != 0);
                        return true;
                    case 38:
                        parcel.enforceInterface(DESCRIPTOR);
                        sendSessionStanza(data.readString());
                        return true;
                    case 39:
                        parcel.enforceInterface(DESCRIPTOR);
                        addRemoteSessionStanzaListener(ISessionStanzaListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 40:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeRemoteSessionStanzaListener(ISessionStanzaListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 41:
                        parcel.enforceInterface(DESCRIPTOR);
                        queryJingleInfo();
                        return true;
                    case 42:
                        parcel.enforceInterface(DESCRIPTOR);
                        addRemoteJingleInfoStanzaListener(IJingleInfoStanzaListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 43:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeRemoteJingleInfoStanzaListener(IJingleInfoStanzaListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 44:
                        parcel.enforceInterface(DESCRIPTOR);
                        requestBatchedBuddyPresence();
                        return true;
                    case 45:
                        parcel.enforceInterface(DESCRIPTOR);
                        sendCallPerfStatsStanza(data.readString());
                        return true;
                    case 46:
                        parcel.enforceInterface(DESCRIPTOR);
                        inviteContactsToGroupchat(data.readString(), data.createStringArray());
                        reply.writeNoException();
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IImSession {
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

            public long getAccountId() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readLong();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getUsername() throws RemoteException {
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

            public String getJid() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void login(String username, boolean keepSignedIn) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(username);
                    _data.writeInt(keepSignedIn ? 1 : 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void logout() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public ConnectionState getConnectionState() throws RemoteException {
                ConnectionState _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ConnectionState.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addConnectionStateListener(IConnectionStateListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeConnectionStateListener(IConnectionStateListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPresence(Presence presence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (presence != null) {
                        _data.writeInt(1);
                        presence.writeToParcel(_data, 0);
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

            public Presence getPresence() throws RemoteException {
                Presence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Presence.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void uploadAvatar(Bitmap avatar) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (avatar != null) {
                        _data.writeInt(1);
                        avatar.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void uploadAvatarFromDb() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void addContact(String contact, String nickname, String[] groups) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(contact);
                    _data.writeString(nickname);
                    _data.writeStringArray(groups);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void editContact(String contact, String nickname, String[] groups) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(contact);
                    _data.writeString(nickname);
                    _data.writeStringArray(groups);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void removeContact(String contact) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(contact);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void blockContact(String contact) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(contact);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void pinContact(String contact) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(contact);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void hideContact(String contact) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(contact);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void clearContactFlags(String contact) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(contact);
                    this.mRemote.transact(19, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void approveSubscriptionRequest(String contact, String nickname, String[] groups) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(contact);
                    _data.writeString(nickname);
                    _data.writeStringArray(groups);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void declineSubscriptionRequest(String contact) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(contact);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public IChatSession createChatSession(String to) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(to);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    return IChatSession.Stub.asInterface(_reply.readStrongBinder());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IChatSession getChatSession(String to) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(to);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    return IChatSession.Stub.asInterface(_reply.readStrongBinder());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void createGroupChatSession(String nickname, String[] contacts) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(nickname);
                    _data.writeStringArray(contacts);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void joinGroupChatSession(String roomAddress, String nickname, String password) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roomAddress);
                    _data.writeString(nickname);
                    _data.writeString(password);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void declineGroupChatInvitation(String roomAddress, String inviter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roomAddress);
                    _data.writeString(inviter);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addGroupChatInvitationListener(IGroupChatInvitationListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeGroupChatInvitationListener(IGroupChatInvitationListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
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
                    this.mRemote.transact(29, _data, _reply, 0);
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
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addRemoteRosterListener(IRosterListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeRemoteRosterListener(IRosterListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void goOffRecordWithContacts(List contacts, boolean offRecordFlag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeList(contacts);
                    _data.writeInt(offRecordFlag ? 1 : 0);
                    this.mRemote.transact(33, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void goOffRecordInRoom(String room, boolean offRecordFlag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(room);
                    _data.writeInt(offRecordFlag ? 1 : 0);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isOffRecordWithContact(String contact) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(contact);
                    boolean _result = false;
                    this.mRemote.transact(35, _data, _reply, 0);
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

            public void closeAllChatSessions() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void pruneOldChatSessions(long expire, long expireHard, long expireForChatsOnOtherClient, boolean closeChatIfTimeStampIsZero) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(expire);
                    _data.writeLong(expireHard);
                    _data.writeLong(expireForChatsOnOtherClient);
                    _data.writeInt(closeChatIfTimeStampIsZero ? 1 : 0);
                    this.mRemote.transact(37, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void sendSessionStanza(String stanza) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(stanza);
                    this.mRemote.transact(38, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void addRemoteSessionStanzaListener(ISessionStanzaListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeRemoteSessionStanzaListener(ISessionStanzaListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void queryJingleInfo() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void addRemoteJingleInfoStanzaListener(IJingleInfoStanzaListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeRemoteJingleInfoStanzaListener(IJingleInfoStanzaListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestBatchedBuddyPresence() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void sendCallPerfStatsStanza(String callPerfStanza) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callPerfStanza);
                    this.mRemote.transact(45, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void inviteContactsToGroupchat(String roomJid, String[] contacts) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roomJid);
                    _data.writeStringArray(contacts);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
