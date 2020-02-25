package com.google.android.exoplayer2.util;

import android.opengl.GLES20;
import android.opengl.GLU;
import android.text.TextUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class GlUtil {
    private static final String TAG = "GlUtil";

    private GlUtil() {
    }

    public static void checkGlError() {
        int lastError = 0;
        while (true) {
            int glGetError = GLES20.glGetError();
            int error = glGetError;
            if (glGetError != 0) {
                String valueOf = String.valueOf(GLU.gluErrorString(lastError));
                Log.m26e(TAG, valueOf.length() != 0 ? "glError ".concat(valueOf) : new String("glError "));
                lastError = error;
            } else {
                return;
            }
        }
    }

    public static int compileProgram(String[] vertexCode, String[] fragmentCode) {
        return compileProgram(TextUtils.join("\n", vertexCode), TextUtils.join("\n", fragmentCode));
    }

    public static int compileProgram(String vertexCode, String fragmentCode) {
        int program = GLES20.glCreateProgram();
        checkGlError();
        addShader(35633, vertexCode, program);
        addShader(35632, fragmentCode, program);
        GLES20.glLinkProgram(program);
        int[] linkStatus = {0};
        GLES20.glGetProgramiv(program, 35714, linkStatus, 0);
        if (linkStatus[0] != 1) {
            String valueOf = String.valueOf(GLES20.glGetProgramInfoLog(program));
            throwGlError(valueOf.length() != 0 ? "Unable to link shader program: \n".concat(valueOf) : new String("Unable to link shader program: \n"));
        }
        checkGlError();
        return program;
    }

    public static FloatBuffer createBuffer(float[] data) {
        return (FloatBuffer) createBuffer(data.length).put(data).flip();
    }

    public static FloatBuffer createBuffer(int capacity) {
        return ByteBuffer.allocateDirect(capacity * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    public static int createExternalTexture() {
        int[] texId = new int[1];
        GLES20.glGenTextures(1, IntBuffer.wrap(texId));
        GLES20.glBindTexture(36197, texId[0]);
        GLES20.glTexParameteri(36197, 10241, 9729);
        GLES20.glTexParameteri(36197, 10240, 9729);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        checkGlError();
        return texId[0];
    }

    private static void addShader(int type, String source, int program) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, source);
        GLES20.glCompileShader(shader);
        int[] result = {0};
        GLES20.glGetShaderiv(shader, 35713, result, 0);
        if (result[0] != 1) {
            String glGetShaderInfoLog = GLES20.glGetShaderInfoLog(shader);
            StringBuilder sb = new StringBuilder(String.valueOf(glGetShaderInfoLog).length() + 10 + String.valueOf(source).length());
            sb.append(glGetShaderInfoLog);
            sb.append(", source: ");
            sb.append(source);
            throwGlError(sb.toString());
        }
        GLES20.glAttachShader(program, shader);
        GLES20.glDeleteShader(shader);
        checkGlError();
    }

    private static void throwGlError(String errorMsg) {
        Log.m26e(TAG, errorMsg);
    }
}
