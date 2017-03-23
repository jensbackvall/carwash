import java.util.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class carWash
{

    public static double economy = 200;
    public static double standard = 400;
    public static double deluxe = 1000;
    public static double rabat = 0.8;
    public static double saldo = 0;
    public static int brugernavn = 2;
    public static String day = "";
    public static int hour = 0;
    public static int PIN;
    public static int oc;


    public static void main(String[] args)
    {
        login();
    }

    public static void login()
    {
       Scanner in = new Scanner (System.in);

       int antalFejl = 0;

       int password;

       boolean loop = false;
       boolean filIkkeFundet = true;


       while(loop == false)
       {
       filIkkeFundet = true;
       System.out.print("Brugernavn: ");
       brugernavn = in.nextInt();
       System.out.print("Password: ");
       password = in.nextInt();

          try
          {
             Scanner input = new Scanner(new File("bruger"+brugernavn+".txt"));

             while (input.hasNextLine())
             {
                  String linje = input.nextLine();
                  String[] linjesplit = linje.split(" ");
                  if (linjesplit[0].equals("Saldo:"))
                  {
                     saldo = Integer.parseInt(linjesplit[1]);
                  }
                  if (linjesplit[0].equals("PIN:"))
                  {
                     PIN = Integer.parseInt(linjesplit[1]);
                  }
                  if (linjesplit[0].equals("Lukket:"))
                  {
                     oc = Integer.parseInt(linjesplit[1]);
                  }
                  if (oc == 1)
                  {
                    login();
                    input.close();
                    in.close();
                    return;
                  }
             }
             input.close();
          }
          catch(FileNotFoundException e)
          {
            System.out.println("Wash Card blev ikke registeret. Prøv igen!");
            filIkkeFundet = false;
          }

          if(filIkkeFundet == true)
          {
             if(password != PIN)
             {
                  antalFejl++;
                  if(antalFejl >= 3)
                  {
                     System.out.println("Dit kort er nu lukket. Buhu");
                     loop = true;
                     return;
                     // Skriv til fil på linje 3 = 1. 1 betyder lukket - 2 betyder ikke lukket.
                  }

                  if(antalFejl < 3)
                  {
                     System.out.println("Fejl - Prøv igen.\nDu har " + (3 - antalFejl) + " forsøg tilbage.");
                  }
             }

             else
             {
               loop = true;
               menu();
             }
          }
       }
        in.close();
    }

    public static void getDayTimeDate()
    {
        DateFormat thisDay = new SimpleDateFormat("E");
        DateFormat thisHour = new SimpleDateFormat("HH");
        Date dateobj = new Date();
        String tempHour = thisHour.format(dateobj);
        day = thisDay.format(dateobj);
        hour = Integer.parseInt(tempHour);
     }

    public static boolean discountCheck()
    {
            getDayTimeDate();
            if (day == "Sun") {
                System.out.println("Ingen rabat om søndagen!");
                return false;
            } else if (day == "Sat") {
                System.out.println("Ingen rabat om lørdagen!");
                return false;
            } else if (hour < 14) {
                System.out.println("Hvor er du heldig!!! Du får 20% rabat");
                return true;
            }
            return false;
    }

    public static void buyWash(int user)
    {
        try {
            Scanner console = new Scanner(System.in);
            Scanner userFile = new Scanner(new File("bruger" + user + ".txt"));
            System.out.println("Du kan vælge mellem følgende vasketyper:");
            System.out.println("1. Economy (pris: " + economy + ").");
            System.out.println("2. Standard (pris: " + standard + ").");
            System.out.println("3. DeLuxe (pris: " + deluxe + ").");
            System.out.print("Vælg venligst en vasketype:");
            int washChoice = console.nextInt();

            String saldoLine = userFile.nextLine();
            String[] linjesplit = saldoLine.split(" ");
            String ub = linjesplit[1];

            String userPin = userFile.nextLine();
            String cardClosed = userFile.nextLine();
            PrintStream writeBalance = new PrintStream(new File("bruger" + user + ".txt"));
            double userBalance = Double.parseDouble(ub);
            boolean correctChoice = false;
            console.close();
            userFile.close();
            while(correctChoice == false) {
                if(washChoice == 1) {
                    correctChoice = true;
                    if (discountCheck() == true) {
                        saldo = (int)userBalance - (economy * rabat);
                        System.out.println("Du har valgt og betalt for en Economy vask med Early Bird rabat. " + (economy * rabat) + " kr. bliver trukket fra dit kort.");
                    } else {
                        saldo = (int)userBalance - (economy);
                        System.out.println("Du har valgt og betalt for en Economy vask. " + economy + " kr. bliver trukket fra dit kort.");
                    }
                } else if(washChoice == 2) {
                    correctChoice = true;
                    if (discountCheck() == true) {
                        saldo = (int)userBalance - (standard * rabat);
                        System.out.println("Du har valgt og betalt for en Standard vask med Early Bird rabat. " + (standard * rabat) + " kr. bliver trukket fra dit kort.");
                    } else {
                        saldo = (int)userBalance - standard;
                        System.out.println("Du har valgt og betalt for en Standard vask. " + standard + " kr. bliver trukket fra dit kort.");
                    }
                } else if(washChoice == 3) {
                    correctChoice = true;
                    saldo = (int)userBalance - deluxe;
                    System.out.println("Du har valgt og betalt for en DeLuxe vask. " + deluxe + " kr. bliver trukket fra dit kort.");
                } else {
                    System.out.println("Du har ikke valgt en gyldig vask. Tast venligst 1, 2 eller 3!");
                    washChoice = console.nextInt();
                }
                writeBalance.println((int)saldo);
                writeBalance.println(userPin);
                writeBalance.println(cardClosed);
                writeBalance.close();
                return; //Skal kalde kvitteringmetoden og derefter returnere til loginmetoden
            }
        }
        catch(FileNotFoundException e) {
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

        System.out.println("Ny pris på economy = " + economy);
        System.out.println("Ny pris på standard = " + standard);
        System.out.println("Ny pris på deluxe = " + deluxe);
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

    public static void menu() {
        if( brugernavn == 1) {
            admin();
        } else
        {
        customer();
        }
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
            case 1: optionNotAvailable = false;
                    buyWash(brugernavn);
                    break;

            case 2: optionNotAvailable = false;
                    System.out.print("Indsæt optanknings method");
                    break;

            case 3: optionNotAvailable = false;
                    System.out.print("indsæt konto oversigt method");
                    break;

            case 4: optionNotAvailable = false;
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
            System.out.print("Valg: ");
            choice = in.nextInt();

        switch(choice)
        {
            case 1: optionNotAvailable = false;
                    System.out.println("Indsæt aendre priser method");
                    break;

            case 2: optionNotAvailable = false;
                    System.out.println("Indsæt se statistik method");
                    break;

            default: System.out.println("Din indtastning er invalid, prøv igen");
            break;
        }
    }
    }



  public static void receipt(int washChoice)
  {
        Scanner in = new Scanner(System.in);
        int a;
            double Pris;
            Date date = new Date();
            if(washChoice == 1) {
            Pris = economy;
            }else if(washChoice == 2) {
        Pris = standard;
}else if(washChoice == 3) {
  Pris = deluxe;
}else {
   System.out.println("Kære soede skat vil du ikke nok vælge en rigtig vask og prøve igen");
return;
}
        System.out.println("Tast 1 for kvittering ellers tast 2.");
       a = in.nextInt();
     if(a == 1) {
   System.out.println("Her faar du din kvittering fortssat god dag");
     System.out.println(" " + date);
     System.out.println(" " + Pris);
        System.out.println("Dette er din tilbageværende saldo " + saldo);
        } else {
   System.out.println("du faar ingen kvittering fortsat god dag");
     }
   }


    // public static void refill()
    // {
    //   System.out.println("Optankning");
    //   System.out.println("Indtast beløb (Mellem 1-10.000)");
    //   Scanner console = new Scanner(System.in);
    //   double amount = console.nextDouble();

    //   while(amount < 1  || amount > 10000) {
    //   System.out.println("Det indtastede beløb er ikke mellem 1 og 10.000 - Prøv igen");
    //   amount = console.nextDouble();
    //   }

    //   System.out.println("***Wash card ejets***");
    //   System.out.println();
    //   System.out.println("Indsæt kreditkort");

    //   System.out.println("***Press ENTER when credit card has been inserted***");
    //   console.nextLine();
    //   console.nextLine();

    //   System.out.println("Indtast pinkode");
    //   int pin = 1234;
    //   int enteredPin = console.nextInt();
    //   int count = 0;
    //   while(enteredPin != pin && count < 2){
    //   System.out.println("Forkert pinkode - Prøv igen");
    //   enteredPin = console.nextInt();
    //   count++;
    //   }

    //   if(enteredPin==pin){
    //   System.out.println(amount + "kr");
    //   System.out.println("For at godkende TRYK 1  -  For at fortryde TRYK 2");
    //   System.out.println("Afslut med ENTER");
    //   }
    //   else{
    //   System.out.print("Korter er spærret");
    //   }

    //   int godkendFortrydKøb = console.nextInt();

    //   while(godkendFortrydKøb != 1 && godkendFortrydKøb != 2) {
    //   System.out.println("Du har ikke tastet 1 eller 2 - Prøv igen");
    //   godkendFortrydKøb = console.nextInt();
    //   }

    //   if (godkendFortrydKøb == 2){
    //   console.close;
    //   login();
    //   return;
    //   }

    //   System.out.println("Udskriv kvittering?");
    //   System.out.println("Tryk 1 for JA  -  Tryk 2 for NEJ");

    //   int jaNejKvit = console.nextInt();

    //   while(jaNejKvit != 1 && jaNejKvit != 2) {
    //   System.out.println("Du har ikke tastet 1 eller 2 - Prøv igen");
    //   jaNejKvit = console.nextInt();
    //   }


    //   if(jaNejKvit == 1){
    //   System.out.println("***Kvittering udskrives***");
    //   System.out.println("Du har indsat " + amount + "kr. på vaskekortet");
    //   System.out.println("Din nye saldo er" + (amount + saldo) + " kr.");
    //   System.out.println("Tryk ENTER for at logge ud - Go' dag!");
    //   console.nextLine();
    //   console.nextLine();
    //   }
    //   else{
    //   console.close;
    //   login();
    //   return;
    //   }
    // }


}


