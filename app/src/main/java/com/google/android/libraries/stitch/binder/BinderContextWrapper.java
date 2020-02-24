package com.google.android.libraries.stitch.binder;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;

public final class BinderContextWrapper extends ContextWrapper implements BinderContext {
    private final Binder binder;
    private LayoutInflater layoutInflater;

    public BinderContextWrapper(Context context, Binder parentBinder) {
        super(context);
        if (context != null) {
            this.binder = new Binder(this, parentBinder);
            return;
        }
        throw new IllegalArgumentException("Cannot construct BinderContextWrapper with null Context");
    }

    public BinderContextWrapper(Context context) {
        this(context, Binder.findBinder(context));
    }

    public BinderContextWrapper() {
        super(null);
        this.binder = new Binder(this, null);
    }

    @Deprecated
    public void attachContext(Context context) {
        attachBaseContext(context);
    }

    public void attachBaseContext(Context base) {
        if (getBaseContext() == null || !getBaseContext().equals(base)) {
            super.attachBaseContext(base);
        }
    }

    public void attachParentBinder(Binder binder2) {
        this.binder.attachParent(binder2);
    }

    public Binder getBinder() {
        return this.binder;
    }

    public Object getSystemService(String name) {
        if (!name.equals("layout_inflater")) {
            return super.getSystemService(name);
        }
        if (this.layoutInflater == null) {
            this.layoutInflater = ((LayoutInflater) super.getSystemService(name)).cloneInContext(this);
        }
        return this.layoutInflater;
    }
}
