package com.google.android.libraries.social.analytics.visualelement;

import android.os.Bundle;
import android.view.View;

public interface ImpressionLogger {
    boolean isImpressionPreviouslyRecorded(VisualElement visualElement);

    void recordImpression(View view);

    void resetAllToImpressionable();

    void resetToImpressionable(View view);

    void restoreState(Bundle bundle);

    void saveState(Bundle bundle);
}
