package com.i0nut02.lsm.types;

import static com.i0nut02.lsm.comparator.ByteArrayComparator.compare;

public record ByteArrayPair(byte[] key, byte[] value) implements Comparable<ByteArrayPair>{
    
    int getSize() {
        return key.length + value.length;
    }

    @Override
    public int compareTo(ByteArrayPair b) {
        return compare(key, b.key);
    }


}
