package com.google.android.libraries.gcoreclient.common.api.support;

import com.google.android.libraries.gcoreclient.common.api.GcoreScope;

public class GcoreScopeImpl implements GcoreScope {
    String scopeUri;

    public static class Builder implements GcoreScope.Builder {
        String scopeUri = null;

        public GcoreScope.Builder setScopeUri(String scopeUri2) {
            this.scopeUri = scopeUri2;
            return this;
        }

        public GcoreScope build() {
            return new GcoreScopeImpl(this.scopeUri);
        }
    }

    public String getScopeUri() {
        return this.scopeUri;
    }

    private GcoreScopeImpl(String scopeUri2) {
        this.scopeUri = scopeUri2;
    }
}
