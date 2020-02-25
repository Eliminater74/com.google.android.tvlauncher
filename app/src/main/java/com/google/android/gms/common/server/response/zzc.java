package com.google.android.gms.common.server.response;

import java.io.BufferedReader;
import java.io.IOException;

/* compiled from: FastParser */
final class zzc implements FastParser.zza<Float> {
    zzc() {
    }

    public final /* synthetic */ Object zza(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        return Float.valueOf(fastParser.zzg(bufferedReader));
    }
}
