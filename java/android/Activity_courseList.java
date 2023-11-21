package com.example.csumb_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_courseList extends AppCompatActivity {

    //PRIVATE VARIABLE TO STORE PRESENT COURSE ID + TEXT ARRAY POSITION
    private static String courseID;
    private static String courseText;

    //PUBLIC ACCESSOR
    public static String getCourseId() {
        return courseID;
    }

    public static String getCourseText()
    {
        return courseText;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        ListView listview;

        listview = (ListView) findViewById(R.id.listView_courses); //LOCATE LIST FROM XML

        //new array list
        ArrayList<String> arrayList = new ArrayList<>(); //array list to store titles
        ArrayList<String> arrayList2 = new ArrayList<>(); //Array list to store descriptions

        arrayList.add("CST 300 - Major ProSeminar");
        arrayList2.add(
                //CST300
                "Helps students identify and articulate personal, professional, " +
                "and social goals. Provides an integrated overview of the computer science " +
                "and communication design majors and their requirements. Students develop " +
                "a plan for their learning goals. Students learn writing, presentation, " +
                "research and critical-thinking skills within the diversified fields of information " +
                "technology and communication design. Students learn how to analyze, predict, and " +
                "articulate trends in the academic, public service");

        arrayList.add("CST 338 - Software Design");
        arrayList2.add(
                //CST338
                "Provides students with the fundamental concepts to develop large-scale software, " +
                "focusing on the object-oriented programming techniques. " +
                "Coverage includes the introduction to Java programming language, " +
                "object-oriented programming, software life cycle and development processes, " +
                "requirements analysis, and graphical user interface development.");

        arrayList.add("CST 311 - Intro to Computer Networks");
        arrayList2.add(
        //CST 311 - Intro to Computer Networks
                "Survey of Telecomm and Data Comm Technology Fundamentals, Local Area Network, " +
                "Wide Area Network, Internet and internetworking protocols including TCP/IP, " +
                "network security and performance, emerging industry trends such as voice over " +
                "the network and high speed networking. Designed as a foundation for students who " +
                "wish to pursue more advanced network studies including certificate programs. " +
                "Includes hands-on networking labs that incorporate Cisco CCNA lab components.");
        arrayList.add("CST 334 - Operating Systems");
        arrayList2.add(
        //CST 334
                "Students in this course will learn about the use and design of modern operating " +
                "systems, focusing on Linux. On the “use” side, students will learn the " +
                "Linux command line, to write shell scripts, and to build programs with" +
                " GNU utilities like awk, sed, and make. On the “design” side, students " +
                "will develop a deep understanding of process management, memory management," +
                " file systems, and concurrency, and how they apply to modern technologies" +
                " like virtualization and cloud computing.");

        arrayList.add("CST 336 - Internet Programming");
        arrayList2.add(
//cst336
                "Provides students with dynamic web application development skills, " +
                "focusing on the integration of server-side programming, database" +
                " connectivity, and client-side scripting. Coverage includes the " +
                "Internet architecture, responsive design, RESTful web services, and Web APIs");


        arrayList.add("CST 363 - Introduction to Database Systems");
        arrayList2.add(
//CST363
                "This course provides balanced coverage of database use and design,focusing " +
                        "on relational databases. Students will learn to design relational schemas," +
                        " write SQL queries, access a DB programmatically,and perform database" +
                        " administration. Students will gain a working knowledge of the algorithms" +
                        " and data structures used in query evaluation and transaction processing." +
                        " Students will also learn to apply newer database technologies such " +
                        "as XML, NoSQL, and Hadoop.");

        arrayList.add("CST 370 - Design and Analysis of Algorithms");
        arrayList2.add(
//CST370
                "Students learn important data structures in computer science " +
                        "and acquire fundamental algorithm design techniques to " +
                        "get the efficient solutions to several computing problems " +
                        "from various disciplines. Topics include the analysis of algorithm " +
                        "efficiency, hash, heap, graph, tree, sorting and searching, brute force," +
                        " divide-and-conquer, decrease-and-conquer, transform-and-conquer, " +
                        "dynamic programming, and greedy programming.");

        arrayList.add("CST 438 - Software Engineering");
        arrayList2.add(
//CST438
                "Prepares students for large-scale software development using software " +
                        "engineering principles and techniques. Coverage includes software " +
                        "process, requirements analysis and specification, software design," +
                        " implementation, testing, and project management. Students are expected " +
                        "to work in teams to carry out a realistic software project.");

        arrayList.add("CST 462S - Race, Gender, Class in the Digital World");
        arrayList2.add(
//CST 4625
                "Provides students with key knowledge of race, gender, class and social " +
                        "justice especially in relation to technology in today’s digital world. " +
                        "Students challenge the barriers of expertise, gender, race, class, and " +
                        "location that restrict wider access to and understanding of the production " +
                        "and usage of new technologies. Students will engage in a practical " +
                        "experience in the community via their service placements, which will" +
                        " provide depth and context for considering questions of justice, equality," +
                        " social responsibilities and the complexities of technology and its " +
                        "societal impact. The course uses scenario based approach combining " +
                        "presentations, discussions, and reflections to allow students explore " +
                        "the relationship between critical reflection and action on the topics " +
                        "mentioned above. ");



        arrayList.add("CST 499 - Computer Science Capstone");
        arrayList2.add(
//CST499
                "Students will work on a project in large groups (up to 5 students in each group), " +
                "developing requirements specification, a solution plan followed by design" +
                " and implementation of the solution. The problem statement for the projects" +
                " will be selected by the faculty. Faculty will also play the role of a project" +
                " manager directing the schedule and deliverables for these projects.");

        arrayList.add("CST 383 - Introduction to Data Science");
        arrayList2.add(
//CST383
                "In data science, data analysis and machine learning techniques are applied to " +
                "visualize data, understand trends, and make predictions. In this course " +
                "students will learn how to obtain data, preprocess it, apply machine" +
                " learning methods, and visualize the results. A student who completes the " +
                "course will have enough theoretical knowledge, and enough skill with modern " +
                "statistical programming languages and their libraries,to define and perform" +
                " complete data science projects.");

        arrayList.add("CST 325 - Graphics Programming");
        arrayList2.add(
//CST325
                "This course teaches the students the fundamentals of game programming and " +
                        "skills needed for game development, including GPU programming, matrix " +
                        "and quaternion algebra for physics calculation, animation, lighting and " +
                        "basics of implementing 3D models into a framework.");


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(Activity_courseList.this, "Selected item " + position + " " + arrayList.get(position).toString(), Toast.LENGTH_SHORT).show();

                    courseID = arrayList.get(position);
                    courseText = arrayList2.get(position);
                    startActivity(new Intent(getApplicationContext(),Activity_cDetails.class));

            }
        });//END OF LISTENER
    }//END ONCREATE()
}//END OF CLASS