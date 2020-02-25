package com.google.common.base;

import com.google.android.exoplayer2.C0841C;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;

import java.nio.charset.Charset;

@GwtCompatible(emulated = true)
public final class Charsets {
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    @GwtIncompatible
    public static final Charset US_ASCII = Charset.forName(C0841C.ASCII_NAME);
    @GwtIncompatible
    public static final Charset UTF_16 = Charset.forName(C0841C.UTF16_NAME);
    @GwtIncompatible
    public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
    @GwtIncompatible
    public static final Charset UTF_16LE = Charset.forName(C0841C.UTF16LE_NAME);
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private Charsets() {
    }
}
