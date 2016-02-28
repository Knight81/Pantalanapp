package charlie.pantalanapp;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Aitor A. on 28/02/16.
 */
public class PantalanApp extends Application {
    public ArrayList<Pantalan> getAvailablePantalanes() {
        return mAvailablePantalanes;
    }

    public ArrayList<Pantalan> mAvailablePantalanes = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        loadPantalanes();
    }
    public void loadPantalanes() {
        Pantalan pantalanVigo = new Pantalan(42.241998, -8.722648, "Pantalan Vigo", null);
        Pantalan pantalanCangas = new Pantalan(42.259534, -8.783657,"Pantalan Cangas",null);
        mAvailablePantalanes.add(pantalanVigo);
        mAvailablePantalanes.add(pantalanCangas);
    }
}
