package com.i0nut02.lsm.comparator;


public class ByteArrayComparator {
    public static int compare(byte[] a, byte[] b) {
        if (a == null) {
            return b == null ? 0 : -1;
        }
        if (b == null) {
            return 1;
        }

        int aLen = a.length;
        int bLen = b.length;

        if (aLen != bLen) {
            return aLen < bLen ? -1 : 1;
        }

        for (int i = 0; i < aLen; i++) {
            byte aByte = a[i];
            byte bByte = b[i]; 
            if (aByte != bByte) {
                return aByte < bByte ? -1 : 1;
            }
        }
        return 0;
    }
}