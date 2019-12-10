import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A priority queue class implemented using a min heap.
 * Priorities cannot be negative.
 *
 * @author Jewell Day, Brody Pearman
 * @version 9/30/19
 *
 */
public class PriorityQueue {

	protected Map<Integer, Integer> location;
	protected List<Pair<Integer, Integer>> heap;

	/**
	 *  Constructs an empty priority queue
	 */
	public PriorityQueue() {
		heap =  new ArrayList<Pair<Integer, Integer>>();
		location = new HashMap<Integer, Integer>();
	}

	/**
	 *  Insert a new element into the queue with the
	 *  given priority.
	 *
	 *	@param priority priority of element to be inserted
	 *	@param element element to be inserted
	 *	<br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The element does not already appear in the priority queue.</li>
	 *	<li> The priority is non-negative.</li>
	 *	</ul>
	 *
	 */
	public void push(int priority, int element)
	{
		if(priority<0 || isPresent(element)){
			throw new AssertionError();
		}
		Pair newElement = new Pair<>(priority, element);
		heap.add(newElement);
		//moves to correct position in heap and saves location
		int loc = percolateUpLeaf();
		location.put(element, loc);
	}

	/**
	 *  Remove the highest priority element
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The priority queue is non-empty.</li>
	 *	</ul>
	 *
	 */
	public void pop(){
		if(isEmpty()){
			throw new AssertionError();
		}
		else {
			swap(0,size()-1);
			Pair p=heap.remove(size()-1);
			location.remove(p.element);
			pushDownRoot();
		}
	}


	/**
	 *  Returns the highest priority in the queue
	 *  @return highest priority value
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The priority queue is non-empty.</li>
	 *	</ul>
	 */
	public int topPriority() {
		if(isEmpty()){
			throw new AssertionError();
		}
		else {
			return heap.get(0).priority;
		
		}
	}


	/**
	 *  Returns the element with the highest priority
	 *  @return element with highest priority
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The priority queue is non-empty.</li>
	 *	</ul>
	 */
	public int topElement() {
		if(isEmpty()){
			throw new AssertionError();
		}
		else {
			return heap.get(0).element;
		
		}
	}


	/**
	 *  Change the priority of an element already in the
	 *  priority queue.
	 *
	 *  @param newpriority the new priority
	 *  @param element element whose priority is to be changed
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The element exists in the priority queue</li>
	 *	<li> The new priority is non-negative </li>
	 *	</ul>
	 */
	public void changePriority(int newpriority, int element) {
		if(newpriority<0 || isPresent(element)==false) {
			throw new AssertionError();
		}
		else {
			int index= location.get(element);
			int oldP= heap.get(index).priority;
			Pair p= new Pair(newpriority,element);
			heap.add(index,p);
			if (oldP < newpriority) {
				pushDown(index);
			}
			else {
				percolateUp(index);
				
			}
		}
	}


	/**
	 *  Gets the priority of the element
	 *
	 *  @param element the element whose priority is returned
	 *  @return the priority value
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The element exists in the priority queue</li>
	 *	</ul>
	 */
	public int getPriority(int element) {
		if(isPresent(element)==false) {
			throw new AssertionError();
		}
		int elementLocation = location.get(element);
		return heap.get(elementLocation).priority;
	}

	/**
	 *  Returns true if the priority queue contains no elements
	 *  @return true if the queue contains no elements, false otherwise
	 */
	public boolean isEmpty() {
		return heap.isEmpty();
	}

	/**
	 *  Returns true if the element exists in the priority queue.
	 *  @return true if the element exists, false otherwise
	 */
	public boolean isPresent(int element) {
		return location.containsKey(element);
	}

	/**
	 *  Removes all elements from the priority queue
	 */
	public void clear() {
		location.clear();
		heap.clear();
	}

	/**
	 *  Returns the number of elements in the priority queue
	 *  @return number of elements in the priority queue
	 */
	public int size() {
		return heap.size();
	}



	/*********************************************************
	 * 				Private helper methods
	 *********************************************************/


