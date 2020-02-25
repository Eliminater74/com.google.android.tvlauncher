package com.google.apps.tiktok.tracing;

import com.google.protobuf.MessageLiteOrBuilder;

import java.util.List;

public interface TraceRecordOrBuilder extends MessageLiteOrBuilder {
    CollectionError getError();

    Span getSpans(int i);

    int getSpansCount();

    List<Span> getSpansList();

    long getStartElapsedTimeMs();

    long getStartTimeMs();

    long getUuidLeastSignificantBits();

    long getUuidMostSignificantBits();

    boolean hasError();

    boolean hasStartElapsedTimeMs();

    boolean hasStartTimeMs();

    boolean hasUuidLeastSignificantBits();

    boolean hasUuidMostSignificantBits();
}
