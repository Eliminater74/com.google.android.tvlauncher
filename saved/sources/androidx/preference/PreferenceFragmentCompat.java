package androidx.preference;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.XmlRes;
import android.support.p001v4.app.DialogFragment;
import android.support.p001v4.app.Fragment;
import android.support.p001v4.app.FragmentManager;
import android.support.p004v7.widget.LinearLayoutManager;
import android.support.p004v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.preference.DialogPreference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceManager;

public abstract class PreferenceFragmentCompat extends Fragment implements PreferenceManager.OnPreferenceTreeClickListener, PreferenceManager.OnDisplayPreferenceDialogListener, PreferenceManager.OnNavigateToScreenListener, DialogPreference.TargetFragment {
    public static final String ARG_PREFERENCE_ROOT = "androidx.preference.PreferenceFragmentCompat.PREFERENCE_ROOT";
    private static final String DIALOG_FRAGMENT_TAG = "androidx.preference.PreferenceFragment.DIALOG";
    private static final int MSG_BIND_PREFERENCES = 1;
    private static final String PREFERENCES_TAG = "android:preferences";
    private static final String TAG = "PreferenceFragment";
    private final DividerDecoration mDividerDecoration = new DividerDecoration();
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                PreferenceFragmentCompat.this.bindPreferences();
            }
        }
    };
    private boolean mHavePrefs;
    private boolean mInitDone;
    private int mLayoutResId = C0731R.layout.preference_list_fragment;
    RecyclerView mList;
    private PreferenceManager mPreferenceManager;
    private final Runnable mRequestFocus = new Runnable() {
        public void run() {
            PreferenceFragmentCompat.this.mList.focusableViewAvailable(PreferenceFragmentCompat.this.mList);
        }
    };
    private Runnable mSelectPreferenceRunnable;

    public interface OnPreferenceDisplayDialogCallback {
        boolean onPreferenceDisplayDialog(@NonNull PreferenceFragmentCompat preferenceFragmentCompat, Preference preference);
    }

    public interface OnPreferenceStartFragmentCallback {
        boolean onPreferenceStartFragment(PreferenceFragmentCompat preferenceFragmentCompat, Preference preference);
    }

    public interface OnPreferenceStartScreenCallback {
        boolean onPreferenceStartScreen(PreferenceFragmentCompat preferenceFragmentCompat, PreferenceScreen preferenceScreen);
    }

    public abstract void onCreatePreferences(Bundle bundle, String str);

    public void onCreate(Bundle savedInstanceState) {
        String rootKey;
        super.onCreate(savedInstanceState);
        TypedValue tv = new TypedValue();
        getActivity().getTheme().resolveAttribute(C0731R.attr.preferenceTheme, tv, true);
        int theme = tv.resourceId;
        if (theme == 0) {
            theme = C0731R.style.PreferenceThemeOverlay;
        }
        getActivity().getTheme().applyStyle(theme, false);
        this.mPreferenceManager = new PreferenceManager(getContext());
        this.mPreferenceManager.setOnNavigateToScreenListener(this);
        if (getArguments() != null) {
            rootKey = getArguments().getString("androidx.preference.PreferenceFragmentCompat.PREFERENCE_ROOT");
        } else {
            rootKey = null;
        }
        onCreatePreferences(savedInstanceState, rootKey);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TypedArray a = getContext().obtainStyledAttributes(null, C0731R.styleable.PreferenceFragmentCompat, C0731R.attr.preferenceFragmentCompatStyle, 0);
        this.mLayoutResId = a.getResourceId(C0731R.styleable.PreferenceFragmentCompat_android_layout, this.mLayoutResId);
        Drawable divider = a.getDrawable(C0731R.styleable.PreferenceFragmentCompat_android_divider);
        int dividerHeight = a.getDimensionPixelSize(C0731R.styleable.PreferenceFragmentCompat_android_dividerHeight, -1);
        boolean allowDividerAfterLastItem = a.getBoolean(C0731R.styleable.PreferenceFragmentCompat_allowDividerAfterLastItem, true);
        a.recycle();
        LayoutInflater themedInflater = inflater.cloneInContext(getContext());
        View view = themedInflater.inflate(this.mLayoutResId, container, false);
        View rawListContainer = view.findViewById(16908351);
        if (rawListContainer instanceof ViewGroup) {
            ViewGroup listContainer = (ViewGroup) rawListContainer;
            RecyclerView listView = onCreateRecyclerView(themedInflater, listContainer, savedInstanceState);
            if (listView != null) {
                this.mList = listView;
                listView.addItemDecoration(this.mDividerDecoration);
                setDivider(divider);
                if (dividerHeight != -1) {
                    setDividerHeight(dividerHeight);
                }
                this.mDividerDecoration.setAllowDividerAfterLastItem(allowDividerAfterLastItem);
                if (this.mList.getParent() == null) {
                    listContainer.addView(this.mList);
                }
                this.mHandler.post(this.mRequestFocus);
                return view;
            }
            throw new RuntimeException("Could not create RecyclerView");
        }
        throw new IllegalStateException("Content has view with id attribute 'android.R.id.list_container' that is not a ViewGroup class");
    }

    public void setDivider(Drawable divider) {
        this.mDividerDecoration.setDivider(divider);
    }

    public void setDividerHeight(int height) {
        this.mDividerDecoration.setDividerHeight(height);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle container;
        PreferenceScreen preferenceScreen;
        super.onViewCreated(view, savedInstanceState);
        if (!(savedInstanceState == null || (container = savedInstanceState.getBundle(PREFERENCES_TAG)) == null || (preferenceScreen = getPreferenceScreen()) == null)) {
            preferenceScreen.restoreHierarchyState(container);
        }
        if (this.mHavePrefs) {
            bindPreferences();
            Runnable runnable = this.mSelectPreferenceRunnable;
            if (runnable != null) {
                runnable.run();
                this.mSelectPreferenceRunnable = null;
            }
        }
        this.mInitDone = true;
    }

    public void onStart() {
        super.onStart();
        this.mPreferenceManager.setOnPreferenceTreeClickListener(this);
        this.mPreferenceManager.setOnDisplayPreferenceDialogListener(this);
    }

    public void onStop() {
        super.onStop();
        this.mPreferenceManager.setOnPreferenceTreeClickListener(null);
        this.mPreferenceManager.setOnDisplayPreferenceDialogListener(null);
    }

    public void onDestroyView() {
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.mHandler.removeMessages(1);
        if (this.mHavePrefs) {
            unbindPreferences();
        }
        this.mList = null;
        super.onDestroyView();
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            Bundle container = new Bundle();
            preferenceScreen.saveHierarchyState(container);
            outState.putBundle(PREFERENCES_TAG, container);
        }
    }

    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    public PreferenceScreen getPreferenceScreen() {
        return this.mPreferenceManager.getPreferenceScreen();
    }

    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        if (this.mPreferenceManager.setPreferences(preferenceScreen) && preferenceScreen != null) {
            onUnbindPreferences();
            this.mHavePrefs = true;
            if (this.mInitDone) {
                postBindPreferences();
            }
        }
    }

    public void addPreferencesFromResource(@XmlRes int preferencesResId) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromResource(getContext(), preferencesResId, getPreferenceScreen()));
    }

    /* JADX INFO: additional move instructions added (1) to help type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setPreferencesFromResource(@android.support.annotation.XmlRes int r6, @android.support.annotation.Nullable java.lang.String r7) {
        /*
            r5 = this;
            r5.requirePreferenceManager()
            androidx.preference.PreferenceManager r0 = r5.mPreferenceManager
            android.content.Context r1 = r5.getContext()
            r2 = 0
            androidx.preference.PreferenceScreen r0 = r0.inflateFromResource(r1, r6, r2)
            if (r7 == 0) goto L_0x0035
            androidx.preference.Preference r1 = r0.findPreference(r7)
            boolean r2 = r1 instanceof androidx.preference.PreferenceScreen
            if (r2 == 0) goto L_0x0019
            goto L_0x0036
        L_0x0019:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Preference object with key "
            r3.append(r4)
            r3.append(r7)
            java.lang.String r4 = " is not a PreferenceScreen"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x0035:
            r1 = r0
        L_0x0036:
            r2 = r1
            androidx.preference.PreferenceScreen r2 = (androidx.preference.PreferenceScreen) r2
            r5.setPreferenceScreen(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.preference.PreferenceFragmentCompat.setPreferencesFromResource(int, java.lang.String):void");
    }

    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getFragment() == null) {
            return false;
        }
        boolean handled = false;
        if (getCallbackFragment() instanceof OnPreferenceStartFragmentCallback) {
            handled = ((OnPreferenceStartFragmentCallback) getCallbackFragment()).onPreferenceStartFragment(this, preference);
        }
        if (!handled && (getActivity() instanceof OnPreferenceStartFragmentCallback)) {
            handled = ((OnPreferenceStartFragmentCallback) getActivity()).onPreferenceStartFragment(this, preference);
        }
        if (handled) {
            return true;
        }
        Log.w(TAG, "onPreferenceStartFragment is not implemented in the parent activity - attempting to use a fallback implementation. You should implement this method so that you can configure the new fragment that will be displayed, and set a transition between the fragments.");
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        Bundle args = preference.getExtras();
        Fragment fragment = fragmentManager.getFragmentFactory().instantiate(requireActivity().getClassLoader(), preference.getFragment());
        fragment.setArguments(args);
        fragment.setTargetFragment(this, 0);
        fragmentManager.beginTransaction().replace(((View) getView().getParent()).getId(), fragment).addToBackStack(null).commit();
        return true;
    }

    public void onNavigateToScreen(PreferenceScreen preferenceScreen) {
        boolean handled = false;
        if (getCallbackFragment() instanceof OnPreferenceStartScreenCallback) {
            handled = ((OnPreferenceStartScreenCallback) getCallbackFragment()).onPreferenceStartScreen(this, preferenceScreen);
        }
        if (!handled && (getActivity() instanceof OnPreferenceStartScreenCallback)) {
            ((OnPreferenceStartScreenCallback) getActivity()).onPreferenceStartScreen(this, preferenceScreen);
        }
    }

    @Nullable
    public <T extends Preference> T findPreference(@NonNull CharSequence key) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager == null) {
            return null;
        }
        return preferenceManager.findPreference(key);
    }

    private void requirePreferenceManager() {
        if (this.mPreferenceManager == null) {
            throw new RuntimeException("This should be called after super.onCreate.");
        }
    }

    private void postBindPreferences() {
        if (!this.mHandler.hasMessages(1)) {
            this.mHandler.obtainMessage(1).sendToTarget();
        }
    }

    /* access modifiers changed from: package-private */
    public void bindPreferences() {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            getListView().setAdapter(onCreateAdapter(preferenceScreen));
            preferenceScreen.onAttached();
        }
        onBindPreferences();
    }

    private void unbindPreferences() {
        getListView().setAdapter(null);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            preferenceScreen.onDetached();
        }
        onUnbindPreferences();
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void onBindPreferences() {
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void onUnbindPreferences() {
    }

    public final RecyclerView getListView() {
        return this.mList;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public RecyclerView onCreateRecyclerView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        RecyclerView recyclerView;
        if (getContext().getPackageManager().hasSystemFeature("android.hardware.type.automotive") && (recyclerView = (RecyclerView) parent.findViewById(C0731R.C0733id.recycler_view)) != null) {
            return recyclerView;
        }
        RecyclerView recyclerView2 = (RecyclerView) inflater.inflate(C0731R.layout.preference_recyclerview, parent, false);
        recyclerView2.setLayoutManager(onCreateLayoutManager());
        recyclerView2.setAccessibilityDelegateCompat(new PreferenceRecyclerViewAccessibilityDelegate(recyclerView2));
        return recyclerView2;
    }

    public RecyclerView.LayoutManager onCreateLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /* access modifiers changed from: protected */
    public RecyclerView.Adapter onCreateAdapter(PreferenceScreen preferenceScreen) {
        return new PreferenceGroupAdapter(preferenceScreen);
    }

    public void onDisplayPreferenceDialog(Preference preference) {
        DialogFragment f;
        boolean handled = false;
        if (getCallbackFragment() instanceof OnPreferenceDisplayDialogCallback) {
            handled = ((OnPreferenceDisplayDialogCallback) getCallbackFragment()).onPreferenceDisplayDialog(this, preference);
        }
        if (!handled && (getActivity() instanceof OnPreferenceDisplayDialogCallback)) {
            handled = ((OnPreferenceDisplayDialogCallback) getActivity()).onPreferenceDisplayDialog(this, preference);
        }
        if (!handled && getFragmentManager().findFragmentByTag(DIALOG_FRAGMENT_TAG) == null) {
            if (preference instanceof EditTextPreference) {
                f = EditTextPreferenceDialogFragmentCompat.newInstance(preference.getKey());
            } else if (preference instanceof ListPreference) {
                f = ListPreferenceDialogFragmentCompat.newInstance(preference.getKey());
            } else if (preference instanceof MultiSelectListPreference) {
                f = MultiSelectListPreferenceDialogFragmentCompat.newInstance(preference.getKey());
            } else {
                throw new IllegalArgumentException("Cannot display dialog for an unknown Preference type: " + preference.getClass().getSimpleName() + ". Make sure to implement onPreferenceDisplayDialog() to handle displaying a custom dialog for this Preference.");
            }
            f.setTargetFragment(this, 0);
            f.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public Fragment getCallbackFragment() {
        return null;
    }

    public void scrollToPreference(String key) {
        scrollToPreferenceInternal(null, key);
    }

    public void scrollToPreference(Preference preference) {
        scrollToPreferenceInternal(preference, null);
    }

    private void scrollToPreferenceInternal(final Preference preference, final String key) {
        Runnable r = new Runnable() {
            public void run() {
                int position;
                RecyclerView.Adapter adapter = PreferenceFragmentCompat.this.mList.getAdapter();
                if (adapter instanceof PreferenceGroup.PreferencePositionCallback) {
                    Preference preference = preference;
                    if (preference != null) {
                        position = ((PreferenceGroup.PreferencePositionCallback) adapter).getPreferenceAdapterPosition(preference);
                    } else {
                        position = ((PreferenceGroup.PreferencePositionCallback) adapter).getPreferenceAdapterPosition(key);
                    }
                    if (position != -1) {
                        PreferenceFragmentCompat.this.mList.scrollToPosition(position);
                    } else {
                        adapter.registerAdapterDataObserver(new ScrollToPreferenceObserver(adapter, PreferenceFragmentCompat.this.mList, preference, key));
                    }
                } else if (adapter != null) {
                    throw new IllegalStateException("Adapter must implement PreferencePositionCallback");
                }
            }
        };
        if (this.mList == null) {
            this.mSelectPreferenceRunnable = r;
        } else {
            r.run();
        }
    }

    private static class ScrollToPreferenceObserver extends RecyclerView.AdapterDataObserver {
        private final RecyclerView.Adapter mAdapter;
        private final String mKey;
        private final RecyclerView mList;
        private final Preference mPreference;

        public ScrollToPreferenceObserver(RecyclerView.Adapter adapter, RecyclerView list, Preference preference, String key) {
            this.mAdapter = adapter;
            this.mList = list;
            this.mPreference = preference;
            this.mKey = key;
        }

        private void scrollToPreference() {
            int position;
            this.mAdapter.unregisterAdapterDataObserver(this);
            Preference preference = this.mPreference;
            if (preference != null) {
                position = ((PreferenceGroup.PreferencePositionCallback) this.mAdapter).getPreferenceAdapterPosition(preference);
            } else {
                position = ((PreferenceGroup.PreferencePositionCallback) this.mAdapter).getPreferenceAdapterPosition(this.mKey);
            }
            if (position != -1) {
                this.mList.scrollToPosition(position);
            }
        }

        public void onChanged() {
            scrollToPreference();
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            scrollToPreference();
        }

        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            scrollToPreference();
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            scrollToPreference();
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            scrollToPreference();
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            scrollToPreference();
        }
    }

    private class DividerDecoration extends RecyclerView.ItemDecoration {
        private boolean mAllowDividerAfterLastItem = true;
        private Drawable mDivider;
        private int mDividerHeight;

        DividerDecoration() {
        }

        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            if (this.mDivider != null) {
                int childCount = parent.getChildCount();
                int width = parent.getWidth();
                for (int childViewIndex = 0; childViewIndex < childCount; childViewIndex++) {
                    View view = parent.getChildAt(childViewIndex);
                    if (shouldDrawDividerBelow(view, parent)) {
                        int top = ((int) view.getY()) + view.getHeight();
                        this.mDivider.setBounds(0, top, width, this.mDividerHeight + top);
                        this.mDivider.draw(c);
                    }
                }
            }
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (shouldDrawDividerBelow(view, parent)) {
                outRect.bottom = this.mDividerHeight;
            }
        }

        private boolean shouldDrawDividerBelow(View view, RecyclerView parent) {
            RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
            boolean nextAllowed = false;
            if (!((holder instanceof PreferenceViewHolder) && ((PreferenceViewHolder) holder).isDividerAllowedBelow())) {
                return false;
            }
            boolean nextAllowed2 = this.mAllowDividerAfterLastItem;
            int index = parent.indexOfChild(view);
            if (index >= parent.getChildCount() - 1) {
                return nextAllowed2;
            }
            RecyclerView.ViewHolder nextHolder = parent.getChildViewHolder(parent.getChildAt(index + 1));
            if ((nextHolder instanceof PreferenceViewHolder) && ((PreferenceViewHolder) nextHolder).isDividerAllowedAbove()) {
                nextAllowed = true;
            }
            return nextAllowed;
        }

        public void setDivider(Drawable divider) {
            if (divider != null) {
                this.mDividerHeight = divider.getIntrinsicHeight();
            } else {
                this.mDividerHeight = 0;
            }
            this.mDivider = divider;
            PreferenceFragmentCompat.this.mList.invalidateItemDecorations();
        }

        public void setDividerHeight(int dividerHeight) {
            this.mDividerHeight = dividerHeight;
            PreferenceFragmentCompat.this.mList.invalidateItemDecorations();
        }

        public void setAllowDividerAfterLastItem(boolean allowDividerAfterLastItem) {
            this.mAllowDividerAfterLastItem = allowDividerAfterLastItem;
        }
    }
}
