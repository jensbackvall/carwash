import java.util.*;
import java.io.*;

public class carWash 
{
    
    public static double economy = 200;
    public static double standard = 400;
    public static double deluxe = 1000;
    public static double rabat = 0.8;
    public static double saldo = 0;
    public static String brugernavn = "";

    public static void main(String[] args) 
    {

        login();

    }

    public static void login() 
    {

    }
    
   public static void buyWash(int user) {
        try {
        Scanner console = new Scanner(System.in);
        Scanner userFile = new Scanner(new File(user + ".txt"));
        System.out.println("Du kan vælge mellem følgende vasketyper:");
        System.out.println("1. Economy (pris: " + economy + ").");
        System.out.println("2. Standard (pris: " + standard + ").");
        System.out.println("3. DeLuxe (pris: " + deluxe + ").");
        System.out.println("Vælg venligst en vasketype (skriv 1, 2 eller 3):");
        int washChoice = console.nextInt();
        String ub = userFile.nextLine();
        String up = userFile.nextLine();
        PrintStream writeBalance = new PrintStream(new File(user + ".txt"));
        double userBalance = Double.parseDouble(ub);
        double userPin = Double.parseDouble(up);
        boolean correctChoice = false;
        while(correctChoice == false) {
            if(washChoice == 1) {
                correctChoice = true;
                writeBalance.println((int)userBalance - economy);
                writeBalance.println((int)userPin);
                System.out.println("Du har valgt og betalt for en Economy vask. " + economy + " kr. bliver trukket fra dit kort.");
                return;
            } else if(washChoice == 2) {
                correctChoice = true;
                writeBalance.println((int)userBalance - standard);
                writeBalance.println((int)userPin);
                System.out.println("Du har valgt og betalt for en Stadard vask. " + standard + " kr. bliver trukket fra dit kort.");
                return;
            } else if(washChoice == 3) {
                correctChoice = true;
                writeBalance.println((int)userBalance - deluxe);
                writeBalance.println((int)userPin);
                System.out.println("Du har valgt og betalt for en DeLuxe vask. " + deluxe + " kr. bliver trukket fra dit kort.");
                return;
            } else {
                System.out.println("Du har ikke valgt en gyldig vask. Tast venligst 1, 2 eller 3!");                    washChoice = console.nextInt();
            }
            }
            }
            catch(FileNotFoundException e)
            {
            e.printStackTrace();
            }
    }
   
   public static void changePrices()
   {
      Scanner sc = new Scanner (System.in);
         
      System.out.println("indtast nye priser pÃ¥ economy");
      economy = sc.nextDouble();
      
      System.out.println("indtast nye priser pÃ¥ standard");
      standard = sc.nextDouble();
      
      System.out.println("indtast nye priser pÃ¥ deluxe");
      deluxe = sc.nextDouble();
            
      System.out.println("Ny pris pÃ¥ economy = " + economy);
      System.out.println("Ny pris pÃ¥ standard = " + standard);
      System.out.println("Ny pris pÃ¥ deluxe = " + deluxe);
      
     
    }
   public static void changeRabat()
   {
      Scanner sc = new Scanner (System.in);
      
      System.out.println("Indtast ny rabat");
      rabat = (100. - sc.nextDouble())/100.;
      
      System.out.println("Ny rabat = " + rabat);
      
      
   }


    
    
    public static void ReadPrice()
    {
       try {
       Scanner input = new Scanner(new File("Admin.txt"));
       while (input.hasNextLine()) 
           {
               String linje = input.nextLine();
               String[] linjesplit = linje.split(" ");
               if (linjesplit[0].equals("economy:"))
               {
                  economy = Double.parseDouble(linjesplit[1]);
                  return;
               }
               if (linjesplit[0].equals("standard:"))
               {
                  standard = Double.parseDouble(linjesplit[1]);
                  return;
               }
               if (linjesplit[0].equals("deluxe:"))
               {
                  deluxe = Double.parseDouble(linjesplit[1]);
                  return;
               }
               if (linjesplit[0].equals("rabat:"))
               {
                  Double rabatbeforecalc = Double.parseDouble(linjesplit[1]);
                  rabat = (100-rabatbeforecalc)/100;
                  return;
               }
           }
       }
       catch(FileNotFoundException e) 
       {
        e.printStackTrace();
       }
    }
    
    public static menu(String bruger) 
{
    if( bruger.equals("01"))
    admin();
    else
    customer();
}



public static void customer()
{  

   int choice;
   boolean optionNotAvailable = true;
   Scanner in = new Scanner(System.in);
      
   while (optionNotAvailable == true)
   {
      System.out.println("Indtast kategoriens nummer for at vaelge den");
      System.out.println("1) Vaskemuligheder");
      System.out.println("2) Optankning");
      System.out.println("3) Konto oversigt");
      System.out.println("4) Kvittering");
      choice = in.nextInt();
                     
      switch (choice)
      {
         case 1:  optionNotAvailable = false;
                  admin();
                  break;
                     
         case 2:  optionNotAvailable = false;
                  System.out.print("Indsæt optanknings method");
                  break;
                     
         case 3:  optionNotAvailable = false;
                  System.out.print("indsæt konto oversigt method");
                  break;
                     
         case 4:  optionNotAvailable = false;
                  System.out.print("indsæt kvittering method");
                  break;
                    
         default: System.out.println("Din indtastning er invalid, prøv igen");
      }
   }
}

public static void admin()
{
   boolean optionNotAvailable = true;
   int choice;
   Scanner in = new Scanner(System.in);
   
   while( optionNotAvailable == true )
   {
      System.out.println("Indtast kategoriens nummer for at vaelge den");
      System.out.println("1) aendre priser");
      System.out.println("2) Se statestik");
      choice = in.nextInt();
   
      switch(choice)
      {
         case 1:  optionNotAvailable = false;
                  System.out.println("Indsæt aendre priser method");
                  break;
                  
         case 2:  optionNotAvailable = false;
                  System.out.println("Indsæt se statistik method");
                  break;
                  
         default: System.out.println("Din indtastning er invalid, prøv igen");
                  break;  
      }
   }
}

  public static void refill()
  {
      System.out.println("Optankning");
      System.out.println("Indtast beløb (Mellem 1-10.000)");
      Scanner console = new Scanner(System.in);
      double amount = console.nextDouble();
      System.out.println("***Wash card ejets***");
      System.out.println();
      System.out.println("Indsæt kreditkort");
      
      System.out.println("***Press enter when credit card has been inserted***");
      console.nextLine();
      console.nextLine();
      System.out.println("Indtast pinkode");
      int pin = 1234;
      int enteredPin = console.nextInt();
      int count = 0;
      while(enteredPin != pin && count < 2) {
      System.out.println("Forkert pinkode - Prøv igen");
      enteredPin = console.nextInt();
      count++;
      }
      if(enteredPin==pin){
      System.out.println(amount + "kr");
      System.out.println("Tryk 1 og enter for at godkende");
      System.out.println("Tryk 2 og enter for at fortryde");
      }
      else{
      System.out.print("Korter er spærret");
      }
      int godkendFortryd = console.nextInt();
      while(godkendFotryd != 1 || godkendFotryd != 2) {
      
      }   
  }
    
    
}

