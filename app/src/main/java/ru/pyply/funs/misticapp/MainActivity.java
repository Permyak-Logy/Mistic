package ru.pyply.funs.misticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    MediaPlayer mediaPlayer;
    float[] light = new float[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mistic_sound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        loadSensorData(event);
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            System.out.println(light[0]);
            try {
                if (!mediaPlayer.isPlaying() && light[0] < 1) {
                    System.out.println();
                    mediaPlayer.start();
                } else if (mediaPlayer.isPlaying() && light[0] >= 1) {
                    mediaPlayer.pause();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void loadSensorData(SensorEvent event) {
        final int type = event.sensor.getType(); //определяем тип датчика
        if (type == Sensor.TYPE_LIGHT) { //если световой датчик
            light = event.values.clone();
        }
    }
}