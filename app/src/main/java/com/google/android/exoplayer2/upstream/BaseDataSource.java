package com.google.android.exoplayer2.upstream;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Map;

public abstract class BaseDataSource implements DataSource {
    @Nullable
    private DataSpec dataSpec;
    private final boolean isNetwork;
    private int listenerCount;
    private final ArrayList<TransferListener> listeners = new ArrayList<>(1);

    public Map getResponseHeaders() {
        return DataSource$$CC.getResponseHeaders$$dflt$$(this);
    }

    protected BaseDataSource(boolean isNetwork2) {
        this.isNetwork = isNetwork2;
    }

    public final void addTransferListener(TransferListener transferListener) {
        if (!this.listeners.contains(transferListener)) {
            this.listeners.add(transferListener);
            this.listenerCount++;
        }
    }

    /* access modifiers changed from: protected */
    public final void transferInitializing(DataSpec dataSpec2) {
        for (int i = 0; i < this.listenerCount; i++) {
            this.listeners.get(i).onTransferInitializing(this, dataSpec2, this.isNetwork);
        }
    }

    /* access modifiers changed from: protected */
    public final void transferStarted(DataSpec dataSpec2) {
        this.dataSpec = dataSpec2;
        for (int i = 0; i < this.listenerCount; i++) {
            this.listeners.get(i).onTransferStart(this, dataSpec2, this.isNetwork);
        }
    }

    /* access modifiers changed from: protected */
    public final void bytesTransferred(int bytesTransferred) {
        DataSpec dataSpec2 = (DataSpec) Util.castNonNull(this.dataSpec);
        for (int i = 0; i < this.listenerCount; i++) {
            this.listeners.get(i).onBytesTransferred(this, dataSpec2, this.isNetwork, bytesTransferred);
        }
    }

    /* access modifiers changed from: protected */
    public final void transferEnded() {
        DataSpec dataSpec2 = (DataSpec) Util.castNonNull(this.dataSpec);
        for (int i = 0; i < this.listenerCount; i++) {
            this.listeners.get(i).onTransferEnd(this, dataSpec2, this.isNetwork);
        }
        this.dataSpec = null;
    }
}
