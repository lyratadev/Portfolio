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

public class FDAReport {

    public static void runReport(String drugName, String startDate, String endDate ) {
        String url = "jdbc:mysql://localhost/mydb";
        String user = "root";
        String password = "1234";

        try (Connection con = DriverManager.getConnection(url, user, password);) {

            PreparedStatement FDAReport = con.prepareStatement("SELECT doctor.name, sum(patient_rx.quantity) as total_quantity_sold  FROM patient_rx " +
                    "INNER JOIN doctor ON doctor.doctor_id = patient_rx.doctor_id " +
                    "INNER JOIN drug ON drug.drug_id = patient_rx.drug_id WHERE drug.trade_name LIKE ? AND patient_rx.date_prescribed BETWEEN ? AND ? GROUP BY patient_rx.doctor_id;");
            FDAReport.setString(1, "%" + drugName + "%");
            FDAReport.setString(2, startDate);
            FDAReport.setString(3, endDate);

            ResultSet FDAReportResults = FDAReport.executeQuery();
            FDAReportResults.next();
            do {
                System.out.println( FDAReportResults.getString(1) + " wrote, a total of " + FDAReportResults.getString(2) + " Prescriptions, during this time frame.");
            } while(FDAReportResults.next());




            } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
