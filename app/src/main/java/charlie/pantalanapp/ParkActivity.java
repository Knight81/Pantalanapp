package charlie.pantalanapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.internal.LinkedHashTreeMap;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public class ParkActivity extends AppCompatActivity {
    private Map<String,String> parameters;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);
        TextView pantalanTexview = (TextView) findViewById(R.id.textViewPantalan);
        checkBox = (CheckBox) findViewById(R.id.checkBoxWithSailor);
        pantalanTexview.setText(getIntent().getStringExtra("Pantalan"));
    }
    public void sendDockRequest(View v){
        parameters = new LinkedHashTreeMap<>();
        parameters.put("boatName", "S.S Barco");
        parameters.put("boatLicensePlate", "DEADBEEF");
        parameters.put("ownerName", "desc");
        parameters.put("withSailor", Boolean.toString(checkBox.isPressed()));
        parameters.put("page", "1");
        RestService.getInstance().requestDock(parameters);
        finish();
    }
}
