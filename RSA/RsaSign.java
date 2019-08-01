import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * CS 1501 Project 5
 * RSA Encryption
 * @author Joseph Reidell
 */

public class RsaSign {
    private static byte[] digest;
    private static LargeInteger hash;
    private static Path p;
    
    
    public static void main(String[] args){
    	
    	if(args.length < 2) {
    		System.out.println("Not enough arguments.");
    		System.out.println("Should be: RsaSign s|v myfile.txt");
    		System.exit(0);
    	}
    	
    	char flag = args[0].charAt(0);
            
        try {
        	p = Paths.get(args[1]);
            byte[] input = Files.readAllBytes(p);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input);
            
            digest = md.digest();
            hash = new LargeInteger(digest);
        }
        catch(IOException e) {
        	System.out.println("Could not find specific file!");
        	System.exit(0);
        }
        catch(NoSuchAlgorithmException ne) {
        	System.out.println("Invalid Algorithm!");
        	System.exit(0);
        }
            
        if(flag == 's'){
        	try {
                FileInputStream privkey = new FileInputStream("privkey.rsa");
                System.out.println("SIGNING FILE....");
                byte[] dArray = new byte[65];
                byte[] nArray = new byte[65];
                
                privkey.read(dArray, 1, 64);
                privkey.read(nArray, 1, 64);
                LargeInteger d = new LargeInteger(dArray);
                LargeInteger n = new LargeInteger(nArray);
                
                privkey.close();
                
                FileOutputStream sigF = new FileOutputStream(args[1] + ".sig");
                sigF.write(hash.modularExp(d, n).getVal());
                sigF.close();
                
                System.out.println("SIGNING COMPLETE!");
            }
            catch(IOException e) {
            	System.out.println("Key file could not be found!");
            }
                //sign();
         }
            
         else if(flag == 'v'){
            try{
            	Path sigFile = Paths.get(args[1] + ".sig");
            	LargeInteger verify = new LargeInteger(Files.readAllBytes(sigFile));
                FileInputStream pubKey = new FileInputStream("pubkey.rsa");
                System.out.println("VERIFYING FILE....");
                byte[] eArray = new byte[65];
                byte[] nArray = new byte[65];
                    
                pubKey.read(eArray, 1, 64);
                pubKey.read(nArray, 1, 64);
                LargeInteger e = new LargeInteger(eArray);
                LargeInteger n = new LargeInteger(nArray);
                    
                pubKey.close();
                    
                if(hash.equals(verify.modularExp(e, n))) {
                    System.out.println("Signature is valid!");
                }
                else {
                    System.out.println("Signature not Valid!");
                }
                   
              }
                
              catch(IOException err){
            	  System.out.println("Key file could not be found!");
              }
         }
	 else {
             System.out.println("Incorrect flag entered! Enter 's' or 'v'!");
         }
    }
}
