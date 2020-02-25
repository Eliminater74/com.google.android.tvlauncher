package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.p001v4.util.Pools;
import android.util.Log;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.util.Preconditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DecodePath<DataType, ResourceType, Transcode> {
    private static final String TAG = "DecodePath";
    private final Class<DataType> dataClass;
    private final List<? extends ResourceDecoder<DataType, ResourceType>> decoders;
    private final String failureMessage;
    private final Pools.Pool<List<Throwable>> listPool;
    private final ResourceTranscoder<ResourceType, Transcode> transcoder;

    public DecodePath(Class<DataType> dataClass2, Class<ResourceType> resourceClass, Class<Transcode> transcodeClass, List<? extends ResourceDecoder<DataType, ResourceType>> decoders2, ResourceTranscoder<ResourceType, Transcode> transcoder2, Pools.Pool<List<Throwable>> listPool2) {
        this.dataClass = dataClass2;
        this.decoders = decoders2;
        this.transcoder = transcoder2;
        this.listPool = listPool2;
        String simpleName = dataClass2.getSimpleName();
        String simpleName2 = resourceClass.getSimpleName();
        String simpleName3 = transcodeClass.getSimpleName();
        StringBuilder sb = new StringBuilder(String.valueOf(simpleName).length() + 23 + String.valueOf(simpleName2).length() + String.valueOf(simpleName3).length());
        sb.append("Failed DecodePath{");
        sb.append(simpleName);
        sb.append("->");
        sb.append(simpleName2);
        sb.append("->");
        sb.append(simpleName3);
        sb.append("}");
        this.failureMessage = sb.toString();
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [com.bumptech.glide.load.data.DataRewinder<DataType>, com.bumptech.glide.load.data.DataRewinder] */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.bumptech.glide.load.engine.DecodePath$DecodeCallback, com.bumptech.glide.load.engine.DecodePath$DecodeCallback<ResourceType>] */
    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: ?
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public com.bumptech.glide.load.engine.Resource<Transcode> decode(com.bumptech.glide.load.data.DataRewinder<DataType> r4, int r5, int r6, @android.support.annotation.NonNull com.bumptech.glide.load.Options r7, com.bumptech.glide.load.engine.DecodePath.DecodeCallback<ResourceType> r8) throws com.bumptech.glide.load.engine.GlideException {
        /*
            r3 = this;
            com.bumptech.glide.load.engine.Resource r0 = r3.decodeResource(r4, r5, r6, r7)
            com.bumptech.glide.load.engine.Resource r1 = r8.onResourceDecoded(r0)
            com.bumptech.glide.load.resource.transcode.ResourceTranscoder<ResourceType, Transcode> r2 = r3.transcoder
            com.bumptech.glide.load.engine.Resource r2 = r2.transcode(r1, r7)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.DecodePath.decode(com.bumptech.glide.load.data.DataRewinder, int, int, com.bumptech.glide.load.Options, com.bumptech.glide.load.engine.DecodePath$DecodeCallback):com.bumptech.glide.load.engine.Resource");
    }

    @NonNull
    private Resource<ResourceType> decodeResource(DataRewinder<DataType> rewinder, int width, int height, @NonNull Options options) throws GlideException {
        List<Throwable> exceptions = (List) Preconditions.checkNotNull(this.listPool.acquire());
        try {
            return decodeResourceWithList(rewinder, width, height, options, exceptions);
        } finally {
            this.listPool.release(exceptions);
        }
    }

    @NonNull
    private Resource<ResourceType> decodeResourceWithList(DataRewinder<DataType> rewinder, int width, int height, @NonNull Options options, List<Throwable> exceptions) throws GlideException {
        Resource<ResourceType> result = null;
        int size = this.decoders.size();
        for (int i = 0; i < size; i++) {
            ResourceDecoder<DataType, ResourceType> decoder = (ResourceDecoder) this.decoders.get(i);
            try {
                if (decoder.handles(rewinder.rewindAndGet(), options)) {
                    result = decoder.decode(rewinder.rewindAndGet(), width, height, options);
                }
            } catch (IOException | OutOfMemoryError | RuntimeException e) {
                if (Log.isLoggable(TAG, 2)) {
                    String valueOf = String.valueOf(decoder);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 26);
                    sb.append("Failed to decode data for ");
                    sb.append(valueOf);
                    Log.v(TAG, sb.toString(), e);
                }
                exceptions.add(e);
            }
            if (result != null) {
                break;
            }
        }
        if (result != null) {
            return result;
        }
        throw new GlideException(this.failureMessage, new ArrayList(exceptions));
    }

    public String toString() {
        String valueOf = String.valueOf(this.dataClass);
        String valueOf2 = String.valueOf(this.decoders);
        String valueOf3 = String.valueOf(this.transcoder);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 47 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length());
        sb.append("DecodePath{ dataClass=");
        sb.append(valueOf);
        sb.append(", decoders=");
        sb.append(valueOf2);
        sb.append(", transcoder=");
        sb.append(valueOf3);
        sb.append('}');
        return sb.toString();
    }

    interface DecodeCallback<ResourceType> {
        @NonNull
        Resource<ResourceType> onResourceDecoded(@NonNull Resource<ResourceType> resource);
    }
}
