package com.google.android.exoplayer2.text.webvtt;

import android.text.TextUtils;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.ParsableByteArray;

import java.util.ArrayList;
import java.util.List;

public final class WebvttDecoder extends SimpleSubtitleDecoder {
    private static final String COMMENT_START = "NOTE";
    private static final int EVENT_COMMENT = 1;
    private static final int EVENT_CUE = 3;
    private static final int EVENT_END_OF_FILE = 0;
    private static final int EVENT_NONE = -1;
    private static final int EVENT_STYLE_BLOCK = 2;
    private static final String STYLE_START = "STYLE";
    private final CssParser cssParser = new CssParser();
    private final WebvttCueParser cueParser = new WebvttCueParser();
    private final List<WebvttCssStyle> definedStyles = new ArrayList();
    private final ParsableByteArray parsableWebvttData = new ParsableByteArray();
    private final WebvttCue.Builder webvttCueBuilder = new WebvttCue.Builder();

    public WebvttDecoder() {
        super("WebvttDecoder");
    }

    private static int getNextEvent(ParsableByteArray parsableWebvttData2) {
        int foundEvent = -1;
        int currentInputPosition = 0;
        while (foundEvent == -1) {
            currentInputPosition = parsableWebvttData2.getPosition();
            String line = parsableWebvttData2.readLine();
            if (line == null) {
                foundEvent = 0;
            } else if (STYLE_START.equals(line)) {
                foundEvent = 2;
            } else if (line.startsWith(COMMENT_START)) {
                foundEvent = 1;
            } else {
                foundEvent = 3;
            }
        }
        parsableWebvttData2.setPosition(currentInputPosition);
        return foundEvent;
    }

    private static void skipComment(ParsableByteArray parsableWebvttData2) {
        do {
        } while (!TextUtils.isEmpty(parsableWebvttData2.readLine()));
    }

    /* access modifiers changed from: protected */
    public WebvttSubtitle decode(byte[] bytes, int length, boolean reset) throws SubtitleDecoderException {
        this.parsableWebvttData.reset(bytes, length);
        this.webvttCueBuilder.reset();
        this.definedStyles.clear();
        try {
            WebvttParserUtil.validateWebvttHeaderLine(this.parsableWebvttData);
            do {
            } while (!TextUtils.isEmpty(this.parsableWebvttData.readLine()));
            ArrayList<WebvttCue> subtitles = new ArrayList<>();
            while (true) {
                int nextEvent = getNextEvent(this.parsableWebvttData);
                int event = nextEvent;
                if (nextEvent == 0) {
                    return new WebvttSubtitle(subtitles);
                }
                if (event == 1) {
                    skipComment(this.parsableWebvttData);
                } else if (event == 2) {
                    if (subtitles.isEmpty()) {
                        this.parsableWebvttData.readLine();
                        WebvttCssStyle styleBlock = this.cssParser.parseBlock(this.parsableWebvttData);
                        if (styleBlock != null) {
                            this.definedStyles.add(styleBlock);
                        }
                    } else {
                        throw new SubtitleDecoderException("A style block was found after the first cue.");
                    }
                } else if (event == 3 && this.cueParser.parseCue(this.parsableWebvttData, this.webvttCueBuilder, this.definedStyles)) {
                    subtitles.add(this.webvttCueBuilder.build());
                    this.webvttCueBuilder.reset();
                }
            }
        } catch (ParserException e) {
            throw new SubtitleDecoderException(e);
        }
    }
}
