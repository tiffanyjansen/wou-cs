import java.io.*;
import java.lang.Math;
import java.text.*;
/**
 * A class that finds the prime numbers. 
 * 
 * @author Stacia Fry and Tiffany Jansen 
 * @version 05/23/2017
 */
public class PrimeFinder
{

    /**
     * Constructor for objects of class PrimeFinder
     */
    public PrimeFinder()
    {

    }

    /**
     * Tests if the number is prime
     * @return boolean True or false
     */
    public boolean prime( int num)
    {
        if(num == 1){
            return false;
        }
        else{
            return primeHelper(num, num - 1);
        }
    }

    /**
     * Tests if num is divisible 
     * @returns boolean true or false.
     */
    private boolean primeHelper( int num, int testDivisor)
    {
        if(testDivisor == 1){
            return true;
        }
        else if(num%testDivisor == 0){
            return false;
        }
        else{
            return primeHelper(num, testDivisor - 1);
        }
    }

    /**
     * Better tester for prime numbers.
     * @returns boolean true or false.
     */
    public boolean betterPrime(int N)
    {
        if(N == 1){
            return false;
        }
        else{
            return primeHelper(N, (int) Math.sqrt(N));
        }
    }

    /**
     * Generates and prime numbers between 1 and 1000.
     * Outputs to a File.
     */
    public boolean generatePrimes()
    {
        boolean works = false;
        try{
            FileWriter writer = new FileWriter("Primes.doc");
            for(int i = 1; i < 1001; i++){
                if(betterPrime(i) == true){
                    writer.write(i);
                    System.out.println(i);
                }
            }
            writer.close();
            works = true;
        }
        catch(IOException e){
            System.out.println("There was a problem writing to Primes.");
        }
        return works; 
    }

    public static void main (String[] args)
    {
        PrimeFinder primeFinder = new PrimeFinder();
        System.out.println("Is 4 prime? " + primeFinder.prime(4));
        System.out.println("Is 7 prime? " + primeFinder.prime(7));
        System.out.println("Is 4 prime? " + primeFinder.betterPrime(4));
        System.out.println("Is 7 prime? " + primeFinder.betterPrime(7));
        System.out.println("Is 15000 prime? " + primeFinder.betterPrime(15000));
        System.out.println("Is 1999 prime? " + primeFinder.betterPrime(1999));
        primeFinder.generatePrimes();
    }
}
