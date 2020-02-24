package com.google.android.libraries.stitch.binder;

import android.content.Context;
import com.google.android.libraries.stitch.binder.Extension;
import java.util.ArrayList;
import java.util.HashMap;

public class ExtensionMap<K, T extends Extension<K>> {
    private final HashMap<K, T> extensionMap = new HashMap<>();

    public ExtensionMap(Context context, Class<T> klass) {
        for (T extension : Binder.getAll(context, klass)) {
            K key = extension.getKey();
            if (key == null) {
                throw new IllegalArgumentException("Extension key must not be null");
            } else if (this.extensionMap.put(key, extension) != null) {
                String valueOf = String.valueOf(key);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 53);
                sb.append("Instantiating multiple extensions with the same key: ");
                sb.append(valueOf);
                throw new IllegalArgumentException(sb.toString());
            }
        }
    }

    public T getExtension(K key) {
        return (Extension) this.extensionMap.get(key);
    }

    public ArrayList<K> getRegisteredKeys() {
        return new ArrayList<>(this.extensionMap.keySet());
    }
}
