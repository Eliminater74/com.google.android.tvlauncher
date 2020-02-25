package com.google.android.libraries.gcoreclient.common.api.impl;

import android.content.Context;

import com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient;
import com.google.android.libraries.gcoreclient.common.api.GcoreScope;
import com.google.android.libraries.gcoreclient.common.api.support.GcoreScopeImpl;
import com.google.android.libraries.stitch.binder.Binder;
import com.google.android.libraries.stitch.binder.BinderModule;
import com.google.android.libraries.stitch.binder.Provides;

@BinderModule
public class StitchModule {
    @Provides
    public GcoreGoogleApiClient.Builder gcoreGoogleApiClientBuilder(Context context) {
        return new GcoreGoogleApiClientImpl.Builder(context);
    }

    @Provides
    public GcoreGoogleApiClient.BuilderFactory gcoreGoogleApiClientBuilderFactory() {
        return new GcoreGoogleApiClientImpl.BuilderFactory();
    }

    @Provides
    public GcoreScope.Builder gcoreScopeBuilder() {
        return new GcoreScopeImpl.Builder();
    }

    public final class Adapter {
        public static final String GCOREGOOGLEAPICLIENT_BUILDER = GcoreGoogleApiClient.Builder.class.getName();
        public static final String GCOREGOOGLEAPICLIENT_BUILDERFACTORY = GcoreGoogleApiClient.BuilderFactory.class.getName();
        public static final String GCORESCOPE_BUILDER = GcoreScope.Builder.class.getName();
        private static StitchModule module;

        public static void bindGcoreGoogleApiClient_Builder(Context context, Binder binder) {
            synchronized (Adapter.class) {
                if (module == null) {
                    module = new StitchModule();
                }
            }
            binder.bind(GcoreGoogleApiClient.Builder.class, module.gcoreGoogleApiClientBuilder(context));
        }

        public static void bindGcoreGoogleApiClient_BuilderFactory(Context context, Binder binder) {
            synchronized (Adapter.class) {
                if (module == null) {
                    module = new StitchModule();
                }
            }
            binder.bind(GcoreGoogleApiClient.BuilderFactory.class, module.gcoreGoogleApiClientBuilderFactory());
        }

        public static void bindGcoreScope_Builder(Context context, Binder binder) {
            synchronized (Adapter.class) {
                if (module == null) {
                    module = new StitchModule();
                }
            }
            binder.bind(GcoreScope.Builder.class, module.gcoreScopeBuilder());
        }
    }
}
