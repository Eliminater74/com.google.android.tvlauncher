package com.google.android.exoplayer2.video.spherical;

import android.opengl.Matrix;

import com.google.android.exoplayer2.util.TimedValueQueue;

public final class FrameRotationQueue {
    private final float[] recenterMatrix = new float[16];
    private final float[] rotationMatrix = new float[16];
    private final TimedValueQueue<float[]> rotations = new TimedValueQueue<>();
    private boolean recenterMatrixComputed;

    public static void computeRecenterMatrix(float[] recenterMatrix2, float[] rotationMatrix2) {
        Matrix.setIdentityM(recenterMatrix2, 0);
        float normRow = (float) Math.sqrt((double) ((rotationMatrix2[10] * rotationMatrix2[10]) + (rotationMatrix2[8] * rotationMatrix2[8])));
        recenterMatrix2[0] = rotationMatrix2[10] / normRow;
        recenterMatrix2[2] = rotationMatrix2[8] / normRow;
        recenterMatrix2[8] = (-rotationMatrix2[8]) / normRow;
        recenterMatrix2[10] = rotationMatrix2[10] / normRow;
    }

    private static void getRotationMatrixFromAngleAxis(float[] matrix, float[] angleAxis) {
        float x = angleAxis[0];
        float y = -angleAxis[1];
        float z = -angleAxis[2];
        float angleRad = Matrix.length(x, y, z);
        if (angleRad != 0.0f) {
            Matrix.setRotateM(matrix, 0, (float) Math.toDegrees((double) angleRad), x / angleRad, y / angleRad, z / angleRad);
            return;
        }
        Matrix.setIdentityM(matrix, 0);
    }

    public void setRotation(long timestampUs, float[] angleAxis) {
        this.rotations.add(timestampUs, angleAxis);
    }

    public void reset() {
        this.rotations.clear();
        this.recenterMatrixComputed = false;
    }

    public boolean pollRotationMatrix(float[] matrix, long timestampUs) {
        float[] rotation = this.rotations.pollFloor(timestampUs);
        if (rotation == null) {
            return false;
        }
        getRotationMatrixFromAngleAxis(this.rotationMatrix, rotation);
        if (!this.recenterMatrixComputed) {
            computeRecenterMatrix(this.recenterMatrix, this.rotationMatrix);
            this.recenterMatrixComputed = true;
        }
        Matrix.multiplyMM(matrix, 0, this.recenterMatrix, 0, this.rotationMatrix, 0);
        return true;
    }
}
