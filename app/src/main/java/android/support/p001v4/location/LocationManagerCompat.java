package android.support.p001v4.location;

import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;

/* renamed from: android.support.v4.location.LocationManagerCompat */
public final class LocationManagerCompat {
    private LocationManagerCompat() {
    }

    public static boolean isLocationEnabled(@NonNull LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return locationManager.isLocationEnabled();
        }
        return locationManager.isProviderEnabled("network") || locationManager.isProviderEnabled("gps");
    }
}
