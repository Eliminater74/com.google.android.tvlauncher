package androidx.leanback.widget;

import android.support.annotation.RestrictTo;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.leanback.widget.GuidedActionAdapter;
import androidx.leanback.widget.GuidedActionsStylist;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class GuidedActionAdapterGroup {
    private static final boolean DEBUG_EDIT = false;
    private static final String TAG_EDIT = "EditableAction";
    ArrayList<Pair<GuidedActionAdapter, GuidedActionAdapter>> mAdapters = new ArrayList<>();
    private GuidedActionAdapter.EditListener mEditListener;
    private boolean mImeOpened;

    public void addAdpter(GuidedActionAdapter adapter1, GuidedActionAdapter adapter2) {
        this.mAdapters.add(new Pair(adapter1, adapter2));
        if (adapter1 != null) {
            adapter1.mGroup = this;
        }
        if (adapter2 != null) {
            adapter2.mGroup = this;
        }
    }

    public GuidedActionAdapter getNextAdapter(GuidedActionAdapter adapter) {
        for (int i = 0; i < this.mAdapters.size(); i++) {
            Pair<GuidedActionAdapter, GuidedActionAdapter> pair = this.mAdapters.get(i);
            if (pair.first == adapter) {
                return (GuidedActionAdapter) pair.second;
            }
        }
        return null;
    }

    public void setEditListener(GuidedActionAdapter.EditListener listener) {
        this.mEditListener = listener;
    }

    /* access modifiers changed from: package-private */
    public boolean focusToNextAction(GuidedActionAdapter adapter, GuidedAction action, long nextActionId) {
        int index = 0;
        if (nextActionId == -2) {
            int index2 = adapter.indexOf(action);
            if (index2 < 0) {
                return false;
            }
            index = index2 + 1;
        }
        while (true) {
            int size = adapter.getCount();
            if (nextActionId == -2) {
                while (index < size && !adapter.getItem(index).isFocusable()) {
                    index++;
                }
            } else {
                while (index < size && adapter.getItem(index).getId() != nextActionId) {
                    index++;
                }
            }
            if (index < size) {
                GuidedActionsStylist.ViewHolder vh = (GuidedActionsStylist.ViewHolder) adapter.getGuidedActionsStylist().getActionsGridView().findViewHolderForPosition(index);
                if (vh == null) {
                    return false;
                }
                if (vh.getAction().hasTextEditable()) {
                    openIme(adapter, vh);
                } else {
                    closeIme(vh.itemView);
                    vh.itemView.requestFocus();
                }
                return true;
            }
            adapter = getNextAdapter(adapter);
            if (adapter == null) {
                return false;
            }
            index = 0;
        }
    }

    public void openIme(GuidedActionAdapter adapter, GuidedActionsStylist.ViewHolder avh) {
        adapter.getGuidedActionsStylist().setEditingMode(avh, true);
        View v = avh.getEditingView();
        if (v != null && avh.isInEditingText()) {
            v.setFocusable(true);
            v.requestFocus();
            ((InputMethodManager) v.getContext().getSystemService("input_method")).showSoftInput(v, 0);
            if (!this.mImeOpened) {
                this.mImeOpened = true;
                this.mEditListener.onImeOpen();
            }
        }
    }

    public void closeIme(View v) {
        if (this.mImeOpened) {
            this.mImeOpened = false;
            ((InputMethodManager) v.getContext().getSystemService("input_method")).hideSoftInputFromWindow(v.getWindowToken(), 0);
            this.mEditListener.onImeClose();
        }
    }

    public void fillAndStay(GuidedActionAdapter adapter, TextView v) {
        GuidedActionsStylist.ViewHolder avh = adapter.findSubChildViewHolder(v);
        updateTextIntoAction(avh, v);
        this.mEditListener.onGuidedActionEditCanceled(avh.getAction());
        adapter.getGuidedActionsStylist().setEditingMode(avh, false);
        closeIme(v);
        avh.itemView.requestFocus();
    }

    public void fillAndGoNext(GuidedActionAdapter adapter, TextView v) {
        boolean handled = false;
        GuidedActionsStylist.ViewHolder avh = adapter.findSubChildViewHolder(v);
        updateTextIntoAction(avh, v);
        adapter.performOnActionClick(avh);
        long nextActionId = this.mEditListener.onGuidedActionEditedAndProceed(avh.getAction());
        adapter.getGuidedActionsStylist().setEditingMode(avh, false);
        if (!(nextActionId == -3 || nextActionId == avh.getAction().getId())) {
            handled = focusToNextAction(adapter, avh.getAction(), nextActionId);
        }
        if (!handled) {
            closeIme(v);
            avh.itemView.requestFocus();
        }
    }

    private void updateTextIntoAction(GuidedActionsStylist.ViewHolder avh, TextView v) {
        GuidedAction action = avh.getAction();
        if (v == avh.getDescriptionView()) {
            if (action.getEditDescription() != null) {
                action.setEditDescription(v.getText());
            } else {
                action.setDescription(v.getText());
            }
        } else if (v != avh.getTitleView()) {
        } else {
            if (action.getEditTitle() != null) {
                action.setEditTitle(v.getText());
            } else {
                action.setTitle(v.getText());
            }
        }
    }
}
