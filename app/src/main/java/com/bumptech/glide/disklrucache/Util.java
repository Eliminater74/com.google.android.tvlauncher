package com.bumptech.glide.disklrucache;

import com.google.android.exoplayer2.C0841C;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;

final class Util {
    static final Charset US_ASCII = Charset.forName(C0841C.ASCII_NAME);
    static final Charset UTF_8 = Charset.forName("UTF-8");

    private Util() {
    }

    static String readFully(Reader reader) throws IOException {
        try {
            StringWriter writer = new StringWriter();
            char[] buffer = new char[1024];
            while (true) {
                int read = reader.read(buffer);
                int count = read;
                if (read == -1) {
                    return writer.toString();
                }
                writer.write(buffer, 0, count);
            }
        } finally {
            reader.close();
        }
    }

    static void deleteContents(File dir) throws IOException {
        File[] files = dir.listFiles();
        if (files != null) {
            int length = files.length;
            int i = 0;
            while (i < length) {
                File file = files[i];
                if (file.isDirectory()) {
                    deleteContents(file);
                }
                if (file.delete()) {
                    i++;
                } else {
                    String valueOf = String.valueOf(file);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23);
                    sb.append("failed to delete file: ");
                    sb.append(valueOf);
                    throw new IOException(sb.toString());
                }
            }
            return;
        }
        String valueOf2 = String.valueOf(dir);
        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 26);
        sb2.append("not a readable directory: ");
        sb2.append(valueOf2);
        throw new IOException(sb2.toString());
    }

    static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception e) {
            }
        }
    }
}
