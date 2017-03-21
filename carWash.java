import java.util.*;
import java.io.*;

public class carWash {

    public static double economy = 0;
    public static double standard = 0;
    public static double deluxe = 0;
    public static double offer = 0;

    public static void main(String[] args) {

        login();

    }

    public static void login() 
    {

    }
   
   public static void ChangePrices()
   {
      Scanner sc = new Scanner (System.in);
         
      System.out.println("indtast nye priser på economy");
      economy = sc.nextDouble();
      
      System.out.println("indtast nye priser på standard");
      standard = sc.nextDouble();
      
      System.out.println("indtast nye priser på deluxe");
      deluxe = sc.nextDouble();
            
      System.out.println("Ny pris på economy = " + economy);
      System.out.println("Ny pris på standard = " + standard);
      System.out.println("Ny pris på deluxe = " + deluxe);
      
     
    }
   public static void ChangeOffer()
   {
      Scanner sc = new Scanner (System.in);
      
      System.out.println("Indtast ny rabat");
      offer = sc.nextDouble();
      
      System.out.println("Ny rabat = " + offer);
      
      
   }
}

