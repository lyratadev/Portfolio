package com.csumb.cst363;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;



public class DataGenerate
{
   public static void main (String[] args)
   {
      Random gen = new Random(); //new random generator
      
            /*
            One team member must do this program.
            10 Random Doctors
            1000 random patients, 
            5000 random prescriptions.
            Tips on generating random data at the end of this document.
            This program is a Java application with a “main” method 
            and uses JDBC statements to obtain a connection 
            and execute SQL statements. Name the java class 
            DataGenerate and use the com.csumb.cst363 package.
            */

   String url = "jdbc:mysql://localhost/mydb";
   String user = "root";
   String password = "YOUR PASSWORD";
   
   try (Connection con = DriverManager.getConnection(url, user, password); ) {
                   con.setAutoCommit(false);

                   //GENERATE 10 DOCTORs
                   ArrayList<Integer> doctorID = new ArrayList<>();
                   
                   for (int i = 0; i < 10; i++)
                   {
                   PreparedStatement ps = con.prepareStatement("insert into doctor(name, specialty, practice_since,  ssn ) values(?, ?, ?, ?)",
                         Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, "Dr. " + DataGenerate.getName() + " " + DataGenerate.getName());
                    ps.setString(2, DataGenerate.getSpecialty());
                    ps.setInt(3, DataGenerate.getPracticeYear());
                    ps.setString(4, DataGenerate.getRandomSSN());

                    ps.executeUpdate();
                    ResultSet rs = ps.getGeneratedKeys();
                    int tempID = 0;
                    if (rs.next()) tempID = rs.getInt(1);
                    doctorID.add(tempID);
                   }


                   //GENERATE 1000 patients
                   String[] CityStateZip = new String[3]; //array for storing citystatezip
                   for (int i = 0; i < 1000; i++) 
                   {
                   CityStateZip =  DataGenerate.getCityStateZip();
                   PreparedStatement ps2 = con.prepareStatement(
                         "insert into patient("
                         + "name, " //1
                         + "birthdate, " //2
                         + "city," //3
                         + "state," //4
                         + "zip,  " //5
                         + "ssn, " //6
                         + "street,"
                         + "primary_doctor_id) " //7
                       + "values(?, ?, ?, ?, ?, ?, ?, ?)",
                         Statement.RETURN_GENERATED_KEYS);
                    ps2.setString(1, DataGenerate.getName());
                    ps2.setString(2, DataGenerate.getRandomDate()); //date
                    ps2.setString(3, CityStateZip[0] ); //city
                    ps2.setString(4, CityStateZip[1] ); //state
                    ps2.setInt(5, Integer.parseInt(CityStateZip[2]) ); //zip
                    ps2.setString(6, DataGenerate.getRandomSSN()); //ssn
                    ps2.setString(7,  DataGenerate.getStreet());
                    ps2.setInt(8, doctorID.get( gen.nextInt(doctorID.size()) ) );

                    ps2.executeUpdate();
                   }
                    
/*
                   select patient_id, primary_doctor_id from patient order by RAND() limit 1;
                   select drug_id from drug order by RAND() limit 1;

                   select count(drug_id) from drug;

                   insert into patient_rx (date_prescribed, quantity, drug_id, patient_id, doctor_id) 
                   VALUES (?, ?, ?, ?, ?)
                   */
              
               //GENERATE 5000 Prescriptions
              //fetch last doctor ID from the database
             int rx_doctor_id = 0;
             int rx_patient_id = 0;
             int rx_drug_id = 0;
             for (int i = 0; i < 5000; i++) //generate 5000 scripts
             {
                     
              //FETCH RANDOM PATIENT ID AND DOCTOR ID TIED TO PATIENT    
              PreparedStatement ps3 = con.prepareStatement(
              "select patient_id, primary_doctor_id from patient order by RAND() limit 1");

              ResultSet rs3 = ps3.executeQuery();
                 while (rs3.next()) {
                 rx_patient_id = rs3.getInt(1);
                 rx_doctor_id = rs3.getInt(2);
         //        System.out.printf("statement 1 random PatientID, DoctorID " + 
           //          rx_patient_id + " " + rx_doctor_id);
                }
             
                 
                 //FETCH RANDOM DRUG ID
              PreparedStatement ps4 = con.prepareStatement(
              "select drug_id from drug order by RAND() limit 1 ");

                    ResultSet rs4 = ps4.executeQuery();
                    while (rs4.next()) {
                       rx_drug_id = rs4.getInt(1);
                      // System.out.printf(" drugid  " +    rx_drug_id);
                          }
                    

                    //ASSIGN RANDOM PATIENTID, DOCTORID AND DRUG INTO TABLE
                    PreparedStatement ps5 = con.prepareStatement(
 "insert into patient_rx(date_prescribed, quantity,drug_id, patient_id, doctor_id) values (?, ?, ?, ?, ?)");
                     ps5.setString(1, DataGenerate.getRandomDate()); //date
                     ps5.setInt(2, (gen.nextInt(5) + 1) ); //city
                     ps5.setInt(3,    rx_drug_id  ); //zip
                     ps5.setInt(4, rx_patient_id  ); //ssn
                     ps5.setInt(5,  rx_doctor_id  );

                     ps5.executeUpdate();
                     
                     
                    }//END FOR
                


                    con.commit();
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
   
       System.out.println("Success");
         
}
   
   public static String getName()
   {
      Random gen = new Random();

      //READS TXT FILE of 1000 names and assigns to a list
      ArrayList<String> firstNames = new ArrayList<>();
      
      int sentinel = 0;
      if (sentinel == 0)
      {
         try {
         File fileNames = new File("src/main/resources/names.txt");
         Scanner myReader = new Scanner (fileNames);
         while (myReader.hasNextLine()) 
         { 
            String data = myReader.nextLine();
            firstNames.add(data);
          //  System.out.println(data);
         }
         myReader.close();
         }
         catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
         }
         sentinel ++;
      }
      
      return firstNames.get(gen.nextInt(1000));
   }



