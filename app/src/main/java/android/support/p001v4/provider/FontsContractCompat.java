package android.support.p001v4.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import android.support.annotation.GuardedBy;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.content.res.FontResourcesParserCompat;
import android.support.p001v4.graphics.TypefaceCompat;
import android.support.p001v4.graphics.TypefaceCompatUtil;
import android.support.p001v4.media.MediaDescriptionCompat;
import android.support.p001v4.util.LruCache;
import android.support.p001v4.util.Preconditions;
import android.support.p001v4.util.SimpleArrayMap;

import com.google.android.exoplayer2.offline.DownloadService;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: android.support.v4.provider.FontsContractCompat */
public class FontsContractCompat {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String PARCEL_FONT_RESULTS = "font_results";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static final int RESULT_CODE_WRONG_CERTIFICATES = -2;
    static final Object sLock = new Object();
    @GuardedBy("sLock")
    static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>>> sPendingReplies = new SimpleArrayMap<>();
    static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(16);
    private static final int BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000;
    private static final SelfDestructiveThread sBackgroundThread = new SelfDestructiveThread("fonts", 10, 10000);
    private static final Comparator<byte[]> sByteArrayComparator = new Comparator<byte[]>() {
        public int compare(byte[] l, byte[] r) {
            if (l.length != r.length) {
                return l.length - r.length;
            }
            for (int i = 0; i < l.length; i++) {
                if (l[i] != r[i]) {
                    return l[i] - r[i];
                }
            }
            return 0;
        }
    };

    private FontsContractCompat() {
    }

