package com.google.android.exoplayer2.p008ui;

import android.content.res.Resources;
import android.text.TextUtils;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

import java.util.Locale;

/* renamed from: com.google.android.exoplayer2.ui.DefaultTrackNameProvider */
public class DefaultTrackNameProvider implements TrackNameProvider {
    private final Resources resources;

    public DefaultTrackNameProvider(Resources resources2) {
        this.resources = (Resources) Assertions.checkNotNull(resources2);
    }

    private static int inferPrimaryTrackType(Format format) {
        int trackType = MimeTypes.getTrackType(format.sampleMimeType);
        if (trackType != -1) {
            return trackType;
        }
        if (MimeTypes.getVideoMediaMimeType(format.codecs) != null) {
            return 2;
        }
        if (MimeTypes.getAudioMediaMimeType(format.codecs) != null) {
            return 1;
        }
        if (format.width != -1 || format.height != -1) {
            return 2;
        }
        if (format.channelCount == -1 && format.sampleRate == -1) {
            return -1;
        }
        return 1;
    }

    public String getTrackName(Format format) {
        String trackName;
        int trackType = inferPrimaryTrackType(format);
        if (trackType == 2) {
            trackName = joinWithSeparator(buildRoleString(format), buildResolutionString(format), buildBitrateString(format));
        } else if (trackType == 1) {
            trackName = joinWithSeparator(buildLanguageOrLabelString(format), buildAudioChannelString(format), buildBitrateString(format));
        } else {
            trackName = buildLanguageOrLabelString(format);
        }
        return trackName.length() == 0 ? this.resources.getString(C0931R.string.exo_track_unknown) : trackName;
    }

    private String buildResolutionString(Format format) {
        int width = format.width;
        int height = format.height;
        if (width == -1 || height == -1) {
            return "";
        }
        return this.resources.getString(C0931R.string.exo_track_resolution, Integer.valueOf(width), Integer.valueOf(height));
    }

    private String buildBitrateString(Format format) {
        int bitrate = format.bitrate;
        if (bitrate == -1) {
            return "";
        }
        return this.resources.getString(C0931R.string.exo_track_bitrate, Float.valueOf(((float) bitrate) / 1000000.0f));
    }

    private String buildAudioChannelString(Format format) {
        int channelCount = format.channelCount;
        if (channelCount == -1 || channelCount < 1) {
            return "";
        }
        if (channelCount == 1) {
            return this.resources.getString(C0931R.string.exo_track_mono);
        }
        if (channelCount == 2) {
            return this.resources.getString(C0931R.string.exo_track_stereo);
        }
        if (channelCount == 6 || channelCount == 7) {
            return this.resources.getString(C0931R.string.exo_track_surround_5_point_1);
        }
        if (channelCount != 8) {
            return this.resources.getString(C0931R.string.exo_track_surround);
        }
        return this.resources.getString(C0931R.string.exo_track_surround_7_point_1);
    }

    private String buildLanguageOrLabelString(Format format) {
        String languageAndRole = joinWithSeparator(buildLanguageString(format), buildRoleString(format));
        return TextUtils.isEmpty(languageAndRole) ? buildLabelString(format) : languageAndRole;
    }

    private String buildLabelString(Format format) {
        return TextUtils.isEmpty(format.label) ? "" : format.label;
    }

    private String buildLanguageString(Format format) {
        String language = format.language;
        if (TextUtils.isEmpty(language) || C0841C.LANGUAGE_UNDETERMINED.equals(language)) {
            return "";
        }
        return (Util.SDK_INT >= 21 ? Locale.forLanguageTag(language) : new Locale(language)).getDisplayName();
    }

    private String buildRoleString(Format format) {
        String roles = "";
        if ((format.roleFlags & 2) != 0) {
            roles = this.resources.getString(C0931R.string.exo_track_role_alternate);
        }
        if ((format.roleFlags & 4) != 0) {
            roles = joinWithSeparator(roles, this.resources.getString(C0931R.string.exo_track_role_supplementary));
        }
        if ((format.roleFlags & 8) != 0) {
            roles = joinWithSeparator(roles, this.resources.getString(C0931R.string.exo_track_role_commentary));
        }
        if ((format.roleFlags & 1088) == 0) {
            return roles;
        }
        return joinWithSeparator(roles, this.resources.getString(C0931R.string.exo_track_role_closed_captions));
    }

    private String joinWithSeparator(String... items) {
        String itemList = "";
        for (String item : items) {
            if (item.length() > 0) {
                if (TextUtils.isEmpty(itemList)) {
                    itemList = item;
                } else {
                    itemList = this.resources.getString(C0931R.string.exo_item_list, itemList, item);
                }
            }
        }
        return itemList;
    }
}
