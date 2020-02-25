package com.google.android.exoplayer2.upstream;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.p001v4.media.session.PlaybackStateCompat;
import android.util.SparseArray;

import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.EventDispatcher;
import com.google.android.exoplayer2.util.SlidingPercentile;
import com.google.android.exoplayer2.util.Util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DefaultBandwidthMeter implements BandwidthMeter, TransferListener {
    public static final Map<String, int[]> DEFAULT_INITIAL_BITRATE_COUNTRY_GROUPS = createInitialBitrateCountryGroupAssignment();
    public static final long DEFAULT_INITIAL_BITRATE_ESTIMATE = 1000000;
    public static final long[] DEFAULT_INITIAL_BITRATE_ESTIMATES_2G = {170000, 139000, 122000, 107000, 90000};
    public static final long[] DEFAULT_INITIAL_BITRATE_ESTIMATES_3G = {2100000, 1300000, 960000, 770000, 450000};
    public static final long[] DEFAULT_INITIAL_BITRATE_ESTIMATES_4G = {6000000, 3400000, 2100000, 1400000, 570000};
    public static final long[] DEFAULT_INITIAL_BITRATE_ESTIMATES_WIFI = {5400000, 3400000, 1900000, 1100000, 400000};
    public static final int DEFAULT_SLIDING_WINDOW_MAX_WEIGHT = 2000;
    private static final int BYTES_TRANSFERRED_FOR_ESTIMATE = 524288;
    private static final int ELAPSED_MILLIS_FOR_ESTIMATE = 2000;
    private final Clock clock;
    @Nullable
    private final Context context;
    private final EventDispatcher<BandwidthMeter.EventListener> eventDispatcher;
    private final SparseArray<Long> initialBitrateEstimates;
    private final SlidingPercentile slidingPercentile;
    private long bitrateEstimate;
    private long lastReportedBitrateEstimate;
    private int networkType;
    private int networkTypeOverride;
    private boolean networkTypeOverrideSet;
    private long sampleBytesTransferred;
    private long sampleStartTimeMs;
    private int streamCount;
    private long totalBytesTransferred;
    private long totalElapsedTimeMs;

    @Deprecated
    public DefaultBandwidthMeter() {
        this(null, new SparseArray(), 2000, Clock.DEFAULT, false);
    }

    private DefaultBandwidthMeter(@Nullable Context context2, SparseArray<Long> initialBitrateEstimates2, int maxWeight, Clock clock2, boolean resetOnNetworkTypeChange) {
        this.context = context2 == null ? null : context2.getApplicationContext();
        this.initialBitrateEstimates = initialBitrateEstimates2;
        this.eventDispatcher = new EventDispatcher<>();
        this.slidingPercentile = new SlidingPercentile(maxWeight);
        this.clock = clock2;
        this.networkType = context2 == null ? 0 : Util.getNetworkType(context2);
        this.bitrateEstimate = getInitialBitrateEstimateForNetworkType(this.networkType);
        if (context2 != null && resetOnNetworkTypeChange) {
            ConnectivityActionReceiver.getInstance(context2).register(this);
        }
    }

    private static Map<String, int[]> createInitialBitrateCountryGroupAssignment() {
        HashMap<String, int[]> countryGroupAssignment = new HashMap<>();
        countryGroupAssignment.put("AD", new int[]{1, 0, 0, 1});
        countryGroupAssignment.put("AE", new int[]{1, 4, 4, 4});
        countryGroupAssignment.put("AF", new int[]{4, 4, 3, 3});
        countryGroupAssignment.put("AG", new int[]{3, 2, 1, 1});
        countryGroupAssignment.put("AI", new int[]{1, 0, 1, 3});
        countryGroupAssignment.put("AL", new int[]{1, 2, 1, 1});
        countryGroupAssignment.put("AM", new int[]{2, 2, 3, 2});
        countryGroupAssignment.put("AO", new int[]{3, 4, 2, 0});
        countryGroupAssignment.put("AQ", new int[]{4, 2, 2, 2});
        countryGroupAssignment.put("AR", new int[]{2, 3, 2, 2});
        countryGroupAssignment.put("AS", new int[]{3, 3, 4, 1});
        countryGroupAssignment.put("AT", new int[]{0, 2, 0, 0});
        countryGroupAssignment.put("AU", new int[]{0, 1, 1, 1});
        countryGroupAssignment.put("AW", new int[]{1, 1, 0, 2});
        countryGroupAssignment.put("AX", new int[]{0, 2, 1, 0});
        countryGroupAssignment.put("AZ", new int[]{3, 3, 2, 2});
        countryGroupAssignment.put("BA", new int[]{1, 1, 1, 2});
        countryGroupAssignment.put("BB", new int[]{0, 1, 0, 0});
        countryGroupAssignment.put("BD", new int[]{2, 2, 3, 2});
        countryGroupAssignment.put("BE", new int[]{0, 0, 0, 1});
        countryGroupAssignment.put("BF", new int[]{4, 4, 3, 1});
        countryGroupAssignment.put("BG", new int[]{0, 1, 0, 0});
        countryGroupAssignment.put("BH", new int[]{2, 1, 3, 4});
        countryGroupAssignment.put("BI", new int[]{4, 3, 4, 4});
        countryGroupAssignment.put("BJ", new int[]{4, 3, 4, 4});
        countryGroupAssignment.put("BL", new int[]{1, 0, 2, 3});
        countryGroupAssignment.put("BM", new int[]{1, 0, 0, 0});
        countryGroupAssignment.put("BN", new int[]{4, 2, 3, 3});
        countryGroupAssignment.put("BO", new int[]{2, 2, 3, 2});
        countryGroupAssignment.put("BQ", new int[]{1, 0, 3, 4});
        countryGroupAssignment.put("BR", new int[]{2, 3, 3, 2});
        countryGroupAssignment.put("BS", new int[]{2, 0, 1, 4});
        countryGroupAssignment.put("BT", new int[]{3, 0, 2, 1});
        countryGroupAssignment.put("BW", new int[]{4, 4, 1, 2});
        countryGroupAssignment.put("BY", new int[]{0, 1, 1, 2});
        countryGroupAssignment.put("BZ", new int[]{2, 2, 3, 1});
        countryGroupAssignment.put("CA", new int[]{0, 3, 3, 3});
        countryGroupAssignment.put("CD", new int[]{4, 4, 3, 2});
        countryGroupAssignment.put("CF", new int[]{4, 3, 3, 4});
        countryGroupAssignment.put("CG", new int[]{4, 4, 4, 4});
        countryGroupAssignment.put("CH", new int[]{0, 0, 1, 1});
        countryGroupAssignment.put("CI", new int[]{3, 4, 3, 3});
        countryGroupAssignment.put("CK", new int[]{2, 4, 1, 0});
        countryGroupAssignment.put("CL", new int[]{2, 2, 2, 3});
        countryGroupAssignment.put("CM", new int[]{3, 4, 2, 1});
        countryGroupAssignment.put("CN", new int[]{2, 2, 2, 3});
        countryGroupAssignment.put("CO", new int[]{2, 3, 2, 2});
        countryGroupAssignment.put("CR", new int[]{2, 2, 4, 4});
        countryGroupAssignment.put("CU", new int[]{4, 4, 3, 1});
        countryGroupAssignment.put("CV", new int[]{2, 3, 2, 4});
        countryGroupAssignment.put("CW", new int[]{1, 0, 0, 0});
        countryGroupAssignment.put("CX", new int[]{2, 2, 2, 2});
        countryGroupAssignment.put("CY", new int[]{1, 1, 1, 1});
        countryGroupAssignment.put("CZ", new int[]{0, 1, 0, 0});
        countryGroupAssignment.put("DE", new int[]{0, 2, 2, 2});
        countryGroupAssignment.put("DJ", new int[]{3, 3, 4, 0});
        countryGroupAssignment.put("DK", new int[]{0, 0, 0, 0});
        countryGroupAssignment.put("DM", new int[]{1, 0, 0, 3});
        countryGroupAssignment.put("DO", new int[]{3, 3, 4, 4});
        countryGroupAssignment.put("DZ", new int[]{3, 3, 4, 4});
        countryGroupAssignment.put("EC", new int[]{2, 4, 4, 2});
        countryGroupAssignment.put("EE", new int[]{0, 0, 0, 0});
        countryGroupAssignment.put("EG", new int[]{3, 4, 2, 2});
        countryGroupAssignment.put("EH", new int[]{2, 0, 3, 3});
        countryGroupAssignment.put("ER", new int[]{4, 2, 2, 2});
        countryGroupAssignment.put("ES", new int[]{0, 1, 1, 1});
        countryGroupAssignment.put("ET", new int[]{4, 4, 4, 0});
        countryGroupAssignment.put("FI", new int[]{0, 0, 1, 0});
        countryGroupAssignment.put("FJ", new int[]{3, 1, 3, 3});
        countryGroupAssignment.put("FK", new int[]{4, 2, 2, 3});
        countryGroupAssignment.put("FM", new int[]{4, 2, 4, 0});
        countryGroupAssignment.put("FO", new int[]{0, 0, 0, 0});
        countryGroupAssignment.put("FR", new int[]{1, 0, 3, 1});
        countryGroupAssignment.put("GA", new int[]{3, 3, 2, 1});
        countryGroupAssignment.put("GB", new int[]{0, 1, 3, 3});
        countryGroupAssignment.put("GD", new int[]{2, 0, 4, 4});
        countryGroupAssignment.put("GE", new int[]{1, 1, 0, 3});
        countryGroupAssignment.put("GF", new int[]{1, 2, 4, 4});
        countryGroupAssignment.put("GG", new int[]{0, 0, 0, 0});
        countryGroupAssignment.put("GH", new int[]{3, 3, 3, 2});
        countryGroupAssignment.put("GI", new int[]{0, 0, 0, 1});
        countryGroupAssignment.put("GL", new int[]{2, 2, 3, 4});
        countryGroupAssignment.put("GM", new int[]{4, 3, 3, 2});
        countryGroupAssignment.put("GN", new int[]{4, 4, 4, 0});
        countryGroupAssignment.put("GP", new int[]{2, 2, 1, 3});
        countryGroupAssignment.put("GQ", new int[]{4, 3, 3, 0});
        countryGroupAssignment.put("GR", new int[]{1, 1, 0, 1});
        countryGroupAssignment.put("GT", new int[]{3, 3, 3, 4});
        countryGroupAssignment.put("GU", new int[]{1, 2, 4, 4});
        countryGroupAssignment.put("GW", new int[]{4, 4, 4, 0});
        countryGroupAssignment.put("GY", new int[]{3, 4, 1, 0});
        countryGroupAssignment.put("HK", new int[]{0, 1, 4, 4});
        countryGroupAssignment.put("HN", new int[]{3, 3, 2, 2});
        countryGroupAssignment.put("HR", new int[]{1, 0, 0, 2});
        countryGroupAssignment.put("HT", new int[]{3, 4, 4, 3});
        countryGroupAssignment.put("HU", new int[]{0, 0, 1, 0});
        countryGroupAssignment.put("ID", new int[]{3, 2, 3, 4});
        countryGroupAssignment.put("IE", new int[]{0, 0, 3, 2});
        countryGroupAssignment.put("IL", new int[]{0, 1, 2, 3});
        countryGroupAssignment.put("IM", new int[]{0, 0, 0, 1});
        countryGroupAssignment.put("IN", new int[]{2, 2, 4, 4});
        countryGroupAssignment.put("IO", new int[]{4, 4, 2, 2});
        countryGroupAssignment.put("IQ", new int[]{3, 3, 4, 4});
        countryGroupAssignment.put("IR", new int[]{1, 0, 1, 0});
        countryGroupAssignment.put("IS", new int[]{0, 0, 0, 0});
        countryGroupAssignment.put("IT", new int[]{1, 0, 1, 1});
        countryGroupAssignment.put("JE", new int[]{1, 0, 0, 1});
        countryGroupAssignment.put("JM", new int[]{3, 2, 2, 1});
        countryGroupAssignment.put("JO", new int[]{1, 1, 1, 2});
        countryGroupAssignment.put("JP", new int[]{0, 2, 2, 2});
        countryGroupAssignment.put("KE", new int[]{3, 3, 3, 3});
        countryGroupAssignment.put("KG", new int[]{1, 1, 2, 3});
        countryGroupAssignment.put("KH", new int[]{2, 0, 4, 4});
        countryGroupAssignment.put("KI", new int[]{4, 4, 4, 4});
        countryGroupAssignment.put("KM", new int[]{4, 4, 3, 3});
        countryGroupAssignment.put("KN", new int[]{1, 0, 1, 4});
        countryGroupAssignment.put("KP", new int[]{1, 2, 0, 2});
        countryGroupAssignment.put("KR", new int[]{0, 3, 0, 2});
        countryGroupAssignment.put("KW", new int[]{2, 2, 1, 2});
        countryGroupAssignment.put("KY", new int[]{1, 1, 0, 2});
        countryGroupAssignment.put("KZ", new int[]{1, 2, 2, 2});
        countryGroupAssignment.put("LA", new int[]{2, 1, 1, 0});
        countryGroupAssignment.put("LB", new int[]{3, 2, 0, 0});
        countryGroupAssignment.put("LC", new int[]{2, 1, 0, 0});
        countryGroupAssignment.put("LI", new int[]{0, 0, 2, 2});
        countryGroupAssignment.put("LK", new int[]{1, 1, 2, 2});
        countryGroupAssignment.put("LR", new int[]{3, 4, 4, 1});
        countryGroupAssignment.put("LS", new int[]{3, 3, 2, 0});
        countryGroupAssignment.put("LT", new int[]{0, 0, 0, 0});
        countryGroupAssignment.put("LU", new int[]{0, 1, 0, 0});
        countryGroupAssignment.put("LV", new int[]{0, 0, 0, 0});
        countryGroupAssignment.put("LY", new int[]{4, 3, 4, 4});
        countryGroupAssignment.put("MA", new int[]{2, 1, 2, 2});
        countryGroupAssignment.put("MC", new int[]{1, 0, 1, 0});
        countryGroupAssignment.put("MD", new int[]{1, 1, 0, 0});
        countryGroupAssignment.put("ME", new int[]{1, 2, 2, 3});
        countryGroupAssignment.put("MF", new int[]{1, 4, 2, 1});
        countryGroupAssignment.put("MG", new int[]{3, 4, 1, 3});
        countryGroupAssignment.put("MH", new int[]{4, 0, 2, 3});
        countryGroupAssignment.put("MK", new int[]{1, 0, 0, 0});
        countryGroupAssignment.put("ML", new int[]{4, 4, 4, 3});
        countryGroupAssignment.put("MM", new int[]{2, 3, 1, 2});
        countryGroupAssignment.put("MN", new int[]{2, 3, 2, 4});
        countryGroupAssignment.put("MO", new int[]{0, 0, 4, 4});
        countryGroupAssignment.put("MP", new int[]{0, 2, 4, 4});
        countryGroupAssignment.put("MQ", new int[]{1, 1, 1, 3});
        countryGroupAssignment.put("MR", new int[]{4, 4, 4, 4});
        countryGroupAssignment.put("MS", new int[]{1, 4, 0, 3});
        countryGroupAssignment.put("MT", new int[]{0, 1, 0, 0});
        countryGroupAssignment.put("MU", new int[]{2, 2, 3, 4});
        countryGroupAssignment.put("MV", new int[]{3, 2, 1, 1});
        countryGroupAssignment.put("MW", new int[]{4, 2, 1, 1});
        countryGroupAssignment.put("MX", new int[]{2, 4, 3, 2});
        countryGroupAssignment.put("MY", new int[]{2, 2, 2, 3});
        countryGroupAssignment.put("MZ", new int[]{3, 4, 2, 2});
        countryGroupAssignment.put("NA", new int[]{3, 2, 2, 1});
        countryGroupAssignment.put("NC", new int[]{2, 1, 3, 2});
        countryGroupAssignment.put("NE", new int[]{4, 4, 4, 3});
        countryGroupAssignment.put("NF", new int[]{1, 2, 2, 2});
        countryGroupAssignment.put("NG", new int[]{3, 4, 3, 2});
        countryGroupAssignment.put("NI", new int[]{3, 3, 3, 4});
        countryGroupAssignment.put("NL", new int[]{0, 2, 4, 3});
        countryGroupAssignment.put("NO", new int[]{0, 1, 0, 0});
        countryGroupAssignment.put("NP", new int[]{3, 3, 2, 2});
        countryGroupAssignment.put("NR", new int[]{4, 0, 4, 0});
        countryGroupAssignment.put("NU", new int[]{2, 2, 2, 1});
        countryGroupAssignment.put("NZ", new int[]{0, 0, 0, 1});
        countryGroupAssignment.put("OM", new int[]{2, 2, 1, 3});
        countryGroupAssignment.put("PA", new int[]{1, 3, 3, 4});
        countryGroupAssignment.put("PE", new int[]{2, 3, 4, 4});
        countryGroupAssignment.put("PF", new int[]{3, 1, 0, 1});
        countryGroupAssignment.put("PG", new int[]{4, 3, 1, 1});
        countryGroupAssignment.put("PH", new int[]{3, 0, 4, 4});
        countryGroupAssignment.put("PK", new int[]{3, 3, 3, 3});
        countryGroupAssignment.put("PL", new int[]{1, 1, 1, 3});
        countryGroupAssignment.put("PM", new int[]{0, 2, 0, 0});
        countryGroupAssignment.put("PR", new int[]{2, 1, 3, 3});
        countryGroupAssignment.put("PS", new int[]{3, 3, 1, 4});
        countryGroupAssignment.put("PT", new int[]{1, 1, 0, 1});
        countryGroupAssignment.put("PW", new int[]{2, 2, 1, 1});
        countryGroupAssignment.put("PY", new int[]{3, 1, 3, 3});
        countryGroupAssignment.put("QA", new int[]{2, 3, 0, 1});
        countryGroupAssignment.put("RE", new int[]{1, 0, 2, 2});
        countryGroupAssignment.put("RO", new int[]{0, 1, 1, 2});
        countryGroupAssignment.put("RS", new int[]{1, 2, 0, 0});
        countryGroupAssignment.put("RU", new int[]{0, 1, 1, 1});
        countryGroupAssignment.put("RW", new int[]{3, 4, 2, 4});
        countryGroupAssignment.put("SA", new int[]{2, 2, 1, 2});
        countryGroupAssignment.put("SB", new int[]{4, 4, 3, 0});
        countryGroupAssignment.put("SC", new int[]{4, 2, 0, 1});
        countryGroupAssignment.put("SD", new int[]{4, 4, 4, 2});
        countryGroupAssignment.put("SE", new int[]{0, 1, 0, 0});
        countryGroupAssignment.put("SG", new int[]{1, 2, 3, 3});
        countryGroupAssignment.put("SH", new int[]{4, 4, 2, 3});
        countryGroupAssignment.put("SI", new int[]{0, 1, 0, 1});
        countryGroupAssignment.put("SJ", new int[]{0, 0, 2, 0});
        countryGroupAssignment.put("SK", new int[]{0, 1, 0, 1});
        countryGroupAssignment.put("SL", new int[]{4, 3, 2, 4});
        countryGroupAssignment.put("SM", new int[]{0, 0, 1, 3});
        countryGroupAssignment.put("SN", new int[]{4, 4, 4, 3});
        countryGroupAssignment.put("SO", new int[]{4, 4, 4, 4});
        countryGroupAssignment.put("SR", new int[]{3, 2, 2, 4});
        countryGroupAssignment.put("SS", new int[]{4, 2, 4, 2});
        countryGroupAssignment.put("ST", new int[]{3, 4, 2, 2});
        countryGroupAssignment.put("SV", new int[]{2, 3, 3, 4});
        countryGroupAssignment.put("SX", new int[]{2, 4, 1, 0});
        countryGroupAssignment.put("SY", new int[]{4, 4, 1, 0});
        countryGroupAssignment.put("SZ", new int[]{3, 4, 2, 3});
        countryGroupAssignment.put("TC", new int[]{1, 1, 3, 1});
        countryGroupAssignment.put("TD", new int[]{4, 4, 4, 3});
        countryGroupAssignment.put("TG", new int[]{3, 3, 1, 0});
        countryGroupAssignment.put("TH", new int[]{1, 3, 4, 4});
        countryGroupAssignment.put("TJ", new int[]{4, 4, 4, 4});
        countryGroupAssignment.put("TL", new int[]{4, 2, 4, 4});
        countryGroupAssignment.put("TM", new int[]{4, 1, 2, 3});
        countryGroupAssignment.put("TN", new int[]{2, 1, 1, 1});
        countryGroupAssignment.put("TO", new int[]{3, 3, 3, 1});
        countryGroupAssignment.put("TR", new int[]{1, 2, 0, 1});
        countryGroupAssignment.put("TT", new int[]{2, 3, 1, 2});
        countryGroupAssignment.put("TV", new int[]{4, 2, 2, 4});
        countryGroupAssignment.put("TW", new int[]{0, 0, 0, 1});
        countryGroupAssignment.put("TZ", new int[]{3, 3, 4, 3});
        countryGroupAssignment.put("UA", new int[]{0, 2, 1, 2});
        countryGroupAssignment.put("UG", new int[]{4, 3, 2, 3});
        countryGroupAssignment.put("US", new int[]{0, 1, 3, 3});
        countryGroupAssignment.put("UY", new int[]{2, 2, 2, 2});
        countryGroupAssignment.put("UZ", new int[]{3, 2, 2, 2});
        countryGroupAssignment.put("VA", new int[]{1, 2, 2, 2});
        countryGroupAssignment.put("VC", new int[]{2, 1, 0, 0});
        countryGroupAssignment.put("VE", new int[]{4, 4, 4, 3});
        countryGroupAssignment.put("VG", new int[]{2, 1, 1, 2});
        countryGroupAssignment.put("VI", new int[]{1, 0, 2, 4});
        countryGroupAssignment.put("VN", new int[]{0, 2, 4, 4});
        countryGroupAssignment.put("VU", new int[]{4, 1, 3, 1});
        countryGroupAssignment.put("WS", new int[]{3, 2, 3, 1});
        countryGroupAssignment.put("XK", new int[]{1, 2, 1, 0});
        countryGroupAssignment.put("YE", new int[]{4, 4, 4, 2});
        countryGroupAssignment.put("YT", new int[]{2, 0, 2, 3});
        countryGroupAssignment.put("ZA", new int[]{2, 3, 2, 2});
        countryGroupAssignment.put("ZM", new int[]{3, 3, 2, 1});
        countryGroupAssignment.put("ZW", new int[]{3, 3, 3, 1});
        return Collections.unmodifiableMap(countryGroupAssignment);
    }

    public synchronized void setNetworkTypeOverride(int networkType2) {
        this.networkTypeOverride = networkType2;
        this.networkTypeOverrideSet = true;
        onConnectivityAction();
    }

    public synchronized long getBitrateEstimate() {
        return this.bitrateEstimate;
    }

    @Nullable
    public TransferListener getTransferListener() {
        return this;
    }

    public void addEventListener(Handler eventHandler, BandwidthMeter.EventListener eventListener) {
        this.eventDispatcher.addListener(eventHandler, eventListener);
    }

    public void removeEventListener(BandwidthMeter.EventListener eventListener) {
        this.eventDispatcher.removeListener(eventListener);
    }

    public void onTransferInitializing(DataSource source, DataSpec dataSpec, boolean isNetwork) {
    }

    public synchronized void onTransferStart(DataSource source, DataSpec dataSpec, boolean isNetwork) {
        if (isNetwork) {
            if (this.streamCount == 0) {
                this.sampleStartTimeMs = this.clock.elapsedRealtime();
            }
            this.streamCount++;
        }
    }

    public synchronized void onBytesTransferred(DataSource source, DataSpec dataSpec, boolean isNetwork, int bytes) {
        if (isNetwork) {
            this.sampleBytesTransferred += (long) bytes;
        }
    }

    public synchronized void onTransferEnd(DataSource source, DataSpec dataSpec, boolean isNetwork) {
        if (isNetwork) {
            Assertions.checkState(this.streamCount > 0);
            long nowMs = this.clock.elapsedRealtime();
            int sampleElapsedTimeMs = (int) (nowMs - this.sampleStartTimeMs);
            this.totalElapsedTimeMs += (long) sampleElapsedTimeMs;
            this.totalBytesTransferred += this.sampleBytesTransferred;
            if (sampleElapsedTimeMs > 0) {
                this.slidingPercentile.addSample((int) Math.sqrt((double) this.sampleBytesTransferred), (float) ((this.sampleBytesTransferred * 8000) / ((long) sampleElapsedTimeMs)));
                if (this.totalElapsedTimeMs >= AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS || this.totalBytesTransferred >= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED) {
                    this.bitrateEstimate = (long) this.slidingPercentile.getPercentile(0.5f);
                }
                maybeNotifyBandwidthSample(sampleElapsedTimeMs, this.sampleBytesTransferred, this.bitrateEstimate);
                this.sampleStartTimeMs = nowMs;
                this.sampleBytesTransferred = 0;
            }
            this.streamCount--;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onConnectivityAction() {
        /*
            r10 = this;
            monitor-enter(r10)
            boolean r0 = r10.networkTypeOverrideSet     // Catch:{ all -> 0x005b }
            r1 = 0
            if (r0 == 0) goto L_0x0009
            int r0 = r10.networkTypeOverride     // Catch:{ all -> 0x005b }
            goto L_0x0015
        L_0x0009:
            android.content.Context r0 = r10.context     // Catch:{ all -> 0x005b }
            if (r0 != 0) goto L_0x000f
            r0 = 0
            goto L_0x0015
        L_0x000f:
            android.content.Context r0 = r10.context     // Catch:{ all -> 0x005b }
            int r0 = com.google.android.exoplayer2.util.Util.getNetworkType(r0)     // Catch:{ all -> 0x005b }
        L_0x0015:
            int r2 = r10.networkType     // Catch:{ all -> 0x005b }
            if (r2 != r0) goto L_0x001c
            monitor-exit(r10)
            return
        L_0x001c:
            r10.networkType = r0     // Catch:{ all -> 0x005b }
            r2 = 1
            if (r0 == r2) goto L_0x0059
            if (r0 == 0) goto L_0x0059
            r2 = 8
            if (r0 != r2) goto L_0x0028
            goto L_0x0059
        L_0x0028:
            long r2 = r10.getInitialBitrateEstimateForNetworkType(r0)     // Catch:{ all -> 0x005b }
            r10.bitrateEstimate = r2     // Catch:{ all -> 0x005b }
            com.google.android.exoplayer2.util.Clock r2 = r10.clock     // Catch:{ all -> 0x005b }
            long r2 = r2.elapsedRealtime()     // Catch:{ all -> 0x005b }
            int r4 = r10.streamCount     // Catch:{ all -> 0x005b }
            if (r4 <= 0) goto L_0x003f
            long r4 = r10.sampleStartTimeMs     // Catch:{ all -> 0x005b }
            long r4 = r2 - r4
            int r1 = (int) r4     // Catch:{ all -> 0x005b }
            r5 = r1
            goto L_0x0040
        L_0x003f:
            r5 = 0
        L_0x0040:
            long r6 = r10.sampleBytesTransferred     // Catch:{ all -> 0x005b }
            long r8 = r10.bitrateEstimate     // Catch:{ all -> 0x005b }
            r4 = r10
            r4.maybeNotifyBandwidthSample(r5, r6, r8)     // Catch:{ all -> 0x005b }
            r10.sampleStartTimeMs = r2     // Catch:{ all -> 0x005b }
            r6 = 0
            r10.sampleBytesTransferred = r6     // Catch:{ all -> 0x005b }
            r10.totalBytesTransferred = r6     // Catch:{ all -> 0x005b }
            r10.totalElapsedTimeMs = r6     // Catch:{ all -> 0x005b }
            com.google.android.exoplayer2.util.SlidingPercentile r1 = r10.slidingPercentile     // Catch:{ all -> 0x005b }
            r1.reset()     // Catch:{ all -> 0x005b }
            monitor-exit(r10)
            return
        L_0x0059:
            monitor-exit(r10)
            return
        L_0x005b:
            r0 = move-exception
            monitor-exit(r10)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.DefaultBandwidthMeter.onConnectivityAction():void");
    }

    private void maybeNotifyBandwidthSample(int elapsedMs, long bytesTransferred, long bitrateEstimate2) {
        if (elapsedMs != 0 || bytesTransferred != 0 || bitrateEstimate2 != this.lastReportedBitrateEstimate) {
            this.lastReportedBitrateEstimate = bitrateEstimate2;
            this.eventDispatcher.dispatch(new DefaultBandwidthMeter$$Lambda$0(elapsedMs, bytesTransferred, bitrateEstimate2));
        }
    }

    private long getInitialBitrateEstimateForNetworkType(int networkType2) {
        Long initialBitrateEstimate = this.initialBitrateEstimates.get(networkType2);
        if (initialBitrateEstimate == null) {
            initialBitrateEstimate = this.initialBitrateEstimates.get(0);
        }
        if (initialBitrateEstimate == null) {
            initialBitrateEstimate = 1000000L;
        }
        return initialBitrateEstimate.longValue();
    }

    public static final class Builder {
        @Nullable
        private final Context context;
        private Clock clock;
        private SparseArray<Long> initialBitrateEstimates;
        private boolean resetOnNetworkTypeChange;
        private int slidingWindowMaxWeight;

        public Builder(Context context2) {
            this.context = context2 == null ? null : context2.getApplicationContext();
            this.initialBitrateEstimates = getInitialBitrateEstimatesForCountry(Util.getCountryCode(context2));
            this.slidingWindowMaxWeight = 2000;
            this.clock = Clock.DEFAULT;
        }

        private static SparseArray<Long> getInitialBitrateEstimatesForCountry(String countryCode) {
            int[] groupIndices = getCountryGroupIndices(countryCode);
            SparseArray<Long> result = new SparseArray<>(6);
            result.append(0, 1000000L);
            result.append(2, Long.valueOf(DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_WIFI[groupIndices[0]]));
            result.append(3, Long.valueOf(DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_2G[groupIndices[1]]));
            result.append(4, Long.valueOf(DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_3G[groupIndices[2]]));
            result.append(5, Long.valueOf(DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_4G[groupIndices[3]]));
            result.append(7, Long.valueOf(DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_WIFI[groupIndices[0]]));
            return result;
        }

        private static int[] getCountryGroupIndices(String countryCode) {
            int[] groupIndices = DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_COUNTRY_GROUPS.get(countryCode);
            return groupIndices == null ? new int[]{2, 2, 2, 2} : groupIndices;
        }

        public Builder setSlidingWindowMaxWeight(int slidingWindowMaxWeight2) {
            this.slidingWindowMaxWeight = slidingWindowMaxWeight2;
            return this;
        }

        public Builder setInitialBitrateEstimate(long initialBitrateEstimate) {
            for (int i = 0; i < this.initialBitrateEstimates.size(); i++) {
                this.initialBitrateEstimates.setValueAt(i, Long.valueOf(initialBitrateEstimate));
            }
            return this;
        }

        public Builder setInitialBitrateEstimate(int networkType, long initialBitrateEstimate) {
            this.initialBitrateEstimates.put(networkType, Long.valueOf(initialBitrateEstimate));
            return this;
        }

        public Builder setInitialBitrateEstimate(String countryCode) {
            this.initialBitrateEstimates = getInitialBitrateEstimatesForCountry(Util.toUpperInvariant(countryCode));
            return this;
        }

        public Builder setClock(Clock clock2) {
            this.clock = clock2;
            return this;
        }

        public Builder experimental_resetOnNetworkTypeChange(boolean resetOnNetworkTypeChange2) {
            this.resetOnNetworkTypeChange = resetOnNetworkTypeChange2;
            return this;
        }

        public DefaultBandwidthMeter build() {
            return new DefaultBandwidthMeter(this.context, this.initialBitrateEstimates, this.slidingWindowMaxWeight, this.clock, this.resetOnNetworkTypeChange);
        }
    }

    private static class ConnectivityActionReceiver extends BroadcastReceiver {
        private static ConnectivityActionReceiver staticInstance;
        private final ArrayList<WeakReference<DefaultBandwidthMeter>> bandwidthMeters = new ArrayList<>();
        private final Handler mainHandler = new Handler(Looper.getMainLooper());

        private ConnectivityActionReceiver() {
        }

        public static synchronized ConnectivityActionReceiver getInstance(Context context) {
            ConnectivityActionReceiver connectivityActionReceiver;
            synchronized (ConnectivityActionReceiver.class) {
                if (staticInstance == null) {
                    staticInstance = new ConnectivityActionReceiver();
                    IntentFilter filter = new IntentFilter();
                    filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                    context.registerReceiver(staticInstance, filter);
                }
                connectivityActionReceiver = staticInstance;
            }
            return connectivityActionReceiver;
        }

        public synchronized void register(DefaultBandwidthMeter bandwidthMeter) {
            removeClearedReferences();
            this.bandwidthMeters.add(new WeakReference(bandwidthMeter));
            this.mainHandler.post(new DefaultBandwidthMeter$ConnectivityActionReceiver$$Lambda$0(this, bandwidthMeter));
        }

        public synchronized void onReceive(Context context, Intent intent) {
            if (!isInitialStickyBroadcast()) {
                removeClearedReferences();
                for (int i = 0; i < this.bandwidthMeters.size(); i++) {
                    DefaultBandwidthMeter bandwidthMeter = (DefaultBandwidthMeter) this.bandwidthMeters.get(i).get();
                    if (bandwidthMeter != null) {
                        mo15526xc0d413df(bandwidthMeter);
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: updateBandwidthMeter */
        public void mo15526xc0d413df(DefaultBandwidthMeter bandwidthMeter) {
            bandwidthMeter.onConnectivityAction();
        }

        private void removeClearedReferences() {
            for (int i = this.bandwidthMeters.size() - 1; i >= 0; i--) {
                if (((DefaultBandwidthMeter) this.bandwidthMeters.get(i).get()) == null) {
                    this.bandwidthMeters.remove(i);
                }
            }
        }
    }
}
