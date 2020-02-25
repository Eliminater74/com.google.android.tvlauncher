package com.google.android.exoplayer2.util;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Handler;
import android.support.annotation.Nullable;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@TargetApi(17)
public final class EGLSurfaceTexture implements SurfaceTexture.OnFrameAvailableListener, Runnable {
    public static final int SECURE_MODE_NONE = 0;
    public static final int SECURE_MODE_PROTECTED_PBUFFER = 2;
    public static final int SECURE_MODE_SURFACELESS_CONTEXT = 1;
    private static final int[] EGL_CONFIG_ATTRIBUTES = {12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12327, 12344, 12339, 4, 12344};
    private static final int EGL_PROTECTED_CONTENT_EXT = 12992;
    private static final int EGL_SURFACE_HEIGHT = 1;
    private static final int EGL_SURFACE_WIDTH = 1;
    @Nullable
    private final TextureImageListener callback;
    private final Handler handler;
    private final int[] textureIdHolder;
    @Nullable
    private EGLContext context;
    @Nullable
    private EGLDisplay display;
    @Nullable
    private EGLSurface surface;
    @Nullable
    private SurfaceTexture texture;

    public EGLSurfaceTexture(Handler handler2) {
        this(handler2, null);
    }

    public EGLSurfaceTexture(Handler handler2, @Nullable TextureImageListener callback2) {
        this.handler = handler2;
        this.callback = callback2;
        this.textureIdHolder = new int[1];
    }

    private static EGLDisplay getDefaultDisplay() {
        EGLDisplay display2 = EGL14.eglGetDisplay(0);
        if (display2 != null) {
            int[] version = new int[2];
            if (EGL14.eglInitialize(display2, version, 0, version, 1)) {
                return display2;
            }
            throw new GlException("eglInitialize failed");
        }
        throw new GlException("eglGetDisplay failed");
    }

    private static EGLConfig chooseEGLConfig(EGLDisplay display2) {
        EGLConfig[] configs = new EGLConfig[1];
        int[] numConfigs = new int[1];
        boolean success = EGL14.eglChooseConfig(display2, EGL_CONFIG_ATTRIBUTES, 0, configs, 0, 1, numConfigs, 0);
        if (success && numConfigs[0] > 0 && configs[0] != null) {
            return configs[0];
        }
        throw new GlException(Util.formatInvariant("eglChooseConfig failed: success=%b, numConfigs[0]=%d, configs[0]=%s", Boolean.valueOf(success), Integer.valueOf(numConfigs[0]), configs[0]));
    }

    private static EGLContext createEGLContext(EGLDisplay display2, EGLConfig config, int secureMode) {
        int[] glAttributes;
        if (secureMode == 0) {
            glAttributes = new int[]{12440, 2, 12344};
        } else {
            glAttributes = new int[]{12440, 2, EGL_PROTECTED_CONTENT_EXT, 1, 12344};
        }
        EGLContext context2 = EGL14.eglCreateContext(display2, config, EGL14.EGL_NO_CONTEXT, glAttributes, 0);
        if (context2 != null) {
            return context2;
        }
        throw new GlException("eglCreateContext failed");
    }

    private static EGLSurface createEGLSurface(EGLDisplay display2, EGLConfig config, EGLContext context2, int secureMode) {
        EGLSurface surface2;
        int[] pbufferAttributes;
        if (secureMode == 1) {
            surface2 = EGL14.EGL_NO_SURFACE;
        } else {
            if (secureMode == 2) {
                pbufferAttributes = new int[]{12375, 1, 12374, 1, EGL_PROTECTED_CONTENT_EXT, 1, 12344};
            } else {
                pbufferAttributes = new int[]{12375, 1, 12374, 1, 12344};
            }
            EGLSurface surface3 = EGL14.eglCreatePbufferSurface(display2, config, pbufferAttributes, 0);
            if (surface3 != null) {
                surface2 = surface3;
            } else {
                throw new GlException("eglCreatePbufferSurface failed");
            }
        }
        if (EGL14.eglMakeCurrent(display2, surface2, surface2, context2)) {
            return surface2;
        }
        throw new GlException("eglMakeCurrent failed");
    }

    private static void generateTextureIds(int[] textureIdHolder2) {
        GLES20.glGenTextures(1, textureIdHolder2, 0);
        GlUtil.checkGlError();
    }

    public void init(int secureMode) {
        this.display = getDefaultDisplay();
        EGLConfig config = chooseEGLConfig(this.display);
        this.context = createEGLContext(this.display, config, secureMode);
        this.surface = createEGLSurface(this.display, config, this.context, secureMode);
        generateTextureIds(this.textureIdHolder);
        this.texture = new SurfaceTexture(this.textureIdHolder[0]);
        this.texture.setOnFrameAvailableListener(this);
    }

    public void release() {
        this.handler.removeCallbacks(this);
        try {
            if (this.texture != null) {
                this.texture.release();
                GLES20.glDeleteTextures(1, this.textureIdHolder, 0);
            }
        } finally {
            EGLDisplay eGLDisplay = this.display;
            if (eGLDisplay != null && !eGLDisplay.equals(EGL14.EGL_NO_DISPLAY)) {
                EGL14.eglMakeCurrent(this.display, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            }
            EGLSurface eGLSurface = this.surface;
            if (eGLSurface != null && !eGLSurface.equals(EGL14.EGL_NO_SURFACE)) {
                EGL14.eglDestroySurface(this.display, this.surface);
            }
            EGLContext eGLContext = this.context;
            if (eGLContext != null) {
                EGL14.eglDestroyContext(this.display, eGLContext);
            }
            if (Util.SDK_INT >= 19) {
                EGL14.eglReleaseThread();
            }
            EGLDisplay eGLDisplay2 = this.display;
            if (eGLDisplay2 != null && !eGLDisplay2.equals(EGL14.EGL_NO_DISPLAY)) {
                EGL14.eglTerminate(this.display);
            }
            this.display = null;
            this.context = null;
            this.surface = null;
            this.texture = null;
        }
    }

    public SurfaceTexture getSurfaceTexture() {
        return (SurfaceTexture) Assertions.checkNotNull(this.texture);
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.handler.post(this);
    }

    public void run() {
        dispatchOnFrameAvailable();
        SurfaceTexture surfaceTexture = this.texture;
        if (surfaceTexture != null) {
            try {
                surfaceTexture.updateTexImage();
            } catch (RuntimeException e) {
            }
        }
    }

    private void dispatchOnFrameAvailable() {
        TextureImageListener textureImageListener = this.callback;
        if (textureImageListener != null) {
            textureImageListener.onFrameAvailable();
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface SecureMode {
    }

    public interface TextureImageListener {
        void onFrameAvailable();
    }

    public static final class GlException extends RuntimeException {
        private GlException(String msg) {
            super(msg);
        }
    }
}
