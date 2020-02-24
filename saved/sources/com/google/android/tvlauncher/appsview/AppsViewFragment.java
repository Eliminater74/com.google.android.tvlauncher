package com.google.android.tvlauncher.appsview;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p004v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;
import androidx.leanback.widget.VerticalGridView;
import com.google.android.libraries.social.analytics.visualelement.VisualElementTag;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.ClickEvent;
import com.google.android.tvlauncher.analytics.FragmentEventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.analytics.LogEventParameters;
import com.google.android.tvlauncher.analytics.LogUtils;
import com.google.android.tvlauncher.analytics.UserActionEvent;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManager;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManagerProvider;
import com.google.android.tvlauncher.util.LaunchUtil;
import com.google.android.tvlauncher.util.OemAppPromotions;
import com.google.android.tvrecommendations.shared.util.Constants;

public class AppsViewFragment extends Fragment {
    private static final String TAG = "AppsViewFragment";
    private View mAppsView;
    private boolean mAppsViewOnStopped = true;
    /* access modifiers changed from: private */
    public final FragmentEventLogger mEventLogger = new FragmentEventLogger(this);
    /* access modifiers changed from: private */
    public VerticalGridView mGridView;
    /* access modifiers changed from: private */
    public LaunchItemsManager mLaunchItemsManager;
    private OnAppsViewActionListener mOnAppsViewActionListener;
    private final OnEditModeOrderChangeCallback mOnEditModeOrderChangeCallback = new OnEditModeOrderChangeCallback();
    /* access modifiers changed from: private */
    public RowListAdapter mRowListAdapter;
    private final RecyclerView.AdapterDataObserver mRowListAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
        public void onChanged() {
            super.onChanged();
            AppsViewFragment.this.updateVerticalGridViewLayout();
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            AppsViewFragment.this.updateVerticalGridViewLayout();
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            AppsViewFragment.this.updateVerticalGridViewLayout();
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            AppsViewFragment.this.updateVerticalGridViewLayout();
        }
    };
    /* access modifiers changed from: private */
    public boolean mScrollToTopWhenResume = true;

    class OnEditModeOrderChangeCallback {
        OnEditModeOrderChangeCallback() {
        }

        /* access modifiers changed from: package-private */
        public void onEditModeExited(int rowIndex, final int colIndex) {
            AppsViewFragment.this.mGridView.scrollToPosition(rowIndex);
            AppsViewFragment.this.mGridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    View row = AppsViewFragment.this.mGridView.getFocusedChild();
                    if (row instanceof LaunchItemsRowView) {
                        ((LaunchItemsRowView) row).setOneTimeFocusPosition(colIndex);
                    }
                    AppsViewFragment.this.mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mLaunchItemsManager = LaunchItemsManagerProvider.getInstance(getContext());
        this.mLaunchItemsManager.refreshLaunchItems();
        this.mRowListAdapter = new RowListAdapter(getContext(), this.mEventLogger, this.mLaunchItemsManager);
        this.mRowListAdapter.registerAdapterDataObserver(this.mRowListAdapterDataObserver);
        this.mLaunchItemsManager.registerAppsViewChangeListener(this.mRowListAdapter);
        this.mOnAppsViewActionListener = createOnShowAppsViewListener();
    }

    public void onResume() {
        this.mEventLogger.log(new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.OPEN_APPS_VIEW).expectParameters(LogEventParameters.APP_COUNT));
        if (this.mGridView != null && this.mScrollToTopWhenResume && this.mAppsViewOnStopped) {
            int i = 0;
            while (true) {
                if (i >= this.mRowListAdapter.getItemCount()) {
                    break;
                } else if (this.mRowListAdapter.getItemViewType(i) != 5) {
                    this.mGridView.scrollToPosition(i);
                    break;
                } else {
                    i++;
                }
            }
        }
        this.mRowListAdapter.setResetViewHolderPositions(this.mScrollToTopWhenResume);
        this.mScrollToTopWhenResume = true;
        this.mAppsViewOnStopped = false;
        OemAppPromotions oemAppPromotions = OemAppPromotions.get(getContext());
        oemAppPromotions.registerOnAppPromotionsLoadedListener(this.mRowListAdapter);
        oemAppPromotions.readAppPromotions(true);
        this.mLaunchItemsManager.registerAppsViewChangeListener(oemAppPromotions);
        super.onResume();
    }

    public void onPause() {
        OemAppPromotions oemAppPromotions = OemAppPromotions.get(getContext());
        oemAppPromotions.unregisterOnAppPromotionsLoadedListener(this.mRowListAdapter);
        this.mLaunchItemsManager.unregisterAppsViewChangeListener(oemAppPromotions);
        super.onPause();
    }

    public void onStop() {
        this.mAppsViewOnStopped = true;
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mLaunchItemsManager.unregisterAppsViewChangeListener(this.mRowListAdapter);
        this.mRowListAdapter.unregisterAdapterDataObserver(this.mRowListAdapterDataObserver);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mAppsView = inflater.inflate(C1188R.layout.apps_view_fragment, container, false);
        return this.mAppsView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.mGridView = (VerticalGridView) this.mAppsView.findViewById(C1188R.C1191id.row_list_view);
        this.mRowListAdapter.setDataInRows();
        this.mRowListAdapter.setOnAppsViewActionListener(this.mOnAppsViewActionListener);
        this.mRowListAdapter.setOnEditModeOrderChangeCallback(this.mOnEditModeOrderChangeCallback);
        this.mRowListAdapter.initRows();
        this.mGridView.setAdapter(this.mRowListAdapter);
        this.mAppsView.requestFocus();
    }

    /* access modifiers changed from: package-private */
    public void updateVerticalGridViewLayout() {
        if (this.mGridView != null && this.mRowListAdapter.getItemCount() != 0) {
            if (this.mRowListAdapter.getItemViewType(0) == 3) {
                this.mGridView.setWindowAlignment(0);
                this.mGridView.setWindowAlignmentOffsetPercent(-1.0f);
                return;
            }
            int paddingTop = this.mGridView.getContext().getResources().getDimensionPixelSize(C1188R.dimen.apps_view_padding_top);
            this.mGridView.setWindowAlignmentOffset(paddingTop);
            this.mGridView.setWindowAlignmentOffsetPercent(-1.0f);
            this.mGridView.setWindowAlignment(1);
            VerticalGridView verticalGridView = this.mGridView;
            verticalGridView.setPadding(verticalGridView.getPaddingLeft(), paddingTop, this.mGridView.getPaddingRight(), this.mGridView.getPaddingBottom());
        }
    }

    public void startEditMode(int editModeType) {
        OnAppsViewActionListener onAppsViewActionListener = this.mOnAppsViewActionListener;
        if (onAppsViewActionListener != null) {
            onAppsViewActionListener.onShowEditModeView(editModeType, 0);
        }
    }

    private OnAppsViewActionListener createOnShowAppsViewListener() {
        return new OnAppsViewActionListener() {
            public void onShowEditModeView(int editModeType, int focusedAppPosition) {
                AppsViewFragment.this.getFragmentManager().beginTransaction().replace(16908290, EditModeFragment.newInstance(editModeType, focusedAppPosition, AppsViewFragment.this.mRowListAdapter.getTopKeylineForEditMode(editModeType), AppsViewFragment.this.mRowListAdapter.getBottomKeylineForEditMode(editModeType)), "edit_mode_fragment").addToBackStack(null).commit();
            }

            public void onShowAppInfo(String packageName) {
                boolean unused = AppsViewFragment.this.mScrollToTopWhenResume = false;
                Intent intent = new Intent();
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                String valueOf = String.valueOf(packageName);
                intent.setData(Uri.parse(valueOf.length() != 0 ? "package:".concat(valueOf) : new String("package:")));
                AppsViewFragment.this.startActivity(intent);
            }

            public void onUninstallApp(String packageName) {
                boolean unused = AppsViewFragment.this.mScrollToTopWhenResume = false;
                if (AppsViewFragment.this.mLaunchItemsManager.getLaunchItem(packageName).isAppLink()) {
                    Intent intent = new Intent(Constants.ACTION_REMOVE_APP_LINK);
                    intent.putExtra(Constants.EXTRA_APP_LINK_ID, packageName);
                    AppsViewFragment.this.startActivityForResult(intent, 0);
                    return;
                }
                Intent intent2 = new Intent();
                intent2.setAction("android.intent.action.UNINSTALL_PACKAGE");
                String valueOf = String.valueOf(packageName);
                intent2.setData(Uri.parse(valueOf.length() != 0 ? "package:".concat(valueOf) : new String("package:")));
                AppsViewFragment.this.startActivity(intent2);
            }

            public void onLaunchApp(Intent intent, View view) {
                try {
                    boolean unused = AppsViewFragment.this.mScrollToTopWhenResume = false;
                    if (Constants.ACTION_ADD_APP_LINK.equals(intent.getAction())) {
                        AppsViewFragment.this.startActivityForResult(intent, 0);
                    } else {
                        LaunchUtil.startActivityWithAnimation(intent, view);
                    }
                } catch (ActivityNotFoundException | SecurityException e) {
                    Toast.makeText(AppsViewFragment.this.getContext(), C1188R.string.failed_launch, 0).show();
                    String valueOf = String.valueOf(e);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
                    sb.append("Cannot start activity : ");
                    sb.append(valueOf);
                    Log.e(AppsViewFragment.TAG, sb.toString());
                }
            }

            public void onToggleFavorite(LaunchItem item) {
                if (AppsViewFragment.this.mLaunchItemsManager.isFavorite(item)) {
                    AppsViewFragment.this.mLaunchItemsManager.removeFromFavorites(item);
                } else {
                    AppsViewFragment.this.mLaunchItemsManager.addToFavorites(item);
                }
            }

            public void onStoreLaunch(Intent intent, VisualElementTag visualElementTag, View view) {
                try {
                    String packageName = LogUtils.getPackage(intent);
                    LogEvent event = new ClickEvent(TvlauncherLogEnum.TvLauncherEventCode.START_APP).setVisualElementTag(visualElementTag);
                    if (packageName != null) {
                        event.getApplication().setPackageName(packageName);
                    }
                    AppsViewFragment.this.mEventLogger.log(event);
                    boolean unused = AppsViewFragment.this.mScrollToTopWhenResume = false;
                    LaunchUtil.startActivityWithAnimation(intent, view);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(AppsViewFragment.this.getContext(), C1188R.string.failed_launch, 0).show();
                    String str = LogUtils.getPackage(intent);
                    String valueOf = String.valueOf(e);
                    StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 44 + String.valueOf(valueOf).length());
                    sb.append("Cannot start store with package: ");
                    sb.append(str);
                    sb.append(", due to : ");
                    sb.append(valueOf);
                    Log.e(AppsViewFragment.TAG, sb.toString());
                }
            }
        };
    }
}
