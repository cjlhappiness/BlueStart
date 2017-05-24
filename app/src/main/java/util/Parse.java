package util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import data.FirstData;
import data.UserData;

public class Parse {

    public static List parseJSON(int code, String json, List list){
        try {
            JSONArray jsonArray = new JSONArray(json);
                for (int i = 0 ; i < jsonArray.length() ; i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private UserData getUserData(){

        
        return null;
    }

    private FirstData getFirstData(){

        return null;
    }

}
