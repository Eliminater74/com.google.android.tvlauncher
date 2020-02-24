package com.google.android.tvlauncher.util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.tvlauncher.C1188R;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

class OemAppPromotionsXmlParser {
    private static final String APP_PROMOTION_TAG = "app";
    private static final String CATEGORY_KEY_ACTION_GAMES = "action_games";
    private static final String CATEGORY_KEY_ADVENTURE_GAMES = "adventure_games";
    private static final String CATEGORY_KEY_ARCADE_GAMES = "arcade_games";
    private static final String CATEGORY_KEY_ART_AND_DESIGN = "art_and_design";
    private static final String CATEGORY_KEY_AUTO_AND_VEHICLES = "auto_and_vehicles";
    private static final String CATEGORY_KEY_BEAUTY = "beauty";
    private static final String CATEGORY_KEY_BOARD_GAMES = "board_games";
    private static final String CATEGORY_KEY_BOOKS_AND_REFERENCE = "books_and_reference";
    private static final String CATEGORY_KEY_BUSINESS = "business";
    private static final String CATEGORY_KEY_CARD_GAMES = "card_games";
    private static final String CATEGORY_KEY_CASINO_GAMES = "casino_games";
    private static final String CATEGORY_KEY_CASUAL_GAMES = "casual_games";
    private static final String CATEGORY_KEY_COMICS = "comics";
    private static final String CATEGORY_KEY_COMMUNICATION = "communication";
    private static final String CATEGORY_KEY_DATING = "dating";
    private static final String CATEGORY_KEY_EDUCATION = "education";
    private static final String CATEGORY_KEY_EDUCATIONAL_GAMES = "educational_games";
    private static final String CATEGORY_KEY_ENTERTAINMENT = "entertainment";
    private static final String CATEGORY_KEY_EVENTS = "events";
    private static final String CATEGORY_KEY_FINANCE = "finance";
    private static final String CATEGORY_KEY_FOOD_AND_DRINK = "food_and_drink";
    private static final String CATEGORY_KEY_HEALTH_AND_FITNESS = "health_and_fitness";
    private static final String CATEGORY_KEY_HOUSE_AND_HOME = "house_and_home";
    private static final String CATEGORY_KEY_LIBRARIES_AND_DEMO = "libraries_and_demo";
    private static final String CATEGORY_KEY_LIFESTYLE = "lifestyle";
    private static final String CATEGORY_KEY_MAPS_AND_NAVIGATION = "maps_and_navigation";
    private static final String CATEGORY_KEY_MEDICAL = "medical";
    private static final String CATEGORY_KEY_MUSIC_AND_AUDIO = "music_and_audio";
    private static final String CATEGORY_KEY_MUSIC_GAMES = "music_games";
    private static final String CATEGORY_KEY_NEWS_AND_MAGAZINES = "news_and_magazines";
    private static final String CATEGORY_KEY_PARENTING = "parenting";
    private static final String CATEGORY_KEY_PERSONALIZATION = "personalization";
    private static final String CATEGORY_KEY_PHOTOGRAPHY = "photography";
    private static final String CATEGORY_KEY_PRODUCTIVITY = "productivity";
    private static final String CATEGORY_KEY_PUZZLE_GAMES = "puzzle_games";
    private static final String CATEGORY_KEY_RACING_GAMES = "racing_games";
    private static final String CATEGORY_KEY_ROLE_PLAYING_GAMES = "role_playing_games";
    private static final String CATEGORY_KEY_SHOPPING = "shopping";
    private static final String CATEGORY_KEY_SIMULATION_GAMES = "simulation_games";
    private static final String CATEGORY_KEY_SOCIAL = "social";
    private static final String CATEGORY_KEY_SPORTS = "sports";
    private static final String CATEGORY_KEY_SPORT_GAMES = "sport_games";
    private static final String CATEGORY_KEY_STRATEGY_GAMES = "strategy_games";
    private static final String CATEGORY_KEY_TOOLS = "tools";
    private static final String CATEGORY_KEY_TRAVEL_AND_LOCAL = "travel_and_local";
    private static final String CATEGORY_KEY_TRIVIA_GAMES = "trivia_games";
    private static final String CATEGORY_KEY_VIDEO_PLAYERS_AND_EDITORS = "video_players_and_editors";
    private static final String CATEGORY_KEY_WEATHER = "weather";
    private static final String CATEGORY_KEY_WORD_GAMES = "word_games";
    private static final boolean DEBUG = false;
    private static final int MAX_PROMOTIONS = 100;
    private static final String PROMOTIONS_ROW_TITLE_ATTR = "row_title";
    private static final String PROMOTION_BANNER_ATTR = "banner_uri";
    private static final String PROMOTION_CATEGORY_ATTR = "category";
    private static final String PROMOTION_DATA_ATTR = "data_uri";
    private static final String PROMOTION_DESCRIPTION_ATTR = "description";
    private static final String PROMOTION_DEVELOPER_ATTR = "developer";
    private static final String PROMOTION_IS_GAME_ATTR = "is_game";
    private static final String PROMOTION_IS_VIRTUAL_ATTR = "is_virtual_app";
    private static final String PROMOTION_NAME_ATTR = "name";
    private static final String PROMOTION_PACKAGE_NAME_ATTR = "package_name";
    private static final String PROMOTION_SCREENSHOT_TAG = "screenshot";
    private static final String PROMOTION_SCREENSHOT_URI_ATTR = "uri";
    private static final String ROOT_TAG = "app-recommendations";
    private static final String TAG = "OemAppPromosXmlParser";
    private static final String TRUE_VALUE = "true";
    private static final Map<String, String> sCategoryMap = new HashMap();
    private static OemAppPromotionsXmlParser sOemAppPromotionsXmlParser;

