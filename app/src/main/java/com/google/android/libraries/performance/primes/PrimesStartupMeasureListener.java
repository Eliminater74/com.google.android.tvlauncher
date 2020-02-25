package com.google.android.libraries.performance.primes;

final class PrimesStartupMeasureListener {

    PrimesStartupMeasureListener() {
    }

    interface OnActivityInit {
        void onActivityInit();
    }

    interface OnDraw {
        void onDraw();
    }
}
