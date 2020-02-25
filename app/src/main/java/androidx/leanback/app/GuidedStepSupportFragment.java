package androidx.leanback.app;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.p001v4.app.ActivityCompat;
import android.support.p001v4.app.Fragment;
import android.support.p001v4.app.FragmentActivity;
import android.support.p001v4.app.FragmentManager;
import android.support.p001v4.app.FragmentTransaction;
import android.support.p001v4.view.GravityCompat;
import android.support.p004v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.leanback.C0364R;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.widget.DiffCallback;
import androidx.leanback.widget.GuidanceStylist;
import androidx.leanback.widget.GuidedAction;
import androidx.leanback.widget.GuidedActionAdapter;
import androidx.leanback.widget.GuidedActionAdapterGroup;
import androidx.leanback.widget.GuidedActionsStylist;
import androidx.leanback.widget.NonOverlappingLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class GuidedStepSupportFragment extends Fragment implements GuidedActionAdapter.FocusListener {
    public static final String EXTRA_UI_STYLE = "uiStyle";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final int SLIDE_FROM_BOTTOM = 1;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final int SLIDE_FROM_SIDE = 0;
    public static final int UI_STYLE_ACTIVITY_ROOT = 2;
    @Deprecated
    public static final int UI_STYLE_DEFAULT = 0;
    public static final int UI_STYLE_ENTRANCE = 1;
    public static final int UI_STYLE_REPLACE = 0;
    private static final boolean DEBUG = false;
    private static final String ENTRY_NAME_ENTRANCE = "GuidedStepEntrance";
    private static final String ENTRY_NAME_REPLACE = "GuidedStepDefault";
    private static final String EXTRA_ACTION_PREFIX = "action_";
    private static final String EXTRA_BUTTON_ACTION_PREFIX = "buttonaction_";
    private static final boolean IS_FRAMEWORK_FRAGMENT = false;
    private static final String TAG = "GuidedStepF";
    private static final String TAG_LEAN_BACK_ACTIONS_FRAGMENT = "leanBackGuidedStepSupportFragment";
    GuidedActionsStylist mActionsStylist;
    private int entranceTransitionType = 0;
    private List<GuidedAction> mActions = new ArrayList();
    private GuidedActionAdapter mAdapter;
    private GuidedActionAdapterGroup mAdapterGroup;
    private List<GuidedAction> mButtonActions = new ArrayList();
    private GuidedActionsStylist mButtonActionsStylist;
    private GuidedActionAdapter mButtonAdapter;
    private GuidanceStylist mGuidanceStylist;
    private GuidedActionAdapter mSubAdapter;
    private ContextThemeWrapper mThemeWrapper;

    public GuidedStepSupportFragment() {
        onProvideFragmentTransitions();
    }

    public static int add(FragmentManager fragmentManager, GuidedStepSupportFragment fragment) {
        return add(fragmentManager, fragment, 16908290);
    }

    public static int add(FragmentManager fragmentManager, GuidedStepSupportFragment fragment, int id) {
        GuidedStepSupportFragment current = getCurrentGuidedStepSupportFragment(fragmentManager);
        int i = 1;
        boolean inGuidedStep = current != null;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (inGuidedStep) {
            i = 0;
        }
        fragment.setUiStyle(i);
        ft.addToBackStack(fragment.generateStackEntryName());
        if (current != null) {
            fragment.onAddSharedElementTransition(ft, current);
        }
        return ft.replace(id, fragment, TAG_LEAN_BACK_ACTIONS_FRAGMENT).commit();
    }

    private static void addNonNullSharedElementTransition(FragmentTransaction ft, View subView, String transitionName) {
    }

    static String generateStackEntryName(int uiStyle, Class guidedStepFragmentClass) {
        if (uiStyle == 0) {
            return ENTRY_NAME_REPLACE + guidedStepFragmentClass.getName();
        } else if (uiStyle != 1) {
            return "";
        } else {
            return ENTRY_NAME_ENTRANCE + guidedStepFragmentClass.getName();
        }
    }

    static boolean isStackEntryUiStyleEntrance(String backStackEntryName) {
        return backStackEntryName != null && backStackEntryName.startsWith(ENTRY_NAME_ENTRANCE);
    }

    static String getGuidedStepSupportFragmentClassName(String backStackEntryName) {
        if (backStackEntryName.startsWith(ENTRY_NAME_REPLACE)) {
            return backStackEntryName.substring(ENTRY_NAME_REPLACE.length());
        }
        if (backStackEntryName.startsWith(ENTRY_NAME_ENTRANCE)) {
            return backStackEntryName.substring(ENTRY_NAME_ENTRANCE.length());
        }
        return "";
    }

    public static int addAsRoot(FragmentActivity activity, GuidedStepSupportFragment fragment, int id) {
        activity.getWindow().getDecorView();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(TAG_LEAN_BACK_ACTIONS_FRAGMENT) != null) {
            Log.w(TAG, "Fragment is already exists, likely calling addAsRoot() when savedInstanceState is not null in Activity.onCreate().");
            return -1;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        fragment.setUiStyle(2);
        return ft.replace(id, fragment, TAG_LEAN_BACK_ACTIONS_FRAGMENT).commit();
    }

    public static GuidedStepSupportFragment getCurrentGuidedStepSupportFragment(FragmentManager fm) {
        Fragment f = fm.findFragmentByTag(TAG_LEAN_BACK_ACTIONS_FRAGMENT);
        if (f instanceof GuidedStepSupportFragment) {
            return (GuidedStepSupportFragment) f;
        }
        return null;
    }

    static boolean isSaveEnabled(GuidedAction action) {
        return action.isAutoSaveRestoreEnabled() && action.getId() != -1;
    }

    private static boolean isGuidedStepTheme(Context context) {
        int resId = C0364R.attr.guidedStepThemeFlag;
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(resId, typedValue, true) || typedValue.type != 18 || typedValue.data == 0) {
            return false;
        }
        return true;
    }

    public GuidanceStylist onCreateGuidanceStylist() {
        return new GuidanceStylist();
    }

    public GuidedActionsStylist onCreateActionsStylist() {
        return new GuidedActionsStylist();
    }

    public GuidedActionsStylist onCreateButtonActionsStylist() {
        GuidedActionsStylist stylist = new GuidedActionsStylist();
        stylist.setAsButtonActions();
        return stylist;
    }

    public int onProvideTheme() {
        return -1;
    }

    @NonNull
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        return new GuidanceStylist.Guidance("", "", "", null);
    }

    public void onCreateActions(@NonNull List<GuidedAction> list, Bundle savedInstanceState) {
    }

    public void onCreateButtonActions(@NonNull List<GuidedAction> list, Bundle savedInstanceState) {
    }

    public void onGuidedActionClicked(GuidedAction action) {
    }

    public boolean onSubGuidedActionClicked(GuidedAction action) {
        return true;
    }

    public boolean isExpanded() {
        return this.mActionsStylist.isExpanded();
    }

    public boolean isSubActionsExpanded() {
        return this.mActionsStylist.isSubActionsExpanded();
    }

    public void expandSubActions(GuidedAction action) {
        if (action.hasSubActions()) {
            expandAction(action, true);
        }
    }

    public void expandAction(GuidedAction action, boolean withTransition) {
        this.mActionsStylist.expandAction(action, withTransition);
    }

    public void collapseSubActions() {
        collapseAction(true);
    }

    public void collapseAction(boolean withTransition) {
        GuidedActionsStylist guidedActionsStylist = this.mActionsStylist;
        if (guidedActionsStylist != null && guidedActionsStylist.getActionsGridView() != null) {
            this.mActionsStylist.collapseAction(withTransition);
        }
    }

    public void onGuidedActionFocused(GuidedAction action) {
    }

    @Deprecated
    public void onGuidedActionEdited(GuidedAction action) {
    }

    public void onGuidedActionEditCanceled(GuidedAction action) {
        onGuidedActionEdited(action);
    }

    public long onGuidedActionEditedAndProceed(GuidedAction action) {
        onGuidedActionEdited(action);
        return -2;
    }

    /* access modifiers changed from: protected */
    public void onAddSharedElementTransition(FragmentTransaction ft, GuidedStepSupportFragment disappearing) {
        View fragmentView = disappearing.getView();
        addNonNullSharedElementTransition(ft, fragmentView.findViewById(C0364R.C0366id.action_fragment_root), "action_fragment_root");
        addNonNullSharedElementTransition(ft, fragmentView.findViewById(C0364R.C0366id.action_fragment_background), "action_fragment_background");
        addNonNullSharedElementTransition(ft, fragmentView.findViewById(C0364R.C0366id.action_fragment), "action_fragment");
        addNonNullSharedElementTransition(ft, fragmentView.findViewById(C0364R.C0366id.guidedactions_root), "guidedactions_root");
        addNonNullSharedElementTransition(ft, fragmentView.findViewById(C0364R.C0366id.guidedactions_content), "guidedactions_content");
        addNonNullSharedElementTransition(ft, fragmentView.findViewById(C0364R.C0366id.guidedactions_list_background), "guidedactions_list_background");
        addNonNullSharedElementTransition(ft, fragmentView.findViewById(C0364R.C0366id.guidedactions_root2), "guidedactions_root2");
        addNonNullSharedElementTransition(ft, fragmentView.findViewById(C0364R.C0366id.guidedactions_content2), "guidedactions_content2");
        addNonNullSharedElementTransition(ft, fragmentView.findViewById(C0364R.C0366id.guidedactions_list_background2), "guidedactions_list_background2");
    }

    /* access modifiers changed from: package-private */
    public final String generateStackEntryName() {
        return generateStackEntryName(getUiStyle(), getClass());
    }

    public GuidanceStylist getGuidanceStylist() {
        return this.mGuidanceStylist;
    }

    public GuidedActionsStylist getGuidedActionsStylist() {
        return this.mActionsStylist;
    }

    public List<GuidedAction> getButtonActions() {
        return this.mButtonActions;
    }

    public void setButtonActions(List<GuidedAction> actions) {
        this.mButtonActions = actions;
        GuidedActionAdapter guidedActionAdapter = this.mButtonAdapter;
        if (guidedActionAdapter != null) {
            guidedActionAdapter.setActions(this.mButtonActions);
        }
    }

    public GuidedAction findButtonActionById(long id) {
        int index = findButtonActionPositionById(id);
        if (index >= 0) {
            return this.mButtonActions.get(index);
        }
        return null;
    }

    public int findButtonActionPositionById(long id) {
        if (this.mButtonActions == null) {
            return -1;
        }
        for (int i = 0; i < this.mButtonActions.size(); i++) {
            GuidedAction guidedAction = this.mButtonActions.get(i);
            if (this.mButtonActions.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public GuidedActionsStylist getGuidedButtonActionsStylist() {
        return this.mButtonActionsStylist;
    }

    public void notifyButtonActionChanged(int position) {
        GuidedActionAdapter guidedActionAdapter = this.mButtonAdapter;
        if (guidedActionAdapter != null) {
            guidedActionAdapter.notifyItemChanged(position);
        }
    }

    public View getButtonActionItemView(int position) {
        RecyclerView.ViewHolder holder = this.mButtonActionsStylist.getActionsGridView().findViewHolderForPosition(position);
        if (holder == null) {
            return null;
        }
        return holder.itemView;
    }

    public int getSelectedButtonActionPosition() {
        return this.mButtonActionsStylist.getActionsGridView().getSelectedPosition();
    }

    public void setSelectedButtonActionPosition(int position) {
        this.mButtonActionsStylist.getActionsGridView().setSelectedPosition(position);
    }

    public List<GuidedAction> getActions() {
        return this.mActions;
    }

    public void setActions(List<GuidedAction> actions) {
        this.mActions = actions;
        GuidedActionAdapter guidedActionAdapter = this.mAdapter;
        if (guidedActionAdapter != null) {
            guidedActionAdapter.setActions(this.mActions);
        }
    }

    public GuidedAction findActionById(long id) {
        int index = findActionPositionById(id);
        if (index >= 0) {
            return this.mActions.get(index);
        }
        return null;
    }

    public int findActionPositionById(long id) {
        if (this.mActions == null) {
            return -1;
        }
        for (int i = 0; i < this.mActions.size(); i++) {
            GuidedAction guidedAction = this.mActions.get(i);
            if (this.mActions.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public void setActionsDiffCallback(DiffCallback<GuidedAction> diffCallback) {
        this.mAdapter.setDiffCallback(diffCallback);
    }

    public void notifyActionChanged(int position) {
        GuidedActionAdapter guidedActionAdapter = this.mAdapter;
        if (guidedActionAdapter != null) {
            guidedActionAdapter.notifyItemChanged(position);
        }
    }

    public View getActionItemView(int position) {
        RecyclerView.ViewHolder holder = this.mActionsStylist.getActionsGridView().findViewHolderForPosition(position);
        if (holder == null) {
            return null;
        }
        return holder.itemView;
    }

    public int getSelectedActionPosition() {
        return this.mActionsStylist.getActionsGridView().getSelectedPosition();
    }

    public void setSelectedActionPosition(int position) {
        this.mActionsStylist.getActionsGridView().setSelectedPosition(position);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: androidx.leanback.transition.TransitionHelper.exclude(java.lang.Object, int, boolean):void
     arg types: [java.lang.Object, int, int]
     candidates:
      androidx.leanback.transition.TransitionHelper.exclude(java.lang.Object, android.view.View, boolean):void
      androidx.leanback.transition.TransitionHelper.exclude(java.lang.Object, int, boolean):void */
    /* access modifiers changed from: protected */
    public void onProvideFragmentTransitions() {
        if (Build.VERSION.SDK_INT >= 21) {
            int uiStyle = getUiStyle();
            if (uiStyle == 0) {
                Object enterTransition = TransitionHelper.createFadeAndShortSlide(GravityCompat.END);
                TransitionHelper.exclude(enterTransition, C0364R.C0366id.guidedstep_background, true);
                TransitionHelper.exclude(enterTransition, C0364R.C0366id.guidedactions_sub_list_background, true);
                setEnterTransition(enterTransition);
                Object fade = TransitionHelper.createFadeTransition(3);
                TransitionHelper.include(fade, C0364R.C0366id.guidedactions_sub_list_background);
                Object changeBounds = TransitionHelper.createChangeBounds(false);
                Object sharedElementTransition = TransitionHelper.createTransitionSet(false);
                TransitionHelper.addTransition(sharedElementTransition, fade);
                TransitionHelper.addTransition(sharedElementTransition, changeBounds);
                setSharedElementEnterTransition(sharedElementTransition);
            } else if (uiStyle == 1) {
                if (this.entranceTransitionType == 0) {
                    Object fade2 = TransitionHelper.createFadeTransition(3);
                    TransitionHelper.include(fade2, C0364R.C0366id.guidedstep_background);
                    Object slideFromSide = TransitionHelper.createFadeAndShortSlide(GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK);
                    TransitionHelper.include(slideFromSide, C0364R.C0366id.content_fragment);
                    TransitionHelper.include(slideFromSide, C0364R.C0366id.action_fragment_root);
                    Object enterTransition2 = TransitionHelper.createTransitionSet(false);
                    TransitionHelper.addTransition(enterTransition2, fade2);
                    TransitionHelper.addTransition(enterTransition2, slideFromSide);
                    setEnterTransition(enterTransition2);
                } else {
                    Object slideFromBottom = TransitionHelper.createFadeAndShortSlide(80);
                    TransitionHelper.include(slideFromBottom, C0364R.C0366id.guidedstep_background_view_root);
                    Object enterTransition3 = TransitionHelper.createTransitionSet(false);
                    TransitionHelper.addTransition(enterTransition3, slideFromBottom);
                    setEnterTransition(enterTransition3);
                }
                setSharedElementEnterTransition(null);
            } else if (uiStyle == 2) {
                setEnterTransition(null);
                setSharedElementEnterTransition(null);
            }
            Object exitTransition = TransitionHelper.createFadeAndShortSlide(GravityCompat.START);
            TransitionHelper.exclude(exitTransition, C0364R.C0366id.guidedstep_background, true);
            TransitionHelper.exclude(exitTransition, C0364R.C0366id.guidedactions_sub_list_background, true);
            setExitTransition(exitTransition);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateBackgroundView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C0364R.layout.lb_guidedstep_background, container, false);
    }

    public int getUiStyle() {
        Bundle b = getArguments();
        if (b == null) {
            return 1;
        }
        return b.getInt("uiStyle", 1);
    }

    public void setUiStyle(int style) {
        int oldStyle = getUiStyle();
        Bundle arguments = getArguments();
        boolean isNew = false;
        if (arguments == null) {
            arguments = new Bundle();
            isNew = true;
        }
        arguments.putInt("uiStyle", style);
        if (isNew) {
            setArguments(arguments);
        }
        if (style != oldStyle) {
            onProvideFragmentTransitions();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mGuidanceStylist = onCreateGuidanceStylist();
        this.mActionsStylist = onCreateActionsStylist();
        this.mButtonActionsStylist = onCreateButtonActionsStylist();
        onProvideFragmentTransitions();
        ArrayList<GuidedAction> actions = new ArrayList<>();
        onCreateActions(actions, savedInstanceState);
        if (savedInstanceState != null) {
            onRestoreActions(actions, savedInstanceState);
        }
        setActions(actions);
        ArrayList<GuidedAction> buttonActions = new ArrayList<>();
        onCreateButtonActions(buttonActions, savedInstanceState);
        if (savedInstanceState != null) {
            onRestoreButtonActions(buttonActions, savedInstanceState);
        }
        setButtonActions(buttonActions);
    }

    public void onDestroyView() {
        this.mGuidanceStylist.onDestroyView();
        this.mActionsStylist.onDestroyView();
        this.mButtonActionsStylist.onDestroyView();
        this.mAdapter = null;
        this.mSubAdapter = null;
        this.mButtonAdapter = null;
        this.mAdapterGroup = null;
        super.onDestroyView();
    }

    /* JADX INFO: Multiple debug info for r0v32 android.view.View: [D('backgroundView' android.view.View), D('ctx' android.content.Context)] */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = savedInstanceState;
        resolveTheme();
        LayoutInflater inflater2 = getThemeInflater(inflater);
        GuidedStepRootLayout root = (GuidedStepRootLayout) inflater2.inflate(C0364R.layout.lb_guidedstep_fragment, container, false);
        root.setFocusOutStart(isFocusOutStartAllowed());
        root.setFocusOutEnd(isFocusOutEndAllowed());
        ViewGroup guidanceContainer = (ViewGroup) root.findViewById(C0364R.C0366id.content_fragment);
        ViewGroup actionContainer = (ViewGroup) root.findViewById(C0364R.C0366id.action_fragment);
        ((NonOverlappingLinearLayout) actionContainer).setFocusableViewAvailableFixEnabled(true);
        guidanceContainer.addView(this.mGuidanceStylist.onCreateView(inflater2, guidanceContainer, onCreateGuidance(bundle)));
        actionContainer.addView(this.mActionsStylist.onCreateView(inflater2, actionContainer));
        View buttonActionsView = this.mButtonActionsStylist.onCreateView(inflater2, actionContainer);
        actionContainer.addView(buttonActionsView);
        GuidedActionAdapter.EditListener editListener = new GuidedActionAdapter.EditListener() {
            public void onImeOpen() {
                GuidedStepSupportFragment.this.runImeAnimations(true);
            }

            public void onImeClose() {
                GuidedStepSupportFragment.this.runImeAnimations(false);
            }

            public long onGuidedActionEditedAndProceed(GuidedAction action) {
                return GuidedStepSupportFragment.this.onGuidedActionEditedAndProceed(action);
            }

            public void onGuidedActionEditCanceled(GuidedAction action) {
                GuidedStepSupportFragment.this.onGuidedActionEditCanceled(action);
            }
        };
        List<GuidedAction> list = this.mActions;
        GuidedActionAdapter guidedActionAdapter = r0;
        GuidedActionAdapter.EditListener editListener2 = editListener;
        C04502 r2 = new GuidedActionAdapter.ClickListener() {
            public void onGuidedActionClicked(GuidedAction action) {
                GuidedStepSupportFragment.this.onGuidedActionClicked(action);
                if (GuidedStepSupportFragment.this.isExpanded()) {
                    GuidedStepSupportFragment.this.collapseAction(true);
                } else if (action.hasSubActions() || action.hasEditableActivatorView()) {
                    GuidedStepSupportFragment.this.expandAction(action, true);
                }
            }
        };
        View buttonActionsView2 = buttonActionsView;
        GuidedActionAdapter guidedActionAdapter2 = new GuidedActionAdapter(list, r2, this, this.mActionsStylist, false);
        this.mAdapter = guidedActionAdapter;
        GuidedActionAdapter guidedActionAdapter3 = r0;
        GuidedActionAdapter guidedActionAdapter4 = new GuidedActionAdapter(this.mButtonActions, new GuidedActionAdapter.ClickListener() {
            public void onGuidedActionClicked(GuidedAction action) {
                GuidedStepSupportFragment.this.onGuidedActionClicked(action);
            }
        }, this, this.mButtonActionsStylist, false);
        this.mButtonAdapter = guidedActionAdapter3;
        this.mSubAdapter = new GuidedActionAdapter(null, new GuidedActionAdapter.ClickListener() {
            public void onGuidedActionClicked(GuidedAction action) {
                if (!GuidedStepSupportFragment.this.mActionsStylist.isInExpandTransition() && GuidedStepSupportFragment.this.onSubGuidedActionClicked(action)) {
                    GuidedStepSupportFragment.this.collapseSubActions();
                }
            }
        }, this, this.mActionsStylist, true);
        this.mAdapterGroup = new GuidedActionAdapterGroup();
        this.mAdapterGroup.addAdpter(this.mAdapter, this.mButtonAdapter);
        this.mAdapterGroup.addAdpter(this.mSubAdapter, null);
        GuidedActionAdapter.EditListener editListener3 = editListener2;
        this.mAdapterGroup.setEditListener(editListener3);
        this.mActionsStylist.setEditListener(editListener3);
        this.mActionsStylist.getActionsGridView().setAdapter(this.mAdapter);
        if (this.mActionsStylist.getSubActionsGridView() != null) {
            this.mActionsStylist.getSubActionsGridView().setAdapter(this.mSubAdapter);
        }
        this.mButtonActionsStylist.getActionsGridView().setAdapter(this.mButtonAdapter);
        if (this.mButtonActions.size() == 0) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) buttonActionsView2.getLayoutParams();
            lp.weight = 0.0f;
            buttonActionsView2.setLayoutParams(lp);
        } else {
            Context ctx = this.mThemeWrapper;
            if (ctx == null) {
                ctx = getContext();
            }
            TypedValue typedValue = new TypedValue();
            if (ctx.getTheme().resolveAttribute(C0364R.attr.guidedActionContentWidthWeightTwoPanels, typedValue, true)) {
                View actionsRoot = root.findViewById(C0364R.C0366id.action_fragment_root);
                float weight = typedValue.getFloat();
                LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) actionsRoot.getLayoutParams();
                lp2.weight = weight;
                actionsRoot.setLayoutParams(lp2);
            }
        }
        View backgroundView = onCreateBackgroundView(inflater2, root, bundle);
        if (backgroundView != null) {
            ((FrameLayout) root.findViewById(C0364R.C0366id.guidedstep_background_view_root)).addView(backgroundView, 0);
        }
        return root;
    }

    public void onResume() {
        super.onResume();
        getView().findViewById(C0364R.C0366id.action_fragment).requestFocus();
    }

    /* access modifiers changed from: package-private */
    public final String getAutoRestoreKey(GuidedAction action) {
        return EXTRA_ACTION_PREFIX + action.getId();
    }

    /* access modifiers changed from: package-private */
    public final String getButtonAutoRestoreKey(GuidedAction action) {
        return EXTRA_BUTTON_ACTION_PREFIX + action.getId();
    }

    /* access modifiers changed from: package-private */
    public final void onRestoreActions(List<GuidedAction> actions, Bundle savedInstanceState) {
        int size = actions.size();
        for (int i = 0; i < size; i++) {
            GuidedAction action = actions.get(i);
            if (isSaveEnabled(action)) {
                action.onRestoreInstanceState(savedInstanceState, getAutoRestoreKey(action));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void onRestoreButtonActions(List<GuidedAction> actions, Bundle savedInstanceState) {
        int size = actions.size();
        for (int i = 0; i < size; i++) {
            GuidedAction action = actions.get(i);
            if (isSaveEnabled(action)) {
                action.onRestoreInstanceState(savedInstanceState, getButtonAutoRestoreKey(action));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void onSaveActions(List<GuidedAction> actions, Bundle outState) {
        int size = actions.size();
        for (int i = 0; i < size; i++) {
            GuidedAction action = actions.get(i);
            if (isSaveEnabled(action)) {
                action.onSaveInstanceState(outState, getAutoRestoreKey(action));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void onSaveButtonActions(List<GuidedAction> actions, Bundle outState) {
        int size = actions.size();
        for (int i = 0; i < size; i++) {
            GuidedAction action = actions.get(i);
            if (isSaveEnabled(action)) {
                action.onSaveInstanceState(outState, getButtonAutoRestoreKey(action));
            }
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        onSaveActions(this.mActions, outState);
        onSaveButtonActions(this.mButtonActions, outState);
    }

    public void finishGuidedStepSupportFragments() {
        FragmentManager fragmentManager = getFragmentManager();
        int entryCount = fragmentManager.getBackStackEntryCount();
        if (entryCount > 0) {
            for (int i = entryCount - 1; i >= 0; i--) {
                FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
                if (isStackEntryUiStyleEntrance(entry.getName())) {
                    GuidedStepSupportFragment top = getCurrentGuidedStepSupportFragment(fragmentManager);
                    if (top != null) {
                        top.setUiStyle(1);
                    }
                    fragmentManager.popBackStackImmediate(entry.getId(), 1);
                    return;
                }
            }
        }
        ActivityCompat.finishAfterTransition(getActivity());
    }

    public void popBackStackToGuidedStepSupportFragment(Class guidedStepFragmentClass, int flags) {
        if (GuidedStepSupportFragment.class.isAssignableFrom(guidedStepFragmentClass)) {
            FragmentManager fragmentManager = getFragmentManager();
            int entryCount = fragmentManager.getBackStackEntryCount();
            String className = guidedStepFragmentClass.getName();
            if (entryCount > 0) {
                for (int i = entryCount - 1; i >= 0; i--) {
                    FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
                    if (className.equals(getGuidedStepSupportFragmentClassName(entry.getName()))) {
                        fragmentManager.popBackStackImmediate(entry.getId(), flags);
                        return;
                    }
                }
            }
        }
    }

    public boolean isFocusOutStartAllowed() {
        return false;
    }

    public boolean isFocusOutEndAllowed() {
        return false;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setEntranceTransitionType(int transitionType) {
        this.entranceTransitionType = transitionType;
    }

    public void openInEditMode(GuidedAction action) {
        this.mActionsStylist.openInEditMode(action);
    }

    private void resolveTheme() {
        Context context = getContext();
        int theme = onProvideTheme();
        if (theme == -1 && !isGuidedStepTheme(context)) {
            int resId = C0364R.attr.guidedStepTheme;
            TypedValue typedValue = new TypedValue();
            boolean found = context.getTheme().resolveAttribute(resId, typedValue, true);
            if (found) {
                ContextThemeWrapper themeWrapper = new ContextThemeWrapper(context, typedValue.resourceId);
                if (isGuidedStepTheme(themeWrapper)) {
                    this.mThemeWrapper = themeWrapper;
                } else {
                    found = false;
                    this.mThemeWrapper = null;
                }
            }
            if (!found) {
                Log.e(TAG, "GuidedStepSupportFragment does not have an appropriate theme set.");
            }
        } else if (theme != -1) {
            this.mThemeWrapper = new ContextThemeWrapper(context, theme);
        }
    }

    private LayoutInflater getThemeInflater(LayoutInflater inflater) {
        ContextThemeWrapper contextThemeWrapper = this.mThemeWrapper;
        if (contextThemeWrapper == null) {
            return inflater;
        }
        return inflater.cloneInContext(contextThemeWrapper);
    }

    /* access modifiers changed from: package-private */
    public void runImeAnimations(boolean entering) {
        ArrayList<Animator> animators = new ArrayList<>();
        if (entering) {
            this.mGuidanceStylist.onImeAppearing(animators);
            this.mActionsStylist.onImeAppearing(animators);
            this.mButtonActionsStylist.onImeAppearing(animators);
        } else {
            this.mGuidanceStylist.onImeDisappearing(animators);
            this.mActionsStylist.onImeDisappearing(animators);
            this.mButtonActionsStylist.onImeDisappearing(animators);
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        set.start();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static class DummyFragment extends Fragment {
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = new View(inflater.getContext());
            v.setVisibility(8);
            return v;
        }
    }
}
