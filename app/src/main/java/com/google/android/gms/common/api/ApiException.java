package com.google.android.gms.common.api;

import android.support.annotation.Nullable;

public class ApiException extends Exception {
    protected final Status mStatus;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ApiException(@android.support.annotation.NonNull com.google.android.gms.common.api.Status r5) {
        /*
            r4 = this;
            int r0 = r5.getStatusCode()
            java.lang.String r1 = r5.getStatusMessage()
            if (r1 == 0) goto L_0x0010
            java.lang.String r1 = r5.getStatusMessage()
            goto L_0x0012
        L_0x0010:
            java.lang.String r1 = ""
        L_0x0012:
            java.lang.String r2 = java.lang.String.valueOf(r1)
            int r2 = r2.length()
            int r2 = r2 + 13
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r2)
            r3.append(r0)
            java.lang.String r0 = ": "
            r3.append(r0)
            r3.append(r1)
            java.lang.String r0 = r3.toString()
            r4.<init>(r0)
            r4.mStatus = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.ApiException.<init>(com.google.android.gms.common.api.Status):void");
    }

    public int getStatusCode() {
        return this.mStatus.getStatusCode();
    }

    @Nullable
    @Deprecated
    public String getStatusMessage() {
        return this.mStatus.getStatusMessage();
    }
}
