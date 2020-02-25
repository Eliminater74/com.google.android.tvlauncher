package androidx.leanback.widget;

import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.p004v7.util.DiffUtil;
import android.support.p004v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class GuidedActionAdapter extends RecyclerView.Adapter {
    static final boolean DEBUG = false;
    static final boolean DEBUG_EDIT = false;
    static final String TAG = "GuidedActionAdapter";
    static final String TAG_EDIT = "EditableAction";
    final List<GuidedAction> mActions;
    final GuidedActionsStylist mStylist;
    private final ActionAutofillListener mActionAutofillListener;
    private final ActionEditListener mActionEditListener;
    private final ActionOnFocusListener mActionOnFocusListener;
    private final ActionOnKeyListener mActionOnKeyListener;
    private final boolean mIsSubAdapter;
    DiffCallback<GuidedAction> mDiffCallback;
    GuidedActionAdapterGroup mGroup;
    private ClickListener mClickListener;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v != null && v.getWindowToken() != null && GuidedActionAdapter.this.getRecyclerView() != null) {
                GuidedActionsStylist.ViewHolder avh = (GuidedActionsStylist.ViewHolder) GuidedActionAdapter.this.getRecyclerView().getChildViewHolder(v);
                GuidedAction action = avh.getAction();
                if (action.hasTextEditable()) {
                    GuidedActionAdapter.this.mGroup.openIme(GuidedActionAdapter.this, avh);
                } else if (action.hasEditableActivatorView()) {
                    GuidedActionAdapter.this.performOnActionClick(avh);
                } else {
                    GuidedActionAdapter.this.handleCheckedActions(avh);
                    if (action.isEnabled() && !action.infoOnly()) {
                        GuidedActionAdapter.this.performOnActionClick(avh);
                    }
                }
            }
        }
    };

    public GuidedActionAdapter(List<GuidedAction> actions, ClickListener clickListener, FocusListener focusListener, GuidedActionsStylist presenter, boolean isSubAdapter) {
        ArrayList arrayList;
        if (actions != null) {
            arrayList = new ArrayList(actions);
        }
        this.mActions = arrayList;
        this.mClickListener = clickListener;
        this.mStylist = presenter;
        this.mActionOnKeyListener = new ActionOnKeyListener();
        this.mActionOnFocusListener = new ActionOnFocusListener(focusListener);
        this.mActionEditListener = new ActionEditListener();
        this.mActionAutofillListener = new ActionAutofillListener();
        this.mIsSubAdapter = isSubAdapter;
        if (!isSubAdapter) {
            this.mDiffCallback = GuidedActionDiffCallback.getInstance();
        }
    }

    public void setDiffCallback(DiffCallback<GuidedAction> diffCallback) {
        this.mDiffCallback = diffCallback;
    }

    public int getCount() {
        return this.mActions.size();
    }

    public GuidedAction getItem(int position) {
        return this.mActions.get(position);
    }

    public int indexOf(GuidedAction action) {
        return this.mActions.indexOf(action);
    }

    public GuidedActionsStylist getGuidedActionsStylist() {
        return this.mStylist;
    }

    public void setClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public void setFocusListener(FocusListener focusListener) {
        this.mActionOnFocusListener.setFocusListener(focusListener);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public List<GuidedAction> getActions() {
        return new ArrayList(this.mActions);
    }

    public void setActions(List<GuidedAction> actions) {
        if (!this.mIsSubAdapter) {
            this.mStylist.collapseAction(false);
        }
        this.mActionOnFocusListener.unFocus();
        if (this.mDiffCallback != null) {
            final List<GuidedAction> oldActions = new ArrayList<>();
            oldActions.addAll(this.mActions);
            this.mActions.clear();
            this.mActions.addAll(actions);
            DiffUtil.calculateDiff(new DiffUtil.Callback() {
                public int getOldListSize() {
                    return oldActions.size();
                }

                public int getNewListSize() {
                    return GuidedActionAdapter.this.mActions.size();
                }

                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return GuidedActionAdapter.this.mDiffCallback.areItemsTheSame(oldActions.get(oldItemPosition), GuidedActionAdapter.this.mActions.get(newItemPosition));
                }

                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return GuidedActionAdapter.this.mDiffCallback.areContentsTheSame(oldActions.get(oldItemPosition), GuidedActionAdapter.this.mActions.get(newItemPosition));
                }

                @Nullable
                public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                    return GuidedActionAdapter.this.mDiffCallback.getChangePayload(oldActions.get(oldItemPosition), GuidedActionAdapter.this.mActions.get(newItemPosition));
                }
            }).dispatchUpdatesTo(this);
            return;
        }
        this.mActions.clear();
        this.mActions.addAll(actions);
        notifyDataSetChanged();
    }

    public int getItemViewType(int position) {
        return this.mStylist.getItemViewType(this.mActions.get(position));
    }

    /* access modifiers changed from: package-private */
    public RecyclerView getRecyclerView() {
        return this.mIsSubAdapter ? this.mStylist.getSubActionsGridView() : this.mStylist.getActionsGridView();
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GuidedActionsStylist.ViewHolder vh = this.mStylist.onCreateViewHolder(parent, viewType);
        View v = vh.itemView;
        v.setOnKeyListener(this.mActionOnKeyListener);
        v.setOnClickListener(this.mOnClickListener);
        v.setOnFocusChangeListener(this.mActionOnFocusListener);
        setupListeners(vh.getEditableTitleView());
        setupListeners(vh.getEditableDescriptionView());
        return vh;
    }

    private void setupListeners(EditText edit) {
        if (edit != null) {
            edit.setPrivateImeOptions("escapeNorth");
            edit.setOnEditorActionListener(this.mActionEditListener);
            if (edit instanceof ImeKeyMonitor) {
                ((ImeKeyMonitor) edit).setImeKeyListener(this.mActionEditListener);
            }
            if (edit instanceof GuidedActionAutofillSupport) {
                ((GuidedActionAutofillSupport) edit).setOnAutofillListener(this.mActionAutofillListener);
            }
        }
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < this.mActions.size()) {
            this.mStylist.onBindViewHolder((GuidedActionsStylist.ViewHolder) holder, this.mActions.get(position));
        }
    }

    public int getItemCount() {
        return this.mActions.size();
    }

    public GuidedActionsStylist.ViewHolder findSubChildViewHolder(View v) {
        if (getRecyclerView() == null) {
            return null;
        }
        ViewParent parent = v.getParent();
        while (parent != getRecyclerView() && parent != null) {
            v = (View) parent;
            parent = parent.getParent();
        }
        if (parent != null) {
            return (GuidedActionsStylist.ViewHolder) getRecyclerView().getChildViewHolder(v);
        }
        return null;
    }

    public void handleCheckedActions(GuidedActionsStylist.ViewHolder avh) {
        GuidedAction action = avh.getAction();
        int actionCheckSetId = action.getCheckSetId();
        if (getRecyclerView() != null && actionCheckSetId != 0) {
            if (actionCheckSetId != -1) {
                int size = this.mActions.size();
                for (int i = 0; i < size; i++) {
                    GuidedAction a = this.mActions.get(i);
                    if (a != action && a.getCheckSetId() == actionCheckSetId && a.isChecked()) {
                        a.setChecked(false);
                        GuidedActionsStylist.ViewHolder vh = (GuidedActionsStylist.ViewHolder) getRecyclerView().findViewHolderForPosition(i);
                        if (vh != null) {
                            this.mStylist.onAnimateItemChecked(vh, false);
                        }
                    }
                }
            }
            if (action.isChecked() == 0) {
                action.setChecked(true);
                this.mStylist.onAnimateItemChecked(avh, true);
            } else if (actionCheckSetId == -1) {
                action.setChecked(false);
                this.mStylist.onAnimateItemChecked(avh, false);
            }
        }
    }

    public void performOnActionClick(GuidedActionsStylist.ViewHolder avh) {
        ClickListener clickListener = this.mClickListener;
        if (clickListener != null) {
            clickListener.onGuidedActionClicked(avh.getAction());
        }
    }

    public interface ClickListener {
        void onGuidedActionClicked(GuidedAction guidedAction);
    }

    public interface EditListener {
        void onGuidedActionEditCanceled(GuidedAction guidedAction);

        long onGuidedActionEditedAndProceed(GuidedAction guidedAction);

        void onImeClose();

        void onImeOpen();
    }

    public interface FocusListener {
        void onGuidedActionFocused(GuidedAction guidedAction);
    }

    private class ActionOnFocusListener implements View.OnFocusChangeListener {
        private FocusListener mFocusListener;
        private View mSelectedView;

        ActionOnFocusListener(FocusListener focusListener) {
            this.mFocusListener = focusListener;
        }

        public void setFocusListener(FocusListener focusListener) {
            this.mFocusListener = focusListener;
        }

        public void unFocus() {
            if (this.mSelectedView != null && GuidedActionAdapter.this.getRecyclerView() != null) {
                RecyclerView.ViewHolder vh = GuidedActionAdapter.this.getRecyclerView().getChildViewHolder(this.mSelectedView);
                if (vh != null) {
                    GuidedActionAdapter.this.mStylist.onAnimateItemFocused((GuidedActionsStylist.ViewHolder) vh, false);
                    return;
                }
                Log.w(GuidedActionAdapter.TAG, "RecyclerView returned null view holder", new Throwable());
            }
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (GuidedActionAdapter.this.getRecyclerView() != null) {
                GuidedActionsStylist.ViewHolder avh = (GuidedActionsStylist.ViewHolder) GuidedActionAdapter.this.getRecyclerView().getChildViewHolder(v);
                if (hasFocus) {
                    this.mSelectedView = v;
                    FocusListener focusListener = this.mFocusListener;
                    if (focusListener != null) {
                        focusListener.onGuidedActionFocused(avh.getAction());
                    }
                } else if (this.mSelectedView == v) {
                    GuidedActionAdapter.this.mStylist.onAnimateItemPressedCancelled(avh);
                    this.mSelectedView = null;
                }
                GuidedActionAdapter.this.mStylist.onAnimateItemFocused(avh, hasFocus);
            }
        }
    }

    private class ActionOnKeyListener implements View.OnKeyListener {
        private boolean mKeyPressed = false;

        ActionOnKeyListener() {
        }

        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (v == null || event == null || GuidedActionAdapter.this.getRecyclerView() == null) {
                return false;
            }
            if (keyCode == 23 || keyCode == 66 || keyCode == 160 || keyCode == 99 || keyCode == 100) {
                GuidedActionsStylist.ViewHolder avh = (GuidedActionsStylist.ViewHolder) GuidedActionAdapter.this.getRecyclerView().getChildViewHolder(v);
                GuidedAction action = avh.getAction();
                if (!action.isEnabled() || action.infoOnly()) {
                    event.getAction();
                    return true;
                }
                int action2 = event.getAction();
                if (action2 != 0) {
                    if (action2 == 1 && this.mKeyPressed) {
                        this.mKeyPressed = false;
                        GuidedActionAdapter.this.mStylist.onAnimateItemPressed(avh, this.mKeyPressed);
                    }
                } else if (!this.mKeyPressed) {
                    this.mKeyPressed = true;
                    GuidedActionAdapter.this.mStylist.onAnimateItemPressed(avh, this.mKeyPressed);
                }
            }
            return false;
        }
    }

    private class ActionEditListener implements TextView.OnEditorActionListener, ImeKeyMonitor.ImeKeyListener {
        ActionEditListener() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == 5 || actionId == 6) {
                GuidedActionAdapter.this.mGroup.fillAndGoNext(GuidedActionAdapter.this, v);
                return true;
            } else if (actionId != 1) {
                return false;
            } else {
                GuidedActionAdapter.this.mGroup.fillAndStay(GuidedActionAdapter.this, v);
                return true;
            }
        }

        public boolean onKeyPreIme(EditText editText, int keyCode, KeyEvent event) {
            if (keyCode == 4 && event.getAction() == 1) {
                GuidedActionAdapter.this.mGroup.fillAndStay(GuidedActionAdapter.this, editText);
                return true;
            } else if (keyCode != 66 || event.getAction() != 1) {
                return false;
            } else {
                GuidedActionAdapter.this.mGroup.fillAndGoNext(GuidedActionAdapter.this, editText);
                return true;
            }
        }
    }

    private class ActionAutofillListener implements GuidedActionAutofillSupport.OnAutofillListener {
        ActionAutofillListener() {
        }

        public void onAutofill(View view) {
            GuidedActionAdapter.this.mGroup.fillAndGoNext(GuidedActionAdapter.this, (EditText) view);
        }
    }
}
