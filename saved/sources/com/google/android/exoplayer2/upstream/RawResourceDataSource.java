package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class RawResourceDataSource extends BaseDataSource {
    public static final String RAW_RESOURCE_SCHEME = "rawresource";
    @Nullable
    private AssetFileDescriptor assetFileDescriptor;
    private long bytesRemaining;
    @Nullable
    private InputStream inputStream;
    private boolean opened;
    private final Resources resources;
    @Nullable
    private Uri uri;

    public static class RawResourceDataSourceException extends IOException {
        public RawResourceDataSourceException(String message) {
            super(message);
        }

        public RawResourceDataSourceException(IOException e) {
            super(e);
        }
    }

    public static Uri buildRawResourceUri(int rawResourceId) {
        StringBuilder sb = new StringBuilder(26);
        sb.append("rawresource:///");
        sb.append(rawResourceId);
        return Uri.parse(sb.toString());
    }

    public RawResourceDataSource(Context context) {
        super(false);
        this.resources = context.getResources();
    }

    @Deprecated
    public RawResourceDataSource(Context context, @Nullable TransferListener listener) {
        this(context);
        if (listener != null) {
            addTransferListener(listener);
        }
    }

    public long open(DataSpec dataSpec) throws RawResourceDataSourceException {
        try {
            this.uri = dataSpec.uri;
            if (TextUtils.equals(RAW_RESOURCE_SCHEME, this.uri.getScheme())) {
                int resourceId = Integer.parseInt(this.uri.getLastPathSegment());
                transferInitializing(dataSpec);
                this.assetFileDescriptor = this.resources.openRawResourceFd(resourceId);
                this.inputStream = new FileInputStream(this.assetFileDescriptor.getFileDescriptor());
                this.inputStream.skip(this.assetFileDescriptor.getStartOffset());
                if (this.inputStream.skip(dataSpec.position) >= dataSpec.position) {
                    long j = -1;
                    if (dataSpec.length != -1) {
                        this.bytesRemaining = dataSpec.length;
                    } else {
                        long assetFileDescriptorLength = this.assetFileDescriptor.getLength();
                        if (assetFileDescriptorLength != -1) {
                            j = assetFileDescriptorLength - dataSpec.position;
                        }
                        this.bytesRemaining = j;
                    }
                    this.opened = true;
                    transferStarted(dataSpec);
                    return this.bytesRemaining;
                }
                throw new EOFException();
            }
            throw new RawResourceDataSourceException("URI must use scheme rawresource");
        } catch (NumberFormatException e) {
            throw new RawResourceDataSourceException("Resource identifier must be an integer.");
        } catch (IOException e2) {
            throw new RawResourceDataSourceException(e2);
        }
    }

    /* JADX INFO: Multiple debug info for r0v2 int: [D('bytesRead' int), D('bytesToRead' int)] */
    public int read(byte[] buffer, int offset, int readLength) throws RawResourceDataSourceException {
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
                throw new RawResourceDataSourceException(e);
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
            throw new RawResourceDataSourceException(new EOFException());
        }
    }

    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    public void close() throws RawResourceDataSourceException {
        this.uri = null;
        try {
            if (this.inputStream != null) {
                this.inputStream.close();
            }
            this.inputStream = null;
            try {
                if (this.assetFileDescriptor != null) {
                    this.assetFileDescriptor.close();
                }
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
            } catch (IOException e) {
                throw new RawResourceDataSourceException(e);
            } catch (Throwable th) {
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
                throw th;
            }
        } catch (IOException e2) {
            throw new RawResourceDataSourceException(e2);
        } catch (Throwable th2) {
            this.inputStream = null;
            try {
                if (this.assetFileDescriptor != null) {
                    this.assetFileDescriptor.close();
                }
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
                throw th2;
            } catch (IOException e3) {
                throw new RawResourceDataSourceException(e3);
            } catch (Throwable th3) {
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
                throw th3;
            }
        }
    }
}
