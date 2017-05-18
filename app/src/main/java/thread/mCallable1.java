package thread;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Callable;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class mCallable1 implements Callable<String>{

    private String url;
    private String[] data;
    private static final String KEY = "data";

    public mCallable1(String url){
        this(url, "null");
    }

    public mCallable1(String url, String ...data){
        this.url = url;
        if (data != null){
            this.data = data.clone();
        }
    }

    @Override
    public String call() throws Exception {
        String o = null;
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        if (data != null){
            for (int i = 0 ; i < data.length ; i++){
                builder.add(KEY + i, data[i]);
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        try {
            Response response = client.newCall(request).execute();
            int code = response.code();
            if (response.isSuccessful()){
                if (code == 200){
                    o = response.body().string();
                }else{
                    o = code + "";
                }
            }else {
                o = "999";
            }
        } catch (IOException e) {
        }
        return o;
    }
}
