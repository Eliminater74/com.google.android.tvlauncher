package android.support.p001v4.p003os;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Message;
import android.support.annotation.NonNull;

/* renamed from: android.support.v4.os.MessageCompat */
public final class MessageCompat {
    private static boolean sTryIsAsynchronous = true;
    private static boolean sTrySetAsynchronous = true;

    @SuppressLint({"NewApi"})
    public static void setAsynchronous(@NonNull Message message, boolean async) {
        if (Build.VERSION.SDK_INT >= 22) {
            message.setAsynchronous(async);
        } else if (sTrySetAsynchronous && Build.VERSION.SDK_INT >= 16) {
            try {
                message.setAsynchronous(async);
            } catch (NoSuchMethodError e) {
                sTrySetAsynchronous = false;
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static boolean isAsynchronous(@NonNull Message message) {
        if (Build.VERSION.SDK_INT >= 22) {
            return message.isAsynchronous();
        }
        if (sTryIsAsynchronous && Build.VERSION.SDK_INT >= 16) {
            try {
                return message.isAsynchronous();
            } catch (NoSuchMethodError e) {
                sTryIsAsynchronous = false;
            }
        }
        return false;
    }

    private MessageCompat() {
    }
}
