package thread;

import android.util.Log;
import java.io.IOException;
import java.util.concurrent.Callable;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class mCallable implements Callable{

    private String url;
    private String[] data;
    private mThreadCallBack callBack;
    private static final String KEY = "data";

    public mCallable(String url, mThreadCallBack callBack){
        this(url, callBack, "null");
    }

    public mCallable(String url, mThreadCallBack callBack, String ...data){
        this.url = url;
        this.callBack = callBack;
        if (data != null){
            this.data = data.clone();
        }
    }

    @Override
    public Object call() throws Exception {
        Object o = null;
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
            Log.e("test---", "call1: ");
            Log.e("test---", "call2: ");
            int code = response.code();
            if (response.isSuccessful()){
                if (code == 200){
                    o = response.body().string();
                }else{
                    o = code+"";
                }
            }
        } catch (IOException e) {
            o = "999";
        }
        callBack.callBack(o);
        return null;
    }
}
