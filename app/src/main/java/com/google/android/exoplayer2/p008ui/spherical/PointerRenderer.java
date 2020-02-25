package com.google.android.exoplayer2.p008ui.spherical;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.google.android.exoplayer2.util.GlUtil;

import java.nio.Buffer;
import java.nio.FloatBuffer;

/* renamed from: com.google.android.exoplayer2.ui.spherical.PointerRenderer */
public final class PointerRenderer {
    private static final int COORDS_PER_VERTEX = 3;
    private static final float DISTANCE = 1.0f;
    private static final String[] FRAGMENT_SHADER_CODE = {"precision mediump float;", "varying vec2 vCoords;", "void main() {", "  float r = length(vCoords);", "  float alpha = smoothstep(0.5, 0.6, r) * (1.0 - smoothstep(0.8, 0.9, r));", "  if (alpha == 0.0) {", "    discard;", "  } else {", "    gl_FragColor = vec4(alpha);", "  }", "}"};
    private static final float SIZE = 0.01f;
    private static final float[] VERTEX_DATA = {-0.01f, -0.01f, -1.0f, SIZE, -0.01f, -1.0f, -0.01f, SIZE, -1.0f, SIZE, SIZE, -1.0f};
    private static final String[] VERTEX_SHADER_CODE = {"uniform mat4 uMvpMatrix;", "attribute vec3 aPosition;", "varying vec2 vCoords;", "void main() {", "  gl_Position = uMvpMatrix * vec4(aPosition, 1);", "  vCoords = aPosition.xy / vec2(0.01, 0.01);", "}"};
    private final float[] controllerOrientationMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];
    private final FloatBuffer vertexBuffer = GlUtil.createBuffer(VERTEX_DATA);
    private int mvpMatrixHandle;
    private int positionHandle;
    private int program = 0;

    public PointerRenderer() {
        Matrix.setIdentityM(this.controllerOrientationMatrix, 0);
    }

    public void init() {
        if (this.program == 0) {
            this.program = GlUtil.compileProgram(VERTEX_SHADER_CODE, FRAGMENT_SHADER_CODE);
            this.mvpMatrixHandle = GLES20.glGetUniformLocation(this.program, "uMvpMatrix");
            this.positionHandle = GLES20.glGetAttribLocation(this.program, "aPosition");
            GlUtil.checkGlError();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.opengl.GLES20.glVertexAttribPointer(int, int, int, boolean, int, java.nio.Buffer):void}
     arg types: [int, int, int, int, int, java.nio.FloatBuffer]
     candidates:
      ClspMth{android.opengl.GLES20.glVertexAttribPointer(int, int, int, boolean, int, int):void}
      ClspMth{android.opengl.GLES20.glVertexAttribPointer(int, int, int, boolean, int, java.nio.Buffer):void} */
    public void draw(float[] viewProjectionMatrix) {
        GLES20.glUseProgram(this.program);
        GlUtil.checkGlError();
        synchronized (this.controllerOrientationMatrix) {
            Matrix.multiplyMM(this.modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, this.controllerOrientationMatrix, 0);
        }
        GLES20.glUniformMatrix4fv(this.mvpMatrixHandle, 1, false, this.modelViewProjectionMatrix, 0);
        GlUtil.checkGlError();
        GLES20.glEnableVertexAttribArray(this.positionHandle);
        GlUtil.checkGlError();
        GLES20.glVertexAttribPointer(this.positionHandle, 3, 5126, false, 0, (Buffer) this.vertexBuffer);
        GlUtil.checkGlError();
        GLES20.glDrawArrays(5, 0, VERTEX_DATA.length / 3);
        GlUtil.checkGlError();
        GLES20.glDisableVertexAttribArray(this.positionHandle);
    }

    public void shutdown() {
        int i = this.program;
        if (i != 0) {
            GLES20.glDeleteProgram(i);
        }
    }

    public void setControllerOrientation(float[] rotationMatrix) {
        synchronized (this.controllerOrientationMatrix) {
            System.arraycopy(rotationMatrix, 0, this.controllerOrientationMatrix, 0, rotationMatrix.length);
        }
    }
}
