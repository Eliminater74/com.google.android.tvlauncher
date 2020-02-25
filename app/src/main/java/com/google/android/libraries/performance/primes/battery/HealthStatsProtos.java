package com.google.android.libraries.performance.primes.battery;

import android.os.health.HealthStats;
import android.os.health.TimerStat;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.google.protobuf.MessageLite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import logs.proto.wireless.performance.mobile.BatteryMetric;

final class HealthStatsProtos {
    private HealthStatsProtos() {
    }

    private static BatteryMetric.HashedString hashedString(String name) {
        return (BatteryMetric.HashedString) BatteryMetric.HashedString.newBuilder().setUnhashedName(name).build();
    }

    /* access modifiers changed from: private */
    @Nullable
    public static BatteryMetric.Timer timer(@Nullable String name, TimerStat timer) {
        BatteryMetric.Timer.Builder retBuilder = BatteryMetric.Timer.newBuilder().setCount(timer.getCount()).setDurationMs(timer.getTime());
        if (retBuilder.getCount() < 0) {
            retBuilder.setCount(0);
        }
        if (name != null) {
            retBuilder.setName(hashedString(name));
        }
        if (retBuilder.getCount() == 0 && retBuilder.getDurationMs() == 0) {
            return null;
        }
        return (BatteryMetric.Timer) retBuilder.build();
    }

    @Nullable
    @VisibleForTesting
    static BatteryMetric.Timer subtract(@Nullable BatteryMetric.Timer a, @Nullable BatteryMetric.Timer b) {
        if (a == null || b == null) {
            return a;
        }
        int count = a.getCount() - b.getCount();
        long duration = a.getDurationMs() - b.getDurationMs();
        if (count == 0 && duration == 0) {
            return null;
        }
        return (BatteryMetric.Timer) BatteryMetric.Timer.newBuilder().setName(a.getName()).setCount(count).setDurationMs(duration).build();
    }

    /* access modifiers changed from: private */
    @Nullable
    public static BatteryMetric.Counter counter(String name, int count) {
        if (count == 0) {
            return null;
        }
        BatteryMetric.Counter.Builder retBuilder = BatteryMetric.Counter.newBuilder().setCount(count);
        if (name != null) {
            retBuilder.setName(hashedString(name));
        }
        return (BatteryMetric.Counter) retBuilder.build();
    }

    @Nullable
    @VisibleForTesting
    static BatteryMetric.Counter subtract(@Nullable BatteryMetric.Counter a, @Nullable BatteryMetric.Counter b) {
        if (a == null || b == null) {
            return a;
        }
        if (!a.hasCount()) {
            return null;
        }
        BatteryMetric.Counter.Builder retBuilder = BatteryMetric.Counter.newBuilder().setName(a.getName());
        int count = a.getCount() - b.getCount();
        if (count == 0) {
            return null;
        }
        return (BatteryMetric.Counter) retBuilder.setCount(count).build();
    }

    @Nullable
    @VisibleForTesting
    static BatteryMetric.ProcessHealthProto processHealthProto(String name, HealthStats stats) {
        BatteryMetric.ProcessHealthProto.Builder result = BatteryMetric.ProcessHealthProto.newBuilder();
        long measurement = getMeasurement(stats, 30001);
        if (measurement != 0) {
            result.setUserTimeMs(measurement);
        }
        long measurement2 = getMeasurement(stats, 30002);
        if (measurement2 != 0) {
            result.setSystemTimeMs(measurement2);
        }
        long measurement3 = getMeasurement(stats, 30003);
        if (measurement3 != 0) {
            result.setStartsCount(measurement3);
        }
        long measurement4 = getMeasurement(stats, 30004);
        if (measurement4 != 0) {
            result.setCrashesCount(measurement4);
        }
        long measurement5 = getMeasurement(stats, 30005);
        if (measurement5 != 0) {
            result.setAnrCount(measurement5);
        }
        long measurement6 = getMeasurement(stats, 30006);
        if (measurement6 != 0) {
            result.setForegroundMs(measurement6);
        }
        if (name != null) {
            result.setName(hashedString(name));
        }
        BatteryMetric.ProcessHealthProto ret = (BatteryMetric.ProcessHealthProto) result.build();
        if (isZero(ret)) {
            return null;
        }
        return ret;
    }

    @Nullable
    static BatteryMetric.ProcessHealthProto subtract(@Nullable BatteryMetric.ProcessHealthProto a, @Nullable BatteryMetric.ProcessHealthProto b) {
        if (a == null || b == null) {
            return a;
        }
        BatteryMetric.ProcessHealthProto.Builder result = BatteryMetric.ProcessHealthProto.newBuilder();
        if (a.hasUserTimeMs()) {
            long value = a.getUserTimeMs() - b.getUserTimeMs();
            if (value != 0) {
                result.setUserTimeMs(value);
            }
        }
        if (a.hasSystemTimeMs()) {
            long value2 = a.getSystemTimeMs() - b.getSystemTimeMs();
            if (value2 != 0) {
                result.setSystemTimeMs(value2);
            }
        }
        if (a.hasStartsCount()) {
            long value3 = a.getStartsCount() - b.getStartsCount();
            if (value3 != 0) {
                result.setStartsCount(value3);
            }
        }
        if (a.hasCrashesCount()) {
            long value4 = a.getCrashesCount() - b.getCrashesCount();
            if (value4 != 0) {
                result.setCrashesCount(value4);
            }
        }
        if (a.hasAnrCount()) {
            long value5 = a.getAnrCount() - b.getAnrCount();
            if (value5 != 0) {
                result.setAnrCount(value5);
            }
        }
        if (a.hasForegroundMs()) {
            long value6 = a.getForegroundMs() - b.getForegroundMs();
            if (value6 != 0) {
                result.setForegroundMs(value6);
            }
        }
        result.setName(a.getName());
        BatteryMetric.ProcessHealthProto ret = (BatteryMetric.ProcessHealthProto) result.build();
        if (isZero(ret)) {
            return null;
        }
        return ret;
    }

