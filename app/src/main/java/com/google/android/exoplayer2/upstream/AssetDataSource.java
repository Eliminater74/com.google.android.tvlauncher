package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public final class AssetDataSource extends BaseDataSource {
    private final AssetManager assetManager;
    private long bytesRemaining;
    @Nullable
    private InputStream inputStream;
    private boolean opened;
    @Nullable
    private Uri uri;

    public AssetDataSource(Context context) {
        super(false);
        this.assetManager = context.getAssets();
    }

    @Deprecated
    public AssetDataSource(Context context, @Nullable TransferListener listener) {
        this(context);
        if (listener != null) {
            addTransferListener(listener);
        }
    }

    public long open(DataSpec dataSpec) throws AssetDataSourceException {
        try {
            this.uri = dataSpec.uri;
            String path = this.uri.getPath();
            if (path.startsWith("/android_asset/")) {
                path = path.substring(15);
            } else if (path.startsWith("/")) {
                path = path.substring(1);
            }
            transferInitializing(dataSpec);
            this.inputStream = this.assetManager.open(path, 1);
            if (this.inputStream.skip(dataSpec.position) >= dataSpec.position) {
                if (dataSpec.length != -1) {
                    this.bytesRemaining = dataSpec.length;
                } else {
                    this.bytesRemaining = (long) this.inputStream.available();
                    if (this.bytesRemaining == 2147483647L) {
                        this.bytesRemaining = -1;
                    }
                }
                this.opened = true;
                transferStarted(dataSpec);
                return this.bytesRemaining;
            }
            throw new EOFException();
        } catch (IOException e) {
            throw new AssetDataSourceException(e);
        }
    }

    /* JADX INFO: Multiple debug info for r0v2 int: [D('bytesRead' int), D('bytesToRead' int)] */
    public int read(byte[] buffer, int offset, int readLength) throws AssetDataSourceException {
        int bytesToRead;
        if (readLength == 0) {
            return 0;
        }
        long j = this.bytesRemaining;
        if (j == 0) {
            return -1;
        }
        if (j == -1) {
            bytesToRead = readLength;
        } else {
            try {
                bytesToRead = (int) Math.min(j, (long) readLength);
            } catch (IOException e) {
                throw new AssetDataSourceException(e);
            }
        }
        int bytesToRead2 = this.inputStream.read(buffer, offset, bytesToRead);
        if (bytesToRead2 != -1) {
            long j2 = this.bytesRemaining;
            if (j2 != -1) {
                this.bytesRemaining = j2 - ((long) bytesToRead2);
            }
            bytesTransferred(bytesToRead2);
            return bytesToRead2;
        } else if (this.bytesRemaining == -1) {
            return -1;
        } else {
            throw new AssetDataSourceException(new EOFException());
        }
    }

    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    public void close() throws AssetDataSourceException {
        this.uri = null;
        try {
            if (this.inputStream != null) {
                this.inputStream.close();
            }
            this.inputStream = null;
            if (this.opened) {
                this.opened = false;
                transferEnded();
            }
        } catch (IOException e) {
            throw new AssetDataSourceException(e);
        } catch (Throwable th) {
            this.inputStream = null;
            if (this.opened) {
                this.opened = false;
                transferEnded();
            }
            throw th;
        }
    }

    public static final class AssetDataSourceException extends IOException {
        public AssetDataSourceException(IOException cause) {
            super(cause);
        }
    }
}
