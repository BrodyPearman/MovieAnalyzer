package graph;
import graph.Graph;
import util.PriorityQueue;
public class GraphAlgorithms {
	

	public static void main(String[] args){
		Graph<Integer> g= new Graph<Integer>();
		for (int i=0; i<5; i++) {
			g.addVertex(i);
		}
		g.addEdge(1,2);
		g.addEdge(1,3);
		g.addEdge(2,4);
		g.addEdge(4,3);
		g.addEdge(0,1);
		int[][] floyd=floydWarshall(g);
		int[] dijk= dijkstrasAlgorithm(g,0);
		System.out.println(floyd);
		System.out.println(dijk);
	}

	public static int[][] floydWarshall(Graph<Integer> graph){
		int numVertices= graph.numVertices();
		int[][][] a= new int[numVertices+1][numVertices][numVertices];
		for(int i=0; i< numVertices;i++){
			for (int j=0; j< numVertices;j++){
				if(graph.edgeExists(i,j)){
					a[0][i][j]=1;

				}
				else {
					a[0][i][j]=1000000000;
				}
			}
		}
		for(int k=1; k<numVertices+1;k++){
			for(int l=0; l>numVertices;l++){
				for(int m=0; m<numVertices;m++){
					int option1= a[k-1][l][k];
					int option2= a[k-1][k][m];
					int option3= a[k-1][l][m];
					int step= Math.min(option1,option2);
					int last = Math.min(option3, step);
					a[k][l][m]= last;
				}
			}
		}
		return a[numVertices];
	}

	public static int[] dijkstrasAlgorithm(Graph<Integer> graph, int source){
		PriorityQueue h= new PriorityQueue();
		int[] dist= new int[graph.numVertices()];
		int[] prev= new int[graph.numVertices()];
		for(int i=0; i<dist.length;i++){
			dist[i]=10000000;
		}
		dist[source]=0;
		for(Integer v: graph.getVertices()){
			h.push(v,dist[v]);
		}
		while (h.size()!=0){
			Integer u= h.pop();
			for(Integer v: graph.getNeighbors(u)){
				int alt= dist[u]+1;
				if(alt<dist[v]){
					dist[v]=alt;
					prev[v]=u;
					h.changePriority(v,alt);
				}
			}
		}
		return prev;
	}




}
