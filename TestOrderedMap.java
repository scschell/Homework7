//Partners: Edward Schembor
//          eschemb1@jhu.edu
//          Sayge Schell
//          sschell3@jhu.edu

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestOrderedMap{

    private interface Fixture {
        OrderedMap<String, Integer> init();
	}
    
	@DataPoint
	public static final Fixture avlMap = new Fixture() {
        public AvlTreeMap<String, Integer> init() {
            return new AvlTreeMap<>();
		}
	};

	@DataPoint
	public static final Fixture treapMap = new Fixture() {
        public TreapMap<String, Integer> init() {
            return new TreapMap<>();
		}
	};

    //General Tests:

    @Theory
	public void createMaps(Fixture fix) {
	    OrderedMap<String, Integer> a = fix.init();
        assertEquals(a.size(), 0);
	}

    @Theory
	public void testInsert(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		a.insert("Abe", 10);
		assertEquals(a.size(), 1);
	}

	@Theory
	public void testHas(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		a.insert("Jay", 5);
		assertTrue(a.has("Jay"));
		assertEquals(a.size(), 1);
	}

	@Theory
	public void testGet(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		a.insert("A", 10);
		Integer i = 10;
		assertEquals(a.get("A"), i);
		assertEquals(a.size(), 1);
	}

	@Theory
	public void testPut(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		a.insert("A", 10);
		Integer i = 5;
		a.put("A", 5);
		assertEquals(a.get("A"), i);
		assertEquals(a.size(), 1);
	}

    @Theory
	public void testRemove(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		a.insert("A", 10);
		a.remove("A");
		assertFalse(a.has("A"));
		assertEquals(a.size(), 0);
	}

	@Theory
	public void testRemoveIntense(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		a.insert("D", 1);
		a.insert("E", 2);
		a.insert("G", 3);
		a.insert("B", 4);
		a.insert("A", 5);
		a.insert("C", 6);
		a.insert("F", 7);
		a.remove("A");
		assertEquals(a.toString(), "{B: 4, C: 6, D: 1, E: 2, F: 7, G: 3}");
		a.remove("B");
		assertEquals(a.toString(), "{C: 6, D: 1, E: 2, F: 7, G: 3}");
		a.remove("C");
		assertEquals(a.toString(), "{D: 1, E: 2, F: 7, G: 3}");
		a.remove("D");
		assertEquals(a.toString(), "{E: 2, F: 7, G: 3}");
		a.remove("E");
		assertEquals(a.toString(), "{F: 7, G: 3}");
		a.remove("F");
		assertEquals(a.toString(), "{G: 3}");
		a.remove("G");
		assertEquals(a.toString(), "{}");
	}

    @Theory
	public void testToString(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		a.insert("D", 1);
		a.insert("E", 2);
		a.insert("G", 3);
		a.insert("B", 4);
		a.insert("A", 5);
		a.insert("C", 6);
		a.insert("F", 7);
        System.out.println(a.toString());
		assertEquals(a.toString(), "{A: 5, B: 4, C: 6, D: 1, E: 2, F: 7, G: 3}");
	}

    //Iterator Tests:
    public void iteratorTest(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		Iterator<String> iter = a.iterator();
		a.insert("A", 10);
		assertTrue(iter.hasNext());
        assertEquals("A", iter.next());
		assertFalse(iter.hasNext());
	}

	//Exception Tests:

    @Theory @Test(expected = NoSuchElementException.class)
	public void iteratorExceptionTest(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		Iterator<String> iter = a.iterator();
		iter.next();
	}

    @Theory @Test(expected = IllegalArgumentException.class)
	public void hasNull(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		a.has(null);
	}

	@Theory @Test(expected = IllegalArgumentException.class)
	public void putNull(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		a.put(null, 10);
	}

    @Theory @Test(expected = IllegalArgumentException.class)
	public void getNull(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		a.get(null);
	}

	@Theory @Test(expected = IllegalArgumentException.class)
	public void insertNull(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		a.insert(null, 10);
	}

	@Theory @Test(expected = IllegalArgumentException.class)
	public void removeNull(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		a.remove(null);
	}

    @Theory @Test(expected = IllegalArgumentException.class)
	public void insertDuplicate(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
        a.insert("A", 1);
		a.insert("A", 2);
	}

	@Theory @Test(expected = IllegalArgumentException.class)
	public void removeNonInserted(Fixture fix) {
        OrderedMap<String, Integer> a = fix.init();
		a.remove("A");
	}

}
