package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.Key;
import java.security.MessageDigest;

final class DataCacheKey implements Key {
    private final Key signature;
    private final Key sourceKey;

    DataCacheKey(Key sourceKey2, Key signature2) {
        this.sourceKey = sourceKey2;
        this.signature = signature2;
    }

    /* access modifiers changed from: package-private */
    public Key getSourceKey() {
        return this.sourceKey;
    }

    public boolean equals(Object o) {
        if (!(o instanceof DataCacheKey)) {
            return false;
        }
        DataCacheKey other = (DataCacheKey) o;
        if (!this.sourceKey.equals(other.sourceKey) || !this.signature.equals(other.signature)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.sourceKey.hashCode() * 31) + this.signature.hashCode();
    }

    public String toString() {
        String valueOf = String.valueOf(this.sourceKey);
        String valueOf2 = String.valueOf(this.signature);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 36 + String.valueOf(valueOf2).length());
        sb.append("DataCacheKey{sourceKey=");
        sb.append(valueOf);
        sb.append(", signature=");
        sb.append(valueOf2);
        sb.append('}');
        return sb.toString();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.sourceKey.updateDiskCacheKey(messageDigest);
        this.signature.updateDiskCacheKey(messageDigest);
    }
}