    static boolean isZero(BatteryMetric.ProcessHealthProto proto) {
        return proto == null || (proto.getUserTimeMs() <= 0 && proto.getSystemTimeMs() <= 0 && proto.getStartsCount() <= 0 && proto.getCrashesCount() <= 0 && proto.getAnrCount() <= 0 && proto.getForegroundMs() <= 0);
    }

    @Nullable
    @VisibleForTesting
    static BatteryMetric.PackageHealthProto packageHealthProto(String name, HealthStats stats) {
        BatteryMetric.PackageHealthProto.Builder result = BatteryMetric.PackageHealthProto.newBuilder();
        result.addAllStatsServices(ServiceHealthProtoOps.INSTANCE.convert(getStatsMap(stats, 40001)));
        result.addAllWakeupAlarmsCount(CounterOps.INSTANCE.convert(getMeasurementsMap(stats, 40002)));
        if (name != null) {
            result.setName(hashedString(name));
        }
        BatteryMetric.PackageHealthProto ret = (BatteryMetric.PackageHealthProto) result.build();
        if (isZero(ret)) {
            return null;
        }
        return ret;
    }

    @Nullable
    static BatteryMetric.PackageHealthProto subtract(@Nullable BatteryMetric.PackageHealthProto a, @Nullable BatteryMetric.PackageHealthProto b) {
        if (a == null || b == null) {
            return a;
        }
        BatteryMetric.PackageHealthProto.Builder result = BatteryMetric.PackageHealthProto.newBuilder();
        result.addAllStatsServices(ServiceHealthProtoOps.INSTANCE.subtract(a.getStatsServicesList(), b.getStatsServicesList()));
        result.addAllWakeupAlarmsCount(CounterOps.INSTANCE.subtract(a.getWakeupAlarmsCountList(), b.getWakeupAlarmsCountList()));
        result.setName(a.getName());
        BatteryMetric.PackageHealthProto ret = (BatteryMetric.PackageHealthProto) result.build();
        if (isZero(ret)) {
            return null;
        }
        return ret;
    }

    static boolean isZero(BatteryMetric.PackageHealthProto proto) {
        return proto == null || (proto.getStatsServicesCount() == 0 && proto.getWakeupAlarmsCountCount() == 0);
    }

    @Nullable
    @VisibleForTesting
    static BatteryMetric.ServiceHealthProto serviceHealthProto(String name, HealthStats stats) {
        BatteryMetric.ServiceHealthProto.Builder result = BatteryMetric.ServiceHealthProto.newBuilder();
        int count = (int) getMeasurement(stats, 50001);
        if (count != 0) {
            result.setStartServiceCount(count);
        }
        int count2 = (int) getMeasurement(stats, 50002);
        if (count2 != 0) {
            result.setLaunchCount(count2);
        }
        if (name != null) {
            result.setName(hashedString(name));
        }
        BatteryMetric.ServiceHealthProto ret = (BatteryMetric.ServiceHealthProto) result.build();
        if (isZero(ret)) {
            return null;
        }
        return ret;
    }

    @Nullable
    static BatteryMetric.ServiceHealthProto subtract(@Nullable BatteryMetric.ServiceHealthProto a, @Nullable BatteryMetric.ServiceHealthProto b) {
        int value;
        int value2;
        if (a == null || b == null) {
            return a;
        }
        BatteryMetric.ServiceHealthProto.Builder result = BatteryMetric.ServiceHealthProto.newBuilder();
        if (a.hasStartServiceCount() && (value2 = a.getStartServiceCount() - b.getStartServiceCount()) != 0) {
            result.setStartServiceCount(value2);
        }
        if (!(a.hasLaunchCount() == 0 || (value = a.getLaunchCount() - b.getLaunchCount()) == 0)) {
            result.setLaunchCount(value);
        }
        result.setName(a.getName());
        BatteryMetric.ServiceHealthProto ret = (BatteryMetric.ServiceHealthProto) result.build();
        if (isZero(ret)) {
            return null;
        }
        return ret;
    }

    static boolean isZero(BatteryMetric.ServiceHealthProto proto) {
        return proto == null || (((long) proto.getStartServiceCount()) <= 0 && ((long) proto.getLaunchCount()) <= 0);
    }

