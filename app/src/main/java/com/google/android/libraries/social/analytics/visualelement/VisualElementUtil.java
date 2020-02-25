package com.google.android.libraries.social.analytics.visualelement;

import android.app.Activity;
import android.view.View;

import java.util.List;

public final class VisualElementUtil {
    private VisualElementUtil() {
    }

    public static VisualElement attach(View view, VisualElement visualElement) {
        if (!(view instanceof VisualElementProvider)) {
            view.setTag(C1171R.C1173id.analytics_visual_element_view_tag, visualElement);
            return visualElement;
        }
        throw new IllegalArgumentException(String.valueOf(view.getClass().getName()).concat(" implements VisualElementProvider; this metadata should be added to the result of VisualElementProvider.getVisualElement()."));
    }

    public static void detach(View view) {
        view.setTag(C1171R.C1173id.analytics_visual_element_view_tag, null);
    }

    public static VisualElement get(View view) {
        if (view instanceof VisualElementProvider) {
            return ((VisualElementProvider) view).getVisualElement();
        }
        return (VisualElement) view.getTag(C1171R.C1173id.analytics_visual_element_view_tag);
    }

    public static boolean hasVisualElement(View view) {
        return get(view) != null;
    }

    public static String toVisualElementIdHierarchyString(List<VisualElement> visualElementList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < visualElementList.size(); i++) {
            if (i > 0) {
                sb.append("->");
            }
            sb.append(visualElementList.get(i).getVisualElementId());
        }
        sb.append(" (leaf->root)");
        return sb.toString();
    }

    public static View getRootView(Activity activity) {
        return activity.findViewById(16908290);
    }

    public static void attachRoot(Activity activity, VisualElement rootPageVe) {
        attach(getRootView(activity), rootPageVe);
    }

    public static void attachRoot(Activity activity, VisualElementTag rootPageVeTag) {
        attach(getRootView(activity), new VisualElement(rootPageVeTag));
    }

    public static VisualElement getRoot(Activity activity) {
        return get(getRootView(activity));
    }
}
