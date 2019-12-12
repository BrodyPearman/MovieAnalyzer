package graph;
import java.util.List;


import graph.Graph;
import util.Pair;
import util.PriorityQueue;
public class GraphAlgorithms {
	public static final int infinity=1000000;
	
	
	public static void main(String[] args) {
		Graph<Integer> g= new Graph<>();
		for (int i=1; i<7; i++) {
			g.addVertex(i);
		}
		g.addEdge(1,2);
		g.addEdge(1,3);
		g.addEdge(2,4);
		g.addEdge(4,5);
		g.addEdge(5,6);
		g.addEdge(6,1);
		g.addEdge(3,1);
		g.addEdge(5,4);
		g.addEdge(2,6);
		Integer[] dijk= dijkstrasAlgorithm(g,1);
		//int[][] floyd= floydWarshall(g);
		for (int j=0; j< dijk.length;j++) {
			System.out.print(dijk[j]);
			System.out.print(" ");
			
		}
		System.out.println("");
		
	}
	
	
	public static int[][] floydWarshall(Graph<Integer> graph){
		int numVertices= graph.numVertices();
		int[][] a= new int[numVertices][numVertices];
		for(int i=0; i< numVertices;i++){
			for (int j=0; j< numVertices;j++){
				if(graph.edgeExists(i,j)){
					a[i][j]=1;

				}
				else {
					a[i][j]=infinity;
				}
			}
		}
	
		//int [][] newer= new int[numVertices][numVertices];
		for(int k=0; k<numVertices;k++){
			//newer= new int[numVertices][numVertices];
			for(int l=0; l<numVertices;l++){
				for(int m=0; m<numVertices;m++){
					int option1= a[l][k]+a[k][m];
					int option3= a[l][m];
					int step= Math.min(option1,option3);
					
					//int last = Math.min(option3, step);
					a[l][m]= step;
				}
			}
			
		}
		
		return a;
	}

	public static Integer[] dijkstrasAlgorithm(Graph<Integer> graph, int source){
		PriorityQueue h= new PriorityQueue();
		int[] dist= new int[graph.numVertices()];
		Integer[] prev= new Integer[graph.numVertices()];
		for(int i=0; i<dist.length;i++){
			dist[i]=infinity;
		}
		dist[source]=0;
		//h.push(dist[0],0);
		for(Integer v: graph.getVertices()){
			h.push(dist[v],v);
		}
		for(int i=0; i<prev.length;i++)
		{
			prev[i]=null;
		}
		while (h.size()!=0){
			int u= h.pop();
			for(Integer v: graph.getNeighbors(u)){
				int alt= dist[u]+1;
				if(alt<dist[v]){
					dist[v]=alt;
					prev[v]=u;
					//System.out.println(v);
					//System.out.println(alt);
					h.changePriority(alt,v);
					
					
				}
			}
		}
		return prev;
	}




}
