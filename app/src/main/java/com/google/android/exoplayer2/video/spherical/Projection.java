package com.google.android.exoplayer2.video.spherical;

import com.google.android.exoplayer2.util.Assertions;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Projection {
    public static final int DRAW_MODE_TRIANGLES = 0;
    public static final int DRAW_MODE_TRIANGLES_FAN = 2;
    public static final int DRAW_MODE_TRIANGLES_STRIP = 1;
    public static final int POSITION_COORDS_PER_VERTEX = 3;
    public static final int TEXTURE_COORDS_PER_VERTEX = 2;
    public final Mesh leftMesh;
    public final Mesh rightMesh;
    public final boolean singleMesh;
    public final int stereoMode;

    public Projection(Mesh mesh, int stereoMode2) {
        this(mesh, mesh, stereoMode2);
    }

    public Projection(Mesh leftMesh2, Mesh rightMesh2, int stereoMode2) {
        this.leftMesh = leftMesh2;
        this.rightMesh = rightMesh2;
        this.stereoMode = stereoMode2;
        this.singleMesh = leftMesh2 == rightMesh2;
    }

    public static Projection createEquirectangular(int stereoMode2) {
        return createEquirectangular(50.0f, 36, 72, 180.0f, 360.0f, stereoMode2);
    }

    public static Projection createEquirectangular(float radius, int latitudes, int longitudes, float verticalFovDegrees, float horizontalFovDegrees, int stereoMode2) {
        int k;
        int i;
        int vOffset;
        float f = radius;
        int i2 = latitudes;
        int k2 = longitudes;
        float f2 = verticalFovDegrees;
        float f3 = horizontalFovDegrees;
        Assertions.checkArgument(f > 0.0f);
        Assertions.checkArgument(i2 >= 1);
        Assertions.checkArgument(k2 >= 1);
        Assertions.checkArgument(f2 > 0.0f && f2 <= 180.0f);
        Assertions.checkArgument(f3 > 0.0f && f3 <= 360.0f);
        float verticalFovRads = (float) Math.toRadians((double) f2);
        float horizontalFovRads = (float) Math.toRadians((double) f3);
        float quadHeightRads = verticalFovRads / ((float) i2);
        float quadWidthRads = horizontalFovRads / ((float) k2);
        int vertexCount = (((k2 + 1) * 2) + 2) * i2;
        float[] vertexData = new float[(vertexCount * 3)];
        float[] textureData = new float[(vertexCount * 2)];
        int vOffset2 = 0;
        int j = 0;
        int tOffset = 0;
        while (j < i2) {
            float phiLow = (((float) j) * quadHeightRads) - (verticalFovRads / 2.0f);
            float phiHigh = (((float) (j + 1)) * quadHeightRads) - (verticalFovRads / 2.0f);
            int i3 = 0;
            while (i3 < k2 + 1) {
                int k3 = 0;
                while (k3 < 2) {
                    float phi = k3 == 0 ? phiLow : phiHigh;
                    float theta = ((((float) i3) * quadWidthRads) + 3.1415927f) - (horizontalFovRads / 2.0f);
                    int vOffset3 = vOffset2 + 1;
                    float phiLow2 = phiLow;
                    float phiHigh2 = phiHigh;
                    double d = (double) f;
                    int k4 = k3;
                    double sin = Math.sin((double) theta);
                    Double.isNaN(d);
                    vertexData[vOffset2] = -((float) (d * sin * Math.cos((double) phi)));
                    int vOffset4 = vOffset3 + 1;
                    double d2 = (double) f;
                    float[] textureData2 = textureData;
                    int j2 = j;
                    double sin2 = Math.sin((double) phi);
                    Double.isNaN(d2);
                    vertexData[vOffset3] = (float) (d2 * sin2);
                    double d3 = (double) f;
                    int vOffset5 = vOffset4 + 1;
                    double cos = Math.cos((double) theta);
                    Double.isNaN(d3);
                    vertexData[vOffset4] = (float) (d3 * cos * Math.cos((double) phi));
                    int tOffset2 = tOffset + 1;
                    textureData2[tOffset] = (((float) i3) * quadWidthRads) / horizontalFovRads;
                    int tOffset3 = tOffset2 + 1;
                    textureData2[tOffset2] = (((float) (j2 + k4)) * quadHeightRads) / verticalFovRads;
                    if (i3 == 0 && k4 == 0) {
                        i = longitudes;
                        k = k4;
                    } else {
                        i = longitudes;
                        if (i3 == i) {
                            k = k4;
                            if (k != 1) {
                                vOffset = vOffset5;
                            }
                        } else {
                            vOffset = vOffset5;
                            k = k4;
                        }
                        vOffset2 = vOffset;
                        tOffset = tOffset3;
                        int vOffset6 = k + 1;
                        textureData = textureData2;
                        phiLow = phiLow2;
                        phiHigh = phiHigh2;
                        j = j2;
                        k2 = i;
                        k3 = vOffset6;
                        f = radius;
                    }
                    int vOffset7 = vOffset5;
                    System.arraycopy(vertexData, vOffset5 - 3, vertexData, vOffset7, 3);
                    vOffset = vOffset7 + 3;
                    System.arraycopy(textureData2, tOffset3 - 2, textureData2, tOffset3, 2);
                    tOffset3 += 2;
                    vOffset2 = vOffset;
                    tOffset = tOffset3;
                    int vOffset62 = k + 1;
                    textureData = textureData2;
                    phiLow = phiLow2;
                    phiHigh = phiHigh2;
                    j = j2;
                    k2 = i;
                    k3 = vOffset62;
                    f = radius;
                }
                i3++;
                f = radius;
                phiHigh = phiHigh;
                j = j;
                k2 = k2;
            }
            j++;
            f = radius;
            k2 = k2;
            i2 = latitudes;
        }
        return new Projection(new Mesh(new SubMesh(0, vertexData, textureData, 1)), stereoMode2);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawMode {
    }

    public static final class SubMesh {
        public static final int VIDEO_TEXTURE_ID = 0;
        public final int mode;
        public final float[] textureCoords;
        public final int textureId;
        public final float[] vertices;

        public SubMesh(int textureId2, float[] vertices2, float[] textureCoords2, int mode2) {
            this.textureId = textureId2;
            Assertions.checkArgument(((long) vertices2.length) * 2 == ((long) textureCoords2.length) * 3);
            this.vertices = vertices2;
            this.textureCoords = textureCoords2;
            this.mode = mode2;
        }

        public int getVertexCount() {
            return this.vertices.length / 3;
        }
    }

    public static final class Mesh {
        private final SubMesh[] subMeshes;

        public Mesh(SubMesh... subMeshes2) {
            this.subMeshes = subMeshes2;
        }

        public int getSubMeshCount() {
            return this.subMeshes.length;
        }

        public SubMesh getSubMesh(int index) {
            return this.subMeshes[index];
        }
    }
}
