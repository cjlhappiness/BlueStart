package util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import data.FirstData;
import data.FourthData;
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

    //解析时光轴数据
    public static List parseFourthJson(String json){
        List list = new ArrayList();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                int userId = jsonObject.getInt("userId");
                String datetime = jsonObject.getString("datetime");
                String[] datetimeArr = datetime.split("-| ");
                int year = Integer.parseInt(datetimeArr[0].substring(2));
                int month = Integer.parseInt(datetimeArr[1]);
                int day = Integer.parseInt(datetimeArr[2]);
                String time = datetimeArr[3];
                String content = time + "\n" + jsonObject.getString("content");
                FourthData data = new FourthData(id, userId, year, month, day, time, content);
                list.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //解析游戏数据
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
