package analyzer;
import java.util.Scanner;
import data.Reviewer;
import graph.Graph;
import util.DataLoader;
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
		Dataloader d= new Dataloader();
		d.loadData(first,second);
		HashMap<Integer, Reviewer> review= d.getReviewers();
		HashMap<Integer, Movie> movies=d.getMovies();
		System.out.println("There are three choices for defining adjacency: ");
		System.out.println("[Option 1] u and v are adjacent if the same 12 users gave the same rating to both movies");
		System.out.println("[Option 2] u and v are adjacent if the same 12 users watched both movies (regardless of rating)");
		System.out.println("[Option 3] u is adjacent to v if at least 33.0% of the users that rated u gave the same rating to v");
		System.out.println("\n");
		boolean b=true;
		System.out.println("Choose an option to build the graph (1-3");
		while(b){
			String s= scan.nextLine();
			if(s.length()!=1) {
				System.out.println("Please enter an integer 1-3");

			}
			else{
				if(s.equals("1")){
					System.out.println("Creating a graph...");
					Graph<Integer> graph= graphBuilderOption1(movies, review);
					b=false;
					System.out.println("The graph has been created \n");
				}
				else if(s.equals("2")){
					System.out.println("Creating a graph...");
					Graph<Integer> graph= graphBuilderOption2(movies, review);
					b=false;
					System.out.println("The graph has been created \n");
				}
				else if(s.equals("3")){
					System.out.println("Creating a graph...");
					Graph<Integer> graph= graphBuilderOption3(movies, review);
					b=false;
					System.out.println("The graph has been created \n");
				}
				else {
					System.out.println("Please enter an integer 1-3");
				}
				
			}
		}
		b=True;
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
					String p=option2(graph);
					System.out.println(p);
				}
				else if(s.equals("3")){
					String p=option3(graph);
					System.out.println(p);
				}
				else if(s.equals("4")) {
					System.out.println("Closing program");
					b=false;
					scan.close();
				}
				
			}

		}
	}




	public static Graph<Integer> graphBuilderOption1(Map<Integer,Movie> movies, Map<Integer,Reviewer> review){
		Graph<Integer> g= new Graph<>();
		//ArrayList<Integer> movieIDs= new ArrayList<>();
		for(int a: movies.keySet()){
			g.addVertex(a);
			//movieIDs.add(movies.get(g).getMovieId());
		}
		for(int k : movies.keySet()){
			for(int j: movies.keySet())	{
				//int counter=0;
				Movie mov= movies.get(k);
				Movie mov2= movies.get(j);
				//Pair<Integer,Integer> p=new Pair<>(movieIds.get(k),movieIds.get(j));
				if(!mov.equals(mov2)){
					HashMap<Integer,Double> rat1= mov.getRatings();
					HashMap<Integer,Double> rat2= mov2.getRatings();
					
				}
				
			}

		}  
			
		

		return g;
	}


	public static Graph<Integer> graphBuilderOption2(Map<Integer,Movie> movies, Map<Integer,Reviewer> review){
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
					for(int y: review.keySet()){
						Reviewer rev=review.get(y);
					
						if(rev.ratedMovie(movID1) && rev.ratedMovie(movID2)){
							counter++;
						}
					}	
				}
				if(counter>=12){
					g.addEdge(k,j);
				}

			}  
			
		}

		return g;
	}


	public static Graph<Integer> graphBuilderOption3(Map<Integer,Movie> movies, Map<Integer,Reviewer> review){
		Graph<Integer> g= new Graph<>();
		return g;
		
	}

	public static String option1(Graph<Integer> g){
		return "";
	}

	public static String option2(Graph<Integer> g){
		return "";
	}
	public static String option3(Graph<Integer> g){
		return "";
	}
}
