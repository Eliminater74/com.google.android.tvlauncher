package com.google.android.exoplayer2.p008ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* renamed from: com.google.android.exoplayer2.ui.TrackSelectionView */
public class TrackSelectionView extends LinearLayout {
    private boolean allowAdaptiveSelections;
    private boolean allowMultipleOverrides;
    private final ComponentListener componentListener;
    private final CheckedTextView defaultView;
    private final CheckedTextView disableView;
    private final LayoutInflater inflater;
    private boolean isDisabled;
    @Nullable
    private TrackSelectionListener listener;
    private MappingTrackSelector.MappedTrackInfo mappedTrackInfo;
    private final SparseArray<DefaultTrackSelector.SelectionOverride> overrides;
    private int rendererIndex;
    private final int selectableItemBackgroundResourceId;
    private TrackGroupArray trackGroups;
    private TrackNameProvider trackNameProvider;
    private CheckedTextView[][] trackViews;

    /* renamed from: com.google.android.exoplayer2.ui.TrackSelectionView$TrackSelectionListener */
    public interface TrackSelectionListener {
        void onTrackSelectionChanged(boolean z, List<DefaultTrackSelector.SelectionOverride> list);
    }

    public TrackSelectionView(Context context) {
        this(context, null);
    }

    public TrackSelectionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, com.google.android.exoplayer2.ui.TrackSelectionView, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public TrackSelectionView(Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(1);
        this.overrides = new SparseArray<>();
        setSaveFromParentEnabled(false);
        TypedArray attributeArray = context.getTheme().obtainStyledAttributes(new int[]{16843534});
        this.selectableItemBackgroundResourceId = attributeArray.getResourceId(0, 0);
        attributeArray.recycle();
        this.inflater = LayoutInflater.from(context);
        this.componentListener = new ComponentListener();
        this.trackNameProvider = new DefaultTrackNameProvider(getResources());
        this.trackGroups = TrackGroupArray.EMPTY;
        this.disableView = (CheckedTextView) this.inflater.inflate(17367055, (ViewGroup) this, false);
        this.disableView.setBackgroundResource(this.selectableItemBackgroundResourceId);
        this.disableView.setText(C0931R.string.exo_track_selection_none);
        this.disableView.setEnabled(false);
        this.disableView.setFocusable(true);
        this.disableView.setOnClickListener(this.componentListener);
        this.disableView.setVisibility(8);
        addView(this.disableView);
        addView(this.inflater.inflate(C0931R.layout.exo_list_divider, (ViewGroup) this, false));
        this.defaultView = (CheckedTextView) this.inflater.inflate(17367055, (ViewGroup) this, false);
        this.defaultView.setBackgroundResource(this.selectableItemBackgroundResourceId);
        this.defaultView.setText(C0931R.string.exo_track_selection_auto);
        this.defaultView.setEnabled(false);
        this.defaultView.setFocusable(true);
        this.defaultView.setOnClickListener(this.componentListener);
        addView(this.defaultView);
    }

    public void setAllowAdaptiveSelections(boolean allowAdaptiveSelections2) {
        if (this.allowAdaptiveSelections != allowAdaptiveSelections2) {
            this.allowAdaptiveSelections = allowAdaptiveSelections2;
            updateViews();
        }
    }

    public void setAllowMultipleOverrides(boolean allowMultipleOverrides2) {
        if (this.allowMultipleOverrides != allowMultipleOverrides2) {
            this.allowMultipleOverrides = allowMultipleOverrides2;
            if (!allowMultipleOverrides2 && this.overrides.size() > 1) {
                for (int i = this.overrides.size() - 1; i > 0; i--) {
                    this.overrides.remove(i);
                }
            }
            updateViews();
        }
    }

    public void setShowDisableOption(boolean showDisableOption) {
        this.disableView.setVisibility(showDisableOption ? 0 : 8);
    }

    public void setTrackNameProvider(TrackNameProvider trackNameProvider2) {
        this.trackNameProvider = (TrackNameProvider) Assertions.checkNotNull(trackNameProvider2);
        updateViews();
    }

