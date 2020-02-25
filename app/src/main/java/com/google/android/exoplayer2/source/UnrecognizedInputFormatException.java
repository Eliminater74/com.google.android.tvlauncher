package com.google.android.exoplayer2.source;

import android.net.Uri;

import com.google.android.exoplayer2.ParserException;

public class UnrecognizedInputFormatException extends ParserException {
    public final Uri uri;

    public UnrecognizedInputFormatException(String message, Uri uri2) {
        super(message);
        this.uri = uri2;
    }
}
