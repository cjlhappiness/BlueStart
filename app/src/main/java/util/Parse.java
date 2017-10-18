package util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import data.FirstData;
import data.SixthData;
import data.ThirdData;
import data.UserData;

public class Parse {



    private UserData getUserData(){

        
        return null;
    }

    private FirstData getFirstData(){

        return null;
    }

    //解析小红花数据
    public static List parseThirdJson(String json){
        List list = new ArrayList();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                int userId = jsonObject.getInt("userId");
                String day = jsonObject.getString("day");
                String content = jsonObject.getString("content");
                int state = jsonObject.getInt("state");
                ThirdData data = new ThirdData(id, userId, state, day, content);
                list.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static SixthData parseSixthJson(String json){
        SixthData data = null;
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            int id = jsonObject.getInt("id");
            int userId = jsonObject.getInt("userId");
            int point = jsonObject.getInt("point");
            int freeCount = jsonObject.getInt("freeCount");
            data = new SixthData(id, userId, point, freeCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

}
