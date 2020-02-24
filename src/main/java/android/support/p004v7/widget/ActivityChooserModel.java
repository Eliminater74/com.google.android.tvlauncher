package android.support.p004v7.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: android.support.v7.widget.ActivityChooserModel */
class ActivityChooserModel extends DataSetObservable {
    static final String ATTRIBUTE_ACTIVITY = "activity";
    static final String ATTRIBUTE_TIME = "time";
    static final String ATTRIBUTE_WEIGHT = "weight";
    static final boolean DEBUG = false;
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;
    static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
    static final String TAG_HISTORICAL_RECORD = "historical-record";
    static final String TAG_HISTORICAL_RECORDS = "historical-records";
    private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
    private static final Object sRegistryLock = new Object();
    private final List<ActivityResolveInfo> mActivities = new ArrayList();
    private OnChooseActivityListener mActivityChoserModelPolicy;
    private ActivitySorter mActivitySorter = new DefaultSorter();
    boolean mCanReadHistoricalData = true;
    final Context mContext;
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList();
    private boolean mHistoricalRecordsChanged = true;
    final String mHistoryFileName;
    private int mHistoryMaxSize = 50;
    private final Object mInstanceLock = new Object();
    private Intent mIntent;
    private boolean mReadShareHistoryCalled = false;
    private boolean mReloadActivities = false;

    /* renamed from: android.support.v7.widget.ActivityChooserModel$ActivityChooserModelClient */
    public interface ActivityChooserModelClient {
        void setActivityChooserModel(ActivityChooserModel activityChooserModel);
    }

