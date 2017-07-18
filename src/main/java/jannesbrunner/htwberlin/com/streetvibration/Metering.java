package jannesbrunner.htwberlin.com.streetvibration;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Metering extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor gyro;
    private TextView output;
    private TextView log;
    private TextView truckVal;
    private TextView carVal;
    private TextView carCounterTV;
    private TextView truckCounterTV;
    private ArrayList<DataSet> storage;
    private int truckCounter;
    private int carCounter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metering);
        log = (TextView) findViewById(R.id.log);
        output = (TextView) findViewById(R.id.sensorOutput);
        truckVal = (TextView) findViewById(R.id.truckTrigVal);
        carVal = (TextView) findViewById(R.id.carTrigVal);
        truckCounterTV = (TextView) findViewById(R.id.trucksTotal);
        carCounterTV = (TextView) findViewById(R.id.carsTotal);

        truckCounter = 0;
        carCounter = 0;

        // inital trigger values
        truckVal.setText("20.0");
        carVal.setText("10.0");


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
        eruption = Math.floor(eruption * 1000)/1000;
        output.setText(Double.toString(eruption));

        // default values
        double truckTrigger = 20.0;
        double carTrigger = 10.0;

        // set user values
        if(!truckVal.getText().toString().isEmpty()) {
            truckTrigger = Double.parseDouble(truckVal.getText().toString());
        }
        if(!carVal.getText().toString().isEmpty()) {
            carTrigger = Double.parseDouble(carVal.getText().toString());
        }

        if(eruption >= truckTrigger && truckTrigger > carTrigger) {
            Timestamp tstamp = new Timestamp(new Date().getTime());
            String tstform = new SimpleDateFormat("MM.dd 'at' HH:mm:ss").format(tstamp);
            log.setText(log.getText() + "\n" +
            "LKW E:" + eruption + " @ " + tstform);
            truckCounter++;
        }

        if(eruption >= carTrigger && eruption < truckTrigger) {
            Timestamp tstamp = new Timestamp(new Date().getTime());
            String tstform = new SimpleDateFormat("MM.dd 'at' HH:mm:ss").format(tstamp);
            log.setText(log.getText() + "\n" +
                    "Auto E:" + eruption + " @ " + tstform);
            carCounter++;
        }

        truckCounterTV.setText(Integer.toString(truckCounter));
        carCounterTV.setText(Integer.toString(carCounter));

        //log.setText(log.getText() + "\n" + test);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }




}
