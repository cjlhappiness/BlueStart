package frag;

/*
Fragment类父类
*/

import android.support.v4.app.Fragment;
import java.util.concurrent.FutureTask;
import thread.mCallBack;
import thread.mCallable;
import thread.mFutureTask;
import util.Date;

public class mFragment extends Fragment{

     private FutureTask task;
    public final static int[] ID      = new int[]{0, 1};
    public final static int[] USER_ID = new int[]{950125, 950422};

    public mFragment() {
    }

    //请求网络数据
    public void onRefresh(String url, mCallBack c){
        mCallable callable = new mCallable(url);
        new mFutureTask(callable, c);
    }
}
