package androidx.leanback.app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.SpeechRecognizer;
import android.support.p001v4.app.Fragment;
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

public class SearchSupportFragment extends Fragment {
    private static final String ARG_PREFIX = SearchSupportFragment.class.getCanonicalName();
    private static final String ARG_QUERY = (ARG_PREFIX + ".query");
    private static final String ARG_TITLE = (ARG_PREFIX + ".title");
    static final int AUDIO_PERMISSION_REQUEST_CODE = 0;
    static final boolean DEBUG = false;
    private static final String EXTRA_LEANBACK_BADGE_PRESENT = "LEANBACK_BADGE_PRESENT";
    static final int QUERY_COMPLETE = 2;
    static final int RESULTS_CHANGED = 1;
    static final long SPEECH_RECOGNITION_DELAY_MS = 300;
    static final String TAG = SearchSupportFragment.class.getSimpleName();
    final ObjectAdapter.DataObserver mAdapterObserver = new ObjectAdapter.DataObserver() {
        public void onChanged() {
            SearchSupportFragment.this.mHandler.removeCallbacks(SearchSupportFragment.this.mResultsChangedCallback);
            SearchSupportFragment.this.mHandler.post(SearchSupportFragment.this.mResultsChangedCallback);
        }
    };
    boolean mAutoStartRecognition = true;
    private Drawable mBadgeDrawable;
    private ExternalQuery mExternalQuery;
    final Handler mHandler = new Handler();
    private boolean mIsPaused;
    private OnItemViewClickedListener mOnItemViewClickedListener;
    OnItemViewSelectedListener mOnItemViewSelectedListener;
    String mPendingQuery = null;
    private boolean mPendingStartRecognitionWhenPaused;
    private SearchBar.SearchBarPermissionListener mPermissionListener = new SearchBar.SearchBarPermissionListener() {
        public void requestAudioPermission() {
            SearchSupportFragment.this.requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 0);
        }
    };
    SearchResultProvider mProvider;
    ObjectAdapter mResultAdapter;
    final Runnable mResultsChangedCallback = new Runnable() {
        public void run() {
            if (!(SearchSupportFragment.this.mRowsSupportFragment == null || SearchSupportFragment.this.mRowsSupportFragment.getAdapter() == SearchSupportFragment.this.mResultAdapter || (SearchSupportFragment.this.mRowsSupportFragment.getAdapter() == null && SearchSupportFragment.this.mResultAdapter.size() == 0))) {
                SearchSupportFragment.this.mRowsSupportFragment.setAdapter(SearchSupportFragment.this.mResultAdapter);
                SearchSupportFragment.this.mRowsSupportFragment.setSelectedPosition(0);
            }
            SearchSupportFragment.this.updateSearchBarVisibility();
            SearchSupportFragment.this.mStatus |= 1;
            if ((SearchSupportFragment.this.mStatus & 2) != 0) {
                SearchSupportFragment.this.updateFocus();
            }
        }
    };
    RowsSupportFragment mRowsSupportFragment;
    SearchBar mSearchBar;
    private final Runnable mSetSearchResultProvider = new Runnable() {
        public void run() {
            if (SearchSupportFragment.this.mRowsSupportFragment != null) {
                ObjectAdapter adapter = SearchSupportFragment.this.mProvider.getResultsAdapter();
                if (adapter != SearchSupportFragment.this.mResultAdapter) {
                    boolean firstTime = SearchSupportFragment.this.mResultAdapter == null;
                    SearchSupportFragment.this.releaseAdapter();
                    SearchSupportFragment searchSupportFragment = SearchSupportFragment.this;
                    searchSupportFragment.mResultAdapter = adapter;
                    if (searchSupportFragment.mResultAdapter != null) {
                        SearchSupportFragment.this.mResultAdapter.registerObserver(SearchSupportFragment.this.mAdapterObserver);
                    }
                    if (!firstTime || !(SearchSupportFragment.this.mResultAdapter == null || SearchSupportFragment.this.mResultAdapter.size() == 0)) {
                        SearchSupportFragment.this.mRowsSupportFragment.setAdapter(SearchSupportFragment.this.mResultAdapter);
                    }
                    SearchSupportFragment.this.executePendingQuery();
                }
                if (SearchSupportFragment.this.mAutoStartRecognition) {
                    SearchSupportFragment.this.mHandler.removeCallbacks(SearchSupportFragment.this.mStartRecognitionRunnable);
                    SearchSupportFragment.this.mHandler.postDelayed(SearchSupportFragment.this.mStartRecognitionRunnable, SearchSupportFragment.SPEECH_RECOGNITION_DELAY_MS);
                    return;
                }
                SearchSupportFragment.this.updateFocus();
            }
        }
    };
    private SpeechRecognitionCallback mSpeechRecognitionCallback;
    private SpeechRecognizer mSpeechRecognizer;
    final Runnable mStartRecognitionRunnable = new Runnable() {
        public void run() {
            SearchSupportFragment searchSupportFragment = SearchSupportFragment.this;
            searchSupportFragment.mAutoStartRecognition = false;
            searchSupportFragment.mSearchBar.startRecognition();
        }
    };
    int mStatus;
    private String mTitle;

    public interface SearchResultProvider {
        ObjectAdapter getResultsAdapter();

        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0 && permissions.length > 0 && permissions[0].equals("android.permission.RECORD_AUDIO") && grantResults[0] == 0) {
            startRecognition();
        }
    }

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

    public static SearchSupportFragment newInstance(String query) {
        SearchSupportFragment fragment = new SearchSupportFragment();
        fragment.setArguments(createArgs(null, query));
        return fragment;
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
                if (SearchSupportFragment.this.mProvider != null) {
                    SearchSupportFragment.this.retrieveResults(query);
                } else {
                    SearchSupportFragment.this.mPendingQuery = query;
                }
            }

            public void onSearchQuerySubmit(String query) {
                SearchSupportFragment.this.submitQuery(query);
            }

            public void onKeyboardDismiss(String query) {
                SearchSupportFragment.this.queryComplete();
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
            this.mRowsSupportFragment = new RowsSupportFragment();
            getChildFragmentManager().beginTransaction().replace(C0364R.C0366id.lb_results_frame, this.mRowsSupportFragment).commit();
        } else {
            this.mRowsSupportFragment = (RowsSupportFragment) getChildFragmentManager().findFragmentById(C0364R.C0366id.lb_results_frame);
        }
        this.mRowsSupportFragment.setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                SearchSupportFragment.this.updateSearchBarVisibility();
                if (SearchSupportFragment.this.mOnItemViewSelectedListener != null) {
                    SearchSupportFragment.this.mOnItemViewSelectedListener.onItemSelected(itemViewHolder, item, rowViewHolder, row);
                }
            }
        });
        this.mRowsSupportFragment.setOnItemViewClickedListener(this.mOnItemViewClickedListener);
        this.mRowsSupportFragment.setExpand(true);
        if (this.mProvider != null) {
            onSetSearchResultProvider();
        }
        searchFrame.setOnFocusSearchListener(new BrowseFrameLayout.OnFocusSearchListener() {
            public View onFocusSearch(View focused, int direction) {
                if (SearchSupportFragment.this.mRowsSupportFragment == null || SearchSupportFragment.this.mRowsSupportFragment.getView() == null || !SearchSupportFragment.this.mRowsSupportFragment.getView().hasFocus()) {
                    if (!SearchSupportFragment.this.mSearchBar.hasFocus() || direction != 130 || SearchSupportFragment.this.mRowsSupportFragment.getView() == null || SearchSupportFragment.this.mResultAdapter == null || SearchSupportFragment.this.mResultAdapter.size() <= 0) {
                        return null;
                    }
                    return SearchSupportFragment.this.mRowsSupportFragment.getView();
                } else if (direction == 33) {
                    return SearchSupportFragment.this.mSearchBar.findViewById(C0364R.C0366id.lb_search_bar_speech_orb);
                } else {
                    return null;
                }
            }
        });
        return root;
    }

    public void onStart() {
        super.onStart();
        VerticalGridView list = this.mRowsSupportFragment.getVerticalGridView();
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
            this.mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
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

    public RowsSupportFragment getRowsSupportFragment() {
        return this.mRowsSupportFragment;
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
            RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
            if (rowsSupportFragment != null) {
                rowsSupportFragment.setOnItemViewClickedListener(this.mOnItemViewClickedListener);
            }
        }
    }

    public void setTitle(String title) {
        this.mTitle = title;
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setTitle(title);
        }
    }

    public String getTitle() {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            return searchBar.getTitle();
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

    public Drawable getBadgeDrawable() {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            return searchBar.getBadgeDrawable();
        }
        return null;
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
        RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
        this.mSearchBar.setVisibility(((rowsSupportFragment != null ? rowsSupportFragment.getSelectedPosition() : -1) <= 0 || (objectAdapter = this.mResultAdapter) == null || objectAdapter.size() == 0) ? 0 : 8);
    }

    /* access modifiers changed from: package-private */
    public void updateFocus() {
        RowsSupportFragment rowsSupportFragment;
        ObjectAdapter objectAdapter = this.mResultAdapter;
        if (objectAdapter == null || objectAdapter.size() <= 0 || (rowsSupportFragment = this.mRowsSupportFragment) == null || rowsSupportFragment.getAdapter() != this.mResultAdapter) {
            this.mSearchBar.requestFocus();
        } else {
            focusOnResults();
        }
    }

    private void focusOnResults() {
        RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
        if (rowsSupportFragment != null && rowsSupportFragment.getVerticalGridView() != null && this.mResultAdapter.size() != 0 && this.mRowsSupportFragment.getVerticalGridView().requestFocus()) {
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

    static class ExternalQuery {
        String mQuery;
        boolean mSubmit;

        ExternalQuery(String query, boolean submit) {
            this.mQuery = query;
            this.mSubmit = submit;
        }
    }
}