    /* renamed from: android.support.v7.widget.ActivityChooserModel$ActivitySorter */
    public interface ActivitySorter {
        void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2);
    }

    /* renamed from: android.support.v7.widget.ActivityChooserModel$OnChooseActivityListener */
    public interface OnChooseActivityListener {
        boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent);
    }

    public static ActivityChooserModel get(Context context, String historyFileName) {
        ActivityChooserModel dataModel;
        synchronized (sRegistryLock) {
            dataModel = sDataModelRegistry.get(historyFileName);
            if (dataModel == null) {
                dataModel = new ActivityChooserModel(context, historyFileName);
                sDataModelRegistry.put(historyFileName, dataModel);
            }
        }
        return dataModel;
    }

    private ActivityChooserModel(Context context, String historyFileName) {
        this.mContext = context.getApplicationContext();
        if (TextUtils.isEmpty(historyFileName) || historyFileName.endsWith(HISTORY_FILE_EXTENSION)) {
            this.mHistoryFileName = historyFileName;
            return;
        }
        this.mHistoryFileName = historyFileName + HISTORY_FILE_EXTENSION;
    }

    public void setIntent(Intent intent) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent != intent) {
                this.mIntent = intent;
                this.mReloadActivities = true;
                ensureConsistentState();
            }
        }
    }

    public Intent getIntent() {
        Intent intent;
        synchronized (this.mInstanceLock) {
            intent = this.mIntent;
        }
        return intent;
    }

    public int getActivityCount() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mActivities.size();
        }
        return size;
    }

    public ResolveInfo getActivity(int index) {
        ResolveInfo resolveInfo;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            resolveInfo = this.mActivities.get(index).resolveInfo;
        }
        return resolveInfo;
    }

    public int getActivityIndex(ResolveInfo activity) {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            List<ActivityResolveInfo> activities = this.mActivities;
            int activityCount = activities.size();
            for (int i = 0; i < activityCount; i++) {
                if (activities.get(i).resolveInfo == activity) {
                    return i;
                }
            }
            return -1;
        }
    }

    public Intent chooseActivity(int index) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == null) {
                return null;
            }
            ensureConsistentState();
            ActivityResolveInfo chosenActivity = this.mActivities.get(index);
            ComponentName chosenName = new ComponentName(chosenActivity.resolveInfo.activityInfo.packageName, chosenActivity.resolveInfo.activityInfo.name);
            Intent choiceIntent = new Intent(this.mIntent);
            choiceIntent.setComponent(chosenName);
            if (this.mActivityChoserModelPolicy != null) {
                if (this.mActivityChoserModelPolicy.onChooseActivity(this, new Intent(choiceIntent))) {
                    return null;
                }
            }
            addHistoricalRecord(new HistoricalRecord(chosenName, System.currentTimeMillis(), (float) DEFAULT_HISTORICAL_RECORD_WEIGHT));
            return choiceIntent;
        }
    }

    public void setOnChooseActivityListener(OnChooseActivityListener listener) {
        synchronized (this.mInstanceLock) {
            this.mActivityChoserModelPolicy = listener;
        }
    }

    public ResolveInfo getDefaultActivity() {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            if (this.mActivities.isEmpty()) {
                return null;
            }
            ResolveInfo resolveInfo = this.mActivities.get(0).resolveInfo;
            return resolveInfo;
        }
    }

    public void setDefaultActivity(int index) {
        float weight;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            ActivityResolveInfo newDefaultActivity = this.mActivities.get(index);
            ActivityResolveInfo oldDefaultActivity = this.mActivities.get(0);
            if (oldDefaultActivity != null) {
                weight = (oldDefaultActivity.weight - newDefaultActivity.weight) + 5.0f;
            } else {
                weight = DEFAULT_HISTORICAL_RECORD_WEIGHT;
            }
            addHistoricalRecord(new HistoricalRecord(new ComponentName(newDefaultActivity.resolveInfo.activityInfo.packageName, newDefaultActivity.resolveInfo.activityInfo.name), System.currentTimeMillis(), weight));
        }
    }

    private void persistHistoricalDataIfNeeded() {
        if (!this.mReadShareHistoryCalled) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        } else if (this.mHistoricalRecordsChanged) {
            this.mHistoricalRecordsChanged = false;
            if (!TextUtils.isEmpty(this.mHistoryFileName)) {
                new PersistHistoryAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new ArrayList(this.mHistoricalRecords), this.mHistoryFileName);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0015, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setActivitySorter(android.support.p004v7.widget.ActivityChooserModel.ActivitySorter r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.mInstanceLock
            monitor-enter(r0)
            android.support.v7.widget.ActivityChooserModel$ActivitySorter r1 = r2.mActivitySorter     // Catch:{ all -> 0x0016 }
            if (r1 != r3) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            return
        L_0x0009:
            r2.mActivitySorter = r3     // Catch:{ all -> 0x0016 }
            boolean r1 = r2.sortActivitiesIfNeeded()     // Catch:{ all -> 0x0016 }
            if (r1 == 0) goto L_0x0014
            r2.notifyChanged()     // Catch:{ all -> 0x0016 }
        L_0x0014:
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            return
        L_0x0016:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.widget.ActivityChooserModel.setActivitySorter(android.support.v7.widget.ActivityChooserModel$ActivitySorter):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setHistoryMaxSize(int r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.mInstanceLock
            monitor-enter(r0)
            int r1 = r2.mHistoryMaxSize     // Catch:{ all -> 0x0019 }
            if (r1 != r3) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            return
        L_0x0009:
            r2.mHistoryMaxSize = r3     // Catch:{ all -> 0x0019 }
            r2.pruneExcessiveHistoricalRecordsIfNeeded()     // Catch:{ all -> 0x0019 }
            boolean r1 = r2.sortActivitiesIfNeeded()     // Catch:{ all -> 0x0019 }
            if (r1 == 0) goto L_0x0017
            r2.notifyChanged()     // Catch:{ all -> 0x0019 }
        L_0x0017:
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            return
        L_0x0019:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.widget.ActivityChooserModel.setHistoryMaxSize(int):void");
    }

    public int getHistoryMaxSize() {
        int i;
        synchronized (this.mInstanceLock) {
            i = this.mHistoryMaxSize;
        }
        return i;
    }

    public int getHistorySize() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mHistoricalRecords.size();
        }
        return size;
    }

    private void ensureConsistentState() {
        boolean stateChanged = loadActivitiesIfNeeded() | readHistoricalDataIfNeeded();
        pruneExcessiveHistoricalRecordsIfNeeded();
        if (stateChanged) {
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
            return false;
        }
        this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
        return true;
    }

    private boolean loadActivitiesIfNeeded() {
        if (!this.mReloadActivities || this.mIntent == null) {
            return false;
        }
        this.mReloadActivities = false;
        this.mActivities.clear();
        List<ResolveInfo> resolveInfos = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        int resolveInfoCount = resolveInfos.size();
        for (int i = 0; i < resolveInfoCount; i++) {
            this.mActivities.add(new ActivityResolveInfo(resolveInfos.get(i)));
        }
        return true;
    }

    private boolean readHistoricalDataIfNeeded() {
        if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.mHistoryFileName)) {
            return false;
        }
        this.mCanReadHistoricalData = false;
        this.mReadShareHistoryCalled = true;
        readHistoricalDataImpl();
        return true;
    }

    private boolean addHistoricalRecord(HistoricalRecord historicalRecord) {
        boolean added = this.mHistoricalRecords.add(historicalRecord);
        if (added) {
            this.mHistoricalRecordsChanged = true;
            pruneExcessiveHistoricalRecordsIfNeeded();
            persistHistoricalDataIfNeeded();
            sortActivitiesIfNeeded();
            notifyChanged();
        }
        return added;
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int pruneCount = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if (pruneCount > 0) {
            this.mHistoricalRecordsChanged = true;
            for (int i = 0; i < pruneCount; i++) {
                HistoricalRecord remove = this.mHistoricalRecords.remove(0);
            }
        }
    }

    /* renamed from: android.support.v7.widget.ActivityChooserModel$HistoricalRecord */
    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(String activityName, long time2, float weight2) {
            this(ComponentName.unflattenFromString(activityName), time2, weight2);
        }

        public HistoricalRecord(ComponentName activityName, long time2, float weight2) {
            this.activity = activityName;
            this.time = time2;
            this.weight = weight2;
        }

        public int hashCode() {
            int i = 1 * 31;
            ComponentName componentName = this.activity;
            int hashCode = componentName == null ? 0 : componentName.hashCode();
            long j = this.time;
            return ((((i + hashCode) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + Float.floatToIntBits(this.weight);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            HistoricalRecord other = (HistoricalRecord) obj;
            ComponentName componentName = this.activity;
            if (componentName == null) {
                if (other.activity != null) {
                    return false;
                }
            } else if (!componentName.equals(other.activity)) {
                return false;
            }
            if (this.time == other.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(other.weight)) {
                return true;
            }
            return false;
        }

        public String toString() {
            return "[" + "; activity:" + this.activity + "; time:" + this.time + "; weight:" + new BigDecimal((double) this.weight) + "]";
        }
    }

    /* renamed from: android.support.v7.widget.ActivityChooserModel$ActivityResolveInfo */
    public static final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo2) {
            this.resolveInfo = resolveInfo2;
        }

        public int hashCode() {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass() && Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo) obj).weight)) {
                return true;
            }
            return false;
        }

        public int compareTo(ActivityResolveInfo another) {
            return Float.floatToIntBits(another.weight) - Float.floatToIntBits(this.weight);
        }

        public String toString() {
            return "[" + "resolveInfo:" + this.resolveInfo.toString() + "; weight:" + new BigDecimal((double) this.weight) + "]";
        }
    }

    /* renamed from: android.support.v7.widget.ActivityChooserModel$DefaultSorter */
    private static final class DefaultSorter implements ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<ComponentName, ActivityResolveInfo> mPackageNameToActivityMap = new HashMap();

        DefaultSorter() {
        }

        public void sort(Intent intent, List<ActivityResolveInfo> activities, List<HistoricalRecord> historicalRecords) {
            Map<ComponentName, ActivityResolveInfo> componentNameToActivityMap = this.mPackageNameToActivityMap;
            componentNameToActivityMap.clear();
            int activityCount = activities.size();
            for (int i = 0; i < activityCount; i++) {
                ActivityResolveInfo activity = activities.get(i);
                activity.weight = 0.0f;
                componentNameToActivityMap.put(new ComponentName(activity.resolveInfo.activityInfo.packageName, activity.resolveInfo.activityInfo.name), activity);
            }
            float nextRecordWeight = ActivityChooserModel.DEFAULT_HISTORICAL_RECORD_WEIGHT;
            for (int i2 = historicalRecords.size() - 1; i2 >= 0; i2--) {
                HistoricalRecord historicalRecord = historicalRecords.get(i2);
                ActivityResolveInfo activity2 = componentNameToActivityMap.get(historicalRecord.activity);
                if (activity2 != null) {
                    activity2.weight += historicalRecord.weight * nextRecordWeight;
                    nextRecordWeight *= WEIGHT_DECAY_COEFFICIENT;
                }
            }
            Collections.sort(activities);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003c, code lost:
        if (r1 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void readHistoricalDataImpl() {
        /*
            r12 = this;
            java.lang.String r0 = "Error reading historical recrod file: "
            r1 = 0
            android.content.Context r2 = r12.mContext     // Catch:{ FileNotFoundException -> 0x00d6 }
            java.lang.String r3 = r12.mHistoryFileName     // Catch:{ FileNotFoundException -> 0x00d6 }
            java.io.FileInputStream r2 = r2.openFileInput(r3)     // Catch:{ FileNotFoundException -> 0x00d6 }
            r1 = r2
            org.xmlpull.v1.XmlPullParser r2 = android.util.Xml.newPullParser()     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            java.lang.String r3 = "UTF-8"
            r2.setInput(r1, r3)     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            r3 = 0
        L_0x0017:
            r4 = 1
            if (r3 == r4) goto L_0x0023
            r5 = 2
            if (r3 == r5) goto L_0x0023
            int r4 = r2.next()     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            r3 = r4
            goto L_0x0017
        L_0x0023:
            java.lang.String r5 = "historical-records"
            java.lang.String r6 = r2.getName()     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            boolean r5 = r5.equals(r6)     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            if (r5 == 0) goto L_0x0085
            java.util.List<android.support.v7.widget.ActivityChooserModel$HistoricalRecord> r5 = r12.mHistoricalRecords     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            r5.clear()     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
        L_0x0034:
            int r6 = r2.next()     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            r3 = r6
            if (r3 != r4) goto L_0x0043
            if (r1 == 0) goto L_0x00cd
            r1.close()     // Catch:{ IOException -> 0x00cb }
            goto L_0x00ca
        L_0x0043:
            r6 = 3
            if (r3 == r6) goto L_0x0034
            r6 = 4
            if (r3 != r6) goto L_0x004a
            goto L_0x0034
        L_0x004a:
            java.lang.String r6 = r2.getName()     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            java.lang.String r7 = "historical-record"
            boolean r7 = r7.equals(r6)     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            if (r7 == 0) goto L_0x007d
            java.lang.String r7 = "activity"
            r8 = 0
            java.lang.String r7 = r2.getAttributeValue(r8, r7)     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            java.lang.String r9 = "time"
            java.lang.String r9 = r2.getAttributeValue(r8, r9)     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            long r9 = java.lang.Long.parseLong(r9)     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            java.lang.String r11 = "weight"
            java.lang.String r8 = r2.getAttributeValue(r8, r11)     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            float r8 = java.lang.Float.parseFloat(r8)     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            android.support.v7.widget.ActivityChooserModel$HistoricalRecord r11 = new android.support.v7.widget.ActivityChooserModel$HistoricalRecord     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            r11.<init>(r7, r9, r8)     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            r5.add(r11)     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            goto L_0x0034
        L_0x007d:
            org.xmlpull.v1.XmlPullParserException r4 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            java.lang.String r7 = "Share records file not well-formed."
            r4.<init>(r7)     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            throw r4     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
        L_0x0085:
            org.xmlpull.v1.XmlPullParserException r4 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            java.lang.String r5 = "Share records file does not start with historical-records tag."
            r4.<init>(r5)     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
            throw r4     // Catch:{ XmlPullParserException -> 0x00ad, IOException -> 0x008f }
        L_0x008d:
            r0 = move-exception
            goto L_0x00ce
        L_0x008f:
            r2 = move-exception
            java.lang.String r3 = android.support.p004v7.widget.ActivityChooserModel.LOG_TAG     // Catch:{ all -> 0x008d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x008d }
            r4.<init>()     // Catch:{ all -> 0x008d }
            r4.append(r0)     // Catch:{ all -> 0x008d }
            java.lang.String r0 = r12.mHistoryFileName     // Catch:{ all -> 0x008d }
            r4.append(r0)     // Catch:{ all -> 0x008d }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x008d }
            android.util.Log.e(r3, r0, r2)     // Catch:{ all -> 0x008d }
            if (r1 == 0) goto L_0x00cd
            r1.close()     // Catch:{ IOException -> 0x00cb }
            goto L_0x00ca
        L_0x00ad:
            r2 = move-exception
            java.lang.String r3 = android.support.p004v7.widget.ActivityChooserModel.LOG_TAG     // Catch:{ all -> 0x008d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x008d }
            r4.<init>()     // Catch:{ all -> 0x008d }
            r4.append(r0)     // Catch:{ all -> 0x008d }
            java.lang.String r0 = r12.mHistoryFileName     // Catch:{ all -> 0x008d }
            r4.append(r0)     // Catch:{ all -> 0x008d }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x008d }
            android.util.Log.e(r3, r0, r2)     // Catch:{ all -> 0x008d }
            if (r1 == 0) goto L_0x00cd
            r1.close()     // Catch:{ IOException -> 0x00cb }
        L_0x00ca:
            goto L_0x00cd
        L_0x00cb:
            r0 = move-exception
            goto L_0x00ca
        L_0x00cd:
            return
        L_0x00ce:
            if (r1 == 0) goto L_0x00d5
            r1.close()     // Catch:{ IOException -> 0x00d4 }
            goto L_0x00d5
        L_0x00d4:
            r2 = move-exception
        L_0x00d5:
            throw r0
        L_0x00d6:
            r0 = move-exception
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.widget.ActivityChooserModel.readHistoricalDataImpl():void");
    }

    /* renamed from: android.support.v7.widget.ActivityChooserModel$PersistHistoryAsyncTask */
    private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
        PersistHistoryAsyncTask() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:27:0x00b1 A[SYNTHETIC, Splitter:B:27:0x00b1] */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x00d7 A[SYNTHETIC, Splitter:B:35:0x00d7] */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x00fd A[SYNTHETIC, Splitter:B:43:0x00fd] */
        /* JADX WARNING: Removed duplicated region for block: B:51:0x010c A[SYNTHETIC, Splitter:B:51:0x010c] */
        /* JADX WARNING: Unknown top exception splitter block from list: {B:23:0x0092=Splitter:B:23:0x0092, B:31:0x00b8=Splitter:B:31:0x00b8, B:39:0x00de=Splitter:B:39:0x00de} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(java.lang.Object... r18) {
            /*
                r17 = this;
                r1 = r17
                java.lang.String r0 = "historical-record"
                java.lang.String r2 = "historical-records"
                java.lang.String r3 = "Error writing historical record file: "
                r4 = 0
                r5 = r18[r4]
                java.util.List r5 = (java.util.List) r5
                r6 = 1
                r7 = r18[r6]
                java.lang.String r7 = (java.lang.String) r7
                r8 = 0
                r9 = 0
                android.support.v7.widget.ActivityChooserModel r10 = android.support.p004v7.widget.ActivityChooserModel.this     // Catch:{ FileNotFoundException -> 0x0112 }
                android.content.Context r10 = r10.mContext     // Catch:{ FileNotFoundException -> 0x0112 }
                java.io.FileOutputStream r10 = r10.openFileOutput(r7, r4)     // Catch:{ FileNotFoundException -> 0x0112 }
                r8 = r10
                org.xmlpull.v1.XmlSerializer r10 = android.util.Xml.newSerializer()
                r10.setOutput(r8, r9)     // Catch:{ IllegalArgumentException -> 0x00db, IllegalStateException -> 0x00b5, IOException -> 0x008f, all -> 0x0089 }
                java.lang.String r11 = "UTF-8"
                java.lang.Boolean r12 = java.lang.Boolean.valueOf(r6)     // Catch:{ IllegalArgumentException -> 0x00db, IllegalStateException -> 0x00b5, IOException -> 0x008f, all -> 0x0089 }
                r10.startDocument(r11, r12)     // Catch:{ IllegalArgumentException -> 0x00db, IllegalStateException -> 0x00b5, IOException -> 0x008f, all -> 0x0089 }
                r10.startTag(r9, r2)     // Catch:{ IllegalArgumentException -> 0x00db, IllegalStateException -> 0x00b5, IOException -> 0x008f, all -> 0x0089 }
                int r11 = r5.size()     // Catch:{ IllegalArgumentException -> 0x00db, IllegalStateException -> 0x00b5, IOException -> 0x008f, all -> 0x0089 }
                r12 = 0
            L_0x0036:
                if (r12 >= r11) goto L_0x0070
                java.lang.Object r13 = r5.remove(r4)     // Catch:{ IllegalArgumentException -> 0x00db, IllegalStateException -> 0x00b5, IOException -> 0x008f, all -> 0x0089 }
                android.support.v7.widget.ActivityChooserModel$HistoricalRecord r13 = (android.support.p004v7.widget.ActivityChooserModel.HistoricalRecord) r13     // Catch:{ IllegalArgumentException -> 0x00db, IllegalStateException -> 0x00b5, IOException -> 0x008f, all -> 0x0089 }
                r10.startTag(r9, r0)     // Catch:{ IllegalArgumentException -> 0x00db, IllegalStateException -> 0x00b5, IOException -> 0x008f, all -> 0x0089 }
                java.lang.String r14 = "activity"
                android.content.ComponentName r15 = r13.activity     // Catch:{ IllegalArgumentException -> 0x00db, IllegalStateException -> 0x00b5, IOException -> 0x008f, all -> 0x0089 }
                java.lang.String r15 = r15.flattenToString()     // Catch:{ IllegalArgumentException -> 0x00db, IllegalStateException -> 0x00b5, IOException -> 0x008f, all -> 0x0089 }
                r10.attribute(r9, r14, r15)     // Catch:{ IllegalArgumentException -> 0x00db, IllegalStateException -> 0x00b5, IOException -> 0x008f, all -> 0x0089 }
                java.lang.String r14 = "time"
                r16 = r5
                long r4 = r13.time     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x0085, IOException -> 0x0083 }
                java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x0085, IOException -> 0x0083 }
                r10.attribute(r9, r14, r4)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x0085, IOException -> 0x0083 }
                java.lang.String r4 = "weight"
                float r5 = r13.weight     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x0085, IOException -> 0x0083 }
                java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x0085, IOException -> 0x0083 }
                r10.attribute(r9, r4, r5)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x0085, IOException -> 0x0083 }
                r10.endTag(r9, r0)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x0085, IOException -> 0x0083 }
                int r12 = r12 + 1
                r5 = r16
                r4 = 0
                goto L_0x0036
            L_0x0070:
                r16 = r5
                r10.endTag(r9, r2)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x0085, IOException -> 0x0083 }
                r10.endDocument()     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x0085, IOException -> 0x0083 }
                android.support.v7.widget.ActivityChooserModel r0 = android.support.p004v7.widget.ActivityChooserModel.this
                r0.mCanReadHistoricalData = r6
                if (r8 == 0) goto L_0x0103
                r8.close()     // Catch:{ IOException -> 0x0101 }
                goto L_0x0100
            L_0x0083:
                r0 = move-exception
                goto L_0x0092
            L_0x0085:
                r0 = move-exception
                goto L_0x00b8
            L_0x0087:
                r0 = move-exception
                goto L_0x00de
            L_0x0089:
                r0 = move-exception
                r16 = r5
                r2 = r0
                goto L_0x0106
            L_0x008f:
                r0 = move-exception
                r16 = r5
            L_0x0092:
                java.lang.String r2 = android.support.p004v7.widget.ActivityChooserModel.LOG_TAG     // Catch:{ all -> 0x0104 }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0104 }
                r4.<init>()     // Catch:{ all -> 0x0104 }
                r4.append(r3)     // Catch:{ all -> 0x0104 }
                android.support.v7.widget.ActivityChooserModel r3 = android.support.p004v7.widget.ActivityChooserModel.this     // Catch:{ all -> 0x0104 }
                java.lang.String r3 = r3.mHistoryFileName     // Catch:{ all -> 0x0104 }
                r4.append(r3)     // Catch:{ all -> 0x0104 }
                java.lang.String r3 = r4.toString()     // Catch:{ all -> 0x0104 }
                android.util.Log.e(r2, r3, r0)     // Catch:{ all -> 0x0104 }
                android.support.v7.widget.ActivityChooserModel r0 = android.support.p004v7.widget.ActivityChooserModel.this
                r0.mCanReadHistoricalData = r6
                if (r8 == 0) goto L_0x0103
                r8.close()     // Catch:{ IOException -> 0x0101 }
                goto L_0x0100
            L_0x00b5:
                r0 = move-exception
                r16 = r5
            L_0x00b8:
                java.lang.String r2 = android.support.p004v7.widget.ActivityChooserModel.LOG_TAG     // Catch:{ all -> 0x0104 }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0104 }
                r4.<init>()     // Catch:{ all -> 0x0104 }
                r4.append(r3)     // Catch:{ all -> 0x0104 }
                android.support.v7.widget.ActivityChooserModel r3 = android.support.p004v7.widget.ActivityChooserModel.this     // Catch:{ all -> 0x0104 }
                java.lang.String r3 = r3.mHistoryFileName     // Catch:{ all -> 0x0104 }
                r4.append(r3)     // Catch:{ all -> 0x0104 }
                java.lang.String r3 = r4.toString()     // Catch:{ all -> 0x0104 }
                android.util.Log.e(r2, r3, r0)     // Catch:{ all -> 0x0104 }
                android.support.v7.widget.ActivityChooserModel r0 = android.support.p004v7.widget.ActivityChooserModel.this
                r0.mCanReadHistoricalData = r6
                if (r8 == 0) goto L_0x0103
                r8.close()     // Catch:{ IOException -> 0x0101 }
                goto L_0x0100
            L_0x00db:
                r0 = move-exception
                r16 = r5
            L_0x00de:
                java.lang.String r2 = android.support.p004v7.widget.ActivityChooserModel.LOG_TAG     // Catch:{ all -> 0x0104 }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0104 }
                r4.<init>()     // Catch:{ all -> 0x0104 }
                r4.append(r3)     // Catch:{ all -> 0x0104 }
                android.support.v7.widget.ActivityChooserModel r3 = android.support.p004v7.widget.ActivityChooserModel.this     // Catch:{ all -> 0x0104 }
                java.lang.String r3 = r3.mHistoryFileName     // Catch:{ all -> 0x0104 }
                r4.append(r3)     // Catch:{ all -> 0x0104 }
                java.lang.String r3 = r4.toString()     // Catch:{ all -> 0x0104 }
                android.util.Log.e(r2, r3, r0)     // Catch:{ all -> 0x0104 }
                android.support.v7.widget.ActivityChooserModel r0 = android.support.p004v7.widget.ActivityChooserModel.this
                r0.mCanReadHistoricalData = r6
                if (r8 == 0) goto L_0x0103
                r8.close()     // Catch:{ IOException -> 0x0101 }
            L_0x0100:
                goto L_0x0103
            L_0x0101:
                r0 = move-exception
                goto L_0x0100
            L_0x0103:
                return r9
            L_0x0104:
                r0 = move-exception
                r2 = r0
            L_0x0106:
                android.support.v7.widget.ActivityChooserModel r0 = android.support.p004v7.widget.ActivityChooserModel.this
                r0.mCanReadHistoricalData = r6
                if (r8 == 0) goto L_0x0111
                r8.close()     // Catch:{ IOException -> 0x0110 }
                goto L_0x0111
            L_0x0110:
                r0 = move-exception
            L_0x0111:
                throw r2
            L_0x0112:
                r0 = move-exception
                r16 = r5
                java.lang.String r2 = android.support.p004v7.widget.ActivityChooserModel.LOG_TAG
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                r4.append(r3)
                r4.append(r7)
                java.lang.String r3 = r4.toString()
                android.util.Log.e(r2, r3, r0)
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.widget.ActivityChooserModel.PersistHistoryAsyncTask.doInBackground(java.lang.Object[]):java.lang.Void");
        }
    }
}
