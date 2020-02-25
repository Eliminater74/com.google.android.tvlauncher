package com.google.analytics.config.protoverifier.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

import java.util.List;

public interface RulesOrBuilder extends MessageLiteOrBuilder {
    String getCustomRule(int i);

    ByteString getCustomRuleBytes(int i);

    int getCustomRuleCount();

    List<String> getCustomRuleList();

    boolean getLanguageCode();

    String getMatchRegex();

    ByteString getMatchRegexBytes();

    double getMaxDouble();

    float getMaxFloat();

    long getMaxInt();

    int getMaxRepeatedLength();

    int getMaxStringLength();

    double getMinDouble();

    float getMinFloat();

    long getMinInt();

    int getMinRepeatedLength();

    int getMinStringLength();

    boolean getMustBeSet();

    String getNotMatchRegex();

    ByteString getNotMatchRegexBytes();

    boolean hasLanguageCode();

    boolean hasMatchRegex();

    boolean hasMaxDouble();

    boolean hasMaxFloat();

    boolean hasMaxInt();

    boolean hasMaxRepeatedLength();

    boolean hasMaxStringLength();

    boolean hasMinDouble();

    boolean hasMinFloat();

    boolean hasMinInt();

    boolean hasMinRepeatedLength();

    boolean hasMinStringLength();

    boolean hasMustBeSet();

    boolean hasNotMatchRegex();
}
