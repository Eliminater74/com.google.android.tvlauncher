package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBufferObserver;
import java.util.HashSet;
import java.util.Iterator;

public final class DataBufferObserverSet implements DataBufferObserver, DataBufferObserver.Observable {
    private HashSet<DataBufferObserver> zza = new HashSet<>();

    public final boolean hasObservers() {
        return !this.zza.isEmpty();
    }

    public final void clear() {
        this.zza.clear();
    }

    public final void addObserver(DataBufferObserver dataBufferObserver) {
        this.zza.add(dataBufferObserver);
    }

    public final void removeObserver(DataBufferObserver dataBufferObserver) {
        this.zza.remove(dataBufferObserver);
    }

    public final void onDataChanged() {
        Iterator<DataBufferObserver> it = this.zza.iterator();
        while (it.hasNext()) {
            it.next().onDataChanged();
        }
    }

    public final void onDataRangeChanged(int i, int i2) {
        Iterator<DataBufferObserver> it = this.zza.iterator();
        while (it.hasNext()) {
            it.next().onDataRangeChanged(i, i2);
        }
    }

    public final void onDataRangeInserted(int i, int i2) {
        Iterator<DataBufferObserver> it = this.zza.iterator();
        while (it.hasNext()) {
            it.next().onDataRangeInserted(i, i2);
        }
    }

    public final void onDataRangeRemoved(int i, int i2) {
        Iterator<DataBufferObserver> it = this.zza.iterator();
        while (it.hasNext()) {
            it.next().onDataRangeRemoved(i, i2);
        }
    }

    public final void onDataRangeMoved(int i, int i2, int i3) {
        Iterator<DataBufferObserver> it = this.zza.iterator();
        while (it.hasNext()) {
            it.next().onDataRangeMoved(i, i2, i3);
        }
    }
}
