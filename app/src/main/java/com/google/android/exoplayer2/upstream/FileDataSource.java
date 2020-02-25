package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public final class FileDataSource extends BaseDataSource {
    private long bytesRemaining;
    @Nullable
    private RandomAccessFile file;
    private boolean opened;
    @Nullable
    private Uri uri;

    public FileDataSource() {
        super(false);
    }

    @Deprecated
    public FileDataSource(@Nullable TransferListener listener) {
        this();
        if (listener != null) {
            addTransferListener(listener);
        }
    }

    public long open(DataSpec dataSpec) throws FileDataSourceException {
        long j;
        try {
            this.uri = dataSpec.uri;
            transferInitializing(dataSpec);
            this.file = new RandomAccessFile(dataSpec.uri.getPath(), "r");
            this.file.seek(dataSpec.position);
            if (dataSpec.length == -1) {
                j = this.file.length() - dataSpec.position;
            } else {
                j = dataSpec.length;
            }
            this.bytesRemaining = j;
            if (this.bytesRemaining >= 0) {
                this.opened = true;
                transferStarted(dataSpec);
                return this.bytesRemaining;
            }
            throw new EOFException();
        } catch (IOException e) {
            throw new FileDataSourceException(e);
        }
    }

    public int read(byte[] buffer, int offset, int readLength) throws FileDataSourceException {
        if (readLength == 0) {
            return 0;
        }
        long j = this.bytesRemaining;
        if (j == 0) {
            return -1;
        }
        try {
            int bytesRead = this.file.read(buffer, offset, (int) Math.min(j, (long) readLength));
            if (bytesRead > 0) {
                this.bytesRemaining -= (long) bytesRead;
                bytesTransferred(bytesRead);
            }
            return bytesRead;
        } catch (IOException e) {
            throw new FileDataSourceException(e);
        }
    }

    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    public void close() throws FileDataSourceException {
        this.uri = null;
        try {
            if (this.file != null) {
                this.file.close();
            }
            this.file = null;
            if (this.opened) {
                this.opened = false;
                transferEnded();
            }
        } catch (IOException e) {
            throw new FileDataSourceException(e);
        } catch (Throwable th) {
            this.file = null;
            if (this.opened) {
                this.opened = false;
                transferEnded();
            }
            throw th;
        }
    }

    public static class FileDataSourceException extends IOException {
        public FileDataSourceException(IOException cause) {
            super(cause);
        }
    }
}
