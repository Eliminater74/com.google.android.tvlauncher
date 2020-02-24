package com.google.android.exoplayer2.trackselection;

import android.content.Context;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class DefaultTrackSelector extends MappingTrackSelector {
    private static final float FRACTION_TO_CONSIDER_FULLSCREEN = 0.98f;
    private static final int[] NO_TRACKS = new int[0];
    private static final int WITHIN_RENDERER_CAPABILITIES_BONUS = 1000;
    private boolean allowMultipleAdaptiveSelections;
    private final AtomicReference<Parameters> parametersReference;
    private final TrackSelection.Factory trackSelectionFactory;

    public static final class ParametersBuilder extends TrackSelectionParameters.Builder {
        private boolean allowAudioMixedMimeTypeAdaptiveness;
        private boolean allowAudioMixedSampleRateAdaptiveness;
        private boolean allowVideoMixedMimeTypeAdaptiveness;
        private boolean allowVideoNonSeamlessAdaptiveness;
        private boolean exceedAudioConstraintsIfNecessary;
        private boolean exceedRendererCapabilitiesIfNecessary;
        private boolean exceedVideoConstraintsIfNecessary;
        private boolean forceHighestSupportedBitrate;
        private boolean forceLowestBitrate;
        private int maxAudioBitrate;
        private int maxAudioChannelCount;
        private int maxVideoBitrate;
        private int maxVideoFrameRate;
        private int maxVideoHeight;
        private int maxVideoWidth;
        private final SparseBooleanArray rendererDisabledFlags;
        private final SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides;
        private int tunnelingAudioSessionId;
        private int viewportHeight;
        private boolean viewportOrientationMayChange;
        private int viewportWidth;

        public ParametersBuilder() {
            this(Parameters.DEFAULT);
        }

        private ParametersBuilder(Parameters initialValues) {
            super(initialValues);
            this.maxVideoWidth = initialValues.maxVideoWidth;
            this.maxVideoHeight = initialValues.maxVideoHeight;
            this.maxVideoFrameRate = initialValues.maxVideoFrameRate;
            this.maxVideoBitrate = initialValues.maxVideoBitrate;
            this.exceedVideoConstraintsIfNecessary = initialValues.exceedVideoConstraintsIfNecessary;
            this.allowVideoMixedMimeTypeAdaptiveness = initialValues.allowVideoMixedMimeTypeAdaptiveness;
            this.allowVideoNonSeamlessAdaptiveness = initialValues.allowVideoNonSeamlessAdaptiveness;
            this.viewportWidth = initialValues.viewportWidth;
            this.viewportHeight = initialValues.viewportHeight;
            this.viewportOrientationMayChange = initialValues.viewportOrientationMayChange;
            this.maxAudioChannelCount = initialValues.maxAudioChannelCount;
            this.maxAudioBitrate = initialValues.maxAudioBitrate;
            this.exceedAudioConstraintsIfNecessary = initialValues.exceedAudioConstraintsIfNecessary;
            this.allowAudioMixedMimeTypeAdaptiveness = initialValues.allowAudioMixedMimeTypeAdaptiveness;
            this.allowAudioMixedSampleRateAdaptiveness = initialValues.allowAudioMixedSampleRateAdaptiveness;
            this.forceLowestBitrate = initialValues.forceLowestBitrate;
            this.forceHighestSupportedBitrate = initialValues.forceHighestSupportedBitrate;
            this.exceedRendererCapabilitiesIfNecessary = initialValues.exceedRendererCapabilitiesIfNecessary;
            this.tunnelingAudioSessionId = initialValues.tunnelingAudioSessionId;
            this.selectionOverrides = cloneSelectionOverrides(initialValues.selectionOverrides);
            this.rendererDisabledFlags = initialValues.rendererDisabledFlags.clone();
        }

        public ParametersBuilder setMaxVideoSizeSd() {
            return setMaxVideoSize(1279, ClientAnalytics.LogRequest.LogSource.WING_OPENSKY_ANDROID_PRIMES_VALUE);
        }

        public ParametersBuilder clearVideoSizeConstraints() {
            return setMaxVideoSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        public ParametersBuilder setMaxVideoSize(int maxVideoWidth2, int maxVideoHeight2) {
            this.maxVideoWidth = maxVideoWidth2;
            this.maxVideoHeight = maxVideoHeight2;
            return this;
        }

        public ParametersBuilder setMaxVideoFrameRate(int maxVideoFrameRate2) {
            this.maxVideoFrameRate = maxVideoFrameRate2;
            return this;
        }

        public ParametersBuilder setMaxVideoBitrate(int maxVideoBitrate2) {
            this.maxVideoBitrate = maxVideoBitrate2;
            return this;
        }

        public ParametersBuilder setExceedVideoConstraintsIfNecessary(boolean exceedVideoConstraintsIfNecessary2) {
            this.exceedVideoConstraintsIfNecessary = exceedVideoConstraintsIfNecessary2;
            return this;
        }

        public ParametersBuilder setAllowVideoMixedMimeTypeAdaptiveness(boolean allowVideoMixedMimeTypeAdaptiveness2) {
            this.allowVideoMixedMimeTypeAdaptiveness = allowVideoMixedMimeTypeAdaptiveness2;
            return this;
        }

        public ParametersBuilder setAllowVideoNonSeamlessAdaptiveness(boolean allowVideoNonSeamlessAdaptiveness2) {
            this.allowVideoNonSeamlessAdaptiveness = allowVideoNonSeamlessAdaptiveness2;
            return this;
        }

        public ParametersBuilder setViewportSizeToPhysicalDisplaySize(Context context, boolean viewportOrientationMayChange2) {
            Point viewportSize = Util.getPhysicalDisplaySize(context);
            return setViewportSize(viewportSize.x, viewportSize.y, viewportOrientationMayChange2);
        }

        public ParametersBuilder clearViewportSizeConstraints() {
            return setViewportSize(Integer.MAX_VALUE, Integer.MAX_VALUE, true);
        }

        public ParametersBuilder setViewportSize(int viewportWidth2, int viewportHeight2, boolean viewportOrientationMayChange2) {
            this.viewportWidth = viewportWidth2;
            this.viewportHeight = viewportHeight2;
            this.viewportOrientationMayChange = viewportOrientationMayChange2;
            return this;
        }

        public ParametersBuilder setPreferredAudioLanguage(@Nullable String preferredAudioLanguage) {
            super.setPreferredAudioLanguage(preferredAudioLanguage);
            return this;
        }

        public ParametersBuilder setMaxAudioChannelCount(int maxAudioChannelCount2) {
            this.maxAudioChannelCount = maxAudioChannelCount2;
            return this;
        }

        public ParametersBuilder setMaxAudioBitrate(int maxAudioBitrate2) {
            this.maxAudioBitrate = maxAudioBitrate2;
            return this;
        }

        public ParametersBuilder setExceedAudioConstraintsIfNecessary(boolean exceedAudioConstraintsIfNecessary2) {
            this.exceedAudioConstraintsIfNecessary = exceedAudioConstraintsIfNecessary2;
            return this;
        }

        public ParametersBuilder setAllowAudioMixedMimeTypeAdaptiveness(boolean allowAudioMixedMimeTypeAdaptiveness2) {
            this.allowAudioMixedMimeTypeAdaptiveness = allowAudioMixedMimeTypeAdaptiveness2;
            return this;
        }

        public ParametersBuilder setAllowAudioMixedSampleRateAdaptiveness(boolean allowAudioMixedSampleRateAdaptiveness2) {
            this.allowAudioMixedSampleRateAdaptiveness = allowAudioMixedSampleRateAdaptiveness2;
            return this;
        }

        public ParametersBuilder setPreferredTextLanguage(@Nullable String preferredTextLanguage) {
            super.setPreferredTextLanguage(preferredTextLanguage);
            return this;
        }

        public ParametersBuilder setSelectUndeterminedTextLanguage(boolean selectUndeterminedTextLanguage) {
            super.setSelectUndeterminedTextLanguage(selectUndeterminedTextLanguage);
            return this;
        }

        public ParametersBuilder setDisabledTextTrackSelectionFlags(int disabledTextTrackSelectionFlags) {
            super.setDisabledTextTrackSelectionFlags(disabledTextTrackSelectionFlags);
            return this;
        }

        public ParametersBuilder setForceLowestBitrate(boolean forceLowestBitrate2) {
            this.forceLowestBitrate = forceLowestBitrate2;
            return this;
        }

        public ParametersBuilder setForceHighestSupportedBitrate(boolean forceHighestSupportedBitrate2) {
            this.forceHighestSupportedBitrate = forceHighestSupportedBitrate2;
            return this;
        }

        @Deprecated
        public ParametersBuilder setAllowMixedMimeAdaptiveness(boolean allowMixedMimeAdaptiveness) {
            setAllowAudioMixedMimeTypeAdaptiveness(allowMixedMimeAdaptiveness);
            setAllowVideoMixedMimeTypeAdaptiveness(allowMixedMimeAdaptiveness);
            return this;
        }

        @Deprecated
        public ParametersBuilder setAllowNonSeamlessAdaptiveness(boolean allowNonSeamlessAdaptiveness) {
            return setAllowVideoNonSeamlessAdaptiveness(allowNonSeamlessAdaptiveness);
        }

        public ParametersBuilder setExceedRendererCapabilitiesIfNecessary(boolean exceedRendererCapabilitiesIfNecessary2) {
            this.exceedRendererCapabilitiesIfNecessary = exceedRendererCapabilitiesIfNecessary2;
            return this;
        }

        public ParametersBuilder setTunnelingAudioSessionId(int tunnelingAudioSessionId2) {
            this.tunnelingAudioSessionId = tunnelingAudioSessionId2;
            return this;
        }

        public final ParametersBuilder setRendererDisabled(int rendererIndex, boolean disabled) {
            if (this.rendererDisabledFlags.get(rendererIndex) == disabled) {
                return this;
            }
            if (disabled) {
                this.rendererDisabledFlags.put(rendererIndex, true);
            } else {
                this.rendererDisabledFlags.delete(rendererIndex);
            }
            return this;
        }

        public final ParametersBuilder setSelectionOverride(int rendererIndex, TrackGroupArray groups, SelectionOverride override) {
            Map<TrackGroupArray, SelectionOverride> overrides = this.selectionOverrides.get(rendererIndex);
            if (overrides == null) {
                overrides = new HashMap<>();
                this.selectionOverrides.put(rendererIndex, overrides);
            }
            if (overrides.containsKey(groups) && Util.areEqual(overrides.get(groups), override)) {
                return this;
            }
            overrides.put(groups, override);
            return this;
        }

        public final ParametersBuilder clearSelectionOverride(int rendererIndex, TrackGroupArray groups) {
            Map<TrackGroupArray, SelectionOverride> overrides = this.selectionOverrides.get(rendererIndex);
            if (overrides == null || !overrides.containsKey(groups)) {
                return this;
            }
            overrides.remove(groups);
            if (overrides.isEmpty()) {
                this.selectionOverrides.remove(rendererIndex);
            }
            return this;
        }

        public final ParametersBuilder clearSelectionOverrides(int rendererIndex) {
            Map<TrackGroupArray, SelectionOverride> overrides = this.selectionOverrides.get(rendererIndex);
            if (overrides == null || overrides.isEmpty()) {
                return this;
            }
            this.selectionOverrides.remove(rendererIndex);
            return this;
        }

        public final ParametersBuilder clearSelectionOverrides() {
            if (this.selectionOverrides.size() == 0) {
                return this;
            }
            this.selectionOverrides.clear();
            return this;
        }

        public Parameters build() {
            return new Parameters(this.maxVideoWidth, this.maxVideoHeight, this.maxVideoFrameRate, this.maxVideoBitrate, this.exceedVideoConstraintsIfNecessary, this.allowVideoMixedMimeTypeAdaptiveness, this.allowVideoNonSeamlessAdaptiveness, this.viewportWidth, this.viewportHeight, this.viewportOrientationMayChange, this.preferredAudioLanguage, this.maxAudioChannelCount, this.maxAudioBitrate, this.exceedAudioConstraintsIfNecessary, this.allowAudioMixedMimeTypeAdaptiveness, this.allowAudioMixedSampleRateAdaptiveness, this.preferredTextLanguage, this.selectUndeterminedTextLanguage, this.disabledTextTrackSelectionFlags, this.forceLowestBitrate, this.forceHighestSupportedBitrate, this.exceedRendererCapabilitiesIfNecessary, this.tunnelingAudioSessionId, this.selectionOverrides, this.rendererDisabledFlags);
        }

        private static SparseArray<Map<TrackGroupArray, SelectionOverride>> cloneSelectionOverrides(SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides2) {
            SparseArray<Map<TrackGroupArray, SelectionOverride>> clone = new SparseArray<>();
            for (int i = 0; i < selectionOverrides2.size(); i++) {
                clone.put(selectionOverrides2.keyAt(i), new HashMap(selectionOverrides2.valueAt(i)));
            }
            return clone;
        }
    }

    public static final class Parameters extends TrackSelectionParameters {
        public static final Parcelable.Creator<Parameters> CREATOR = new Parcelable.Creator<Parameters>() {
            public Parameters createFromParcel(Parcel in) {
                return new Parameters(in);
            }

            public Parameters[] newArray(int size) {
                return new Parameters[size];
            }
        };
        public static final Parameters DEFAULT = new Parameters();
        public final boolean allowAudioMixedMimeTypeAdaptiveness;
        public final boolean allowAudioMixedSampleRateAdaptiveness;
        @Deprecated
        public final boolean allowMixedMimeAdaptiveness;
        @Deprecated
        public final boolean allowNonSeamlessAdaptiveness;
        public final boolean allowVideoMixedMimeTypeAdaptiveness;
        public final boolean allowVideoNonSeamlessAdaptiveness;
        public final boolean exceedAudioConstraintsIfNecessary;
        public final boolean exceedRendererCapabilitiesIfNecessary;
        public final boolean exceedVideoConstraintsIfNecessary;
        public final boolean forceHighestSupportedBitrate;
        public final boolean forceLowestBitrate;
        public final int maxAudioBitrate;
        public final int maxAudioChannelCount;
        public final int maxVideoBitrate;
        public final int maxVideoFrameRate;
        public final int maxVideoHeight;
        public final int maxVideoWidth;
        /* access modifiers changed from: private */
        public final SparseBooleanArray rendererDisabledFlags;
        /* access modifiers changed from: private */
        public final SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides;
        public final int tunnelingAudioSessionId;
        public final int viewportHeight;
        public final boolean viewportOrientationMayChange;
        public final int viewportWidth;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private Parameters() {
            /*
                r26 = this;
                r0 = r26
                com.google.android.exoplayer2.trackselection.TrackSelectionParameters r1 = com.google.android.exoplayer2.trackselection.TrackSelectionParameters.DEFAULT
                java.lang.String r11 = r1.preferredAudioLanguage
                com.google.android.exoplayer2.trackselection.TrackSelectionParameters r1 = com.google.android.exoplayer2.trackselection.TrackSelectionParameters.DEFAULT
                java.lang.String r1 = r1.preferredTextLanguage
                r17 = r1
                com.google.android.exoplayer2.trackselection.TrackSelectionParameters r1 = com.google.android.exoplayer2.trackselection.TrackSelectionParameters.DEFAULT
                boolean r1 = r1.selectUndeterminedTextLanguage
                r18 = r1
                com.google.android.exoplayer2.trackselection.TrackSelectionParameters r1 = com.google.android.exoplayer2.trackselection.TrackSelectionParameters.DEFAULT
                int r1 = r1.disabledTextTrackSelectionFlags
                r19 = r1
                android.util.SparseArray r1 = new android.util.SparseArray
                r24 = r1
                r1.<init>()
                android.util.SparseBooleanArray r1 = new android.util.SparseBooleanArray
                r25 = r1
                r1.<init>()
                r1 = 2147483647(0x7fffffff, float:NaN)
                r2 = 2147483647(0x7fffffff, float:NaN)
                r3 = 2147483647(0x7fffffff, float:NaN)
                r4 = 2147483647(0x7fffffff, float:NaN)
                r5 = 1
                r6 = 0
                r7 = 1
                r8 = 2147483647(0x7fffffff, float:NaN)
                r9 = 2147483647(0x7fffffff, float:NaN)
                r10 = 1
                r12 = 2147483647(0x7fffffff, float:NaN)
                r13 = 2147483647(0x7fffffff, float:NaN)
                r14 = 1
                r15 = 0
                r16 = 0
                r20 = 0
                r21 = 0
                r22 = 1
                r23 = 0
                r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters.<init>():void");
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        Parameters(int maxVideoWidth2, int maxVideoHeight2, int maxVideoFrameRate2, int maxVideoBitrate2, boolean exceedVideoConstraintsIfNecessary2, boolean allowVideoMixedMimeTypeAdaptiveness2, boolean allowVideoNonSeamlessAdaptiveness2, int viewportWidth2, int viewportHeight2, boolean viewportOrientationMayChange2, @Nullable String preferredAudioLanguage, int maxAudioChannelCount2, int maxAudioBitrate2, boolean exceedAudioConstraintsIfNecessary2, boolean allowAudioMixedMimeTypeAdaptiveness2, boolean allowAudioMixedSampleRateAdaptiveness2, @Nullable String preferredTextLanguage, boolean selectUndeterminedTextLanguage, int disabledTextTrackSelectionFlags, boolean forceLowestBitrate2, boolean forceHighestSupportedBitrate2, boolean exceedRendererCapabilitiesIfNecessary2, int tunnelingAudioSessionId2, SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides2, SparseBooleanArray rendererDisabledFlags2) {
            super(preferredAudioLanguage, preferredTextLanguage, selectUndeterminedTextLanguage, disabledTextTrackSelectionFlags);
            boolean z = allowVideoMixedMimeTypeAdaptiveness2;
            boolean z2 = allowVideoNonSeamlessAdaptiveness2;
            this.maxVideoWidth = maxVideoWidth2;
            this.maxVideoHeight = maxVideoHeight2;
            this.maxVideoFrameRate = maxVideoFrameRate2;
            this.maxVideoBitrate = maxVideoBitrate2;
            this.exceedVideoConstraintsIfNecessary = exceedVideoConstraintsIfNecessary2;
            this.allowVideoMixedMimeTypeAdaptiveness = z;
            this.allowVideoNonSeamlessAdaptiveness = z2;
            this.viewportWidth = viewportWidth2;
            this.viewportHeight = viewportHeight2;
            this.viewportOrientationMayChange = viewportOrientationMayChange2;
            this.maxAudioChannelCount = maxAudioChannelCount2;
            this.maxAudioBitrate = maxAudioBitrate2;
            this.exceedAudioConstraintsIfNecessary = exceedAudioConstraintsIfNecessary2;
            this.allowAudioMixedMimeTypeAdaptiveness = allowAudioMixedMimeTypeAdaptiveness2;
            this.allowAudioMixedSampleRateAdaptiveness = allowAudioMixedSampleRateAdaptiveness2;
            this.forceLowestBitrate = forceLowestBitrate2;
            this.forceHighestSupportedBitrate = forceHighestSupportedBitrate2;
            this.exceedRendererCapabilitiesIfNecessary = exceedRendererCapabilitiesIfNecessary2;
            this.tunnelingAudioSessionId = tunnelingAudioSessionId2;
            this.allowMixedMimeAdaptiveness = z;
            this.allowNonSeamlessAdaptiveness = z2;
            this.selectionOverrides = selectionOverrides2;
            this.rendererDisabledFlags = rendererDisabledFlags2;
        }

        Parameters(Parcel in) {
            super(in);
            this.maxVideoWidth = in.readInt();
            this.maxVideoHeight = in.readInt();
            this.maxVideoFrameRate = in.readInt();
            this.maxVideoBitrate = in.readInt();
            this.exceedVideoConstraintsIfNecessary = Util.readBoolean(in);
            this.allowVideoMixedMimeTypeAdaptiveness = Util.readBoolean(in);
            this.allowVideoNonSeamlessAdaptiveness = Util.readBoolean(in);
            this.viewportWidth = in.readInt();
            this.viewportHeight = in.readInt();
            this.viewportOrientationMayChange = Util.readBoolean(in);
            this.maxAudioChannelCount = in.readInt();
            this.maxAudioBitrate = in.readInt();
            this.exceedAudioConstraintsIfNecessary = Util.readBoolean(in);
            this.allowAudioMixedMimeTypeAdaptiveness = Util.readBoolean(in);
            this.allowAudioMixedSampleRateAdaptiveness = Util.readBoolean(in);
            this.forceLowestBitrate = Util.readBoolean(in);
            this.forceHighestSupportedBitrate = Util.readBoolean(in);
            this.exceedRendererCapabilitiesIfNecessary = Util.readBoolean(in);
            this.tunnelingAudioSessionId = in.readInt();
            this.selectionOverrides = readSelectionOverrides(in);
            this.rendererDisabledFlags = (SparseBooleanArray) Util.castNonNull(in.readSparseBooleanArray());
            this.allowMixedMimeAdaptiveness = this.allowVideoMixedMimeTypeAdaptiveness;
            this.allowNonSeamlessAdaptiveness = this.allowVideoNonSeamlessAdaptiveness;
        }

        public final boolean getRendererDisabled(int rendererIndex) {
            return this.rendererDisabledFlags.get(rendererIndex);
        }

        public final boolean hasSelectionOverride(int rendererIndex, TrackGroupArray groups) {
            Map<TrackGroupArray, SelectionOverride> overrides = this.selectionOverrides.get(rendererIndex);
            return overrides != null && overrides.containsKey(groups);
        }

        @Nullable
        public final SelectionOverride getSelectionOverride(int rendererIndex, TrackGroupArray groups) {
            Map<TrackGroupArray, SelectionOverride> overrides = this.selectionOverrides.get(rendererIndex);
            if (overrides != null) {
                return (SelectionOverride) overrides.get(groups);
            }
            return null;
        }

        public ParametersBuilder buildUpon() {
            return new ParametersBuilder(this);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Parameters other = (Parameters) obj;
            if (super.equals(obj) && this.maxVideoWidth == other.maxVideoWidth && this.maxVideoHeight == other.maxVideoHeight && this.maxVideoFrameRate == other.maxVideoFrameRate && this.maxVideoBitrate == other.maxVideoBitrate && this.exceedVideoConstraintsIfNecessary == other.exceedVideoConstraintsIfNecessary && this.allowVideoMixedMimeTypeAdaptiveness == other.allowVideoMixedMimeTypeAdaptiveness && this.allowVideoNonSeamlessAdaptiveness == other.allowVideoNonSeamlessAdaptiveness && this.viewportOrientationMayChange == other.viewportOrientationMayChange && this.viewportWidth == other.viewportWidth && this.viewportHeight == other.viewportHeight && this.maxAudioChannelCount == other.maxAudioChannelCount && this.maxAudioBitrate == other.maxAudioBitrate && this.exceedAudioConstraintsIfNecessary == other.exceedAudioConstraintsIfNecessary && this.allowAudioMixedMimeTypeAdaptiveness == other.allowAudioMixedMimeTypeAdaptiveness && this.allowAudioMixedSampleRateAdaptiveness == other.allowAudioMixedSampleRateAdaptiveness && this.forceLowestBitrate == other.forceLowestBitrate && this.forceHighestSupportedBitrate == other.forceHighestSupportedBitrate && this.exceedRendererCapabilitiesIfNecessary == other.exceedRendererCapabilitiesIfNecessary && this.tunnelingAudioSessionId == other.tunnelingAudioSessionId && areRendererDisabledFlagsEqual(this.rendererDisabledFlags, other.rendererDisabledFlags) && areSelectionOverridesEqual(this.selectionOverrides, other.selectionOverrides)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (((((((((((((((((((((((((((((((((((((super.hashCode() * 31) + this.maxVideoWidth) * 31) + this.maxVideoHeight) * 31) + this.maxVideoFrameRate) * 31) + this.maxVideoBitrate) * 31) + (this.exceedVideoConstraintsIfNecessary ? 1 : 0)) * 31) + (this.allowVideoMixedMimeTypeAdaptiveness ? 1 : 0)) * 31) + (this.allowVideoNonSeamlessAdaptiveness ? 1 : 0)) * 31) + (this.viewportOrientationMayChange ? 1 : 0)) * 31) + this.viewportWidth) * 31) + this.viewportHeight) * 31) + this.maxAudioChannelCount) * 31) + this.maxAudioBitrate) * 31) + (this.exceedAudioConstraintsIfNecessary ? 1 : 0)) * 31) + (this.allowAudioMixedMimeTypeAdaptiveness ? 1 : 0)) * 31) + (this.allowAudioMixedSampleRateAdaptiveness ? 1 : 0)) * 31) + (this.forceLowestBitrate ? 1 : 0)) * 31) + (this.forceHighestSupportedBitrate ? 1 : 0)) * 31) + (this.exceedRendererCapabilitiesIfNecessary ? 1 : 0)) * 31) + this.tunnelingAudioSessionId;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.maxVideoWidth);
            dest.writeInt(this.maxVideoHeight);
            dest.writeInt(this.maxVideoFrameRate);
            dest.writeInt(this.maxVideoBitrate);
            Util.writeBoolean(dest, this.exceedVideoConstraintsIfNecessary);
            Util.writeBoolean(dest, this.allowVideoMixedMimeTypeAdaptiveness);
            Util.writeBoolean(dest, this.allowVideoNonSeamlessAdaptiveness);
            dest.writeInt(this.viewportWidth);
            dest.writeInt(this.viewportHeight);
            Util.writeBoolean(dest, this.viewportOrientationMayChange);
            dest.writeInt(this.maxAudioChannelCount);
            dest.writeInt(this.maxAudioBitrate);
            Util.writeBoolean(dest, this.exceedAudioConstraintsIfNecessary);
            Util.writeBoolean(dest, this.allowAudioMixedMimeTypeAdaptiveness);
            Util.writeBoolean(dest, this.allowAudioMixedSampleRateAdaptiveness);
            Util.writeBoolean(dest, this.forceLowestBitrate);
            Util.writeBoolean(dest, this.forceHighestSupportedBitrate);
            Util.writeBoolean(dest, this.exceedRendererCapabilitiesIfNecessary);
            dest.writeInt(this.tunnelingAudioSessionId);
            writeSelectionOverridesToParcel(dest, this.selectionOverrides);
            dest.writeSparseBooleanArray(this.rendererDisabledFlags);
        }

        private static SparseArray<Map<TrackGroupArray, SelectionOverride>> readSelectionOverrides(Parcel in) {
            int renderersWithOverridesCount = in.readInt();
            SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides2 = new SparseArray<>(renderersWithOverridesCount);
            for (int i = 0; i < renderersWithOverridesCount; i++) {
                int rendererIndex = in.readInt();
                int overrideCount = in.readInt();
                Map<TrackGroupArray, SelectionOverride> overrides = new HashMap<>(overrideCount);
                for (int j = 0; j < overrideCount; j++) {
                    overrides.put((TrackGroupArray) in.readParcelable(TrackGroupArray.class.getClassLoader()), (SelectionOverride) in.readParcelable(SelectionOverride.class.getClassLoader()));
                }
                selectionOverrides2.put(rendererIndex, overrides);
            }
            return selectionOverrides2;
        }

        private static void writeSelectionOverridesToParcel(Parcel dest, SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides2) {
            int renderersWithOverridesCount = selectionOverrides2.size();
            dest.writeInt(renderersWithOverridesCount);
            for (int i = 0; i < renderersWithOverridesCount; i++) {
                int rendererIndex = selectionOverrides2.keyAt(i);
                Map<TrackGroupArray, SelectionOverride> overrides = selectionOverrides2.valueAt(i);
                int overrideCount = overrides.size();
                dest.writeInt(rendererIndex);
                dest.writeInt(overrideCount);
                for (Map.Entry<TrackGroupArray, SelectionOverride> override : overrides.entrySet()) {
                    dest.writeParcelable((Parcelable) override.getKey(), 0);
                    dest.writeParcelable((Parcelable) override.getValue(), 0);
                }
            }
        }

        private static boolean areRendererDisabledFlagsEqual(SparseBooleanArray first, SparseBooleanArray second) {
            int firstSize = first.size();
            if (second.size() != firstSize) {
                return false;
            }
            for (int indexInFirst = 0; indexInFirst < firstSize; indexInFirst++) {
                if (second.indexOfKey(first.keyAt(indexInFirst)) < 0) {
                    return false;
                }
            }
            return true;
        }

        private static boolean areSelectionOverridesEqual(SparseArray<Map<TrackGroupArray, SelectionOverride>> first, SparseArray<Map<TrackGroupArray, SelectionOverride>> second) {
            int firstSize = first.size();
            if (second.size() != firstSize) {
                return false;
            }
            for (int indexInFirst = 0; indexInFirst < firstSize; indexInFirst++) {
                int indexInSecond = second.indexOfKey(first.keyAt(indexInFirst));
                if (indexInSecond < 0 || !areSelectionOverridesEqual(first.valueAt(indexInFirst), second.valueAt(indexInSecond))) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARNING: Removed duplicated region for block: B:6:0x001a  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static boolean areSelectionOverridesEqual(java.util.Map<com.google.android.exoplayer2.source.TrackGroupArray, com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride> r7, java.util.Map<com.google.android.exoplayer2.source.TrackGroupArray, com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride> r8) {
            /*
                int r0 = r7.size()
                int r1 = r8.size()
                r2 = 0
                if (r1 == r0) goto L_0x000c
                return r2
            L_0x000c:
                java.util.Set r1 = r7.entrySet()
                java.util.Iterator r1 = r1.iterator()
            L_0x0014:
                boolean r3 = r1.hasNext()
                if (r3 == 0) goto L_0x003d
                java.lang.Object r3 = r1.next()
                java.util.Map$Entry r3 = (java.util.Map.Entry) r3
                java.lang.Object r4 = r3.getKey()
                com.google.android.exoplayer2.source.TrackGroupArray r4 = (com.google.android.exoplayer2.source.TrackGroupArray) r4
                boolean r5 = r8.containsKey(r4)
                if (r5 == 0) goto L_0x003c
                java.lang.Object r5 = r3.getValue()
                java.lang.Object r6 = r8.get(r4)
                boolean r5 = com.google.android.exoplayer2.util.Util.areEqual(r5, r6)
                if (r5 != 0) goto L_0x003b
                goto L_0x003c
            L_0x003b:
                goto L_0x0014
            L_0x003c:
                return r2
            L_0x003d:
                r1 = 1
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters.areSelectionOverridesEqual(java.util.Map, java.util.Map):boolean");
        }
    }

    public static final class SelectionOverride implements Parcelable {
        public static final Parcelable.Creator<SelectionOverride> CREATOR = new Parcelable.Creator<SelectionOverride>() {
            public SelectionOverride createFromParcel(Parcel in) {
                return new SelectionOverride(in);
            }

            public SelectionOverride[] newArray(int size) {
                return new SelectionOverride[size];
            }
        };
        public final int data;
        public final int groupIndex;
        public final int length;
        public final int reason;
        public final int[] tracks;

        public SelectionOverride(int groupIndex2, int... tracks2) {
            this(groupIndex2, tracks2, 2, 0);
        }

        public SelectionOverride(int groupIndex2, int[] tracks2, int reason2, int data2) {
            this.groupIndex = groupIndex2;
            this.tracks = Arrays.copyOf(tracks2, tracks2.length);
            this.length = tracks2.length;
            this.reason = reason2;
            this.data = data2;
            Arrays.sort(this.tracks);
        }

        SelectionOverride(Parcel in) {
            this.groupIndex = in.readInt();
            this.length = in.readByte();
            this.tracks = new int[this.length];
            in.readIntArray(this.tracks);
            this.reason = in.readInt();
            this.data = in.readInt();
        }

        public boolean containsTrack(int track) {
            for (int overrideTrack : this.tracks) {
                if (overrideTrack == track) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (((((this.groupIndex * 31) + Arrays.hashCode(this.tracks)) * 31) + this.reason) * 31) + this.data;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SelectionOverride other = (SelectionOverride) obj;
            if (this.groupIndex == other.groupIndex && Arrays.equals(this.tracks, other.tracks) && this.reason == other.reason && this.data == other.data) {
                return true;
            }
            return false;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.groupIndex);
            dest.writeInt(this.tracks.length);
            dest.writeIntArray(this.tracks);
            dest.writeInt(this.reason);
            dest.writeInt(this.data);
        }
    }

    public DefaultTrackSelector() {
        this(new AdaptiveTrackSelection.Factory());
    }

    @Deprecated
    public DefaultTrackSelector(BandwidthMeter bandwidthMeter) {
        this(new AdaptiveTrackSelection.Factory(bandwidthMeter));
    }

    public DefaultTrackSelector(TrackSelection.Factory trackSelectionFactory2) {
        this.trackSelectionFactory = trackSelectionFactory2;
        this.parametersReference = new AtomicReference<>(Parameters.DEFAULT);
    }

    public void setParameters(Parameters parameters) {
        Assertions.checkNotNull(parameters);
        if (!this.parametersReference.getAndSet(parameters).equals(parameters)) {
            invalidate();
        }
    }

    public void setParameters(ParametersBuilder parametersBuilder) {
        setParameters(parametersBuilder.build());
    }

    public Parameters getParameters() {
        return this.parametersReference.get();
    }

    public ParametersBuilder buildUponParameters() {
        return getParameters().buildUpon();
    }

    @Deprecated
    public final void setRendererDisabled(int rendererIndex, boolean disabled) {
        setParameters(buildUponParameters().setRendererDisabled(rendererIndex, disabled));
    }

    @Deprecated
    public final boolean getRendererDisabled(int rendererIndex) {
        return getParameters().getRendererDisabled(rendererIndex);
    }

    @Deprecated
    public final void setSelectionOverride(int rendererIndex, TrackGroupArray groups, SelectionOverride override) {
        setParameters(buildUponParameters().setSelectionOverride(rendererIndex, groups, override));
    }

    @Deprecated
    public final boolean hasSelectionOverride(int rendererIndex, TrackGroupArray groups) {
        return getParameters().hasSelectionOverride(rendererIndex, groups);
    }

    @Nullable
    @Deprecated
    public final SelectionOverride getSelectionOverride(int rendererIndex, TrackGroupArray groups) {
        return getParameters().getSelectionOverride(rendererIndex, groups);
    }

    @Deprecated
    public final void clearSelectionOverride(int rendererIndex, TrackGroupArray groups) {
        setParameters(buildUponParameters().clearSelectionOverride(rendererIndex, groups));
    }

    @Deprecated
    public final void clearSelectionOverrides(int rendererIndex) {
        setParameters(buildUponParameters().clearSelectionOverrides(rendererIndex));
    }

    @Deprecated
    public final void clearSelectionOverrides() {
        setParameters(buildUponParameters().clearSelectionOverrides());
    }

    @Deprecated
    public void setTunnelingAudioSessionId(int tunnelingAudioSessionId) {
        setParameters(buildUponParameters().setTunnelingAudioSessionId(tunnelingAudioSessionId));
    }

    public void experimental_allowMultipleAdaptiveSelections() {
        this.allowMultipleAdaptiveSelections = true;
    }

    /* access modifiers changed from: protected */
    public final Pair<RendererConfiguration[], TrackSelection[]> selectTracks(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] rendererFormatSupports, int[] rendererMixedMimeTypeAdaptationSupports) throws ExoPlaybackException {
        Parameters params = this.parametersReference.get();
        int rendererCount = mappedTrackInfo.getRendererCount();
        TrackSelection.Definition[] definitions = selectAllTracks(mappedTrackInfo, rendererFormatSupports, rendererMixedMimeTypeAdaptationSupports, params);
        int i = 0;
        while (true) {
            TrackSelection.Definition definition = null;
            if (i >= rendererCount) {
                break;
            }
            if (params.getRendererDisabled(i)) {
                definitions[i] = null;
            } else {
                TrackGroupArray rendererTrackGroups = mappedTrackInfo.getTrackGroups(i);
                if (params.hasSelectionOverride(i, rendererTrackGroups)) {
                    SelectionOverride override = params.getSelectionOverride(i, rendererTrackGroups);
                    if (override != null) {
                        definition = new TrackSelection.Definition(rendererTrackGroups.get(override.groupIndex), override.tracks, override.reason, Integer.valueOf(override.data));
                    }
                    definitions[i] = definition;
                }
            }
            i++;
        }
        TrackSelection[] rendererTrackSelections = this.trackSelectionFactory.createTrackSelections(definitions, getBandwidthMeter());
        RendererConfiguration[] rendererConfigurations = new RendererConfiguration[rendererCount];
        for (int i2 = 0; i2 < rendererCount; i2++) {
            rendererConfigurations[i2] = !params.getRendererDisabled(i2) && (mappedTrackInfo.getRendererType(i2) == 6 || rendererTrackSelections[i2] != null) ? RendererConfiguration.DEFAULT : null;
        }
        maybeConfigureRenderersForTunneling(mappedTrackInfo, rendererFormatSupports, rendererConfigurations, rendererTrackSelections, params.tunnelingAudioSessionId);
        return Pair.create(rendererConfigurations, rendererTrackSelections);
    }

    /* access modifiers changed from: protected */
    public TrackSelection.Definition[] selectAllTracks(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] rendererFormatSupports, int[] rendererMixedMimeTypeAdaptationSupports, Parameters params) throws ExoPlaybackException {
        String selectedAudioLanguage;
        AudioTrackScore selectedAudioTrackScore;
        String selectedAudioLanguage2;
        int selectedAudioRendererIndex;
        boolean z;
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo2 = mappedTrackInfo;
        Parameters parameters = params;
        int rendererCount = mappedTrackInfo.getRendererCount();
        TrackSelection.Definition[] definitions = new TrackSelection.Definition[rendererCount];
        boolean seenVideoRendererWithMappedTracks = false;
        boolean selectedVideoTracks = false;
        for (int i = 0; i < rendererCount; i++) {
            if (2 == mappedTrackInfo2.getRendererType(i)) {
                if (!selectedVideoTracks) {
                    z = true;
                    definitions[i] = selectVideoTrack(mappedTrackInfo2.getTrackGroups(i), rendererFormatSupports[i], rendererMixedMimeTypeAdaptationSupports[i], params, true);
                    selectedVideoTracks = definitions[i] != null;
                } else {
                    z = true;
                }
                if (mappedTrackInfo2.getTrackGroups(i).length <= 0) {
                    z = false;
                }
                seenVideoRendererWithMappedTracks |= z;
            }
        }
        int i2 = 1;
        int selectedAudioRendererIndex2 = -1;
        AudioTrackScore selectedAudioTrackScore2 = null;
        int i3 = 0;
        String selectedAudioLanguage3 = null;
        while (i3 < rendererCount) {
            if (i2 == mappedTrackInfo2.getRendererType(i3)) {
                selectedAudioRendererIndex = selectedAudioRendererIndex2;
                selectedAudioLanguage2 = selectedAudioLanguage3;
                selectedAudioTrackScore = selectedAudioTrackScore2;
                Pair<TrackSelection.Definition, AudioTrackScore> audioSelection = selectAudioTrack(mappedTrackInfo2.getTrackGroups(i3), rendererFormatSupports[i3], rendererMixedMimeTypeAdaptationSupports[i3], params, this.allowMultipleAdaptiveSelections || !seenVideoRendererWithMappedTracks);
                if (audioSelection != null) {
                    if (selectedAudioTrackScore == null || ((AudioTrackScore) audioSelection.second).compareTo(selectedAudioTrackScore) > 0) {
                        if (selectedAudioRendererIndex != -1) {
                            definitions[selectedAudioRendererIndex] = null;
                        }
                        TrackSelection.Definition definition = (TrackSelection.Definition) audioSelection.first;
                        definitions[i3] = definition;
                        selectedAudioLanguage3 = definition.group.getFormat(definition.tracks[0]).language;
                        selectedAudioRendererIndex2 = i3;
                        selectedAudioTrackScore = (AudioTrackScore) audioSelection.second;
                        i3++;
                        selectedAudioTrackScore2 = selectedAudioTrackScore;
                        i2 = 1;
                    }
                }
            } else {
                selectedAudioRendererIndex = selectedAudioRendererIndex2;
                selectedAudioLanguage2 = selectedAudioLanguage3;
                selectedAudioTrackScore = selectedAudioTrackScore2;
            }
            selectedAudioRendererIndex2 = selectedAudioRendererIndex;
            selectedAudioLanguage3 = selectedAudioLanguage2;
            i3++;
            selectedAudioTrackScore2 = selectedAudioTrackScore;
            i2 = 1;
        }
        String selectedAudioLanguage4 = selectedAudioLanguage3;
        int selectedTextTrackScore = Integer.MIN_VALUE;
        int selectedTextRendererIndex = -1;
        int i4 = 0;
        while (i4 < rendererCount) {
            int trackType = mappedTrackInfo2.getRendererType(i4);
            if (trackType == 1 || trackType == 2) {
                selectedAudioLanguage = selectedAudioLanguage4;
            } else if (trackType != 3) {
                definitions[i4] = selectOtherTrack(trackType, mappedTrackInfo2.getTrackGroups(i4), rendererFormatSupports[i4], parameters);
                selectedAudioLanguage = selectedAudioLanguage4;
            } else {
                selectedAudioLanguage = selectedAudioLanguage4;
                Pair<TrackSelection.Definition, Integer> textSelection = selectTextTrack(mappedTrackInfo2.getTrackGroups(i4), rendererFormatSupports[i4], parameters, selectedAudioLanguage);
                if (textSelection != null && ((Integer) textSelection.second).intValue() > selectedTextTrackScore) {
                    if (selectedTextRendererIndex != -1) {
                        definitions[selectedTextRendererIndex] = null;
                    }
                    definitions[i4] = (TrackSelection.Definition) textSelection.first;
                    selectedTextTrackScore = ((Integer) textSelection.second).intValue();
                    selectedTextRendererIndex = i4;
                }
            }
            i4++;
            selectedAudioLanguage4 = selectedAudioLanguage;
        }
        return definitions;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public TrackSelection.Definition selectVideoTrack(TrackGroupArray groups, int[][] formatSupports, int mixedMimeTypeAdaptationSupports, Parameters params, boolean enableAdaptiveTrackSelection) throws ExoPlaybackException {
        TrackSelection.Definition definition = null;
        if (!params.forceHighestSupportedBitrate && !params.forceLowestBitrate && enableAdaptiveTrackSelection) {
            definition = selectAdaptiveVideoTrack(groups, formatSupports, mixedMimeTypeAdaptationSupports, params);
        }
        if (definition == null) {
            return selectFixedVideoTrack(groups, formatSupports, params);
        }
        return definition;
    }

    @Nullable
    private static TrackSelection.Definition selectAdaptiveVideoTrack(TrackGroupArray groups, int[][] formatSupport, int mixedMimeTypeAdaptationSupports, Parameters params) {
        int requiredAdaptiveSupport;
        TrackGroupArray trackGroupArray = groups;
        Parameters parameters = params;
        if (parameters.allowVideoNonSeamlessAdaptiveness) {
            requiredAdaptiveSupport = 24;
        } else {
            requiredAdaptiveSupport = 16;
        }
        boolean allowMixedMimeTypes = parameters.allowVideoMixedMimeTypeAdaptiveness && (mixedMimeTypeAdaptationSupports & requiredAdaptiveSupport) != 0;
        for (int i = 0; i < trackGroupArray.length; i++) {
            TrackGroup group = trackGroupArray.get(i);
            int[] adaptiveTracks = getAdaptiveVideoTracksForGroup(group, formatSupport[i], allowMixedMimeTypes, requiredAdaptiveSupport, parameters.maxVideoWidth, parameters.maxVideoHeight, parameters.maxVideoFrameRate, parameters.maxVideoBitrate, parameters.viewportWidth, parameters.viewportHeight, parameters.viewportOrientationMayChange);
            if (adaptiveTracks.length > 0) {
                return new TrackSelection.Definition(group, adaptiveTracks);
            }
        }
        return null;
    }

    private static int[] getAdaptiveVideoTracksForGroup(TrackGroup group, int[] formatSupport, boolean allowMixedMimeTypes, int requiredAdaptiveSupport, int maxVideoWidth, int maxVideoHeight, int maxVideoFrameRate, int maxVideoBitrate, int viewportWidth, int viewportHeight, boolean viewportOrientationMayChange) {
        String selectedMimeType;
        int i;
        int selectedMimeTypeTrackCount;
        TrackGroup trackGroup = group;
        if (trackGroup.length < 2) {
            return NO_TRACKS;
        }
        List<Integer> selectedTrackIndices = getViewportFilteredTrackIndices(trackGroup, viewportWidth, viewportHeight, viewportOrientationMayChange);
        if (selectedTrackIndices.size() < 2) {
            return NO_TRACKS;
        }
        if (!allowMixedMimeTypes) {
            HashSet hashSet = new HashSet();
            selectedMimeType = null;
            int selectedMimeTypeTrackCount2 = 0;
            int i2 = 0;
            while (i2 < selectedTrackIndices.size()) {
                String sampleMimeType = trackGroup.getFormat(selectedTrackIndices.get(i2).intValue()).sampleMimeType;
                if (hashSet.add(sampleMimeType)) {
                    String sampleMimeType2 = sampleMimeType;
                    selectedMimeTypeTrackCount = selectedMimeTypeTrackCount2;
                    i = i2;
                    int countForMimeType = getAdaptiveVideoTrackCountForMimeType(group, formatSupport, requiredAdaptiveSupport, sampleMimeType, maxVideoWidth, maxVideoHeight, maxVideoFrameRate, maxVideoBitrate, selectedTrackIndices);
                    if (countForMimeType > selectedMimeTypeTrackCount) {
                        selectedMimeType = sampleMimeType2;
                        selectedMimeTypeTrackCount2 = countForMimeType;
                        i2 = i + 1;
                    }
                } else {
                    selectedMimeTypeTrackCount = selectedMimeTypeTrackCount2;
                    i = i2;
                }
                selectedMimeTypeTrackCount2 = selectedMimeTypeTrackCount;
                i2 = i + 1;
            }
        } else {
            selectedMimeType = null;
        }
        filterAdaptiveVideoTrackCountForMimeType(group, formatSupport, requiredAdaptiveSupport, selectedMimeType, maxVideoWidth, maxVideoHeight, maxVideoFrameRate, maxVideoBitrate, selectedTrackIndices);
        return selectedTrackIndices.size() < 2 ? NO_TRACKS : Util.toArray(selectedTrackIndices);
    }

    private static int getAdaptiveVideoTrackCountForMimeType(TrackGroup group, int[] formatSupport, int requiredAdaptiveSupport, @Nullable String mimeType, int maxVideoWidth, int maxVideoHeight, int maxVideoFrameRate, int maxVideoBitrate, List<Integer> selectedTrackIndices) {
        int adaptiveTrackCount = 0;
        for (int i = 0; i < selectedTrackIndices.size(); i++) {
            int trackIndex = selectedTrackIndices.get(i).intValue();
            if (isSupportedAdaptiveVideoTrack(group.getFormat(trackIndex), mimeType, formatSupport[trackIndex], requiredAdaptiveSupport, maxVideoWidth, maxVideoHeight, maxVideoFrameRate, maxVideoBitrate)) {
                adaptiveTrackCount++;
            }
        }
        return adaptiveTrackCount;
    }

    private static void filterAdaptiveVideoTrackCountForMimeType(TrackGroup group, int[] formatSupport, int requiredAdaptiveSupport, @Nullable String mimeType, int maxVideoWidth, int maxVideoHeight, int maxVideoFrameRate, int maxVideoBitrate, List<Integer> selectedTrackIndices) {
        List<Integer> list = selectedTrackIndices;
        for (int i = selectedTrackIndices.size() - 1; i >= 0; i--) {
            int trackIndex = list.get(i).intValue();
            if (!isSupportedAdaptiveVideoTrack(group.getFormat(trackIndex), mimeType, formatSupport[trackIndex], requiredAdaptiveSupport, maxVideoWidth, maxVideoHeight, maxVideoFrameRate, maxVideoBitrate)) {
                list.remove(i);
            }
        }
    }

    private static boolean isSupportedAdaptiveVideoTrack(Format format, @Nullable String mimeType, int formatSupport, int requiredAdaptiveSupport, int maxVideoWidth, int maxVideoHeight, int maxVideoFrameRate, int maxVideoBitrate) {
        if (!isSupported(formatSupport, false) || (formatSupport & requiredAdaptiveSupport) == 0) {
            return false;
        }
        if (mimeType != null && !Util.areEqual(format.sampleMimeType, mimeType)) {
            return false;
        }
        if (format.width != -1 && format.width > maxVideoWidth) {
            return false;
        }
        if (format.height != -1 && format.height > maxVideoHeight) {
            return false;
        }
        if (format.frameRate != -1.0f && format.frameRate > ((float) maxVideoFrameRate)) {
            return false;
        }
        if (format.bitrate == -1 || format.bitrate <= maxVideoBitrate) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Multiple debug info for r2v7 int: [D('selectTrack' boolean), D('formatPixelCount' int)] */
    @Nullable
    private static TrackSelection.Definition selectFixedVideoTrack(TrackGroupArray groups, int[][] formatSupports, Parameters params) {
        TrackGroup selectedGroup;
        boolean selectTrack;
        int bitrateComparison;
        TrackGroupArray trackGroupArray = groups;
        Parameters parameters = params;
        TrackGroup selectedGroup2 = null;
        int selectedTrackIndex = 0;
        int selectedTrackScore = 0;
        int selectedBitrate = -1;
        int selectedPixelCount = -1;
        int groupIndex = 0;
        while (groupIndex < trackGroupArray.length) {
            TrackGroup trackGroup = trackGroupArray.get(groupIndex);
            List<Integer> selectedTrackIndices = getViewportFilteredTrackIndices(trackGroup, parameters.viewportWidth, parameters.viewportHeight, parameters.viewportOrientationMayChange);
            int[] trackFormatSupport = formatSupports[groupIndex];
            for (int trackIndex = 0; trackIndex < trackGroup.length; trackIndex++) {
                if (isSupported(trackFormatSupport[trackIndex], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    Format format = trackGroup.getFormat(trackIndex);
                    boolean isWithinConstraints = selectedTrackIndices.contains(Integer.valueOf(trackIndex)) && (format.width == -1 || format.width <= parameters.maxVideoWidth) && ((format.height == -1 || format.height <= parameters.maxVideoHeight) && ((format.frameRate == -1.0f || format.frameRate <= ((float) parameters.maxVideoFrameRate)) && (format.bitrate == -1 || format.bitrate <= parameters.maxVideoBitrate)));
                    if (isWithinConstraints || parameters.exceedVideoConstraintsIfNecessary) {
                        int trackScore = isWithinConstraints ? 2 : 1;
                        boolean isWithinCapabilities = isSupported(trackFormatSupport[trackIndex], false);
                        if (isWithinCapabilities) {
                            trackScore += 1000;
                        }
                        boolean selectTrack2 = trackScore > selectedTrackScore;
                        if (trackScore == selectedTrackScore) {
                            int bitrateComparison2 = compareFormatValues(format.bitrate, selectedBitrate);
                            selectedGroup = selectedGroup2;
                            if (!parameters.forceLowestBitrate || bitrateComparison2 == 0) {
                                int formatPixelCount = format.getPixelCount();
                                if (formatPixelCount != selectedPixelCount) {
                                    bitrateComparison = compareFormatValues(formatPixelCount, selectedPixelCount);
                                } else {
                                    bitrateComparison = compareFormatValues(format.bitrate, selectedBitrate);
                                }
                                selectTrack = !isWithinCapabilities || !isWithinConstraints ? bitrateComparison < 0 : bitrateComparison > 0;
                            } else {
                                selectTrack = bitrateComparison2 < 0;
                            }
                        } else {
                            selectTrack = selectTrack2;
                            selectedGroup = selectedGroup2;
                        }
                        if (selectTrack) {
                            selectedGroup2 = trackGroup;
                            selectedTrackIndex = trackIndex;
                            selectedTrackScore = trackScore;
                            selectedBitrate = format.bitrate;
                            selectedPixelCount = format.getPixelCount();
                        }
                    } else {
                        selectedGroup = selectedGroup2;
                    }
                } else {
                    selectedGroup = selectedGroup2;
                }
                selectedGroup2 = selectedGroup;
            }
            groupIndex++;
            trackGroupArray = groups;
        }
        if (selectedGroup2 == null) {
            return null;
        }
        return new TrackSelection.Definition(selectedGroup2, selectedTrackIndex);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Pair<TrackSelection.Definition, AudioTrackScore> selectAudioTrack(TrackGroupArray groups, int[][] formatSupports, int mixedMimeTypeAdaptationSupports, Parameters params, boolean enableAdaptiveTrackSelection) throws ExoPlaybackException {
        int selectedTrackIndex = -1;
        int selectedGroupIndex = -1;
        AudioTrackScore selectedTrackScore = null;
        for (int groupIndex = 0; groupIndex < groups.length; groupIndex++) {
            TrackGroup trackGroup = groups.get(groupIndex);
            int[] trackFormatSupport = formatSupports[groupIndex];
            for (int trackIndex = 0; trackIndex < trackGroup.length; trackIndex++) {
                if (isSupported(trackFormatSupport[trackIndex], params.exceedRendererCapabilitiesIfNecessary)) {
                    AudioTrackScore trackScore = new AudioTrackScore(trackGroup.getFormat(trackIndex), params, trackFormatSupport[trackIndex]);
                    if ((trackScore.isWithinConstraints || params.exceedAudioConstraintsIfNecessary) && (selectedTrackScore == null || trackScore.compareTo(selectedTrackScore) > 0)) {
                        selectedGroupIndex = groupIndex;
                        selectedTrackIndex = trackIndex;
                        selectedTrackScore = trackScore;
                    }
                }
            }
        }
        if (selectedGroupIndex == -1) {
            return null;
        }
        TrackGroup selectedGroup = groups.get(selectedGroupIndex);
        TrackSelection.Definition definition = null;
        if (!params.forceHighestSupportedBitrate && !params.forceLowestBitrate && enableAdaptiveTrackSelection) {
            int[] adaptiveTracks = getAdaptiveAudioTracks(selectedGroup, formatSupports[selectedGroupIndex], params.allowAudioMixedMimeTypeAdaptiveness, params.allowAudioMixedSampleRateAdaptiveness);
            if (adaptiveTracks.length > 0) {
                definition = new TrackSelection.Definition(selectedGroup, adaptiveTracks);
            }
        }
        if (definition == null) {
            definition = new TrackSelection.Definition(selectedGroup, selectedTrackIndex);
        }
        return Pair.create(definition, (AudioTrackScore) Assertions.checkNotNull(selectedTrackScore));
    }

    private static int[] getAdaptiveAudioTracks(TrackGroup group, int[] formatSupport, boolean allowMixedMimeTypeAdaptiveness, boolean allowMixedSampleRateAdaptiveness) {
        int configurationCount;
        int selectedConfigurationTrackCount = 0;
        AudioConfigurationTuple selectedConfiguration = null;
        HashSet<AudioConfigurationTuple> seenConfigurationTuples = new HashSet<>();
        for (int i = 0; i < group.length; i++) {
            Format format = group.getFormat(i);
            AudioConfigurationTuple configuration = new AudioConfigurationTuple(format.channelCount, format.sampleRate, format.sampleMimeType);
            if (seenConfigurationTuples.add(configuration) && (configurationCount = getAdaptiveAudioTrackCount(group, formatSupport, configuration, allowMixedMimeTypeAdaptiveness, allowMixedSampleRateAdaptiveness)) > selectedConfigurationTrackCount) {
                selectedConfiguration = configuration;
                selectedConfigurationTrackCount = configurationCount;
            }
        }
        if (selectedConfigurationTrackCount <= 1) {
            return NO_TRACKS;
        }
        int[] adaptiveIndices = new int[selectedConfigurationTrackCount];
        int index = 0;
        for (int i2 = 0; i2 < group.length; i2++) {
            if (isSupportedAdaptiveAudioTrack(group.getFormat(i2), formatSupport[i2], (AudioConfigurationTuple) Assertions.checkNotNull(selectedConfiguration), allowMixedMimeTypeAdaptiveness, allowMixedSampleRateAdaptiveness)) {
                adaptiveIndices[index] = i2;
                index++;
            }
        }
        return adaptiveIndices;
    }

    private static int getAdaptiveAudioTrackCount(TrackGroup group, int[] formatSupport, AudioConfigurationTuple configuration, boolean allowMixedMimeTypeAdaptiveness, boolean allowMixedSampleRateAdaptiveness) {
        int count = 0;
        for (int i = 0; i < group.length; i++) {
            if (isSupportedAdaptiveAudioTrack(group.getFormat(i), formatSupport[i], configuration, allowMixedMimeTypeAdaptiveness, allowMixedSampleRateAdaptiveness)) {
                count++;
            }
        }
        return count;
    }

    private static boolean isSupportedAdaptiveAudioTrack(Format format, int formatSupport, AudioConfigurationTuple configuration, boolean allowMixedMimeTypeAdaptiveness, boolean allowMixedSampleRateAdaptiveness) {
        if (!isSupported(formatSupport, false) || format.channelCount == -1 || format.channelCount != configuration.channelCount) {
            return false;
        }
        if (allowMixedMimeTypeAdaptiveness || (format.sampleMimeType != null && TextUtils.equals(format.sampleMimeType, configuration.mimeType))) {
            return allowMixedSampleRateAdaptiveness || (format.sampleRate != -1 && format.sampleRate == configuration.sampleRate);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x009b A[SYNTHETIC] */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.util.Pair<com.google.android.exoplayer2.trackselection.TrackSelection.Definition, java.lang.Integer> selectTextTrack(com.google.android.exoplayer2.source.TrackGroupArray r20, int[][] r21, com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters r22, @android.support.annotation.Nullable java.lang.String r23) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r19 = this;
            r0 = r20
            r1 = r22
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
        L_0x0008:
            int r6 = r0.length
            r7 = 0
            if (r5 >= r6) goto L_0x00a9
            com.google.android.exoplayer2.source.TrackGroup r6 = r0.get(r5)
            r9 = r21[r5]
            r10 = 0
        L_0x0014:
            int r11 = r6.length
            if (r10 >= r11) goto L_0x00a1
            r11 = r9[r10]
            boolean r12 = r1.exceedRendererCapabilitiesIfNecessary
            boolean r11 = isSupported(r11, r12)
            if (r11 == 0) goto L_0x0099
            com.google.android.exoplayer2.Format r11 = r6.getFormat(r10)
            int r12 = r11.selectionFlags
            int r13 = r1.disabledTextTrackSelectionFlags
            r13 = r13 ^ -1
            r12 = r12 & r13
            r13 = r12 & 1
            if (r13 == 0) goto L_0x0033
            r13 = 1
            goto L_0x0034
        L_0x0033:
            r13 = 0
        L_0x0034:
            r14 = r12 & 2
            if (r14 == 0) goto L_0x003a
            r14 = 1
            goto L_0x003b
        L_0x003a:
            r14 = 0
        L_0x003b:
            java.lang.String r15 = r1.preferredTextLanguage
            int r15 = getFormatLanguageScore(r11, r15)
            boolean r16 = formatHasNoLanguage(r11)
            if (r15 > 0) goto L_0x0072
            boolean r8 = r1.selectUndeterminedTextLanguage
            if (r8 == 0) goto L_0x0050
            if (r16 == 0) goto L_0x0050
            r8 = r23
            goto L_0x0074
        L_0x0050:
            if (r13 == 0) goto L_0x0058
            r8 = 2
            r18 = r8
            r8 = r23
            goto L_0x0084
        L_0x0058:
            if (r14 == 0) goto L_0x006f
            r8 = r23
            int r17 = getFormatLanguageScore(r11, r8)
            if (r17 > 0) goto L_0x006a
            if (r16 == 0) goto L_0x009b
            boolean r17 = stringDefinesNoLanguage(r23)
            if (r17 == 0) goto L_0x009b
        L_0x006a:
            r17 = 1
            r18 = r17
            goto L_0x0084
        L_0x006f:
            r8 = r23
            goto L_0x009b
        L_0x0072:
            r8 = r23
        L_0x0074:
            if (r13 == 0) goto L_0x0079
            r17 = 11
            goto L_0x0080
        L_0x0079:
            if (r14 != 0) goto L_0x007e
            r17 = 7
            goto L_0x0080
        L_0x007e:
            r17 = 3
        L_0x0080:
            int r17 = r17 + r15
            r18 = r17
        L_0x0084:
            r0 = r9[r10]
            boolean r0 = isSupported(r0, r7)
            if (r0 == 0) goto L_0x0091
            r0 = r18
            int r0 = r0 + 1000
            goto L_0x0093
        L_0x0091:
            r0 = r18
        L_0x0093:
            if (r0 <= r4) goto L_0x009b
            r2 = r6
            r3 = r10
            r4 = r0
            goto L_0x009b
        L_0x0099:
            r8 = r23
        L_0x009b:
            int r10 = r10 + 1
            r0 = r20
            goto L_0x0014
        L_0x00a1:
            r8 = r23
            int r5 = r5 + 1
            r0 = r20
            goto L_0x0008
        L_0x00a9:
            r8 = r23
            if (r2 != 0) goto L_0x00af
            r0 = 0
            goto L_0x00c1
        L_0x00af:
            com.google.android.exoplayer2.trackselection.TrackSelection$Definition r0 = new com.google.android.exoplayer2.trackselection.TrackSelection$Definition
            r5 = 1
            int[] r5 = new int[r5]
            r5[r7] = r3
            r0.<init>(r2, r5)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            android.util.Pair r0 = android.util.Pair.create(r0, r5)
        L_0x00c1:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.selectTextTrack(com.google.android.exoplayer2.source.TrackGroupArray, int[][], com.google.android.exoplayer2.trackselection.DefaultTrackSelector$Parameters, java.lang.String):android.util.Pair");
    }

    /* access modifiers changed from: protected */
    @Nullable
    public TrackSelection.Definition selectOtherTrack(int trackType, TrackGroupArray groups, int[][] formatSupport, Parameters params) throws ExoPlaybackException {
        TrackGroupArray trackGroupArray = groups;
        TrackGroup selectedGroup = null;
        int selectedTrackIndex = 0;
        int selectedTrackScore = 0;
        for (int groupIndex = 0; groupIndex < trackGroupArray.length; groupIndex++) {
            TrackGroup trackGroup = trackGroupArray.get(groupIndex);
            int[] trackFormatSupport = formatSupport[groupIndex];
            for (int trackIndex = 0; trackIndex < trackGroup.length; trackIndex++) {
                if (isSupported(trackFormatSupport[trackIndex], params.exceedRendererCapabilitiesIfNecessary)) {
                    int trackScore = (trackGroup.getFormat(trackIndex).selectionFlags & 1) != 0 ? 2 : 1;
                    if (isSupported(trackFormatSupport[trackIndex], false)) {
                        trackScore += 1000;
                    }
                    if (trackScore > selectedTrackScore) {
                        selectedGroup = trackGroup;
                        selectedTrackIndex = trackIndex;
                        selectedTrackScore = trackScore;
                    }
                }
            }
        }
        if (selectedGroup == null) {
            return null;
        }
        return new TrackSelection.Definition(selectedGroup, selectedTrackIndex);
    }

    private static void maybeConfigureRenderersForTunneling(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] renderererFormatSupports, RendererConfiguration[] rendererConfigurations, TrackSelection[] trackSelections, int tunnelingAudioSessionId) {
        boolean z;
        if (tunnelingAudioSessionId != 0) {
            int tunnelingAudioRendererIndex = -1;
            int tunnelingVideoRendererIndex = -1;
            boolean enableTunneling = true;
            int i = 0;
            while (true) {
                z = true;
                if (i >= mappedTrackInfo.getRendererCount()) {
                    break;
                }
                int rendererType = mappedTrackInfo.getRendererType(i);
                TrackSelection trackSelection = trackSelections[i];
                if ((rendererType == 1 || rendererType == 2) && trackSelection != null && rendererSupportsTunneling(renderererFormatSupports[i], mappedTrackInfo.getTrackGroups(i), trackSelection)) {
                    if (rendererType == 1) {
                        if (tunnelingAudioRendererIndex != -1) {
                            enableTunneling = false;
                            break;
                        }
                        tunnelingAudioRendererIndex = i;
                    } else if (tunnelingVideoRendererIndex != -1) {
                        enableTunneling = false;
                        break;
                    } else {
                        tunnelingVideoRendererIndex = i;
                    }
                }
                i++;
            }
            if (tunnelingAudioRendererIndex == -1 || tunnelingVideoRendererIndex == -1) {
                z = false;
            }
            if (enableTunneling && z) {
                RendererConfiguration tunnelingRendererConfiguration = new RendererConfiguration(tunnelingAudioSessionId);
                rendererConfigurations[tunnelingAudioRendererIndex] = tunnelingRendererConfiguration;
                rendererConfigurations[tunnelingVideoRendererIndex] = tunnelingRendererConfiguration;
            }
        }
    }

    private static boolean rendererSupportsTunneling(int[][] formatSupports, TrackGroupArray trackGroups, TrackSelection selection) {
        if (selection == null) {
            return false;
        }
        int trackGroupIndex = trackGroups.indexOf(selection.getTrackGroup());
        for (int i = 0; i < selection.length(); i++) {
            if ((formatSupports[trackGroupIndex][selection.getIndexInTrackGroup(i)] & 32) != 32) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static int compareFormatValues(int first, int second) {
        if (first == -1) {
            if (second == -1) {
                return 0;
            }
            return -1;
        } else if (second == -1) {
            return 1;
        } else {
            return first - second;
        }
    }

    protected static boolean isSupported(int formatSupport, boolean allowExceedsCapabilities) {
        int maskedSupport = formatSupport & 7;
        return maskedSupport == 4 || (allowExceedsCapabilities && maskedSupport == 3);
    }

    protected static boolean formatHasNoLanguage(Format format) {
        return stringDefinesNoLanguage(format.language);
    }

    protected static boolean stringDefinesNoLanguage(@Nullable String language) {
        return TextUtils.isEmpty(language) || TextUtils.equals(language, C0841C.LANGUAGE_UNDETERMINED);
    }

    protected static int getFormatLanguageScore(Format format, @Nullable String language) {
        if (format.language == null || language == null) {
            return 0;
        }
        if (TextUtils.equals(format.language, language)) {
            return 3;
        }
        if (format.language.startsWith(language) || language.startsWith(format.language)) {
            return 2;
        }
        if (format.language.length() < 3 || language.length() < 3 || !format.language.substring(0, 3).equals(language.substring(0, 3))) {
            return 0;
        }
        return 1;
    }

    private static List<Integer> getViewportFilteredTrackIndices(TrackGroup group, int viewportWidth, int viewportHeight, boolean orientationMayChange) {
        ArrayList<Integer> selectedTrackIndices = new ArrayList<>(group.length);
        for (int i = 0; i < group.length; i++) {
            selectedTrackIndices.add(Integer.valueOf(i));
        }
        if (viewportWidth == Integer.MAX_VALUE || viewportHeight == Integer.MAX_VALUE) {
            return selectedTrackIndices;
        }
        int maxVideoPixelsToRetain = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < group.length; i2++) {
            Format format = group.getFormat(i2);
            if (format.width > 0 && format.height > 0) {
                Point maxVideoSizeInViewport = getMaxVideoSizeInViewport(orientationMayChange, viewportWidth, viewportHeight, format.width, format.height);
                int videoPixels = format.width * format.height;
                if (format.width >= ((int) (((float) maxVideoSizeInViewport.x) * FRACTION_TO_CONSIDER_FULLSCREEN)) && format.height >= ((int) (((float) maxVideoSizeInViewport.y) * FRACTION_TO_CONSIDER_FULLSCREEN)) && videoPixels < maxVideoPixelsToRetain) {
                    maxVideoPixelsToRetain = videoPixels;
                }
            }
        }
        if (maxVideoPixelsToRetain != Integer.MAX_VALUE) {
            for (int i3 = selectedTrackIndices.size() - 1; i3 >= 0; i3--) {
                int pixelCount = group.getFormat(((Integer) selectedTrackIndices.get(i3)).intValue()).getPixelCount();
                if (pixelCount == -1 || pixelCount > maxVideoPixelsToRetain) {
                    selectedTrackIndices.remove(i3);
                }
            }
        }
        return selectedTrackIndices;
    }

    private static Point getMaxVideoSizeInViewport(boolean orientationMayChange, int viewportWidth, int viewportHeight, int videoWidth, int videoHeight) {
        if (orientationMayChange) {
            boolean z = true;
            boolean z2 = videoWidth > videoHeight;
            if (viewportWidth <= viewportHeight) {
                z = false;
            }
            if (z2 != z) {
                int tempViewportWidth = viewportWidth;
                viewportWidth = viewportHeight;
                viewportHeight = tempViewportWidth;
            }
        }
        if (videoWidth * viewportHeight >= videoHeight * viewportWidth) {
            return new Point(viewportWidth, Util.ceilDivide(viewportWidth * videoHeight, videoWidth));
        }
        return new Point(Util.ceilDivide(viewportHeight * videoWidth, videoHeight), viewportHeight);
    }

    protected static final class AudioTrackScore implements Comparable<AudioTrackScore> {
        private final int bitrate;
        private final int channelCount;
        private final boolean isDefaultSelectionFlag;
        public final boolean isWithinConstraints;
        private final boolean isWithinRendererCapabilities;
        private final int localeLanguageMatchIndex;
        private final int localeLanguageScore;
        private final Parameters parameters;
        private final int preferredLanguageScore;
        private final int sampleRate;

        public AudioTrackScore(Format format, Parameters parameters2, int formatSupport) {
            this.parameters = parameters2;
            boolean z = false;
            this.isWithinRendererCapabilities = DefaultTrackSelector.isSupported(formatSupport, false);
            this.preferredLanguageScore = DefaultTrackSelector.getFormatLanguageScore(format, parameters2.preferredAudioLanguage);
            this.isDefaultSelectionFlag = (format.selectionFlags & 1) != 0;
            this.channelCount = format.channelCount;
            this.sampleRate = format.sampleRate;
            this.bitrate = format.bitrate;
            if ((format.bitrate == -1 || format.bitrate <= parameters2.maxAudioBitrate) && (format.channelCount == -1 || format.channelCount <= parameters2.maxAudioChannelCount)) {
                z = true;
            }
            this.isWithinConstraints = z;
            String[] localeLanguages = Util.getSystemLanguageCodes();
            int bestMatchIndex = Integer.MAX_VALUE;
            int bestMatchScore = 0;
            int i = 0;
            while (true) {
                if (i >= localeLanguages.length) {
                    break;
                }
                int score = DefaultTrackSelector.getFormatLanguageScore(format, localeLanguages[i]);
                if (score > 0) {
                    bestMatchIndex = i;
                    bestMatchScore = score;
                    break;
                }
                i++;
            }
            this.localeLanguageMatchIndex = bestMatchIndex;
            this.localeLanguageScore = bestMatchScore;
        }

        public int compareTo(@NonNull AudioTrackScore other) {
            int bitrateComparison;
            boolean z = this.isWithinRendererCapabilities;
            int i = 1;
            if (z == other.isWithinRendererCapabilities) {
                int i2 = this.preferredLanguageScore;
                int i3 = other.preferredLanguageScore;
                if (i2 != i3) {
                    return DefaultTrackSelector.compareInts(i2, i3);
                }
                boolean z2 = this.isWithinConstraints;
                if (z2 != other.isWithinConstraints) {
                    if (z2) {
                        return 1;
                    }
                    return -1;
                } else if (!this.parameters.forceLowestBitrate || (bitrateComparison = DefaultTrackSelector.compareFormatValues(this.bitrate, other.bitrate)) == 0) {
                    int bitrateComparison2 = this.isDefaultSelectionFlag;
                    if (bitrateComparison2 == other.isDefaultSelectionFlag) {
                        int i4 = this.localeLanguageMatchIndex;
                        int i5 = other.localeLanguageMatchIndex;
                        if (i4 != i5) {
                            return -DefaultTrackSelector.compareInts(i4, i5);
                        }
                        int i6 = this.localeLanguageScore;
                        int i7 = other.localeLanguageScore;
                        if (i6 != i7) {
                            return DefaultTrackSelector.compareInts(i6, i7);
                        }
                        if (!this.isWithinConstraints || !this.isWithinRendererCapabilities) {
                            i = -1;
                        }
                        int resultSign = i;
                        int i8 = this.channelCount;
                        int i9 = other.channelCount;
                        if (i8 != i9) {
                            return DefaultTrackSelector.compareInts(i8, i9) * resultSign;
                        }
                        int i10 = this.sampleRate;
                        int i11 = other.sampleRate;
                        if (i10 != i11) {
                            return DefaultTrackSelector.compareInts(i10, i11) * resultSign;
                        }
                        return DefaultTrackSelector.compareInts(this.bitrate, other.bitrate) * resultSign;
                    } else if (bitrateComparison2 != 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else if (bitrateComparison > 0) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (z) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /* access modifiers changed from: private */
    public static int compareInts(int first, int second) {
        if (first > second) {
            return 1;
        }
        return second > first ? -1 : 0;
    }

    private static final class AudioConfigurationTuple {
        public final int channelCount;
        @Nullable
        public final String mimeType;
        public final int sampleRate;

        public AudioConfigurationTuple(int channelCount2, int sampleRate2, @Nullable String mimeType2) {
            this.channelCount = channelCount2;
            this.sampleRate = sampleRate2;
            this.mimeType = mimeType2;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            AudioConfigurationTuple other = (AudioConfigurationTuple) obj;
            if (this.channelCount == other.channelCount && this.sampleRate == other.sampleRate && TextUtils.equals(this.mimeType, other.mimeType)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int result = ((this.channelCount * 31) + this.sampleRate) * 31;
            String str = this.mimeType;
            return result + (str != null ? str.hashCode() : 0);
        }
    }
}
