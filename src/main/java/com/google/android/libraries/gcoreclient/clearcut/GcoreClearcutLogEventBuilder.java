package com.google.android.libraries.gcoreclient.clearcut;

import com.google.android.libraries.gcoreclient.common.api.GcorePendingResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreStatus;
import com.google.android.libraries.gcoreclient.phenotype.GcoreExperimentTokens;
import javax.annotation.Nullable;

public interface GcoreClearcutLogEventBuilder {
    GcoreClearcutLogEventBuilder addExperimentIds(int[] iArr);

    @Deprecated
    GcoreClearcutLogEventBuilder addExperimentToken(byte[] bArr);

    @Deprecated
    GcoreClearcutLogEventBuilder addExperimentTokenAndSkipPhenotype(byte[] bArr);

    GcoreClearcutLogEventBuilder addExperimentTokens(GcoreExperimentTokens gcoreExperimentTokens);

    GcoreClearcutLogEventBuilder addExperimentTokensAndSkipPhenotype(GcoreExperimentTokens gcoreExperimentTokens);

    GcoreClearcutLogEventBuilder addMendelPackage(String str);

    GcoreClearcutLogEventBuilder addTestCode(int i);

    GcorePendingResult<GcoreStatus> logAsync();

    GcoreClearcutLogEventBuilder setClientVisualElements(byte[] bArr);

    GcoreClearcutLogEventBuilder setClientVisualElementsProducer(GcoreClearcutMessageProducer gcoreClearcutMessageProducer);

    GcoreClearcutLogEventBuilder setEventCode(int i);

    GcoreClearcutLogEventBuilder setEventFlowId(int i);

    GcoreClearcutLogEventBuilder setLogSourceName(String str);

    GcoreClearcutLogEventBuilder setQosTier(int i);

    GcoreClearcutLogEventBuilder setUploadAccountName(@Nullable String str);

    GcoreClearcutLogEventBuilder setZwiebackCookieOverride(@Nullable String str);
}
