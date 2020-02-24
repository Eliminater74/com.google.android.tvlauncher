package com.google.android.exoplayer2.p008ui.spherical;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.support.annotation.Nullable;
import android.view.Surface;
import com.google.android.exoplayer2.util.GlUtil;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.google.android.exoplayer2.ui.spherical.CanvasRenderer */
public final class CanvasRenderer {
    private static final int COORDS_PER_VERTEX = 5;
    private static final float DISTANCE_UNIT = 1.0f;
    private static final String[] FRAGMENT_SHADER_CODE = {"#extension GL_OES_EGL_image_external : require", "precision mediump float;", "uniform samplerExternalOES uTexture;", "varying vec2 vTexCoords;", "void main() {", "  gl_FragColor = texture2D(uTexture, vTexCoords);", "}"};
    private static final float HALF_PI = 1.5707964f;
    private static final int POSITION_COORDS_PER_VERTEX = 3;
    private static final int TEXTURE_COORDS_PER_VERTEX = 2;
    private static final int VERTEX_COUNT = 4;
    private static final String[] VERTEX_SHADER_CODE = {"uniform mat4 uMvpMatrix;", "attribute vec3 aPosition;", "attribute vec2 aTexCoords;", "varying vec2 vTexCoords;", "void main() {", "  gl_Position = uMvpMatrix * vec4(aPosition, 1);", "  vTexCoords = aTexCoords;", "}"};
    private static final int VERTEX_STRIDE_BYTES = 20;
    private static final float WIDTH_UNIT = 0.8f;
    private static final float X_UNIT = -0.4f;
    private static final float Y_UNIT = -0.3f;
    private Surface displaySurface;
    private SurfaceTexture displaySurfaceTexture;
    private int height;
    private float heightUnit;
    private int mvpMatrixHandle;
    private int positionHandle;
    private int program = 0;
    private final AtomicBoolean surfaceDirty = new AtomicBoolean();
    private int textureCoordsHandle;
    private int textureHandle;
    private int textureId;
    private final FloatBuffer vertexBuffer = GlUtil.createBuffer(20);
    private int width;

    public void setSize(int width2, int height2) {
        this.width = width2;
        this.height = height2;
        this.heightUnit = (((float) height2) * WIDTH_UNIT) / ((float) width2);
        float[] vertexData = new float[20];
        int vertexDataIndex = 0;
        for (int y = 0; y < 2; y++) {
            int x = 0;
            while (x < 2) {
                int vertexDataIndex2 = vertexDataIndex + 1;
                vertexData[vertexDataIndex] = (((float) x) * WIDTH_UNIT) + X_UNIT;
                int vertexDataIndex3 = vertexDataIndex2 + 1;
                vertexData[vertexDataIndex2] = (this.heightUnit * ((float) y)) + Y_UNIT;
                int vertexDataIndex4 = vertexDataIndex3 + 1;
                vertexData[vertexDataIndex3] = -1.0f;
                int vertexDataIndex5 = vertexDataIndex4 + 1;
                vertexData[vertexDataIndex4] = (float) x;
                vertexData[vertexDataIndex5] = (float) (1 - y);
                x++;
                vertexDataIndex = vertexDataIndex5 + 1;
            }
        }
        this.vertexBuffer.position(0);
        this.vertexBuffer.put(vertexData);
    }

    @Nullable
    public Canvas lockCanvas() {
        Surface surface = this.displaySurface;
        if (surface == null) {
            return null;
        }
        return surface.lockCanvas(null);
    }

    public void unlockCanvasAndPost(@Nullable Canvas canvas) {
        Surface surface;
        if (canvas != null && (surface = this.displaySurface) != null) {
            surface.unlockCanvasAndPost(canvas);
        }
    }

