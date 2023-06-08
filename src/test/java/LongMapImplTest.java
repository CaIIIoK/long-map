import de.comparus.opensource.longmap.LongMapImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LongMapImplTest {

    private LongMapImpl<String> map;

    @Before
    public void setup() {
        map = new LongMapImpl<>();
    }

    @Test
    public void testPutAndGet() {
        map.put(1L, "Value1");
        map.put(2L, "Value2");
        map.put(3L, "Value3");

        assertEquals("Value1", map.get(1L));
        assertEquals("Value2", map.get(2L));
        assertEquals("Value3", map.get(3L));
    }

    @Test
    public void testPutOverwrite() {
        map.put(1L, "Value1");
        map.put(1L, "Value2");

        assertEquals("Value2", map.get(1L));
    }

    @Test
    public void testRemove() {
        map.put(1L, "Value1");
        map.put(2L, "Value2");

        assertEquals("Value1", map.remove(1L));
        assertNull(map.get(1L));
    }

    @Test
    public void testContainsKey() {
        map.put(1L, "Value1");
        map.put(2L, "Value2");

        assertTrue(map.containsKey(1L));
        assertFalse(map.containsKey(3L));
    }

    @Test
    public void testContainsValue() {
        map.put(1L, "Value1");
        map.put(2L, "Value2");

        assertTrue(map.containsValue("Value1"));
        assertFalse(map.containsValue("Value3"));
    }

    @Test
    public void testKeys() {
        map.put(1L, "Value1");
        map.put(2L, "Value2");

        long[] keys = map.keys();

        assertEquals(2, keys.length);
        assertEquals(1L, keys[0]);
        assertEquals(2L, keys[1]);
    }

    @Test
    public void testValues() {
        map.put(1L, "Value1");
        map.put(2L, "Value2");

        Object[] values = map.values();
        String[] stringValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            stringValues[i] = (String) values[i];
        }

        assertEquals(2, stringValues.length);
        assertEquals("Value1", stringValues[0]);
        assertEquals("Value2", stringValues[1]);
    }

    @Test
    public void testSize() {
        map.put(1L, "Value1");
        map.put(2L, "Value2");

        assertEquals(2, map.size());

        map.remove(1L);

        assertEquals(1, map.size());

        map.clear();

        assertEquals(0, map.size());
    }

    @Test
    public void testClear() {
        map.put(1L, "Value1");
        map.put(2L, "Value2");

        map.clear();

        assertTrue(map.isEmpty());
        assertNull(map.get(1L));
        assertNull(map.get(2L));
    }
}