package android.support.p001v4.graphics;

import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

/* renamed from: android.support.v4.graphics.PathParser */
public class PathParser {
    private static final String LOGTAG = "PathParser";

    private PathParser() {
    }

    static float[] copyOfRange(float[] original, int start, int end) {
        if (start <= end) {
            int originalLength = original.length;
            if (start < 0 || start > originalLength) {
                throw new ArrayIndexOutOfBoundsException();
            }
            int resultLength = end - start;
            float[] result = new float[resultLength];
            System.arraycopy(original, start, result, 0, Math.min(resultLength, originalLength - start));
            return result;
        }
        throw new IllegalArgumentException();
    }

    public static Path createPathFromPathData(String pathData) {
        Path path = new Path();
        PathDataNode[] nodes = createNodesFromPathData(pathData);
        if (nodes == null) {
            return null;
        }
        try {
            PathDataNode.nodesToPath(nodes, path);
            return path;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error in parsing " + pathData, e);
        }
    }

    public static PathDataNode[] createNodesFromPathData(String pathData) {
        if (pathData == null) {
            return null;
        }
        int start = 0;
        int end = 1;
        ArrayList<PathDataNode> list = new ArrayList<>();
        while (end < pathData.length()) {
            int end2 = nextStart(pathData, end);
            String s = pathData.substring(start, end2).trim();
            if (s.length() > 0) {
                addNode(list, s.charAt(0), getFloats(s));
            }
            start = end2;
            end = end2 + 1;
        }
        if (end - start == 1 && start < pathData.length()) {
            addNode(list, pathData.charAt(start), new float[0]);
        }
        return (PathDataNode[]) list.toArray(new PathDataNode[list.size()]);
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] source) {
        if (source == null) {
            return null;
        }
        PathDataNode[] copy = new PathDataNode[source.length];
        for (int i = 0; i < source.length; i++) {
            copy[i] = new PathDataNode(source[i]);
        }
        return copy;
    }

    public static boolean canMorph(@Nullable PathDataNode[] nodesFrom, @Nullable PathDataNode[] nodesTo) {
        if (nodesFrom == null || nodesTo == null || nodesFrom.length != nodesTo.length) {
            return false;
        }
        for (int i = 0; i < nodesFrom.length; i++) {
            if (nodesFrom[i].mType != nodesTo[i].mType || nodesFrom[i].mParams.length != nodesTo[i].mParams.length) {
                return false;
            }
        }
        return true;
    }

    public static void updateNodes(PathDataNode[] target, PathDataNode[] source) {
        for (int i = 0; i < source.length; i++) {
            target[i].mType = source[i].mType;
            for (int j = 0; j < source[i].mParams.length; j++) {
                target[i].mParams[j] = source[i].mParams[j];
            }
        }
    }

    private static int nextStart(String s, int end) {
        while (end < s.length()) {
            char c = s.charAt(end);
            if (((c - 'A') * (c - 'Z') <= 0 || (c - 'a') * (c - 'z') <= 0) && c != 'e' && c != 'E') {
                return end;
            }
            end++;
        }
        return end;
    }

    private static void addNode(ArrayList<PathDataNode> list, char cmd, float[] val) {
        list.add(new PathDataNode(cmd, val));
    }

    private static float[] getFloats(String s) {
        if (s.charAt(0) == 'z' || s.charAt(0) == 'Z') {
            return new float[0];
        }
        try {
            float[] results = new float[s.length()];
            int count = 0;
            int startPosition = 1;
            ExtractFloatResult result = new ExtractFloatResult();
            int totalLength = s.length();
            while (startPosition < totalLength) {
                extract(s, startPosition, result);
                int endPosition = result.mEndPosition;
                if (startPosition < endPosition) {
                    results[count] = Float.parseFloat(s.substring(startPosition, endPosition));
                    count++;
                }
                if (result.mEndWithNegOrDot != 0) {
                    startPosition = endPosition;
                } else {
                    startPosition = endPosition + 1;
                }
            }
            return copyOfRange(results, 0, count);
        } catch (NumberFormatException e) {
            throw new RuntimeException("error in parsing \"" + s + "\"", e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x003b A[LOOP:0: B:1:0x0007->B:20:0x003b, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003e A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void extract(java.lang.String r7, int r8, android.support.p001v4.graphics.PathParser.ExtractFloatResult r9) {
        /*
            r0 = r8
            r1 = 0
            r2 = 0
            r9.mEndWithNegOrDot = r2
            r2 = 0
            r3 = 0
        L_0x0007:
            int r4 = r7.length()
            if (r0 >= r4) goto L_0x003e
            r4 = r3
            r3 = 0
            char r5 = r7.charAt(r0)
            r6 = 32
            if (r5 == r6) goto L_0x0036
            r6 = 69
            if (r5 == r6) goto L_0x0034
            r6 = 101(0x65, float:1.42E-43)
            if (r5 == r6) goto L_0x0034
            r6 = 1
            switch(r5) {
                case 44: goto L_0x0036;
                case 45: goto L_0x002c;
                case 46: goto L_0x0024;
                default: goto L_0x0023;
            }
        L_0x0023:
            goto L_0x0038
        L_0x0024:
            if (r2 != 0) goto L_0x0028
            r2 = 1
            goto L_0x0038
        L_0x0028:
            r1 = 1
            r9.mEndWithNegOrDot = r6
            goto L_0x0038
        L_0x002c:
            if (r0 == r8) goto L_0x0038
            if (r4 != 0) goto L_0x0038
            r1 = 1
            r9.mEndWithNegOrDot = r6
            goto L_0x0038
        L_0x0034:
            r3 = 1
            goto L_0x0038
        L_0x0036:
            r1 = 1
        L_0x0038:
            if (r1 == 0) goto L_0x003b
            goto L_0x003e
        L_0x003b:
            int r0 = r0 + 1
            goto L_0x0007
        L_0x003e:
            r9.mEndPosition = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p001v4.graphics.PathParser.extract(java.lang.String, int, android.support.v4.graphics.PathParser$ExtractFloatResult):void");
    }

    public static boolean interpolatePathDataNodes(PathDataNode[] target, PathDataNode[] from, PathDataNode[] to, float fraction) {
        if (target == null || from == null || to == null) {
            throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes cannot be null");
        } else if (target.length != from.length || from.length != to.length) {
            throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes must have the same length");
        } else if (!canMorph(from, to)) {
            return false;
        } else {
            for (int i = 0; i < target.length; i++) {
                target[i].interpolatePathDataNode(from[i], to[i], fraction);
            }
            return true;
        }
    }

    /* renamed from: android.support.v4.graphics.PathParser$ExtractFloatResult */
    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;

        ExtractFloatResult() {
        }
    }

    /* renamed from: android.support.v4.graphics.PathParser$PathDataNode */
    public static class PathDataNode {
        public float[] mParams;
        public char mType;

        PathDataNode(char type, float[] params) {
            this.mType = type;
            this.mParams = params;
        }

        PathDataNode(PathDataNode n) {
            this.mType = n.mType;
            float[] fArr = n.mParams;
            this.mParams = PathParser.copyOfRange(fArr, 0, fArr.length);
        }

        public static void nodesToPath(PathDataNode[] node, Path path) {
            float[] current = new float[6];
            char previousCommand = 'm';
            for (int i = 0; i < node.length; i++) {
                addCommand(path, current, previousCommand, node[i].mType, node[i].mParams);
                previousCommand = node[i].mType;
            }
        }

        private static void addCommand(Path path, float[] current, char previousCmd, char cmd, float[] val) {
            int incr;
            int k;
            float reflectiveCtrlPointY;
            float reflectiveCtrlPointX;
            float reflectiveCtrlPointY2;
            float reflectiveCtrlPointX2;
            Path path2 = path;
            char c = cmd;
            float[] fArr = val;
            float currentX = current[0];
            float currentY = current[1];
            float ctrlPointX = current[2];
            float ctrlPointY = current[3];
            float currentSegmentStartX = current[4];
            float currentSegmentStartY = current[5];
            switch (c) {
                case 'A':
                case 'a':
                    incr = 7;
                    break;
                case 'C':
                case 'c':
                    incr = 6;
                    break;
                case 'H':
                case 'V':
                case 'h':
                case 'v':
                    incr = 1;
                    break;
                case 'L':
                case 'M':
                case 'T':
                case 'l':
                case 'm':
                case 't':
                    incr = 2;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    incr = 4;
                    break;
                case 'Z':
                case 'z':
                    path.close();
                    currentX = currentSegmentStartX;
                    currentY = currentSegmentStartY;
                    ctrlPointX = currentSegmentStartX;
                    ctrlPointY = currentSegmentStartY;
                    path2.moveTo(currentX, currentY);
                    incr = 2;
                    break;
                default:
                    incr = 2;
                    break;
            }
            int k2 = 0;
            float currentX2 = currentX;
            float currentY2 = currentY;
            float ctrlPointX2 = ctrlPointX;
            float ctrlPointY2 = ctrlPointY;
            float currentSegmentStartX2 = currentSegmentStartX;
            float currentSegmentStartY2 = currentSegmentStartY;
            char previousCmd2 = previousCmd;
            while (k2 < fArr.length) {
                if (c == 'A') {
                    k = k2;
                    drawArc(path, currentX2, currentY2, fArr[k + 5], fArr[k + 6], fArr[k + 0], fArr[k + 1], fArr[k + 2], fArr[k + 3] != 0.0f, fArr[k + 4] != 0.0f);
                    currentX2 = fArr[k + 5];
                    currentY2 = fArr[k + 6];
                    ctrlPointX2 = currentX2;
                    ctrlPointY2 = currentY2;
                } else if (c == 'C') {
                    k = k2;
                    path.cubicTo(fArr[k + 0], fArr[k + 1], fArr[k + 2], fArr[k + 3], fArr[k + 4], fArr[k + 5]);
                    currentX2 = fArr[k + 4];
                    currentY2 = fArr[k + 5];
                    ctrlPointX2 = fArr[k + 2];
                    ctrlPointY2 = fArr[k + 3];
                } else if (c == 'H') {
                    k = k2;
                    path2.lineTo(fArr[k + 0], currentY2);
                    currentX2 = fArr[k + 0];
                } else if (c == 'Q') {
                    k = k2;
                    path2.quadTo(fArr[k + 0], fArr[k + 1], fArr[k + 2], fArr[k + 3]);
                    float ctrlPointX3 = fArr[k + 0];
                    float ctrlPointY3 = fArr[k + 1];
                    currentX2 = fArr[k + 2];
                    currentY2 = fArr[k + 3];
                    ctrlPointX2 = ctrlPointX3;
                    ctrlPointY2 = ctrlPointY3;
                } else if (c == 'V') {
                    k = k2;
                    path2.lineTo(currentX2, fArr[k + 0]);
                    currentY2 = fArr[k + 0];
                } else if (c == 'a') {
                    k = k2;
                    drawArc(path, currentX2, currentY2, fArr[k2 + 5] + currentX2, fArr[k2 + 6] + currentY2, fArr[k2 + 0], fArr[k2 + 1], fArr[k2 + 2], fArr[k2 + 3] != 0.0f, fArr[k2 + 4] != 0.0f);
                    currentX2 += fArr[k + 5];
                    currentY2 += fArr[k + 6];
                    ctrlPointX2 = currentX2;
                    ctrlPointY2 = currentY2;
                } else if (c == 'c') {
                    path.rCubicTo(fArr[k2 + 0], fArr[k2 + 1], fArr[k2 + 2], fArr[k2 + 3], fArr[k2 + 4], fArr[k2 + 5]);
                    float ctrlPointX4 = fArr[k2 + 2] + currentX2;
                    float ctrlPointY4 = fArr[k2 + 3] + currentY2;
                    currentX2 += fArr[k2 + 4];
                    currentY2 += fArr[k2 + 5];
                    ctrlPointX2 = ctrlPointX4;
                    ctrlPointY2 = ctrlPointY4;
                    k = k2;
                } else if (c == 'h') {
                    path2.rLineTo(fArr[k2 + 0], 0.0f);
                    currentX2 += fArr[k2 + 0];
                    k = k2;
                } else if (c == 'q') {
                    path2.rQuadTo(fArr[k2 + 0], fArr[k2 + 1], fArr[k2 + 2], fArr[k2 + 3]);
                    float ctrlPointX5 = fArr[k2 + 0] + currentX2;
                    float ctrlPointY5 = fArr[k2 + 1] + currentY2;
                    currentX2 += fArr[k2 + 2];
                    currentY2 += fArr[k2 + 3];
                    ctrlPointX2 = ctrlPointX5;
                    ctrlPointY2 = ctrlPointY5;
                    k = k2;
                } else if (c == 'v') {
                    path2.rLineTo(0.0f, fArr[k2 + 0]);
                    currentY2 += fArr[k2 + 0];
                    k = k2;
                } else if (c == 'L') {
                    path2.lineTo(fArr[k2 + 0], fArr[k2 + 1]);
                    currentX2 = fArr[k2 + 0];
                    currentY2 = fArr[k2 + 1];
                    k = k2;
                } else if (c == 'M') {
                    currentX2 = fArr[k2 + 0];
                    currentY2 = fArr[k2 + 1];
                    if (k2 > 0) {
                        path2.lineTo(fArr[k2 + 0], fArr[k2 + 1]);
                        k = k2;
                    } else {
                        path2.moveTo(fArr[k2 + 0], fArr[k2 + 1]);
                        currentSegmentStartX2 = currentX2;
                        currentSegmentStartY2 = currentY2;
                        k = k2;
                    }
                } else if (c == 'S') {
                    char previousCmd3 = previousCmd2;
                    float reflectiveCtrlPointX3 = currentX2;
                    float reflectiveCtrlPointY3 = currentY2;
                    if (previousCmd3 == 'c' || previousCmd3 == 's' || previousCmd3 == 'C' || previousCmd3 == 'S') {
                        reflectiveCtrlPointX = (currentX2 * 2.0f) - ctrlPointX2;
                        reflectiveCtrlPointY = (2.0f * currentY2) - ctrlPointY2;
                    } else {
                        reflectiveCtrlPointX = reflectiveCtrlPointX3;
                        reflectiveCtrlPointY = reflectiveCtrlPointY3;
                    }
                    path.cubicTo(reflectiveCtrlPointX, reflectiveCtrlPointY, fArr[k2 + 0], fArr[k2 + 1], fArr[k2 + 2], fArr[k2 + 3]);
                    float ctrlPointX6 = fArr[k2 + 0];
                    float ctrlPointY6 = fArr[k2 + 1];
                    currentX2 = fArr[k2 + 2];
                    currentY2 = fArr[k2 + 3];
                    ctrlPointX2 = ctrlPointX6;
                    ctrlPointY2 = ctrlPointY6;
                    k = k2;
                } else if (c == 'T') {
                    char previousCmd4 = previousCmd2;
                    float reflectiveCtrlPointX4 = currentX2;
                    float reflectiveCtrlPointY4 = currentY2;
                    if (previousCmd4 == 'q' || previousCmd4 == 't' || previousCmd4 == 'Q' || previousCmd4 == 'T') {
                        reflectiveCtrlPointX4 = (currentX2 * 2.0f) - ctrlPointX2;
                        reflectiveCtrlPointY4 = (2.0f * currentY2) - ctrlPointY2;
                    }
                    path2.quadTo(reflectiveCtrlPointX4, reflectiveCtrlPointY4, fArr[k2 + 0], fArr[k2 + 1]);
                    currentX2 = fArr[k2 + 0];
                    currentY2 = fArr[k2 + 1];
                    ctrlPointX2 = reflectiveCtrlPointX4;
                    ctrlPointY2 = reflectiveCtrlPointY4;
                    k = k2;
                } else if (c == 'l') {
                    path2.rLineTo(fArr[k2 + 0], fArr[k2 + 1]);
                    currentX2 += fArr[k2 + 0];
                    currentY2 += fArr[k2 + 1];
                    k = k2;
                } else if (c == 'm') {
                    currentX2 += fArr[k2 + 0];
                    currentY2 += fArr[k2 + 1];
                    if (k2 > 0) {
                        path2.rLineTo(fArr[k2 + 0], fArr[k2 + 1]);
                        k = k2;
                    } else {
                        path2.rMoveTo(fArr[k2 + 0], fArr[k2 + 1]);
                        currentSegmentStartX2 = currentX2;
                        currentSegmentStartY2 = currentY2;
                        k = k2;
                    }
                } else if (c == 's') {
                    if (previousCmd2 == 'c' || previousCmd2 == 's' || previousCmd2 == 'C' || previousCmd2 == 'S') {
                        reflectiveCtrlPointX2 = currentX2 - ctrlPointX2;
                        reflectiveCtrlPointY2 = currentY2 - ctrlPointY2;
                    } else {
                        reflectiveCtrlPointX2 = 0.0f;
                        reflectiveCtrlPointY2 = 0.0f;
                    }
                    path.rCubicTo(reflectiveCtrlPointX2, reflectiveCtrlPointY2, fArr[k2 + 0], fArr[k2 + 1], fArr[k2 + 2], fArr[k2 + 3]);
                    float ctrlPointX7 = fArr[k2 + 0] + currentX2;
                    float ctrlPointY7 = fArr[k2 + 1] + currentY2;
                    currentX2 += fArr[k2 + 2];
                    currentY2 += fArr[k2 + 3];
                    ctrlPointX2 = ctrlPointX7;
                    ctrlPointY2 = ctrlPointY7;
                    k = k2;
                } else if (c != 't') {
                    k = k2;
                } else {
                    float reflectiveCtrlPointX5 = 0.0f;
                    float reflectiveCtrlPointY5 = 0.0f;
                    if (previousCmd2 == 'q' || previousCmd2 == 't' || previousCmd2 == 'Q' || previousCmd2 == 'T') {
                        reflectiveCtrlPointX5 = currentX2 - ctrlPointX2;
                        reflectiveCtrlPointY5 = currentY2 - ctrlPointY2;
                    }
                    path2.rQuadTo(reflectiveCtrlPointX5, reflectiveCtrlPointY5, fArr[k2 + 0], fArr[k2 + 1]);
                    float ctrlPointX8 = currentX2 + reflectiveCtrlPointX5;
                    float ctrlPointY8 = currentY2 + reflectiveCtrlPointY5;
                    currentX2 += fArr[k2 + 0];
                    currentY2 += fArr[k2 + 1];
                    ctrlPointX2 = ctrlPointX8;
                    ctrlPointY2 = ctrlPointY8;
                    k = k2;
                }
                previousCmd2 = cmd;
                k2 = k + incr;
            }
            current[0] = currentX2;
            current[1] = currentY2;
            current[2] = ctrlPointX2;
            current[3] = ctrlPointY2;
            current[4] = currentSegmentStartX2;
            current[5] = currentSegmentStartY2;
        }

        private static void drawArc(Path p, float x0, float y0, float x1, float y1, float a, float b, float theta, boolean isMoreThanHalf, boolean isPositiveArc) {
            double cy;
            double cx;
            float f = x0;
            float f2 = y0;
            float f3 = x1;
            float f4 = y1;
            float f5 = a;
            float f6 = b;
            boolean z = isPositiveArc;
            double thetaD = Math.toRadians((double) theta);
            double cosTheta = Math.cos(thetaD);
            double sinTheta = Math.sin(thetaD);
            double d = (double) f;
            Double.isNaN(d);
            double d2 = (double) f2;
            Double.isNaN(d2);
            double d3 = (d * cosTheta) + (d2 * sinTheta);
            double d4 = (double) f5;
            Double.isNaN(d4);
            double x0p = d3 / d4;
            double d5 = (double) (-f);
            Double.isNaN(d5);
            double d6 = (double) f2;
            Double.isNaN(d6);
            double d7 = (d5 * sinTheta) + (d6 * cosTheta);
            double d8 = (double) f6;
            Double.isNaN(d8);
            double y0p = d7 / d8;
            double d9 = (double) f3;
            Double.isNaN(d9);
            double d10 = (double) f4;
            Double.isNaN(d10);
            double d11 = (d9 * cosTheta) + (d10 * sinTheta);
            double d12 = (double) f5;
            Double.isNaN(d12);
            double x1p = d11 / d12;
            double d13 = (double) (-f3);
            Double.isNaN(d13);
            double d14 = (double) f4;
            Double.isNaN(d14);
            double d15 = (d13 * sinTheta) + (d14 * cosTheta);
            double d16 = (double) f6;
            Double.isNaN(d16);
            double y1p = d15 / d16;
            double dx = x0p - x1p;
            double dy = y0p - y1p;
            double xm = (x0p + x1p) / 2.0d;
            double ym = (y0p + y1p) / 2.0d;
            double dsq = (dx * dx) + (dy * dy);
            if (dsq == 0.0d) {
                Log.w(PathParser.LOGTAG, " Points are coincident");
                return;
            }
            double disc = (1.0d / dsq) - 0.25d;
            if (disc < 0.0d) {
                Log.w(PathParser.LOGTAG, "Points are too far apart " + dsq);
                float adjust = (float) (Math.sqrt(dsq) / 1.99999d);
                drawArc(p, x0, y0, x1, y1, f5 * adjust, f6 * adjust, theta, isMoreThanHalf, isPositiveArc);
                return;
            }
            boolean z2 = z;
            double s = Math.sqrt(disc);
            double sdx = s * dx;
            double sdy = s * dy;
            if (isMoreThanHalf == z2) {
                cx = xm - sdy;
                cy = ym + sdx;
            } else {
                cx = xm + sdy;
                cy = ym - sdx;
            }
            double eta0 = Math.atan2(y0p - cy, x0p - cx);
            double sweep = Math.atan2(y1p - cy, x1p - cx) - eta0;
            if (z2 != (sweep >= 0.0d)) {
                if (sweep > 0.0d) {
                    sweep -= 6.283185307179586d;
                } else {
                    sweep += 6.283185307179586d;
                }
            }
            double eta1 = (double) f5;
            Double.isNaN(eta1);
            double cx2 = cx * eta1;
            double d17 = (double) f6;
            Double.isNaN(d17);
            double cy2 = d17 * cy;
            arcToBezier(p, (cx2 * cosTheta) - (cy2 * sinTheta), (cx2 * sinTheta) + (cy2 * cosTheta), (double) f5, (double) f6, (double) f, (double) f2, thetaD, eta0, sweep);
        }

        /* JADX INFO: Multiple debug info for r2v12 double: [D('anglePerSegment' double), D('e2x' double)] */
        /* JADX INFO: Multiple debug info for r11v2 double: [D('e2y' double), D('cosEta1' double)] */
        /* JADX INFO: Multiple debug info for r6v2 double: [D('numSegments' int), D('q1y' double)] */
        /* JADX INFO: Multiple debug info for r4v10 double: [D('ep2y' double), D('q2y' double)] */
        private static void arcToBezier(Path p, double cx, double cy, double a, double b, double e1x, double e1y, double theta, double start, double sweep) {
            double d = a;
            int numSegments = (int) Math.ceil(Math.abs((sweep * 4.0d) / 3.141592653589793d));
            double eta1 = start;
            double cosTheta = Math.cos(theta);
            double sinTheta = Math.sin(theta);
            double cosEta1 = Math.cos(eta1);
            double sinEta1 = Math.sin(eta1);
            double ep1y = (double) numSegments;
            Double.isNaN(ep1y);
            double anglePerSegment = sweep / ep1y;
            double ep1x = (((-d) * cosTheta) * sinEta1) - ((b * sinTheta) * cosEta1);
            double ep1y2 = ((-d) * sinTheta * sinEta1) + (b * cosTheta * cosEta1);
            double e1x2 = e1x;
            double e1y2 = e1y;
            double d2 = eta1;
            int i = 0;
            double eta12 = d2;
            while (i < numSegments) {
                double eta2 = eta12 + anglePerSegment;
                double sinEta2 = Math.sin(eta2);
                double cosEta2 = Math.cos(eta2);
                double anglePerSegment2 = anglePerSegment;
                double e2x = (cx + ((d * cosTheta) * cosEta2)) - ((b * sinTheta) * sinEta2);
                double cosEta12 = cosEta1;
                double cosEta13 = cy + (d * sinTheta * cosEta2) + (b * cosTheta * sinEta2);
                double sinEta12 = sinEta1;
                double ep2x = (((-d) * cosTheta) * sinEta2) - ((b * sinTheta) * cosEta2);
                int numSegments2 = numSegments;
                double ep2y = ((-d) * sinTheta * sinEta2) + (b * cosTheta * cosEta2);
                double tanDiff2 = Math.tan((eta2 - eta12) / 2.0d);
                double alpha = (Math.sin(eta2 - eta12) * (Math.sqrt(((tanDiff2 * 3.0d) * tanDiff2) + 4.0d) - 1.0d)) / 3.0d;
                int numSegments3 = numSegments2;
                p.rLineTo(0.0f, 0.0f);
                p.cubicTo((float) (e1x2 + (alpha * ep1x)), (float) (e1y2 + (alpha * ep1y2)), (float) (e2x - (alpha * ep2x)), (float) (cosEta13 - (alpha * ep2y)), (float) e2x, (float) cosEta13);
                eta12 = eta2;
                e1x2 = e2x;
                e1y2 = cosEta13;
                ep1x = ep2x;
                ep1y2 = ep2y;
                i++;
                d = a;
                numSegments = numSegments3;
                sinEta1 = sinEta12;
                anglePerSegment = anglePerSegment2;
                cosEta1 = cosEta12;
                cosTheta = cosTheta;
                sinTheta = sinTheta;
            }
        }

        public void interpolatePathDataNode(PathDataNode nodeFrom, PathDataNode nodeTo, float fraction) {
            this.mType = nodeFrom.mType;
            int i = 0;
            while (true) {
                float[] fArr = nodeFrom.mParams;
                if (i < fArr.length) {
                    this.mParams[i] = (fArr[i] * (1.0f - fraction)) + (nodeTo.mParams[i] * fraction);
                    i++;
                } else {
                    return;
                }
            }
        }
    }
}
