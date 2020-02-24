package com.google.android.libraries.performance.primes.persistent;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Base64;
import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.stitch.util.Preconditions;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;

public final class PersistentStorage {
    private static final byte PROTO_HEADER = 1;
    private static final String TAG = "PersistStorage";
    private final SharedPreferences sharedPreferences;

    public PersistentStorage(SharedPreferences sharedPreferences2) {
        this.sharedPreferences = sharedPreferences2;
    }

    private byte[] read(String key) {
        return Base64.decode(this.sharedPreferences.getString(key, ""), 0);
    }

    private boolean write(String key, byte header, byte[] data) {
        byte[] allData = new byte[(data.length + 1)];
        allData[0] = header;
        System.arraycopy(data, 0, allData, 1, data.length);
        return this.sharedPreferences.edit().putString(key, Base64.encodeToString(allData, 0)).commit();
    }

    @Nullable
    public <T extends MessageLite> T readProto(String key, Parser<T> parser) {
        byte[] data = read(key);
        if (data == null || data.length == 0) {
            PrimesLog.m54w(TAG, "unknown key", new Object[0]);
            return null;
        } else if (data[0] == 1) {
            try {
                return (MessageLite) parser.parseFrom(data, 1, data.length - 1);
            } catch (InvalidProtocolBufferException e) {
                PrimesLog.m53w(TAG, "failure reading proto", e, new Object[0]);
            }
        } else {
            PrimesLog.m54w(TAG, "wrong header", new Object[0]);
            return null;
        }
    }

    public <T extends MessageLite> boolean writeProto(String key, T val) {
        return write(key, (byte) 1, ((MessageLite) Preconditions.checkNotNull(val)).toByteArray());
    }

    public boolean remove(String key) {
        return this.sharedPreferences.edit().remove(key).commit();
    }
}
