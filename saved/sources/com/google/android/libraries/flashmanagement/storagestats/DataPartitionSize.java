package com.google.android.libraries.flashmanagement.storagestats;

import android.content.Context;
import android.os.Build;
import java.io.File;

public class DataPartitionSize {
    private final Context context;
    private File dataPartition;
    private final Object lock = new Object();

    public DataPartitionSize(Context context2) {
        this.context = context2.getApplicationContext();
    }

    private File getDataPartition() {
        File file;
        synchronized (this.lock) {
            if (this.dataPartition == null) {
                if (Build.VERSION.SDK_INT >= 24) {
                    this.dataPartition = this.context.getDataDir();
                } else {
                    this.dataPartition = this.context.getDatabasePath("dps-dummy").getParentFile().getParentFile();
                }
            }
            file = this.dataPartition;
        }
        return file;
    }

    public long getFreeSpaceSizeInBytes() {
        return getDataPartition().getFreeSpace();
    }

    public long getSizeInBytes() {
        return getDataPartition().getTotalSpace();
    }
}