    static BatteryMetric.UidHealthProto convert(@Nullable HealthStats stats) {
        BatteryMetric.UidHealthProto.Builder result = BatteryMetric.UidHealthProto.newBuilder();
        long measurement = getMeasurement(stats, 10001);
        if (measurement != 0) {
            result.setRealtimeBatteryMs(measurement);
        }
        long measurement2 = getMeasurement(stats, 10002);
        if (measurement2 != 0) {
            result.setUptimeBatteryMs(measurement2);
        }
        long measurement3 = getMeasurement(stats, 10003);
        if (measurement3 != 0) {
            result.setRealtimeScreenOffBatteryMs(measurement3);
        }
        long measurement4 = getMeasurement(stats, 10004);
        if (measurement4 != 0) {
            result.setUptimeScreenOffBatteryMs(measurement4);
        }
        result.addAllWakelocksFull(getTimers(stats, 10005));
        result.addAllWakelocksPartial(getTimers(stats, 10006));
        result.addAllWakelocksWindow(getTimers(stats, 10007));
        result.addAllWakelocksDraw(getTimers(stats, 10008));
        result.addAllSyncs(getTimers(stats, 10009));
        result.addAllJobs(getTimers(stats, 10010));
        BatteryMetric.Timer timer = getTimer(stats, 10011);
        if (timer != null) {
            result.setGpsSensor(timer);
        }
        result.addAllSensors(getTimers(stats, 10012));
        result.addAllStatsProcesses(ProcessHealthProtoOps.INSTANCE.convert(getStatsMap(stats, 10014)));
        result.addAllStatsPackages(PackageHealthProtoOps.INSTANCE.convert(getStatsMap(stats, 10015)));
        long measurement5 = getMeasurement(stats, 10016);
        if (measurement5 != 0) {
            result.setWifiIdleMs(measurement5);
        }
        long measurement6 = getMeasurement(stats, 10017);
        if (measurement6 != 0) {
            result.setWifiRxMs(measurement6);
        }
        long measurement7 = getMeasurement(stats, 10018);
        if (measurement7 != 0) {
            result.setWifiTxMs(measurement7);
        }
        long measurement8 = getMeasurement(stats, 10019);
        if (measurement8 != 0) {
            result.setWifiPowerMams(measurement8);
        }
        long measurement9 = getMeasurement(stats, 10020);
        if (measurement9 != 0) {
            result.setBluetoothIdleMs(measurement9);
        }
        long measurement10 = getMeasurement(stats, 10021);
        if (measurement10 != 0) {
            result.setBluetoothRxMs(measurement10);
        }
        long measurement11 = getMeasurement(stats, 10022);
        if (measurement11 != 0) {
            result.setBluetoothTxMs(measurement11);
        }
        long measurement12 = getMeasurement(stats, 10023);
        if (measurement12 != 0) {
            result.setBluetoothPowerMams(measurement12);
        }
        long measurement13 = getMeasurement(stats, 10024);
        if (measurement13 != 0) {
            result.setMobileIdleMs(measurement13);
        }
        long measurement14 = getMeasurement(stats, 10025);
        if (measurement14 != 0) {
            result.setMobileRxMs(measurement14);
        }
        long measurement15 = getMeasurement(stats, 10026);
        if (measurement15 != 0) {
            result.setMobileTxMs(measurement15);
        }
        long measurement16 = getMeasurement(stats, 10027);
        if (measurement16 != 0) {
            result.setMobilePowerMams(measurement16);
        }
        long measurement17 = getMeasurement(stats, 10028);
        if (measurement17 != 0) {
            result.setWifiRunningMs(measurement17);
        }
        long measurement18 = getMeasurement(stats, 10029);
        if (measurement18 != 0) {
            result.setWifiFullLockMs(measurement18);
        }
        BatteryMetric.Timer timer2 = getTimer(stats, 10030);
        if (timer2 != null) {
            result.setWifiScan(timer2);
        }
        long measurement19 = getMeasurement(stats, 10031);
        if (measurement19 != 0) {
            result.setWifiMulticastMs(measurement19);
        }
        BatteryMetric.Timer timer3 = getTimer(stats, 10032);
        if (timer3 != null) {
            result.setAudio(timer3);
        }
        BatteryMetric.Timer timer4 = getTimer(stats, 10033);
        if (timer4 != null) {
            result.setVideo(timer4);
        }
        BatteryMetric.Timer timer5 = getTimer(stats, 10034);
        if (timer5 != null) {
            result.setFlashlight(timer5);
        }
        BatteryMetric.Timer timer6 = getTimer(stats, 10035);
        if (timer6 != null) {
            result.setCamera(timer6);
        }
        BatteryMetric.Timer timer7 = getTimer(stats, 10036);
        if (timer7 != null) {
            result.setForegroundActivity(timer7);
        }
        BatteryMetric.Timer timer8 = getTimer(stats, 10037);
        if (timer8 != null) {
            result.setBluetoothScan(timer8);
        }
        BatteryMetric.Timer timer9 = getTimer(stats, 10038);
        if (timer9 != null) {
            result.setProcessStateTopMs(timer9);
        }
        BatteryMetric.Timer timer10 = getTimer(stats, 10039);
        if (timer10 != null) {
            result.setProcessStateForegroundServiceMs(timer10);
        }
        BatteryMetric.Timer timer11 = getTimer(stats, 10040);
        if (timer11 != null) {
            result.setProcessStateTopSleepingMs(timer11);
        }
        BatteryMetric.Timer timer12 = getTimer(stats, 10041);
        if (timer12 != null) {
            result.setProcessStateForegroundMs(timer12);
        }
        BatteryMetric.Timer timer13 = getTimer(stats, 10042);
        if (timer13 != null) {
            result.setProcessStateBackgroundMs(timer13);
        }
        BatteryMetric.Timer timer14 = getTimer(stats, 10043);
        if (timer14 != null) {
            result.setProcessStateCachedMs(timer14);
        }
        BatteryMetric.Timer timer15 = getTimer(stats, 10044);
        if (timer15 != null) {
            result.setVibrator(timer15);
        }
        long measurement20 = getMeasurement(stats, 10045);
        if (measurement20 != 0) {
            result.setOtherUserActivityCount(measurement20);
        }
        long measurement21 = getMeasurement(stats, 10046);
        if (measurement21 != 0) {
            result.setButtonUserActivityCount(measurement21);
        }
        long measurement22 = getMeasurement(stats, 10047);
        if (measurement22 != 0) {
            result.setTouchUserActivityCount(measurement22);
        }
        long measurement23 = getMeasurement(stats, 10048);
        if (measurement23 != 0) {
            result.setMobileRxBytes(measurement23);
        }
        long measurement24 = getMeasurement(stats, 10049);
        if (measurement24 != 0) {
            result.setMobileTxBytes(measurement24);
        }
        long measurement25 = getMeasurement(stats, 10050);
        if (measurement25 != 0) {
            result.setWifiRxBytes(measurement25);
        }
        long measurement26 = getMeasurement(stats, 10051);
        if (measurement26 != 0) {
            result.setWifiTxBytes(measurement26);
        }
        long measurement27 = getMeasurement(stats, 10052);
        if (measurement27 != 0) {
            result.setBluetoothRxBytes(measurement27);
        }
        long measurement28 = getMeasurement(stats, 10053);
        if (measurement28 != 0) {
            result.setBluetoothTxBytes(measurement28);
        }
        long measurement29 = getMeasurement(stats, 10054);
        if (measurement29 != 0) {
            result.setMobileRxPackets(measurement29);
        }
        long measurement30 = getMeasurement(stats, 10055);
        if (measurement30 != 0) {
            result.setMobileTxPackets(measurement30);
        }
        long measurement31 = getMeasurement(stats, 10056);
        if (measurement31 != 0) {
            result.setWifiRxPackets(measurement31);
        }
        long measurement32 = getMeasurement(stats, 10057);
        if (measurement32 != 0) {
            result.setWifiTxPackets(measurement32);
        }
        long measurement33 = getMeasurement(stats, 10058);
        if (measurement33 != 0) {
            result.setBluetoothRxPackets(measurement33);
        }
        long measurement34 = getMeasurement(stats, 10059);
        if (measurement34 != 0) {
            result.setBluetoothTxPackets(measurement34);
        }
        BatteryMetric.Timer timer16 = getTimer(stats, 10061);
        if (timer16 != null) {
            result.setMobileRadioActive(timer16);
        }
        long measurement35 = getMeasurement(stats, 10062);
        if (measurement35 != 0) {
            result.setUserCpuTimeMs(measurement35);
        }
        long measurement36 = getMeasurement(stats, 10063);
        if (measurement36 != 0) {
            result.setSystemCpuTimeMs(measurement36);
        }
        long measurement37 = getMeasurement(stats, 10064);
        if (measurement37 != 0) {
            result.setCpuPowerMams(measurement37);
        }
        return (BatteryMetric.UidHealthProto) result.build();
    }

