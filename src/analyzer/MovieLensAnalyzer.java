package analyzer;
import java.util.Scanner;
import java.util.Map;
import data.Reviewer;
import data.Movie;
import graph.Graph;
import graph.GraphAlgorithms;
import util.DataLoader;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * Please include in this comment you and your partner's name and describe any extra credit that you implement 
 */
public class MovieLensAnalyzer {
	
	public static void main(String[] args){
		// Your program should take two command-line arguments: 
		// 1. A ratings file
		// 2. A movies file with information on each movie e.g. the title and genres	
		Scanner scan= new Scanner(System.in);	
		if(args.length != 2){
			System.err.println("Usage: java MovieLensAnalyzer [ratings_file] [movie_title_file]");
			System.exit(-1);
		}	
		System.out.println("The files being analyzed are: ");
		String first= args[0];
		String second= args[1];
		System.out.println(first);
		System.out.println(second);
		DataLoader d= new DataLoader();
		d.loadData(first,second);
		HashMap<Integer, Reviewer> review= (HashMap<Integer,Reviewer>) d.getReviewers();
		HashMap<Integer, Movie> movies= (HashMap<Integer,Movie>) d.getMovies();
		System.out.println("There are three choices for defining adjacency: ");
		System.out.println("[Option 1] u and v are adjacent if the same 12 users gave the same rating to both movies");
		System.out.println("[Option 2] u and v are adjacent if the same 12 users watched both movies (regardless of rating)");
		System.out.println("[Option 3] u is adjacent to v if at least 33.0% of the users that rated u gave the same rating to v");
		System.out.println("\n");
		boolean b=true;
		System.out.println("Choose an option to build the graph (1-3");
		Graph<Integer> graph= new Graph<>();
		while(b){
			String s= scan.nextLine();
			if(s.length()!=1) {
				System.out.println("Please enter an integer 1-3");
			}
			else{
				if(s.equals("1")){
					System.out.println("Creating a graph...");
					graph= graphBuilderOption1(movies, review);
					b=false;
					System.out.println("The graph has been created \n");
				}
				else if(s.equals("2")){
					System.out.println("Creating a graph...");
					graph= graphBuilderOption2(movies, review);
					b=false;
					System.out.println("The graph has been created \n");
				}
				else if(s.equals("3")){
					System.out.println("Creating a graph...");
					graph= graphBuilderOption3(movies, review);
					b=false;
					System.out.println("The graph has been created \n");
				}
				else {
					System.out.println("Please enter an integer 1-3");
				}
				
			}
		}
		b=true;
		while(b){
			System.out.println("[Option 1] Print out statistics about the graph");
			System.out.println("[Option 2] Print node information");
			System.out.println("[Option 3] Display shortest path between two nodes");
			System.out.println("[Option 4] Quit");
			System.out.println("Choose an option (1-4)");
			String s= scan.nextLine();
			if(s.length()!=1) {
				System.out.println("Please enter an integer 1-4");

			}
			else{
				if(s.equals("1")){
					String p=option1(graph);
					System.out.println(p);
				}
				else if(s.equals("2")){
					
					boolean t=true;
					while(t) {
						System.out.println("Please select a node: ");
						try {
							int input=scan.nextInt();
							if(movies.containsKey(input)) {
								String pl= option2(graph,input,movies);
								System.out.println(pl);
								t=false;
							}
							else {
								System.out.println("No such node. Please try again.");
							}
							
						}
						catch(Exception e) {
							System.out.println("Please print a valid integer");
							
						}
					}
				}
				else if(s.equals("3")){
					boolean t=true;
					while(t) {
						System.out.println("Please select a start node: ");
						try {
							int startNode=scan.nextInt();
							if(movies.containsKey(startNode)) {
								System.out.println("Please select an end node: ");
								boolean brody=true;
								while(brody) {
									int endNode=scan.nextInt();
									if(movies.containsKey(endNode)) {
										String str=option3(graph,movies,startNode,endNode);
										System.out.println(str);
										brody=false;
									}
									else
									{
										System.out.println("No such node. Please try again.");
									}
								}
								t=false;
							}
							else {
								System.out.println("No such node. Please try again.");
							}
							
						}
						catch(Exception e) {
							System.out.println("Please print a valid integer");
							
						}
					}
				}
				else if(s.equals("4")) {
					System.out.println("Closing program");
					b=false;
					scan.close();
				}
				
			}

		}
	}






    public static Graph<Integer> graphBuilderOption1(HashMap<Integer,Movie> movies, HashMap<Integer,Reviewer> review){
    	Graph<Integer> g= new Graph<>();
        Set<Integer> movieList= movies.keySet();
        Set<Integer> reviewerList = review.keySet();
        //ArrayList<Integer> movieIDs= new ArrayList<>();
        for(Integer a: movieList){
            g.addVertex(a);
            //movieIDs.add(movies.get(g).getMovieId());

        }

        for(Integer i: movieList){
            for(Integer j: movieList){

                //gets the people who have reviewed each movie
                int a = i;
                int b = j;
            	int movID1= movies.get(i).getMovieId();
				int movID2= movies.get(j).getMovieId();
				if(movID1!=movID2) {
                
					Set<Integer> aRev = movies.get(i).getRatings().keySet();
					Set<Integer> bRev = movies.get(j).getRatings().keySet();
					if(aRev.size()> bRev.size()){
						Set<Integer> temp = aRev;
						aRev = bRev;
						bRev = temp;
					}
					//iterates over the shortest list and counts how many 
					//gave the same review for both
					int numSame = 0;
					for(int c: aRev){
						if(movies.get(a).getRating(c)==movies.get(b).getRating(c)){
							numSame++;
						}
						if(numSame==12){
							g.addEdge(a, b);
							g.addEdge(b, a);
							break;
						}
					}
				}
            }
       }

        return g;

    }


