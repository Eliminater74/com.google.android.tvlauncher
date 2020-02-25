package com.google.android.gms.common.server.response;

import java.io.BufferedReader;
import java.io.IOException;

/* compiled from: FastParser */
final class zzb implements FastParser.zza<Long> {
    zzb() {
    }

    public final /* synthetic */ Object zza(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        return Long.valueOf(fastParser.zze(bufferedReader));
    }
}
