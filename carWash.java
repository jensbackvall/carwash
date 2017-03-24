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
    public static String reportDate;

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
       System.out.print("Postal Index Number: ");
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
                     saldo = Double.parseDouble(linjesplit[1]);
                  }
                  if (linjesplit[0].equals("PIN:"))
                  {
                     PIN = Integer.parseInt(linjesplit[1]);
                  }
                  if (linjesplit[0].equals("Lukket:"))
                  {
                     oc = Integer.parseInt(linjesplit[1]);
                  }
             }
             if (oc == 1)
              {
                System.out.println("Din konto er lukket - buhu");
                login();
                input.close();
                return;
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
                     // Skriv til fil på linje 3 = 1. 1 betyder lukket - 0 betyder ikke lukket.
                  }

                  if(antalFejl < 3)
                  {
                     System.out.println("Fejl - Prøv igen.\nDu har " + (3 - antalFejl) + " forsøg tilbage");
                  }
             }

             else
             {
               loop = true;
               menu();
             }
          }
       }
    }

    public static void WriteStats(String s,String s2)
    {
        ArrayList<String> gamlelinjer = new ArrayList<String>();
        try
        {
            Scanner input = new Scanner(new File("stats.txt"));
            while (input.hasNextLine())
            {
                String linje = input.nextLine();
                String[] linjesplit = linje.split(" ");
                if (linjesplit[0].equals("economy:"))
                {
                    if (s.equals("economy"))
                    {
                        gamlelinjer.add(linjesplit[0]+" "+(Integer.parseInt(linjesplit[1])+1));
                    } else
                    {
                        gamlelinjer.add(linjesplit[0]+" "+linjesplit[1]);
                    }
                } else if (linjesplit[0].equals("standard:"))
                {
                    if (s.equals("standard"))
                    {
                        gamlelinjer.add(linjesplit[0]+" "+(Integer.parseInt(linjesplit[1])+1));
                    } else
                    {
                        gamlelinjer.add(linjesplit[0]+" "+linjesplit[1]);
                    }
                } else if (linjesplit[0].equals("deluxe:"))
                {
                    if (s.equals("deluxe"))
                    {
                        gamlelinjer.add(linjesplit[0]+" "+(Integer.parseInt(linjesplit[1])+1));
                    }else
                    {
                        gamlelinjer.add(linjesplit[0]+" "+linjesplit[1]);
                    }
                } else if (linjesplit[0].equals("antalvaske:"))
                {
                    gamlelinjer.add(linjesplit[0]+" "+(Integer.parseInt(linjesplit[1])+1));
                } else
                {
                    gamlelinjer.add(linje);
                }
            }
            PrintStream writeStats = new PrintStream(new File("stats.txt"));
            for (int i = 0; i < gamlelinjer.size(); i++)
            {
                writeStats.println(gamlelinjer.get(i));
            }
            writeStats.println(s2);
        }
        catch(FileNotFoundException e)
        {
        e.printStackTrace();
        }
    }

    public static void getDayTimeDate()
    {
        DateFormat thisDay = new SimpleDateFormat("E");
        DateFormat thisHour = new SimpleDateFormat("HH");
        Date dateobj = new Date();
        String tempHour = thisHour.format(dateobj);
        day = thisDay.format(dateobj);
        hour = Integer.parseInt(tempHour);


        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        reportDate = df.format(today);
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
            } else if (!day.equals("Sun") && !day.equals("Sat") && hour < 14) {
                System.out.println("Hvor er du heldig!!! Du får 20% rabat");
                return true;
            }
            return false;
    }

    public static void buyWash(int user)
    {
        try
        {
            Scanner console = new Scanner(System.in);
            Scanner userFile = new Scanner(new File("bruger" + user + ".txt"));
            System.out.println("Du kan vælge mellem følgende vasketyper:");
            System.out.println("1. Economy (pris: " + economy + ").");
            System.out.println("2. Standard (pris: " + standard + ").");
            System.out.println("3. DeLuxe (pris: " + deluxe + ").");
            System.out.print("Vælg venligst en vasketype: ");
            int washChoice = console.nextInt();
            String saldoLine = userFile.nextLine();
            String[] linjesplit = saldoLine.split(" ");
            String ub = linjesplit[1];
            String userPin = userFile.nextLine();
            String cardClosed = userFile.nextLine();
            PrintStream writeBalance = new PrintStream(new File("bruger" + user + ".txt"));
            double userBalance = Double.parseDouble(ub);
            boolean correctChoice = false;
            userFile.close();
            getDayTimeDate();
            while(correctChoice == false)
            {
                if(washChoice == 1)
                {
                    correctChoice = true;
                    if (discountCheck() == true && userBalance >= (economy*rabat))
                    {
                        WriteStats("economy", reportDate+" "+"economy");
                        saldo = (int)userBalance - (economy * rabat);
                        System.out.println("Du har valgt og betalt for en Economy vask med Early Bird rabat " + (economy * rabat) + " kr. bliver trukket fra dit kort.");
                    }

                    else if(userBalance >= economy) {
                        WriteStats("economy", reportDate+" "+"Early Bird economy");
                        saldo = (int)userBalance - (economy);
                        System.out.println("Du har valgt og betalt for en Economy vask " + economy + " kr. bliver trukket fra dit kort.");
                    }
                    else
                    {
                      System.out.println("Du har ikke nok penge på kortet!");
                    }
                } else if(washChoice == 2)
                {
                    correctChoice = true;
                    if (discountCheck() == true && userBalance >=(standard*rabat))
                    {
                        WriteStats("standard", reportDate+" "+"standard");
                        saldo = (int)userBalance - (standard * rabat);
                        System.out.println("Du har valgt og betalt for en Standard vask med Early Bird rabat " + (standard * rabat) + " kr. bliver trukket fra dit kort.");
                    } else if(userBalance >= standard)
                    {
                        WriteStats("standard", reportDate+" "+"Early Bird standard");
                        saldo = (int)userBalance - standard;
                        System.out.println("Du har valgt og betalt for en Standard vask " + standard + " kr. bliver trukket fra dit kort.");
                    }
                    else
                    {
                      System.out.println("Du har ikke nok penge på kortet!");
                    }

                } else if(washChoice == 3)
                {
                    if (userBalance >= deluxe)
                    {
                    WriteStats("deluxe", reportDate+" "+"deluxe");
                    correctChoice = true;
                    saldo = (int)userBalance - deluxe;
                    System.out.println("Du har valgt og betalt for en DeLuxe vask " + deluxe + " kr. bliver trukket fra dit kort.");
                    }

                    else
                    {
                    System.out.println("Du har ikke nok penge på kortet!");
                    }
                }
                else
                {
                    System.out.println("Du har ikke valgt en gyldig vask \nTast venligst 1, 2 eller 3!");
                    washChoice = console.nextInt();
                }

                writeBalance.println("Saldo: " + (int)saldo);
                writeBalance.println(userPin);
                writeBalance.println(cardClosed);
                writeBalance.close();
                login();
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

        System.out.println("indtast nye priser paa economy");
        System.out.print("Pris: ");
        economy = sc.nextDouble();

        System.out.println("indtast nye priser paa standard");
        System.out.print("Pris: ");
        standard = sc.nextDouble();

        System.out.println("indtast nye priser paa deluxe");
        System.out.print("Pris: ");
        deluxe = sc.nextDouble();

        System.out.println("Ny pris på economy = " + economy);
        System.out.println("Ny pris på standard = " + standard);
        System.out.println("Ny pris på deluxe = " + deluxe);
    }

    public static void changeRabat()
    {
        Scanner sc = new Scanner (System.in);

        System.out.print("Indtast ny rabat: ");
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
            System.out.print("Vælg venligst en kategori: ");

            choice = in.nextInt();

        switch (choice)
        {
            case 1: optionNotAvailable = false;
                    buyWash(brugernavn);
                    break;

            case 2: optionNotAvailable = false;
                    refill();
                    break;

            case 3: optionNotAvailable = false;
                    System.out.println("Din saldo er: " + saldo +" kr");
                    try {
                        System.out.println("FARVEL");
                        Thread.sleep(3000);                 //1000 milliseconds is one second.
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    login();
                    break;

            case 4: optionNotAvailable = false;
                    receipt(5);
                    break;

            default: System.out.println("Din indtastning er invalid, prøv igen!");
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

            default: System.out.println("Din indtastning er invalid, prøv igen!");
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
        if(washChoice == 1)
        {
            Pris = economy;
        } else if(washChoice == 2)
        {
        Pris = standard;
        } else if(washChoice == 3)
        {
        Pris = deluxe;
        }else
        {
        System.out.println("Kære soede skat vil du ikke nok vælge en rigtig vask og prøve igen!");
        return;
        }
        System.out.println("Tast 1 for kvittering ellers tast 2.");
        a = in.nextInt();
        if(a == 1)
        {
        System.out.println("Her faar du din kvittering fortsat god dag.");
        System.out.println(" " + date);
        System.out.println(" " + Pris);
        System.out.println("Dette er din tilbageværende saldo " + saldo);
        } else
        {
        System.out.println("du faar ingen kvittering fortsat god dag.");
        }
    }


    public static void refill()
    {


      System.out.println("Optankning");
      System.out.println("Indtast beloeb (Mellem 0 og " + (10000-saldo) + ")");
      System.out.print("Beloeb: ");
      Scanner console = new Scanner(System.in);
      double amount = console.nextDouble();


      while(amount < 0 || (amount+saldo) > 10000)
      {
      System.out.println("Det indtastede beløb er ikke mellem 0 og " + (10000-saldo) + " \nPrøv igen!");
      System.out.print("Beloeb: ");
      amount = console.nextDouble();
      }
      double total = amount + saldo;
      System.out.println("***Washcard skubbes ud!!!***");
      System.out.println();
      System.out.println("Indsæt kreditkort");

      System.out.println("***Tryk ENTER når kreditkort er indsat***");
      console.nextLine();
      console.nextLine();

      System.out.print("Indtast pinkode: ");
      int pin = 1234;
      int enteredPin = console.nextInt();
      int count = 0;
      while(enteredPin != pin && count < 2){
      System.out.println("Forkert pinkode - Prøv igen");
      enteredPin = console.nextInt();
      count++;
      }

      if(enteredPin==pin){
      System.out.println("Din saldo er nu: " + total + "kr");
      System.out.print("For at godkende TRYK 1  -  For at fortryde TRYK 2: ");
      System.out.println("Afslut med ENTER");
      }
      else{
      System.out.print("Kortet er spærret");
      }

      int godkendFortrydKøb = console.nextInt();

      while(godkendFortrydKøb != 1 && godkendFortrydKøb != 2) {
      System.out.println("Du har ikke tastet 1 eller 2 - Prøv igen");
      godkendFortrydKøb = console.nextInt();
      }

      if (godkendFortrydKøb == 2){
      login();
      return;
      }

      System.out.println("Udskriv kvittering?");
      System.out.println("Tryk 1 for JA  -  Tryk 2 for NEJ");
      System.out.print("Valg: ");

      int jaNejKvit = console.nextInt();

      while(jaNejKvit != 1 && jaNejKvit != 2) {
      System.out.println("Du har ikke tastet 1 eller 2 - Prøv igen");
      jaNejKvit = console.nextInt();
      }


      if(jaNejKvit == 1){
      System.out.println("***Kvittering udskrives***");
      System.out.println("Du har indsat " + amount + " kr. på vaskekortet");
      System.out.println("Din nye saldo er " + total + " kr.");
      System.out.println("Tryk ENTER for at logge ud - Go' dag!");
      login();
      return;
      }
      else{
      login();
      return;
      }
    }


}


