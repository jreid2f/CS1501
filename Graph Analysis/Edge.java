
/**
 * CS 1501 Project 4
 * Graph Analyzer
 * @author Joseph Reidell
 */

public class Edge implements Comparable<Edge> {
    private String material;
    private int bandwidth;
    private int length;
    private Vertex source;
    private Vertex destination;
    private double travelTime;
    private final int copperSpeed = 230000000;
    private final int fiberSpeed = 200000000;
    
    public Edge(String mat, int bandwid, int len, Vertex dest, Vertex sour){
        material = mat;
        bandwidth = bandwid;
        length = len;
        destination = dest;
        source = sour;
        
        if(material.equals("copper")){
            travelTime = ((double)1/copperSpeed) * length * Math.pow(10, 9);
        }
        else if(material.equals("optical")){
            travelTime = ((double)1/fiberSpeed) * length * Math.pow(10, 9);
        }
    }
    
    public void setMaterial(String mat){
        material = mat;
    }
    
    public String getMaterial(){
        return material;
    }
    
    public void setBandwidth(int bandwid){
        bandwidth = bandwid;
    }
    
    public int getBandwidth(){
        return bandwidth;
    }
    
    public void setLength(int len){
        length = len;
    }
    
    public int getLength(){
        return length;
    }
    
    public void setDestination(Vertex dest){
        destination = dest;
    }
    
    public Vertex getDestination(){
        return destination;
    }
    
    public void setTravelTime(double time) {
    	travelTime = time;
    }
    
    public double getTravelTime(){
        return travelTime;
    }
    
    public void setSource(Vertex sour) {
    	source = sour;
    }
    
    public Vertex getSource(){
        return source;
    }
    
    @Override
    public int compareTo(Edge nextEdge) {
        if(travelTime > nextEdge.getTravelTime()){
            return 1;
        }
        else if(travelTime == nextEdge.getTravelTime()){
            return 0;
        }
        else{
            return -1;
        }
    }    
}
