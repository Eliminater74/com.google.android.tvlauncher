package com.google.android.libraries.performance.primes.metriccapture;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.performance.primes.trace.PrimesTrace;
import com.google.android.libraries.stitch.util.ThreadUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import logs.proto.wireless.performance.mobile.SystemHealthProto;

public final class DirStatsCapture {
    private static final int MAX_TOTAL_DIR_STATS = 512;
    private static final String TAG = "DirStatsCapture";

    /* access modifiers changed from: private */
    public static long subtreeSize(File[] dirContent) {
        long size;
        Object e;
        try {
            int length = dirContent.length;
            size = 0;
            int i = 0;
            while (i < length) {
                try {
                    File child = dirContent[i];
                    if (!isSymlink(child)) {
                        if (child.isFile()) {
                            size += child.length();
                        } else if (child.isDirectory()) {
                            size += subtreeSize(child.listFiles());
                        } else {
                            PrimesLog.m54w(TAG, "not a link / dir / regular file: %s", child);
                        }
                    }
                    i++;
                } catch (IOException | SecurityException e2) {
                    e = e2;
                    PrimesLog.m53w(TAG, "failure computing subtree size", e, new Object[0]);
                    return size;
                }
            }
        } catch (IOException | SecurityException e3) {
            size = 0;
            e = e3;
            PrimesLog.m53w(TAG, "failure computing subtree size", e, new Object[0]);
            return size;
        }
        return size;
    }

    @VisibleForTesting
    static boolean matchesFile(List<Pattern> listFilesPatterns, String relativeFilePath) {
        for (Pattern pattern : listFilesPatterns) {
            if (pattern.matcher(relativeFilePath).matches()) {
                return true;
            }
        }
        return false;
    }

    @VisibleForTesting
    static boolean isSymlink(File file) throws IOException {
        if (Build.VERSION.SDK_INT >= 26) {
            return Files.isSymbolicLink(file.toPath());
        }
        try {
            File withCanonicalParent = new File(file.getParentFile().getCanonicalFile(), file.getName());
            return true ^ withCanonicalParent.getCanonicalFile().equals(withCanonicalParent.getAbsoluteFile());
        } catch (IOException e) {
            PrimesLog.m54w(TAG, "Could not check symlink for file: %s, assuming symlink.", file);
            return true;
        }
    }

    @VisibleForTesting
    static long collectDirStats(File baseDir, List<SystemHealthProto.PackageMetric.DirStats> dirStats, int maxDepth, Pattern... listFilesPatterns) throws IOException {
        return new Traversal(baseDir, dirStats, maxDepth, listFilesPatterns).scan();
    }

