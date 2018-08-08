package Util.donny;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import static com.donny.fitness.CommonFunc.BytesToInt16;
import static com.donny.fitness.CommonFunc.BytesToInt32;

/**
 * Created by donny on 5/24/18.
 */

public class SensorDataProcessor {

    public class DeviceData {
        public double[] a = new double[4], w = new double[4], h = new double[4], q = new double[4],
                ang = new double[4], Port = new double[4];
        public double Temperature, Pressure, Altitude, GroundVelocity, GPSYaw, GPSHeight;
        public long Longitude, Latitude;
        public short sRightPack = 0;
        public short[] ChipTime = new short[7];
    }

    private DeviceData dvData = new DeviceData();

    private class DataBuffer {
        final int MAX_RXLENGTH = 1000;
        byte RxBuffer[] = new byte[MAX_RXLENGTH];
        short usRxLength = 0;
    }

    private BluetoothSocket sensorSocket;
    private DataBuffer dataBuffer = new DataBuffer();

    public void setSocket(BluetoothSocket socket) {
        sensorSocket = socket;
    }

    public DeviceData getData() {
        Log.d("BBB", "updateData: " + dvData.q[0]);
        Log.d("BBC", "updateData: " + dvData.ang[0]);
        return dvData;
    }

    public boolean updateData() throws IOException {
        if (sensorSocket == null || !sensorSocket.isConnected()) return false;

        InputStream inDataStream = sensorSocket.getInputStream();
        byte[] buffer = dataBuffer.RxBuffer;

        short usLength = (short) inDataStream.read(dataBuffer.RxBuffer, dataBuffer.usRxLength, dataBuffer.MAX_RXLENGTH - dataBuffer.usRxLength);
        dataBuffer.usRxLength += usLength;

        int pos = 0;
        while (pos + 10 < dataBuffer.usRxLength) {
            if (!((buffer[pos] == 0x55) & ((buffer[pos + 1] & 0x50) == 0x50))) {
                ++pos;
                continue;
            }
            if (((buffer[pos] + buffer[pos + 1] + buffer[pos + 2] + buffer[pos + 3] + buffer[pos + 4] +
                    buffer[pos + 5] + buffer[pos + 6] + buffer[pos + 7] + buffer[pos + 8] + buffer[pos + 9]) & 0xff) == buffer[pos + 10]) {
                // Thread safe
                DeviceData nextData = new DeviceData();
                DecodeData(nextData, buffer, pos);
                dvData = nextData;
                Log.d("ZZZ", "updateData: " + pos);
                Log.d("AAA", "updateData: " + dvData.q[0]);
                Log.d("AAC", "updateData: " + dvData.ang[0]);
                Log.d("AAC", "updateData: " + dvData.sRightPack);
            }
            pos += 11;
        }

        if (pos > 0) {
            System.arraycopy(dataBuffer.RxBuffer, pos, dataBuffer.RxBuffer, 0, dataBuffer.usRxLength - pos);
            dataBuffer.usRxLength -= pos;
        }

        return true;
    }

    private long TimeStart = 0;
    private double[] LastTime = new double[10];

