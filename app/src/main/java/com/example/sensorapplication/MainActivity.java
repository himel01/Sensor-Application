package com.example.sensorapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   private TextView lightTV;
   private SensorManager sm;
   private SensorEventListener listener;
   private Sensor light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                lightTV.setText(String.valueOf(event.values[0]));
                int grayShade = (int) event.values[0];
                if (grayShade > 255) grayShade = 255;
                lightTV.setTextColor(Color.rgb(255 - grayShade, 255 - grayShade, 255 - grayShade));
                lightTV.setBackgroundColor(Color.rgb(grayShade, grayShade, grayShade));

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

                Toast.makeText(MainActivity.this, "accuracy changed!", Toast.LENGTH_SHORT).show();

            }
        };
        sm.registerListener(listener, light, SensorManager.SENSOR_DELAY_FASTEST);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(listener, light);
    }

    private void init() {
        lightTV=findViewById(R.id.lightSensorTV);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
    }
}