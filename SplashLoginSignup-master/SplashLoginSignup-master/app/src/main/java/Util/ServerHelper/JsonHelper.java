package Util.ServerHelper;

import org.json.JSONObject;

public class JsonHelper {
    //返回是否成功
    public static String getMsg(String string) throws Exception{
        JSONObject jsonObject = new JSONObject(string);
        String msg = jsonObject.getString("msg");
        return msg;
    }
    //返回用户的id
    public static String getUserId(String string) throws Exception{
        JSONObject jsonObject = new JSONObject(string);
        String userId= jsonObject.getString("userId");
        return userId;
    }
}
