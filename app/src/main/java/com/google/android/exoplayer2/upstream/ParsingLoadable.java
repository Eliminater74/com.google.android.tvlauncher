package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public final class ParsingLoadable<T> implements Loader.Loadable {
    public final DataSpec dataSpec;
    public final int type;
    private final StatsDataSource dataSource;
    private final Parser<? extends T> parser;
    @Nullable
    private volatile T result;

    public ParsingLoadable(DataSource dataSource2, Uri uri, int type2, Parser<? extends T> parser2) {
        this(dataSource2, new DataSpec(uri, 1), type2, parser2);
    }

    public ParsingLoadable(DataSource dataSource2, DataSpec dataSpec2, int type2, Parser<? extends T> parser2) {
        this.dataSource = new StatsDataSource(dataSource2);
        this.dataSpec = dataSpec2;
        this.type = type2;
        this.parser = parser2;
    }

    public static <T> T load(DataSource dataSource2, Parser<? extends T> parser2, Uri uri, int type2) throws IOException {
        ParsingLoadable<T> loadable = new ParsingLoadable<>(dataSource2, uri, type2, parser2);
        loadable.load();
        return Assertions.checkNotNull(loadable.getResult());
    }

    public static <T> T load(DataSource dataSource2, Parser<? extends T> parser2, DataSpec dataSpec2, int type2) throws IOException {
        ParsingLoadable<T> loadable = new ParsingLoadable<>(dataSource2, dataSpec2, type2, parser2);
        loadable.load();
        return Assertions.checkNotNull(loadable.getResult());
    }

    @Nullable
    public final T getResult() {
        return this.result;
    }

    public long bytesLoaded() {
        return this.dataSource.getBytesRead();
    }

    public Uri getUri() {
        return this.dataSource.getLastOpenedUri();
    }

    public Map<String, List<String>> getResponseHeaders() {
        return this.dataSource.getLastResponseHeaders();
    }

    public final void cancelLoad() {
    }

    public final void load() throws IOException {
        this.dataSource.resetBytesRead();
        DataSourceInputStream inputStream = new DataSourceInputStream(this.dataSource, this.dataSpec);
        try {
            inputStream.open();
            this.result = this.parser.parse((Uri) Assertions.checkNotNull(this.dataSource.getUri()), inputStream);
        } finally {
            Util.closeQuietly(inputStream);
        }
    }

    public interface Parser<T> {
        T parse(Uri uri, InputStream inputStream) throws IOException;
    }
}
