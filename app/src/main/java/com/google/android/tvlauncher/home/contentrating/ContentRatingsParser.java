package com.google.android.tvlauncher.home.contentrating;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContentRatingsParser {
    public static final String DOMAIN_SYSTEM_RATINGS = "com.android.tv";
    private static final String ATTR_CONTENT_AGE_HINT = "contentAgeHint";
    private static final String ATTR_COUNTRY = "country";
    private static final String ATTR_DESCRIPTION = "description";
    private static final String ATTR_ICON = "icon";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_TITLE = "title";
    private static final String ATTR_VERSION_CODE = "versionCode";
    private static final boolean DEBUG = false;
    private static final String LIVE_TV_APP_DOMAIN = "com.google.android.tv";
    private static final String TAG = "ContentRatingsParser";
    private static final String TAG_RATING = "rating";
    private static final String TAG_RATING_DEFINITION = "rating-definition";
    private static final String TAG_RATING_ORDER = "rating-order";
    private static final String TAG_RATING_SYSTEM_DEFINITION = "rating-system-definition";
    private static final String TAG_RATING_SYSTEM_DEFINITIONS = "rating-system-definitions";
    private static final String TAG_SUB_RATING = "sub-rating";
    private static final String TAG_SUB_RATING_DEFINITION = "sub-rating-definition";
    private static final String VERSION_CODE = "1";
    private final Context mContext;
    private Resources mResources;
    private String mXmlVersionCode;

    public ContentRatingsParser(Context context) {
        this.mContext = context;
    }

    private static void assertEquals(int a, int b, String msg) throws XmlPullParserException {
        if (a != b) {
            throw new XmlPullParserException(msg);
        }
    }

    private static void assertEquals(String a, String b, String msg) throws XmlPullParserException {
        if (!b.equals(a)) {
            throw new XmlPullParserException(msg);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0056, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0057, code lost:
        if (r3 != null) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0061, code lost:
        throw r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.google.android.tvlauncher.home.contentrating.ContentRatingSystem> parse(android.media.tv.TvContentRatingSystemInfo r10) {
        /*
            r9 = this;
            r0 = 0
            android.net.Uri r1 = r10.getXmlUri()
            java.lang.String r2 = r1.getAuthority()     // Catch:{ Exception -> 0x0062 }
            long r3 = android.content.ContentUris.parseId(r1)     // Catch:{ Exception -> 0x0062 }
            int r4 = (int) r3     // Catch:{ Exception -> 0x0062 }
            android.content.Context r3 = r9.mContext     // Catch:{ Exception -> 0x0062 }
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch:{ Exception -> 0x0062 }
            r5 = 0
            android.content.res.XmlResourceParser r3 = r3.getXml(r2, r4, r5)     // Catch:{ Exception -> 0x0062 }
            if (r3 == 0) goto L_0x0030
            boolean r5 = r10.isSystemDefined()     // Catch:{ all -> 0x002e }
            if (r5 != 0) goto L_0x0024
            r5 = 1
            goto L_0x0025
        L_0x0024:
            r5 = 0
        L_0x0025:
            java.util.List r5 = r9.parse(r3, r2, r5)     // Catch:{ all -> 0x002e }
            r0 = r5
            r3.close()     // Catch:{ Exception -> 0x0062 }
            goto L_0x0087
        L_0x002e:
            r5 = move-exception
            goto L_0x0055
        L_0x0030:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x002e }
            java.lang.String r6 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x002e }
            java.lang.String r7 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x002e }
            int r7 = r7.length()     // Catch:{ all -> 0x002e }
            int r7 = r7 + 24
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x002e }
            r8.<init>(r7)     // Catch:{ all -> 0x002e }
            java.lang.String r7 = "Cannot get XML with URI "
            r8.append(r7)     // Catch:{ all -> 0x002e }
            r8.append(r6)     // Catch:{ all -> 0x002e }
            java.lang.String r6 = r8.toString()     // Catch:{ all -> 0x002e }
            r5.<init>(r6)     // Catch:{ all -> 0x002e }
            throw r5     // Catch:{ all -> 0x002e }
        L_0x0055:
            throw r5     // Catch:{ all -> 0x0056 }
        L_0x0056:
            r6 = move-exception
            if (r3 == 0) goto L_0x0061
            r3.close()     // Catch:{ all -> 0x005d }
            goto L_0x0061
        L_0x005d:
            r7 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r5, r7)     // Catch:{ Exception -> 0x0062 }
        L_0x0061:
            throw r6     // Catch:{ Exception -> 0x0062 }
        L_0x0062:
            r2 = move-exception
            java.lang.String r3 = java.lang.String.valueOf(r1)
            java.lang.String r4 = java.lang.String.valueOf(r3)
            int r4 = r4.length()
            int r4 = r4 + 18
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "Error parsing XML "
            r5.append(r4)
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            java.lang.String r4 = "ContentRatingsParser"
            android.util.Log.w(r4, r3, r2)
        L_0x0087:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.home.contentrating.ContentRatingsParser.parse(android.media.tv.TvContentRatingSystemInfo):java.util.List");
    }

    private List<ContentRatingSystem> parse(XmlResourceParser parser, String domain, boolean isCustom) throws XmlPullParserException, IOException {
        try {
            this.mResources = this.mContext.getPackageManager().getResourcesForApplication(domain);
        } catch (PackageManager.NameNotFoundException e) {
            String valueOf = String.valueOf(domain);
            Log.w(TAG, valueOf.length() != 0 ? "Failed to get resources for ".concat(valueOf) : new String("Failed to get resources for "), e);
            this.mResources = this.mContext.getResources();
        }
        if (domain.equals(this.mContext.getPackageName()) || domain.equals(LIVE_TV_APP_DOMAIN)) {
            domain = DOMAIN_SYSTEM_RATINGS;
        }
        do {
        } while (parser.next() == 0);
        assertEquals(parser.getEventType(), 2, "Malformed XML: Not a valid XML file");
        assertEquals(parser.getName(), TAG_RATING_SYSTEM_DEFINITIONS, "Malformed XML: Should start with tag rating-system-definitions");
        boolean hasVersionAttr = false;
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            if (ATTR_VERSION_CODE.equals(parser.getAttributeName(i))) {
                hasVersionAttr = true;
                this.mXmlVersionCode = parser.getAttributeValue(i);
            }
        }
        if (hasVersionAttr) {
            List<ContentRatingSystem> ratingSystems = new ArrayList<>();
            while (parser.next() != 1) {
                int eventType = parser.getEventType();
                if (eventType != 2) {
                    if (eventType != 3) {
                        continue;
                    } else if (TAG_RATING_SYSTEM_DEFINITIONS.equals(parser.getName())) {
                        assertEquals(parser.next(), 1, "Malformed XML: Should end with tag rating-system-definitions");
                        return ratingSystems;
                    } else {
                        checkVersion("Malformed XML: Should end with tag rating-system-definitions");
                    }
                } else if (TAG_RATING_SYSTEM_DEFINITION.equals(parser.getName())) {
                    ratingSystems.add(parseRatingSystemDefinition(parser, domain, isCustom));
                } else {
                    checkVersion("Malformed XML: Should contains rating-system-definition");
                }
            }
            throw new XmlPullParserException("rating-system-definitions section is incomplete or section ending tag is missing");
        }
        throw new XmlPullParserException("Malformed XML: Should contains a version attribute in rating-system-definitions");
    }

    private void checkVersion(String msg) throws XmlPullParserException {
        if (!"1".equals(this.mXmlVersionCode)) {
            throw new XmlPullParserException(msg);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x0119  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0152  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.android.tvlauncher.home.contentrating.ContentRatingSystem parseRatingSystemDefinition(android.content.res.XmlResourceParser r12, java.lang.String r13, boolean r14) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r11 = this;
            com.google.android.tvlauncher.home.contentrating.ContentRatingSystem$Builder r0 = new com.google.android.tvlauncher.home.contentrating.ContentRatingSystem$Builder
            android.content.Context r1 = r11.mContext
            r0.<init>(r1)
            r0.setDomain(r13)
            r1 = 0
        L_0x000b:
            int r2 = r12.getAttributeCount()
            java.lang.String r3 = " in "
            r4 = -1
            java.lang.String r5 = "rating-system-definition"
            r6 = 3
            r7 = 0
            r8 = 2
            r9 = 1
            if (r1 >= r2) goto L_0x00b4
            java.lang.String r2 = r12.getAttributeName(r1)
            int r10 = r2.hashCode()
            switch(r10) {
                case -1724546052: goto L_0x0046;
                case 3373707: goto L_0x003c;
                case 110371416: goto L_0x0031;
                case 957831062: goto L_0x0027;
                default: goto L_0x0026;
            }
        L_0x0026:
            goto L_0x004f
        L_0x0027:
            java.lang.String r10 = "country"
            boolean r10 = r2.equals(r10)
            if (r10 == 0) goto L_0x0026
            r4 = 1
            goto L_0x004f
        L_0x0031:
            java.lang.String r10 = "title"
            boolean r10 = r2.equals(r10)
            if (r10 == 0) goto L_0x0026
            r4 = 2
            goto L_0x004f
        L_0x003c:
            java.lang.String r10 = "name"
            boolean r10 = r2.equals(r10)
            if (r10 == 0) goto L_0x0026
            r4 = 0
            goto L_0x004f
        L_0x0046:
            java.lang.String r10 = "description"
            boolean r10 = r2.equals(r10)
            if (r10 == 0) goto L_0x0026
            r4 = 3
        L_0x004f:
            if (r4 == 0) goto L_0x00a8
            if (r4 == r9) goto L_0x0092
            if (r4 == r8) goto L_0x008a
            if (r4 == r6) goto L_0x007c
            java.lang.String r4 = java.lang.String.valueOf(r2)
            int r4 = r4.length()
            int r4 = r4 + 61
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r4)
            java.lang.String r4 = "Malformed XML: Unknown attribute "
            r6.append(r4)
            r6.append(r2)
            r6.append(r3)
            r6.append(r5)
            java.lang.String r3 = r6.toString()
            r11.checkVersion(r3)
            goto L_0x00b0
        L_0x007c:
            android.content.res.Resources r3 = r11.mResources
            int r4 = r12.getAttributeResourceValue(r1, r7)
            java.lang.String r3 = r3.getString(r4)
            r0.setDescription(r3)
            goto L_0x00b0
        L_0x008a:
            java.lang.String r3 = r11.getTitle(r12, r1)
            r0.setTitle(r3)
            goto L_0x00b0
        L_0x0092:
            java.lang.String r3 = r12.getAttributeValue(r1)
            java.lang.String r4 = "\\s*,\\s*"
            java.lang.String[] r3 = r3.split(r4)
            int r4 = r3.length
        L_0x009d:
            if (r7 >= r4) goto L_0x00a7
            r5 = r3[r7]
            r0.addCountry(r5)
            int r7 = r7 + 1
            goto L_0x009d
        L_0x00a7:
            goto L_0x00b0
        L_0x00a8:
            java.lang.String r3 = r12.getAttributeValue(r1)
            r0.setName(r3)
        L_0x00b0:
            int r1 = r1 + 1
            goto L_0x000b
        L_0x00b4:
            int r1 = r12.next()
            if (r1 == r9) goto L_0x015d
            int r1 = r12.getEventType()
            if (r1 == r8) goto L_0x00dd
            if (r1 == r6) goto L_0x00c4
            goto L_0x015b
        L_0x00c4:
            java.lang.String r1 = r12.getName()
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x00d6
            r0.setIsCustom(r14)
            com.google.android.tvlauncher.home.contentrating.ContentRatingSystem r1 = r0.build()
            return r1
        L_0x00d6:
            java.lang.String r1 = "Malformed XML: Tag mismatch for rating-system-definition"
            r11.checkVersion(r1)
            goto L_0x015b
        L_0x00dd:
            java.lang.String r1 = r12.getName()
            int r2 = r1.hashCode()
            r10 = -1751456994(0xffffffff979ae31e, float:-1.0009349E-24)
            if (r2 == r10) goto L_0x010b
            r10 = 308029750(0x125c2936, float:6.9470556E-28)
            if (r2 == r10) goto L_0x0100
            r10 = 1137752963(0x43d0bb83, float:417.46494)
            if (r2 == r10) goto L_0x00f5
        L_0x00f4:
            goto L_0x0116
        L_0x00f5:
            java.lang.String r2 = "rating-definition"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f4
            r2 = 0
            goto L_0x0117
        L_0x0100:
            java.lang.String r2 = "sub-rating-definition"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f4
            r2 = 1
            goto L_0x0117
        L_0x010b:
            java.lang.String r2 = "rating-order"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f4
            r2 = 2
            goto L_0x0117
        L_0x0116:
            r2 = -1
        L_0x0117:
            if (r2 == 0) goto L_0x0152
            if (r2 == r9) goto L_0x014a
            if (r2 == r8) goto L_0x0142
            java.lang.String r2 = java.lang.String.valueOf(r1)
            int r2 = r2.length()
            int r2 = r2 + 55
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r2)
            java.lang.String r2 = "Malformed XML: Unknown tag "
            r10.append(r2)
            r10.append(r1)
            r10.append(r3)
            r10.append(r5)
            java.lang.String r2 = r10.toString()
            r11.checkVersion(r2)
            goto L_0x015a
        L_0x0142:
            com.google.android.tvlauncher.home.contentrating.ContentRatingSystem$Order$Builder r2 = r11.parseOrder(r12)
            r0.addOrderBuilder(r2)
            goto L_0x015a
        L_0x014a:
            com.google.android.tvlauncher.home.contentrating.ContentRatingSystem$SubRating$Builder r2 = r11.parseSubRatingDefinition(r12)
            r0.addSubRatingBuilder(r2)
            goto L_0x015a
        L_0x0152:
            com.google.android.tvlauncher.home.contentrating.ContentRatingSystem$Rating$Builder r2 = r11.parseRatingDefinition(r12)
            r0.addRatingBuilder(r2)
        L_0x015a:
        L_0x015b:
            goto L_0x00b4
        L_0x015d:
            org.xmlpull.v1.XmlPullParserException r1 = new org.xmlpull.v1.XmlPullParserException
            java.lang.String r2 = "rating-system-definition section is incomplete or section ending tag is missing"
            r1.<init>(r2)
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.home.contentrating.ContentRatingsParser.parseRatingSystemDefinition(android.content.res.XmlResourceParser, java.lang.String, boolean):com.google.android.tvlauncher.home.contentrating.ContentRatingSystem");
    }

    private ContentRatingSystem.Rating.Builder parseRatingDefinition(XmlResourceParser parser) throws XmlPullParserException, IOException {
        ContentRatingSystem.Rating.Builder builder = new ContentRatingSystem.Rating.Builder();
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attr = parser.getAttributeName(i);
            char c = 65535;
            switch (attr.hashCode()) {
                case -1724546052:
                    if (attr.equals("description")) {
                        c = 2;
                        break;
                    }
                    break;
                case -706851475:
                    if (attr.equals(ATTR_CONTENT_AGE_HINT)) {
                        c = 4;
                        break;
                    }
                    break;
                case 3226745:
                    if (attr.equals("icon")) {
                        c = 3;
                        break;
                    }
                    break;
                case 3373707:
                    if (attr.equals("name")) {
                        c = 0;
                        break;
                    }
                    break;
                case 110371416:
                    if (attr.equals("title")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                builder.setName(parser.getAttributeValue(i));
            } else if (c == 1) {
                builder.setTitle(getTitle(parser, i));
            } else if (c == 2) {
                builder.setDescription(this.mResources.getString(parser.getAttributeResourceValue(i, 0)));
            } else if (c == 3) {
                builder.setIcon(this.mResources.getDrawable(parser.getAttributeResourceValue(i, 0), null));
            } else if (c != 4) {
                StringBuilder sb = new StringBuilder(String.valueOf(attr).length() + 54);
                sb.append("Malformed XML: Unknown attribute ");
                sb.append(attr);
                sb.append(" in ");
                sb.append(TAG_RATING_DEFINITION);
                checkVersion(sb.toString());
            } else {
                int contentAgeHint = -1;
                try {
                    contentAgeHint = Integer.parseInt(parser.getAttributeValue(i));
                } catch (NumberFormatException e) {
                }
                if (contentAgeHint >= 0) {
                    builder.setContentAgeHint(contentAgeHint);
                } else {
                    throw new XmlPullParserException("Malformed XML: contentAgeHint should be a non-negative number");
                }
            }
        }
        while (parser.next() != 1) {
            int eventType = parser.getEventType();
            if (eventType != 2) {
                if (eventType != 3) {
                    continue;
                } else if (TAG_RATING_DEFINITION.equals(parser.getName())) {
                    return builder;
                } else {
                    checkVersion("Malformed XML: Tag mismatch for rating-definition");
                }
            } else if (TAG_SUB_RATING.equals(parser.getName())) {
                builder = parseSubRating(parser, builder);
            } else {
                checkVersion("Malformed XML: Only sub-rating is allowed in rating-definition");
            }
        }
        throw new XmlPullParserException("rating-definition section is incomplete or section ending tag is missing");
    }

    private ContentRatingSystem.SubRating.Builder parseSubRatingDefinition(XmlResourceParser parser) throws XmlPullParserException, IOException {
        ContentRatingSystem.SubRating.Builder builder = new ContentRatingSystem.SubRating.Builder();
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attr = parser.getAttributeName(i);
            char c = 65535;
            switch (attr.hashCode()) {
                case -1724546052:
                    if (attr.equals("description")) {
                        c = 2;
                        break;
                    }
                    break;
                case 3226745:
                    if (attr.equals("icon")) {
                        c = 3;
                        break;
                    }
                    break;
                case 3373707:
                    if (attr.equals("name")) {
                        c = 0;
                        break;
                    }
                    break;
                case 110371416:
                    if (attr.equals("title")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                builder.setName(parser.getAttributeValue(i));
            } else if (c == 1) {
                builder.setTitle(getTitle(parser, i));
            } else if (c == 2) {
                builder.setDescription(this.mResources.getString(parser.getAttributeResourceValue(i, 0)));
            } else if (c != 3) {
                StringBuilder sb = new StringBuilder(String.valueOf(attr).length() + 58);
                sb.append("Malformed XML: Unknown attribute ");
                sb.append(attr);
                sb.append(" in ");
                sb.append(TAG_SUB_RATING_DEFINITION);
                checkVersion(sb.toString());
            } else {
                builder.setIcon(this.mResources.getDrawable(parser.getAttributeResourceValue(i, 0), null));
            }
        }
        while (parser.next() != 1) {
            if (parser.getEventType() != 3) {
                checkVersion("Malformed XML: sub-rating-definition has child");
            } else if (TAG_SUB_RATING_DEFINITION.equals(parser.getName())) {
                return builder;
            } else {
                checkVersion("Malformed XML: sub-rating-definition isn't closed");
            }
        }
        throw new XmlPullParserException("sub-rating-definition section is incomplete or section ending tag is missing");
    }

    private ContentRatingSystem.Order.Builder parseOrder(XmlResourceParser parser) throws XmlPullParserException, IOException {
        ContentRatingSystem.Order.Builder builder = new ContentRatingSystem.Order.Builder();
        assertEquals(parser.getAttributeCount(), 0, "Malformed XML: Attribute isn't allowed in rating-order");
        while (parser.next() != 1) {
            int eventType = parser.getEventType();
            if (eventType != 2) {
                if (eventType == 3) {
                    assertEquals(parser.getName(), TAG_RATING_ORDER, "Malformed XML: Tag mismatch for rating-order");
                    return builder;
                }
            } else if (TAG_RATING.equals(parser.getName())) {
                builder = parseRating(parser, builder);
            } else {
                checkVersion("Malformed XML: Only rating is allowed in rating-order");
            }
        }
        throw new XmlPullParserException("rating-order section is incomplete or section ending tag is missing");
    }

    private ContentRatingSystem.Order.Builder parseRating(XmlResourceParser parser, ContentRatingSystem.Order.Builder builder) throws XmlPullParserException, IOException {
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attr = parser.getAttributeName(i);
            char c = 65535;
            if (attr.hashCode() == 3373707 && attr.equals("name")) {
                c = 0;
            }
            if (c != 0) {
                checkVersion("Malformed XML: rating-order should only contain name");
            } else {
                builder.addRatingName(parser.getAttributeValue(i));
            }
        }
        while (parser.next() != 1) {
            if (parser.getEventType() == 3) {
                if (TAG_RATING.equals(parser.getName())) {
                    return builder;
                }
                checkVersion("Malformed XML: rating has child");
            }
        }
        throw new XmlPullParserException("rating section is incomplete or section ending tag is missing");
    }

    private ContentRatingSystem.Rating.Builder parseSubRating(XmlResourceParser parser, ContentRatingSystem.Rating.Builder builder) throws XmlPullParserException, IOException {
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attr = parser.getAttributeName(i);
            char c = 65535;
            if (attr.hashCode() == 3373707 && attr.equals("name")) {
                c = 0;
            }
            if (c != 0) {
                checkVersion("Malformed XML: sub-rating should only contain name");
            } else {
                builder.addSubRatingName(parser.getAttributeValue(i));
            }
        }
        while (parser.next() != 1) {
            if (parser.getEventType() == 3) {
                if (TAG_SUB_RATING.equals(parser.getName())) {
                    return builder;
                }
                checkVersion("Malformed XML: sub-rating has child");
            }
        }
        throw new XmlPullParserException("sub-rating section is incomplete or section ending tag is missing");
    }

    private String getTitle(XmlResourceParser parser, int index) {
        int titleResId = parser.getAttributeResourceValue(index, 0);
        if (titleResId != 0) {
            return this.mResources.getString(titleResId);
        }
        return parser.getAttributeValue(index);
    }
}
