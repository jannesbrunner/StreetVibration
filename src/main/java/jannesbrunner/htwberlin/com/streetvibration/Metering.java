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

public class Metering extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metering);
    }
}
