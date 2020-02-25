package com.google.android.gms.phenotype;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.gms.tasks.Tasks;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class PhenotypeFlagCommitter {
    public static final String SHARED_PREFS_SERVER_TOKEN_KEY_NAME = "__phenotype_server_token";
    public static final String SHARED_PREFS_SNAPSHOT_TOKEN_KEY_NAME = "__phenotype_snapshot_token";
    protected final PhenotypeClient client;
    protected final String packageName;
    protected long timeoutMillis;

    @Deprecated
    public PhenotypeFlagCommitter(GoogleApiClient googleApiClient, String str) {
        this(Phenotype.getInstance(googleApiClient.zzb()), str);
    }

    @Deprecated
    public PhenotypeFlagCommitter(GoogleApiClient googleApiClient, PhenotypeApi phenotypeApi, String str) {
        this(Phenotype.getInstance(googleApiClient.zzb()), str);
    }

    public PhenotypeFlagCommitter(PhenotypeClient phenotypeClient, String str) {
        this.client = phenotypeClient;
        this.packageName = str;
        this.timeoutMillis = AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS;
    }

    @SuppressLint({"ApplySharedPref"})
    public static void writeToSharedPrefs(SharedPreferences sharedPreferences, Configurations configurations) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (!configurations.isDelta) {
            edit.clear();
        }
        for (Configuration zza : configurations.configurations) {
            zza(edit, zza);
        }
        edit.putString("__phenotype_server_token", configurations.serverToken);
        edit.putLong("__phenotype_configuration_version", configurations.configurationVersion);
        edit.putString("__phenotype_snapshot_token", configurations.snapshotToken);
        if (!edit.commit()) {
            Log.w("PhenotypeFlagCommitter", "Failed to commit Phenotype configs to SharedPreferences!");
        }
    }

    public static void writeToSharedPrefs(SharedPreferences sharedPreferences, Configuration... configurationArr) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        for (Configuration zza : configurationArr) {
            zza(edit, zza);
        }
        if (!edit.commit()) {
            Log.w("PhenotypeFlagCommitter", "Failed to commit Phenotype configs to SharedPreferences!");
        }
    }

    private static void zza(SharedPreferences.Editor editor, Configuration configuration) {
        if (configuration != null) {
            for (String remove : configuration.deleteFlags) {
                editor.remove(remove);
            }
            for (Flag flag : configuration.flags) {
                int i = flag.flagValueType;
                if (i == 1) {
                    editor.putLong(flag.name, flag.getLong());
                } else if (i == 2) {
                    editor.putBoolean(flag.name, flag.getBoolean());
                } else if (i == 3) {
                    editor.putFloat(flag.name, (float) flag.getDouble());
                } else if (i == 4) {
                    editor.putString(flag.name, flag.getString());
                } else if (i == 5) {
                    editor.putString(flag.name, Base64.encodeToString(flag.getBytesValue(), 3));
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void handleConfigurations(Configurations configurations);

    public void setTimeoutMillis(long j) {
        this.timeoutMillis = j;
    }

    /* access modifiers changed from: protected */
    public String getSnapshotToken() {
        return null;
    }

    public boolean commitForUser(String str) {
        zzau.zza((Object) str);
        return zza(str, 3);
    }

    /* access modifiers changed from: protected */
    public Configurations getConfigurations(String str, String str2, String str3) {
        try {
            return (Configurations) Tasks.await(this.client.getConfigurationSnapshot(str, str2, str3), this.timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 31);
            sb.append("Retrieving snapshot for ");
            sb.append(str);
            sb.append(" failed");
            Log.e("PhenotypeFlagCommitter", sb.toString(), e);
            return null;
        }
    }

    private final boolean zza(String str, int i) {
        while (i > 0) {
            Configurations configurations = getConfigurations(this.packageName, str, getSnapshotToken());
            if (configurations == null) {
                return false;
            }
            handleConfigurations(configurations);
            try {
                Tasks.await(this.client.commitToConfiguration(configurations.snapshotToken), this.timeoutMillis, TimeUnit.MILLISECONDS);
                return true;
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                String str2 = this.packageName;
                StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 41);
                sb.append("Committing snapshot for ");
                sb.append(str2);
                sb.append(" failed, retrying");
                Log.w("PhenotypeFlagCommitter", sb.toString(), e);
                i--;
            }
        }
        String valueOf = String.valueOf(this.packageName);
        Log.w("PhenotypeFlagCommitter", valueOf.length() != 0 ? "No more attempts remaining, giving up for ".concat(valueOf) : new String("No more attempts remaining, giving up for "));
        return false;
    }

    public final void commitForUserAsync(String str, Callback callback) {
        commitForUserAsync(str, TaskExecutors.MAIN_THREAD, callback);
    }

    public final void commitForUserAsync(String str, Executor executor, Callback callback) {
        zzau.zza((Object) str);
        zza(str, executor, callback, 3);
    }

    private final void zza(String str, Executor executor, Callback callback, int i) {
        this.client.getConfigurationSnapshot(this.packageName, str, getSnapshotToken()).addOnCompleteListener(executor, new zzau(this, callback, executor, i, str));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(int i, String str, Executor executor, Callback callback, Task task) {
        boolean isSuccessful = task.isSuccessful();
        if (!isSuccessful && i > 1) {
            String str2 = this.packageName;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 41);
            sb.append("Committing snapshot for ");
            sb.append(str2);
            sb.append(" failed, retrying");
            Log.w("PhenotypeFlagCommitter", sb.toString());
            zza(str, executor, callback, i - 1);
        } else if (callback != null) {
            callback.onFinish(isSuccessful);
        }
    }

    public interface Callback {
        void onFinish(boolean z);
    }
}
