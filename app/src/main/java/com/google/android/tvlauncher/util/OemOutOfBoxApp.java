package com.google.android.tvlauncher.util;

public class OemOutOfBoxApp extends OemAppBase {
    private final boolean mCanHide;
    private final boolean mCanMove;

    private OemOutOfBoxApp(Builder builder) {
        super(builder);
        this.mCanMove = builder.mCanMove;
        this.mCanHide = builder.mCanHide;
    }

    public boolean canMove() {
        return this.mCanMove;
    }

    public boolean canHide() {
        return this.mCanHide;
    }

    public static final class Builder extends OemAppBase.Builder<Builder> {
        boolean mCanHide;
        boolean mCanMove;

        public void setCanMove(boolean canMove) {
            this.mCanMove = canMove;
        }

        public void setCanHide(boolean canHide) {
            this.mCanHide = canHide;
        }

        public OemOutOfBoxApp build() {
            return new OemOutOfBoxApp(this);
        }
    }
}
