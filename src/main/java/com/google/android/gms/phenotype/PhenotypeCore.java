package com.google.android.gms.phenotype;

public final class PhenotypeCore {
    public static final String ACTION_UPDATE = "com.google.android.gms.phenotype.UPDATE";
    public static final String CONFIGURATION_VERSION_FLAG_NAME = "__phenotype_configuration_version";
    public static final String EMPTY_ALTERNATE = "com.google.EMPTY";
    public static final int ERROR_ALREADY_REGISTERED = 29506;
    public static final int ERROR_DEBUG_ACCESS_DENIED = 29502;
    public static final int ERROR_INVALID_ARGUMENT = 29500;
    public static final int ERROR_NO_EXPERIMENT_TOKENS = 29505;
    public static final int ERROR_PACKAGE_NOT_REGISTERED = 29503;
    public static final int ERROR_PARTIAL_REGISTRATION_SUCCESS = 29507;
    public static final int ERROR_STALE_SNAPSHOT_TOKEN = 29501;
    public static final int ERROR_SYNC_FAILED = 29504;
    public static final String EXTRA_PACKAGE_NAME = "com.google.android.gms.phenotype.PACKAGE_NAME";
    public static final String EXTRA_UPDATE_REASON = "com.google.android.gms.phenotype.UPDATE_REASON";
    public static final String EXTRA_URGENT_UPDATE = "com.google.android.gms.phenotype.URGENT";
    public static final String LOGGED_OUT_USER = "";
    public static final String SERVER_TOKEN_FLAG_NAME = "__phenotype_server_token";

    static boolean zza(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    private PhenotypeCore() {
    }
}
