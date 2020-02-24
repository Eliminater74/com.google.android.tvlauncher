package com.google.android.gms.clearcut;

import android.util.Log;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzgsh;
import com.google.android.gms.internal.zzgtg;
import com.google.android.gms.internal.zzgth;
import com.google.android.gms.internal.zzgti;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Counters {
    public static final Alias IDENTITY = new BucketAlias(1);
    private static final Charset zza = Charset.forName("UTF-8");
    /* access modifiers changed from: private */
    public static final long[] zzb = new long[0];
    private static final Comparator zzs = new zzp();
    private final String zzc;
    /* access modifiers changed from: private */
    public final int zzd;
    /* access modifiers changed from: private */
    public final Clock zze;
    /* access modifiers changed from: private */
    public LogEventModifier zzf;
    /* access modifiers changed from: private */
    public boolean zzg;
    /* access modifiers changed from: private */
    public volatile int zzh;
    private ScheduledExecutorService zzi;
    /* access modifiers changed from: private */
    public volatile Future<?> zzj;
    /* access modifiers changed from: private */
    public long zzk;
    private final ClearcutLogger zzl;
    /* access modifiers changed from: private */
    public final ReentrantReadWriteLock zzm;
    /* access modifiers changed from: private */
    public Map<String, AbstractCounter> zzn;
    /* access modifiers changed from: private */
    public byte[] zzo;
    /* access modifiers changed from: private */
    public Integer zzp;
    /* access modifiers changed from: private */
    public TreeMap<byte[], Integer> zzq;
    private LogCallback zzr;

    public interface Alias {
        long alias(long j);
    }

    public interface LogCallback {
        void onLogged(Counters counters);
    }

    public interface LogEventModifier {
        ClearcutLogger.LogEventBuilder modify(ClearcutLogger.LogEventBuilder logEventBuilder);
    }

    public Counters(ClearcutLogger clearcutLogger, String str, int i) {
        this(clearcutLogger, str, i, zzh.zza());
    }

    public class BooleanHistogram extends AbstractCounter {
        private BooleanHistogram(Counters counters, String str) {
            super(str);
        }

        private BooleanHistogram(Counters counters, BooleanHistogram booleanHistogram, boolean z) {
            super(counters, booleanHistogram, z);
        }

        public void increment(boolean z) {
            incrementBase(z ? 1 : 0);
        }

        public long getCount(boolean z) {
            return getCountBase(z ? 1 : 0);
        }

        /* synthetic */ BooleanHistogram(Counters counters, BooleanHistogram booleanHistogram, boolean z, zzp zzp) {
            this(counters, booleanHistogram, z);
        }

        /* synthetic */ BooleanHistogram(Counters counters, String str, zzp zzp) {
            this(counters, str);
        }
    }

    public class Counter extends AbstractCounter {
        private Counter(Counters counters, String str) {
            super(str);
        }

        private Counter(Counters counters, Counter counter, boolean z) {
            super(counters, counter, z);
        }

        public void increment() {
            incrementBy(1);
        }

        public void incrementBy(long j) {
            incrementBase(0, j);
        }

        public long getCount() {
            return getCountBase(0);
        }

        /* synthetic */ Counter(Counters counters, Counter counter, boolean z, zzp zzp) {
            this(counters, counter, z);
        }

        /* synthetic */ Counter(Counters counters, String str, zzp zzp) {
            this(counters, str);
        }
    }

    public class IntegerHistogram extends AbstractCounter {
        private IntegerHistogram(Counters counters, String str) {
            super(str);
        }

        private IntegerHistogram(Counters counters, IntegerHistogram integerHistogram, boolean z) {
            super(counters, integerHistogram, z);
        }

        public void increment(int i) {
            incrementBase((long) i);
        }

        public long getCount(int i) {
            return getCountBase((long) i);
        }

        /* synthetic */ IntegerHistogram(Counters counters, IntegerHistogram integerHistogram, boolean z, zzp zzp) {
            this(counters, integerHistogram, z);
        }

        /* synthetic */ IntegerHistogram(Counters counters, String str, zzp zzp) {
            this(counters, str);
        }
    }

    public class LongHistogram extends zza {
        private LongHistogram(Counters counters, String str, Alias alias) {
            super(counters, str, alias);
        }

        private LongHistogram(Counters counters, LongHistogram longHistogram, boolean z) {
            super(counters, longHistogram, z);
        }

        public void increment(long j) {
            super.increment(j);
        }

        public void incrementBy(long j, long j2) {
            super.incrementBy(j, j2);
        }

        public void increment(long[] jArr) {
            super.incrementBy(jArr, Counters.zzb);
        }

        public void incrementBy(long[] jArr, long[] jArr2) {
            super.incrementBy(jArr, jArr2);
        }

        public long getCount(long j) {
            return super.getCount(j);
        }

        /* synthetic */ LongHistogram(Counters counters, LongHistogram longHistogram, boolean z, zzp zzp) {
            this(counters, longHistogram, z);
        }

        /* synthetic */ LongHistogram(Counters counters, String str, Alias alias, zzp zzp) {
            this(counters, str, alias);
        }
    }

    public final class Timer {
        private long zza = Counters.this.zze.elapsedRealtime();

        public Timer() {
        }

        public final long reset() {
            long j = this.zza;
            this.zza = Counters.this.zze.elapsedRealtime();
            return j;
        }

        public final long getMilliseconds() {
            return Counters.this.zze.elapsedRealtime() - this.zza;
        }

        public final void incrementTo(TimerHistogram timerHistogram) {
            timerHistogram.increment(getMilliseconds());
        }
    }

    public class TimerHistogram extends zza {
        private TimerHistogram(String str, Alias alias) {
            super(Counters.this, str, alias);
        }

        public class BoundTimer {
            private long zza;
            private final TimerHistogram zzb;

            private BoundTimer(TimerHistogram timerHistogram) {
                this.zzb = timerHistogram;
                reset();
            }

            public void reset() {
                this.zza = Counters.this.zze.elapsedRealtime();
            }

            public long getMilliseconds() {
                return Counters.this.zze.elapsedRealtime() - this.zza;
            }

            public void incrementTo() {
                this.zzb.increment(getMilliseconds());
            }

            /* synthetic */ BoundTimer(TimerHistogram timerHistogram, TimerHistogram timerHistogram2, zzp zzp) {
                this(timerHistogram2);
            }
        }

        private TimerHistogram(TimerHistogram timerHistogram, boolean z) {
            super(Counters.this, timerHistogram, z);
        }

        public long getCount(long j) {
            return super.getCount(j);
        }

        public BoundTimer newTimer() {
            return new BoundTimer(this, this, null);
        }

        /* synthetic */ TimerHistogram(Counters counters, TimerHistogram timerHistogram, boolean z, zzp zzp) {
            this(timerHistogram, z);
        }

        /* synthetic */ TimerHistogram(Counters counters, String str, Alias alias, zzp zzp) {
            this(str, alias);
        }
    }

    public class zza extends AbstractCounter {
        final Alias zzb;

        protected zza(Counters counters, String str, Alias alias) {
            super(str);
            this.zzb = alias;
        }

        protected zza(Counters counters, zza zza, boolean z) {
            super(counters, zza, z);
            this.zzb = zza.zzb;
        }

        /* access modifiers changed from: protected */
        public void increment(long j) {
            incrementBase(this.zzb.alias(j), 1);
        }

        /* access modifiers changed from: protected */
        public void incrementBy(long j, long j2) {
            incrementBase(this.zzb.alias(j), j2);
        }

        /* access modifiers changed from: protected */
        public void incrementBy(long[] jArr, long[] jArr2) {
            incrementBase(jArr, jArr2, this.zzb);
        }

        /* access modifiers changed from: protected */
        public long getCount(long j) {
            return getCountBase(this.zzb.alias(j));
        }
    }

    private Counters(Counters counters, boolean z) {
        this(counters.zzl, counters.zzc, counters.zzd, counters.zze);
        ReentrantReadWriteLock reentrantReadWriteLock = counters.zzm;
        Lock writeLock = z ? reentrantReadWriteLock.writeLock() : reentrantReadWriteLock.readLock();
        writeLock.lock();
        try {
            this.zzo = counters.zzo;
            this.zzp = counters.zzp;
            this.zzk = counters.zzk;
            this.zzf = counters.zzf;
            this.zzn = new TreeMap();
            if (z) {
                for (Map.Entry next : counters.zzn.entrySet()) {
                    this.zzn.put((String) next.getKey(), zza((AbstractCounter) next.getValue(), z));
                }
                TreeMap<byte[], Integer> treeMap = this.zzq;
                this.zzq = counters.zzq;
                counters.zzq = treeMap;
                counters.zzp = null;
                counters.zzk = this.zze.elapsedRealtime();
            } else {
                for (Map.Entry next2 : counters.zzn.entrySet()) {
                    this.zzn.put((String) next2.getKey(), zza((AbstractCounter) next2.getValue(), z));
                }
                this.zzq.putAll(counters.zzq);
            }
        } finally {
            writeLock.unlock();
        }
    }

    public static class ClippedBucketAlias extends BucketAlias {
        private final long zza;
        private final long zzb;

        public ClippedBucketAlias(int i, int i2, int i3) {
            super(i);
            this.zza = (long) i2;
            this.zzb = (long) i3;
        }

        public long alias(long j) {
            return super.alias(Math.max(Math.min(j, this.zzb), this.zza));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if ((obj instanceof ClippedBucketAlias) && this.alias == ((ClippedBucketAlias) obj).alias) {
                return true;
            }
            return false;
        }
    }

    public static class BucketAlias implements Alias {
        protected final int alias;

        public BucketAlias(int i) {
            if (i > 0) {
                this.alias = i;
                return;
            }
            StringBuilder sb = new StringBuilder(22);
            sb.append("bad alias: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }

        public long alias(long j) {
            int i = this.alias;
            return ((long) i) * (j / ((long) i));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if ((obj instanceof BucketAlias) && this.alias == ((BucketAlias) obj).alias) {
                return true;
            }
            return false;
        }
    }

    class zzb implements ClearcutLogger.MessageProducer {
        private final byte[] zza;
        private final Integer zzb;
        private final ArrayList<AbstractCounter> zzc;

        zzb(byte[] bArr) {
            this.zza = bArr;
            this.zzb = (Integer) Counters.this.zzq.get(this.zza);
            Integer num = this.zzb;
            ArrayList<AbstractCounter> arrayList = new ArrayList<>(Counters.this.zzn.size());
            for (AbstractCounter abstractCounter : Counters.this.zzn.values()) {
                if (abstractCounter.zza.containsKey(num)) {
                    arrayList.add(abstractCounter);
                }
            }
            this.zzc = arrayList;
        }

        private final zzgti zza() {
            zzgti zzgti = new zzgti();
            zzgti.zza = Counters.this.zzk;
            byte[] bArr = this.zza;
            if (bArr != null) {
                zzgti.zzc = bArr;
            }
            zzgti.zzb = new zzgth[this.zzc.size()];
            ArrayList arrayList = this.zzc;
            int size = arrayList.size();
            int i = 0;
            int i2 = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                AbstractCounter abstractCounter = (AbstractCounter) obj;
                zzgth[] zzgthArr = zzgti.zzb;
                Map map = abstractCounter.zza.get(this.zzb);
                zzgth zzgth = new zzgth();
                zzgth.zza = Counters.umaMetricHash(abstractCounter.zzb);
                zzgth.zzb = new zzgtg[map.size()];
                int i3 = 0;
                for (Map.Entry entry : map.entrySet()) {
                    zzgtg zzgtg = new zzgtg();
                    zzgtg.zza = ((Long) entry.getKey()).longValue();
                    zzgtg.zzb = ((long[]) entry.getValue())[0];
                    zzgth.zzb[i3] = zzgtg;
                    i3++;
                }
                zzgthArr[i2] = zzgth;
                i2++;
            }
            return zzgti;
        }

        public final byte[] toProtoBytes() {
            return zzgsh.toByteArray(zza());
        }

        public final String toString() {
            return zza().toString();
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof zzb)) {
                return false;
            }
            return zza().equals(((zzb) obj).zza());
        }

        public final int hashCode() {
            return 1;
        }
    }

    public abstract class AbstractCounter {
        Map<Integer, Map<Long, long[]>> zza;
        /* access modifiers changed from: private */
        public final String zzb;
        private int zzc;
        private int zzd;
        private final Object zze;

        protected AbstractCounter(Counters counters, AbstractCounter abstractCounter, boolean z) {
            this(abstractCounter.zzb);
            synchronized (abstractCounter.zze) {
                this.zzc = abstractCounter.zzc;
                if (z) {
                    Map<Integer, Map<Long, long[]>> map = this.zza;
                    this.zza = abstractCounter.zza;
                    abstractCounter.zza = map;
                    abstractCounter.zzc = 0;
                    return;
                }
                this.zza = new HashMap(abstractCounter.zza.size());
                for (Map.Entry next : abstractCounter.zza.entrySet()) {
                    HashMap hashMap = new HashMap(((Map) next.getValue()).size());
                    for (Map.Entry entry : ((Map) next.getValue()).entrySet()) {
                        hashMap.put((Long) entry.getKey(), new long[]{((long[]) entry.getValue())[0]});
                    }
                    this.zza.put((Integer) next.getKey(), hashMap);
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("AbstractCounter");
            sb.append("(");
            sb.append(this.zzb);
            sb.append(")[");
            synchronized (this.zze) {
                for (Map.Entry next : this.zza.entrySet()) {
                    sb.append(next.getKey());
                    sb.append(" -> [");
                    for (Map.Entry entry : ((Map) next.getValue()).entrySet()) {
                        sb.append(entry.getKey());
                        sb.append(" = ");
                        sb.append(((long[]) entry.getValue())[0]);
                        sb.append(", ");
                    }
                    sb.append("], ");
                }
            }
            sb.append("]");
            return sb.toString();
        }

        protected AbstractCounter(String str) {
            this.zzd = Counters.this.zzd;
            this.zza = new HashMap();
            this.zze = new Object();
            if (Counters.this.zzn.containsKey(str)) {
                String valueOf = String.valueOf(str);
                throw new IllegalStateException(valueOf.length() != 0 ? "counter/histogram already exists: ".concat(valueOf) : new String("counter/histogram already exists: "));
            }
            Counters.this.zzn.put(str, this);
            this.zzb = str;
        }

        /* access modifiers changed from: protected */
        public final void incrementBase(long j, long j2) {
            boolean z;
            Counters.this.zzm.readLock().lock();
            try {
                boolean z2 = false;
                if (Counters.this.zzp == null) {
                    z = true;
                } else {
                    z2 = zzb(j, j2);
                    z = false;
                }
                if (z) {
                    z2 = zza(j, j2);
                }
                if (z2) {
                    Counters counters = Counters.this;
                    counters.logAllAsync(counters.zzf);
                }
                if (Counters.this.zzh > 0 && Counters.this.zzj == null) {
                    Counters.this.zzc();
                }
            } finally {
                Counters.this.zzm.readLock().unlock();
            }
        }

        /* JADX INFO: finally extract failed */
        private final boolean zza(long j, long j2) {
            ReentrantReadWriteLock.WriteLock writeLock = Counters.this.zzm.writeLock();
            writeLock.lock();
            try {
                Integer unused = Counters.this.zzp = Counters.this.zza(Counters.this.zzo);
                Counters.this.zzm.readLock().lock();
                writeLock.unlock();
                ReentrantReadWriteLock.ReadLock readLock = Counters.this.zzm.readLock();
                boolean zzb2 = zzb(j, j2);
                readLock.unlock();
                return zzb2;
            } catch (Throwable th) {
                writeLock.unlock();
                throw th;
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:18:0x005f, code lost:
            return false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0095, code lost:
            return r3;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final boolean zzb(long r8, long r10) {
            /*
                r7 = this;
                java.lang.Object r0 = r7.zze
                monitor-enter(r0)
                java.util.Map<java.lang.Integer, java.util.Map<java.lang.Long, long[]>> r1 = r7.zza     // Catch:{ all -> 0x0096 }
                com.google.android.gms.clearcut.Counters r2 = com.google.android.gms.clearcut.Counters.this     // Catch:{ all -> 0x0096 }
                java.lang.Integer r2 = r2.zzp     // Catch:{ all -> 0x0096 }
                java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x0096 }
                java.util.Map r1 = (java.util.Map) r1     // Catch:{ all -> 0x0096 }
                if (r1 != 0) goto L_0x0023
                java.util.HashMap r1 = new java.util.HashMap     // Catch:{ all -> 0x0096 }
                r1.<init>()     // Catch:{ all -> 0x0096 }
                java.util.Map<java.lang.Integer, java.util.Map<java.lang.Long, long[]>> r2 = r7.zza     // Catch:{ all -> 0x0096 }
                com.google.android.gms.clearcut.Counters r3 = com.google.android.gms.clearcut.Counters.this     // Catch:{ all -> 0x0096 }
                java.lang.Integer r3 = r3.zzp     // Catch:{ all -> 0x0096 }
                r2.put(r3, r1)     // Catch:{ all -> 0x0096 }
            L_0x0023:
                int r2 = r7.zzc     // Catch:{ all -> 0x0096 }
                com.google.android.gms.clearcut.Counters r3 = com.google.android.gms.clearcut.Counters.this     // Catch:{ all -> 0x0096 }
                int r3 = r3.zzd     // Catch:{ all -> 0x0096 }
                r4 = 0
                if (r2 < r3) goto L_0x0060
                com.google.android.gms.clearcut.Counters r2 = com.google.android.gms.clearcut.Counters.this     // Catch:{ all -> 0x0096 }
                boolean r2 = r2.zzg     // Catch:{ all -> 0x0096 }
                if (r2 != 0) goto L_0x0060
                int r8 = r7.zzc     // Catch:{ all -> 0x0096 }
                com.google.android.gms.clearcut.Counters r9 = com.google.android.gms.clearcut.Counters.this     // Catch:{ all -> 0x0096 }
                int r9 = r9.zzd     // Catch:{ all -> 0x0096 }
                if (r8 != r9) goto L_0x005e
                java.lang.String r8 = "Counters"
                java.lang.String r9 = "exceeded sample count in "
                java.lang.String r10 = r7.zzb     // Catch:{ all -> 0x0096 }
                java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch:{ all -> 0x0096 }
                int r11 = r10.length()     // Catch:{ all -> 0x0096 }
                if (r11 == 0) goto L_0x0055
                java.lang.String r9 = r9.concat(r10)     // Catch:{ all -> 0x0096 }
                goto L_0x005b
            L_0x0055:
                java.lang.String r10 = new java.lang.String     // Catch:{ all -> 0x0096 }
                r10.<init>(r9)     // Catch:{ all -> 0x0096 }
                r9 = r10
            L_0x005b:
                android.util.Log.i(r8, r9)     // Catch:{ all -> 0x0096 }
            L_0x005e:
                monitor-exit(r0)     // Catch:{ all -> 0x0096 }
                return r4
            L_0x0060:
                int r2 = r7.zzc     // Catch:{ all -> 0x0096 }
                r3 = 1
                int r2 = r2 + r3
                r7.zzc = r2     // Catch:{ all -> 0x0096 }
                java.lang.Long r2 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x0096 }
                java.lang.Object r2 = r1.get(r2)     // Catch:{ all -> 0x0096 }
                long[] r2 = (long[]) r2     // Catch:{ all -> 0x0096 }
                if (r2 != 0) goto L_0x007f
                long[] r2 = new long[r3]     // Catch:{ all -> 0x0096 }
                r5 = 0
                r2[r4] = r5     // Catch:{ all -> 0x0096 }
                java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x0096 }
                r1.put(r8, r2)     // Catch:{ all -> 0x0096 }
            L_0x007f:
                r8 = r2[r4]     // Catch:{ all -> 0x0096 }
                long r8 = r8 + r10
                r2[r4] = r8     // Catch:{ all -> 0x0096 }
                com.google.android.gms.clearcut.Counters r8 = com.google.android.gms.clearcut.Counters.this     // Catch:{ all -> 0x0096 }
                boolean r8 = r8.zzg     // Catch:{ all -> 0x0096 }
                if (r8 == 0) goto L_0x0093
                int r8 = r7.zzc     // Catch:{ all -> 0x0096 }
                int r9 = r7.zzd     // Catch:{ all -> 0x0096 }
                if (r8 < r9) goto L_0x0093
                goto L_0x0094
            L_0x0093:
                r3 = 0
            L_0x0094:
                monitor-exit(r0)     // Catch:{ all -> 0x0096 }
                return r3
            L_0x0096:
                r8 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0096 }
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.clearcut.Counters.AbstractCounter.zzb(long, long):boolean");
        }

        /* access modifiers changed from: protected */
        public void incrementBase(long j) {
            incrementBase(j, 1);
        }

        /* access modifiers changed from: protected */
        public final void incrementBase(long[] jArr, long[] jArr2, Alias alias) {
            boolean z;
            if (jArr2.length <= 0 || jArr2.length == jArr.length) {
                Counters.this.zzm.readLock().lock();
                try {
                    boolean z2 = false;
                    if (Counters.this.zzp == null) {
                        z = true;
                    } else {
                        z2 = zzb(jArr, jArr2, alias);
                        z = false;
                    }
                    if (z) {
                        z2 = zza(jArr, jArr2, alias);
                    }
                    if (z2) {
                        Counters counters = Counters.this;
                        counters.logAllAsync(counters.zzf);
                    }
                } finally {
                    Counters.this.zzm.readLock().unlock();
                }
            } else {
                throw new IllegalArgumentException("inconsistent key/increment lengths");
            }
        }

        /* JADX INFO: finally extract failed */
        private final boolean zza(long[] jArr, long[] jArr2, Alias alias) {
            ReentrantReadWriteLock.WriteLock writeLock = Counters.this.zzm.writeLock();
            writeLock.lock();
            try {
                Integer unused = Counters.this.zzp = Counters.this.zza(Counters.this.zzo);
                Counters.this.zzm.readLock().lock();
                writeLock.unlock();
                ReentrantReadWriteLock.ReadLock readLock = Counters.this.zzm.readLock();
                boolean zzb2 = zzb(jArr, jArr2, alias);
                readLock.unlock();
                return zzb2;
            } catch (Throwable th) {
                writeLock.unlock();
                throw th;
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:20:0x006d, code lost:
            return false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b8, code lost:
            return r4;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final boolean zzb(long[] r11, long[] r12, com.google.android.gms.clearcut.Counters.Alias r13) {
            /*
                r10 = this;
                java.lang.Object r0 = r10.zze
                monitor-enter(r0)
                java.util.Map<java.lang.Integer, java.util.Map<java.lang.Long, long[]>> r1 = r10.zza     // Catch:{ all -> 0x00b9 }
                com.google.android.gms.clearcut.Counters r2 = com.google.android.gms.clearcut.Counters.this     // Catch:{ all -> 0x00b9 }
                java.lang.Integer r2 = r2.zzp     // Catch:{ all -> 0x00b9 }
                java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x00b9 }
                java.util.Map r1 = (java.util.Map) r1     // Catch:{ all -> 0x00b9 }
                if (r1 != 0) goto L_0x0023
                java.util.HashMap r1 = new java.util.HashMap     // Catch:{ all -> 0x00b9 }
                r1.<init>()     // Catch:{ all -> 0x00b9 }
                java.util.Map<java.lang.Integer, java.util.Map<java.lang.Long, long[]>> r2 = r10.zza     // Catch:{ all -> 0x00b9 }
                com.google.android.gms.clearcut.Counters r3 = com.google.android.gms.clearcut.Counters.this     // Catch:{ all -> 0x00b9 }
                java.lang.Integer r3 = r3.zzp     // Catch:{ all -> 0x00b9 }
                r2.put(r3, r1)     // Catch:{ all -> 0x00b9 }
            L_0x0023:
                int r2 = r10.zzc     // Catch:{ all -> 0x00b9 }
                int r3 = r11.length     // Catch:{ all -> 0x00b9 }
                int r2 = r2 + r3
                com.google.android.gms.clearcut.Counters r3 = com.google.android.gms.clearcut.Counters.this     // Catch:{ all -> 0x00b9 }
                int r3 = r3.zzd     // Catch:{ all -> 0x00b9 }
                r4 = 0
                if (r2 < r3) goto L_0x006e
                com.google.android.gms.clearcut.Counters r2 = com.google.android.gms.clearcut.Counters.this     // Catch:{ all -> 0x00b9 }
                boolean r2 = r2.zzg     // Catch:{ all -> 0x00b9 }
                if (r2 != 0) goto L_0x006e
                int r12 = r10.zzc     // Catch:{ all -> 0x00b9 }
                com.google.android.gms.clearcut.Counters r13 = com.google.android.gms.clearcut.Counters.this     // Catch:{ all -> 0x00b9 }
                int r13 = r13.zzd     // Catch:{ all -> 0x00b9 }
                if (r12 >= r13) goto L_0x006c
                int r12 = r10.zzc     // Catch:{ all -> 0x00b9 }
                int r11 = r11.length     // Catch:{ all -> 0x00b9 }
                int r12 = r12 + r11
                com.google.android.gms.clearcut.Counters r11 = com.google.android.gms.clearcut.Counters.this     // Catch:{ all -> 0x00b9 }
                int r11 = r11.zzd     // Catch:{ all -> 0x00b9 }
                if (r12 < r11) goto L_0x006c
                java.lang.String r11 = "Counters"
                java.lang.String r12 = "exceeded sample count in "
                java.lang.String r13 = r10.zzb     // Catch:{ all -> 0x00b9 }
                java.lang.String r13 = java.lang.String.valueOf(r13)     // Catch:{ all -> 0x00b9 }
                int r1 = r13.length()     // Catch:{ all -> 0x00b9 }
                if (r1 == 0) goto L_0x0063
                java.lang.String r12 = r12.concat(r13)     // Catch:{ all -> 0x00b9 }
                goto L_0x0069
            L_0x0063:
                java.lang.String r13 = new java.lang.String     // Catch:{ all -> 0x00b9 }
                r13.<init>(r12)     // Catch:{ all -> 0x00b9 }
                r12 = r13
            L_0x0069:
                android.util.Log.i(r11, r12)     // Catch:{ all -> 0x00b9 }
            L_0x006c:
                monitor-exit(r0)     // Catch:{ all -> 0x00b9 }
                return r4
            L_0x006e:
                int r2 = r10.zzc     // Catch:{ all -> 0x00b9 }
                int r3 = r11.length     // Catch:{ all -> 0x00b9 }
                int r2 = r2 + r3
                r10.zzc = r2     // Catch:{ all -> 0x00b9 }
                r2 = 0
            L_0x0075:
                int r3 = r11.length     // Catch:{ all -> 0x00b9 }
                r5 = 1
                if (r2 >= r3) goto L_0x00a8
                r6 = r11[r2]     // Catch:{ all -> 0x00b9 }
                long r6 = r13.alias(r6)     // Catch:{ all -> 0x00b9 }
                java.lang.Long r3 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x00b9 }
                java.lang.Object r3 = r1.get(r3)     // Catch:{ all -> 0x00b9 }
                long[] r3 = (long[]) r3     // Catch:{ all -> 0x00b9 }
                if (r3 != 0) goto L_0x0098
                long[] r3 = new long[r5]     // Catch:{ all -> 0x00b9 }
                r8 = 0
                r3[r4] = r8     // Catch:{ all -> 0x00b9 }
                java.lang.Long r5 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x00b9 }
                r1.put(r5, r3)     // Catch:{ all -> 0x00b9 }
            L_0x0098:
                r5 = r3[r4]     // Catch:{ all -> 0x00b9 }
                int r7 = r12.length     // Catch:{ all -> 0x00b9 }
                if (r2 < r7) goto L_0x00a0
                r7 = 1
                goto L_0x00a2
            L_0x00a0:
                r7 = r12[r2]     // Catch:{ all -> 0x00b9 }
            L_0x00a2:
                long r5 = r5 + r7
                r3[r4] = r5     // Catch:{ all -> 0x00b9 }
                int r2 = r2 + 1
                goto L_0x0075
            L_0x00a8:
                com.google.android.gms.clearcut.Counters r11 = com.google.android.gms.clearcut.Counters.this     // Catch:{ all -> 0x00b9 }
                boolean r11 = r11.zzg     // Catch:{ all -> 0x00b9 }
                if (r11 == 0) goto L_0x00b7
                int r11 = r10.zzc     // Catch:{ all -> 0x00b9 }
                int r12 = r10.zzd     // Catch:{ all -> 0x00b9 }
                if (r11 < r12) goto L_0x00b7
                r4 = 1
            L_0x00b7:
                monitor-exit(r0)     // Catch:{ all -> 0x00b9 }
                return r4
            L_0x00b9:
                r11 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00b9 }
                throw r11
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.clearcut.Counters.AbstractCounter.zzb(long[], long[], com.google.android.gms.clearcut.Counters$Alias):boolean");
        }

        /* access modifiers changed from: protected */
        public long getCountBase(long j) {
            Counters.this.zzm.readLock().lock();
            try {
                synchronized (this.zze) {
                    if (Counters.this.zzp == null) {
                        Counters.this.zzm.readLock().unlock();
                        return 0;
                    }
                    Map map = this.zza.get(Counters.this.zzp);
                    if (map == null) {
                        Counters.this.zzm.readLock().unlock();
                        return 0;
                    }
                    long[] jArr = (long[]) map.get(Long.valueOf(j));
                    if (jArr == null) {
                        Counters.this.zzm.readLock().unlock();
                        return 0;
                    }
                    long j2 = jArr[0];
                    Counters.this.zzm.readLock().unlock();
                    return j2;
                }
            } catch (Throwable th) {
                Counters.this.zzm.readLock().unlock();
                throw th;
            }
        }

        public String getName() {
            return this.zzb;
        }

        public void setAutoLogAsyncThreshold(int i) {
            boolean z = true;
            zzau.zzb(i > 0);
            if (i > Counters.this.zzd) {
                z = false;
            }
            zzau.zzb(z);
            this.zzd = i;
        }
    }

    public Counters(ClearcutLogger clearcutLogger, String str, int i, Clock clock) {
        this.zzm = new ReentrantReadWriteLock();
        this.zzn = new TreeMap();
        this.zzo = null;
        this.zzp = null;
        this.zzq = new TreeMap<>(zzs);
        this.zzr = null;
        zzau.zza(clearcutLogger);
        zzau.zza((Object) str);
        zzau.zzb(i > 0);
        zzau.zza(clock);
        this.zzl = clearcutLogger;
        this.zzc = str;
        this.zzd = i;
        this.zze = clock;
        this.zzk = clock.elapsedRealtime();
    }

    @Deprecated
    public void setAutoLogAsync(GoogleApiClient googleApiClient) {
        setAutoLogAsync();
    }

    public void setAutoLogAsync() {
        this.zzm.writeLock().lock();
        try {
            this.zzg = true;
        } finally {
            this.zzm.writeLock().unlock();
        }
    }

    public void setAutoLogMillis(ScheduledExecutorService scheduledExecutorService, int i) {
        this.zzm.writeLock().lock();
        try {
            this.zzi = scheduledExecutorService;
            if (this.zzi != null) {
                this.zzh = i;
                zzc();
            } else {
                this.zzh = 0;
            }
        } finally {
            this.zzm.writeLock().unlock();
        }
    }

    /* access modifiers changed from: private */
    public final void zzc() {
        this.zzm.writeLock().lock();
        try {
            if (this.zzj != null) {
                this.zzj.cancel(false);
            }
            this.zzj = this.zzi.schedule(new zzo(this), (long) this.zzh, TimeUnit.MILLISECONDS);
        } finally {
            this.zzm.writeLock().unlock();
        }
    }

    public void setLogEventModifier(LogEventModifier logEventModifier) {
        this.zzm.writeLock().lock();
        try {
            this.zzf = logEventModifier;
        } finally {
            this.zzm.writeLock().unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.zzm.readLock().lock();
        try {
            sb.append("{");
            for (Map.Entry next : this.zzq.entrySet()) {
                sb.append(next.getKey() == null ? "null" : new String((byte[]) next.getKey()));
                sb.append(", ");
            }
            sb.append("}\n");
            for (AbstractCounter abstractCounter : this.zzn.values()) {
                sb.append(abstractCounter.toString());
                sb.append("\n");
            }
            this.zzm.readLock().unlock();
            return sb.toString();
        } catch (Throwable th) {
            this.zzm.readLock().unlock();
            throw th;
        }
    }

    private final AbstractCounter zza(AbstractCounter abstractCounter, boolean z) {
        if (abstractCounter instanceof Counter) {
            return new Counter(this, (Counter) abstractCounter, z, null);
        }
        if (abstractCounter instanceof TimerHistogram) {
            return new TimerHistogram(this, (TimerHistogram) abstractCounter, z, (zzp) null);
        }
        if (abstractCounter instanceof IntegerHistogram) {
            return new IntegerHistogram(this, (IntegerHistogram) abstractCounter, z, null);
        }
        if (abstractCounter instanceof LongHistogram) {
            return new LongHistogram(this, (LongHistogram) abstractCounter, z, (zzp) null);
        }
        if (abstractCounter instanceof BooleanHistogram) {
            return new BooleanHistogram(this, (BooleanHistogram) abstractCounter, z, null);
        }
        String valueOf = String.valueOf(abstractCounter);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 21);
        sb.append("Unkown counter type: ");
        sb.append(valueOf);
        throw new IllegalArgumentException(sb.toString());
    }

    public void setLogCallback(LogCallback logCallback) {
        this.zzr = logCallback;
    }

    public void setDimensionsInstance(byte[] bArr) {
        this.zzm.writeLock().lock();
        try {
            this.zzo = bArr;
            this.zzp = this.zzq.get(this.zzo);
        } finally {
            this.zzm.writeLock().unlock();
        }
    }

    public Collection<byte[]> getDimensionsInstances() {
        this.zzm.readLock().lock();
        try {
            return new ArrayList(this.zzq.keySet());
        } finally {
            this.zzm.readLock().unlock();
        }
    }

    /* access modifiers changed from: private */
    public final Integer zza(byte[] bArr) {
        Integer num = this.zzq.get(bArr);
        if (num != null) {
            return num;
        }
        Integer valueOf = Integer.valueOf(this.zzq.size());
        this.zzq.put(bArr, valueOf);
        return valueOf;
    }

    public Counters snapshotAndReset() {
        return new Counters(this, true);
    }

    public Counters snapshot() {
        return new Counters(this, false);
    }

    public static long umaMetricHash(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes(zza));
            return ByteBuffer.wrap(instance.digest()).getLong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Timer newTimer() {
        return new Timer();
    }

    public Counter newCounter(String str) {
        this.zzm.writeLock().lock();
        try {
            return new Counter(this, str, (zzp) null);
        } finally {
            this.zzm.writeLock().unlock();
        }
    }

    public BooleanHistogram newBooleanHistogram(String str) {
        this.zzm.writeLock().lock();
        try {
            return new BooleanHistogram(this, str, (zzp) null);
        } finally {
            this.zzm.writeLock().unlock();
        }
    }

    public IntegerHistogram newIntegerHistogram(String str) {
        this.zzm.writeLock().lock();
        try {
            return new IntegerHistogram(this, str, (zzp) null);
        } finally {
            this.zzm.writeLock().unlock();
        }
    }

    public LongHistogram newLongHistogram(String str) {
        return newLongHistogram(str, IDENTITY);
    }

    public LongHistogram newLongHistogram(String str, Alias alias) {
        this.zzm.writeLock().lock();
        try {
            return new LongHistogram(this, str, alias, (zzp) null);
        } finally {
            this.zzm.writeLock().unlock();
        }
    }

    public TimerHistogram newTimerHistogram(String str) {
        return new TimerHistogram(this, str, IDENTITY, (zzp) null);
    }

    public TimerHistogram newTimerHistogram(String str, Alias alias) {
        this.zzm.writeLock().lock();
        try {
            return new TimerHistogram(this, str, alias, (zzp) null);
        } finally {
            this.zzm.writeLock().unlock();
        }
    }

    public Counter getCounter(String str) {
        this.zzm.writeLock().lock();
        try {
            AbstractCounter abstractCounter = this.zzn.get(str);
            if (abstractCounter == null) {
                Counter newCounter = newCounter(str);
                this.zzm.writeLock().unlock();
                return newCounter;
            }
            Counter counter = (Counter) abstractCounter;
            this.zzm.writeLock().unlock();
            return counter;
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "another type of counter exists with name: ".concat(valueOf) : new String("another type of counter exists with name: "));
        } catch (Throwable th) {
            this.zzm.writeLock().unlock();
            throw th;
        }
    }

    public BooleanHistogram getBooleanHistogram(String str) {
        this.zzm.writeLock().lock();
        try {
            AbstractCounter abstractCounter = this.zzn.get(str);
            if (abstractCounter == null) {
                BooleanHistogram newBooleanHistogram = newBooleanHistogram(str);
                this.zzm.writeLock().unlock();
                return newBooleanHistogram;
            }
            BooleanHistogram booleanHistogram = (BooleanHistogram) abstractCounter;
            this.zzm.writeLock().unlock();
            return booleanHistogram;
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "another type of counter exists with name: ".concat(valueOf) : new String("another type of counter exists with name: "));
        } catch (Throwable th) {
            this.zzm.writeLock().unlock();
            throw th;
        }
    }

    public IntegerHistogram getIntegerHistogram(String str) {
        this.zzm.writeLock().lock();
        try {
            AbstractCounter abstractCounter = this.zzn.get(str);
            if (abstractCounter == null) {
                IntegerHistogram newIntegerHistogram = newIntegerHistogram(str);
                this.zzm.writeLock().unlock();
                return newIntegerHistogram;
            }
            IntegerHistogram integerHistogram = (IntegerHistogram) abstractCounter;
            this.zzm.writeLock().unlock();
            return integerHistogram;
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "another type of counter exists with name: ".concat(valueOf) : new String("another type of counter exists with name: "));
        } catch (Throwable th) {
            this.zzm.writeLock().unlock();
            throw th;
        }
    }

    public LongHistogram getLongHistogram(String str) {
        return getLongHistogram(str, IDENTITY);
    }

    public LongHistogram getLongHistogram(String str, Alias alias) {
        this.zzm.writeLock().lock();
        try {
            AbstractCounter abstractCounter = this.zzn.get(str);
            if (abstractCounter == null) {
                LongHistogram newLongHistogram = newLongHistogram(str, alias);
                this.zzm.writeLock().unlock();
                return newLongHistogram;
            }
            LongHistogram longHistogram = (LongHistogram) abstractCounter;
            if (!alias.equals(longHistogram.zzb)) {
                String valueOf = String.valueOf(str);
                throw new IllegalArgumentException(valueOf.length() != 0 ? "alias mismatch: ".concat(valueOf) : new String("alias mismatch: "));
            }
            this.zzm.writeLock().unlock();
            return longHistogram;
        } catch (ClassCastException e) {
            String valueOf2 = String.valueOf(str);
            throw new IllegalArgumentException(valueOf2.length() != 0 ? "another type of counter exists with name: ".concat(valueOf2) : new String("another type of counter exists with name: "));
        } catch (Throwable th) {
            this.zzm.writeLock().unlock();
            throw th;
        }
    }

    public TimerHistogram getTimerHistogram(String str) {
        return getTimerHistogram(str, IDENTITY);
    }

    public TimerHistogram getTimerHistogram(String str, Alias alias) {
        this.zzm.writeLock().lock();
        try {
            AbstractCounter abstractCounter = this.zzn.get(str);
            if (abstractCounter == null) {
                TimerHistogram newTimerHistogram = newTimerHistogram(str, alias);
                this.zzm.writeLock().unlock();
                return newTimerHistogram;
            }
            TimerHistogram timerHistogram = (TimerHistogram) abstractCounter;
            if (!alias.equals(timerHistogram.zzb)) {
                String valueOf = String.valueOf(str);
                throw new IllegalArgumentException(valueOf.length() != 0 ? "alias mismatch: ".concat(valueOf) : new String("alias mismatch: "));
            }
            this.zzm.writeLock().unlock();
            return timerHistogram;
        } catch (ClassCastException e) {
            String valueOf2 = String.valueOf(str);
            throw new IllegalArgumentException(valueOf2.length() != 0 ? "another type of counter exists with name: ".concat(valueOf2) : new String("another type of counter exists with name: "));
        } catch (Throwable th) {
            this.zzm.writeLock().unlock();
            throw th;
        }
    }

    public ClearcutLogger.MessageProducer makeProducer(byte[] bArr) {
        return new zzb(bArr);
    }

    private final Counters zzd() {
        LogCallback logCallback = this.zzr;
        this.zzm.writeLock().lock();
        if (logCallback != null) {
            try {
                logCallback.onLogged(this);
            } catch (RuntimeException e) {
                Log.i("Counters", "problem executing callback: ", e);
            } catch (Throwable th) {
                this.zzm.writeLock().unlock();
                throw th;
            }
        }
        Counters snapshotAndReset = snapshotAndReset();
        this.zzm.writeLock().unlock();
        return snapshotAndReset;
    }

    @Deprecated
    public PendingResult<Status> logAll(GoogleApiClient googleApiClient) {
        return logAll();
    }

    public PendingResult<Status> logAll() {
        return logAll(this.zzf);
    }

    public PendingResult<Status> logAll(LogEventModifier logEventModifier) {
        Counters zzd2 = zzd();
        PendingResult<Status> pendingResult = null;
        for (ClearcutLogger.MessageProducer protoBytes : zzd2.zze()) {
            ClearcutLogger.LogEventBuilder logSourceName = zzd2.zzl.newEvent(protoBytes.toProtoBytes()).setLogSourceName(zzd2.zzc);
            if (logEventModifier != null) {
                logSourceName = logEventModifier.modify(logSourceName);
            }
            pendingResult = logSourceName.logAsync();
        }
        if (pendingResult != null) {
            return pendingResult;
        }
        return PendingResults.zza(Status.zza, (GoogleApiClient) null);
    }

    @Deprecated
    public PendingResult<Status> logAllAsync(GoogleApiClient googleApiClient) {
        return zzd().zza((LogEventModifier) null);
    }

    public void logAllAsync() {
        logAllAsync(this.zzf);
    }

    public void logAllAsync(LogEventModifier logEventModifier) {
        zzd().zza(logEventModifier);
    }

    private final PendingResult<Status> zza(LogEventModifier logEventModifier) {
        PendingResult<Status> pendingResult = null;
        for (ClearcutLogger.MessageProducer newEvent : zze()) {
            ClearcutLogger.LogEventBuilder logSourceName = this.zzl.newEvent(newEvent).setLogSourceName(this.zzc);
            if (logEventModifier != null) {
                logSourceName = logEventModifier.modify(logSourceName);
            }
            pendingResult = logSourceName.logAsync();
        }
        if (pendingResult != null) {
            return pendingResult;
        }
        return PendingResults.zza(Status.zza, (GoogleApiClient) null);
    }

    public ClearcutLogger.MessageProducer[] getEventProducersForLogging() {
        return zzd().zze();
    }

    private final ClearcutLogger.MessageProducer[] zze() {
        Set<byte[]> keySet = this.zzq.keySet();
        ClearcutLogger.MessageProducer[] messageProducerArr = new ClearcutLogger.MessageProducer[keySet.size()];
        int i = 0;
        for (byte[] makeProducer : keySet) {
            messageProducerArr[i] = makeProducer(makeProducer);
            i++;
        }
        return messageProducerArr;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza() {
        this.zzm.writeLock().lock();
        try {
            this.zzj = null;
            this.zzm.writeLock().unlock();
            logAllAsync();
        } catch (Throwable th) {
            this.zzm.writeLock().unlock();
            throw th;
        }
    }
}
