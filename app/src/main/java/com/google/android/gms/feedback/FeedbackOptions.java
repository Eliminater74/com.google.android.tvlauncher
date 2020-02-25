package com.google.android.gms.feedback;

import android.app.ApplicationErrorReport;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Pair;

import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeedbackOptions extends zzbkv {
    @Hide
    public static final Parcelable.Creator<FeedbackOptions> CREATOR = new zzh();
    private String zza;
    private Bundle zzb;
    private String zzc;
    private ApplicationErrorReport zzd;
    private String zze;
    private BitmapTeleporter zzf;
    private String zzg;
    private List<FileTeleporter> zzh;
    private boolean zzi;
    private ThemeSettings zzj;
    private LogOptions zzk;
    private boolean zzl;
    private Bitmap zzm;
    private BaseFeedbackProductSpecificData zzn;

    FeedbackOptions(String str, Bundle bundle, String str2, ApplicationErrorReport applicationErrorReport, String str3, BitmapTeleporter bitmapTeleporter, String str4, List<FileTeleporter> list, boolean z, ThemeSettings themeSettings, LogOptions logOptions, boolean z2, Bitmap bitmap) {
        this.zzn = null;
        this.zza = str;
        this.zzb = bundle;
        this.zzc = str2;
        this.zzd = applicationErrorReport;
        this.zze = str3;
        this.zzf = bitmapTeleporter;
        this.zzg = str4;
        this.zzh = list;
        this.zzi = z;
        this.zzj = themeSettings;
        this.zzk = logOptions;
        this.zzl = z2;
        this.zzm = bitmap;
    }

    private FeedbackOptions(ApplicationErrorReport applicationErrorReport) {
        this(null, null, null, applicationErrorReport, null, null, null, null, true, null, null, false, null);
    }

    @Hide
    public static FeedbackOptions zza(List<FileTeleporter> list) {
        FeedbackOptions feedbackOptions = new FeedbackOptions(null);
        feedbackOptions.zzh = list;
        return feedbackOptions;
    }

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
     arg types: [android.os.Parcel, int, android.app.ApplicationErrorReport, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, com.google.android.gms.common.data.BitmapTeleporter, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
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
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 2, this.zza, false);
        zzbky.zza(parcel, 3, this.zzb, false);
        zzbky.zza(parcel, 5, this.zzc, false);
        zzbky.zza(parcel, 6, (Parcelable) this.zzd, i, false);
        zzbky.zza(parcel, 7, this.zze, false);
        zzbky.zza(parcel, 8, (Parcelable) this.zzf, i, false);
        zzbky.zza(parcel, 9, this.zzg, false);
        zzbky.zzc(parcel, 10, this.zzh, false);
        zzbky.zza(parcel, 11, this.zzi);
        zzbky.zza(parcel, 12, (Parcelable) this.zzj, i, false);
        zzbky.zza(parcel, 13, (Parcelable) this.zzk, i, false);
        zzbky.zza(parcel, 14, this.zzl);
        zzbky.zza(parcel, 15, (Parcelable) this.zzm, i, false);
        zzbky.zza(parcel, zza2);
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zza(Bitmap bitmap) {
        this.zzm = bitmap;
        return this;
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zza(BitmapTeleporter bitmapTeleporter) {
        this.zzf = bitmapTeleporter;
        return this;
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zza(String str) {
        this.zza = str;
        return this;
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zza(Bundle bundle) {
        this.zzb = bundle;
        return this;
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zzb(String str) {
        this.zzc = str;
        return this;
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zza(ApplicationErrorReport.CrashInfo crashInfo) {
        this.zzd.crashInfo = crashInfo;
        return this;
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zzc(String str) {
        this.zze = str;
        return this;
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zzd(String str) {
        this.zzg = str;
        return this;
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zzb(List<FileTeleporter> list) {
        this.zzh = list;
        return this;
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zza(boolean z) {
        this.zzi = z;
        return this;
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zza(ThemeSettings themeSettings) {
        this.zzj = themeSettings;
        return this;
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zza(LogOptions logOptions) {
        this.zzk = logOptions;
        return this;
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zza(BaseFeedbackProductSpecificData baseFeedbackProductSpecificData) {
        this.zzn = baseFeedbackProductSpecificData;
        return this;
    }

    /* access modifiers changed from: private */
    public final FeedbackOptions zzb(boolean z) {
        this.zzl = z;
        return this;
    }

    @Hide
    @VisibleForTesting
    @Deprecated
    public final String zza() {
        return this.zza;
    }

    @Hide
    @VisibleForTesting
    @Deprecated
    public final Bundle zzb() {
        return this.zzb;
    }

    @Hide
    @VisibleForTesting
    @Deprecated
    public final ThemeSettings zzc() {
        return this.zzj;
    }

    @Hide
    @VisibleForTesting
    @Deprecated
    public final String zzd() {
        return this.zzc;
    }

    @Hide
    @VisibleForTesting
    @Deprecated
    public final ApplicationErrorReport.CrashInfo zze() {
        ApplicationErrorReport applicationErrorReport = this.zzd;
        if (applicationErrorReport == null) {
            return null;
        }
        return applicationErrorReport.crashInfo;
    }

    @Hide
    @VisibleForTesting
    @Deprecated
    public final String zzf() {
        return this.zze;
    }

    @Hide
    @VisibleForTesting
    @Deprecated
    public final Bitmap zzg() {
        return this.zzm;
    }

    @Hide
    @VisibleForTesting
    @Deprecated
    public final BitmapTeleporter zzh() {
        return this.zzf;
    }

    @Hide
    @VisibleForTesting
    @Deprecated
    public final String zzi() {
        return this.zzg;
    }

    @VisibleForTesting
    @Hide
    @Deprecated
    public final List<FileTeleporter> zzj() {
        return this.zzh;
    }

    @Hide
    @VisibleForTesting
    @Deprecated
    public final boolean zzk() {
        return this.zzi;
    }

    @Hide
    @VisibleForTesting
    @Deprecated
    public final LogOptions zzl() {
        return this.zzk;
    }

    @Hide
    @VisibleForTesting
    @Deprecated
    public final boolean zzm() {
        return this.zzl;
    }

    @Nullable
    @Hide
    @Deprecated
    public final BaseFeedbackProductSpecificData zzn() {
        return this.zzn;
    }

    @Nullable
    @Hide
    @Deprecated
    public final BaseFeedbackProductSpecificData zzo() {
        return this.zzn;
    }

    @VisibleForTesting
    public static class Builder {
        private Bitmap zza;
        private BitmapTeleporter zzb;
        private String zzc;
        private Bundle zzd = new Bundle();
        private String zze;
        private String zzf;
        private List<FileTeleporter> zzg = new ArrayList();
        private boolean zzh;
        private ThemeSettings zzi;
        private LogOptions zzj;
        private boolean zzk;
        private BaseFeedbackProductSpecificData zzl;

        @VisibleForTesting
        @Deprecated
        public Builder setScreenshot(Bitmap bitmap) {
            if (bitmap != null) {
                this.zzb = new BitmapTeleporter(bitmap);
            }
            return this;
        }

        public Builder setScreenshotBitmap(Bitmap bitmap) {
            this.zza = bitmap;
            return this;
        }

        @VisibleForTesting
        public Builder setAccountInUse(String str) {
            this.zzc = str;
            return this;
        }

        @VisibleForTesting
        @Deprecated
        public Builder addPsd(String str, String str2) {
            if (!this.zzk) {
                this.zzd.putString(str, str2);
                return this;
            }
            throw new IllegalStateException("Can't call addPsd after psd is already certified pii free");
        }

        @VisibleForTesting
        @Deprecated
        public Builder addPsdBundle(Bundle bundle) {
            if (!this.zzk) {
                if (bundle != null) {
                    this.zzd.putAll(bundle);
                }
                return this;
            }
            throw new IllegalStateException("Can't call addPsdBundle after psd is already certified pii free");
        }

        public Builder addPsdForSilentFeedback(String str, String str2, boolean z) {
            zza(z);
            this.zzd.putString(str, str2);
            return this;
        }

        public Builder addPsdsForSilentFeedback(Map<String, String> map, boolean z) {
            zza(z);
            for (Map.Entry next : map.entrySet()) {
                this.zzd.putString((String) next.getKey(), (String) next.getValue());
            }
            return this;
        }

        @Deprecated
        public Builder addProductSpecificBinaryData(String str, String str2, byte[] bArr) {
            return addPsbdForSilentFeedback(str, str2, bArr, false);
        }

        public Builder addPsbdForSilentFeedback(String str, String str2, byte[] bArr, boolean z) {
            zza(z);
            this.zzg.add(new FileTeleporter(bArr, str2, str));
            return this;
        }

        @VisibleForTesting
        private final void zza(boolean z) {
            if (!(!this.zzd.isEmpty() || !this.zzg.isEmpty()) || this.zzk == z) {
                this.zzk = z;
                return;
            }
            throw new IllegalStateException("Can't mix pii-full psd and pii-free psd");
        }

        public Builder setDescription(String str) {
            this.zze = str;
            return this;
        }

        public Builder setCategoryTag(String str) {
            this.zzf = str;
            return this;
        }

        public Builder setExcludePii(boolean z) {
            this.zzh = z;
            return this;
        }

        public Builder setThemeSettings(ThemeSettings themeSettings) {
            this.zzi = themeSettings;
            return this;
        }

        public Builder setLogOptions(LogOptions logOptions) {
            this.zzj = logOptions;
            return this;
        }

        @Deprecated
        public Builder setPiiFreePsd(BaseFeedbackProductSpecificData baseFeedbackProductSpecificData) {
            List<Pair<String, String>> asyncFeedbackPsd = baseFeedbackProductSpecificData.getAsyncFeedbackPsd();
            ArrayMap arrayMap = new ArrayMap();
            int i = 0;
            int i2 = 0;
            while (asyncFeedbackPsd != null && i2 < asyncFeedbackPsd.size()) {
                int i3 = i2 + 1;
                Pair pair = asyncFeedbackPsd.get(i2);
                arrayMap.put((String) pair.first, (String) pair.second);
                i2 = i3;
            }
            List<FileTeleporter> asyncFeedbackPsbd = baseFeedbackProductSpecificData.getAsyncFeedbackPsbd();
            while (asyncFeedbackPsbd != null && i < asyncFeedbackPsbd.size()) {
                int i4 = i + 1;
                FileTeleporter fileTeleporter = asyncFeedbackPsbd.get(i);
                addPsbdForSilentFeedback(fileTeleporter.zzb, fileTeleporter.zza, fileTeleporter.zzc, true);
                i = i4;
            }
            return addPsdsForSilentFeedback(arrayMap, true);
        }

        @Deprecated
        public Builder setPsd(BaseFeedbackProductSpecificData baseFeedbackProductSpecificData) {
            return setAsyncPsd(baseFeedbackProductSpecificData, false);
        }

        public Builder setAsyncPsd(BaseFeedbackProductSpecificData baseFeedbackProductSpecificData, boolean z) {
            zza(z);
            this.zzl = baseFeedbackProductSpecificData;
            return this;
        }

        @VisibleForTesting
        public FeedbackOptions build() {
            return new FeedbackOptions(new ApplicationErrorReport()).zza(this.zza).zza(this.zzb).zza(this.zzc).zzb(this.zze).zza(this.zzd).zzc(this.zzf).zzb(this.zzg).zza(this.zzh).zza(this.zzi).zza(this.zzj).zzb(this.zzk).zza(this.zzl);
        }
    }

    @VisibleForTesting
    public static class CrashBuilder extends Builder {
        private final ApplicationErrorReport zza;
        private String zzb;

        public CrashBuilder() {
            this.zza = new ApplicationErrorReport();
            this.zza.crashInfo = new ApplicationErrorReport.CrashInfo();
            this.zza.crashInfo.throwLineNumber = -1;
        }

        public CrashBuilder(Throwable th) {
            this();
            this.zza.crashInfo = new ApplicationErrorReport.CrashInfo(th);
        }

        @VisibleForTesting
        public CrashBuilder setExceptionClassName(String str) {
            this.zza.crashInfo.exceptionClassName = str;
            return this;
        }

        @VisibleForTesting
        public CrashBuilder setThrowFileName(String str) {
            this.zza.crashInfo.throwFileName = str;
            return this;
        }

        @VisibleForTesting
        public CrashBuilder setThrowLineNumber(int i) {
            this.zza.crashInfo.throwLineNumber = i;
            return this;
        }

        @VisibleForTesting
        public CrashBuilder setThrowClassName(String str) {
            this.zza.crashInfo.throwClassName = str;
            return this;
        }

        @VisibleForTesting
        public CrashBuilder setThrowMethodName(String str) {
            this.zza.crashInfo.throwMethodName = str;
            return this;
        }

        @VisibleForTesting
        public CrashBuilder setStackTrace(String str) {
            this.zza.crashInfo.stackTrace = str;
            return this;
        }

        @VisibleForTesting
        public CrashBuilder setExceptionMessage(String str) {
            this.zza.crashInfo.exceptionMessage = str;
            return this;
        }

        public CrashBuilder setCrashedPackage(String str) {
            this.zzb = str;
            return this;
        }

        @VisibleForTesting
        public FeedbackOptions build() {
            zzau.zza((Object) this.zza.crashInfo.exceptionClassName);
            zzau.zza((Object) this.zza.crashInfo.throwClassName);
            zzau.zza((Object) this.zza.crashInfo.throwMethodName);
            zzau.zza((Object) this.zza.crashInfo.stackTrace);
            if (TextUtils.isEmpty(this.zza.crashInfo.throwFileName)) {
                this.zza.crashInfo.throwFileName = "unknown";
            }
            return super.build().zza(this.zza.crashInfo).zzd(this.zzb);
        }
    }
}
