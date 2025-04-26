package com.i0nut02.lsm.memtable;

import com.i0nut02.lsm.types.ByteArrayPair;

public class Memtable {
    SkipList list;
    long byteSize;

    public Memtable() {
        list = new SkipList();
        byteSize = 0;
    }

    public void add(ByteArrayPair b) {
        list.put(b);
        byteSize += b.getSize();
        return;
    }

    public byte[] get(byte[] key) {
        return list.get(key);
    }

    public void remove(byte[] key) {
        byte[] value = get(key);
        byteSize -= (value == null) ? 0 : value.length;
        list.put(new ByteArrayPair(key, new byte[]{}));
        return;
    }

    public long getByteSize() {
        return byteSize;
    }
}