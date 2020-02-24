package com.google.android.exoplayer2.util;

import android.support.annotation.NonNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class AtomicFile {
    private static final String TAG = "AtomicFile";
    private final File backupName;
    private final File baseName;

    public AtomicFile(File baseName2) {
        this.baseName = baseName2;
        this.backupName = new File(String.valueOf(baseName2.getPath()).concat(".bak"));
    }

    public boolean exists() {
        return this.baseName.exists() || this.backupName.exists();
    }

    public void delete() {
        this.baseName.delete();
        this.backupName.delete();
    }

    /* JADX INFO: Multiple debug info for r0v3 java.io.OutputStream: [D('e' java.io.FileNotFoundException), D('str' java.io.OutputStream)] */
    public OutputStream startWrite() throws IOException {
        if (this.baseName.exists()) {
            if (this.backupName.exists()) {
                this.baseName.delete();
            } else if (!this.baseName.renameTo(this.backupName)) {
                String valueOf = String.valueOf(this.baseName);
                String valueOf2 = String.valueOf(this.backupName);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 37 + String.valueOf(valueOf2).length());
                sb.append("Couldn't rename file ");
                sb.append(valueOf);
                sb.append(" to backup file ");
                sb.append(valueOf2);
                Log.m30w(TAG, sb.toString());
            }
        }
        try {
            return new AtomicFileOutputStream(this.baseName);
        } catch (FileNotFoundException e) {
            File parent = this.baseName.getParentFile();
            if (parent == null || !parent.mkdirs()) {
                String valueOf3 = String.valueOf(this.baseName);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf3).length() + 26);
                sb2.append("Couldn't create directory ");
                sb2.append(valueOf3);
                throw new IOException(sb2.toString(), e);
            }
            try {
                return new AtomicFileOutputStream(this.baseName);
            } catch (FileNotFoundException e2) {
                String valueOf4 = String.valueOf(this.baseName);
                StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf4).length() + 16);
                sb3.append("Couldn't create ");
                sb3.append(valueOf4);
                throw new IOException(sb3.toString(), e2);
            }
        }
    }

    public void endWrite(OutputStream str) throws IOException {
        str.close();
        this.backupName.delete();
    }

    public InputStream openRead() throws FileNotFoundException {
        restoreBackup();
        return new FileInputStream(this.baseName);
    }

    private void restoreBackup() {
        if (this.backupName.exists()) {
            this.baseName.delete();
            this.backupName.renameTo(this.baseName);
        }
    }

    private static final class AtomicFileOutputStream extends OutputStream {
        private boolean closed = false;
        private final FileOutputStream fileOutputStream;

        public AtomicFileOutputStream(File file) throws FileNotFoundException {
            this.fileOutputStream = new FileOutputStream(file);
        }

        public void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                flush();
                try {
                    this.fileOutputStream.getFD().sync();
                } catch (IOException e) {
                    Log.m31w(AtomicFile.TAG, "Failed to sync file descriptor:", e);
                }
                this.fileOutputStream.close();
            }
        }

        public void flush() throws IOException {
            this.fileOutputStream.flush();
        }

        public void write(int b) throws IOException {
            this.fileOutputStream.write(b);
        }

        public void write(@NonNull byte[] b) throws IOException {
            this.fileOutputStream.write(b);
        }

        public void write(@NonNull byte[] b, int off, int len) throws IOException {
            this.fileOutputStream.write(b, off, len);
        }
    }
}
