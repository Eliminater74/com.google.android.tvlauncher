package com.google.android.exoplayer2.p008ui.spherical;

import android.opengl.GLES20;
import com.google.android.exoplayer2.util.GlUtil;
import com.google.android.exoplayer2.video.spherical.Projection;
import java.nio.Buffer;
import java.nio.FloatBuffer;

/* renamed from: com.google.android.exoplayer2.ui.spherical.ProjectionRenderer */
final class ProjectionRenderer {
    private static final String[] FRAGMENT_SHADER_CODE = {"#extension GL_OES_EGL_image_external : require", "precision mediump float;", "uniform samplerExternalOES uTexture;", "varying vec2 vTexCoords;", "void main() {", "  gl_FragColor = texture2D(uTexture, vTexCoords);", "}"};
    private static final float[] TEX_MATRIX_BOTTOM = {1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final float[] TEX_MATRIX_LEFT = {0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final float[] TEX_MATRIX_RIGHT = {0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.5f, 1.0f, 1.0f};
    private static final float[] TEX_MATRIX_TOP = {1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, 0.0f, 0.5f, 1.0f};
    private static final float[] TEX_MATRIX_WHOLE = {1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final String[] VERTEX_SHADER_CODE = {"uniform mat4 uMvpMatrix;", "uniform mat3 uTexMatrix;", "attribute vec4 aPosition;", "attribute vec2 aTexCoords;", "varying vec2 vTexCoords;", "void main() {", "  gl_Position = uMvpMatrix * aPosition;", "  vTexCoords = (uTexMatrix * vec3(aTexCoords, 1)).xy;", "}"};
    private MeshData leftMeshData;
    private int mvpMatrixHandle;
    private int positionHandle;
    private int program;
    private MeshData rightMeshData;
    private int stereoMode;
    private int texCoordsHandle;
    private int textureHandle;
    private int uTexMatrixHandle;

    ProjectionRenderer() {
    }

    public static boolean isSupported(Projection projection) {
        Projection.Mesh leftMesh = projection.leftMesh;
        Projection.Mesh rightMesh = projection.rightMesh;
        if (leftMesh.getSubMeshCount() == 1 && leftMesh.getSubMesh(0).textureId == 0 && rightMesh.getSubMeshCount() == 1 && rightMesh.getSubMesh(0).textureId == 0) {
            return true;
        }
        return false;
    }

    public void setProjection(Projection projection) {
        if (isSupported(projection)) {
            this.stereoMode = projection.stereoMode;
            this.leftMeshData = new MeshData(projection.leftMesh.getSubMesh(0));
            this.rightMeshData = projection.singleMesh ? this.leftMeshData : new MeshData(projection.rightMesh.getSubMesh(0));
        }
    }

    /* access modifiers changed from: package-private */
    public void init() {
        this.program = GlUtil.compileProgram(VERTEX_SHADER_CODE, FRAGMENT_SHADER_CODE);
        this.mvpMatrixHandle = GLES20.glGetUniformLocation(this.program, "uMvpMatrix");
        this.uTexMatrixHandle = GLES20.glGetUniformLocation(this.program, "uTexMatrix");
        this.positionHandle = GLES20.glGetAttribLocation(this.program, "aPosition");
        this.texCoordsHandle = GLES20.glGetAttribLocation(this.program, "aTexCoords");
        this.textureHandle = GLES20.glGetUniformLocation(this.program, "uTexture");
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.opengl.GLES20.glVertexAttribPointer(int, int, int, boolean, int, java.nio.Buffer):void}
     arg types: [int, int, int, int, int, java.nio.FloatBuffer]
     candidates:
      ClspMth{android.opengl.GLES20.glVertexAttribPointer(int, int, int, boolean, int, int):void}
      ClspMth{android.opengl.GLES20.glVertexAttribPointer(int, int, int, boolean, int, java.nio.Buffer):void} */
    /* access modifiers changed from: package-private */
    public void draw(int textureId, float[] mvpMatrix, boolean rightEye) {
        float[] texMatrix;
        MeshData meshData = rightEye ? this.rightMeshData : this.leftMeshData;
        if (meshData != null) {
            GLES20.glUseProgram(this.program);
            GlUtil.checkGlError();
            GLES20.glEnableVertexAttribArray(this.positionHandle);
            GLES20.glEnableVertexAttribArray(this.texCoordsHandle);
            GlUtil.checkGlError();
            int i = this.stereoMode;
            if (i == 1) {
                texMatrix = rightEye ? TEX_MATRIX_BOTTOM : TEX_MATRIX_TOP;
            } else if (i == 2) {
                texMatrix = rightEye ? TEX_MATRIX_RIGHT : TEX_MATRIX_LEFT;
            } else {
                texMatrix = TEX_MATRIX_WHOLE;
            }
            GLES20.glUniformMatrix3fv(this.uTexMatrixHandle, 1, false, texMatrix, 0);
            GLES20.glUniformMatrix4fv(this.mvpMatrixHandle, 1, false, mvpMatrix, 0);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, textureId);
            GLES20.glUniform1i(this.textureHandle, 0);
            GlUtil.checkGlError();
            GLES20.glVertexAttribPointer(this.positionHandle, 3, 5126, false, 12, (Buffer) meshData.vertexBuffer);
            GlUtil.checkGlError();
            GLES20.glVertexAttribPointer(this.texCoordsHandle, 2, 5126, false, 8, (Buffer) meshData.textureBuffer);
            GlUtil.checkGlError();
            GLES20.glDrawArrays(meshData.drawMode, 0, meshData.vertexCount);
            GlUtil.checkGlError();
            GLES20.glDisableVertexAttribArray(this.positionHandle);
            GLES20.glDisableVertexAttribArray(this.texCoordsHandle);
        }
    }

    /* access modifiers changed from: package-private */
    public void shutdown() {
        int i = this.program;
        if (i != 0) {
            GLES20.glDeleteProgram(i);
        }
    }

    /* renamed from: com.google.android.exoplayer2.ui.spherical.ProjectionRenderer$MeshData */
    private static class MeshData {
        /* access modifiers changed from: private */
        public final int drawMode;
        /* access modifiers changed from: private */
        public final FloatBuffer textureBuffer;
        /* access modifiers changed from: private */
        public final FloatBuffer vertexBuffer;
        /* access modifiers changed from: private */
        public final int vertexCount;

        public MeshData(Projection.SubMesh subMesh) {
            this.vertexCount = subMesh.getVertexCount();
            this.vertexBuffer = GlUtil.createBuffer(subMesh.vertices);
            this.textureBuffer = GlUtil.createBuffer(subMesh.textureCoords);
            int i = subMesh.mode;
            if (i == 1) {
                this.drawMode = 5;
            } else if (i != 2) {
                this.drawMode = 4;
            } else {
                this.drawMode = 6;
            }
        }
    }
}
