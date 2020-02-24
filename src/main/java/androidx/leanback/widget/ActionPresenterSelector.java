package androidx.leanback.widget;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.leanback.C0364R;
import androidx.leanback.widget.Presenter;

class ActionPresenterSelector extends PresenterSelector {
    private final Presenter mOneLineActionPresenter = new OneLineActionPresenter();
    private final Presenter[] mPresenters = {this.mOneLineActionPresenter, this.mTwoLineActionPresenter};
    private final Presenter mTwoLineActionPresenter = new TwoLineActionPresenter();

    ActionPresenterSelector() {
    }

    public Presenter getPresenter(Object item) {
        if (TextUtils.isEmpty(((Action) item).getLabel2())) {
            return this.mOneLineActionPresenter;
        }
        return this.mTwoLineActionPresenter;
    }

    public Presenter[] getPresenters() {
        return this.mPresenters;
    }

    static class ActionViewHolder extends Presenter.ViewHolder {
        Action mAction;
        Button mButton;
        int mLayoutDirection;

        public ActionViewHolder(View view, int layoutDirection) {
            super(view);
            this.mButton = (Button) view.findViewById(C0364R.C0366id.lb_action_button);
            this.mLayoutDirection = layoutDirection;
        }
    }

    static abstract class ActionPresenter extends Presenter {
        ActionPresenter() {
        }

        public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
            Action action = (Action) item;
            ActionViewHolder vh = (ActionViewHolder) viewHolder;
            vh.mAction = action;
            Drawable icon = action.getIcon();
            if (icon != null) {
                vh.view.setPaddingRelative(vh.view.getResources().getDimensionPixelSize(C0364R.dimen.lb_action_with_icon_padding_start), 0, vh.view.getResources().getDimensionPixelSize(C0364R.dimen.lb_action_with_icon_padding_end), 0);
            } else {
                int padding = vh.view.getResources().getDimensionPixelSize(C0364R.dimen.lb_action_padding_horizontal);
                vh.view.setPaddingRelative(padding, 0, padding, 0);
            }
            if (vh.mLayoutDirection == 1) {
                vh.mButton.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, icon, (Drawable) null);
            } else {
                vh.mButton.setCompoundDrawablesWithIntrinsicBounds(icon, (Drawable) null, (Drawable) null, (Drawable) null);
            }
        }

        public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
            ActionViewHolder vh = (ActionViewHolder) viewHolder;
            vh.mButton.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            vh.view.setPadding(0, 0, 0, 0);
            vh.mAction = null;
        }
    }

    static class OneLineActionPresenter extends ActionPresenter {
        OneLineActionPresenter() {
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
         arg types: [int, android.view.ViewGroup, int]
         candidates:
          ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
          ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
        public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
            return new ActionViewHolder(LayoutInflater.from(parent.getContext()).inflate(C0364R.layout.lb_action_1_line, parent, false), parent.getLayoutDirection());
        }

        public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
            super.onBindViewHolder(viewHolder, item);
            ((ActionViewHolder) viewHolder).mButton.setText(((Action) item).getLabel1());
        }
    }

    static class TwoLineActionPresenter extends ActionPresenter {
        TwoLineActionPresenter() {
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
         arg types: [int, android.view.ViewGroup, int]
         candidates:
          ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
          ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
        public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
            return new ActionViewHolder(LayoutInflater.from(parent.getContext()).inflate(C0364R.layout.lb_action_2_lines, parent, false), parent.getLayoutDirection());
        }

        public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
            super.onBindViewHolder(viewHolder, item);
            Action action = (Action) item;
            ActionViewHolder vh = (ActionViewHolder) viewHolder;
            CharSequence line1 = action.getLabel1();
            CharSequence line2 = action.getLabel2();
            if (TextUtils.isEmpty(line1)) {
                vh.mButton.setText(line2);
            } else if (TextUtils.isEmpty(line2)) {
                vh.mButton.setText(line1);
            } else {
                Button button = vh.mButton;
                button.setText(((Object) line1) + "\n" + ((Object) line2));
            }
        }
    }
}
