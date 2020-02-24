package com.google.android.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract class BaseStub extends Binder implements IInterface {
    private static TransactionInterceptor globalInterceptor = null;

    static synchronized void installTransactionInterceptorPackagePrivate(TransactionInterceptor interceptor) {
        synchronized (BaseStub.class) {
            if (interceptor != null) {
                try {
                    if (globalInterceptor == null) {
                        globalInterceptor = interceptor;
                    } else {
                        throw new IllegalStateException("Duplicate TransactionInterceptor installation.");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                throw new IllegalArgumentException("null interceptor");
            }
        }
    }

    protected BaseStub(String descriptor) {
        attachInterface(this, descriptor);
    }

    public IBinder asBinder() {
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean routeToSuperOrEnforceInterface(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code > 16777215) {
            return super.onTransact(code, data, reply, flags);
        }
        data.enforceInterface(getInterfaceDescriptor());
        return false;
    }

    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (routeToSuperOrEnforceInterface(code, data, reply, flags)) {
            return true;
        }
        TransactionInterceptor transactionInterceptor = globalInterceptor;
        if (transactionInterceptor == null) {
            return dispatchTransaction(code, data, reply, flags);
        }
        return transactionInterceptor.interceptTransaction(this, code, data, reply, flags);
    }

    /* access modifiers changed from: protected */
    public boolean dispatchTransaction(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        return false;
    }
}
