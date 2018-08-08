package Util.donny;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.donny.fitness.CommonFunc.getHexColorRGB;
import static com.donny.fitness.CommonFunc.setStateButton;

public class MainFragment extends Fragment {

    static final String sData_SensorType = "Sensor Type";
    static final String sData_SensorName = "Sensor Name";
    static final String sData_SensorDevice = "Sensor Device";

    private final int REQUESTCODE_ADDDEVACTIVITY = 1;

    private final String sStateConnected = App.getContext().getString(R.string.state_connected);
    private final String sStateDisconnected = App.getContext().getString(R.string.state_disconnected);
    private final String sStateConnecting = App.getContext().getString(R.string.state_connecting);
    private final String sStateDataFlow = App.getContext().getString(R.string.state_recording);

    private final String sStateColorConnected = getHexColorRGB(App.getContext(), R.color.colorConnected);
    private final String sStateColorDisconnected = getHexColorRGB(App.getContext(), R.color.colorDisconnected);
    private final String sStateColorConnecting = getHexColorRGB(App.getContext(), R.color.colorConnecting);
    private final String sStateColorDataFlow = getHexColorRGB(App.getContext(), R.color.colorDataFlow);

    private final int nSensorCount = 3;

    private final String sSensorsName[] = {
            App.getContext().getString(R.string.btn_arm),
            App.getContext().getString(R.string.btn_leg),
            App.getContext().getString(R.string.btn_chest),
    };
    private class SensorInd {
        public static final int SENSOR_UNKNOWN = -1, SENSOR_ARM = 0, SENSOR_LEG = 1, SENSOR_CHEST = 2;
    }

    private Button btnArm, btnLeg, btnChest;
    private RelativeLayout btnStart;
    private TextView tvStart;
    private enum Status {STATUS_RECORDING, STATUS_READY, STATUS_ENDED, STATUS_PENDING, STATUS_PREPARE };
    private Status recordStatus = Status.STATUS_PREPARE;

