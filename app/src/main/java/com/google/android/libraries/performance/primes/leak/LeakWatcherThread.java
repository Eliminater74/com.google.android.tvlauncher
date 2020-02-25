package com.google.android.libraries.performance.primes.leak;

import android.os.Debug;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.media.session.PlaybackStateCompat;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.performance.primes.hprof.HprofAnalyzer;
import com.google.android.libraries.stitch.util.Preconditions;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

class LeakWatcherThread extends Thread {
    @VisibleForTesting
    static final int DUMMY_GC_CYCLES = 3;
    @VisibleForTesting
    static final int QUEUE_FOR_DUMP_MAX_SIZE = 500;
    private static final int DUMMY_DEPTH = 20;
    private static final int DUMMY_RELEASE_MIN_GAP = 5000;
    private static final String SENTINEL = "Sentinel";
    private static final String TAG = "LeakWatcherThread";
    @VisibleForTesting
    final Deque<Object> dummyQueue;
    @VisibleForTesting
    final GarbageReference queueForDump;
    private final Deque<GarbageReference> garbageListQueue;
    private final GarbageReference incomingList;
    private final LeakListener leakListener;
    private final boolean quantifyLeakSizeEnabled;
    private final GarbageReferenceFactory referenceFactory;
    private final ReferenceQueue<Object> referenceQueue;
    private File hprofFile;

