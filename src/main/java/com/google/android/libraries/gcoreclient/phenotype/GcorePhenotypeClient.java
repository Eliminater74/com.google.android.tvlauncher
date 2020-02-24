package com.google.android.libraries.gcoreclient.phenotype;

import com.google.android.libraries.gcoreclient.tasks.GcoreTask;

public interface GcorePhenotypeClient {
    GcoreTask<Void> commitToConfiguration(String str);

    GcoreTask<GcoreConfigurations> getConfigurationSnapshot(String str, String str2);

    GcoreTask<GcoreConfigurations> getConfigurationSnapshot(String str, String str2, String str3);

    GcoreTask<GcoreExperimentTokens> getExperiments(String str);

    boolean isSupportedOnClient();

    GcoreTask<Void> register(String str, int i, String[] strArr, byte[] bArr);

    GcoreTask<GcoreConfigurations> registerSync(String str, int i, String[] strArr, byte[] bArr, String str2, String str3);

    GcoreTask<Void> unRegister(String str);

    GcoreTask<Void> weakRegister(String str, int i, String[] strArr, int[] iArr, byte[] bArr);
}
