import java.io.*;
import java.util.*;


/**
 * CS 1501 Project 5
 * RSA Encryption
 * @author Joseph Reidell
 */

public class RsaKeyGen {
    
    public static void main(String args[]){
    	LargeInteger one = new LargeInteger(new byte[] {(byte) 1});
        LargeInteger two = new LargeInteger(new byte[] {(byte) 2});
        
        Random rd = new Random();
        
        LargeInteger p;
        LargeInteger q;
        LargeInteger n;
        LargeInteger e;
        LargeInteger d;
        LargeInteger phiN;
        
        //do-while loop checks if the numbers randomly generated end up being negative
        do {
        	 p = new LargeInteger(256, rd);	//create new LargeInteger p with a random integer
             q = new LargeInteger(256, rd); //create new LargeInteger q with a random integer
         
             n = p.multiply(q);	//multiply p and q to get n.
             
             //calculate phiN by doing (p - 1) * (q - 1)
             phiN = (p.subtract(one)).multiply(q.subtract(one));
             e = new LargeInteger(512, rd);	//create new LargeInteger e with a random integer
        }while(phiN.subtract(e).isNegative() || !phiN.XGCD(e)[0].subtract(two).isNegative());
        
        //using modular exponentiation to calculate d
        d = e.modularExp(one.negate(), phiN);	
        
        try{
        	//create the pubkey.rsa file
        	System.out.println("GENERATING KEYS....");
            FileOutputStream pubkey = new FileOutputStream("pubkey.rsa");
            pubkey.write(e.resize().getVal());
            pubkey.write(n.resize().getVal());
            pubkey.close();
            
            //create the privkey.rsa file
            FileOutputStream privKey = new FileOutputStream("privkey.rsa");
            privKey.write(d.resize().getVal());
            privKey.write(n.resize().getVal());
            privKey.close();
            
            System.out.println("KEYS SUCCESSFULLY GENERATED!");
        }
        
        catch(IOException io){
            System.out.println("Sorry there was an error. Error: " + io.toString());
            return;
        }
    
    }
}
