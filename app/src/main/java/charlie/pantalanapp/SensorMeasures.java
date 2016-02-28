package charlie.pantalanapp;

/**
 * Created by christian on 28/02/16.
 */
public class SensorMeasures {
    private int sensorLeft;
    private int sensorRight;
    private int sensorFront;
    private ParkingActivity.SensorUpdateListener listener;

    public int getSensorLeft() {
        return sensorLeft;
    }

    public void setSensorLeft(int sensorLeft) {
        this.sensorLeft = sensorLeft;
    }

    public int getSensorRight() {
        return sensorRight;
    }

    public void setSensorRight(int sensorRight) {
        this.sensorRight = sensorRight;
    }

    public int getSensorFront() {
        return sensorFront;
    }

    public void setSensorFront(int sensorFront) {
        this.sensorFront = sensorFront;
    }
    public void setCustomObjectListener(ParkingActivity.SensorUpdateListener listener) {
        this.setListener(listener);
    }

    public ParkingActivity.SensorUpdateListener getListener() {
        return listener;
    }

    public void setListener(ParkingActivity.SensorUpdateListener listener) {
        this.listener = listener;
    }
}
