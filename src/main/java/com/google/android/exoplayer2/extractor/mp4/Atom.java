package com.google.android.exoplayer2.extractor.mp4;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.source.hls.DefaultHlsExtractorFactory;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class Atom {
    public static final int DEFINES_LARGE_SIZE = 1;
    public static final int EXTENDS_TO_END_SIZE = 0;
    public static final int FULL_HEADER_SIZE = 12;
    public static final int HEADER_SIZE = 8;
    public static final int LONG_HEADER_SIZE = 16;
    public static final int TYPE_Opus = Util.getIntegerCodeForString("Opus");
    public static final int TYPE_TTML = Util.getIntegerCodeForString("TTML");
    public static final int TYPE__mp3 = Util.getIntegerCodeForString(DefaultHlsExtractorFactory.MP3_FILE_EXTENSION);
    public static final int TYPE_ac_3 = Util.getIntegerCodeForString("ac-3");
    public static final int TYPE_ac_4 = Util.getIntegerCodeForString("ac-4");
    public static final int TYPE_alac = Util.getIntegerCodeForString("alac");
    public static final int TYPE_alaw = Util.getIntegerCodeForString("alaw");
    public static final int TYPE_av01 = Util.getIntegerCodeForString("av01");
    public static final int TYPE_av1C = Util.getIntegerCodeForString("av1C");
    public static final int TYPE_avc1 = Util.getIntegerCodeForString("avc1");
    public static final int TYPE_avc3 = Util.getIntegerCodeForString("avc3");
    public static final int TYPE_avcC = Util.getIntegerCodeForString("avcC");
    public static final int TYPE_c608 = Util.getIntegerCodeForString("c608");
    public static final int TYPE_camm = Util.getIntegerCodeForString("camm");
    public static final int TYPE_co64 = Util.getIntegerCodeForString("co64");
    public static final int TYPE_ctts = Util.getIntegerCodeForString("ctts");
    public static final int TYPE_d263 = Util.getIntegerCodeForString("d263");
    public static final int TYPE_dOps = Util.getIntegerCodeForString("dOps");
    public static final int TYPE_dac3 = Util.getIntegerCodeForString("dac3");
    public static final int TYPE_dac4 = Util.getIntegerCodeForString("dac4");
    public static final int TYPE_data = Util.getIntegerCodeForString("data");
    public static final int TYPE_ddts = Util.getIntegerCodeForString("ddts");
    public static final int TYPE_dec3 = Util.getIntegerCodeForString("dec3");
    public static final int TYPE_dfLa = Util.getIntegerCodeForString("dfLa");
    public static final int TYPE_dtsc = Util.getIntegerCodeForString("dtsc");
    public static final int TYPE_dtse = Util.getIntegerCodeForString("dtse");
    public static final int TYPE_dtsh = Util.getIntegerCodeForString("dtsh");
    public static final int TYPE_dtsl = Util.getIntegerCodeForString("dtsl");
    public static final int TYPE_dva1 = Util.getIntegerCodeForString("dva1");
    public static final int TYPE_dvav = Util.getIntegerCodeForString("dvav");
    public static final int TYPE_dvcC = Util.getIntegerCodeForString("dvcC");
    public static final int TYPE_dvh1 = Util.getIntegerCodeForString("dvh1");
    public static final int TYPE_dvhe = Util.getIntegerCodeForString("dvhe");
    public static final int TYPE_dvvC = Util.getIntegerCodeForString("dvvC");
    public static final int TYPE_ec_3 = Util.getIntegerCodeForString("ec-3");
    public static final int TYPE_edts = Util.getIntegerCodeForString("edts");
    public static final int TYPE_elst = Util.getIntegerCodeForString("elst");
    public static final int TYPE_emsg = Util.getIntegerCodeForString("emsg");
    public static final int TYPE_enca = Util.getIntegerCodeForString("enca");
    public static final int TYPE_encv = Util.getIntegerCodeForString("encv");
    public static final int TYPE_esds = Util.getIntegerCodeForString("esds");
    public static final int TYPE_fLaC = Util.getIntegerCodeForString("fLaC");
    public static final int TYPE_frma = Util.getIntegerCodeForString("frma");
    public static final int TYPE_ftyp = Util.getIntegerCodeForString("ftyp");
    public static final int TYPE_hdlr = Util.getIntegerCodeForString("hdlr");
    public static final int TYPE_hev1 = Util.getIntegerCodeForString("hev1");
    public static final int TYPE_hvc1 = Util.getIntegerCodeForString("hvc1");
    public static final int TYPE_hvcC = Util.getIntegerCodeForString("hvcC");
    public static final int TYPE_ilst = Util.getIntegerCodeForString("ilst");
    public static final int TYPE_keys = Util.getIntegerCodeForString("keys");
    public static final int TYPE_lpcm = Util.getIntegerCodeForString("lpcm");
    public static final int TYPE_mdat = Util.getIntegerCodeForString("mdat");
    public static final int TYPE_mdhd = Util.getIntegerCodeForString("mdhd");
    public static final int TYPE_mdia = Util.getIntegerCodeForString("mdia");
    public static final int TYPE_mean = Util.getIntegerCodeForString("mean");
    public static final int TYPE_mehd = Util.getIntegerCodeForString("mehd");
    public static final int TYPE_meta = Util.getIntegerCodeForString("meta");
    public static final int TYPE_minf = Util.getIntegerCodeForString("minf");
    public static final int TYPE_moof = Util.getIntegerCodeForString("moof");
    public static final int TYPE_moov = Util.getIntegerCodeForString("moov");
    public static final int TYPE_mp4a = Util.getIntegerCodeForString("mp4a");
    public static final int TYPE_mp4v = Util.getIntegerCodeForString("mp4v");
    public static final int TYPE_mvex = Util.getIntegerCodeForString("mvex");
    public static final int TYPE_mvhd = Util.getIntegerCodeForString("mvhd");
    public static final int TYPE_name = Util.getIntegerCodeForString("name");
    public static final int TYPE_pasp = Util.getIntegerCodeForString("pasp");
    public static final int TYPE_proj = Util.getIntegerCodeForString("proj");
    public static final int TYPE_pssh = Util.getIntegerCodeForString("pssh");
    public static final int TYPE_s263 = Util.getIntegerCodeForString("s263");
    public static final int TYPE_saio = Util.getIntegerCodeForString("saio");
    public static final int TYPE_saiz = Util.getIntegerCodeForString("saiz");
    public static final int TYPE_samr = Util.getIntegerCodeForString("samr");
    public static final int TYPE_sawb = Util.getIntegerCodeForString("sawb");
    public static final int TYPE_sbgp = Util.getIntegerCodeForString("sbgp");
    public static final int TYPE_schi = Util.getIntegerCodeForString("schi");
    public static final int TYPE_schm = Util.getIntegerCodeForString("schm");
    public static final int TYPE_senc = Util.getIntegerCodeForString("senc");
    public static final int TYPE_sgpd = Util.getIntegerCodeForString("sgpd");
    public static final int TYPE_sidx = Util.getIntegerCodeForString("sidx");
    public static final int TYPE_sinf = Util.getIntegerCodeForString("sinf");
    public static final int TYPE_sowt = Util.getIntegerCodeForString("sowt");
    public static final int TYPE_st3d = Util.getIntegerCodeForString("st3d");
    public static final int TYPE_stbl = Util.getIntegerCodeForString("stbl");
    public static final int TYPE_stco = Util.getIntegerCodeForString("stco");
    public static final int TYPE_stpp = Util.getIntegerCodeForString("stpp");
    public static final int TYPE_stsc = Util.getIntegerCodeForString("stsc");
    public static final int TYPE_stsd = Util.getIntegerCodeForString("stsd");
    public static final int TYPE_stss = Util.getIntegerCodeForString("stss");
    public static final int TYPE_stsz = Util.getIntegerCodeForString("stsz");
    public static final int TYPE_stts = Util.getIntegerCodeForString("stts");
    public static final int TYPE_stz2 = Util.getIntegerCodeForString("stz2");
    public static final int TYPE_sv3d = Util.getIntegerCodeForString("sv3d");
    public static final int TYPE_tenc = Util.getIntegerCodeForString("tenc");
    public static final int TYPE_tfdt = Util.getIntegerCodeForString("tfdt");
    public static final int TYPE_tfhd = Util.getIntegerCodeForString("tfhd");
    public static final int TYPE_tkhd = Util.getIntegerCodeForString("tkhd");
    public static final int TYPE_traf = Util.getIntegerCodeForString("traf");
    public static final int TYPE_trak = Util.getIntegerCodeForString("trak");
    public static final int TYPE_trex = Util.getIntegerCodeForString("trex");
    public static final int TYPE_trun = Util.getIntegerCodeForString("trun");
    public static final int TYPE_tx3g = Util.getIntegerCodeForString("tx3g");
    public static final int TYPE_udta = Util.getIntegerCodeForString("udta");
    public static final int TYPE_ulaw = Util.getIntegerCodeForString("ulaw");
    public static final int TYPE_uuid = Util.getIntegerCodeForString("uuid");
    public static final int TYPE_vmhd = Util.getIntegerCodeForString("vmhd");
    public static final int TYPE_vp08 = Util.getIntegerCodeForString("vp08");
    public static final int TYPE_vp09 = Util.getIntegerCodeForString("vp09");
    public static final int TYPE_vpcC = Util.getIntegerCodeForString("vpcC");
    public static final int TYPE_wave = Util.getIntegerCodeForString("wave");
    public static final int TYPE_wvtt = Util.getIntegerCodeForString("wvtt");
    public final int type;

    public Atom(int type2) {
        this.type = type2;
    }

    public String toString() {
        return getAtomTypeString(this.type);
    }

    static final class LeafAtom extends Atom {
        public final ParsableByteArray data;

        public LeafAtom(int type, ParsableByteArray data2) {
            super(type);
            this.data = data2;
        }
    }

    static final class ContainerAtom extends Atom {
        public final List<ContainerAtom> containerChildren = new ArrayList();
        public final long endPosition;
        public final List<LeafAtom> leafChildren = new ArrayList();

        public ContainerAtom(int type, long endPosition2) {
            super(type);
            this.endPosition = endPosition2;
        }

        public void add(LeafAtom atom) {
            this.leafChildren.add(atom);
        }

        public void add(ContainerAtom atom) {
            this.containerChildren.add(atom);
        }

        @Nullable
        public LeafAtom getLeafAtomOfType(int type) {
            int childrenSize = this.leafChildren.size();
            for (int i = 0; i < childrenSize; i++) {
                LeafAtom atom = this.leafChildren.get(i);
                if (atom.type == type) {
                    return atom;
                }
            }
            return null;
        }

        @Nullable
        public ContainerAtom getContainerAtomOfType(int type) {
            int childrenSize = this.containerChildren.size();
            for (int i = 0; i < childrenSize; i++) {
                ContainerAtom atom = this.containerChildren.get(i);
                if (atom.type == type) {
                    return atom;
                }
            }
            return null;
        }

        public int getChildAtomOfTypeCount(int type) {
            int count = 0;
            int size = this.leafChildren.size();
            for (int i = 0; i < size; i++) {
                if (this.leafChildren.get(i).type == type) {
                    count++;
                }
            }
            int size2 = this.containerChildren.size();
            for (int i2 = 0; i2 < size2; i2++) {
                if (this.containerChildren.get(i2).type == type) {
                    count++;
                }
            }
            return count;
        }

        public String toString() {
            String atomTypeString = getAtomTypeString(this.type);
            String arrays = Arrays.toString(this.leafChildren.toArray());
            String arrays2 = Arrays.toString(this.containerChildren.toArray());
            StringBuilder sb = new StringBuilder(String.valueOf(atomTypeString).length() + 22 + String.valueOf(arrays).length() + String.valueOf(arrays2).length());
            sb.append(atomTypeString);
            sb.append(" leaves: ");
            sb.append(arrays);
            sb.append(" containers: ");
            sb.append(arrays2);
            return sb.toString();
        }
    }

    public static int parseFullAtomVersion(int fullAtomInt) {
        return (fullAtomInt >> 24) & 255;
    }

    public static int parseFullAtomFlags(int fullAtomInt) {
        return 16777215 & fullAtomInt;
    }

    public static String getAtomTypeString(int type2) {
        StringBuilder sb = new StringBuilder(4);
        sb.append((char) ((type2 >> 24) & 255));
        sb.append((char) ((type2 >> 16) & 255));
        sb.append((char) ((type2 >> 8) & 255));
        sb.append((char) (type2 & 255));
        return sb.toString();
    }
}
