package Util.donny;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.donny.commonutils.SearchBluetoothDevice;

import java.io.IOException;

/**
 * Created by donny on 5/25/18.
 */

public class SensorUnit {

    private final int nMaxBreakRetry = 10;

    private int id;
    private boolean bRunning = false;
    private boolean bStopping = false;

    private BluetoothDevice bleDevice = null;
    private BluetoothSocket bleSocket = null;
    private SensorDataProcessor processor = new SensorDataProcessor();

    public void init(BluetoothDevice device, int id) {
        this.bleDevice = device;
        this.id = id;
    }

    public boolean start() {
        if (bRunning) return false;
        bStopping = false;
        new Thread(processThread).start();
        return true;
    }

    public boolean stop() {
        if (!bRunning) return false;
        bStopping = true;
        return true;
    }

    public boolean ready() {
        return bRunning && (this.bleSocket != null) && (this.bleSocket.isConnected());
    }

    public BluetoothDevice getDevice() { return this.bleDevice; }
    public int getId() { return this.id; }

    public SensorDataProcessor.DeviceData getData() {
        return processor.getData();
    }

    private OnSensorUnitStateListener sensorUnitStateListener;

    public void setListener(OnSensorUnitStateListener listener) {
        this.sensorUnitStateListener = listener;
    }

    public interface OnSensorUnitStateListener {
        void onConnected(int id);
        void onDisconnected(int id);
        void onConnectFail(int id);
        void onConnecting(int id);
        void onAbort(int id);
    }

    private Runnable processThread = new Runnable() {
        @Override
        public void run() {
            bRunning = true;

            try {
                if (sensorUnitStateListener != null) sensorUnitStateListener.onConnecting(id);
                bleSocket = SearchBluetoothDevice.getSocketFromDevice(bleDevice);
                if (!bleSocket.isConnected()) bleSocket.connect();
            } catch (IOException e) {
                bleSocket = null;
                e.printStackTrace();
                if (sensorUnitStateListener != null) sensorUnitStateListener.onConnectFail(id);
                return;
            }
            processor.setSocket(bleSocket);

            if (sensorUnitStateListener != null) sensorUnitStateListener.onConnected(id);

            int nBreakCount = 0;
            while (!bStopping) {
                try {
                    processor.updateData();
                    nBreakCount = 0;
                } catch (IOException e) {
                    e.printStackTrace();
                    ++nBreakCount;
                    if (!bleSocket.isConnected() || nBreakCount >= nMaxBreakRetry) {
                        if (sensorUnitStateListener != null) sensorUnitStateListener.onAbort(id);
                        break; // while
                    }
                }
            }

            try {
                bleSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bleSocket = null;

            if (sensorUnitStateListener != null) sensorUnitStateListener.onDisconnected(id);

            bRunning = false;
        }
    };

}
