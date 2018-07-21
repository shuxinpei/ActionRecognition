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
    public static final int PORT = 12339;//服务器端口号
    
    @Override
    public void run(){
            while (true) {
                Socket socket = null;
                try {
                    System.out.println("执行");
                    //创建一个流套接字并将其连接到指定主机上的指定端口号
                    socket = new Socket(IP_ADDR, PORT);
                    //读取服务器端数据
                    InputStream is = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    BufferedWriter out = null;
                    File writename = new File("D:\\01WorkFile\\Hack\\HackShanghai\\data.txt");
                    out = new BufferedWriter(new FileWriter(writename));
                    String info = null;
                    data =new ArrayList();
                    while((info=br.readLine())!=null){
                        data.add(Double.valueOf(info));
                        System.out.println("加速度"+info);
                        out.write(info+" ");
                        out.flush();
                    }
                    out.close();
                    br.close();
                    is.close();
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }
    public static void main(String []arg) {
        //new SensorRasp().start();
        String [] array = null;
        try {
            StringBuffer sb = new StringBuffer("");
            FileReader reader = new FileReader("D:\\01WorkFile\\Hack\\HackShanghai\\data.txt");
            BufferedReader br = new BufferedReader(reader);
            String str = null;
            while ((str = br.readLine()) != null) {
               array = str.split(" ");
            }
            br.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(String item : array){
            double itemdata = Double.valueOf(item);
            data.add(itemdata);
        }
        System.out.println(SensorRasp.getCount());
    }
    public static int getCount(){
        int a = 0;
        for(int i =0 ;i < data.size();i++ ){
            Double item = (Double)data.get(i);
            if(item>5 | item <-5 ){
                a++;
                i+=2;
            }
        }
        return a;
    }
    public double getDistance(){
        double distance = 0.0;


        return distance;
    }

}
