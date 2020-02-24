package com.google.android.libraries.gcoreclient.clearcut.impl;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogEventBuilder;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogger;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutMessageProducer;
import com.google.android.libraries.gcoreclient.common.api.GcoreStatus;
import com.google.android.libraries.gcoreclient.common.api.support.GcoreStatusImpl;
import com.google.android.libraries.gcoreclient.common.api.support.ResultWrapper;
import java.util.concurrent.TimeUnit;

abstract class BaseClearcutLoggerImpl implements GcoreClearcutLogger {
    static final ResultWrapper<GcoreStatus, Status> RESULT_WRAPPER = new ResultWrapper<GcoreStatus, Status>() {
        public GcoreStatus wrap(Status result) {
            return new GcoreStatusImpl(result);
        }
    };
    private final ClearcutLogger clearcutLogger;

    BaseClearcutLoggerImpl(ClearcutLogger anonymousClearcutLogger) {
        this.clearcutLogger = anonymousClearcutLogger;
    }

    BaseClearcutLoggerImpl(Context context, String logSourceName, @Nullable String uploadAccountName) {
        this.clearcutLogger = new ClearcutLogger(context, logSourceName, uploadAccountName, (String) null);
    }

    static ClearcutLogger unwrap(GcoreClearcutLogger logger) {
        if (logger instanceof BaseClearcutLoggerImpl) {
            return ((BaseClearcutLoggerImpl) logger).clearcutLogger;
        }
        return null;
    }

    public final GcoreClearcutLogEventBuilder newEvent(byte[] protoExtension) {
        return new GcoreClearcutLogEventBuilderImpl(this.clearcutLogger, protoExtension);
    }

    public GcoreClearcutLogEventBuilder newEvent(final GcoreClearcutMessageProducer extensionProducer) {
        return new GcoreClearcutLogEventBuilderImpl(this.clearcutLogger, new ClearcutLogger.MessageProducer(this) {
            public byte[] toProtoBytes() {
                return extensionProducer.toProtoBytes();
            }
        });
    }

    public final boolean flush(long waitTime, TimeUnit unit) {
        return this.clearcutLogger.flush(null, waitTime, unit);
    }
}
