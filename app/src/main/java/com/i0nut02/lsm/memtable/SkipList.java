package com.i0nut02.lsm.memtable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.i0nut02.lsm.comparator.ByteArrayComparator.compare;
import static java.lang.Math.log;

import com.i0nut02.lsm.types.ByteArrayPair;

public class SkipList implements Iterable<ByteArrayPair>, Memtable {

    private final static int DEFAULT_SIZE = 1 << 20;
    private final int levels;
    private int size;
    private Random rand;
    private SkipNode head;

    public SkipList() {
        this(DEFAULT_SIZE);
    }

    public SkipList(int numElements) {
        levels = (int) log(numElements);
        size = 0;
        head = new SkipNode(null, levels);
        rand = new Random();
    }

    @Override
    public Iterator<ByteArrayPair> iterator() {
        return new SkipListIterator(head);  
    }

    @Override
    public void put(ByteArrayPair b) {
        SkipNode curr = head;
        SkipNode[] history = new SkipNode[levels];

        for (int i = levels -1; i >= 0; i--) {
            while (curr.next[i] != null && curr.next[i].val.compareTo(b) <= 0) {
                curr = curr.next[i];
            }
            history[i] = curr;
        }

        if (curr.val != null && curr.val.compareTo(b) == 0) {
            curr.val = b;
            return;
        }

        int upperLevel = rand.nextInt(levels);
        SkipNode newNode = new SkipNode(b, levels);

        for (int i = upperLevel; i >= 0; i--) {
            newNode.next[i] = history[i].next[i];
            history[i].next[i] = newNode;
        }
        size++;
        return;
    }

    @Override
    public void delete(byte[] key) {
        SkipNode curr = head;
        SkipNode[] history = new SkipNode[levels];

        for (int i = levels-1; i >= 0; i--) {
            while (curr.next[i] != null && compare(curr.next[i].val.key(), key) < 0) {
                curr = curr.next[i];
            }
            history[i] = curr;
        }

        if (curr.next[0].val != null && compare(curr.next[0].val.key(), key) != 0) {
            return;
        }

        for (int i = 0; i < levels; i++) {
            if (history[i].next[i] == null || compare(history[i].next[i].val.key(), key) != 0) {
                break;
            }
            history[i].next[i] = history[i].next[i].next[i];
        }
        size--;
        return;
    }

    @Override
    public byte[] get(byte[] key) {
        SkipNode curr = head;
        for (int i = levels -1; i >= 0; i--) {
            while (curr.next[i] != null && compare(curr.next[i].val.key(), key) <= 0) {
                curr = head.next[i];
            }
            if (curr.val != null && compare(curr.val.key(), key) == 0) {
                return curr.val.value();
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;    
    }

    @Override
    public List<ByteArrayPair> flush() {
        List<ByteArrayPair> res = new ArrayList<>();
        SkipNode curr = head;

        while (curr.next[0] != null) {
            res.add(curr.next[0].val);
            curr = curr.next[0];
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = levels - 1; i >= 0; i--) {
            sb.append(String.format("Level %2d: ", i));
            SkipNode current = head;
            
            while (current.next[i] != null) {
                sb.append(current.next[i].val).append(" -> ");
                current = current.next[i];
            }
            sb.append("END\n");
        }
        return sb.toString();
    }

    private final static class SkipNode {
        ByteArrayPair val;
        SkipNode[] next;

        SkipNode(ByteArrayPair val, int numLevels) {
            this.val = val;
            this.next = new SkipNode[numLevels];
        }
    }

    private static class SkipListIterator implements Iterator<ByteArrayPair> {
        SkipNode curr;

        SkipListIterator(SkipNode head) {
            this.curr = head;
        }

        @Override
        public boolean hasNext() {
            return curr.next[0] != null;
        }

        @Override
        public ByteArrayPair next() {
            if (curr.next[0] == null) {
                return null;
            }
            curr = curr.next[0];
            return curr.val;
        }
    }
}