    public void init(MappingTrackSelector.MappedTrackInfo mappedTrackInfo2, int rendererIndex2, boolean isDisabled2, List<DefaultTrackSelector.SelectionOverride> overrides2, @Nullable TrackSelectionListener listener2) {
        this.mappedTrackInfo = mappedTrackInfo2;
        this.rendererIndex = rendererIndex2;
        this.isDisabled = isDisabled2;
        this.listener = listener2;
        int maxOverrides = this.allowMultipleOverrides ? overrides2.size() : Math.min(overrides2.size(), 1);
        for (int i = 0; i < maxOverrides; i++) {
            DefaultTrackSelector.SelectionOverride override = overrides2.get(i);
            this.overrides.put(override.groupIndex, override);
        }
        updateViews();
    }

    public boolean getIsDisabled() {
        return this.isDisabled;
    }

    public List<DefaultTrackSelector.SelectionOverride> getOverrides() {
        List<DefaultTrackSelector.SelectionOverride> overrideList = new ArrayList<>(this.overrides.size());
        for (int i = 0; i < this.overrides.size(); i++) {
            overrideList.add(this.overrides.valueAt(i));
        }
        return overrideList;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, com.google.android.exoplayer2.ui.TrackSelectionView, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    private void updateViews() {
        int trackViewLayoutId;
        for (int i = getChildCount() - 1; i >= 3; i--) {
            removeViewAt(i);
        }
        if (this.mappedTrackInfo == null) {
            this.disableView.setEnabled(false);
            this.defaultView.setEnabled(false);
            return;
        }
        this.disableView.setEnabled(true);
        this.defaultView.setEnabled(true);
        this.trackGroups = this.mappedTrackInfo.getTrackGroups(this.rendererIndex);
        this.trackViews = new CheckedTextView[this.trackGroups.length][];
        boolean enableMultipleChoiceForMultipleOverrides = shouldEnableMultiGroupSelection();
        for (int groupIndex = 0; groupIndex < this.trackGroups.length; groupIndex++) {
            TrackGroup group = this.trackGroups.get(groupIndex);
            boolean enableMultipleChoiceForAdaptiveSelections = shouldEnableAdaptiveSelection(groupIndex);
            this.trackViews[groupIndex] = new CheckedTextView[group.length];
            for (int trackIndex = 0; trackIndex < group.length; trackIndex++) {
                if (trackIndex == 0) {
                    addView(this.inflater.inflate(C0931R.layout.exo_list_divider, (ViewGroup) this, false));
                }
                if (enableMultipleChoiceForAdaptiveSelections || enableMultipleChoiceForMultipleOverrides) {
                    trackViewLayoutId = 17367056;
                } else {
                    trackViewLayoutId = 17367055;
                }
                CheckedTextView trackView = (CheckedTextView) this.inflater.inflate(trackViewLayoutId, (ViewGroup) this, false);
                trackView.setBackgroundResource(this.selectableItemBackgroundResourceId);
                trackView.setText(this.trackNameProvider.getTrackName(group.getFormat(trackIndex)));
                if (this.mappedTrackInfo.getTrackSupport(this.rendererIndex, groupIndex, trackIndex) == 4) {
                    trackView.setFocusable(true);
                    trackView.setTag(Pair.create(Integer.valueOf(groupIndex), Integer.valueOf(trackIndex)));
                    trackView.setOnClickListener(this.componentListener);
                } else {
                    trackView.setFocusable(false);
                    trackView.setEnabled(false);
                }
                this.trackViews[groupIndex][trackIndex] = trackView;
                addView(trackView);
            }
        }
        updateViewStates();
    }

    private void updateViewStates() {
        this.disableView.setChecked(this.isDisabled);
        this.defaultView.setChecked(!this.isDisabled && this.overrides.size() == 0);
        for (int i = 0; i < this.trackViews.length; i++) {
            DefaultTrackSelector.SelectionOverride override = this.overrides.get(i);
            int j = 0;
            while (true) {
                CheckedTextView[][] checkedTextViewArr = this.trackViews;
                if (j >= checkedTextViewArr[i].length) {
                    break;
                }
                checkedTextViewArr[i][j].setChecked(override != null && override.containsTrack(j));
                j++;
            }
        }
    }

    /* access modifiers changed from: private */
    public void onClick(View view) {
        if (view == this.disableView) {
            onDisableViewClicked();
        } else if (view == this.defaultView) {
            onDefaultViewClicked();
        } else {
            onTrackViewClicked(view);
        }
        updateViewStates();
        TrackSelectionListener trackSelectionListener = this.listener;
        if (trackSelectionListener != null) {
            trackSelectionListener.onTrackSelectionChanged(getIsDisabled(), getOverrides());
        }
    }

    private void onDisableViewClicked() {
        this.isDisabled = true;
        this.overrides.clear();
    }

    private void onDefaultViewClicked() {
        this.isDisabled = false;
        this.overrides.clear();
    }

    private void onTrackViewClicked(View view) {
        this.isDisabled = false;
        Pair<Integer, Integer> tag = (Pair) view.getTag();
        int groupIndex = ((Integer) tag.first).intValue();
        int trackIndex = ((Integer) tag.second).intValue();
        DefaultTrackSelector.SelectionOverride override = this.overrides.get(groupIndex);
        Assertions.checkNotNull(this.mappedTrackInfo);
        if (override == null) {
            if (!this.allowMultipleOverrides && this.overrides.size() > 0) {
                this.overrides.clear();
            }
            this.overrides.put(groupIndex, new DefaultTrackSelector.SelectionOverride(groupIndex, trackIndex));
            return;
        }
        int overrideLength = override.length;
        int[] overrideTracks = override.tracks;
        boolean isCurrentlySelected = ((CheckedTextView) view).isChecked();
        boolean isAdaptiveAllowed = shouldEnableAdaptiveSelection(groupIndex);
        boolean isUsingCheckBox = isAdaptiveAllowed || shouldEnableMultiGroupSelection();
        if (!isCurrentlySelected || !isUsingCheckBox) {
            if (isCurrentlySelected) {
                return;
            }
            if (isAdaptiveAllowed) {
                this.overrides.put(groupIndex, new DefaultTrackSelector.SelectionOverride(groupIndex, getTracksAdding(overrideTracks, trackIndex)));
            } else {
                this.overrides.put(groupIndex, new DefaultTrackSelector.SelectionOverride(groupIndex, trackIndex));
            }
        } else if (overrideLength == 1) {
            this.overrides.remove(groupIndex);
        } else {
            this.overrides.put(groupIndex, new DefaultTrackSelector.SelectionOverride(groupIndex, getTracksRemoving(overrideTracks, trackIndex)));
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo.getAdaptiveSupport(int, int, boolean):int
     arg types: [int, int, int]
     candidates:
      com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo.getAdaptiveSupport(int, int, int[]):int
      com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo.getAdaptiveSupport(int, int, boolean):int */
    @RequiresNonNull({"mappedTrackInfo"})
    private boolean shouldEnableAdaptiveSelection(int groupIndex) {
        if (!this.allowAdaptiveSelections || this.trackGroups.get(groupIndex).length <= 1 || this.mappedTrackInfo.getAdaptiveSupport(this.rendererIndex, groupIndex, false) == 0) {
            return false;
        }
        return true;
    }

    private boolean shouldEnableMultiGroupSelection() {
        return this.allowMultipleOverrides && this.trackGroups.length > 1;
    }

    private static int[] getTracksAdding(int[] tracks, int addedTrack) {
        int[] tracks2 = Arrays.copyOf(tracks, tracks.length + 1);
        tracks2[tracks2.length - 1] = addedTrack;
        return tracks2;
    }

    private static int[] getTracksRemoving(int[] tracks, int removedTrack) {
        int[] newTracks = new int[(tracks.length - 1)];
        int trackCount = 0;
        for (int track : tracks) {
            if (track != removedTrack) {
                newTracks[trackCount] = track;
                trackCount++;
            }
        }
        return newTracks;
    }

    /* renamed from: com.google.android.exoplayer2.ui.TrackSelectionView$ComponentListener */
    private class ComponentListener implements View.OnClickListener {
        private ComponentListener() {
        }

        public void onClick(View view) {
            TrackSelectionView.this.onClick(view);
        }
    }
}
