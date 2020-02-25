package android.support.p004v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p004v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.SpinnerAdapter;

/* renamed from: android.support.v7.widget.ThemedSpinnerAdapter */
public interface ThemedSpinnerAdapter extends SpinnerAdapter {
    @Nullable
    Resources.Theme getDropDownViewTheme();

    void setDropDownViewTheme(@Nullable Resources.Theme theme);

    /* renamed from: android.support.v7.widget.ThemedSpinnerAdapter$Helper */
    public static final class Helper {
        private final Context mContext;
        private final LayoutInflater mInflater;
        private LayoutInflater mDropDownInflater;

        public Helper(@NonNull Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
        }

        @Nullable
        public Resources.Theme getDropDownViewTheme() {
            LayoutInflater layoutInflater = this.mDropDownInflater;
            if (layoutInflater == null) {
                return null;
            }
            return layoutInflater.getContext().getTheme();
        }

        public void setDropDownViewTheme(@Nullable Resources.Theme theme) {
            if (theme == null) {
                this.mDropDownInflater = null;
            } else if (theme == this.mContext.getTheme()) {
                this.mDropDownInflater = this.mInflater;
            } else {
                this.mDropDownInflater = LayoutInflater.from(new ContextThemeWrapper(this.mContext, theme));
            }
        }

        @NonNull
        public LayoutInflater getDropDownViewInflater() {
            LayoutInflater layoutInflater = this.mDropDownInflater;
            return layoutInflater != null ? layoutInflater : this.mInflater;
        }
    }
}
