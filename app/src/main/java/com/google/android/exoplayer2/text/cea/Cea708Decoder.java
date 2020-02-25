package com.google.android.exoplayer2.text.cea;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.SubtitleInputBuffer;
import com.google.android.exoplayer2.text.SubtitleOutputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Cea708Decoder extends CeaDecoder {
    private static final int CC_VALID_FLAG = 4;
    private static final int CHARACTER_BIG_CARONS = 42;
    private static final int CHARACTER_BIG_OE = 44;
    private static final int CHARACTER_BOLD_BULLET = 53;
    private static final int CHARACTER_CLOSE_DOUBLE_QUOTE = 52;
    private static final int CHARACTER_CLOSE_SINGLE_QUOTE = 50;
    private static final int CHARACTER_DIAERESIS_Y = 63;
    private static final int CHARACTER_ELLIPSIS = 37;
    private static final int CHARACTER_FIVE_EIGHTHS = 120;
    private static final int CHARACTER_HORIZONTAL_BORDER = 125;
    private static final int CHARACTER_LOWER_LEFT_BORDER = 124;
    private static final int CHARACTER_LOWER_RIGHT_BORDER = 126;
    private static final int CHARACTER_MN = 127;
    private static final int CHARACTER_NBTSP = 33;
    private static final int CHARACTER_ONE_EIGHTH = 118;
    private static final int CHARACTER_OPEN_DOUBLE_QUOTE = 51;
    private static final int CHARACTER_OPEN_SINGLE_QUOTE = 49;
    private static final int CHARACTER_SEVEN_EIGHTHS = 121;
    private static final int CHARACTER_SM = 61;
    private static final int CHARACTER_SMALL_CARONS = 58;
    private static final int CHARACTER_SMALL_OE = 60;
    private static final int CHARACTER_SOLID_BLOCK = 48;
    private static final int CHARACTER_THREE_EIGHTHS = 119;
    private static final int CHARACTER_TM = 57;
    private static final int CHARACTER_TSP = 32;
    private static final int CHARACTER_UPPER_LEFT_BORDER = 127;
    private static final int CHARACTER_UPPER_RIGHT_BORDER = 123;
    private static final int CHARACTER_VERTICAL_BORDER = 122;
    private static final int COMMAND_BS = 8;
    private static final int COMMAND_CLW = 136;
    private static final int COMMAND_CR = 13;
    private static final int COMMAND_CW0 = 128;
    private static final int COMMAND_CW1 = 129;
    private static final int COMMAND_CW2 = 130;
    private static final int COMMAND_CW3 = 131;
    private static final int COMMAND_CW4 = 132;
    private static final int COMMAND_CW5 = 133;
    private static final int COMMAND_CW6 = 134;
    private static final int COMMAND_CW7 = 135;
    private static final int COMMAND_DF0 = 152;
    private static final int COMMAND_DF1 = 153;
    private static final int COMMAND_DF2 = 154;
    private static final int COMMAND_DF3 = 155;
    private static final int COMMAND_DF4 = 156;
    private static final int COMMAND_DF5 = 157;
    private static final int COMMAND_DF6 = 158;
    private static final int COMMAND_DF7 = 159;
    private static final int COMMAND_DLC = 142;
    private static final int COMMAND_DLW = 140;
    private static final int COMMAND_DLY = 141;
    private static final int COMMAND_DSW = 137;
    private static final int COMMAND_ETX = 3;
    private static final int COMMAND_EXT1 = 16;
    private static final int COMMAND_EXT1_END = 23;
    private static final int COMMAND_EXT1_START = 17;
    private static final int COMMAND_FF = 12;
    private static final int COMMAND_HCR = 14;
    private static final int COMMAND_HDW = 138;
    private static final int COMMAND_NUL = 0;
    private static final int COMMAND_P16_END = 31;
    private static final int COMMAND_P16_START = 24;
    private static final int COMMAND_RST = 143;
    private static final int COMMAND_SPA = 144;
    private static final int COMMAND_SPC = 145;
    private static final int COMMAND_SPL = 146;
    private static final int COMMAND_SWA = 151;
    private static final int COMMAND_TGW = 139;
    private static final int DTVCC_PACKET_DATA = 2;
    private static final int DTVCC_PACKET_START = 3;
    private static final int GROUP_C0_END = 31;
    private static final int GROUP_C1_END = 159;
    private static final int GROUP_C2_END = 31;
    private static final int GROUP_C3_END = 159;
    private static final int GROUP_G0_END = 127;
    private static final int GROUP_G1_END = 255;
    private static final int GROUP_G2_END = 127;
    private static final int GROUP_G3_END = 255;
    private static final int NUM_WINDOWS = 8;
    private static final String TAG = "Cea708Decoder";
    private final ParsableByteArray ccData = new ParsableByteArray();
    private final CueBuilder[] cueBuilders;
    private final int selectedServiceNumber;
    private final ParsableBitArray serviceBlockPacket = new ParsableBitArray();
    private List<Cue> cues;
    private CueBuilder currentCueBuilder;
    private DtvCcPacket currentDtvCcPacket;
    private int currentWindow;
    private List<Cue> lastCues;

    public Cea708Decoder(int accessibilityChannel, List<byte[]> list) {
        this.selectedServiceNumber = accessibilityChannel == -1 ? 1 : accessibilityChannel;
        this.cueBuilders = new CueBuilder[8];
        for (int i = 0; i < 8; i++) {
            this.cueBuilders[i] = new CueBuilder();
        }
        this.currentCueBuilder = this.cueBuilders[0];
        resetCueBuilders();
    }

    public /* bridge */ /* synthetic */ SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        return super.dequeueInputBuffer();
    }

    public /* bridge */ /* synthetic */ SubtitleOutputBuffer dequeueOutputBuffer() throws SubtitleDecoderException {
        return super.dequeueOutputBuffer();
    }

    public /* bridge */ /* synthetic */ void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) throws SubtitleDecoderException {
        super.queueInputBuffer(subtitleInputBuffer);
    }

    public /* bridge */ /* synthetic */ void release() {
        super.release();
    }

    public /* bridge */ /* synthetic */ void setPositionUs(long j) {
        super.setPositionUs(j);
    }

    public String getName() {
        return TAG;
    }

    public void flush() {
        super.flush();
        this.cues = null;
        this.lastCues = null;
        this.currentWindow = 0;
        this.currentCueBuilder = this.cueBuilders[this.currentWindow];
        resetCueBuilders();
        this.currentDtvCcPacket = null;
    }

    /* access modifiers changed from: protected */
    public boolean isNewSubtitleDataAvailable() {
        return this.cues != this.lastCues;
    }

    /* access modifiers changed from: protected */
    public Subtitle createSubtitle() {
        List<Cue> list = this.cues;
        this.lastCues = list;
        return new CeaSubtitle(list);
    }

    /* access modifiers changed from: protected */
    public void decode(SubtitleInputBuffer inputBuffer) {
        this.ccData.reset(inputBuffer.data.array(), inputBuffer.data.limit());
        while (this.ccData.bytesLeft() >= 3) {
            int ccTypeAndValid = this.ccData.readUnsignedByte() & 7;
            int ccType = ccTypeAndValid & 3;
            boolean z = false;
            boolean ccValid = (ccTypeAndValid & 4) == 4;
            byte ccData1 = (byte) this.ccData.readUnsignedByte();
            byte ccData2 = (byte) this.ccData.readUnsignedByte();
            if ((ccType == 2 || ccType == 3) && ccValid) {
                if (ccType == 3) {
                    finalizeCurrentPacket();
                    int sequenceNumber = (ccData1 & 192) >> 6;
                    int packetSize = ccData1 & 63;
                    if (packetSize == 0) {
                        packetSize = 64;
                    }
                    this.currentDtvCcPacket = new DtvCcPacket(sequenceNumber, packetSize);
                    byte[] bArr = this.currentDtvCcPacket.packetData;
                    DtvCcPacket dtvCcPacket = this.currentDtvCcPacket;
                    int i = dtvCcPacket.currentIndex;
                    dtvCcPacket.currentIndex = i + 1;
                    bArr[i] = ccData2;
                } else {
                    if (ccType == 2) {
                        z = true;
                    }
                    Assertions.checkArgument(z);
                    DtvCcPacket dtvCcPacket2 = this.currentDtvCcPacket;
                    if (dtvCcPacket2 == null) {
                        Log.m26e(TAG, "Encountered DTVCC_PACKET_DATA before DTVCC_PACKET_START");
                    } else {
                        byte[] bArr2 = dtvCcPacket2.packetData;
                        DtvCcPacket dtvCcPacket3 = this.currentDtvCcPacket;
                        int i2 = dtvCcPacket3.currentIndex;
                        dtvCcPacket3.currentIndex = i2 + 1;
                        bArr2[i2] = ccData1;
                        byte[] bArr3 = this.currentDtvCcPacket.packetData;
                        DtvCcPacket dtvCcPacket4 = this.currentDtvCcPacket;
                        int i3 = dtvCcPacket4.currentIndex;
                        dtvCcPacket4.currentIndex = i3 + 1;
                        bArr3[i3] = ccData2;
                    }
                }
                if (this.currentDtvCcPacket.currentIndex == (this.currentDtvCcPacket.packetSize * 2) - 1) {
                    finalizeCurrentPacket();
                }
            }
        }
    }

    private void finalizeCurrentPacket() {
        if (this.currentDtvCcPacket != null) {
            processCurrentPacket();
            this.currentDtvCcPacket = null;
        }
    }

    private void processCurrentPacket() {
        if (this.currentDtvCcPacket.currentIndex != (this.currentDtvCcPacket.packetSize * 2) - 1) {
            int i = this.currentDtvCcPacket.currentIndex;
            int i2 = this.currentDtvCcPacket.sequenceNumber;
            StringBuilder sb = new StringBuilder(131);
            sb.append("DtvCcPacket ended prematurely; size is ");
            sb.append((this.currentDtvCcPacket.packetSize * 2) - 1);
            sb.append(", but current index is ");
            sb.append(i);
            sb.append(" (sequence number ");
            sb.append(i2);
            sb.append("); ignoring packet");
            Log.m30w(TAG, sb.toString());
            return;
        }
        this.serviceBlockPacket.reset(this.currentDtvCcPacket.packetData, this.currentDtvCcPacket.currentIndex);
        int serviceNumber = this.serviceBlockPacket.readBits(3);
        int blockSize = this.serviceBlockPacket.readBits(5);
        if (serviceNumber == 7) {
            this.serviceBlockPacket.skipBits(2);
            serviceNumber = this.serviceBlockPacket.readBits(6);
            if (serviceNumber < 7) {
                StringBuilder sb2 = new StringBuilder(44);
                sb2.append("Invalid extended service number: ");
                sb2.append(serviceNumber);
                Log.m30w(TAG, sb2.toString());
            }
        }
        if (blockSize == 0) {
            if (serviceNumber != 0) {
                StringBuilder sb3 = new StringBuilder(59);
                sb3.append("serviceNumber is non-zero (");
                sb3.append(serviceNumber);
                sb3.append(") when blockSize is 0");
                Log.m30w(TAG, sb3.toString());
            }
        } else if (serviceNumber == this.selectedServiceNumber) {
            boolean cuesNeedUpdate = false;
            while (this.serviceBlockPacket.bitsLeft() > 0) {
                int command = this.serviceBlockPacket.readBits(8);
                if (command == 16) {
                    int command2 = this.serviceBlockPacket.readBits(8);
                    if (command2 <= 31) {
                        handleC2Command(command2);
                    } else if (command2 <= 127) {
                        handleG2Character(command2);
                        cuesNeedUpdate = true;
                    } else if (command2 <= 159) {
                        handleC3Command(command2);
                    } else if (command2 <= 255) {
                        handleG3Character(command2);
                        cuesNeedUpdate = true;
                    } else {
                        StringBuilder sb4 = new StringBuilder(37);
                        sb4.append("Invalid extended command: ");
                        sb4.append(command2);
                        Log.m30w(TAG, sb4.toString());
                    }
                } else if (command <= 31) {
                    handleC0Command(command);
                } else if (command <= 127) {
                    handleG0Character(command);
                    cuesNeedUpdate = true;
                } else if (command <= 159) {
                    handleC1Command(command);
                    cuesNeedUpdate = true;
                } else if (command <= 255) {
                    handleG1Character(command);
                    cuesNeedUpdate = true;
                } else {
                    StringBuilder sb5 = new StringBuilder(33);
                    sb5.append("Invalid base command: ");
                    sb5.append(command);
                    Log.m30w(TAG, sb5.toString());
                }
            }
            if (cuesNeedUpdate) {
                this.cues = getDisplayCues();
            }
        }
    }

    private void handleC0Command(int command) {
        if (command == 0) {
            return;
        }
        if (command == 3) {
            this.cues = getDisplayCues();
        } else if (command != 8) {
            switch (command) {
                case 12:
                    resetCueBuilders();
                    return;
                case 13:
                    this.currentCueBuilder.append(10);
                    return;
                case 14:
                    return;
                default:
                    if (command >= 17 && command <= 23) {
                        StringBuilder sb = new StringBuilder(55);
                        sb.append("Currently unsupported COMMAND_EXT1 Command: ");
                        sb.append(command);
                        Log.m30w(TAG, sb.toString());
                        this.serviceBlockPacket.skipBits(8);
                        return;
                    } else if (command < 24 || command > 31) {
                        StringBuilder sb2 = new StringBuilder(31);
                        sb2.append("Invalid C0 command: ");
                        sb2.append(command);
                        Log.m30w(TAG, sb2.toString());
                        return;
                    } else {
                        StringBuilder sb3 = new StringBuilder(54);
                        sb3.append("Currently unsupported COMMAND_P16 Command: ");
                        sb3.append(command);
                        Log.m30w(TAG, sb3.toString());
                        this.serviceBlockPacket.skipBits(16);
                        return;
                    }
            }
        } else {
            this.currentCueBuilder.backspace();
        }
    }

    private void handleC1Command(int command) {
        switch (command) {
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
                int window = command - 128;
                if (this.currentWindow != window) {
                    this.currentWindow = window;
                    this.currentCueBuilder = this.cueBuilders[window];
                    return;
                }
                return;
            case 136:
                for (int i = 1; i <= 8; i++) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueBuilders[8 - i].clear();
                    }
                }
                return;
            case 137:
                for (int i2 = 1; i2 <= 8; i2++) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueBuilders[8 - i2].setVisibility(true);
                    }
                }
                return;
            case 138:
                for (int i3 = 1; i3 <= 8; i3++) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueBuilders[8 - i3].setVisibility(false);
                    }
                }
                return;
            case 139:
                for (int i4 = 1; i4 <= 8; i4++) {
                    if (this.serviceBlockPacket.readBit()) {
                        CueBuilder cueBuilder = this.cueBuilders[8 - i4];
                        cueBuilder.setVisibility(!cueBuilder.isVisible());
                    }
                }
                return;
            case 140:
                for (int i5 = 1; i5 <= 8; i5++) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueBuilders[8 - i5].reset();
                    }
                }
                return;
            case 141:
                this.serviceBlockPacket.skipBits(8);
                return;
            case 142:
                return;
            case 143:
                resetCueBuilders();
                return;
            case 144:
                if (!this.currentCueBuilder.isDefined()) {
                    this.serviceBlockPacket.skipBits(16);
                    return;
                } else {
                    handleSetPenAttributes();
                    return;
                }
            case 145:
                if (!this.currentCueBuilder.isDefined()) {
                    this.serviceBlockPacket.skipBits(24);
                    return;
                } else {
                    handleSetPenColor();
                    return;
                }
            case 146:
                if (!this.currentCueBuilder.isDefined()) {
                    this.serviceBlockPacket.skipBits(16);
                    return;
                } else {
                    handleSetPenLocation();
                    return;
                }
            case ClientAnalytics.LogRequest.LogSource.GMS_CORE_PEOPLE_AUTOCOMPLETE_VALUE /*147*/:
            case ClientAnalytics.LogRequest.LogSource.FLYDROID_COUNTERS_VALUE /*148*/:
            case ClientAnalytics.LogRequest.LogSource.FIREBALL_VALUE /*149*/:
            case 150:
            default:
                StringBuilder sb = new StringBuilder(31);
                sb.append("Invalid C1 command: ");
                sb.append(command);
                Log.m30w(TAG, sb.toString());
                return;
            case 151:
                if (!this.currentCueBuilder.isDefined()) {
                    this.serviceBlockPacket.skipBits(32);
                    return;
                } else {
                    handleSetWindowAttributes();
                    return;
                }
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case ClientAnalytics.LogRequest.LogSource.JAM_ANDROID_PRIMES_VALUE /*159*/:
                int window2 = command - 152;
                handleDefineWindow(window2);
                if (this.currentWindow != window2) {
                    this.currentWindow = window2;
                    this.currentCueBuilder = this.cueBuilders[window2];
                    return;
                }
                return;
        }
    }

    private void handleC2Command(int command) {
        if (command > 7) {
            if (command <= 15) {
                this.serviceBlockPacket.skipBits(8);
            } else if (command <= 23) {
                this.serviceBlockPacket.skipBits(16);
            } else if (command <= 31) {
                this.serviceBlockPacket.skipBits(24);
            }
        }
    }

    private void handleC3Command(int command) {
        if (command <= 135) {
            this.serviceBlockPacket.skipBits(32);
        } else if (command <= 143) {
            this.serviceBlockPacket.skipBits(40);
        } else if (command <= 159) {
            this.serviceBlockPacket.skipBits(2);
            this.serviceBlockPacket.skipBits(this.serviceBlockPacket.readBits(6) * 8);
        }
    }

    private void handleG0Character(int characterCode) {
        if (characterCode == 127) {
            this.currentCueBuilder.append(9835);
        } else {
            this.currentCueBuilder.append((char) (characterCode & 255));
        }
    }

    private void handleG1Character(int characterCode) {
        this.currentCueBuilder.append((char) (characterCode & 255));
    }

    private void handleG2Character(int characterCode) {
        if (characterCode == 32) {
            this.currentCueBuilder.append(' ');
        } else if (characterCode == 33) {
            this.currentCueBuilder.append(160);
        } else if (characterCode == 37) {
            this.currentCueBuilder.append(8230);
        } else if (characterCode == 42) {
            this.currentCueBuilder.append(352);
        } else if (characterCode == 44) {
            this.currentCueBuilder.append(338);
        } else if (characterCode == 63) {
            this.currentCueBuilder.append(376);
        } else if (characterCode == 57) {
            this.currentCueBuilder.append(8482);
        } else if (characterCode == 58) {
            this.currentCueBuilder.append(353);
        } else if (characterCode == 60) {
            this.currentCueBuilder.append(339);
        } else if (characterCode != 61) {
            switch (characterCode) {
                case 48:
                    this.currentCueBuilder.append(9608);
                    return;
                case 49:
                    this.currentCueBuilder.append(8216);
                    return;
                case 50:
                    this.currentCueBuilder.append(8217);
                    return;
                case 51:
                    this.currentCueBuilder.append(8220);
                    return;
                case 52:
                    this.currentCueBuilder.append(8221);
                    return;
                case 53:
                    this.currentCueBuilder.append(8226);
                    return;
                default:
                    switch (characterCode) {
                        case 118:
                            this.currentCueBuilder.append(8539);
                            return;
                        case 119:
                            this.currentCueBuilder.append(8540);
                            return;
                        case 120:
                            this.currentCueBuilder.append(8541);
                            return;
                        case 121:
                            this.currentCueBuilder.append(8542);
                            return;
                        case 122:
                            this.currentCueBuilder.append(9474);
                            return;
                        case 123:
                            this.currentCueBuilder.append(9488);
                            return;
                        case 124:
                            this.currentCueBuilder.append(9492);
                            return;
                        case 125:
                            this.currentCueBuilder.append(9472);
                            return;
                        case 126:
                            this.currentCueBuilder.append(9496);
                            return;
                        case ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE /*127*/:
                            this.currentCueBuilder.append(9484);
                            return;
                        default:
                            StringBuilder sb = new StringBuilder(33);
                            sb.append("Invalid G2 character: ");
                            sb.append(characterCode);
                            Log.m30w(TAG, sb.toString());
                            return;
                    }
            }
        } else {
            this.currentCueBuilder.append(8480);
        }
    }

    private void handleG3Character(int characterCode) {
        if (characterCode == 160) {
            this.currentCueBuilder.append(13252);
            return;
        }
        StringBuilder sb = new StringBuilder(33);
        sb.append("Invalid G3 character: ");
        sb.append(characterCode);
        Log.m30w(TAG, sb.toString());
        this.currentCueBuilder.append('_');
    }

    private void handleSetPenAttributes() {
        this.currentCueBuilder.setPenAttributes(this.serviceBlockPacket.readBits(4), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBit(), this.serviceBlockPacket.readBit(), this.serviceBlockPacket.readBits(3), this.serviceBlockPacket.readBits(3));
    }

    private void handleSetPenColor() {
        int foregroundColor = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        int backgroundColor = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        this.serviceBlockPacket.skipBits(2);
        this.currentCueBuilder.setPenColor(foregroundColor, backgroundColor, CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2)));
    }

    private void handleSetPenLocation() {
        this.serviceBlockPacket.skipBits(4);
        int row = this.serviceBlockPacket.readBits(4);
        this.serviceBlockPacket.skipBits(2);
        this.currentCueBuilder.setPenLocation(row, this.serviceBlockPacket.readBits(6));
    }

    private void handleSetWindowAttributes() {
        int borderType;
        int fillColor = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        int borderType2 = this.serviceBlockPacket.readBits(2);
        int borderColor = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        if (this.serviceBlockPacket.readBit()) {
            borderType = borderType2 | 4;
        } else {
            borderType = borderType2;
        }
        boolean wordWrapToggle = this.serviceBlockPacket.readBit();
        int printDirection = this.serviceBlockPacket.readBits(2);
        int scrollDirection = this.serviceBlockPacket.readBits(2);
        int justification = this.serviceBlockPacket.readBits(2);
        this.serviceBlockPacket.skipBits(8);
        this.currentCueBuilder.setWindowAttributes(fillColor, borderColor, wordWrapToggle, borderType, printDirection, scrollDirection, justification);
    }

    private void handleDefineWindow(int window) {
        CueBuilder cueBuilder = this.cueBuilders[window];
        this.serviceBlockPacket.skipBits(2);
        boolean visible = this.serviceBlockPacket.readBit();
        boolean rowLock = this.serviceBlockPacket.readBit();
        boolean columnLock = this.serviceBlockPacket.readBit();
        int priority = this.serviceBlockPacket.readBits(3);
        boolean relativePositioning = this.serviceBlockPacket.readBit();
        int verticalAnchor = this.serviceBlockPacket.readBits(7);
        int horizontalAnchor = this.serviceBlockPacket.readBits(8);
        int anchorId = this.serviceBlockPacket.readBits(4);
        int rowCount = this.serviceBlockPacket.readBits(4);
        this.serviceBlockPacket.skipBits(2);
        int columnCount = this.serviceBlockPacket.readBits(6);
        this.serviceBlockPacket.skipBits(2);
        cueBuilder.defineWindow(visible, rowLock, columnLock, priority, relativePositioning, verticalAnchor, horizontalAnchor, rowCount, columnCount, anchorId, this.serviceBlockPacket.readBits(3), this.serviceBlockPacket.readBits(3));
    }

    private List<Cue> getDisplayCues() {
        List<Cea708Cue> displayCues = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (!this.cueBuilders[i].isEmpty() && this.cueBuilders[i].isVisible()) {
                displayCues.add(this.cueBuilders[i].build());
            }
        }
        Collections.sort(displayCues);
        return Collections.unmodifiableList(displayCues);
    }

    private void resetCueBuilders() {
        for (int i = 0; i < 8; i++) {
            this.cueBuilders[i].reset();
        }
    }

    private static final class DtvCcPacket {
        public final byte[] packetData;
        public final int packetSize;
        public final int sequenceNumber;
        int currentIndex = 0;

        public DtvCcPacket(int sequenceNumber2, int packetSize2) {
            this.sequenceNumber = sequenceNumber2;
            this.packetSize = packetSize2;
            this.packetData = new byte[((packetSize2 * 2) - 1)];
        }
    }

    private static final class CueBuilder {
        public static final int COLOR_SOLID_BLACK = getArgbColorFromCeaColor(0, 0, 0, 0);
        public static final int COLOR_SOLID_WHITE = getArgbColorFromCeaColor(2, 2, 2, 0);
        public static final int COLOR_TRANSPARENT = getArgbColorFromCeaColor(0, 0, 0, 3);
        private static final int BORDER_AND_EDGE_TYPE_NONE = 0;
        private static final int BORDER_AND_EDGE_TYPE_UNIFORM = 3;
        private static final int DEFAULT_PRIORITY = 4;
        private static final int DIRECTION_BOTTOM_TO_TOP = 3;
        private static final int DIRECTION_LEFT_TO_RIGHT = 0;
        private static final int DIRECTION_RIGHT_TO_LEFT = 1;
        private static final int DIRECTION_TOP_TO_BOTTOM = 2;
        private static final int HORIZONTAL_SIZE = 209;
        private static final int JUSTIFICATION_CENTER = 2;
        private static final int JUSTIFICATION_FULL = 3;
        private static final int JUSTIFICATION_LEFT = 0;
        private static final int JUSTIFICATION_RIGHT = 1;
        private static final int MAXIMUM_ROW_COUNT = 15;
        private static final int PEN_FONT_STYLE_DEFAULT = 0;
        private static final int PEN_FONT_STYLE_MONOSPACED_WITHOUT_SERIFS = 3;
        private static final int PEN_FONT_STYLE_MONOSPACED_WITH_SERIFS = 1;
        private static final int PEN_FONT_STYLE_PROPORTIONALLY_SPACED_WITHOUT_SERIFS = 4;
        private static final int PEN_FONT_STYLE_PROPORTIONALLY_SPACED_WITH_SERIFS = 2;
        private static final int PEN_OFFSET_NORMAL = 1;
        private static final int PEN_SIZE_STANDARD = 1;
        private static final int[] PEN_STYLE_BACKGROUND;
        private static final int[] PEN_STYLE_EDGE_TYPE = {0, 0, 0, 0, 0, 3, 3};
        private static final int[] PEN_STYLE_FONT_STYLE = {0, 1, 2, 3, 4, 3, 4};
        private static final int RELATIVE_CUE_SIZE = 99;
        private static final int VERTICAL_SIZE = 74;
        private static final int[] WINDOW_STYLE_FILL;
        private static final int[] WINDOW_STYLE_JUSTIFICATION = {0, 0, 0, 0, 0, 2, 0};
        private static final int[] WINDOW_STYLE_PRINT_DIRECTION = {0, 0, 0, 0, 0, 0, 2};
        private static final int[] WINDOW_STYLE_SCROLL_DIRECTION = {3, 3, 3, 3, 3, 3, 1};
        private static final boolean[] WINDOW_STYLE_WORD_WRAP = {false, false, false, true, true, true, false};

        static {
            int i = COLOR_SOLID_BLACK;
            int i2 = COLOR_TRANSPARENT;
            WINDOW_STYLE_FILL = new int[]{i, i2, i, i, i2, i, i};
            PEN_STYLE_BACKGROUND = new int[]{i, i, i, i, i, i2, i2};
        }

        private final SpannableStringBuilder captionStringBuilder = new SpannableStringBuilder();
        private final List<SpannableString> rolledUpCaptions = new ArrayList();
        private int anchorId;
        private int backgroundColor;
        private int backgroundColorStartPosition;
        private boolean defined;
        private int foregroundColor;
        private int foregroundColorStartPosition;
        private int horizontalAnchor;
        private int italicsStartPosition;
        private int justification;
        private int penStyleId;
        private int priority;
        private boolean relativePositioning;
        private int row;
        private int rowCount;
        private boolean rowLock;
        private int underlineStartPosition;
        private int verticalAnchor;
        private boolean visible;
        private int windowFillColor;
        private int windowStyleId;

        public CueBuilder() {
            reset();
        }

        public static int getArgbColorFromCeaColor(int red, int green, int blue) {
            return getArgbColorFromCeaColor(red, green, blue, 0);
        }

        public static int getArgbColorFromCeaColor(int red, int green, int blue, int opacity) {
            int alpha;
            int i = 0;
            Assertions.checkIndex(red, 0, 4);
            Assertions.checkIndex(green, 0, 4);
            Assertions.checkIndex(blue, 0, 4);
            Assertions.checkIndex(opacity, 0, 4);
            if (opacity == 0 || opacity == 1) {
                alpha = 255;
            } else if (opacity == 2) {
                alpha = ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE;
            } else if (opacity != 3) {
                alpha = 255;
            } else {
                alpha = 0;
            }
            int i2 = red > 1 ? 255 : 0;
            int i3 = green > 1 ? 255 : 0;
            if (blue > 1) {
                i = 255;
            }
            return Color.argb(alpha, i2, i3, i);
        }

        public boolean isEmpty() {
            return !isDefined() || (this.rolledUpCaptions.isEmpty() && this.captionStringBuilder.length() == 0);
        }

        public void reset() {
            clear();
            this.defined = false;
            this.visible = false;
            this.priority = 4;
            this.relativePositioning = false;
            this.verticalAnchor = 0;
            this.horizontalAnchor = 0;
            this.anchorId = 0;
            this.rowCount = 15;
            this.rowLock = true;
            this.justification = 0;
            this.windowStyleId = 0;
            this.penStyleId = 0;
            int i = COLOR_SOLID_BLACK;
            this.windowFillColor = i;
            this.foregroundColor = COLOR_SOLID_WHITE;
            this.backgroundColor = i;
        }

        public void clear() {
            this.rolledUpCaptions.clear();
            this.captionStringBuilder.clear();
            this.italicsStartPosition = -1;
            this.underlineStartPosition = -1;
            this.foregroundColorStartPosition = -1;
            this.backgroundColorStartPosition = -1;
            this.row = 0;
        }

        public boolean isDefined() {
            return this.defined;
        }

        public void setVisibility(boolean visible2) {
            this.visible = visible2;
        }

        public boolean isVisible() {
            return this.visible;
        }

        public void defineWindow(boolean visible2, boolean rowLock2, boolean columnLock, int priority2, boolean relativePositioning2, int verticalAnchor2, int horizontalAnchor2, int rowCount2, int columnCount, int anchorId2, int windowStyleId2, int penStyleId2) {
            boolean z = rowLock2;
            int i = windowStyleId2;
            int i2 = penStyleId2;
            this.defined = true;
            this.visible = visible2;
            this.rowLock = z;
            this.priority = priority2;
            this.relativePositioning = relativePositioning2;
            this.verticalAnchor = verticalAnchor2;
            this.horizontalAnchor = horizontalAnchor2;
            this.anchorId = anchorId2;
            if (this.rowCount != rowCount2 + 1) {
                this.rowCount = rowCount2 + 1;
                while (true) {
                    if ((!z || this.rolledUpCaptions.size() < this.rowCount) && this.rolledUpCaptions.size() < 15) {
                        break;
                    }
                    this.rolledUpCaptions.remove(0);
                }
            }
            if (!(i == 0 || this.windowStyleId == i)) {
                this.windowStyleId = i;
                int windowStyleIdIndex = i - 1;
                setWindowAttributes(WINDOW_STYLE_FILL[windowStyleIdIndex], COLOR_TRANSPARENT, WINDOW_STYLE_WORD_WRAP[windowStyleIdIndex], 0, WINDOW_STYLE_PRINT_DIRECTION[windowStyleIdIndex], WINDOW_STYLE_SCROLL_DIRECTION[windowStyleIdIndex], WINDOW_STYLE_JUSTIFICATION[windowStyleIdIndex]);
            }
            if (i2 != 0 && this.penStyleId != i2) {
                this.penStyleId = i2;
                int penStyleIdIndex = i2 - 1;
                setPenAttributes(0, 1, 1, false, false, PEN_STYLE_EDGE_TYPE[penStyleIdIndex], PEN_STYLE_FONT_STYLE[penStyleIdIndex]);
                setPenColor(COLOR_SOLID_WHITE, PEN_STYLE_BACKGROUND[penStyleIdIndex], COLOR_SOLID_BLACK);
            }
        }

        public void setWindowAttributes(int fillColor, int borderColor, boolean wordWrapToggle, int borderType, int printDirection, int scrollDirection, int justification2) {
            this.windowFillColor = fillColor;
            this.justification = justification2;
        }

        public void setPenAttributes(int textTag, int offset, int penSize, boolean italicsToggle, boolean underlineToggle, int edgeType, int fontStyle) {
            if (this.italicsStartPosition != -1) {
                if (!italicsToggle) {
                    this.captionStringBuilder.setSpan(new StyleSpan(2), this.italicsStartPosition, this.captionStringBuilder.length(), 33);
                    this.italicsStartPosition = -1;
                }
            } else if (italicsToggle) {
                this.italicsStartPosition = this.captionStringBuilder.length();
            }
            if (this.underlineStartPosition != -1) {
                if (!underlineToggle) {
                    this.captionStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, this.captionStringBuilder.length(), 33);
                    this.underlineStartPosition = -1;
                }
            } else if (underlineToggle) {
                this.underlineStartPosition = this.captionStringBuilder.length();
            }
        }

        public void setPenColor(int foregroundColor2, int backgroundColor2, int edgeColor) {
            int i;
            int i2;
            if (!(this.foregroundColorStartPosition == -1 || (i2 = this.foregroundColor) == foregroundColor2)) {
                this.captionStringBuilder.setSpan(new ForegroundColorSpan(i2), this.foregroundColorStartPosition, this.captionStringBuilder.length(), 33);
            }
            if (foregroundColor2 != COLOR_SOLID_WHITE) {
                this.foregroundColorStartPosition = this.captionStringBuilder.length();
                this.foregroundColor = foregroundColor2;
            }
            if (!(this.backgroundColorStartPosition == -1 || (i = this.backgroundColor) == backgroundColor2)) {
                this.captionStringBuilder.setSpan(new BackgroundColorSpan(i), this.backgroundColorStartPosition, this.captionStringBuilder.length(), 33);
            }
            if (backgroundColor2 != COLOR_SOLID_BLACK) {
                this.backgroundColorStartPosition = this.captionStringBuilder.length();
                this.backgroundColor = backgroundColor2;
            }
        }

        public void setPenLocation(int row2, int column) {
            if (this.row != row2) {
                append(10);
            }
            this.row = row2;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.text.SpannableStringBuilder.delete(int, int):android.text.SpannableStringBuilder}
         arg types: [int, int]
         candidates:
          ClspMth{android.text.SpannableStringBuilder.delete(int, int):android.text.Editable}
          ClspMth{android.text.SpannableStringBuilder.delete(int, int):android.text.SpannableStringBuilder} */
        public void backspace() {
            int length = this.captionStringBuilder.length();
            if (length > 0) {
                this.captionStringBuilder.delete(length - 1, length);
            }
        }

        public void append(char text) {
            if (text == 10) {
                this.rolledUpCaptions.add(buildSpannableString());
                this.captionStringBuilder.clear();
                if (this.italicsStartPosition != -1) {
                    this.italicsStartPosition = 0;
                }
                if (this.underlineStartPosition != -1) {
                    this.underlineStartPosition = 0;
                }
                if (this.foregroundColorStartPosition != -1) {
                    this.foregroundColorStartPosition = 0;
                }
                if (this.backgroundColorStartPosition != -1) {
                    this.backgroundColorStartPosition = 0;
                }
                while (true) {
                    if ((this.rowLock && this.rolledUpCaptions.size() >= this.rowCount) || this.rolledUpCaptions.size() >= 15) {
                        this.rolledUpCaptions.remove(0);
                    } else {
                        return;
                    }
                }
            } else {
                this.captionStringBuilder.append(text);
            }
        }

        public SpannableString buildSpannableString() {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.captionStringBuilder);
            int length = spannableStringBuilder.length();
            if (length > 0) {
                if (this.italicsStartPosition != -1) {
                    spannableStringBuilder.setSpan(new StyleSpan(2), this.italicsStartPosition, length, 33);
                }
                if (this.underlineStartPosition != -1) {
                    spannableStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, length, 33);
                }
                if (this.foregroundColorStartPosition != -1) {
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(this.foregroundColor), this.foregroundColorStartPosition, length, 33);
                }
                if (this.backgroundColorStartPosition != -1) {
                    spannableStringBuilder.setSpan(new BackgroundColorSpan(this.backgroundColor), this.backgroundColorStartPosition, length, 33);
                }
            }
            return new SpannableString(spannableStringBuilder);
        }

        /* JADX WARNING: Removed duplicated region for block: B:20:0x0068  */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x0073  */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x0094  */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x0098  */
        /* JADX WARNING: Removed duplicated region for block: B:31:0x00a8  */
        /* JADX WARNING: Removed duplicated region for block: B:32:0x00ac  */
        /* JADX WARNING: Removed duplicated region for block: B:38:0x00bc  */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x00be  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.google.android.exoplayer2.text.cea.Cea708Cue build() {
            /*
                r20 = this;
                r0 = r20
                boolean r1 = r20.isEmpty()
                if (r1 == 0) goto L_0x000a
                r1 = 0
                return r1
            L_0x000a:
                android.text.SpannableStringBuilder r1 = new android.text.SpannableStringBuilder
                r1.<init>()
                r2 = 0
            L_0x0010:
                java.util.List<android.text.SpannableString> r3 = r0.rolledUpCaptions
                int r3 = r3.size()
                if (r2 >= r3) goto L_0x002b
                java.util.List<android.text.SpannableString> r3 = r0.rolledUpCaptions
                java.lang.Object r3 = r3.get(r2)
                java.lang.CharSequence r3 = (java.lang.CharSequence) r3
                r1.append(r3)
                r3 = 10
                r1.append(r3)
                int r2 = r2 + 1
                goto L_0x0010
            L_0x002b:
                android.text.SpannableString r2 = r20.buildSpannableString()
                r1.append(r2)
                int r2 = r0.justification
                r3 = 3
                r4 = 1
                if (r2 == 0) goto L_0x0061
                if (r2 == r4) goto L_0x005d
                r5 = 2
                if (r2 == r5) goto L_0x0059
                if (r2 != r3) goto L_0x0040
                goto L_0x0061
            L_0x0040:
                java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
                r4 = 43
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>(r4)
                java.lang.String r4 = "Unexpected justification value: "
                r5.append(r4)
                r5.append(r2)
                java.lang.String r2 = r5.toString()
                r3.<init>(r2)
                throw r3
            L_0x0059:
                android.text.Layout$Alignment r2 = android.text.Layout.Alignment.ALIGN_CENTER
                r14 = r2
                goto L_0x0064
            L_0x005d:
                android.text.Layout$Alignment r2 = android.text.Layout.Alignment.ALIGN_OPPOSITE
                r14 = r2
                goto L_0x0064
            L_0x0061:
                android.text.Layout$Alignment r2 = android.text.Layout.Alignment.ALIGN_NORMAL
                r14 = r2
            L_0x0064:
                boolean r2 = r0.relativePositioning
                if (r2 == 0) goto L_0x0073
                int r2 = r0.horizontalAnchor
                float r2 = (float) r2
                r5 = 1120272384(0x42c60000, float:99.0)
                float r2 = r2 / r5
                int r6 = r0.verticalAnchor
                float r6 = (float) r6
                float r6 = r6 / r5
                goto L_0x0080
            L_0x0073:
                int r2 = r0.horizontalAnchor
                float r2 = (float) r2
                r5 = 1129381888(0x43510000, float:209.0)
                float r2 = r2 / r5
                int r5 = r0.verticalAnchor
                float r5 = (float) r5
                r6 = 1116995584(0x42940000, float:74.0)
                float r6 = r5 / r6
            L_0x0080:
                r5 = 1063675494(0x3f666666, float:0.9)
                float r7 = r2 * r5
                r8 = 1028443341(0x3d4ccccd, float:0.05)
                float r15 = r7 + r8
                float r5 = r5 * r6
                float r16 = r5 + r8
                int r2 = r0.anchorId
                int r5 = r2 % 3
                if (r5 != 0) goto L_0x0098
                r2 = 0
                r17 = r2
                goto L_0x00a2
            L_0x0098:
                int r2 = r2 % r3
                if (r2 != r4) goto L_0x009f
                r2 = 1
                r17 = r2
                goto L_0x00a2
            L_0x009f:
                r2 = 2
                r17 = r2
            L_0x00a2:
                int r2 = r0.anchorId
                int r5 = r2 / 3
                if (r5 != 0) goto L_0x00ac
                r2 = 0
                r18 = r2
                goto L_0x00b6
            L_0x00ac:
                int r2 = r2 / r3
                if (r2 != r4) goto L_0x00b3
                r2 = 1
                r18 = r2
                goto L_0x00b6
            L_0x00b3:
                r2 = 2
                r18 = r2
            L_0x00b6:
                int r2 = r0.windowFillColor
                int r3 = com.google.android.exoplayer2.text.cea.Cea708Decoder.CueBuilder.COLOR_SOLID_BLACK
                if (r2 == r3) goto L_0x00be
                r11 = 1
                goto L_0x00c0
            L_0x00be:
                r2 = 0
                r11 = 0
            L_0x00c0:
                com.google.android.exoplayer2.text.cea.Cea708Cue r19 = new com.google.android.exoplayer2.text.cea.Cea708Cue
                r6 = 0
                r10 = 1
                int r12 = r0.windowFillColor
                int r13 = r0.priority
                r2 = r19
                r3 = r1
                r4 = r14
                r5 = r16
                r7 = r17
                r8 = r15
                r9 = r18
                r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
                return r19
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.cea.Cea708Decoder.CueBuilder.build():com.google.android.exoplayer2.text.cea.Cea708Cue");
        }
    }
}
