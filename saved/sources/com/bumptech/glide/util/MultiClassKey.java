package com.bumptech.glide.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MultiClassKey {
    private Class<?> first;
    private Class<?> second;
    private Class<?> third;

    public MultiClassKey() {
    }

    public MultiClassKey(@NonNull Class<?> first2, @NonNull Class<?> second2) {
        set(first2, second2);
    }

    public MultiClassKey(@NonNull Class<?> first2, @NonNull Class<?> second2, @Nullable Class<?> third2) {
        set(first2, second2, third2);
    }

    public void set(@NonNull Class<?> first2, @NonNull Class<?> second2) {
        set(first2, second2, null);
    }

    public void set(@NonNull Class<?> first2, @NonNull Class<?> second2, @Nullable Class<?> third2) {
        this.first = first2;
        this.second = second2;
        this.third = third2;
    }

    public String toString() {
        String valueOf = String.valueOf(this.first);
        String valueOf2 = String.valueOf(this.second);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 30 + String.valueOf(valueOf2).length());
        sb.append("MultiClassKey{first=");
        sb.append(valueOf);
        sb.append(", second=");
        sb.append(valueOf2);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MultiClassKey that = (MultiClassKey) o;
        if (this.first.equals(that.first) && this.second.equals(that.second) && Util.bothNullOrEqual(this.third, that.third)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = ((this.first.hashCode() * 31) + this.second.hashCode()) * 31;
        Class<?> cls = this.third;
        return result + (cls != null ? cls.hashCode() : 0);
    }
}