    static void hashNames(BatteryMetric.UidHealthProto.Builder proto, HashingNameSanitizer hashingNameSanitizer) {
        List<BatteryMetric.Timer> wakelocksFullList = proto.getWakelocksFullList();
        for (int i = 0; i < proto.getWakelocksFullCount(); i++) {
            proto.setWakelocksFull(i, hashingNameSanitizer.hashRawTimerName(HashingNameSanitizer.NameType.WAKELOCK, proto.getWakelocksFull(i)));
        }
        List<BatteryMetric.Timer> timers = proto.getWakelocksPartialList();
        for (int i2 = 0; i2 < proto.getWakelocksPartialCount(); i2++) {
            proto.setWakelocksPartial(i2, hashingNameSanitizer.hashRawTimerName(HashingNameSanitizer.NameType.WAKELOCK, proto.getWakelocksPartial(i2)));
        }
        List<BatteryMetric.Timer> timers2 = proto.getWakelocksWindowList();
        for (int i3 = 0; i3 < proto.getWakelocksWindowCount(); i3++) {
            proto.setWakelocksWindow(i3, hashingNameSanitizer.hashRawTimerName(HashingNameSanitizer.NameType.WAKELOCK, proto.getWakelocksWindow(i3)));
        }
        List<BatteryMetric.Timer> timers3 = proto.getWakelocksDrawList();
        for (int i4 = 0; i4 < proto.getWakelocksDrawCount(); i4++) {
            proto.setWakelocksDraw(i4, hashingNameSanitizer.hashRawTimerName(HashingNameSanitizer.NameType.WAKELOCK, proto.getWakelocksDraw(i4)));
        }
        List<BatteryMetric.Timer> timers4 = proto.getSyncsList();
        for (int i5 = 0; i5 < proto.getSyncsCount(); i5++) {
            proto.setSyncs(i5, hashingNameSanitizer.hashRawTimerName(HashingNameSanitizer.NameType.SYNC, proto.getSyncs(i5)));
        }
        List<BatteryMetric.Timer> timers5 = proto.getJobsList();
        for (int i6 = 0; i6 < proto.getJobsCount(); i6++) {
            proto.setJobs(i6, hashingNameSanitizer.hashRawTimerName(HashingNameSanitizer.NameType.JOB, proto.getJobs(i6)));
        }
        List<BatteryMetric.Timer> timers6 = proto.getSensorsList();
        for (int i7 = 0; i7 < proto.getSensorsCount(); i7++) {
            proto.setSensors(i7, hashingNameSanitizer.hashRawTimerName(HashingNameSanitizer.NameType.SENSOR, proto.getSensors(i7)));
        }
    }

    static void sanitizeHashedNames(BatteryMetric.UidHealthProto.Builder proto, HashingNameSanitizer hashingNameSanitizer) {
        List<BatteryMetric.Timer> wakelocksFullList = proto.getWakelocksFullList();
        for (int i = 0; i < proto.getWakelocksFullCount(); i++) {
            proto.setWakelocksFull(i, hashingNameSanitizer.sanitizeHashedTimerName(HashingNameSanitizer.NameType.WAKELOCK, proto.getWakelocksFull(i)));
        }
        List<BatteryMetric.Timer> timers = proto.getWakelocksPartialList();
        for (int i2 = 0; i2 < proto.getWakelocksPartialCount(); i2++) {
            proto.setWakelocksPartial(i2, hashingNameSanitizer.sanitizeHashedTimerName(HashingNameSanitizer.NameType.WAKELOCK, proto.getWakelocksPartial(i2)));
        }
        List<BatteryMetric.Timer> timers2 = proto.getWakelocksWindowList();
        for (int i3 = 0; i3 < proto.getWakelocksWindowCount(); i3++) {
            proto.setWakelocksWindow(i3, hashingNameSanitizer.sanitizeHashedTimerName(HashingNameSanitizer.NameType.WAKELOCK, proto.getWakelocksWindow(i3)));
        }
        List<BatteryMetric.Timer> timers3 = proto.getWakelocksDrawList();
        for (int i4 = 0; i4 < proto.getWakelocksDrawCount(); i4++) {
            proto.setWakelocksDraw(i4, hashingNameSanitizer.sanitizeHashedTimerName(HashingNameSanitizer.NameType.WAKELOCK, proto.getWakelocksDraw(i4)));
        }
        List<BatteryMetric.Timer> timers4 = proto.getSyncsList();
        for (int i5 = 0; i5 < proto.getSyncsCount(); i5++) {
            proto.setSyncs(i5, hashingNameSanitizer.sanitizeHashedTimerName(HashingNameSanitizer.NameType.SYNC, proto.getSyncs(i5)));
        }
        List<BatteryMetric.Timer> timers5 = proto.getJobsList();
        for (int i6 = 0; i6 < proto.getJobsCount(); i6++) {
            proto.setJobs(i6, hashingNameSanitizer.sanitizeHashedTimerName(HashingNameSanitizer.NameType.JOB, proto.getJobs(i6)));
        }
        List<BatteryMetric.Timer> timers6 = proto.getSensorsList();
        for (int i7 = 0; i7 < proto.getSensorsCount(); i7++) {
            proto.setSensors(i7, hashingNameSanitizer.sanitizeHashedTimerName(HashingNameSanitizer.NameType.SENSOR, proto.getSensors(i7)));
        }
    }

