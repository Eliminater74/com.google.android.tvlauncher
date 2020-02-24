package org.checkerframework.checker.units;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

public class UnitsTools {

    /* renamed from: A */
    public static final int f225A = 1;

    /* renamed from: C */
    public static final int f226C = 1;

    /* renamed from: K */
    public static final int f227K = 1;

    /* renamed from: cd */
    public static final int f228cd = 1;
    public static final double deg = 1.0d;

    /* renamed from: g */
    public static final int f229g = 1;

    /* renamed from: h */
    public static final int f230h = 1;

    /* renamed from: kg */
    public static final int f231kg = 1;

    /* renamed from: km */
    public static final int f232km = 1;
    public static final int km2 = 1;
    public static final int kmPERh = 1;

    /* renamed from: m */
    public static final int f233m = 1;

    /* renamed from: m2 */
    public static final int f234m2 = 1;
    public static final int mPERs = 1;
    public static final int mPERs2 = 1;
    public static final int min = 1;

    /* renamed from: mm */
    public static final int f235mm = 1;
    public static final int mm2 = 1;
    public static final int mol = 1;
    public static final double rad = 1.0d;

    /* renamed from: s */
    public static final int f236s = 1;

    public static double toRadians(double angdeg) {
        return Math.toRadians(angdeg);
    }

    public static double toDegrees(double angrad) {
        return Math.toDegrees(angrad);
    }

    public static int fromMilliMeterToMeter(int mm) {
        return mm / 1000;
    }

    public static int fromMeterToMilliMeter(int m) {
        return m * 1000;
    }

    public static int fromMeterToKiloMeter(int m) {
        return m / 1000;
    }

    public static int fromKiloMeterToMeter(int km) {
        return km * 1000;
    }

    public static int fromGramToKiloGram(int g) {
        return g / 1000;
    }

    public static int fromKiloGramToGram(int kg) {
        return kg * 1000;
    }

    public static double fromMeterPerSecondToKiloMeterPerHour(double mps) {
        return 3.6d * mps;
    }

    public static double fromKiloMeterPerHourToMeterPerSecond(double kmph) {
        return kmph / 3.6d;
    }

    public static int fromKelvinToCelsius(int k) {
        return k - 273;
    }

    public static int fromCelsiusToKelvin(int c) {
        return c + ClientAnalytics.LogRequest.LogSource.FAMILYLINK_VALUE;
    }

    public static int fromSecondToMinute(int s) {
        return s / 60;
    }

    public static int fromMinuteToSecond(int min2) {
        return min2 * 60;
    }

    public static int fromMinuteToHour(int min2) {
        return min2 / 60;
    }

    public static int fromHourToMinute(int h) {
        return h * 60;
    }
}
