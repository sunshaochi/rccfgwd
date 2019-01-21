package com.rmwl.rcchgwd.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by acer on 2018/8/23.
 */

public class JsonTool {

    public static int getHttpCode(String jsonObject) throws JSONException {
        JSONObject object = getJsonObject(jsonObject);
        return Integer.parseInt(object.getString("code"));
    }

    private static JSONObject getJsonObject(String result) throws JSONException {
        return new JSONObject(result);
    }

    public static String getResultMsg(String result) {
        try {
            JSONObject jsonObject = getJsonObject(result);
            return jsonObject.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
