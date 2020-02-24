package com.google.android.tvlauncher.analytics;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClearcutAppEventLogger extends AppEventLogger implements EventLogger {
    private static final long DEFAULT_PENDING_PARAMETERS_TIMEOUT = 5000;
    private static final int MSG_FLUSH_PENDING_EVENT = 1;
    private static final String TAG = "Clearcut-EventLogger";
    private final ClearcutEventLoggerEngine mEngine;
    private List<String> mExpectedParameters;
    private Handler mHandler;
    private LogEvent mPendingEvent;

    @MainThread
    public static void init(Context context, ClearcutEventLoggerEngine engine) {
        sInstance = new ClearcutAppEventLogger(engine);
        checkOptedInForUsageReporting(context);
    }

    @VisibleForTesting
    ClearcutAppEventLogger(ClearcutEventLoggerEngine engine) {
        this.mEngine = engine;
    }

    /* access modifiers changed from: protected */
    public void setUsageReportingOptedIn(boolean optedIn) {
        this.mEngine.setEnabled(optedIn);
    }

    /* access modifiers changed from: package-private */
    public void setName(Activity activity, String name) {
    }

    public void log(LogEvent event) {
        if (event instanceof LogEventParameters) {
            mergePendingParameters((LogEventParameters) event);
            return;
        }
        flushPendingEvent();
        String[] expectedParameters = event.getExpectedParameters();
        if (expectedParameters != null && expectedParameters.length != 0) {
            this.mPendingEvent = event;
            this.mExpectedParameters = new ArrayList(expectedParameters.length);
            Collections.addAll(this.mExpectedParameters, expectedParameters);
            long timeout = event.getParameterTimeout();
            if (timeout == 0) {
                timeout = 5000;
            }
            if (this.mHandler == null) {
                this.mHandler = new Handler(Looper.getMainLooper()) {
                    public void handleMessage(Message msg) {
                        if (msg.what == 1) {
                            ClearcutAppEventLogger.this.flushPendingEvent();
                        }
                    }
                };
            }
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageDelayed(1, timeout);
        } else if (event.shouldBypassUsageReportingOptOut()) {
            this.mEngine.logEventUnconditionally(event.getEventCode(), event.getClientLogEntry());
        } else {
            this.mEngine.logEvent(event.getEventCode(), event.getClientLogEntry());
        }
    }

    private void mergePendingParameters(LogEventParameters parameters) {
        LogEvent logEvent = this.mPendingEvent;
        if (logEvent == null) {
            String valueOf = String.valueOf(parameters.getParameterName());
            Log.e(TAG, valueOf.length() != 0 ? "Unexpected log parameter ".concat(valueOf) : new String("Unexpected log parameter "));
        } else if (logEvent.getEventCode() == null || !this.mPendingEvent.getEventCode().equals(parameters.getEventCode())) {
            String valueOf2 = String.valueOf(parameters.getEventCode());
            String valueOf3 = String.valueOf(this.mPendingEvent.getEventCode());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf2).length() + 56 + String.valueOf(valueOf3).length());
            sb.append("Parameters for a previous event. Event code: ");
            sb.append(valueOf2);
            sb.append(", expected ");
            sb.append(valueOf3);
            Log.e(TAG, sb.toString());
        } else {
            boolean expected = false;
            String parameter = parameters.getParameterName();
            String[] expectedParameters = this.mPendingEvent.getExpectedParameters();
            int i = 0;
            while (true) {
                if (i >= expectedParameters.length) {
                    break;
                } else if (expectedParameters[i].equals(parameter)) {
                    expected = true;
                    break;
                } else {
                    i++;
                }
            }
            if (!expected) {
                String valueOf4 = String.valueOf(parameters.getParameterName());
                Log.e(TAG, valueOf4.length() != 0 ? "Unexpected log parameter ".concat(valueOf4) : new String("Unexpected log parameter "));
                return;
            }
            this.mPendingEvent.mergeFrom(parameters);
            this.mExpectedParameters.remove(parameter);
            if (this.mExpectedParameters.isEmpty()) {
                flushPendingEvent();
            }
        }
    }

    /* access modifiers changed from: private */
    public void flushPendingEvent() {
        LogEvent logEvent = this.mPendingEvent;
        if (logEvent != null) {
            this.mEngine.logEvent(logEvent.getEventCode(), this.mPendingEvent.getClientLogEntry());
            this.mPendingEvent = null;
            this.mExpectedParameters = null;
        }
    }
}
