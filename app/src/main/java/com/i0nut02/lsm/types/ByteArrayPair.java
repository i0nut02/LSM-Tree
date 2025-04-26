package com.i0nut02.lsm.types;

import static com.i0nut02.lsm.comparator.ByteArrayComparator.compare;

import java.util.Arrays;

public record ByteArrayPair(byte[] key, byte[] value) implements Comparable<ByteArrayPair>{
    
    public int getSize() {
        return key.length + value.length;
    }

    @Override
    public int compareTo(ByteArrayPair b) {
        return compare(key, b.key);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(key);
    }

}
