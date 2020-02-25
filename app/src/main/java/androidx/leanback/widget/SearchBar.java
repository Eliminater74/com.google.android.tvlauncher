package androidx.leanback.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.leanback.C0364R;

import java.util.ArrayList;
import java.util.List;

public class SearchBar extends RelativeLayout {
    static final boolean DEBUG = false;
    static final int DEFAULT_PRIORITY = 1;
    static final float DEFAULT_RATE = 1.0f;
    static final int DO_NOT_LOOP = 0;
    static final float FULL_LEFT_VOLUME = 1.0f;
    static final float FULL_RIGHT_VOLUME = 1.0f;
    static final String TAG = SearchBar.class.getSimpleName();
    final Handler mHandler;
    private final Context mContext;
    private final InputMethodManager mInputMethodManager;
    private final int mTextColor;
    private final int mTextColorSpeechMode;
    private final int mTextHintColor;
    private final int mTextHintColorSpeechMode;
    boolean mAutoStartRecognition;
    boolean mRecognizing;
    SearchBarListener mSearchBarListener;
    String mSearchQuery;
    SearchEditText mSearchTextEditor;
    SparseIntArray mSoundMap;
    SoundPool mSoundPool;
    SpeechOrbView mSpeechOrbView;
    private AudioManager mAudioManager;
    private int mBackgroundAlpha;
    private int mBackgroundSpeechAlpha;
    private Drawable mBadgeDrawable;
    private ImageView mBadgeView;
    private Drawable mBarBackground;
    private int mBarHeight;
    private String mHint;
    private boolean mListening;
    private SearchBarPermissionListener mPermissionListener;
    private SpeechRecognitionCallback mSpeechRecognitionCallback;
    private SpeechRecognizer mSpeechRecognizer;
    private String mTitle;

