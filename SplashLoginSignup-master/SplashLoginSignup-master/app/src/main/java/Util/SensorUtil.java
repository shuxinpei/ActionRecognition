package Util;

import java.util.HashMap;
import java.util.Map;

public class SensorUtil {
    public static Map<String ,String> sensors = new HashMap<>();
    //这边返回传感器的状态
    //这边需要用蓝牙
    public static Map<String ,String > getSensorSatus(){
        sensors.put("传感器1","连接");
        sensors.put("传感器2","未连接");
        sensors.put("传感器3","未连接");
        sensors.put("传感器4","未连接");
        sensors.put("传感器5","未连接");
        sensors.put("传感器6","未连接");
        return sensors;
    }
}
