package com.i0nut02.lsm.memtable;

import java.util.List;

import com.i0nut02.lsm.types.*;

interface Memtable {
    void put(ByteArrayPair b);
    void delete(byte[] key);
    byte[] get(byte[] key);
    int size();
    List<ByteArrayPair> flush();
}