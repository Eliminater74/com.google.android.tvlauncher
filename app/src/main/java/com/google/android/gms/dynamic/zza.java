package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzf;

import java.util.LinkedList;

@Hide
/* compiled from: DeferredLifecycleHelper */
public abstract class zza<T extends LifecycleDelegate> {
    private final zzo<T> zzd = new zzb(this);
    /* access modifiers changed from: private */
    public T zza;
    /* access modifiers changed from: private */
    public Bundle zzb;
    /* access modifiers changed from: private */
    public LinkedList<zzi> zzc;

    public static void zzb(FrameLayout frameLayout) {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        Context context = frameLayout.getContext();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(context);
        String zzc2 = zzf.zzc(context, isGooglePlayServicesAvailable);
        String zze = zzf.zze(context, isGooglePlayServicesAvailable);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        TextView textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        textView.setText(zzc2);
        linearLayout.addView(textView);
        Intent errorResolutionIntent = instance.getErrorResolutionIntent(context, isGooglePlayServicesAvailable, null);
        if (errorResolutionIntent != null) {
            Button button = new Button(context);
            button.setId(16908313);
            button.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            button.setText(zze);
            linearLayout.addView(button);
            button.setOnClickListener(new zzf(context, errorResolutionIntent));
        }
    }

    /* access modifiers changed from: protected */
    public abstract void zza(zzo<T> zzo);

    public final T zza() {
        return this.zza;
    }

    private final void zza(int i) {
        while (!this.zzc.isEmpty() && this.zzc.getLast().zza() >= i) {
            this.zzc.removeLast();
        }
    }

    private final void zza(Bundle bundle, zzi zzi) {
        T t = this.zza;
        if (t != null) {
            zzi.zza(t);
            return;
        }
        if (this.zzc == null) {
            this.zzc = new LinkedList<>();
        }
        this.zzc.add(zzi);
        if (bundle != null) {
            Bundle bundle2 = this.zzb;
            if (bundle2 == null) {
                this.zzb = (Bundle) bundle.clone();
            } else {
                bundle2.putAll(bundle);
            }
        }
        zza(this.zzd);
    }

    public final void zza(Activity activity, Bundle bundle, Bundle bundle2) {
        zza(bundle2, new zzc(this, activity, bundle, bundle2));
    }

    public final void zza(Bundle bundle) {
        zza(bundle, new zzd(this, bundle));
    }

    public final View zza(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        zza(bundle, new zze(this, frameLayout, layoutInflater, viewGroup, bundle));
        if (this.zza == null) {
            zza(frameLayout);
        }
        return frameLayout;
    }

    /* access modifiers changed from: protected */
    public void zza(FrameLayout frameLayout) {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        Context context = frameLayout.getContext();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(context);
        String zzc2 = zzf.zzc(context, isGooglePlayServicesAvailable);
        String zze = zzf.zze(context, isGooglePlayServicesAvailable);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        TextView textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        textView.setText(zzc2);
        linearLayout.addView(textView);
        Intent errorResolutionIntent = instance.getErrorResolutionIntent(context, isGooglePlayServicesAvailable, null);
        if (errorResolutionIntent != null) {
            Button button = new Button(context);
            button.setId(16908313);
            button.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            button.setText(zze);
            linearLayout.addView(button);
            button.setOnClickListener(new zzf(context, errorResolutionIntent));
        }
    }

    public final void zzb() {
        zza((Bundle) null, new zzg(this));
    }

    public final void zzc() {
        zza((Bundle) null, new zzh(this));
    }

    public final void zzd() {
        T t = this.zza;
        if (t != null) {
            t.onPause();
        } else {
            zza(5);
        }
    }

    public final void zze() {
        T t = this.zza;
        if (t != null) {
            t.onStop();
        } else {
            zza(4);
        }
    }

    public final void zzf() {
        T t = this.zza;
        if (t != null) {
            t.onDestroyView();
        } else {
            zza(2);
        }
    }

    public final void zzg() {
        T t = this.zza;
        if (t != null) {
            t.onDestroy();
        } else {
            zza(1);
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public final void zzb(android.os.Bundle r2) {
        /*
            r1 = this;
            T r0 = r1.zza
            if (r0 == 0) goto L_0x0008
            r0.onSaveInstanceState(r2)
            return
        L_0x0008:
            android.os.Bundle r0 = r1.zzb
            if (r0 == 0) goto L_0x000f
            r2.putAll(r0)
        L_0x000f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamic.zza.zzb(android.os.Bundle):void");
    }

    public final void zzh() {
        T t = this.zza;
        if (t != null) {
            t.onLowMemory();
        }
    }
}
