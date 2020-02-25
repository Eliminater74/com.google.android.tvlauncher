package android.support.p004v7.view;

import android.support.annotation.RestrictTo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/* renamed from: android.support.v7.view.ActionMode */
public abstract class ActionMode {
    private Object mTag;
    private boolean mTitleOptionalHint;

    public abstract void finish();

    public abstract View getCustomView();

    public abstract void setCustomView(View view);

    public abstract Menu getMenu();

    public abstract MenuInflater getMenuInflater();

    public abstract CharSequence getSubtitle();

    public abstract void setSubtitle(int i);

    public abstract void setSubtitle(CharSequence charSequence);

    public abstract CharSequence getTitle();

    public abstract void setTitle(int i);

    public abstract void setTitle(CharSequence charSequence);

    public abstract void invalidate();

    public Object getTag() {
        return this.mTag;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    public boolean getTitleOptionalHint() {
        return this.mTitleOptionalHint;
    }

    public void setTitleOptionalHint(boolean titleOptional) {
        this.mTitleOptionalHint = titleOptional;
    }

    public boolean isTitleOptional() {
        return false;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isUiFocusable() {
        return true;
    }

    /* renamed from: android.support.v7.view.ActionMode$Callback */
    public interface Callback {
        boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem);

        boolean onCreateActionMode(ActionMode actionMode, Menu menu);

        void onDestroyActionMode(ActionMode actionMode);

        boolean onPrepareActionMode(ActionMode actionMode, Menu menu);
    }
}
