package androidx.leanback.widget;

public interface BaseOnItemViewSelectedListener<T> {
    void onItemSelected(Presenter.ViewHolder viewHolder, Object obj, RowPresenter.ViewHolder viewHolder2, T t);
}
