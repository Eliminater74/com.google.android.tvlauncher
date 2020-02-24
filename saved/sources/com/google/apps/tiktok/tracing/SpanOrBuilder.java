package com.google.apps.tiktok.tracing;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

public interface SpanOrBuilder extends MessageLiteOrBuilder {
    int getCpuTimeMs();

    long getDurationMs();

    int getId();

    String getName();

    ByteString getNameBytes();

    int getParentId();

    long getRelativeStartTimeMs();

    boolean getUiThread();

    boolean hasCpuTimeMs();

    boolean hasDurationMs();

    boolean hasId();

    boolean hasName();

    boolean hasParentId();

    boolean hasRelativeStartTimeMs();

    boolean hasUiThread();
}
