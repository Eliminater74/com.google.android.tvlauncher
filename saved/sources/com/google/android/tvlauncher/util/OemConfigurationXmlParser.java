package com.google.android.tvlauncher.util;

import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.tvlauncher.util.ChannelConfigurationInfo;
import com.google.android.tvlauncher.util.OemConfiguration;
import com.google.android.tvlauncher.util.OemOutOfBoxApp;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

class OemConfigurationXmlParser {
    private static final String ALL_APPS_OOB_ORDER_TAG = "partner-all-apps-out-of-box-order";
    private static final String APPLY_STANDARD_STYLE_TO_INPUT_STATE_ICONS_ATTR = "apply_standard_style_to_input_state_icons";
    private static final String APPS_OOB_CAN_HIDE_ATTR = "can_hide";
    private static final String APPS_OOB_CAN_MOVE_ATTR = "can_move";
    private static final String APPS_VIEW_LAYOUT_OPTION_TAG = "apps-view-layout";
    private static final String APPS_VIEW_LAYOUT_ROWS_ORDER_ATTR = "rows_order";
    private static final String APP_CHANNEL_QUOTA_TAG = "partner-app-channel-quota";
    private static final String APP_NAME_ATTR = "name";
    private static final String APP_TAG = "app";
    private static final String BANNER_ATTR = "banner_uri";
    private static final String BUNDLED_TUNER_BANNER_ATTR = "bundled_tuner_banner";
    private static final String BUNDLED_TUNER_TITLE_ATTR = "bundled_tuner_title";
    private static final String CHANNEL_OOB_CAN_HIDE_ATTR = "can_hide";
    private static final String CHANNEL_OOB_CAN_MOVE_ATTR = "can_move";
    private static final String CHANNEL_OOB_ORDER_TAG = "partner-channels-out-of-box-order";
    private static final String CHANNEL_OOB_TYPE_ATTR = "type";
    private static final String CONFIGURE_CHANNELS_APP_ORDER_TAG = "configure-channels-app-order";
    private static final String DATA_ATTR = "data_uri";
    private static final boolean DEBUG = false;
    private static final String DISABLE_DISCONNECTED_INPUTS_ATTR = "disable_disconnected_inputs";
    private static final String DISCONNECTED_INPUTS_TEXT_ATTR = "disconnected_input_text";
    private static final String ENABLED_BY_DEFAULT_ATTR = "enabled_by_default";
    private static final String ENABLE_AUTO_HIDE_ATTR = "auto_hide_enabled";
    private static final String ENABLE_INPUT_STATE_ICON_ATTR = "enable_input_state_icon";
    private static final String FALSE_VALUE = "false";
    private static final String FAVORITE_APPS_OOB_ORDER_TAG = "partner-favorite-apps-out-of-box-order";
    private static final String FORCE_LAUNCH_PACKAGE_AFTER_BOOT_ATTR = "force_launch";
    private static final String GAMES_OOB_ORDER_TAG = "partner-games-out-of-box-order";
    private static final String HEADSUP_NOTIFS_BACKGROUND_COLOR_ATTR = "background_color";
    private static final String HEADSUP_NOTIFS_FONT_ATTR = "font";
    private static final String HEADSUP_NOTIFS_LOCATION = "location";
    private static final String HEADSUP_NOTIFS_TAG = "headsup-notifications";
    private static final String HEADSUP_NOTIFS_TEXT_COLOR_ATTR = "text_color";
    private static final String HIDE_UNINSTALL_APPS_TAG = "hide-uninstallation-user-action-apps";
    private static final String HOME_BACKGROUND_TAG = "home-background";
    private static final String HOME_BACKGROUND_URI_ATTR = "uri";
    private static final String HOME_SCREEN_INPUTS_ORDERING_TAG = "home-screen-inputs-ordering";
    private static final String INPUTS_CONFIG_TAG = "inputs-configuration";
    private static final String INPUTS_ICON_URI_ATTR = "icon_uri";
    private static final String INPUTS_PANEL_LABEL_OPTION_ATTR = "inputs_panel_label_option";
    private static final String INPUT_TYPE_TAG = "input-type";
    private static final String IS_VIRTUAL_ATTR = "is_virtual_app";
    private static final String LAUNCH_AFTER_BOOT_TAG = "launch-after-boot";
    private static final String LIVE_TV_APP_CHANNEL_TAG = "live-tv-app-channel";
    private static final String LIVE_TV_CHANNEL_OOB_POSITION_ATTR = "out_of_box_position";
    private static final String PACKAGE_NAME_ATTR = "package_name";
    private static final String PACKAGE_NAME_LAUNCH_AFTER_BOOT_ATTR = "package_name";
    private static final String PACKAGE_NAME_TAG = "package-name";
    private static final String PACKAGE_NOTIFICATION_WHILELIST_TAG = "partner-package-notification-whitelist";
    private static final String PROGRAM_MENU_CONFIG_TAG = "program-menu-configuration";
    private static final String QUOTA_ATTR = "quota";
    private static final String ROOT_TAG = "configuration";
    private static final String SEARCH_ICON_TAG = "partner-search-icon";
    private static final String SEARCH_ICON_URI_ATTR = "icon_uri";
    private static final String SEARCH_ORB_FOCUSED_COLOR_ATTR = "background_color";
    private static final String SHOW_ADD_TO_WATCH_NEXT_FROM_PROGRAM_MENU_ATTR = "show_add_to_watch_next";
    private static final String SHOW_INPUTS_ATTR = "show_inputs";
    private static final String SHOW_PHYSICAL_TUNERS_SEPARATELY_ATTR = "show_physical_tuners_separately";
    private static final String SHOW_REMOVE_PROGRAM_FROM_PROGRAM_MENU_ATTR = "show_remove_program";
    private static final String SPONSORED_CHANNEL_TYPE = "sponsored";
    private static final String SYSTEM_CHANNEL_KEY_ATTR = "system_channel_key";
    private static final String TAG = "OemConfigXmlParser";
    private static final String TRUE_VALUE = "true";
    private static final String USE_CUSTOM_INPUT_LIST_ATTR = "use_custom_input_list";
    private static final String WATCH_NEXT_CHANNEL_TAG = "watch-next-channel";
    private final OemConfigurationData mDataContainer;
    private final InputStream mInputStream;
    private final boolean mIsDynamicConfig;
    private final boolean mIsOperatorTier;

