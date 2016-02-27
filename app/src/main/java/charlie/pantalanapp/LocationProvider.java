package charlie.pantalanapp;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;


public class LocationProvider implements
        GoogleApiClient.ConnectionCallbacks, OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS            = 3000;


    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS    =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    public static final int LOCATION_UPDATES = 1;

    private final Context mContext;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;

    public LocationProvider(Context context) {

        mContext = context;
        buildGoogleApiClient();
        createLocationRequest();
        boolean locationEnabled = isTheLocationEnabled();

        if (locationEnabled)
            mGoogleApiClient.connect();
    }

    private void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void createLocationRequest () {
        Log.d("Location","Created location request");
        mLocationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
                .setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
    }

    public boolean isTheLocationEnabled() {

        LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        boolean gps_enabled     = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!gps_enabled && !network_enabled)
            return false;

        return true;
    }


    @Override
    public void onConnected(Bundle bundle) {

        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);

        }

        startLocationUpdates();
    }


    /**
     * Requests location updates from the FusedLocationApi.
     */
    private void startLocationUpdates() {

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {

//        LocationServices.FusedLocationApi.removeLocationUpdates(
        //mGoogleApiClient, this);
        if (location != null) {
            Log.d("Location changed","Location changed");
        }

    }




    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e("[ERROR]", "GetLastKnownLocationUsecaseController, onConnectionFailed (116)- " +
                " ConnectionResult: "+connectionResult.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.e("[ERROR]", "GetLastKnownLocationUsecaseController, onConnectionSuspended (125)- " +
                "ErrorCode: "+i);
    }

    public class LocationWrapper {

        private boolean isTheCurrentLocation;
        private Location location;

        LocationWrapper( Location location, boolean isTheLastLocation) {

            this.isTheCurrentLocation = isTheLastLocation;
            this.location = location;
        }

        public boolean isTheCurrentLocation() {

            return isTheCurrentLocation;
        }

        public Location getLocation() {

            return location;
        }
    }
}
