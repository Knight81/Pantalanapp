package charlie.pantalanapp;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
        setContentView(R.layout.activity_parking);
        final RelativeLayout view = (RelativeLayout)findViewById(R.id.RelativeLayParking);
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @SuppressLint("NewApi")
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        UDOOConnection udooConnection = new UDOOConnection();
                        UDOOConnection.Listener udooConnectionListener = new UDOOConnection.Listener() {
                            @Override
                            public void gotPositonUpdate(List<Float> measures) {
                                Log.i("~~~", "Got measures " + measures.get(0) + " " + measures.get(1) + " " + measures.get(2));
                                updateSensorGUI(measures);

                            }
                        };
                        udooConnection.addListener(udooConnectionListener);
                    }
                });
        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getWindowManager().getDefaultDisplay().getHeight();
        final ImageView barquito = (ImageView) findViewById(R.id.boatImage);

    }

    public void updateSensorGUI(final List<Float> measures){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                TextView leftSensor = (TextView) findViewById(R.id.LeftSensor);
                TextView rightSensor = (TextView) findViewById(R.id.RightSensor);
                TextView frontSensor = (TextView) findViewById(R.id.FrontSensor);
                leftSensor.setText(Float.toString(measures.get(0)));
                rightSensor.setText(Float.toString(measures.get(1)));
                frontSensor.setText(Float.toString(measures.get(2)));

            }
        });

    }
}