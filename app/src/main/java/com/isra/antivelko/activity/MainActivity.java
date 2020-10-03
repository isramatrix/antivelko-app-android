package com.isra.antivelko.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.widget.TextView;

import com.isra.antivelko.logic.NoiseThread;
import com.isra.antivelko.R;
import com.isra.antivelko.logic.OnNoiseFetch;
import com.isra.antivelko.logic.SsshPlayer;
import com.isra.antivelko.view.FaceView;
import com.isra.antivelko.view.IntensityTextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private final String[] permissions = { Manifest.permission.RECORD_AUDIO};

    private static String fileName;

    private MediaRecorder recorder;

    private SsshPlayer player;

    private Thread noiseThread;

    private boolean permissionToRecordAccepted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Initializes the layout.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gets the file in which audio will be stored.
        fileName = getExternalCacheDir().getAbsolutePath() + "/temp.3gp";

        // Requests for the microphone permissions
        requestPermissions(permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        // If no permission granted, stops the execution
        if (!checkIfAllPermissionsGranted()) return;

        // Starts the microphone recording.
        startRecording();

        // Starts the ssshPlayer.
        player = new SsshPlayer(getApplicationContext());

        // Gets the text view in which noise intensity will be displayed.
        final IntensityTextView dbText = findViewById(R.id.db_text);
        final FaceView face = findViewById(R.id.face);

        // Prints the value of the noise on intensity text view.
        setOnNoiseFetch(new OnNoiseFetch() {
            @Override
            public void fetch(double intensity)
            {
                dbText.setIntensityText(intensity);
                if (intensity > 90)
                {
                    player.hit();
                    face.hit();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
    }

    private boolean checkIfAllPermissionsGranted()
    {
        return true;
    }

    private void startRecording()
    {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(fileName);

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recorder.start();
    }

    private void setOnNoiseFetch(final OnNoiseFetch onNoiseFetch)
    {
        // It only can be one listener for the noise fetcher.
        if (noiseThread != null) return;

        // Creates a thread to fetch the VELKO noise intensity.
        noiseThread = new NoiseThread(onNoiseFetch, recorder);

        // Initializes the thread.
        noiseThread.start();
    }
}
