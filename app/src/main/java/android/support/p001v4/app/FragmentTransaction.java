package android.support.p001v4.app;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.p001v4.view.ViewCompat;
import android.view.View;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/* renamed from: android.support.v4.app.FragmentTransaction */
public abstract class FragmentTransaction {
    static final int OP_ADD = 1;
    static final int OP_ATTACH = 7;
    static final int OP_DETACH = 6;
    static final int OP_HIDE = 4;
    static final int OP_NULL = 0;
    static final int OP_REMOVE = 3;
    static final int OP_REPLACE = 2;
    static final int OP_SET_MAX_LIFECYCLE = 10;
    static final int OP_SET_PRIMARY_NAV = 8;
    static final int OP_SHOW = 5;
    static final int OP_UNSET_MAX_LIFECYCLE = 11;
    static final int OP_UNSET_PRIMARY_NAV = 9;
    public static final int TRANSIT_ENTER_MASK = 4096;
    public static final int TRANSIT_EXIT_MASK = 8192;
    public static final int TRANSIT_FRAGMENT_CLOSE = 8194;
    public static final int TRANSIT_FRAGMENT_FADE = 4099;
    public static final int TRANSIT_FRAGMENT_OPEN = 4097;
    public static final int TRANSIT_NONE = 0;
    public static final int TRANSIT_UNSET = -1;
    boolean mAddToBackStack;
    boolean mAllowAddToBackStack = true;
    int mBreadCrumbShortTitleRes;
    CharSequence mBreadCrumbShortTitleText;
    int mBreadCrumbTitleRes;
    CharSequence mBreadCrumbTitleText;
    ArrayList<Runnable> mCommitRunnables;
    int mEnterAnim;
    int mExitAnim;
    @Nullable
    String mName;
    ArrayList<C0075Op> mOps = new ArrayList<>();
    int mPopEnterAnim;
    int mPopExitAnim;
    boolean mReorderingAllowed = false;
    ArrayList<String> mSharedElementSourceNames;
    ArrayList<String> mSharedElementTargetNames;
    int mTransition;
    int mTransitionStyle;

    public abstract int commit();

    public abstract int commitAllowingStateLoss();

    public abstract void commitNow();

    public abstract void commitNowAllowingStateLoss();

    /* renamed from: android.support.v4.app.FragmentTransaction$Op */
    static final class C0075Op {
        int mCmd;
        int mEnterAnim;
        int mExitAnim;
        Fragment mFragment;
        int mPopEnterAnim;
        int mPopExitAnim;
        Lifecycle.State mState;

        C0075Op() {
        }

        C0075Op(int cmd, Fragment fragment) {
            this.mCmd = cmd;
            this.mFragment = fragment;
            this.mState = Lifecycle.State.RESUMED;
        }

        C0075Op(int cmd, Fragment fragment, Lifecycle.State state) {
            this.mCmd = cmd;
            this.mFragment = fragment;
            this.mState = state;
        }
    }

    /* access modifiers changed from: package-private */
    public void addOp(C0075Op op) {
        this.mOps.add(op);
        op.mEnterAnim = this.mEnterAnim;
        op.mExitAnim = this.mExitAnim;
        op.mPopEnterAnim = this.mPopEnterAnim;
        op.mPopExitAnim = this.mPopExitAnim;
    }

    @NonNull
    public FragmentTransaction add(@NonNull Fragment fragment, @Nullable String tag) {
        doAddOp(0, fragment, tag, 1);
        return this;
    }

    @NonNull
    public FragmentTransaction add(@IdRes int containerViewId, @NonNull Fragment fragment) {
        doAddOp(containerViewId, fragment, null, 1);
        return this;
    }

    @NonNull
    public FragmentTransaction add(@IdRes int containerViewId, @NonNull Fragment fragment, @Nullable String tag) {
        doAddOp(containerViewId, fragment, tag, 1);
        return this;
    }

