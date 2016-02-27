package charlie.pantalanapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raziel on 27/02/16.
 */
public class UDOOConnection {

    interface Listener {
        void gotPositonUpdate(int[] measures);
    }

    private List<Listener> mListeners;

    public UDOOConnection() {
        mListeners = new ArrayList<Listener>();
    }

    void addListener(Listener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    void removeListener(Listener listener) {
        if (mListeners.contains(listener)) {
            mListeners.remove(listener);
        }
    }
}
