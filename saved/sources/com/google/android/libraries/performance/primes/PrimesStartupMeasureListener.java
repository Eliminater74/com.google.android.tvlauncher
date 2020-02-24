package com.google.android.libraries.performance.primes;

final class PrimesStartupMeasureListener {

    interface OnActivityInit {
        void onActivityInit();
    }

    interface OnDraw {
        void onDraw();
    }

    PrimesStartupMeasureListener() {
    }
}
