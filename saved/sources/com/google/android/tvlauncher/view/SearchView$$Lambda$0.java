package com.google.android.tvlauncher.view;

import android.view.View;

final /* synthetic */ class SearchView$$Lambda$0 implements View.OnClickListener {
    private final SearchView arg$1;
    private final boolean arg$2;

    SearchView$$Lambda$0(SearchView searchView, boolean z) {
        this.arg$1 = searchView;
        this.arg$2 = z;
    }

    public void onClick(View view) {
        this.arg$1.lambda$onAttachedToWindow$0$SearchView(this.arg$2, view);
    }
}
