/**
 * Tests Ordered maps.
 * @author Edward Schembor < eschemb1@jhu.edu >
 * @author Sayge Schell < sschell3@jhu.edu >
 * Data Structures 600.226, Assignment 7
 * March 4th, 2014
 */
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
        OrderedMap<Integer, Integer> init();
	}
    
	@DataPoint
	public static final Fixture binaryMap = new Fixture() {
        public AvlTreeMap<Integer, Integer> init() {
            return new AvlTreeMap<>();
		}
	};
	
    private interface Fixture {
        OrderedMap<Integer, Integer> init();
	}
    
	@DataPoint
	public static final Fixture treapMap = new Fixture() {
        public TreapmapMap<Integer, Integer> init() {
            return new TreapMap<>();
		}
	};

    //checks that size of created map is 0
    @Theory
	public void createMaps(Fixture fix) {
	    OrderedMap<Integer, Integer> a = fix.init();
        assertEquals(a.size(), 0);
	}
    //General Tests:
    
    //checks that size works
    @Theory
	public void testInsert(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
        for (int i = 0; i < 25; i++) {
            assertEquals(a.size(), i);
            a.insert(i, (i+1));
        	assertEquals(a.size(), i+1);
        }
	}
    
    //checks if the has method works
	@Theory
	public void testHas(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();   
		for (int i = 0; i < 25; i++) {
            assertFalse(a.has(i));
            a.insert(i, i+1);
            assertTrue(a.has(i));
        }
        assertEquals(a.size(), 25);
	}

    //checks if the get method works
	@Theory
	public void testGet(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
		for (int i = 0; i < 25; i++) {
            a.insert(i, i +1);
            assertEquals(a.get(i), (Integer) (i+1));
        }
        assertEquals(a.size(), 25);
	}
    
    //checks if the put method works
	@Theory
	public void testPut(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
        for (int i = 0; i < 25; i++) {
            a.insert(i, i+1);
            a.put(i, 27);
            assertEquals(a.get(i), (Integer) 27);
        }
        assertEquals(a.size(), 25);
	}
    
    //checks that remove works
    @Theory
	public void testRemove(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
        for (int i = 0; i < 25; i++) {
            a.insert(i, i+1);
            assertTrue(a.has(i));
        }
        for (int i = 0; i < 25; i++) {
            a.remove(i);
            assertFalse(a.has(i));
        }
		assertEquals(a.size(), 0);
	}

    //thoroughly checks that remove works
   	@Theory
	public void testRemoveIntense(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
        for (int i = 0; i < 10; i++) {
            a.insert(i, i+1);
        }
        a.remove(0);
		assertEquals(a.toString(), "{1: 2, 2: 3, 3: 4, 4: 5, 5: 6, 6: 7, 7: 8, 8: 9, 9: 10}");
		a.remove(1);
		assertEquals(a.toString(), "{2: 3, 3: 4, 4: 5, 5: 6, 6: 7, 7: 8, 8: 9, 9: 10}");
		a.remove(2);
		assertEquals(a.toString(), "{3: 4, 4: 5, 5: 6, 6: 7, 7: 8, 8: 9, 9: 10}");
		a.remove(3);
		assertEquals(a.toString(), "{4: 5, 5: 6, 6: 7, 7: 8, 8: 9, 9: 10}");
		a.remove(4);
		assertEquals(a.toString(), "{5: 6, 6: 7, 7: 8, 8: 9, 9: 10}");
		a.remove(5);
		assertEquals(a.toString(), "{6: 7, 7: 8, 8: 9, 9: 10}");
		a.remove(6);
        assertEquals(a.toString(), "{7: 8, 8: 9, 9: 10}");
        a.remove(7);
        assertEquals(a.toString(), "{8: 9, 9: 10}");
        a.remove(8);
        assertEquals(a.toString(), "{9: 10}");
        a.remove(9);
		assertEquals(a.toString(), "{}");
	}

    //checks that toString works
    @Theory
	public void testToString(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
		for (int i = 0; i < 10; i++) {
            a.insert(i, i+1);
        }
        System.out.println(a.toString());
		assertEquals(a.toString(), "{0: 1, 1: 2, 2: 3, 3: 4, 4: 5, 5: 6, 6: 7, 7: 8, 8: 9, 9: 10}");
	}

    //Iterator Tests:

    //checks that iterator works
    public void iteratorTest(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
		Iterator<Integer> iter = a.iterator();
        for (int i = 0; i < 25; i++) {
		    a.insert(i, i+1);
		    assertTrue(iter.hasNext());
            assertEquals((Integer) i, iter.next());
        }
		assertFalse(iter.hasNext());
	}

	//Exception Tests:
    
    //checks that exception is thrown when iterator reaches end of list
    @Theory @Test(expected = NoSuchElementException.class)
	public void iteratorExceptionTest(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
		Iterator<Integer> iter = a.iterator();
		iter.next();
	}
    
    //checks that exception is thrown when null key is passed to has method 
    @Theory @Test(expected = IllegalArgumentException.class)
	public void hasNull(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
		a.has(null);
	}

    //checks that exception is thrown when null key is passed into put method
  	@Theory @Test(expected = IllegalArgumentException.class)
	public void putNull(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
		a.put(null, 10);
	}

    //checks that exception is thrown when null key is passed into get method
    @Theory @Test(expected = IllegalArgumentException.class)
	public void getNull(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
		a.get(null);
	}

    //checks that exception is thrown when null key is passed into insert method
	@Theory @Test(expected = IllegalArgumentException.class)
	public void insertNull(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
		a.insert(null, 10);
	}

    //checks that exception is thrown when null key is passed into remove method
	@Theory @Test(expected = IllegalArgumentException.class)
	public void removeNull(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
		a.remove(null);
	}

    //checks that exception is thrown when duplicate key is used for insert
    @Theory @Test(expected = IllegalArgumentException.class)
	public void insertDuplicate(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
        a.insert(2, 1);
		a.insert(2, 2);
	}

    //checks that exception is thrown when trying to remove a key that doesn't exist
	@Theory @Test(expected = IllegalArgumentException.class)
	public void removeNonInserted(Fixture fix) {
        OrderedMap<Integer, Integer> a = fix.init();
		a.remove(0);
	}

}
