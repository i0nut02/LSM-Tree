package com.i0nut02.lsm.memtable;

import static org.junit.jupiter.api.Assertions.*;

import com.i0nut02.lsm.types.ByteArrayPair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemtableTest {

    private Memtable memtable;

    @BeforeEach
    void setUp() {
        memtable = new Memtable();
    }

    @Test
    void testAddAndGet() {
        byte[] key = "key1".getBytes();
        byte[] value = "value1".getBytes();
        ByteArrayPair pair = new ByteArrayPair(key, value);

        memtable.add(pair);

        assertArrayEquals(value, memtable.get(key));
    }

    @Test
    void testRemove() {
        byte[] key = "key2".getBytes();
        byte[] value = "value2".getBytes();
        ByteArrayPair pair = new ByteArrayPair(key, value);

        memtable.add(pair);
        memtable.remove(key);

        assertArrayEquals(new byte[]{}, memtable.get(key));
    }

    @Test
    void testByteSize() {
        byte[] key = "key3".getBytes();
        byte[] value = "value3".getBytes();
        ByteArrayPair pair = new ByteArrayPair(key, value);

        long expectedSize = pair.getSize();

        memtable.add(pair);

        assertEquals(expectedSize, memtable.getByteSize());

        memtable.remove(key);

        assertEquals(key.length, memtable.getByteSize());
    }
}
