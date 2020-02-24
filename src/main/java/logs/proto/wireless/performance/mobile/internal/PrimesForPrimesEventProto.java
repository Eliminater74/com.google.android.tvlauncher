package logs.proto.wireless.performance.mobile.internal;

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.Internal;

public final class PrimesForPrimesEventProto {
    private PrimesForPrimesEventProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public enum PrimesForPrimesEvent implements Internal.EnumLite {
        UNKNOWN(0),
        PRIMES_INITIALIZE(17),
        INIT_DELAY(18),
        INIT_ALL(1),
        INIT_SHUTDOWN(2),
        INIT_CONFIGS(3),
        INIT_FLAGS(4),
        LOGGER_JOB_STARTED(5),
        LOGGER_JOB_SUCCESSFUL(6),
        LOGGER_JOB_INVALID_PARAMS(7),
        LOGGER_JOB_NO_FILE(8),
        LOGGER_JOB_MESSAGE_EMPTY(9),
        LOGGER_JOB_INTERRUPTED(10),
        LOGGER_JOB_EXCEPTION(11),
        LOGGER_JOB_HTTP_STATUS_200(12),
        LOGGER_JOB_HTTP_STATUS_400(13),
        LOGGER_JOB_HTTP_STATUS_500(14),
        LOGGER_JOB_HTTP_STATUS_OTHER(15),
        LOGGER_JOB_HTTP_TIMEOUT_EXCEPTION(16);
        
        public static final int INIT_ALL_VALUE = 1;
        public static final int INIT_CONFIGS_VALUE = 3;
        public static final int INIT_DELAY_VALUE = 18;
        public static final int INIT_FLAGS_VALUE = 4;
        public static final int INIT_SHUTDOWN_VALUE = 2;
        public static final int LOGGER_JOB_EXCEPTION_VALUE = 11;
        public static final int LOGGER_JOB_HTTP_STATUS_200_VALUE = 12;
        public static final int LOGGER_JOB_HTTP_STATUS_400_VALUE = 13;
        public static final int LOGGER_JOB_HTTP_STATUS_500_VALUE = 14;
        public static final int LOGGER_JOB_HTTP_STATUS_OTHER_VALUE = 15;
        public static final int LOGGER_JOB_HTTP_TIMEOUT_EXCEPTION_VALUE = 16;
        public static final int LOGGER_JOB_INTERRUPTED_VALUE = 10;
        public static final int LOGGER_JOB_INVALID_PARAMS_VALUE = 7;
        public static final int LOGGER_JOB_MESSAGE_EMPTY_VALUE = 9;
        public static final int LOGGER_JOB_NO_FILE_VALUE = 8;
        public static final int LOGGER_JOB_STARTED_VALUE = 5;
        public static final int LOGGER_JOB_SUCCESSFUL_VALUE = 6;
        public static final int PRIMES_INITIALIZE_VALUE = 17;
        public static final int UNKNOWN_VALUE = 0;
        private static final Internal.EnumLiteMap<PrimesForPrimesEvent> internalValueMap = new Internal.EnumLiteMap<PrimesForPrimesEvent>() {
            public PrimesForPrimesEvent findValueByNumber(int number) {
                return PrimesForPrimesEvent.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static PrimesForPrimesEvent forNumber(int value2) {
            switch (value2) {
                case 0:
                    return UNKNOWN;
                case 1:
                    return INIT_ALL;
                case 2:
                    return INIT_SHUTDOWN;
                case 3:
                    return INIT_CONFIGS;
                case 4:
                    return INIT_FLAGS;
                case 5:
                    return LOGGER_JOB_STARTED;
                case 6:
                    return LOGGER_JOB_SUCCESSFUL;
                case 7:
                    return LOGGER_JOB_INVALID_PARAMS;
                case 8:
                    return LOGGER_JOB_NO_FILE;
                case 9:
                    return LOGGER_JOB_MESSAGE_EMPTY;
                case 10:
                    return LOGGER_JOB_INTERRUPTED;
                case 11:
                    return LOGGER_JOB_EXCEPTION;
                case 12:
                    return LOGGER_JOB_HTTP_STATUS_200;
                case 13:
                    return LOGGER_JOB_HTTP_STATUS_400;
                case 14:
                    return LOGGER_JOB_HTTP_STATUS_500;
                case 15:
                    return LOGGER_JOB_HTTP_STATUS_OTHER;
                case 16:
                    return LOGGER_JOB_HTTP_TIMEOUT_EXCEPTION;
                case 17:
                    return PRIMES_INITIALIZE;
                case 18:
                    return INIT_DELAY;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<PrimesForPrimesEvent> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return PrimesForPrimesEventVerifier.INSTANCE;
        }

        private static final class PrimesForPrimesEventVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new PrimesForPrimesEventVerifier();

            private PrimesForPrimesEventVerifier() {
            }

            public boolean isInRange(int number) {
                return PrimesForPrimesEvent.forNumber(number) != null;
            }
        }

        private PrimesForPrimesEvent(int value2) {
            this.value = value2;
        }
    }
}
