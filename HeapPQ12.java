/**
 * A file to implement HeapPQ12 class.
 */
package hw6;

import java.util.ArrayList;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

/** HeapPQ12 class that implements an unbounded array-backed heap structure and is
 * an extension of the Java Collections AbstractQueue class 
 * <p>
 * The elements of the heap are ordered according to their natural 
 * ordering,  HeapPQ12 does not permit null elements. 
 * The top of this heap is the minimal or maximal element (called min/max)  
 * with respect to the specified natural ordering.  
 * If multiple elements are tied for min/max value, the top is one of 
 * those elements -- ties are broken arbitrarily. 
 * The queue retrieval operations poll and  peek 
 * access the element at the top of the heap.
 * <p>
 * A HeapPQ12 is unbounded, but has an internal capacity governing the size of 
 * an array used to store the elements on the queue. It is always at least as 
 * large as the queue size. As elements are added to a HeapPQ12, its capacity 
 * grows automatically. The details of the growth policy are not specified.
 * <p>
 * This class and its iterator implements the optional methods of the 
 * Iterator interface (including remove()). The Iterator provided in method 
 * iterator() is not guaranteed to traverse the elements of the HeapPQ12 in 
 * any particular order. 
 * <p>
 * Note that this implementation is not synchronized. Multiple threads 
 * should not access a HeapPQ12 instance concurrently if any of the 
 * threads modifies the HeapPQ12.
 * @author Chaitanya
 */
