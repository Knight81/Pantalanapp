package charlie.pantalanapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {
    private static final String TAG = MapActivity.class.getName();
    private static final float MAP_CAMERA_ZOOM = 14;
    private static final float MAP_CAMERA_ZOOM_PARKING = 17;
    private GoogleMap mMap;
    private LocationProvider mLocationManager;
    private ArrayList<Pantalan> mAvailablePantalanes = new ArrayList<>();
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
        loadPantalanes();
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
    //TODO Tweak this bitch
    public void loadPantalanes(){
        Pantalan pantalanVigo = new Pantalan(42.241998, -8.722648,"Pantalan Vigo",null);
        mAvailablePantalanes.add(pantalanVigo);
        showPantalanesOnMap();
    }

    private void showPantalanesOnMap() {
        for (Pantalan pantalan:
             mAvailablePantalanes) {
            final IconGenerator iconGenerator = new IconGenerator(this);
            iconGenerator.setBackground(getResources().getDrawable(R.drawable.markership));
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(pantalan.getLatLng())
                    .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(iconGenerator.makeIcon(),50,82,false)));
            mMap.addMarker(markerOptions);
        }
    }
}
