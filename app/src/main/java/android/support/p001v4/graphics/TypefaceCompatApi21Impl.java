package android.support.p001v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p001v4.content.res.FontResourcesParserCompat;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v4.graphics.TypefaceCompatApi21Impl */
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String TAG = "TypefaceCompatApi21Impl";
    private static Method sAddFontWeightStyle;
    private static Method sCreateFromFamiliesWithDefault;
    private static Class sFontFamily;
    private static Constructor sFontFamilyCtor;
    private static boolean sHasInitBeenCalled = false;

    TypefaceCompatApi21Impl() {
    }

    private static void init() {
        Method addFontMethod;
        Constructor fontFamilyCtor;
        Class fontFamilyClass;
        Method createFromFamiliesWithDefaultMethod;
        if (!sHasInitBeenCalled) {
            sHasInitBeenCalled = true;
            try {
                fontFamilyClass = Class.forName(FONT_FAMILY_CLASS);
                fontFamilyCtor = fontFamilyClass.getConstructor(new Class[0]);
                addFontMethod = fontFamilyClass.getMethod(ADD_FONT_WEIGHT_STYLE_METHOD, String.class, Integer.TYPE, Boolean.TYPE);
                createFromFamiliesWithDefaultMethod = Typeface.class.getMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance(fontFamilyClass, 1).getClass());
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                Log.e(TAG, e.getClass().getName(), e);
                fontFamilyClass = null;
                fontFamilyCtor = null;
                addFontMethod = null;
                createFromFamiliesWithDefaultMethod = null;
            }
            sFontFamilyCtor = fontFamilyCtor;
            sFontFamily = fontFamilyClass;
            sAddFontWeightStyle = addFontMethod;
            sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
        }
    }

    private File getFile(@NonNull ParcelFileDescriptor fd) {
        try {
            String path = Os.readlink("/proc/self/fd/" + fd.getFd());
            if (OsConstants.S_ISREG(Os.stat(path).st_mode)) {
                return new File(path);
            }
            return null;
        } catch (ErrnoException e) {
            return null;
        }
    }

    private static Object newFamily() {
        init();
        try {
            return sFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Typeface createFromFamiliesWithDefault(Object family) {
        init();
        try {
            Object familyArray = Array.newInstance(sFontFamily, 1);
            Array.set(familyArray, 0, family);
            return (Typeface) sCreateFromFamiliesWithDefault.invoke(null, familyArray);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean addFontWeightStyle(Object family, String name, int weight, boolean style) {
        init();
        try {
            return ((Boolean) sAddFontWeightStyle.invoke(family, name, Integer.valueOf(weight), Boolean.valueOf(style))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x004f, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0054, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r6, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0058, code lost:
        throw r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x005b, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0064, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Typeface createFromFontInfo(android.content.Context r10, android.os.CancellationSignal r11, @android.support.annotation.NonNull android.support.p001v4.provider.FontsContractCompat.FontInfo[] r12, int r13) {
        /*
            r9 = this;
            int r0 = r12.length
            r1 = 0
            r2 = 1
            if (r0 >= r2) goto L_0x0006
            return r1
        L_0x0006:
            android.support.v4.provider.FontsContractCompat$FontInfo r0 = r9.findBestInfo(r12, r13)
            android.content.ContentResolver r2 = r10.getContentResolver()
            android.net.Uri r3 = r0.getUri()     // Catch:{ IOException -> 0x0065 }
            java.lang.String r4 = "r"
            android.os.ParcelFileDescriptor r3 = r2.openFileDescriptor(r3, r4, r11)     // Catch:{ IOException -> 0x0065 }
            if (r3 != 0) goto L_0x0024
            if (r3 == 0) goto L_0x0023
            r3.close()     // Catch:{ IOException -> 0x0065 }
        L_0x0023:
            return r1
        L_0x0024:
            java.io.File r4 = r9.getFile(r3)     // Catch:{ all -> 0x0059 }
            if (r4 == 0) goto L_0x0039
            boolean r5 = r4.canRead()     // Catch:{ all -> 0x0059 }
            if (r5 != 0) goto L_0x0031
            goto L_0x0039
        L_0x0031:
            android.graphics.Typeface r5 = android.graphics.Typeface.createFromFile(r4)     // Catch:{ all -> 0x0059 }
            r3.close()     // Catch:{ IOException -> 0x0065 }
            return r5
        L_0x0039:
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ all -> 0x0059 }
            java.io.FileDescriptor r6 = r3.getFileDescriptor()     // Catch:{ all -> 0x0059 }
            r5.<init>(r6)     // Catch:{ all -> 0x0059 }
            android.graphics.Typeface r6 = super.createFromInputStream(r10, r5)     // Catch:{ all -> 0x004d }
            r5.close()     // Catch:{ all -> 0x0059 }
            r3.close()     // Catch:{ IOException -> 0x0065 }
            return r6
        L_0x004d:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x004f }
        L_0x004f:
            r7 = move-exception
            r5.close()     // Catch:{ all -> 0x0054 }
            goto L_0x0058
        L_0x0054:
            r8 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r6, r8)     // Catch:{ all -> 0x0059 }
        L_0x0058:
            throw r7     // Catch:{ all -> 0x0059 }
        L_0x0059:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x005b }
        L_0x005b:
            r5 = move-exception
            r3.close()     // Catch:{ all -> 0x0060 }
            goto L_0x0064
        L_0x0060:
            r6 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r4, r6)     // Catch:{ IOException -> 0x0065 }
        L_0x0064:
            throw r5     // Catch:{ IOException -> 0x0065 }
        L_0x0065:
            r3 = move-exception
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p001v4.graphics.TypefaceCompatApi21Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, android.support.v4.provider.FontsContractCompat$FontInfo[], int):android.graphics.Typeface");
    }

    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, Resources resources, int style) {
        Object family = newFamily();
        FontResourcesParserCompat.FontFileResourceEntry[] entries = entry.getEntries();
        int length = entries.length;
        int i = 0;
        while (i < length) {
            FontResourcesParserCompat.FontFileResourceEntry e = entries[i];
            File tmpFile = TypefaceCompatUtil.getTempFile(context);
            if (tmpFile == null) {
                return null;
            }
            try {
                if (!TypefaceCompatUtil.copyToFile(tmpFile, resources, e.getResourceId())) {
                    tmpFile.delete();
                    return null;
                } else if (!addFontWeightStyle(family, tmpFile.getPath(), e.getWeight(), e.isItalic())) {
                    return null;
                } else {
                    tmpFile.delete();
                    i++;
                }
            } catch (RuntimeException e2) {
                return null;
            } finally {
                tmpFile.delete();
            }
        }
        return createFromFamiliesWithDefault(family);
    }
}
