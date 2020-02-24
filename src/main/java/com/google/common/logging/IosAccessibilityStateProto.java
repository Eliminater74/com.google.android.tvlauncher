package com.google.common.logging;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.FieldType;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtoField;
import com.google.protobuf.ProtoMessage;
import com.google.protobuf.ProtoPresenceBits;
import com.google.protobuf.ProtoPresenceCheckedField;
import com.google.protobuf.ProtoSyntax;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class IosAccessibilityStateProto {

    public interface IosAccessibilityStateOrBuilder extends MessageLiteOrBuilder {
        boolean getAssistiveTouchRunning();

        boolean getBoldTextEnabled();

        boolean getClosedCaptioningEnabled();

        boolean getDarkerSystemColorsEnabled();

        boolean getGrayscaleEnabled();

        boolean getGuidedAccessEnabled();

        boolean getInvertColorsEnabled();

        boolean getMonoAudioEnabled();

        boolean getReduceMotionEnabled();

        boolean getReduceTransparencyEnabled();

        boolean getShakeToUndoEnabled();

        boolean getSpeakScreenEnabled();

        boolean getSpeakSelectionEnabled();

        boolean getSwitchControlRunning();

        boolean getVoiceOverRunning();

        boolean hasAssistiveTouchRunning();

        boolean hasBoldTextEnabled();

        boolean hasClosedCaptioningEnabled();

        boolean hasDarkerSystemColorsEnabled();

        boolean hasGrayscaleEnabled();

        boolean hasGuidedAccessEnabled();

        boolean hasInvertColorsEnabled();

        boolean hasMonoAudioEnabled();

        boolean hasReduceMotionEnabled();

        boolean hasReduceTransparencyEnabled();

        boolean hasShakeToUndoEnabled();

        boolean hasSpeakScreenEnabled();

        boolean hasSpeakSelectionEnabled();

        boolean hasSwitchControlRunning();

        boolean hasVoiceOverRunning();
    }

    private IosAccessibilityStateProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class IosAccessibilityState extends GeneratedMessageLite<IosAccessibilityState, Builder> implements IosAccessibilityStateOrBuilder {
        public static final int ASSISTIVE_TOUCH_RUNNING_FIELD_NUMBER = 1;
        public static final int BOLD_TEXT_ENABLED_FIELD_NUMBER = 6;
        public static final int CLOSED_CAPTIONING_ENABLED_FIELD_NUMBER = 5;
        public static final int DARKER_SYSTEM_COLORS_ENABLED_FIELD_NUMBER = 7;
        /* access modifiers changed from: private */
        public static final IosAccessibilityState DEFAULT_INSTANCE = new IosAccessibilityState();
        public static final int GRAYSCALE_ENABLED_FIELD_NUMBER = 8;
        public static final int GUIDED_ACCESS_ENABLED_FIELD_NUMBER = 9;
        public static final int INVERT_COLORS_ENABLED_FIELD_NUMBER = 10;
        public static final int MONO_AUDIO_ENABLED_FIELD_NUMBER = 11;
        private static volatile Parser<IosAccessibilityState> PARSER = null;
        public static final int REDUCE_MOTION_ENABLED_FIELD_NUMBER = 12;
        public static final int REDUCE_TRANSPARENCY_ENABLED_FIELD_NUMBER = 13;
        public static final int SHAKE_TO_UNDO_ENABLED_FIELD_NUMBER = 4;
        public static final int SPEAK_SCREEN_ENABLED_FIELD_NUMBER = 14;
        public static final int SPEAK_SELECTION_ENABLED_FIELD_NUMBER = 15;
        public static final int SWITCH_CONTROL_RUNNING_FIELD_NUMBER = 3;
        public static final int VOICE_OVER_RUNNING_FIELD_NUMBER = 2;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean assistiveTouchRunning_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private boolean boldTextEnabled_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private boolean closedCaptioningEnabled_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private boolean darkerSystemColorsEnabled_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private boolean grayscaleEnabled_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private boolean guidedAccessEnabled_;
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private boolean invertColorsEnabled_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private boolean monoAudioEnabled_;
        @ProtoField(fieldNumber = 12, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2048, presenceBitsId = 0)
        private boolean reduceMotionEnabled_;
        @ProtoField(fieldNumber = 13, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4096, presenceBitsId = 0)
        private boolean reduceTransparencyEnabled_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private boolean shakeToUndoEnabled_;
        @ProtoField(fieldNumber = 14, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 8192, presenceBitsId = 0)
        private boolean speakScreenEnabled_;
        @ProtoField(fieldNumber = 15, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 16384, presenceBitsId = 0)
        private boolean speakSelectionEnabled_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean switchControlRunning_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private boolean voiceOverRunning_;

        private IosAccessibilityState() {
        }

        public boolean hasAssistiveTouchRunning() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getAssistiveTouchRunning() {
            return this.assistiveTouchRunning_;
        }

        /* access modifiers changed from: private */
        public void setAssistiveTouchRunning(boolean value) {
            this.bitField0_ |= 1;
            this.assistiveTouchRunning_ = value;
        }

        /* access modifiers changed from: private */
        public void clearAssistiveTouchRunning() {
            this.bitField0_ &= -2;
            this.assistiveTouchRunning_ = false;
        }

        public boolean hasVoiceOverRunning() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getVoiceOverRunning() {
            return this.voiceOverRunning_;
        }

        /* access modifiers changed from: private */
        public void setVoiceOverRunning(boolean value) {
            this.bitField0_ |= 2;
            this.voiceOverRunning_ = value;
        }

        /* access modifiers changed from: private */
        public void clearVoiceOverRunning() {
            this.bitField0_ &= -3;
            this.voiceOverRunning_ = false;
        }

        public boolean hasSwitchControlRunning() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getSwitchControlRunning() {
            return this.switchControlRunning_;
        }

        /* access modifiers changed from: private */
        public void setSwitchControlRunning(boolean value) {
            this.bitField0_ |= 4;
            this.switchControlRunning_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSwitchControlRunning() {
            this.bitField0_ &= -5;
            this.switchControlRunning_ = false;
        }

        public boolean hasShakeToUndoEnabled() {
            return (this.bitField0_ & 8) != 0;
        }

        public boolean getShakeToUndoEnabled() {
            return this.shakeToUndoEnabled_;
        }

        /* access modifiers changed from: private */
        public void setShakeToUndoEnabled(boolean value) {
            this.bitField0_ |= 8;
            this.shakeToUndoEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearShakeToUndoEnabled() {
            this.bitField0_ &= -9;
            this.shakeToUndoEnabled_ = false;
        }

        public boolean hasClosedCaptioningEnabled() {
            return (this.bitField0_ & 16) != 0;
        }

        public boolean getClosedCaptioningEnabled() {
            return this.closedCaptioningEnabled_;
        }

        /* access modifiers changed from: private */
        public void setClosedCaptioningEnabled(boolean value) {
            this.bitField0_ |= 16;
            this.closedCaptioningEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearClosedCaptioningEnabled() {
            this.bitField0_ &= -17;
            this.closedCaptioningEnabled_ = false;
        }

        public boolean hasBoldTextEnabled() {
            return (this.bitField0_ & 32) != 0;
        }

        public boolean getBoldTextEnabled() {
            return this.boldTextEnabled_;
        }

        /* access modifiers changed from: private */
        public void setBoldTextEnabled(boolean value) {
            this.bitField0_ |= 32;
            this.boldTextEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBoldTextEnabled() {
            this.bitField0_ &= -33;
            this.boldTextEnabled_ = false;
        }

        public boolean hasDarkerSystemColorsEnabled() {
            return (this.bitField0_ & 64) != 0;
        }

        public boolean getDarkerSystemColorsEnabled() {
            return this.darkerSystemColorsEnabled_;
        }

        /* access modifiers changed from: private */
        public void setDarkerSystemColorsEnabled(boolean value) {
            this.bitField0_ |= 64;
            this.darkerSystemColorsEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDarkerSystemColorsEnabled() {
            this.bitField0_ &= -65;
            this.darkerSystemColorsEnabled_ = false;
        }

        public boolean hasGrayscaleEnabled() {
            return (this.bitField0_ & 128) != 0;
        }

        public boolean getGrayscaleEnabled() {
            return this.grayscaleEnabled_;
        }

        /* access modifiers changed from: private */
        public void setGrayscaleEnabled(boolean value) {
            this.bitField0_ |= 128;
            this.grayscaleEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearGrayscaleEnabled() {
            this.bitField0_ &= -129;
            this.grayscaleEnabled_ = false;
        }

        public boolean hasGuidedAccessEnabled() {
            return (this.bitField0_ & 256) != 0;
        }

        public boolean getGuidedAccessEnabled() {
            return this.guidedAccessEnabled_;
        }

        /* access modifiers changed from: private */
        public void setGuidedAccessEnabled(boolean value) {
            this.bitField0_ |= 256;
            this.guidedAccessEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearGuidedAccessEnabled() {
            this.bitField0_ &= -257;
            this.guidedAccessEnabled_ = false;
        }

        public boolean hasInvertColorsEnabled() {
            return (this.bitField0_ & 512) != 0;
        }

        public boolean getInvertColorsEnabled() {
            return this.invertColorsEnabled_;
        }

        /* access modifiers changed from: private */
        public void setInvertColorsEnabled(boolean value) {
            this.bitField0_ |= 512;
            this.invertColorsEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearInvertColorsEnabled() {
            this.bitField0_ &= -513;
            this.invertColorsEnabled_ = false;
        }

        public boolean hasMonoAudioEnabled() {
            return (this.bitField0_ & 1024) != 0;
        }

        public boolean getMonoAudioEnabled() {
            return this.monoAudioEnabled_;
        }

        /* access modifiers changed from: private */
        public void setMonoAudioEnabled(boolean value) {
            this.bitField0_ |= 1024;
            this.monoAudioEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMonoAudioEnabled() {
            this.bitField0_ &= -1025;
            this.monoAudioEnabled_ = false;
        }

        public boolean hasReduceMotionEnabled() {
            return (this.bitField0_ & 2048) != 0;
        }

        public boolean getReduceMotionEnabled() {
            return this.reduceMotionEnabled_;
        }

        /* access modifiers changed from: private */
        public void setReduceMotionEnabled(boolean value) {
            this.bitField0_ |= 2048;
            this.reduceMotionEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearReduceMotionEnabled() {
            this.bitField0_ &= -2049;
            this.reduceMotionEnabled_ = false;
        }

        public boolean hasReduceTransparencyEnabled() {
            return (this.bitField0_ & 4096) != 0;
        }

        public boolean getReduceTransparencyEnabled() {
            return this.reduceTransparencyEnabled_;
        }

        /* access modifiers changed from: private */
        public void setReduceTransparencyEnabled(boolean value) {
            this.bitField0_ |= 4096;
            this.reduceTransparencyEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearReduceTransparencyEnabled() {
            this.bitField0_ &= -4097;
            this.reduceTransparencyEnabled_ = false;
        }

        public boolean hasSpeakScreenEnabled() {
            return (this.bitField0_ & 8192) != 0;
        }

        public boolean getSpeakScreenEnabled() {
            return this.speakScreenEnabled_;
        }

        /* access modifiers changed from: private */
        public void setSpeakScreenEnabled(boolean value) {
            this.bitField0_ |= 8192;
            this.speakScreenEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSpeakScreenEnabled() {
            this.bitField0_ &= -8193;
            this.speakScreenEnabled_ = false;
        }

        public boolean hasSpeakSelectionEnabled() {
            return (this.bitField0_ & 16384) != 0;
        }

        public boolean getSpeakSelectionEnabled() {
            return this.speakSelectionEnabled_;
        }

        /* access modifiers changed from: private */
        public void setSpeakSelectionEnabled(boolean value) {
            this.bitField0_ |= 16384;
            this.speakSelectionEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSpeakSelectionEnabled() {
            this.bitField0_ &= -16385;
            this.speakSelectionEnabled_ = false;
        }

        public static IosAccessibilityState parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (IosAccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static IosAccessibilityState parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (IosAccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static IosAccessibilityState parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (IosAccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static IosAccessibilityState parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (IosAccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static IosAccessibilityState parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (IosAccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static IosAccessibilityState parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (IosAccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static IosAccessibilityState parseFrom(InputStream input) throws IOException {
            return (IosAccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static IosAccessibilityState parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (IosAccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static IosAccessibilityState parseDelimitedFrom(InputStream input) throws IOException {
            return (IosAccessibilityState) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static IosAccessibilityState parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (IosAccessibilityState) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static IosAccessibilityState parseFrom(CodedInputStream input) throws IOException {
            return (IosAccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static IosAccessibilityState parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (IosAccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(IosAccessibilityState prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<IosAccessibilityState, Builder> implements IosAccessibilityStateOrBuilder {
            private Builder() {
                super(IosAccessibilityState.DEFAULT_INSTANCE);
            }

            public boolean hasAssistiveTouchRunning() {
                return ((IosAccessibilityState) this.instance).hasAssistiveTouchRunning();
            }

            public boolean getAssistiveTouchRunning() {
                return ((IosAccessibilityState) this.instance).getAssistiveTouchRunning();
            }

            public Builder setAssistiveTouchRunning(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setAssistiveTouchRunning(value);
                return this;
            }

            public Builder clearAssistiveTouchRunning() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearAssistiveTouchRunning();
                return this;
            }

            public boolean hasVoiceOverRunning() {
                return ((IosAccessibilityState) this.instance).hasVoiceOverRunning();
            }

            public boolean getVoiceOverRunning() {
                return ((IosAccessibilityState) this.instance).getVoiceOverRunning();
            }

            public Builder setVoiceOverRunning(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setVoiceOverRunning(value);
                return this;
            }

            public Builder clearVoiceOverRunning() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearVoiceOverRunning();
                return this;
            }

            public boolean hasSwitchControlRunning() {
                return ((IosAccessibilityState) this.instance).hasSwitchControlRunning();
            }

            public boolean getSwitchControlRunning() {
                return ((IosAccessibilityState) this.instance).getSwitchControlRunning();
            }

            public Builder setSwitchControlRunning(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setSwitchControlRunning(value);
                return this;
            }

            public Builder clearSwitchControlRunning() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearSwitchControlRunning();
                return this;
            }

            public boolean hasShakeToUndoEnabled() {
                return ((IosAccessibilityState) this.instance).hasShakeToUndoEnabled();
            }

            public boolean getShakeToUndoEnabled() {
                return ((IosAccessibilityState) this.instance).getShakeToUndoEnabled();
            }

            public Builder setShakeToUndoEnabled(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setShakeToUndoEnabled(value);
                return this;
            }

            public Builder clearShakeToUndoEnabled() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearShakeToUndoEnabled();
                return this;
            }

            public boolean hasClosedCaptioningEnabled() {
                return ((IosAccessibilityState) this.instance).hasClosedCaptioningEnabled();
            }

            public boolean getClosedCaptioningEnabled() {
                return ((IosAccessibilityState) this.instance).getClosedCaptioningEnabled();
            }

            public Builder setClosedCaptioningEnabled(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setClosedCaptioningEnabled(value);
                return this;
            }

            public Builder clearClosedCaptioningEnabled() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearClosedCaptioningEnabled();
                return this;
            }

            public boolean hasBoldTextEnabled() {
                return ((IosAccessibilityState) this.instance).hasBoldTextEnabled();
            }

            public boolean getBoldTextEnabled() {
                return ((IosAccessibilityState) this.instance).getBoldTextEnabled();
            }

            public Builder setBoldTextEnabled(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setBoldTextEnabled(value);
                return this;
            }

            public Builder clearBoldTextEnabled() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearBoldTextEnabled();
                return this;
            }

            public boolean hasDarkerSystemColorsEnabled() {
                return ((IosAccessibilityState) this.instance).hasDarkerSystemColorsEnabled();
            }

            public boolean getDarkerSystemColorsEnabled() {
                return ((IosAccessibilityState) this.instance).getDarkerSystemColorsEnabled();
            }

            public Builder setDarkerSystemColorsEnabled(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setDarkerSystemColorsEnabled(value);
                return this;
            }

            public Builder clearDarkerSystemColorsEnabled() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearDarkerSystemColorsEnabled();
                return this;
            }

            public boolean hasGrayscaleEnabled() {
                return ((IosAccessibilityState) this.instance).hasGrayscaleEnabled();
            }

            public boolean getGrayscaleEnabled() {
                return ((IosAccessibilityState) this.instance).getGrayscaleEnabled();
            }

            public Builder setGrayscaleEnabled(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setGrayscaleEnabled(value);
                return this;
            }

            public Builder clearGrayscaleEnabled() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearGrayscaleEnabled();
                return this;
            }

            public boolean hasGuidedAccessEnabled() {
                return ((IosAccessibilityState) this.instance).hasGuidedAccessEnabled();
            }

            public boolean getGuidedAccessEnabled() {
                return ((IosAccessibilityState) this.instance).getGuidedAccessEnabled();
            }

            public Builder setGuidedAccessEnabled(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setGuidedAccessEnabled(value);
                return this;
            }

            public Builder clearGuidedAccessEnabled() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearGuidedAccessEnabled();
                return this;
            }

            public boolean hasInvertColorsEnabled() {
                return ((IosAccessibilityState) this.instance).hasInvertColorsEnabled();
            }

            public boolean getInvertColorsEnabled() {
                return ((IosAccessibilityState) this.instance).getInvertColorsEnabled();
            }

            public Builder setInvertColorsEnabled(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setInvertColorsEnabled(value);
                return this;
            }

            public Builder clearInvertColorsEnabled() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearInvertColorsEnabled();
                return this;
            }

            public boolean hasMonoAudioEnabled() {
                return ((IosAccessibilityState) this.instance).hasMonoAudioEnabled();
            }

            public boolean getMonoAudioEnabled() {
                return ((IosAccessibilityState) this.instance).getMonoAudioEnabled();
            }

            public Builder setMonoAudioEnabled(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setMonoAudioEnabled(value);
                return this;
            }

            public Builder clearMonoAudioEnabled() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearMonoAudioEnabled();
                return this;
            }

            public boolean hasReduceMotionEnabled() {
                return ((IosAccessibilityState) this.instance).hasReduceMotionEnabled();
            }

            public boolean getReduceMotionEnabled() {
                return ((IosAccessibilityState) this.instance).getReduceMotionEnabled();
            }

            public Builder setReduceMotionEnabled(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setReduceMotionEnabled(value);
                return this;
            }

            public Builder clearReduceMotionEnabled() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearReduceMotionEnabled();
                return this;
            }

            public boolean hasReduceTransparencyEnabled() {
                return ((IosAccessibilityState) this.instance).hasReduceTransparencyEnabled();
            }

            public boolean getReduceTransparencyEnabled() {
                return ((IosAccessibilityState) this.instance).getReduceTransparencyEnabled();
            }

            public Builder setReduceTransparencyEnabled(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setReduceTransparencyEnabled(value);
                return this;
            }

            public Builder clearReduceTransparencyEnabled() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearReduceTransparencyEnabled();
                return this;
            }

            public boolean hasSpeakScreenEnabled() {
                return ((IosAccessibilityState) this.instance).hasSpeakScreenEnabled();
            }

            public boolean getSpeakScreenEnabled() {
                return ((IosAccessibilityState) this.instance).getSpeakScreenEnabled();
            }

            public Builder setSpeakScreenEnabled(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setSpeakScreenEnabled(value);
                return this;
            }

            public Builder clearSpeakScreenEnabled() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearSpeakScreenEnabled();
                return this;
            }

            public boolean hasSpeakSelectionEnabled() {
                return ((IosAccessibilityState) this.instance).hasSpeakSelectionEnabled();
            }

            public boolean getSpeakSelectionEnabled() {
                return ((IosAccessibilityState) this.instance).getSpeakSelectionEnabled();
            }

            public Builder setSpeakSelectionEnabled(boolean value) {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).setSpeakSelectionEnabled(value);
                return this;
            }

            public Builder clearSpeakSelectionEnabled() {
                copyOnWrite();
                ((IosAccessibilityState) this.instance).clearSpeakSelectionEnabled();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new IosAccessibilityState();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u000f\u0000\u0001\u0001\u000f\u000f\u0000\u0000\u0000\u0001\u0007\u0000\u0002\u0007\u0001\u0003\u0007\u0002\u0004\u0007\u0003\u0005\u0007\u0004\u0006\u0007\u0005\u0007\u0007\u0006\b\u0007\u0007\t\u0007\b\n\u0007\t\u000b\u0007\n\f\u0007\u000b\r\u0007\f\u000e\u0007\r\u000f\u0007\u000e", new Object[]{"bitField0_", "assistiveTouchRunning_", "voiceOverRunning_", "switchControlRunning_", "shakeToUndoEnabled_", "closedCaptioningEnabled_", "boldTextEnabled_", "darkerSystemColorsEnabled_", "grayscaleEnabled_", "guidedAccessEnabled_", "invertColorsEnabled_", "monoAudioEnabled_", "reduceMotionEnabled_", "reduceTransparencyEnabled_", "speakScreenEnabled_", "speakSelectionEnabled_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<IosAccessibilityState> parser = PARSER;
                    if (parser == null) {
                        synchronized (IosAccessibilityState.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(IosAccessibilityState.class, DEFAULT_INSTANCE);
        }

        public static IosAccessibilityState getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<IosAccessibilityState> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
