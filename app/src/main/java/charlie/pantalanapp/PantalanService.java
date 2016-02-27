package charlie.pantalanapp;

/**
 * Created by Aitor A. on 27/02/16.
 */
public class PantalanService {
    private String serviceName;
    private String servicePrice;

    public PantalanService(String servicePrice, String serviceName) {

        this.servicePrice = servicePrice;
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
