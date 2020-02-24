package com.google.apps.tiktok.tracing;

import com.google.apps.tiktok.tracing.CollectionError;
import com.google.protobuf.MessageLiteOrBuilder;

public interface CollectionErrorOrBuilder extends MessageLiteOrBuilder {
    CollectionError.TimedOut getTimedOut();

    CollectionError.TooManySpans getTooManySpans();

    boolean hasTimedOut();

    boolean hasTooManySpans();
}
