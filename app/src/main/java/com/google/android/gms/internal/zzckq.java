package com.google.android.gms.internal;

import android.app.ApplicationErrorReport;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.zzl;
import com.google.android.gms.feedback.ErrorReport;
import com.google.android.gms.feedback.FeedbackOptions;
import com.google.android.gms.feedback.FileTeleporter;
import java.io.File;
import java.util.List;

/* compiled from: FeedbackClientImpl */
public final class zzckq extends zzl<zzckt> {
    @NonNull
    private final Context zzc;

    public zzckq(@NonNull Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, ClientSettings clientSettings) {
        super(context, looper, 29, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzc = context;
    }

    /* access modifiers changed from: protected */
    public final String getStartServiceAction() {
        return "com.google.android.gms.feedback.internal.IFeedbackService";
    }

    /* access modifiers changed from: protected */
    public final String getServiceDescriptor() {
        return "com.google.android.gms.feedback.internal.IFeedbackService";
    }

    @Deprecated
    public final void zza(FeedbackOptions feedbackOptions) throws RemoteException {
        ((zzckt) zzag()).zza(zzc(feedbackOptions));
    }

    public final void zza(@Nullable FeedbackOptions feedbackOptions, long j) throws RemoteException {
        ((zzckt) zzag()).zza(zzc(feedbackOptions), j);
    }

    public final void zzb(FeedbackOptions feedbackOptions) throws RemoteException {
        ((zzckt) zzag()).zzb(zzc(feedbackOptions));
    }

    private final ErrorReport zzc(FeedbackOptions feedbackOptions) {
        return zza(feedbackOptions, this.zzc.getCacheDir());
    }

    public static ErrorReport zza(FeedbackOptions feedbackOptions, File file) {
        ErrorReport errorReport = new ErrorReport();
        if (feedbackOptions == null) {
            return errorReport;
        }
        if (feedbackOptions.zzb() != null && feedbackOptions.zzb().size() > 0) {
            errorReport.psdBundle = feedbackOptions.zzb();
        }
        if (!TextUtils.isEmpty(feedbackOptions.zza())) {
            errorReport.account = feedbackOptions.zza();
        }
        if (!TextUtils.isEmpty(feedbackOptions.zzd())) {
            errorReport.description = feedbackOptions.zzd();
        }
        ApplicationErrorReport.CrashInfo zze = feedbackOptions.zze();
        if (zze != null) {
            errorReport.throwMethodName = zze.throwMethodName;
            errorReport.throwLineNumber = zze.throwLineNumber;
            errorReport.throwClassName = zze.throwClassName;
            errorReport.stackTrace = zze.stackTrace;
            errorReport.exceptionClassName = zze.exceptionClassName;
            errorReport.exceptionMessage = zze.exceptionMessage;
            errorReport.throwFileName = zze.throwFileName;
        }
        if (feedbackOptions.zzc() != null) {
            errorReport.themeSettings = feedbackOptions.zzc();
        }
        if (!TextUtils.isEmpty(feedbackOptions.zzf())) {
            errorReport.categoryTag = feedbackOptions.zzf();
        }
        if (!TextUtils.isEmpty(feedbackOptions.zzi())) {
            errorReport.applicationErrorReport.packageName = feedbackOptions.zzi();
        }
        if (feedbackOptions.zzg() != null) {
            errorReport.screenshotBitmap = feedbackOptions.zzg();
        }
        if (file != null) {
            if (feedbackOptions.zzh() != null) {
                errorReport.bitmapTeleporter = feedbackOptions.zzh();
                errorReport.bitmapTeleporter.setTempDir(file);
            }
            List<FileTeleporter> zzj = feedbackOptions.zzj();
            if (!(zzj == null || zzj.size() == 0)) {
                zza(zzj, file);
                errorReport.fileTeleporterList = (FileTeleporter[]) feedbackOptions.zzj().toArray(new FileTeleporter[feedbackOptions.zzj().size()]);
            }
        }
        if (feedbackOptions.zzl() != null) {
            errorReport.logOptions = feedbackOptions.zzl();
        }
        errorReport.excludePii = feedbackOptions.zzk();
        errorReport.psdHasNoPii = feedbackOptions.zzm();
        return errorReport;
    }

    public static void zza(@NonNull List<FileTeleporter> list, @NonNull File file) {
        for (int i = 0; i < list.size(); i++) {
            FileTeleporter fileTeleporter = list.get(i);
            if (fileTeleporter != null) {
                fileTeleporter.setTempDir(file);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.feedback.internal.IFeedbackService");
        if (queryLocalInterface instanceof zzckt) {
            return (zzckt) queryLocalInterface;
        }
        return new zzcku(iBinder);
    }
}
