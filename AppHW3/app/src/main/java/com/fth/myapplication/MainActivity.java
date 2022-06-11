package com.fth.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Gerekli tanımlamalar
    MediaPlayer player;
    private SensorManager sensorManager;
    private TextView lightLevel;
    private TextView posLevel;
    private AudioManager myAudioManager;



    private final SensorEventListener listener = new SensorEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onSensorChanged(SensorEvent event) {
            // The value of the first subscript in the values array is the current light intensity
            float value = event.values[0];
            lightLevel.setText("Current light level is " + value + " lx");


            if (value < 20) {
                posLevel.setText("Akıllı telefon cepte");

            } else {
                posLevel.setText("Akıllı telefon masada");
            }




        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightLevel = findViewById(R.id.light_level);
        posLevel = findViewById(R.id.posLevel);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);


        final Button button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);


            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.isFinishing()) {
            player.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }


}