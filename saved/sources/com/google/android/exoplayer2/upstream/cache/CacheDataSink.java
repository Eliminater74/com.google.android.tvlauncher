package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ReusableBufferedOutputStream;
import com.google.android.exoplayer2.util.Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class CacheDataSink implements DataSink {
    public static final int DEFAULT_BUFFER_SIZE = 20480;
    public static final long DEFAULT_FRAGMENT_SIZE = 5242880;
    private static final long MIN_RECOMMENDED_FRAGMENT_SIZE = 2097152;
    private static final String TAG = "CacheDataSink";
    private final int bufferSize;
    private ReusableBufferedOutputStream bufferedOutputStream;
    private final Cache cache;
    private DataSpec dataSpec;
    private long dataSpecBytesWritten;
    private long dataSpecFragmentSize;
    private File file;
    private final long fragmentSize;
    private OutputStream outputStream;
    private long outputStreamBytesWritten;
    private boolean syncFileDescriptor;
    private FileOutputStream underlyingFileOutputStream;

    public static class CacheDataSinkException extends Cache.CacheException {
        public CacheDataSinkException(IOException cause) {
            super(cause);
        }
    }

    public CacheDataSink(Cache cache2, long fragmentSize2) {
        this(cache2, fragmentSize2, DEFAULT_BUFFER_SIZE);
    }

    public CacheDataSink(Cache cache2, long fragmentSize2, int bufferSize2) {
        Assertions.checkState(fragmentSize2 > 0 || fragmentSize2 == -1, "fragmentSize must be positive or C.LENGTH_UNSET.");
        if (fragmentSize2 != -1 && fragmentSize2 < 2097152) {
            Log.m30w(TAG, "fragmentSize is below the minimum recommended value of 2097152. This may cause poor cache performance.");
        }
        this.cache = (Cache) Assertions.checkNotNull(cache2);
        this.fragmentSize = fragmentSize2 == -1 ? Long.MAX_VALUE : fragmentSize2;
        this.bufferSize = bufferSize2;
        this.syncFileDescriptor = true;
    }

    public void experimental_setSyncFileDescriptor(boolean syncFileDescriptor2) {
        this.syncFileDescriptor = syncFileDescriptor2;
    }

    public void open(DataSpec dataSpec2) throws CacheDataSinkException {
        if (dataSpec2.length != -1 || !dataSpec2.isFlagSet(4)) {
            this.dataSpec = dataSpec2;
            this.dataSpecFragmentSize = dataSpec2.isFlagSet(16) ? this.fragmentSize : Long.MAX_VALUE;
            this.dataSpecBytesWritten = 0;
            try {
                openNextOutputStream();
            } catch (IOException e) {
                throw new CacheDataSinkException(e);
            }
        } else {
            this.dataSpec = null;
        }
    }

    public void write(byte[] buffer, int offset, int length) throws CacheDataSinkException {
        if (this.dataSpec != null) {
            int bytesWritten = 0;
            while (bytesWritten < length) {
                try {
                    if (this.outputStreamBytesWritten == this.dataSpecFragmentSize) {
                        closeCurrentOutputStream();
                        openNextOutputStream();
                    }
                    int bytesToWrite = (int) Math.min((long) (length - bytesWritten), this.dataSpecFragmentSize - this.outputStreamBytesWritten);
                    this.outputStream.write(buffer, offset + bytesWritten, bytesToWrite);
                    bytesWritten += bytesToWrite;
                    this.outputStreamBytesWritten += (long) bytesToWrite;
                    this.dataSpecBytesWritten += (long) bytesToWrite;
                } catch (IOException e) {
                    throw new CacheDataSinkException(e);
                }
            }
        }
    }

    public void close() throws CacheDataSinkException {
        if (this.dataSpec != null) {
            try {
                closeCurrentOutputStream();
            } catch (IOException e) {
                throw new CacheDataSinkException(e);
            }
        }
    }

    private void openNextOutputStream() throws IOException {
        long length;
        if (this.dataSpec.length == -1) {
            length = -1;
        } else {
            length = Math.min(this.dataSpec.length - this.dataSpecBytesWritten, this.dataSpecFragmentSize);
        }
        this.file = this.cache.startFile(this.dataSpec.key, this.dataSpec.absoluteStreamPosition + this.dataSpecBytesWritten, length);
        this.underlyingFileOutputStream = new FileOutputStream(this.file);
        int i = this.bufferSize;
        if (i > 0) {
            ReusableBufferedOutputStream reusableBufferedOutputStream = this.bufferedOutputStream;
            if (reusableBufferedOutputStream == null) {
                this.bufferedOutputStream = new ReusableBufferedOutputStream(this.underlyingFileOutputStream, i);
            } else {
                reusableBufferedOutputStream.reset(this.underlyingFileOutputStream);
            }
            this.outputStream = this.bufferedOutputStream;
        } else {
            this.outputStream = this.underlyingFileOutputStream;
        }
        this.outputStreamBytesWritten = 0;
    }

    private void closeCurrentOutputStream() throws IOException {
        File fileToCommit;
        OutputStream outputStream2 = this.outputStream;
        if (outputStream2 != null) {
            boolean success = false;
            try {
                outputStream2.flush();
                if (this.syncFileDescriptor) {
                    this.underlyingFileOutputStream.getFD().sync();
                }
                success = true;
                if (!success) {
                    fileToCommit.delete();
                }
            } finally {
                Util.closeQuietly(this.outputStream);
                this.outputStream = null;
                fileToCommit = this.file;
                this.file = null;
                if (success) {
                    this.cache.commitFile(fileToCommit, this.outputStreamBytesWritten);
                } else {
                    fileToCommit.delete();
                }
            }
        }
    }
}
