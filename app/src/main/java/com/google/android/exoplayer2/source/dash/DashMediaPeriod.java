package com.google.android.exoplayer2.source.dash;

import android.support.annotation.Nullable;
import android.util.Pair;
import android.util.SparseIntArray;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.EmptySampleStream;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.ChunkSampleStream;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.Descriptor;
import com.google.android.exoplayer2.source.dash.manifest.EventStream;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class DashMediaPeriod implements MediaPeriod, SequenceableLoader.Callback<ChunkSampleStream<DashChunkSource>>, ChunkSampleStream.ReleaseCallback<DashChunkSource> {
    private static final Pattern CEA608_SERVICE_DESCRIPTOR_REGEX = Pattern.compile("CC([1-4])=(.+)");
    /* renamed from: id */
    final int f94id;
    private final Allocator allocator;
    private final DashChunkSource.Factory chunkSourceFactory;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final long elapsedRealtimeOffsetMs;
    private final MediaSourceEventListener.EventDispatcher eventDispatcher;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final PlayerEmsgHandler playerEmsgHandler;
    private final IdentityHashMap<ChunkSampleStream<DashChunkSource>, PlayerEmsgHandler.PlayerTrackEmsgHandler> trackEmsgHandlerBySampleStream = new IdentityHashMap<>();
    private final TrackGroupInfo[] trackGroupInfos;
    private final TrackGroupArray trackGroups;
    @Nullable
    private final TransferListener transferListener;
    @Nullable
    private MediaPeriod.Callback callback;
    private SequenceableLoader compositeSequenceableLoader;
    private EventSampleStream[] eventSampleStreams = new EventSampleStream[0];
    private List<EventStream> eventStreams;
    private DashManifest manifest;
    private boolean notifiedReadingStarted;
    private int periodIndex;
    private ChunkSampleStream<DashChunkSource>[] sampleStreams = newSampleStreamArray(0);

    public DashMediaPeriod(int id, DashManifest manifest2, int periodIndex2, DashChunkSource.Factory chunkSourceFactory2, @Nullable TransferListener transferListener2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, MediaSourceEventListener.EventDispatcher eventDispatcher2, long elapsedRealtimeOffsetMs2, LoaderErrorThrower manifestLoaderErrorThrower2, Allocator allocator2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2, PlayerEmsgHandler.PlayerEmsgCallback playerEmsgCallback) {
        DashManifest dashManifest = manifest2;
        Allocator allocator3 = allocator2;
        CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory3 = compositeSequenceableLoaderFactory2;
        this.f94id = id;
        this.manifest = dashManifest;
        this.periodIndex = periodIndex2;
        this.chunkSourceFactory = chunkSourceFactory2;
        this.transferListener = transferListener2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.eventDispatcher = eventDispatcher2;
        this.elapsedRealtimeOffsetMs = elapsedRealtimeOffsetMs2;
        this.manifestLoaderErrorThrower = manifestLoaderErrorThrower2;
        this.allocator = allocator3;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory3;
        this.playerEmsgHandler = new PlayerEmsgHandler(dashManifest, playerEmsgCallback, allocator3);
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory3.createCompositeSequenceableLoader(this.sampleStreams);
        Period period = manifest2.getPeriod(periodIndex2);
        this.eventStreams = period.eventStreams;
        Pair<TrackGroupArray, TrackGroupInfo[]> result = buildTrackGroups(period.adaptationSets, this.eventStreams);
        this.trackGroups = (TrackGroupArray) result.first;
        this.trackGroupInfos = (TrackGroupInfo[]) result.second;
        eventDispatcher2.mediaPeriodCreated();
    }

    private static Pair<TrackGroupArray, TrackGroupInfo[]> buildTrackGroups(List<AdaptationSet> adaptationSets, List<EventStream> eventStreams2) {
        int[][] groupedAdaptationSetIndices = getGroupedAdaptationSetIndices(adaptationSets);
        int primaryGroupCount = groupedAdaptationSetIndices.length;
        boolean[] primaryGroupHasEventMessageTrackFlags = new boolean[primaryGroupCount];
        Format[][] primaryGroupCea608TrackFormats = new Format[primaryGroupCount][];
        int totalGroupCount = primaryGroupCount + identifyEmbeddedTracks(primaryGroupCount, adaptationSets, groupedAdaptationSetIndices, primaryGroupHasEventMessageTrackFlags, primaryGroupCea608TrackFormats) + eventStreams2.size();
        TrackGroup[] trackGroups2 = new TrackGroup[totalGroupCount];
        TrackGroupInfo[] trackGroupInfos2 = new TrackGroupInfo[totalGroupCount];
        buildManifestEventTrackGroupInfos(eventStreams2, trackGroups2, trackGroupInfos2, buildPrimaryAndEmbeddedTrackGroupInfos(adaptationSets, groupedAdaptationSetIndices, primaryGroupCount, primaryGroupHasEventMessageTrackFlags, primaryGroupCea608TrackFormats, trackGroups2, trackGroupInfos2));
        return Pair.create(new TrackGroupArray(trackGroups2), trackGroupInfos2);
    }

    /* JADX INFO: Multiple debug info for r2v2 int[][]: [D('groupedAdaptationSetIndices' int[][]), D('i' int)] */
    private static int[][] getGroupedAdaptationSetIndices(List<AdaptationSet> adaptationSets) {
        int adaptationSetCount = adaptationSets.size();
        SparseIntArray idToIndexMap = new SparseIntArray(adaptationSetCount);
        for (int i = 0; i < adaptationSetCount; i++) {
            idToIndexMap.put(adaptationSets.get(i).f95id, i);
        }
        int[][] groupedAdaptationSetIndices = new int[adaptationSetCount][];
        boolean[] adaptationSetUsedFlags = new boolean[adaptationSetCount];
        int groupCount = 0;
        for (int i2 = 0; i2 < adaptationSetCount; i2++) {
            if (!adaptationSetUsedFlags[i2]) {
                adaptationSetUsedFlags[i2] = true;
                Descriptor adaptationSetSwitchingProperty = findAdaptationSetSwitchingProperty(adaptationSets.get(i2).supplementalProperties);
                if (adaptationSetSwitchingProperty == null) {
                    groupedAdaptationSetIndices[groupCount] = new int[]{i2};
                    groupCount++;
                } else {
                    String[] extraAdaptationSetIds = Util.split(adaptationSetSwitchingProperty.value, ",");
                    int[] adaptationSetIndices = new int[(extraAdaptationSetIds.length + 1)];
                    adaptationSetIndices[0] = i2;
                    int outputIndex = 1;
                    for (String parseInt : extraAdaptationSetIds) {
                        int extraIndex = idToIndexMap.get(Integer.parseInt(parseInt), -1);
                        if (extraIndex != -1) {
                            adaptationSetUsedFlags[extraIndex] = true;
                            adaptationSetIndices[outputIndex] = extraIndex;
                            outputIndex++;
                        }
                    }
                    if (outputIndex < adaptationSetIndices.length) {
                        adaptationSetIndices = Arrays.copyOf(adaptationSetIndices, outputIndex);
                    }
                    groupedAdaptationSetIndices[groupCount] = adaptationSetIndices;
                    groupCount++;
                }
            }
        }
        return groupCount < adaptationSetCount ? (int[][]) Arrays.copyOf(groupedAdaptationSetIndices, groupCount) : groupedAdaptationSetIndices;
    }

    private static int identifyEmbeddedTracks(int primaryGroupCount, List<AdaptationSet> adaptationSets, int[][] groupedAdaptationSetIndices, boolean[] primaryGroupHasEventMessageTrackFlags, Format[][] primaryGroupCea608TrackFormats) {
        int numEmbeddedTrackGroups = 0;
        for (int i = 0; i < primaryGroupCount; i++) {
            if (hasEventMessageTrack(adaptationSets, groupedAdaptationSetIndices[i])) {
                primaryGroupHasEventMessageTrackFlags[i] = true;
                numEmbeddedTrackGroups++;
            }
            primaryGroupCea608TrackFormats[i] = getCea608TrackFormats(adaptationSets, groupedAdaptationSetIndices[i]);
            if (primaryGroupCea608TrackFormats[i].length != 0) {
                numEmbeddedTrackGroups++;
            }
        }
        return numEmbeddedTrackGroups;
    }

    /* JADX INFO: Multiple debug info for r1v1 'trackGroupCount'  int: [D('trackGroupCount' int), D('primaryTrackGroupIndex' int)] */
    private static int buildPrimaryAndEmbeddedTrackGroupInfos(List<AdaptationSet> adaptationSets, int[][] groupedAdaptationSetIndices, int primaryGroupCount, boolean[] primaryGroupHasEventMessageTrackFlags, Format[][] primaryGroupCea608TrackFormats, TrackGroup[] trackGroups2, TrackGroupInfo[] trackGroupInfos2) {
        int trackGroupCount;
        int trackGroupCount2;
        List<AdaptationSet> list = adaptationSets;
        int trackGroupCount3 = 0;
        int i = 0;
        while (i < primaryGroupCount) {
            int[] adaptationSetIndices = groupedAdaptationSetIndices[i];
            List<Representation> representations = new ArrayList<>();
            for (int adaptationSetIndex : adaptationSetIndices) {
                representations.addAll(list.get(adaptationSetIndex).representations);
            }
            Format[] formats = new Format[representations.size()];
            for (int j = 0; j < formats.length; j++) {
                formats[j] = ((Representation) representations.get(j)).format;
            }
            AdaptationSet firstAdaptationSet = list.get(adaptationSetIndices[0]);
            int trackGroupCount4 = trackGroupCount3 + 1;
            if (primaryGroupHasEventMessageTrackFlags[i]) {
                trackGroupCount = trackGroupCount4 + 1;
            } else {
                trackGroupCount = trackGroupCount4;
                trackGroupCount4 = -1;
            }
            if (primaryGroupCea608TrackFormats[i].length != 0) {
                trackGroupCount2 = trackGroupCount + 1;
            } else {
                trackGroupCount2 = trackGroupCount;
                trackGroupCount = -1;
            }
            trackGroups2[trackGroupCount3] = new TrackGroup(formats);
            trackGroupInfos2[trackGroupCount3] = TrackGroupInfo.primaryTrack(firstAdaptationSet.type, adaptationSetIndices, trackGroupCount3, trackGroupCount4, trackGroupCount);
            if (trackGroupCount4 != -1) {
                int i2 = firstAdaptationSet.f95id;
                StringBuilder sb = new StringBuilder(16);
                sb.append(i2);
                sb.append(":emsg");
                trackGroups2[trackGroupCount4] = new TrackGroup(Format.createSampleFormat(sb.toString(), MimeTypes.APPLICATION_EMSG, null, -1, null));
                trackGroupInfos2[trackGroupCount4] = TrackGroupInfo.embeddedEmsgTrack(adaptationSetIndices, trackGroupCount3);
            }
            if (trackGroupCount != -1) {
                trackGroups2[trackGroupCount] = new TrackGroup(primaryGroupCea608TrackFormats[i]);
                trackGroupInfos2[trackGroupCount] = TrackGroupInfo.embeddedCea608Track(adaptationSetIndices, trackGroupCount3);
            }
            i++;
            trackGroupCount3 = trackGroupCount2;
        }
        return trackGroupCount3;
    }

    private static void buildManifestEventTrackGroupInfos(List<EventStream> eventStreams2, TrackGroup[] trackGroups2, TrackGroupInfo[] trackGroupInfos2, int existingTrackGroupCount) {
        int i = 0;
        while (i < eventStreams2.size()) {
            trackGroups2[existingTrackGroupCount] = new TrackGroup(Format.createSampleFormat(eventStreams2.get(i).mo14759id(), MimeTypes.APPLICATION_EMSG, null, -1, null));
            trackGroupInfos2[existingTrackGroupCount] = TrackGroupInfo.mpdEventTrack(i);
            i++;
            existingTrackGroupCount++;
        }
    }

    private static Descriptor findAdaptationSetSwitchingProperty(List<Descriptor> descriptors) {
        for (int i = 0; i < descriptors.size(); i++) {
            Descriptor descriptor = descriptors.get(i);
            if ("urn:mpeg:dash:adaptation-set-switching:2016".equals(descriptor.schemeIdUri)) {
                return descriptor;
            }
        }
        return null;
    }

    private static boolean hasEventMessageTrack(List<AdaptationSet> adaptationSets, int[] adaptationSetIndices) {
        for (int i : adaptationSetIndices) {
            List<Representation> representations = adaptationSets.get(i).representations;
            for (int j = 0; j < representations.size(); j++) {
                if (!representations.get(j).inbandEventStreams.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Format[] getCea608TrackFormats(List<AdaptationSet> adaptationSets, int[] adaptationSetIndices) {
        List<AdaptationSet> list = adaptationSets;
        for (int i : adaptationSetIndices) {
            AdaptationSet adaptationSet = list.get(i);
            List<Descriptor> descriptors = list.get(i).accessibilityDescriptors;
            for (int j = 0; j < descriptors.size(); j++) {
                Descriptor descriptor = descriptors.get(j);
                if ("urn:scte:dash:cc:cea-608:2015".equals(descriptor.schemeIdUri)) {
                    String value = descriptor.value;
                    int i2 = 1;
                    if (value == null) {
                        return new Format[]{buildCea608TrackFormat(adaptationSet.f95id)};
                    }
                    String[] services = Util.split(value, ";");
                    Format[] formats = new Format[services.length];
                    int k = 0;
                    while (k < services.length) {
                        Matcher matcher = CEA608_SERVICE_DESCRIPTOR_REGEX.matcher(services[k]);
                        if (!matcher.matches()) {
                            Format[] formatArr = new Format[i2];
                            formatArr[0] = buildCea608TrackFormat(adaptationSet.f95id);
                            return formatArr;
                        }
                        formats[k] = buildCea608TrackFormat(adaptationSet.f95id, matcher.group(2), Integer.parseInt(matcher.group(i2)));
                        k++;
                        i2 = 1;
                    }
                    return formats;
                }
            }
        }
        return new Format[0];
    }

    private static Format buildCea608TrackFormat(int adaptationSetId) {
        return buildCea608TrackFormat(adaptationSetId, null, -1);
    }

    private static Format buildCea608TrackFormat(int adaptationSetId, String language, int accessibilityChannel) {
        String str;
        if (accessibilityChannel != -1) {
            StringBuilder sb = new StringBuilder(12);
            sb.append(":");
            sb.append(accessibilityChannel);
            str = sb.toString();
        } else {
            str = "";
        }
        StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 18);
        sb2.append(adaptationSetId);
        sb2.append(":cea608");
        sb2.append(str);
        return Format.createTextSampleFormat(sb2.toString(), MimeTypes.APPLICATION_CEA608, null, -1, 0, language, accessibilityChannel, null, Long.MAX_VALUE, null);
    }

    private static ChunkSampleStream<DashChunkSource>[] newSampleStreamArray(int length) {
        return new ChunkSampleStream[length];
    }

    public /* bridge */ /* synthetic */ void onContinueLoadingRequested(SequenceableLoader sequenceableLoader) {
        onContinueLoadingRequested((ChunkSampleStream<DashChunkSource>) ((ChunkSampleStream) sequenceableLoader));
    }

    public void updateManifest(DashManifest manifest2, int periodIndex2) {
        this.manifest = manifest2;
        this.periodIndex = periodIndex2;
        this.playerEmsgHandler.updateManifest(manifest2);
        ChunkSampleStream<DashChunkSource>[] chunkSampleStreamArr = this.sampleStreams;
        if (chunkSampleStreamArr != null) {
            for (ChunkSampleStream<DashChunkSource> sampleStream : chunkSampleStreamArr) {
                sampleStream.getChunkSource().updateManifest(manifest2, periodIndex2);
            }
            this.callback.onContinueLoadingRequested(this);
        }
        this.eventStreams = manifest2.getPeriod(periodIndex2).eventStreams;
        for (EventSampleStream eventSampleStream : this.eventSampleStreams) {
            Iterator<EventStream> it = this.eventStreams.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                EventStream eventStream = it.next();
                if (eventStream.mo14759id().equals(eventSampleStream.eventStreamId())) {
                    boolean z = true;
                    int lastPeriodIndex = manifest2.getPeriodCount() - 1;
                    if (!manifest2.dynamic || periodIndex2 != lastPeriodIndex) {
                        z = false;
                    }
                    eventSampleStream.updateEventStream(eventStream, z);
                }
            }
        }
    }

    public void release() {
        this.playerEmsgHandler.release();
        for (ChunkSampleStream<DashChunkSource> sampleStream : this.sampleStreams) {
            sampleStream.release(this);
        }
        this.callback = null;
        this.eventDispatcher.mediaPeriodReleased();
    }

    public synchronized void onSampleStreamReleased(ChunkSampleStream<DashChunkSource> stream) {
        PlayerEmsgHandler.PlayerTrackEmsgHandler trackEmsgHandler = this.trackEmsgHandlerBySampleStream.remove(stream);
        if (trackEmsgHandler != null) {
            trackEmsgHandler.release();
        }
    }

    public void prepare(MediaPeriod.Callback callback2, long positionUs) {
        this.callback = callback2;
        callback2.onPrepared(this);
    }

    public void maybeThrowPrepareError() throws IOException {
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public List<StreamKey> getStreamKeys(List<TrackSelection> trackSelections) {
        DashMediaPeriod dashMediaPeriod = this;
        List<AdaptationSet> manifestAdaptationSets = dashMediaPeriod.manifest.getPeriod(dashMediaPeriod.periodIndex).adaptationSets;
        List<StreamKey> streamKeys = new ArrayList<>();
        for (TrackSelection trackSelection : trackSelections) {
            TrackGroupInfo trackGroupInfo = dashMediaPeriod.trackGroupInfos[dashMediaPeriod.trackGroups.indexOf(trackSelection.getTrackGroup())];
            if (trackGroupInfo.trackGroupCategory == 0) {
                int[] adaptationSetIndices = trackGroupInfo.adaptationSetIndices;
                int[] trackIndices = new int[trackSelection.length()];
                for (int i = 0; i < trackSelection.length(); i++) {
                    trackIndices[i] = trackSelection.getIndexInTrackGroup(i);
                }
                Arrays.sort(trackIndices);
                int currentAdaptationSetIndex = 0;
                int totalTracksInPreviousAdaptationSets = 0;
                int tracksInCurrentAdaptationSet = manifestAdaptationSets.get(adaptationSetIndices[0]).representations.size();
                int i2 = 0;
                while (i2 < trackIndices.length) {
                    while (trackIndices[i2] >= totalTracksInPreviousAdaptationSets + tracksInCurrentAdaptationSet) {
                        currentAdaptationSetIndex++;
                        totalTracksInPreviousAdaptationSets += tracksInCurrentAdaptationSet;
                        tracksInCurrentAdaptationSet = manifestAdaptationSets.get(adaptationSetIndices[currentAdaptationSetIndex]).representations.size();
                    }
                    streamKeys.add(new StreamKey(dashMediaPeriod.periodIndex, adaptationSetIndices[currentAdaptationSetIndex], trackIndices[i2] - totalTracksInPreviousAdaptationSets));
                    i2++;
                    dashMediaPeriod = this;
                }
                dashMediaPeriod = this;
            }
        }
        return streamKeys;
    }

    public long selectTracks(TrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        int[] streamIndexToTrackGroupIndex = getStreamIndexToTrackGroupIndex(selections);
        releaseDisabledStreams(selections, mayRetainStreamFlags, streams);
        releaseOrphanEmbeddedStreams(selections, streams, streamIndexToTrackGroupIndex);
        selectNewStreams(selections, streams, streamResetFlags, positionUs, streamIndexToTrackGroupIndex);
        ArrayList<ChunkSampleStream<DashChunkSource>> sampleStreamList = new ArrayList<>();
        ArrayList<EventSampleStream> eventSampleStreamList = new ArrayList<>();
        for (SampleStream sampleStream : streams) {
            if (sampleStream instanceof ChunkSampleStream) {
                sampleStreamList.add((ChunkSampleStream) sampleStream);
            } else if (sampleStream instanceof EventSampleStream) {
                eventSampleStreamList.add((EventSampleStream) sampleStream);
            }
        }
        this.sampleStreams = newSampleStreamArray(sampleStreamList.size());
        sampleStreamList.toArray(this.sampleStreams);
        this.eventSampleStreams = new EventSampleStream[eventSampleStreamList.size()];
        eventSampleStreamList.toArray(this.eventSampleStreams);
        this.compositeSequenceableLoader = this.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.sampleStreams);
        return positionUs;
    }

    public void discardBuffer(long positionUs, boolean toKeyframe) {
        for (ChunkSampleStream<DashChunkSource> sampleStream : this.sampleStreams) {
            sampleStream.discardBuffer(positionUs, toKeyframe);
        }
    }

    public void reevaluateBuffer(long positionUs) {
        this.compositeSequenceableLoader.reevaluateBuffer(positionUs);
    }

    public boolean continueLoading(long positionUs) {
        return this.compositeSequenceableLoader.continueLoading(positionUs);
    }

    public long getNextLoadPositionUs() {
        return this.compositeSequenceableLoader.getNextLoadPositionUs();
    }

    public long readDiscontinuity() {
        if (this.notifiedReadingStarted) {
            return C0841C.TIME_UNSET;
        }
        this.eventDispatcher.readingStarted();
        this.notifiedReadingStarted = true;
        return C0841C.TIME_UNSET;
    }

    public long getBufferedPositionUs() {
        return this.compositeSequenceableLoader.getBufferedPositionUs();
    }

    public long seekToUs(long positionUs) {
        for (ChunkSampleStream<DashChunkSource> sampleStream : this.sampleStreams) {
            sampleStream.seekToUs(positionUs);
        }
        for (EventSampleStream sampleStream2 : this.eventSampleStreams) {
            sampleStream2.seekToUs(positionUs);
        }
        return positionUs;
    }

    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        for (ChunkSampleStream<DashChunkSource> sampleStream : this.sampleStreams) {
            if (sampleStream.primaryTrackType == 2) {
                return sampleStream.getAdjustedSeekPositionUs(positionUs, seekParameters);
            }
        }
        return positionUs;
    }

    public void onContinueLoadingRequested(ChunkSampleStream<DashChunkSource> chunkSampleStream) {
        this.callback.onContinueLoadingRequested(this);
    }

    private int[] getStreamIndexToTrackGroupIndex(TrackSelection[] selections) {
        int[] streamIndexToTrackGroupIndex = new int[selections.length];
        for (int i = 0; i < selections.length; i++) {
            if (selections[i] != null) {
                streamIndexToTrackGroupIndex[i] = this.trackGroups.indexOf(selections[i].getTrackGroup());
            } else {
                streamIndexToTrackGroupIndex[i] = -1;
            }
        }
        return streamIndexToTrackGroupIndex;
    }

    private void releaseDisabledStreams(TrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams) {
        for (int i = 0; i < selections.length; i++) {
            if (selections[i] == null || !mayRetainStreamFlags[i]) {
                if (streams[i] instanceof ChunkSampleStream) {
                    ((ChunkSampleStream) streams[i]).release(this);
                } else if (streams[i] instanceof ChunkSampleStream.EmbeddedSampleStream) {
                    ((ChunkSampleStream.EmbeddedSampleStream) streams[i]).release();
                }
                streams[i] = null;
            }
        }
    }

    private void releaseOrphanEmbeddedStreams(TrackSelection[] selections, SampleStream[] streams, int[] streamIndexToTrackGroupIndex) {
        boolean mayRetainStream;
        for (int i = 0; i < selections.length; i++) {
            if ((streams[i] instanceof EmptySampleStream) || (streams[i] instanceof ChunkSampleStream.EmbeddedSampleStream)) {
                int primaryStreamIndex = getPrimaryStreamIndex(i, streamIndexToTrackGroupIndex);
                if (primaryStreamIndex == -1) {
                    mayRetainStream = streams[i] instanceof EmptySampleStream;
                } else {
                    mayRetainStream = (streams[i] instanceof ChunkSampleStream.EmbeddedSampleStream) && ((ChunkSampleStream.EmbeddedSampleStream) streams[i]).parent == streams[primaryStreamIndex];
                }
                if (!mayRetainStream) {
                    if (streams[i] instanceof ChunkSampleStream.EmbeddedSampleStream) {
                        ((ChunkSampleStream.EmbeddedSampleStream) streams[i]).release();
                    }
                    streams[i] = null;
                }
            }
        }
    }

    private void selectNewStreams(TrackSelection[] selections, SampleStream[] streams, boolean[] streamResetFlags, long positionUs, int[] streamIndexToTrackGroupIndex) {
        for (int i = 0; i < selections.length; i++) {
            if (streams[i] == null && selections[i] != null) {
                streamResetFlags[i] = true;
                TrackGroupInfo trackGroupInfo = this.trackGroupInfos[streamIndexToTrackGroupIndex[i]];
                if (trackGroupInfo.trackGroupCategory == 0) {
                    streams[i] = buildSampleStream(trackGroupInfo, selections[i], positionUs);
                } else if (trackGroupInfo.trackGroupCategory == 2) {
                    streams[i] = new EventSampleStream(this.eventStreams.get(trackGroupInfo.eventStreamGroupIndex), selections[i].getTrackGroup().getFormat(0), this.manifest.dynamic);
                }
            }
        }
        for (int i2 = 0; i2 < selections.length; i2++) {
            if (streams[i2] == null && selections[i2] != null) {
                TrackGroupInfo trackGroupInfo2 = this.trackGroupInfos[streamIndexToTrackGroupIndex[i2]];
                if (trackGroupInfo2.trackGroupCategory == 1) {
                    int primaryStreamIndex = getPrimaryStreamIndex(i2, streamIndexToTrackGroupIndex);
                    if (primaryStreamIndex == -1) {
                        streams[i2] = new EmptySampleStream();
                    } else {
                        streams[i2] = ((ChunkSampleStream) streams[primaryStreamIndex]).selectEmbeddedTrack(positionUs, trackGroupInfo2.trackType);
                    }
                }
            }
        }
    }

    private int getPrimaryStreamIndex(int embeddedStreamIndex, int[] streamIndexToTrackGroupIndex) {
        int embeddedTrackGroupIndex = streamIndexToTrackGroupIndex[embeddedStreamIndex];
        if (embeddedTrackGroupIndex == -1) {
            return -1;
        }
        int primaryTrackGroupIndex = this.trackGroupInfos[embeddedTrackGroupIndex].primaryTrackGroupIndex;
        for (int i = 0; i < streamIndexToTrackGroupIndex.length; i++) {
            int trackGroupIndex = streamIndexToTrackGroupIndex[i];
            if (trackGroupIndex == primaryTrackGroupIndex && this.trackGroupInfos[trackGroupIndex].trackGroupCategory == 0) {
                return i;
            }
        }
        return -1;
    }

    private ChunkSampleStream<DashChunkSource> buildSampleStream(TrackGroupInfo trackGroupInfo, TrackSelection selection, long positionUs) {
        TrackGroup embeddedEventMessageTrackGroup;
        TrackGroup embeddedCea608TrackGroup;
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler;
        TrackGroupInfo trackGroupInfo2 = trackGroupInfo;
        int embeddedTrackCount = 0;
        boolean z = true;
        boolean enableEventMessageTrack = trackGroupInfo2.embeddedEventMessageTrackGroupIndex != -1;
        if (enableEventMessageTrack) {
            embeddedTrackCount = 0 + 1;
            embeddedEventMessageTrackGroup = this.trackGroups.get(trackGroupInfo2.embeddedEventMessageTrackGroupIndex);
        } else {
            embeddedEventMessageTrackGroup = null;
        }
        if (trackGroupInfo2.embeddedCea608TrackGroupIndex == -1) {
            z = false;
        }
        boolean enableCea608Tracks = z;
        if (enableCea608Tracks) {
            TrackGroup embeddedCea608TrackGroup2 = this.trackGroups.get(trackGroupInfo2.embeddedCea608TrackGroupIndex);
            embeddedTrackCount += embeddedCea608TrackGroup2.length;
            embeddedCea608TrackGroup = embeddedCea608TrackGroup2;
        } else {
            embeddedCea608TrackGroup = null;
        }
        Format[] embeddedTrackFormats = new Format[embeddedTrackCount];
        int[] embeddedTrackTypes = new int[embeddedTrackCount];
        int embeddedTrackCount2 = 0;
        if (enableEventMessageTrack) {
            embeddedTrackFormats[0] = embeddedEventMessageTrackGroup.getFormat(0);
            embeddedTrackTypes[0] = 4;
            embeddedTrackCount2 = 0 + 1;
        }
        ArrayList arrayList = new ArrayList();
        if (enableCea608Tracks) {
            for (int i = 0; i < embeddedCea608TrackGroup.length; i++) {
                embeddedTrackFormats[embeddedTrackCount2] = embeddedCea608TrackGroup.getFormat(i);
                embeddedTrackTypes[embeddedTrackCount2] = 3;
                arrayList.add(embeddedTrackFormats[embeddedTrackCount2]);
                embeddedTrackCount2++;
            }
        }
        if (!this.manifest.dynamic || !enableEventMessageTrack) {
            playerTrackEmsgHandler = null;
        } else {
            playerTrackEmsgHandler = this.playerEmsgHandler.newPlayerTrackEmsgHandler();
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler trackPlayerEmsgHandler = playerTrackEmsgHandler;
        DashChunkSource chunkSource = this.chunkSourceFactory.createDashChunkSource(this.manifestLoaderErrorThrower, this.manifest, this.periodIndex, trackGroupInfo2.adaptationSetIndices, selection, trackGroupInfo2.trackType, this.elapsedRealtimeOffsetMs, enableEventMessageTrack, arrayList, trackPlayerEmsgHandler, this.transferListener);
        PlayerEmsgHandler.PlayerTrackEmsgHandler trackPlayerEmsgHandler2 = trackPlayerEmsgHandler;
        ChunkSampleStream<DashChunkSource> stream = new ChunkSampleStream<>(trackGroupInfo2.trackType, embeddedTrackTypes, embeddedTrackFormats, chunkSource, this, this.allocator, positionUs, this.loadErrorHandlingPolicy, this.eventDispatcher);
        synchronized (this) {
            this.trackEmsgHandlerBySampleStream.put(stream, trackPlayerEmsgHandler2);
        }
        return stream;
    }

    private static final class TrackGroupInfo {
        private static final int CATEGORY_EMBEDDED = 1;
        private static final int CATEGORY_MANIFEST_EVENTS = 2;
        private static final int CATEGORY_PRIMARY = 0;
        public final int[] adaptationSetIndices;
        public final int embeddedCea608TrackGroupIndex;
        public final int embeddedEventMessageTrackGroupIndex;
        public final int eventStreamGroupIndex;
        public final int primaryTrackGroupIndex;
        public final int trackGroupCategory;
        public final int trackType;

        private TrackGroupInfo(int trackType2, int trackGroupCategory2, int[] adaptationSetIndices2, int primaryTrackGroupIndex2, int embeddedEventMessageTrackGroupIndex2, int embeddedCea608TrackGroupIndex2, int eventStreamGroupIndex2) {
            this.trackType = trackType2;
            this.adaptationSetIndices = adaptationSetIndices2;
            this.trackGroupCategory = trackGroupCategory2;
            this.primaryTrackGroupIndex = primaryTrackGroupIndex2;
            this.embeddedEventMessageTrackGroupIndex = embeddedEventMessageTrackGroupIndex2;
            this.embeddedCea608TrackGroupIndex = embeddedCea608TrackGroupIndex2;
            this.eventStreamGroupIndex = eventStreamGroupIndex2;
        }

        public static TrackGroupInfo primaryTrack(int trackType2, int[] adaptationSetIndices2, int primaryTrackGroupIndex2, int embeddedEventMessageTrackGroupIndex2, int embeddedCea608TrackGroupIndex2) {
            return new TrackGroupInfo(trackType2, 0, adaptationSetIndices2, primaryTrackGroupIndex2, embeddedEventMessageTrackGroupIndex2, embeddedCea608TrackGroupIndex2, -1);
        }

        public static TrackGroupInfo embeddedEmsgTrack(int[] adaptationSetIndices2, int primaryTrackGroupIndex2) {
            return new TrackGroupInfo(4, 1, adaptationSetIndices2, primaryTrackGroupIndex2, -1, -1, -1);
        }

        public static TrackGroupInfo embeddedCea608Track(int[] adaptationSetIndices2, int primaryTrackGroupIndex2) {
            return new TrackGroupInfo(3, 1, adaptationSetIndices2, primaryTrackGroupIndex2, -1, -1, -1);
        }

        public static TrackGroupInfo mpdEventTrack(int eventStreamIndex) {
            return new TrackGroupInfo(4, 2, new int[0], -1, -1, -1, eventStreamIndex);
        }

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface TrackGroupCategory {
        }
    }
}
