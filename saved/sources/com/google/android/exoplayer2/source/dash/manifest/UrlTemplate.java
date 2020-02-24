package com.google.android.exoplayer2.source.dash.manifest;

import java.util.Locale;

public final class UrlTemplate {
    private static final String BANDWIDTH = "Bandwidth";
    private static final int BANDWIDTH_ID = 3;
    private static final String DEFAULT_FORMAT_TAG = "%01d";
    private static final String ESCAPED_DOLLAR = "$$";
    private static final String NUMBER = "Number";
    private static final int NUMBER_ID = 2;
    private static final String REPRESENTATION = "RepresentationID";
    private static final int REPRESENTATION_ID = 1;
    private static final String TIME = "Time";
    private static final int TIME_ID = 4;
    private final int identifierCount;
    private final String[] identifierFormatTags;
    private final int[] identifiers;
    private final String[] urlPieces;

    public static UrlTemplate compile(String template) {
        String[] urlPieces2 = new String[5];
        int[] identifiers2 = new int[4];
        String[] identifierFormatTags2 = new String[4];
        return new UrlTemplate(urlPieces2, identifiers2, identifierFormatTags2, parseTemplate(template, urlPieces2, identifiers2, identifierFormatTags2));
    }

    private UrlTemplate(String[] urlPieces2, int[] identifiers2, String[] identifierFormatTags2, int identifierCount2) {
        this.urlPieces = urlPieces2;
        this.identifiers = identifiers2;
        this.identifierFormatTags = identifierFormatTags2;
        this.identifierCount = identifierCount2;
    }

    public String buildUri(String representationId, long segmentNumber, int bandwidth, long time) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (true) {
            int i2 = this.identifierCount;
            if (i < i2) {
                builder.append(this.urlPieces[i]);
                int[] iArr = this.identifiers;
                if (iArr[i] == 1) {
                    builder.append(representationId);
                } else if (iArr[i] == 2) {
                    builder.append(String.format(Locale.US, this.identifierFormatTags[i], Long.valueOf(segmentNumber)));
                } else if (iArr[i] == 3) {
                    builder.append(String.format(Locale.US, this.identifierFormatTags[i], Integer.valueOf(bandwidth)));
                } else if (iArr[i] == 4) {
                    builder.append(String.format(Locale.US, this.identifierFormatTags[i], Long.valueOf(time)));
                }
                i++;
            } else {
                builder.append(this.urlPieces[i2]);
                return builder.toString();
            }
        }
    }

    private static int parseTemplate(String template, String[] urlPieces2, int[] identifiers2, String[] identifierFormatTags2) {
        urlPieces2[0] = "";
        int templateIndex = 0;
        int identifierCount2 = 0;
        while (templateIndex < template.length()) {
            int dollarIndex = template.indexOf("$", templateIndex);
            char c = 65535;
            if (dollarIndex == -1) {
                String valueOf = String.valueOf(urlPieces2[identifierCount2]);
                String valueOf2 = String.valueOf(template.substring(templateIndex));
                urlPieces2[identifierCount2] = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
                templateIndex = template.length();
            } else if (dollarIndex != templateIndex) {
                String valueOf3 = String.valueOf(urlPieces2[identifierCount2]);
                String valueOf4 = String.valueOf(template.substring(templateIndex, dollarIndex));
                urlPieces2[identifierCount2] = valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3);
                templateIndex = dollarIndex;
            } else if (template.startsWith(ESCAPED_DOLLAR, templateIndex)) {
                urlPieces2[identifierCount2] = String.valueOf(urlPieces2[identifierCount2]).concat("$");
                templateIndex += 2;
            } else {
                int secondIndex = template.indexOf("$", templateIndex + 1);
                String identifier = template.substring(templateIndex + 1, secondIndex);
                if (identifier.equals(REPRESENTATION)) {
                    identifiers2[identifierCount2] = 1;
                } else {
                    int formatTagIndex = identifier.indexOf("%0");
                    String formatTag = DEFAULT_FORMAT_TAG;
                    if (formatTagIndex != -1) {
                        formatTag = identifier.substring(formatTagIndex);
                        if (!formatTag.endsWith("d")) {
                            formatTag = String.valueOf(formatTag).concat("d");
                        }
                        identifier = identifier.substring(0, formatTagIndex);
                    }
                    int hashCode = identifier.hashCode();
                    if (hashCode != -1950496919) {
                        if (hashCode != 2606829) {
                            if (hashCode == 38199441 && identifier.equals(BANDWIDTH)) {
                                c = 1;
                            }
                        } else if (identifier.equals(TIME)) {
                            c = 2;
                        }
                    } else if (identifier.equals(NUMBER)) {
                        c = 0;
                    }
                    if (c == 0) {
                        identifiers2[identifierCount2] = 2;
                    } else if (c == 1) {
                        identifiers2[identifierCount2] = 3;
                    } else if (c != 2) {
                        String valueOf5 = String.valueOf(template);
                        throw new IllegalArgumentException(valueOf5.length() != 0 ? "Invalid template: ".concat(valueOf5) : new String("Invalid template: "));
                    } else {
                        identifiers2[identifierCount2] = 4;
                    }
                    identifierFormatTags2[identifierCount2] = formatTag;
                }
                identifierCount2++;
                urlPieces2[identifierCount2] = "";
                templateIndex = secondIndex + 1;
            }
        }
        return identifierCount2;
    }
}
