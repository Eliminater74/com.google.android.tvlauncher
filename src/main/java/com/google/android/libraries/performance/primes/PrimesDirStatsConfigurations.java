package com.google.android.libraries.performance.primes;

import java.util.ArrayList;
import java.util.regex.Pattern;

public final class PrimesDirStatsConfigurations {
    private static final int MAX_FOLDER_DEPTH_DEFAULT = 5;
    private final boolean enabled;
    private final Pattern[] listFilesPatterns;
    private final int maxFolderDepth;

    public PrimesDirStatsConfigurations(boolean enabled2) {
        this(enabled2, 5, new Pattern[0]);
    }

    private PrimesDirStatsConfigurations(boolean enabled2, int maxFolderDepth2, Pattern... listFilesPatterns2) {
        this.enabled = enabled2;
        this.maxFolderDepth = maxFolderDepth2;
        this.listFilesPatterns = listFilesPatterns2;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public int getMaxFolderDepth() {
        return this.maxFolderDepth;
    }

    public Pattern[] getListFilesPatterns() {
        return (Pattern[]) this.listFilesPatterns.clone();
    }

    public Builder toBuilder() {
        Builder builder = newBuilder();
        boolean unused = builder.enabled = this.enabled;
        int unused2 = builder.maxFolderDepth = this.maxFolderDepth;
        for (Pattern pattern : this.listFilesPatterns) {
            builder.listFilesPatterns.add(pattern);
        }
        return builder;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public boolean enabled;
        /* access modifiers changed from: private */
        public final ArrayList<Pattern> listFilesPatterns;
        /* access modifiers changed from: private */
        public int maxFolderDepth;

        private Builder() {
            this.maxFolderDepth = 5;
            this.listFilesPatterns = new ArrayList<>();
        }

        public Builder setEnabled(boolean enabled2) {
            this.enabled = enabled2;
            return this;
        }

        public Builder setMaxFolderDepth(int maxFolderDepth2) {
            this.maxFolderDepth = maxFolderDepth2;
            return this;
        }

        public Builder addListFilesPattern(Pattern pattern) {
            this.listFilesPatterns.add(pattern);
            return this;
        }

        public Builder matchAllFiles() {
            this.listFilesPatterns.add(Pattern.compile(".*"));
            return this;
        }

        public Builder matchFilenames(String... filenames) {
            for (String filename : filenames) {
                ArrayList<Pattern> arrayList = this.listFilesPatterns;
                String valueOf = String.valueOf(Pattern.quote(filename));
                arrayList.add(Pattern.compile(valueOf.length() != 0 ? ".*/".concat(valueOf) : new String(".*/")));
            }
            return this;
        }

        public Builder matchFolders(String... folderNames) {
            for (String name : folderNames) {
                if (!name.endsWith("/")) {
                    name = String.valueOf(name).concat("/");
                }
                this.listFilesPatterns.add(Pattern.compile(String.valueOf(Pattern.quote(name)).concat("[^/]+")));
            }
            return this;
        }

        public Builder matchFileSuffixes(String... suffixes) {
            for (String suffix : suffixes) {
                ArrayList<Pattern> arrayList = this.listFilesPatterns;
                String valueOf = String.valueOf(suffix);
                String valueOf2 = String.valueOf(Pattern.quote(valueOf.length() != 0 ? ".".concat(valueOf) : new String(".")));
                arrayList.add(Pattern.compile(valueOf2.length() != 0 ? ".*[^/]+".concat(valueOf2) : new String(".*[^/]+")));
            }
            return this;
        }

        public PrimesDirStatsConfigurations build() {
            return new PrimesDirStatsConfigurations(this.enabled, this.maxFolderDepth, (Pattern[]) this.listFilesPatterns.toArray(new Pattern[0]));
        }
    }
}
