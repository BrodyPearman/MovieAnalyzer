package analyzer;
import java.util.Scanner;
import java.util.ArrayList;
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
 * Extra credit: added recommender system that takes in two movies the users liked and recommends common neighbors
 * Brody Pearman
 * Jewell Day
 * 12/12/19
 * 
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
		String first= args[0]; //grab movies and reviewers files as arguments
		String second= args[1];
		System.out.println(first);
		System.out.println(second);
		DataLoader d= new DataLoader();
		d.loadData(first,second);
		HashMap<Integer, Reviewer> review= (HashMap<Integer,Reviewer>) d.getReviewers();//create hashmaps of reviewers and movies
		HashMap<Integer, Movie> movies= (HashMap<Integer,Movie>) d.getMovies();
		//prompt user for what they want to do
		System.out.println("There are three choices for defining adjacency: ");
		System.out.println("[Option 1] u and v are adjacent if at least 12 users gave the same rating to both movies");
		System.out.println("[Option 2] u and v are adjacent if at least 12 users watched both movies (regardless of rating)");
		System.out.println("[Option 3] u is adjacent to v if at least 33.0% of the users that rated u gave the same rating to v");
		System.out.println("\n");
		boolean b=true;
		System.out.println("Choose an option to build the graph (1-3)");
		Graph<Integer> graph= new Graph<>();
		//build the graph based on their choice and make sure input is valid
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
			//prompt user for what they want to know about the graph
			System.out.println("[Option 1] Print out statistics about the graph");
			System.out.println("[Option 2] Print node information");
			System.out.println("[Option 3] Display shortest path between two nodes");
			System.out.println("[Option 4] Recommend movies");
			System.out.println("[Option 5] Quit");
			System.out.println("Choose an option (1-5)");
			String s= scan.nextLine();
			if(s.length()!=1) {
				System.out.println("Please enter an integer 1-5");

			}
			else{
				//respond to user's prompt checking validity of inputs
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
							System.out.println("excet");
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
				 else if(s.equals("5")) {
	                    System.out.println("Closing program");
	                    b=false;
	                    scan.close();
	                }

	             else if(s.equals("4")){
	                    boolean t=true;
	                    while(t) {
	                        System.out.println("Please select a node: ");
	                        try {
	                            int startNode=scan.nextInt();
	                            if(movies.containsKey(startNode)) {
	                                System.out.println("Please provide another the ID for a movie you liked: ");
	                                boolean brody=true;
	                                while(brody) {
	                                    int endNode=scan.nextInt();
	                                    if(movies.containsKey(endNode)) {
	                                        String str=option4(graph,movies,startNode,endNode);
	                                        System.out.println(str);
	                                        brody=false;
	                                    }
	                                    else
	                                    {
	                                        System.out.println("No such movie. Please try again.");
	                                    }
	                                }
	                                t=false;
                            }
	                            else {
	                                System.out.println("No such movie. Please try again.");
	                            }                         
	                        }
	                        catch(Exception e) {
	                            System.out.println("Please print a valid integer");                           
	                        }
	                    }
	                }
			}
		}
	}





/**
 * Builds graph where edges are defined as if at least 12 users have rated both movies the same
 * @param movies	movies hashmap	
 * @param review	reviwers hashmap
 * @return	graph with movies as nodes and edges defined as above
 */
    public static Graph<Integer> graphBuilderOption1(HashMap<Integer,Movie> movies, HashMap<Integer,Reviewer> review){
    	Graph<Integer> g= new Graph<>(); //instantiate graph to return
        Set<Integer> movieList= movies.keySet();//get movies 
        Set<Integer> reviewerList = review.keySet();//get reviewer ids
        //for every movie, add its index as a vertex
        for(Integer a: movieList){
            g.addVertex(a);
         

        }
        
        for(Integer i: movieList){ //for every pair of movies
            for(Integer j: movieList){

               
                int a = i;
                int b = j;
            	int movID1= movies.get(i).getMovieId();
				int movID2= movies.get(j).getMovieId();
				if(movID1!=movID2) {//make sure they aren't the same movie
                
					Set<Integer> aRev = movies.get(i).getRatings().keySet(); //get all the users that have seen each movie
					Set<Integer> bRev = movies.get(j).getRatings().keySet();
					if(aRev.size()> bRev.size()){ //switch aRev and bRev if a is longer
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
						if(numSame>=12){
							g.addEdge(a, b);
							//g.addEdge(b, a);
							break;
						}
					}
				}
            }
       }


        return g;

    }
/**
 * Builds a graph with edges defined as those that have had 12 users view both movies
 * @param movies	movies hashmap
 * @param review 	reviewers hashmap
 * @return	graph with movies as nodes and edges defined as above
 */

	public static Graph<Integer> graphBuilderOption2(HashMap<Integer,Movie> movies, HashMap<Integer,Reviewer> review){
		Graph<Integer> g= new Graph<>();//instantiate a new graph
	
		for(int a: movies.keySet()){//for every movie, add a node
			g.addVertex(a);
			
		}
		for(int k : movies.keySet()){
			for(int j: movies.keySet())	{//for every pair of movies
				int counter=0;
				int movID1= movies.get(k).getMovieId();
				int movID2= movies.get(j).getMovieId();
				//if they aren't the same movie
				if(movID1!=movID2){
					for(int revID : movies.get(k).getRatings().keySet()){//get the first list of those who have rated it
						if(movies.get(j).rated(revID)){//if they have also rated the other movie
							counter++;//increment
						}
					}	
				}
				if(counter>=12){//if the incrementer exceeds 12, add an edge
					g.addEdge(k,j);
				}

			}  
			
		}

		return g;
	}

