package charlie.pantalanapp;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Aitor A. on 27/02/16.
 */
public class Pantalan {
    private double latitude;
    private double longitude;
    private String name;
    private ArrayList<PantalanService> pantalanServices;

    public Pantalan(double latitude, double longitude, String name, ArrayList<PantalanService> pantalanServices) {
        this.longitude = longitude;
        this.name = name;
        this.pantalanServices = pantalanServices;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public ArrayList<PantalanService> getPantalanServices() {
        return pantalanServices;
    }

    public String getName() {
        return name;
    }
    public LatLng getLatLng(){
        return new LatLng(latitude,longitude);
    }
    public void addPantalanService(String serviceName,String price){
        PantalanService pantalanService = new PantalanService(serviceName,price);
        pantalanServices.add(pantalanService);
    }
}
