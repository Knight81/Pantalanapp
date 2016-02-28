package charlie.pantalanapp;

import android.os.AsyncTask;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Aitor A. on 28/02/16.
 */
public class RestService {
    private static RestService _instance;
    private RestAdapter mRetrofit;
    private RestAPI mRestApi;
    private Callback<String> callback = new Callback<String>() {
        @Override
        public void success(String s, Response response) {

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };
    public RestService(){
        mRetrofit = new RestAdapter.Builder()
                .setEndpoint("http://172.16.3.127:3000")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        mRestApi = mRetrofit.create(RestAPI.class);
    }
    public static RestService getInstance(){
        if(_instance == null)
            _instance = new RestService();
        return _instance;
    }
    public void requestDock(Map<String,String> parameters){
        mRestApi.registerDock(parameters,callback);
    }
    public void updateBoat(Map<String,String> parameters){
        mRestApi.updateBoat(parameters,callback);
    }
}