public class HeapPQ12<E extends Comparable <? super E>> extends 
    AbstractQueue<E> 
{
    
	
	private ArrayList<E> heap;
	private int nelems; //Tells us the amount of elements in the heap, that is the.
	private int capacity; //Because we are using the arraylist as if it was an array, this variable will tell us the actual length of the arraylist in which the heap is implemented.
	private boolean isMaxHeap;
	
	/* Also note that the heap will run from index 1 of the arraylist and not 0, therefore the total number of nelemes will be same as the index of the last element.*/ 
    /** O-argument constructor. Creates an empty HeapPQ12 with specified
     *  initial capacity of 5, and is a min-heap 
     */ 
    public HeapPQ12()
    {
    	declareHeap(5, false);
    	
    }

    /** 
     * Constructor to build a min or max heap
     * @param isMaxHeap   if true, this is a max-heap, else a min-heap 
     */ 
    public HeapPQ12(boolean isMaxHeap)
    {
    	declareHeap(5, isMaxHeap);
    }

    /** 
     * Constructor to build a with specified initial capacity 
     *  min or max heap
     * @param capacity      initial capacity of the heap.   
     * @param isMaxHeap     if true, this is a max-heap, else a min-heap 
     * @throws IllegalArgumentException	if the initial capacity is less than 1
     */ 
    public HeapPQ12(int capacity, boolean isMaxHeap)
    {
    	if(capacity<1)
    	{
    		throw new IllegalArgumentException();
    	}
    	else
    	{
    		declareHeap(capacity, isMaxHeap);
    	}
    	
    }
    
    
    /** Copy constructor. Creates HeapPQ12 with a deep copy of the argument
     * @param toCopy      the heap that should be copied
     */
    public HeapPQ12 (HeapPQ12<E> toCopy)
    {
    	this.declareHeap(toCopy.capacity(), toCopy.isMaxHeap());
    	this.nelems = toCopy.size();
    	for(int i=0; i<=toCopy.size(); i++)
    	{
    		this.heap.set(i, toCopy.heap.get(i));
    	}    	
    }
    
    /**
     * Because we have to several constructors, let us define a general method to initialize the heap and its private member variables.
     * @param Length Length of the heap to be declared, i.e. the length of the array
     * @param isMaxHeap A boolean value showing if the heap to be declared is a maxHeap
     */
    private void declareHeap(int length, boolean isMaxHeap)
    {
    	this.heap = new ArrayList<>(length+1); //We will not make it dynamically sized because we are using arraylist with the behavior of an array.
    	this.nelems = 0;
    	this.capacity = length;
    	this.isMaxHeap = isMaxHeap;
    	for(int i=0; i<(length+1); i++)
    	{
    		this.heap.add(0, null);
    	}
    }
    /* The following are defined "stub" methods that provide degenerate
     * implementations of methods defined as abstract in parent classes.
     * These need to be coded properly for HeapPQ12 to function correctly
     */
   

    /** Size of the heap
     * @return the number of elements stored in the heap
    */
    public int size()
    {
        return this.nelems; 
    }
    
    public int capacity()
    {
    	return this.capacity;
    }
    
    public boolean isMaxHeap()
    {
    	return this.isMaxHeap;
    }

    /** 
     * @return an Iterator for the heap 
    */
    public Iterator<E> iterator()
    {
        return new HeapPQ12Iterator(); 
    }

    /** peek - see AbstractQueue for details 
     * 
     * @return Element at top of heap. Do not remove 
    */
    public E peek()
    {
        if(this.size()==0)
        {
        	return null;
        }
        else
        {
        	return this.heap.get(1);
        }
    }
    
    /**
     * printheap - A method to print the entire heap
     */
    public void printheap()
    {
    	for(int i=1; i<=this.nelems; i++)
    	{
    		System.out.println(this.heap.get(i));
    	}
    }
    
    /** poll - see AbstractQueue for details 
     * @return Element at top of heap. And remove it from the heap. 
     *  return <tt>null</tt> if the heap is empty
    */
    public E poll()
    {
        if(this.size()==0)
        {
        	return null;
        }
        else
        {
        	E first_value = this.heap.get(1);
        	this.heap.set(1, this.heap.get(nelems));
        	this.nelems--;
        	trickleDown(1, this.isMaxHeap);        	
        	return first_value;
        }
    }
    /** offer -- see AbstractQueue for details
     * insert an element in the heap
     * @return true
     * @throws NullPointerException 
     *  if the specified element is null    
     */
    public boolean offer (E e)
    {
        if(e==null)
        {
        	throw new NullPointerException();
        }
        else 
        {
        	if(this.size()>=this.capacity)
        	{
        		this.doubleSize();
        	}
        	this.nelems++;
        	this.heap.set(this.size(), e);
        	bubbleUp(this.size(), this.isMaxHeap);        	
        	return true;
        }
    }

    /**
     * A method to double the size of the heap when the number of elements exceeds the capacity.
     */
    private void doubleSize()
    {
    	
    	this.capacity = capacity*2;
    	ArrayList<E> temp = new ArrayList<>(this.capacity()+1);
    	for(int j=0;j<=this.capacity()+1;j++)
    	{
    		temp.add(0, null);
    	}
    	for(int i=1; i<=this.size(); i++)
    	{
    		temp.set(i, this.heap.get(i));
    	}
    	this.heap = temp;
    	
    	
    }
    
    /**
     * A method to compare and swap elements in heap according to the properties of heap in down to up order.
     * @param idx Index of heap to take upwards
     * @param isMaxHeap A boolean to indicate whether the heap is a min or max heap
     */
    private void bubbleUp(int idx, boolean isMaxHeap)
    {
    	if(!isMaxHeap)
    	{
    		if(parent(idx)<1)
    		{
    			return;
    		}
    		else if(this.heap.get(idx).compareTo(this.heap.get(parent(idx))) >= 0)
    		{
    			return;
    		}
    		else if(this.heap.get(idx).compareTo(this.heap.get(parent(idx))) < 0)
    		{
    			//System.out.println("Added" + this.heap.get(idx));
    			//System.out.println("Comparing child " + heap.get(idx) + "to parent " + heap.get(parent(idx)));
    			swap(idx, parent(idx));
    			//System.out.println("Swapping child" + heap.get(idx) + "to parent " + heap.get(parent(idx)));
    			idx = parent(idx);
    			//System.out.println("Next");
    			//System.out.println(" ");
    		}
    		bubbleUp(idx, isMaxHeap);
    		
    	}
    	else if(isMaxHeap)
    	{
    		if(parent(idx)<1)
    		{
    			return;
    		}
    		else if(this.heap.get(idx).compareTo(this.heap.get(parent(idx))) <= 0)
    		{
    			return;
    		}
    		else if(this.heap.get(idx).compareTo(this.heap.get(parent(idx))) > 0)
    		{
    			swap(idx, parent(idx));
    			idx = parent(idx);
    		}
    		bubbleUp(idx, isMaxHeap);
    	}
    }
    
    /**
     * A method to compare and swap elements in heap according to the properties of heap in up to down order.
     * @param Index of heap to take downwards
     * @param isMaxHeap A boolean to indicate whether the heap is a min or max heap
     */
    private void trickleDown(int idx, boolean isMaxHeap)
    {
    	if(!isMaxHeap)
    	{
    		if(leftchild(idx)>this.size() || leftchild(idx)>this.capacity())
        	{
        		return;
        	}
        	else if(this.heap.get(idx).compareTo(this.heap.get(leftchild(idx)))<=0 && this.heap.get(idx).compareTo(this.heap.get(rightchild(idx)))<=0)
        	{
        		return;
        	}
        	else
        	{
        		if(this.heap.get(leftchild(idx)).compareTo(this.heap.get(rightchild(idx)))<0)
        		{
        			swap(idx, leftchild(idx));
        			idx = leftchild(idx);
        		}
        		else
        		{
        			
        			swap(idx, rightchild(idx));
        			idx = rightchild(idx);
        		}
        	}
        	trickleDown(idx, this.isMaxHeap());
    	}
    	else if(isMaxHeap)
    	{
    		if(leftchild(idx)>this.size() || leftchild(idx)>this.capacity())
        	{
        		return;
        	}
        	else if(this.heap.get(idx).compareTo(this.heap.get(leftchild(idx)))>=0 && this.heap.get(idx).compareTo(this.heap.get(rightchild(idx)))>=0)
        	{
        		return;
        	}
        	else
        	{
        		
        		if(this.heap.get(leftchild(idx)).compareTo(this.heap.get(rightchild(idx)))>0)
        		{
        			swap(idx, leftchild(idx));
        			idx = leftchild(idx);
        		}
        		else
        		{        			
        			swap(idx, rightchild(idx));
        			idx = rightchild(idx);
        		}
        	}
        	trickleDown(idx, this.isMaxHeap());
    	}
    	
    }
    
    /**
     * A method to swap two elements in the heap
     * @param first First element to swap
     * @param second Second element to swap
     */
    private void swap(int first, int second)
    {
    	E temp = this.heap.get(first);
    	this.heap.set(first, this.heap.get(second));
    	this.heap.set(second, temp);
    }
    /**
     * A method that gives the index of the parent of the provided index number.
     * @param idx Index whose parent element's index is returned
     * @return Index of parent element
     */
    private int parent(int idx)
    {
    	return (idx/2);
    }
    
    /**
     * A method that returns the left child of the argument index
     * @param idx Index of the parent 
     * @return Index of the left child
     */
    private int leftchild(int idx)
    {
    	return (idx*2);
    }
    
    /**
     * A method that returns the right child of the argument index
     * @param idx Index of the parent
     * @return Index of the right child
     */
    private int rightchild(int idx)
    {
    	return ((idx*2)+1);
    }
    
    
    /** Inner Class for an Iterator **/
    /**
     * A class to implement iterator for the HeadPQ12 class
     * @author Chaitanya
     *
     */
    private class HeapPQ12Iterator implements Iterator<E>
    {
        int iterator_index;
        boolean canRemove;
    	private HeapPQ12Iterator()
        {
    		this.iterator_index=0;
    		this.canRemove = false;
    		
        }

        /* hasNext() to implement the Iterator<E> interface */
    	/**
    	 * A method to check if a call to next() would return a value.
    	 * @return Boolean indicating if a next element exists
    	 */
        public boolean hasNext()
        {
            if(this.iterator_index<HeapPQ12.this.size())
            {
            	return true;
            }
            else
            {
            	return false;
            }
        }

        /* next() to implement the Iterator<E> interface */
        /**
         * A method to return the next element in the heap
         * @return Next element in the heap
         * @throws NoSuchElementException when there is no next element
         */
        public E next() throws NoSuchElementException
        {
            if(!this.hasNext())
            {
            	throw new NoSuchElementException();
            }
            else
            {
            	iterator_index++;
            	this.canRemove = true;
            	return HeapPQ12.this.heap.get(iterator_index);
            }
            
        }
        /* remove() to implement the Iterator<E> interface */
        /**
         * A method to remove an element from the heap
         * @throws IllegalStateException if a call to next has not been made after the last call to remove
         */
        public void remove() throws IllegalStateException
        {
        	if(this.canRemove)
        	{
        		HeapPQ12.this.heap.set(iterator_index, HeapPQ12.this.heap.get(nelems));
        		HeapPQ12.this.nelems--;
        		HeapPQ12.this.trickleDown(iterator_index, HeapPQ12.this.isMaxHeap());
        		this.canRemove = false;
        	}
        	else
        	{
        		throw new IllegalStateException();
        	}
        }   
    }
} 
