package androidx.leanback.widget;

import android.support.annotation.RestrictTo;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.leanback.C0364R;
import androidx.leanback.widget.Presenter;

public class DividerPresenter extends Presenter {
    private final int mLayoutResourceId;

    public DividerPresenter() {
        this(C0364R.layout.lb_divider);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public DividerPresenter(int layoutResourceId) {
        this.mLayoutResourceId = layoutResourceId;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new Presenter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(this.mLayoutResourceId, parent, false));
    }

    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
    }

    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
    }
}