	public static Graph<Integer> graphBuilderOption2(HashMap<Integer,Movie> movies, HashMap<Integer,Reviewer> review){
		Graph<Integer> g= new Graph<>();
		//ArrayList<Integer> movieIDs= new ArrayList<>();
		for(int a: movies.keySet()){
			g.addVertex(a);
			//movieIDs.add(movies.get(g).getMovieId());
		}
		for(int k : movies.keySet()){
			for(int j: movies.keySet())	{
				int counter=0;
				int movID1= movies.get(k).getMovieId();
				int movID2= movies.get(j).getMovieId();
				//Pair<Integer,Integer> p=new Pair<>(movieIds.get(k),movieIds.get(j));
				if(movID1!=movID2){
					for(int revID : movies.get(k).getRatings().keySet()){
						if(movies.get(j).rated(revID)){
							counter++;
						}
					}	
				}
				if(counter>=12){
					g.addEdge(k,j);
					g.addEdge(j,k);
				}

			}  
			
		}

		return g;
	}


	public static Graph<Integer> graphBuilderOption3(Map<Integer,Movie> movies, Map<Integer,Reviewer> review){
        Graph<Integer> g= new Graph<>();
        Set<Integer> movieList= movies.keySet();
        Set<Integer> reviewerList = review.keySet();

        //ArrayList<Integer> movieIDs= new ArrayList<>();

        for(int a: movieList){
            g.addVertex(a);
            //movieIDs.add(movies.get(g).getMovieId());
        }

        for(int i : movieList){
            for(int j : movieList){
                //gets the people who have reviewed each movie
            	int movID1= movies.get(i).getMovieId();
				int movID2= movies.get(j).getMovieId();
				if(movID1!=movID2) {
					Set<Integer> aRev = movies.get(i).getRatings().keySet();
					Set<Integer> bRev = movies.get(j).getRatings().keySet();
					if(aRev.size() > bRev.size()){
						Set<Integer> temp = aRev;
						aRev = bRev;
						bRev = temp;
					}
					//iterates over the shortest list and counts how many 
					//gave the same review for both
					double numSame = 0;
					double numReviewers = 0;
                	for(int c: aRev){
                		if(movies.get(i).getRating(c)==movies.get(j).getRating(c)){
                			numReviewers+=1;
                			numSame+=1;
                		}
                		else if(movies.get(j).getRating(c)!=-1){
                			numReviewers++;
                		}
                	}
                	if(numSame/numReviewers >= .33){
                		g.addEdge(i, j);
                		g.addEdge(j, i);
                	}

					}

            	}

            }



        return g;

        

    }

	public static String option1(Graph<Integer> g){
		String s="";
		int edges= g.numEdges();
		int nodes= g.numVertices();
		double density= ((double) edges)/(nodes*(nodes-1));
		s+="Number of nodes: " +nodes+"\n";
		s+="Number of edges: "+ edges + "\n";
		s+="Density: "+ density +"\n";
		int maxDeg=0;
		for(Integer i: g.getVertices()) {
			if(g.degree(i)>maxDeg) {
				maxDeg=g.degree(i);
			}
		}
		s+="Max Degree: "+ maxDeg + "\n";
		int[][] floyd= GraphAlgorithms.floydWarshall(g);
		int maximum=0;
		int sum=0;
		int ticker=0;
		for(int i=0; i<floyd.length;i++) {
			for(int j=0; j<floyd[i].length;j++) {
				sum+=floyd[i][j];
				ticker++;
				if(maximum<floyd[i][j]) {
					maximum=floyd[i][j];
				}
			}
		}
		double average= ((double) sum)/ticker;
		s+="Diameter: " + maximum + "\n";
		s+="Average length of the shortest paths: " + average;
		return s;
	}

	public static String option2(Graph<Integer> g, int input, HashMap<Integer,Movie> movies){
		String s="";
		Movie m= movies.get(input);
		s+=m.toString();
		s+="\n";
		s+="Adjacent to: ";
		for(Integer i: g.getNeighbors(input)) {
			Movie sub= movies.get(i);
			String title= sub.getTitle();
			s+=title + ", ";
		}
		s=s.substring(0,s.length());
		return s;
	}
	public static String option3(Graph<Integer> g,Map<Integer,Movie> movies, int movieID1, int movieID2){

        Integer fastestPaths[] = GraphAlgorithms.dijkstrasAlgorithm(g, movieID1);

        Integer i = movieID2; 

        String s1 = "The shortest path between " + movies.get(movieID1).getTitle() + " and " + movies.get(movieID2).getTitle() +" is ";

        String s = "";

        int count = 0;

        while(i != null){

            s =  movies.get(i).getTitle() + "\n\t|\n" + s;

            count++;

            i = fastestPaths[i];

        }

        return s1 + count +" movies long and follows the path:\n" + s;

    }
}
