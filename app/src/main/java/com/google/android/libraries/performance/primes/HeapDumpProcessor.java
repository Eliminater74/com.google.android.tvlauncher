package com.google.android.libraries.performance.primes;

import com.google.android.libraries.performance.primes.hprof.HprofSerializer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import logs.proto.wireless.performance.mobile.PrimesHeapDumpProto;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

final class HeapDumpProcessor {
    static final int MAX_SERIALIZED_SIZE_KB = 10000;
    private static final String TAG = "HeapDumpProcessor";
    /* access modifiers changed from: private */
    public final HprofSerializer serializer;
    private final MetricStamper metricStamper;

    HeapDumpProcessor(HprofSerializer serializer2, MetricStamper metricStamper2) {
        this.serializer = serializer2;
        this.metricStamper = metricStamper2;
    }

    private static SystemHealthProto.SystemHealthMetric wireFormat(MetricStamper metricStamper2, PrimesHeapDumpProto.PrimesHeapDump primesHeapDump) {
        return metricStamper2.stamp((SystemHealthProto.SystemHealthMetric) SystemHealthProto.SystemHealthMetric.newBuilder().setPrimesHeapDump(primesHeapDump).build());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0017, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void writeToFile(java.io.File r4, logs.proto.wireless.performance.mobile.SystemHealthProto.SystemHealthMetric r5) {
        /*
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0018 }
            r0.<init>(r4)     // Catch:{ IOException -> 0x0018 }
            r5.writeTo(r0)     // Catch:{ all -> 0x000c }
            r0.close()     // Catch:{ IOException -> 0x0018 }
            goto L_0x002c
        L_0x000c:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x000e }
        L_0x000e:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0013 }
            goto L_0x0017
        L_0x0013:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r1, r3)     // Catch:{ IOException -> 0x0018 }
        L_0x0017:
            throw r2     // Catch:{ IOException -> 0x0018 }
        L_0x0018:
            r0 = move-exception
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r2 = "HeapDumpProcessor"
            java.lang.String r3 = "Failed to write mini heap dump to file."
            com.google.android.libraries.performance.primes.PrimesLog.m45d(r2, r3, r0, r1)
            boolean r1 = r4.exists()
            if (r1 == 0) goto L_0x002c
            r4.delete()
        L_0x002c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.performance.primes.HeapDumpProcessor.writeToFile(java.io.File, logs.proto.wireless.performance.mobile.SystemHealthProto$SystemHealthMetric):void");
    }

    /* access modifiers changed from: package-private */
    public List<SystemHealthProto.PrimesHeapDumpEvent> process(final File hprofFile, PrimesHeapDumpProto.HeapDumpContext heapDumpContext, File serializedHeapDumpFile) {
        if (!hprofFile.exists()) {
            return Collections.emptyList();
        }
        List<SystemHealthProto.PrimesHeapDumpEvent> events = new ArrayList<>(2);
        SystemHealthProto.PrimesHeapDumpEvent event = executeSerializer(new Callable<PrimesHeapDumpProto.PrimesHeapDump>() {
            public PrimesHeapDumpProto.PrimesHeapDump call() throws Exception {
                return HeapDumpProcessor.this.serializer.serialize(hprofFile);
            }
        }, heapDumpContext, serializedHeapDumpFile);
        events.add(event);
        if (event.getError() == SystemHealthProto.PrimesHeapDumpEvent.PrimesHeapDumpError.SERIALIZED_HEAP_DUMP_TOO_LARGE) {
            events.add(executeSerializer(new Callable<PrimesHeapDumpProto.PrimesHeapDump>() {
                public PrimesHeapDumpProto.PrimesHeapDump call() throws Exception {
                    return HeapDumpProcessor.this.serializer.serializeTopRooted(hprofFile);
                }
            }, heapDumpContext, serializedHeapDumpFile));
        }
        return events;
    }

    private SystemHealthProto.PrimesHeapDumpEvent executeSerializer(Callable<PrimesHeapDumpProto.PrimesHeapDump> serializeCallable, PrimesHeapDumpProto.HeapDumpContext heapDumpContext, File serializedHeapDumpFile) {
        SystemHealthProto.PrimesHeapDumpEvent.Builder event = SystemHealthProto.PrimesHeapDumpEvent.newBuilder().setError(SystemHealthProto.PrimesHeapDumpEvent.PrimesHeapDumpError.NONE);
        try {
            SystemHealthProto.SystemHealthMetric primesHeapDumpWireFormat = wireFormat(this.metricStamper, (PrimesHeapDumpProto.PrimesHeapDump) ((PrimesHeapDumpProto.PrimesHeapDump.Builder) serializeCallable.call().toBuilder()).setContext(heapDumpContext).build());
            event.setSerializedSizeKb(primesHeapDumpWireFormat.getSerializedSize() / 1024);
            if (event.getSerializedSizeKb() > 10000) {
                return (SystemHealthProto.PrimesHeapDumpEvent) event.setError(SystemHealthProto.PrimesHeapDumpEvent.PrimesHeapDumpError.SERIALIZED_HEAP_DUMP_TOO_LARGE).build();
            }
            writeToFile(serializedHeapDumpFile, primesHeapDumpWireFormat);
            return (SystemHealthProto.PrimesHeapDumpEvent) event.build();
        } catch (OutOfMemoryError e) {
            event.setError(SystemHealthProto.PrimesHeapDumpEvent.PrimesHeapDumpError.OUT_OF_MEMORY_SERIALIZING);
        } catch (Exception e2) {
            event.setError(SystemHealthProto.PrimesHeapDumpEvent.PrimesHeapDumpError.UNKNOWN);
        }
    }
}
