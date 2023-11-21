package com.example.csumb_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class Activity_video extends AppCompatActivity {

    Button play; //BUTTON FOR PLAYBACK
    VideoView mVideoView; //VIEW FOR VIDEO
    MediaController mediaC; //CONTROLLER FOR VIDEO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        play = (Button)findViewById(R.id.button_play); //LOCATE BUTTONS FROM XML
        mVideoView = (VideoView)findViewById(R.id.video_view); //LOCATE VIEW FROM XML
        mediaC = new MediaController(this); //SET MEDIA CONTROLLER
    }


    public void videoPlay(View v)
    {
       getWindow().setFormat(PixelFormat.UNKNOWN);
        String videoPath = "android.resource://com.example.csumb_androidapp/"+R.raw.movie;
  //      String videoURL = "https://file-examples-com.github.io/uploads/2017/04/file_example_MP4_480_1_5MG.mp4";
        Uri uri = Uri.parse(videoPath);
        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();
        mVideoView.start();
        mVideoView.setMediaController(mediaC);
        mediaC.setAnchorView(mVideoView); //MEDIA CONTROLLER FOR PLAYBACK CONTROL
    }

}