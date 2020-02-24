package com.google.android.tvlauncher.data;

import com.google.android.tvlauncher.model.Program;

class ProgramRef extends DataBufferRef implements Program {
    ProgramRef(AbstractDataBuffer dataBuffer, int position) {
        super(dataBuffer, position);
    }

    public long getId() {
        return getLong(0);
    }

    public long getChannelId() {
        return getLong(1);
    }

    public int getType() {
        return getInt(2);
    }

    public String getTitle() {
        return getString(3);
    }

    public String getShortDescription() {
        return getString(4);
    }

    public String getPreviewImageUri() {
        return getString(5);
    }

    public int getPreviewImageAspectRatio() {
        return getInt(6);
    }

    public String getThumbnailUri() {
        return getString(7);
    }

    public int getThumbnailAspectRatio() {
        return getInt(8);
    }

    public String getPreviewVideoUri() {
        return getString(9);
    }

    public String getPreviewAudioUri() {
        return getString(10);
    }

    public String getActionUri() {
        return getString(11);
    }

    public String getAuthor() {
        return getString(12);
    }

    public String getReleaseDate() {
        return getString(13);
    }

    public int getInteractionType() {
        return getInt(14);
    }

    public long getInteractionCount() {
        return getLong(15);
    }

    public int getAvailability() {
        return getInt(16);
    }

    public String getStartingPrice() {
        return getString(17);
    }

    public String getOfferPrice() {
        return getString(18);
    }

    public String getContentRating() {
        return getString(19);
    }

    public String getLogoUri() {
        return getString(20);
    }

    public String getLogoContentDescription() {
        return getString(21);
    }

    public String getCanonicalGenres() {
        return getString(22);
    }

    public long getDuration() {
        return getLong(24);
    }

    public int getItemCount() {
        return getInt(25);
    }

    public String getSeasonDisplayNumber() {
        return getString(26);
    }

    public int getTvSeriesItemType() {
        return getInt(27);
    }

    public String getEpisodeDisplayNumber() {
        return getString(28);
    }

    public String getEpisodeTitle() {
        return getString(29);
    }

    public long getPlaybackPosition() {
        return getLong(30);
    }

    public String getContentId() {
        return getString(31);
    }

    public int getReviewRatingStyle() {
        return getInt(32);
    }

    public String getReviewRating() {
        return getString(33);
    }

    public boolean isLive() {
        return getInt(34) == 1;
    }

    public long getLiveStartTime() {
        return getLong(35);
    }

    public long getLiveEndTime() {
        return getLong(36);
    }

    public int getVideoWidth() {
        return getInt(37);
    }

    public int getVideoHeight() {
        return getInt(38);
    }

    public String getGenre() {
        return getString(23);
    }

    public int getWatchNextType() {
        return getInt(41);
    }

    public long getLastEngagementTime() {
        return getLong(42);
    }

    public String getPackageName() {
        return getString(43);
    }

    public String getAdId() {
        return getString(40);
    }

    public byte[] getAdConfigSerialized() {
        return getBlob(39);
    }

    public String toString() {
        long id = getId();
        long channelId = getChannelId();
        String title = getTitle();
        String previewImageUri = getPreviewImageUri();
        String previewVideoUri = getPreviewVideoUri();
        StringBuilder sb = new StringBuilder(String.valueOf(title).length() + 114 + String.valueOf(previewImageUri).length() + String.valueOf(previewVideoUri).length());
        sb.append("Program{id=");
        sb.append(id);
        sb.append(", channelId=");
        sb.append(channelId);
        sb.append(", title='");
        sb.append(title);
        sb.append('\'');
        sb.append(", previewImageUri='");
        sb.append(previewImageUri);
        sb.append('\'');
        sb.append(", previewVideoUri='");
        sb.append(previewVideoUri);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