    public static List<SystemHealthProto.PackageMetric.DirStats> getDirStats(Context context, int maxDirDepthForDirStats, Pattern... listFilesPatterns) {
        List<SystemHealthProto.PackageMetric.DirStats> dirStats = new ArrayList<>();
        File dataDir = null;
        try {
            PrimesTrace.beginSection("DirStatsCapture-getDirStats");
            ThreadUtil.ensureBackgroundThread();
            try {
                dataDir = new File(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).dataDir);
            } catch (PackageManager.NameNotFoundException e) {
                PrimesLog.m54w(TAG, "Failed to use package manager getting data directory from context instead.", new Object[0]);
                File filesDir = context.getFilesDir();
                if (filesDir != null) {
                    dataDir = filesDir.getParentFile();
                }
            }
            if (dataDir == null) {
                return Collections.emptyList();
            }
            collectDirStats(dataDir, dirStats, maxDirDepthForDirStats, listFilesPatterns);
            PrimesTrace.endSection();
            return dirStats;
        } catch (Exception e2) {
            String valueOf = String.valueOf(e2);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 29);
            sb.append("Failed to retrieve DirStats: ");
            sb.append(valueOf);
            PrimesLog.m54w(TAG, sb.toString(), new Object[0]);
            return Collections.emptyList();
        } finally {
            PrimesTrace.endSection();
        }
    }

    private static final class Traversal {
        /* access modifiers changed from: private */
        public final File baseDir;
        private final List<SystemHealthProto.PackageMetric.DirStats> dirStats;
        private final List<Pattern> listFilesPatterns;
        private final int maxDepth;

        private Traversal(File baseDir2, List<SystemHealthProto.PackageMetric.DirStats> dirStats2, int maxDepth2, Pattern... listFilesPatterns2) {
            List<Pattern> list;
            this.baseDir = baseDir2;
            this.maxDepth = maxDepth2;
            this.dirStats = dirStats2;
            if (listFilesPatterns2.length == 0) {
                list = Collections.emptyList();
            } else {
                list = Arrays.asList(listFilesPatterns2);
            }
            this.listFilesPatterns = list;
        }

        /* access modifiers changed from: private */
        public long scan() throws IOException {
            return scanDir(new Dir());
        }

        private long scanDir(Dir dir) throws IOException {
            long dirSize;
            Object e;
            SystemHealthProto.PackageMetric.DirStats.Builder stats = SystemHealthProto.PackageMetric.DirStats.newBuilder().setDirPath(dir.relativeDir);
            try {
                File[] children = dir.getFile().listFiles();
                if (dir.depth < this.maxDepth) {
                    if (this.dirStats.size() < 512) {
                        int length = children.length;
                        dirSize = 0;
                        int i = 0;
                        while (i < length) {
                            try {
                                File child = children[i];
                                if (!DirStatsCapture.isSymlink(child)) {
                                    if (child.isFile()) {
                                        String relativeFilePath = dir.relativePathToChild(child.getName());
                                        if (DirStatsCapture.matchesFile(this.listFilesPatterns, relativeFilePath) && this.dirStats.size() < 512) {
                                            this.dirStats.add((SystemHealthProto.PackageMetric.DirStats) SystemHealthProto.PackageMetric.DirStats.newBuilder().setDirPath(relativeFilePath).setSizeBytes(child.length()).build());
                                        }
                                        dirSize += child.length();
                                    } else if (child.isDirectory()) {
                                        dirSize += scanDir(new Dir(dir, child.getName()));
                                    }
                                }
                                i++;
                            } catch (IOException | SecurityException e2) {
                                e = e2;
                                PrimesLog.m45d(DirStatsCapture.TAG, "exception while collecting DirStats for dir %s", e, dir.relativeDir);
                                stats.setSizeBytes(dirSize);
                                this.dirStats.add((SystemHealthProto.PackageMetric.DirStats) stats.build());
                                return dirSize;
                            }
                        }
                        stats.setSizeBytes(dirSize);
                        this.dirStats.add((SystemHealthProto.PackageMetric.DirStats) stats.build());
                        return dirSize;
                    }
                }
                dirSize = DirStatsCapture.subtreeSize(children);
            } catch (IOException | SecurityException e3) {
                dirSize = 0;
                e = e3;
                PrimesLog.m45d(DirStatsCapture.TAG, "exception while collecting DirStats for dir %s", e, dir.relativeDir);
                stats.setSizeBytes(dirSize);
                this.dirStats.add((SystemHealthProto.PackageMetric.DirStats) stats.build());
                return dirSize;
            }
            stats.setSizeBytes(dirSize);
            this.dirStats.add((SystemHealthProto.PackageMetric.DirStats) stats.build());
            return dirSize;
        }

        private final class Dir {
            /* access modifiers changed from: private */
            public final int depth;
            /* access modifiers changed from: private */
            public final String relativeDir;

            private Dir() {
                this.relativeDir = "";
                this.depth = 0;
            }

            private Dir(Dir parent, String dir) {
                String str;
                if (parent.depth == 0) {
                    str = dir;
                } else {
                    String str2 = parent.relativeDir;
                    StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 1 + String.valueOf(dir).length());
                    sb.append(str2);
                    sb.append('/');
                    sb.append(dir);
                    str = sb.toString();
                }
                this.relativeDir = str;
                this.depth = parent.depth + 1;
            }

            /* access modifiers changed from: private */
            public File getFile() {
                return new File(Traversal.this.baseDir, this.relativeDir);
            }

            /* access modifiers changed from: private */
            public String relativePathToChild(String childName) {
                if (this.depth == 0) {
                    return childName;
                }
                String str = this.relativeDir;
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(childName).length());
                sb.append(str);
                sb.append('/');
                sb.append(childName);
                return sb.toString();
            }
        }
    }
}
