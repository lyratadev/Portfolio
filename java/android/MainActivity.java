package com.example.csumb_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button event;
        event = findViewById(R.id.buttonEnter);
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"testing 123", Toast.LENGTH_SHORT).show();
                startActivity(new Intent (getApplicationContext(),Activity_courseList.class));
            }
        });

        Button eventVideo;
        eventVideo = findViewById(R.id.buttonVideo);
        eventVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (getApplicationContext(),Activity_video.class));
            }
        });

    }



}