    private SensorUnit sensorUnits[] = new SensorUnit[nSensorCount];
    private SensorUnit.OnSensorUnitStateListener sensorUnitStateListener = new SensorUnit.OnSensorUnitStateListener() {
        @Override
        public void onConnected(final int sensorType) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int sensorInd = getSensorInd(sensorType);
                    setStateButton((Button)getView().findViewById(sensorType), sSensorsName[sensorInd], sStateConnected, sStateColorConnected);

                    boolean bReady = true;
                    for (int a = 0; a < nSensorCount; ++a) {
                        if (sensorUnits[a] == null || !sensorUnits[a].ready()) {
                            bReady = false;
                            break;
                        }
                    }
                    if (bReady) {
                        updateStatus(Status.STATUS_READY);
                    }
                }
            });
        }

        @Override
        public void onDisconnected(final int sensorType) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int sensorInd = getSensorInd(sensorType);
                    setStateButton((Button)getView().findViewById(sensorType), sSensorsName[sensorInd], sStateDisconnected, sStateColorDisconnected);

                    if (recordStatus == Status.STATUS_READY)
                        updateStatus(Status.STATUS_READY);
                }
            });
        }

        @Override
        public void onConnectFail(final int sensorType) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int sensorInd = getSensorInd(sensorType);
                    setStateButton((Button)getView().findViewById(sensorType), sSensorsName[sensorInd], sStateDisconnected, sStateColorDisconnected);
                    Toast.makeText(getContext(), R.string.toast_connect_fail, Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onConnecting(final int sensorType) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int sensorInd = getSensorInd(sensorType);
                    setStateButton((Button)getView().findViewById(sensorType), sSensorsName[sensorInd], sStateConnecting, sStateColorConnecting);
                }
            });
        }

        @Override
        public void onAbort(final int sensorType) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int sensorInd = getSensorInd(sensorType);
                    setStateButton((Button)getView().findViewById(sensorType), sSensorsName[sensorInd], sStateDisconnected, sStateColorDisconnected);
                    if (recordStatus == Status.STATUS_RECORDING) {
                        sensorDataRecorder.stop();
                    }
                    Toast.makeText(getContext(), R.string.toast_connection_aborted, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private final int nRecordRateMs = 100;
    private SensorDataRecorder sensorDataRecorder = new SensorDataRecorder();
    private SensorDataRecorder.OnRecordStateListener recordStateListener = new SensorDataRecorder.OnRecordStateListener() {
        @Override
        public void onRecording() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int a = 0; a < nSensorCount; ++a) {
                        if (sensorUnits[a] == null) continue;
                        if (sensorUnits[a].ready()) {
                            int sensorType = sensorUnits[a].getId();
                            int sensorInd = getSensorInd(sensorType);
                            setStateButton((Button)getView().findViewById(sensorType), sSensorsName[sensorInd], sStateDataFlow, sStateColorDataFlow);
                        }
                    }
                    updateStatus(Status.STATUS_RECORDING);
                }
            });
        }

        @Override
        public void onRecordEnded() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int a = 0; a < nSensorCount; ++a) {
                        if (sensorUnits[a] == null) continue;
                        int sensorType = sensorUnits[a].getId();
                        int sensorInd = getSensorInd(sensorType);
                        if (sensorUnits[a].ready()) {
                            setStateButton((Button)getView().findViewById(sensorType), sSensorsName[sensorInd], sStateConnected, sStateColorConnected);
                        } else {
                            setStateButton((Button)getView().findViewById(sensorType), sSensorsName[sensorInd], sStateDisconnected, sStateColorDisconnected);
                        }
                    }
                    updateStatus(Status.STATUS_ENDED);
                }
            });
        }
    };

    private void updateStatus(Status status) {
        recordStatus = status;
        switch (status) {
            case STATUS_PREPARE:
                tvStart.setText(R.string.btn_start_prepare);
                break;
            case STATUS_READY:
                tvStart.setText(R.string.btn_start_begin);
                break;
            case STATUS_ENDED:
                tvStart.setText(R.string.btn_start_again);
                break;
            case STATUS_PENDING:
                tvStart.setText(R.string.btn_start_pending);
                break;
            case STATUS_RECORDING:
                tvStart.setText(R.string.btn_start_recording);
                break;
        }
    }

    private static int getSensorInd(int btnId)
    {
        switch (btnId) {
            case R.id.btn_arm:
                return SensorInd.SENSOR_ARM;
            case R.id.btn_leg:
                return SensorInd.SENSOR_LEG;
            case R.id.btn_chest:
                return SensorInd.SENSOR_CHEST;
            default:
                return SensorInd.SENSOR_UNKNOWN;
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btn_arm:
                case R.id.btn_leg:
                case R.id.btn_chest:
                    int sensorInd = getSensorInd(v.getId());
                    setStateButton((Button)getView().findViewById(v.getId()), sSensorsName[sensorInd], sStateDisconnected, sStateColorDisconnected);
                    Intent data = new Intent(getContext(), AddDevActivity.class);
                    data.putExtra(sData_SensorType, v.getId());
                    data.putExtra(sData_SensorName, sSensorsName[sensorInd]);
                    startActivityForResult(data, REQUESTCODE_ADDDEVACTIVITY);
                    break;
                case R.id.btn_start:
                    switch (recordStatus) {
                        case STATUS_READY:
                        case STATUS_PREPARE:
                            boolean bReady = true;
                            for (int a = 0; a < nSensorCount; ++a) {
                                if (sensorUnits[a] == null || !sensorUnits[a].ready()) {
                                    bReady = false;
                                    break;
                                }
                            }
                            if (!bReady) {
                                Toast.makeText(getContext(), R.string.toast_not_all_devices_connected, Toast.LENGTH_LONG).show();
                                updateStatus(Status.STATUS_PREPARE);
                            } else {
                                String sCurrentTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
                                String sRecordFilename = App.getStorageDir() + sCurrentTime + "-record.csv";
                                sensorDataRecorder.init(sensorUnits, sRecordFilename, nRecordRateMs);
                                sensorDataRecorder.setListener(recordStateListener);
                                sensorDataRecorder.start();
                                updateStatus(Status.STATUS_PENDING);
                            }
                            break;
                        case STATUS_PENDING:
                            break;
                        case STATUS_ENDED:
                            updateStatus(Status.STATUS_READY);
                            break;
                        case STATUS_RECORDING:
                            sensorDataRecorder.stop();
                            updateStatus(Status.STATUS_PENDING);
                            break;
                    }
                    break;
            }
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case REQUESTCODE_ADDDEVACTIVITY:
                switch (resultCode)
                {
                    case RESULT_OK:
                        int sensorType = data.getIntExtra(sData_SensorType, R.id.btn_arm);
                        BluetoothDevice device = data.getParcelableExtra(sData_SensorDevice);
                        int sensorInd = getSensorInd(sensorType);
                        if (sensorUnits[sensorInd] != null)
                            sensorUnits[sensorInd].stop();
                        sensorUnits[sensorInd] = new SensorUnit();
                        sensorUnits[sensorInd].init(device, sensorType);
                        sensorUnits[sensorInd].setListener(sensorUnitStateListener);
                        sensorUnits[sensorInd].start();
                        break;
                }
                break;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        if (rootView == null) return null;

        btnArm = rootView.findViewById(R.id.btn_arm); btnArm.setOnClickListener(onClickListener);
        btnLeg = rootView.findViewById(R.id.btn_leg); btnLeg.setOnClickListener(onClickListener);
        btnChest = rootView.findViewById(R.id.btn_chest); btnChest.setOnClickListener(onClickListener);
        btnStart = rootView.findViewById(R.id.btn_start); btnStart.setOnClickListener(onClickListener);
        tvStart = rootView.findViewById(R.id.tv_start);

        setStateButton(btnArm, sSensorsName[SensorInd.SENSOR_ARM], sStateDisconnected, sStateColorDisconnected);
        setStateButton(btnLeg, sSensorsName[SensorInd.SENSOR_LEG], sStateDisconnected, sStateColorDisconnected);
        setStateButton(btnChest, sSensorsName[SensorInd.SENSOR_CHEST], sStateDisconnected, sStateColorDisconnected);

        return rootView;
    }

}