	/**
	 * Push down the element at the given position in the heap
	 * @param start_index the index of the element to be pushed down
	 * @return the index in the list where the element is finally stored
	 */
	private int pushDown(int start_index) {
		if(left(start_index)<heap.size()-1 && hasTwoChildren(start_index)==false) {
			if(heap.get(left(start_index)).priority< heap.get(start_index).priority){
				swap(start_index,left(start_index));
				return pushDown(left(start_index));
			}
			else{
				return start_index;
			}
		}
		else if (hasTwoChildren(start_index)){
			int priorityLeft= heap.get(left(start_index)).priority;
			int priorityRight= heap.get(right(start_index)).priority;
			int priorityParent= heap.get(start_index).priority;
			if(priorityLeft<=priorityRight && priorityLeft<priorityParent){
				swap(start_index,left(start_index));
				return pushDown(left(start_index));
			}
			else if (priorityRight<priorityLeft && priorityRight<priorityParent) {
				swap(start_index,right(start_index));
				return pushDown(right(start_index));
			}
			else {
				return start_index;
			}
		}
		else {
			return start_index;
		}

	}

	/**
	 * Percolate up the element at the given position in the heap
	 * @param start_index the index of the element to be percolated up
	 * @return the index in the list where the element is finally stored
	 */
	private int percolateUp(int start_index) {
		if(start_index > 0 && heap.get(start_index). priority< heap.get(parent(start_index)).priority)
		{
			swap(start_index,parent(start_index));
			return percolateUp(parent(start_index));
		}
		return start_index;
	}


	/**
	 * Swaps two elements in the priority queue by updating BOTH
	 * the list representing the heap AND the map
	 * @param i The index of the element to be swapped
	 * @param j The index of the element to be swapped
	 */
	private void swap(int i, int j) {
		//gets elements
		Pair e1 = heap.get(i);
		Pair e2 = heap.get(j);
		//swaps them in heap
		heap.remove(i);
		heap.add(i,e2);
		heap.remove(j);
		heap.add(j,e1);
		//removes and re-adds their mappings
		location.remove(e1.element);
		location.remove(e2.element);
		location.put((int)e1.element, j);
		location.put((int)e2.element, i);
	}

	/**
	 * Computes the index of the element's left child
	 * @param parent index of element in list
	 * @return index of element's left child in list
	 */
	private int left(int parent) {
		return (parent*2)+1;
	}

	/**
	 * Computes the index of the element's right child
	 * @param parent index of element in list
	 * @return index of element's right child in list
	 */
	private int right(int parent) {
		return parent*2+2;
	}

	/**
	 * Computes the index of the element's parent
	 * @param child index of element in list
	 * @return index of element's parent in list
	 */

	private int parent(int child) {
		return (child-1)/2;
	}


	/*********************************************************
	 * 	These are optional private methods that may be useful
	 *********************************************************/


	/**
	 * Push down the root element
	 * @return the index in the list where the element is finally stored
	 */
	private int pushDownRoot() {
		return pushDown(0);// TODO: A one-line function that calls pushDown()
	}

	/**
	 * Percolate up the last leaf in the heap, i.e. the most recently
	 * added element which is stored in the last slot in the list
	 *
	 * @return the index in the list where the element is finally stored
	 */
	private int percolateUpLeaf(){
		return percolateUp(heap.size()-1);// TODO: A one-line function that calls percolateUp()
	}

	/**
	 * Returns true if element is a leaf in the heap
	 * @param i index of element in heap
	 * @return true if element is a leaf
	 */
	private boolean isLeaf(int i){
		if(left(i) > heap.size() -1)
		{
			return true;
		}
		return false;
	}

	/**
	 * Returns true if element has two children in the heap
	 * @param i index of element in the heap
	 * @return true if element in heap has two children
	 */
	private boolean hasTwoChildren(int i) {
		int left=left(i);
		int right=right(i);
		int sizee=heap.size();
		if(left<sizee-1 && right<sizee-1){
			return true;
		}
		return false;
	}

	/**
	 * Print the underlying list representation
	 */
	private void printHeap() {
		for(Pair p: heap){
			int a= (int)p.element;
			int b=(int)p.priority;
			String printable="(" + a + ", " + b + ")";
			System.out.println(printable);
		}
	}

	/**
	 * Print the entries in the location map
	 */
	private void printMap() {
		for(Integer key: location.keySet()){
			String val= location.get(key).toString();
			System.out.println("(" + key.toString() + ", " + val+ ")");
		}
	}


}
