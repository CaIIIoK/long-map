package de.comparus.opensource.longmap;

import java.util.Arrays;
import java.util.Objects;

public class LongMapImpl<V> implements LongMap<V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Entry<V>[] table;
    private int size;

    public LongMapImpl() {
        table = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    public V put(long key, V value) {
        int index = hash(key) % table.length;

        Entry<V> entry = table[index];
        while (entry != null) {
            if (Objects.equals(entry.key, key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
            entry = entry.next;
        }

        if (size >= table.length * LOAD_FACTOR) {
            resizeTable();
            index = hash(key) % table.length;
        }

        Entry<V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;

        return null;
    }

    public V get(long key) {
        int index = hash(key) % table.length;

        Entry<V> entry = table[index];
        while (entry != null) {
            if (Objects.equals(entry.key, key)) {
                return entry.value;
            }
            entry = entry.next;
        }

        return null;
    }

    public V remove(long key) {
        int index = hash(key) % table.length;

        Entry<V> entry = table[index];
        Entry<V> prev = null;
        while (entry != null) {
            if (Objects.equals(entry.key, key)) {
                if (prev == null) {
                    table[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;
                return entry.value;
            }
            prev = entry;
            entry = entry.next;
        }

        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        int index = hash(key) % table.length;

        Entry<V> entry = table[index];
        while (entry != null) {
            if (Objects.equals(entry.key, key)) {
                return true;
            }
            entry = entry.next;
        }

        return false;
    }

    public boolean containsValue(V value) {
        for (Entry<V> entry : table) {
            while (entry != null) {
                if (Objects.equals(entry.value, value)) {
                    return true;
                }
                entry = entry.next;
            }
        }

        return false;
    }

    public long[] keys() {
        long[] keys = new long[size];
        int index = 0;

        for (Entry<V> entry : table) {
            while (entry != null) {
                keys[index++] = entry.key;
                entry = entry.next;
            }
        }

        return keys;
    }

    public V[] values() {
        V[] values = (V[]) new Object[size];
        int index = 0;

        for (Entry<V> entry : table) {
            while (entry != null) {
                values[index++] = entry.value;
                entry = entry.next;
            }
        }

        return values;
    }

    public long size() {
        return size;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    private int hash(long key) {
        return (int) (key ^ (key >>> 32));
    }

    private void resizeTable() {
        Entry<V>[] oldTable = table;
        table = new Entry[table.length * 2];
        size = 0;

        for (Entry<V> entry : oldTable) {
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    private static class Entry<V> {
        private final long key;
        private V value;
        private Entry<V> next;

        private Entry(long key, V value) {
            this.key = key;
            this.value = value;
            next = null;
        }
    }
}

