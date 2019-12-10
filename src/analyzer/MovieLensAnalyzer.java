package analyzer;
import java.util.Scanner;
/**
 * Please include in this comment you and your partner's name and describe any extra credit that you implement 
 */
public class MovieLensAnalyzer {
	
	public static void main(String[] args){
		// Your program should take two command-line arguments: 
		// 1. A ratings file
		// 2. A movies file with information on each movie e.g. the title and genres	
		Scanner scan= new Scanner();	
		if(args.length != 2){
			System.err.println("Usage: java MovieLensAnalyzer [ratings_file] [movie_title_file]");
			System.exit(-1);
		}	

	}
}
