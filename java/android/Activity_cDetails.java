package com.example.csumb_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Activity_cDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdetails);
        String courseID = Activity_courseList.getCourseId();
        String courseText = Activity_courseList.getCourseText();

        TextView cID = findViewById(R.id.txt_title);
        TextView cText = findViewById(R.id.txt_body);

        cID.setText(courseID);
        cText.setText(courseText);

    }
}