    OemAppPromotionsXmlParser() {
    }

    static class PromotionsData {
        private HashSet<OemPromotionApp> mDedupeCheckSet = new HashSet<>();
        private List<OemPromotionApp> mPromotions = new ArrayList();
        private String mRowTitle;

        PromotionsData() {
        }

        /* access modifiers changed from: package-private */
        public void setRowTitle(String title) {
            this.mRowTitle = title;
        }

        /* access modifiers changed from: package-private */
        public String getRowTitle() {
            return this.mRowTitle;
        }

        /* access modifiers changed from: package-private */
        public void add(OemPromotionApp promotion) {
            if (!this.mDedupeCheckSet.contains(promotion)) {
                this.mPromotions.add(promotion);
                this.mDedupeCheckSet.add(promotion);
            }
        }

        /* access modifiers changed from: package-private */
        public List<OemPromotionApp> getPromotions() {
            return this.mPromotions;
        }

        /* access modifiers changed from: package-private */
        public int size() {
            return this.mPromotions.size();
        }
    }

    private static class ParentPromotion {
        private OemPromotionApp mPromotion;

        private ParentPromotion() {
        }

        /* access modifiers changed from: package-private */
        public OemPromotionApp getPromotion() {
            return this.mPromotion;
        }

        /* access modifiers changed from: package-private */
        public void setPromotion(OemPromotionApp promotion) {
            this.mPromotion = promotion;
        }
    }

    static OemAppPromotionsXmlParser getInstance(Context context) {
        if (sOemAppPromotionsXmlParser == null) {
            synchronized (OemAppPromotionsXmlParser.class) {
                if (sOemAppPromotionsXmlParser == null) {
                    initializeCategoryMap(context);
                    sOemAppPromotionsXmlParser = new OemAppPromotionsXmlParser();
                }
            }
        }
        return sOemAppPromotionsXmlParser;
    }

