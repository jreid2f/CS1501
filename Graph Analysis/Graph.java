import java.io.*;
import java.util.*;

/**
 * CS 1501 Project 4
 * Graph Analyzer
 * @author Joseph Reidell
 */

public class Graph {
    private int verticiesNum;
    private Vertex[] verticies;
    private ArrayList<Edge> edges;
    private boolean copper = true;
    public static Scanner scan;
    
    // Constructor method
    public Graph(String filename){
        drawGraph(filename);
    }
    
    public void findLatencyPath(){
        scan = new Scanner(System.in);
        Vertex start = null;
        Vertex end = null;
        
        /*
         * Ask the user for two vertex ID's and check if they exist. 
         */
        do{
            System.out.print("Enter the first vertex ID: ");
            int id_1 = Integer.parseInt(scan.nextLine());
            
            if(id_1 >= verticiesNum || id_1 < 0) System.out.println("\nInvalid Vertex!\n");

            else start = verticies[id_1];
            
        }while(start == null);
        
        do{
            System.out.print("Enter the second vertex ID: ");
            int id_2 = Integer.parseInt(scan.nextLine());
            
            if(id_2 >= verticiesNum || id_2 < 0 || id_2 == start.getID()){
                System.out.println("\nInvalid Vertex!\n");
            }
            else end = verticies[id_2];
            
        }while(end == null);
        
        Object[] dataPath = shortestPath(start, end, "" + start.getID(), 0L, -1);
       
        if(dataPath == null){
            return;
        }
        
        String path = (String) dataPath[0];
        String directPath = "";
        
        for(int i = 0; i < path.length(); i++){
            if(i < path.length() - 1) directPath += path.charAt(i) + " -> ";
            
            else directPath += path.charAt(i);
        }
        
        path = directPath;
        double pathTravel = (double)dataPath[1];
        int bandPath = (int)dataPath[2];
        
        System.out.printf("\nShortest Path: %s\nTime/Latency: %.3f nanoseconds\n"
                + "Bandwidth: %d mbps\n", path, pathTravel, bandPath);
    }
    
    public void copperCheck(){
        if(copper){
            System.out.println("The graph consists of only copper wires, "
                    + "so it is copper connected.\n");
        }
        
        else{
            boolean isCopper = true;
            
            for(int i = 0; i < verticiesNum; i++){
                LinkedList<Edge> vertEdge = verticies[i].getConnection();
                
                boolean hasCopper = false;
                
                for(Edge e : vertEdge){
                    if(e.getMaterial().equals("copper")){
                        hasCopper = true;
                        break;
                    }   
                }
                
                if(!hasCopper){
                        isCopper = false;
                        break;
                    }
            }
            
            if(isCopper){
                System.out.println("The graph can be copper connected but, "
                        + "it does have fiber optic wires.");
            }
            
            else{
                System.out.println("The graph is not copper only and "
                        + "cannot be connected with only copper wire.");
            }
        }
    }
    
    public void getMaxData(){
        scan = new Scanner(System.in);
        Vertex start = null;
        Vertex end = null;
        
        do{
            System.out.print("Enter an Start Vertex ID: ");
            int ID = Integer.parseInt(scan.nextLine());
            if(ID >= verticiesNum || ID < 0) System.out.println("\nNot a valid vertex ID\n");
            
            else start = verticies[ID];
       
        }while(start == null);
        
        do{
            System.out.print("Enter an End Vertex ID: ");
            int ID = Integer.parseInt(scan.nextLine());
            if(ID >= verticiesNum || ID < 0 || ID == start.getID()){
                System.out.println("\nNot a valid vertex ID\n");
            }
            else{
                end = verticies[ID];
            }
        }while(end == null);
        
        int max = maxVerticies(start, end, "" + start.getID(), -1);
        System.out.println("\nMax amount of data between the verticies " 
                + start.getID() + " and " + end.getID() + ": " + max + " mbps");
    }
    
    public void vertexFail(){
        for(int i = 0; i < verticiesNum - 1; i++){
            for(int g = i + 1; g < verticiesNum; g++){
                Vertex start = null;
                Vertex fail1 = verticies[i];
                Vertex fail2 = verticies[g];
                boolean[] visit = new boolean[verticiesNum];
                
                visit[fail1.getID()] = true;
                visit[fail2.getID()] = true;
                
                if(i != 0){
                    start = verticies[0];
                }
                
                else{
                    if(g != verticiesNum - 1){
                        start = verticies[g + 1];
                    }
                    
                    else if(g - i != 1){
                        start = verticies[g - 1];
                    }
                    
                    else{
                        System.out.println("\nThe graph is not connected when "
                                + "two verticies fail.");
                    }
                }
                
                findConnection(start, fail1, fail2, visit);
                boolean isConnect = true;
                
                for(int j = 0; j < visit.length; j++){
                    if(visit[j] == false){
                        isConnect = false;
                        break;
                    }
                }
                
                if(!isConnect){
                    System.out.println("The graph is not connected when "
                            + "any two verticies fail.");
                    return;
                }
            }
        }
        
        System.out.println("The graph is connected when any two verticies fail.");
        return;
        
    }
    
