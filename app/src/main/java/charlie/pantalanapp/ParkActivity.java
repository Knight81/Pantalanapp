package charlie.pantalanapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;

public class ParkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);
        TextView pantalanTexview = (TextView) findViewById(R.id.textViewPantalan);
        pantalanTexview.setText(getIntent().getStringExtra("Pantalan"));
    }
    public void sendDockRequest(View v){
    }
}
