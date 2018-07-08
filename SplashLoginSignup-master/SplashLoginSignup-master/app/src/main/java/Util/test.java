package Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class test extends Thread{
    String username;
    String password;
    public static boolean flag=true;
    public test(String username ,String password){
        this.username= username;
        this.password= password;
    }
    @Override
    public void run(){
        try {
            String param = "username=" + username + "&password=" + password;
            String res = test.reqPost("http://localhost:8080/account/SignonFormData", param.trim());
            System.out.println(res);
            if (res.contains("success")) {
                flag=true;
                System.out.print("跳转");
            } else {
                flag=false;
                System.out.println("不跳转");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public static URLConnection getConnection(String s) throws IOException {
        URL url = new URL(s);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

        return conn;
    }

    public static String reqGet(String s, String param) throws IOException {
        String res = "";
        URLConnection conn = getConnection(s + "?" + param); // GET要求请求参数写在URL末尾，中间用?隔开
        conn.connect(); // GET方法默认用connect发送连接请求

        Map<String, List<String>> map = conn.getHeaderFields(); // 得到响应标头的所有属性键值
        for (String key : map.keySet()) { // 遍历打印
            System.out.println(key + "---->" + map.get(key));
        }

        try ( // 正常的请求（获取资源的请求）
              BufferedReader in = new BufferedReader( // 用BufferedReader包装响应得到的数据
                      new InputStreamReader(conn.getInputStream(), "utf-8"))
        ) {
            String line;
            while ((line = in.readLine()) != null) { // 一行行读取（由于读取的都是文本文件：JSP文件）
                res += line + "\n";
            }
        } catch (Exception e) {
            System.out.println("Get Error Occured!");
            e.printStackTrace();
        }

        return res;
    }

    public static String reqPost(String s, String param) throws IOException {
        String res = "";
        URLConnection conn = getConnection(s); // POST要求URL中不包含请求参数
        conn.setDoOutput(true); // 必须设置这两个请求属性为true，就表示默认使用POST发送请求
        conn.setDoInput(true);

        // 请求参数必须使用conn获取的OutputStream输出到请求体中
        PrintWriter out = new PrintWriter(conn.getOutputStream()); // 用PrintWriter进行包装
        out.println(param);
        out.flush(); // 立即充刷至请求体）PrintWriter默认先写在内存缓存中

        try ( // 发送正常的请求（获取资源）
              BufferedReader in = new BufferedReader(
                      new InputStreamReader(conn.getInputStream(), "utf-8"))
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                res += line + "\n";
            }
        } catch (Exception e) {
            System.out.println("Get Error Occured!");
            e.printStackTrace();
        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        String param = "username="+"shuxin"+"&password="+"shuxin";
        String res = test.reqPost("http://localhost:8080/account/SignonFormData", param.trim());
        System.out.println(res);
            if(res.contains("success")){
                System.out.print("跳转");
            }else{
                System.out.println("不跳转");
            }
        }
    }

