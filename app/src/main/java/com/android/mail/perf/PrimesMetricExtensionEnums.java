package com.android.mail.perf;

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.Internal;

public final class PrimesMetricExtensionEnums {
    private PrimesMetricExtensionEnums() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public enum Annotation implements Internal.EnumLite {
        UNKNOWN_ANNOTATION(0),
        IS_TABLET(1),
        IS_NATIVE_SAPI(3),
        EXPERIMENT_START_ADS_BEFORE_CRITICAL_STARTUP(2);
        
        public static final int EXPERIMENT_START_ADS_BEFORE_CRITICAL_STARTUP_VALUE = 2;
        public static final int IS_NATIVE_SAPI_VALUE = 3;
        public static final int IS_TABLET_VALUE = 1;
        public static final int UNKNOWN_ANNOTATION_VALUE = 0;
        private static final Internal.EnumLiteMap<Annotation> internalValueMap = new Internal.EnumLiteMap<Annotation>() {
            public Annotation findValueByNumber(int number) {
                return Annotation.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static Annotation forNumber(int value2) {
            if (value2 == 0) {
                return UNKNOWN_ANNOTATION;
            }
            if (value2 == 1) {
                return IS_TABLET;
            }
            if (value2 == 2) {
                return EXPERIMENT_START_ADS_BEFORE_CRITICAL_STARTUP;
            }
            if (value2 != 3) {
                return null;
            }
            return IS_NATIVE_SAPI;
        }

        public static Internal.EnumLiteMap<Annotation> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return AnnotationVerifier.INSTANCE;
        }

        private static final class AnnotationVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new AnnotationVerifier();

            private AnnotationVerifier() {
            }

            public boolean isInRange(int number) {
                return Annotation.forNumber(number) != null;
            }
        }

        private Annotation(int value2) {
            this.value = value2;
        }
    }

    public enum CancellationReason implements Internal.EnumLite {
        NONE(0),
        DESTRUCTIVE_ACTION_DIALOG(1),
        STARTUP_ENTRY_POINT(2),
        STARTUP_MAIL_ACTIVITY_INTERRUPTED(3),
        STARTUP_MAIL_ACTIVITY_PAUSED(4),
        STARTUP_RESTORED_STATE(6),
        STARTUP_WAIT(5);
        
