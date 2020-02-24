package com.google.android.libraries.performance.primes.sampling;

import android.content.Context;
import android.provider.Settings;
import com.google.android.gsf.GservicesKeys;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.InflaterInputStream;

public final class SamplingUtils {
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), GservicesKeys.ANDROID_ID);
    }

    public static byte[] compressBytes(byte[] bytes) throws IOException {
        Deflater deflater = new Deflater(9);
        deflater.setInput(bytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bytes.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            outputStream.write(buffer, 0, deflater.deflate(buffer));
        }
        outputStream.close();
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] bytes) throws IOException {
        InflaterInputStream inflater = new InflaterInputStream(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bytes.length);
        byte[] buf = new byte[bytes.length];
        for (int count = inflater.read(buf); count != -1; count = inflater.read(buf)) {
            outputStream.write(buf, 0, count);
        }
        return outputStream.toByteArray();
    }
}
