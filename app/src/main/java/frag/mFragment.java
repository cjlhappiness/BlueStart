package frag;

/*
Fragment类父类
*/

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import thread.mCallable;
import thread.mFutureTask;
import pl.droidsonroids.gif.*;
import com.xicp.cjlhappiness.bluestart.R;

public class mFragment extends Fragment{

    public GifDrawable gifDrawable;
    public mFutureTask futureTask;

    public ExecutorService exec = Executors.newFixedThreadPool(5);

    public final static int[] ID      = new int[]{0, 1};
    public final static int[] USER_ID = new int[]{950125, 950422};

    public final static int[] OPERATE_CODE = new int[]{0x100, 0x200};

    public mFragment() {
    }

    //请求网络数据
    public void LoadOrUpdateData(String url, Handler handler, List params, int operateCode){
        mCallable callable = new mCallable(url, params);
        futureTask = new mFutureTask(callable, handler, operateCode);
        exec.submit(futureTask);
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
