package com.google.android.tvlauncher.appsview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p004v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.FragmentEventLogger;
import com.google.android.tvlauncher.analytics.UserActionEvent;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManager;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManagerProvider;
import com.google.android.tvlauncher.util.OemConfiguration;
import com.google.android.tvlauncher.util.Util;
import com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog;
import java.util.ArrayList;

public class EditModeFragment extends Fragment {
    public static final int EDIT_TYPE_APPS = 0;
    public static final int EDIT_TYPE_GAMES = 1;
    private static final String KEY_BOTTOM_KEYLINE = "key_bottom_keyline";
    public static final String KEY_EDIT_MODE_FOCUSED_POSITION = "key_edit_mode_focused_position";
    public static final String KEY_EDIT_MODE_TYPE = "key_edit_mode_type";
    private static final String KEY_TOP_KEYLINE = "key_top_keyline";
    private RecyclerView.AdapterDataObserver mAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            EditModeFragment.this.mGridView.updateAccessibilityContextMenuIfNeeded();
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            EditModeFragment.this.mGridView.updateAccessibilityContextMenuIfNeeded();
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            EditModeFragment.this.mGridView.updateAccessibilityContextMenuIfNeeded();
        }
    };
    private EditModeGridAdapter mEditAdapter;
    private View mEditModeView;
    private int mEditType;
    private final FragmentEventLogger mEventLogger = new FragmentEventLogger(this);
    private int mFocusPosition;
    /* access modifiers changed from: private */
    public EditModeGridView mGridView;
    private final LaunchItemsManager mLaunchItemsManager = LaunchItemsManagerProvider.getInstance(getContext());
    private OemConfiguration.LayoutOrderOptions mLayoutOrderOptions = OemConfiguration.get(getContext()).getAppsViewLayoutOption();
    /* access modifiers changed from: private */
    public OnShowAccessibilityMenuListener mOnShowAccessibilityMenuListener;

    public static EditModeFragment newInstance(int editModeType, int focusedAppPosition, int topKeyline, int bottomKeyline) {
        Bundle args = new Bundle();
        args.putInt(KEY_EDIT_MODE_TYPE, editModeType);
        args.putInt(KEY_EDIT_MODE_FOCUSED_POSITION, focusedAppPosition);
        args.putInt(KEY_TOP_KEYLINE, topKeyline);
        args.putInt(KEY_BOTTOM_KEYLINE, bottomKeyline);
        EditModeFragment fragment = new EditModeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EditModeFragment() {
        if (this.mLayoutOrderOptions == null) {
            this.mLayoutOrderOptions = OemConfiguration.LayoutOrderOptions.APPS_OEM_GAMES;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        this.mEditType = args.getInt(KEY_EDIT_MODE_TYPE);
        this.mFocusPosition = args.getInt(KEY_EDIT_MODE_FOCUSED_POSITION);
        this.mEditAdapter = new EditModeGridAdapter(getContext(), this.mEventLogger);
        this.mEditAdapter.registerAdapterDataObserver(this.mAdapterDataObserver);
        this.mLaunchItemsManager.refreshLaunchItems();
        this.mLaunchItemsManager.registerAppsViewChangeListener(this.mEditAdapter);
        this.mEditAdapter.setTopKeyline(args.getInt(KEY_TOP_KEYLINE));
        this.mEditAdapter.setBottomKeyline(args.getInt(KEY_BOTTOM_KEYLINE));
        this.mOnShowAccessibilityMenuListener = new EditModeFragment$$Lambda$0(this);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onCreate$0$EditModeFragment(boolean show) {
        if (!Util.isAccessibilityEnabled(getContext()) || !show) {
            this.mGridView.hideAccessibilityMenu();
        } else {
            this.mGridView.showAccessibilityMenu();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mEditModeView = inflater.inflate(C1188R.layout.edit_mode_view, container, false);
        return this.mEditModeView;
    }

    /* renamed from: com.google.android.tvlauncher.appsview.EditModeFragment$3 */
    static /* synthetic */ class C12133 {

        /* renamed from: $SwitchMap$com$google$android$tvlauncher$util$OemConfiguration$LayoutOrderOptions */
        static final /* synthetic */ int[] f130x70c2e182 = new int[OemConfiguration.LayoutOrderOptions.values().length];

        static {
            try {
                f130x70c2e182[OemConfiguration.LayoutOrderOptions.APPS_OEM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f130x70c2e182[OemConfiguration.LayoutOrderOptions.APPS_OEM_GAMES.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f130x70c2e182[OemConfiguration.LayoutOrderOptions.APPS_GAMES_OEM.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f130x70c2e182[OemConfiguration.LayoutOrderOptions.GAMES_APPS_OEM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.mGridView = (EditModeGridView) this.mEditModeView.findViewById(C1188R.C1191id.edit_mode_grid);
        int i = C12133.f130x70c2e182[this.mLayoutOrderOptions.ordinal()];
        if (i == 1) {
            this.mEditAdapter.setLaunchItems(this.mLaunchItemsManager.getAllLaunchItemsWithSorting());
        } else if (i == 2 || i == 3 || i == 4) {
            int i2 = this.mEditType;
            if (i2 == 0) {
                this.mEditAdapter.setLaunchItems(this.mLaunchItemsManager.getAppLaunchItems());
            } else if (i2 == 1) {
                this.mEditAdapter.setLaunchItems(this.mLaunchItemsManager.getGameLaunchItems());
            }
        }
        this.mEditAdapter.setOnShowAccessibilityMenuListener(this.mOnShowAccessibilityMenuListener);
        this.mEditAdapter.setOnEditItemRemovedListener(new EditModeFragment$$Lambda$1(this));
        if (this.mEditAdapter.getItemCount() <= 0) {
            getFragmentManager().popBackStack();
        }
        this.mGridView.setNumColumns(4);
        this.mGridView.setAdapter(this.mEditAdapter);
        this.mGridView.setSelectedPosition(this.mFocusPosition);
        this.mGridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                View focusedChild = EditModeFragment.this.mGridView.getFocusedChild();
                if (focusedChild instanceof BannerView) {
                    focusedChild.setSelected(true);
                    EditModeFragment.this.mOnShowAccessibilityMenuListener.onShowAccessibilityMenu(true);
                    EditModeFragment.this.mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
        this.mGridView.setWindowAlignment(0);
        this.mGridView.setWindowAlignmentOffsetPercent(-1.0f);
        this.mGridView.requestFocus();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onViewCreated$1$EditModeFragment(int position) {
        EditModeGridView editModeGridView = this.mGridView;
        if (editModeGridView != null && editModeGridView.getSelectedPosition() == position && !getFragmentManager().isStateSaved()) {
            getFragmentManager().popBackStack();
        }
    }

    public void onResume() {
        super.onResume();
        this.mEventLogger.log(new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.ENTER_EDIT_APPS_MODE));
    }

    public void onPause() {
        int selectedPosition = this.mGridView.getSelectedPosition();
        ArrayList<LaunchItem> launchItems = this.mEditAdapter.getLaunchItems();
        LaunchItemsManager launchItemsManager = this.mLaunchItemsManager;
        boolean z = true;
        if (this.mEditType != 1) {
            z = false;
        }
        launchItemsManager.onAppOrderChange(launchItems, z, LaunchItemsHolder.getRowColIndexFromListIndex(selectedPosition));
        this.mOnShowAccessibilityMenuListener.onShowAccessibilityMenu(false);
        super.onPause();
        UserActionEvent event = new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.EXIT_EDIT_APPS_MODE);
        TvlauncherClientLog.LaunchItemCollection.Builder launchItemCollection = event.getLaunchItemCollection();
        if (this.mEditType == 0) {
            launchItemCollection.setCount(launchItems.size());
        } else {
            launchItemCollection.setGameCount(launchItems.size());
        }
        int size = launchItems.size();
        for (int i = 0; i < size; i++) {
            LaunchItem launchItem = launchItems.get(i);
            TvlauncherClientLog.LaunchItem.Builder item = TvlauncherClientLog.LaunchItem.newBuilder();
            if (launchItem.isAppLink()) {
                TvlauncherClientLog.AppLink.Builder appLink = TvlauncherClientLog.AppLink.newBuilder();
                appLink.setPackageName(launchItem.getPackageName());
                if (launchItem.getDataUri() != null) {
                    appLink.setUri(launchItem.getDataUri());
                }
                item.setAppLink(appLink);
            } else {
                TvlauncherClientLog.Application.Builder app = TvlauncherClientLog.Application.newBuilder();
                app.setPackageName(launchItem.getPackageName());
                app.setIsGame(launchItem.isGame());
                item.setApp(app);
            }
            launchItemCollection.addItems(item);
        }
        this.mEventLogger.log(event);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mEditAdapter.unregisterAdapterDataObserver(this.mAdapterDataObserver);
        this.mLaunchItemsManager.unregisterAppsViewChangeListener(this.mEditAdapter);
    }
}
