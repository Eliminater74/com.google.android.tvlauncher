package com.google.common.logging.proto2api;

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.Internal;

public final class UserActionEnum {
    private UserActionEnum() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public enum UserAction implements Internal.EnumLite {
        UNASSIGNED_USER_ACTION_ID(0),
        AUTOMATED(1),
        USER(2),
        GENERIC_CLICK(3),
        TAP(4),
        KEYBOARD_ENTER(5),
        MOUSE_CLICK(6),
        LEFT_CLICK(7),
        RIGHT_CLICK(8),
        HOVER(9),
        INTO_BOUNDING_BOX(10),
        OUT_OF_BOUNDING_BOX(11),
        PINCH(12),
        PINCH_OPEN(13),
        PINCH_CLOSED(14),
        INPUT_TEXT(15),
        INPUT_KEYBOARD(16),
        INPUT_VOICE(17),
        RESIZE_BROWSER(18),
        ROTATE_SCREEN(19),
        DIRECTIONAL_MOVEMENT(20),
        SWIPE(21),
        SCROLL_BAR(22),
        MOUSE_WHEEL(23),
        ARROW_KEYS(24),
        NAVIGATE(25),
        BACK_BUTTON(26),
        UNKNOWN_ACTION(27),
        HEAD_MOVEMENT(28),
        SHAKE(29),
        DRAG(30),
        LONG_PRESS(31),
        KEY_PRESS(32),
        ACTION_BY_TIMER(33),
        DOUBLE_CLICK(34),
        DOUBLE_TAP(35),
        ROLL(36),
        DROP(37),
        FORCE_TOUCH(38),
        MULTI_KEY_PRESS(39),
        TWO_FINGER_DRAG(40),
        ENTER_PROXIMITY(41);
        
        public static final int ACTION_BY_TIMER_VALUE = 33;
        public static final int ARROW_KEYS_VALUE = 24;
        public static final int AUTOMATED_VALUE = 1;
        public static final int BACK_BUTTON_VALUE = 26;
        public static final int DIRECTIONAL_MOVEMENT_VALUE = 20;
        public static final int DOUBLE_CLICK_VALUE = 34;
        public static final int DOUBLE_TAP_VALUE = 35;
        public static final int DRAG_VALUE = 30;
        public static final int DROP_VALUE = 37;
        public static final int ENTER_PROXIMITY_VALUE = 41;
        public static final int FORCE_TOUCH_VALUE = 38;
        public static final int GENERIC_CLICK_VALUE = 3;
        public static final int HEAD_MOVEMENT_VALUE = 28;
        public static final int HOVER_VALUE = 9;
        public static final int INPUT_KEYBOARD_VALUE = 16;
        public static final int INPUT_TEXT_VALUE = 15;
        public static final int INPUT_VOICE_VALUE = 17;
        public static final int INTO_BOUNDING_BOX_VALUE = 10;
        public static final int KEYBOARD_ENTER_VALUE = 5;
        public static final int KEY_PRESS_VALUE = 32;
        public static final int LEFT_CLICK_VALUE = 7;
        public static final int LONG_PRESS_VALUE = 31;
        public static final int MOUSE_CLICK_VALUE = 6;
        public static final int MOUSE_WHEEL_VALUE = 23;
        public static final int MULTI_KEY_PRESS_VALUE = 39;
        public static final int NAVIGATE_VALUE = 25;
        public static final int OUT_OF_BOUNDING_BOX_VALUE = 11;
        public static final int PINCH_CLOSED_VALUE = 14;
        public static final int PINCH_OPEN_VALUE = 13;
        public static final int PINCH_VALUE = 12;
        public static final int RESIZE_BROWSER_VALUE = 18;
        public static final int RIGHT_CLICK_VALUE = 8;
        public static final int ROLL_VALUE = 36;
        public static final int ROTATE_SCREEN_VALUE = 19;
        public static final int SCROLL_BAR_VALUE = 22;
        public static final int SHAKE_VALUE = 29;
        public static final int SWIPE_VALUE = 21;
        public static final int TAP_VALUE = 4;
        public static final int TWO_FINGER_DRAG_VALUE = 40;
        public static final int UNASSIGNED_USER_ACTION_ID_VALUE = 0;
        public static final int UNKNOWN_ACTION_VALUE = 27;
        public static final int USER_VALUE = 2;
        private static final Internal.EnumLiteMap<UserAction> internalValueMap = new Internal.EnumLiteMap<UserAction>() {
            public UserAction findValueByNumber(int number) {
                return UserAction.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static UserAction forNumber(int value2) {
            switch (value2) {
                case 0:
                    return UNASSIGNED_USER_ACTION_ID;
                case 1:
                    return AUTOMATED;
                case 2:
                    return USER;
                case 3:
                    return GENERIC_CLICK;
                case 4:
                    return TAP;
                case 5:
                    return KEYBOARD_ENTER;
                case 6:
                    return MOUSE_CLICK;
                case 7:
                    return LEFT_CLICK;
                case 8:
                    return RIGHT_CLICK;
                case 9:
                    return HOVER;
                case 10:
                    return INTO_BOUNDING_BOX;
                case 11:
                    return OUT_OF_BOUNDING_BOX;
                case 12:
                    return PINCH;
                case 13:
                    return PINCH_OPEN;
                case 14:
                    return PINCH_CLOSED;
                case 15:
                    return INPUT_TEXT;
                case 16:
                    return INPUT_KEYBOARD;
                case 17:
                    return INPUT_VOICE;
                case 18:
                    return RESIZE_BROWSER;
                case 19:
                    return ROTATE_SCREEN;
                case 20:
                    return DIRECTIONAL_MOVEMENT;
                case 21:
                    return SWIPE;
                case 22:
                    return SCROLL_BAR;
                case 23:
                    return MOUSE_WHEEL;
                case 24:
                    return ARROW_KEYS;
                case 25:
                    return NAVIGATE;
                case 26:
                    return BACK_BUTTON;
                case 27:
                    return UNKNOWN_ACTION;
                case 28:
                    return HEAD_MOVEMENT;
                case 29:
                    return SHAKE;
                case 30:
                    return DRAG;
                case 31:
                    return LONG_PRESS;
                case 32:
                    return KEY_PRESS;
                case 33:
                    return ACTION_BY_TIMER;
                case 34:
                    return DOUBLE_CLICK;
                case 35:
                    return DOUBLE_TAP;
                case 36:
                    return ROLL;
                case 37:
                    return DROP;
                case 38:
                    return FORCE_TOUCH;
                case 39:
                    return MULTI_KEY_PRESS;
                case 40:
                    return TWO_FINGER_DRAG;
                case 41:
                    return ENTER_PROXIMITY;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<UserAction> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return UserActionVerifier.INSTANCE;
        }

        private static final class UserActionVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new UserActionVerifier();

            private UserActionVerifier() {
            }

            public boolean isInRange(int number) {
                return UserAction.forNumber(number) != null;
            }
        }

        private UserAction(int value2) {
            this.value = value2;
        }
    }

    public enum CardinalDirection implements Internal.EnumLite {
        UNASSIGNED_DIRECTIONAL_MOVEMENT_ID(0),
        LEFT(1),
        RIGHT(2),
        UP(3),
        DOWN(4);
        
        public static final int DOWN_VALUE = 4;
        public static final int LEFT_VALUE = 1;
        public static final int RIGHT_VALUE = 2;
        public static final int UNASSIGNED_DIRECTIONAL_MOVEMENT_ID_VALUE = 0;
        public static final int UP_VALUE = 3;
        private static final Internal.EnumLiteMap<CardinalDirection> internalValueMap = new Internal.EnumLiteMap<CardinalDirection>() {
            public CardinalDirection findValueByNumber(int number) {
                return CardinalDirection.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static CardinalDirection forNumber(int value2) {
            if (value2 == 0) {
                return UNASSIGNED_DIRECTIONAL_MOVEMENT_ID;
            }
            if (value2 == 1) {
                return LEFT;
            }
            if (value2 == 2) {
                return RIGHT;
            }
            if (value2 == 3) {
                return UP;
            }
            if (value2 != 4) {
                return null;
            }
            return DOWN;
        }

        public static Internal.EnumLiteMap<CardinalDirection> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return CardinalDirectionVerifier.INSTANCE;
        }

        private static final class CardinalDirectionVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new CardinalDirectionVerifier();

            private CardinalDirectionVerifier() {
            }

            public boolean isInRange(int number) {
                return CardinalDirection.forNumber(number) != null;
            }
        }

        private CardinalDirection(int value2) {
            this.value = value2;
        }
    }
}
