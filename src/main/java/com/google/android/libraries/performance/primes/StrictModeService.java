package com.google.android.libraries.performance.primes;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.StrictMode;
import android.os.strictmode.CustomViolation;
import android.os.strictmode.DiskReadViolation;
import android.os.strictmode.DiskWriteViolation;
import android.os.strictmode.Violation;
import com.google.android.libraries.performance.primes.MetricRecorder;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.stitch.util.ThreadUtil;
import java.util.concurrent.ScheduledExecutorService;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

@TargetApi(28)
final class StrictModeService extends AbstractMetricService implements PrimesStartupListener {
    private final StrictMode.OnThreadViolationListener onThreadViolation = new StrictMode.OnThreadViolationListener() {
        public void onThreadViolation(Violation v) {
            if (StrictModeService.this.shouldRecord()) {
                SystemHealthProto.StrictModeViolationMetric.Builder violation = SystemHealthProto.StrictModeViolationMetric.newBuilder();
                if (v instanceof DiskReadViolation) {
                    violation.setViolationType(SystemHealthProto.StrictModeViolationMetric.ViolationType.DISK_READ);
                } else if (v instanceof DiskWriteViolation) {
                    violation.setViolationType(SystemHealthProto.StrictModeViolationMetric.ViolationType.DISK_WRITE);
                } else if (v instanceof CustomViolation) {
                    violation.setViolationType(SystemHealthProto.StrictModeViolationMetric.ViolationType.SLOW);
                } else {
                    return;
                }
                StrictModeService.this.recordSystemHealthMetric((SystemHealthProto.SystemHealthMetric) SystemHealthProto.SystemHealthMetric.newBuilder().setStrictModeViolation(violation).build());
            }
        }
    };
    private final StrictMode.OnVmViolationListener onVmViolation = new StrictMode.OnVmViolationListener(this) {
        public void onVmViolation(Violation violation) {
        }
    };

    protected StrictModeService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, MetricRecorder.RunIn.BACKGROUND_THREAD);
    }

    public void onPrimesInitialize() {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyListener(getScheduledExecutorService(), this.onVmViolation).build());
        ThreadUtil.postOnUiThread(new StrictModeService$$Lambda$0(this));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onPrimesInitialize$0$StrictModeService() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyListener(getScheduledExecutorService(), this.onThreadViolation).build());
    }

    public void onFirstActivityCreated() {
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        StrictMode.setVmPolicy(StrictMode.VmPolicy.LAX);
        ThreadUtil.postOnUiThread(StrictModeService$$Lambda$1.$instance);
    }
}
