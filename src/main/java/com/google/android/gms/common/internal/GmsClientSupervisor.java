package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import java.util.Arrays;

public abstract class GmsClientSupervisor {
    public static final int DEFAULT_BIND_FLAGS = 129;
    private static final Object zza = new Object();
    private static GmsClientSupervisor zzb;

    /* access modifiers changed from: protected */
    public abstract boolean bindService(ConnectionStatusConfig connectionStatusConfig, ServiceConnection serviceConnection, String str);

    public abstract void resetForTesting();

    /* access modifiers changed from: protected */
    public abstract void unbindService(ConnectionStatusConfig connectionStatusConfig, ServiceConnection serviceConnection, String str);

    public static GmsClientSupervisor getInstance(Context context) {
        synchronized (zza) {
            if (zzb == null) {
                zzb = new zzq(context.getApplicationContext());
            }
        }
        return zzb;
    }

    public static final class ConnectionStatusConfig {
        private final String zza;
        private final String zzb;
        private final ComponentName zzc;
        private final int zzd;

        public ConnectionStatusConfig(String str, int i) {
            this.zza = zzau.zza(str);
            this.zzb = "com.google.android.gms";
            this.zzc = null;
            this.zzd = i;
        }

        public ConnectionStatusConfig(String str, String str2, int i) {
            this.zza = zzau.zza(str);
            this.zzb = zzau.zza(str2);
            this.zzc = null;
            this.zzd = i;
        }

        public ConnectionStatusConfig(ComponentName componentName, int i) {
            this.zza = null;
            this.zzb = null;
            this.zzc = (ComponentName) zzau.zza(componentName);
            this.zzd = i;
        }

        public final String toString() {
            String str = this.zza;
            return str == null ? this.zzc.flattenToString() : str;
        }

        public final String getAction() {
            return this.zza;
        }

        public final String getPackage() {
            return this.zzb;
        }

        public final ComponentName getComponentName() {
            return this.zzc;
        }

        public final int getBindFlags() {
            return this.zzd;
        }

        public final Intent getStartServiceIntent(Context context) {
            String str = this.zza;
            if (str != null) {
                return new Intent(str).setPackage(this.zzb);
            }
            return new Intent().setComponent(this.zzc);
        }

        public final int hashCode() {
            return Arrays.hashCode(new Object[]{this.zza, this.zzb, this.zzc, Integer.valueOf(this.zzd)});
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConnectionStatusConfig)) {
                return false;
            }
            ConnectionStatusConfig connectionStatusConfig = (ConnectionStatusConfig) obj;
            if (!zzak.zza(this.zza, connectionStatusConfig.zza) || !zzak.zza(this.zzb, connectionStatusConfig.zzb) || !zzak.zza(this.zzc, connectionStatusConfig.zzc) || this.zzd != connectionStatusConfig.zzd) {
                return false;
            }
            return true;
        }
    }

    public boolean bindService(String str, ServiceConnection serviceConnection, String str2) {
        return bindService(new ConnectionStatusConfig(str, 129), serviceConnection, str2);
    }

    public boolean bindService(String str, String str2, ServiceConnection serviceConnection, String str3) {
        return bindService(str, str2, 129, serviceConnection, str3);
    }

    public boolean bindService(String str, String str2, int i, ServiceConnection serviceConnection, String str3) {
        return bindService(new ConnectionStatusConfig(str, str2, i), serviceConnection, str3);
    }

    public boolean bindService(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        return bindService(new ConnectionStatusConfig(componentName, 129), serviceConnection, str);
    }

    public void unbindService(String str, ServiceConnection serviceConnection, String str2) {
        unbindService(new ConnectionStatusConfig(str, 129), serviceConnection, str2);
    }

    public void unbindService(String str, String str2, ServiceConnection serviceConnection, String str3) {
        unbindService(str, str2, 129, serviceConnection, str3);
    }

    public void unbindService(String str, String str2, int i, ServiceConnection serviceConnection, String str3) {
        unbindService(new ConnectionStatusConfig(str, str2, i), serviceConnection, str3);
    }

    public void unbindService(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        unbindService(new ConnectionStatusConfig(componentName, 129), serviceConnection, str);
    }
}
