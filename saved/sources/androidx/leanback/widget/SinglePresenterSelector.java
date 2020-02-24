package androidx.leanback.widget;

public final class SinglePresenterSelector extends PresenterSelector {
    private final Presenter mPresenter;

    public SinglePresenterSelector(Presenter presenter) {
        this.mPresenter = presenter;
    }

    public Presenter getPresenter(Object item) {
        return this.mPresenter;
    }

    public Presenter[] getPresenters() {
        return new Presenter[]{this.mPresenter};
    }
}
