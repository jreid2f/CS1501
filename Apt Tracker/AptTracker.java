import java.io.*;
import java.util.*;

/*
    CS 1501 Project 3
    Apartment Tracker
    @author Joseph Reidell
*/

public class AptTracker {
    
    public static Scanner scan;
    public static int track = 0;
    private static IndexMaxPQ maxPQ;
    private static IndexMinPQ minPQ;

    public static void main(String[] args) {
        
        scan = new Scanner(System.in);
        int userChoice = 0;
        int maxN = 10;
        boolean exitChoice = false;
        
        System.out.println("Welcome to My Apartment Tracker\n");

        minPQ = new IndexMinPQ(maxN);
        maxPQ = new IndexMaxPQ(maxN);
        
        while(!exitChoice){
            System.out.println("-------- MAIN MENU --------\n"
            + "Select an option by entering the number next to the option name.\n"
            + "\t1. Add a new apartment for rent\n"
            + "\t2. Update an existing apartment\n"
            + "\t3. Remove a apartment from consideration\n"
            + "\t4. Retrieve the lowest price apartment\n"
            + "\t5. Retrieve the highest square footage apartment\n"
            + "\t6. Retrieve the lowest price apartment by city\n"
            + "\t7. Retrieve the hightest square footage apartment by city\n"
            + "\t8. Exit\n");
        
            System.out.print("Please enter an option number: ");
            userChoice = scan.nextInt();
            System.out.println();
        
            switch(userChoice){
                case 1:
                    addApartment();
                    break;
                case 2:
                    updateApt();
                    break;
                case 3:
                    removeApt();
                    break;
                case 4:
                    findCheapApt();
                    break;
                case 5:
                    findLargeApt();
                    break;
                case 6:
                    findCheapCity();
                    break;
                case 7:
                    findLargeCity();
                    break;
                case 8:
                    exitChoice = true;
                    break;
                default:
                    System.out.printf("Not a valid integer, please try again.\n");
                    break;
                     
            }
        }
        
        System.exit(0);
    }
    
    /*
        This method will create a new Apartment. The Apartment method will
        ask the user for all the information about the new apartment. Once
        the user enters the information, it will insert the new apartment and
        the index into the min and max priority queue.
    */
    private static void addApartment(){
        track++;
        Apartment apt = new Apartment();
        minPQ.insert(track, apt);
        maxPQ.insert(track, apt);
    }
    
    /*
        This will ask the user for the street address, apartment number, and
        zip code of the specific apartment. It will then prompt the user if
        they want to update the price of the apartment entered. If yes, it will
        take the updated price and set/change the apartments price in both
        the min and max priority queue.
    */
    private static void updateApt(){
        int aptNum, zip;
        String address, c;
        double price;
        boolean askUser;
        
        scan.nextLine();
        System.out.print("Enter the street address: ");
        address = scan.nextLine();
        
        System.out.print("Enter the apartment number: ");
        aptNum = scan.nextInt(); 
        
        System.out.print("Enter the zip code: ");
        zip = scan.nextInt();
        
        int index = minPQ.checkQueue(address, aptNum, zip);
        
        if(index == -1){
            System.out.println("Could not find Apartment, back to main menu!\n");
        }
        else{
            scan.nextLine();
            System.out.print("Would you like to update the price?(y/n): ");
            while (true) {
            c = scan.nextLine().trim().toLowerCase();
            if (c.equals("y")) {
                askUser = true;
                System.out.print("Enter the updated price: $");
                price = scan.nextDouble();
                
                minPQ.apt[index].setPrice(price);
                maxPQ.apt[index].setPrice(price);
                
                minPQ.change(index, minPQ.apt[index]);
                maxPQ.change(index, maxPQ.apt[index]);
                
                System.out.println("Apartment Price successfully updated!\n");
            break;
            } else if (c.equals("n")) {
                askUser = false;
                break;
            }
        }
        }
    }
    
    /*
        This will ask the user for the street address, apartment number, and
        zip code of the specific apartment. It will then check for the 
        apartment and delete that key from the queue.
    */
    private static void removeApt(){
        String address;
        int aptNum, zip;
        
        scan.nextLine();
        System.out.print("Enter the street address: ");
        address = scan.nextLine();
        
        if(address.length() < 0){
            System.out.println("Nothing entered. Back to main menu!\n");
        }
        else{
            System.out.print("Enter the apartment number: ");
            aptNum = scan.nextInt(); 
        
        
            System.out.print("Enter the zip code: ");
            zip = scan.nextInt();
        
            int index = minPQ.checkQueue(address, aptNum, zip);
            if(index == -1){
                System.out.println("Could not find Apartment, back to main menu!\n");
            }
            else{
                minPQ.delete(index);
                maxPQ.delete(index);
                track--;
                System.out.println("Apartment successfully removed!\n");
            }
            
        }
       
            
        
    }
    
    /*
        This will look at the minimum key to figure out the cheapest price.
        When the key is first entered, it compared the prices and set 
        the lowest as the minimum key.
    */
    private static void findCheapApt(){
        Apartment apt = minPQ.minKey();
        System.out.println("The Cheapest Apartment:");
        System.out.println("Address: " + apt.getAddress());
        System.out.println("City & Zip: " + apt.getCity() + ", " + apt.getZip());
        System.out.println("Price: $" + apt.getPrice());
        System.out.println("Square Foot: " + apt.getSqFt() + "\n");
    }
    
    /*
        This will ask the user to enter a city. It will compare the city's in
        the queue. If there is multiple it will then check the price in each
        key.
    */
    private static void findCheapCity(){
        String city;
        System.out.print("Enter the city: ");
        city = scan.next();
        System.out.println();
        
        if(city.length() < 0){
            System.out.println("Nothing entered. Back to main menu!\n");
        }
        else{
            Apartment apt = minPQ.getMinCity(city);
            System.out.println(city + "'s Cheapest Apartment:");
            System.out.println("Address: " + apt.getAddress());
            System.out.println("City & Zip: " + apt.getCity() + ", " + apt.getZip());
            System.out.println("Price: $" + apt.getPrice());
            System.out.println("Square Foot: " + apt.getSqFt() + "\n");
        }
    }
    
    /*
        This will look at the maximum key to figure out the largest sq foot.
        When the key is first entered, it compared the square feet and set 
        the highest as the maximum key.
    */
    private static void findLargeApt(){
        Apartment apt = maxPQ.maxKey();
        System.out.println("The Largest Square Foot Apartment:");
        System.out.println("Address: " + apt.getAddress());
        System.out.println("City & Zip: " + apt.getCity() + ", " + apt.getZip());
        System.out.println("Price: $" + apt.getPrice());
        System.out.println("Square Foot: " + apt.getSqFt() + "\n");
    }
    
    /*
        This will ask the user to enter a city. It will compare the city's in
        the queue. If there is multiple it will then check the square foot
        in each key.
    */
    private static void findLargeCity(){
        String city;
        System.out.print("Enter the city: ");
        city = scan.next();
        System.out.println();
        
        if(city.length() < 0){
            System.out.println("Nothing entered. Back to main menu!\n");
        }
        else{
            Apartment apt = maxPQ.getMaxCity(city);
            System.out.println(city + "'s Largest Square Foot Apartment:");
            System.out.println("Address: " + apt.getAddress());
            System.out.println("City & Zip: " + apt.getCity() + ", " + apt.getZip());
            System.out.println("Price: $" + apt.getPrice());
            System.out.println("Square Foot: " + apt.getSqFt() + "\n");
        }
    }
    
}
