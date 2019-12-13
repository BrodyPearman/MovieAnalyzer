package graph;
import java.util.List;


import graph.Graph;
import util.Pair;
import util.PriorityQueue;
public class GraphAlgorithms {
	public static final int infinity=1001;//infinity value
	
	
	public static void main(String[] args) {
		Graph<Integer> g= new Graph<>();
		for (int i=1; i<10; i++) {
			g.addVertex(i);
		}
		g.addEdge(1,2);
		g.addEdge(2,1);
		g.addEdge(1,3);
		g.addEdge(3,1);
		//g.addEdge(2,4);
		//g.addEdge(4,2);
		g.addEdge(4,5);
		g.addEdge(5,6);
		g.addEdge(6,5);
		//g.addEdge(6,3);
		//g.addEdge(3,6);
		g.addEdge(3,2);
		g.addEdge(5,4);
		g.addEdge(2,3);
		g.addEdge(4,6);
		g.addEdge(9,8);
		g.addEdge(2,7);
	
		g.addEdge(8,9);
		g.addEdge(7,2);
		g.addEdge(6,4);
	
		int[][] floyd= floydWarshall(g);
		Integer[] dijk=dijkstrasAlgorithm(g,1);
		System.out.println("Floyd: ");
		for (int k=0; k<floyd.length;k++) {
			for(int i=0;i<floyd[k].length;i++) {
				System.out.print(floyd[k][i]);
				System.out.print("  ");
			}
			System.out.println("");
		}
		System.out.println("Dijkstras: ");
		for(int i=0;i<dijk.length;i++) {
			System.out.print(dijk[i]);
			System.out.print("  ");
		}
		
		
	}
	
	/**
	 * Returns 2D array of shortest paths from node i to node j
	 * @param graph	graph of interest
	 * @return 2D array of shortest paths
	 */
	public static int[][] floydWarshall(Graph<Integer> graph){

		int numVertices= graph.numVertices();
		int[][] a= new int[numVertices][numVertices];
		for(int i: graph.getVertices()){
			for (int j: graph.getVertices()){
				if(graph.edgeExists(i,j)){//if you can get from one node to another without intermediates
					a[i-1][j-1]=1;

				}
				else if(i==j) {
					a[i-1][j-1]=0; //no cost to go to same node
				}
				else {
					
						a[i-1][j-1]=infinity; //else set to infinity
				}
				
			}
		}
	    for(int k=0; k<numVertices;k++){
			for(int l=0; l<numVertices;l++){
				for(int m=0; m<numVertices;m++){//for more and more intermediate nodes
					int option1= a[l][k]+a[k][m];
					int option3= a[l][m];
					int step= Math.min(option1,option3);//find shortest path 
					a[l][m]= step;
				}
			}
		}	
			
	return a;
	   
	}


	/**
	 * Returns array of parent nodes for shortest path from source node
	 * @param graph	graph of interest
	 * @param source	source node 
	 * @return array of parent nodes for shortest path from source node
	 */
	public static Integer[] dijkstrasAlgorithm(Graph<Integer> graph, int source){
		PriorityQueue h= new PriorityQueue();
		int[] dist= new int[graph.numVertices()+1]; //make 1 longer to avoid 0 index error
		Integer[] prev= new Integer[graph.numVertices()+1];
		for(int i=0; i<dist.length;i++){
			dist[i]=infinity; //initialize all distances to infinity
		}
		dist[source]=0;
		
		for(Integer v: graph.getVertices()){
			h.push(dist[v],v);//push all on the queue
		}
		for(int i=0; i<prev.length;i++)
		{
			prev[i]=null;//instantiate all parents to null
		}
	
		while (h.size()!=0){//while the queue has elements
			int u= h.topElement();//grab the top element
			h.pop();
			for(Integer v: graph.getNeighbors(u)){//grab its adjacencies
				
					int alt= dist[u]+1;
					if(alt<dist[v]){//if there is a shorter path
					
						dist[v]=alt;
						prev[v]=u;//replace and change priority
						h.changePriority(alt,v);
					
					}
				
			}
		}
		return prev;
	}




}
