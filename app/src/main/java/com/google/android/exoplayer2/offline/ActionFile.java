package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.AtomicFile;
import com.google.android.exoplayer2.util.Util;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Deprecated
final class ActionFile {
    private static final int VERSION = 0;
    private final AtomicFile atomicFile;

    public ActionFile(File actionFile) {
        this.atomicFile = new AtomicFile(actionFile);
    }

    private static DownloadRequest readDownloadRequest(DataInputStream input) throws IOException {
        byte[] data;
        String customCacheKey;
        DataInputStream dataInputStream = input;
        String type = input.readUTF();
        int version = input.readInt();
        Uri uri = Uri.parse(input.readUTF());
        boolean isRemoveAction = input.readBoolean();
        int dataLength = input.readInt();
        if (dataLength != 0) {
            byte[] data2 = new byte[dataLength];
            dataInputStream.readFully(data2);
            data = data2;
        } else {
            data = null;
        }
        boolean isLegacySegmented = false;
        boolean isLegacyProgressive = version == 0 && DownloadRequest.TYPE_PROGRESSIVE.equals(type);
        List<StreamKey> keys = new ArrayList<>();
        if (!isLegacyProgressive) {
            int keyCount = input.readInt();
            for (int i = 0; i < keyCount; i++) {
                keys.add(readKey(type, version, dataInputStream));
            }
        }
        if (version < 2 && (DownloadRequest.TYPE_DASH.equals(type) || DownloadRequest.TYPE_HLS.equals(type) || DownloadRequest.TYPE_SS.equals(type))) {
            isLegacySegmented = true;
        }
        if (!isLegacySegmented) {
            customCacheKey = input.readBoolean() ? input.readUTF() : null;
        } else {
            customCacheKey = null;
        }
        String id = version < 3 ? generateDownloadId(uri, customCacheKey) : input.readUTF();
        if (!isRemoveAction) {
            return new DownloadRequest(id, type, uri, keys, customCacheKey, data);
        }
        throw new DownloadRequest.UnsupportedRequestException();
    }

    private static StreamKey readKey(String type, int version, DataInputStream input) throws IOException {
        int trackIndex;
        int groupIndex;
        int periodIndex;
        if ((DownloadRequest.TYPE_HLS.equals(type) || DownloadRequest.TYPE_SS.equals(type)) && version == 0) {
            periodIndex = 0;
            groupIndex = input.readInt();
            trackIndex = input.readInt();
        } else {
            periodIndex = input.readInt();
            groupIndex = input.readInt();
            trackIndex = input.readInt();
        }
        return new StreamKey(periodIndex, groupIndex, trackIndex);
    }

    private static String generateDownloadId(Uri uri, @Nullable String customCacheKey) {
        return customCacheKey != null ? customCacheKey : uri.toString();
    }

    public boolean exists() {
        return this.atomicFile.exists();
    }

    public void delete() {
        this.atomicFile.delete();
    }

    public DownloadRequest[] load() throws IOException {
        if (!exists()) {
            return new DownloadRequest[0];
        }
        InputStream inputStream = null;
        try {
            inputStream = this.atomicFile.openRead();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            int version = dataInputStream.readInt();
            if (version <= 0) {
                int actionCount = dataInputStream.readInt();
                ArrayList<DownloadRequest> actions = new ArrayList<>();
                for (int i = 0; i < actionCount; i++) {
                    try {
                        actions.add(readDownloadRequest(dataInputStream));
                    } catch (DownloadRequest.UnsupportedRequestException e) {
                    }
                }
                return (DownloadRequest[]) actions.toArray(new DownloadRequest[0]);
            }
            StringBuilder sb = new StringBuilder(44);
            sb.append("Unsupported action file version: ");
            sb.append(version);
            throw new IOException(sb.toString());
        } finally {
            Util.closeQuietly(inputStream);
        }
    }
}
