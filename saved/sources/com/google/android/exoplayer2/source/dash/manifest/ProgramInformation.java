package com.google.android.exoplayer2.source.dash.manifest;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.util.Util;

public class ProgramInformation {
    public final String copyright;
    public final String lang;
    public final String moreInformationURL;
    public final String source;
    public final String title;

    public ProgramInformation(String title2, String source2, String copyright2, String moreInformationURL2, String lang2) {
        this.title = title2;
        this.source = source2;
        this.copyright = copyright2;
        this.moreInformationURL = moreInformationURL2;
        this.lang = lang2;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ProgramInformation other = (ProgramInformation) obj;
        if (!Util.areEqual(this.title, other.title) || !Util.areEqual(this.source, other.source) || !Util.areEqual(this.copyright, other.copyright) || !Util.areEqual(this.moreInformationURL, other.moreInformationURL) || !Util.areEqual(this.lang, other.lang)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i = 17 * 31;
        String str = this.title;
        int i2 = 0;
        int result = (i + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.source;
        int result2 = (result + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.copyright;
        int result3 = (result2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        String str4 = this.moreInformationURL;
        int result4 = (result3 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.lang;
        if (str5 != null) {
            i2 = str5.hashCode();
        }
        return result4 + i2;
    }
}
