package com.i0nut02.lsm.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteArrayComaratorTest {
    
    @Test
    void testEqualZeros() {
        assertEquals(ByteArrayComparator.compare(new byte[]{0}, new byte[]{0}), 0);
    }

    @Test
    void testNullVal() {
        assertEquals(ByteArrayComparator.compare(null, new byte[]{0}), -1);
    }

    @Test
    void testValNull() {
        assertEquals(ByteArrayComparator.compare(new byte[]{0}, null), 1);
    }

    @Test
    void testMultiVals() {
        assertEquals(ByteArrayComparator.compare(new byte[]{1, 2}, new byte[]{0}), 1);
    }

    @Test
    void testMultiVals2() {
        assertEquals(ByteArrayComparator.compare(new byte[]{1, 2}, new byte[]{1, 3}), -1);
    }

    @Test
    void testMultiValsEquals() {
        assertEquals(ByteArrayComparator.compare(new byte[]{1, 2, 3}, new byte[]{1, 2, 3}), 0);
    }
}
