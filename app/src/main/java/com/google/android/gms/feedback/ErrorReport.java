package com.google.android.gms.feedback;

import android.annotation.SuppressLint;
import android.app.ApplicationErrorReport;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

import java.util.List;

public class ErrorReport extends zzbkv implements ReflectedParcelable {
    public static final Parcelable.Creator<ErrorReport> CREATOR = new zza();
    public static final int TYPE_USER_INITIATED_BUG_REPORT = 11;
    public String account;
    public String anrStackTraces;
    @SuppressLint({"NewApi"})
    public ApplicationErrorReport applicationErrorReport = new ApplicationErrorReport();
    public BitmapTeleporter bitmapTeleporter;
    public String board;
    public String brand;
    public String buildFingerprint;
    public String buildId;
    public String buildType;
    public String categoryTag;
    public Bundle classificationSignals;
    public String codename;
    @Deprecated
    public String color;
    public String description;
    public String device;
    public String[] eventLog;
    public String exceptionClassName;
    public String exceptionMessage;
    public boolean excludePii;
    public FileTeleporter[] fileTeleporterList;
    public List<RectF> highlightBounds;
    public String incremental;
    public boolean isCtlAllowed;
    public boolean isSilentSend;
    public String launcher;
    public String localeString;
    public LogOptions logOptions;
    public String model;
    public int networkMcc;
    public int networkMnc;
    public String networkName;
    public int networkType;
    public int packageVersion;
    public String packageVersionName;
    public int phoneType;
    public String product;
    public Bundle psdBundle;
    public String[] psdFilePaths;
    public boolean psdHasNoPii;
    public String release;
    public String[] runningApplications;
    public String screenshot;
    public Bitmap screenshotBitmap;
    public byte[] screenshotBytes;
    public int screenshotHeight;
    public String screenshotPath;
    public int screenshotWidth;
    public int sdk_int;
    public String stackTrace;
    public String submittingPackageName;
    public String suggestionSessionId;
    public boolean suggestionShown;
    public String[] systemLog;
    public ThemeSettings themeSettings;
    public String throwClassName;
    public String throwFileName;
    public int throwLineNumber;
    public String throwMethodName;

    ErrorReport(ApplicationErrorReport applicationErrorReport2, String str, int i, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i2, String str9, String str10, String str11, String str12, String str13, String[] strArr, String[] strArr2, String[] strArr3, String str14, String str15, byte[] bArr, int i3, int i4, int i5, int i6, String str16, String str17, String str18, Bundle bundle, boolean z, int i7, int i8, boolean z2, String str19, String str20, int i9, String str21, String str22, String str23, String str24, String str25, String str26, String str27, BitmapTeleporter bitmapTeleporter2, String str28, FileTeleporter[] fileTeleporterArr, String[] strArr4, boolean z3, String str29, ThemeSettings themeSettings2, LogOptions logOptions2, String str30, boolean z4, Bundle bundle2, List<RectF> list, boolean z5, Bitmap bitmap) {
        this.applicationErrorReport = applicationErrorReport2;
        this.description = str;
        this.packageVersion = i;
        this.packageVersionName = str2;
        this.device = str3;
        this.buildId = str4;
        this.buildType = str5;
        this.model = str6;
        this.product = str7;
        this.buildFingerprint = str8;
        this.sdk_int = i2;
        this.release = str9;
        this.incremental = str10;
        this.codename = str11;
        this.board = str12;
        this.brand = str13;
        this.runningApplications = strArr;
        this.systemLog = strArr2;
        this.eventLog = strArr3;
        this.anrStackTraces = str14;
        this.screenshot = str15;
        this.screenshotBytes = bArr;
        this.screenshotHeight = i3;
        this.screenshotWidth = i4;
        this.phoneType = i5;
        this.networkType = i6;
        this.networkName = str16;
        this.account = str17;
        this.localeString = str18;
        this.psdBundle = bundle;
        this.isSilentSend = z;
        this.networkMcc = i7;
        this.networkMnc = i8;
        this.isCtlAllowed = z2;
        this.exceptionClassName = str19;
        this.throwFileName = str20;
        this.throwLineNumber = i9;
        this.throwClassName = str21;
        this.throwMethodName = str22;
        this.stackTrace = str23;
        this.exceptionMessage = str24;
        this.categoryTag = str25;
        this.color = str26;
        this.submittingPackageName = str27;
        this.bitmapTeleporter = bitmapTeleporter2;
        this.screenshotPath = str28;
        this.fileTeleporterList = fileTeleporterArr;
        this.psdFilePaths = strArr4;
        this.excludePii = z3;
        this.launcher = str29;
        this.themeSettings = themeSettings2;
        this.logOptions = logOptions2;
        this.suggestionSessionId = str30;
        this.suggestionShown = z4;
        this.classificationSignals = bundle2;
        this.highlightBounds = list;
        this.psdHasNoPii = z5;
        this.screenshotBitmap = bitmap;
    }

    public ErrorReport() {
    }

