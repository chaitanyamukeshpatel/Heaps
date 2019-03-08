/**
 * A file to implement a class for testing HeapPQ12 class.
 */
package hw6;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * A class to check the implmentation of the HeapPQ12 class
 * @author Chaitanya
 *
 */
public class HeapPQTester {
	private HeapPQ12<Integer> heap1, heap2, heap3;
 	@Before
	public void setUp()
	{
		heap1 = new HeapPQ12<>();
		heap2 = new HeapPQ12<>();
     	
	}
	@Test
	public void testsize()
	{
		assertEquals("Initially the heap should be empty",0, heap1.size());
		heap1.offer(1);
		assertEquals("As we added and element, now the size should be 1", 1, heap1.size());
	} 
	@Test
	public void testOfferException()
	{
		try
		{
			heap1.offer(null);
			fail("Should throw a NullPointerException");
		}
		catch(NullPointerException e)
		{
			//Empty
		}
	}
 	
 	@Test
 	public void testOffer()
    {
 		assertTrue(heap1.offer(1));
     	assertTrue(heap2.offer(2));
     	//Now if the element is added, when we use poll, these elements will be returned.
     	assertEquals("1 should be returned", new Integer(1) , heap1.poll());
     	assertEquals("2 should be returned", new Integer(2) , heap2.poll());  
    }
 	
 	@Test
 	public void testPeek()
    {
 		heap1.offer(1);
     	assertEquals("The element on the top should be 1", new Integer(1) , heap1.peek());    
    }
 	@Test
 	public void testConstructorMaxHeap()
    {
 		heap3 = new HeapPQ12<>(true);
     	//So this is a max heap, when we add elements, the largest element should remain on top
     	heap3.offer(5);
     	heap3.offer(1);
     	assertEquals("The element on top should be five for max heap", new Integer(5), heap3.peek());     	
    }
 	@Test
 	public void testConstructorMinHeap()
    {
 		heap3 = new HeapPQ12<>(false);
     	//So this is a min heap, when we add elements, the smallest element should remain on top
     	heap3.offer(5);
     	heap3.offer(1);
     	assertEquals("The element on top should be five for max heap", new Integer(1), heap3.peek());     	
    }
 	@Test
 	public void testPollEmpty()
    {
    	assertEquals("When we poll on empty heap, null is returned", null, heap1.poll());    
    }
 	@Test
 	public void testPeekEmpty()
    {
    	assertEquals("When we peek on empty heap, null is returned", null, heap1.peek());    
    }
 	@Test
 	public void testPollMaxHeap()
    {
 		heap3 = new HeapPQ12<>(true);
     	//So this is a max heap, when we add elements, the largest element should remain on top
     	heap3.offer(5);
     	heap3.offer(1);
     	assertEquals("The element returned by poll is the one on top", new Integer(5), heap3.poll());
     	assertEquals("The element returned by poll is the one on top", new Integer(1), heap3.poll());
    }
 	@Test
 	public void testPollMinHeap()
    {
 		heap3 = new HeapPQ12<>(false);
     	//So this is a max heap, when we add elements, the largest element should remain on top
     	heap3.offer(5);
     	heap3.offer(1);
     	heap3.offer(2);
     	assertEquals("The element returned by poll is the one on top", new Integer(1), heap3.poll());
     	assertEquals("The element returned by poll is the one on top", new Integer(2), heap3.poll());
     	assertEquals("The element returned by poll is the one on top", new Integer(5), heap3.poll());
    }
 	@Test
 	public void testIterator()
 	{
 		for(int i=0; i<10; i++)
 		{
 			heap1.offer(i);
 		}
 		Iterator it = heap1.iterator();
 		for(int j=0; j<10; j++)
 		{
 			it.next();
 		}
 		
 		assertFalse("As the iterator has already traversed through the entire heap, there should not be an element next", it.hasNext());
 		
 	}
 	@Test
 	public void testRemoveException()
 	{
 		Iterator it = heap1.iterator();
 		try
		{
			it.remove();
			fail("Should throw an IllegalStateException as next is not called before the call to remove");
		}
		catch(IllegalStateException e)
		{
			//Empty
		}
 	}
 	@Test
 	public void testNextException()
 	{
 		for(int i=0; i<10; i++)
 		{
 			heap1.offer(i);
 		}
 		Iterator it = heap1.iterator();
 		for(int j=0; j<10; j++)
 		{
 			it.next();
 		}
 		try
		{
			it.next();
			fail("Should throw a NoSuchElementException as the iterator has already traversed through the entire heap");
		}
		catch(NoSuchElementException e)
		{
			//Empty
		}
 		
 	}
 	
 	
 	
 
 
 	
}
