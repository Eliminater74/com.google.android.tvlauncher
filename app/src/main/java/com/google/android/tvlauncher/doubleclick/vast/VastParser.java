package com.google.android.tvlauncher.doubleclick.vast;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.tvlauncher.doubleclick.Clock;
import com.google.android.tvlauncher.doubleclick.proto.nano.AdConfig;
import com.google.android.tvlauncher.doubleclick.proto.nano.VideoCreative;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

public class VastParser {

    static final long AD_VALIDITY_TTL_MS = 3600000;
    /* renamed from: AD */
    private static final String f141AD = "VAST/Ad/%s";
    private static final String BASE = "VAST";
    private static final String BASE_AD = "VAST/Ad";
    private static final String COMPANION = "VAST/Ad/%s/Creatives/Creative/CompanionAds/Companion";
    private static final String COMPANION_ADS = "VAST/Ad/%s/Creatives/Creative/CompanionAds";
    private static final String COMPANION_TRACKING_EVENT = "VAST/Ad/%s/Creatives/Creative/CompanionAds/Companion/TrackingEvents/Tracking";
    private static final String COMPANION_TRACKING_EVENTS = "VAST/Ad/%s/Creatives/Creative/CompanionAds/Companion/TrackingEvents";
    private static final String CREATIVE = "VAST/Ad/%s/Creatives/Creative";
    private static final String DESTINATION_URL = "VAST/Ad/%s/Creatives/Creative/Linear/VideoClicks/ClickThrough";
    private static final String DURATION = "VAST/Ad/%s/Creatives/Creative/Linear/Duration";
    private static final String EXTENSION = "VAST/Ad/%s/Extensions/Extension";
    private static final String EXTENSIONS_TRACKING_CUSTOM = "VAST/Ad/%s/Extensions/Extension/CustomTracking/Tracking";
    private static final String IMPRESSION = "VAST/Ad/%s/Impression";
    private static final String LINEAR = "Linear";
    private static final String LINEAR_PARAMS = "VAST/Ad/%s/Creatives/Creative/Linear/AdParameters";
    private static final String MEDIA_FILE = "VAST/Ad/%s/Creatives/Creative/Linear/MediaFiles/MediaFile";
    private static final String MEDIA_FILES = "VAST/Ad/%s/Creatives/Creative/Linear/MediaFiles";
    private static final String NONLINEAR_ELEMENT = "NonLinearAds";
    private static final String NON_LINEAR = "VAST/Ad/%s/Creatives/Creative/NonLinearAds/NonLinear";
    private static final String[] COMPANION_NONLINEAR = {COMPANION, NON_LINEAR};
    private static final String NON_LINEAR_ADS = "VAST/Ad/%s/Creatives/Creative/NonLinearAds";
    private static final String NON_LINEAR_AD_TRACKING_EVENT = "VAST/Ad/%s/Creatives/Creative/NonLinearAds/TrackingEvents/Tracking";
    private static final String NON_LINEAR_AD_TRACKING_EVENTS = "VAST/Ad/%s/Creatives/Creative/NonLinearAds/TrackingEvents";
    private static final String NON_LINEAR_CLICK_THROUGH = "VAST/Ad/%s/Creatives/Creative/NonLinearAds/NonLinear/NonLinearClickThrough";
    private static final String NON_LINEAR_PARAMS = "VAST/Ad/%s/Creatives/Creative/NonLinearAds/NonLinear/AdParameters";
    private static final String REDIRECT_URL = "VAST/Ad/%s/VASTAdTagURI";
    private static final String SURVEY = "VAST/Ad/%s/Survey";
    private static final String TAG = "VastParser";
    private static final String TRACKING_CLICK = "VAST/Ad/%s/Creatives/Creative/Linear/VideoClicks/ClickTracking";
    private static final String TRACKING_CUSTOM = "VAST/Ad/%s/Creatives/Creative/Linear/VideoClicks/CustomClick";
    private static final String TRACKING_EVENT = "VAST/Ad/%s/Creatives/Creative/Linear/TrackingEvents/Tracking";
    private static final String TRACKING_EVENTS = "VAST/Ad/%s/Creatives/Creative/Linear/TrackingEvents";
    private static final String VAST2_XSD = "/schemas/vast_2.0.1.xsd";
    private static final String VAST3_XSD = "/schemas/vast_3.0.0.xsd";
    private static final String VIDEO_CLICKS = "VAST/Ad/%s/Creatives/Creative/Linear/VideoClicks";
    private final Clock mClock;
    /* access modifiers changed from: private */
    public int vastVersion;
    private ParserType parserType;
    private List<VideoCreative.VastXml> videos;

