package thread;

/*
网络请求任务类Callable
*/

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class mCallable implements Callable{

    private String url;
    private List params;
    private static final String KEY = "data";

    public mCallable(String url){
        this(url, null);
    }

    public mCallable(String url, List params){
        this.url = url;
        if (params != null){
            this.params = params;
        }
    }

    @Override
    public Object call() throws Exception {
        Map m = null;
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null){
            for (int i = 0 ; i < params.size() ; i++){
                String[] param = (String[]) params.get(i);
                builder.add(param[0], param[1]);
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        try {
            Response response = client.newCall(request).execute();
            int code = response.code();
            m = new HashMap();
            m.put("code", code);
            if (code == 200){
                String s = response.body().string();
                m.put("content", s);
            }
        } catch (IOException e) {
        }
        return m;
    }


}