    private void DecodeData(DeviceData dvData, byte[] byteTemp, int off) {
        double[] Data = new double[4];
        double TimeElapse = (System.currentTimeMillis() - TimeStart) / 1000;

        Data[0] = BytesToInt16(byteTemp, 2+off);
        Data[1] = BytesToInt16(byteTemp, 4+off);
        Data[2] = BytesToInt16(byteTemp, 6+off);
        Data[3] = BytesToInt16(byteTemp, 8+off);
        ++dvData.sRightPack;

        switch (byteTemp[1+off]) {
            case 0x50:
                //Data[3] = Data[3] / 32768 * double.Parse(textBox9.Text) + double.Parse(textBox8.Text);
                dvData.ChipTime[0] = (short) (2000 + byteTemp[2+off]);
                dvData.ChipTime[1] = byteTemp[3+off];
                dvData.ChipTime[2] = byteTemp[4+off];
                dvData.ChipTime[3] = byteTemp[5+off];
                dvData.ChipTime[4] = byteTemp[6+off];
                dvData.ChipTime[5] = byteTemp[7+off];
                dvData.ChipTime[6] = BytesToInt16(byteTemp, 8+off);

                break;

            case 0x51:
                //Data[3] = Data[3] / 32768 * double.Parse(textBox9.Text) + double.Parse(textBox8.Text);
                dvData.Temperature = Data[3] / 100.0;
                Data[0] = Data[0] / 32768.0 * 16;
                Data[1] = Data[1] / 32768.0 * 16;
                Data[2] = Data[2] / 32768.0 * 16;

                dvData.a[0] = Data[0];
                dvData.a[1] = Data[1];
                dvData.a[2] = Data[2];
                dvData.a[3] = Data[3];

                if ((TimeElapse - LastTime[1]) < 0.1) return;
                LastTime[1] = TimeElapse;

                break;

            case 0x52:
                //Data[3] = Data[3] / 32768 * double.Parse(textBox9.Text) + double.Parse(textBox8.Text);
                dvData.Temperature = Data[3] / 100.0;
                Data[0] = Data[0] / 32768.0 * 2000;
                Data[1] = Data[1] / 32768.0 * 2000;
                Data[2] = Data[2] / 32768.0 * 2000;
                dvData.w[0] = Data[0];
                dvData.w[1] = Data[1];
                dvData.w[2] = Data[2];
                dvData.w[3] = Data[3];

                if ((TimeElapse - LastTime[2]) < 0.1) return;
                LastTime[2] = TimeElapse;

                break;

            case 0x53:
                //Data[3] = Data[3] / 32768 * double.Parse(textBox9.Text) + double.Parse(textBox8.Text);
                dvData.Temperature = Data[3] / 100.0;
                Data[0] = Data[0] / 32768.0 * 180;
                Data[1] = Data[1] / 32768.0 * 180;
                Data[2] = Data[2] / 32768.0 * 180;
                dvData.ang[0] = Data[0];
                dvData.ang[1] = Data[1];
                dvData.ang[2] = Data[2];
                dvData.ang[3] = Data[3];

                if ((TimeElapse - LastTime[3]) < 0.1) return;
                LastTime[3] = TimeElapse;

                break;

            case 0x54:
                //Data[3] = Data[3] / 32768 * double.Parse(textBox9.Text) + double.Parse(textBox8.Text);
                dvData.Temperature = Data[3] / 100.0;
                dvData.h[0] = Data[0];
                dvData.h[1] = Data[1];
                dvData.h[2] = Data[2];
                dvData.h[3] = Data[3];

                if ((TimeElapse - LastTime[4]) < 0.1) return;
                LastTime[4] = TimeElapse;

                break;

            case 0x55:
                dvData.Port[0] = Data[0];
                dvData.Port[1] = Data[1];
                dvData.Port[2] = Data[2];
                dvData.Port[3] = Data[3];

                break;

            case 0x56:
                dvData.Pressure = BytesToInt32(byteTemp, 2+off);
                dvData.Altitude = (double) BytesToInt32(byteTemp, 6+off) / 100.0;

                break;

            case 0x57:
                dvData.Longitude = BytesToInt32(byteTemp, 2+off);
                dvData.Latitude = BytesToInt32(byteTemp, 6+off);

                break;

            case 0x58:
                dvData.GPSHeight = (double) BytesToInt16(byteTemp, 2+off) / 10.0;
                dvData.GPSYaw = (double) BytesToInt16(byteTemp, 4+off) / 10.0;
                dvData.GroundVelocity = BytesToInt16(byteTemp, 6+off) / 1e3;

                break;

            case 0x59:
                final int norm = 32768;
                dvData.q[0] = Data[0] / norm;
                dvData.q[1] = Data[1] / norm;
                dvData.q[2] = Data[2] / norm;
                dvData.q[3] = Data[3] / norm;

                break;

            default:
                break;
        }
    }

}
