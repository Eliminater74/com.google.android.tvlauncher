package com.google.android.libraries.gcoreclient.common.version;

public interface GcoreVersion {
    Version getVersion();

    public enum Version {
        MANCHEGO(6000000),
        NACHO(6500000),
        OLIVET(6700000),
        ORLA(7000000),
        PARMESAN(7200000),
        QUESO(7500000),
        REBLOCHON(7800000),
        SAGA(8000000),
        TALA(8200000),
        URDA(8400000),
        V1(8600000),
        V2(8800000),
        V3(9000000),
        V4(9200000),
        V5(9410000),
        V6(9600000),
        V7(9800000),
        V8(10000000),
        V9(10200000),
        V10(10400000),
        V11(11021000),
        V12(11200000),
        V13(11400000);
        
        private final int versionCode;

        private Version(int versionCode2) {
            this.versionCode = versionCode2;
        }

        public int getVersionCode() {
            return this.versionCode;
        }

        public static Version getVersionFromCode(int versionCode2) {
            Version[] versions = values();
            for (int i = versions.length - 1; i >= 0; i--) {
                if (versions[i].getVersionCode() <= versionCode2) {
                    return versions[i];
                }
            }
            StringBuilder sb = new StringBuilder(41);
            sb.append("Unsupported GMS version code: ");
            sb.append(versionCode2);
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
