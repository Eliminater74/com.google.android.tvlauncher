package com.google.android.tvlauncher.doubleclick;

public class NetworkException extends Exception {
    private int mHttpErrorStatus;

    public NetworkException(String message) {
        super(message);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NetworkException(java.lang.String r3, int r4) {
        /*
            r2 = this;
            java.lang.String r0 = java.lang.String.valueOf(r3)
            int r0 = r0.length()
            int r0 = r0 + 21
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r0)
            r1.append(r3)
            java.lang.String r0 = " - status:"
            r1.append(r0)
            r1.append(r4)
            java.lang.String r0 = r1.toString()
            r2.<init>(r0)
            r2.mHttpErrorStatus = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.doubleclick.NetworkException.<init>(java.lang.String, int):void");
    }

    public int getHttpErrorStatus() {
        return this.mHttpErrorStatus;
    }
}
