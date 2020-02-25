package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class DefaultDataSource implements DataSource {
    private static final String SCHEME_ASSET = "asset";
    private static final String SCHEME_CONTENT = "content";
    private static final String SCHEME_RAW = "rawresource";
    private static final String SCHEME_RTMP = "rtmp";
    private static final String TAG = "DefaultDataSource";
    private final DataSource baseDataSource;
    private final Context context;
    private final List<TransferListener> transferListeners;
    @Nullable
    private DataSource assetDataSource;
    @Nullable
    private DataSource contentDataSource;
    @Nullable
    private DataSource dataSchemeDataSource;
    @Nullable
    private DataSource dataSource;
    @Nullable
    private DataSource fileDataSource;
    @Nullable
    private DataSource rawResourceDataSource;
    @Nullable
    private DataSource rtmpDataSource;

    public DefaultDataSource(Context context2, String userAgent, boolean allowCrossProtocolRedirects) {
        this(context2, userAgent, 8000, 8000, allowCrossProtocolRedirects);
    }

    public DefaultDataSource(Context context2, String userAgent, int connectTimeoutMillis, int readTimeoutMillis, boolean allowCrossProtocolRedirects) {
        this(context2, new DefaultHttpDataSource(userAgent, null, connectTimeoutMillis, readTimeoutMillis, allowCrossProtocolRedirects, null));
    }

    public DefaultDataSource(Context context2, DataSource baseDataSource2) {
        this.context = context2.getApplicationContext();
        this.baseDataSource = (DataSource) Assertions.checkNotNull(baseDataSource2);
        this.transferListeners = new ArrayList();
    }

    @Deprecated
    public DefaultDataSource(Context context2, @Nullable TransferListener listener, String userAgent, boolean allowCrossProtocolRedirects) {
        this(context2, listener, userAgent, 8000, 8000, allowCrossProtocolRedirects);
    }

    @Deprecated
    public DefaultDataSource(Context context2, @Nullable TransferListener listener, String userAgent, int connectTimeoutMillis, int readTimeoutMillis, boolean allowCrossProtocolRedirects) {
        this(context2, listener, new DefaultHttpDataSource(userAgent, null, listener, connectTimeoutMillis, readTimeoutMillis, allowCrossProtocolRedirects, null));
    }

    @Deprecated
    public DefaultDataSource(Context context2, @Nullable TransferListener listener, DataSource baseDataSource2) {
        this(context2, baseDataSource2);
        if (listener != null) {
            this.transferListeners.add(listener);
        }
    }

    public void addTransferListener(TransferListener transferListener) {
        this.baseDataSource.addTransferListener(transferListener);
        this.transferListeners.add(transferListener);
        maybeAddListenerToDataSource(this.fileDataSource, transferListener);
        maybeAddListenerToDataSource(this.assetDataSource, transferListener);
        maybeAddListenerToDataSource(this.contentDataSource, transferListener);
        maybeAddListenerToDataSource(this.rtmpDataSource, transferListener);
        maybeAddListenerToDataSource(this.dataSchemeDataSource, transferListener);
        maybeAddListenerToDataSource(this.rawResourceDataSource, transferListener);
    }

    public long open(DataSpec dataSpec) throws IOException {
        Assertions.checkState(this.dataSource == null);
        String scheme = dataSpec.uri.getScheme();
        if (Util.isLocalFileUri(dataSpec.uri)) {
            String uriPath = dataSpec.uri.getPath();
            if (uriPath == null || !uriPath.startsWith("/android_asset/")) {
                this.dataSource = getFileDataSource();
            } else {
                this.dataSource = getAssetDataSource();
            }
        } else if (SCHEME_ASSET.equals(scheme)) {
            this.dataSource = getAssetDataSource();
        } else if (SCHEME_CONTENT.equals(scheme)) {
            this.dataSource = getContentDataSource();
        } else if (SCHEME_RTMP.equals(scheme)) {
            this.dataSource = getRtmpDataSource();
        } else if ("data".equals(scheme)) {
            this.dataSource = getDataSchemeDataSource();
        } else if ("rawresource".equals(scheme)) {
            this.dataSource = getRawResourceDataSource();
        } else {
            this.dataSource = this.baseDataSource;
        }
        return this.dataSource.open(dataSpec);
    }

    public int read(byte[] buffer, int offset, int readLength) throws IOException {
        return ((DataSource) Assertions.checkNotNull(this.dataSource)).read(buffer, offset, readLength);
    }

    @Nullable
    public Uri getUri() {
        DataSource dataSource2 = this.dataSource;
        if (dataSource2 == null) {
            return null;
        }
        return dataSource2.getUri();
    }

    public Map<String, List<String>> getResponseHeaders() {
        DataSource dataSource2 = this.dataSource;
        return dataSource2 == null ? Collections.emptyMap() : dataSource2.getResponseHeaders();
    }

    public void close() throws IOException {
        DataSource dataSource2 = this.dataSource;
        if (dataSource2 != null) {
            try {
                dataSource2.close();
            } finally {
                this.dataSource = null;
            }
        }
    }

    private DataSource getFileDataSource() {
        if (this.fileDataSource == null) {
            this.fileDataSource = new FileDataSource();
            addListenersToDataSource(this.fileDataSource);
        }
        return this.fileDataSource;
    }

    private DataSource getAssetDataSource() {
        if (this.assetDataSource == null) {
            this.assetDataSource = new AssetDataSource(this.context);
            addListenersToDataSource(this.assetDataSource);
        }
        return this.assetDataSource;
    }

    private DataSource getContentDataSource() {
        if (this.contentDataSource == null) {
            this.contentDataSource = new ContentDataSource(this.context);
            addListenersToDataSource(this.contentDataSource);
        }
        return this.contentDataSource;
    }

    private DataSource getRtmpDataSource() {
        if (this.rtmpDataSource == null) {
            try {
                this.rtmpDataSource = (DataSource) Class.forName("com.google.android.exoplayer2.ext.rtmp.RtmpDataSource").getConstructor(new Class[0]).newInstance(new Object[0]);
                addListenersToDataSource(this.rtmpDataSource);
            } catch (ClassNotFoundException e) {
                Log.m30w(TAG, "Attempting to play RTMP stream without depending on the RTMP extension");
            } catch (Exception e2) {
                throw new RuntimeException("Error instantiating RTMP extension", e2);
            }
            if (this.rtmpDataSource == null) {
                this.rtmpDataSource = this.baseDataSource;
            }
        }
        return this.rtmpDataSource;
    }

    private DataSource getDataSchemeDataSource() {
        if (this.dataSchemeDataSource == null) {
            this.dataSchemeDataSource = new DataSchemeDataSource();
            addListenersToDataSource(this.dataSchemeDataSource);
        }
        return this.dataSchemeDataSource;
    }

    private DataSource getRawResourceDataSource() {
        if (this.rawResourceDataSource == null) {
            this.rawResourceDataSource = new RawResourceDataSource(this.context);
            addListenersToDataSource(this.rawResourceDataSource);
        }
        return this.rawResourceDataSource;
    }

    private void addListenersToDataSource(DataSource dataSource2) {
        for (int i = 0; i < this.transferListeners.size(); i++) {
            dataSource2.addTransferListener(this.transferListeners.get(i));
        }
    }

    private void maybeAddListenerToDataSource(@Nullable DataSource dataSource2, TransferListener listener) {
        if (dataSource2 != null) {
            dataSource2.addTransferListener(listener);
        }
    }
}
