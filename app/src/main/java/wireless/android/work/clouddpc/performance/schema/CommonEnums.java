package wireless.android.work.clouddpc.performance.schema;

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.Internal;

public final class CommonEnums {
    private CommonEnums() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public enum ProvisionMode implements Internal.EnumLite {
        UNSPECIFIED_MODE(0),
        ARC_USER(1),
        ARC_KIOSK(2),
        ARC_ACTIVE_DIRECTORY(3),
        COMP(4),
        DEVICE_ADMIN(5),
        DEVICE_OWNER(6),
        EDU_USER(7),
        MANAGED_PROFILE(8),
        KIOSK_USER(9),
        ARC_CHILD_USER(10),
        ARC_OFFLINE_DEMO_MODE(11);

        public static final int ARC_ACTIVE_DIRECTORY_VALUE = 3;
        public static final int ARC_CHILD_USER_VALUE = 10;
        public static final int ARC_KIOSK_VALUE = 2;
        public static final int ARC_OFFLINE_DEMO_MODE_VALUE = 11;
        public static final int ARC_USER_VALUE = 1;
        public static final int COMP_VALUE = 4;
        public static final int DEVICE_ADMIN_VALUE = 5;
        public static final int DEVICE_OWNER_VALUE = 6;
        public static final int EDU_USER_VALUE = 7;
        public static final int KIOSK_USER_VALUE = 9;
        public static final int MANAGED_PROFILE_VALUE = 8;
        public static final int UNSPECIFIED_MODE_VALUE = 0;
        private static final Internal.EnumLiteMap<ProvisionMode> internalValueMap = new Internal.EnumLiteMap<ProvisionMode>() {
            public ProvisionMode findValueByNumber(int number) {
                return ProvisionMode.forNumber(number);
            }
        };
        private final int value;

        private ProvisionMode(int value2) {
            this.value = value2;
        }

        public static ProvisionMode forNumber(int value2) {
            switch (value2) {
                case 0:
                    return UNSPECIFIED_MODE;
                case 1:
                    return ARC_USER;
                case 2:
                    return ARC_KIOSK;
                case 3:
                    return ARC_ACTIVE_DIRECTORY;
                case 4:
                    return COMP;
                case 5:
                    return DEVICE_ADMIN;
                case 6:
                    return DEVICE_OWNER;
                case 7:
                    return EDU_USER;
                case 8:
                    return MANAGED_PROFILE;
                case 9:
                    return KIOSK_USER;
                case 10:
                    return ARC_CHILD_USER;
                case 11:
                    return ARC_OFFLINE_DEMO_MODE;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<ProvisionMode> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return ProvisionModeVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class ProvisionModeVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new ProvisionModeVerifier();

            private ProvisionModeVerifier() {
            }

            public boolean isInRange(int number) {
                return ProvisionMode.forNumber(number) != null;
            }
        }
    }

    public enum EventState implements Internal.EnumLite {
        UNSPECIFIED_STATE(0),
        SUCCESS(1),
        FAILURE(2),
        TIMEOUT(3),
        START(4);

        public static final int FAILURE_VALUE = 2;
        public static final int START_VALUE = 4;
        public static final int SUCCESS_VALUE = 1;
        public static final int TIMEOUT_VALUE = 3;
        public static final int UNSPECIFIED_STATE_VALUE = 0;
        private static final Internal.EnumLiteMap<EventState> internalValueMap = new Internal.EnumLiteMap<EventState>() {
            public EventState findValueByNumber(int number) {
                return EventState.forNumber(number);
            }
        };
        private final int value;

        private EventState(int value2) {
            this.value = value2;
        }

        public static EventState forNumber(int value2) {
            if (value2 == 0) {
                return UNSPECIFIED_STATE;
            }
            if (value2 == 1) {
                return SUCCESS;
            }
            if (value2 == 2) {
                return FAILURE;
            }
            if (value2 == 3) {
                return TIMEOUT;
            }
            if (value2 != 4) {
                return null;
            }
            return START;
        }

        public static Internal.EnumLiteMap<EventState> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return EventStateVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class EventStateVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new EventStateVerifier();

            private EventStateVerifier() {
            }

            public boolean isInRange(int number) {
                return EventState.forNumber(number) != null;
            }
        }
    }