        public static final int DESTRUCTIVE_ACTION_DIALOG_VALUE = 1;
        public static final int NONE_VALUE = 0;
        public static final int STARTUP_ENTRY_POINT_VALUE = 2;
        public static final int STARTUP_MAIL_ACTIVITY_INTERRUPTED_VALUE = 3;
        public static final int STARTUP_MAIL_ACTIVITY_PAUSED_VALUE = 4;
        public static final int STARTUP_RESTORED_STATE_VALUE = 6;
        public static final int STARTUP_WAIT_VALUE = 5;
        private static final Internal.EnumLiteMap<CancellationReason> internalValueMap = new Internal.EnumLiteMap<CancellationReason>() {
            public CancellationReason findValueByNumber(int number) {
                return CancellationReason.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static CancellationReason forNumber(int value2) {
            switch (value2) {
                case 0:
                    return NONE;
                case 1:
                    return DESTRUCTIVE_ACTION_DIALOG;
                case 2:
                    return STARTUP_ENTRY_POINT;
                case 3:
                    return STARTUP_MAIL_ACTIVITY_INTERRUPTED;
                case 4:
                    return STARTUP_MAIL_ACTIVITY_PAUSED;
                case 5:
                    return STARTUP_WAIT;
                case 6:
                    return STARTUP_RESTORED_STATE;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<CancellationReason> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return CancellationReasonVerifier.INSTANCE;
        }

        private static final class CancellationReasonVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new CancellationReasonVerifier();

            private CancellationReasonVerifier() {
            }

            public boolean isInRange(int number) {
                return CancellationReason.forNumber(number) != null;
            }
        }

        private CancellationReason(int value2) {
            this.value = value2;
        }
    }

    public enum FolderType implements Internal.EnumLite {
        UNKNOWN_FOLDER_TYPE(0),
        COMBINED_INBOX(4),
        DRAFT(6),
        FLAGGED(11),
        IMPORTANT(5),
        INBOX(2),
        INBOX_SECTION(3),
        OTHER_FOLDER_TYPE(1),
        OUTBOX(7),
        SEARCH(12),
        SENT(8),
        SPAM(9),
        STARRED(10);
        
        public static final int COMBINED_INBOX_VALUE = 4;
        public static final int DRAFT_VALUE = 6;
        public static final int FLAGGED_VALUE = 11;
        public static final int IMPORTANT_VALUE = 5;
        public static final int INBOX_SECTION_VALUE = 3;
        public static final int INBOX_VALUE = 2;
        public static final int OTHER_FOLDER_TYPE_VALUE = 1;
        public static final int OUTBOX_VALUE = 7;
        public static final int SEARCH_VALUE = 12;
        public static final int SENT_VALUE = 8;
        public static final int SPAM_VALUE = 9;
        public static final int STARRED_VALUE = 10;
        public static final int UNKNOWN_FOLDER_TYPE_VALUE = 0;
        private static final Internal.EnumLiteMap<FolderType> internalValueMap = new Internal.EnumLiteMap<FolderType>() {
            public FolderType findValueByNumber(int number) {
                return FolderType.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static FolderType forNumber(int value2) {
            switch (value2) {
                case 0:
                    return UNKNOWN_FOLDER_TYPE;
                case 1:
                    return OTHER_FOLDER_TYPE;
                case 2:
                    return INBOX;
                case 3:
                    return INBOX_SECTION;
                case 4:
                    return COMBINED_INBOX;
                case 5:
                    return IMPORTANT;
                case 6:
                    return DRAFT;
                case 7:
                    return OUTBOX;
                case 8:
                    return SENT;
                case 9:
                    return SPAM;
                case 10:
                    return STARRED;
                case 11:
                    return FLAGGED;
                case 12:
                    return SEARCH;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<FolderType> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return FolderTypeVerifier.INSTANCE;
        }

        private static final class FolderTypeVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new FolderTypeVerifier();

            private FolderTypeVerifier() {
            }

            public boolean isInRange(int number) {
                return FolderType.forNumber(number) != null;
            }
        }

        private FolderType(int value2) {
            this.value = value2;
        }
    }

    public enum AccountType implements Internal.EnumLite {
        UNKNOWN_ACCOUNT_TYPE(0),
        GOOGLE(1),
        IMAP(2),
        POP3(3),
        EXCHANGE(4);
        
        public static final int EXCHANGE_VALUE = 4;
        public static final int GOOGLE_VALUE = 1;
        public static final int IMAP_VALUE = 2;
        public static final int POP3_VALUE = 3;
        public static final int UNKNOWN_ACCOUNT_TYPE_VALUE = 0;
        private static final Internal.EnumLiteMap<AccountType> internalValueMap = new Internal.EnumLiteMap<AccountType>() {
            public AccountType findValueByNumber(int number) {
                return AccountType.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static AccountType forNumber(int value2) {
            if (value2 == 0) {
                return UNKNOWN_ACCOUNT_TYPE;
            }
            if (value2 == 1) {
                return GOOGLE;
            }
            if (value2 == 2) {
                return IMAP;
            }
            if (value2 == 3) {
                return POP3;
            }
            if (value2 != 4) {
                return null;
            }
            return EXCHANGE;
        }

        public static Internal.EnumLiteMap<AccountType> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return AccountTypeVerifier.INSTANCE;
        }

        private static final class AccountTypeVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new AccountTypeVerifier();

            private AccountTypeVerifier() {
            }

            public boolean isInRange(int number) {
                return AccountType.forNumber(number) != null;
            }
        }

        private AccountType(int value2) {
            this.value = value2;
        }
    }

    public enum DataLayer implements Internal.EnumLite {
        UNKNOWN_DATA_LAYER(0),
        LEGACY(1),
        BTD(2),
        BTD_CONTROL(3);
        
        public static final int BTD_CONTROL_VALUE = 3;
        public static final int BTD_VALUE = 2;
        public static final int LEGACY_VALUE = 1;
        public static final int UNKNOWN_DATA_LAYER_VALUE = 0;
        private static final Internal.EnumLiteMap<DataLayer> internalValueMap = new Internal.EnumLiteMap<DataLayer>() {
            public DataLayer findValueByNumber(int number) {
                return DataLayer.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static DataLayer forNumber(int value2) {
            if (value2 == 0) {
                return UNKNOWN_DATA_LAYER;
            }
            if (value2 == 1) {
                return LEGACY;
            }
            if (value2 == 2) {
                return BTD;
            }
            if (value2 != 3) {
                return null;
            }
            return BTD_CONTROL;
        }

        public static Internal.EnumLiteMap<DataLayer> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return DataLayerVerifier.INSTANCE;
        }

        private static final class DataLayerVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new DataLayerVerifier();

            private DataLayerVerifier() {
            }

            public boolean isInRange(int number) {
                return DataLayer.forNumber(number) != null;
            }
        }

        private DataLayer(int value2) {
            this.value = value2;
        }
    }

    public enum ContentSource implements Internal.EnumLite {
        UNKNOWN_CONTENT_SOURCE(0),
        LOCAL(1),
        REMOTE(2);
        
        public static final int LOCAL_VALUE = 1;
        public static final int REMOTE_VALUE = 2;
        public static final int UNKNOWN_CONTENT_SOURCE_VALUE = 0;
        private static final Internal.EnumLiteMap<ContentSource> internalValueMap = new Internal.EnumLiteMap<ContentSource>() {
            public ContentSource findValueByNumber(int number) {
                return ContentSource.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static ContentSource forNumber(int value2) {
            if (value2 == 0) {
                return UNKNOWN_CONTENT_SOURCE;
            }
            if (value2 == 1) {
                return LOCAL;
            }
            if (value2 != 2) {
                return null;
            }
            return REMOTE;
        }

        public static Internal.EnumLiteMap<ContentSource> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return ContentSourceVerifier.INSTANCE;
        }

        private static final class ContentSourceVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new ContentSourceVerifier();

            private ContentSourceVerifier() {
            }

            public boolean isInRange(int number) {
                return ContentSource.forNumber(number) != null;
            }
        }

        private ContentSource(int value2) {
            this.value = value2;
        }
    }
}
