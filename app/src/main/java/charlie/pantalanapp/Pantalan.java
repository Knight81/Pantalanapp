package charlie.pantalanapp;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by Aitor A. on 27/02/16.
 */
public class Pantalan {
    private long latitude;
    private long longitude;
    private String name;
    private ArrayList<PantalanService> pantalanServices;

    public Pantalan(long longitude, String name, ArrayList<PantalanService> pantalanServices, long latitude) {
        this.longitude = longitude;
        this.name = name;
        this.pantalanServices = pantalanServices;
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public ArrayList<PantalanService> getPantalanServices() {
        return pantalanServices;
    }

    public String getName() {
        return name;
    }
    public void addPantalanService(String serviceName,String price){
        PantalanService pantalanService = new PantalanService(serviceName,price);
        pantalanServices.add(pantalanService);
    }
}
