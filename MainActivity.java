package com.example.video_20300830;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private Spinner videoSpinner;
    private ArrayList<String> videoPaths;
    private int currentVideoIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        videoSpinner = findViewById(R.id.videoSpinner);

        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnPause = findViewById(R.id.btnPause);
        Button btnStop = findViewById(R.id.btnStop);
        Button btnForward = findViewById(R.id.btnForward);
        Button btnRewind = findViewById(R.id.btnRewind);
        Button btnNext = findViewById(R.id.btnNext);
        Button btnPrevious = findViewById(R.id.btnPrevious);

        // Añadir videos por defecto
        videoPaths = new ArrayList<>();
        videoPaths.add("android.resource://" + getPackageName() + "/" + R.raw.video1);
        videoPaths.add("android.resource://" + getPackageName() + "/" + R.raw.video2);
        videoPaths.add("android.resource://" + getPackageName() + "/" + R.raw.video3);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, videoPaths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        videoSpinner.setAdapter(adapter);

        videoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentVideoIndex = position;
                playVideo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.stopPlayback();
                playVideo();
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = videoView.getCurrentPosition();
                int duration = videoView.getDuration();
                if (currentPosition + 10000 < duration) {
                    videoView.seekTo(currentPosition + 10000);
                } else {
                    videoView.seekTo(duration);
                }
            }
        });

        btnRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = videoView.getCurrentPosition();
                if (currentPosition - 10000 > 0) {
                    videoView.seekTo(currentPosition - 10000);
                } else {
                    videoView.seekTo(0);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentVideoIndex = (currentVideoIndex + 1) % videoPaths.size();
                playVideo();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentVideoIndex = (currentVideoIndex - 1 + videoPaths.size()) % videoPaths.size();
                playVideo();
            }
        });

        playVideo();
    }

    private void playVideo() {
        Uri uri = Uri.parse(videoPaths.get(currentVideoIndex));
        videoView.setVideoURI(uri);
        videoView.start();
    }
}