    @Nullable
    static BatteryMetric.UidHealthProto subtract(@Nullable BatteryMetric.UidHealthProto a, @Nullable BatteryMetric.UidHealthProto b) {
        if (a == null || b == null) {
            return a;
        }
        BatteryMetric.UidHealthProto.Builder result = BatteryMetric.UidHealthProto.newBuilder();
        if (a.hasRealtimeBatteryMs()) {
            long value = a.getRealtimeBatteryMs() - b.getRealtimeBatteryMs();
            if (value != 0) {
                result.setRealtimeBatteryMs(value);
            }
        }
        if (a.hasUptimeBatteryMs()) {
            long value2 = a.getUptimeBatteryMs() - b.getUptimeBatteryMs();
            if (value2 != 0) {
                result.setUptimeBatteryMs(value2);
            }
        }
        if (a.hasRealtimeScreenOffBatteryMs()) {
            long value3 = a.getRealtimeScreenOffBatteryMs() - b.getRealtimeScreenOffBatteryMs();
            if (value3 != 0) {
                result.setRealtimeScreenOffBatteryMs(value3);
            }
        }
        if (a.hasUptimeScreenOffBatteryMs()) {
            long value4 = a.getUptimeScreenOffBatteryMs() - b.getUptimeScreenOffBatteryMs();
            if (value4 != 0) {
                result.setUptimeScreenOffBatteryMs(value4);
            }
        }
        result.addAllWakelocksFull(TimerOps.INSTANCE.subtract(a.getWakelocksFullList(), b.getWakelocksFullList()));
        result.addAllWakelocksPartial(TimerOps.INSTANCE.subtract(a.getWakelocksPartialList(), b.getWakelocksPartialList()));
        result.addAllWakelocksWindow(TimerOps.INSTANCE.subtract(a.getWakelocksWindowList(), b.getWakelocksWindowList()));
        result.addAllWakelocksDraw(TimerOps.INSTANCE.subtract(a.getWakelocksDrawList(), b.getWakelocksDrawList()));
        result.addAllSyncs(TimerOps.INSTANCE.subtract(a.getSyncsList(), b.getSyncsList()));
        result.addAllJobs(TimerOps.INSTANCE.subtract(a.getJobsList(), b.getJobsList()));
        BatteryMetric.Timer timer = subtract(a.hasGpsSensor() ? a.getGpsSensor() : null, b.hasGpsSensor() ? b.getGpsSensor() : null);
        if (timer != null) {
            result.setGpsSensor(timer);
        }
        result.addAllSensors(TimerOps.INSTANCE.subtract(a.getSensorsList(), b.getSensorsList()));
        result.addAllStatsProcesses(ProcessHealthProtoOps.INSTANCE.subtract(a.getStatsProcessesList(), b.getStatsProcessesList()));
        result.addAllStatsPackages(PackageHealthProtoOps.INSTANCE.subtract(a.getStatsPackagesList(), b.getStatsPackagesList()));
        if (a.hasWifiIdleMs()) {
            long value5 = a.getWifiIdleMs() - b.getWifiIdleMs();
            if (value5 != 0) {
                result.setWifiIdleMs(value5);
            }
        }
        if (a.hasWifiRxMs()) {
            long value6 = a.getWifiRxMs() - b.getWifiRxMs();
            if (value6 != 0) {
                result.setWifiRxMs(value6);
            }
        }
        if (a.hasWifiTxMs()) {
            long value7 = a.getWifiTxMs() - b.getWifiTxMs();
            if (value7 != 0) {
                result.setWifiTxMs(value7);
            }
        }
        if (a.hasWifiPowerMams()) {
            long value8 = a.getWifiPowerMams() - b.getWifiPowerMams();
            if (value8 != 0) {
                result.setWifiPowerMams(value8);
            }
        }
        if (a.hasBluetoothIdleMs()) {
            long value9 = a.getBluetoothIdleMs() - b.getBluetoothIdleMs();
            if (value9 != 0) {
                result.setBluetoothIdleMs(value9);
            }
        }
        if (a.hasBluetoothRxMs()) {
            long value10 = a.getBluetoothRxMs() - b.getBluetoothRxMs();
            if (value10 != 0) {
                result.setBluetoothRxMs(value10);
            }
        }
        if (a.hasBluetoothTxMs()) {
            long value11 = a.getBluetoothTxMs() - b.getBluetoothTxMs();
            if (value11 != 0) {
                result.setBluetoothTxMs(value11);
            }
        }
        if (a.hasBluetoothPowerMams()) {
            long value12 = a.getBluetoothPowerMams() - b.getBluetoothPowerMams();
            if (value12 != 0) {
                result.setBluetoothPowerMams(value12);
            }
        }
        if (a.hasMobileIdleMs()) {
            long value13 = a.getMobileIdleMs() - b.getMobileIdleMs();
            if (value13 != 0) {
                result.setMobileIdleMs(value13);
            }
        }
        if (a.hasMobileRxMs()) {
            long value14 = a.getMobileRxMs() - b.getMobileRxMs();
            if (value14 != 0) {
                result.setMobileRxMs(value14);
            }
        }
        if (a.hasMobileTxMs()) {
            long value15 = a.getMobileTxMs() - b.getMobileTxMs();
            if (value15 != 0) {
                result.setMobileTxMs(value15);
            }
        }
        if (a.hasMobilePowerMams()) {
            long value16 = a.getMobilePowerMams() - b.getMobilePowerMams();
            if (value16 != 0) {
                result.setMobilePowerMams(value16);
            }
        }
        if (a.hasWifiRunningMs()) {
            long value17 = a.getWifiRunningMs() - b.getWifiRunningMs();
            if (value17 != 0) {
                result.setWifiRunningMs(value17);
            }
        }
        if (a.hasWifiFullLockMs()) {
            long value18 = a.getWifiFullLockMs() - b.getWifiFullLockMs();
            if (value18 != 0) {
                result.setWifiFullLockMs(value18);
            }
        }
        BatteryMetric.Timer timer2 = subtract(a.hasWifiScan() ? a.getWifiScan() : null, b.hasWifiScan() ? b.getWifiScan() : null);
        if (timer2 != null) {
            result.setWifiScan(timer2);
        }
        if (a.hasWifiMulticastMs()) {
            long value19 = a.getWifiMulticastMs() - b.getWifiMulticastMs();
            if (value19 != 0) {
                result.setWifiMulticastMs(value19);
            }
        }
        BatteryMetric.Timer timer3 = subtract(a.hasAudio() ? a.getAudio() : null, b.hasAudio() ? b.getAudio() : null);
        if (timer3 != null) {
            result.setAudio(timer3);
        }
        BatteryMetric.Timer timer4 = subtract(a.hasVideo() ? a.getVideo() : null, b.hasVideo() ? b.getVideo() : null);
        if (timer4 != null) {
            result.setVideo(timer4);
        }
        BatteryMetric.Timer timer5 = subtract(a.hasFlashlight() ? a.getFlashlight() : null, b.hasFlashlight() ? b.getFlashlight() : null);
        if (timer5 != null) {
            result.setFlashlight(timer5);
        }
        BatteryMetric.Timer timer6 = subtract(a.hasCamera() ? a.getCamera() : null, b.hasCamera() ? b.getCamera() : null);
        if (timer6 != null) {
            result.setCamera(timer6);
        }
        BatteryMetric.Timer timer7 = subtract(a.hasForegroundActivity() ? a.getForegroundActivity() : null, b.hasForegroundActivity() ? b.getForegroundActivity() : null);
        if (timer7 != null) {
            result.setForegroundActivity(timer7);
        }
        BatteryMetric.Timer timer8 = subtract(a.hasBluetoothScan() ? a.getBluetoothScan() : null, b.hasBluetoothScan() ? b.getBluetoothScan() : null);
        if (timer8 != null) {
            result.setBluetoothScan(timer8);
        }
        BatteryMetric.Timer timer9 = subtract(a.hasProcessStateTopMs() ? a.getProcessStateTopMs() : null, b.hasProcessStateTopMs() ? b.getProcessStateTopMs() : null);
        if (timer9 != null) {
            result.setProcessStateTopMs(timer9);
        }
        BatteryMetric.Timer timer10 = subtract(a.hasProcessStateForegroundServiceMs() ? a.getProcessStateForegroundServiceMs() : null, b.hasProcessStateForegroundServiceMs() ? b.getProcessStateForegroundServiceMs() : null);
        if (timer10 != null) {
            result.setProcessStateForegroundServiceMs(timer10);
        }
        BatteryMetric.Timer timer11 = subtract(a.hasProcessStateTopSleepingMs() ? a.getProcessStateTopSleepingMs() : null, b.hasProcessStateTopSleepingMs() ? b.getProcessStateTopSleepingMs() : null);
        if (timer11 != null) {
            result.setProcessStateTopSleepingMs(timer11);
        }
        BatteryMetric.Timer timer12 = subtract(a.hasProcessStateForegroundMs() ? a.getProcessStateForegroundMs() : null, b.hasProcessStateForegroundMs() ? b.getProcessStateForegroundMs() : null);
        if (timer12 != null) {
            result.setProcessStateForegroundMs(timer12);
        }
        BatteryMetric.Timer timer13 = subtract(a.hasProcessStateBackgroundMs() ? a.getProcessStateBackgroundMs() : null, b.hasProcessStateBackgroundMs() ? b.getProcessStateBackgroundMs() : null);
        if (timer13 != null) {
            result.setProcessStateBackgroundMs(timer13);
        }
        BatteryMetric.Timer timer14 = subtract(a.hasProcessStateCachedMs() ? a.getProcessStateCachedMs() : null, b.hasProcessStateCachedMs() ? b.getProcessStateCachedMs() : null);
        if (timer14 != null) {
            result.setProcessStateCachedMs(timer14);
        }
        BatteryMetric.Timer timer15 = subtract(a.hasVibrator() ? a.getVibrator() : null, b.hasVibrator() ? b.getVibrator() : null);
        if (timer15 != null) {
            result.setVibrator(timer15);
        }
        if (a.hasOtherUserActivityCount()) {
            long value20 = a.getOtherUserActivityCount() - b.getOtherUserActivityCount();
            if (value20 != 0) {
                result.setOtherUserActivityCount(value20);
            }
        }
        if (a.hasButtonUserActivityCount()) {
            long value21 = a.getButtonUserActivityCount() - b.getButtonUserActivityCount();
            if (value21 != 0) {
                result.setButtonUserActivityCount(value21);
            }
        }
        if (a.hasTouchUserActivityCount()) {
            long value22 = a.getTouchUserActivityCount() - b.getTouchUserActivityCount();
            if (value22 != 0) {
                result.setTouchUserActivityCount(value22);
            }
        }
        if (a.hasMobileRxBytes()) {
            long value23 = a.getMobileRxBytes() - b.getMobileRxBytes();
            if (value23 != 0) {
                result.setMobileRxBytes(value23);
            }
        }
        if (a.hasMobileTxBytes()) {
            long value24 = a.getMobileTxBytes() - b.getMobileTxBytes();
            if (value24 != 0) {
                result.setMobileTxBytes(value24);
            }
        }
        if (a.hasWifiRxBytes()) {
            long value25 = a.getWifiRxBytes() - b.getWifiRxBytes();
            if (value25 != 0) {
                result.setWifiRxBytes(value25);
            }
        }
        if (a.hasWifiTxBytes()) {
            long value26 = a.getWifiTxBytes() - b.getWifiTxBytes();
            if (value26 != 0) {
                result.setWifiTxBytes(value26);
            }
        }
        if (a.hasBluetoothRxBytes()) {
            long value27 = a.getBluetoothRxBytes() - b.getBluetoothRxBytes();
            if (value27 != 0) {
                result.setBluetoothRxBytes(value27);
            }
        }
        if (a.hasBluetoothTxBytes()) {
            long value28 = a.getBluetoothTxBytes() - b.getBluetoothTxBytes();
            if (value28 != 0) {
                result.setBluetoothTxBytes(value28);
            }
        }
        if (a.hasMobileRxPackets()) {
            long value29 = a.getMobileRxPackets() - b.getMobileRxPackets();
            if (value29 != 0) {
                result.setMobileRxPackets(value29);
            }
        }
        if (a.hasMobileTxPackets()) {
            long value30 = a.getMobileTxPackets() - b.getMobileTxPackets();
            if (value30 != 0) {
                result.setMobileTxPackets(value30);
            }
        }
        if (a.hasWifiRxPackets()) {
            long value31 = a.getWifiRxPackets() - b.getWifiRxPackets();
            if (value31 != 0) {
                result.setWifiRxPackets(value31);
            }
        }
        if (a.hasWifiTxPackets()) {
            long value32 = a.getWifiTxPackets() - b.getWifiTxPackets();
            if (value32 != 0) {
                result.setWifiTxPackets(value32);
            }
        }
        if (a.hasBluetoothRxPackets()) {
            long value33 = a.getBluetoothRxPackets() - b.getBluetoothRxPackets();
            if (value33 != 0) {
                result.setBluetoothRxPackets(value33);
            }
        }
        if (a.hasBluetoothTxPackets()) {
            long value34 = a.getBluetoothTxPackets() - b.getBluetoothTxPackets();
            if (value34 != 0) {
                result.setBluetoothTxPackets(value34);
            }
        }
        BatteryMetric.Timer timer16 = subtract(a.hasMobileRadioActive() ? a.getMobileRadioActive() : null, b.hasMobileRadioActive() ? b.getMobileRadioActive() : null);
        if (timer16 != null) {
            result.setMobileRadioActive(timer16);
        }
        if (a.hasUserCpuTimeMs()) {
            long value35 = a.getUserCpuTimeMs() - b.getUserCpuTimeMs();
            if (value35 != 0) {
                result.setUserCpuTimeMs(value35);
            }
        }
        if (a.hasSystemCpuTimeMs()) {
            long value36 = a.getSystemCpuTimeMs() - b.getSystemCpuTimeMs();
            if (value36 != 0) {
                result.setSystemCpuTimeMs(value36);
            }
        }
        if (a.hasCpuPowerMams()) {
            long value37 = a.getCpuPowerMams() - b.getCpuPowerMams();
            if (value37 != 0) {
                result.setCpuPowerMams(value37);
            }
        }
        BatteryMetric.UidHealthProto ret = (BatteryMetric.UidHealthProto) result.build();
        if (isZero(ret)) {
            return null;
        }
        return ret;
    }

