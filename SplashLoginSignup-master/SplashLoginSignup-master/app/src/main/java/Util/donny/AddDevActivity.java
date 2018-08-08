package Util.donny;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.donny.commonutils.SearchBluetoothDevice;
import com.md.splashloginsignup.R;

public class AddDevActivity extends AppCompatActivity {

    private final String sTag = "AddDevActivity";

    private Context activityContext = AddDevActivity.this;
    private SearchBluetoothDevice sdBluetooth = new SearchBluetoothDevice();
    private String sDeviceName = "HC-06";

    private TextView tvSensorType;
    private Button btnScan;
    private EditText etDevName;
    private LinearLayout svAvailDev;

    private boolean bScanning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsensor);

        tvSensorType = findViewById(R.id.tvSensorType);
        btnScan = findViewById(R.id.btnScan);
        etDevName = findViewById(R.id.etDevName);
        svAvailDev = findViewById(R.id.svAvailDev);

        Intent intent = getIntent();
        final int sensorType = intent.getIntExtra(MainFragment.sData_SensorType, -1);
        CharSequence sensorName = intent.getCharSequenceExtra(MainFragment.sData_SensorName);

        tvSensorType.setText(sensorName);

        sdBluetooth.setOnNewDeviceListener(new SearchBluetoothDevice.OnNewDeviceListener() {
            @Override
            public void onNewDeviceConnected(String addr, BluetoothDevice dev) {
                final String address = addr;
                final BluetoothDevice device = dev;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Button btnNewDev = new Button(activityContext);
                        btnNewDev.setText(address);
                        btnNewDev.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent data = new Intent();
                                data.putExtra(MainFragment.sData_SensorType, sensorType);
                                data.putExtra(MainFragment.sData_SensorDevice, device);
                                setResult(RESULT_OK, data);
                                AddDevActivity.this.finish();
                            }
                        });
                        svAvailDev.addView(btnNewDev);
                    }
                });
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bScanning) {
                    bScanning = true;
                    sDeviceName = etDevName.getText().toString();
                    btnScan.setText(R.string.btn_stopscan);
                    svAvailDev.removeAllViews();
                    sdBluetooth.searchDevice(activityContext, sDeviceName);
                } else {
                    btnScan.setText(R.string.btn_scan);
                    try {
                        sdBluetooth.stopSearch();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    bScanning = false;
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(sTag, "onDestroy");
        super.onDestroy();
        try {
            sdBluetooth.stopSearch();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setResult(RESULT_CANCELED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bScanning)
            sdBluetooth.searchDevice(activityContext, sDeviceName);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (bScanning)
                sdBluetooth.stopSearch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        AddDevActivity.this.finish();
        return true;
//        return super.onNavigateUp();
    }

    @Override
    public void onBackPressed() {
        AddDevActivity.this.finish();
        super.onBackPressed();
    }
}
