package androidx.leanback.widget;

public interface BaseOnItemViewClickedListener<T> {
    void onItemClicked(Presenter.ViewHolder viewHolder, Object obj, RowPresenter.ViewHolder viewHolder2, T t);
}