    static boolean isZero(BatteryMetric.UidHealthProto proto) {
        return proto == null || (proto.getRealtimeBatteryMs() <= 0 && proto.getUptimeBatteryMs() <= 0 && proto.getRealtimeScreenOffBatteryMs() <= 0 && proto.getUptimeScreenOffBatteryMs() <= 0 && proto.getWakelocksFullCount() == 0 && proto.getWakelocksPartialCount() == 0 && proto.getWakelocksWindowCount() == 0 && proto.getWakelocksDrawCount() == 0 && proto.getSyncsCount() == 0 && proto.getJobsCount() == 0 && proto.getSensorsCount() == 0 && proto.getStatsPidsCount() == 0 && proto.getStatsProcessesCount() == 0 && proto.getStatsPackagesCount() == 0 && proto.getWifiIdleMs() <= 0 && proto.getWifiRxMs() <= 0 && proto.getWifiTxMs() <= 0 && proto.getWifiPowerMams() <= 0 && proto.getBluetoothIdleMs() <= 0 && proto.getBluetoothRxMs() <= 0 && proto.getBluetoothTxMs() <= 0 && proto.getBluetoothPowerMams() <= 0 && proto.getMobileIdleMs() <= 0 && proto.getMobileRxMs() <= 0 && proto.getMobileTxMs() <= 0 && proto.getMobilePowerMams() <= 0 && proto.getWifiRunningMs() <= 0 && proto.getWifiFullLockMs() <= 0 && proto.getWifiMulticastMs() <= 0 && proto.getOtherUserActivityCount() <= 0 && proto.getButtonUserActivityCount() <= 0 && proto.getTouchUserActivityCount() <= 0 && proto.getMobileRxBytes() <= 0 && proto.getMobileTxBytes() <= 0 && proto.getWifiRxBytes() <= 0 && proto.getWifiTxBytes() <= 0 && proto.getBluetoothRxBytes() <= 0 && proto.getBluetoothTxBytes() <= 0 && proto.getMobileRxPackets() <= 0 && proto.getMobileTxPackets() <= 0 && proto.getWifiRxPackets() <= 0 && proto.getWifiTxPackets() <= 0 && proto.getBluetoothRxPackets() <= 0 && proto.getBluetoothTxPackets() <= 0 && proto.getUserCpuTimeMs() <= 0 && proto.getSystemCpuTimeMs() <= 0 && proto.getCpuPowerMams() <= 0);
    }

