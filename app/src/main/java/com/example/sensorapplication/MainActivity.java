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
   private TextView lightTV,proximityTV,accelerometerTV;
   private SensorManager sm;
   private SensorEventListener listener;
   private Sensor light,proximity,accelerometer;
   private float vibrateThreshold = 0;
   private double x,y,z;

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

                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if (event.values[0] == 0) {
                        proximityTV.setText("Object Detected");
                    } else {
                        proximityTV.setText("Not Detected");
                    }
                }

                if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
                    x=event.values[0];
                    y=event.values[1];
                    z=event.values[2];
                    //vibrateThreshold = accelerometer.getMaximumRange()/2;
                    //accelerometerTV.setText(x+" "+y+" "+z);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

                Toast.makeText(MainActivity.this, "accuracy changed!", Toast.LENGTH_SHORT).show();

            }
        };
        sm.registerListener(listener, light, SensorManager.SENSOR_DELAY_FASTEST);
        sm.registerListener(listener,proximity, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(listener,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(listener, light);
        sm.unregisterListener(listener,proximity);
        sm.unregisterListener(listener,accelerometer);
    }

    private void init() {
        lightTV=findViewById(R.id.lightSensorTV);
        proximityTV=findViewById(R.id.proximitySensorTV);
        accelerometerTV=findViewById(R.id.speedSensorTV);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximity=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
}