package Util.donny;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by donny on 5/25/18.
 */

public class SensorDataRecorder {

    private boolean bRunning = false;
    private boolean bStopping = false;

    private SensorUnit sensorUnits[];
    private String sRecordFilename = "";
    private int nRecordRateMs = 100;

    public boolean init(SensorUnit[] sensorUnits, String recordFilename, int recordRateMs) {
        this.sensorUnits = sensorUnits;
        this.sRecordFilename = recordFilename;
        this.nRecordRateMs = recordRateMs;
        return true;
    }

    public boolean start() {
        if (bRunning) return false;
        bStopping = false;
        new Thread(recorderRunnable).start();
        return true;
    }

    public boolean stop() {
        if (!bRunning) return false;
        bStopping = true;
        return true;
    }

    public boolean isRunning() {
        return bRunning;
    }

    private OnRecordStateListener recordStateListener;

    public void setListener(OnRecordStateListener listener) {
        this.recordStateListener = listener;
    }

    public interface OnRecordStateListener {
        void onRecording();
        void onRecordEnded();
    }

    private Runnable recorderRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d("RecorderThread", "Running...");
            bRunning = true;
            if (recordStateListener != null) recordStateListener.onRecording();

            try {
                FileOutputStream fileout = new FileOutputStream(sRecordFilename);
                DataOutputStream dataout = new DataOutputStream(fileout);

                FileOutputStream qfileout = new FileOutputStream(sRecordFilename + "q");
                DataOutputStream qdataout = new DataOutputStream(qfileout);

                while (!bStopping) {
                    long ts = System.currentTimeMillis();

                    // Synchronize
                    SensorDataProcessor.DeviceData[] devicesData = new SensorDataProcessor.DeviceData[sensorUnits.length];
                    for (int k = 0; k < sensorUnits.length; ++k) {
                        devicesData[k] = sensorUnits[k].getData();
                    }

//                    for (int k = 0; k < sensorUnits.length; ++k) {
                    for (int k = 0; k < 2; ++k) {
                        SensorDataProcessor.DeviceData dvData = devicesData[k];
//                        String strOut = String.format("%d %d %.3f %.3f %.3f %.3f %.3f %.3f \r\n",
//                                k, // device id
//                                ts, // timestamp
//                                dvData.a[0], // accelerator
//                                dvData.a[1],
//                                dvData.a[2],
//                                dvData.ang[0], // angle
//                                dvData.ang[1],
//                                dvData.ang[2]
//                        );
//                        dataout.writeBytes(strOut);

                        String strOut = String.format("%.3f %.3f %.3f ",
                                dvData.ang[0], // angle
                                dvData.ang[1],
                                dvData.ang[2]
                        );
                        dataout.writeBytes(strOut);

                        String qstrOut = String.format("%.3f %.3f %.3f %.3f ",
                                dvData.q[0], // accelerator
                                dvData.q[1],
                                dvData.q[2],
                                dvData.q[3]
                        );
                        qdataout.writeBytes(qstrOut);
                    }
                    dataout.writeBytes("\r\n");
                    qdataout.writeBytes("\r\n");

                    Thread.sleep(nRecordRateMs);
                }

                dataout.close();
                fileout.close();

                qdataout.close();
                qfileout.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            if (recordStateListener != null) recordStateListener.onRecordEnded();
            bRunning = false;
            Log.d("RecorderThread", "Stopped.");
        }
    };

}
