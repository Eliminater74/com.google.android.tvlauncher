package com.bumptech.glide.load;

import android.support.annotation.Nullable;
import java.io.IOException;

public final class HttpException extends IOException {
    public static final int UNKNOWN = -1;
    private static final long serialVersionUID = 1;
    private final int statusCode;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public HttpException(int r3) {
        /*
            r2 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = 49
            r0.<init>(r1)
            java.lang.String r1 = "Http request failed with status code: "
            r0.append(r1)
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            r2.<init>(r0, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.HttpException.<init>(int):void");
    }

    public HttpException(String message) {
        this(message, -1);
    }

    public HttpException(String message, int statusCode2) {
        this(message, statusCode2, null);
    }

    public HttpException(String message, int statusCode2, @Nullable Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode2;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
