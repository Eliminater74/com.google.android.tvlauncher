package com.google.android.exoplayer2.text.ttml;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.util.Base64;
import android.util.Pair;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.util.Assertions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

final class TtmlNode {
    public static final String ANONYMOUS_REGION_ID = "";
    public static final String ATTR_ID = "id";
    public static final String ATTR_TTS_BACKGROUND_COLOR = "backgroundColor";
    public static final String ATTR_TTS_COLOR = "color";
    public static final String ATTR_TTS_DISPLAY_ALIGN = "displayAlign";
    public static final String ATTR_TTS_EXTENT = "extent";
    public static final String ATTR_TTS_FONT_FAMILY = "fontFamily";
    public static final String ATTR_TTS_FONT_SIZE = "fontSize";
    public static final String ATTR_TTS_FONT_STYLE = "fontStyle";
    public static final String ATTR_TTS_FONT_WEIGHT = "fontWeight";
    public static final String ATTR_TTS_ORIGIN = "origin";
    public static final String ATTR_TTS_TEXT_ALIGN = "textAlign";
    public static final String ATTR_TTS_TEXT_DECORATION = "textDecoration";
    public static final String BOLD = "bold";
    public static final String CENTER = "center";
    public static final String END = "end";
    public static final String ITALIC = "italic";
    public static final String LEFT = "left";
    public static final String LINETHROUGH = "linethrough";
    public static final String NO_LINETHROUGH = "nolinethrough";
    public static final String NO_UNDERLINE = "nounderline";
    public static final String RIGHT = "right";
    public static final String START = "start";
    public static final String TAG_BODY = "body";
    public static final String TAG_BR = "br";
    public static final String TAG_DATA = "data";
    public static final String TAG_DIV = "div";
    public static final String TAG_HEAD = "head";
    public static final String TAG_IMAGE = "image";
    public static final String TAG_INFORMATION = "information";
    public static final String TAG_LAYOUT = "layout";
    public static final String TAG_METADATA = "metadata";
    public static final String TAG_P = "p";
    public static final String TAG_REGION = "region";
    public static final String TAG_SPAN = "span";
    public static final String TAG_STYLE = "style";
    public static final String TAG_STYLING = "styling";
    public static final String TAG_TT = "tt";
    public static final String UNDERLINE = "underline";
    public final long endTimeUs;
    @Nullable
    public final String imageId;
    public final boolean isTextNode;
    public final String regionId;
    public final long startTimeUs;
    @Nullable
    public final TtmlStyle style;
    @Nullable
    public final String tag;
    @Nullable
    public final String text;
    private final HashMap<String, Integer> nodeEndsByRegion;
    private final HashMap<String, Integer> nodeStartsByRegion;
    @Nullable
    private final String[] styleIds;
    private List<TtmlNode> children;

    private TtmlNode(@Nullable String tag2, @Nullable String text2, long startTimeUs2, long endTimeUs2, @Nullable TtmlStyle style2, @Nullable String[] styleIds2, String regionId2, @Nullable String imageId2) {
        this.tag = tag2;
        this.text = text2;
        this.imageId = imageId2;
        this.style = style2;
        this.styleIds = styleIds2;
        this.isTextNode = text2 != null;
        this.startTimeUs = startTimeUs2;
        this.endTimeUs = endTimeUs2;
        this.regionId = (String) Assertions.checkNotNull(regionId2);
        this.nodeStartsByRegion = new HashMap<>();
        this.nodeEndsByRegion = new HashMap<>();
    }

    public static TtmlNode buildTextNode(String text2) {
        return new TtmlNode(null, TtmlRenderUtil.applyTextElementSpacePolicy(text2), C0841C.TIME_UNSET, C0841C.TIME_UNSET, null, null, "", null);
    }

    public static TtmlNode buildNode(@Nullable String tag2, long startTimeUs2, long endTimeUs2, @Nullable TtmlStyle style2, @Nullable String[] styleIds2, String regionId2, @Nullable String imageId2) {
        return new TtmlNode(tag2, null, startTimeUs2, endTimeUs2, style2, styleIds2, regionId2, imageId2);
    }

    private static SpannableStringBuilder getRegionOutput(String resolvedRegionId, Map<String, SpannableStringBuilder> regionOutputs) {
        if (!regionOutputs.containsKey(resolvedRegionId)) {
            regionOutputs.put(resolvedRegionId, new SpannableStringBuilder());
        }
        return regionOutputs.get(resolvedRegionId);
    }

