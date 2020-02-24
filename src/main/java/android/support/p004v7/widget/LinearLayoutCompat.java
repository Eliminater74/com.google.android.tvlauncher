package android.support.p004v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.p001v4.view.GravityCompat;
import android.support.p001v4.view.ViewCompat;
import android.support.p004v7.appcompat.C0233R;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: android.support.v7.widget.LinearLayoutCompat */
public class LinearLayoutCompat extends ViewGroup {
    private static final String ACCESSIBILITY_CLASS_NAME = "android.support.v7.widget.LinearLayoutCompat";
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v7.widget.LinearLayoutCompat$DividerMode */
    public @interface DividerMode {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v7.widget.LinearLayoutCompat$OrientationMode */
    public @interface OrientationMode {
    }

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, C0233R.styleable.LinearLayoutCompat, defStyleAttr, 0);
        int index = a.getInt(C0233R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (index >= 0) {
            setOrientation(index);
        }
        int index2 = a.getInt(C0233R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (index2 >= 0) {
            setGravity(index2);
        }
        boolean baselineAligned = a.getBoolean(C0233R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!baselineAligned) {
            setBaselineAligned(baselineAligned);
        }
        this.mWeightSum = a.getFloat(C0233R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = a.getInt(C0233R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = a.getBoolean(C0233R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(a.getDrawable(C0233R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = a.getInt(C0233R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = a.getDimensionPixelSize(C0233R.styleable.LinearLayoutCompat_dividerPadding, 0);
        a.recycle();
    }

    public void setShowDividers(int showDividers) {
        if (showDividers != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = showDividers;
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public void setDividerDrawable(Drawable divider) {
        if (divider != this.mDivider) {
            this.mDivider = divider;
            boolean z = false;
            if (divider != null) {
                this.mDividerWidth = divider.getIntrinsicWidth();
                this.mDividerHeight = divider.getIntrinsicHeight();
            } else {
                this.mDividerWidth = 0;
                this.mDividerHeight = 0;
            }
            if (divider == null) {
                z = true;
            }
            setWillNotDraw(z);
            requestLayout();
        }
    }

    public void setDividerPadding(int padding) {
        this.mDividerPadding = padding;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mDivider != null) {
            if (this.mOrientation == 1) {
                drawDividersVertical(canvas);
            } else {
                drawDividersHorizontal(canvas);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void drawDividersVertical(Canvas canvas) {
        int bottom;
        int count = getVirtualChildCount();
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (!(child == null || child.getVisibility() == 8 || !hasDividerBeforeChildAt(i))) {
                drawHorizontalDivider(canvas, (child.getTop() - ((LayoutParams) child.getLayoutParams()).topMargin) - this.mDividerHeight);
            }
        }
        if (hasDividerBeforeChildAt(count) != 0) {
            View child2 = getVirtualChildAt(count - 1);
            if (child2 == null) {
                bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                bottom = child2.getBottom() + ((LayoutParams) child2.getLayoutParams()).bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    /* access modifiers changed from: package-private */
    public void drawDividersHorizontal(Canvas canvas) {
        int position;
        int position2;
        int count = getVirtualChildCount();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (!(child == null || child.getVisibility() == 8 || !hasDividerBeforeChildAt(i))) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (isLayoutRtl) {
                    position2 = child.getRight() + lp.rightMargin;
                } else {
                    position2 = (child.getLeft() - lp.leftMargin) - this.mDividerWidth;
                }
                drawVerticalDivider(canvas, position2);
            }
        }
        if (hasDividerBeforeChildAt(count) != 0) {
            View child2 = getVirtualChildAt(count - 1);
            if (child2 != null) {
                LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                if (isLayoutRtl) {
                    position = (child2.getLeft() - lp2.leftMargin) - this.mDividerWidth;
                } else {
                    position = child2.getRight() + lp2.rightMargin;
                }
            } else if (isLayoutRtl) {
                position = getPaddingLeft();
            } else {
                position = (getWidth() - getPaddingRight()) - this.mDividerWidth;
            }
            drawVerticalDivider(canvas, position);
        }
    }

    /* access modifiers changed from: package-private */
    public void drawHorizontalDivider(Canvas canvas, int top) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, top, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + top);
        this.mDivider.draw(canvas);
    }

    /* access modifiers changed from: package-private */
    public void drawVerticalDivider(Canvas canvas, int left) {
        this.mDivider.setBounds(left, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + left, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public void setBaselineAligned(boolean baselineAligned) {
        this.mBaselineAligned = baselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    public void setMeasureWithLargestChildEnabled(boolean enabled) {
        this.mUseLargestChild = enabled;
    }

    public int getBaseline() {
        int majorGravity;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i = this.mBaselineAlignedChildIndex;
        if (childCount > i) {
            View child = getChildAt(i);
            int childBaseline = child.getBaseline();
            if (childBaseline != -1) {
                int childTop = this.mBaselineChildTop;
                if (this.mOrientation == 1 && (majorGravity = this.mGravity & 112) != 48) {
                    if (majorGravity == 16) {
                        childTop += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
                    } else if (majorGravity == 80) {
                        childTop = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
                    }
                }
                return ((LayoutParams) child.getLayoutParams()).topMargin + childTop + childBaseline;
            } else if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            } else {
                throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
            }
        } else {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i < 0 || i >= getChildCount()) {
            throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
        }
        this.mBaselineAlignedChildIndex = i;
    }

    /* access modifiers changed from: package-private */
    public View getVirtualChildAt(int index) {
        return getChildAt(index);
    }

    /* access modifiers changed from: package-private */
    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(float, float):float}
     arg types: [int, float]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(long, long):long}
      ClspMth{java.lang.Math.max(float, float):float} */
    public void setWeightSum(float weightSum) {
        this.mWeightSum = Math.max(0.0f, weightSum);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mOrientation == 1) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean hasDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            if ((this.mShowDividers & 1) != 0) {
                return true;
            }
            return false;
        } else if (childIndex == getChildCount()) {
            if ((this.mShowDividers & 4) != 0) {
                return true;
            }
            return false;
        } else if ((this.mShowDividers & 2) == 0) {
            return false;
        } else {
            for (int i = childIndex - 1; i >= 0; i--) {
                if (getChildAt(i).getVisibility() != 8) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX INFO: Multiple debug info for r13v2 int: [D('heightMode' int), D('largestChildHeight' int)] */
    /* JADX INFO: Multiple debug info for r20v1 int: [D('weightedMaxWidth' int), D('count' int)] */
    /* JADX INFO: Multiple debug info for r11v2 int: [D('weightedMaxWidth' int), D('count' int)] */
    /* JADX INFO: Multiple debug info for r10v3 android.view.View: [D('useLargestChild' boolean), D('child' android.view.View)] */
    /* JADX INFO: Multiple debug info for r1v29 android.view.View: [D('heightSize' int), D('child' android.view.View)] */
    /* JADX INFO: Multiple debug info for r0v29 int: [D('alternativeMaxWidth' int), D('matchWidthLocally' boolean)] */
    /* JADX INFO: Multiple debug info for r13v15 'largestChildHeight'  int: [D('heightMode' int), D('largestChildHeight' int)] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x045b  */
    /* JADX WARNING: Removed duplicated region for block: B:188:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void measureVertical(int r39, int r40) {
        /*
            r38 = this;
            r7 = r38
            r8 = r39
            r9 = r40
            r10 = 0
            r7.mTotalLength = r10
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 1
            r5 = 0
            int r11 = r38.getVirtualChildCount()
            int r12 = android.view.View.MeasureSpec.getMode(r39)
            int r13 = android.view.View.MeasureSpec.getMode(r40)
            r6 = 0
            r14 = 0
            int r15 = r7.mBaselineAlignedChildIndex
            boolean r10 = r7.mUseLargestChild
            r17 = 0
            r18 = 0
            r19 = r6
            r6 = r2
            r2 = r0
            r37 = r4
            r4 = r3
            r3 = r17
            r17 = r14
            r14 = r18
            r18 = r37
        L_0x0034:
            r20 = r4
            r0 = 8
            r22 = 1
            r23 = 0
            if (r14 >= r11) goto L_0x01af
            android.view.View r4 = r7.getVirtualChildAt(r14)
            if (r4 != 0) goto L_0x0056
            int r0 = r7.mTotalLength
            int r21 = r7.measureNullChild(r14)
            int r0 = r0 + r21
            r7.mTotalLength = r0
            r21 = r13
            r4 = r20
            r20 = r11
            goto L_0x01a3
        L_0x0056:
            r26 = r1
            int r1 = r4.getVisibility()
            if (r1 != r0) goto L_0x006d
            int r0 = r7.getChildrenSkipCount(r4, r14)
            int r14 = r14 + r0
            r21 = r13
            r4 = r20
            r1 = r26
            r20 = r11
            goto L_0x01a3
        L_0x006d:
            boolean r0 = r7.hasDividerBeforeChildAt(r14)
            if (r0 == 0) goto L_0x007a
            int r0 = r7.mTotalLength
            int r1 = r7.mDividerHeight
            int r0 = r0 + r1
            r7.mTotalLength = r0
        L_0x007a:
            android.view.ViewGroup$LayoutParams r0 = r4.getLayoutParams()
            r1 = r0
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r1 = (android.support.p004v7.widget.LinearLayoutCompat.LayoutParams) r1
            float r0 = r1.weight
            float r27 = r5 + r0
            r5 = 1073741824(0x40000000, float:2.0)
            if (r13 != r5) goto L_0x00b6
            int r0 = r1.height
            if (r0 != 0) goto L_0x00b6
            float r0 = r1.weight
            int r0 = (r0 > r23 ? 1 : (r0 == r23 ? 0 : -1))
            if (r0 <= 0) goto L_0x00b6
            int r0 = r7.mTotalLength
            int r5 = r1.topMargin
            int r5 = r5 + r0
            r28 = r2
            int r2 = r1.bottomMargin
            int r5 = r5 + r2
            int r2 = java.lang.Math.max(r0, r5)
            r7.mTotalLength = r2
            r17 = 1
            r8 = r1
            r33 = r6
            r21 = r13
            r24 = r20
            r9 = r26
            r31 = r28
            r13 = r3
            r3 = r4
            r20 = r11
            goto L_0x0124
        L_0x00b6:
            r28 = r2
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            int r2 = r1.height
            if (r2 != 0) goto L_0x00ca
            float r2 = r1.weight
            int r2 = (r2 > r23 ? 1 : (r2 == r23 ? 0 : -1))
            if (r2 <= 0) goto L_0x00ca
            r0 = 0
            r2 = -2
            r1.height = r2
            r5 = r0
            goto L_0x00cb
        L_0x00ca:
            r5 = r0
        L_0x00cb:
            r29 = 0
            int r0 = (r27 > r23 ? 1 : (r27 == r23 ? 0 : -1))
            if (r0 != 0) goto L_0x00d6
            int r0 = r7.mTotalLength
            r30 = r0
            goto L_0x00d8
        L_0x00d6:
            r30 = 0
        L_0x00d8:
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r0 = r38
            r8 = r1
            r9 = r26
            r1 = r4
            r31 = r28
            r2 = r14
            r21 = r13
            r13 = r3
            r3 = r39
            r26 = r4
            r24 = r20
            r20 = r11
            r11 = 1073741824(0x40000000, float:2.0)
            r4 = r29
            r11 = r5
            r5 = r40
            r33 = r6
            r6 = r30
            r0.measureChildBeforeLayout(r1, r2, r3, r4, r5, r6)
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r11 == r1) goto L_0x0102
            r8.height = r11
        L_0x0102:
            int r0 = r26.getMeasuredHeight()
            int r1 = r7.mTotalLength
            int r2 = r1 + r0
            int r3 = r8.topMargin
            int r2 = r2 + r3
            int r3 = r8.bottomMargin
            int r2 = r2 + r3
            r3 = r26
            int r4 = r7.getNextLocationOffset(r3)
            int r2 = r2 + r4
            int r2 = java.lang.Math.max(r1, r2)
            r7.mTotalLength = r2
            if (r10 == 0) goto L_0x0124
            int r2 = java.lang.Math.max(r0, r13)
            r13 = r2
        L_0x0124:
            if (r15 < 0) goto L_0x012e
            int r0 = r14 + 1
            if (r15 != r0) goto L_0x012e
            int r0 = r7.mTotalLength
            r7.mBaselineChildTop = r0
        L_0x012e:
            if (r14 >= r15) goto L_0x013f
            float r0 = r8.weight
            int r0 = (r0 > r23 ? 1 : (r0 == r23 ? 0 : -1))
            if (r0 > 0) goto L_0x0137
            goto L_0x013f
        L_0x0137:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex."
            r0.<init>(r1)
            throw r0
        L_0x013f:
            r0 = 0
            r1 = 1073741824(0x40000000, float:2.0)
            if (r12 == r1) goto L_0x014c
            int r1 = r8.width
            r2 = -1
            if (r1 != r2) goto L_0x014c
            r19 = 1
            r0 = 1
        L_0x014c:
            int r1 = r8.leftMargin
            int r2 = r8.rightMargin
            int r1 = r1 + r2
            int r2 = r3.getMeasuredWidth()
            int r2 = r2 + r1
            r4 = r31
            int r4 = java.lang.Math.max(r4, r2)
            int r5 = r3.getMeasuredState()
            int r5 = android.view.View.combineMeasuredStates(r9, r5)
            if (r18 == 0) goto L_0x016e
            int r6 = r8.width
            r9 = -1
            if (r6 != r9) goto L_0x016e
            r6 = 1
            goto L_0x016f
        L_0x016e:
            r6 = 0
        L_0x016f:
            float r9 = r8.weight
            int r9 = (r9 > r23 ? 1 : (r9 == r23 ? 0 : -1))
            if (r9 <= 0) goto L_0x0185
            if (r0 == 0) goto L_0x0179
            r9 = r1
            goto L_0x017a
        L_0x0179:
            r9 = r2
        L_0x017a:
            r11 = r24
            int r9 = java.lang.Math.max(r11, r9)
            r24 = r0
            r0 = r33
            goto L_0x0195
        L_0x0185:
            r11 = r24
            if (r0 == 0) goto L_0x018b
            r9 = r1
            goto L_0x018c
        L_0x018b:
            r9 = r2
        L_0x018c:
            r24 = r0
            r0 = r33
            int r0 = java.lang.Math.max(r0, r9)
            r9 = r11
        L_0x0195:
            int r11 = r7.getChildrenSkipCount(r3, r14)
            int r14 = r14 + r11
            r2 = r4
            r1 = r5
            r18 = r6
            r4 = r9
            r3 = r13
            r5 = r27
            r6 = r0
        L_0x01a3:
            int r14 = r14 + 1
            r8 = r39
            r9 = r40
            r11 = r20
            r13 = r21
            goto L_0x0034
        L_0x01af:
            r9 = r1
            r4 = r2
            r0 = r6
            r21 = r13
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = 8
            r13 = r3
            r37 = r20
            r20 = r11
            r11 = r37
            int r3 = r7.mTotalLength
            if (r3 <= 0) goto L_0x01d3
            r3 = r20
            boolean r6 = r7.hasDividerBeforeChildAt(r3)
            if (r6 == 0) goto L_0x01d5
            int r6 = r7.mTotalLength
            int r8 = r7.mDividerHeight
            int r6 = r6 + r8
            r7.mTotalLength = r6
            goto L_0x01d5
        L_0x01d3:
            r3 = r20
        L_0x01d5:
            if (r10 == 0) goto L_0x022d
            r6 = r21
            if (r6 == r1) goto L_0x01dd
            if (r6 != 0) goto L_0x022f
        L_0x01dd:
            r1 = 0
            r7.mTotalLength = r1
            r1 = 0
        L_0x01e1:
            if (r1 >= r3) goto L_0x022a
            android.view.View r8 = r7.getVirtualChildAt(r1)
            if (r8 != 0) goto L_0x01f6
            int r14 = r7.mTotalLength
            int r20 = r7.measureNullChild(r1)
            int r14 = r14 + r20
            r7.mTotalLength = r14
            r24 = r1
            goto L_0x0223
        L_0x01f6:
            int r14 = r8.getVisibility()
            if (r14 != r2) goto L_0x0202
            int r14 = r7.getChildrenSkipCount(r8, r1)
            int r1 = r1 + r14
            goto L_0x0225
        L_0x0202:
            android.view.ViewGroup$LayoutParams r14 = r8.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r14 = (android.support.p004v7.widget.LinearLayoutCompat.LayoutParams) r14
            int r2 = r7.mTotalLength
            int r21 = r2 + r13
            r24 = r1
            int r1 = r14.topMargin
            int r21 = r21 + r1
            int r1 = r14.bottomMargin
            int r21 = r21 + r1
            int r1 = r7.getNextLocationOffset(r8)
            int r1 = r21 + r1
            int r1 = java.lang.Math.max(r2, r1)
            r7.mTotalLength = r1
        L_0x0223:
            r1 = r24
        L_0x0225:
            int r1 = r1 + 1
            r2 = 8
            goto L_0x01e1
        L_0x022a:
            r24 = r1
            goto L_0x022f
        L_0x022d:
            r6 = r21
        L_0x022f:
            int r1 = r7.mTotalLength
            int r2 = r38.getPaddingTop()
            int r8 = r38.getPaddingBottom()
            int r2 = r2 + r8
            int r1 = r1 + r2
            r7.mTotalLength = r1
            int r1 = r7.mTotalLength
            int r2 = r38.getSuggestedMinimumHeight()
            int r1 = java.lang.Math.max(r1, r2)
            r2 = r40
            r8 = 0
            int r14 = android.view.View.resolveSizeAndState(r1, r2, r8)
            r8 = 16777215(0xffffff, float:2.3509886E-38)
            r1 = r14 & r8
            int r8 = r7.mTotalLength
            int r8 = r1 - r8
            if (r17 != 0) goto L_0x02f9
            if (r8 == 0) goto L_0x0269
            int r21 = (r5 > r23 ? 1 : (r5 == r23 ? 0 : -1))
            if (r21 <= 0) goto L_0x0269
            r21 = r1
            r31 = r4
            r24 = r5
            r26 = r8
            goto L_0x0301
        L_0x0269:
            int r0 = java.lang.Math.max(r0, r11)
            if (r10 == 0) goto L_0x02df
            r16 = r0
            r0 = 1073741824(0x40000000, float:2.0)
            if (r6 == r0) goto L_0x02d6
            r0 = 0
        L_0x0276:
            if (r0 >= r3) goto L_0x02cd
            r21 = r1
            android.view.View r1 = r7.getVirtualChildAt(r0)
            if (r1 == 0) goto L_0x02bc
            r31 = r4
            int r4 = r1.getVisibility()
            r24 = r5
            r5 = 8
            if (r4 != r5) goto L_0x028f
            r26 = r8
            goto L_0x02c2
        L_0x028f:
            android.view.ViewGroup$LayoutParams r4 = r1.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r4 = (android.support.p004v7.widget.LinearLayoutCompat.LayoutParams) r4
            float r5 = r4.weight
            int r22 = (r5 > r23 ? 1 : (r5 == r23 ? 0 : -1))
            if (r22 <= 0) goto L_0x02b5
            r22 = r4
            int r4 = r1.getMeasuredWidth()
            r25 = r5
            r5 = 1073741824(0x40000000, float:2.0)
            int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r5)
            r26 = r8
            int r8 = android.view.View.MeasureSpec.makeMeasureSpec(r13, r5)
            r1.measure(r4, r8)
            goto L_0x02c2
        L_0x02b5:
            r22 = r4
            r25 = r5
            r26 = r8
            goto L_0x02c2
        L_0x02bc:
            r31 = r4
            r24 = r5
            r26 = r8
        L_0x02c2:
            int r0 = r0 + 1
            r1 = r21
            r5 = r24
            r8 = r26
            r4 = r31
            goto L_0x0276
        L_0x02cd:
            r21 = r1
            r31 = r4
            r24 = r5
            r26 = r8
            goto L_0x02e9
        L_0x02d6:
            r21 = r1
            r31 = r4
            r24 = r5
            r26 = r8
            goto L_0x02e9
        L_0x02df:
            r16 = r0
            r21 = r1
            r31 = r4
            r24 = r5
            r26 = r8
        L_0x02e9:
            r34 = r6
            r27 = r11
            r29 = r13
            r30 = r15
            r4 = r26
            r13 = r39
            r26 = r10
            goto L_0x0437
        L_0x02f9:
            r21 = r1
            r31 = r4
            r24 = r5
            r26 = r8
        L_0x0301:
            float r5 = r7.mWeightSum
            int r1 = (r5 > r23 ? 1 : (r5 == r23 ? 0 : -1))
            if (r1 <= 0) goto L_0x0308
            goto L_0x030a
        L_0x0308:
            r5 = r24
        L_0x030a:
            r1 = r5
            r4 = 0
            r7.mTotalLength = r4
            r5 = 0
            r8 = r0
            r4 = r26
            r0 = r31
        L_0x0314:
            if (r5 >= r3) goto L_0x0419
            r26 = r10
            android.view.View r10 = r7.getVirtualChildAt(r5)
            r27 = r11
            int r11 = r10.getVisibility()
            r29 = r13
            r13 = 8
            if (r11 != r13) goto L_0x0330
            r13 = r39
            r34 = r6
            r30 = r15
            goto L_0x040b
        L_0x0330:
            android.view.ViewGroup$LayoutParams r11 = r10.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r11 = (android.support.p004v7.widget.LinearLayoutCompat.LayoutParams) r11
            float r13 = r11.weight
            int r30 = (r13 > r23 ? 1 : (r13 == r23 ? 0 : -1))
            if (r30 <= 0) goto L_0x03a7
            r30 = r15
            float r15 = (float) r4
            float r15 = r15 * r13
            float r15 = r15 / r1
            int r15 = (int) r15
            float r1 = r1 - r13
            int r4 = r4 - r15
            int r31 = r38.getPaddingLeft()
            int r32 = r38.getPaddingRight()
            int r31 = r31 + r32
            r32 = r1
            int r1 = r11.leftMargin
            int r31 = r31 + r1
            int r1 = r11.rightMargin
            int r1 = r31 + r1
            r31 = r4
            int r4 = r11.width
            r33 = r13
            r13 = r39
            int r1 = getChildMeasureSpec(r13, r1, r4)
            int r4 = r11.height
            if (r4 != 0) goto L_0x0384
            r4 = 1073741824(0x40000000, float:2.0)
            if (r6 == r4) goto L_0x0371
            r34 = r6
            goto L_0x0386
        L_0x0371:
            if (r15 <= 0) goto L_0x0375
            r4 = r15
            goto L_0x0376
        L_0x0375:
            r4 = 0
        L_0x0376:
            r34 = r6
            r6 = 1073741824(0x40000000, float:2.0)
            int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r6)
            r10.measure(r1, r4)
            r35 = r15
            goto L_0x039b
        L_0x0384:
            r34 = r6
        L_0x0386:
            int r4 = r10.getMeasuredHeight()
            int r4 = r4 + r15
            if (r4 >= 0) goto L_0x038e
            r4 = 0
        L_0x038e:
            r35 = r15
            r6 = 1073741824(0x40000000, float:2.0)
            int r15 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r6)
            r10.measure(r1, r15)
        L_0x039b:
            int r4 = r10.getMeasuredState()
            r4 = r4 & -256(0xffffffffffffff00, float:NaN)
            int r9 = android.view.View.combineMeasuredStates(r9, r4)
            goto L_0x03b3
        L_0x03a7:
            r34 = r6
            r33 = r13
            r30 = r15
            r13 = r39
            r32 = r1
            r31 = r4
        L_0x03b3:
            int r1 = r11.leftMargin
            int r4 = r11.rightMargin
            int r1 = r1 + r4
            int r4 = r10.getMeasuredWidth()
            int r4 = r4 + r1
            int r0 = java.lang.Math.max(r0, r4)
            r6 = 1073741824(0x40000000, float:2.0)
            if (r12 == r6) goto L_0x03cc
            int r6 = r11.width
            r15 = -1
            if (r6 != r15) goto L_0x03cc
            r6 = 1
            goto L_0x03cd
        L_0x03cc:
            r6 = 0
        L_0x03cd:
            if (r6 == 0) goto L_0x03d1
            r15 = r1
            goto L_0x03d2
        L_0x03d1:
            r15 = r4
        L_0x03d2:
            int r8 = java.lang.Math.max(r8, r15)
            if (r18 == 0) goto L_0x03e1
            int r15 = r11.width
            r35 = r0
            r0 = -1
            if (r15 != r0) goto L_0x03e4
            r15 = 1
            goto L_0x03e5
        L_0x03e1:
            r35 = r0
            r0 = -1
        L_0x03e4:
            r15 = 0
        L_0x03e5:
            int r0 = r7.mTotalLength
            int r18 = r10.getMeasuredHeight()
            int r18 = r0 + r18
            r36 = r1
            int r1 = r11.topMargin
            int r18 = r18 + r1
            int r1 = r11.bottomMargin
            int r18 = r18 + r1
            int r1 = r7.getNextLocationOffset(r10)
            int r1 = r18 + r1
            int r1 = java.lang.Math.max(r0, r1)
            r7.mTotalLength = r1
            r18 = r15
            r4 = r31
            r1 = r32
            r0 = r35
        L_0x040b:
            int r5 = r5 + 1
            r10 = r26
            r11 = r27
            r13 = r29
            r15 = r30
            r6 = r34
            goto L_0x0314
        L_0x0419:
            r31 = r0
            r34 = r6
            r26 = r10
            r27 = r11
            r29 = r13
            r30 = r15
            r13 = r39
            int r0 = r7.mTotalLength
            int r5 = r38.getPaddingTop()
            int r6 = r38.getPaddingBottom()
            int r5 = r5 + r6
            int r0 = r0 + r5
            r7.mTotalLength = r0
            r16 = r8
        L_0x0437:
            if (r18 != 0) goto L_0x043f
            r0 = 1073741824(0x40000000, float:2.0)
            if (r12 == r0) goto L_0x043f
            r31 = r16
        L_0x043f:
            int r0 = r38.getPaddingLeft()
            int r1 = r38.getPaddingRight()
            int r0 = r0 + r1
            int r0 = r31 + r0
            int r1 = r38.getSuggestedMinimumWidth()
            int r0 = java.lang.Math.max(r0, r1)
            int r1 = android.view.View.resolveSizeAndState(r0, r13, r9)
            r7.setMeasuredDimension(r1, r14)
            if (r19 == 0) goto L_0x045e
            r7.forceUniformWidth(r3, r2)
        L_0x045e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.widget.LinearLayoutCompat.measureVertical(int, int):void");
    }

    private void forceUniformWidth(int count, int heightMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.width == -1) {
                    int oldHeight = lp.height;
                    lp.height = child.getMeasuredHeight();
                    measureChildWithMargins(child, uniformMeasureSpec, 0, heightMeasureSpec, 0);
                    lp.height = oldHeight;
                }
            }
        }
    }

    /* JADX INFO: Multiple debug info for r14v1 boolean: [D('skippedMeasure' boolean), D('useLargestChild' boolean)] */
    /* JADX INFO: Multiple debug info for r6v4 int: [D('childState' int), D('baselineAligned' boolean)] */
    /* JADX INFO: Multiple debug info for r12v2 int: [D('widthMode' int), D('weightedMaxHeight' int)] */
    /* JADX INFO: Multiple debug info for r2v3 int: [D('maxHeight' int), D('weightedMaxHeight' int)] */
    /* JADX INFO: Multiple debug info for r3v3 int: [D('largestChildWidth' int), D('alternativeMaxHeight' int)] */
    /* JADX INFO: Multiple debug info for r1v3 int: [D('largestChildWidth' int), D('alternativeMaxHeight' int)] */
    /* JADX INFO: Multiple debug info for r12v6 android.view.View: [D('weightedMaxHeight' int), D('child' android.view.View)] */
    /* JADX INFO: Multiple debug info for r0v14 int: [D('weightSum' float), D('alternativeMaxHeight' int)] */
    /* JADX INFO: Multiple debug info for r0v40 android.view.View: [D('totalWeight' float), D('child' android.view.View)] */
    /* JADX INFO: Multiple debug info for r2v29 int: [D('maxHeight' int), D('totalLength' int)] */
    /* JADX INFO: Multiple debug info for r2v42 int: [D('maxHeight' int), D('margin' int)] */
    /* JADX INFO: Multiple debug info for r1v38 int: [D('lp' android.support.v7.widget.LinearLayoutCompat$LayoutParams), D('alternativeMaxHeight' int)] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x053e  */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x0576  */
    /* JADX WARNING: Removed duplicated region for block: B:225:0x0629  */
    /* JADX WARNING: Removed duplicated region for block: B:226:0x0631  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void measureHorizontal(int r47, int r48) {
        /*
            r46 = this;
            r7 = r46
            r8 = r47
            r9 = r48
            r10 = 0
            r7.mTotalLength = r10
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 1
            r5 = 0
            int r11 = r46.getVirtualChildCount()
            int r12 = android.view.View.MeasureSpec.getMode(r47)
            int r13 = android.view.View.MeasureSpec.getMode(r48)
            r6 = 0
            r14 = 0
            int[] r15 = r7.mMaxAscent
            if (r15 == 0) goto L_0x0025
            int[] r15 = r7.mMaxDescent
            if (r15 != 0) goto L_0x002e
        L_0x0025:
            r15 = 4
            int[] r10 = new int[r15]
            r7.mMaxAscent = r10
            int[] r10 = new int[r15]
            r7.mMaxDescent = r10
        L_0x002e:
            int[] r10 = r7.mMaxAscent
            int[] r15 = r7.mMaxDescent
            r17 = 3
            r18 = r6
            r6 = -1
            r10[r17] = r6
            r19 = 2
            r10[r19] = r6
            r20 = 1
            r10[r20] = r6
            r16 = 0
            r10[r16] = r6
            r15[r17] = r6
            r15[r19] = r6
            r15[r20] = r6
            r15[r16] = r6
            boolean r6 = r7.mBaselineAligned
            r22 = r14
            boolean r14 = r7.mUseLargestChild
            r9 = 1073741824(0x40000000, float:2.0)
            if (r12 != r9) goto L_0x005a
            r23 = 1
            goto L_0x005c
        L_0x005a:
            r23 = 0
        L_0x005c:
            r24 = 0
            r25 = 0
            r26 = r1
            r1 = r24
            r24 = r18
            r18 = r4
            r4 = r0
            r0 = r5
            r5 = r25
            r45 = r3
            r3 = r2
            r2 = r45
        L_0x0071:
            r29 = 0
            if (r5 >= r11) goto L_0x025a
            android.view.View r9 = r7.getVirtualChildAt(r5)
            if (r9 != 0) goto L_0x0091
            r31 = r1
            int r1 = r7.mTotalLength
            int r27 = r7.measureNullChild(r5)
            int r1 = r1 + r27
            r7.mTotalLength = r1
            r21 = r6
            r6 = r26
            r1 = r31
            r31 = r12
            goto L_0x024c
        L_0x0091:
            r31 = r1
            int r1 = r9.getVisibility()
            r32 = r2
            r2 = 8
            if (r1 != r2) goto L_0x00ae
            int r1 = r7.getChildrenSkipCount(r9, r5)
            int r5 = r5 + r1
            r21 = r6
            r6 = r26
            r1 = r31
            r2 = r32
            r31 = r12
            goto L_0x024c
        L_0x00ae:
            boolean r1 = r7.hasDividerBeforeChildAt(r5)
            if (r1 == 0) goto L_0x00bb
            int r1 = r7.mTotalLength
            int r2 = r7.mDividerWidth
            int r1 = r1 + r2
            r7.mTotalLength = r1
        L_0x00bb:
            android.view.ViewGroup$LayoutParams r1 = r9.getLayoutParams()
            r2 = r1
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r2 = (android.support.p004v7.widget.LinearLayoutCompat.LayoutParams) r2
            float r1 = r2.weight
            float r30 = r0 + r1
            r0 = 1073741824(0x40000000, float:2.0)
            if (r12 != r0) goto L_0x0122
            int r0 = r2.width
            if (r0 != 0) goto L_0x0122
            float r0 = r2.weight
            int r0 = (r0 > r29 ? 1 : (r0 == r29 ? 0 : -1))
            if (r0 <= 0) goto L_0x0122
            if (r23 == 0) goto L_0x00e4
            int r0 = r7.mTotalLength
            int r1 = r2.leftMargin
            r33 = r3
            int r3 = r2.rightMargin
            int r1 = r1 + r3
            int r0 = r0 + r1
            r7.mTotalLength = r0
            goto L_0x00f4
        L_0x00e4:
            r33 = r3
            int r0 = r7.mTotalLength
            int r1 = r2.leftMargin
            int r1 = r1 + r0
            int r3 = r2.rightMargin
            int r1 = r1 + r3
            int r1 = java.lang.Math.max(r0, r1)
            r7.mTotalLength = r1
        L_0x00f4:
            if (r6 == 0) goto L_0x010f
            r0 = 0
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r0)
            r9.measure(r1, r1)
            r1 = r2
            r41 = r4
            r8 = r5
            r21 = r6
            r3 = r31
            r38 = r32
            r39 = r33
            r31 = r12
            r12 = -1
            goto L_0x01a9
        L_0x010f:
            r22 = 1
            r1 = r2
            r41 = r4
            r8 = r5
            r21 = r6
            r3 = r31
            r38 = r32
            r39 = r33
            r31 = r12
            r12 = -1
            goto L_0x01a9
        L_0x0122:
            r33 = r3
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            int r1 = r2.width
            if (r1 != 0) goto L_0x0136
            float r1 = r2.weight
            int r1 = (r1 > r29 ? 1 : (r1 == r29 ? 0 : -1))
            if (r1 <= 0) goto L_0x0136
            r0 = 0
            r1 = -2
            r2.width = r1
            r3 = r0
            goto L_0x0137
        L_0x0136:
            r3 = r0
        L_0x0137:
            int r0 = (r30 > r29 ? 1 : (r30 == r29 ? 0 : -1))
            if (r0 != 0) goto L_0x0140
            int r0 = r7.mTotalLength
            r34 = r0
            goto L_0x0142
        L_0x0140:
            r34 = 0
        L_0x0142:
            r35 = 0
            r0 = r46
            r36 = r31
            r1 = r9
            r37 = r2
            r38 = r32
            r2 = r5
            r40 = r3
            r39 = r33
            r3 = r47
            r41 = r4
            r4 = r34
            r8 = r5
            r5 = r48
            r21 = r6
            r31 = r12
            r12 = -1
            r6 = r35
            r0.measureChildBeforeLayout(r1, r2, r3, r4, r5, r6)
            r0 = r40
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r0 == r1) goto L_0x0170
            r1 = r37
            r1.width = r0
            goto L_0x0172
        L_0x0170:
            r1 = r37
        L_0x0172:
            int r2 = r9.getMeasuredWidth()
            if (r23 == 0) goto L_0x0189
            int r3 = r7.mTotalLength
            int r4 = r1.leftMargin
            int r4 = r4 + r2
            int r5 = r1.rightMargin
            int r4 = r4 + r5
            int r5 = r7.getNextLocationOffset(r9)
            int r4 = r4 + r5
            int r3 = r3 + r4
            r7.mTotalLength = r3
            goto L_0x019e
        L_0x0189:
            int r3 = r7.mTotalLength
            int r4 = r3 + r2
            int r5 = r1.leftMargin
            int r4 = r4 + r5
            int r5 = r1.rightMargin
            int r4 = r4 + r5
            int r5 = r7.getNextLocationOffset(r9)
            int r4 = r4 + r5
            int r4 = java.lang.Math.max(r3, r4)
            r7.mTotalLength = r4
        L_0x019e:
            if (r14 == 0) goto L_0x01a7
            r3 = r36
            int r3 = java.lang.Math.max(r2, r3)
            goto L_0x01a9
        L_0x01a7:
            r3 = r36
        L_0x01a9:
            r0 = 0
            r2 = 1073741824(0x40000000, float:2.0)
            if (r13 == r2) goto L_0x01b5
            int r2 = r1.height
            if (r2 != r12) goto L_0x01b5
            r24 = 1
            r0 = 1
        L_0x01b5:
            int r2 = r1.topMargin
            int r4 = r1.bottomMargin
            int r2 = r2 + r4
            int r4 = r9.getMeasuredHeight()
            int r4 = r4 + r2
            int r5 = r9.getMeasuredState()
            r6 = r26
            int r5 = android.view.View.combineMeasuredStates(r6, r5)
            if (r21 == 0) goto L_0x0200
            int r6 = r9.getBaseline()
            if (r6 == r12) goto L_0x01fb
            int r12 = r1.gravity
            if (r12 >= 0) goto L_0x01d8
            int r12 = r7.mGravity
            goto L_0x01da
        L_0x01d8:
            int r12 = r1.gravity
        L_0x01da:
            r12 = r12 & 112(0x70, float:1.57E-43)
            int r27 = r12 >> 4
            r28 = -2
            r27 = r27 & -2
            int r27 = r27 >> 1
            r28 = r2
            r2 = r10[r27]
            int r2 = java.lang.Math.max(r2, r6)
            r10[r27] = r2
            r2 = r15[r27]
            r32 = r3
            int r3 = r4 - r6
            int r2 = java.lang.Math.max(r2, r3)
            r15[r27] = r2
            goto L_0x0204
        L_0x01fb:
            r28 = r2
            r32 = r3
            goto L_0x0204
        L_0x0200:
            r28 = r2
            r32 = r3
        L_0x0204:
            r2 = r41
            int r2 = java.lang.Math.max(r2, r4)
            if (r18 == 0) goto L_0x0213
            int r3 = r1.height
            r6 = -1
            if (r3 != r6) goto L_0x0213
            r3 = 1
            goto L_0x0214
        L_0x0213:
            r3 = 0
        L_0x0214:
            float r6 = r1.weight
            int r6 = (r6 > r29 ? 1 : (r6 == r29 ? 0 : -1))
            if (r6 <= 0) goto L_0x022b
            if (r0 == 0) goto L_0x021f
            r6 = r28
            goto L_0x0220
        L_0x021f:
            r6 = r4
        L_0x0220:
            r12 = r38
            int r6 = java.lang.Math.max(r12, r6)
            r37 = r1
            r1 = r39
            goto L_0x023c
        L_0x022b:
            r12 = r38
            if (r0 == 0) goto L_0x0232
            r6 = r28
            goto L_0x0233
        L_0x0232:
            r6 = r4
        L_0x0233:
            r37 = r1
            r1 = r39
            int r1 = java.lang.Math.max(r1, r6)
            r6 = r12
        L_0x023c:
            int r12 = r7.getChildrenSkipCount(r9, r8)
            int r8 = r8 + r12
            r4 = r2
            r18 = r3
            r2 = r6
            r0 = r30
            r3 = r1
            r6 = r5
            r5 = r8
            r1 = r32
        L_0x024c:
            int r5 = r5 + 1
            r8 = r47
            r26 = r6
            r6 = r21
            r12 = r31
            r9 = 1073741824(0x40000000, float:2.0)
            goto L_0x0071
        L_0x025a:
            r8 = r5
            r21 = r6
            r31 = r12
            r6 = r26
            r12 = r2
            r2 = r4
            r45 = r3
            r3 = r1
            r1 = r45
            int r4 = r7.mTotalLength
            if (r4 <= 0) goto L_0x0279
            boolean r4 = r7.hasDividerBeforeChildAt(r11)
            if (r4 == 0) goto L_0x0279
            int r4 = r7.mTotalLength
            int r5 = r7.mDividerWidth
            int r4 = r4 + r5
            r7.mTotalLength = r4
        L_0x0279:
            r4 = r10[r20]
            r5 = -1
            if (r4 != r5) goto L_0x028f
            r4 = 0
            r8 = r10[r4]
            if (r8 != r5) goto L_0x028f
            r4 = r10[r19]
            if (r4 != r5) goto L_0x028f
            r4 = r10[r17]
            if (r4 == r5) goto L_0x028c
            goto L_0x028f
        L_0x028c:
            r32 = r6
            goto L_0x02c1
        L_0x028f:
            r4 = r10[r17]
            r5 = 0
            r8 = r10[r5]
            r9 = r10[r20]
            r5 = r10[r19]
            int r5 = java.lang.Math.max(r9, r5)
            int r5 = java.lang.Math.max(r8, r5)
            int r4 = java.lang.Math.max(r4, r5)
            r5 = r15[r17]
            r8 = 0
            r9 = r15[r8]
            r8 = r15[r20]
            r32 = r6
            r6 = r15[r19]
            int r6 = java.lang.Math.max(r8, r6)
            int r6 = java.lang.Math.max(r9, r6)
            int r5 = java.lang.Math.max(r5, r6)
            int r6 = r4 + r5
            int r2 = java.lang.Math.max(r2, r6)
        L_0x02c1:
            if (r14 == 0) goto L_0x033d
            r4 = r31
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r4 == r5) goto L_0x02d0
            if (r4 != 0) goto L_0x02cc
            goto L_0x02d0
        L_0x02cc:
            r27 = r2
            goto L_0x0341
        L_0x02d0:
            r5 = 0
            r7.mTotalLength = r5
            r5 = 0
        L_0x02d4:
            if (r5 >= r11) goto L_0x0338
            android.view.View r6 = r7.getVirtualChildAt(r5)
            if (r6 != 0) goto L_0x02ea
            int r8 = r7.mTotalLength
            int r9 = r7.measureNullChild(r5)
            int r8 = r8 + r9
            r7.mTotalLength = r8
            r27 = r2
            r31 = r5
            goto L_0x0333
        L_0x02ea:
            int r8 = r6.getVisibility()
            r9 = 8
            if (r8 != r9) goto L_0x02fc
            int r8 = r7.getChildrenSkipCount(r6, r5)
            int r5 = r5 + r8
            r27 = r2
            r31 = r5
            goto L_0x0333
        L_0x02fc:
            android.view.ViewGroup$LayoutParams r8 = r6.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r8 = (android.support.p004v7.widget.LinearLayoutCompat.LayoutParams) r8
            if (r23 == 0) goto L_0x031a
            int r9 = r7.mTotalLength
            r27 = r2
            int r2 = r8.leftMargin
            int r2 = r2 + r3
            r31 = r5
            int r5 = r8.rightMargin
            int r2 = r2 + r5
            int r5 = r7.getNextLocationOffset(r6)
            int r2 = r2 + r5
            int r9 = r9 + r2
            r7.mTotalLength = r9
            goto L_0x0333
        L_0x031a:
            r27 = r2
            r31 = r5
            int r2 = r7.mTotalLength
            int r5 = r2 + r3
            int r9 = r8.leftMargin
            int r5 = r5 + r9
            int r9 = r8.rightMargin
            int r5 = r5 + r9
            int r9 = r7.getNextLocationOffset(r6)
            int r5 = r5 + r9
            int r5 = java.lang.Math.max(r2, r5)
            r7.mTotalLength = r5
        L_0x0333:
            int r5 = r31 + 1
            r2 = r27
            goto L_0x02d4
        L_0x0338:
            r27 = r2
            r31 = r5
            goto L_0x0341
        L_0x033d:
            r27 = r2
            r4 = r31
        L_0x0341:
            int r2 = r7.mTotalLength
            int r5 = r46.getPaddingLeft()
            int r6 = r46.getPaddingRight()
            int r5 = r5 + r6
            int r2 = r2 + r5
            r7.mTotalLength = r2
            int r2 = r7.mTotalLength
            int r5 = r46.getSuggestedMinimumWidth()
            int r2 = java.lang.Math.max(r2, r5)
            r5 = r47
            r6 = 0
            int r8 = android.view.View.resolveSizeAndState(r2, r5, r6)
            r6 = 16777215(0xffffff, float:2.3509886E-38)
            r2 = r8 & r6
            int r6 = r7.mTotalLength
            int r6 = r2 - r6
            if (r22 != 0) goto L_0x040c
            if (r6 == 0) goto L_0x037c
            int r31 = (r0 > r29 ? 1 : (r0 == r29 ? 0 : -1))
            if (r31 <= 0) goto L_0x037c
            r33 = r0
            r34 = r2
            r36 = r3
            r2 = r1
            r1 = 1073741824(0x40000000, float:2.0)
            goto L_0x0415
        L_0x037c:
            int r1 = java.lang.Math.max(r1, r12)
            if (r14 == 0) goto L_0x03ef
            r9 = 1073741824(0x40000000, float:2.0)
            if (r4 == r9) goto L_0x03ef
            r9 = 0
        L_0x0387:
            if (r9 >= r11) goto L_0x03e4
            r33 = r0
            android.view.View r0 = r7.getVirtualChildAt(r9)
            if (r0 == 0) goto L_0x03d1
            r16 = r1
            int r1 = r0.getVisibility()
            r34 = r2
            r2 = 8
            if (r1 != r2) goto L_0x03a2
            r36 = r3
            r1 = 1073741824(0x40000000, float:2.0)
            goto L_0x03d9
        L_0x03a2:
            android.view.ViewGroup$LayoutParams r1 = r0.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r1 = (android.support.p004v7.widget.LinearLayoutCompat.LayoutParams) r1
            float r2 = r1.weight
            int r17 = (r2 > r29 ? 1 : (r2 == r29 ? 0 : -1))
            if (r17 <= 0) goto L_0x03c8
            r17 = r1
            r19 = r2
            r1 = 1073741824(0x40000000, float:2.0)
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r1)
            r36 = r3
            int r3 = r0.getMeasuredHeight()
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r1)
            r0.measure(r2, r3)
            goto L_0x03d9
        L_0x03c8:
            r17 = r1
            r19 = r2
            r36 = r3
            r1 = 1073741824(0x40000000, float:2.0)
            goto L_0x03d9
        L_0x03d1:
            r16 = r1
            r34 = r2
            r36 = r3
            r1 = 1073741824(0x40000000, float:2.0)
        L_0x03d9:
            int r9 = r9 + 1
            r1 = r16
            r0 = r33
            r2 = r34
            r3 = r36
            goto L_0x0387
        L_0x03e4:
            r33 = r0
            r16 = r1
            r34 = r2
            r36 = r3
            r1 = 1073741824(0x40000000, float:2.0)
            goto L_0x03f9
        L_0x03ef:
            r33 = r0
            r16 = r1
            r34 = r2
            r36 = r3
            r1 = 1073741824(0x40000000, float:2.0)
        L_0x03f9:
            r40 = r4
            r4 = r6
            r25 = r8
            r38 = r12
            r3 = r27
            r2 = r32
            r6 = r48
            r32 = r11
            r27 = r14
            goto L_0x05ff
        L_0x040c:
            r33 = r0
            r34 = r2
            r36 = r3
            r2 = r1
            r1 = 1073741824(0x40000000, float:2.0)
        L_0x0415:
            float r0 = r7.mWeightSum
            int r3 = (r0 > r29 ? 1 : (r0 == r29 ? 0 : -1))
            if (r3 <= 0) goto L_0x041c
            goto L_0x041e
        L_0x041c:
            r0 = r33
        L_0x041e:
            r3 = -1
            r10[r17] = r3
            r10[r19] = r3
            r10[r20] = r3
            r9 = 0
            r10[r9] = r3
            r15[r17] = r3
            r15[r19] = r3
            r15[r20] = r3
            r15[r9] = r3
            r3 = -1
            r7.mTotalLength = r9
            r9 = 0
            r1 = r2
            r2 = r32
        L_0x0437:
            if (r9 >= r11) goto L_0x05a1
            r38 = r12
            android.view.View r12 = r7.getVirtualChildAt(r9)
            if (r12 == 0) goto L_0x0583
            r27 = r14
            int r14 = r12.getVisibility()
            r5 = 8
            if (r14 != r5) goto L_0x0458
            r40 = r4
            r4 = r6
            r25 = r8
            r32 = r11
            r28 = -2
            r6 = r48
            goto L_0x0590
        L_0x0458:
            android.view.ViewGroup$LayoutParams r14 = r12.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r14 = (android.support.p004v7.widget.LinearLayoutCompat.LayoutParams) r14
            float r5 = r14.weight
            int r32 = (r5 > r29 ? 1 : (r5 == r29 ? 0 : -1))
            if (r32 <= 0) goto L_0x04cd
            r32 = r11
            float r11 = (float) r6
            float r11 = r11 * r5
            float r11 = r11 / r0
            int r11 = (int) r11
            float r0 = r0 - r5
            int r6 = r6 - r11
            int r35 = r46.getPaddingTop()
            int r37 = r46.getPaddingBottom()
            int r35 = r35 + r37
            r37 = r0
            int r0 = r14.topMargin
            int r35 = r35 + r0
            int r0 = r14.bottomMargin
            int r0 = r35 + r0
            r35 = r5
            int r5 = r14.height
            r39 = r6
            r25 = r8
            r8 = 1073741824(0x40000000, float:2.0)
            r6 = r48
            int r0 = getChildMeasureSpec(r6, r0, r5)
            int r5 = r14.width
            if (r5 != 0) goto L_0x04a9
            if (r4 == r8) goto L_0x049a
            goto L_0x04a9
        L_0x049a:
            if (r11 <= 0) goto L_0x049e
            r5 = r11
            goto L_0x049f
        L_0x049e:
            r5 = 0
        L_0x049f:
            int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r8)
            r12.measure(r5, r0)
            r40 = r4
            goto L_0x04bc
        L_0x04a9:
            int r5 = r12.getMeasuredWidth()
            int r5 = r5 + r11
            if (r5 >= 0) goto L_0x04b1
            r5 = 0
        L_0x04b1:
            r40 = r4
            int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r8)
            r12.measure(r4, r0)
        L_0x04bc:
            int r4 = r12.getMeasuredState()
            r5 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r4 = r4 & r5
            int r2 = android.view.View.combineMeasuredStates(r2, r4)
            r0 = r37
            r4 = r39
            goto L_0x04da
        L_0x04cd:
            r40 = r4
            r35 = r5
            r4 = r6
            r25 = r8
            r32 = r11
            r8 = 1073741824(0x40000000, float:2.0)
            r6 = r48
        L_0x04da:
            if (r23 == 0) goto L_0x04f1
            int r5 = r7.mTotalLength
            int r11 = r12.getMeasuredWidth()
            int r8 = r14.leftMargin
            int r11 = r11 + r8
            int r8 = r14.rightMargin
            int r11 = r11 + r8
            int r8 = r7.getNextLocationOffset(r12)
            int r11 = r11 + r8
            int r5 = r5 + r11
            r7.mTotalLength = r5
            goto L_0x0509
        L_0x04f1:
            int r5 = r7.mTotalLength
            int r8 = r12.getMeasuredWidth()
            int r8 = r8 + r5
            int r11 = r14.leftMargin
            int r8 = r8 + r11
            int r11 = r14.rightMargin
            int r8 = r8 + r11
            int r11 = r7.getNextLocationOffset(r12)
            int r8 = r8 + r11
            int r8 = java.lang.Math.max(r5, r8)
            r7.mTotalLength = r8
        L_0x0509:
            r5 = 1073741824(0x40000000, float:2.0)
            if (r13 == r5) goto L_0x0514
            int r5 = r14.height
            r8 = -1
            if (r5 != r8) goto L_0x0514
            r5 = 1
            goto L_0x0515
        L_0x0514:
            r5 = 0
        L_0x0515:
            int r8 = r14.topMargin
            int r11 = r14.bottomMargin
            int r8 = r8 + r11
            int r11 = r12.getMeasuredHeight()
            int r11 = r11 + r8
            int r3 = java.lang.Math.max(r3, r11)
            r39 = r0
            if (r5 == 0) goto L_0x0529
            r0 = r8
            goto L_0x052a
        L_0x0529:
            r0 = r11
        L_0x052a:
            int r0 = java.lang.Math.max(r1, r0)
            if (r18 == 0) goto L_0x0539
            int r1 = r14.height
            r41 = r0
            r0 = -1
            if (r1 != r0) goto L_0x053b
            r0 = 1
            goto L_0x053c
        L_0x0539:
            r41 = r0
        L_0x053b:
            r0 = 0
        L_0x053c:
            if (r21 == 0) goto L_0x0576
            int r1 = r12.getBaseline()
            r18 = r0
            r0 = -1
            if (r1 == r0) goto L_0x0571
            int r0 = r14.gravity
            if (r0 >= 0) goto L_0x054e
            int r0 = r7.mGravity
            goto L_0x0550
        L_0x054e:
            int r0 = r14.gravity
        L_0x0550:
            r0 = r0 & 112(0x70, float:1.57E-43)
            int r42 = r0 >> 4
            r28 = -2
            r42 = r42 & -2
            int r42 = r42 >> 1
            r43 = r0
            r0 = r10[r42]
            int r0 = java.lang.Math.max(r0, r1)
            r10[r42] = r0
            r0 = r15[r42]
            r44 = r2
            int r2 = r11 - r1
            int r0 = java.lang.Math.max(r0, r2)
            r15[r42] = r0
            goto L_0x057c
        L_0x0571:
            r44 = r2
            r28 = -2
            goto L_0x057c
        L_0x0576:
            r18 = r0
            r44 = r2
            r28 = -2
        L_0x057c:
            r0 = r39
            r1 = r41
            r2 = r44
            goto L_0x0590
        L_0x0583:
            r40 = r4
            r4 = r6
            r25 = r8
            r32 = r11
            r27 = r14
            r28 = -2
            r6 = r48
        L_0x0590:
            int r9 = r9 + 1
            r5 = r47
            r6 = r4
            r8 = r25
            r14 = r27
            r11 = r32
            r12 = r38
            r4 = r40
            goto L_0x0437
        L_0x05a1:
            r40 = r4
            r4 = r6
            r25 = r8
            r32 = r11
            r38 = r12
            r27 = r14
            r6 = r48
            int r5 = r7.mTotalLength
            int r8 = r46.getPaddingLeft()
            int r9 = r46.getPaddingRight()
            int r8 = r8 + r9
            int r5 = r5 + r8
            r7.mTotalLength = r5
            r5 = r10[r20]
            r8 = -1
            if (r5 != r8) goto L_0x05ce
            r5 = 0
            r9 = r10[r5]
            if (r9 != r8) goto L_0x05ce
            r5 = r10[r19]
            if (r5 != r8) goto L_0x05ce
            r5 = r10[r17]
            if (r5 == r8) goto L_0x05fd
        L_0x05ce:
            r5 = r10[r17]
            r8 = 0
            r9 = r10[r8]
            r11 = r10[r20]
            r12 = r10[r19]
            int r11 = java.lang.Math.max(r11, r12)
            int r9 = java.lang.Math.max(r9, r11)
            int r5 = java.lang.Math.max(r5, r9)
            r9 = r15[r17]
            r8 = r15[r8]
            r11 = r15[r20]
            r12 = r15[r19]
            int r11 = java.lang.Math.max(r11, r12)
            int r8 = java.lang.Math.max(r8, r11)
            int r8 = java.lang.Math.max(r9, r8)
            int r9 = r5 + r8
            int r3 = java.lang.Math.max(r3, r9)
        L_0x05fd:
            r16 = r1
        L_0x05ff:
            if (r18 != 0) goto L_0x0607
            r0 = 1073741824(0x40000000, float:2.0)
            if (r13 == r0) goto L_0x0607
            r3 = r16
        L_0x0607:
            int r0 = r46.getPaddingTop()
            int r1 = r46.getPaddingBottom()
            int r0 = r0 + r1
            int r3 = r3 + r0
            int r0 = r46.getSuggestedMinimumHeight()
            int r0 = java.lang.Math.max(r3, r0)
            r1 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r1 = r1 & r2
            r1 = r25 | r1
            int r3 = r2 << 16
            int r3 = android.view.View.resolveSizeAndState(r0, r6, r3)
            r7.setMeasuredDimension(r1, r3)
            if (r24 == 0) goto L_0x0631
            r1 = r47
            r3 = r32
            r7.forceUniformHeight(r3, r1)
            goto L_0x0635
        L_0x0631:
            r1 = r47
            r3 = r32
        L_0x0635:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.widget.LinearLayoutCompat.measureHorizontal(int, int):void");
    }

    private void forceUniformHeight(int count, int widthMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.height == -1) {
                    int oldWidth = lp.width;
                    lp.width = child.getMeasuredWidth();
                    measureChildWithMargins(child, widthMeasureSpec, 0, uniformMeasureSpec, 0);
                    lp.width = oldWidth;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int getChildrenSkipCount(View child, int index) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int measureNullChild(int childIndex) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void measureChildBeforeLayout(View child, int childIndex, int widthMeasureSpec, int totalWidth, int heightMeasureSpec, int totalHeight) {
        measureChildWithMargins(child, widthMeasureSpec, totalWidth, heightMeasureSpec, totalHeight);
    }

    /* access modifiers changed from: package-private */
    public int getLocationOffset(View child) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int getNextLocationOffset(View child) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mOrientation == 1) {
            layoutVertical(l, t, r, b);
        } else {
            layoutHorizontal(l, t, r, b);
        }
    }

    /* JADX INFO: Multiple debug info for r7v3 android.support.v7.widget.LinearLayoutCompat$LayoutParams: [D('paddingLeft' int), D('lp' android.support.v7.widget.LinearLayoutCompat$LayoutParams)] */
    /* access modifiers changed from: package-private */
    public void layoutVertical(int left, int top, int right, int bottom) {
        int childTop;
        int paddingLeft;
        int gravity;
        int childLeft;
        int paddingLeft2 = getPaddingLeft();
        int width = right - left;
        int childRight = width - getPaddingRight();
        int childSpace = (width - paddingLeft2) - getPaddingRight();
        int count = getVirtualChildCount();
        int i = this.mGravity;
        int majorGravity = i & 112;
        int minorGravity = i & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (majorGravity == 16) {
            childTop = getPaddingTop() + (((bottom - top) - this.mTotalLength) / 2);
        } else if (majorGravity != 80) {
            childTop = getPaddingTop();
        } else {
            childTop = ((getPaddingTop() + bottom) - top) - this.mTotalLength;
        }
        int i2 = 0;
        while (i2 < count) {
            View child = getVirtualChildAt(i2);
            if (child == null) {
                childTop += measureNullChild(i2);
                paddingLeft = paddingLeft2;
            } else if (child.getVisibility() != 8) {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int gravity2 = lp.gravity;
                if (gravity2 < 0) {
                    gravity = minorGravity;
                } else {
                    gravity = gravity2;
                }
                int absoluteGravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this)) & 7;
                if (absoluteGravity == 1) {
                    childLeft = ((((childSpace - childWidth) / 2) + paddingLeft2) + lp.leftMargin) - lp.rightMargin;
                } else if (absoluteGravity != 5) {
                    childLeft = lp.leftMargin + paddingLeft2;
                } else {
                    childLeft = (childRight - childWidth) - lp.rightMargin;
                }
                if (hasDividerBeforeChildAt(i2) != 0) {
                    childTop += this.mDividerHeight;
                }
                int childTop2 = childTop + lp.topMargin;
                paddingLeft = paddingLeft2;
                setChildFrame(child, childLeft, childTop2 + getLocationOffset(child), childWidth, childHeight);
                int childTop3 = childTop2 + childHeight + lp.bottomMargin + getNextLocationOffset(child);
                i2 += getChildrenSkipCount(child, i2);
                childTop = childTop3;
            } else {
                paddingLeft = paddingLeft2;
            }
            i2++;
            paddingLeft2 = paddingLeft;
        }
    }

    /* JADX INFO: Multiple debug info for r7v2 android.view.View: [D('isLayoutRtl' boolean), D('child' android.view.View)] */
    /* JADX INFO: Multiple debug info for r8v4 int: [D('paddingTop' int), D('childIndex' int)] */
    /* JADX INFO: Multiple debug info for r9v4 android.support.v7.widget.LinearLayoutCompat$LayoutParams: [D('childBaseline' int), D('lp' android.support.v7.widget.LinearLayoutCompat$LayoutParams)] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00c5  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c9  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x011a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void layoutHorizontal(int r34, int r35, int r36, int r37) {
        /*
            r33 = this;
            r6 = r33
            boolean r7 = android.support.p004v7.widget.ViewUtils.isLayoutRtl(r33)
            int r8 = r33.getPaddingTop()
            int r9 = r37 - r35
            int r0 = r33.getPaddingBottom()
            int r10 = r9 - r0
            int r0 = r9 - r8
            int r1 = r33.getPaddingBottom()
            int r11 = r0 - r1
            int r12 = r33.getVirtualChildCount()
            int r0 = r6.mGravity
            r1 = 8388615(0x800007, float:1.1754953E-38)
            r13 = r0 & r1
            r14 = r0 & 112(0x70, float:1.57E-43)
            boolean r15 = r6.mBaselineAligned
            int[] r5 = r6.mMaxAscent
            int[] r4 = r6.mMaxDescent
            int r3 = android.support.p001v4.view.ViewCompat.getLayoutDirection(r33)
            int r0 = android.support.p001v4.view.GravityCompat.getAbsoluteGravity(r13, r3)
            r16 = 2
            r2 = 1
            if (r0 == r2) goto L_0x004e
            r1 = 5
            if (r0 == r1) goto L_0x0042
            int r0 = r33.getPaddingLeft()
            goto L_0x005b
        L_0x0042:
            int r0 = r33.getPaddingLeft()
            int r0 = r0 + r36
            int r0 = r0 - r34
            int r1 = r6.mTotalLength
            int r0 = r0 - r1
            goto L_0x005b
        L_0x004e:
            int r0 = r33.getPaddingLeft()
            int r1 = r36 - r34
            int r2 = r6.mTotalLength
            int r1 = r1 - r2
            int r1 = r1 / 2
            int r0 = r0 + r1
        L_0x005b:
            r1 = 0
            r2 = 1
            if (r7 == 0) goto L_0x0067
            int r1 = r12 + -1
            r2 = -1
            r18 = r1
            r19 = r2
            goto L_0x006b
        L_0x0067:
            r18 = r1
            r19 = r2
        L_0x006b:
            r1 = 0
            r2 = r1
        L_0x006d:
            if (r2 >= r12) goto L_0x0177
            int r1 = r19 * r2
            int r1 = r18 + r1
            r20 = r7
            android.view.View r7 = r6.getVirtualChildAt(r1)
            if (r7 != 0) goto L_0x0091
            int r21 = r6.measureNullChild(r1)
            int r0 = r0 + r21
            r22 = r3
            r31 = r4
            r32 = r5
            r29 = r8
            r26 = r9
            r28 = r12
            r21 = 1
            goto L_0x0165
        L_0x0091:
            r21 = r2
            int r2 = r7.getVisibility()
            r22 = r3
            r3 = 8
            if (r2 == r3) goto L_0x0154
            int r23 = r7.getMeasuredWidth()
            int r24 = r7.getMeasuredHeight()
            r2 = -1
            android.view.ViewGroup$LayoutParams r3 = r7.getLayoutParams()
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r3 = (android.support.p004v7.widget.LinearLayoutCompat.LayoutParams) r3
            r25 = r2
            r2 = -1
            if (r15 == 0) goto L_0x00bd
            r26 = r9
            int r9 = r3.height
            if (r9 == r2) goto L_0x00bf
            int r9 = r7.getBaseline()
            goto L_0x00c1
        L_0x00bd:
            r26 = r9
        L_0x00bf:
            r9 = r25
        L_0x00c1:
            int r2 = r3.gravity
            if (r2 >= 0) goto L_0x00c9
            r2 = r14
            r27 = r2
            goto L_0x00cb
        L_0x00c9:
            r27 = r2
        L_0x00cb:
            r2 = r27 & 112(0x70, float:1.57E-43)
            r28 = r12
            r12 = 16
            if (r2 == r12) goto L_0x0107
            r12 = 48
            if (r2 == r12) goto L_0x00f5
            r12 = 80
            if (r2 == r12) goto L_0x00de
            r2 = r8
            r12 = r2
            goto L_0x0114
        L_0x00de:
            int r2 = r10 - r24
            int r12 = r3.bottomMargin
            int r2 = r2 - r12
            r12 = -1
            if (r9 == r12) goto L_0x00f3
            int r12 = r7.getMeasuredHeight()
            int r12 = r12 - r9
            r25 = r4[r16]
            int r25 = r25 - r12
            int r2 = r2 - r25
            r12 = r2
            goto L_0x0114
        L_0x00f3:
            r12 = r2
            goto L_0x0114
        L_0x00f5:
            int r2 = r3.topMargin
            int r2 = r2 + r8
            r12 = -1
            if (r9 == r12) goto L_0x0104
            r12 = 1
            r17 = r5[r12]
            int r17 = r17 - r9
            int r2 = r2 + r17
            r12 = r2
            goto L_0x0114
        L_0x0104:
            r12 = 1
            r12 = r2
            goto L_0x0114
        L_0x0107:
            r12 = 1
            int r2 = r11 - r24
            int r2 = r2 / 2
            int r2 = r2 + r8
            int r12 = r3.topMargin
            int r2 = r2 + r12
            int r12 = r3.bottomMargin
            int r2 = r2 - r12
            r12 = r2
        L_0x0114:
            boolean r2 = r6.hasDividerBeforeChildAt(r1)
            if (r2 == 0) goto L_0x011d
            int r2 = r6.mDividerWidth
            int r0 = r0 + r2
        L_0x011d:
            int r2 = r3.leftMargin
            int r25 = r0 + r2
            int r0 = r6.getLocationOffset(r7)
            int r2 = r25 + r0
            r0 = r33
            r29 = r8
            r8 = r1
            r1 = r7
            r17 = r21
            r21 = 1
            r30 = r9
            r9 = r3
            r3 = r12
            r31 = r4
            r4 = r23
            r32 = r5
            r5 = r24
            r0.setChildFrame(r1, r2, r3, r4, r5)
            int r0 = r9.rightMargin
            int r0 = r23 + r0
            int r1 = r6.getNextLocationOffset(r7)
            int r0 = r0 + r1
            int r25 = r25 + r0
            int r0 = r6.getChildrenSkipCount(r7, r8)
            int r2 = r17 + r0
            r0 = r25
            goto L_0x0165
        L_0x0154:
            r31 = r4
            r32 = r5
            r29 = r8
            r26 = r9
            r28 = r12
            r17 = r21
            r21 = 1
            r8 = r1
            r2 = r17
        L_0x0165:
            int r2 = r2 + 1
            r7 = r20
            r3 = r22
            r9 = r26
            r12 = r28
            r8 = r29
            r4 = r31
            r5 = r32
            goto L_0x006d
        L_0x0177:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.widget.LinearLayoutCompat.layoutHorizontal(int, int, int, int):void");
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    public void setOrientation(int orientation) {
        if (this.mOrientation != orientation) {
            this.mOrientation = orientation;
            requestLayout();
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setGravity(int gravity) {
        if (this.mGravity != gravity) {
            if ((8388615 & gravity) == 0) {
                gravity |= GravityCompat.START;
            }
            if ((gravity & 112) == 0) {
                gravity |= 48;
            }
            this.mGravity = gravity;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setHorizontalGravity(int horizontalGravity) {
        int gravity = horizontalGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int i = this.mGravity;
        if ((8388615 & i) != gravity) {
            this.mGravity = (-8388616 & i) | gravity;
            requestLayout();
        }
    }

    public void setVerticalGravity(int verticalGravity) {
        int gravity = verticalGravity & 112;
        int i = this.mGravity;
        if ((i & 112) != gravity) {
            this.mGravity = (i & -113) | gravity;
            requestLayout();
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        int i = this.mOrientation;
        if (i == 0) {
            return new LayoutParams(-2, -2);
        }
        if (i == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(ACCESSIBILITY_CLASS_NAME);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ACCESSIBILITY_CLASS_NAME);
    }

    /* renamed from: android.support.v7.widget.LinearLayoutCompat$LayoutParams */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int gravity;
        public float weight;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.gravity = -1;
            TypedArray a = c.obtainStyledAttributes(attrs, C0233R.styleable.LinearLayoutCompat_Layout);
            this.weight = a.getFloat(C0233R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.gravity = a.getInt(C0233R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(int width, int height, float weight2) {
            super(width, height);
            this.gravity = -1;
            this.weight = weight2;
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
            this.gravity = -1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
            this.gravity = -1;
        }

        public LayoutParams(LayoutParams source) {
            super((ViewGroup.MarginLayoutParams) source);
            this.gravity = -1;
            this.weight = source.weight;
            this.gravity = source.gravity;
        }
    }
}
