package santorini.sanyo.bitfield;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BitfieldTest {
    private static final Bitfield a = new Bitfield();
    private static final Bitfield b = new Bitfield();
    private static final Bitfield same = new Bitfield();
    private static final Bitfield different = new Bitfield();
    private static final Bitfield none = new Bitfield();
    private static final Bitfield all = new Bitfield();

    static {
        boolean[] a = {false, true, false, true};
        boolean[] b = {false, true, true, false};

        for (int i = 0; i != a.length; ++i) {
            BitfieldTest.a.set(i, a[i]);
        }

        for (int i = 0; i != b.length; ++i) {
            BitfieldTest.b.set(i, b[i]);
        }

        for (int i = 0; i != Bitfield.SIZE; ++i) {
            same.set(i, BitfieldTest.a.get(i) == BitfieldTest.b.get(i));
        }

        for (int i = 0; i != Bitfield.SIZE; ++i) {
            different.set(i, BitfieldTest.a.get(i) != BitfieldTest.b.get(i));
        }

        for (int i = 0; i != all.SIZE; ++i) {
            all.set(i, true);
        }
    }

    @Test
    public void get() {
        for (int i = 0; i != Bitfield.SIZE; ++i) {
            if (same.get(i)) {
                assertEquals("Index " + i, a.get(i), b.get(i));
            } else {
                assertEquals("Index " + i, different.get(i), true);
                assertNotEquals("Index " + i, a.get(i), b.get(i));
            }
        }
    }

    @Test
    public void set1() {
        Bitfield temp = new Bitfield();
        temp.set(a, true);
        assertEquals(temp, a);
        temp.set(a, true);
        assertEquals(temp, a);
        temp.set(b, false);
        temp.set(a, true);
        assertEquals(temp, a);
        temp.set(a, false);
        assertEquals(temp, none);
        temp.set(a, false);
        assertEquals(temp, none);
        temp.set(a, true);
        temp.set(b, true);
        assertEquals(temp, a.or(b));
        temp.set(b, false);
        temp.set(b, true);
        assertEquals(temp, a.or(b));
    }

    @Test
    public void set2() {
        Bitfield temp = new Bitfield();
        temp.set(a);
        assertEquals(temp, a);
        assertNotEquals(temp, b);
    }

    @Test
    public void setAll() {
        Bitfield temp = new Bitfield();
        temp.setAll(true);
        assertEquals(temp, all);
        temp.setAll(false);
        assertEquals(temp, none);
    }

    @Test
    public void switchBit() {
        Bitfield temp = new Bitfield(a);
        for (int i = 0; i != Bitfield.SIZE; ++i) {
            if (different.get(i)) {
                temp.switchBit(i);
            }
        }
        assertEquals(temp, b);
    }

    @Test
    public void switchBits() {
        Bitfield temp = new Bitfield(a);
        temp.switchBits(different);
        assertEquals(temp, b);
    }

    @Test
    public void any() {
        assertEquals(a.any(), true);
        assertEquals(b.any(), true);
        assertEquals(same.any(), true);
        assertEquals(different.any(), true);
        assertEquals(none.any(), false);
        assertEquals(all.any(), true);
    }

    @Test
    public void anyOf() {
        assertEquals(a.anyOf(a), true);
        assertEquals(a.anyOf(b), true);
        assertEquals(same.anyOf(different), false);
        assertEquals(a.anyOf(none), false);
        assertEquals(a.anyOf(all), true);
        assertEquals(all.anyOf(none), false);
        assertEquals(all.anyOf(all), true);
        assertEquals(none.anyOf(none), false);
    }

    @Test
    public void all() {
        assertEquals(a.all(), false);
        assertEquals(b.all(), false);
        assertEquals(same.all(), false);
        assertEquals(different.all(), false);
        assertEquals(none.all(), false);
        assertEquals(all.all(), true);
    }

    @Test
    public void allOf() {
        assertEquals(a.allOf(a), true);
        assertEquals(a.allOf(b), false);
        assertEquals(same.allOf(different), false);
        assertEquals(a.allOf(none), true);
        assertEquals(a.allOf(all), false);
        assertEquals(all.allOf(none), true);
        assertEquals(all.allOf(all), true);
        assertEquals(none.allOf(none), true);
    }

    @Test
    public void invert() {
        Bitfield temp = new Bitfield(same);
        temp.invert();
        assertEquals(temp, different);
    }

    @Test
    public void keep() {
        Bitfield temp = new Bitfield(a);
        temp.keep(b);
        temp.keep(temp);
        assertEquals(a.allOf(temp), true);
        assertEquals(b.allOf(temp), true);
    }

    @Test
    public void include() {
        Bitfield temp = new Bitfield(a);
        temp.include(b);
        temp.include(temp);
        assertEquals(temp.allOf(a), true);
        assertEquals(temp.allOf(b), true);
    }

    @Test
    public void not() {
        assertEquals(same.not(), different);
        assertNotEquals(a.not(), a);
        assertNotEquals(a.not(), b);
        assertEquals(a.not().not(), a);
    }

    @Test
    public void and() {
        assertEquals(a.and(b), b.and(a));
        assertEquals(a.and(a), a);
        assertEquals(same.allOf(a.and(b)), true);
        assertEquals(different.anyOf(a.and(b)), false);
    }

    @Test
    public void or() {
        assertEquals(a.or(b), b.or(a));
        assertEquals(a.or(a), a);
        assertEquals(same.allOf(a.or(b).not()), true);
        assertEquals(a.or(b).allOf(different), true);
    }

    @Test
    public void xor() {
        assertEquals(a.xor(b), b.xor(a));
        assertEquals(a.xor(b), different);
        assertEquals(a.xor(a), none);
    }

    @Test
    public void notAndOrXorRelations() {
        assertEquals(a.or(a.not()), all);
        assertEquals(a.and(b), a.not().or(b.not()).not());
        assertEquals(a.or(b), a.not().and(b.not()).not());
        assertEquals(a.xor(b), a.and(b.not()).or(b.and(a.not())));
    }

    @Test
    public void equals() {
        assertEquals(a, a);
        assertEquals(a.hashCode(), a.hashCode());
        assertEquals(a, new Bitfield(a));
        assertEquals(a.hashCode(), new Bitfield(a).hashCode());
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
        assertEquals(none, new Bitfield());
        assertEquals(none.hashCode(), new Bitfield().hashCode());
    }
}