package com.google.android.gms.common.server.response;

import com.google.android.gms.common.server.response.FastParser;
import java.io.BufferedReader;
import java.io.IOException;

/* compiled from: FastParser */
final class zze implements FastParser.zza<Boolean> {
    zze() {
    }

    public final /* synthetic */ Object zza(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        return Boolean.valueOf(fastParser.zza(bufferedReader, false));
    }
}
