package hw6;
import static org.junit.Assert.*;
import org.junit.*;
import java.util.PriorityQueue;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Arrays;
/**
 *  Title: class HeapPQ12 Tester
 *  Description: JUnit test class for HeapPQ12 class
 *  @author Philip Papadopoulos
 *  @version 2.0 01-May-2014 
 */
public class HeapPQ12_SubmissionTester
{
    private AbstractQueue<Integer> empty ;
    private AbstractQueue<Integer> one ;
    private AbstractQueue<Integer> several ;
    private AbstractQueue<Integer> dups ;
    private AbstractQueue<String>  sheap ;
    private static final int HEAPDIM = 48;
    private static final int DIM = 500;
    private static final int DEVIL = 666;
    private static final boolean testHeapPQ12 = true;
    private static final String[] s={"Alice","Bob","Cal","David","Ethan"};
    private HeapPQ12<Integer> maxHeap;
    private static final int MAX = 55;
    private static int[] testSeq={MAX-10, MAX-5, MAX-11, MAX, MAX-18, MAX-4, 1 ,2};
    static int points = 0;

    public HeapPQ12_SubmissionTester()
    {
        super() ;
    }

    private static class HeapFactory<T extends Comparable <? super T>>
    {
        public AbstractQueue<T> manufacture()
        {
            if (testHeapPQ12) return new HeapPQ12<T>();
            else return new PriorityQueue<T>();
        }
    }
    /**
     * Standard Test Fixture. An empty heap, a heap with one entry (0) and 
     * a heap with several entries (0,1,2,...HEAPDIM), a heap with dups
     * 0,1,2,..., HEAPDIM, 0,1,2,...., HEAPDDIM
     */ 
    @Before
    public void setUp() throws Exception
    {
        empty = new HeapFactory<Integer>().manufacture();
        one = new HeapFactory<Integer>().manufacture();
        if (!one.offer(new Integer(0)))
            throw new Exception("one: fixture initialize");
        several = new HeapFactory<Integer>().manufacture();
        dups = new HeapFactory<Integer>().manufacture();
        // HEAP: 1,2,3,...,Dim
        for (int i = HEAPDIM; i > 0; i--)
            if (!several.offer(new Integer(i)))
                throw new Exception("".format("several: fixture initialize %d", i));
        for (int i = HEAPDIM; i > 0; i--)
        {
            if (!dups.offer(new Integer(i)))
                throw new Exception("".format("dups-a: fixture init %d", i));
            if (!dups.offer(new Integer(i)))
                throw new Exception("".format("dups-b: fixture init %d", i));
        }
            

        // Heap: "Alice","Bob","Cal","David","Ethan"
        sheap = new HeapFactory<String>().manufacture(); 
        for (int i = 0; i < s.length; i++)
            if (!sheap.offer(s[i]))
                throw new Exception("".format("sheap: fixture init %s", s[i]));

        if (testHeapPQ12) 
        {
            maxHeap = new HeapPQ12<Integer>(true); 
            for (int i = 0; i < testSeq.length; i++)
                maxHeap.offer(new Integer(testSeq[i]));
        }
    }
    /** Test if heads of the lists are correct */
    @Test
    public void testPoll()
    {
        System.out.println("Check poll");
        assertEquals("Check poll (null)",(Integer) null,empty.poll()) ;
        assertEquals("Check poll (one)",new Integer(0),one.poll()) ;

     	assertEquals("Check poll (several)0",new Integer(1),several.poll()) ;
        assertEquals("Check poll (dups)",new Integer(1),dups.poll()) ;
        assertEquals("Check poll (sheap)0",s[0],sheap.poll()) ;

    }

    /** Test if heads of the lists are correct */
    @Test
    public void testPeek()
    {
        System.out.println("Check peek");
        assertEquals("Check peek (null)",(Integer) null,empty.peek()) ;
        assertEquals("Check peek2 (null)",(Integer) null,empty.peek()) ;

     	assertEquals("Check peek (one)",new Integer(0),one.peek()) ;
        assertEquals("Check peek2 (one)",new Integer(0),one.peek()) ;

     	assertEquals("Check peek (several)0",new Integer(1),several.peek()) ;
        assertEquals("Check peek2 (several)0",new Integer(1),several.peek()) ;

     	assertEquals("Check peek (dups)",new Integer(1),dups.peek()) ;
        assertEquals("Check peek2 (dups)",new Integer(1),dups.peek()) ;

     	assertEquals("Check peek (sheap)0",s[0],sheap.peek()) ;
        assertEquals("Check peek2 (sheap)0",s[0],sheap.peek()) ;
    }
    /** Test if size of lists are correct */
    @Test
    public void testHeapSize()
    {
        System.out.println("Check heap size");
        assertEquals("Check Empty Size",0,empty.size()) ;
        assertEquals("Check One Size",1,one.size()) ;
        assertEquals("Check Several Size",HEAPDIM,several.size()) ;
        assertEquals("Check Duplicates Size",2*HEAPDIM,dups.size()) ;
        assertEquals("Check sheap Size",s.length,sheap.size()) ;
    }

    
    /** Test isEmpty */
    @Test
    public void testEmpty()
    {
        System.out.println("Check if empty");
        assertTrue("empty is empty",empty.isEmpty()) ;
        assertTrue("one is not empty",!one.isEmpty()) ;
        assertTrue("several is not empty",!several.isEmpty()) ;
    }
    /** Test Clear */
    @Test
    public void testClear()
    {
        System.out.println("Check clear");
        empty.clear();
        one.clear();
        several.clear();
        assertTrue("empty not cleared",empty.isEmpty()) ;
        assertTrue("one not cleared",one.isEmpty()) ;
        assertTrue("several not cleared",several.isEmpty()) ;
    }

    /** Test if size of lists are correct */
    @Test
    public void testIterator()
    {
        System.out.println("Check iterator");
        Iterator<String> iter = sheap.iterator();
        int expected = s.length;
        int i = 0;
        while (iter.hasNext())
        {
            iter.next();
            i++;
        }
        assertEquals("testIterator: incorrect #elements", expected,i++);
    }   
}


// VIM: set the tabstop and shiftwidth to 4 
// vim:tw=78:ts=4:et:sw=4