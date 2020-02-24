package androidx.leanback.widget;

import android.graphics.drawable.Drawable;

public interface MultiActionsProvider {
    MultiAction[] getActions();

    public static class MultiAction {
        private Drawable[] mDrawables;
        private long mId;
        private int mIndex = 0;

        public MultiAction(long id) {
            this.mId = id;
        }

        public void setDrawables(Drawable[] drawables) {
            this.mDrawables = drawables;
            if (this.mIndex > drawables.length - 1) {
                this.mIndex = drawables.length - 1;
            }
        }

        public Drawable[] getDrawables() {
            return this.mDrawables;
        }

        public void incrementIndex() {
            int i = this.mIndex;
            setIndex(i < this.mDrawables.length + -1 ? i + 1 : 0);
        }

        public void setIndex(int index) {
            this.mIndex = index;
        }

        public int getIndex() {
            return this.mIndex;
        }

        public Drawable getCurrentDrawable() {
            return this.mDrawables[this.mIndex];
        }

        public long getId() {
            return this.mId;
        }
    }
}
