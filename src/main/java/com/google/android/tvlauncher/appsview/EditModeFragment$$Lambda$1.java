package com.google.android.tvlauncher.appsview;

import com.google.android.tvlauncher.appsview.EditModeGridAdapter;

final /* synthetic */ class EditModeFragment$$Lambda$1 implements EditModeGridAdapter.OnEditItemRemovedListener {
    private final EditModeFragment arg$1;

    EditModeFragment$$Lambda$1(EditModeFragment editModeFragment) {
        this.arg$1 = editModeFragment;
    }

    public void onEditItemRemoved(int i) {
        this.arg$1.lambda$onViewCreated$1$EditModeFragment(i);
    }
}
