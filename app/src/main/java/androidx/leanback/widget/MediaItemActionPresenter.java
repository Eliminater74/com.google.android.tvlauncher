package androidx.leanback.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.leanback.C0364R;
import androidx.leanback.widget.MultiActionsProvider;
import androidx.leanback.widget.Presenter;

class MediaItemActionPresenter extends Presenter {
    MediaItemActionPresenter() {
    }

    static class ViewHolder extends Presenter.ViewHolder {
        final ImageView mIcon;

        public ViewHolder(View view) {
            super(view);
            this.mIcon = (ImageView) view.findViewById(C0364R.C0366id.actionIcon);
        }

        public ImageView getIcon() {
            return this.mIcon;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(C0364R.layout.lb_row_media_item_action, parent, false));
    }

    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        ((ViewHolder) viewHolder).getIcon().setImageDrawable(((MultiActionsProvider.MultiAction) item).getCurrentDrawable());
    }

    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
    }
}
