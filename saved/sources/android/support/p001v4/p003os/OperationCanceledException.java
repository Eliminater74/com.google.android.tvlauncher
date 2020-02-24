package android.support.p001v4.p003os;

import android.support.annotation.Nullable;

/* renamed from: android.support.v4.os.OperationCanceledException */
public class OperationCanceledException extends RuntimeException {
    public OperationCanceledException() {
        this(null);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public OperationCanceledException(@Nullable String message) {
        super(message != null ? message : "The operation has been canceled.");
    }
}
