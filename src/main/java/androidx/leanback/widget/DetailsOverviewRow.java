package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DetailsOverviewRow extends Row {
    private ObjectAdapter mActionsAdapter = new ArrayObjectAdapter(this.mDefaultActionPresenter);
    private PresenterSelector mDefaultActionPresenter = new ActionPresenterSelector();
    private Drawable mImageDrawable;
    private boolean mImageScaleUpAllowed = true;
    private Object mItem;
    private ArrayList<WeakReference<Listener>> mListeners;

    public static class Listener {
        public void onImageDrawableChanged(DetailsOverviewRow row) {
        }

        public void onItemChanged(DetailsOverviewRow row) {
        }

        public void onActionsAdapterChanged(DetailsOverviewRow row) {
        }
    }

    public DetailsOverviewRow(Object item) {
        super(null);
        this.mItem = item;
        verify();
    }

    /* access modifiers changed from: package-private */
    public final void addListener(Listener listener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        } else {
            int i = 0;
            while (i < this.mListeners.size()) {
                Listener l = (Listener) this.mListeners.get(i).get();
                if (l == null) {
                    this.mListeners.remove(i);
                } else if (l != listener) {
                    i++;
                } else {
                    return;
                }
            }
        }
        this.mListeners.add(new WeakReference(listener));
    }

    /* access modifiers changed from: package-private */
    public final void removeListener(Listener listener) {
        if (this.mListeners != null) {
            int i = 0;
            while (i < this.mListeners.size()) {
                Listener l = (Listener) this.mListeners.get(i).get();
                if (l == null) {
                    this.mListeners.remove(i);
                } else if (l == listener) {
                    this.mListeners.remove(i);
                    return;
                } else {
                    i++;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void notifyItemChanged() {
        if (this.mListeners != null) {
            int i = 0;
            while (i < this.mListeners.size()) {
                Listener l = (Listener) this.mListeners.get(i).get();
                if (l == null) {
                    this.mListeners.remove(i);
                } else {
                    l.onItemChanged(this);
                    i++;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void notifyImageDrawableChanged() {
        if (this.mListeners != null) {
            int i = 0;
            while (i < this.mListeners.size()) {
                Listener l = (Listener) this.mListeners.get(i).get();
                if (l == null) {
                    this.mListeners.remove(i);
                } else {
                    l.onImageDrawableChanged(this);
                    i++;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void notifyActionsAdapterChanged() {
        if (this.mListeners != null) {
            int i = 0;
            while (i < this.mListeners.size()) {
                Listener l = (Listener) this.mListeners.get(i).get();
                if (l == null) {
                    this.mListeners.remove(i);
                } else {
                    l.onActionsAdapterChanged(this);
                    i++;
                }
            }
        }
    }

    public final Object getItem() {
        return this.mItem;
    }

    public final void setItem(Object item) {
        if (item != this.mItem) {
            this.mItem = item;
            notifyItemChanged();
        }
    }

    public final void setImageDrawable(Drawable drawable) {
        if (this.mImageDrawable != drawable) {
            this.mImageDrawable = drawable;
            notifyImageDrawableChanged();
        }
    }

    public final void setImageBitmap(Context context, Bitmap bm) {
        this.mImageDrawable = new BitmapDrawable(context.getResources(), bm);
        notifyImageDrawableChanged();
    }

    public final Drawable getImageDrawable() {
        return this.mImageDrawable;
    }

    public void setImageScaleUpAllowed(boolean allowed) {
        if (allowed != this.mImageScaleUpAllowed) {
            this.mImageScaleUpAllowed = allowed;
            notifyImageDrawableChanged();
        }
    }

    public boolean isImageScaleUpAllowed() {
        return this.mImageScaleUpAllowed;
    }

    private ArrayObjectAdapter getArrayObjectAdapter() {
        return (ArrayObjectAdapter) this.mActionsAdapter;
    }

    @Deprecated
    public final void addAction(Action action) {
        getArrayObjectAdapter().add(action);
    }

    @Deprecated
    public final void addAction(int pos, Action action) {
        getArrayObjectAdapter().add(pos, action);
    }

    @Deprecated
    public final boolean removeAction(Action action) {
        return getArrayObjectAdapter().remove(action);
    }

    @Deprecated
    public final List<Action> getActions() {
        return getArrayObjectAdapter().unmodifiableList();
    }

    public final ObjectAdapter getActionsAdapter() {
        return this.mActionsAdapter;
    }

    public final void setActionsAdapter(ObjectAdapter adapter) {
        if (adapter != this.mActionsAdapter) {
            this.mActionsAdapter = adapter;
            if (this.mActionsAdapter.getPresenterSelector() == null) {
                this.mActionsAdapter.setPresenterSelector(this.mDefaultActionPresenter);
            }
            notifyActionsAdapterChanged();
        }
    }

    public Action getActionForKeyCode(int keyCode) {
        ObjectAdapter adapter = getActionsAdapter();
        if (adapter == null) {
            return null;
        }
        for (int i = 0; i < adapter.size(); i++) {
            Action action = (Action) adapter.get(i);
            if (action.respondsToKeyCode(keyCode)) {
                return action;
            }
        }
        return null;
    }

    private void verify() {
        if (this.mItem == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
    }
}
