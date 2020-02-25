package android.support.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.support.annotation.AnimatorRes;
import android.support.annotation.RestrictTo;
import android.support.p001v4.content.res.TypedArrayUtils;
import android.support.p001v4.graphics.PathParser;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class AnimatorInflaterCompat {
    private static final boolean DBG_ANIMATOR_INFLATER = false;
    private static final int MAX_NUM_POINTS = 100;
    private static final String TAG = "AnimatorInflater";
    private static final int TOGETHER = 0;
    private static final int VALUE_TYPE_COLOR = 3;
    private static final int VALUE_TYPE_FLOAT = 0;
    private static final int VALUE_TYPE_INT = 1;
    private static final int VALUE_TYPE_PATH = 2;
    private static final int VALUE_TYPE_UNDEFINED = 4;

    private AnimatorInflaterCompat() {
    }

    public static Animator loadAnimator(Context context, @AnimatorRes int id) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 24) {
            return AnimatorInflater.loadAnimator(context, id);
        }
        return loadAnimator(context, context.getResources(), context.getTheme(), id);
    }

    public static Animator loadAnimator(Context context, Resources resources, Resources.Theme theme, @AnimatorRes int id) throws Resources.NotFoundException {
        return loadAnimator(context, resources, theme, id, 1.0f);
    }

    public static Animator loadAnimator(Context context, Resources resources, Resources.Theme theme, @AnimatorRes int id, float pathErrorScale) throws Resources.NotFoundException {
        XmlResourceParser parser = null;
        try {
            XmlResourceParser parser2 = resources.getAnimation(id);
            Animator animator = createAnimatorFromXml(context, resources, theme, parser2, pathErrorScale);
            if (parser2 != null) {
                parser2.close();
            }
            return animator;
        } catch (XmlPullParserException ex) {
            Resources.NotFoundException rnf = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(id));
            rnf.initCause(ex);
            throw rnf;
        } catch (IOException ex2) {
            Resources.NotFoundException rnf2 = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(id));
            rnf2.initCause(ex2);
            throw rnf2;
        } catch (Throwable th) {
            if (parser != null) {
                parser.close();
            }
            throw th;
        }
    }

    /* JADX INFO: Multiple debug info for r4v28 android.support.v4.graphics.PathParser$PathDataNode[]: [D('nodesTo' android.support.v4.graphics.PathParser$PathDataNode[]), D('tvFrom' android.util.TypedValue)] */
    /* JADX INFO: Multiple debug info for r14v19 android.support.graphics.drawable.AnimatorInflaterCompat$PathDataEvaluator: [D('evaluator' android.animation.TypeEvaluator), D('returnValue' android.animation.PropertyValuesHolder)] */
    /* JADX INFO: Multiple debug info for r14v22 android.animation.TypeEvaluator: [D('evaluator' android.animation.TypeEvaluator), D('returnValue' android.animation.PropertyValuesHolder)] */
    private static PropertyValuesHolder getPVH(TypedArray styledAttributes, int valueType, int valueFromId, int valueToId, String propertyName) {
        int valueType2;
        PropertyValuesHolder returnValue;
        PropertyValuesHolder returnValue2;
        int valueTo;
        char c;
        int valueFrom;
        int valueTo2;
        char c2;
        float valueTo3;
        float valueFrom2;
        float valueTo4;
        int toType;
        PropertyValuesHolder propertyValuesHolder;
        TypedArray typedArray = styledAttributes;
        int i = valueFromId;
        int i2 = valueToId;
        String str = propertyName;
        TypedValue tvFrom = typedArray.peekValue(i);
        boolean hasFrom = tvFrom != null;
        int fromType = hasFrom ? tvFrom.type : 0;
        TypedValue tvTo = typedArray.peekValue(i2);
        boolean hasTo = tvTo != null;
        int toType2 = hasTo ? tvTo.type : 0;
        int i3 = valueType;
        if (i3 != 4) {
            valueType2 = i3;
        } else if ((!hasFrom || !isColorType(fromType)) && (!hasTo || !isColorType(toType2))) {
            valueType2 = 0;
        } else {
            valueType2 = 3;
        }
        boolean getFloats = valueType2 == 0;
        if (valueType2 == 2) {
            String fromString = typedArray.getString(i);
            String toString = typedArray.getString(i2);
            PathParser.PathDataNode[] nodesFrom = PathParser.createNodesFromPathData(fromString);
            PathParser.PathDataNode[] nodesTo = PathParser.createNodesFromPathData(toString);
            if (nodesFrom == null && nodesTo == null) {
                toType = toType2;
                propertyValuesHolder = null;
            } else if (nodesFrom != null) {
                TypeEvaluator evaluator = new PathDataEvaluator();
                if (nodesTo == null) {
                    TypeEvaluator evaluator2 = evaluator;
                    toType = toType2;
                    returnValue = PropertyValuesHolder.ofObject(str, evaluator2, nodesFrom);
                } else if (PathParser.canMorph(nodesFrom, nodesTo)) {
                    returnValue = PropertyValuesHolder.ofObject(str, evaluator, nodesFrom, nodesTo);
                    toType = toType2;
                } else {
                    throw new InflateException(" Can't morph from " + fromString + " to " + toString);
                }
            } else {
                toType = toType2;
                propertyValuesHolder = null;
                if (nodesTo != null) {
                    returnValue = PropertyValuesHolder.ofObject(str, new PathDataEvaluator(), nodesTo);
                }
            }
            returnValue = propertyValuesHolder;
        } else {
            int toType3 = toType2;
            TypeEvaluator evaluator3 = null;
            if (valueType2 == 3) {
                evaluator3 = ArgbEvaluator.getInstance();
            }
            if (!getFloats) {
                int i4 = valueToId;
                int toType4 = toType3;
                if (hasFrom) {
                    if (fromType == 5) {
                        valueFrom = (int) typedArray.getDimension(i, 0.0f);
                    } else if (isColorType(fromType) != 0) {
                        valueFrom = typedArray.getColor(i, 0);
                    } else {
                        valueFrom = typedArray.getInt(i, 0);
                    }
                    if (hasTo) {
                        if (toType4 == 5) {
                            valueTo2 = (int) typedArray.getDimension(i4, 0.0f);
                            c2 = 0;
                        } else if (isColorType(toType4) != 0) {
                            c2 = 0;
                            valueTo2 = typedArray.getColor(i4, 0);
                        } else {
                            c2 = 0;
                            valueTo2 = typedArray.getInt(i4, 0);
                        }
                        int[] iArr = new int[2];
                        iArr[c2] = valueFrom;
                        iArr[1] = valueTo2;
                        returnValue2 = PropertyValuesHolder.ofInt(str, iArr);
                    } else {
                        returnValue2 = PropertyValuesHolder.ofInt(str, valueFrom);
                    }
                } else if (hasTo) {
                    if (toType4 == 5) {
                        valueTo = (int) typedArray.getDimension(i4, 0.0f);
                        c = 0;
                    } else if (isColorType(toType4) != 0) {
                        c = 0;
                        valueTo = typedArray.getColor(i4, 0);
                    } else {
                        c = 0;
                        valueTo = typedArray.getInt(i4, 0);
                    }
                    int[] iArr2 = new int[1];
                    iArr2[c] = valueTo;
                    returnValue2 = PropertyValuesHolder.ofInt(str, iArr2);
                } else {
                    returnValue2 = null;
                }
            } else if (hasFrom) {
                if (fromType == 5) {
                    valueFrom2 = typedArray.getDimension(i, 0.0f);
                } else {
                    valueFrom2 = typedArray.getFloat(i, 0.0f);
                }
                if (hasTo) {
                    if (toType3 == 5) {
                        valueTo4 = typedArray.getDimension(valueToId, 0.0f);
                    } else {
                        valueTo4 = typedArray.getFloat(valueToId, 0.0f);
                    }
                    returnValue2 = PropertyValuesHolder.ofFloat(str, valueFrom2, valueTo4);
                } else {
                    returnValue2 = PropertyValuesHolder.ofFloat(str, valueFrom2);
                }
            } else {
                int i5 = valueToId;
                if (toType3 == 5) {
                    valueTo3 = typedArray.getDimension(i5, 0.0f);
                } else {
                    valueTo3 = typedArray.getFloat(i5, 0.0f);
                }
                returnValue2 = PropertyValuesHolder.ofFloat(str, valueTo3);
            }
            if (!(returnValue == null || evaluator3 == null)) {
                returnValue.setEvaluator(evaluator3);
            }
        }
        return returnValue;
    }

    private static void parseAnimatorFromTypeArray(ValueAnimator anim, TypedArray arrayAnimator, TypedArray arrayObjectAnimator, float pixelSize, XmlPullParser parser) {
        long duration = (long) TypedArrayUtils.getNamedInt(arrayAnimator, parser, "duration", 1, ClientAnalytics.LogRequest.LogSource.CHIME_VALUE);
        long startDelay = (long) TypedArrayUtils.getNamedInt(arrayAnimator, parser, "startOffset", 2, 0);
        int valueType = TypedArrayUtils.getNamedInt(arrayAnimator, parser, "valueType", 7, 4);
        if (TypedArrayUtils.hasAttribute(parser, "valueFrom") && TypedArrayUtils.hasAttribute(parser, "valueTo")) {
            if (valueType == 4) {
                valueType = inferValueTypeFromValues(arrayAnimator, 5, 6);
            }
            PropertyValuesHolder pvh = getPVH(arrayAnimator, valueType, 5, 6, "");
            if (pvh != null) {
                anim.setValues(pvh);
            }
        }
        anim.setDuration(duration);
        anim.setStartDelay(startDelay);
        anim.setRepeatCount(TypedArrayUtils.getNamedInt(arrayAnimator, parser, "repeatCount", 3, 0));
        anim.setRepeatMode(TypedArrayUtils.getNamedInt(arrayAnimator, parser, "repeatMode", 4, 1));
        if (arrayObjectAnimator != null) {
            setupObjectAnimator(anim, arrayObjectAnimator, valueType, pixelSize, parser);
        }
    }

    private static void setupObjectAnimator(ValueAnimator anim, TypedArray arrayObjectAnimator, int valueType, float pixelSize, XmlPullParser parser) {
        ObjectAnimator oa = (ObjectAnimator) anim;
        String pathData = TypedArrayUtils.getNamedString(arrayObjectAnimator, parser, "pathData", 1);
        if (pathData != null) {
            String propertyXName = TypedArrayUtils.getNamedString(arrayObjectAnimator, parser, "propertyXName", 2);
            String propertyYName = TypedArrayUtils.getNamedString(arrayObjectAnimator, parser, "propertyYName", 3);
            if (valueType == 2 || valueType == 4) {
            }
            if (propertyXName == null && propertyYName == null) {
                throw new InflateException(arrayObjectAnimator.getPositionDescription() + " propertyXName or propertyYName is needed for PathData");
            }
            setupPathMotion(PathParser.createPathFromPathData(pathData), oa, 0.5f * pixelSize, propertyXName, propertyYName);
            return;
        }
        oa.setPropertyName(TypedArrayUtils.getNamedString(arrayObjectAnimator, parser, "propertyName", 0));
    }

    private static void setupPathMotion(Path path, ObjectAnimator oa, float precision, String propertyXName, String propertyYName) {
        Path path2 = path;
        ObjectAnimator objectAnimator = oa;
        String str = propertyXName;
        String str2 = propertyYName;
        boolean z = false;
        PathMeasure measureForTotalLength = new PathMeasure(path2, false);
        float totalLength = 0.0f;
        ArrayList<Float> contourLengths = new ArrayList<>();
        contourLengths.add(Float.valueOf(0.0f));
        while (true) {
            totalLength += measureForTotalLength.getLength();
            contourLengths.add(Float.valueOf(totalLength));
            if (!measureForTotalLength.nextContour()) {
                break;
            }
            z = false;
            path2 = path;
        }
        PathMeasure pathMeasure = new PathMeasure(path2, z);
        int numPoints = Math.min(100, ((int) (totalLength / precision)) + 1);
        float[] mX = new float[numPoints];
        float[] mY = new float[numPoints];
        float[] position = new float[2];
        float step = totalLength / ((float) (numPoints - 1));
        float currentDistance = 0.0f;
        int contourIndex = 0;
        for (int i = 0; i < numPoints; i++) {
            pathMeasure.getPosTan(currentDistance - ((Float) contourLengths.get(contourIndex)).floatValue(), position, null);
            mX[i] = position[0];
            mY[i] = position[1];
            currentDistance += step;
            if (contourIndex + 1 < contourLengths.size() && currentDistance > ((Float) contourLengths.get(contourIndex + 1)).floatValue()) {
                contourIndex++;
                pathMeasure.nextContour();
            }
        }
        PropertyValuesHolder x = null;
        PropertyValuesHolder y = null;
        if (str != null) {
            x = PropertyValuesHolder.ofFloat(str, mX);
        }
        if (str2 != null) {
            y = PropertyValuesHolder.ofFloat(str2, mY);
        }
        if (x == null) {
            objectAnimator.setValues(y);
        } else if (y == null) {
            objectAnimator.setValues(x);
        } else {
            objectAnimator.setValues(x, y);
        }
    }

    private static Animator createAnimatorFromXml(Context context, Resources res, Resources.Theme theme, XmlPullParser parser, float pixelSize) throws XmlPullParserException, IOException {
        return createAnimatorFromXml(context, res, theme, parser, Xml.asAttributeSet(parser), null, 0, pixelSize);
    }

    private static Animator createAnimatorFromXml(Context context, Resources res, Resources.Theme theme, XmlPullParser parser, AttributeSet attrs, AnimatorSet parent, int sequenceOrdering, float pixelSize) throws XmlPullParserException, IOException {
        Resources resources = res;
        Resources.Theme theme2 = theme;
        XmlPullParser xmlPullParser = parser;
        AnimatorSet animatorSet = parent;
        int depth = parser.getDepth();
        Animator anim = null;
        ArrayList<Animator> childAnims = null;
        while (true) {
            int next = parser.next();
            int type = next;
            if (next != 3 || parser.getDepth() > depth) {
                if (type == 1) {
                    break;
                } else if (type == 2) {
                    String name = parser.getName();
                    boolean gotValues = false;
                    if (name.equals("objectAnimator")) {
                        anim = loadObjectAnimator(context, res, theme, attrs, pixelSize, parser);
                    } else if (name.equals("animator")) {
                        anim = loadAnimator(context, res, theme, attrs, null, pixelSize, parser);
                    } else if (name.equals("set")) {
                        Animator anim2 = new AnimatorSet();
                        TypedArray a = TypedArrayUtils.obtainAttributes(resources, theme2, attrs, AndroidResources.STYLEABLE_ANIMATOR_SET);
                        createAnimatorFromXml(context, res, theme, parser, attrs, (AnimatorSet) anim2, TypedArrayUtils.getNamedInt(a, xmlPullParser, "ordering", 0, 0), pixelSize);
                        a.recycle();
                        anim = anim2;
                    } else if (name.equals("propertyValuesHolder")) {
                        PropertyValuesHolder[] values = loadValues(context, resources, theme2, xmlPullParser, Xml.asAttributeSet(parser));
                        if (values != null && (anim instanceof ValueAnimator)) {
                            ((ValueAnimator) anim).setValues(values);
                        }
                        gotValues = true;
                    } else {
                        throw new RuntimeException("Unknown animator name: " + parser.getName());
                    }
                    if (animatorSet != null && !gotValues) {
                        if (childAnims == null) {
                            childAnims = new ArrayList<>();
                        }
                        childAnims.add(anim);
                    }
                }
            } else {
                break;
            }
        }
        if (!(animatorSet == null || childAnims == null)) {
            Animator[] animsArray = new Animator[childAnims.size()];
            int index = 0;
            Iterator it = childAnims.iterator();
            while (it.hasNext()) {
                animsArray[index] = (Animator) it.next();
                index++;
            }
            if (sequenceOrdering == 0) {
                animatorSet.playTogether(animsArray);
            } else {
                animatorSet.playSequentially(animsArray);
            }
        }
        return anim;
    }

    private static PropertyValuesHolder[] loadValues(Context context, Resources res, Resources.Theme theme, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = parser;
        ArrayList<PropertyValuesHolder> values = null;
        while (true) {
            int eventType = parser.getEventType();
            int type = eventType;
            if (eventType == 3 || type == 1) {
                PropertyValuesHolder[] valuesArray = null;
            } else if (type != 2) {
                parser.next();
            } else {
                if (parser.getName().equals("propertyValuesHolder")) {
                    TypedArray a = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_PROPERTY_VALUES_HOLDER);
                    String propertyName = TypedArrayUtils.getNamedString(a, xmlPullParser, "propertyName", 3);
                    int valueType = TypedArrayUtils.getNamedInt(a, xmlPullParser, "valueType", 2, 4);
                    int valueType2 = valueType;
                    PropertyValuesHolder pvh = loadPvh(context, res, theme, parser, propertyName, valueType);
                    if (pvh == null) {
                        pvh = getPVH(a, valueType2, 0, 1, propertyName);
                    }
                    if (pvh != null) {
                        if (values == null) {
                            values = new ArrayList<>();
                        }
                        values.add(pvh);
                    }
                    a.recycle();
                }
                parser.next();
            }
        }
        PropertyValuesHolder[] valuesArray2 = null;
        if (values != null) {
            int count = values.size();
            valuesArray2 = new PropertyValuesHolder[count];
            for (int i = 0; i < count; i++) {
                valuesArray2[i] = (PropertyValuesHolder) values.get(i);
            }
        }
        return valuesArray2;
    }

    private static int inferValueTypeOfKeyframe(Resources res, Resources.Theme theme, AttributeSet attrs, XmlPullParser parser) {
        int valueType;
        TypedArray a = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_KEYFRAME);
        boolean hasValue = false;
        TypedValue keyframeValue = TypedArrayUtils.peekNamedValue(a, parser, "value", 0);
        if (keyframeValue != null) {
            hasValue = true;
        }
        if (!hasValue || !isColorType(keyframeValue.type)) {
            valueType = 0;
        } else {
            valueType = 3;
        }
        a.recycle();
        return valueType;
    }

    private static int inferValueTypeFromValues(TypedArray styledAttributes, int valueFromId, int valueToId) {
        TypedValue tvFrom = styledAttributes.peekValue(valueFromId);
        boolean hasTo = true;
        int toType = 0;
        boolean hasFrom = tvFrom != null;
        int fromType = hasFrom ? tvFrom.type : 0;
        TypedValue tvTo = styledAttributes.peekValue(valueToId);
        if (tvTo == null) {
            hasTo = false;
        }
        if (hasTo) {
            toType = tvTo.type;
        }
        if ((!hasFrom || !isColorType(fromType)) && (!hasTo || !isColorType(toType))) {
            return 0;
        }
        return 3;
    }

    private static void dumpKeyframes(Object[] keyframes, String header) {
        if (keyframes != null && keyframes.length != 0) {
            Log.d(TAG, header);
            int count = keyframes.length;
            for (int i = 0; i < count; i++) {
                Keyframe keyframe = (Keyframe) keyframes[i];
                StringBuilder sb = new StringBuilder();
                sb.append("Keyframe ");
                sb.append(i);
                sb.append(": fraction ");
                Object obj = "null";
                sb.append(keyframe.getFraction() < 0.0f ? obj : Float.valueOf(keyframe.getFraction()));
                sb.append(", , value : ");
                if (keyframe.hasValue()) {
                    obj = keyframe.getValue();
                }
                sb.append(obj);
                Log.d(TAG, sb.toString());
            }
        }
    }

    /* JADX INFO: Multiple debug info for r1v2 android.animation.PropertyValuesHolder: [D('keyframes' java.util.ArrayList<android.animation.Keyframe>), D('value' android.animation.PropertyValuesHolder)] */
    /* JADX INFO: Multiple debug info for r18v6 'type'  int: [D('type' int), D('endIndex' int)] */
    private static PropertyValuesHolder loadPvh(Context context, Resources res, Resources.Theme theme, XmlPullParser parser, String propertyName, int valueType) throws XmlPullParserException, IOException {
        int type;
        PropertyValuesHolder value;
        PropertyValuesHolder value2;
        int type2;
        ArrayList<Keyframe> keyframes;
        PropertyValuesHolder value3 = null;
        ArrayList<Keyframe> keyframes2 = null;
        int valueType2 = valueType;
        while (true) {
            int next = parser.next();
            type = next;
            if (next != 3 && type != 1) {
                if (parser.getName().equals("keyframe")) {
                    if (valueType2 == 4) {
                        valueType2 = inferValueTypeOfKeyframe(res, theme, Xml.asAttributeSet(parser), parser);
                    }
                    Keyframe keyframe = loadKeyframe(context, res, theme, Xml.asAttributeSet(parser), valueType2, parser);
                    if (keyframe != null) {
                        if (keyframes2 == null) {
                            keyframes2 = new ArrayList<>();
                        }
                        keyframes2.add(keyframe);
                    }
                    parser.next();
                }
            }
        }
        if (keyframes2 != null) {
            int size = keyframes2.size();
            int count = size;
            if (size > 0) {
                Keyframe firstKeyframe = (Keyframe) keyframes2.get(0);
                Keyframe lastKeyframe = (Keyframe) keyframes2.get(count - 1);
                float endFraction = lastKeyframe.getFraction();
                float f = 0.0f;
                if (endFraction < 1.0f) {
                    if (endFraction < 0.0f) {
                        lastKeyframe.setFraction(1.0f);
                    } else {
                        keyframes2.add(keyframes2.size(), createNewKeyframe(lastKeyframe, 1.0f));
                        count++;
                    }
                }
                float startFraction = firstKeyframe.getFraction();
                if (startFraction != 0.0f) {
                    if (startFraction < 0.0f) {
                        firstKeyframe.setFraction(0.0f);
                    } else {
                        keyframes2.add(0, createNewKeyframe(firstKeyframe, 0.0f));
                        count++;
                    }
                }
                Keyframe[] keyframeArray = new Keyframe[count];
                keyframes2.toArray(keyframeArray);
                int i = 0;
                while (i < count) {
                    Keyframe keyframe2 = keyframeArray[i];
                    if (keyframe2.getFraction() >= f) {
                        value2 = value3;
                        keyframes = keyframes2;
                        type2 = type;
                    } else if (i == 0) {
                        keyframe2.setFraction(f);
                        value2 = value3;
                        keyframes = keyframes2;
                        type2 = type;
                    } else if (i == count - 1) {
                        keyframe2.setFraction(1.0f);
                        value2 = value3;
                        keyframes = keyframes2;
                        type2 = type;
                    } else {
                        int startIndex = i;
                        value2 = value3;
                        int j = startIndex + 1;
                        keyframes = keyframes2;
                        int endIndex = i;
                        while (true) {
                            type2 = type;
                            if (j >= count - 1) {
                                break;
                            } else if (keyframeArray[j].getFraction() >= 0.0f) {
                                break;
                            } else {
                                endIndex = j;
                                j++;
                                type = type2;
                            }
                        }
                        distributeKeyframes(keyframeArray, keyframeArray[endIndex + 1].getFraction() - keyframeArray[startIndex - 1].getFraction(), startIndex, endIndex);
                    }
                    i++;
                    keyframes2 = keyframes;
                    type = type2;
                    value3 = value2;
                    f = 0.0f;
                }
                PropertyValuesHolder value4 = PropertyValuesHolder.ofKeyframe(propertyName, keyframeArray);
                if (valueType2 != 3) {
                    return value4;
                }
                value4.setEvaluator(ArgbEvaluator.getInstance());
                return value4;
            }
            value = null;
        } else {
            value = null;
        }
        return value;
    }

    private static Keyframe createNewKeyframe(Keyframe sampleKeyframe, float fraction) {
        if (sampleKeyframe.getType() == Float.TYPE) {
            return Keyframe.ofFloat(fraction);
        }
        if (sampleKeyframe.getType() == Integer.TYPE) {
            return Keyframe.ofInt(fraction);
        }
        return Keyframe.ofObject(fraction);
    }

    private static void distributeKeyframes(Keyframe[] keyframes, float gap, int startIndex, int endIndex) {
        float increment = gap / ((float) ((endIndex - startIndex) + 2));
        for (int i = startIndex; i <= endIndex; i++) {
            keyframes[i].setFraction(keyframes[i - 1].getFraction() + increment);
        }
    }

    private static Keyframe loadKeyframe(Context context, Resources res, Resources.Theme theme, AttributeSet attrs, int valueType, XmlPullParser parser) throws XmlPullParserException, IOException {
        Keyframe keyframe;
        TypedArray a = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_KEYFRAME);
        Keyframe keyframe2 = null;
        float fraction = TypedArrayUtils.getNamedFloat(a, parser, "fraction", 3, -1.0f);
        TypedValue keyframeValue = TypedArrayUtils.peekNamedValue(a, parser, "value", 0);
        boolean hasValue = keyframeValue != null;
        if (valueType == 4) {
            if (!hasValue || !isColorType(keyframeValue.type)) {
                valueType = 0;
            } else {
                valueType = 3;
            }
        }
        if (!hasValue) {
            if (valueType == 0) {
                keyframe = Keyframe.ofFloat(fraction);
            } else {
                keyframe = Keyframe.ofInt(fraction);
            }
            keyframe2 = keyframe;
        } else if (valueType == 0) {
            keyframe2 = Keyframe.ofFloat(fraction, TypedArrayUtils.getNamedFloat(a, parser, "value", 0, 0.0f));
        } else if (valueType == 1 || valueType == 3) {
            keyframe2 = Keyframe.ofInt(fraction, TypedArrayUtils.getNamedInt(a, parser, "value", 0, 0));
        }
        int resID = TypedArrayUtils.getNamedResourceId(a, parser, "interpolator", 1, 0);
        if (resID > 0) {
            keyframe2.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, resID));
        }
        a.recycle();
        return keyframe2;
    }

    private static ObjectAnimator loadObjectAnimator(Context context, Resources res, Resources.Theme theme, AttributeSet attrs, float pathErrorScale, XmlPullParser parser) throws Resources.NotFoundException {
        ObjectAnimator anim = new ObjectAnimator();
        loadAnimator(context, res, theme, attrs, anim, pathErrorScale, parser);
        return anim;
    }

    private static ValueAnimator loadAnimator(Context context, Resources res, Resources.Theme theme, AttributeSet attrs, ValueAnimator anim, float pathErrorScale, XmlPullParser parser) throws Resources.NotFoundException {
        TypedArray arrayAnimator = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_ANIMATOR);
        TypedArray arrayObjectAnimator = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_PROPERTY_ANIMATOR);
        if (anim == null) {
            anim = new ValueAnimator();
        }
        parseAnimatorFromTypeArray(anim, arrayAnimator, arrayObjectAnimator, pathErrorScale, parser);
        int resID = TypedArrayUtils.getNamedResourceId(arrayAnimator, parser, "interpolator", 0, 0);
        if (resID > 0) {
            anim.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, resID));
        }
        arrayAnimator.recycle();
        if (arrayObjectAnimator != null) {
            arrayObjectAnimator.recycle();
        }
        return anim;
    }

    private static boolean isColorType(int type) {
        return type >= 28 && type <= 31;
    }

    private static class PathDataEvaluator implements TypeEvaluator<PathParser.PathDataNode[]> {
        private PathParser.PathDataNode[] mNodeArray;

        PathDataEvaluator() {
        }

        PathDataEvaluator(PathParser.PathDataNode[] nodeArray) {
            this.mNodeArray = nodeArray;
        }

        public PathParser.PathDataNode[] evaluate(float fraction, PathParser.PathDataNode[] startPathData, PathParser.PathDataNode[] endPathData) {
            if (PathParser.canMorph(startPathData, endPathData)) {
                if (!PathParser.canMorph(this.mNodeArray, startPathData)) {
                    this.mNodeArray = PathParser.deepCopyNodes(startPathData);
                }
                for (int i = 0; i < startPathData.length; i++) {
                    this.mNodeArray[i].interpolatePathDataNode(startPathData[i], endPathData[i], fraction);
                }
                return this.mNodeArray;
            }
            throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
        }
    }
}
