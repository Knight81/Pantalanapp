package charlie.pantalanapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian on 27/02/16.
 */
public class ParkingActivity extends AppCompatActivity {
    int windowwidth;
    int windowheight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SensorUpdateListener listener;
        setContentView(R.layout.activity_parking);
        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getWindowManager().getDefaultDisplay().getHeight();
        final ImageView barquito = (ImageView) findViewById(R.id.boatImage);
        final TextView leftSensor = (TextView) findViewById(R.id.LeftSensor);
        final TextView rightSensor = (TextView) findViewById(R.id.RightSensor);
        final TextView frontSensor = (TextView) findViewById(R.id.FrontSensor);
        SensorMeasures measures = new SensorMeasures();
        measures.setCustomObjectListener(new SensorUpdateListener() {
            @Override
            public void onDataLoaded(List<Integer> sensorMeasures) {
                leftSensor.setText(sensorMeasures.get(0));
                rightSensor.setText(sensorMeasures.get(1));
                frontSensor.setText(sensorMeasures.get(2));
            }
        });
    }

    public interface SensorUpdateListener {
        public void onDataLoaded(List<Integer> sensorMeasures);
    }
}
