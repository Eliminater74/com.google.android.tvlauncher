package com.google.android.tvlauncher.data;

import android.content.Context;
import com.google.android.tvlauncher.data.TvDataManager;

final /* synthetic */ class TvDataManager$$Lambda$2 implements TvDataManager.Provider {
    static final TvDataManager.Provider $instance = new TvDataManager$$Lambda$2();

    private TvDataManager$$Lambda$2() {
    }

    public TvDataManager get(Context context) {
        return TvDataManager.getInstance(context);
    }
}
