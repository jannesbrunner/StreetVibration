package jannesbrunner.htwberlin.com.streetvibration;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class Metering extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor gyro;
    private TextView output;
    private TextView log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metering);
        log = (TextView) findViewById(R.id.textView);
        output = (TextView) findViewById(R.id.sensorOutput);

        mSensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        mSensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);

        List<Sensor> list = mSensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);

        String supportedSensors = "Searching for supported sensors... \n";

        if(!list.isEmpty()) {
            supportedSensors += "Found: \n";
        for(Sensor sensor: list){
            supportedSensors += sensor.getName() + "\n";
        }

        gyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);





        } else {

            supportedSensors += " Your device seems to have no supported gyroscope sensors! \n";

        }


        log.setText(supportedSensors);



    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;
        String txtValues = "";
        double x_2 = Math.pow((double) values[0], 2);
        double y_2 = Math.pow((double) values[1], 2);
        double z_2 = Math.pow((double) values[2], 2);
        double eruption = Math.sqrt((x_2 + y_2 + z_2));
        eruption = eruption * 10;
        output.setText("Eruption: " + eruption);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
