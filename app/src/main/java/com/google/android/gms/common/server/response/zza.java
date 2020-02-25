package com.google.android.gms.common.server.response;

import java.io.BufferedReader;
import java.io.IOException;

/* compiled from: FastParser */
final class zza implements FastParser.zza<Integer> {
    zza() {
    }

    public final /* synthetic */ Object zza(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        return Integer.valueOf(fastParser.zzd(bufferedReader));
    }
}
