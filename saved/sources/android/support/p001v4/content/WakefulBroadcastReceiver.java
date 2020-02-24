package android.support.p001v4.content;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;

@Deprecated
/* renamed from: android.support.v4.content.WakefulBroadcastReceiver */
public abstract class WakefulBroadcastReceiver extends BroadcastReceiver {
    private static final String EXTRA_WAKE_LOCK_ID = "androidx.contentpager.content.wakelockid";
    private static final String EXTRA_WAKE_LOCK_ID_INTEROP = "android.support.content.wakelockid";
    private static int mNextId = 1;
    private static final SparseArray<PowerManager.WakeLock> sActiveWakeLocks = new SparseArray<>();

    public static ComponentName startWakefulService(Context context, Intent intent) {
        synchronized (sActiveWakeLocks) {
            int id = mNextId;
            mNextId++;
            if (mNextId <= 0) {
                mNextId = 1;
            }
            intent.putExtra(EXTRA_WAKE_LOCK_ID, id);
            intent.putExtra(EXTRA_WAKE_LOCK_ID_INTEROP, id);
            ComponentName comp = context.startService(intent);
            if (comp == null) {
                return null;
            }
            PowerManager.WakeLock wl = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "androidx.core:wake:" + comp.flattenToShortString());
            wl.setReferenceCounted(false);
            wl.acquire(DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS);
            sActiveWakeLocks.put(id, wl);
            return comp;
        }
    }

    public static boolean completeWakefulIntent(Intent intent) {
        int id = intent.getIntExtra(EXTRA_WAKE_LOCK_ID, 0);
        if (id == 0 && (id = intent.getIntExtra(EXTRA_WAKE_LOCK_ID_INTEROP, 0)) == 0) {
            return false;
        }
        synchronized (sActiveWakeLocks) {
            PowerManager.WakeLock wl = sActiveWakeLocks.get(id);
            if (wl != null) {
                wl.release();
                sActiveWakeLocks.remove(id);
                return true;
            }
            Log.w("WakefulBroadcastReceiv.", "No active wake lock id #" + id);
            return true;
        }
    }
}
