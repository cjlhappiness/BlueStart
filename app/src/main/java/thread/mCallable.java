package thread;

/*
网络请求任务类Callable
*/

import android.util.Log;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class mCallable implements Callable{

    private String url;
    private List params;

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
        Map m = new HashMap();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
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

            m.put("code", code);
            if (code == 200){
                String s = response.body().string();
                m.put("content", s);
            }
        } catch (SocketTimeoutException e){
        } catch (IOException e) {

        }
        return m;
    }

}