    private static long getMeasurement(@Nullable HealthStats stats, int key) {
        if (stats == null || !stats.hasMeasurement(key)) {
            return 0;
        }
        return stats.getMeasurement(key);
    }

    @Nullable
    private static BatteryMetric.Timer getTimer(@Nullable HealthStats stats, int key) {
        if (stats == null || !stats.hasTimer(key)) {
            return null;
        }
        return timer(null, stats.getTimer(key));
    }

    private static List<BatteryMetric.Timer> getTimers(@Nullable HealthStats stats, int key) {
        if (stats == null || !stats.hasTimers(key)) {
            return Collections.emptyList();
        }
        return TimerOps.INSTANCE.convert(stats.getTimers(key));
    }

    private static Map<String, Long> getMeasurementsMap(@Nullable HealthStats stats, int key) {
        return (stats == null || !stats.hasMeasurements(key)) ? Collections.emptyMap() : stats.getMeasurements(key);
    }

    private static Map<String, HealthStats> getStatsMap(@Nullable HealthStats stats, int key) {
        return (stats == null || !stats.hasStats(key)) ? Collections.emptyMap() : stats.getStats(key);
    }

    private static abstract class ProtoStatsOps<S, P extends MessageLite> {
        private ProtoStatsOps() {
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public abstract P convert(String str, S s);

        /* access modifiers changed from: package-private */
        public abstract String nameOf(P p);

        /* access modifiers changed from: package-private */
        @Nullable
        public abstract P subtract(@Nullable P p, @Nullable P p2);

        /* access modifiers changed from: package-private */
        @Nullable
        public P find(List<P> vals, String name) {
            for (P val : vals) {
                if (name.equals(nameOf(val))) {
                    return val;
                }
            }
            return null;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.android.libraries.performance.primes.battery.HealthStatsProtos.ProtoStatsOps.subtract(com.google.protobuf.MessageLite, com.google.protobuf.MessageLite):P
         arg types: [P, P]
         candidates:
          com.google.android.libraries.performance.primes.battery.HealthStatsProtos.ProtoStatsOps.subtract(java.util.List, java.util.List):java.util.List<P>
          com.google.android.libraries.performance.primes.battery.HealthStatsProtos.ProtoStatsOps.subtract(com.google.protobuf.MessageLite, com.google.protobuf.MessageLite):P */
        /* access modifiers changed from: package-private */
        public List<P> subtract(List<P> a, List<P> b) {
            if (a.isEmpty()) {
                return a;
            }
            ArrayList<P> ret = new ArrayList<>();
            for (P aa : a) {
                P diff = subtract((MessageLite) aa, (MessageLite) find(b, nameOf(aa)));
                if (diff != null) {
                    ret.add(diff);
                }
            }
            return ret;
        }

        /* access modifiers changed from: package-private */
        public List<P> convert(Map<String, S> statsMap) {
            P proto;
            ArrayList<P> protos = new ArrayList<>();
            for (Map.Entry<String, S> entry : statsMap.entrySet()) {
                if (!(entry.getValue() == null || (proto = convert(entry.getKey(), entry.getValue())) == null)) {
                    protos.add(proto);
                }
            }
            return protos;
        }
    }

    private static class CounterOps extends ProtoStatsOps<Long, BatteryMetric.Counter> {
        /* access modifiers changed from: private */
        public static final CounterOps INSTANCE = new CounterOps();

        private CounterOps() {
            super();
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public BatteryMetric.Counter subtract(@Nullable BatteryMetric.Counter a, @Nullable BatteryMetric.Counter b) {
            return HealthStatsProtos.subtract(a, b);
        }

        /* access modifiers changed from: package-private */
        public String nameOf(BatteryMetric.Counter val) {
            return val.getName().getUnhashedName();
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public BatteryMetric.Counter convert(String name, Long value) {
            return HealthStatsProtos.counter(name, value.intValue());
        }
    }

    private static final class TimerOps extends ProtoStatsOps<TimerStat, BatteryMetric.Timer> {
        /* access modifiers changed from: private */
        public static final TimerOps INSTANCE = new TimerOps();

        private TimerOps() {
            super();
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public BatteryMetric.Timer convert(String name, TimerStat timerStat) {
            return HealthStatsProtos.timer(name, timerStat);
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public BatteryMetric.Timer subtract(@Nullable BatteryMetric.Timer a, @Nullable BatteryMetric.Timer b) {
            return HealthStatsProtos.subtract(a, b);
        }

        /* access modifiers changed from: package-private */
        public String nameOf(BatteryMetric.Timer val) {
            if (val.getName().hasUnhashedName()) {
                return val.getName().getUnhashedName();
            }
            return Long.toHexString(val.getName().getHash());
        }
    }

    private static final class PackageHealthProtoOps extends ProtoStatsOps<HealthStats, BatteryMetric.PackageHealthProto> {
        /* access modifiers changed from: private */
        public static final PackageHealthProtoOps INSTANCE = new PackageHealthProtoOps();

        private PackageHealthProtoOps() {
            super();
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public BatteryMetric.PackageHealthProto convert(String name, HealthStats stats) {
            return HealthStatsProtos.packageHealthProto(name, stats);
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public BatteryMetric.PackageHealthProto subtract(@Nullable BatteryMetric.PackageHealthProto a, @Nullable BatteryMetric.PackageHealthProto b) {
            return HealthStatsProtos.subtract(a, b);
        }

        /* access modifiers changed from: package-private */
        public String nameOf(BatteryMetric.PackageHealthProto val) {
            return val.getName().getUnhashedName();
        }
    }

    private static final class ProcessHealthProtoOps extends ProtoStatsOps<HealthStats, BatteryMetric.ProcessHealthProto> {
        /* access modifiers changed from: private */
        public static final ProcessHealthProtoOps INSTANCE = new ProcessHealthProtoOps();

        private ProcessHealthProtoOps() {
            super();
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public BatteryMetric.ProcessHealthProto convert(String name, HealthStats stats) {
            return HealthStatsProtos.processHealthProto(name, stats);
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public BatteryMetric.ProcessHealthProto subtract(@Nullable BatteryMetric.ProcessHealthProto a, @Nullable BatteryMetric.ProcessHealthProto b) {
            return HealthStatsProtos.subtract(a, b);
        }

        /* access modifiers changed from: package-private */
        public String nameOf(BatteryMetric.ProcessHealthProto val) {
            return val.getName().getUnhashedName();
        }
    }

    private static final class ServiceHealthProtoOps extends ProtoStatsOps<HealthStats, BatteryMetric.ServiceHealthProto> {
        /* access modifiers changed from: private */
        public static final ServiceHealthProtoOps INSTANCE = new ServiceHealthProtoOps();

        private ServiceHealthProtoOps() {
            super();
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public BatteryMetric.ServiceHealthProto convert(String name, HealthStats stats) {
            return HealthStatsProtos.serviceHealthProto(name, stats);
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public BatteryMetric.ServiceHealthProto subtract(@Nullable BatteryMetric.ServiceHealthProto a, @Nullable BatteryMetric.ServiceHealthProto b) {
            return HealthStatsProtos.subtract(a, b);
        }

        /* access modifiers changed from: package-private */
        public String nameOf(BatteryMetric.ServiceHealthProto val) {
            return val.getName().getUnhashedName();
        }
    }
}