    /*
     * Helper functions 
     */
    private void drawGraph(String filename){
        if(filename == null){
            return;
        }
        
        File f = new File(filename);
        try{
            scan = new Scanner(f);
        }
        catch(FileNotFoundException fnf){
            System.out.println("Could not find file");
            System.exit(0);
        }
        
        if(scan.hasNextLine()){
            verticiesNum = Integer.parseInt(scan.nextLine());
        }
        
        else{
            return;
        }
        
        verticies = new Vertex[verticiesNum];
        for(int i = 0; i < verticies.length; i++){
            verticies[i] = new Vertex(i);
        }
        
        edges = new ArrayList<Edge>();
        
        while(scan.hasNextLine()){
            String line = scan.nextLine();
            String[] contents = line.split(" ");
            if(contents.length != 5){
                continue;
            }
            
            Vertex vert1 = verticies[Integer.parseInt(contents[0])]; 
            Vertex vert2 = verticies[Integer.parseInt(contents[1])];
            String mat = contents[2];
            int bandwidth = Integer.parseInt(contents[3]);
            int length = Integer.parseInt(contents[4]);
            
            Edge v1 = new Edge(mat, bandwidth, length, vert2, vert1);
            Edge v2 = new Edge(mat, bandwidth, length, vert1, vert2);
            vert1.getConnection().addFirst(v1);
            vert2.getConnection().addFirst(v2);
            edges.add(v1);
            if(mat.equals("optical")){
                copper = false;
            }
           
        }
    }
    
    private Object[] shortestPath(Vertex cur, Vertex dest, String path, double length, int minBandwidth){
        if(cur == null  || dest == null || path == null || length < 0.0){
            return null;
        }
        
        if(cur == dest){
            return new Object[]{path, length, minBandwidth};
        }
        
        LinkedList<Edge> curEdge = cur.getConnection();
        
        double min = -1.0;
        String minPath = "";
        
        for(Edge e : curEdge){
            Vertex edgeDest = e.getDestination();
            if(path.contains("" + edgeDest.getID())){
                continue;
            }
            
            String path_2 = path + edgeDest.getID();
            double len = 0.0;
            
            if(e.getMaterial().equals("copper")){
                len = length + e.getTravelTime();
            }
            
            else if(e.getMaterial().equals("optical")){
                len = length + e.getTravelTime();
            }
            
            else{
                return null;
            }
            
            int band = minBandwidth;
            if(minBandwidth == -1.0 || e.getBandwidth() < minBandwidth){
                band = e.getBandwidth();
            }
            
            Object[] dataPath = shortestPath(edgeDest, dest, path_2, len, band);
            if(dataPath == null){
                continue;
            }
            
            String edgePath = (String)dataPath[0];
            double lenPath = (double)dataPath[1];
            int bandPath = (int)dataPath[2];
            
            if(min == -1 || lenPath < min){
                min = lenPath;
                minPath = edgePath;
                minBandwidth = bandPath;
            }
            else if(lenPath == min && bandPath > minBandwidth){
                min = lenPath;
                minPath = edgePath;
                minBandwidth = bandPath;
            }
        }
        if(min > -1.0){
            return new Object[]{minPath, min, minBandwidth};
        }
        return null;
    }
    
    private int maxVerticies(Vertex cur, Vertex dest, String path, int maxBandwidth){
        int max = -1;
        
        if(cur == null || dest == null || path == null){
            return -1;
        }
        
        if(cur == dest){
            return maxBandwidth;
        }
        
        LinkedList<Edge> curEdge = cur.getConnection();
        for(Edge e : curEdge){
            Vertex edgeDest = e.getDestination();
            
            if(path.contains("" + edgeDest.getID())){
                continue;
            }
            
            int maxPath = maxBandwidth;
            
            if(maxPath == -1 || e.getBandwidth() < maxPath){
                maxPath = e.getBandwidth();
            }
            
            String newPath = path + edgeDest.getID();
            int bandPath = maxVerticies(edgeDest, dest, newPath, maxPath);
            if(bandPath == -1){
                continue;
            }
            
            if(bandPath > max){
                max = bandPath;
            }
        }
        
        return max;
    }
    
    private void findConnection(Vertex cur, Vertex a1, Vertex a2, boolean[] visit){
        if(cur == null || a1 == null || a2 == null || visit == null){
            return;
        }
        
        if(visit[cur.getID()] == true){
            return;
        }
        
        visit[cur.getID()] = true;
        LinkedList<Edge> curEdge = cur.getConnection();
        
        for(Edge e : curEdge){
            Vertex edgeDest = e.getDestination();
            if(visit[edgeDest.getID()] == true){
                continue;
            }
            
            findConnection(edgeDest, a1, a2, visit);
        }
        
        return;
    }
}
