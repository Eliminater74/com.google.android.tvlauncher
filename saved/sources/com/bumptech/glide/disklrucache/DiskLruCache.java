package com.bumptech.glide.disklrucache;

import android.os.Build;
import android.os.StrictMode;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class DiskLruCache implements Closeable {
    static final long ANY_SEQUENCE_NUMBER = -1;
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    static final String JOURNAL_FILE_TEMP = "journal.tmp";
    static final String MAGIC = "libcore.io.DiskLruCache";
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";
    static final String VERSION_1 = "1";
    private final int appVersion;
    private final Callable<Void> cleanupCallable = new Callable<Void>() {
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0027, code lost:
            return null;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void call() throws java.lang.Exception {
            /*
                r4 = this;
                com.bumptech.glide.disklrucache.DiskLruCache r0 = com.bumptech.glide.disklrucache.DiskLruCache.this
                monitor-enter(r0)
                com.bumptech.glide.disklrucache.DiskLruCache r1 = com.bumptech.glide.disklrucache.DiskLruCache.this     // Catch:{ all -> 0x0028 }
                java.io.Writer r1 = r1.journalWriter     // Catch:{ all -> 0x0028 }
                r2 = 0
                if (r1 != 0) goto L_0x000e
                monitor-exit(r0)     // Catch:{ all -> 0x0028 }
                return r2
            L_0x000e:
                com.bumptech.glide.disklrucache.DiskLruCache r1 = com.bumptech.glide.disklrucache.DiskLruCache.this     // Catch:{ all -> 0x0028 }
                r1.trimToSize()     // Catch:{ all -> 0x0028 }
                com.bumptech.glide.disklrucache.DiskLruCache r1 = com.bumptech.glide.disklrucache.DiskLruCache.this     // Catch:{ all -> 0x0028 }
                boolean r1 = r1.journalRebuildRequired()     // Catch:{ all -> 0x0028 }
                if (r1 == 0) goto L_0x0026
                com.bumptech.glide.disklrucache.DiskLruCache r1 = com.bumptech.glide.disklrucache.DiskLruCache.this     // Catch:{ all -> 0x0028 }
                r1.rebuildJournal()     // Catch:{ all -> 0x0028 }
                com.bumptech.glide.disklrucache.DiskLruCache r1 = com.bumptech.glide.disklrucache.DiskLruCache.this     // Catch:{ all -> 0x0028 }
                r3 = 0
                int unused = r1.redundantOpCount = r3     // Catch:{ all -> 0x0028 }
            L_0x0026:
                monitor-exit(r0)     // Catch:{ all -> 0x0028 }
                return r2
            L_0x0028:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0028 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.disklrucache.DiskLruCache.C07821.call():java.lang.Void");
        }
    };
    /* access modifiers changed from: private */
    public final File directory;
    final ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), new DiskLruCacheThreadFactory());
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    /* access modifiers changed from: private */
    public Writer journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap<>(0, 0.75f, true);
    private long maxSize;
    private long nextSequenceNumber = 0;
    /* access modifiers changed from: private */
    public int redundantOpCount;
    private long size = 0;
    /* access modifiers changed from: private */
    public final int valueCount;

    private DiskLruCache(File directory2, int appVersion2, int valueCount2, long maxSize2) {
        File file = directory2;
        this.directory = file;
        this.appVersion = appVersion2;
        this.journalFile = new File(file, JOURNAL_FILE);
        this.journalFileTmp = new File(file, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(file, JOURNAL_FILE_BACKUP);
        this.valueCount = valueCount2;
        this.maxSize = maxSize2;
    }

    public static DiskLruCache open(File directory2, int appVersion2, int valueCount2, long maxSize2) throws IOException {
        if (maxSize2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (valueCount2 > 0) {
            File backupFile = new File(directory2, JOURNAL_FILE_BACKUP);
            if (backupFile.exists()) {
                File journalFile2 = new File(directory2, JOURNAL_FILE);
                if (journalFile2.exists()) {
                    backupFile.delete();
                } else {
                    renameTo(backupFile, journalFile2, false);
                }
            }
            DiskLruCache diskLruCache = new DiskLruCache(directory2, appVersion2, valueCount2, maxSize2);
            if (diskLruCache.journalFile.exists()) {
                try {
                    diskLruCache.readJournal();
                    diskLruCache.processJournal();
                    return diskLruCache;
                } catch (IOException journalIsCorrupt) {
                    PrintStream printStream = System.out;
                    String valueOf = String.valueOf(directory2);
                    String message = journalIsCorrupt.getMessage();
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 36 + String.valueOf(message).length());
                    sb.append("DiskLruCache ");
                    sb.append(valueOf);
                    sb.append(" is corrupt: ");
                    sb.append(message);
                    sb.append(", removing");
                    printStream.println(sb.toString());
                    diskLruCache.delete();
                }
            }
            directory2.mkdirs();
            DiskLruCache cache = new DiskLruCache(directory2, appVersion2, valueCount2, maxSize2);
            cache.rebuildJournal();
            return cache;
        } else {
            throw new IllegalArgumentException("valueCount <= 0");
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.io.FileOutputStream.<init>(java.io.File, boolean):void throws java.io.FileNotFoundException}
     arg types: [java.io.File, int]
     candidates:
      ClspMth{java.io.FileOutputStream.<init>(java.lang.String, boolean):void throws java.io.FileNotFoundException}
      ClspMth{java.io.FileOutputStream.<init>(java.io.File, boolean):void throws java.io.FileNotFoundException} */
    private void readJournal() throws IOException {
        int lineCount;
        StrictLineReader reader = new StrictLineReader(new FileInputStream(this.journalFile), Util.US_ASCII);
        try {
            String magic = reader.readLine();
            String version = reader.readLine();
            String appVersionString = reader.readLine();
            String valueCountString = reader.readLine();
            String blank = reader.readLine();
            if (!MAGIC.equals(magic) || !"1".equals(version) || !Integer.toString(this.appVersion).equals(appVersionString) || !Integer.toString(this.valueCount).equals(valueCountString) || !"".equals(blank)) {
                StringBuilder sb = new StringBuilder(String.valueOf(magic).length() + 35 + String.valueOf(version).length() + String.valueOf(valueCountString).length() + String.valueOf(blank).length());
                sb.append("unexpected journal header: [");
                sb.append(magic);
                sb.append(", ");
                sb.append(version);
                sb.append(", ");
                sb.append(valueCountString);
                sb.append(", ");
                sb.append(blank);
                sb.append("]");
                throw new IOException(sb.toString());
            }
            lineCount = 0;
            while (true) {
                readJournalLine(reader.readLine());
                lineCount++;
            }
        } catch (EOFException e) {
            this.redundantOpCount = lineCount - this.lruEntries.size();
            if (reader.hasUnterminatedLine()) {
                rebuildJournal();
            } else {
                this.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFile, true), Util.US_ASCII));
            }
            Util.closeQuietly(reader);
        } catch (Throwable th) {
            Util.closeQuietly(reader);
            throw th;
        }
    }

    private void readJournalLine(String line) throws IOException {
        String key;
        int firstSpace = line.indexOf(32);
        if (firstSpace == -1) {
            String valueOf = String.valueOf(line);
            throw new IOException(valueOf.length() != 0 ? "unexpected journal line: ".concat(valueOf) : new String("unexpected journal line: "));
        }
        int keyBegin = firstSpace + 1;
        int secondSpace = line.indexOf(32, keyBegin);
        if (secondSpace == -1) {
            key = line.substring(keyBegin);
            if (firstSpace == REMOVE.length() && line.startsWith(REMOVE)) {
                this.lruEntries.remove(key);
                return;
            }
        } else {
            key = line.substring(keyBegin, secondSpace);
        }
        Entry entry = this.lruEntries.get(key);
        if (entry == null) {
            entry = new Entry(key);
            this.lruEntries.put(key, entry);
        }
        if (secondSpace != -1 && firstSpace == CLEAN.length() && line.startsWith(CLEAN)) {
            String[] parts = line.substring(secondSpace + 1).split(" ");
            boolean unused = entry.readable = true;
            Editor unused2 = entry.currentEditor = null;
            entry.setLengths(parts);
        } else if (secondSpace == -1 && firstSpace == DIRTY.length() && line.startsWith(DIRTY)) {
            Editor unused3 = entry.currentEditor = new Editor(entry);
        } else if (secondSpace != -1 || firstSpace != READ.length() || !line.startsWith(READ)) {
            String valueOf2 = String.valueOf(line);
            throw new IOException(valueOf2.length() != 0 ? "unexpected journal line: ".concat(valueOf2) : new String("unexpected journal line: "));
        }
    }

    private void processJournal() throws IOException {
        deleteIfExists(this.journalFileTmp);
        Iterator<Entry> i = this.lruEntries.values().iterator();
        while (i.hasNext()) {
            Entry entry = i.next();
            if (entry.currentEditor == null) {
                for (int t = 0; t < this.valueCount; t++) {
                    this.size += entry.lengths[t];
                }
            } else {
                Editor unused = entry.currentEditor = null;
                for (int t2 = 0; t2 < this.valueCount; t2++) {
                    deleteIfExists(entry.getCleanFile(t2));
                    deleteIfExists(entry.getDirtyFile(t2));
                }
                i.remove();
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.io.FileOutputStream.<init>(java.io.File, boolean):void throws java.io.FileNotFoundException}
     arg types: [java.io.File, int]
     candidates:
      ClspMth{java.io.FileOutputStream.<init>(java.lang.String, boolean):void throws java.io.FileNotFoundException}
      ClspMth{java.io.FileOutputStream.<init>(java.io.File, boolean):void throws java.io.FileNotFoundException} */
    /* access modifiers changed from: private */
    public synchronized void rebuildJournal() throws IOException {
        if (this.journalWriter != null) {
            this.journalWriter.close();
        }
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFileTmp), Util.US_ASCII));
        try {
            writer.write(MAGIC);
            writer.write("\n");
            writer.write("1");
            writer.write("\n");
            writer.write(Integer.toString(this.appVersion));
            writer.write("\n");
            writer.write(Integer.toString(this.valueCount));
            writer.write("\n");
            writer.write("\n");
            for (Entry entry : this.lruEntries.values()) {
                if (entry.currentEditor != null) {
                    String access$1200 = entry.key;
                    StringBuilder sb = new StringBuilder(String.valueOf(access$1200).length() + 7);
                    sb.append("DIRTY ");
                    sb.append(access$1200);
                    sb.append(10);
                    writer.write(sb.toString());
                } else {
                    String access$12002 = entry.key;
                    String lengths = entry.getLengths();
                    StringBuilder sb2 = new StringBuilder(String.valueOf(access$12002).length() + 7 + String.valueOf(lengths).length());
                    sb2.append("CLEAN ");
                    sb2.append(access$12002);
                    sb2.append(lengths);
                    sb2.append(10);
                    writer.write(sb2.toString());
                }
            }
            writer.close();
            if (this.journalFile.exists()) {
                renameTo(this.journalFile, this.journalFileBackup, true);
            }
            renameTo(this.journalFileTmp, this.journalFile, false);
            this.journalFileBackup.delete();
            this.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFile, true), Util.US_ASCII));
        } catch (Throwable th) {
            writer.close();
            throw th;
        }
    }

    private static void deleteIfExists(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void renameTo(File from, File to, boolean deleteDestination) throws IOException {
        if (deleteDestination) {
            deleteIfExists(to);
        }
        if (!from.renameTo(to)) {
            throw new IOException();
        }
    }

    public synchronized Value get(String key) throws IOException {
        checkNotClosed();
        Entry entry = this.lruEntries.get(key);
        if (entry == null) {
            return null;
        }
        if (!entry.readable) {
            return null;
        }
        for (File file : entry.cleanFiles) {
            if (!file.exists()) {
                return null;
            }
        }
        this.redundantOpCount++;
        this.journalWriter.append((CharSequence) READ);
        this.journalWriter.append(' ');
        this.journalWriter.append((CharSequence) key);
        this.journalWriter.append(10);
        if (journalRebuildRequired()) {
            this.executorService.submit(this.cleanupCallable);
        }
        return new Value(key, entry.sequenceNumber, entry.cleanFiles, entry.lengths);
    }

    public Editor edit(String key) throws IOException {
        return edit(key, -1);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001e, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.bumptech.glide.disklrucache.DiskLruCache.Editor edit(java.lang.String r6, long r7) throws java.io.IOException {
        /*
            r5 = this;
            monitor-enter(r5)
            r5.checkNotClosed()     // Catch:{ all -> 0x005e }
            java.util.LinkedHashMap<java.lang.String, com.bumptech.glide.disklrucache.DiskLruCache$Entry> r0 = r5.lruEntries     // Catch:{ all -> 0x005e }
            java.lang.Object r0 = r0.get(r6)     // Catch:{ all -> 0x005e }
            com.bumptech.glide.disklrucache.DiskLruCache$Entry r0 = (com.bumptech.glide.disklrucache.DiskLruCache.Entry) r0     // Catch:{ all -> 0x005e }
            r1 = -1
            r3 = 0
            int r4 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r4 == 0) goto L_0x001f
            if (r0 == 0) goto L_0x001d
            long r1 = r0.sequenceNumber     // Catch:{ all -> 0x005e }
            int r4 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r4 == 0) goto L_0x001f
        L_0x001d:
            monitor-exit(r5)
            return r3
        L_0x001f:
            if (r0 != 0) goto L_0x002d
            com.bumptech.glide.disklrucache.DiskLruCache$Entry r1 = new com.bumptech.glide.disklrucache.DiskLruCache$Entry     // Catch:{ all -> 0x005e }
            r1.<init>(r6)     // Catch:{ all -> 0x005e }
            r0 = r1
            java.util.LinkedHashMap<java.lang.String, com.bumptech.glide.disklrucache.DiskLruCache$Entry> r1 = r5.lruEntries     // Catch:{ all -> 0x005e }
            r1.put(r6, r0)     // Catch:{ all -> 0x005e }
            goto L_0x0035
        L_0x002d:
            com.bumptech.glide.disklrucache.DiskLruCache$Editor r1 = r0.currentEditor     // Catch:{ all -> 0x005e }
            if (r1 == 0) goto L_0x0035
            monitor-exit(r5)
            return r3
        L_0x0035:
            com.bumptech.glide.disklrucache.DiskLruCache$Editor r1 = new com.bumptech.glide.disklrucache.DiskLruCache$Editor     // Catch:{ all -> 0x005e }
            r1.<init>(r0)     // Catch:{ all -> 0x005e }
            com.bumptech.glide.disklrucache.DiskLruCache.Editor unused = r0.currentEditor = r1     // Catch:{ all -> 0x005e }
            java.io.Writer r2 = r5.journalWriter     // Catch:{ all -> 0x005e }
            java.lang.String r3 = "DIRTY"
            r2.append(r3)     // Catch:{ all -> 0x005e }
            java.io.Writer r2 = r5.journalWriter     // Catch:{ all -> 0x005e }
            r3 = 32
            r2.append(r3)     // Catch:{ all -> 0x005e }
            java.io.Writer r2 = r5.journalWriter     // Catch:{ all -> 0x005e }
            r2.append(r6)     // Catch:{ all -> 0x005e }
            java.io.Writer r2 = r5.journalWriter     // Catch:{ all -> 0x005e }
            r3 = 10
            r2.append(r3)     // Catch:{ all -> 0x005e }
            java.io.Writer r2 = r5.journalWriter     // Catch:{ all -> 0x005e }
            flushWriter(r2)     // Catch:{ all -> 0x005e }
            monitor-exit(r5)
            return r1
        L_0x005e:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.disklrucache.DiskLruCache.edit(java.lang.String, long):com.bumptech.glide.disklrucache.DiskLruCache$Editor");
    }

    public File getDirectory() {
        return this.directory;
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    public synchronized void setMaxSize(long maxSize2) {
        this.maxSize = maxSize2;
        this.executorService.submit(this.cleanupCallable);
    }

    public synchronized long size() {
        return this.size;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x010b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void completeEdit(com.bumptech.glide.disklrucache.DiskLruCache.Editor r11, boolean r12) throws java.io.IOException {
        /*
            r10 = this;
            monitor-enter(r10)
            com.bumptech.glide.disklrucache.DiskLruCache$Entry r0 = r11.entry     // Catch:{ all -> 0x0112 }
            com.bumptech.glide.disklrucache.DiskLruCache$Editor r1 = r0.currentEditor     // Catch:{ all -> 0x0112 }
            if (r1 != r11) goto L_0x010c
            r1 = 0
            if (r12 == 0) goto L_0x004f
            boolean r2 = r0.readable     // Catch:{ all -> 0x0112 }
            if (r2 != 0) goto L_0x004f
            r2 = r1
        L_0x0015:
            int r3 = r10.valueCount     // Catch:{ all -> 0x0112 }
            if (r2 >= r3) goto L_0x004f
            boolean[] r3 = r11.written     // Catch:{ all -> 0x0112 }
            boolean r3 = r3[r2]     // Catch:{ all -> 0x0112 }
            if (r3 == 0) goto L_0x0033
            java.io.File r3 = r0.getDirtyFile(r2)     // Catch:{ all -> 0x0112 }
            boolean r3 = r3.exists()     // Catch:{ all -> 0x0112 }
            if (r3 != 0) goto L_0x0030
            r11.abort()     // Catch:{ all -> 0x0112 }
            monitor-exit(r10)
            return
        L_0x0030:
            int r2 = r2 + 1
            goto L_0x0015
        L_0x0033:
            r11.abort()     // Catch:{ all -> 0x0112 }
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0112 }
            r3 = 61
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0112 }
            r4.<init>(r3)     // Catch:{ all -> 0x0112 }
            java.lang.String r3 = "Newly created entry didn't create value for index "
            r4.append(r3)     // Catch:{ all -> 0x0112 }
            r4.append(r2)     // Catch:{ all -> 0x0112 }
            java.lang.String r3 = r4.toString()     // Catch:{ all -> 0x0112 }
            r1.<init>(r3)     // Catch:{ all -> 0x0112 }
            throw r1     // Catch:{ all -> 0x0112 }
        L_0x004f:
        L_0x0050:
            int r2 = r10.valueCount     // Catch:{ all -> 0x0112 }
            if (r1 >= r2) goto L_0x0085
            java.io.File r2 = r0.getDirtyFile(r1)     // Catch:{ all -> 0x0112 }
            if (r12 == 0) goto L_0x007f
            boolean r3 = r2.exists()     // Catch:{ all -> 0x0112 }
            if (r3 == 0) goto L_0x0082
            java.io.File r3 = r0.getCleanFile(r1)     // Catch:{ all -> 0x0112 }
            r2.renameTo(r3)     // Catch:{ all -> 0x0112 }
            long[] r4 = r0.lengths     // Catch:{ all -> 0x0112 }
            r5 = r4[r1]     // Catch:{ all -> 0x0112 }
            r4 = r5
            long r6 = r3.length()     // Catch:{ all -> 0x0112 }
            long[] r8 = r0.lengths     // Catch:{ all -> 0x0112 }
            r8[r1] = r6     // Catch:{ all -> 0x0112 }
            long r8 = r10.size     // Catch:{ all -> 0x0112 }
            long r8 = r8 - r4
            long r8 = r8 + r6
            r10.size = r8     // Catch:{ all -> 0x0112 }
            goto L_0x0082
        L_0x007f:
            deleteIfExists(r2)     // Catch:{ all -> 0x0112 }
        L_0x0082:
            int r1 = r1 + 1
            goto L_0x0050
        L_0x0085:
            int r1 = r10.redundantOpCount     // Catch:{ all -> 0x0112 }
            r2 = 1
            int r1 = r1 + r2
            r10.redundantOpCount = r1     // Catch:{ all -> 0x0112 }
            r1 = 0
            com.bumptech.glide.disklrucache.DiskLruCache.Editor unused = r0.currentEditor = r1     // Catch:{ all -> 0x0112 }
            boolean r1 = r0.readable     // Catch:{ all -> 0x0112 }
            r1 = r1 | r12
            r3 = 10
            r4 = 32
            if (r1 == 0) goto L_0x00cd
            boolean unused = r0.readable = r2     // Catch:{ all -> 0x0112 }
            java.io.Writer r1 = r10.journalWriter     // Catch:{ all -> 0x0112 }
            java.lang.String r2 = "CLEAN"
            r1.append(r2)     // Catch:{ all -> 0x0112 }
            java.io.Writer r1 = r10.journalWriter     // Catch:{ all -> 0x0112 }
            r1.append(r4)     // Catch:{ all -> 0x0112 }
            java.io.Writer r1 = r10.journalWriter     // Catch:{ all -> 0x0112 }
            java.lang.String r2 = r0.key     // Catch:{ all -> 0x0112 }
            r1.append(r2)     // Catch:{ all -> 0x0112 }
            java.io.Writer r1 = r10.journalWriter     // Catch:{ all -> 0x0112 }
            java.lang.String r2 = r0.getLengths()     // Catch:{ all -> 0x0112 }
            r1.append(r2)     // Catch:{ all -> 0x0112 }
            java.io.Writer r1 = r10.journalWriter     // Catch:{ all -> 0x0112 }
            r1.append(r3)     // Catch:{ all -> 0x0112 }
            if (r12 == 0) goto L_0x00f0
            long r1 = r10.nextSequenceNumber     // Catch:{ all -> 0x0112 }
            r3 = 1
            long r3 = r3 + r1
            r10.nextSequenceNumber = r3     // Catch:{ all -> 0x0112 }
            long unused = r0.sequenceNumber = r1     // Catch:{ all -> 0x0112 }
            goto L_0x00f0
        L_0x00cd:
            java.util.LinkedHashMap<java.lang.String, com.bumptech.glide.disklrucache.DiskLruCache$Entry> r1 = r10.lruEntries     // Catch:{ all -> 0x0112 }
            java.lang.String r2 = r0.key     // Catch:{ all -> 0x0112 }
            r1.remove(r2)     // Catch:{ all -> 0x0112 }
            java.io.Writer r1 = r10.journalWriter     // Catch:{ all -> 0x0112 }
            java.lang.String r2 = "REMOVE"
            r1.append(r2)     // Catch:{ all -> 0x0112 }
            java.io.Writer r1 = r10.journalWriter     // Catch:{ all -> 0x0112 }
            r1.append(r4)     // Catch:{ all -> 0x0112 }
            java.io.Writer r1 = r10.journalWriter     // Catch:{ all -> 0x0112 }
            java.lang.String r2 = r0.key     // Catch:{ all -> 0x0112 }
            r1.append(r2)     // Catch:{ all -> 0x0112 }
            java.io.Writer r1 = r10.journalWriter     // Catch:{ all -> 0x0112 }
            r1.append(r3)     // Catch:{ all -> 0x0112 }
        L_0x00f0:
            java.io.Writer r1 = r10.journalWriter     // Catch:{ all -> 0x0112 }
            flushWriter(r1)     // Catch:{ all -> 0x0112 }
            long r1 = r10.size     // Catch:{ all -> 0x0112 }
            long r3 = r10.maxSize     // Catch:{ all -> 0x0112 }
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 > 0) goto L_0x0103
            boolean r1 = r10.journalRebuildRequired()     // Catch:{ all -> 0x0112 }
            if (r1 == 0) goto L_0x010a
        L_0x0103:
            java.util.concurrent.ThreadPoolExecutor r1 = r10.executorService     // Catch:{ all -> 0x0112 }
            java.util.concurrent.Callable<java.lang.Void> r2 = r10.cleanupCallable     // Catch:{ all -> 0x0112 }
            r1.submit(r2)     // Catch:{ all -> 0x0112 }
        L_0x010a:
            monitor-exit(r10)
            return
        L_0x010c:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0112 }
            r1.<init>()     // Catch:{ all -> 0x0112 }
            throw r1     // Catch:{ all -> 0x0112 }
        L_0x0112:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.disklrucache.DiskLruCache.completeEdit(com.bumptech.glide.disklrucache.DiskLruCache$Editor, boolean):void");
    }

    /* access modifiers changed from: private */
    public boolean journalRebuildRequired() {
        int i = this.redundantOpCount;
        return i >= 2000 && i >= this.lruEntries.size();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x009b, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x009d, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean remove(java.lang.String r9) throws java.io.IOException {
        /*
            r8 = this;
            monitor-enter(r8)
            r8.checkNotClosed()     // Catch:{ all -> 0x009e }
            java.util.LinkedHashMap<java.lang.String, com.bumptech.glide.disklrucache.DiskLruCache$Entry> r0 = r8.lruEntries     // Catch:{ all -> 0x009e }
            java.lang.Object r0 = r0.get(r9)     // Catch:{ all -> 0x009e }
            com.bumptech.glide.disklrucache.DiskLruCache$Entry r0 = (com.bumptech.glide.disklrucache.DiskLruCache.Entry) r0     // Catch:{ all -> 0x009e }
            r1 = 0
            if (r0 == 0) goto L_0x009c
            com.bumptech.glide.disklrucache.DiskLruCache$Editor r2 = r0.currentEditor     // Catch:{ all -> 0x009e }
            if (r2 == 0) goto L_0x0017
            goto L_0x009c
        L_0x0017:
        L_0x0018:
            int r2 = r8.valueCount     // Catch:{ all -> 0x009e }
            if (r1 >= r2) goto L_0x0068
            java.io.File r2 = r0.getCleanFile(r1)     // Catch:{ all -> 0x009e }
            boolean r3 = r2.exists()     // Catch:{ all -> 0x009e }
            if (r3 == 0) goto L_0x0052
            boolean r3 = r2.delete()     // Catch:{ all -> 0x009e }
            if (r3 == 0) goto L_0x002d
            goto L_0x0052
        L_0x002d:
            java.io.IOException r3 = new java.io.IOException     // Catch:{ all -> 0x009e }
            java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x009e }
            java.lang.String r5 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x009e }
            int r5 = r5.length()     // Catch:{ all -> 0x009e }
            int r5 = r5 + 17
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x009e }
            r6.<init>(r5)     // Catch:{ all -> 0x009e }
            java.lang.String r5 = "failed to delete "
            r6.append(r5)     // Catch:{ all -> 0x009e }
            r6.append(r4)     // Catch:{ all -> 0x009e }
            java.lang.String r4 = r6.toString()     // Catch:{ all -> 0x009e }
            r3.<init>(r4)     // Catch:{ all -> 0x009e }
            throw r3     // Catch:{ all -> 0x009e }
        L_0x0052:
            long r3 = r8.size     // Catch:{ all -> 0x009e }
            long[] r5 = r0.lengths     // Catch:{ all -> 0x009e }
            r6 = r5[r1]     // Catch:{ all -> 0x009e }
            long r3 = r3 - r6
            r8.size = r3     // Catch:{ all -> 0x009e }
            long[] r3 = r0.lengths     // Catch:{ all -> 0x009e }
            r4 = 0
            r3[r1] = r4     // Catch:{ all -> 0x009e }
            int r1 = r1 + 1
            goto L_0x0018
        L_0x0068:
            int r1 = r8.redundantOpCount     // Catch:{ all -> 0x009e }
            r2 = 1
            int r1 = r1 + r2
            r8.redundantOpCount = r1     // Catch:{ all -> 0x009e }
            java.io.Writer r1 = r8.journalWriter     // Catch:{ all -> 0x009e }
            java.lang.String r3 = "REMOVE"
            r1.append(r3)     // Catch:{ all -> 0x009e }
            java.io.Writer r1 = r8.journalWriter     // Catch:{ all -> 0x009e }
            r3 = 32
            r1.append(r3)     // Catch:{ all -> 0x009e }
            java.io.Writer r1 = r8.journalWriter     // Catch:{ all -> 0x009e }
            r1.append(r9)     // Catch:{ all -> 0x009e }
            java.io.Writer r1 = r8.journalWriter     // Catch:{ all -> 0x009e }
            r3 = 10
            r1.append(r3)     // Catch:{ all -> 0x009e }
            java.util.LinkedHashMap<java.lang.String, com.bumptech.glide.disklrucache.DiskLruCache$Entry> r1 = r8.lruEntries     // Catch:{ all -> 0x009e }
            r1.remove(r9)     // Catch:{ all -> 0x009e }
            boolean r1 = r8.journalRebuildRequired()     // Catch:{ all -> 0x009e }
            if (r1 == 0) goto L_0x009a
            java.util.concurrent.ThreadPoolExecutor r1 = r8.executorService     // Catch:{ all -> 0x009e }
            java.util.concurrent.Callable<java.lang.Void> r3 = r8.cleanupCallable     // Catch:{ all -> 0x009e }
            r1.submit(r3)     // Catch:{ all -> 0x009e }
        L_0x009a:
            monitor-exit(r8)
            return r2
        L_0x009c:
            monitor-exit(r8)
            return r1
        L_0x009e:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.disklrucache.DiskLruCache.remove(java.lang.String):boolean");
    }

    public synchronized boolean isClosed() {
        return this.journalWriter == null;
    }

    private void checkNotClosed() {
        if (this.journalWriter == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void flush() throws IOException {
        checkNotClosed();
        trimToSize();
        flushWriter(this.journalWriter);
    }

    public synchronized void close() throws IOException {
        if (this.journalWriter != null) {
            Iterator it = new ArrayList(this.lruEntries.values()).iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                if (entry.currentEditor != null) {
                    entry.currentEditor.abort();
                }
            }
            trimToSize();
            this.journalWriter.close();
            this.journalWriter = null;
        }
    }

    /* access modifiers changed from: private */
    public void trimToSize() throws IOException {
        while (this.size > this.maxSize) {
            remove((String) this.lruEntries.entrySet().iterator().next().getKey());
        }
    }

    public void delete() throws IOException {
        close();
        Util.deleteContents(this.directory);
    }

    /* access modifiers changed from: private */
    public static String inputStreamToString(InputStream in) throws IOException {
        return Util.readFully(new InputStreamReader(in, Util.UTF_8));
    }

    private static void flushWriter(Writer writer) throws IOException {
        if (Build.VERSION.SDK_INT < 26) {
            writer.flush();
            return;
        }
        StrictMode.ThreadPolicy oldPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(oldPolicy).permitUnbufferedIo().build());
        try {
            writer.flush();
        } finally {
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    public final class Value {
        private final File[] files;
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;

        private Value(String key2, long sequenceNumber2, File[] files2, long[] lengths2) {
            this.key = key2;
            this.sequenceNumber = sequenceNumber2;
            this.files = files2;
            this.lengths = lengths2;
        }

        public Editor edit() throws IOException {
            return DiskLruCache.this.edit(this.key, this.sequenceNumber);
        }

        public File getFile(int index) {
            return this.files[index];
        }

        public String getString(int index) throws IOException {
            return DiskLruCache.inputStreamToString(new FileInputStream(this.files[index]));
        }

        public long getLength(int index) {
            return this.lengths[index];
        }
    }

    public final class Editor {
        private boolean committed;
        /* access modifiers changed from: private */
        public final Entry entry;
        /* access modifiers changed from: private */
        public final boolean[] written;

        private Editor(Entry entry2) {
            this.entry = entry2;
            this.written = entry2.readable ? null : new boolean[DiskLruCache.this.valueCount];
        }

        private InputStream newInputStream(int index) throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor != this) {
                    throw new IllegalStateException();
                } else if (!this.entry.readable) {
                    return null;
                } else {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(this.entry.getCleanFile(index));
                        return fileInputStream;
                    } catch (FileNotFoundException e) {
                        return null;
                    }
                }
            }
        }

        public String getString(int index) throws IOException {
            InputStream in = newInputStream(index);
            if (in != null) {
                return DiskLruCache.inputStreamToString(in);
            }
            return null;
        }

        public File getFile(int index) throws IOException {
            File dirtyFile;
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor == this) {
                    if (!this.entry.readable) {
                        this.written[index] = true;
                    }
                    dirtyFile = this.entry.getDirtyFile(index);
                    if (!DiskLruCache.this.directory.exists()) {
                        DiskLruCache.this.directory.mkdirs();
                    }
                } else {
                    throw new IllegalStateException();
                }
            }
            return dirtyFile;
        }

        public void set(int index, String value) throws IOException {
            Writer writer = null;
            try {
                writer = new OutputStreamWriter(new FileOutputStream(getFile(index)), Util.UTF_8);
                writer.write(value);
            } finally {
                Util.closeQuietly(writer);
            }
        }

        public void commit() throws IOException {
            DiskLruCache.this.completeEdit(this, true);
            this.committed = true;
        }

        public void abort() throws IOException {
            DiskLruCache.this.completeEdit(this, false);
        }

        public void abortUnlessCommitted() {
            if (!this.committed) {
                try {
                    abort();
                } catch (IOException e) {
                }
            }
        }
    }

    private final class Entry {
        File[] cleanFiles;
        /* access modifiers changed from: private */
        public Editor currentEditor;
        File[] dirtyFiles;
        /* access modifiers changed from: private */
        public final String key;
        /* access modifiers changed from: private */
        public final long[] lengths;
        /* access modifiers changed from: private */
        public boolean readable;
        /* access modifiers changed from: private */
        public long sequenceNumber;

        private Entry(String key2) {
            this.key = key2;
            this.lengths = new long[DiskLruCache.this.valueCount];
            this.cleanFiles = new File[DiskLruCache.this.valueCount];
            this.dirtyFiles = new File[DiskLruCache.this.valueCount];
            StringBuilder fileBuilder = new StringBuilder(key2).append('.');
            int truncateTo = fileBuilder.length();
            for (int i = 0; i < DiskLruCache.this.valueCount; i++) {
                fileBuilder.append(i);
                this.cleanFiles[i] = new File(DiskLruCache.this.directory, fileBuilder.toString());
                fileBuilder.append(".tmp");
                this.dirtyFiles[i] = new File(DiskLruCache.this.directory, fileBuilder.toString());
                fileBuilder.setLength(truncateTo);
            }
        }

        public String getLengths() throws IOException {
            StringBuilder result = new StringBuilder();
            for (long size : this.lengths) {
                result.append(' ');
                result.append(size);
            }
            return result.toString();
        }

        /* access modifiers changed from: private */
        public void setLengths(String[] strings) throws IOException {
            if (strings.length == DiskLruCache.this.valueCount) {
                int i = 0;
                while (i < strings.length) {
                    try {
                        this.lengths[i] = Long.parseLong(strings[i]);
                        i++;
                    } catch (NumberFormatException e) {
                        throw invalidLengths(strings);
                    }
                }
                return;
            }
            throw invalidLengths(strings);
        }

        private IOException invalidLengths(String[] strings) throws IOException {
            String valueOf = String.valueOf(Arrays.toString(strings));
            throw new IOException(valueOf.length() != 0 ? "unexpected journal line: ".concat(valueOf) : new String("unexpected journal line: "));
        }

        public File getCleanFile(int i) {
            return this.cleanFiles[i];
        }

        public File getDirtyFile(int i) {
            return this.dirtyFiles[i];
        }
    }

    private static final class DiskLruCacheThreadFactory implements ThreadFactory {
        private DiskLruCacheThreadFactory() {
        }

        public synchronized Thread newThread(Runnable runnable) {
            Thread result;
            result = new Thread(runnable, "glide-disk-lru-cache-thread");
            result.setPriority(1);
            return result;
        }
    }
}
