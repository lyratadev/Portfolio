package com.csumb.cst363;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Cst363Application {

    public static void main(String[] args) {

    	// Data Generation Program Functionality
		DataGenerate.main(args);
		
		// FDA Report 
		FDAReport.runReport("Xanax", "2021-03-04", "2021-09-13");
		
		// Spring Application
    	SpringApplication.run(Cst363Application.class, args);
    }

}