    public SearchBar(Context context) {
        this(context, null);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, androidx.leanback.widget.SearchBar, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public SearchBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mHandler = new Handler();
        this.mAutoStartRecognition = false;
        this.mSoundMap = new SparseIntArray();
        this.mRecognizing = false;
        this.mContext = context;
        Resources r = getResources();
        LayoutInflater.from(getContext()).inflate(C0364R.layout.lb_search_bar, (ViewGroup) this, true);
        this.mBarHeight = getResources().getDimensionPixelSize(C0364R.dimen.lb_search_bar_height);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, this.mBarHeight);
        params.addRule(10, -1);
        setLayoutParams(params);
        setBackgroundColor(0);
        setClipChildren(false);
        this.mSearchQuery = "";
        this.mInputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        this.mTextColorSpeechMode = r.getColor(C0364R.color.lb_search_bar_text_speech_mode);
        this.mTextColor = r.getColor(C0364R.color.lb_search_bar_text);
        this.mBackgroundSpeechAlpha = r.getInteger(C0364R.integer.lb_search_bar_speech_mode_background_alpha);
        this.mBackgroundAlpha = r.getInteger(C0364R.integer.lb_search_bar_text_mode_background_alpha);
        this.mTextHintColorSpeechMode = r.getColor(C0364R.color.lb_search_bar_hint_speech_mode);
        this.mTextHintColor = r.getColor(C0364R.color.lb_search_bar_hint);
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBarBackground = ((RelativeLayout) findViewById(C0364R.C0366id.lb_search_bar_items)).getBackground();
        this.mSearchTextEditor = (SearchEditText) findViewById(C0364R.C0366id.lb_search_text_editor);
        this.mBadgeView = (ImageView) findViewById(C0364R.C0366id.lb_search_bar_badge);
        Drawable drawable = this.mBadgeDrawable;
        if (drawable != null) {
            this.mBadgeView.setImageDrawable(drawable);
        }
        this.mSearchTextEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    SearchBar.this.showNativeKeyboard();
                } else {
                    SearchBar.this.hideNativeKeyboard();
                }
                SearchBar.this.updateUi(hasFocus);
            }
        });
        final Runnable mOnTextChangedRunnable = new Runnable() {
            public void run() {
                SearchBar searchBar = SearchBar.this;
                searchBar.setSearchQueryInternal(searchBar.mSearchTextEditor.getText().toString());
            }
        };
        this.mSearchTextEditor.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (!SearchBar.this.mRecognizing) {
                    SearchBar.this.mHandler.removeCallbacks(mOnTextChangedRunnable);
                    SearchBar.this.mHandler.post(mOnTextChangedRunnable);
                }
            }

            public void afterTextChanged(Editable editable) {
            }
        });
        this.mSearchTextEditor.setOnKeyboardDismissListener(new SearchEditText.OnKeyboardDismissListener() {
            public void onKeyboardDismiss() {
                if (SearchBar.this.mSearchBarListener != null) {
                    SearchBar.this.mSearchBarListener.onKeyboardDismiss(SearchBar.this.mSearchQuery);
                }
            }
        });
        this.mSearchTextEditor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if ((3 == action || action == 0) && SearchBar.this.mSearchBarListener != null) {
                    SearchBar.this.hideNativeKeyboard();
                    SearchBar.this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            SearchBar.this.submitQuery();
                        }
                    }, 500);
                    return true;
                } else if (1 == action && SearchBar.this.mSearchBarListener != null) {
                    SearchBar.this.hideNativeKeyboard();
                    SearchBar.this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            SearchBar.this.mSearchBarListener.onKeyboardDismiss(SearchBar.this.mSearchQuery);
                        }
                    }, 500);
                    return true;
                } else if (2 != action) {
                    return false;
                } else {
                    SearchBar.this.hideNativeKeyboard();
                    SearchBar.this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            SearchBar.this.mAutoStartRecognition = true;
                            SearchBar.this.mSpeechOrbView.requestFocus();
                        }
                    }, 500);
                    return true;
                }
            }
        });
        this.mSearchTextEditor.setPrivateImeOptions("escapeNorth,voiceDismiss");
        this.mSpeechOrbView = (SpeechOrbView) findViewById(C0364R.C0366id.lb_search_bar_speech_orb);
        this.mSpeechOrbView.setOnOrbClickedListener(new View.OnClickListener() {
            public void onClick(View view) {
                SearchBar.this.toggleRecognition();
            }
        });
        this.mSpeechOrbView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    SearchBar.this.hideNativeKeyboard();
                    if (SearchBar.this.mAutoStartRecognition) {
                        SearchBar.this.startRecognition();
                        SearchBar.this.mAutoStartRecognition = false;
                    }
                } else {
                    SearchBar.this.stopRecognition();
                }
                SearchBar.this.updateUi(hasFocus);
            }
        });
        updateUi(hasFocus());
        updateHint();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSoundPool = new SoundPool(2, 1, 0);
        loadSounds(this.mContext);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        stopRecognition();
        this.mSoundPool.release();
        super.onDetachedFromWindow();
    }

    public void setSearchBarListener(SearchBarListener listener) {
        this.mSearchBarListener = listener;
    }

    public void setSearchQuery(String query) {
        stopRecognition();
        this.mSearchTextEditor.setText(query);
        setSearchQueryInternal(query);
    }

    /* access modifiers changed from: package-private */
    public void setSearchQueryInternal(String query) {
        if (!TextUtils.equals(this.mSearchQuery, query)) {
            this.mSearchQuery = query;
            SearchBarListener searchBarListener = this.mSearchBarListener;
            if (searchBarListener != null) {
                searchBarListener.onSearchQueryChange(this.mSearchQuery);
            }
        }
    }

    public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
        SpeechOrbView speechOrbView = this.mSpeechOrbView;
        if (speechOrbView != null) {
            speechOrbView.setNotListeningOrbColors(colors);
        }
    }

    public void setSearchAffordanceColorsInListening(SearchOrbView.Colors colors) {
        SpeechOrbView speechOrbView = this.mSpeechOrbView;
        if (speechOrbView != null) {
            speechOrbView.setListeningOrbColors(colors);
        }
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
        updateHint();
    }

    public CharSequence getHint() {
        return this.mHint;
    }

    public Drawable getBadgeDrawable() {
        return this.mBadgeDrawable;
    }

    public void setBadgeDrawable(Drawable drawable) {
        this.mBadgeDrawable = drawable;
        ImageView imageView = this.mBadgeView;
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
            if (drawable != null) {
                this.mBadgeView.setVisibility(0);
            } else {
                this.mBadgeView.setVisibility(8);
            }
        }
    }

    public void displayCompletions(List<String> completions) {
        List<CompletionInfo> infos = new ArrayList<>();
        if (completions != null) {
            for (String completion : completions) {
                infos.add(new CompletionInfo((long) infos.size(), infos.size(), completion));
            }
        }
        displayCompletions((CompletionInfo[]) infos.toArray(new CompletionInfo[infos.size()]));
    }

    public void displayCompletions(CompletionInfo[] completions) {
        this.mInputMethodManager.displayCompletions(this.mSearchTextEditor, completions);
    }

    public void setSpeechRecognizer(SpeechRecognizer recognizer) {
        stopRecognition();
        SpeechRecognizer speechRecognizer = this.mSpeechRecognizer;
        if (speechRecognizer != null) {
            speechRecognizer.setRecognitionListener(null);
            if (this.mListening) {
                this.mSpeechRecognizer.cancel();
                this.mListening = false;
            }
        }
        this.mSpeechRecognizer = recognizer;
        if (this.mSpeechRecognitionCallback != null && this.mSpeechRecognizer != null) {
            throw new IllegalStateException("Can't have speech recognizer and request");
        }
    }

    @Deprecated
    public void setSpeechRecognitionCallback(SpeechRecognitionCallback request) {
        this.mSpeechRecognitionCallback = request;
        if (this.mSpeechRecognitionCallback != null && this.mSpeechRecognizer != null) {
            throw new IllegalStateException("Can't have speech recognizer and request");
        }
    }

    /* access modifiers changed from: package-private */
    public void hideNativeKeyboard() {
        this.mInputMethodManager.hideSoftInputFromWindow(this.mSearchTextEditor.getWindowToken(), 0);
    }

    /* access modifiers changed from: package-private */
    public void showNativeKeyboard() {
        this.mHandler.post(new Runnable() {
            public void run() {
                SearchBar.this.mSearchTextEditor.requestFocusFromTouch();
                SearchBar.this.mSearchTextEditor.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, (float) SearchBar.this.mSearchTextEditor.getWidth(), (float) SearchBar.this.mSearchTextEditor.getHeight(), 0));
                SearchBar.this.mSearchTextEditor.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, (float) SearchBar.this.mSearchTextEditor.getWidth(), (float) SearchBar.this.mSearchTextEditor.getHeight(), 0));
            }
        });
    }

    private void updateHint() {
        String title = getResources().getString(C0364R.string.lb_search_bar_hint);
        if (!TextUtils.isEmpty(this.mTitle)) {
            if (isVoiceMode()) {
                title = getResources().getString(C0364R.string.lb_search_bar_hint_with_title_speech, this.mTitle);
            } else {
                title = getResources().getString(C0364R.string.lb_search_bar_hint_with_title, this.mTitle);
            }
        } else if (isVoiceMode()) {
            title = getResources().getString(C0364R.string.lb_search_bar_hint_speech);
        }
        this.mHint = title;
        SearchEditText searchEditText = this.mSearchTextEditor;
        if (searchEditText != null) {
            searchEditText.setHint(this.mHint);
        }
    }

    /* access modifiers changed from: package-private */
    public void toggleRecognition() {
        if (this.mRecognizing) {
            stopRecognition();
        } else {
            startRecognition();
        }
    }

    public boolean isRecognizing() {
        return this.mRecognizing;
    }

    public void stopRecognition() {
        if (this.mRecognizing) {
            this.mSearchTextEditor.setText(this.mSearchQuery);
            this.mSearchTextEditor.setHint(this.mHint);
            this.mRecognizing = false;
            if (this.mSpeechRecognitionCallback == null && this.mSpeechRecognizer != null) {
                this.mSpeechOrbView.showNotListening();
                if (this.mListening) {
                    this.mSpeechRecognizer.cancel();
                    this.mListening = false;
                }
                this.mSpeechRecognizer.setRecognitionListener(null);
            }
        }
    }

    public void setPermissionListener(SearchBarPermissionListener listener) {
        this.mPermissionListener = listener;
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
    public void startRecognition() {
        SearchBarPermissionListener searchBarPermissionListener;
        if (!this.mRecognizing) {
            if (!hasFocus()) {
                requestFocus();
            }
            if (this.mSpeechRecognitionCallback != null) {
                this.mSearchTextEditor.setText("");
                this.mSearchTextEditor.setHint("");
                this.mSpeechRecognitionCallback.recognizeSpeech();
                this.mRecognizing = true;
            } else if (this.mSpeechRecognizer != null) {
                if (getContext().checkCallingOrSelfPermission("android.permission.RECORD_AUDIO") == 0) {
                    this.mRecognizing = true;
                    this.mSearchTextEditor.setText("");
                    Intent recognizerIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                    recognizerIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                    recognizerIntent.putExtra("android.speech.extra.PARTIAL_RESULTS", true);
                    this.mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                        public void onReadyForSpeech(Bundle bundle) {
                            SearchBar.this.mSpeechOrbView.showListening();
                            SearchBar.this.playSearchOpen();
                        }

                        public void onBeginningOfSpeech() {
                        }

                        public void onRmsChanged(float rmsdB) {
                            SearchBar.this.mSpeechOrbView.setSoundLevel(rmsdB < 0.0f ? 0 : (int) (10.0f * rmsdB));
                        }

                        public void onBufferReceived(byte[] bytes) {
                        }

                        public void onEndOfSpeech() {
                        }

                        public void onError(int error) {
                            switch (error) {
                                case 1:
                                    Log.w(SearchBar.TAG, "recognizer network timeout");
                                    break;
                                case 2:
                                    Log.w(SearchBar.TAG, "recognizer network error");
                                    break;
                                case 3:
                                    Log.w(SearchBar.TAG, "recognizer audio error");
                                    break;
                                case 4:
                                    Log.w(SearchBar.TAG, "recognizer server error");
                                    break;
                                case 5:
                                    Log.w(SearchBar.TAG, "recognizer client error");
                                    break;
                                case 6:
                                    Log.w(SearchBar.TAG, "recognizer speech timeout");
                                    break;
                                case 7:
                                    Log.w(SearchBar.TAG, "recognizer no match");
                                    break;
                                case 8:
                                    Log.w(SearchBar.TAG, "recognizer busy");
                                    break;
                                case 9:
                                    Log.w(SearchBar.TAG, "recognizer insufficient permissions");
                                    break;
                                default:
                                    Log.d(SearchBar.TAG, "recognizer other error");
                                    break;
                            }
                            SearchBar.this.stopRecognition();
                            SearchBar.this.playSearchFailure();
                        }

                        public void onResults(Bundle bundle) {
                            ArrayList<String> matches = bundle.getStringArrayList("results_recognition");
                            if (matches != null) {
                                SearchBar.this.mSearchQuery = matches.get(0);
                                SearchBar.this.mSearchTextEditor.setText(SearchBar.this.mSearchQuery);
                                SearchBar.this.submitQuery();
                            }
                            SearchBar.this.stopRecognition();
                            SearchBar.this.playSearchSuccess();
                        }

                        public void onPartialResults(Bundle bundle) {
                            ArrayList<String> results = bundle.getStringArrayList("results_recognition");
                            if (results != null && results.size() != 0) {
                                SearchBar.this.mSearchTextEditor.updateRecognizedText(results.get(0), results.size() > 1 ? results.get(1) : null);
                            }
                        }

                        public void onEvent(int i, Bundle bundle) {
                        }
                    });
                    this.mListening = true;
                    this.mSpeechRecognizer.startListening(recognizerIntent);
                } else if (Build.VERSION.SDK_INT < 23 || (searchBarPermissionListener = this.mPermissionListener) == null) {
                    throw new IllegalStateException("android.permission.RECORD_AUDIO required for search");
                } else {
                    searchBarPermissionListener.requestAudioPermission();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void updateUi(boolean hasFocus) {
        if (hasFocus) {
            this.mBarBackground.setAlpha(this.mBackgroundSpeechAlpha);
            if (isVoiceMode()) {
                this.mSearchTextEditor.setTextColor(this.mTextHintColorSpeechMode);
                this.mSearchTextEditor.setHintTextColor(this.mTextHintColorSpeechMode);
            } else {
                this.mSearchTextEditor.setTextColor(this.mTextColorSpeechMode);
                this.mSearchTextEditor.setHintTextColor(this.mTextHintColorSpeechMode);
            }
        } else {
            this.mBarBackground.setAlpha(this.mBackgroundAlpha);
            this.mSearchTextEditor.setTextColor(this.mTextColor);
            this.mSearchTextEditor.setHintTextColor(this.mTextHintColor);
        }
        updateHint();
    }

    private boolean isVoiceMode() {
        return this.mSpeechOrbView.isFocused();
    }

    /* access modifiers changed from: package-private */
    public void submitQuery() {
        SearchBarListener searchBarListener;
        if (!TextUtils.isEmpty(this.mSearchQuery) && (searchBarListener = this.mSearchBarListener) != null) {
            searchBarListener.onSearchQuerySubmit(this.mSearchQuery);
        }
    }

    private void loadSounds(Context context) {
        for (int sound : new int[]{C0364R.raw.lb_voice_failure, C0364R.raw.lb_voice_open, C0364R.raw.lb_voice_no_input, C0364R.raw.lb_voice_success}) {
            this.mSoundMap.put(sound, this.mSoundPool.load(context, sound, 1));
        }
    }

    private void play(final int resId) {
        this.mHandler.post(new Runnable() {
            public void run() {
                SearchBar.this.mSoundPool.play(SearchBar.this.mSoundMap.get(resId), 1.0f, 1.0f, 1, 0, 1.0f);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void playSearchOpen() {
        play(C0364R.raw.lb_voice_open);
    }

    /* access modifiers changed from: package-private */
    public void playSearchFailure() {
        play(C0364R.raw.lb_voice_failure);
    }

    private void playSearchNoInput() {
        play(C0364R.raw.lb_voice_no_input);
    }

    /* access modifiers changed from: package-private */
    public void playSearchSuccess() {
        play(C0364R.raw.lb_voice_success);
    }

    public void setNextFocusDownId(int viewId) {
        this.mSpeechOrbView.setNextFocusDownId(viewId);
        this.mSearchTextEditor.setNextFocusDownId(viewId);
    }

    public interface SearchBarListener {
        void onKeyboardDismiss(String str);

        void onSearchQueryChange(String str);

        void onSearchQuerySubmit(String str);
    }

    public interface SearchBarPermissionListener {
        void requestAudioPermission();
    }
}
