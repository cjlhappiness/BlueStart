package frag;

/*
Fragment类父类
*/

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

    public final static int[] ID      = new int[]{0, 1};
    public final static int[] USER_ID = new int[]{950125, 950422};

    //加载日历，更新小红花，更新留言
    public final static int[] OPERATE_CODE = new int[]{0x100, 0x200, 0x300};

    public mFragment() {
    }

    //请求网络数据
    public void LoadOrUpdateData(String url, Handler handler, List params, int operateCode){
        mCallable callable = new mCallable(url, params);
        futureTask = new mFutureTask(callable, handler, operateCode);
        exec.submit(futureTask);
        ((mCallBack) getActivity()).isBegin(operateCode);
    }

    public void parseData(Map m) {
        ((mCallBack) getActivity()).isFinish();
    }
}
