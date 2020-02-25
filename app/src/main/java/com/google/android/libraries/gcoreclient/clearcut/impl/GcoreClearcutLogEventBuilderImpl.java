package com.google.android.libraries.gcoreclient.clearcut.impl;

import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.phenotype.ExperimentTokens;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogEventBuilder;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutMessageProducer;
import com.google.android.libraries.gcoreclient.common.api.GcorePendingResult;
import com.google.android.libraries.gcoreclient.phenotype.GcoreExperimentTokens;

public final class GcoreClearcutLogEventBuilderImpl extends BaseClearcutLogEventBuilderImpl {
    GcoreClearcutLogEventBuilderImpl(ClearcutLogger clearcutLogger, byte[] protoExtension) {
        super(clearcutLogger, protoExtension);
    }

    GcoreClearcutLogEventBuilderImpl(ClearcutLogger clearcutLogger, ClearcutLogger.MessageProducer extensionProducer) {
        super(clearcutLogger, extensionProducer);
    }

    private static ExperimentTokens newExperimentTokens(GcoreExperimentTokens experimentTokens) {
        return new ExperimentTokens(experimentTokens.getUser(), experimentTokens.getDirectExperimentToken(), experimentTokens.getGaiaCrossExperimentTokens(), experimentTokens.getPseudonymousCrossExperimentTokens(), experimentTokens.getAlwaysCrossExperimentTokens(), experimentTokens.getOtherCrossExperimentTokens(), experimentTokens.getWeakExperimentIds(), experimentTokens.getAdditionalDirectExperimentTokens());
    }

    public /* bridge */ /* synthetic */ GcorePendingResult logAsync() {
        return super.logAsync();
    }

    public /* bridge */ /* synthetic */ GcoreClearcutLogEventBuilder setEventCode(int i) {
        return super.setEventCode(i);
    }

    public /* bridge */ /* synthetic */ GcoreClearcutLogEventBuilder setEventFlowId(int i) {
        return super.setEventFlowId(i);
    }

    public /* bridge */ /* synthetic */ GcoreClearcutLogEventBuilder setLogSourceName(String str) {
        return super.setLogSourceName(str);
    }

    public /* bridge */ /* synthetic */ GcoreClearcutLogEventBuilder setUploadAccountName(String str) {
        return super.setUploadAccountName(str);
    }

    public /* bridge */ /* synthetic */ GcoreClearcutLogEventBuilder setZwiebackCookieOverride(String str) {
        return super.setZwiebackCookieOverride(str);
    }

    public GcoreClearcutLogEventBuilder setQosTier(int qosTier) {
        this.logEventBuilder.setQosTier(qosTier);
        return this;
    }

    public GcoreClearcutLogEventBuilder setClientVisualElements(byte[] clientVisualElements) {
        this.logEventBuilder.setClientVisualElements(clientVisualElements);
        return this;
    }

    public GcoreClearcutLogEventBuilder setClientVisualElementsProducer(final GcoreClearcutMessageProducer clientVisualElementsProducer) {
        this.logEventBuilder.setClientVisualElementsProducer(new ClearcutLogger.MessageProducer(this) {
            public byte[] toProtoBytes() {
                return clientVisualElementsProducer.toProtoBytes();
            }
        });
        return this;
    }

    public GcoreClearcutLogEventBuilder addTestCode(int testCode) {
        this.logEventBuilder.addTestCode(testCode);
        return this;
    }

    public GcoreClearcutLogEventBuilder addMendelPackage(String mendelPackage) {
        this.logEventBuilder.addMendelPackage(mendelPackage);
        return this;
    }

    public GcoreClearcutLogEventBuilder addExperimentIds(int[] experimentIds) {
        this.logEventBuilder.addExperimentIds(experimentIds);
        return this;
    }

    public GcoreClearcutLogEventBuilder addExperimentToken(byte[] experimentToken) {
        this.logEventBuilder.addExperimentToken(experimentToken);
        return this;
    }

    public GcoreClearcutLogEventBuilder addExperimentTokens(GcoreExperimentTokens experimentTokens) {
        this.logEventBuilder.addExperimentTokens(newExperimentTokens(experimentTokens));
        return this;
    }

    public GcoreClearcutLogEventBuilder addExperimentTokenAndSkipPhenotype(byte[] experimentToken) {
        this.logEventBuilder.addExperimentTokenAndSkipPhenotype(experimentToken);
        return this;
    }

    public GcoreClearcutLogEventBuilder addExperimentTokensAndSkipPhenotype(GcoreExperimentTokens experimentTokens) {
        this.logEventBuilder.addExperimentTokensAndSkipPhenotype(newExperimentTokens(experimentTokens));
        return this;
    }
}