    public enum Mitigation implements Internal.EnumLite {
        UNSPECIFIED_MITIGATION(0),
        NO_MITIGATION(1),
        WIFI_NETWORK_MITIGATION(2);

        public static final int NO_MITIGATION_VALUE = 1;
        public static final int UNSPECIFIED_MITIGATION_VALUE = 0;
        public static final int WIFI_NETWORK_MITIGATION_VALUE = 2;
        private static final Internal.EnumLiteMap<Mitigation> internalValueMap = new Internal.EnumLiteMap<Mitigation>() {
            public Mitigation findValueByNumber(int number) {
                return Mitigation.forNumber(number);
            }
        };
        private final int value;

        private Mitigation(int value2) {
            this.value = value2;
        }

        public static Mitigation forNumber(int value2) {
            if (value2 == 0) {
                return UNSPECIFIED_MITIGATION;
            }
            if (value2 == 1) {
                return NO_MITIGATION;
            }
            if (value2 != 2) {
                return null;
            }
            return WIFI_NETWORK_MITIGATION;
        }

        public static Internal.EnumLiteMap<Mitigation> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return MitigationVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class MitigationVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new MitigationVerifier();

            private MitigationVerifier() {
            }

            public boolean isInRange(int number) {
                return Mitigation.forNumber(number) != null;
            }
        }
    }

    public enum MetricType implements Internal.EnumLite {
        APP_INSTALL_METRIC(0);

        public static final int APP_INSTALL_METRIC_VALUE = 0;
        private static final Internal.EnumLiteMap<MetricType> internalValueMap = new Internal.EnumLiteMap<MetricType>() {
            public MetricType findValueByNumber(int number) {
                return MetricType.forNumber(number);
            }
        };
        private final int value;

        private MetricType(int value2) {
            this.value = value2;
        }

        public static MetricType forNumber(int value2) {
            if (value2 != 0) {
                return null;
            }
            return APP_INSTALL_METRIC;
        }

        public static Internal.EnumLiteMap<MetricType> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return MetricTypeVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class MetricTypeVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new MetricTypeVerifier();

            private MetricTypeVerifier() {
            }

            public boolean isInRange(int number) {
                return MetricType.forNumber(number) != null;
            }
        }
    }

    public enum InstallErrorReason implements Internal.EnumLite {
        REASON_UNSPECIFIED_ERROR(0),
        TIMEOUT_ERROR(1),
        TRANSIENT_ERROR(2),
        NOT_FOUND_ERROR(3),
        NOT_COMPATIBLE_WITH_DEVICE_ERROR(4),
        NOT_APPROVED_ERROR(5),
        PERMISSIONS_NOT_ACCEPTED_ERROR(6),
        NOT_AVAILABLE_IN_COUNTRY_ERROR(7),
        NO_LICENSES_REMAINING_ERROR(8),
        NOT_ENROLLED_ERROR(9);

        public static final int NOT_APPROVED_ERROR_VALUE = 5;
        public static final int NOT_AVAILABLE_IN_COUNTRY_ERROR_VALUE = 7;
        public static final int NOT_COMPATIBLE_WITH_DEVICE_ERROR_VALUE = 4;
        public static final int NOT_ENROLLED_ERROR_VALUE = 9;
        public static final int NOT_FOUND_ERROR_VALUE = 3;
        public static final int NO_LICENSES_REMAINING_ERROR_VALUE = 8;
        public static final int PERMISSIONS_NOT_ACCEPTED_ERROR_VALUE = 6;
        public static final int REASON_UNSPECIFIED_ERROR_VALUE = 0;
        public static final int TIMEOUT_ERROR_VALUE = 1;
        public static final int TRANSIENT_ERROR_VALUE = 2;
        private static final Internal.EnumLiteMap<InstallErrorReason> internalValueMap = new Internal.EnumLiteMap<InstallErrorReason>() {
            public InstallErrorReason findValueByNumber(int number) {
                return InstallErrorReason.forNumber(number);
            }
        };
        private final int value;

        private InstallErrorReason(int value2) {
            this.value = value2;
        }

        public static InstallErrorReason forNumber(int value2) {
            switch (value2) {
                case 0:
                    return REASON_UNSPECIFIED_ERROR;
                case 1:
                    return TIMEOUT_ERROR;
                case 2:
                    return TRANSIENT_ERROR;
                case 3:
                    return NOT_FOUND_ERROR;
                case 4:
                    return NOT_COMPATIBLE_WITH_DEVICE_ERROR;
                case 5:
                    return NOT_APPROVED_ERROR;
                case 6:
                    return PERMISSIONS_NOT_ACCEPTED_ERROR;
                case 7:
                    return NOT_AVAILABLE_IN_COUNTRY_ERROR;
                case 8:
                    return NO_LICENSES_REMAINING_ERROR;
                case 9:
                    return NOT_ENROLLED_ERROR;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<InstallErrorReason> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return InstallErrorReasonVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class InstallErrorReasonVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new InstallErrorReasonVerifier();

            private InstallErrorReasonVerifier() {
            }

            public boolean isInRange(int number) {
                return InstallErrorReason.forNumber(number) != null;
            }
        }
    }

    public enum TaskFailureReason implements Internal.EnumLite {
        FAILURE_REASON_UNKNOWN(0),
        FAILURE_REASON_TIMEOUT(13),
        FAILURE_REASON_NETWORK_UNAVAILABLE(14),
        FAILURE_REASON_USER_CANCEL(16),
        FAILURE_REASON_INVALID_SETUP_ACTION(21),
        FAILURE_REASON_CLOUD_DPS_CLIENT(7),
        FAILURE_REASON_ENROLLMENT_TOKEN_INVALID(8),
        FAILURE_REASON_DEVICE_QUOTA_EXCEEDED(9),
        FAILURE_REASON_ENTERPRISE_INVALID(15),
        FAILURE_REASON_SERVER_OTHER(23),
        FAILURE_REASON_ACCOUNT_NOT_READY(3),
        FAILURE_REASON_ADD_ACCOUNT_FAILED(11),
        FAILURE_REASON_NO_ACCOUNT_IN_WORK_PROFILE(17),
        FAILURE_REASON_ACCOUNT_NOT_WHITELISTED(19),
        FAILURE_REASON_REMOVE_ACCOUNT(24),
        FAILURE_REASON_OAUTH_TOKEN(25),
        FAILURE_REASON_ACCOUNT(1),
        FAILURE_REASON_ANDROID_ID(2),
        FAILURE_REASON_CHECKIN(4),
        FAILURE_REASON_DPC_SUPPORT(5),
        FAILURE_REASON_MANAGED_PROVISIONING_FAILED(20),
        FAILURE_REASON_POLICY_UPDATE(6),
        FAILURE_REASON_QUARANTINE(12),
        FAILURE_REASON_JSON(22),
        FAILURE_REASON_INVALID_POLICY_STATE(18);

        public static final int FAILURE_REASON_ACCOUNT_NOT_READY_VALUE = 3;
        public static final int FAILURE_REASON_ACCOUNT_NOT_WHITELISTED_VALUE = 19;
        public static final int FAILURE_REASON_ACCOUNT_VALUE = 1;
        public static final int FAILURE_REASON_ADD_ACCOUNT_FAILED_VALUE = 11;
        public static final int FAILURE_REASON_ANDROID_ID_VALUE = 2;
        public static final int FAILURE_REASON_CHECKIN_VALUE = 4;
        public static final int FAILURE_REASON_CLOUD_DPS_CLIENT_VALUE = 7;
        public static final int FAILURE_REASON_DEVICE_QUOTA_EXCEEDED_VALUE = 9;
        public static final int FAILURE_REASON_DPC_SUPPORT_VALUE = 5;
        public static final int FAILURE_REASON_ENROLLMENT_TOKEN_INVALID_VALUE = 8;
        public static final int FAILURE_REASON_ENTERPRISE_INVALID_VALUE = 15;
        public static final int FAILURE_REASON_INVALID_POLICY_STATE_VALUE = 18;
        public static final int FAILURE_REASON_INVALID_SETUP_ACTION_VALUE = 21;
        public static final int FAILURE_REASON_JSON_VALUE = 22;
        public static final int FAILURE_REASON_MANAGED_PROVISIONING_FAILED_VALUE = 20;
        public static final int FAILURE_REASON_NETWORK_UNAVAILABLE_VALUE = 14;
        public static final int FAILURE_REASON_NO_ACCOUNT_IN_WORK_PROFILE_VALUE = 17;
        public static final int FAILURE_REASON_OAUTH_TOKEN_VALUE = 25;
        public static final int FAILURE_REASON_POLICY_UPDATE_VALUE = 6;
        public static final int FAILURE_REASON_QUARANTINE_VALUE = 12;
        public static final int FAILURE_REASON_REMOVE_ACCOUNT_VALUE = 24;
        public static final int FAILURE_REASON_SERVER_OTHER_VALUE = 23;
        public static final int FAILURE_REASON_TIMEOUT_VALUE = 13;
        public static final int FAILURE_REASON_UNKNOWN_VALUE = 0;
        public static final int FAILURE_REASON_USER_CANCEL_VALUE = 16;
        private static final Internal.EnumLiteMap<TaskFailureReason> internalValueMap = new Internal.EnumLiteMap<TaskFailureReason>() {
            public TaskFailureReason findValueByNumber(int number) {
                return TaskFailureReason.forNumber(number);
            }
        };
        private final int value;

        private TaskFailureReason(int value2) {
            this.value = value2;
        }

        public static TaskFailureReason forNumber(int value2) {
            switch (value2) {
                case 0:
                    return FAILURE_REASON_UNKNOWN;
                case 1:
                    return FAILURE_REASON_ACCOUNT;
                case 2:
                    return FAILURE_REASON_ANDROID_ID;
                case 3:
                    return FAILURE_REASON_ACCOUNT_NOT_READY;
                case 4:
                    return FAILURE_REASON_CHECKIN;
                case 5:
                    return FAILURE_REASON_DPC_SUPPORT;
                case 6:
                    return FAILURE_REASON_POLICY_UPDATE;
                case 7:
                    return FAILURE_REASON_CLOUD_DPS_CLIENT;
                case 8:
                    return FAILURE_REASON_ENROLLMENT_TOKEN_INVALID;
                case 9:
                    return FAILURE_REASON_DEVICE_QUOTA_EXCEEDED;
                case 10:
                default:
                    return null;
                case 11:
                    return FAILURE_REASON_ADD_ACCOUNT_FAILED;
                case 12:
                    return FAILURE_REASON_QUARANTINE;
                case 13:
                    return FAILURE_REASON_TIMEOUT;
                case 14:
                    return FAILURE_REASON_NETWORK_UNAVAILABLE;
                case 15:
                    return FAILURE_REASON_ENTERPRISE_INVALID;
                case 16:
                    return FAILURE_REASON_USER_CANCEL;
                case 17:
                    return FAILURE_REASON_NO_ACCOUNT_IN_WORK_PROFILE;
                case 18:
                    return FAILURE_REASON_INVALID_POLICY_STATE;
                case 19:
                    return FAILURE_REASON_ACCOUNT_NOT_WHITELISTED;
                case 20:
                    return FAILURE_REASON_MANAGED_PROVISIONING_FAILED;
                case 21:
                    return FAILURE_REASON_INVALID_SETUP_ACTION;
                case 22:
                    return FAILURE_REASON_JSON;
                case 23:
                    return FAILURE_REASON_SERVER_OTHER;
                case 24:
                    return FAILURE_REASON_REMOVE_ACCOUNT;
                case 25:
                    return FAILURE_REASON_OAUTH_TOKEN;
            }
        }

        public static Internal.EnumLiteMap<TaskFailureReason> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return TaskFailureReasonVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class TaskFailureReasonVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new TaskFailureReasonVerifier();

            private TaskFailureReasonVerifier() {
            }

            public boolean isInRange(int number) {
                return TaskFailureReason.forNumber(number) != null;
            }
        }
    }

    public enum SetupTaskFailureType implements Internal.EnumLite {
        ERROR_UNKNOWN(0),
        ERROR_SERVER(1),
        ERROR_TRANSIENT(2),
        ERROR_DEVICE(3),
        ERROR_POLICY(4),
        ERROR_NETWORK(5),
        ERROR_USER_CANCEL(6),
        ERROR_SETUP_ACTION(7);

        public static final int ERROR_DEVICE_VALUE = 3;
        public static final int ERROR_NETWORK_VALUE = 5;
        public static final int ERROR_POLICY_VALUE = 4;
        public static final int ERROR_SERVER_VALUE = 1;
        public static final int ERROR_SETUP_ACTION_VALUE = 7;
        public static final int ERROR_TRANSIENT_VALUE = 2;
        public static final int ERROR_UNKNOWN_VALUE = 0;
        public static final int ERROR_USER_CANCEL_VALUE = 6;
        private static final Internal.EnumLiteMap<SetupTaskFailureType> internalValueMap = new Internal.EnumLiteMap<SetupTaskFailureType>() {
            public SetupTaskFailureType findValueByNumber(int number) {
                return SetupTaskFailureType.forNumber(number);
            }
        };
        private final int value;

        private SetupTaskFailureType(int value2) {
            this.value = value2;
        }

        public static SetupTaskFailureType forNumber(int value2) {
            switch (value2) {
                case 0:
                    return ERROR_UNKNOWN;
                case 1:
                    return ERROR_SERVER;
                case 2:
                    return ERROR_TRANSIENT;
                case 3:
                    return ERROR_DEVICE;
                case 4:
                    return ERROR_POLICY;
                case 5:
                    return ERROR_NETWORK;
                case 6:
                    return ERROR_USER_CANCEL;
                case 7:
                    return ERROR_SETUP_ACTION;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<SetupTaskFailureType> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return SetupTaskFailureTypeVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class SetupTaskFailureTypeVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new SetupTaskFailureTypeVerifier();

            private SetupTaskFailureTypeVerifier() {
            }

            public boolean isInRange(int number) {
                return SetupTaskFailureType.forNumber(number) != null;
            }
        }
    }

    public enum ProvisionEntryPoint implements Internal.EnumLite {
        UNSPECIFIED_PROVISION_ENTRY_POINT(0),
        SUW_DIRECT(1),
        SUW_SYNC_AUTH(2),
        SUW_AFW_STRING(3),
        SETTINGS_ADD_ACCOUNT(4),
        EASY_WORK_SETUP(5),
        ENROLLMENT_LINK(6),
        MANUAL_START_CLOUDDPC(7),
        DPC_TRANSFER(8),
        ADMIN_INTEGRATED(9);

        public static final int ADMIN_INTEGRATED_VALUE = 9;
        public static final int DPC_TRANSFER_VALUE = 8;
        public static final int EASY_WORK_SETUP_VALUE = 5;
        public static final int ENROLLMENT_LINK_VALUE = 6;
        public static final int MANUAL_START_CLOUDDPC_VALUE = 7;
        public static final int SETTINGS_ADD_ACCOUNT_VALUE = 4;
        public static final int SUW_AFW_STRING_VALUE = 3;
        public static final int SUW_DIRECT_VALUE = 1;
        public static final int SUW_SYNC_AUTH_VALUE = 2;
        public static final int UNSPECIFIED_PROVISION_ENTRY_POINT_VALUE = 0;
        private static final Internal.EnumLiteMap<ProvisionEntryPoint> internalValueMap = new Internal.EnumLiteMap<ProvisionEntryPoint>() {
            public ProvisionEntryPoint findValueByNumber(int number) {
                return ProvisionEntryPoint.forNumber(number);
            }
        };
        private final int value;

        private ProvisionEntryPoint(int value2) {
            this.value = value2;
        }

        public static ProvisionEntryPoint forNumber(int value2) {
            switch (value2) {
                case 0:
                    return UNSPECIFIED_PROVISION_ENTRY_POINT;
                case 1:
                    return SUW_DIRECT;
                case 2:
                    return SUW_SYNC_AUTH;
                case 3:
                    return SUW_AFW_STRING;
                case 4:
                    return SETTINGS_ADD_ACCOUNT;
                case 5:
                    return EASY_WORK_SETUP;
                case 6:
                    return ENROLLMENT_LINK;
                case 7:
                    return MANUAL_START_CLOUDDPC;
                case 8:
                    return DPC_TRANSFER;
                case 9:
                    return ADMIN_INTEGRATED;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<ProvisionEntryPoint> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return ProvisionEntryPointVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class ProvisionEntryPointVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new ProvisionEntryPointVerifier();

            private ProvisionEntryPointVerifier() {
            }

            public boolean isInRange(int number) {
                return ProvisionEntryPoint.forNumber(number) != null;
            }
        }
    }

    public enum SmartSystemAppAction implements Internal.EnumLite {
        UNKNOWN(0),
        DOWNLOADS(1),
        CAMERA(2),
        DIALER(3),
        MESSAGING(4),
        SETTINGS(5),
        AUDIO(6),
        IMAGE_VIDEO(7),
        CONTACTS(8);

        public static final int AUDIO_VALUE = 6;
        public static final int CAMERA_VALUE = 2;
        public static final int CONTACTS_VALUE = 8;
        public static final int DIALER_VALUE = 3;
        public static final int DOWNLOADS_VALUE = 1;
        public static final int IMAGE_VIDEO_VALUE = 7;
        public static final int MESSAGING_VALUE = 4;
        public static final int SETTINGS_VALUE = 5;
        public static final int UNKNOWN_VALUE = 0;
        private static final Internal.EnumLiteMap<SmartSystemAppAction> internalValueMap = new Internal.EnumLiteMap<SmartSystemAppAction>() {
            public SmartSystemAppAction findValueByNumber(int number) {
                return SmartSystemAppAction.forNumber(number);
            }
        };
        private final int value;

        private SmartSystemAppAction(int value2) {
            this.value = value2;
        }

        public static SmartSystemAppAction forNumber(int value2) {
            switch (value2) {
                case 0:
                    return UNKNOWN;
                case 1:
                    return DOWNLOADS;
                case 2:
                    return CAMERA;
                case 3:
                    return DIALER;
                case 4:
                    return MESSAGING;
                case 5:
                    return SETTINGS;
                case 6:
                    return AUDIO;
                case 7:
                    return IMAGE_VIDEO;
                case 8:
                    return CONTACTS;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<SmartSystemAppAction> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return SmartSystemAppActionVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class SmartSystemAppActionVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new SmartSystemAppActionVerifier();

            private SmartSystemAppActionVerifier() {
            }

            public boolean isInRange(int number) {
                return SmartSystemAppAction.forNumber(number) != null;
            }
        }
    }
}
