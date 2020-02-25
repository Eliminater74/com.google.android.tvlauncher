package com.google.android.tvlauncher.util;

import android.support.annotation.IdRes;

import androidx.leanback.widget.ItemAlignmentFacet;

public class KeylineUtil {
    public static ItemAlignmentFacet createItemAlignmentFacet(int offset) {
        return createItemAlignmentFacet(offset, null);
    }

    public static ItemAlignmentFacet createItemAlignmentFacet(int offset, @IdRes Integer alignedItemResourceId) {
        ItemAlignmentFacet.ItemAlignmentDef def = new ItemAlignmentFacet.ItemAlignmentDef();
        def.setItemAlignmentOffset(offset);
        def.setItemAlignmentOffsetPercent(50.0f);
        if (alignedItemResourceId != null) {
            def.setItemAlignmentViewId(alignedItemResourceId.intValue());
        }
        ItemAlignmentFacet facet = new ItemAlignmentFacet();
        facet.setAlignmentDefs(new ItemAlignmentFacet.ItemAlignmentDef[]{def});
        return facet;
    }
}
