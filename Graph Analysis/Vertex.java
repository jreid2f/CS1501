import java.util.*;

/**
 * CS 1501 Project 4
 * Graph Analyzer
 * @author Joseph Reidell
 */

public class Vertex {
    private LinkedList<Edge> connection;
    private int id;
    
    public Vertex(int ID){
        id = ID;
        connection = new LinkedList<Edge>();
    }
    
    public void setConnection(LinkedList<Edge> connect) {
    	connection = connect;
    }
    
    public LinkedList<Edge> getConnection(){
        return connection;
    }
    
    public void setID(int identify) {
    	id = identify;
    }
    
    public int getID(){
        return id;
    }
}
