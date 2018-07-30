package Util.DataHelpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SensorRasp extends Thread{

    public static List data = new ArrayList();
    public static final String IP_ADDR = "192.168.43.151";//服务器地址
    public static final int PORT = 12340;//服务器端口号

    public static int count = 0 ;
    public static double energy = 0.0;
    public static double water = 0.0;

    @Override
    public void run(){
                Socket socket = null;
                try {
                    System.out.println("执行");
                    //创建一个流套接字并将其连接到指定主机上的指定端口号
                    socket = new Socket(IP_ADDR, PORT);
                    //读取服务器端数据
                    InputStream is = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    BufferedWriter out = null;
                    String info = null;
                    data =new ArrayList();
                    while((info=br.readLine())!=null){
                        double itemdata = Double.valueOf(info.trim());
                        System.out.println(itemdata);
                        data.add(itemdata);
                    }
                    br.close();
                    is.close();
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
    public static void main(String []arg) {
        Thread thread = new SensorRasp();
        thread.start();
        try {
            sleep(3000);
            getAlldatas();
            System.out.println(count);
            thread.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getAlldatas(){
        count = getCount();
        energy = getEnergy();
        water = getWater();
        System.out.println(count);
        System.out.println(energy);
        System.out.println(water);
    }
    public static int getCount(){
        int a = 0 ;
        List list = getDistances();
        for(int i = 0 ;i<list.size();i++){
            if((Double)list.get(i)>42.5){
                i+=10;
                a++;
            }
        }
        return a;
    }

    public static List getDistances(){
        List list = new ArrayList();
        double distance = 0 ;
        for (Object item : SensorRasp.data) {
            double itemdata = ((Double) item).doubleValue();
            distance += itemdata * 0.5 * 0.05 *0.05*1000;
            list.add(distance);
        }
        return  list;
    }
    public static void getData(String str ){
        String [] array = null;
        try {
            str = null;
            array = str.split(" ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(String item : array){
            double itemdata = Double.valueOf(item);
            data.add(itemdata);
        }
    }
    public static double getEnergy(){
        double energy =0.0;
        int a = 0 ;
        List list = getDistances();
        for(int i = 0 ;i<list.size();i++){
            if((Double)list.get(i)>42.5){
                double max = ((Double) list.get(i)).doubleValue();
                for(int j = 0 ;j < 7; j++) {
                    if ((i + j) < list.size()) {
                        double value = ((Double) list.get(i + j)).doubleValue();
                        if (value > max) {
                            max = value;
                        }
                    }
                }
                energy += 68.6 * max/100;
                i+=8;
                a++;
            }
        }
        return energy;
    }
    public static double getWater(){
        return energy/23.7;
    }
}
