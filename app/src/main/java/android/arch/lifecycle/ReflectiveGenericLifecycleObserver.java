package android.arch.lifecycle;

class ReflectiveGenericLifecycleObserver implements GenericLifecycleObserver {
    private final Object mWrapped;
    private final ClassesInfoCache.CallbackInfo mInfo = ClassesInfoCache.sInstance.getInfo(this.mWrapped.getClass());

    ReflectiveGenericLifecycleObserver(Object wrapped) {
        this.mWrapped = wrapped;
    }

    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        this.mInfo.invokeCallbacks(source, event, this.mWrapped);
    }
}
