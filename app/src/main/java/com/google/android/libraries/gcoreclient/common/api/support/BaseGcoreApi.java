package com.google.android.libraries.gcoreclient.common.api.support;

import com.google.android.gms.common.api.Api;
import com.google.android.libraries.gcoreclient.common.api.GcoreApi;
import com.google.android.libraries.gcoreclient.common.api.support.BaseGcoreApi.BaseGcoreApiOptions;

public interface BaseGcoreApi<O extends BaseGcoreApiOptions> extends GcoreApi<O> {

    Api getApi();

    public interface BaseGcoreApiOptions extends GcoreApi.GcoreApiOptions {

        public interface BaseGcoreHasOptions extends BaseGcoreApiOptions, GcoreApi.GcoreApiOptions.GcoreHasOptions {
            Api.ApiOptions.HasOptions getApiOptions();
        }

        public interface BaseGcoreNotRequiredOptions extends BaseGcoreApiOptions, GcoreApi.GcoreApiOptions.GcoreNotRequiredOptions {
            Api.ApiOptions.NotRequiredOptions getApiOptions();
        }

        public interface BaseGcoreOptional extends BaseGcoreApiOptions, GcoreApi.GcoreApiOptions.GcoreOptional {
            Api.ApiOptions.Optional getApiOptions();
        }

        public static final class BaseGcoreNoOptions implements BaseGcoreApiOptions, GcoreApi.GcoreApiOptions.GcoreNoOptions {
        }
    }
}
