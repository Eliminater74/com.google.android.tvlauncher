package com.google.android.libraries.gcoreclient.clearcut.impl;

import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogEventBuilder;
import com.google.android.libraries.gcoreclient.common.api.GcorePendingResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreStatus;
import com.google.android.libraries.gcoreclient.common.api.support.GcorePendingResultImpl;

abstract class BaseClearcutLogEventBuilderImpl implements GcoreClearcutLogEventBuilder {
    final ClearcutLogger.LogEventBuilder logEventBuilder;

    BaseClearcutLogEventBuilderImpl(ClearcutLogger clearcutLogger, byte[] protoExtension) {
        this.logEventBuilder = clearcutLogger.newEvent(protoExtension);
    }

    BaseClearcutLogEventBuilderImpl(ClearcutLogger clearcutLogger, ClearcutLogger.MessageProducer extensionProducer) {
        this.logEventBuilder = clearcutLogger.newEvent(extensionProducer);
    }

    public GcoreClearcutLogEventBuilder setLogSourceName(String logSourceName) {
        this.logEventBuilder.setLogSourceName(logSourceName);
        return this;
    }

    public GcoreClearcutLogEventBuilder setUploadAccountName(String accountName) {
        this.logEventBuilder.setUploadAccountName(accountName);
        return this;
    }

    public GcoreClearcutLogEventBuilder setZwiebackCookieOverride(String zwiebackCookieOverride) {
        return this;
    }

    public GcoreClearcutLogEventBuilder setEventCode(int eventCode) {
        this.logEventBuilder.setEventCode(eventCode);
        return this;
    }

    public GcoreClearcutLogEventBuilder setEventFlowId(int eventFlowId) {
        this.logEventBuilder.setEventFlowId(eventFlowId);
        return this;
    }

    public GcorePendingResult<GcoreStatus> logAsync() {
        return new GcorePendingResultImpl(this.logEventBuilder.logAsync(), BaseClearcutLoggerImpl.RESULT_WRAPPER);
    }
}
