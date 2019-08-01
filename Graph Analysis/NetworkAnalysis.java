import java.util.*;


/**
 * CS 1501 Project 4
 * Graph Analyzer
 * @author Joseph Reidell
 */

public class NetworkAnalysis {

	public static Scanner scan;
    
    public static void main(String[] args) {
        if(args.length == 1){
            Graph graph = new Graph(args[0]);
            scan = new Scanner(System.in);
            
            while(true){
            	System.out.println("-------- MAIN MENU --------\n"
            			+ "Select an option by entering the number next to the option name.\n"
            			+ "\t1. Find the lowest latency path\n"
            			+ "\t2. Determine the copper connectivity in a graph\n"
            			+ "\t3. Find the maximum amount of data between verticies\n"
            			+ "\t4. Determine if any two verticies in the graph fails\n"
            			+ "\t5. Exit\n");
                
                System.out.print("Enter a choice from the menu above: ");
                String input = scan.next();
                
                switch(input){
                    case "1":
                        graph.findLatencyPath();
                        break;
                    case "2":
                        graph.copperCheck();
                        break;
                    case "3":
                        graph.getMaxData();
                        break;
                    case "4":
                        graph.vertexFail();
                        break;
                    case "5":
                        System.exit(0);
                    default:
                        System.out.println("Invalid option.\n");
                        break;
                }
            }
        }
        
        else{
            System.out.println("Only one argument is required.");
        }
        
        System.exit(0);
    }
    
}
