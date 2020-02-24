package com.google.android.libraries.gcoreclient.common.api;

import android.accounts.Account;
import com.google.android.libraries.gcoreclient.common.api.GcoreApi.GcoreApiOptions;

public interface GcoreApi<O extends GcoreApiOptions> {

    public interface GcoreApiOptions {

        public interface GcoreHasAccountOptions extends GcoreHasOptions, GcoreNotRequiredOptions {
            Account getAccount();
        }

        public interface GcoreHasOptions extends GcoreApiOptions {
        }

        public interface GcoreNoOptions extends GcoreNotRequiredOptions {
        }

        public interface GcoreNotRequiredOptions extends GcoreApiOptions {
        }

        public interface GcoreOptional extends GcoreHasOptions, GcoreNotRequiredOptions {
        }
    }
}
