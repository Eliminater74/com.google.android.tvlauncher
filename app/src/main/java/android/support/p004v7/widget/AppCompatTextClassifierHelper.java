package android.support.p004v7.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.p001v4.util.Preconditions;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;

/* renamed from: android.support.v7.widget.AppCompatTextClassifierHelper */
final class AppCompatTextClassifierHelper {
    @Nullable
    private TextClassifier mTextClassifier;
    @NonNull
    private TextView mTextView;

    AppCompatTextClassifierHelper(TextView textView) {
        this.mTextView = (TextView) Preconditions.checkNotNull(textView);
    }

    @RequiresApi(api = 26)
    @NonNull
    public TextClassifier getTextClassifier() {
        TextClassifier textClassifier = this.mTextClassifier;
        if (textClassifier != null) {
            return textClassifier;
        }
        TextClassificationManager tcm = (TextClassificationManager) this.mTextView.getContext().getSystemService(TextClassificationManager.class);
        if (tcm != null) {
            return tcm.getTextClassifier();
        }
        return TextClassifier.NO_OP;
    }

    @RequiresApi(api = 26)
    public void setTextClassifier(@Nullable TextClassifier textClassifier) {
        this.mTextClassifier = textClassifier;
    }
}
