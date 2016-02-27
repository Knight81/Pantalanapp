package charlie.pantalanapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {
    private static final String TAG = MapActivity.class.getName();
    private static final float MAP_CAMERA_ZOOM = 10;
    private static final float MAP_CAMERA_ZOOM_PARKING = 16;
    private GoogleMap mMap;
    private LocationProvider mLocationManager;
    private ArrayList<Pantalan> mAvailablePantalanes;
    private boolean isParking = false;
    UDOOConnection udooConnection = new UDOOConnection();

    UDOOConnection.Listener udooConnectionListener = new UDOOConnection.Listener() {
        @Override
        public void gotPositonUpdate(List<Integer> measures) {
            Log.i("~~~", "Got measures " + measures.get(0) + " " + measures.get(1) + " " + measures.get(2));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        udooConnection.addListener(udooConnectionListener);
    }

    @Override
    protected void onPause() {
        udooConnection.removeListener(udooConnectionListener);
        super.onPause();
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
    }

    @Override
    public void onLocationReceived(Location location) {
        if (mMap != null) {
            CameraUpdate cu;
            if(!isParking)
                cu = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), MAP_CAMERA_ZOOM);
            else
                cu = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), MAP_CAMERA_ZOOM_PARKING);
            mMap.animateCamera(cu);
        }
    }
}
