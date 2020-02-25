package androidx.leanback.widget;

import android.support.p001v4.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

public abstract class Presenter implements FacetProvider {
    private Map<Class, Object> mFacets;

    protected static void cancelAnimationsRecursive(View view) {
        if (view != null && view.hasTransientState()) {
            view.animate().cancel();
            if (view instanceof ViewGroup) {
                int count = ((ViewGroup) view).getChildCount();
                int i = 0;
                while (view.hasTransientState() && i < count) {
                    cancelAnimationsRecursive(((ViewGroup) view).getChildAt(i));
                    i++;
                }
            }
        }
    }

    public abstract void onBindViewHolder(ViewHolder viewHolder, Object obj);

    public abstract ViewHolder onCreateViewHolder(ViewGroup viewGroup);

    public abstract void onUnbindViewHolder(ViewHolder viewHolder);

    public void onBindViewHolder(ViewHolder viewHolder, Object item, List<Object> list) {
        onBindViewHolder(viewHolder, item);
    }

    public void onViewAttachedToWindow(ViewHolder holder) {
    }

    public void onViewDetachedFromWindow(ViewHolder holder) {
        cancelAnimationsRecursive(holder.view);
    }

    public void setOnClickListener(ViewHolder holder, View.OnClickListener listener) {
        holder.view.setOnClickListener(listener);
    }

    public final Object getFacet(Class<?> facetClass) {
        Map<Class, Object> map = this.mFacets;
        if (map == null) {
            return null;
        }
        return map.get(facetClass);
    }

    public final void setFacet(Class<?> facetClass, Object facetImpl) {
        if (this.mFacets == null) {
            this.mFacets = new ArrayMap();
        }
        this.mFacets.put(facetClass, facetImpl);
    }

    public static class ViewHolder implements FacetProvider {
        public final View view;
        private Map<Class, Object> mFacets;

        public ViewHolder(View view2) {
            this.view = view2;
        }

        public final Object getFacet(Class<?> facetClass) {
            Map<Class, Object> map = this.mFacets;
            if (map == null) {
                return null;
            }
            return map.get(facetClass);
        }

        public final void setFacet(Class<?> facetClass, Object facetImpl) {
            if (this.mFacets == null) {
                this.mFacets = new ArrayMap();
            }
            this.mFacets.put(facetClass, facetImpl);
        }
    }

    public static abstract class ViewHolderTask {
        public void run(ViewHolder holder) {
        }
    }
}
