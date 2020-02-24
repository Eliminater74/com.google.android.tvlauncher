package com.google.android.exoplayer2.p008ui.spherical;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.Matrix;
import android.support.annotation.BinderThread;
import android.view.Display;
import com.google.android.exoplayer2.video.spherical.FrameRotationQueue;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

/* renamed from: com.google.android.exoplayer2.ui.spherical.OrientationListener */
final class OrientationListener implements SensorEventListener {
    private final float[] angles = new float[3];
    private final float[] deviceOrientationMatrix4x4 = new float[16];
    private final Display display;
    private final Listener[] listeners;
    private final float[] recenterMatrix4x4 = new float[16];
    private boolean recenterMatrixComputed;
    private final float[] tempMatrix4x4 = new float[16];

    /* renamed from: com.google.android.exoplayer2.ui.spherical.OrientationListener$Listener */
    public interface Listener {
        void onOrientationChange(float[] fArr, float f);
    }

    public OrientationListener(Display display2, Listener... listeners2) {
        this.display = display2;
        this.listeners = listeners2;
    }

    @BinderThread
    public void onSensorChanged(SensorEvent event) {
        SensorManager.getRotationMatrixFromVector(this.deviceOrientationMatrix4x4, event.values);
        rotateAroundZ(this.deviceOrientationMatrix4x4, this.display.getRotation());
        float roll = extractRoll(this.deviceOrientationMatrix4x4);
        rotateYtoSky(this.deviceOrientationMatrix4x4);
        recenter(this.deviceOrientationMatrix4x4);
        notifyListeners(this.deviceOrientationMatrix4x4, roll);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void notifyListeners(float[] deviceOrientationMatrix, float roll) {
        for (Listener listener : this.listeners) {
            listener.onOrientationChange(deviceOrientationMatrix, roll);
        }
    }

    private void recenter(float[] matrix) {
        if (!this.recenterMatrixComputed) {
            FrameRotationQueue.computeRecenterMatrix(this.recenterMatrix4x4, matrix);
            this.recenterMatrixComputed = true;
        }
        float[] fArr = this.tempMatrix4x4;
        System.arraycopy(matrix, 0, fArr, 0, fArr.length);
        Matrix.multiplyMM(matrix, 0, this.tempMatrix4x4, 0, this.recenterMatrix4x4, 0);
    }

    private float extractRoll(float[] matrix) {
        SensorManager.remapCoordinateSystem(matrix, 1, ClientAnalytics.LogRequest.LogSource.DROP_BOX_VALUE, this.tempMatrix4x4);
        SensorManager.getOrientation(this.tempMatrix4x4, this.angles);
        return this.angles[2];
    }

    private void rotateAroundZ(float[] matrix, int rotation) {
        int yAxis;
        int xAxis;
        if (rotation != 0) {
            if (rotation == 1) {
                xAxis = 2;
                yAxis = 129;
            } else if (rotation == 2) {
                xAxis = 129;
                yAxis = 130;
            } else if (rotation == 3) {
                xAxis = 130;
                yAxis = 1;
            } else {
                throw new IllegalStateException();
            }
            float[] fArr = this.tempMatrix4x4;
            System.arraycopy(matrix, 0, fArr, 0, fArr.length);
            SensorManager.remapCoordinateSystem(this.tempMatrix4x4, xAxis, yAxis, matrix);
        }
    }

    private static void rotateYtoSky(float[] matrix) {
        Matrix.rotateM(matrix, 0, 90.0f, 1.0f, 0.0f, 0.0f);
    }
}
