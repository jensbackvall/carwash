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


     public static void kontooversigt()
    {
    
    System.out.println("Se kontooversigt");
    System.out.println("Din saldo er: " + saldo + "kr");

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

}

