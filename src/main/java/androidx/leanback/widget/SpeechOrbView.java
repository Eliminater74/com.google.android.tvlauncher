package androidx.leanback.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import androidx.leanback.C0364R;
import androidx.leanback.widget.SearchOrbView;

public class SpeechOrbView extends SearchOrbView {
    private int mCurrentLevel;
    private boolean mListening;
    private SearchOrbView.Colors mListeningOrbColors;
    private SearchOrbView.Colors mNotListeningOrbColors;
    private final float mSoundLevelMaxZoom;

    public SpeechOrbView(Context context) {
        this(context, null);
    }

    public SpeechOrbView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpeechOrbView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCurrentLevel = 0;
        this.mListening = false;
        Resources resources = context.getResources();
        this.mSoundLevelMaxZoom = resources.getFraction(C0364R.fraction.lb_search_bar_speech_orb_max_level_zoom, 1, 1);
        this.mNotListeningOrbColors = new SearchOrbView.Colors(resources.getColor(C0364R.color.lb_speech_orb_not_recording), resources.getColor(C0364R.color.lb_speech_orb_not_recording_pulsed), resources.getColor(C0364R.color.lb_speech_orb_not_recording_icon));
        this.mListeningOrbColors = new SearchOrbView.Colors(resources.getColor(C0364R.color.lb_speech_orb_recording), resources.getColor(C0364R.color.lb_speech_orb_recording), 0);
        showNotListening();
    }

    /* access modifiers changed from: package-private */
    public int getLayoutResourceId() {
        return C0364R.layout.lb_speech_orb;
    }

    public void setListeningOrbColors(SearchOrbView.Colors colors) {
        this.mListeningOrbColors = colors;
    }

    public void setNotListeningOrbColors(SearchOrbView.Colors colors) {
        this.mNotListeningOrbColors = colors;
    }

    public void showListening() {
        setOrbColors(this.mListeningOrbColors);
        setOrbIcon(getResources().getDrawable(C0364R.C0365drawable.lb_ic_search_mic));
        animateOnFocus(true);
        enableOrbColorAnimation(false);
        scaleOrbViewOnly(1.0f);
        this.mCurrentLevel = 0;
        this.mListening = true;
    }

    public void showNotListening() {
        setOrbColors(this.mNotListeningOrbColors);
        setOrbIcon(getResources().getDrawable(C0364R.C0365drawable.lb_ic_search_mic_out));
        animateOnFocus(hasFocus());
        scaleOrbViewOnly(1.0f);
        this.mListening = false;
    }

    public void setSoundLevel(int level) {
        if (this.mListening) {
            int i = this.mCurrentLevel;
            if (level > i) {
                this.mCurrentLevel = i + ((level - i) / 2);
            } else {
                this.mCurrentLevel = (int) (((float) i) * 0.7f);
            }
            scaleOrbViewOnly((((this.mSoundLevelMaxZoom - getFocusedZoom()) * ((float) this.mCurrentLevel)) / 100.0f) + 1.0f);
        }
    }
}
