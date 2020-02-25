package androidx.leanback.app;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CompletionInfo;

import androidx.leanback.C0364R;
import androidx.leanback.widget.BrowseFrameLayout;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.SearchBar;
import androidx.leanback.widget.SearchOrbView;
import androidx.leanback.widget.SpeechRecognitionCallback;
import androidx.leanback.widget.VerticalGridView;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class SearchFragment extends Fragment {
    static final int AUDIO_PERMISSION_REQUEST_CODE = 0;
    static final boolean DEBUG = false;
    static final int QUERY_COMPLETE = 2;
    static final int RESULTS_CHANGED = 1;
    static final long SPEECH_RECOGNITION_DELAY_MS = 300;
    static final String TAG = SearchFragment.class.getSimpleName();
    private static final String ARG_PREFIX = SearchFragment.class.getCanonicalName();
    private static final String ARG_QUERY = (ARG_PREFIX + ".query");
    private static final String ARG_TITLE = (ARG_PREFIX + ".title");
    private static final String EXTRA_LEANBACK_BADGE_PRESENT = "LEANBACK_BADGE_PRESENT";
    final Handler mHandler = new Handler();
    boolean mAutoStartRecognition = true;
    OnItemViewSelectedListener mOnItemViewSelectedListener;
    String mPendingQuery = null;
    SearchResultProvider mProvider;
    ObjectAdapter mResultAdapter;
    RowsFragment mRowsFragment;
    SearchBar mSearchBar;
    final Runnable mStartRecognitionRunnable = new Runnable() {
        public void run() {
            SearchFragment searchFragment = SearchFragment.this;
            searchFragment.mAutoStartRecognition = false;
            searchFragment.mSearchBar.startRecognition();
        }
    };
    int mStatus;
    final Runnable mResultsChangedCallback = new Runnable() {
        public void run() {
            if (!(SearchFragment.this.mRowsFragment == null || SearchFragment.this.mRowsFragment.getAdapter() == SearchFragment.this.mResultAdapter || (SearchFragment.this.mRowsFragment.getAdapter() == null && SearchFragment.this.mResultAdapter.size() == 0))) {
                SearchFragment.this.mRowsFragment.setAdapter(SearchFragment.this.mResultAdapter);
                SearchFragment.this.mRowsFragment.setSelectedPosition(0);
            }
            SearchFragment.this.updateSearchBarVisibility();
            SearchFragment.this.mStatus |= 1;
            if ((SearchFragment.this.mStatus & 2) != 0) {
                SearchFragment.this.updateFocus();
            }
        }
    };
    final ObjectAdapter.DataObserver mAdapterObserver = new ObjectAdapter.DataObserver() {
        public void onChanged() {
            SearchFragment.this.mHandler.removeCallbacks(SearchFragment.this.mResultsChangedCallback);
            SearchFragment.this.mHandler.post(SearchFragment.this.mResultsChangedCallback);
        }
    };
    private final Runnable mSetSearchResultProvider = new Runnable() {
        public void run() {
            if (SearchFragment.this.mRowsFragment != null) {
                ObjectAdapter adapter = SearchFragment.this.mProvider.getResultsAdapter();
                if (adapter != SearchFragment.this.mResultAdapter) {
                    boolean firstTime = SearchFragment.this.mResultAdapter == null;
                    SearchFragment.this.releaseAdapter();
                    SearchFragment searchFragment = SearchFragment.this;
                    searchFragment.mResultAdapter = adapter;
                    if (searchFragment.mResultAdapter != null) {
                        SearchFragment.this.mResultAdapter.registerObserver(SearchFragment.this.mAdapterObserver);
                    }
                    if (!firstTime || !(SearchFragment.this.mResultAdapter == null || SearchFragment.this.mResultAdapter.size() == 0)) {
                        SearchFragment.this.mRowsFragment.setAdapter(SearchFragment.this.mResultAdapter);
                    }
                    SearchFragment.this.executePendingQuery();
                }
                if (SearchFragment.this.mAutoStartRecognition) {
                    SearchFragment.this.mHandler.removeCallbacks(SearchFragment.this.mStartRecognitionRunnable);
                    SearchFragment.this.mHandler.postDelayed(SearchFragment.this.mStartRecognitionRunnable, SearchFragment.SPEECH_RECOGNITION_DELAY_MS);
                    return;
                }
                SearchFragment.this.updateFocus();
            }
        }
    };
    private Drawable mBadgeDrawable;
    private ExternalQuery mExternalQuery;
    private boolean mIsPaused;
    private OnItemViewClickedListener mOnItemViewClickedListener;
    private boolean mPendingStartRecognitionWhenPaused;
    private SearchBar.SearchBarPermissionListener mPermissionListener = new SearchBar.SearchBarPermissionListener() {
        public void requestAudioPermission() {
            PermissionHelper.requestPermissions(SearchFragment.this, new String[]{"android.permission.RECORD_AUDIO"}, 0);
        }
    };
    private SpeechRecognitionCallback mSpeechRecognitionCallback;
    private SpeechRecognizer mSpeechRecognizer;
    private String mTitle;

    public static Bundle createArgs(Bundle args, String query) {
        return createArgs(args, query, null);
    }

    public static Bundle createArgs(Bundle args, String query, String title) {
        if (args == null) {
            args = new Bundle();
        }
        args.putString(ARG_QUERY, query);
        args.putString(ARG_TITLE, title);
        return args;
    }

    public static SearchFragment newInstance(String query) {
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(createArgs(null, query));
        return fragment;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0 && permissions.length > 0 && permissions[0].equals("android.permission.RECORD_AUDIO") && grantResults[0] == 0) {
            startRecognition();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (this.mAutoStartRecognition) {
            this.mAutoStartRecognition = savedInstanceState == null;
        }
        super.onCreate(savedInstanceState);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(C0364R.layout.lb_search_fragment, container, false);
        BrowseFrameLayout searchFrame = (BrowseFrameLayout) root.findViewById(C0364R.C0366id.lb_search_frame);
        this.mSearchBar = (SearchBar) searchFrame.findViewById(C0364R.C0366id.lb_search_bar);
        this.mSearchBar.setSearchBarListener(new SearchBar.SearchBarListener() {
            public void onSearchQueryChange(String query) {
                if (SearchFragment.this.mProvider != null) {
                    SearchFragment.this.retrieveResults(query);
                } else {
                    SearchFragment.this.mPendingQuery = query;
                }
            }

            public void onSearchQuerySubmit(String query) {
                SearchFragment.this.submitQuery(query);
            }

            public void onKeyboardDismiss(String query) {
                SearchFragment.this.queryComplete();
            }
        });
        this.mSearchBar.setSpeechRecognitionCallback(this.mSpeechRecognitionCallback);
        this.mSearchBar.setPermissionListener(this.mPermissionListener);
        applyExternalQuery();
        readArguments(getArguments());
        Drawable drawable = this.mBadgeDrawable;
        if (drawable != null) {
            setBadgeDrawable(drawable);
        }
        String str = this.mTitle;
        if (str != null) {
            setTitle(str);
        }
        if (getChildFragmentManager().findFragmentById(C0364R.C0366id.lb_results_frame) == null) {
            this.mRowsFragment = new RowsFragment();
            getChildFragmentManager().beginTransaction().replace(C0364R.C0366id.lb_results_frame, this.mRowsFragment).commit();
        } else {
            this.mRowsFragment = (RowsFragment) getChildFragmentManager().findFragmentById(C0364R.C0366id.lb_results_frame);
        }
        this.mRowsFragment.setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                SearchFragment.this.updateSearchBarVisibility();
                if (SearchFragment.this.mOnItemViewSelectedListener != null) {
                    SearchFragment.this.mOnItemViewSelectedListener.onItemSelected(itemViewHolder, item, rowViewHolder, row);
                }
            }
        });
        this.mRowsFragment.setOnItemViewClickedListener(this.mOnItemViewClickedListener);
        this.mRowsFragment.setExpand(true);
        if (this.mProvider != null) {
            onSetSearchResultProvider();
        }
        searchFrame.setOnFocusSearchListener(new BrowseFrameLayout.OnFocusSearchListener() {
            public View onFocusSearch(View focused, int direction) {
                if (SearchFragment.this.mRowsFragment == null || SearchFragment.this.mRowsFragment.getView() == null || !SearchFragment.this.mRowsFragment.getView().hasFocus()) {
                    if (!SearchFragment.this.mSearchBar.hasFocus() || direction != 130 || SearchFragment.this.mRowsFragment.getView() == null || SearchFragment.this.mResultAdapter == null || SearchFragment.this.mResultAdapter.size() <= 0) {
                        return null;
                    }
                    return SearchFragment.this.mRowsFragment.getView();
                } else if (direction == 33) {
                    return SearchFragment.this.mSearchBar.findViewById(C0364R.C0366id.lb_search_bar_speech_orb);
                } else {
                    return null;
                }
            }
        });
        return root;
    }

    public void onStart() {
        super.onStart();
        VerticalGridView list = this.mRowsFragment.getVerticalGridView();
        int mContainerListAlignTop = getResources().getDimensionPixelSize(C0364R.dimen.lb_search_browse_rows_align_top);
        list.setItemAlignmentOffset(0);
        list.setItemAlignmentOffsetPercent(-1.0f);
        list.setWindowAlignmentOffset(mContainerListAlignTop);
        list.setWindowAlignmentOffsetPercent(-1.0f);
        list.setWindowAlignment(0);
    }

    public void onResume() {
        super.onResume();
        this.mIsPaused = false;
        if (this.mSpeechRecognitionCallback == null && this.mSpeechRecognizer == null) {
            this.mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(FragmentUtil.getContext(this));
            this.mSearchBar.setSpeechRecognizer(this.mSpeechRecognizer);
        }
        if (this.mPendingStartRecognitionWhenPaused) {
            this.mPendingStartRecognitionWhenPaused = false;
            this.mSearchBar.startRecognition();
            return;
        }
        this.mSearchBar.stopRecognition();
    }

    public void onPause() {
        releaseRecognizer();
        this.mIsPaused = true;
        super.onPause();
    }

    public void onDestroy() {
        releaseAdapter();
        super.onDestroy();
    }

    public RowsFragment getRowsFragment() {
        return this.mRowsFragment;
    }

    private void releaseRecognizer() {
        if (this.mSpeechRecognizer != null) {
            this.mSearchBar.setSpeechRecognizer(null);
            this.mSpeechRecognizer.destroy();
            this.mSpeechRecognizer = null;
        }
    }

    public void startRecognition() {
        if (this.mIsPaused) {
            this.mPendingStartRecognitionWhenPaused = true;
        } else {
            this.mSearchBar.startRecognition();
        }
    }

    public void setSearchResultProvider(SearchResultProvider searchResultProvider) {
        if (this.mProvider != searchResultProvider) {
            this.mProvider = searchResultProvider;
            onSetSearchResultProvider();
        }
    }

    public void setOnItemViewSelectedListener(OnItemViewSelectedListener listener) {
        this.mOnItemViewSelectedListener = listener;
    }

    public void setOnItemViewClickedListener(OnItemViewClickedListener listener) {
        if (listener != this.mOnItemViewClickedListener) {
            this.mOnItemViewClickedListener = listener;
            RowsFragment rowsFragment = this.mRowsFragment;
            if (rowsFragment != null) {
                rowsFragment.setOnItemViewClickedListener(this.mOnItemViewClickedListener);
            }
        }
    }

    public String getTitle() {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            return searchBar.getTitle();
        }
        return null;
    }

    public void setTitle(String title) {
        this.mTitle = title;
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setTitle(title);
        }
    }

    public Drawable getBadgeDrawable() {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            return searchBar.getBadgeDrawable();
        }
        return null;
    }

    public void setBadgeDrawable(Drawable drawable) {
        this.mBadgeDrawable = drawable;
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setBadgeDrawable(drawable);
        }
    }

    public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setSearchAffordanceColors(colors);
        }
    }

    public void setSearchAffordanceColorsInListening(SearchOrbView.Colors colors) {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setSearchAffordanceColorsInListening(colors);
        }
    }

    public void displayCompletions(List<String> completions) {
        this.mSearchBar.displayCompletions(completions);
    }

    public void displayCompletions(CompletionInfo[] completions) {
        this.mSearchBar.displayCompletions(completions);
    }

    @Deprecated
    public void setSpeechRecognitionCallback(SpeechRecognitionCallback callback) {
        this.mSpeechRecognitionCallback = callback;
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setSpeechRecognitionCallback(this.mSpeechRecognitionCallback);
        }
        if (callback != null) {
            releaseRecognizer();
        }
    }

    public void setSearchQuery(String query, boolean submit) {
        if (query != null) {
            this.mExternalQuery = new ExternalQuery(query, submit);
            applyExternalQuery();
            if (this.mAutoStartRecognition) {
                this.mAutoStartRecognition = false;
                this.mHandler.removeCallbacks(this.mStartRecognitionRunnable);
            }
        }
    }

    public void setSearchQuery(Intent intent, boolean submit) {
        ArrayList<String> matches = intent.getStringArrayListExtra("android.speech.extra.RESULTS");
        if (matches != null && matches.size() > 0) {
            setSearchQuery(matches.get(0), submit);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.content.Intent.putExtra(java.lang.String, boolean):android.content.Intent}
     arg types: [java.lang.String, int]
     candidates:
      ClspMth{android.content.Intent.putExtra(java.lang.String, int):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.String[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, int[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, double):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, char):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, boolean[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, byte):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Bundle):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, float):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.CharSequence[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.CharSequence):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, long[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, long):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, short):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Parcelable[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.io.Serializable):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, double[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Parcelable):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, float[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, byte[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.String):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, short[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, char[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, boolean):android.content.Intent} */
    public Intent getRecognizerIntent() {
        Intent recognizerIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        recognizerIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        boolean z = true;
        recognizerIntent.putExtra("android.speech.extra.PARTIAL_RESULTS", true);
        SearchBar searchBar = this.mSearchBar;
        if (!(searchBar == null || searchBar.getHint() == null)) {
            recognizerIntent.putExtra("android.speech.extra.PROMPT", this.mSearchBar.getHint());
        }
        if (this.mBadgeDrawable == null) {
            z = false;
        }
        recognizerIntent.putExtra(EXTRA_LEANBACK_BADGE_PRESENT, z);
        return recognizerIntent;
    }

    /* access modifiers changed from: package-private */
    public void retrieveResults(String searchQuery) {
        if (this.mProvider.onQueryTextChange(searchQuery)) {
            this.mStatus &= -3;
        }
    }

    /* access modifiers changed from: package-private */
    public void submitQuery(String query) {
        queryComplete();
        SearchResultProvider searchResultProvider = this.mProvider;
        if (searchResultProvider != null) {
            searchResultProvider.onQueryTextSubmit(query);
        }
    }

    /* access modifiers changed from: package-private */
    public void queryComplete() {
        this.mStatus |= 2;
        focusOnResults();
    }

    /* access modifiers changed from: package-private */
    public void updateSearchBarVisibility() {
        ObjectAdapter objectAdapter;
        RowsFragment rowsFragment = this.mRowsFragment;
        this.mSearchBar.setVisibility(((rowsFragment != null ? rowsFragment.getSelectedPosition() : -1) <= 0 || (objectAdapter = this.mResultAdapter) == null || objectAdapter.size() == 0) ? 0 : 8);
    }

    /* access modifiers changed from: package-private */
    public void updateFocus() {
        RowsFragment rowsFragment;
        ObjectAdapter objectAdapter = this.mResultAdapter;
        if (objectAdapter == null || objectAdapter.size() <= 0 || (rowsFragment = this.mRowsFragment) == null || rowsFragment.getAdapter() != this.mResultAdapter) {
            this.mSearchBar.requestFocus();
        } else {
            focusOnResults();
        }
    }

    private void focusOnResults() {
        RowsFragment rowsFragment = this.mRowsFragment;
        if (rowsFragment != null && rowsFragment.getVerticalGridView() != null && this.mResultAdapter.size() != 0 && this.mRowsFragment.getVerticalGridView().requestFocus()) {
            this.mStatus &= -2;
        }
    }

    private void onSetSearchResultProvider() {
        this.mHandler.removeCallbacks(this.mSetSearchResultProvider);
        this.mHandler.post(this.mSetSearchResultProvider);
    }

    /* access modifiers changed from: package-private */
    public void releaseAdapter() {
        ObjectAdapter objectAdapter = this.mResultAdapter;
        if (objectAdapter != null) {
            objectAdapter.unregisterObserver(this.mAdapterObserver);
            this.mResultAdapter = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void executePendingQuery() {
        if (this.mPendingQuery != null && this.mResultAdapter != null) {
            String query = this.mPendingQuery;
            this.mPendingQuery = null;
            retrieveResults(query);
        }
    }

    private void applyExternalQuery() {
        SearchBar searchBar;
        ExternalQuery externalQuery = this.mExternalQuery;
        if (externalQuery != null && (searchBar = this.mSearchBar) != null) {
            searchBar.setSearchQuery(externalQuery.mQuery);
            if (this.mExternalQuery.mSubmit) {
                submitQuery(this.mExternalQuery.mQuery);
            }
            this.mExternalQuery = null;
        }
    }

    private void readArguments(Bundle args) {
        if (args != null) {
            if (args.containsKey(ARG_QUERY)) {
                setSearchQuery(args.getString(ARG_QUERY));
            }
            if (args.containsKey(ARG_TITLE)) {
                setTitle(args.getString(ARG_TITLE));
            }
        }
    }

    private void setSearchQuery(String query) {
        this.mSearchBar.setSearchQuery(query);
    }

    public interface SearchResultProvider {
        ObjectAdapter getResultsAdapter();

        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    static class ExternalQuery {
        String mQuery;
        boolean mSubmit;

        ExternalQuery(String query, boolean submit) {
            this.mQuery = query;
            this.mSubmit = submit;
        }
    }
}
