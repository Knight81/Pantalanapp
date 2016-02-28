package charlie.pantalanapp;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    UDOOConnection mUdooConnection = new UDOOConnection();
    UDOOConnection.Listener mUdooConnectionListener = new UDOOConnection.Listener() {
        @Override
        public void gotPositonUpdate(List<Float> measures) {
             updateSensorGUI(measures);
        }
    };
    private ImageView barquito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_parking);
        final RelativeLayout view = (RelativeLayout)findViewById(R.id.RelativeLayParking);
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @SuppressLint("NewApi")
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        mUdooConnection.addListener(mUdooConnectionListener);
                    }
                });
        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getWindowManager().getDefaultDisplay().getHeight();
        barquito = (ImageView) findViewById(R.id.boatImage);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUdooConnection.removeListener(mUdooConnectionListener);
    }

    public void updateSensorGUI(final List<Float> measures){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                TextView leftSensor = (TextView) findViewById(R.id.LeftSensor);
                TextView rightSensor = (TextView) findViewById(R.id.RightSensor);
                leftSensor.setText(Float.toString(measures.get(0)));
                rightSensor.setText(Float.toString(measures.get(2)));
                setBoatMargins(measures.get(0), measures.get(2));
            }
        });

    }
    public void setBoatMargins(float left,float top){
        int marginleft = (int) ((int) 2.17*left+350);
        int margintop = (int) (top*3+ 80);
        barquito.setX(marginleft);
        barquito.setY(margintop);
        barquito.invalidate();
    }
}