package gen_binder;

import android.content.Context;
import com.google.android.libraries.gcoreclient.common.api.impl.StitchModule;
import com.google.android.libraries.stitch.binder.Binder;
import com.google.android.libraries.stitch.binder.Module;
import java.util.HashMap;

/* renamed from: gen_binder.com$google$android$libraries$gcoreclient$common$api$impl$StitchModule */
public final class C1936xe8119179 implements Module {
    private final boolean[] alreadyConfigured = new boolean[0];
    private HashMap<String, Integer> typeMap;

    private void buildTypeMap() {
        this.typeMap = new HashMap<>(5);
        addTypesToMap0();
    }

    private void addTypesToMap0() {
        this.typeMap.put(StitchModule.Adapter.GCOREGOOGLEAPICLIENT_BUILDER, 0);
        this.typeMap.put(StitchModule.Adapter.GCOREGOOGLEAPICLIENT_BUILDERFACTORY, 1);
        this.typeMap.put(StitchModule.Adapter.GCORESCOPE_BUILDER, 2);
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
            if (index.intValue() < 1) {
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
            throw new IllegalStateException("Index not handled. This implies a bug in the ModuleCompiler.");
        } else if (key == null) {
            StitchModule.Adapter.bindGcoreGoogleApiClient_Builder(context, binder);
        }
    }

    private void switch2(Context context, int index, Object key, Binder binder) {
        if (key == null) {
        }
        if (index != 1) {
            if (index != 2) {
                throw new IllegalStateException("Index not handled. This implies a bug in the ModuleCompiler.");
            } else if (key == null) {
                StitchModule.Adapter.bindGcoreScope_Builder(context, binder);
            }
        } else if (key == null) {
            StitchModule.Adapter.bindGcoreGoogleApiClient_BuilderFactory(context, binder);
        }
    }
}