    public void init() {
        if (this.program == 0) {
            this.program = GlUtil.compileProgram(VERTEX_SHADER_CODE, FRAGMENT_SHADER_CODE);
            this.mvpMatrixHandle = GLES20.glGetUniformLocation(this.program, "uMvpMatrix");
            this.positionHandle = GLES20.glGetAttribLocation(this.program, "aPosition");
            this.textureCoordsHandle = GLES20.glGetAttribLocation(this.program, "aTexCoords");
            this.textureHandle = GLES20.glGetUniformLocation(this.program, "uTexture");
            this.textureId = GlUtil.createExternalTexture();
            GlUtil.checkGlError();
            this.displaySurfaceTexture = new SurfaceTexture(this.textureId);
            this.displaySurfaceTexture.setOnFrameAvailableListener(new CanvasRenderer$$Lambda$0(this));
            this.displaySurfaceTexture.setDefaultBufferSize(this.width, this.height);
            this.displaySurface = new Surface(this.displaySurfaceTexture);
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$init$0$CanvasRenderer(SurfaceTexture surfaceTexture) {
        this.surfaceDirty.set(true);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.opengl.GLES20.glVertexAttribPointer(int, int, int, boolean, int, java.nio.Buffer):void}
     arg types: [int, int, int, int, int, java.nio.FloatBuffer]
     candidates:
      ClspMth{android.opengl.GLES20.glVertexAttribPointer(int, int, int, boolean, int, int):void}
      ClspMth{android.opengl.GLES20.glVertexAttribPointer(int, int, int, boolean, int, java.nio.Buffer):void} */
    public void draw(float[] viewProjectionMatrix) {
        if (this.displaySurfaceTexture != null) {
            GLES20.glUseProgram(this.program);
            GlUtil.checkGlError();
            GLES20.glEnableVertexAttribArray(this.positionHandle);
            GLES20.glEnableVertexAttribArray(this.textureCoordsHandle);
            GlUtil.checkGlError();
            GLES20.glUniformMatrix4fv(this.mvpMatrixHandle, 1, false, viewProjectionMatrix, 0);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, this.textureId);
            GLES20.glUniform1i(this.textureHandle, 0);
            GlUtil.checkGlError();
            this.vertexBuffer.position(0);
            GLES20.glVertexAttribPointer(this.positionHandle, 3, 5126, false, 20, (Buffer) this.vertexBuffer);
            GlUtil.checkGlError();
            this.vertexBuffer.position(3);
            GLES20.glVertexAttribPointer(this.textureCoordsHandle, 2, 5126, false, 20, (Buffer) this.vertexBuffer);
            GlUtil.checkGlError();
            if (this.surfaceDirty.compareAndSet(true, false)) {
                this.displaySurfaceTexture.updateTexImage();
            }
            GLES20.glDrawArrays(5, 0, 4);
            GlUtil.checkGlError();
            GLES20.glDisableVertexAttribArray(this.positionHandle);
            GLES20.glDisableVertexAttribArray(this.textureCoordsHandle);
        }
    }

    public void shutdown() {
        int i = this.program;
        if (i != 0) {
            GLES20.glDeleteProgram(i);
            GLES20.glDeleteTextures(1, new int[]{this.textureId}, 0);
        }
        SurfaceTexture surfaceTexture = this.displaySurfaceTexture;
        if (surfaceTexture != null) {
            surfaceTexture.release();
        }
        Surface surface = this.displaySurface;
        if (surface != null) {
            surface.release();
        }
    }

    @Nullable
    public PointF translateClick(float yaw, float pitch) {
        return internalTranslateClick(yaw, pitch, X_UNIT, Y_UNIT, WIDTH_UNIT, this.heightUnit, this.width, this.height);
    }

    @Nullable
    static PointF internalTranslateClick(float yaw, float pitch, float xUnit, float yUnit, float widthUnit, float heightUnit2, int widthPixel, int heightPixel) {
        float f = yaw;
        float f2 = pitch;
        float f3 = widthUnit;
        float f4 = heightUnit2;
        int i = widthPixel;
        int i2 = heightPixel;
        if (f < HALF_PI && f > -1.5707964f && f2 < HALF_PI) {
            if (f2 > -1.5707964f) {
                double d = (double) xUnit;
                Double.isNaN(d);
                double clickXUnit = (Math.tan((double) f) * 1.0d) - d;
                double d2 = (double) yUnit;
                Double.isNaN(d2);
                double clickYUnit = (Math.tan((double) f2) * 1.0d) - d2;
                if (clickXUnit >= 0.0d && clickXUnit <= ((double) f3) && clickYUnit >= 0.0d) {
                    if (clickYUnit <= ((double) f4)) {
                        double d3 = (double) i;
                        double d4 = (double) i;
                        Double.isNaN(d4);
                        double d5 = (double) f3;
                        Double.isNaN(d5);
                        Double.isNaN(d3);
                        float clickXPixel = (float) (d3 - ((d4 * clickXUnit) / d5));
                        double d6 = (double) i2;
                        double d7 = (double) i2;
                        Double.isNaN(d7);
                        double d8 = (double) f4;
                        Double.isNaN(d8);
                        Double.isNaN(d6);
                        return new PointF(clickXPixel, (float) (d6 - ((d7 * clickYUnit) / d8)));
                    }
                }
                return null;
            }
        }
        return null;
    }
}