    OemConfigurationXmlParser(@NonNull InputStream inputStream, @NonNull OemConfigurationData dataContainer, boolean isOperatorTier, boolean isDynamicConfig) {
        this.mInputStream = inputStream;
        this.mDataContainer = dataContainer;
        this.mIsOperatorTier = isOperatorTier;
        this.mIsDynamicConfig = isDynamicConfig;
    }

    /* access modifiers changed from: package-private */
    public boolean parse() {
        String parentTag;
        ArrayDeque<String> startTags = new ArrayDeque<>();
        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new InputStreamReader(this.mInputStream));
            for (int eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
                if (eventType != 0) {
                    if (eventType == 2) {
                        if (!startTags.isEmpty()) {
                            parentTag = (String) startTags.peek();
                        } else {
                            parentTag = null;
                        }
                        startTags.push(parser.getName());
                        parseTag(parser, parentTag);
                    } else if (eventType == 3) {
                        if (startTags.isEmpty()) {
                            throw new XmlPullParserException("end tag without start tag");
                        } else if (!TextUtils.equals((String) startTags.pop(), parser.getName())) {
                            throw new XmlPullParserException("start and end tags don't match");
                        }
                    }
                }
            }
            if (startTags.isEmpty()) {
                return true;
            }
            throw new XmlPullParserException("missing end tag");
        } catch (IOException | XmlPullParserException e) {
            Log.e(TAG, "Error parsing configuration file", e);
            return false;
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private void parseTag(XmlPullParser parser, String parentTag) throws XmlPullParserException, IOException {
        char c;
        String name = parser.getName();
        switch (name.hashCode()) {
            case -2135709027:
                if (name.equals(LAUNCH_AFTER_BOOT_TAG)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1923341390:
                if (name.equals(PACKAGE_NAME_TAG)) {
                    c = 8;
                    break;
                }
                c = 65535;
                break;
            case -1768284787:
                if (name.equals(LIVE_TV_APP_CHANNEL_TAG)) {
                    c = 12;
                    break;
                }
                c = 65535;
                break;
            case -1722951491:
                if (name.equals(APP_CHANNEL_QUOTA_TAG)) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -1630898555:
                if (name.equals(HOME_SCREEN_INPUTS_ORDERING_TAG)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1107278855:
                if (name.equals(SEARCH_ICON_TAG)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1091921801:
                if (name.equals(APPS_VIEW_LAYOUT_OPTION_TAG)) {
                    c = 10;
                    break;
                }
                c = 65535;
                break;
            case -1042206617:
                if (name.equals(WATCH_NEXT_CHANNEL_TAG)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -874475868:
                if (name.equals(FAVORITE_APPS_OOB_ORDER_TAG)) {
                    c = 13;
                    break;
                }
                c = 65535;
                break;
            case -615296567:
                if (name.equals(HEADSUP_NOTIFS_TAG)) {
                    c = 9;
                    break;
                }
                c = 65535;
                break;
            case -470197540:
                if (name.equals(HOME_BACKGROUND_TAG)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -371424527:
                if (name.equals(PROGRAM_MENU_CONFIG_TAG)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -119178222:
                if (name.equals(INPUTS_CONFIG_TAG)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 96801:
                if (name.equals("app")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 51246093:
                if (name.equals(CHANNEL_OOB_ORDER_TAG)) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 784148646:
                if (name.equals(GAMES_OOB_ORDER_TAG)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 1340516189:
                if (name.equals(INPUT_TYPE_TAG)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1377815793:
                if (name.equals(PACKAGE_NOTIFICATION_WHILELIST_TAG)) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 1815804758:
                if (name.equals(HIDE_UNINSTALL_APPS_TAG)) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 1932752118:
                if (name.equals(ROOT_TAG)) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 2031280428:
                if (name.equals(CONFIGURE_CHANNELS_APP_ORDER_TAG)) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 2042236543:
                if (name.equals(ALL_APPS_OOB_ORDER_TAG)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                String iconUri = parser.getAttributeValue(null, "icon_uri");
                if (iconUri != null) {
                    this.mDataContainer.setSearchIconUri(Uri.parse(iconUri));
                    String focusedColor = parser.getAttributeValue(null, "background_color");
                    if (!TextUtils.isEmpty(focusedColor)) {
                        try {
                            this.mDataContainer.setSearchOrbFocusedColor(Color.parseColor(focusedColor));
                            return;
                        } catch (IllegalArgumentException e) {
                            String valueOf = String.valueOf(focusedColor);
                            Log.e(TAG, valueOf.length() != 0 ? "Cannot decode search orb focused color ".concat(valueOf) : new String("Cannot decode search orb focused color "), e);
                            return;
                        }
                    } else {
                        return;
                    }
                } else {
                    throw new XmlPullParserException("missing icon_uri attribute in partner-search-icon");
                }
            case 1:
                parseInputsConfig(parser);
                return;
            case 2:
                parseProgramMenuConfig(parser);
                return;
            case 3:
                parseWatchNextChannelConfig(parser);
                return;
            case 4:
                parseHomeBackgroundTag(parser);
                return;
            case 5:
                if (!TextUtils.equals(parentTag, INPUTS_CONFIG_TAG)) {
                    throw new XmlPullParserException("home-screen-inputs-ordering must be inside inputs-configuration");
                }
                return;
            case 6:
                if (TextUtils.equals(parentTag, HOME_SCREEN_INPUTS_ORDERING_TAG)) {
                    parser.next();
                    String type = parser.getText();
                    if (type != null) {
                        this.mDataContainer.addInputToHomeScreenInputsOrdering(type);
                        return;
                    }
                    throw new XmlPullParserException("missing input type");
                }
                throw new XmlPullParserException("input-type must be inside home-screen-inputs-ordering");
            case 7:
                parseAppTag(parser, parentTag);
                return;
            case 8:
                parsePackageNameTag(parser, parentTag);
                return;
            case 9:
                parseHeadsupNotifications(parser);
                return;
            case 10:
                parseAppsViewLayoutTag(parser);
                return;
            case 11:
                parseLaunchAfterBootTag(parser);
                return;
            case 12:
                parseLiveTvAppChannel(parser);
                return;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            default:
                return;
        }
    }

    private void parseLaunchAfterBootTag(XmlPullParser parser) throws XmlPullParserException {
        String packageName = parser.getAttributeValue(null, "package_name");
        if (packageName != null) {
            this.mDataContainer.setPackageNameLaunchAfterBoot(packageName);
            Boolean force = getBoolAttr(parser, FORCE_LAUNCH_PACKAGE_AFTER_BOOT_ATTR);
            if (force != null) {
                this.mDataContainer.setForceLaunchPackageAfterBoot(force.booleanValue());
                return;
            }
            return;
        }
        throw new XmlPullParserException("missing package_name attribute in launch-after-boot");
    }

    private void parseAppsViewLayoutTag(XmlPullParser parser) throws XmlPullParserException {
        String optionKey = parser.getAttributeValue(null, APPS_VIEW_LAYOUT_ROWS_ORDER_ATTR);
        if (optionKey != null) {
            OemConfiguration.LayoutOrderOptions option = OemConfiguration.LayoutOrderOptions.getLayoutOptionForRowOrder(optionKey);
            if (option != null) {
                this.mDataContainer.setAppsViewLayoutOption(option);
                return;
            }
            StringBuilder sb = new StringBuilder(String.valueOf(optionKey).length() + 59);
            sb.append("invalid value ");
            sb.append(optionKey);
            sb.append(" for attribute ");
            sb.append(APPS_VIEW_LAYOUT_ROWS_ORDER_ATTR);
            sb.append(" in ");
            sb.append(APPS_VIEW_LAYOUT_OPTION_TAG);
            throw new XmlPullParserException(sb.toString());
        }
        throw new XmlPullParserException("missing rows_order attribute in apps-view-layout");
    }

    private void parseLiveTvAppChannel(XmlPullParser parser) throws XmlPullParserException {
        String packageName = parser.getAttributeValue(null, "package_name");
        if (TextUtils.isEmpty(packageName)) {
            throw new XmlPullParserException("package_name and out_of_box_position are required for live-tv-app-channel");
        } else if (this.mDataContainer.getLiveTvOobPackageInfo() == null) {
            String systemChannelKey = parser.getAttributeValue(null, "system_channel_key");
            int liveTvChannelOobPosition = getIntAttr(parser, LIVE_TV_CHANNEL_OOB_POSITION_ATTR);
            this.mDataContainer.setLiveTvPackageInfo(packageName, systemChannelKey);
            this.mDataContainer.setLiveTvChannelOobPosition(liveTvChannelOobPosition);
        } else {
            Log.e(TAG, "There should be only one tag live-tv-app-channel");
        }
    }

    private void parseInputsConfig(XmlPullParser parser) throws XmlPullParserException {
        String labelText;
        String bundledTunerBanner = parser.getAttributeValue(null, BUNDLED_TUNER_BANNER_ATTR);
        if (bundledTunerBanner != null) {
            this.mDataContainer.setBundledTunerBannerUri(Uri.parse(bundledTunerBanner));
        }
        String bundledTunerTitle = parser.getAttributeValue(null, BUNDLED_TUNER_TITLE_ATTR);
        if (bundledTunerTitle != null) {
            this.mDataContainer.setBundledTunerTitle(bundledTunerTitle);
        }
        Boolean disableDisconnectedInputs = getBoolAttr(parser, DISABLE_DISCONNECTED_INPUTS_ATTR);
        if (disableDisconnectedInputs != null) {
            this.mDataContainer.setDisableDisconnectedInputs(disableDisconnectedInputs.booleanValue());
        }
        String disconnectedInputsText = parser.getAttributeValue(null, DISCONNECTED_INPUTS_TEXT_ATTR);
        if (disconnectedInputsText != null) {
            this.mDataContainer.setDisconnectedInputText(disconnectedInputsText);
        }
        Boolean enableInputStateIcon = getBoolAttr(parser, ENABLE_INPUT_STATE_ICON_ATTR);
        if (enableInputStateIcon != null) {
            this.mDataContainer.setEnableInputStateIcon(enableInputStateIcon.booleanValue());
        }
        Boolean applyStandardStyleToInputStateIcons = getBoolAttr(parser, APPLY_STANDARD_STYLE_TO_INPUT_STATE_ICONS_ATTR);
        if (applyStandardStyleToInputStateIcons != null) {
            this.mDataContainer.setApplyStandardStyleToInputStateIcons(applyStandardStyleToInputStateIcons.booleanValue());
        }
        Boolean showInputs = getBoolAttr(parser, SHOW_INPUTS_ATTR);
        if (showInputs != null) {
            this.mDataContainer.setShowInputs(showInputs.booleanValue());
        }
        String inputsPanelLabelOption = parser.getAttributeValue(null, INPUTS_PANEL_LABEL_OPTION_ATTR);
        if (inputsPanelLabelOption != null) {
            if (inputsPanelLabelOption.equals(OemConfiguration.INPUTS_PANEL_LABEL_INPUTS) || inputsPanelLabelOption.equals(OemConfiguration.INPUTS_PANEL_LABEL_SOURCES)) {
                labelText = inputsPanelLabelOption;
            } else {
                labelText = OemConfiguration.INPUTS_PANEL_LABEL_INPUTS;
                StringBuilder sb = new StringBuilder(String.valueOf(inputsPanelLabelOption).length() + 46);
                sb.append("Invalid value \"");
                sb.append(inputsPanelLabelOption);
                sb.append("\" for ");
                sb.append(INPUTS_PANEL_LABEL_OPTION_ATTR);
                Log.e(TAG, sb.toString());
            }
            this.mDataContainer.setInputsPanelLabelOption(labelText);
        }
        Boolean showPhysicalTunersSeparately = getBoolAttr(parser, SHOW_PHYSICAL_TUNERS_SEPARATELY_ATTR);
        if (showPhysicalTunersSeparately != null) {
            this.mDataContainer.setShowPhysicalInputsSeparately(showPhysicalTunersSeparately.booleanValue());
        }
        Boolean useCustomInputList = getBoolAttr(parser, USE_CUSTOM_INPUT_LIST_ATTR);
        if (useCustomInputList != null) {
            this.mDataContainer.setUseCustomInputList(useCustomInputList.booleanValue());
        }
        String iconUri = parser.getAttributeValue(null, "icon_uri");
        if (iconUri != null) {
            this.mDataContainer.setOemInputsIconUri(Uri.parse(iconUri));
        }
    }

    private void parseHeadsupNotifications(XmlPullParser parser) {
        String location = parser.getAttributeValue(null, HEADSUP_NOTIFS_LOCATION);
        if (location != null) {
            this.mDataContainer.setHeadsupNotificationsLocation(location);
        }
        String font = parser.getAttributeValue(null, HEADSUP_NOTIFS_FONT_ATTR);
        if (font != null) {
            this.mDataContainer.setHeadsupNotificationsFont(font);
        }
        String textColor = parser.getAttributeValue(null, HEADSUP_NOTIFS_TEXT_COLOR_ATTR);
        if (textColor != null) {
            try {
                this.mDataContainer.setHeadsupNotificationsTextColor(Color.parseColor(textColor));
            } catch (IllegalArgumentException e) {
                String valueOf = String.valueOf(textColor);
                Log.e(TAG, valueOf.length() != 0 ? "Cannot decode notification text color ".concat(valueOf) : new String("Cannot decode notification text color "), e);
            }
        }
        String backgroundColor = parser.getAttributeValue(null, "background_color");
        if (backgroundColor != null) {
            try {
                this.mDataContainer.setHeadsupNotificationsBackgroundColor(Color.parseColor(backgroundColor));
            } catch (IllegalArgumentException e2) {
                String valueOf2 = String.valueOf(backgroundColor);
                Log.e(TAG, valueOf2.length() != 0 ? "Cannot decode notification background color ".concat(valueOf2) : new String("Cannot decode notification background color "), e2);
            }
        }
    }

    private void parseAppTag(XmlPullParser parser, String parentTag) throws XmlPullParserException {
        OemOutOfBoxApp oemApp = parseAppAttributes(parser, GAMES_OOB_ORDER_TAG.equals(parentTag));
        if (ALL_APPS_OOB_ORDER_TAG.equals(parentTag) || GAMES_OOB_ORDER_TAG.equals(parentTag)) {
            this.mDataContainer.addAppToVirtualAppsOutOfBoxOrdering(oemApp);
        }
        String name = oemApp.getAppName();
        String packageName = oemApp.getId();
        char c = 65535;
        switch (parentTag.hashCode()) {
            case -1722951491:
                if (parentTag.equals(APP_CHANNEL_QUOTA_TAG)) {
                    c = 3;
                    break;
                }
                break;
            case -874475868:
                if (parentTag.equals(FAVORITE_APPS_OOB_ORDER_TAG)) {
                    c = 0;
                    break;
                }
                break;
            case 784148646:
                if (parentTag.equals(GAMES_OOB_ORDER_TAG)) {
                    c = 2;
                    break;
                }
                break;
            case 2042236543:
                if (parentTag.equals(ALL_APPS_OOB_ORDER_TAG)) {
                    c = 1;
                    break;
                }
                break;
        }
        if (c != 0) {
            if (c != 1) {
                if (c != 2) {
                    if (c == 3) {
                        int quota = getIntAttr(parser, QUOTA_ATTR);
                        if (!TextUtils.isEmpty(packageName)) {
                            this.mDataContainer.addAppToAppChannelQuota(packageName, quota);
                            return;
                        }
                        throw new XmlPullParserException("missing or empty attributes for app in partner-app-channel-quota , package_name and quota are required.");
                    }
                } else if (TextUtils.isEmpty(name) != 0 || TextUtils.isEmpty(packageName)) {
                    throw new XmlPullParserException("missing or empty attributes for app in partner-games-out-of-box-order , name and package_name are required.");
                } else {
                    this.mDataContainer.addAppToGamesOutOfBoxOrdering(name, packageName);
                }
            } else if (TextUtils.isEmpty(name) || TextUtils.isEmpty(packageName)) {
                throw new XmlPullParserException("missing or empty attributes for app in partner-all-apps-out-of-box-order , name and package_name are required.");
            } else {
                this.mDataContainer.addAppToAllAppsOutOfBoxOrdering(name, packageName);
            }
        } else if (TextUtils.isEmpty(name) || TextUtils.isEmpty(packageName)) {
            throw new XmlPullParserException("missing or empty attributes for app in partner-favorite-apps-out-of-box-order , name and package_name are required.");
        } else if (oemApp.canMove() || oemApp.canHide()) {
            if (!this.mDataContainer.addAppToFavoriteAppsOutOfBoxOrdering(name, packageName)) {
                Log.e(TAG, "Cannot add to favorites because of non-unique pinned/favorite apps.");
            }
        } else if (!this.mDataContainer.getFavoriteAppsOutOfBoxOrdering().isEmpty()) {
            Log.e(TAG, "Cannot add pinned app because it was not in first two position of out of box ordering");
        } else if (!this.mDataContainer.addAppToPinnedFavoriteApps(name, packageName)) {
            Log.e(TAG, "Cannot add pinned app due to having too many pinned apps, or non-unique pinned/favorite apps.");
        }
    }

    private OemOutOfBoxApp parseAppAttributes(XmlPullParser parser, boolean isGame) throws XmlPullParserException {
        OemOutOfBoxApp.Builder oemAppBuilder = new OemOutOfBoxApp.Builder();
        oemAppBuilder.setAppName(parser.getAttributeValue(null, "name"));
        oemAppBuilder.setPackageName(parser.getAttributeValue(null, "package_name"));
        oemAppBuilder.setBannerUri(parser.getAttributeValue(null, BANNER_ATTR));
        oemAppBuilder.setDataUri(parser.getAttributeValue(null, DATA_ATTR));
        oemAppBuilder.setGame(isGame);
        Boolean isVirtual = getBoolAttr(parser, IS_VIRTUAL_ATTR);
        if (isVirtual != null) {
            oemAppBuilder.setVirtualApp(isVirtual.booleanValue());
        }
        Boolean canMove = getBoolAttr(parser, "can_move");
        Boolean canHide = getBoolAttr(parser, "can_hide");
        boolean z = true;
        oemAppBuilder.setCanMove(canMove == null ? true : canMove.booleanValue());
        if (canHide != null) {
            z = canHide.booleanValue();
        }
        oemAppBuilder.setCanHide(z);
        return oemAppBuilder.build();
    }

    private void parseProgramMenuConfig(XmlPullParser parser) throws XmlPullParserException {
        Boolean showAddToPlayNextFromProgramMenu = getBoolAttr(parser, SHOW_ADD_TO_WATCH_NEXT_FROM_PROGRAM_MENU_ATTR);
        if (showAddToPlayNextFromProgramMenu != null) {
            this.mDataContainer.setShowAddToWatchNextFromProgramMenu(showAddToPlayNextFromProgramMenu.booleanValue());
        }
        Boolean showRemoveProgramFromProgramMenu = getBoolAttr(parser, SHOW_REMOVE_PROGRAM_FROM_PROGRAM_MENU_ATTR);
        if (showRemoveProgramFromProgramMenu != null) {
            this.mDataContainer.setShowRemoveProgramFromProgramMenu(showRemoveProgramFromProgramMenu.booleanValue());
        }
    }

    private void parseWatchNextChannelConfig(XmlPullParser parser) throws XmlPullParserException {
        Boolean watchNextChannelEnabledByDefault = getBoolAttr(parser, ENABLED_BY_DEFAULT_ATTR);
        if (watchNextChannelEnabledByDefault != null) {
            this.mDataContainer.setWatchNextChannelEnabledByDefault(watchNextChannelEnabledByDefault.booleanValue());
        }
        Boolean watchNextAutoHideEnabled = getBoolAttr(parser, ENABLE_AUTO_HIDE_ATTR);
        if (watchNextAutoHideEnabled != null) {
            this.mDataContainer.setWatchNextChannelAutoHideEnabled(watchNextAutoHideEnabled.booleanValue());
        }
    }

    private void parseHomeBackgroundTag(XmlPullParser parser) {
        this.mDataContainer.setHomeBackgroundUri(parser.getAttributeValue(null, "uri"));
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private void parsePackageNameTag(XmlPullParser parser, String parentTag) throws XmlPullParserException, IOException {
        char c;
        switch (parentTag.hashCode()) {
            case 51246093:
                if (parentTag.equals(CHANNEL_OOB_ORDER_TAG)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1377815793:
                if (parentTag.equals(PACKAGE_NOTIFICATION_WHILELIST_TAG)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1815804758:
                if (parentTag.equals(HIDE_UNINSTALL_APPS_TAG)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 2031280428:
                if (parentTag.equals(CONFIGURE_CHANNELS_APP_ORDER_TAG)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            String systemChannelKey = parser.getAttributeValue(null, "system_channel_key");
            String type = parser.getAttributeValue(null, "type");
            boolean canMove = getBoolAttr(parser, "can_move", true);
            boolean canHide = getBoolAttr(parser, "can_hide", true);
            parser.next();
            String packageName = parser.getText();
            if (!TextUtils.isEmpty(packageName)) {
                ChannelConfigurationInfo.Builder channelInfoBuilder = new ChannelConfigurationInfo.Builder().setPackageName(packageName).setSystemChannelKey(systemChannelKey);
                if ("sponsored".equalsIgnoreCase(type)) {
                    if (!TextUtils.isEmpty(systemChannelKey)) {
                        channelInfoBuilder.setSponsored(true);
                    } else {
                        StringBuilder sb = new StringBuilder(String.valueOf(packageName).length() + 79);
                        sb.append("Empty system_channel_key for package ");
                        sb.append(packageName);
                        sb.append(" in the ");
                        sb.append(CHANNEL_OOB_ORDER_TAG);
                        sb.append(".");
                        throw new XmlPullParserException(sb.toString());
                    }
                }
                if (channelInfoBuilder.isSponsored() || this.mIsOperatorTier) {
                    if (canHide ^ canMove) {
                        StringBuilder sb2 = new StringBuilder(113);
                        sb2.append("Combination of can_move=");
                        sb2.append(canMove);
                        sb2.append(" and ");
                        sb2.append("can_hide");
                        sb2.append("=");
                        sb2.append(canHide);
                        sb2.append(" is not supported. Only both \"true\" or both \"false\" are supported");
                        Log.e(TAG, sb2.toString());
                    } else {
                        channelInfoBuilder.setCanMove(canMove);
                        channelInfoBuilder.setCanHide(canHide);
                    }
                }
                this.mDataContainer.addChannelToOutOfBoxOrdering(channelInfoBuilder);
                return;
            }
            throw new XmlPullParserException("Empty package name for partner-channels-out-of-box-order");
        } else if (c == 1) {
            parser.next();
            String packageName2 = parser.getText();
            if (!TextUtils.isEmpty(packageName2)) {
                this.mDataContainer.addPackageToPackageNotificationWhitelist(packageName2);
                return;
            }
            throw new XmlPullParserException("Empty package name for partner-package-notification-whitelist");
        } else if (c == 2) {
            parser.next();
            String packageName3 = parser.getText();
            if (!TextUtils.isEmpty(packageName3)) {
                this.mDataContainer.addAppToConfigureChannelsAppOrdering(packageName3);
                return;
            }
            throw new XmlPullParserException("Empty package name for configure-channels-app-order");
        } else if (c != 3) {
            parser.next();
        } else {
            parser.next();
            if (this.mIsDynamicConfig) {
                if (!this.mDataContainer.getHiddenUninstallPackageList().isEmpty()) {
                    Log.e(TAG, "Not accepting multiple packages in hide-uninstallation-user-action-apps");
                    return;
                }
                String packageName4 = parser.getText();
                if (!TextUtils.isEmpty(packageName4)) {
                    this.mDataContainer.addPackageToHiddenUninstallList(packageName4);
                    return;
                }
                throw new XmlPullParserException("Empty package name for hide-uninstallation-user-action-apps");
            }
        }
    }

    private boolean getBoolAttr(XmlPullParser parser, String attrName, boolean defaultValue) throws XmlPullParserException {
        Boolean value = getBoolAttr(parser, attrName);
        return value != null ? value.booleanValue() : defaultValue;
    }

    private Boolean getBoolAttr(XmlPullParser parser, String attrName) throws XmlPullParserException {
        String val = parser.getAttributeValue(null, attrName);
        if (val == null) {
            return null;
        }
        String val2 = val.trim();
        if (val2.equalsIgnoreCase(TRUE_VALUE)) {
            return true;
        }
        if (val2.equalsIgnoreCase(FALSE_VALUE)) {
            return false;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(val2).length() + 47 + String.valueOf(attrName).length());
        sb.append("Invalid boolean value ");
        sb.append(val2);
        sb.append(" specified for attribute ");
        sb.append(attrName);
        throw new XmlPullParserException(sb.toString());
    }

    private int getIntAttr(XmlPullParser parser, String attrName) throws XmlPullParserException {
        String val = parser.getAttributeValue(null, attrName);
        if (val != null) {
            try {
                return Integer.parseInt(val.trim());
            } catch (NumberFormatException e) {
                StringBuilder sb = new StringBuilder(String.valueOf(val).length() + 37 + String.valueOf(attrName).length());
                sb.append("wrong attribute value ");
                sb.append(val);
                sb.append(" for attribute ");
                sb.append(attrName);
                throw new XmlPullParserException(sb.toString());
            }
        } else {
            String valueOf = String.valueOf(attrName);
            throw new XmlPullParserException(valueOf.length() != 0 ? "missing value for attribute ".concat(valueOf) : new String("missing value for attribute "));
        }
    }
}
