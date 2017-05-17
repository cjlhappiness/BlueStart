package thread;

import java.io.IOException;
import java.util.concurrent.Callable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class mFutureTask implements Callable, Callback{

    private String url;
    private String[] data;
    private Object object;
    private static final String KEY = "data";

    public mFutureTask(String url){
        this(url, "null");
    }

    public mFutureTask(String url, String ...data){
        this.url = url;
        if (data != null){
            this.data = data.clone();
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        object = response.body().string();
    }

    @Override
    public Object call() throws Exception {
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
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        client.newCall(request).enqueue(this);
        return null;
    }
}
