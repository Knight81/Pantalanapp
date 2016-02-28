package charlie.pantalanapp;

import java.util.Map;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Aitor A. on 28/02/16.
 */
public interface RestAPI {
        @GET("/restful/registerDock")
        void registerDock(@QueryMap Map<String, String> params, Callback<String> callback);

        @GET("/restful/updateBoat")
        void updateBoat(@QueryMap Map<String, String> params, Callback<String> callback);
}
