package util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import data.FirstData;
import data.ThirdData;
import data.UserData;

public class Parse {



    private UserData getUserData(){

        
        return null;
    }

    private FirstData getFirstData(){

        return null;
    }

    //{"id":"1","date":"201708","content":"\u4f60\u662f","day":"1","state":"2"}
    public static List parseThirdJson(String json){
        List list = new ArrayList();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                int userId = jsonObject.getInt("id");
                int state = jsonObject.getInt("state");
                String day = jsonObject.getString("day");
                String content = jsonObject.getString("content");
                ThirdData data = new ThirdData(id, userId, state, day, content);
                list.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
