package androidx.leanback.graphics;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.p001v4.graphics.drawable.DrawableCompat;
import android.util.Property;

import java.util.ArrayList;

public class CompositeDrawable extends Drawable implements Drawable.Callback {
    boolean mMutated;
    CompositeState mState;

    public CompositeDrawable() {
        this.mMutated = false;
        this.mState = new CompositeState();
    }

    CompositeDrawable(CompositeState state) {
        this.mMutated = false;
        this.mState = state;
    }

    public Drawable.ConstantState getConstantState() {
        return this.mState;
    }

    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = new CompositeState(this.mState, this, null);
            ArrayList<ChildDrawable> children = this.mState.mChildren;
            int n = children.size();
            for (int i = 0; i < n; i++) {
                Drawable dr = children.get(i).mDrawable;
                if (dr != null) {
                    dr.mutate();
                }
            }
            this.mMutated = true;
        }
        return this;
    }

    public void addChildDrawable(Drawable drawable) {
        this.mState.mChildren.add(new ChildDrawable(drawable, this));
    }

    public void setChildDrawableAt(int index, Drawable drawable) {
        this.mState.mChildren.set(index, new ChildDrawable(drawable, this));
    }

    public Drawable getDrawable(int index) {
        return this.mState.mChildren.get(index).mDrawable;
    }

    public ChildDrawable getChildAt(int index) {
        return this.mState.mChildren.get(index);
    }

    public void removeChild(int index) {
        this.mState.mChildren.remove(index);
    }

    public void removeDrawable(Drawable drawable) {
        ArrayList<ChildDrawable> children = this.mState.mChildren;
        for (int i = 0; i < children.size(); i++) {
            if (drawable == children.get(i).mDrawable) {
                children.get(i).mDrawable.setCallback(null);
                children.remove(i);
                return;
            }
        }
    }

    public int getChildCount() {
        return this.mState.mChildren.size();
    }

    public void draw(Canvas canvas) {
        ArrayList<ChildDrawable> children = this.mState.mChildren;
        for (int i = 0; i < children.size(); i++) {
            children.get(i).mDrawable.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        updateBounds(bounds);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        ArrayList<ChildDrawable> children = this.mState.mChildren;
        for (int i = 0; i < children.size(); i++) {
            children.get(i).mDrawable.setColorFilter(colorFilter);
        }
    }

    public int getOpacity() {
        return 0;
    }

    public int getAlpha() {
        Drawable dr = getFirstNonNullDrawable();
        if (dr != null) {
            return DrawableCompat.getAlpha(dr);
        }
        return 255;
    }

    public void setAlpha(int alpha) {
        ArrayList<ChildDrawable> children = this.mState.mChildren;
        for (int i = 0; i < children.size(); i++) {
            children.get(i).mDrawable.setAlpha(alpha);
        }
    }

    /* access modifiers changed from: package-private */
    public final Drawable getFirstNonNullDrawable() {
        ArrayList<ChildDrawable> children = this.mState.mChildren;
        int n = children.size();
        for (int i = 0; i < n; i++) {
            Drawable dr = children.get(i).mDrawable;
            if (dr != null) {
                return dr;
            }
        }
        return null;
    }

    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        scheduleSelf(what, when);
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        unscheduleSelf(what);
    }

    /* access modifiers changed from: package-private */
    public void updateBounds(Rect bounds) {
        ArrayList<ChildDrawable> children = this.mState.mChildren;
        for (int i = 0; i < children.size(); i++) {
            children.get(i).updateBounds(bounds);
        }
    }

    static class CompositeState extends Drawable.ConstantState {
        final ArrayList<ChildDrawable> mChildren;

        CompositeState() {
            this.mChildren = new ArrayList<>();
        }

        CompositeState(CompositeState other, CompositeDrawable parent, Resources res) {
            int n = other.mChildren.size();
            this.mChildren = new ArrayList<>(n);
            for (int k = 0; k < n; k++) {
                this.mChildren.add(new ChildDrawable(other.mChildren.get(k), parent, res));
            }
        }

        @NonNull
        public Drawable newDrawable() {
            return new CompositeDrawable(this);
        }

        public int getChangingConfigurations() {
            return 0;
        }
    }

    public static final class ChildDrawable {
        public static final Property<ChildDrawable, Integer> BOTTOM_ABSOLUTE = new Property<ChildDrawable, Integer>(Integer.class, "absoluteBottom") {
            public void set(ChildDrawable obj, Integer value) {
                if (obj.getBoundsRule().bottom == null) {
                    obj.getBoundsRule().bottom = BoundsRule.ValueRule.absoluteValue(value.intValue());
                } else {
                    obj.getBoundsRule().bottom.setAbsoluteValue(value.intValue());
                }
                obj.recomputeBounds();
            }

            public Integer get(ChildDrawable obj) {
                if (obj.getBoundsRule().bottom == null) {
                    return Integer.valueOf(obj.mParent.getBounds().bottom);
                }
                return Integer.valueOf(obj.getBoundsRule().bottom.getAbsoluteValue());
            }
        };
        public static final Property<ChildDrawable, Float> BOTTOM_FRACTION = new Property<ChildDrawable, Float>(Float.class, "fractionBottom") {
            public void set(ChildDrawable obj, Float value) {
                if (obj.getBoundsRule().bottom == null) {
                    obj.getBoundsRule().bottom = BoundsRule.ValueRule.inheritFromParent(value.floatValue());
                } else {
                    obj.getBoundsRule().bottom.setFraction(value.floatValue());
                }
                obj.recomputeBounds();
            }

            public Float get(ChildDrawable obj) {
                if (obj.getBoundsRule().bottom == null) {
                    return Float.valueOf(1.0f);
                }
                return Float.valueOf(obj.getBoundsRule().bottom.getFraction());
            }
        };
        public static final Property<ChildDrawable, Integer> LEFT_ABSOLUTE = new Property<ChildDrawable, Integer>(Integer.class, "absoluteLeft") {
            public void set(ChildDrawable obj, Integer value) {
                if (obj.getBoundsRule().left == null) {
                    obj.getBoundsRule().left = BoundsRule.ValueRule.absoluteValue(value.intValue());
                } else {
                    obj.getBoundsRule().left.setAbsoluteValue(value.intValue());
                }
                obj.recomputeBounds();
            }

            public Integer get(ChildDrawable obj) {
                if (obj.getBoundsRule().left == null) {
                    return Integer.valueOf(obj.mParent.getBounds().left);
                }
                return Integer.valueOf(obj.getBoundsRule().left.getAbsoluteValue());
            }
        };
        public static final Property<ChildDrawable, Float> LEFT_FRACTION = new Property<ChildDrawable, Float>(Float.class, "fractionLeft") {
            public void set(ChildDrawable obj, Float value) {
                if (obj.getBoundsRule().left == null) {
                    obj.getBoundsRule().left = BoundsRule.ValueRule.inheritFromParent(value.floatValue());
                } else {
                    obj.getBoundsRule().left.setFraction(value.floatValue());
                }
                obj.recomputeBounds();
            }

            public Float get(ChildDrawable obj) {
                if (obj.getBoundsRule().left == null) {
                    return Float.valueOf(0.0f);
                }
                return Float.valueOf(obj.getBoundsRule().left.getFraction());
            }
        };
        public static final Property<ChildDrawable, Integer> RIGHT_ABSOLUTE = new Property<ChildDrawable, Integer>(Integer.class, "absoluteRight") {
            public void set(ChildDrawable obj, Integer value) {
                if (obj.getBoundsRule().right == null) {
                    obj.getBoundsRule().right = BoundsRule.ValueRule.absoluteValue(value.intValue());
                } else {
                    obj.getBoundsRule().right.setAbsoluteValue(value.intValue());
                }
                obj.recomputeBounds();
            }

            public Integer get(ChildDrawable obj) {
                if (obj.getBoundsRule().right == null) {
                    return Integer.valueOf(obj.mParent.getBounds().right);
                }
                return Integer.valueOf(obj.getBoundsRule().right.getAbsoluteValue());
            }
        };
        public static final Property<ChildDrawable, Float> RIGHT_FRACTION = new Property<ChildDrawable, Float>(Float.class, "fractionRight") {
            public void set(ChildDrawable obj, Float value) {
                if (obj.getBoundsRule().right == null) {
                    obj.getBoundsRule().right = BoundsRule.ValueRule.inheritFromParent(value.floatValue());
                } else {
                    obj.getBoundsRule().right.setFraction(value.floatValue());
                }
                obj.recomputeBounds();
            }

            public Float get(ChildDrawable obj) {
                if (obj.getBoundsRule().right == null) {
                    return Float.valueOf(1.0f);
                }
                return Float.valueOf(obj.getBoundsRule().right.getFraction());
            }
        };
        public static final Property<ChildDrawable, Integer> TOP_ABSOLUTE = new Property<ChildDrawable, Integer>(Integer.class, "absoluteTop") {
            public void set(ChildDrawable obj, Integer value) {
                if (obj.getBoundsRule().top == null) {
                    obj.getBoundsRule().top = BoundsRule.ValueRule.absoluteValue(value.intValue());
                } else {
                    obj.getBoundsRule().top.setAbsoluteValue(value.intValue());
                }
                obj.recomputeBounds();
            }

            public Integer get(ChildDrawable obj) {
                if (obj.getBoundsRule().top == null) {
                    return Integer.valueOf(obj.mParent.getBounds().top);
                }
                return Integer.valueOf(obj.getBoundsRule().top.getAbsoluteValue());
            }
        };
        public static final Property<ChildDrawable, Float> TOP_FRACTION = new Property<ChildDrawable, Float>(Float.class, "fractionTop") {
            public void set(ChildDrawable obj, Float value) {
                if (obj.getBoundsRule().top == null) {
                    obj.getBoundsRule().top = BoundsRule.ValueRule.inheritFromParent(value.floatValue());
                } else {
                    obj.getBoundsRule().top.setFraction(value.floatValue());
                }
                obj.recomputeBounds();
            }

            public Float get(ChildDrawable obj) {
                if (obj.getBoundsRule().top == null) {
                    return Float.valueOf(0.0f);
                }
                return Float.valueOf(obj.getBoundsRule().top.getFraction());
            }
        };
        final Drawable mDrawable;
        final CompositeDrawable mParent;
        private final Rect adjustedBounds = new Rect();
        private final BoundsRule mBoundsRule;

        public ChildDrawable(Drawable drawable, CompositeDrawable parent) {
            this.mDrawable = drawable;
            this.mParent = parent;
            this.mBoundsRule = new BoundsRule();
            drawable.setCallback(parent);
        }

        ChildDrawable(ChildDrawable orig, CompositeDrawable parent, Resources res) {
            Drawable clone;
            Drawable dr = orig.mDrawable;
            if (dr != null) {
                Drawable.ConstantState cs = dr.getConstantState();
                if (res != null) {
                    clone = cs.newDrawable(res);
                } else {
                    clone = cs.newDrawable();
                }
                clone.setCallback(parent);
                DrawableCompat.setLayoutDirection(clone, DrawableCompat.getLayoutDirection(dr));
                clone.setBounds(dr.getBounds());
                clone.setLevel(dr.getLevel());
            } else {
                clone = null;
            }
            BoundsRule boundsRule = orig.mBoundsRule;
            if (boundsRule != null) {
                this.mBoundsRule = new BoundsRule(boundsRule);
            } else {
                this.mBoundsRule = new BoundsRule();
            }
            this.mDrawable = clone;
            this.mParent = parent;
        }

        public BoundsRule getBoundsRule() {
            return this.mBoundsRule;
        }

        public Drawable getDrawable() {
            return this.mDrawable;
        }

        /* access modifiers changed from: package-private */
        public void updateBounds(Rect bounds) {
            this.mBoundsRule.calculateBounds(bounds, this.adjustedBounds);
            this.mDrawable.setBounds(this.adjustedBounds);
        }

        public void recomputeBounds() {
            updateBounds(this.mParent.getBounds());
        }
    }
}