    /* access modifiers changed from: package-private */
    public PromotionsData parse(@NonNull InputStream inputStream) {
        PromotionsData promotions = new PromotionsData();
        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new InputStreamReader(inputStream));
            ArrayDeque<String> startTags = new ArrayDeque<>(2);
            int eventType = parser.getEventType();
            boolean error = false;
            ParentPromotion parentPromotion = new ParentPromotion();
            while (!error && eventType != 1) {
                if (eventType != 0) {
                    if (eventType == 2) {
                        startTags.push(parser.getName());
                        if (!parseTag(parser, promotions, parentPromotion)) {
                            error = true;
                        }
                    } else if (eventType == 3) {
                        if (startTags.isEmpty()) {
                            throw new XmlPullParserException("end tag without start tag");
                        } else if (!TextUtils.equals((String) startTags.pop(), parser.getName())) {
                            throw new XmlPullParserException("start and end tags don't match");
                        }
                    }
                }
                eventType = parser.next();
            }
            return promotions;
        } catch (IOException | XmlPullParserException e) {
            Log.e(TAG, "Error parsing configuration file", e);
            return null;
        }
    }

    /* JADX INFO: Multiple debug info for r4v5 com.google.android.tvlauncher.util.OemPromotionApp: [D('title' java.lang.String), D('parent' com.google.android.tvlauncher.util.OemPromotionApp)] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean parseTag(org.xmlpull.v1.XmlPullParser r24, com.google.android.tvlauncher.util.OemAppPromotionsXmlParser.PromotionsData r25, com.google.android.tvlauncher.util.OemAppPromotionsXmlParser.ParentPromotion r26) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r0 = r24
            r1 = r25
            r2 = r26
            java.lang.String r3 = r24.getName()
            int r4 = r3.hashCode()
            r5 = -416447130(0xffffffffe72d8566, float:-8.194302E23)
            r6 = 0
            r7 = 2
            r8 = 1
            if (r4 == r5) goto L_0x0035
            r5 = 96801(0x17a21, float:1.35647E-40)
            if (r4 == r5) goto L_0x002b
            r5 = 1907281966(0x71aed02e, float:1.7312661E30)
            if (r4 == r5) goto L_0x0021
        L_0x0020:
            goto L_0x0040
        L_0x0021:
            java.lang.String r4 = "app-recommendations"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0020
            r4 = 2
            goto L_0x0041
        L_0x002b:
            java.lang.String r4 = "app"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0020
            r4 = 0
            goto L_0x0041
        L_0x0035:
            java.lang.String r4 = "screenshot"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0020
            r4 = 1
            goto L_0x0041
        L_0x0040:
            r4 = -1
        L_0x0041:
            java.lang.String r5 = "OemAppPromosXmlParser"
            r9 = 0
            if (r4 == 0) goto L_0x00a4
            if (r4 == r8) goto L_0x0086
            if (r4 == r7) goto L_0x004e
            r16 = r3
            goto L_0x023f
        L_0x004e:
            java.lang.String r4 = "row_title"
            java.lang.String r4 = r0.getAttributeValue(r9, r4)
            boolean r7 = android.text.TextUtils.isEmpty(r4)
            if (r7 != 0) goto L_0x0062
            r1.setRowTitle(r4)
            r16 = r3
            goto L_0x023f
        L_0x0062:
            java.lang.String r7 = java.lang.String.valueOf(r3)
            int r7 = r7.length()
            int r7 = r7 + 29
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r7)
            java.lang.String r7 = "Must supply row_title in "
            r8.append(r7)
            r8.append(r3)
            java.lang.String r7 = " tag"
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            android.util.Log.e(r5, r7)
            return r6
        L_0x0086:
            com.google.android.tvlauncher.util.OemPromotionApp r4 = r26.getPromotion()
            if (r4 == 0) goto L_0x00a0
            java.lang.String r5 = "uri"
            android.net.Uri r5 = getUriAttr(r0, r5)
            if (r5 == 0) goto L_0x009c
            java.lang.String r6 = r5.toString()
            r4.addScreenshotUri(r6)
        L_0x009c:
            r16 = r3
            goto L_0x023f
        L_0x00a0:
            r16 = r3
            goto L_0x023f
        L_0x00a4:
            int r4 = r25.size()
            r6 = 100
            if (r4 >= r6) goto L_0x023d
            java.lang.String r4 = "name"
            java.lang.String r4 = r0.getAttributeValue(r9, r4)
            java.lang.String r6 = "package_name"
            java.lang.String r7 = r0.getAttributeValue(r9, r6)
            java.lang.String r10 = "banner_uri"
            android.net.Uri r11 = getUriAttr(r0, r10)
            java.lang.String r12 = "data_uri"
            android.net.Uri r13 = getUriAttr(r0, r12)
            java.lang.String r14 = "is_game"
            boolean r15 = getBoolAttr(r0, r14)
            java.lang.String r8 = "is_virtual_app"
            boolean r9 = getBoolAttr(r0, r8)
            r16 = r3
            java.lang.String r3 = "developer"
            r17 = r5
            r18 = r8
            r5 = 0
            java.lang.String r8 = r0.getAttributeValue(r5, r3)
            r19 = r3
            java.lang.String r3 = "category"
            r20 = r14
            java.lang.String r14 = r0.getAttributeValue(r5, r3)
            r21 = r3
            java.lang.String r3 = "description"
            r22 = r12
            java.lang.String r12 = r0.getAttributeValue(r5, r3)
            if (r4 == 0) goto L_0x0164
            if (r7 == 0) goto L_0x0164
            if (r11 == 0) goto L_0x0164
            if (r9 == 0) goto L_0x0109
            if (r13 == 0) goto L_0x0164
            if (r8 == 0) goto L_0x0164
            if (r14 == 0) goto L_0x0164
            java.util.Map<java.lang.String, java.lang.String> r5 = com.google.android.tvlauncher.util.OemAppPromotionsXmlParser.sCategoryMap
            boolean r5 = r5.containsKey(r14)
            if (r5 == 0) goto L_0x0164
            if (r12 == 0) goto L_0x0164
        L_0x0109:
            com.google.android.tvlauncher.util.OemPromotionApp$Builder r3 = new com.google.android.tvlauncher.util.OemPromotionApp$Builder
            r3.<init>()
            com.google.android.tvlauncher.util.OemAppBase$Builder r3 = r3.setAppName(r4)
            com.google.android.tvlauncher.util.OemPromotionApp$Builder r3 = (com.google.android.tvlauncher.util.OemPromotionApp.Builder) r3
            com.google.android.tvlauncher.util.OemAppBase$Builder r3 = r3.setPackageName(r7)
            com.google.android.tvlauncher.util.OemPromotionApp$Builder r3 = (com.google.android.tvlauncher.util.OemPromotionApp.Builder) r3
            java.lang.String r5 = r11.toString()
            com.google.android.tvlauncher.util.OemAppBase$Builder r3 = r3.setBannerUri(r5)
            com.google.android.tvlauncher.util.OemPromotionApp$Builder r3 = (com.google.android.tvlauncher.util.OemPromotionApp.Builder) r3
            if (r13 != 0) goto L_0x0128
            r5 = 0
            goto L_0x012c
        L_0x0128:
            java.lang.String r5 = r13.toString()
        L_0x012c:
            com.google.android.tvlauncher.util.OemAppBase$Builder r3 = r3.setDataUri(r5)
            com.google.android.tvlauncher.util.OemPromotionApp$Builder r3 = (com.google.android.tvlauncher.util.OemPromotionApp.Builder) r3
            com.google.android.tvlauncher.util.OemAppBase$Builder r3 = r3.setDeveloper(r8)
            com.google.android.tvlauncher.util.OemPromotionApp$Builder r3 = (com.google.android.tvlauncher.util.OemPromotionApp.Builder) r3
            java.util.Map<java.lang.String, java.lang.String> r5 = com.google.android.tvlauncher.util.OemAppPromotionsXmlParser.sCategoryMap
            java.lang.Object r5 = r5.get(r14)
            java.lang.String r5 = (java.lang.String) r5
            com.google.android.tvlauncher.util.OemAppBase$Builder r3 = r3.setCategory(r5)
            com.google.android.tvlauncher.util.OemPromotionApp$Builder r3 = (com.google.android.tvlauncher.util.OemPromotionApp.Builder) r3
            com.google.android.tvlauncher.util.OemAppBase$Builder r3 = r3.setDescription(r12)
            com.google.android.tvlauncher.util.OemPromotionApp$Builder r3 = (com.google.android.tvlauncher.util.OemPromotionApp.Builder) r3
            com.google.android.tvlauncher.util.OemAppBase$Builder r3 = r3.setGame(r15)
            com.google.android.tvlauncher.util.OemPromotionApp$Builder r3 = (com.google.android.tvlauncher.util.OemPromotionApp.Builder) r3
            com.google.android.tvlauncher.util.OemAppBase$Builder r3 = r3.setVirtualApp(r9)
            com.google.android.tvlauncher.util.OemPromotionApp$Builder r3 = (com.google.android.tvlauncher.util.OemPromotionApp.Builder) r3
            com.google.android.tvlauncher.util.OemPromotionApp r3 = r3.build()
            r1.add(r3)
            r2.setPromotion(r3)
            goto L_0x023c
        L_0x0164:
            java.lang.String r5 = java.lang.String.valueOf(r11)
            java.lang.String r0 = java.lang.String.valueOf(r13)
            java.lang.String r23 = java.lang.String.valueOf(r4)
            int r1 = r23.length()
            int r1 = r1 + 171
            java.lang.String r23 = java.lang.String.valueOf(r7)
            int r23 = r23.length()
            int r1 = r1 + r23
            java.lang.String r23 = java.lang.String.valueOf(r5)
            int r23 = r23.length()
            int r1 = r1 + r23
            java.lang.String r23 = java.lang.String.valueOf(r0)
            int r23 = r23.length()
            int r1 = r1 + r23
            java.lang.String r23 = java.lang.String.valueOf(r8)
            int r23 = r23.length()
            int r1 = r1 + r23
            java.lang.String r23 = java.lang.String.valueOf(r14)
            int r23 = r23.length()
            int r1 = r1 + r23
            java.lang.String r23 = java.lang.String.valueOf(r12)
            int r23 = r23.length()
            int r1 = r1 + r23
            r23 = r11
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>(r1)
            java.lang.String r1 = "Missing data in promotion: name : "
            r11.append(r1)
            r11.append(r4)
            java.lang.String r1 = " , "
            r11.append(r1)
            r11.append(r6)
            java.lang.String r6 = " : "
            r11.append(r6)
            r11.append(r7)
            r11.append(r1)
            r11.append(r10)
            r11.append(r6)
            r11.append(r5)
            r11.append(r1)
            r5 = r22
            r11.append(r5)
            r11.append(r6)
            r11.append(r0)
            r11.append(r1)
            r0 = r20
            r11.append(r0)
            r11.append(r6)
            r11.append(r15)
            r11.append(r1)
            r0 = r18
            r11.append(r0)
            r11.append(r6)
            r11.append(r9)
            r11.append(r1)
            r0 = r19
            r11.append(r0)
            r11.append(r6)
            r11.append(r8)
            r11.append(r1)
            r0 = r21
            r11.append(r0)
            r11.append(r6)
            r11.append(r14)
            r11.append(r1)
            r11.append(r3)
            r11.append(r6)
            r11.append(r12)
            java.lang.String r0 = r11.toString()
            r1 = r17
            android.util.Log.e(r1, r0)
            r0 = 0
            r2.setPromotion(r0)
        L_0x023c:
            goto L_0x023f
        L_0x023d:
            r16 = r3
        L_0x023f:
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.util.OemAppPromotionsXmlParser.parseTag(org.xmlpull.v1.XmlPullParser, com.google.android.tvlauncher.util.OemAppPromotionsXmlParser$PromotionsData, com.google.android.tvlauncher.util.OemAppPromotionsXmlParser$ParentPromotion):boolean");
    }

    @Nullable
    private static Uri getUriAttr(XmlPullParser parser, String attrName) {
        String uri = parser.getAttributeValue(null, attrName);
        if (uri != null) {
            return Uri.parse(uri);
        }
        return null;
    }

    private static boolean getBoolAttr(XmlPullParser parser, String attrName) {
        String val = parser.getAttributeValue(null, attrName);
        return val != null && val.trim().equalsIgnoreCase(TRUE_VALUE);
    }

    private static void initializeCategoryMap(Context context) {
        sCategoryMap.put(CATEGORY_KEY_ART_AND_DESIGN, context.getString(C1188R.string.category_art_and_design));
        sCategoryMap.put(CATEGORY_KEY_AUTO_AND_VEHICLES, context.getString(C1188R.string.category_auto_and_vehicles));
        sCategoryMap.put(CATEGORY_KEY_BEAUTY, context.getString(C1188R.string.category_beauty));
        sCategoryMap.put(CATEGORY_KEY_BOOKS_AND_REFERENCE, context.getString(C1188R.string.category_books_and_reference));
        sCategoryMap.put(CATEGORY_KEY_BUSINESS, context.getString(C1188R.string.category_business));
        sCategoryMap.put(CATEGORY_KEY_COMICS, context.getString(C1188R.string.category_comics));
        sCategoryMap.put(CATEGORY_KEY_COMMUNICATION, context.getString(C1188R.string.category_communication));
        sCategoryMap.put(CATEGORY_KEY_DATING, context.getString(C1188R.string.category_dating));
        sCategoryMap.put(CATEGORY_KEY_EDUCATION, context.getString(C1188R.string.category_education));
        sCategoryMap.put(CATEGORY_KEY_ENTERTAINMENT, context.getString(C1188R.string.category_entertainment));
        sCategoryMap.put(CATEGORY_KEY_EVENTS, context.getString(C1188R.string.category_events));
        sCategoryMap.put(CATEGORY_KEY_FINANCE, context.getString(C1188R.string.category_finance));
        sCategoryMap.put(CATEGORY_KEY_FOOD_AND_DRINK, context.getString(C1188R.string.category_food_and_drink));
        sCategoryMap.put(CATEGORY_KEY_HEALTH_AND_FITNESS, context.getString(C1188R.string.category_health_and_fitness));
        sCategoryMap.put(CATEGORY_KEY_HOUSE_AND_HOME, context.getString(C1188R.string.category_house_and_home));
        sCategoryMap.put(CATEGORY_KEY_LIFESTYLE, context.getString(C1188R.string.category_lifestyle));
        sCategoryMap.put(CATEGORY_KEY_MAPS_AND_NAVIGATION, context.getString(C1188R.string.category_maps_and_navigation));
        sCategoryMap.put(CATEGORY_KEY_MEDICAL, context.getString(C1188R.string.category_medical));
        sCategoryMap.put(CATEGORY_KEY_MUSIC_AND_AUDIO, context.getString(C1188R.string.category_music_and_audio));
        sCategoryMap.put(CATEGORY_KEY_NEWS_AND_MAGAZINES, context.getString(C1188R.string.category_news_and_magazines));
        sCategoryMap.put(CATEGORY_KEY_PARENTING, context.getString(C1188R.string.category_parenting));
        sCategoryMap.put(CATEGORY_KEY_PERSONALIZATION, context.getString(C1188R.string.category_personalization));
        sCategoryMap.put(CATEGORY_KEY_PHOTOGRAPHY, context.getString(C1188R.string.category_photography));
        sCategoryMap.put(CATEGORY_KEY_PRODUCTIVITY, context.getString(C1188R.string.category_productivity));
        sCategoryMap.put(CATEGORY_KEY_SHOPPING, context.getString(C1188R.string.category_shopping));
        sCategoryMap.put("social", context.getString(C1188R.string.category_social));
        sCategoryMap.put(CATEGORY_KEY_SPORTS, context.getString(C1188R.string.category_sports));
        sCategoryMap.put(CATEGORY_KEY_TOOLS, context.getString(C1188R.string.category_tools));
        sCategoryMap.put(CATEGORY_KEY_TRAVEL_AND_LOCAL, context.getString(C1188R.string.category_travel_and_local));
        sCategoryMap.put(CATEGORY_KEY_VIDEO_PLAYERS_AND_EDITORS, context.getString(C1188R.string.category_video_players_and_editors));
        sCategoryMap.put(CATEGORY_KEY_WEATHER, context.getString(C1188R.string.category_weather));
        sCategoryMap.put(CATEGORY_KEY_LIBRARIES_AND_DEMO, context.getString(C1188R.string.category_libraries_and_demo));
        sCategoryMap.put(CATEGORY_KEY_ARCADE_GAMES, context.getString(C1188R.string.category_arcade_games));
        sCategoryMap.put(CATEGORY_KEY_PUZZLE_GAMES, context.getString(C1188R.string.category_puzzle_games));
        sCategoryMap.put(CATEGORY_KEY_CARD_GAMES, context.getString(C1188R.string.category_card_games));
        sCategoryMap.put(CATEGORY_KEY_CASUAL_GAMES, context.getString(C1188R.string.category_casual_games));
        sCategoryMap.put(CATEGORY_KEY_RACING_GAMES, context.getString(C1188R.string.category_racing_games));
        sCategoryMap.put(CATEGORY_KEY_SPORT_GAMES, context.getString(C1188R.string.category_sport_games));
        sCategoryMap.put(CATEGORY_KEY_ACTION_GAMES, context.getString(C1188R.string.category_action_games));
        sCategoryMap.put(CATEGORY_KEY_ADVENTURE_GAMES, context.getString(C1188R.string.category_adventure_games));
        sCategoryMap.put(CATEGORY_KEY_BOARD_GAMES, context.getString(C1188R.string.category_board_games));
        sCategoryMap.put(CATEGORY_KEY_CASINO_GAMES, context.getString(C1188R.string.category_casino_games));
        sCategoryMap.put(CATEGORY_KEY_EDUCATIONAL_GAMES, context.getString(C1188R.string.category_educational_games));
        sCategoryMap.put(CATEGORY_KEY_MUSIC_GAMES, context.getString(C1188R.string.category_music_games));
        sCategoryMap.put(CATEGORY_KEY_ROLE_PLAYING_GAMES, context.getString(C1188R.string.category_role_playing_games));
        sCategoryMap.put(CATEGORY_KEY_SIMULATION_GAMES, context.getString(C1188R.string.category_simulation_games));
        sCategoryMap.put(CATEGORY_KEY_STRATEGY_GAMES, context.getString(C1188R.string.category_strategy_games));
        sCategoryMap.put(CATEGORY_KEY_TRIVIA_GAMES, context.getString(C1188R.string.category_trivia_games));
        sCategoryMap.put(CATEGORY_KEY_WORD_GAMES, context.getString(C1188R.string.category_word_games));
    }
}