/**
 * Builds graph where edges are defined as 33% of users that rated u gave same rating to v
 * @param movies	movies hashmap
 * @param review	reviewers hashmap
 * @return	graph with movies as nodes and edges as defined above
 */	
	public static Graph<Integer> graphBuilderOption3(Map<Integer,Movie> movies, Map<Integer,Reviewer> review){
        Graph<Integer> g= new Graph<>();//create a new graph
        Set<Integer> movieList= movies.keySet();//get the movie list
        Set<Integer> reviewerList = review.keySet();//get the reviewer list

        

        for(int a: movieList){
            g.addVertex(a);//add every movie as a node
           
        }

        for(int i : movieList){
            for(int j : movieList){//every pair of movies
                //gets the people who have reviewed each movie
            	int movID1= movies.get(i).getMovieId();
				int movID2= movies.get(j).getMovieId();
				//if the movies aren't the same
				if(movID1!=movID2) {
					Set<Integer> aRev = movies.get(i).getRatings().keySet();//get all that have rated each
					
					//iterates over the shortest list and counts how many 
					//gave the same review for both
					double numSame = 0;
					double numReviewers = 0;
                	for(int c: aRev){ 
                		if(movies.get(i).getRating(c)==movies.get(j).getRating(c)){ //gave same rating to v
                			numReviewers+=1;
                			numSame+=1;
                		}
                		else if(movies.get(j).getRating(c)!=-1){
                			numReviewers++;
                		}
                	}
                	if(numSame/numReviewers >= .33){//is proportion greater than .33
                		g.addEdge(i, j);
        
                	}

					}

            	}

            }



        return g;

        

    }
/**
 * Returns stats about the graph
 * @param g	graph that has stats
 * @return graph statistics
 */
	public static String option1(Graph<Integer> g){
		String s="Graph statistics: " + "\n";
		int edges= g.numEdges();
		int nodes= g.numVertices();
		double density= ((double) edges)/(nodes*(nodes-1));
		s+="Number of nodes: " +nodes + "\n";
		s+="Number of edges: "+ edges + "\n";
		s+="Density: "+ density +"\n";
		int maxDeg=0;
		Integer maxDegreeNode=-1;
		for(Integer i: g.getVertices()) {
			if(g.degree(i)>maxDeg) {
				maxDeg=g.degree(i);
				maxDegreeNode=i;
			}
		}
		s+="Max Degree: "+ maxDeg + " (node " + maxDegreeNode + ")" +"\n";
		int[][] floyd= GraphAlgorithms.floydWarshall(g);
		int maximum=0;
		int sum=0;
		int ticker=0;
		int start=-1;
		int end=-1;
		for(int i=0; i<floyd.length;i++) {
			for(int j=0; j<floyd[i].length;j++) {
				if(floyd[i][j]!=GraphAlgorithms.infinity) {
					sum+=floyd[i][j];
					ticker++;
					if(maximum<floyd[i][j]) {
						maximum=floyd[i][j];
						start=i+1;
						end=j+1;
					}
				}
			}
		}
		double average= ((double) sum)/ticker;
		s+="Diameter: " + maximum + " (from " + start + " to " + end + ")" + "\n";
		s+="Average length of the shortest paths: " + average;
		return s;
	}

	/**
	 * Returns specified node information
	 * @param g	graph with node
	 * @param input node of interest
	 * @param movies hashmap of all movies
	 * @return information about the node
	 */
	public static String option2(Graph<Integer> g, int input, HashMap<Integer,Movie> movies){
		String s="";
		Movie m= movies.get(input);
		s+=m.toString();
		s+="\n";
		s+="Neighbors: " + "\n";
		for(Integer i: g.getNeighbors(input)) {
			Movie sub= movies.get(i);
			String title= sub.getTitle();
			s+=title + "\n";
		}
		return s;
	}
	/**
	 * Runs dijkstras and returns shortest path between two given nodes 
	 * @param g	graph to be considered
	 * @param movies	movies hashmap
	 * @param movieID1	start node movie
	 * @param movieID2	end node movie
	 * @return string of path
	 */
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
	
	/**
	 * Recommends movies based on two movies user liked
	 * Recommendations are shared neighbors
	 * @param g	graph of movies
	 * @param movies	hashmap of movies
	 * @param movieID1	first movie user likes
	 * @param movieID2	second movies user likes
	 * @return	string of recommended movies
	 */
	 public static String option4(Graph<Integer> g,Map<Integer,Movie> movies, int movieID1, int movieID2){

	        ArrayList<Integer> likedMovies = new ArrayList<>();//create new array list to use

	        String recMovies = "\nRecomended Movies:"; 

	        for(int a: g.getNeighbors(movieID1)){//grab both movies adjacencies

	            for(int b: g.getNeighbors(movieID1)){

	                if(a == b){//if they are same recommend it

	                    recMovies = recMovies + "\n" + movies.get(a).getTitle();
	                    likedMovies.add(b);
	                }
	            }
	        }
	        if(likedMovies.size() ==0){
	        	System.out.println("Sorry These movies didn't have any in common!");
	        }

	        return recMovies +"\n";

	    }
}