    public static ErrorReport fromByteArray(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        ErrorReport createFromParcel = CREATOR.createFromParcel(obtain);
        obtain.recycle();
        return createFromParcel;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, android.app.ApplicationErrorReport, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void
     arg types: [android.os.Parcel, int, java.lang.String, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.util.SparseArray<java.lang.String>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Boolean, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Double, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Float, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Integer, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Long, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.util.List<java.lang.Integer>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, double[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
     arg types: [android.os.Parcel, int, java.lang.String[], int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.util.SparseArray<java.lang.String>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Boolean, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Double, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Float, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Integer, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Long, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.util.List<java.lang.Integer>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, double[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[], boolean):void
     arg types: [android.os.Parcel, int, byte[], int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.util.SparseArray<java.lang.String>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Boolean, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Double, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Float, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Integer, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Long, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.util.List<java.lang.Integer>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, double[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[], boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
     arg types: [android.os.Parcel, int, android.os.Bundle, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.util.SparseArray<java.lang.String>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Boolean, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Double, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Float, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Integer, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Long, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.util.List<java.lang.Integer>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, double[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, com.google.android.gms.common.data.BitmapTeleporter, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
     arg types: [android.os.Parcel, int, com.google.android.gms.feedback.FileTeleporter[], int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, com.google.android.gms.feedback.ThemeSettings, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, com.google.android.gms.feedback.LogOptions, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, android.graphics.Bitmap, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    public void writeToParcel(Parcel parcel, int i) {
        int zza = zzbky.zza(parcel);
        zzbky.zza(parcel, 2, (Parcelable) this.applicationErrorReport, i, false);
        zzbky.zza(parcel, 3, this.description, false);
        zzbky.zza(parcel, 4, this.packageVersion);
        zzbky.zza(parcel, 5, this.packageVersionName, false);
        zzbky.zza(parcel, 6, this.device, false);
        zzbky.zza(parcel, 7, this.buildId, false);
        zzbky.zza(parcel, 8, this.buildType, false);
        zzbky.zza(parcel, 9, this.model, false);
        zzbky.zza(parcel, 10, this.product, false);
        zzbky.zza(parcel, 11, this.buildFingerprint, false);
        zzbky.zza(parcel, 12, this.sdk_int);
        zzbky.zza(parcel, 13, this.release, false);
        zzbky.zza(parcel, 14, this.incremental, false);
        zzbky.zza(parcel, 15, this.codename, false);
        zzbky.zza(parcel, 16, this.board, false);
        zzbky.zza(parcel, 17, this.brand, false);
        zzbky.zza(parcel, 18, this.runningApplications, false);
        zzbky.zza(parcel, 19, this.systemLog, false);
        zzbky.zza(parcel, 20, this.eventLog, false);
        zzbky.zza(parcel, 21, this.anrStackTraces, false);
        zzbky.zza(parcel, 22, this.screenshot, false);
        zzbky.zza(parcel, 23, this.screenshotBytes, false);
        zzbky.zza(parcel, 24, this.screenshotHeight);
        zzbky.zza(parcel, 25, this.screenshotWidth);
        zzbky.zza(parcel, 26, this.phoneType);
        zzbky.zza(parcel, 27, this.networkType);
        zzbky.zza(parcel, 28, this.networkName, false);
        zzbky.zza(parcel, 29, this.account, false);
        zzbky.zza(parcel, 30, this.localeString, false);
        zzbky.zza(parcel, 31, this.psdBundle, false);
        zzbky.zza(parcel, 32, this.isSilentSend);
        zzbky.zza(parcel, 33, this.networkMcc);
        zzbky.zza(parcel, 34, this.networkMnc);
        zzbky.zza(parcel, 35, this.isCtlAllowed);
        zzbky.zza(parcel, 36, this.exceptionClassName, false);
        zzbky.zza(parcel, 37, this.throwFileName, false);
        zzbky.zza(parcel, 38, this.throwLineNumber);
        zzbky.zza(parcel, 39, this.throwClassName, false);
        zzbky.zza(parcel, 40, this.throwMethodName, false);
        zzbky.zza(parcel, 41, this.stackTrace, false);
        zzbky.zza(parcel, 42, this.exceptionMessage, false);
        zzbky.zza(parcel, 43, this.categoryTag, false);
        zzbky.zza(parcel, 44, this.color, false);
        zzbky.zza(parcel, 45, this.submittingPackageName, false);
        zzbky.zza(parcel, 46, (Parcelable) this.bitmapTeleporter, i, false);
        zzbky.zza(parcel, 47, this.screenshotPath, false);
        zzbky.zza(parcel, 48, (Parcelable[]) this.fileTeleporterList, i, false);
        zzbky.zza(parcel, 49, this.psdFilePaths, false);
        zzbky.zza(parcel, 50, this.excludePii);
        zzbky.zza(parcel, 51, this.launcher, false);
        zzbky.zza(parcel, 52, (Parcelable) this.themeSettings, i, false);
        zzbky.zza(parcel, 53, (Parcelable) this.logOptions, i, false);
        zzbky.zza(parcel, 54, this.suggestionSessionId, false);
        zzbky.zza(parcel, 55, this.suggestionShown);
        zzbky.zza(parcel, 56, this.classificationSignals, false);
        zzbky.zzc(parcel, 57, this.highlightBounds, false);
        zzbky.zza(parcel, 58, this.psdHasNoPii);
        zzbky.zza(parcel, 59, (Parcelable) this.screenshotBitmap, i, false);
        zzbky.zza(parcel, zza);
    }

    public byte[] toByteArray() {
        Parcel obtain = Parcel.obtain();
        writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }
}
