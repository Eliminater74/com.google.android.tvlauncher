package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.util.Preconditions;
import java.security.MessageDigest;
import java.util.Map;

class EngineKey implements Key {
    private int hashCode;
    private final int height;
    private final Object model;
    private final Options options;
    private final Class<?> resourceClass;
    private final Key signature;
    private final Class<?> transcodeClass;
    private final Map<Class<?>, Transformation<?>> transformations;
    private final int width;

    EngineKey(Object model2, Key signature2, int width2, int height2, Map<Class<?>, Transformation<?>> transformations2, Class<?> resourceClass2, Class<?> transcodeClass2, Options options2) {
        this.model = Preconditions.checkNotNull(model2);
        this.signature = (Key) Preconditions.checkNotNull(signature2, "Signature must not be null");
        this.width = width2;
        this.height = height2;
        this.transformations = (Map) Preconditions.checkNotNull(transformations2);
        this.resourceClass = (Class) Preconditions.checkNotNull(resourceClass2, "Resource class must not be null");
        this.transcodeClass = (Class) Preconditions.checkNotNull(transcodeClass2, "Transcode class must not be null");
        this.options = (Options) Preconditions.checkNotNull(options2);
    }

    public boolean equals(Object o) {
        if (!(o instanceof EngineKey)) {
            return false;
        }
        EngineKey other = (EngineKey) o;
        if (!this.model.equals(other.model) || !this.signature.equals(other.signature) || this.height != other.height || this.width != other.width || !this.transformations.equals(other.transformations) || !this.resourceClass.equals(other.resourceClass) || !this.transcodeClass.equals(other.transcodeClass) || !this.options.equals(other.options)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.model.hashCode();
            this.hashCode = (this.hashCode * 31) + this.signature.hashCode();
            this.hashCode = (this.hashCode * 31) + this.width;
            this.hashCode = (this.hashCode * 31) + this.height;
            this.hashCode = (this.hashCode * 31) + this.transformations.hashCode();
            this.hashCode = (this.hashCode * 31) + this.resourceClass.hashCode();
            this.hashCode = (this.hashCode * 31) + this.transcodeClass.hashCode();
            this.hashCode = (this.hashCode * 31) + this.options.hashCode();
        }
        return this.hashCode;
    }

    public String toString() {
        String valueOf = String.valueOf(this.model);
        int i = this.width;
        int i2 = this.height;
        String valueOf2 = String.valueOf(this.resourceClass);
        String valueOf3 = String.valueOf(this.transcodeClass);
        String valueOf4 = String.valueOf(this.signature);
        int i3 = this.hashCode;
        String valueOf5 = String.valueOf(this.transformations);
        String valueOf6 = String.valueOf(this.options);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 151 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length() + String.valueOf(valueOf6).length());
        sb.append("EngineKey{model=");
        sb.append(valueOf);
        sb.append(", width=");
        sb.append(i);
        sb.append(", height=");
        sb.append(i2);
        sb.append(", resourceClass=");
        sb.append(valueOf2);
        sb.append(", transcodeClass=");
        sb.append(valueOf3);
        sb.append(", signature=");
        sb.append(valueOf4);
        sb.append(", hashCode=");
        sb.append(i3);
        sb.append(", transformations=");
        sb.append(valueOf5);
        sb.append(", options=");
        sb.append(valueOf6);
        sb.append('}');
        return sb.toString();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        throw new UnsupportedOperationException();
    }
}
