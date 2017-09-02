package frag;

/*
Fragment类父类
*/

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import thread.mCallBack;
import thread.mCallable;
import thread.mFutureTask;

public class mFragment extends Fragment{

    public mFutureTask futureTask;

    public SwipeRefreshLayout swipeRefreshLayout;

    public ExecutorService exec = Executors.newFixedThreadPool(5);

    public mCallBack callBack;

    public final static int[] ID      = new int[]{0, 1};
    public final static int[] USER_ID = new int[]{950125, 950422};

    //加载日历，更新小红花，更新留言
    public final static int[] OPERATE_CODE = new int[]{0x100, 0x200, 0x300};

    public mFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof mCallBack){
            callBack = (mCallBack) getActivity();
        }
    }

    //请求网络数据
    public void LoadOrUpdateData(String url, Handler handler, List requestParams, Map callBackParams){
        mCallable callable = new mCallable(url, requestParams);
        futureTask = new mFutureTask(callable, handler, callBackParams);
        exec.submit(futureTask);
        callBack.isBegin((int) callBackParams.get("operateCode"));
    }

    public void parseData(Map m) {
        ((mCallBack) getActivity()).isFinish();
    }
}