    @NonNull
    static TypefaceResult getFontInternal(Context context, FontRequest request, int style) {
        try {
            FontFamilyResult result = fetchFonts(context, null, request);
            int resultCode = -3;
            if (result.getStatusCode() == 0) {
                Typeface typeface = TypefaceCompat.createFromFontInfo(context, null, result.getFonts(), style);
                if (typeface != null) {
                    resultCode = 0;
                }
                return new TypefaceResult(typeface, resultCode);
            }
            if (result.getStatusCode() == 1) {
                resultCode = -2;
            }
            return new TypefaceResult(null, resultCode);
        } catch (PackageManager.NameNotFoundException e) {
            return new TypefaceResult(null, -1);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static void resetCache() {
        sTypefaceCache.evictAll();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0076, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0088, code lost:
        android.support.p001v4.provider.FontsContractCompat.sBackgroundThread.postAndReply(r2, new android.support.p001v4.provider.FontsContractCompat.C01583());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0092, code lost:
        return null;
     */
    @android.support.annotation.RestrictTo({android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Typeface getFontSync(final android.content.Context r8, final android.support.p001v4.provider.FontRequest r9, @android.support.annotation.Nullable final android.support.p001v4.content.res.ResourcesCompat.FontCallback r10, @android.support.annotation.Nullable final android.os.Handler r11, boolean r12, int r13, final int r14) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r9.getIdentifier()
            r0.append(r1)
            java.lang.String r1 = "-"
            r0.append(r1)
            r0.append(r14)
            java.lang.String r0 = r0.toString()
            android.support.v4.util.LruCache<java.lang.String, android.graphics.Typeface> r1 = android.support.p001v4.provider.FontsContractCompat.sTypefaceCache
            java.lang.Object r1 = r1.get(r0)
            android.graphics.Typeface r1 = (android.graphics.Typeface) r1
            if (r1 == 0) goto L_0x0028
            if (r10 == 0) goto L_0x0027
            r10.onFontRetrieved(r1)
        L_0x0027:
            return r1
        L_0x0028:
            if (r12 == 0) goto L_0x0045
            r2 = -1
            if (r13 != r2) goto L_0x0045
            android.support.v4.provider.FontsContractCompat$TypefaceResult r2 = getFontInternal(r8, r9, r14)
            if (r10 == 0) goto L_0x0042
            int r3 = r2.mResult
            if (r3 != 0) goto L_0x003d
            android.graphics.Typeface r3 = r2.mTypeface
            r10.callbackSuccessAsync(r3, r11)
            goto L_0x0042
        L_0x003d:
            int r3 = r2.mResult
            r10.callbackFailAsync(r3, r11)
        L_0x0042:
            android.graphics.Typeface r3 = r2.mTypeface
            return r3
        L_0x0045:
            android.support.v4.provider.FontsContractCompat$1 r2 = new android.support.v4.provider.FontsContractCompat$1
            r2.<init>(r8, r9, r14, r0)
            r3 = 0
            if (r12 == 0) goto L_0x005a
            android.support.v4.provider.SelfDestructiveThread r4 = android.support.p001v4.provider.FontsContractCompat.sBackgroundThread     // Catch:{ InterruptedException -> 0x0058 }
            java.lang.Object r4 = r4.postAndWait(r2, r13)     // Catch:{ InterruptedException -> 0x0058 }
            android.support.v4.provider.FontsContractCompat$TypefaceResult r4 = (android.support.p001v4.provider.FontsContractCompat.TypefaceResult) r4     // Catch:{ InterruptedException -> 0x0058 }
            android.graphics.Typeface r3 = r4.mTypeface     // Catch:{ InterruptedException -> 0x0058 }
            return r3
        L_0x0058:
            r4 = move-exception
            return r3
        L_0x005a:
            if (r10 != 0) goto L_0x005e
            r4 = r3
            goto L_0x0063
        L_0x005e:
            android.support.v4.provider.FontsContractCompat$2 r4 = new android.support.v4.provider.FontsContractCompat$2
            r4.<init>(r10, r11)
        L_0x0063:
            java.lang.Object r5 = android.support.p001v4.provider.FontsContractCompat.sLock
            monitor-enter(r5)
            android.support.v4.util.SimpleArrayMap<java.lang.String, java.util.ArrayList<android.support.v4.provider.SelfDestructiveThread$ReplyCallback<android.support.v4.provider.FontsContractCompat$TypefaceResult>>> r6 = android.support.p001v4.provider.FontsContractCompat.sPendingReplies     // Catch:{ all -> 0x0093 }
            java.lang.Object r6 = r6.get(r0)     // Catch:{ all -> 0x0093 }
            java.util.ArrayList r6 = (java.util.ArrayList) r6     // Catch:{ all -> 0x0093 }
            if (r6 == 0) goto L_0x0077
            if (r4 == 0) goto L_0x0075
            r6.add(r4)     // Catch:{ all -> 0x0093 }
        L_0x0075:
            monitor-exit(r5)     // Catch:{ all -> 0x0093 }
            return r3
        L_0x0077:
            if (r4 == 0) goto L_0x0087
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ all -> 0x0093 }
            r7.<init>()     // Catch:{ all -> 0x0093 }
            r6 = r7
            r6.add(r4)     // Catch:{ all -> 0x0093 }
            android.support.v4.util.SimpleArrayMap<java.lang.String, java.util.ArrayList<android.support.v4.provider.SelfDestructiveThread$ReplyCallback<android.support.v4.provider.FontsContractCompat$TypefaceResult>>> r7 = android.support.p001v4.provider.FontsContractCompat.sPendingReplies     // Catch:{ all -> 0x0093 }
            r7.put(r0, r6)     // Catch:{ all -> 0x0093 }
        L_0x0087:
            monitor-exit(r5)     // Catch:{ all -> 0x0093 }
            android.support.v4.provider.SelfDestructiveThread r5 = android.support.p001v4.provider.FontsContractCompat.sBackgroundThread
            android.support.v4.provider.FontsContractCompat$3 r6 = new android.support.v4.provider.FontsContractCompat$3
            r6.<init>(r0)
            r5.postAndReply(r2, r6)
            return r3
        L_0x0093:
            r3 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0093 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p001v4.provider.FontsContractCompat.getFontSync(android.content.Context, android.support.v4.provider.FontRequest, android.support.v4.content.res.ResourcesCompat$FontCallback, android.os.Handler, boolean, int, int):android.graphics.Typeface");
    }

    public static void requestFont(@NonNull Context context, @NonNull FontRequest request, @NonNull FontRequestCallback callback, @NonNull Handler handler) {
        requestFontInternal(context.getApplicationContext(), request, callback, handler);
    }

    private static void requestFontInternal(@NonNull final Context appContext, @NonNull final FontRequest request, @NonNull final FontRequestCallback callback, @NonNull Handler handler) {
        final Handler callerThreadHandler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                try {
                    FontFamilyResult result = FontsContractCompat.fetchFonts(appContext, null, request);
                    if (result.getStatusCode() != 0) {
                        int statusCode = result.getStatusCode();
                        if (statusCode == 1) {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRequestFailed(-2);
                                }
                            });
                        } else if (statusCode != 2) {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRequestFailed(-3);
                                }
                            });
                        } else {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRequestFailed(-3);
                                }
                            });
                        }
                    } else {
                        FontInfo[] fonts = result.getFonts();
                        if (fonts == null || fonts.length == 0) {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRequestFailed(1);
                                }
                            });
                            return;
                        }
                        int length = fonts.length;
                        int i = 0;
                        while (i < length) {
                            FontInfo font = fonts[i];
                            if (font.getResultCode() != 0) {
                                final int resultCode = font.getResultCode();
                                if (resultCode < 0) {
                                    callerThreadHandler.post(new Runnable() {
                                        public void run() {
                                            callback.onTypefaceRequestFailed(-3);
                                        }
                                    });
                                    return;
                                } else {
                                    callerThreadHandler.post(new Runnable() {
                                        public void run() {
                                            callback.onTypefaceRequestFailed(resultCode);
                                        }
                                    });
                                    return;
                                }
                            } else {
                                i++;
                            }
                        }
                        final Typeface typeface = FontsContractCompat.buildTypeface(appContext, null, fonts);
                        if (typeface == null) {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRequestFailed(-3);
                                }
                            });
                        } else {
                            callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    callback.onTypefaceRetrieved(typeface);
                                }
                            });
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    callerThreadHandler.post(new Runnable() {
                        public void run() {
                            callback.onTypefaceRequestFailed(-1);
                        }
                    });
                }
            }
        });
    }

    @Nullable
    public static Typeface buildTypeface(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontInfo[] fonts) {
        return TypefaceCompat.createFromFontInfo(context, cancellationSignal, fonts, 0);
    }

    @RequiresApi(19)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] fonts, CancellationSignal cancellationSignal) {
        HashMap<Uri, ByteBuffer> out = new HashMap<>();
        for (FontInfo font : fonts) {
            if (font.getResultCode() == 0) {
                Uri uri = font.getUri();
                if (!out.containsKey(uri)) {
                    out.put(uri, TypefaceCompatUtil.mmap(context, cancellationSignal, uri));
                }
            }
        }
        return Collections.unmodifiableMap(out);
    }

    @NonNull
    public static FontFamilyResult fetchFonts(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontRequest request) throws PackageManager.NameNotFoundException {
        ProviderInfo providerInfo = getProvider(context.getPackageManager(), request, context.getResources());
        if (providerInfo == null) {
            return new FontFamilyResult(1, null);
        }
        return new FontFamilyResult(0, getFontFromProvider(context, request, providerInfo.authority, cancellationSignal));
    }

    @VisibleForTesting
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Nullable
    public static ProviderInfo getProvider(@NonNull PackageManager packageManager, @NonNull FontRequest request, @Nullable Resources resources) throws PackageManager.NameNotFoundException {
        String providerAuthority = request.getProviderAuthority();
        ProviderInfo info = packageManager.resolveContentProvider(providerAuthority, 0);
        if (info == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        } else if (info.packageName.equals(request.getProviderPackage())) {
            List<byte[]> signatures = convertToByteArrayList(packageManager.getPackageInfo(info.packageName, 64).signatures);
            Collections.sort(signatures, sByteArrayComparator);
            List<List<byte[]>> requestCertificatesList = getCertificates(request, resources);
            for (int i = 0; i < requestCertificatesList.size(); i++) {
                List<byte[]> requestSignatures = new ArrayList<>(requestCertificatesList.get(i));
                Collections.sort(requestSignatures, sByteArrayComparator);
                if (equalsByteArrayList(signatures, requestSignatures)) {
                    return info;
                }
            }
            return null;
        } else {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + request.getProviderPackage());
        }
    }

    private static List<List<byte[]>> getCertificates(FontRequest request, Resources resources) {
        if (request.getCertificates() != null) {
            return request.getCertificates();
        }
        return FontResourcesParserCompat.readCerts(resources, request.getCertificatesArrayResId());
    }

    private static boolean equalsByteArrayList(List<byte[]> signatures, List<byte[]> requestSignatures) {
        if (signatures.size() != requestSignatures.size()) {
            return false;
        }
        for (int i = 0; i < signatures.size(); i++) {
            if (!Arrays.equals(signatures.get(i), requestSignatures.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatures) {
        List<byte[]> shas = new ArrayList<>();
        for (Signature byteArray : signatures) {
            shas.add(byteArray.toByteArray());
        }
        return shas;
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x0166  */
    @android.support.annotation.VisibleForTesting
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.support.p001v4.provider.FontsContractCompat.FontInfo[] getFontFromProvider(android.content.Context r24, android.support.p001v4.provider.FontRequest r25, java.lang.String r26, android.os.CancellationSignal r27) {
        /*
            r1 = r26
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r2 = r0
            android.net.Uri$Builder r0 = new android.net.Uri$Builder
            r0.<init>()
            java.lang.String r3 = "content"
            android.net.Uri$Builder r0 = r0.scheme(r3)
            android.net.Uri$Builder r0 = r0.authority(r1)
            android.net.Uri r11 = r0.build()
            android.net.Uri$Builder r0 = new android.net.Uri$Builder
            r0.<init>()
            android.net.Uri$Builder r0 = r0.scheme(r3)
            android.net.Uri$Builder r0 = r0.authority(r1)
            java.lang.String r3 = "file"
            android.net.Uri$Builder r0 = r0.appendPath(r3)
            android.net.Uri r3 = r0.build()
            r12 = 0
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0161 }
            r4 = 16
            java.lang.String r6 = "font_variation_settings"
            r9 = 7
            java.lang.String r13 = "result_code"
            java.lang.String r14 = "font_italic"
            java.lang.String r15 = "font_weight"
            java.lang.String r10 = "font_ttc_index"
            java.lang.String r5 = "file_id"
            java.lang.String r7 = "_id"
            r8 = 0
            if (r0 <= r4) goto L_0x008e
            android.content.ContentResolver r4 = r24.getContentResolver()     // Catch:{ all -> 0x0161 }
            java.lang.String[] r0 = new java.lang.String[r9]     // Catch:{ all -> 0x0161 }
            r0[r8] = r7     // Catch:{ all -> 0x0161 }
            r9 = 1
            r0[r9] = r5     // Catch:{ all -> 0x0161 }
            r9 = 2
            r0[r9] = r10     // Catch:{ all -> 0x0161 }
            r9 = 3
            r0[r9] = r6     // Catch:{ all -> 0x0161 }
            r6 = 4
            r0[r6] = r15     // Catch:{ all -> 0x0161 }
            r6 = 5
            r0[r6] = r14     // Catch:{ all -> 0x0161 }
            r6 = 6
            r0[r6] = r13     // Catch:{ all -> 0x0161 }
            java.lang.String r9 = "query = ?"
            r6 = 1
            java.lang.String[] r8 = new java.lang.String[r6]     // Catch:{ all -> 0x0161 }
            java.lang.String r17 = r25.getQuery()     // Catch:{ all -> 0x0161 }
            r16 = 0
            r8[r16] = r17     // Catch:{ all -> 0x0161 }
            r17 = 0
            r1 = r5
            r5 = r11
            r18 = 1
            r6 = r0
            r0 = r7
            r7 = r9
            r19 = r2
            r2 = 0
            r9 = 1
            r9 = r17
            r21 = r10
            r10 = r27
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x015f }
            r12 = r4
            r2 = r21
            r10 = 1
            goto L_0x00cd
        L_0x008e:
            r19 = r2
            r1 = r5
            r0 = r7
            r21 = r10
            r2 = 0
            android.content.ContentResolver r4 = r24.getContentResolver()     // Catch:{ all -> 0x015f }
            java.lang.String[] r7 = new java.lang.String[r9]     // Catch:{ all -> 0x015f }
            r7[r2] = r0     // Catch:{ all -> 0x015f }
            r10 = 1
            r7[r10] = r1     // Catch:{ all -> 0x015f }
            r9 = r21
            r5 = 2
            r7[r5] = r9     // Catch:{ all -> 0x015f }
            r5 = 3
            r7[r5] = r6     // Catch:{ all -> 0x015f }
            r5 = 4
            r7[r5] = r15     // Catch:{ all -> 0x015f }
            r5 = 5
            r7[r5] = r14     // Catch:{ all -> 0x015f }
            r5 = 6
            r7[r5] = r13     // Catch:{ all -> 0x015f }
            java.lang.String r8 = "query = ?"
            java.lang.String[] r6 = new java.lang.String[r10]     // Catch:{ all -> 0x015f }
            java.lang.String r5 = r25.getQuery()     // Catch:{ all -> 0x015f }
            r6[r2] = r5     // Catch:{ all -> 0x015f }
            r16 = 0
            r5 = r11
            r17 = r6
            r6 = r7
            r7 = r8
            r8 = r17
            r2 = r9
            r9 = r16
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x015f }
            r12 = r4
        L_0x00cd:
            if (r12 == 0) goto L_0x014e
            int r4 = r12.getCount()     // Catch:{ all -> 0x015f }
            if (r4 <= 0) goto L_0x014e
            int r4 = r12.getColumnIndex(r13)     // Catch:{ all -> 0x015f }
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ all -> 0x015f }
            r5.<init>()     // Catch:{ all -> 0x015f }
            int r0 = r12.getColumnIndex(r0)     // Catch:{ all -> 0x014a }
            int r1 = r12.getColumnIndex(r1)     // Catch:{ all -> 0x014a }
            int r2 = r12.getColumnIndex(r2)     // Catch:{ all -> 0x014a }
            int r6 = r12.getColumnIndex(r15)     // Catch:{ all -> 0x014a }
            int r7 = r12.getColumnIndex(r14)     // Catch:{ all -> 0x014a }
        L_0x00f2:
            boolean r8 = r12.moveToNext()     // Catch:{ all -> 0x014a }
            if (r8 == 0) goto L_0x0150
            r8 = -1
            if (r4 == r8) goto L_0x0102
            int r9 = r12.getInt(r4)     // Catch:{ all -> 0x014a }
            r23 = r9
            goto L_0x0104
        L_0x0102:
            r23 = 0
        L_0x0104:
            if (r2 == r8) goto L_0x010d
            int r9 = r12.getInt(r2)     // Catch:{ all -> 0x014a }
            r20 = r9
            goto L_0x010f
        L_0x010d:
            r20 = 0
        L_0x010f:
            if (r1 != r8) goto L_0x011a
            long r13 = r12.getLong(r0)     // Catch:{ all -> 0x014a }
            android.net.Uri r9 = android.content.ContentUris.withAppendedId(r11, r13)     // Catch:{ all -> 0x014a }
            goto L_0x0122
        L_0x011a:
            long r13 = r12.getLong(r1)     // Catch:{ all -> 0x014a }
            android.net.Uri r9 = android.content.ContentUris.withAppendedId(r3, r13)     // Catch:{ all -> 0x014a }
        L_0x0122:
            if (r6 == r8) goto L_0x012b
            int r13 = r12.getInt(r6)     // Catch:{ all -> 0x014a }
            r21 = r13
            goto L_0x012f
        L_0x012b:
            r13 = 400(0x190, float:5.6E-43)
            r21 = 400(0x190, float:5.6E-43)
        L_0x012f:
            if (r7 == r8) goto L_0x013a
            int r8 = r12.getInt(r7)     // Catch:{ all -> 0x014a }
            if (r8 != r10) goto L_0x013a
            r22 = 1
            goto L_0x013c
        L_0x013a:
            r22 = 0
        L_0x013c:
            android.support.v4.provider.FontsContractCompat$FontInfo r8 = new android.support.v4.provider.FontsContractCompat$FontInfo     // Catch:{ all -> 0x014a }
            r18 = r8
            r19 = r9
            r18.<init>(r19, r20, r21, r22, r23)     // Catch:{ all -> 0x014a }
            r5.add(r8)     // Catch:{ all -> 0x014a }
            goto L_0x00f2
        L_0x014a:
            r0 = move-exception
            r19 = r5
            goto L_0x0164
        L_0x014e:
            r5 = r19
        L_0x0150:
            if (r12 == 0) goto L_0x0155
            r12.close()
        L_0x0155:
            r0 = 0
            android.support.v4.provider.FontsContractCompat$FontInfo[] r0 = new android.support.p001v4.provider.FontsContractCompat.FontInfo[r0]
            java.lang.Object[] r0 = r5.toArray(r0)
            android.support.v4.provider.FontsContractCompat$FontInfo[] r0 = (android.support.p001v4.provider.FontsContractCompat.FontInfo[]) r0
            return r0
        L_0x015f:
            r0 = move-exception
            goto L_0x0164
        L_0x0161:
            r0 = move-exception
            r19 = r2
        L_0x0164:
            if (r12 == 0) goto L_0x0169
            r12.close()
        L_0x0169:
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p001v4.provider.FontsContractCompat.getFontFromProvider(android.content.Context, android.support.v4.provider.FontRequest, java.lang.String, android.os.CancellationSignal):android.support.v4.provider.FontsContractCompat$FontInfo[]");
    }

    /* renamed from: android.support.v4.provider.FontsContractCompat$Columns */
    public static final class Columns implements BaseColumns {
        public static final String FILE_ID = "file_id";
        public static final String ITALIC = "font_italic";
        public static final String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final String TTC_INDEX = "font_ttc_index";
        public static final String VARIATION_SETTINGS = "font_variation_settings";
        public static final String WEIGHT = "font_weight";
    }

    /* renamed from: android.support.v4.provider.FontsContractCompat$TypefaceResult */
    private static final class TypefaceResult {
        final int mResult;
        final Typeface mTypeface;

        TypefaceResult(@Nullable Typeface typeface, int result) {
            this.mTypeface = typeface;
            this.mResult = result;
        }
    }

    /* renamed from: android.support.v4.provider.FontsContractCompat$FontInfo */
    public static class FontInfo {
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final Uri mUri;
        private final int mWeight;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public FontInfo(@NonNull Uri uri, @IntRange(from = 0) int ttcIndex, @IntRange(from = 1, mo124to = 1000) int weight, boolean italic, int resultCode) {
            this.mUri = (Uri) Preconditions.checkNotNull(uri);
            this.mTtcIndex = ttcIndex;
            this.mWeight = weight;
            this.mItalic = italic;
            this.mResultCode = resultCode;
        }

        @NonNull
        public Uri getUri() {
            return this.mUri;
        }

        @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        @IntRange(from = 1, mo124to = DownloadService.DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL)
        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }

        public int getResultCode() {
            return this.mResultCode;
        }
    }

    /* renamed from: android.support.v4.provider.FontsContractCompat$FontFamilyResult */
    public static class FontFamilyResult {
        public static final int STATUS_OK = 0;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        private final FontInfo[] mFonts;
        private final int mStatusCode;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public FontFamilyResult(int statusCode, @Nullable FontInfo[] fonts) {
            this.mStatusCode = statusCode;
            this.mFonts = fonts;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public FontInfo[] getFonts() {
            return this.mFonts;
        }
    }

    /* renamed from: android.support.v4.provider.FontsContractCompat$FontRequestCallback */
    public static class FontRequestCallback {
        public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
        public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
        public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
        public static final int FAIL_REASON_MALFORMED_QUERY = 3;
        public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
        public static final int FAIL_REASON_SECURITY_VIOLATION = -4;
        public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public static final int RESULT_OK = 0;

        public void onTypefaceRetrieved(Typeface typeface) {
        }

        public void onTypefaceRequestFailed(int reason) {
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        @Retention(RetentionPolicy.SOURCE)
        /* renamed from: android.support.v4.provider.FontsContractCompat$FontRequestCallback$FontRequestFailReason */
        public @interface FontRequestFailReason {
        }
    }
}
