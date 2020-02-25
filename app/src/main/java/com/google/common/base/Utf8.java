package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
@Beta
public final class Utf8 {
    private Utf8() {
    }

    public static int encodedLength(CharSequence sequence) {
        int utf16Length = sequence.length();
        int utf8Length = utf16Length;
        int i = 0;
        while (i < utf16Length && sequence.charAt(i) < 128) {
            i++;
        }
        while (true) {
            if (i < utf16Length) {
                char c = sequence.charAt(i);
                if (c >= 2048) {
                    utf8Length += encodedLengthGeneral(sequence, i);
                    break;
                }
                utf8Length += (127 - c) >>> 31;
                i++;
            } else {
                break;
            }
        }
        if (utf8Length >= utf16Length) {
            return utf8Length;
        }
        StringBuilder sb = new StringBuilder(54);
        sb.append("UTF-8 length does not fit in int: ");
        sb.append(((long) utf8Length) + 4294967296L);
        throw new IllegalArgumentException(sb.toString());
    }

    private static int encodedLengthGeneral(CharSequence sequence, int start) {
        int utf16Length = sequence.length();
        int utf8Length = 0;
        int i = start;
        while (i < utf16Length) {
            char c = sequence.charAt(i);
            if (c < 2048) {
                utf8Length += (127 - c) >>> 31;
            } else {
                utf8Length += 2;
                if (55296 <= c && c <= 57343) {
                    if (Character.codePointAt(sequence, i) != c) {
                        i++;
                    } else {
                        throw new IllegalArgumentException(unpairedSurrogateMsg(i));
                    }
                }
            }
            i++;
        }
        return utf8Length;
    }

    public static boolean isWellFormed(byte[] bytes) {
        return isWellFormed(bytes, 0, bytes.length);
    }

    public static boolean isWellFormed(byte[] bytes, int off, int len) {
        int end = off + len;
        Preconditions.checkPositionIndexes(off, end, bytes.length);
        for (int i = off; i < end; i++) {
            if (bytes[i] < 0) {
                return isWellFormedSlowPath(bytes, i, end);
            }
        }
        return true;
    }

    /* JADX INFO: Multiple debug info for r1v2 byte: [D('byte2' int), D('index' int)] */
    /* JADX INFO: Multiple debug info for r1v3 byte: [D('byte2' int), D('index' int)] */
    private static boolean isWellFormedSlowPath(byte[] bytes, int off, int end) {
        int index = off;
        while (index < end) {
            int index2 = index + 1;
            byte b = bytes[index];
            int byte1 = b;
            if (b >= 0) {
                index = index2;
            } else if (byte1 < -32) {
                if (index2 == end) {
                    return false;
                }
                if (byte1 >= -62) {
                    index = index2 + 1;
                    if (bytes[index2] > -65) {
                    }
                }
                return false;
            } else if (byte1 < -16) {
                if (index2 + 1 >= end) {
                    return false;
                }
                int index3 = index2 + 1;
                byte b2 = bytes[index2];
                if (b2 <= -65 && ((byte1 != -32 || b2 >= -96) && (byte1 != -19 || -96 > b2))) {
                    index = index3 + 1;
                    if (bytes[index3] > -65) {
                    }
                }
                return false;
            } else if (index2 + 2 >= end) {
                return false;
            } else {
                int index4 = index2 + 1;
                byte b3 = bytes[index2];
                if (b3 <= -65 && (((byte1 << 28) + (b3 + 112)) >> 30) == 0) {
                    int index5 = index4 + 1;
                    if (bytes[index4] <= -65) {
                        index = index5 + 1;
                        if (bytes[index5] > -65) {
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }

    private static String unpairedSurrogateMsg(int i) {
        StringBuilder sb = new StringBuilder(39);
        sb.append("Unpaired surrogate at index ");
        sb.append(i);
        return sb.toString();
    }
}
