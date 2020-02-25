package androidx.leanback.widget;

import android.view.KeyEvent;
import android.widget.EditText;

public interface ImeKeyMonitor {

    void setImeKeyListener(ImeKeyListener imeKeyListener);

    public interface ImeKeyListener {
        boolean onKeyPreIme(EditText editText, int i, KeyEvent keyEvent);
    }
}
