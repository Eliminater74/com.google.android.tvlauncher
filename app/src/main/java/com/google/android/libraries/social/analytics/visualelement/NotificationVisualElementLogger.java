package com.google.android.libraries.social.analytics.visualelement;

public interface NotificationVisualElementLogger {
    void logAction(VisualElement visualElement, VisualElement visualElement2);

    void logDelete(VisualElement visualElement);

    void logShown(VisualElement visualElement);

    void logTapContent(VisualElement visualElement);
}
