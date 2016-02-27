package charlie.pantalanapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by raziel on 27/02/16.
 */
public class UDOOConnection {

    interface Listener {
        void gotPositonUpdate(int[] measures);
    }

    private List<Listener> mListeners;
    private Timer checkStatus;
    private CheckTimerTask checkTimerTask;

    public UDOOConnection() {
        mListeners = new ArrayList<Listener>();
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

    private void notifyListeners(int[] measures) {
        for (Listener listener: mListeners) {
            listener.gotPositonUpdate(measures);
        }
    }

    private class CheckTimerTask extends TimerTask {
        int m = 100;
        @Override
        public void run() {
            m += (int)(Math.random() * 10) - 5;
            int measures[] = { m, m, m };
            notifyListeners(measures);
        }
    }

}
