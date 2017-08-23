package frag;

/*
Fragment类父类
*/

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import thread.mCallBack;
import thread.mCallable;
import thread.mFutureTask;
import pl.droidsonroids.gif.*;
import com.xicp.cjlhappiness.bluestart.R;

public class mFragment extends Fragment{

    public GifDrawable gifDrawable;
    public mFutureTask futureTask;
    private FutureTask task;

    public ExecutorService exec = Executors.newFixedThreadPool(5);

    public final static int[] ID      = new int[]{0, 1};
    public final static int[] USER_ID = new int[]{950125, 950422};

    public mFragment() {
    }

    //请求网络数据
    public void onRefresh(String url, final mCallBack c, String ...data){
        mCallable callable = new mCallable(url, data);
        futureTask = new mFutureTask<Object>(callable);
        exec.submit(futureTask);
        Map m = null;
        try {
            m = (Map) futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        c.jsonData(m);
        Toast.makeText(getActivity(), "onRefresh", Toast.LENGTH_SHORT).show();
    }

    public GifDrawable loadGif(){
        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(getResources(),R.mipmap.load_gif);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gifDrawable;
    }
}
