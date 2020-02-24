package com.google.android.libraries.gcoreclient.phenotype;

@Deprecated
public interface GcorePhenotypeFlag<T> {

    public interface Builder {
        GcorePhenotypeFlag<Double> buildFlag(String str, double d);

        GcorePhenotypeFlag<Integer> buildFlag(String str, int i);

        GcorePhenotypeFlag<Long> buildFlag(String str, long j);

        GcorePhenotypeFlag<String> buildFlag(String str, String str2);

        GcorePhenotypeFlag<Boolean> buildFlag(String str, boolean z);

        GcorePhenotypeFlag<byte[]> buildFlag(String str, byte[] bArr);

        Builder setGservicePrefix(String str);

        Builder setPhenotypePrefix(String str);

        Builder skipGservices();
    }

    public interface BuilderFactory {
        Builder create(String str, String str2);
    }

    T get();
}
