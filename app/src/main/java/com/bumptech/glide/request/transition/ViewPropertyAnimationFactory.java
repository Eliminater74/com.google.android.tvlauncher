package com.bumptech.glide.request.transition;

import com.bumptech.glide.load.DataSource;

public class ViewPropertyAnimationFactory<R> implements TransitionFactory<R> {
    private final ViewPropertyTransition.Animator animator;
    private ViewPropertyTransition<R> animation;

    public ViewPropertyAnimationFactory(ViewPropertyTransition.Animator animator2) {
        this.animator = animator2;
    }

    public Transition<R> build(DataSource dataSource, boolean isFirstResource) {
        if (dataSource == DataSource.MEMORY_CACHE || !isFirstResource) {
            return NoTransition.get();
        }
        if (this.animation == null) {
            this.animation = new ViewPropertyTransition<>(this.animator);
        }
        return this.animation;
    }
}
