package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.Hide;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Hide
/* compiled from: AndroidUtilsLight */
public final class zza {
    public static MessageDigest zza(String str) {
        int i = 0;
        while (i < 2) {
            try {
                MessageDigest instance = MessageDigest.getInstance(str);
                if (instance != null) {
                    return instance;
                }
                i++;
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return null;
    }
}
