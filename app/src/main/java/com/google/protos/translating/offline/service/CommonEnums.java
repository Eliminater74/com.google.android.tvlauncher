package com.google.protos.translating.offline.service;

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.Internal;

public final class CommonEnums {
    private CommonEnums() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public enum Result implements Internal.EnumLite {
        RESULT_DEFAULT(0),
        RESULT_SUCCESS(1),
        RESULT_ERROR_CORRUPTED(2),
        RESULT_ERROR_INVALID_ARGUMENT(3),
        RESULT_ERROR_WRITE_FILE_FAILED(4),
        RESULT_ERROR_NOT_READY(5),
        RESULT_ERROR_RECONSTRUCTION_FAILED(6),
        RESULT_ERROR_DELETE_FILE_FAILED(7),
        RESULT_ERROR_OFFLINE_FILE_NOT_FOUND(8),
        RESULT_ERROR_UNSUPPORTED_DECODER_PROCESS_MODE(9);

        public static final int RESULT_DEFAULT_VALUE = 0;
        public static final int RESULT_ERROR_CORRUPTED_VALUE = 2;
        public static final int RESULT_ERROR_DELETE_FILE_FAILED_VALUE = 7;
        public static final int RESULT_ERROR_INVALID_ARGUMENT_VALUE = 3;
        public static final int RESULT_ERROR_NOT_READY_VALUE = 5;
        public static final int RESULT_ERROR_OFFLINE_FILE_NOT_FOUND_VALUE = 8;
        public static final int RESULT_ERROR_RECONSTRUCTION_FAILED_VALUE = 6;
        public static final int RESULT_ERROR_UNSUPPORTED_DECODER_PROCESS_MODE_VALUE = 9;
        public static final int RESULT_ERROR_WRITE_FILE_FAILED_VALUE = 4;
        public static final int RESULT_SUCCESS_VALUE = 1;
        private static final Internal.EnumLiteMap<Result> internalValueMap = new Internal.EnumLiteMap<Result>() {
            public Result findValueByNumber(int number) {
                return Result.forNumber(number);
            }
        };
        private final int value;

        private Result(int value2) {
            this.value = value2;
        }

        public static Result forNumber(int value2) {
            switch (value2) {
                case 0:
                    return RESULT_DEFAULT;
                case 1:
                    return RESULT_SUCCESS;
                case 2:
                    return RESULT_ERROR_CORRUPTED;
                case 3:
                    return RESULT_ERROR_INVALID_ARGUMENT;
                case 4:
                    return RESULT_ERROR_WRITE_FILE_FAILED;
                case 5:
                    return RESULT_ERROR_NOT_READY;
                case 6:
                    return RESULT_ERROR_RECONSTRUCTION_FAILED;
                case 7:
                    return RESULT_ERROR_DELETE_FILE_FAILED;
                case 8:
                    return RESULT_ERROR_OFFLINE_FILE_NOT_FOUND;
                case 9:
                    return RESULT_ERROR_UNSUPPORTED_DECODER_PROCESS_MODE;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<Result> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return ResultVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class ResultVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new ResultVerifier();

            private ResultVerifier() {
            }

            public boolean isInRange(int number) {
                return Result.forNumber(number) != null;
            }
        }
    }

    public enum Source implements Internal.EnumLite {
        SOURCE_DEFAULT(0),
        SOURCE_STT(1),
        SOURCE_PBMT_PRIMARY(2),
        SOURCE_PBMT_FALLBACK(3),
        SOURCE_BEE(4),
        SOURCE_MULTIPLE_SENTENCES(5);

        public static final int SOURCE_BEE_VALUE = 4;
        public static final int SOURCE_DEFAULT_VALUE = 0;
        public static final int SOURCE_MULTIPLE_SENTENCES_VALUE = 5;
        public static final int SOURCE_PBMT_FALLBACK_VALUE = 3;
        public static final int SOURCE_PBMT_PRIMARY_VALUE = 2;
        public static final int SOURCE_STT_VALUE = 1;
        private static final Internal.EnumLiteMap<Source> internalValueMap = new Internal.EnumLiteMap<Source>() {
            public Source findValueByNumber(int number) {
                return Source.forNumber(number);
            }
        };
        private final int value;

        private Source(int value2) {
            this.value = value2;
        }

        public static Source forNumber(int value2) {
            if (value2 == 0) {
                return SOURCE_DEFAULT;
            }
            if (value2 == 1) {
                return SOURCE_STT;
            }
            if (value2 == 2) {
                return SOURCE_PBMT_PRIMARY;
            }
            if (value2 == 3) {
                return SOURCE_PBMT_FALLBACK;
            }
            if (value2 == 4) {
                return SOURCE_BEE;
            }
            if (value2 != 5) {
                return null;
            }
            return SOURCE_MULTIPLE_SENTENCES;
        }

        public static Internal.EnumLiteMap<Source> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return SourceVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class SourceVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new SourceVerifier();

            private SourceVerifier() {
            }

            public boolean isInRange(int number) {
                return Source.forNumber(number) != null;
            }
        }
    }
}
