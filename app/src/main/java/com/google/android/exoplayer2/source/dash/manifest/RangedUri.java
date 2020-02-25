package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.UriUtil;

public final class RangedUri {
    public final long length;
    public final long start;
    private final String referenceUri;
    private int hashCode;

    public RangedUri(@Nullable String referenceUri2, long start2, long length2) {
        this.referenceUri = referenceUri2 == null ? "" : referenceUri2;
        this.start = start2;
        this.length = length2;
    }

    public Uri resolveUri(String baseUri) {
        return UriUtil.resolveToUri(baseUri, this.referenceUri);
    }

    public String resolveUriString(String baseUri) {
        return UriUtil.resolve(baseUri, this.referenceUri);
    }

    @Nullable
    public RangedUri attemptMerge(@Nullable RangedUri other, String baseUri) {
        String resolvedUri = resolveUriString(baseUri);
        if (other == null || !resolvedUri.equals(other.resolveUriString(baseUri))) {
            return null;
        }
        long j = this.length;
        if (j != -1) {
            long j2 = this.start;
            if (j2 + j == other.start) {
                long j3 = other.length;
                return new RangedUri(resolvedUri, j2, j3 == -1 ? -1 : j + j3);
            }
        }
        long j4 = other.length;
        if (j4 != -1) {
            long j5 = other.start;
            if (j5 + j4 == this.start) {
                long j6 = this.length;
                return new RangedUri(resolvedUri, j5, j6 == -1 ? -1 : j4 + j6);
            }
        }
        return null;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = (((((17 * 31) + ((int) this.start)) * 31) + ((int) this.length)) * 31) + this.referenceUri.hashCode();
        }
        return this.hashCode;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RangedUri other = (RangedUri) obj;
        if (this.start == other.start && this.length == other.length && this.referenceUri.equals(other.referenceUri)) {
            return true;
        }
        return false;
    }

    public String toString() {
        String str = this.referenceUri;
        long j = this.start;
        long j2 = this.length;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 81);
        sb.append("RangedUri(referenceUri=");
        sb.append(str);
        sb.append(", start=");
        sb.append(j);
        sb.append(", length=");
        sb.append(j2);
        sb.append(")");
        return sb.toString();
    }
}
