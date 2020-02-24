package com.google.android.libraries.social.analytics.visualelement;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.p001v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import com.google.android.libraries.social.analytics.binder.extensions.FragmentDeferredVisualElementProvider;
import com.google.android.libraries.stitch.binder.Binder;
import com.google.android.libraries.stitch.util.Objects;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class VisualElementPath implements Serializable {
    private static final String EXTRA_VISUAL_ELEMENT_PATH = VisualElementPath.class.getName();
    private static final String TAG = "VisualElementPath";
    private static final long serialVersionUID = 1;
    final List<VisualElement> visualElements = new ArrayList();

    public VisualElementPath add(VisualElement visualElement) {
        if (visualElement != null) {
            this.visualElements.add(visualElement);
            return this;
        }
        throw new NullPointerException();
    }

    public VisualElementPath add(Context context) {
        addChain(Binder.findBinder(context));
        addFromIntent(findIntent(context));
        return this;
    }

    private Intent findIntent(Context context) {
        while (true) {
            if (!(context instanceof Activity) && !(context instanceof ContextWrapper)) {
                return null;
            }
            if (context instanceof Activity) {
                return ((Activity) context).getIntent();
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
    }

    private void addFromIntent(Intent intent) {
        VisualElementPath v;
        if (intent != null && (v = (VisualElementPath) intent.getSerializableExtra(EXTRA_VISUAL_ELEMENT_PATH)) != null) {
            this.visualElements.addAll(v.getVisualElements());
        }
    }

    public VisualElementPath add(Context context, Fragment fragment) {
        addChain(Binder.findBinder(context, fragment));
        addFromIntent(findIntent(context));
        return this;
    }

    private void addChain(Binder binder) {
        List<VisualElementProvider> visualElementProviders = binder.getChain(VisualElementProvider.class);
        for (int i = 0; i < visualElementProviders.size(); i++) {
            VisualElement visualElement = ((VisualElementProvider) visualElementProviders.get(i)).getVisualElement();
            if (visualElement != null) {
                this.visualElements.add(visualElement);
            }
        }
        if (!this.visualElements.isEmpty()) {
            List<VisualElement> list = this.visualElements;
            if (list.get(list.size() - 1).tag.isRootPage) {
                return;
            }
        }
        addVisualElementsFromDeferredVisualElementProvider(binder);
    }

    private void addVisualElementsFromDeferredVisualElementProvider(Binder binder) {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Didn't find RootPage VE, querying deferredVisualElementProvider");
        }
        FragmentDeferredVisualElementProvider deferredVisualElementProvider = (FragmentDeferredVisualElementProvider) binder.getOptional(FragmentDeferredVisualElementProvider.class);
        if (deferredVisualElementProvider != null) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Found deferredVisualElementProvider");
            }
            VisualElement visualElement = deferredVisualElementProvider.getVisualElement();
            if (visualElement != null) {
                this.visualElements.add(visualElement);
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, String.format(Locale.US, "Found VE: %s resulting path: %s", visualElement, toString()));
                }
            }
        }
    }

    public VisualElementPath add(View view) {
        addVisualElement(view);
        for (ViewParent currentViewParent = view.getParent(); currentViewParent != null; currentViewParent = currentViewParent.getParent()) {
            if (currentViewParent instanceof View) {
                addVisualElement((View) currentViewParent);
            }
        }
        add(view.getContext());
        return this;
    }

    private void addVisualElement(View view) {
        VisualElement visualElement;
        if (view instanceof VisualElementProvider) {
            visualElement = ((VisualElementProvider) view).getVisualElement();
        } else {
            visualElement = VisualElementUtil.get(view);
        }
        if (visualElement != null) {
            this.visualElements.add(visualElement);
        }
    }

    public List<VisualElement> getVisualElements() {
        return this.visualElements;
    }

    public Intent addToIntent(Intent intent) {
        intent.putExtra(EXTRA_VISUAL_ELEMENT_PATH, this);
        return intent;
    }

    public String toString() {
        return VisualElementUtil.toVisualElementIdHierarchyString(this.visualElements);
    }

    public boolean equals(Object object) {
        if (!(object instanceof VisualElementPath)) {
            return false;
        }
        return Objects.equals(((VisualElementPath) object).getVisualElements(), getVisualElements());
    }

    public int hashCode() {
        return this.visualElements.hashCode();
    }
}
