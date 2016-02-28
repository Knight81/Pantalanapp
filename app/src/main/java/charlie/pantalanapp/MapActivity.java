package charlie.pantalanapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.internal.LinkedHashTreeMap;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapActivity extends Activity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {
    private static final String TAG = MapActivity.class.getName();
    private static final float MAP_CAMERA_ZOOM = 12;
    private static final float MAP_CAMERA_ZOOM_PARKING = 17;
    private GoogleMap mMap;
    private LocationProvider mLocationManager;
    private PantalanApp application;
    private boolean isParking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        application = (PantalanApp) getApplication();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mLocationManager = new LocationProvider(this);
        mLocationManager.addLocationListener(this);
        mMap.setOnMarkerClickListener(this);
        showPantalanesOnMap();
    }

    @Override
    public void onLocationReceived(Location location) {
        if (mMap != null) {
            CameraUpdate cu;
            if (!isParking)
                cu = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), MAP_CAMERA_ZOOM);
            else
                cu = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), MAP_CAMERA_ZOOM_PARKING);
            mMap.animateCamera(cu);
        }
        sendLocationtoServer(location);
    }

    private void showPantalanesOnMap() {
        for (Pantalan pantalan :
                application.getAvailablePantalanes()) {
            final IconGenerator iconGenerator = new IconGenerator(this);
            iconGenerator.setBackground(getResources().getDrawable(R.drawable.markership));
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(pantalan.getLatLng())
                    .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(iconGenerator.makeIcon(), 50, 82, false)))
                    .title(pantalan.getName());
            mMap.addMarker(markerOptions);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent i = new Intent(this,ParkActivity.class);
        i.putExtra("Pantalan",marker.getTitle());
        startActivity(i);
        return false;
    }
    private  void sendLocationtoServer(Location location){
        Map<String,String> parameters = new LinkedHashTreeMap<>();
        parameters.put("licensePlate","DEADBEEF");
        parameters.put("lat",Double.toString(location.getLatitude()));
        parameters.put("lng", Double.toString(location.getLongitude()));
        RestService.getInstance().updateBoat(parameters);
    }
    public void beginDocking(View v){
        Intent intent = new Intent(this,ParkingActivity.class);
        startActivity(intent);
    }
}
