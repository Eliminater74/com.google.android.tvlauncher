package com.google.android.gtalkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGroupChatInvitationListener extends IInterface {
    boolean onInvitationReceived(GroupChatInvitation groupChatInvitation) throws RemoteException;

    public static abstract class Stub extends Binder implements IGroupChatInvitationListener {
        private static final String DESCRIPTOR = "com.google.android.gtalkservice.IGroupChatInvitationListener";
        static final int TRANSACTION_onInvitationReceived = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IGroupChatInvitationListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IGroupChatInvitationListener)) {
                return new Proxy(obj);
            }
            return (IGroupChatInvitationListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            GroupChatInvitation _arg0;
            if (code == 1) {
                data.enforceInterface(DESCRIPTOR);
                if (data.readInt() != 0) {
                    _arg0 = GroupChatInvitation.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                boolean _result = onInvitationReceived(_arg0);
                reply.writeNoException();
                reply.writeInt(_result);
                return true;
            } else if (code != 1598968902) {
                return super.onTransact(code, data, reply, flags);
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IGroupChatInvitationListener {
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

            public boolean onInvitationReceived(GroupChatInvitation invitation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (invitation != null) {
                        _data.writeInt(1);
                        invitation.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
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
