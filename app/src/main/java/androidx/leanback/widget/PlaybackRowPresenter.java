package androidx.leanback.widget;

import android.view.View;

public abstract class PlaybackRowPresenter extends RowPresenter {

    public void onReappear(RowPresenter.ViewHolder rowViewHolder) {
    }

    public static class ViewHolder extends RowPresenter.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
