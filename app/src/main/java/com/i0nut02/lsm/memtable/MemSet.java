package com.i0nut02.lsm.memtable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.i0nut02.lsm.types.ByteArrayPair;

public class MemSet implements Memtable {
    private HashMap<byte[], byte[]> hashMap = new HashMap<>();

    public void put(ByteArrayPair b) {
        hashMap.put(b.key(), b.value());
    }

    public void delete(byte[] key) {
        hashMap.remove(key);
        return;
    }

    public byte[] get(byte[] key){
        return hashMap.get(key);
    }

    public int size() {
        return hashMap.size();
    }

    public List<ByteArrayPair> flush() {
        List<ByteArrayPair> list = new ArrayList<>();
        for (var entry : hashMap.entrySet()) {
            list.add(new ByteArrayPair(entry.getKey(), entry.getValue()));
        }
        return list;
    }
}
