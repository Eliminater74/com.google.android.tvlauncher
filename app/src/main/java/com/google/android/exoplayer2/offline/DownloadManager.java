package com.google.android.exoplayer2.offline;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.RequirementsWatcher;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public final class DownloadManager {
    public static final int DEFAULT_MAX_PARALLEL_DOWNLOADS = 3;
    public static final int DEFAULT_MIN_RETRY_COUNT = 5;
    public static final Requirements DEFAULT_REQUIREMENTS = new Requirements(1);
    private static final boolean DEBUG = false;
    private static final int MSG_ADD_DOWNLOAD = 4;
    private static final int MSG_CONTENT_LENGTH_CHANGED = 7;
    private static final int MSG_DOWNLOAD_CHANGED = 2;
    private static final int MSG_DOWNLOAD_REMOVED = 3;
    private static final int MSG_DOWNLOAD_THREAD_STOPPED = 6;
    private static final int MSG_INITIALIZE = 0;
    private static final int MSG_INITIALIZED = 0;
    private static final int MSG_PROCESSED = 1;
    private static final int MSG_RELEASE = 8;
    private static final int MSG_REMOVE_DOWNLOAD = 5;
    private static final int MSG_SET_DOWNLOADS_RESUMED = 1;
    private static final int MSG_SET_NOT_MET_REQUIREMENTS = 2;
    private static final int MSG_SET_STOP_REASON = 3;
    private static final int START_THREAD_SUCCEEDED = 0;
    private static final int START_THREAD_TOO_MANY_DOWNLOADS = 3;
    private static final int START_THREAD_WAIT_DOWNLOAD_CANCELLATION = 2;
    private static final int START_THREAD_WAIT_REMOVAL_TO_FINISH = 1;
    private static final String TAG = "DownloadManager";
    private final Context context;
    private final WritableDownloadIndex downloadIndex;
    private final ArrayList<DownloadInternal> downloadInternals = new ArrayList<>();
    private final HashMap<String, DownloadThread> downloadThreads = new HashMap<>();
    private final DownloaderFactory downloaderFactory;
    private final ArrayList<Download> downloads = new ArrayList<>();
    private final Handler internalHandler;
    private final HandlerThread internalThread = new HandlerThread("DownloadManager file i/o");
    private final CopyOnWriteArraySet<Listener> listeners = new CopyOnWriteArraySet<>();
    private final Handler mainHandler = new Handler(Util.getLooper(), new DownloadManager$$Lambda$1(this));
    private final Object releaseLock = new Object();
    private final RequirementsWatcher.Listener requirementsListener = new DownloadManager$$Lambda$0(this);
    private int activeDownloadCount;
    private boolean downloadsResumed;
    private boolean initialized;
    private volatile int maxParallelDownloads = 3;
    private volatile int minRetryCount = 5;
    private int notMetRequirements;
    private int parallelDownloads;
    private int pendingMessages;
    private boolean released;
    private RequirementsWatcher requirementsWatcher;

    public DownloadManager(Context context2, WritableDownloadIndex downloadIndex2, DownloaderFactory downloaderFactory2) {
        this.context = context2.getApplicationContext();
        this.downloadIndex = downloadIndex2;
        this.downloaderFactory = downloaderFactory2;
        this.internalThread.start();
        this.internalHandler = new Handler(this.internalThread.getLooper(), new DownloadManager$$Lambda$2(this));
        this.requirementsWatcher = new RequirementsWatcher(context2, this.requirementsListener, DEFAULT_REQUIREMENTS);
        int notMetRequirements2 = this.requirementsWatcher.start();
        this.pendingMessages = 1;
        this.internalHandler.obtainMessage(0, notMetRequirements2, 0).sendToTarget();
    }

    static Download mergeRequest(Download download, DownloadRequest request, int stopReason) {
        int state;
        Download download2 = download;
        int state2 = download2.state;
        if (state2 == 5 || state2 == 7) {
            state = 7;
        } else if (stopReason != 0) {
            state = 1;
        } else {
            state = 0;
        }
        long nowMs = System.currentTimeMillis();
        return new Download(download2.request.copyWithMergedRequest(request), state, download.isTerminalState() ? nowMs : download2.startTimeMs, nowMs, -1, stopReason, 0);
    }

    private static Download copyWithState(Download download, int state) {
        return new Download(download.request, state, download.startTimeMs, System.currentTimeMillis(), download.contentLength, download.stopReason, 0, download.progress);
    }

    private static void logd(String message) {
    }

    private static void logd(String message, DownloadInternal downloadInternal) {
        logd(message, downloadInternal.download.request);
    }

    /* access modifiers changed from: private */
    public static void logd(String message, DownloadRequest request) {
    }

    private static void logdFlags(String message, int flags) {
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public boolean isIdle() {
        return this.activeDownloadCount == 0 && this.pendingMessages == 0;
    }

    public boolean isWaitingForRequirements() {
        return !this.downloads.isEmpty();
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public Requirements getRequirements() {
        return this.requirementsWatcher.getRequirements();
    }

    public void setRequirements(Requirements requirements) {
        if (!requirements.equals(this.requirementsWatcher.getRequirements())) {
            this.requirementsWatcher.stop();
            this.requirementsWatcher = new RequirementsWatcher(this.context, this.requirementsListener, requirements);
            bridge$lambda$0$DownloadManager(this.requirementsWatcher, this.requirementsWatcher.start());
        }
    }

    public void setMaxParallelDownloads(int maxParallelDownloads2) {
        this.maxParallelDownloads = maxParallelDownloads2;
    }

    public void setMinRetryCount(int minRetryCount2) {
        this.minRetryCount = minRetryCount2;
    }

    public DownloadIndex getDownloadIndex() {
        return this.downloadIndex;
    }

    public List<Download> getCurrentDownloads() {
        return Collections.unmodifiableList(new ArrayList(this.downloads));
    }

    public void resumeDownloads() {
        this.pendingMessages++;
        this.internalHandler.obtainMessage(1, 1, 0).sendToTarget();
    }

    public void pauseDownloads() {
        this.pendingMessages++;
        this.internalHandler.obtainMessage(1, 0, 0).sendToTarget();
    }

    public void setStopReason(@Nullable String id, int stopReason) {
        this.pendingMessages++;
        this.internalHandler.obtainMessage(3, stopReason, 0, id).sendToTarget();
    }

    public void addDownload(DownloadRequest request) {
        addDownload(request, 0);
    }

    public void addDownload(DownloadRequest request, int stopReason) {
        this.pendingMessages++;
        this.internalHandler.obtainMessage(4, stopReason, 0, request).sendToTarget();
    }

    public void removeDownload(String id) {
        this.pendingMessages++;
        this.internalHandler.obtainMessage(5, id).sendToTarget();
    }

    public void release() {
        synchronized (this.releaseLock) {
            if (!this.released) {
                this.internalHandler.sendEmptyMessage(8);
                boolean wasInterrupted = false;
                while (!this.released) {
                    try {
                        this.releaseLock.wait();
                    } catch (InterruptedException e) {
                        wasInterrupted = true;
                    }
                }
                if (wasInterrupted) {
                    Thread.currentThread().interrupt();
                }
                this.mainHandler.removeCallbacksAndMessages(null);
                this.pendingMessages = 0;
                this.activeDownloadCount = 0;
                this.initialized = false;
                this.downloads.clear();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: onRequirementsStateChanged */
    public void bridge$lambda$0$DownloadManager(RequirementsWatcher requirementsWatcher2, int notMetRequirements2) {
        Requirements requirements = requirementsWatcher2.getRequirements();
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onRequirementsStateChanged(this, requirements, notMetRequirements2);
        }
        this.pendingMessages++;
        this.internalHandler.obtainMessage(2, notMetRequirements2, 0).sendToTarget();
    }

    /* JADX INFO: Multiple debug info for r0v3 int: [D('state' com.google.android.exoplayer2.offline.Download), D('processedMessageCount' int)] */
    /* access modifiers changed from: private */
    /* renamed from: handleMainMessage */
    public boolean bridge$lambda$1$DownloadManager(Message message) {
        int i = message.what;
        if (i == 0) {
            onInitialized((List) message.obj);
        } else if (i == 1) {
            onMessageProcessed(message.arg1, message.arg2);
        } else if (i == 2) {
            onDownloadChanged((Download) message.obj);
        } else if (i == 3) {
            onDownloadRemoved((Download) message.obj);
        } else {
            throw new IllegalStateException();
        }
        return true;
    }

    private void onInitialized(List<Download> downloads2) {
        this.initialized = true;
        this.downloads.addAll(downloads2);
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onInitialized(this);
        }
    }

    private void onDownloadChanged(Download download) {
        int downloadIndex2 = getDownloadIndex(download.request.f89id);
        if (download.isTerminalState()) {
            if (downloadIndex2 != -1) {
                this.downloads.remove(downloadIndex2);
            }
        } else if (downloadIndex2 != -1) {
            this.downloads.set(downloadIndex2, download);
        } else {
            this.downloads.add(download);
        }
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDownloadChanged(this, download);
        }
    }

    private void onDownloadRemoved(Download download) {
        this.downloads.remove(getDownloadIndex(download.request.f89id));
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDownloadRemoved(this, download);
        }
    }

    private void onMessageProcessed(int processedMessageCount, int activeDownloadCount2) {
        this.pendingMessages -= processedMessageCount;
        this.activeDownloadCount = activeDownloadCount2;
        if (isIdle()) {
            Iterator<Listener> it = this.listeners.iterator();
            while (it.hasNext()) {
                it.next().onIdle(this);
            }
        }
    }

    private int getDownloadIndex(String id) {
        for (int i = 0; i < this.downloads.size(); i++) {
            if (this.downloads.get(i).request.f89id.equals(id)) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Multiple debug info for r1v3 int: [D('notMetRequirements' int), D('downloadsResumed' boolean)] */
    /* JADX INFO: Multiple debug info for r1v8 int: [D('notMetRequirements' int), D('id' java.lang.String)] */
    /* access modifiers changed from: private */
    /* renamed from: handleInternalMessage */
    public boolean bridge$lambda$2$DownloadManager(Message message) {
        boolean processedExternalMessage = true;
        int i = 0;
        switch (message.what) {
            case 0:
                initializeInternal(message.arg1);
                break;
            case 1:
                setDownloadsResumed(message.arg1 != 0);
                break;
            case 2:
                setNotMetRequirementsInternal(message.arg1);
                break;
            case 3:
                setStopReasonInternal((String) message.obj, message.arg1);
                break;
            case 4:
                addDownloadInternal((DownloadRequest) message.obj, message.arg1);
                break;
            case 5:
                removeDownloadInternal((String) message.obj);
                break;
            case 6:
                onDownloadThreadStoppedInternal((DownloadThread) message.obj);
                processedExternalMessage = false;
                break;
            case 7:
                onDownloadThreadContentLengthChangedInternal((DownloadThread) message.obj);
                processedExternalMessage = false;
                break;
            case 8:
                releaseInternal();
                return true;
            default:
                throw new IllegalStateException();
        }
        Handler handler = this.mainHandler;
        if (processedExternalMessage) {
            i = 1;
        }
        handler.obtainMessage(1, i, this.downloadThreads.size()).sendToTarget();
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003a, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003b, code lost:
        if (r2 != null) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0045, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initializeInternal(int r7) {
        /*
            r6 = this;
            r6.notMetRequirements = r7
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            com.google.android.exoplayer2.offline.WritableDownloadIndex r2 = r6.downloadIndex     // Catch:{ all -> 0x0046 }
            r3 = 5
            int[] r4 = new int[r3]     // Catch:{ all -> 0x0046 }
            r4[r1] = r1     // Catch:{ all -> 0x0046 }
            r5 = 1
            r4[r5] = r5     // Catch:{ all -> 0x0046 }
            r5 = 2
            r4[r5] = r5     // Catch:{ all -> 0x0046 }
            r5 = 3
            r4[r5] = r3     // Catch:{ all -> 0x0046 }
            r3 = 4
            r5 = 7
            r4[r3] = r5     // Catch:{ all -> 0x0046 }
            com.google.android.exoplayer2.offline.DownloadCursor r2 = r2.getDownloads(r4)     // Catch:{ all -> 0x0046 }
        L_0x0021:
            boolean r3 = r2.moveToNext()     // Catch:{ all -> 0x0038 }
            if (r3 == 0) goto L_0x002f
            com.google.android.exoplayer2.offline.Download r3 = r2.getDownload()     // Catch:{ all -> 0x0038 }
            r0.add(r3)     // Catch:{ all -> 0x0038 }
            goto L_0x0021
        L_0x002f:
            java.lang.String r3 = "Downloads are loaded."
            logd(r3)     // Catch:{ all -> 0x0038 }
            r2.close()     // Catch:{ all -> 0x0046 }
            goto L_0x0051
        L_0x0038:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x003a }
        L_0x003a:
            r4 = move-exception
            if (r2 == 0) goto L_0x0045
            r2.close()     // Catch:{ all -> 0x0041 }
            goto L_0x0045
        L_0x0041:
            r5 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r3, r5)     // Catch:{ all -> 0x0046 }
        L_0x0045:
            throw r4     // Catch:{ all -> 0x0046 }
        L_0x0046:
            r2 = move-exception
            java.lang.String r3 = "DownloadManager"
            java.lang.String r4 = "Download state loading failed."
            com.google.android.exoplayer2.util.Log.m27e(r3, r4, r2)
            r0.clear()
        L_0x0051:
            java.util.Iterator r2 = r0.iterator()
        L_0x0055:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0065
            java.lang.Object r3 = r2.next()
            com.google.android.exoplayer2.offline.Download r3 = (com.google.android.exoplayer2.offline.Download) r3
            r6.addDownloadForState(r3)
            goto L_0x0055
        L_0x0065:
            java.lang.String r2 = "Downloads are created."
            logd(r2)
            android.os.Handler r2 = r6.mainHandler
            android.os.Message r1 = r2.obtainMessage(r1, r0)
            r1.sendToTarget()
            r1 = 0
        L_0x0074:
            java.util.ArrayList<com.google.android.exoplayer2.offline.DownloadManager$DownloadInternal> r2 = r6.downloadInternals
            int r2 = r2.size()
            if (r1 >= r2) goto L_0x008a
            java.util.ArrayList<com.google.android.exoplayer2.offline.DownloadManager$DownloadInternal> r2 = r6.downloadInternals
            java.lang.Object r2 = r2.get(r1)
            com.google.android.exoplayer2.offline.DownloadManager$DownloadInternal r2 = (com.google.android.exoplayer2.offline.DownloadManager.DownloadInternal) r2
            r2.start()
            int r1 = r1 + 1
            goto L_0x0074
        L_0x008a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.DownloadManager.initializeInternal(int):void");
    }

    private void setDownloadsResumed(boolean downloadsResumed2) {
        if (this.downloadsResumed != downloadsResumed2) {
            this.downloadsResumed = downloadsResumed2;
            for (int i = 0; i < this.downloadInternals.size(); i++) {
                this.downloadInternals.get(i).updateStopState();
            }
        }
    }

    private void setNotMetRequirementsInternal(int notMetRequirements2) {
        if (this.notMetRequirements != notMetRequirements2) {
            this.notMetRequirements = notMetRequirements2;
            logdFlags("Not met requirements are changed", notMetRequirements2);
            for (int i = 0; i < this.downloadInternals.size(); i++) {
                this.downloadInternals.get(i).updateStopState();
            }
        }
    }

    private void setStopReasonInternal(@Nullable String id, int stopReason) {
        if (id != null) {
            DownloadInternal downloadInternal = getDownload(id);
            if (downloadInternal != null) {
                StringBuilder sb = new StringBuilder(44);
                sb.append("download stop reason is set to : ");
                sb.append(stopReason);
                logd(sb.toString(), downloadInternal);
                downloadInternal.setStopReason(stopReason);
                return;
            }
        } else {
            for (int i = 0; i < this.downloadInternals.size(); i++) {
                this.downloadInternals.get(i).setStopReason(stopReason);
            }
        }
        if (id != null) {
            try {
                this.downloadIndex.setStopReason(id, stopReason);
            } catch (IOException e) {
                Log.m27e(TAG, "setStopReason failed", e);
            }
        } else {
            this.downloadIndex.setStopReason(stopReason);
        }
    }

    private void addDownloadInternal(DownloadRequest request, int stopReason) {
        Download download;
        DownloadRequest downloadRequest = request;
        int i = stopReason;
        DownloadInternal downloadInternal = getDownload(downloadRequest.f89id);
        if (downloadInternal != null) {
            downloadInternal.addRequest(downloadRequest, i);
            logd("Request is added to existing download", downloadInternal);
            return;
        }
        Download download2 = loadDownload(downloadRequest.f89id);
        if (download2 == null) {
            long nowMs = System.currentTimeMillis();
            download = new Download(request, i != 0 ? 1 : 0, nowMs, nowMs, -1, stopReason, 0);
            String valueOf = String.valueOf(downloadRequest.f89id);
            logd(valueOf.length() != 0 ? "Download state is created for ".concat(valueOf) : new String("Download state is created for "));
        } else {
            download = mergeRequest(download2, downloadRequest, i);
            String valueOf2 = String.valueOf(downloadRequest.f89id);
            logd(valueOf2.length() != 0 ? "Download state is loaded for ".concat(valueOf2) : new String("Download state is loaded for "));
        }
        addDownloadForState(download);
    }

    private void removeDownloadInternal(String id) {
        DownloadInternal downloadInternal = getDownload(id);
        if (downloadInternal != null) {
            downloadInternal.remove();
            return;
        }
        Download download = loadDownload(id);
        if (download != null) {
            addDownloadForState(copyWithState(download, 5));
            return;
        }
        String valueOf = String.valueOf(id);
        logd(valueOf.length() != 0 ? "Can't remove download. No download with id: ".concat(valueOf) : new String("Can't remove download. No download with id: "));
    }

    private void onDownloadThreadStoppedInternal(DownloadThread downloadThread) {
        logd("Download is stopped", downloadThread.request);
        String downloadId = downloadThread.request.f89id;
        this.downloadThreads.remove(downloadId);
        boolean tryToStartDownloads = false;
        if (!downloadThread.isRemove) {
            tryToStartDownloads = this.parallelDownloads == this.maxParallelDownloads;
            this.parallelDownloads--;
        }
        getDownload(downloadId).onDownloadThreadStopped(downloadThread.isCanceled, downloadThread.finalError);
        if (tryToStartDownloads) {
            int i = 0;
            while (this.parallelDownloads < this.maxParallelDownloads && i < this.downloadInternals.size()) {
                this.downloadInternals.get(i).start();
                i++;
            }
        }
    }

    private void onDownloadThreadContentLengthChangedInternal(DownloadThread downloadThread) {
        getDownload(downloadThread.request.f89id).setContentLength(downloadThread.contentLength);
    }

    private void releaseInternal() {
        for (DownloadThread downloadThread : this.downloadThreads.values()) {
            downloadThread.cancel(true);
        }
        this.downloadThreads.clear();
        this.downloadInternals.clear();
        this.internalThread.quit();
        synchronized (this.releaseLock) {
            this.released = true;
            this.releaseLock.notifyAll();
        }
    }

    /* access modifiers changed from: private */
    public void onDownloadChangedInternal(DownloadInternal downloadInternal, Download download) {
        logd("Download state is changed", downloadInternal);
        try {
            this.downloadIndex.putDownload(download);
        } catch (IOException e) {
            Log.m27e(TAG, "Failed to update index", e);
        }
        if (downloadInternal.state == 3 || downloadInternal.state == 4) {
            this.downloadInternals.remove(downloadInternal);
        }
        this.mainHandler.obtainMessage(2, download).sendToTarget();
    }

    /* access modifiers changed from: private */
    public void onDownloadRemovedInternal(DownloadInternal downloadInternal, Download download) {
        logd("Download is removed", downloadInternal);
        try {
            this.downloadIndex.removeDownload(download.request.f89id);
        } catch (IOException e) {
            Log.m27e(TAG, "Failed to remove from index", e);
        }
        this.downloadInternals.remove(downloadInternal);
        this.mainHandler.obtainMessage(3, download).sendToTarget();
    }

    /* access modifiers changed from: private */
    public int startDownloadThread(DownloadInternal downloadInternal) {
        DownloadRequest request = downloadInternal.download.request;
        String downloadId = request.f89id;
        if (!this.downloadThreads.containsKey(downloadId)) {
            boolean isRemove = downloadInternal.isInRemoveState();
            if (!isRemove) {
                if (this.parallelDownloads == this.maxParallelDownloads) {
                    return 3;
                }
                this.parallelDownloads++;
            }
            DownloadThread downloadThread = new DownloadThread(request, this.downloaderFactory.createDownloader(request), downloadInternal.download.progress, isRemove, this.minRetryCount, this.internalHandler);
            this.downloadThreads.put(downloadId, downloadThread);
            downloadThread.start();
            logd("Download is started", downloadInternal);
            return 0;
        } else if (stopDownloadThreadInternal(downloadId)) {
            return 2;
        } else {
            return 1;
        }
    }

    /* access modifiers changed from: private */
    public boolean stopDownloadThreadInternal(String downloadId) {
        DownloadThread downloadThread = this.downloadThreads.get(downloadId);
        if (downloadThread == null || downloadThread.isRemove) {
            return false;
        }
        downloadThread.cancel(false);
        logd("Download is cancelled", downloadThread.request);
        return true;
    }

    @Nullable
    private DownloadInternal getDownload(String id) {
        for (int i = 0; i < this.downloadInternals.size(); i++) {
            DownloadInternal downloadInternal = this.downloadInternals.get(i);
            if (downloadInternal.download.request.f89id.equals(id)) {
                return downloadInternal;
            }
        }
        return null;
    }

    private Download loadDownload(String id) {
        try {
            return this.downloadIndex.getDownload(id);
        } catch (IOException e) {
            Log.m27e(TAG, "loadDownload failed", e);
            return null;
        }
    }

    private void addDownloadForState(Download download) {
        DownloadInternal downloadInternal = new DownloadInternal(download);
        this.downloadInternals.add(downloadInternal);
        logd("Download is added", downloadInternal);
        downloadInternal.initialize();
    }

    /* access modifiers changed from: private */
    public boolean canStartDownloads() {
        return this.downloadsResumed && this.notMetRequirements == 0;
    }

    public interface Listener {
        void onDownloadChanged(DownloadManager downloadManager, Download download);

        void onDownloadRemoved(DownloadManager downloadManager, Download download);

        void onIdle(DownloadManager downloadManager);

        void onInitialized(DownloadManager downloadManager);

        void onRequirementsStateChanged(DownloadManager downloadManager, Requirements requirements, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface StartThreadResults {
    }

    private static final class DownloadInternal {
        private final DownloadManager downloadManager;
        /* access modifiers changed from: private */
        public Download download;
        /* access modifiers changed from: private */
        public int state;
        private long contentLength;
        private int failureReason;
        private int stopReason;

        private DownloadInternal(DownloadManager downloadManager2, Download download2) {
            this.downloadManager = downloadManager2;
            this.download = download2;
            this.state = download2.state;
            this.contentLength = download2.contentLength;
            this.stopReason = download2.stopReason;
            this.failureReason = download2.failureReason;
        }

        /* access modifiers changed from: private */
        public void initialize() {
            initialize(this.download.state);
        }

        public void addRequest(DownloadRequest newRequest, int stopReason2) {
            this.download = DownloadManager.mergeRequest(this.download, newRequest, stopReason2);
            initialize();
        }

        public void remove() {
            initialize(5);
        }

        public Download getUpdatedDownload() {
            this.download = new Download(this.download.request, this.state, this.download.startTimeMs, System.currentTimeMillis(), this.contentLength, this.stopReason, this.state != 4 ? 0 : this.failureReason, this.download.progress);
            return this.download;
        }

        public boolean isIdle() {
            int i = this.state;
            return (i == 2 || i == 5 || i == 7) ? false : true;
        }

        public String toString() {
            String str = this.download.request.f89id;
            String stateString = Download.getStateString(this.state);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(stateString).length());
            sb.append(str);
            sb.append(' ');
            sb.append(stateString);
            return sb.toString();
        }

        public void start() {
            int i = this.state;
            if (i == 0 || i == 2) {
                startOrQueue();
            } else if (isInRemoveState()) {
                int unused = this.downloadManager.startDownloadThread(this);
            }
        }

        public void setStopReason(int stopReason2) {
            this.stopReason = stopReason2;
            updateStopState();
        }

        public boolean isInRemoveState() {
            int i = this.state;
            return i == 5 || i == 7;
        }

        public void setContentLength(long contentLength2) {
            if (this.contentLength != contentLength2) {
                this.contentLength = contentLength2;
                this.downloadManager.onDownloadChangedInternal(this, getUpdatedDownload());
            }
        }

        /* access modifiers changed from: private */
        public void updateStopState() {
            Download oldDownload = this.download;
            if (!canStart()) {
                int i = this.state;
                if (i == 2 || i == 0) {
                    boolean unused = this.downloadManager.stopDownloadThreadInternal(this.download.request.f89id);
                    setState(1);
                }
            } else if (this.state == 1) {
                startOrQueue();
            }
            if (oldDownload == this.download) {
                this.downloadManager.onDownloadChangedInternal(this, getUpdatedDownload());
            }
        }

        private void initialize(int initialState) {
            this.state = initialState;
            if (isInRemoveState()) {
                int unused = this.downloadManager.startDownloadThread(this);
            } else if (canStart()) {
                startOrQueue();
            } else {
                setState(1);
            }
            if (this.state == initialState) {
                this.downloadManager.onDownloadChangedInternal(this, getUpdatedDownload());
            }
        }

        private boolean canStart() {
            return this.downloadManager.canStartDownloads() && this.stopReason == 0;
        }

        private void startOrQueue() {
            boolean z = true;
            Assertions.checkState(!isInRemoveState());
            int result = this.downloadManager.startDownloadThread(this);
            if (result == 1) {
                z = false;
            }
            Assertions.checkState(z);
            if (result == 0 || result == 2) {
                setState(2);
            } else {
                setState(0);
            }
        }

        private void setState(int newState) {
            if (this.state != newState) {
                this.state = newState;
                this.downloadManager.onDownloadChangedInternal(this, getUpdatedDownload());
            }
        }

        /* access modifiers changed from: private */
        public void onDownloadThreadStopped(boolean isCanceled, @Nullable Throwable error) {
            if (!isIdle()) {
                if (isCanceled) {
                    int unused = this.downloadManager.startDownloadThread(this);
                    return;
                }
                int i = this.state;
                if (i == 5) {
                    this.downloadManager.onDownloadRemovedInternal(this, getUpdatedDownload());
                } else if (i == 7) {
                    initialize(0);
                } else if (error != null) {
                    String valueOf = String.valueOf(this.download.request.f89id);
                    Log.m27e(DownloadManager.TAG, valueOf.length() != 0 ? "Download failed: ".concat(valueOf) : new String("Download failed: "), error);
                    this.failureReason = 1;
                    setState(4);
                } else {
                    setState(3);
                }
            }
        }
    }

    private static class DownloadThread extends Thread implements Downloader.ProgressListener {
        /* access modifiers changed from: private */
        public final boolean isRemove;
        /* access modifiers changed from: private */
        public final DownloadRequest request;
        private final DownloadProgress downloadProgress;
        private final Downloader downloader;
        private final int minRetryCount;
        /* access modifiers changed from: private */
        public long contentLength;
        /* access modifiers changed from: private */
        public Throwable finalError;
        /* access modifiers changed from: private */
        public volatile boolean isCanceled;
        private volatile Handler updateHandler;

        private DownloadThread(DownloadRequest request2, Downloader downloader2, DownloadProgress downloadProgress2, boolean isRemove2, int minRetryCount2, Handler updateHandler2) {
            this.request = request2;
            this.downloader = downloader2;
            this.downloadProgress = downloadProgress2;
            this.isRemove = isRemove2;
            this.minRetryCount = minRetryCount2;
            this.updateHandler = updateHandler2;
            this.contentLength = -1;
        }

        private static int getRetryDelayMillis(int errorCount) {
            return Math.min((errorCount - 1) * 1000, 5000);
        }

        public void cancel(boolean released) {
            if (released) {
                this.updateHandler = null;
            }
            this.isCanceled = true;
            this.downloader.cancel();
            interrupt();
        }

        /* JADX INFO: Multiple debug info for r0v1 android.os.Handler: [D('e' java.lang.Throwable), D('updateHandler' android.os.Handler)] */
        public void run() {
            int errorCount;
            long errorPosition;
            DownloadManager.logd("Download started", this.request);
            try {
                if (this.isRemove) {
                    this.downloader.remove();
                } else {
                    errorCount = 0;
                    errorPosition = -1;
                    while (!this.isCanceled) {
                        this.downloader.download(this);
                    }
                }
            } catch (IOException e) {
                if (!this.isCanceled) {
                    long bytesDownloaded = this.downloadProgress.bytesDownloaded;
                    if (bytesDownloaded != errorPosition) {
                        StringBuilder sb = new StringBuilder(57);
                        sb.append("Reset error count. bytesDownloaded = ");
                        sb.append(bytesDownloaded);
                        DownloadManager.logd(sb.toString(), this.request);
                        errorPosition = bytesDownloaded;
                        errorCount = 0;
                    }
                    errorCount++;
                    if (errorCount <= this.minRetryCount) {
                        StringBuilder sb2 = new StringBuilder(33);
                        sb2.append("Download error. Retry ");
                        sb2.append(errorCount);
                        DownloadManager.logd(sb2.toString(), this.request);
                        Thread.sleep((long) getRetryDelayMillis(errorCount));
                    } else {
                        throw e;
                    }
                }
            } catch (Throwable e2) {
                this.finalError = e2;
            }
            Handler updateHandler2 = this.updateHandler;
            if (updateHandler2 != null) {
                updateHandler2.obtainMessage(6, this).sendToTarget();
            }
        }

        public void onProgress(long contentLength2, long bytesDownloaded, float percentDownloaded) {
            DownloadProgress downloadProgress2 = this.downloadProgress;
            downloadProgress2.bytesDownloaded = bytesDownloaded;
            downloadProgress2.percentDownloaded = percentDownloaded;
            if (contentLength2 != this.contentLength) {
                this.contentLength = contentLength2;
                Handler updateHandler2 = this.updateHandler;
                if (updateHandler2 != null) {
                    updateHandler2.obtainMessage(7, this).sendToTarget();
                }
            }
        }
    }
}
