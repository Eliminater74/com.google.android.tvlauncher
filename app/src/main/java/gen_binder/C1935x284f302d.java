package gen_binder;

import android.content.Context;

import com.google.android.libraries.gcoreclient.clearcut.impl.StitchModule;
import com.google.android.libraries.stitch.binder.Binder;
import com.google.android.libraries.stitch.binder.Module;

import java.util.HashMap;

/* renamed from: gen_binder.com$google$android$libraries$gcoreclient$clearcut$impl$StitchModule */
public final class C1935x284f302d implements Module {
    private final boolean[] alreadyConfigured = new boolean[0];
    private HashMap<String, Integer> typeMap;

    private void buildTypeMap() {
        this.typeMap = new HashMap<>(6);
        addTypesToMap0();
    }

    private void addTypesToMap0() {
        this.typeMap.put(StitchModule.Adapter.GCORECLEARCUTLOGGERFACTORY, 0);
        this.typeMap.put(StitchModule.Adapter.GCORECOUNTERSBUCKETALIASFACTORY, 1);
        this.typeMap.put(StitchModule.Adapter.GCORECLEARCUTAPI_BUILDER, 2);
        this.typeMap.put(StitchModule.Adapter.GCORECOUNTERSFACTORY, 3);
    }

    public void configure(Context context, Class<?> type, Binder binder) {
        configure(context, type, null, binder);
    }

    public void configure(Context context, Class<?> type, Object key, Binder binder) {
        synchronized (this) {
            if (this.typeMap == null) {
                buildTypeMap();
            }
        }
        Integer index = this.typeMap.get(type.getName());
        if (index != null) {
            if (index.intValue() < 2) {
                switch1(context, index.intValue(), key, binder);
            } else {
                switch2(context, index.intValue(), key, binder);
            }
        }
    }

    private void switch1(Context context, int index, Object key, Binder binder) {
        if (key == null) {
        }
        if (index != 0) {
            if (index != 1) {
                throw new IllegalStateException("Index not handled. This implies a bug in the ModuleCompiler.");
            } else if (key == null) {
                StitchModule.Adapter.bindGcoreCountersBucketAliasFactory(context, binder);
            }
        } else if (key == null) {
            StitchModule.Adapter.bindGcoreClearcutLoggerFactory(context, binder);
        }
    }

    private void switch2(Context context, int index, Object key, Binder binder) {
        if (key == null) {
        }
        if (index != 2) {
            if (index != 3) {
                throw new IllegalStateException("Index not handled. This implies a bug in the ModuleCompiler.");
            } else if (key == null) {
                StitchModule.Adapter.bindGcoreCountersFactory(context, binder);
            }
        } else if (key == null) {
            StitchModule.Adapter.bindGcoreClearcutApi_Builder(context, binder);
        }
    }
}
