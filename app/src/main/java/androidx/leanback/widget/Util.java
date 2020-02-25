package androidx.leanback.widget;

import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class Util {
    private Util() {
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isDescendant(android.view.ViewGroup r3, android.view.View r4) {
        /*
        L_0x0000:
            r0 = 0
            if (r4 == 0) goto L_0x0014
            if (r4 != r3) goto L_0x0007
            r0 = 1
            return r0
        L_0x0007:
            android.view.ViewParent r1 = r4.getParent()
            boolean r2 = r1 instanceof android.view.View
            if (r2 != 0) goto L_0x0010
            return r0
        L_0x0010:
            r4 = r1
            android.view.View r4 = (android.view.View) r4
            goto L_0x0000
        L_0x0014:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.Util.isDescendant(android.view.ViewGroup, android.view.View):boolean");
    }
}
