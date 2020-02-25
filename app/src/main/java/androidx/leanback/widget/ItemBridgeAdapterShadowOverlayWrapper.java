package androidx.leanback.widget;

import android.view.View;

public class ItemBridgeAdapterShadowOverlayWrapper extends ItemBridgeAdapter.Wrapper {
    private final ShadowOverlayHelper mHelper;

    public ItemBridgeAdapterShadowOverlayWrapper(ShadowOverlayHelper helper) {
        this.mHelper = helper;
    }

    public View createWrapper(View root) {
        return this.mHelper.createShadowOverlayContainer(root.getContext());
    }

    public void wrap(View wrapper, View wrapped) {
        ((ShadowOverlayContainer) wrapper).wrap(wrapped);
    }
}
