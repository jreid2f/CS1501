import java.util.*;

/*
    CS 1501 Project 3
    Apartment Tracker
    @author Joseph Reidell
*/

public class Apartment {
    private String address, city;
    private int aptNumber, zipCode;
    private double sqFoot, price;
    private static Scanner scan;
    
    public Apartment(){
        String add, place;
        int zip, num;
        double change, square;
        scan = new Scanner(System.in);
        
        System.out.print("Enter the street address: ");
        add = scan.nextLine();
        setAddress(add);
        
        System.out.print("Enter the apartment number: ");
        num = scan.nextInt();
        setAptNumber(num);
        scan.nextLine();
        
        System.out.print("Enter the city: ");
        place = scan.nextLine();
        setCity(place);
        
        System.out.print("Enter the zip code: ");
        zip = scan.nextInt();
        setZip(zip);
        
        System.out.print("Enter the price: $");
        change = scan.nextDouble();
        setPrice(change);
        
        System.out.print("Enter the square footage: ");
        square = scan.nextDouble();
        setSqFt(square);
        System.out.println();
        
    }
    
    public Apartment(String address, int number, String city, int zip, double price, double sqFt){
        this.address = address;
        this.aptNumber = number;
        this.city = city;
        this.zipCode = zip;
        this.price = price;
        this.sqFoot = sqFt;
    }
    
    private void setAddress(String add){
        this.address = add;
    }
    
    private void setAptNumber(int num){
        this.aptNumber = num;
    }
    
    public void setCity(String town){
        this.city = town;
    }
    
    public void setZip(int code){
        this.zipCode = code;
    }
    
    public void setPrice(double cost){
        this.price = cost;
    }
    
    public void setSqFt(double sq){
        this.sqFoot = sq;
    }
    
    public String getAddress(){
        return address;
    }
    
    public int getAptNumber(){
        return aptNumber;
    }
    
    public String getCity(){
        return city;
    }
    
    public int getZip(){
        return zipCode;
    }
    
    public double getPrice(){
        return price;
    }
    
    public double getSqFt(){
        return sqFoot;
    }
    
    /*
        This will compare each square foot entered.
    */
    public int compareSqFoot(Apartment apt){
        if(this.sqFoot < apt.sqFoot){
            return -1;
        }
        else if(this.sqFoot == apt.sqFoot){
            return 0;
        }
        else{
            return 1;
        }
    }
    
    /*
        This will compare each price entered.
    */
    public int comparePrice(Apartment apt){
        if(this.price < apt.price){
            return -1;
        }
        else if(this.price == apt.price){
            return 0;
        }
        else{
            return 1;
        }
    }
   
    @Override
    public String toString(){
        return address + "\nApartment Number: " + aptNumber + "\n" + city + ", " + zipCode + 
                "\n" + String.format("%.2f", sqFoot) + " sq " + "$" + String.format("%.2f", price);
    }
    
    
}
