package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.CommentFrame;
import com.google.android.exoplayer2.metadata.id3.InternalFrame;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GaplessInfoHolder {
    private static final Pattern GAPLESS_COMMENT_PATTERN = Pattern.compile("^ [0-9a-fA-F]{8} ([0-9a-fA-F]{8}) ([0-9a-fA-F]{8})");
    private static final String GAPLESS_DESCRIPTION = "iTunSMPB";
    private static final String GAPLESS_DOMAIN = "com.apple.iTunes";
    public int encoderDelay = -1;
    public int encoderPadding = -1;

    public boolean setFromXingHeaderValue(int value) {
        int encoderDelay2 = value >> 12;
        int encoderPadding2 = value & 4095;
        if (encoderDelay2 <= 0 && encoderPadding2 <= 0) {
            return false;
        }
        this.encoderDelay = encoderDelay2;
        this.encoderPadding = encoderPadding2;
        return true;
    }

    public boolean setFromMetadata(Metadata metadata) {
        for (int i = 0; i < metadata.length(); i++) {
            Metadata.Entry entry = metadata.get(i);
            if (entry instanceof CommentFrame) {
                CommentFrame commentFrame = (CommentFrame) entry;
                if (GAPLESS_DESCRIPTION.equals(commentFrame.description) && setFromComment(commentFrame.text)) {
                    return true;
                }
            } else if (entry instanceof InternalFrame) {
                InternalFrame internalFrame = (InternalFrame) entry;
                if (GAPLESS_DOMAIN.equals(internalFrame.domain) && GAPLESS_DESCRIPTION.equals(internalFrame.description) && setFromComment(internalFrame.text)) {
                    return true;
                }
            } else {
                continue;
            }
        }
        return false;
    }

    private boolean setFromComment(String data) {
        Matcher matcher = GAPLESS_COMMENT_PATTERN.matcher(data);
        if (!matcher.find()) {
            return false;
        }
        try {
            int encoderDelay2 = Integer.parseInt(matcher.group(1), 16);
            int encoderPadding2 = Integer.parseInt(matcher.group(2), 16);
            if (encoderDelay2 <= 0) {
                if (encoderPadding2 <= 0) {
                    return false;
                }
            }
            this.encoderDelay = encoderDelay2;
            this.encoderPadding = encoderPadding2;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean hasGaplessInfo() {
        return (this.encoderDelay == -1 || this.encoderPadding == -1) ? false : true;
    }
}
