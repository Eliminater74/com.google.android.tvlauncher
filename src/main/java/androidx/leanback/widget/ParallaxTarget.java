package androidx.leanback.widget;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.Property;
import android.view.animation.LinearInterpolator;

public abstract class ParallaxTarget {
    public void update(float fraction) {
    }

    public boolean isDirectMapping() {
        return false;
    }

    public void directUpdate(Number value) {
    }

    public static final class PropertyValuesHolderTarget extends ParallaxTarget {
        private static final long PSEUDO_DURATION = 1000000;
        private final ObjectAnimator mAnimator;
        private float mFraction;

        public PropertyValuesHolderTarget(Object targetObject, PropertyValuesHolder values) {
            this.mAnimator = ObjectAnimator.ofPropertyValuesHolder(targetObject, values);
            this.mAnimator.setInterpolator(new LinearInterpolator());
            this.mAnimator.setDuration(1000000L);
        }

        public void update(float fraction) {
            this.mFraction = fraction;
            this.mAnimator.setCurrentPlayTime((long) (1000000.0f * fraction));
        }
    }

    public static final class DirectPropertyTarget<T, V extends Number> extends ParallaxTarget {
        Object mObject;
        Property<T, V> mProperty;

        public DirectPropertyTarget(Object targetObject, Property<T, V> property) {
            this.mObject = targetObject;
            this.mProperty = property;
        }

        public boolean isDirectMapping() {
            return true;
        }

        public void directUpdate(Number value) {
            this.mProperty.set(this.mObject, value);
        }
    }
}
