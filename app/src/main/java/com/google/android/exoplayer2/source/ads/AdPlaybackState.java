package com.google.android.exoplayer2.source.ads;

import android.net.Uri;
import android.support.annotation.CheckResult;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.util.Assertions;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public final class AdPlaybackState {
    public static final int AD_STATE_AVAILABLE = 1;
    public static final int AD_STATE_ERROR = 4;
    public static final int AD_STATE_PLAYED = 3;
    public static final int AD_STATE_SKIPPED = 2;
    public static final int AD_STATE_UNAVAILABLE = 0;
    public static final AdPlaybackState NONE = new AdPlaybackState(new long[0]);
    public final int adGroupCount;
    public final long[] adGroupTimesUs;
    public final AdGroup[] adGroups;
    public final long adResumePositionUs;
    public final long contentDurationUs;

    public AdPlaybackState(long... adGroupTimesUs2) {
        int count = adGroupTimesUs2.length;
        this.adGroupCount = count;
        this.adGroupTimesUs = Arrays.copyOf(adGroupTimesUs2, count);
        this.adGroups = new AdGroup[count];
        for (int i = 0; i < count; i++) {
            this.adGroups[i] = new AdGroup();
        }
        this.adResumePositionUs = 0;
        this.contentDurationUs = C0841C.TIME_UNSET;
    }

    private AdPlaybackState(long[] adGroupTimesUs2, AdGroup[] adGroups2, long adResumePositionUs2, long contentDurationUs2) {
        this.adGroupCount = adGroups2.length;
        this.adGroupTimesUs = adGroupTimesUs2;
        this.adGroups = adGroups2;
        this.adResumePositionUs = adResumePositionUs2;
        this.contentDurationUs = contentDurationUs2;
    }

    public int getAdGroupIndexForPositionUs(long positionUs) {
        int index = this.adGroupTimesUs.length - 1;
        while (index >= 0 && isPositionBeforeAdGroup(positionUs, index)) {
            index--;
        }
        if (index < 0 || !this.adGroups[index].hasUnplayedAds()) {
            return -1;
        }
        return index;
    }

    public int getAdGroupIndexAfterPositionUs(long positionUs, long periodDurationUs) {
        if (positionUs == Long.MIN_VALUE || (periodDurationUs != C0841C.TIME_UNSET && positionUs >= periodDurationUs)) {
            return -1;
        }
        int index = 0;
        while (true) {
            long[] jArr = this.adGroupTimesUs;
            if (index < jArr.length && jArr[index] != Long.MIN_VALUE && (positionUs >= jArr[index] || !this.adGroups[index].hasUnplayedAds())) {
                index++;
            }
        }
        if (index < this.adGroupTimesUs.length) {
            return index;
        }
        return -1;
    }

    @CheckResult
    public AdPlaybackState withAdCount(int adGroupIndex, int adCount) {
        Assertions.checkArgument(adCount > 0);
        if (this.adGroups[adGroupIndex].count == adCount) {
            return this;
        }
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroups2 = (AdGroup[]) Arrays.copyOf(adGroupArr, adGroupArr.length);
        adGroups2[adGroupIndex] = this.adGroups[adGroupIndex].withAdCount(adCount);
        return new AdPlaybackState(this.adGroupTimesUs, adGroups2, this.adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withAdUri(int adGroupIndex, int adIndexInAdGroup, Uri uri) {
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroups2 = (AdGroup[]) Arrays.copyOf(adGroupArr, adGroupArr.length);
        adGroups2[adGroupIndex] = adGroups2[adGroupIndex].withAdUri(uri, adIndexInAdGroup);
        return new AdPlaybackState(this.adGroupTimesUs, adGroups2, this.adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withPlayedAd(int adGroupIndex, int adIndexInAdGroup) {
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroups2 = (AdGroup[]) Arrays.copyOf(adGroupArr, adGroupArr.length);
        adGroups2[adGroupIndex] = adGroups2[adGroupIndex].withAdState(3, adIndexInAdGroup);
        return new AdPlaybackState(this.adGroupTimesUs, adGroups2, this.adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withSkippedAd(int adGroupIndex, int adIndexInAdGroup) {
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroups2 = (AdGroup[]) Arrays.copyOf(adGroupArr, adGroupArr.length);
        adGroups2[adGroupIndex] = adGroups2[adGroupIndex].withAdState(2, adIndexInAdGroup);
        return new AdPlaybackState(this.adGroupTimesUs, adGroups2, this.adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withAdLoadError(int adGroupIndex, int adIndexInAdGroup) {
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroups2 = (AdGroup[]) Arrays.copyOf(adGroupArr, adGroupArr.length);
        adGroups2[adGroupIndex] = adGroups2[adGroupIndex].withAdState(4, adIndexInAdGroup);
        return new AdPlaybackState(this.adGroupTimesUs, adGroups2, this.adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withSkippedAdGroup(int adGroupIndex) {
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroups2 = (AdGroup[]) Arrays.copyOf(adGroupArr, adGroupArr.length);
        adGroups2[adGroupIndex] = adGroups2[adGroupIndex].withAllAdsSkipped();
        return new AdPlaybackState(this.adGroupTimesUs, adGroups2, this.adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withAdDurationsUs(long[][] adDurationUs) {
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroups2 = (AdGroup[]) Arrays.copyOf(adGroupArr, adGroupArr.length);
        for (int adGroupIndex = 0; adGroupIndex < this.adGroupCount; adGroupIndex++) {
            adGroups2[adGroupIndex] = adGroups2[adGroupIndex].withAdDurationsUs(adDurationUs[adGroupIndex]);
        }
        return new AdPlaybackState(this.adGroupTimesUs, adGroups2, this.adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withAdResumePositionUs(long adResumePositionUs2) {
        if (this.adResumePositionUs == adResumePositionUs2) {
            return this;
        }
        return new AdPlaybackState(this.adGroupTimesUs, this.adGroups, adResumePositionUs2, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withContentDurationUs(long contentDurationUs2) {
        if (this.contentDurationUs == contentDurationUs2) {
            return this;
        }
        return new AdPlaybackState(this.adGroupTimesUs, this.adGroups, this.adResumePositionUs, contentDurationUs2);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdPlaybackState that = (AdPlaybackState) o;
        if (this.adGroupCount == that.adGroupCount && this.adResumePositionUs == that.adResumePositionUs && this.contentDurationUs == that.contentDurationUs && Arrays.equals(this.adGroupTimesUs, that.adGroupTimesUs) && Arrays.equals(this.adGroups, that.adGroups)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((this.adGroupCount * 31) + ((int) this.adResumePositionUs)) * 31) + ((int) this.contentDurationUs)) * 31) + Arrays.hashCode(this.adGroupTimesUs)) * 31) + Arrays.hashCode(this.adGroups);
    }

    private boolean isPositionBeforeAdGroup(long positionUs, int adGroupIndex) {
        if (positionUs == Long.MIN_VALUE) {
            return false;
        }
        long adGroupPositionUs = this.adGroupTimesUs[adGroupIndex];
        if (adGroupPositionUs == Long.MIN_VALUE) {
            long j = this.contentDurationUs;
            if (j == C0841C.TIME_UNSET || positionUs < j) {
                return true;
            }
            return false;
        } else if (positionUs < adGroupPositionUs) {
            return true;
        } else {
            return false;
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface AdState {
    }

    public static final class AdGroup {
        public final int count;
        public final long[] durationsUs;
        public final int[] states;
        public final Uri[] uris;

        public AdGroup() {
            this(-1, new int[0], new Uri[0], new long[0]);
        }

        private AdGroup(int count2, int[] states2, Uri[] uris2, long[] durationsUs2) {
            Assertions.checkArgument(states2.length == uris2.length);
            this.count = count2;
            this.states = states2;
            this.uris = uris2;
            this.durationsUs = durationsUs2;
        }

        @CheckResult
        private static int[] copyStatesWithSpaceForAdCount(int[] states2, int count2) {
            int oldStateCount = states2.length;
            int newStateCount = Math.max(count2, oldStateCount);
            int[] states3 = Arrays.copyOf(states2, newStateCount);
            Arrays.fill(states3, oldStateCount, newStateCount, 0);
            return states3;
        }

        @CheckResult
        private static long[] copyDurationsUsWithSpaceForAdCount(long[] durationsUs2, int count2) {
            int oldDurationsUsCount = durationsUs2.length;
            int newDurationsUsCount = Math.max(count2, oldDurationsUsCount);
            long[] durationsUs3 = Arrays.copyOf(durationsUs2, newDurationsUsCount);
            Arrays.fill(durationsUs3, oldDurationsUsCount, newDurationsUsCount, (long) C0841C.TIME_UNSET);
            return durationsUs3;
        }

        public int getFirstAdIndexToPlay() {
            return getNextAdIndexToPlay(-1);
        }

        public int getNextAdIndexToPlay(int lastPlayedAdIndex) {
            int nextAdIndexToPlay = lastPlayedAdIndex + 1;
            while (true) {
                int[] iArr = this.states;
                if (nextAdIndexToPlay >= iArr.length || iArr[nextAdIndexToPlay] == 0 || iArr[nextAdIndexToPlay] == 1) {
                    return nextAdIndexToPlay;
                }
                nextAdIndexToPlay++;
            }
            return nextAdIndexToPlay;
        }

        public boolean hasUnplayedAds() {
            return this.count == -1 || getFirstAdIndexToPlay() < this.count;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            AdGroup adGroup = (AdGroup) o;
            if (this.count != adGroup.count || !Arrays.equals(this.uris, adGroup.uris) || !Arrays.equals(this.states, adGroup.states) || !Arrays.equals(this.durationsUs, adGroup.durationsUs)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((((this.count * 31) + Arrays.hashCode(this.uris)) * 31) + Arrays.hashCode(this.states)) * 31) + Arrays.hashCode(this.durationsUs);
        }

        @CheckResult
        public AdGroup withAdCount(int count2) {
            Assertions.checkArgument(this.count == -1 && this.states.length <= count2);
            return new AdGroup(count2, copyStatesWithSpaceForAdCount(this.states, count2), (Uri[]) Arrays.copyOf(this.uris, count2), copyDurationsUsWithSpaceForAdCount(this.durationsUs, count2));
        }

        @CheckResult
        public AdGroup withAdUri(Uri uri, int index) {
            int i = this.count;
            boolean z = false;
            Assertions.checkArgument(i == -1 || index < i);
            int[] states2 = copyStatesWithSpaceForAdCount(this.states, index + 1);
            if (states2[index] == 0) {
                z = true;
            }
            Assertions.checkArgument(z);
            long[] durationsUs2 = this.durationsUs;
            if (durationsUs2.length != states2.length) {
                durationsUs2 = copyDurationsUsWithSpaceForAdCount(durationsUs2, states2.length);
            }
            Uri[] uris2 = (Uri[]) Arrays.copyOf(this.uris, states2.length);
            uris2[index] = uri;
            states2[index] = 1;
            return new AdGroup(this.count, states2, uris2, durationsUs2);
        }

        @CheckResult
        public AdGroup withAdState(int state, int index) {
            int i = this.count;
            boolean z = false;
            Assertions.checkArgument(i == -1 || index < i);
            int[] states2 = copyStatesWithSpaceForAdCount(this.states, index + 1);
            if (states2[index] == 0 || states2[index] == 1 || states2[index] == state) {
                z = true;
            }
            Assertions.checkArgument(z);
            long[] durationsUs2 = this.durationsUs;
            if (durationsUs2.length != states2.length) {
                durationsUs2 = copyDurationsUsWithSpaceForAdCount(durationsUs2, states2.length);
            }
            Uri[] uris2 = this.uris;
            if (uris2.length != states2.length) {
                uris2 = (Uri[]) Arrays.copyOf(uris2, states2.length);
            }
            states2[index] = state;
            return new AdGroup(this.count, states2, uris2, durationsUs2);
        }

        @CheckResult
        public AdGroup withAdDurationsUs(long[] durationsUs2) {
            Assertions.checkArgument(this.count == -1 || durationsUs2.length <= this.uris.length);
            int length = durationsUs2.length;
            Uri[] uriArr = this.uris;
            if (length < uriArr.length) {
                durationsUs2 = copyDurationsUsWithSpaceForAdCount(durationsUs2, uriArr.length);
            }
            return new AdGroup(this.count, this.states, this.uris, durationsUs2);
        }

        @CheckResult
        public AdGroup withAllAdsSkipped() {
            if (this.count == -1) {
                return new AdGroup(0, new int[0], new Uri[0], new long[0]);
            }
            int[] iArr = this.states;
            int count2 = iArr.length;
            int[] states2 = Arrays.copyOf(iArr, count2);
            for (int i = 0; i < count2; i++) {
                if (states2[i] == 1 || states2[i] == 0) {
                    states2[i] = 2;
                }
            }
            return new AdGroup(count2, states2, this.uris, this.durationsUs);
        }
    }
}