   //function generates a random 9 char SSN
   public static String getRandomSSN()
   {
      //int seed = 2;
      Random gen = new Random();
      StringBuilder SSN = new StringBuilder();
      SSN.setLength(0);
      
         for ( int i = 0; i < 9; i ++)
         {
            SSN.append(gen.nextInt(10));
         }
      return SSN.toString();
   }

   //Returns string of random date within year 2021
   public static String getRandomDate()
   {
      //int seed = 2;
      Random gen = new Random();
      SimpleDateFormat simpleDateFormat = 
            new SimpleDateFormat("YYYY-MM-dd");
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.YEAR,  2021);
                    c.set(Calendar.DAY_OF_YEAR, 1);
                    c.add(Calendar.DAY_OF_YEAR, gen.nextInt(365));
                    Date dt = new Date(c.getTimeInMillis());
                    String random_date = simpleDateFormat.format(dt);
      return random_date;
   }
   
   public static int getPracticeYear()
   {
      //returns a practice year between 2000-2020
      Random gen = new Random();
      return (2000 + gen.nextInt(20));
   }
   
   public static String getSpecialty()
   {
      //returns doctor specialty
      Random gen = new Random();
      String[] specialty = { "Internal Medicine", 
            "Family Medicine", "Pediatrics", "Orthopedics",
            "Dermatology",  "Cardiology", "Gynecology", 
            "Gastroenterology", "Psychiatry", "Oncology" };

      return specialty[gen.nextInt(specialty.length)];
   }
   
   public static String getStreet()
   {
      //returns random list of street names
      Random gen = new Random();
      String[] street = { "Mccarty Dr", "Monte Cielo Dr",  "Monte Leon Dr",
      "N Alpine Dr", "N Alta Dr", "N Arden Dr", "N Bedford Dr",
      "N Carson Rd", "N Elm Dr", "N Foothill Rd", "N Oakhurst Dr", "N Palm Dr",
      "N Sierra Dr", "N Sierra Pl", "Pamela Dr", "Park Way", "Pickfair Way", "Reeves Dr",
      "Ridgecrest Rd", "Ridgedale Dr", "S Hamel Dr", "S Mccarty Dr"};
      
      return street[gen.nextInt(street.length)];
   }
   
   public static String[] getCityStateZip()
   {
      //Creates a string array to be passed containing a list of city, state and zipcode in elements 0-2
      Random gen = new Random();
      String[] city = {"Los Angeles", "Manhattan", "Las Vegas", "Portland", "San Francisco", "Salt Lake"};
      String[] state = {"California", "New York", "Nevada", "Oregon", "California", "Utah"};
      String[] zip = {"90210", "10001", "88901", "97035", "94016", "84116"};
      
      String[] result = new String[3];
      
      int seed = gen.nextInt(city.length);

      
      result[0] = city[seed];
      result[1] = state[seed];
      result[2] = zip[seed];
            
            
      return result;
   }
   
   

} // END CLASS