    public boolean isActive(long timeUs) {
        return (this.startTimeUs == C0841C.TIME_UNSET && this.endTimeUs == C0841C.TIME_UNSET) || (this.startTimeUs <= timeUs && this.endTimeUs == C0841C.TIME_UNSET) || ((this.startTimeUs == C0841C.TIME_UNSET && timeUs < this.endTimeUs) || (this.startTimeUs <= timeUs && timeUs < this.endTimeUs));
    }

    public void addChild(TtmlNode child) {
        if (this.children == null) {
            this.children = new ArrayList();
        }
        this.children.add(child);
    }

    public TtmlNode getChild(int index) {
        List<TtmlNode> list = this.children;
        if (list != null) {
            return list.get(index);
        }
        throw new IndexOutOfBoundsException();
    }

    public int getChildCount() {
        List<TtmlNode> list = this.children;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public long[] getEventTimesUs() {
        TreeSet<Long> eventTimeSet = new TreeSet<>();
        getEventTimes(eventTimeSet, false);
        long[] eventTimes = new long[eventTimeSet.size()];
        int i = 0;
        Iterator it = eventTimeSet.iterator();
        while (it.hasNext()) {
            eventTimes[i] = ((Long) it.next()).longValue();
            i++;
        }
        return eventTimes;
    }

    private void getEventTimes(TreeSet<Long> out, boolean descendsPNode) {
        boolean isPNode = TAG_P.equals(this.tag);
        boolean isDivNode = TAG_DIV.equals(this.tag);
        if (descendsPNode || isPNode || (isDivNode && this.imageId != null)) {
            long j = this.startTimeUs;
            if (j != C0841C.TIME_UNSET) {
                out.add(Long.valueOf(j));
            }
            long j2 = this.endTimeUs;
            if (j2 != C0841C.TIME_UNSET) {
                out.add(Long.valueOf(j2));
            }
        }
        if (this.children != null) {
            for (int i = 0; i < this.children.size(); i++) {
                this.children.get(i).getEventTimes(out, descendsPNode || isPNode);
            }
        }
    }

    public String[] getStyleIds() {
        return this.styleIds;
    }

    public List<Cue> getCues(long timeUs, Map<String, TtmlStyle> globalStyles, Map<String, TtmlRegion> regionMap, Map<String, String> imageMap) {
        long j = timeUs;
        Map<String, TtmlRegion> map = regionMap;
        List<Pair<String, String>> regionImageOutputs = new ArrayList<>();
        traverseForImage(j, this.regionId, regionImageOutputs);
        TreeMap<String, SpannableStringBuilder> regionTextOutputs = new TreeMap<>();
        traverseForText(timeUs, false, this.regionId, regionTextOutputs);
        traverseForStyle(j, globalStyles, regionTextOutputs);
        List<Cue> cues = new ArrayList<>();
        Iterator it = regionImageOutputs.iterator();
        while (it.hasNext()) {
            Pair<String, String> regionImagePair = (Pair) it.next();
            String encodedBitmapData = imageMap.get(regionImagePair.second);
            if (encodedBitmapData != null) {
                byte[] bitmapData = Base64.decode(encodedBitmapData, 0);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
                TtmlRegion region = map.get(regionImagePair.first);
                Iterator it2 = it;
                Cue cue = r15;
                Cue cue2 = new Cue(bitmap, region.position, 1, region.line, region.lineAnchor, region.width, Float.MIN_VALUE);
                cues.add(cue);
                it = it2;
            }
        }
        for (Map.Entry<String, SpannableStringBuilder> entry : regionTextOutputs.entrySet()) {
            TtmlRegion region2 = map.get(entry.getKey());
            cues.add(new Cue(cleanUpText((SpannableStringBuilder) entry.getValue()), (Layout.Alignment) null, region2.line, region2.lineType, region2.lineAnchor, region2.position, Integer.MIN_VALUE, region2.width, region2.textSizeType, region2.textSize));
        }
        return cues;
    }

    private void traverseForImage(long timeUs, String inheritedRegion, List<Pair<String, String>> regionImageList) {
        String str;
        String resolvedRegionId = "".equals(this.regionId) ? inheritedRegion : this.regionId;
        if (!isActive(timeUs) || !TAG_DIV.equals(this.tag) || (str = this.imageId) == null) {
            for (int i = 0; i < getChildCount(); i++) {
                getChild(i).traverseForImage(timeUs, resolvedRegionId, regionImageList);
            }
            return;
        }
        regionImageList.add(new Pair(resolvedRegionId, str));
    }

    private void traverseForText(long timeUs, boolean descendsPNode, String inheritedRegion, Map<String, SpannableStringBuilder> regionOutputs) {
        this.nodeStartsByRegion.clear();
        this.nodeEndsByRegion.clear();
        if (!TAG_METADATA.equals(this.tag)) {
            String resolvedRegionId = "".equals(this.regionId) ? inheritedRegion : this.regionId;
            if (this.isTextNode && descendsPNode) {
                getRegionOutput(resolvedRegionId, regionOutputs).append((CharSequence) this.text);
            } else if (TAG_BR.equals(this.tag) && descendsPNode) {
                getRegionOutput(resolvedRegionId, regionOutputs).append(10);
            } else if (isActive(timeUs)) {
                for (Map.Entry<String, SpannableStringBuilder> entry : regionOutputs.entrySet()) {
                    this.nodeStartsByRegion.put((String) entry.getKey(), Integer.valueOf(((SpannableStringBuilder) entry.getValue()).length()));
                }
                boolean isPNode = TAG_P.equals(this.tag);
                for (int i = 0; i < getChildCount(); i++) {
                    getChild(i).traverseForText(timeUs, descendsPNode || isPNode, resolvedRegionId, regionOutputs);
                }
                if (isPNode) {
                    TtmlRenderUtil.endParagraph(getRegionOutput(resolvedRegionId, regionOutputs));
                }
                for (Map.Entry<String, SpannableStringBuilder> entry2 : regionOutputs.entrySet()) {
                    this.nodeEndsByRegion.put((String) entry2.getKey(), Integer.valueOf(((SpannableStringBuilder) entry2.getValue()).length()));
                }
            }
        }
    }

    private void traverseForStyle(long timeUs, Map<String, TtmlStyle> globalStyles, Map<String, SpannableStringBuilder> regionOutputs) {
        if (isActive(timeUs)) {
            for (Map.Entry<String, Integer> entry : this.nodeEndsByRegion.entrySet()) {
                String regionId2 = (String) entry.getKey();
                int start = this.nodeStartsByRegion.containsKey(regionId2) ? this.nodeStartsByRegion.get(regionId2).intValue() : 0;
                int end = ((Integer) entry.getValue()).intValue();
                if (start != end) {
                    applyStyleToOutput(globalStyles, regionOutputs.get(regionId2), start, end);
                }
            }
            for (int i = 0; i < getChildCount(); i++) {
                getChild(i).traverseForStyle(timeUs, globalStyles, regionOutputs);
            }
        }
    }

    private void applyStyleToOutput(Map<String, TtmlStyle> globalStyles, SpannableStringBuilder regionOutput, int start, int end) {
        TtmlStyle resolvedStyle = TtmlRenderUtil.resolveStyle(this.style, this.styleIds, globalStyles);
        if (resolvedStyle != null) {
            TtmlRenderUtil.applyStylesToSpan(regionOutput, start, end, resolvedStyle);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.text.SpannableStringBuilder.delete(int, int):android.text.SpannableStringBuilder}
     arg types: [int, int]
     candidates:
      ClspMth{android.text.SpannableStringBuilder.delete(int, int):android.text.Editable}
      ClspMth{android.text.SpannableStringBuilder.delete(int, int):android.text.SpannableStringBuilder} */
    private SpannableStringBuilder cleanUpText(SpannableStringBuilder builder) {
        int builderLength = builder.length();
        for (int i = 0; i < builderLength; i++) {
            if (builder.charAt(i) == ' ') {
                int j = i + 1;
                while (j < builder.length() && builder.charAt(j) == ' ') {
                    j++;
                }
                int spacesToDelete = j - (i + 1);
                if (spacesToDelete > 0) {
                    builder.delete(i, i + spacesToDelete);
                    builderLength -= spacesToDelete;
                }
            }
        }
        if (builderLength > 0 && builder.charAt(0) == ' ') {
            builder.delete(0, 1);
            builderLength--;
        }
        for (int i2 = 0; i2 < builderLength - 1; i2++) {
            if (builder.charAt(i2) == 10 && builder.charAt(i2 + 1) == ' ') {
                builder.delete(i2 + 1, i2 + 2);
                builderLength--;
            }
        }
        if (builderLength > 0 && builder.charAt(builderLength - 1) == ' ') {
            builder.delete(builderLength - 1, builderLength);
            builderLength--;
        }
        for (int i3 = 0; i3 < builderLength - 1; i3++) {
            if (builder.charAt(i3) == ' ' && builder.charAt(i3 + 1) == 10) {
                builder.delete(i3, i3 + 1);
                builderLength--;
            }
        }
        if (builderLength > 0 && builder.charAt(builderLength - 1) == 10) {
            builder.delete(builderLength - 1, builderLength);
        }
        return builder;
    }
}
