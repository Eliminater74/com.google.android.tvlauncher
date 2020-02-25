package dagger.internal;

import dagger.MembersInjector;

public final class MembersInjectors {
    private MembersInjectors() {
    }

    public static <T> MembersInjector<T> noOp() {
        return NoOpMembersInjector.INSTANCE;
    }

    private enum NoOpMembersInjector implements MembersInjector<Object> {
        INSTANCE;

        public void injectMembers(Object instance) {
            Preconditions.checkNotNull(instance, "Cannot inject members into a null reference");
        }
    }
}
