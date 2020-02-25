package androidx.leanback.widget;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.C0364R;

public class ControlButtonPresenterSelector extends PresenterSelector {
    private final Presenter mPrimaryPresenter = new ControlButtonPresenter(C0364R.layout.lb_control_button_primary);
    private final Presenter[] mPresenters = {this.mPrimaryPresenter};
    private final Presenter mSecondaryPresenter = new ControlButtonPresenter(C0364R.layout.lb_control_button_secondary);

    public Presenter getPrimaryPresenter() {
        return this.mPrimaryPresenter;
    }

    public Presenter getSecondaryPresenter() {
        return this.mSecondaryPresenter;
    }

    public Presenter getPresenter(Object item) {
        return this.mPrimaryPresenter;
    }

    public Presenter[] getPresenters() {
        return this.mPresenters;
    }

    static class ActionViewHolder extends Presenter.ViewHolder {
        View mFocusableView;
        ImageView mIcon;
        TextView mLabel;

        public ActionViewHolder(View view) {
            super(view);
            this.mIcon = (ImageView) view.findViewById(C0364R.C0366id.icon);
            this.mLabel = (TextView) view.findViewById(C0364R.C0366id.label);
            this.mFocusableView = view.findViewById(C0364R.C0366id.button);
        }
    }

    static class ControlButtonPresenter extends Presenter {
        private int mLayoutResourceId;

        ControlButtonPresenter(int layoutResourceId) {
            this.mLayoutResourceId = layoutResourceId;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
         arg types: [int, android.view.ViewGroup, int]
         candidates:
          ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
          ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
        public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
            return new ActionViewHolder(LayoutInflater.from(parent.getContext()).inflate(this.mLayoutResourceId, parent, false));
        }

        public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
            Action action = (Action) item;
            ActionViewHolder vh = (ActionViewHolder) viewHolder;
            vh.mIcon.setImageDrawable(action.getIcon());
            if (vh.mLabel != null) {
                if (action.getIcon() == null) {
                    vh.mLabel.setText(action.getLabel1());
                } else {
                    vh.mLabel.setText((CharSequence) null);
                }
            }
            CharSequence contentDescription = TextUtils.isEmpty(action.getLabel2()) ? action.getLabel1() : action.getLabel2();
            if (!TextUtils.equals(vh.mFocusableView.getContentDescription(), contentDescription)) {
                vh.mFocusableView.setContentDescription(contentDescription);
                vh.mFocusableView.sendAccessibilityEvent(32768);
            }
        }

        public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
            ActionViewHolder vh = (ActionViewHolder) viewHolder;
            vh.mIcon.setImageDrawable(null);
            if (vh.mLabel != null) {
                vh.mLabel.setText((CharSequence) null);
            }
            vh.mFocusableView.setContentDescription(null);
        }

        public void setOnClickListener(Presenter.ViewHolder viewHolder, View.OnClickListener listener) {
            ((ActionViewHolder) viewHolder).mFocusableView.setOnClickListener(listener);
        }
    }
}