    /* access modifiers changed from: package-private */
    public void doAddOp(int containerViewId, Fragment fragment, @Nullable String tag, int opcmd) {
        Class fragmentClass = fragment.getClass();
        int modifiers = fragmentClass.getModifiers();
        if (fragmentClass.isAnonymousClass() || !Modifier.isPublic(modifiers) || (fragmentClass.isMemberClass() && !Modifier.isStatic(modifiers))) {
            throw new IllegalStateException("Fragment " + fragmentClass.getCanonicalName() + " must be a public static class to be  properly recreated from instance state.");
        }
        if (tag != null) {
            if (fragment.mTag == null || tag.equals(fragment.mTag)) {
                fragment.mTag = tag;
            } else {
                throw new IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.mTag + " now " + tag);
            }
        }
        if (containerViewId != 0) {
            if (containerViewId == -1) {
                throw new IllegalArgumentException("Can't add fragment " + fragment + " with tag " + tag + " to container view with no id");
            } else if (fragment.mFragmentId == 0 || fragment.mFragmentId == containerViewId) {
                fragment.mFragmentId = containerViewId;
                fragment.mContainerId = containerViewId;
            } else {
                throw new IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.mFragmentId + " now " + containerViewId);
            }
        }
        addOp(new C0075Op(opcmd, fragment));
    }

    @NonNull
    public FragmentTransaction replace(@IdRes int containerViewId, @NonNull Fragment fragment) {
        return replace(containerViewId, fragment, null);
    }

    @NonNull
    public FragmentTransaction replace(@IdRes int containerViewId, @NonNull Fragment fragment, @Nullable String tag) {
        if (containerViewId != 0) {
            doAddOp(containerViewId, fragment, tag, 2);
            return this;
        }
        throw new IllegalArgumentException("Must use non-zero containerViewId");
    }

    @NonNull
    public FragmentTransaction remove(@NonNull Fragment fragment) {
        addOp(new C0075Op(3, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction hide(@NonNull Fragment fragment) {
        addOp(new C0075Op(4, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction show(@NonNull Fragment fragment) {
        addOp(new C0075Op(5, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction detach(@NonNull Fragment fragment) {
        addOp(new C0075Op(6, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction attach(@NonNull Fragment fragment) {
        addOp(new C0075Op(7, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction setPrimaryNavigationFragment(@Nullable Fragment fragment) {
        addOp(new C0075Op(8, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction setMaxLifecycle(@NonNull Fragment fragment, @NonNull Lifecycle.State state) {
        addOp(new C0075Op(10, fragment, state));
        return this;
    }

    public boolean isEmpty() {
        return this.mOps.isEmpty();
    }

    @NonNull
    public FragmentTransaction setCustomAnimations(@AnimRes @AnimatorRes int enter, @AnimRes @AnimatorRes int exit) {
        return setCustomAnimations(enter, exit, 0, 0);
    }

    @NonNull
    public FragmentTransaction setCustomAnimations(@AnimRes @AnimatorRes int enter, @AnimRes @AnimatorRes int exit, @AnimRes @AnimatorRes int popEnter, @AnimRes @AnimatorRes int popExit) {
        this.mEnterAnim = enter;
        this.mExitAnim = exit;
        this.mPopEnterAnim = popEnter;
        this.mPopExitAnim = popExit;
        return this;
    }

    @NonNull
    public FragmentTransaction addSharedElement(@NonNull View sharedElement, @NonNull String name) {
        if (FragmentTransition.supportsTransition()) {
            String transitionName = ViewCompat.getTransitionName(sharedElement);
            if (transitionName != null) {
                if (this.mSharedElementSourceNames == null) {
                    this.mSharedElementSourceNames = new ArrayList<>();
                    this.mSharedElementTargetNames = new ArrayList<>();
                } else if (this.mSharedElementTargetNames.contains(name)) {
                    throw new IllegalArgumentException("A shared element with the target name '" + name + "' has already been added to the transaction.");
                } else if (this.mSharedElementSourceNames.contains(transitionName)) {
                    throw new IllegalArgumentException("A shared element with the source name '" + transitionName + "' has already been added to the transaction.");
                }
                this.mSharedElementSourceNames.add(transitionName);
                this.mSharedElementTargetNames.add(name);
            } else {
                throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
            }
        }
        return this;
    }

    @NonNull
    public FragmentTransaction setTransition(int transition) {
        this.mTransition = transition;
        return this;
    }

    @NonNull
    public FragmentTransaction setTransitionStyle(@StyleRes int styleRes) {
        this.mTransitionStyle = styleRes;
        return this;
    }

    @NonNull
    public FragmentTransaction addToBackStack(@Nullable String name) {
        if (this.mAllowAddToBackStack) {
            this.mAddToBackStack = true;
            this.mName = name;
            return this;
        }
        throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
    }

    public boolean isAddToBackStackAllowed() {
        return this.mAllowAddToBackStack;
    }

    @NonNull
    public FragmentTransaction disallowAddToBackStack() {
        if (!this.mAddToBackStack) {
            this.mAllowAddToBackStack = false;
            return this;
        }
        throw new IllegalStateException("This transaction is already being added to the back stack");
    }

    @NonNull
    public FragmentTransaction setBreadCrumbTitle(@StringRes int res) {
        this.mBreadCrumbTitleRes = res;
        this.mBreadCrumbTitleText = null;
        return this;
    }

    @NonNull
    public FragmentTransaction setBreadCrumbTitle(@Nullable CharSequence text) {
        this.mBreadCrumbTitleRes = 0;
        this.mBreadCrumbTitleText = text;
        return this;
    }

    @NonNull
    public FragmentTransaction setBreadCrumbShortTitle(@StringRes int res) {
        this.mBreadCrumbShortTitleRes = res;
        this.mBreadCrumbShortTitleText = null;
        return this;
    }

    @NonNull
    public FragmentTransaction setBreadCrumbShortTitle(@Nullable CharSequence text) {
        this.mBreadCrumbShortTitleRes = 0;
        this.mBreadCrumbShortTitleText = text;
        return this;
    }

    @NonNull
    public FragmentTransaction setReorderingAllowed(boolean reorderingAllowed) {
        this.mReorderingAllowed = reorderingAllowed;
        return this;
    }

    @Deprecated
    @NonNull
    public FragmentTransaction setAllowOptimization(boolean allowOptimization) {
        return setReorderingAllowed(allowOptimization);
    }

    @NonNull
    public FragmentTransaction runOnCommit(@NonNull Runnable runnable) {
        disallowAddToBackStack();
        if (this.mCommitRunnables == null) {
            this.mCommitRunnables = new ArrayList<>();
        }
        this.mCommitRunnables.add(runnable);
        return this;
    }
}