    public VastParser() {
        this(new SystemClock());
    }

    @VisibleForTesting
    VastParser(Clock clock) {
        this.videos = new ArrayList();
        this.mClock = clock;
    }

    private static byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[65536];
        int len = inputStream.read(buffer);
        while (len != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
            len = inputStream.read(buffer);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /* access modifiers changed from: private */
    public static int parseDuration(String videoDuration) {
        String[] tokens = videoDuration.split(":");
        if (tokens.length != 3 && tokens.length != 4) {
            return -1;
        }
        try {
            return (int) (TimeUnit.HOURS.toMillis((long) Integer.parseInt(tokens[0])) + TimeUnit.MINUTES.toMillis((long) Integer.parseInt(tokens[1])) + Math.round(Double.parseDouble(tokens[2]) * 1000.0d) + (tokens.length == 3 ? 0 : parseAndRoundNumber(tokens[3])));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static long parseAndRoundNumber(String number) throws NumberFormatException {
        return Math.round(Double.parseDouble(number));
    }

    public boolean isVast1(byte[] input) {
        try {
            return isVast1(new String(input, Charset.forName("UTF-8")));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isVast1(String input) {
        final boolean[] isVast1 = new boolean[1];
        DomDigester digester = new DomDigester();
        digester.addRule("VideoAdServingTemplate", new DomDigester.Rule(this) {
            public void executeBeforeChildren(Element element) {
                isVast1[0] = true;
            }
        });
        try {
            digester.parse(input);
            return isVast1[0];
        } catch (DomDigester.BadXmlException | IOException e) {
            return false;
        }
    }

    private void addDigesterRulesForVastVersion(final DomDigester digester) {
        this.vastVersion = -1;
        digester.addRule("VideoAdServingTemplate", new DomDigester.Rule() {
            public void executeBeforeChildren(Element element) {
                int unused = VastParser.this.vastVersion = 1;
            }
        });
        digester.addRule(BASE, new DomDigester.Rule() {
            public void executeBeforeChildren(Element element) {
                if (element.getAttribute("version") != null) {
                    String value = digester.getAttributeValue(element, "version").trim();
                    if (value.startsWith("2")) {
                        int unused = VastParser.this.vastVersion = 2;
                    } else if (value.startsWith("3")) {
                        int unused2 = VastParser.this.vastVersion = 3;
                    } else {
                        int unused3 = VastParser.this.vastVersion = -1;
                    }
                }
            }
        });
    }

    private void validate(VideoCreative.VastXml video, Source instanceDocument, String xsd) {
        try {
            SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema").newSchema(new StreamSource[]{new StreamSource(getClass().getResourceAsStream(xsd))}).newValidator().validate(instanceDocument);
        } catch (IOException e) {
            video.vastSchemaValidationErrors = e.getMessage();
        } catch (SAXException e2) {
            if (!e2.toString().contains("ddm/activity/dc_oe=") || !e2.toString().contains("anyURI")) {
                video.vastSchemaValidationErrors = e2.toString().replace("org.xml.sax.SAXParseException;", "").trim();
            }
        }
    }

    private String formatParserType(String s) {
        return formatParserType(s, false);
    }

    private String formatParserType(String s, boolean addUrlInVast1) {
        if (this.vastVersion == 1) {
            String str = "";
            String s2 = s.replace("VAST/", "VideoAdServingTemplate/").replace("/Creatives/Creative", str).replace("/VASTAdTagURI", "/VASTAdTagURL").replace("/StaticResource", str).replace("/HTMLResource", "/Code");
            if (s2.endsWith("/Tracking")) {
                s = s2.replace("/Linear", str);
            } else {
                if (this.parserType == ParserType.INLINE) {
                    str = "/Video";
                }
                s = s2.replace("/Linear", str);
            }
            if (addUrlInVast1) {
                s = String.valueOf(s).concat("/URL");
            }
        }
        return String.format(s, this.parserType.toString());
    }

    private void addDigestorRulesForVastBody(DomDigester digester) {
        this.videos = new ArrayList();
        digester.push(this);
        this.parserType = ParserType.INLINE;
        digester.createObjectRule(formatParserType(BASE_AD), VideoCreative.VastXml.class);
        digester.setPropertiesRule(formatParserType(BASE_AD), TtmlNode.ATTR_ID, TtmlNode.ATTR_ID);
        digester.setPropertiesRule(formatParserType(BASE_AD), "sequence", "sequence");
        addRulesToDigester(digester, ParserType.INLINE);
        addRulesToDigester(digester, ParserType.WRAPPER);
        digester.addSetNext(formatParserType(BASE_AD), "addVideo");
    }

    private void addRulesToDigester(final DomDigester digester, ParserType parserType2) {
        this.parserType = parserType2;
        digester.setPropertiesRule(formatParserType(CREATIVE), "AdID", "adId");
        digester.addRule(formatParserType(CREATIVE), new DomDigester.Rule(this) {
            public void executeBeforeChildren(Element element) {
                VideoCreative.VastXml top = (VideoCreative.VastXml) digester.peek();
                Element linear = DomUtils.getFirstChildElement(element, VastParser.LINEAR);
                Element nonlinear = DomUtils.getFirstChildElement(element, VastParser.NONLINEAR_ELEMENT);
                if (linear != null || nonlinear != null) {
                    String id = digester.getAttributeValue(element, TtmlNode.ATTR_ID);
                    if (!TextUtils.isEmpty(id)) {
                        top.adId = id;
                    }
                }
            }
        });
        digester.setNodeValueRule(formatParserType(SURVEY), "survey");
        digester.createObjectRule(formatParserType(IMPRESSION, true), VideoCreative.VastImpression.class);
        digester.setNodeValueRule(formatParserType(IMPRESSION, true), "url");
        digester.setPropertiesRule(formatParserType(IMPRESSION, true), TtmlNode.ATTR_ID, TtmlNode.ATTR_ID);
        digester.addRule(formatParserType(IMPRESSION, true), new DomDigester.Rule(this) {
            public void executeBeforeChildren(Element element) {
                ((VideoCreative.VastXml) digester.belowPeek()).impression = new VideoCreative.VastImpression[]{(VideoCreative.VastImpression) digester.peek()};
            }
        });
        digester.setNodeValueRule(formatParserType(REDIRECT_URL, true), "redirectUrl");
        digester.addRule(formatParserType(DURATION), new DomDigester.Rule(this) {
            public void executeBeforeChildren(Element element) {
                ((VideoCreative.VastXml) digester.peek()).duration = VastParser.parseDuration(digester.getElementValue(element));
            }
        });
        digester.addRule(formatParserType(LINEAR_PARAMS), new DomDigester.Rule(this) {
            public void executeBeforeChildren(Element element) {
                ((VideoCreative.VastXml) digester.peek()).customParameters = digester.getElementValue(element);
            }
        });
        digester.addRule(formatParserType(EXTENSION), new DomDigester.Rule() {
            public void executeBeforeChildren(Element element) {
                VideoCreative.VastXml top = (VideoCreative.VastXml) digester.peek();
                if (element.getAttribute("type") != null) {
                    String type = element.getAttribute("type");
                    if ("waterfall".equals(type) && element.getAttribute("fallback_index") != null) {
                        top.fallbackIndex = VastParser.this.parseInteger(element.getAttribute("fallback_index"));
                    } else if ("pod".equals(type) && element.getAttribute("sequence") != null) {
                        top.sequence = VastParser.this.parseInteger(element.getAttribute("sequence"));
                    }
                }
            }
        });
        digester.addRule(formatParserType(EXTENSIONS_TRACKING_CUSTOM), new DomDigester.Rule(this) {
            public void executeBeforeChildren(Element element) {
                VideoCreative.VastXml top = (VideoCreative.VastXml) digester.peek();
                if ("skip".equals(element.getAttribute(NotificationCompat.CATEGORY_EVENT))) {
                    top.customSkipEventExists = true;
                }
            }
        });
        digester.setNodeValueRule(formatParserType(LINEAR_PARAMS), "customParameters");
        digester.collectAllChildrenResultIntoArrayFieldRule(formatParserType(TRACKING_EVENTS), new String[]{"eventTracking"});
        digester.createObjectRule(formatParserType(TRACKING_EVENT), VideoCreative.VastTracking.class);
        digester.setNodeValueRule(formatParserType(TRACKING_EVENT, true), "eventUrl");
        digester.setPropertiesRule(formatParserType(TRACKING_EVENT), NotificationCompat.CATEGORY_EVENT, "eventName");
        digester.collectChildResultIntoArrayListRule(formatParserType(TRACKING_EVENT, true), "eventTracking");
        digester.collectAllChildrenResultIntoArrayFieldRule(formatParserType(VIDEO_CLICKS), new String[]{"clickTracking", "customTracking"});
        digester.addRule(formatParserType(DESTINATION_URL, true), new DomDigester.Rule(this) {
            public void executeBeforeChildren(Element element) {
                ((VideoCreative.VastXml) digester.belowPeek()).destinationUrl = digester.getElementValue(element);
            }
        });
        digester.createObjectRule(formatParserType(TRACKING_CLICK, true), VideoCreative.VastTracking.class);
        digester.setNodeValueRule(formatParserType(TRACKING_CLICK, true), "eventUrl");
        digester.setPropertiesRule(formatParserType(TRACKING_CLICK, true), TtmlNode.ATTR_ID, "eventName");
        digester.collectChildResultIntoArrayListRule(formatParserType(TRACKING_CLICK, true), "clickTracking");
        digester.createObjectRule(formatParserType(TRACKING_CUSTOM, true), VideoCreative.VastTracking.class);
        digester.setNodeValueRule(formatParserType(TRACKING_CUSTOM, true), "eventUrl");
        digester.setPropertiesRule(formatParserType(TRACKING_CUSTOM, true), TtmlNode.ATTR_ID, "eventName");
        digester.collectChildResultIntoArrayListRule(formatParserType(TRACKING_CUSTOM, true), "customTracking");
        digester.collectAllChildrenResultIntoArrayFieldRule(formatParserType(MEDIA_FILES), new String[]{"media"});
        digester.createObjectRule(formatParserType(MEDIA_FILE), VideoCreative.VastMedia.class);
        digester.setNodeValueRule(formatParserType(MEDIA_FILE, true), "url");
        digester.setPropertiesRule(formatParserType(MEDIA_FILE), "delivery", "delivery");
        digester.setPropertiesRule(formatParserType(MEDIA_FILE), "type", "type");
        digester.setPropertiesRule(formatParserType(MEDIA_FILE), "bitrate", "bitrate");
        digester.setPropertiesRule(formatParserType(MEDIA_FILE), "width", "width");
        digester.setPropertiesRule(formatParserType(MEDIA_FILE), "height", "height");
        digester.setPropertiesRule(formatParserType(MEDIA_FILE), "scalable", "scalable");
        digester.setPropertiesRule(formatParserType(MEDIA_FILE), "apiFramework", "apiFramework");
        digester.setPropertiesRule(formatParserType(MEDIA_FILE), "maintainAspectRatio", "maintainAspectRatio");
        digester.collectChildResultIntoArrayListRule(formatParserType(MEDIA_FILE, true), "media");
        digester.collectAllChildrenResultIntoArrayFieldRule(formatParserType(COMPANION_ADS), new String[]{"companion"});
        digester.createObjectRule(formatParserType(COMPANION), VideoCreative.VastCompanion.class);
        digester.setPropertiesRule(formatParserType(COMPANION), "width", "width");
        digester.setPropertiesRule(formatParserType(COMPANION), "height", "height");
        digester.setPropertiesRule(formatParserType(COMPANION), "expandedWidth", "expandedWidth");
        digester.setPropertiesRule(formatParserType(COMPANION), "expandedHeight", "expandedHeight");
        digester.setPropertiesRule(formatParserType(COMPANION), "apiFramework", "apiFramework");
        digester.setNodeValueRule(formatParserType("VAST/Ad/%s/Creatives/Creative/CompanionAds/Companion/CompanionClickThrough", true), "destinationUrl");
        digester.collectAllChildrenResultIntoArrayFieldRule(formatParserType(COMPANION_TRACKING_EVENTS), new String[]{"eventTracking"});
        digester.createObjectRule(formatParserType(COMPANION_TRACKING_EVENT), VideoCreative.VastTracking.class);
        digester.setNodeValueRule(formatParserType(COMPANION_TRACKING_EVENT, true), "eventUrl");
        digester.setPropertiesRule(formatParserType(COMPANION_TRACKING_EVENT), NotificationCompat.CATEGORY_EVENT, "eventName");
        digester.collectChildResultIntoArrayListRule(formatParserType(COMPANION_TRACKING_EVENT, true), "eventTracking");
        digester.setNodeValueRule(formatParserType("VAST/Ad/%s/Creatives/Creative/CompanionAds/Companion/HTMLResource"), "htmlResource");
        digester.setNodeValueRule(formatParserType("VAST/Ad/%s/Creatives/Creative/CompanionAds/Companion/IFrameResource"), "iframeResource");
        digester.setNodeValueRule(formatParserType("VAST/Ad/%s/Creatives/Creative/CompanionAds/Companion/StaticResource", true), "staticResource");
        digester.setPropertiesRule(formatParserType("VAST/Ad/%s/Creatives/Creative/CompanionAds/Companion/StaticResource"), "creativeType", "type");
        digester.collectChildResultIntoArrayListRule(formatParserType(COMPANION, true), "companion");
        digester.collectAllChildrenResultIntoArrayFieldRule(formatParserType(NON_LINEAR_ADS), new String[]{"nonLinearAsset", "nonLinearEventTracking"});
        digester.createObjectRule(formatParserType(NON_LINEAR), VideoCreative.VastNonLinear.class);
        digester.setPropertiesRule(formatParserType(NON_LINEAR), "scalable", "scalable");
        digester.setPropertiesRule(formatParserType(NON_LINEAR), "maintainAspectRatio", "maintainAspectRatio");
        digester.addRule(formatParserType(NON_LINEAR), new DomDigester.Rule(this) {
            public void executeBeforeChildren(Element element) {
                if (element.getAttribute("minSuggestedDuration") != null) {
                    ((VideoCreative.VastNonLinear) digester.peek()).minSuggestedDuration = VastParser.parseDuration(digester.getAttributeValue(element, "minSuggestedDuration"));
                }
            }
        });
        digester.collectChildResultIntoArrayListRule(formatParserType(NON_LINEAR, true), "nonLinearAsset");
        digester.createObjectRule(formatParserType(NON_LINEAR_AD_TRACKING_EVENT), VideoCreative.VastTracking.class);
        digester.setNodeValueRule(formatParserType(NON_LINEAR_AD_TRACKING_EVENT, true), "eventUrl");
        digester.setPropertiesRule(formatParserType(NON_LINEAR_AD_TRACKING_EVENT), NotificationCompat.CATEGORY_EVENT, "eventName");
        digester.collectChildResultIntoArrayListRule(formatParserType(NON_LINEAR_AD_TRACKING_EVENT, true), "nonLinearEventTracking");
        digester.addRule(formatParserType(NON_LINEAR_PARAMS), new DomDigester.Rule(this) {
            public void executeBeforeChildren(Element element) {
                ((VideoCreative.VastNonLinear) digester.peek()).customParameters = digester.getElementValue(element);
            }
        });
        digester.addRule(formatParserType(NON_LINEAR_CLICK_THROUGH), new DomDigester.Rule(this) {
            public void executeBeforeChildren(Element element) {
                ((VideoCreative.VastNonLinear) digester.peek()).destinationUrl = digester.getElementValue(element);
            }
        });
    }

    @Nullable
    public AdConfig.AdAsset parse(String adUnitId, InputStream inputStream) {
        try {
            VideoCreative.VastXml vastXml = parse(getBytesFromInputStream(inputStream));
            AdConfig.AdAsset adAsset = new AdConfig.AdAsset();
            adAsset.expiration = getExpirationTimeInMillis();
            AdConfig.DoubleClickAdConfig doubleClickAdConfig = new AdConfig.DoubleClickAdConfig();
            adAsset.setDoubleclickAdConfig(doubleClickAdConfig);
            doubleClickAdConfig.adUnitId = adUnitId;
            doubleClickAdConfig.setVast(vastXml);
            return adAsset;
        } catch (DomDigester.BadXmlException | IOException ex) {
            Log.e(TAG, "Problem with vast ad format parsing..returning null", ex);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public VideoCreative.VastXml parse(byte[] input) throws DomDigester.BadXmlException {
        List<VideoCreative.VastXml> ads = parseMultiple(input);
        if (!ads.isEmpty()) {
            return ads.get(0);
        }
        VideoCreative.VastXml video = new VideoCreative.VastXml();
        video.vastVersion = this.vastVersion;
        return video;
    }

    private long getExpirationTimeInMillis() {
        return this.mClock.getCurrentTimeMillis() + AD_VALIDITY_TTL_MS;
    }

    private List<VideoCreative.VastXml> parseMultiple(byte[] input) throws DomDigester.BadXmlException {
        DomDigester digester = new DomDigester();
        addDigesterRulesForVastVersion(digester);
        addDigestorRulesForVastBody(digester);
        try {
            digester.parse(new ByteArrayInputStream(input));
            return this.videos;
        } catch (IOException e) {
            throw new AssertionError("This exception cannot happen. Digester creates an InputSource and  passes it to the XML parser, however that InputSource cannot throw IOException", e);
        }
    }

    /* access modifiers changed from: private */
    public int parseInteger(String number) {
        if (TextUtils.isEmpty(number)) {
            return 0;
        }
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public VideoCreative.VastXml getVideo() {
        if (!this.videos.isEmpty()) {
            List<VideoCreative.VastXml> list = this.videos;
            return list.get(list.size() - 1);
        }
        VideoCreative.VastXml video = new VideoCreative.VastXml();
        video.vastVersion = this.vastVersion;
        return video;
    }

    public void addVideo(@NonNull VideoCreative.VastXml video) {
        video.vastVersion = this.vastVersion;
        this.videos.add(video);
    }

    public enum ParserType {
        INLINE("InLine"),
        WRAPPER("Wrapper");

        private final String type;

        private ParserType(String type2) {
            this.type = type2;
        }

        public String toString() {
            return this.type;
        }
    }

    private static class SystemClock implements Clock {
        private SystemClock() {
        }

        public long getCurrentTimeMillis() {
            return System.currentTimeMillis();
        }

        public void sleep(long durationMillis) {
        }
    }
}
