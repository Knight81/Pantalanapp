package charlie.pantalanapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

/**
 * Created by christian on 27/02/16.
 */
public class ParkingActivity extends AppCompatActivity {
    int windowwidth;
    int windowheight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getWindowManager().getDefaultDisplay().getHeight();
        final ImageView barquito = (ImageView) findViewById(R.id.boatImage);
//        barquito.
    }

}
