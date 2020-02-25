package android.support.p001v4.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.XmlRes;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v4.content.res.ColorStateListInflaterCompat */
public final class ColorStateListInflaterCompat {
    private ColorStateListInflaterCompat() {
    }

    @Nullable
    public static ColorStateList inflate(@NonNull Resources resources, @XmlRes int resId, @Nullable Resources.Theme theme) {
        try {
            return createFromXml(resources, resources.getXml(resId), theme);
        } catch (Exception e) {
            Log.e("CSLCompat", "Failed to inflate ColorStateList.", e);
            return null;
        }
    }

    @NonNull
    public static ColorStateList createFromXml(@NonNull Resources r, @NonNull XmlPullParser parser, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        int type;
        AttributeSet attrs = Xml.asAttributeSet(parser);
        do {
            int next = parser.next();
            type = next;
            if (next == 2) {
                break;
            }
        } while (type != 1);
        if (type == 2) {
            return createFromXmlInner(r, parser, attrs, theme);
        }
        throw new XmlPullParserException("No start tag found");
    }

    @NonNull
    public static ColorStateList createFromXmlInner(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        String name = parser.getName();
        if (name.equals("selector")) {
            return inflate(r, parser, attrs, theme);
        }
        throw new XmlPullParserException(parser.getPositionDescription() + ": invalid color state list tag " + name);
    }

    /* JADX INFO: Multiple debug info for r1v3 int[]: [D('innerDepth' int), D('colors' int[])] */
    /* JADX INFO: Multiple debug info for r1v7 int[]: [D('stateSpec' int[]), D('innerDepth' int)] */
    /* JADX INFO: Multiple debug info for r2v6 int: [D('stateSpec' int[]), D('color' int)] */
    /* JADX INFO: Multiple debug info for r1v9 int: [D('stateResId' int), D('innerDepth' int)] */
    /* JADX WARN: Type inference failed for: r6v10, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.content.res.ColorStateList inflate(@android.support.annotation.NonNull android.content.res.Resources r21, @android.support.annotation.NonNull org.xmlpull.v1.XmlPullParser r22, @android.support.annotation.NonNull android.util.AttributeSet r23, @android.support.annotation.Nullable android.content.res.Resources.Theme r24) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r0 = r23
            int r1 = r22.getDepth()
            r2 = 1
            int r1 = r1 + r2
            r3 = 20
            int[][] r3 = new int[r3][]
            int r4 = r3.length
            int[] r4 = new int[r4]
            r5 = 0
        L_0x0010:
            int r6 = r22.next()
            r7 = r6
            if (r6 == r2) goto L_0x00df
            int r6 = r22.getDepth()
            r9 = r6
            if (r6 >= r1) goto L_0x002a
            r6 = 3
            if (r7 == r6) goto L_0x0022
            goto L_0x002a
        L_0x0022:
            r10 = r21
            r11 = r24
            r17 = r1
            goto L_0x00e5
        L_0x002a:
            r6 = 2
            if (r7 != r6) goto L_0x00d4
            if (r9 > r1) goto L_0x00d4
            java.lang.String r6 = r22.getName()
            java.lang.String r10 = "item"
            boolean r6 = r6.equals(r10)
            if (r6 != 0) goto L_0x0043
            r10 = r21
            r11 = r24
            r17 = r1
            goto L_0x00da
        L_0x0043:
            int[] r6 = android.support.compat.C0014R.styleable.ColorStateListItem
            r10 = r21
            r11 = r24
            android.content.res.TypedArray r6 = obtainAttributes(r10, r11, r0, r6)
            int r12 = android.support.compat.C0014R.styleable.ColorStateListItem_android_color
            r13 = -65281(0xffffffffffff00ff, float:NaN)
            int r12 = r6.getColor(r12, r13)
            r13 = 1065353216(0x3f800000, float:1.0)
            int r14 = android.support.compat.C0014R.styleable.ColorStateListItem_android_alpha
            boolean r14 = r6.hasValue(r14)
            if (r14 == 0) goto L_0x0067
            int r14 = android.support.compat.C0014R.styleable.ColorStateListItem_android_alpha
            float r13 = r6.getFloat(r14, r13)
            goto L_0x0075
        L_0x0067:
            int r14 = android.support.compat.C0014R.styleable.ColorStateListItem_alpha
            boolean r14 = r6.hasValue(r14)
            if (r14 == 0) goto L_0x0075
            int r14 = android.support.compat.C0014R.styleable.ColorStateListItem_alpha
            float r13 = r6.getFloat(r14, r13)
        L_0x0075:
            r6.recycle()
            r14 = 0
            int r15 = r23.getAttributeCount()
            int[] r2 = new int[r15]
            r16 = 0
            r8 = r14
            r14 = r16
        L_0x0084:
            if (r14 >= r15) goto L_0x00b5
            r17 = r1
            int r1 = r0.getAttributeNameResource(r14)
            r18 = r6
            r6 = 16843173(0x10101a5, float:2.3694738E-38)
            if (r1 == r6) goto L_0x00ae
            r6 = 16843551(0x101031f, float:2.3695797E-38)
            if (r1 == r6) goto L_0x00ae
            int r6 = android.support.compat.C0014R.attr.alpha
            if (r1 == r6) goto L_0x00ae
            int r6 = r8 + 1
            r19 = r6
            r6 = 0
            boolean r20 = r0.getAttributeBooleanValue(r14, r6)
            if (r20 == 0) goto L_0x00a9
            r6 = r1
            goto L_0x00aa
        L_0x00a9:
            int r6 = -r1
        L_0x00aa:
            r2[r8] = r6
            r8 = r19
        L_0x00ae:
            int r14 = r14 + 1
            r1 = r17
            r6 = r18
            goto L_0x0084
        L_0x00b5:
            r17 = r1
            r18 = r6
            int[] r1 = android.util.StateSet.trimStateSet(r2, r8)
            int r2 = modulateColorAlpha(r12, r13)
            int[] r4 = android.support.p001v4.content.res.GrowingArrayUtils.append(r4, r5, r2)
            java.lang.Object[] r6 = android.support.p001v4.content.res.GrowingArrayUtils.append(r3, r5, r1)
            r3 = r6
            int[][] r3 = (int[][]) r3
            int r5 = r5 + 1
            r1 = r17
            r2 = 1
            goto L_0x0010
        L_0x00d4:
            r10 = r21
            r11 = r24
            r17 = r1
        L_0x00da:
            r1 = r17
            r2 = 1
            goto L_0x0010
        L_0x00df:
            r10 = r21
            r11 = r24
            r17 = r1
        L_0x00e5:
            int[] r1 = new int[r5]
            int[][] r2 = new int[r5][]
            r6 = 0
            java.lang.System.arraycopy(r4, r6, r1, r6, r5)
            java.lang.System.arraycopy(r3, r6, r2, r6, r5)
            android.content.res.ColorStateList r6 = new android.content.res.ColorStateList
            r6.<init>(r2, r1)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p001v4.content.res.ColorStateListInflaterCompat.inflate(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):android.content.res.ColorStateList");
    }

    private static TypedArray obtainAttributes(Resources res, Resources.Theme theme, AttributeSet set, int[] attrs) {
        if (theme == null) {
            return res.obtainAttributes(set, attrs);
        }
        return theme.obtainStyledAttributes(set, attrs, 0, 0);
    }

    @ColorInt
    private static int modulateColorAlpha(@ColorInt int color, @FloatRange(from = 0.0d, mo106to = 1.0d) float alphaMod) {
        return (16777215 & color) | (Math.round(((float) Color.alpha(color)) * alphaMod) << 24);
    }
}
