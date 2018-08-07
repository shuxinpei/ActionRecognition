package com.md.splashloginsignup;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import Util.ServerHelper.HttpHelper;

public class jsonHelperTest {
    //@RunWith(RobolectricTestRunner.class)
    //@Config(emulateSdk = 18)

        @Test
        public void jsonTest() throws Exception{
            JSONObject jsonObject = new JSONObject();
            String str = HttpHelper.RegistByGet("zhouzhiming23","shuxin123");
            //JSONObject jsonObj = new JSONObject(str);
            System.out.println(str);
            JSONObject obj = new JSONObject(str.toString());
            String result = obj.getString("msg");
            System.out.println(result);
        }
}

