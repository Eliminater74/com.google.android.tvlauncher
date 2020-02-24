package com.google.android.libraries.stitch.binder;

import android.content.Context;
import android.util.Log;
import com.google.android.libraries.stitch.binder.BinderProvider;
import java.util.Set;

public final class RootBinderInitializer implements BinderProvider.Initializer {
    private static final String ROOT_MODULE_CLASS_NAME = "gen_binder.root.RootModule$Generated";
    private static final String TAG = "Binder";

    public void initBinder(Context appContext, Binder binder) {
        Set<String> sourceModules = null;
        try {
            RootModule rootModule = (RootModule) Class.forName(ROOT_MODULE_CLASS_NAME).newInstance();
            sourceModules = rootModule.getSourceModuleNames();
            binder.bind(rootModule);
            rootModule.bindConstants(binder);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to instantiate root module gen_binder.root.RootModule$Generated", e);
        } catch (InstantiationException e2) {
            throw new IllegalStateException("Failed to instantiate root module gen_binder.root.RootModule$Generated", e2);
        } catch (ClassNotFoundException e3) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "To use Binder more efficiently, your android_binary target should include \"//java/com/google/android/libraries/stitch/binder:rootmodule\" in srcs.");
            }
        }
        binder.bind(new AndroidManifestModule(appContext, sourceModules));
        binder.useFineGrainedLocks();
    }
}
