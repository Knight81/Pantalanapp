package charlie.pantalanapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;

/**
 * Created by raziel on 27/02/16.
 */
public class UDOOConnection {

    public interface Listener {
        void gotPositonUpdate(List<Integer> measures);
    }

    public interface UDOOService {
        @GET("/")
        void getMeasures(Callback<List<Integer>> callback);
    }

    private RestAdapter mRetrofit;
    private UDOOService mUdooService;
    private List<Listener> mListeners;
    private Timer checkStatus;
    private CheckTimerTask checkTimerTask;

    private Callback<List<Integer>> mGetMeasuresCallback = new Callback<List<Integer>>() {

        @Override
        public void success(List<Integer> measures, Response response) {
            Log.e("~~", "aw yiss "+response);
            notifyListeners(measures);
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("~~", "fuck "+error);
        }
    };

    public UDOOConnection() {
        mListeners = new ArrayList<Listener>();
        mRetrofit = new RestAdapter.Builder()
                .setEndpoint("http://172.16.3.120:1808")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mUdooService = mRetrofit.create(UDOOService.class);
    }

    private void start() {
        try {
            checkStatus = new Timer();
            checkTimerTask = new CheckTimerTask();
            checkStatus.schedule(checkTimerTask, 0, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        checkStatus.cancel();
        checkTimerTask.cancel();
        checkStatus.purge();
    }

    public void addListener(Listener listener) {
        if (!mListeners.contains(listener)) {
            if (mListeners.isEmpty()) {
                start();
            }
            mListeners.add(listener);
        }
    }

    public void removeListener(Listener listener) {
        if (mListeners.contains(listener)) {
            mListeners.remove(listener);
            if (mListeners.isEmpty()) {
                stop();
            }
        }
    }

    private void notifyListeners(List<Integer> measures) {
        for (Listener listener: mListeners) {
            listener.gotPositonUpdate(measures);
        }
    }

    private class CheckTimerTask extends TimerTask {
        int m = 100;
        @Override
        public void run() {
            //mUdooService.getMeasures(mGetMeasuresCallback);
            m += (int)(Math.random() * 5) - 10;
            List<Integer> measures = new ArrayList<>();
            measures.add(m);
            measures.add(m);
            measures.add(m);
            notifyListeners(measures);
        }
    }

}
