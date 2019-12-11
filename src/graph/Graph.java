package graph;
import java.util.HashMap; 
import java.util.ArrayList;
import java.util.Map; 
import java.util.List;
import java.util.Set;
import java.util.Random;

//Authors: Brody Pearman and Jewell Day
//Version: 10/9/19
public class Graph<V> implements GraphIfc<V>
{
	//Storing our graph in a map
	private  Map<V,ArrayList<V>> adjList;
	private int numEdges;
	public Graph() {
		adjList= new HashMap<V,ArrayList<V>>();
		numEdges=0;
	}
	/**
	 * Returns the number of vertices in the graph
	 * @return The number of vertices in the graph
	 */
	public int numVertices(){
		//Number of vertices is the size of the map 
		return adjList.size();
	}	
		
	/**
	 * Returns the number of edges in the graph
	 * @return The number of edges in the graph
	 */
	public int numEdges(){
		return numEdges;
	}	
	
	/**
	 * Removes all vertices from the graph
	 */
	public void clear(){
		adjList.clear();
	}	
		
	/** 
	 * Adds a vertex to the graph. This method has no effect if the vertex already exists in the graph. 
	 * @param v The vertex to be added
	 */
	public void addVertex(V v){
		//make sure they give a vertex
		if(v==null){
			throw new IllegalArgumentException();
		}
		//if the adj list does not have a vertex
		else if (adjList.containsKey(v)==false){
			adjList.put(v,new ArrayList());
		}
	}
	
	/**
	 * Adds an edge between vertices u and v in the graph, does nothing if edge already exists
	 * @param u A vertex in the graph
	 * @param v A vertex in the graph
	 * @throws IllegalArgumentException if either vertex does not occur in the graph
	 */
	public void addEdge(V u, V v){
		//if either vertex is not in the graph
		if(adjList.containsKey(u)==false || adjList.containsKey(v)==false){
			throw new IllegalArgumentException();
		}
		//if there is no edge between them already	
		else if(edgeExists(u, v) == false){
			ArrayList<V> potList= adjList.get(u);
			potList.add(v);
			adjList.put(u,potList);
			numEdges+=1;
		}
	}

	/**
	 * Returns the set of all vertices in the graph.
	 * @return A set containing all vertices in the graph
	 */
	public Set<V> getVertices(){
		//all of the vertices are keys in adjlist
		return adjList.keySet();
	}
	
	/**
	 * Returns the neighbors of v in the graph. A neighbor is a vertex that is connected to
	 * v by an edge. If the graph is directed, this returns the vertices u for which an 
	 * edge (v, u) exists.
	 *  
	 * @param v An existing node in the graph
	 * @return All neighbors of v in the graph.
	 * @throws IllegalArgumentException if the vertex does not occur in the graph
	 */
	public List<V> getNeighbors(V v){
		//if the vertex is not in our adj list
		if(adjList.containsKey(v)==false){
		   throw new IllegalArgumentException();
		}
		//return the lsit stored at the key v
		return adjList.get(v);
	}

	/**
	 * Determines whether the given vertex is already contained in the graph. The comparison
	 * is based on the <code>equals()</code> method in the class V. 
	 * 
	 * @param v The vertex to be tested.
	 * @return True if v exists in the graph, false otherwise.
	 */
	public boolean containsVertex(V v){
		//if our map has this key
		if(adjList.containsKey(v)){
			return true;
		}
		return false;
	}
	
	/**
	 * Determines whether an edge exists between two vertices. In a directed graph,
	 * this returns true only if the edge starts at v and ends at u. 
	 * @param v A node in the graph
	 * @param u A node in the graph
	 * @return True if an edge exists between the two vertices
	 * @throws IllegalArgumentException if either vertex does not occur in the graph
	 */
	public boolean edgeExists(V v, V u){
		//if either vertice is not in the map
		if(adjList.containsKey(v)==false || adjList.containsKey(u)==false){
			throw new IllegalArgumentException();
		}
		//get the arraylist at the node v
		ArrayList<V> node= adjList.get(v);
		//loop through all of its edges to see if u is one
		for(int i=0;i<node.size();i++){
			if(node.get(i)==u){
				return true;
		}
        }
		return false;
	}

	/**
	 * Returns the degree of the vertex. In a directed graph, this returns the outdegree of the
	 * vertex. 
	 * @param v A vertex in the graph
	 * @return The degree of the vertex
	 * @throws IllegalArgumentException if the vertex does not occur in the graph
	 */
	public int degree(V v){
		//if the vertex given is not in the graph
		if(adjList.containsKey(v)==false){
			throw new IllegalArgumentException();
		}
		//return the size of its array list since those are all the nodes it points to
		return adjList.get(v).size();
	}
	
	/**
	 * Returns a string representation of the graph. The string representation shows all
	 * vertices and edges in the graph. 
	 * @return A string representation of the graph
	 */
	public String toString(){
		String gString = "";
		//for each node
		for(V l: adjList.keySet()){
			gString += l + ": { ";
			//for each of its edges
			for(V k: adjList.get(l))
			{
				gString += k +" ";
			}
			gString += "}\n";
		}
		return gString;
	}
	
}
