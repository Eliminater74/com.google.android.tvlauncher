package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

/* compiled from: BaseHttpStack */
public abstract class zzai implements zzar {
    public abstract zzaq zza(zzr<?> zzr, Map<String, String> map) throws IOException, zza;

    @Deprecated
    public final HttpResponse zzb(zzr<?> zzr, Map<String, String> map) throws IOException, zza {
        zzaq zza = zza(zzr, map);
        BasicHttpResponse basicHttpResponse = new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), zza.zza(), ""));
        ArrayList arrayList = new ArrayList();
        for (zzl next : zza.zzb()) {
            arrayList.add(new BasicHeader(next.zza(), next.zzb()));
        }
        basicHttpResponse.setHeaders((Header[]) arrayList.toArray(new Header[arrayList.size()]));
        InputStream zzd = zza.zzd();
        if (zzd != null) {
            BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
            basicHttpEntity.setContent(zzd);
            basicHttpEntity.setContentLength((long) zza.zzc());
            basicHttpResponse.setEntity(basicHttpEntity);
        }
        return basicHttpResponse;
    }
}
