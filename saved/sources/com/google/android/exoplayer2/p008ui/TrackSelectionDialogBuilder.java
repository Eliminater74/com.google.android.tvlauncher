package com.google.android.exoplayer2.p008ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Collections;
import java.util.List;

/* renamed from: com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder */
public final class TrackSelectionDialogBuilder {
    private boolean allowAdaptiveSelections;
    private boolean allowMultipleOverrides;
    private final DialogCallback callback;
    private final Context context;
    private boolean isDisabled;
    private final MappingTrackSelector.MappedTrackInfo mappedTrackInfo;
    private List<DefaultTrackSelector.SelectionOverride> overrides;
    private final int rendererIndex;
    private boolean showDisableOption;
    private final CharSequence title;
    @Nullable
    private TrackNameProvider trackNameProvider;

    /* renamed from: com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder$DialogCallback */
    public interface DialogCallback {
        void onTracksSelected(boolean z, List<DefaultTrackSelector.SelectionOverride> list);
    }

    public TrackSelectionDialogBuilder(Context context2, CharSequence title2, MappingTrackSelector.MappedTrackInfo mappedTrackInfo2, int rendererIndex2, DialogCallback callback2) {
        this.context = context2;
        this.title = title2;
        this.mappedTrackInfo = mappedTrackInfo2;
        this.rendererIndex = rendererIndex2;
        this.callback = callback2;
        this.overrides = Collections.emptyList();
    }

    public TrackSelectionDialogBuilder(Context context2, CharSequence title2, DefaultTrackSelector trackSelector, int rendererIndex2) {
        this.context = context2;
        this.title = title2;
        this.mappedTrackInfo = (MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(trackSelector.getCurrentMappedTrackInfo());
        this.rendererIndex = rendererIndex2;
        TrackGroupArray rendererTrackGroups = this.mappedTrackInfo.getTrackGroups(rendererIndex2);
        DefaultTrackSelector.Parameters selectionParameters = trackSelector.getParameters();
        this.isDisabled = selectionParameters.getRendererDisabled(rendererIndex2);
        DefaultTrackSelector.SelectionOverride override = selectionParameters.getSelectionOverride(rendererIndex2, rendererTrackGroups);
        this.overrides = override == null ? Collections.emptyList() : Collections.singletonList(override);
        this.callback = new TrackSelectionDialogBuilder$$Lambda$0(trackSelector, selectionParameters, rendererIndex2, rendererTrackGroups);
    }

    static final /* synthetic */ void lambda$new$0$TrackSelectionDialogBuilder(DefaultTrackSelector trackSelector, DefaultTrackSelector.Parameters selectionParameters, int rendererIndex2, TrackGroupArray rendererTrackGroups, boolean newIsDisabled, List newOverrides) {
        trackSelector.setParameters(TrackSelectionUtil.updateParametersWithOverride(selectionParameters, rendererIndex2, rendererTrackGroups, newIsDisabled, newOverrides.isEmpty() ? null : (DefaultTrackSelector.SelectionOverride) newOverrides.get(0)));
    }

    public TrackSelectionDialogBuilder setIsDisabled(boolean isDisabled2) {
        this.isDisabled = isDisabled2;
        return this;
    }

    public TrackSelectionDialogBuilder setOverride(@Nullable DefaultTrackSelector.SelectionOverride override) {
        return setOverrides(override == null ? Collections.emptyList() : Collections.singletonList(override));
    }

    public TrackSelectionDialogBuilder setOverrides(List<DefaultTrackSelector.SelectionOverride> overrides2) {
        this.overrides = overrides2;
        return this;
    }

    public TrackSelectionDialogBuilder setAllowAdaptiveSelections(boolean allowAdaptiveSelections2) {
        this.allowAdaptiveSelections = allowAdaptiveSelections2;
        return this;
    }

    public TrackSelectionDialogBuilder setAllowMultipleOverrides(boolean allowMultipleOverrides2) {
        this.allowMultipleOverrides = allowMultipleOverrides2;
        return this;
    }

    public TrackSelectionDialogBuilder setShowDisableOption(boolean showDisableOption2) {
        this.showDisableOption = showDisableOption2;
        return this;
    }

    public TrackSelectionDialogBuilder setTrackNameProvider(@Nullable TrackNameProvider trackNameProvider2) {
        this.trackNameProvider = trackNameProvider2;
        return this;
    }

    public AlertDialog build() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        View dialogView = LayoutInflater.from(builder.getContext()).inflate(C0931R.layout.exo_track_selection_dialog, (ViewGroup) null);
        TrackSelectionView selectionView = (TrackSelectionView) dialogView.findViewById(C0931R.C0933id.exo_track_selection_view);
        selectionView.setAllowMultipleOverrides(this.allowMultipleOverrides);
        selectionView.setAllowAdaptiveSelections(this.allowAdaptiveSelections);
        selectionView.setShowDisableOption(this.showDisableOption);
        TrackNameProvider trackNameProvider2 = this.trackNameProvider;
        if (trackNameProvider2 != null) {
            selectionView.setTrackNameProvider(trackNameProvider2);
        }
        selectionView.init(this.mappedTrackInfo, this.rendererIndex, this.isDisabled, this.overrides, null);
        return builder.setTitle(this.title).setView(dialogView).setPositiveButton(17039370, new TrackSelectionDialogBuilder$$Lambda$1(this, selectionView)).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).create();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$build$1$TrackSelectionDialogBuilder(TrackSelectionView selectionView, DialogInterface dialog, int which) {
        this.callback.onTracksSelected(selectionView.getIsDisabled(), selectionView.getOverrides());
    }
}
