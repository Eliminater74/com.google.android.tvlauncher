package com.bumptech.glide.gifencoder;

import android.support.p001v4.internal.view.SupportMenu;
import com.google.common.primitives.UnsignedBytes;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.IOException;
import java.io.OutputStream;

class LZWEncoder {
    static final int BITS = 12;
    private static final int EOF = -1;
    static final int HSIZE = 5003;
    int ClearCode;
    int EOFCode;
    int a_count;
    byte[] accum = new byte[256];
    boolean clear_flg = false;
    int[] codetab = new int[HSIZE];
    private int curPixel;
    int cur_accum = 0;
    int cur_bits = 0;
    int free_ent = 0;
    int g_init_bits;
    int hsize = HSIZE;
    int[] htab = new int[HSIZE];
    private int imgH;
    private int imgW;
    private int initCodeSize;
    int[] masks = {0, 1, 3, 7, 15, 31, 63, ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE, 255, 511, ClientAnalytics.LogRequest.LogSource.G_SUITE_ADD_ONS_COUNTERS_VALUE, 2047, 4095, 8191, 16383, 32767, SupportMenu.USER_MASK};
    int maxbits = 12;
    int maxcode;
    int maxmaxcode = 4096;
    int n_bits;
    private byte[] pixAry;
    private int remaining;

    LZWEncoder(int width, int height, byte[] pixels, int color_depth) {
        this.imgW = width;
        this.imgH = height;
        this.pixAry = pixels;
        this.initCodeSize = Math.max(2, color_depth);
    }

    /* access modifiers changed from: package-private */
    public void char_out(byte c, OutputStream outs) throws IOException {
        byte[] bArr = this.accum;
        int i = this.a_count;
        this.a_count = i + 1;
        bArr[i] = c;
        if (this.a_count >= 254) {
            flush_char(outs);
        }
    }

    /* access modifiers changed from: package-private */
    public void cl_block(OutputStream outs) throws IOException {
        cl_hash(this.hsize);
        int i = this.ClearCode;
        this.free_ent = i + 2;
        this.clear_flg = true;
        output(i, outs);
    }

    /* access modifiers changed from: package-private */
    public void cl_hash(int hsize2) {
        for (int i = 0; i < hsize2; i++) {
            this.htab[i] = -1;
        }
    }

    /* access modifiers changed from: package-private */
    public void compress(int init_bits, OutputStream outs) throws IOException {
        this.g_init_bits = init_bits;
        this.clear_flg = false;
        this.n_bits = this.g_init_bits;
        this.maxcode = MAXCODE(this.n_bits);
        this.ClearCode = 1 << (init_bits - 1);
        int i = this.ClearCode;
        this.EOFCode = i + 1;
        this.free_ent = i + 2;
        this.a_count = 0;
        int ent = nextPixel();
        int hshift = 0;
        for (int fcode = this.hsize; fcode < 65536; fcode *= 2) {
            hshift++;
        }
        int hshift2 = 8 - hshift;
        int hsize_reg = this.hsize;
        cl_hash(hsize_reg);
        output(this.ClearCode, outs);
        while (true) {
            int nextPixel = nextPixel();
            int c = nextPixel;
            if (nextPixel != -1) {
                int fcode2 = (c << this.maxbits) + ent;
                int i2 = (c << hshift2) ^ ent;
                int[] iArr = this.htab;
                if (iArr[i2] == fcode2) {
                    ent = this.codetab[i2];
                } else if (iArr[i2] >= 0) {
                    int disp = hsize_reg - i2;
                    if (i2 == 0) {
                        disp = 1;
                    }
                    while (true) {
                        int i3 = i2 - disp;
                        i2 = i3;
                        if (i3 < 0) {
                            i2 += hsize_reg;
                        }
                        int[] iArr2 = this.htab;
                        if (iArr2[i2] != fcode2) {
                            if (iArr2[i2] < 0) {
                                break;
                            }
                        } else {
                            ent = this.codetab[i2];
                            break;
                        }
                    }
                } else {
                    output(ent, outs);
                    ent = c;
                    int i4 = this.free_ent;
                    if (i4 < this.maxmaxcode) {
                        int[] iArr3 = this.codetab;
                        this.free_ent = i4 + 1;
                        iArr3[i2] = i4;
                        this.htab[i2] = fcode2;
                    } else {
                        cl_block(outs);
                    }
                }
            } else {
                output(ent, outs);
                output(this.EOFCode, outs);
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void encode(OutputStream os) throws IOException {
        os.write(this.initCodeSize);
        this.remaining = this.imgW * this.imgH;
        this.curPixel = 0;
        compress(this.initCodeSize + 1, os);
        os.write(0);
    }

    /* access modifiers changed from: package-private */
    public void flush_char(OutputStream outs) throws IOException {
        int i = this.a_count;
        if (i > 0) {
            outs.write(i);
            outs.write(this.accum, 0, this.a_count);
            this.a_count = 0;
        }
    }

    /* access modifiers changed from: package-private */
    public final int MAXCODE(int n_bits2) {
        return (1 << n_bits2) - 1;
    }

    private int nextPixel() {
        int i = this.remaining;
        if (i == 0) {
            return -1;
        }
        this.remaining = i - 1;
        byte[] bArr = this.pixAry;
        int i2 = this.curPixel;
        this.curPixel = i2 + 1;
        return bArr[i2] & UnsignedBytes.MAX_VALUE;
    }

    /* access modifiers changed from: package-private */
    public void output(int code, OutputStream outs) throws IOException {
        int i = this.cur_accum;
        int[] iArr = this.masks;
        int i2 = this.cur_bits;
        this.cur_accum = i & iArr[i2];
        if (i2 > 0) {
            this.cur_accum |= code << i2;
        } else {
            this.cur_accum = code;
        }
        this.cur_bits += this.n_bits;
        while (this.cur_bits >= 8) {
            char_out((byte) (this.cur_accum & 255), outs);
            this.cur_accum >>= 8;
            this.cur_bits -= 8;
        }
        if (this.free_ent > this.maxcode || this.clear_flg) {
            if (this.clear_flg) {
                int i3 = this.g_init_bits;
                this.n_bits = i3;
                this.maxcode = MAXCODE(i3);
                this.clear_flg = false;
            } else {
                this.n_bits++;
                int i4 = this.n_bits;
                if (i4 == this.maxbits) {
                    this.maxcode = this.maxmaxcode;
                } else {
                    this.maxcode = MAXCODE(i4);
                }
            }
        }
        if (code == this.EOFCode) {
            while (this.cur_bits > 0) {
                char_out((byte) (this.cur_accum & 255), outs);
                this.cur_accum >>= 8;
                this.cur_bits -= 8;
            }
            flush_char(outs);
        }
    }
}
