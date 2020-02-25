package com.google.android.gms.common.server.response;

import java.io.BufferedReader;
import java.io.IOException;

/* compiled from: FastParser */
final class zzd implements FastParser.zza<Double> {
    zzd() {
    }

    public final /* synthetic */ Object zza(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        return Double.valueOf(fastParser.zzh(bufferedReader));
    }
}
