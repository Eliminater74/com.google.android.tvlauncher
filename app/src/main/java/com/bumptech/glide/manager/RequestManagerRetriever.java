package com.bumptech.glide.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.app.FragmentActivity;
import android.support.p001v4.util.ArrayMap;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RequestManagerRetriever implements Handler.Callback {
    @VisibleForTesting
    static final String FRAGMENT_TAG = "com.bumptech.glide.manager";
    private static final RequestManagerFactory DEFAULT_FACTORY = new RequestManagerFactory() {
        @NonNull
        public RequestManager build(@NonNull Glide glide, @NonNull Lifecycle lifecycle, @NonNull RequestManagerTreeNode requestManagerTreeNode, @NonNull Context context) {
            return new RequestManager(glide, lifecycle, requestManagerTreeNode, context);
        }
    };
    private static final String FRAGMENT_INDEX_KEY = "key";
    private static final int ID_REMOVE_FRAGMENT_MANAGER = 1;
    private static final int ID_REMOVE_SUPPORT_FRAGMENT_MANAGER = 2;
    private static final String TAG = "RMRetriever";
    @VisibleForTesting
    final Map<FragmentManager, RequestManagerFragment> pendingRequestManagerFragments = new HashMap();
    @VisibleForTesting
    final Map<android.support.p001v4.app.FragmentManager, SupportRequestManagerFragment> pendingSupportRequestManagerFragments = new HashMap();
    private final RequestManagerFactory factory;
    private final Handler handler;
    private final Bundle tempBundle = new Bundle();
    private final ArrayMap<View, Fragment> tempViewToFragment = new ArrayMap<>();
    private final ArrayMap<View, android.support.p001v4.app.Fragment> tempViewToSupportFragment = new ArrayMap<>();
    private volatile RequestManager applicationManager;

    public RequestManagerRetriever(@Nullable RequestManagerFactory factory2) {
        this.factory = factory2 != null ? factory2 : DEFAULT_FACTORY;
        this.handler = new Handler(Looper.getMainLooper(), this);
    }

    private static void findAllSupportFragmentsWithViews(@Nullable Collection<android.support.p001v4.app.Fragment> topLevelFragments, @NonNull Map<View, android.support.p001v4.app.Fragment> result) {
        if (topLevelFragments != null) {
            for (android.support.p001v4.app.Fragment fragment : topLevelFragments) {
                if (!(fragment == null || fragment.getView() == null)) {
                    result.put(fragment.getView(), fragment);
                    findAllSupportFragmentsWithViews(fragment.getChildFragmentManager().getFragments(), result);
                }
            }
        }
    }

    @TargetApi(17)
    private static void assertNotDestroyed(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 17 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

    private static boolean isActivityVisible(Activity activity) {
        return !activity.isFinishing();
    }

    @NonNull
    private RequestManager getApplicationManager(@NonNull Context context) {
        if (this.applicationManager == null) {
            synchronized (this) {
                if (this.applicationManager == null) {
                    this.applicationManager = this.factory.build(Glide.get(context.getApplicationContext()), new ApplicationLifecycle(), new EmptyRequestManagerTreeNode(), context.getApplicationContext());
                }
            }
        }
        return this.applicationManager;
    }

    @NonNull
    public RequestManager get(@NonNull Context context) {
        if (context != null) {
            if (Util.isOnMainThread() && !(context instanceof Application)) {
                if (context instanceof FragmentActivity) {
                    return get((FragmentActivity) context);
                }
                if (context instanceof Activity) {
                    return get((Activity) context);
                }
                if (context instanceof ContextWrapper) {
                    return get(((ContextWrapper) context).getBaseContext());
                }
            }
            return getApplicationManager(context);
        }
        throw new IllegalArgumentException("You cannot start a load on a null Context");
    }

    @NonNull
    public RequestManager get(@NonNull FragmentActivity activity) {
        if (Util.isOnBackgroundThread()) {
            return get(activity.getApplicationContext());
        }
        assertNotDestroyed(activity);
        return supportFragmentGet(activity, activity.getSupportFragmentManager(), null, isActivityVisible(activity));
    }

    @NonNull
    public RequestManager get(@NonNull android.support.p001v4.app.Fragment fragment) {
        Preconditions.checkNotNull(fragment.getActivity(), "You cannot start a load on a fragment before it is attached or after it is destroyed");
        if (Util.isOnBackgroundThread()) {
            return get(fragment.getActivity().getApplicationContext());
        }
        return supportFragmentGet(fragment.getActivity(), fragment.getChildFragmentManager(), fragment, fragment.isVisible());
    }

    @NonNull
    public RequestManager get(@NonNull Activity activity) {
        if (Util.isOnBackgroundThread()) {
            return get(activity.getApplicationContext());
        }
        assertNotDestroyed(activity);
        return fragmentGet(activity, activity.getFragmentManager(), null, isActivityVisible(activity));
    }

    /* JADX INFO: Multiple debug info for r1v2 android.app.Fragment: [D('fragment' android.app.Fragment), D('fragment' android.support.v4.app.Fragment)] */
    @NonNull
    public RequestManager get(@NonNull View view) {
        if (Util.isOnBackgroundThread()) {
            return get(view.getContext().getApplicationContext());
        }
        Preconditions.checkNotNull(view);
        Preconditions.checkNotNull(view.getContext(), "Unable to obtain a request manager for a view without a Context");
        Activity activity = findActivity(view.getContext());
        if (activity == null) {
            return get(view.getContext().getApplicationContext());
        }
        if (activity instanceof FragmentActivity) {
            android.support.p001v4.app.Fragment fragment = findSupportFragment(view, (FragmentActivity) activity);
            return fragment != null ? get(fragment) : get(activity);
        }
        Fragment fragment2 = findFragment(view, activity);
        if (fragment2 == null) {
            return get(activity);
        }
        return get(fragment2);
    }

    /* JADX WARN: Type inference failed for: r3v6, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.support.p001v4.app.Fragment findSupportFragment(@android.support.annotation.NonNull android.view.View r5, @android.support.annotation.NonNull android.support.p001v4.app.FragmentActivity r6) {
        /*
            r4 = this;
            android.support.v4.util.ArrayMap<android.view.View, android.support.v4.app.Fragment> r0 = r4.tempViewToSupportFragment
            r0.clear()
            android.support.v4.app.FragmentManager r0 = r6.getSupportFragmentManager()
            java.util.List r0 = r0.getFragments()
            android.support.v4.util.ArrayMap<android.view.View, android.support.v4.app.Fragment> r1 = r4.tempViewToSupportFragment
            findAllSupportFragmentsWithViews(r0, r1)
            r0 = 0
            r1 = 16908290(0x1020002, float:2.3877235E-38)
            android.view.View r1 = r6.findViewById(r1)
            r2 = r5
        L_0x001c:
            boolean r3 = r2.equals(r1)
            if (r3 != 0) goto L_0x003e
            android.support.v4.util.ArrayMap<android.view.View, android.support.v4.app.Fragment> r3 = r4.tempViewToSupportFragment
            java.lang.Object r3 = r3.get(r2)
            r0 = r3
            android.support.v4.app.Fragment r0 = (android.support.p001v4.app.Fragment) r0
            if (r0 == 0) goto L_0x002e
            goto L_0x003e
        L_0x002e:
            android.view.ViewParent r3 = r2.getParent()
            boolean r3 = r3 instanceof android.view.View
            if (r3 == 0) goto L_0x003e
            android.view.ViewParent r3 = r2.getParent()
            r2 = r3
            android.view.View r2 = (android.view.View) r2
            goto L_0x001c
        L_0x003e:
            android.support.v4.util.ArrayMap<android.view.View, android.support.v4.app.Fragment> r3 = r4.tempViewToSupportFragment
            r3.clear()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.manager.RequestManagerRetriever.findSupportFragment(android.view.View, android.support.v4.app.FragmentActivity):android.support.v4.app.Fragment");
    }

    /* JADX WARN: Type inference failed for: r3v6, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.support.annotation.Nullable
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.app.Fragment findFragment(@android.support.annotation.NonNull android.view.View r5, @android.support.annotation.NonNull android.app.Activity r6) {
        /*
            r4 = this;
            android.support.v4.util.ArrayMap<android.view.View, android.app.Fragment> r0 = r4.tempViewToFragment
            r0.clear()
            android.app.FragmentManager r0 = r6.getFragmentManager()
            android.support.v4.util.ArrayMap<android.view.View, android.app.Fragment> r1 = r4.tempViewToFragment
            r4.findAllFragmentsWithViews(r0, r1)
            r0 = 0
            r1 = 16908290(0x1020002, float:2.3877235E-38)
            android.view.View r1 = r6.findViewById(r1)
            r2 = r5
        L_0x0017:
            boolean r3 = r2.equals(r1)
            if (r3 != 0) goto L_0x0039
            android.support.v4.util.ArrayMap<android.view.View, android.app.Fragment> r3 = r4.tempViewToFragment
            java.lang.Object r3 = r3.get(r2)
            r0 = r3
            android.app.Fragment r0 = (android.app.Fragment) r0
            if (r0 == 0) goto L_0x0029
            goto L_0x0039
        L_0x0029:
            android.view.ViewParent r3 = r2.getParent()
            boolean r3 = r3 instanceof android.view.View
            if (r3 == 0) goto L_0x0039
            android.view.ViewParent r3 = r2.getParent()
            r2 = r3
            android.view.View r2 = (android.view.View) r2
            goto L_0x0017
        L_0x0039:
            android.support.v4.util.ArrayMap<android.view.View, android.app.Fragment> r3 = r4.tempViewToFragment
            r3.clear()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.manager.RequestManagerRetriever.findFragment(android.view.View, android.app.Activity):android.app.Fragment");
    }

    @TargetApi(26)
    @Deprecated
    private void findAllFragmentsWithViews(@NonNull FragmentManager fragmentManager, @NonNull ArrayMap<View, Fragment> result) {
        if (Build.VERSION.SDK_INT >= 26) {
            for (Fragment fragment : fragmentManager.getFragments()) {
                if (fragment.getView() != null) {
                    result.put(fragment.getView(), fragment);
                    findAllFragmentsWithViews(fragment.getChildFragmentManager(), result);
                }
            }
            return;
        }
        findAllFragmentsWithViewsPreO(fragmentManager, result);
    }

    @Deprecated
    private void findAllFragmentsWithViewsPreO(@NonNull FragmentManager fragmentManager, @NonNull ArrayMap<View, Fragment> result) {
        int index = 0;
        while (true) {
            int index2 = index + 1;
            this.tempBundle.putInt("key", index);
            Fragment fragment = null;
            try {
                fragment = fragmentManager.getFragment(this.tempBundle, "key");
            } catch (Exception e) {
            }
            if (fragment != null) {
                if (fragment.getView() != null) {
                    result.put(fragment.getView(), fragment);
                    if (Build.VERSION.SDK_INT >= 17) {
                        findAllFragmentsWithViews(fragment.getChildFragmentManager(), result);
                    }
                }
                index = index2;
            } else {
                return;
            }
        }
    }

    @Nullable
    private Activity findActivity(@NonNull Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    @TargetApi(17)
    @Deprecated
    @NonNull
    public RequestManager get(@NonNull Fragment fragment) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        } else if (Util.isOnBackgroundThread() || Build.VERSION.SDK_INT < 17) {
            return get(fragment.getActivity().getApplicationContext());
        } else {
            return fragmentGet(fragment.getActivity(), fragment.getChildFragmentManager(), fragment, fragment.isVisible());
        }
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    @NonNull
    public RequestManagerFragment getRequestManagerFragment(Activity activity) {
        return getRequestManagerFragment(activity.getFragmentManager(), null, isActivityVisible(activity));
    }

    @NonNull
    private RequestManagerFragment getRequestManagerFragment(@NonNull FragmentManager fm, @Nullable Fragment parentHint, boolean isParentVisible) {
        RequestManagerFragment current = (RequestManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null && (current = this.pendingRequestManagerFragments.get(fm)) == null) {
            current = new RequestManagerFragment();
            current.setParentFragmentHint(parentHint);
            if (isParentVisible) {
                current.getGlideLifecycle().onStart();
            }
            this.pendingRequestManagerFragments.put(fm, current);
            fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
            this.handler.obtainMessage(1, fm).sendToTarget();
        }
        return current;
    }

    @Deprecated
    @NonNull
    private RequestManager fragmentGet(@NonNull Context context, @NonNull FragmentManager fm, @Nullable Fragment parentHint, boolean isParentVisible) {
        RequestManagerFragment current = getRequestManagerFragment(fm, parentHint, isParentVisible);
        RequestManager requestManager = current.getRequestManager();
        if (requestManager != null) {
            return requestManager;
        }
        RequestManager requestManager2 = this.factory.build(Glide.get(context), current.getGlideLifecycle(), current.getRequestManagerTreeNode(), context);
        current.setRequestManager(requestManager2);
        return requestManager2;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public SupportRequestManagerFragment getSupportRequestManagerFragment(FragmentActivity activity) {
        return getSupportRequestManagerFragment(activity.getSupportFragmentManager(), null, isActivityVisible(activity));
    }

    @NonNull
    private SupportRequestManagerFragment getSupportRequestManagerFragment(@NonNull android.support.p001v4.app.FragmentManager fm, @Nullable android.support.p001v4.app.Fragment parentHint, boolean isParentVisible) {
        SupportRequestManagerFragment current = (SupportRequestManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null && (current = this.pendingSupportRequestManagerFragments.get(fm)) == null) {
            current = new SupportRequestManagerFragment();
            current.setParentFragmentHint(parentHint);
            if (isParentVisible) {
                current.getGlideLifecycle().onStart();
            }
            this.pendingSupportRequestManagerFragments.put(fm, current);
            fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
            this.handler.obtainMessage(2, fm).sendToTarget();
        }
        return current;
    }

    @NonNull
    private RequestManager supportFragmentGet(@NonNull Context context, @NonNull android.support.p001v4.app.FragmentManager fm, @Nullable android.support.p001v4.app.Fragment parentHint, boolean isParentVisible) {
        SupportRequestManagerFragment current = getSupportRequestManagerFragment(fm, parentHint, isParentVisible);
        RequestManager requestManager = current.getRequestManager();
        if (requestManager != null) {
            return requestManager;
        }
        RequestManager requestManager2 = this.factory.build(Glide.get(context), current.getGlideLifecycle(), current.getRequestManagerTreeNode(), context);
        current.setRequestManager(requestManager2);
        return requestManager2;
    }

    public boolean handleMessage(Message message) {
        boolean handled = true;
        Object removed = null;
        Object key = null;
        int i = message.what;
        if (i == 1) {
            Object fm = (FragmentManager) message.obj;
            key = fm;
            removed = this.pendingRequestManagerFragments.remove(fm);
        } else if (i != 2) {
            handled = false;
        } else {
            Object supportFm = (android.support.p001v4.app.FragmentManager) message.obj;
            key = supportFm;
            removed = this.pendingSupportRequestManagerFragments.remove(supportFm);
        }
        if (handled && removed == null && Log.isLoggable(TAG, 5)) {
            String valueOf = String.valueOf(key);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 61);
            sb.append("Failed to remove expected request manager fragment, manager: ");
            sb.append(valueOf);
            Log.w(TAG, sb.toString());
        }
        return handled;
    }

    public interface RequestManagerFactory {
        @NonNull
        RequestManager build(@NonNull Glide glide, @NonNull Lifecycle lifecycle, @NonNull RequestManagerTreeNode requestManagerTreeNode, @NonNull Context context);
    }
}