    private LeakWatcherThread(ReferenceQueue<Object> referenceQueue2, GarbageReferenceFactory referenceFactory2, LeakListener leakListener2, boolean quantifyLeakSizeEnabled2) {
        this.dummyQueue = new ArrayDeque(20);
        this.garbageListQueue = new ArrayDeque(3);
        setName("Primes-Watcher");
        this.referenceQueue = referenceQueue2;
        this.leakListener = leakListener2;
        this.referenceFactory = referenceFactory2;
        this.quantifyLeakSizeEnabled = quantifyLeakSizeEnabled2;
        this.incomingList = new GarbageReference(SENTINEL, SENTINEL, referenceQueue2);
        this.queueForDump = new GarbageReference(SENTINEL, SENTINEL, referenceQueue2);
        for (int i = 0; i < 20; i++) {
            this.dummyQueue.add(new Object());
        }
        for (int i2 = 0; i2 < 3; i2++) {
            this.garbageListQueue.add(new GarbageReference(SENTINEL, SENTINEL, referenceQueue2));
        }
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                Thread.sleep(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                processOneRound();
            } catch (InterruptedException e) {
                interrupt();
                if (this.hprofFile != null) {
                    interrupted();
                    dumpAndAnalyzeHeap();
                }
            }
        }
        synchronized (this.incomingList) {
            this.incomingList.next = null;
        }
        this.dummyQueue.clear();
        this.garbageListQueue.clear();
    }

    /* access modifiers changed from: package-private */
    public void watch(Object object, String name) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(object);
        PrimesLog.m46d(TAG, "Watching %s", name);
        GarbageReference newRef = this.referenceFactory.newReference(object, name, this.referenceQueue);
        synchronized (this.incomingList) {
            newRef.insertAfter(this.incomingList);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void processOneRound() throws InterruptedException {
        readAndProcessQueueTillDummyCollected();
        advanceListQueue();
    }

    private void readAndProcessQueueTillDummyCollected() throws InterruptedException {
        Object dummy = this.dummyQueue.poll();
        this.dummyQueue.offer(new Object());
        Reference<?> dummyRef = this.referenceFactory.newReference(dummy, "", this.referenceQueue);
        boolean dummyRefFound = false;
        while (!dummyRefFound) {
            Reference<?> ref = null;
            while (ref == null) {
                try {
                    ref = this.referenceQueue.remove();
                } catch (InterruptedException e) {
                    if (this.hprofFile != null) {
                        dumpAndAnalyzeHeap();
                    } else {
                        throw e;
                    }
                }
            }
            while (true) {
                boolean z = false;
                if (ref == null) {
                    break;
                }
                if (ref == dummyRef) {
                    if (!dummyRefFound) {
                        z = true;
                    }
                    Preconditions.checkState(z, "Only one dummy released at a time.");
                    dummyRefFound = true;
                } else {
                    this.leakListener.onReleased(removeRef((GarbageReference) ref));
                }
                ref = this.referenceQueue.poll();
            }
            if (!dummyRefFound) {
                this.leakListener.onBatchComplete(false);
            }
        }
    }

    private void advanceListQueue() {
        GarbageReference head = this.garbageListQueue.poll();
        boolean leakFound = head.next != null;
        if (PrimesLog.dLoggable(TAG)) {
            String[] strArr = new String[1];
            strArr[0] = leakFound ? "" : "no";
            PrimesLog.m46d(TAG, "Check for leak: %s leak found", strArr);
        }
        int queueForDumpSize = 0;
        for (GarbageReference ref = this.queueForDump.next; ref != null; ref = ref.next) {
            queueForDumpSize++;
        }
        while (head.next != null) {
            GarbageReference ref2 = head.next.removeSelf();
            this.leakListener.onLeaked(ref2.name);
            if (queueForDumpSize < 500) {
                ref2.insertAfter(this.queueForDump);
                queueForDumpSize++;
            }
        }
        this.garbageListQueue.offer(head);
        synchronized (this.incomingList) {
            if (this.incomingList.next != null) {
                head.next = this.incomingList.next;
                head.next.prev = head;
                this.incomingList.next = null;
            }
        }
        this.leakListener.onBatchComplete(leakFound);
    }

    private String removeRef(GarbageReference ref) {
        GarbageReference garbageReference = ref.prev;
        GarbageReference garbageReference2 = this.incomingList;
        if (garbageReference == garbageReference2) {
            synchronized (garbageReference2) {
                ref.removeSelf();
            }
        } else {
            ref.removeSelf();
        }
        return ref.name;
    }

    /* access modifiers changed from: package-private */
    public boolean scheduleHeapDumpAndAnalysis(File hprofFile2) {
        if (this.queueForDump.next != null) {
            this.hprofFile = (File) Preconditions.checkNotNull(hprofFile2);
            interrupt();
            PrimesLog.m46d(TAG, "Schedule for heap dump", new Object[0]);
            return true;
        }
        PrimesLog.m46d(TAG, "Skip heap dump. No leak suspects found.", new Object[0]);
        return false;
    }

    /* JADX INFO: Multiple debug info for r1v2 java.io.File: [D('t' java.lang.Throwable), D('fileToDelete' java.io.File)] */
    private void dumpAndAnalyzeHeap() {
        Preconditions.checkState(this.hprofFile != null);
        if (this.hprofFile.exists()) {
            PrimesLog.m46d(TAG, "Abort dumping heap because heapdump file %s exists", this.hprofFile.getName());
            this.hprofFile = null;
            return;
        }
        GarbageReference cutoff = new GarbageReference(SENTINEL, SENTINEL, this.referenceQueue);
        synchronized (this.incomingList) {
            cutoff.insertAfter(this.incomingList);
            this.incomingList.next = null;
            cutoff.prev = null;
        }
        try {
            long startTime = System.nanoTime();
            Debug.dumpHprofData(this.hprofFile.getAbsolutePath());
            if (PrimesLog.dLoggable(TAG)) {
                PrimesLog.m46d(TAG, "Hprof dumped. File size: %d  MB. Took %d ms.", Long.valueOf(this.hprofFile.length() / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED), Long.valueOf((System.nanoTime() - startTime) / 1000000));
            }
            long startTime2 = System.nanoTime();
            List<LeakInfo> leaks = new HprofAnalyzer(this.hprofFile, this.quantifyLeakSizeEnabled).checkTrackedObjectsForLeak(GarbageReference.class.getName());
            if (!leaks.isEmpty()) {
                this.leakListener.onHeapDumpResult(leaks);
            }
            for (GarbageReference ref : this.garbageListQueue) {
                ref.removeSelf();
            }
            this.queueForDump.removeSelf();
            if (PrimesLog.dLoggable(TAG)) {
                int size = leaks.size();
                StringBuilder sb = new StringBuilder(69);
                sb.append("Found ");
                sb.append(size);
                sb.append(" leak(s). The analysis took ");
                sb.append((System.nanoTime() - startTime2) / 1000000);
                sb.append(" ms.");
                PrimesLog.m46d(TAG, sb.toString(), new Object[0]);
            }
        } catch (Throwable t) {
            File fileToDelete = this.hprofFile;
            this.hprofFile = null;
            fileToDelete.delete();
            throw t;
        }
        File fileToDelete2 = this.hprofFile;
        this.hprofFile = null;
        fileToDelete2.delete();
    }

    @VisibleForTesting
    interface GarbageReferenceFactory {
        GarbageReference newReference(Object obj, String str, ReferenceQueue<Object> referenceQueue);
    }

    static class LeakWatcherThreadFactory {
        private final boolean quantifyLeakSizeEnabled;

        public LeakWatcherThreadFactory(boolean quantifyLeakSizeEnabled2) {
            this.quantifyLeakSizeEnabled = quantifyLeakSizeEnabled2;
        }

        public LeakWatcherThread newInstance(LeakListener leakListener) {
            return new LeakWatcherThread(new ReferenceQueue(), new GarbageReferenceFactoryImpl(), leakListener, this.quantifyLeakSizeEnabled);
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting(otherwise = 5)
        public LeakWatcherThread newTestInstance(ReferenceQueue<Object> referenceQueue, GarbageReferenceFactory referenceFactory, LeakListener leakListener) {
            return new LeakWatcherThread(referenceQueue, referenceFactory, leakListener, this.quantifyLeakSizeEnabled);
        }
    }

    private static final class GarbageReferenceFactoryImpl implements GarbageReferenceFactory {
        private GarbageReferenceFactoryImpl() {
        }

        public GarbageReference newReference(Object o, String name, ReferenceQueue<Object> refQueue) {
            return new GarbageReference(o, name, refQueue);
        }
    }
}
