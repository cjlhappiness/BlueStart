package frag;

/*
Fragment类父类
*/

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import java.util.List;
import java.util.Map;
import thread.mCallBack;
import thread.mCallable;
import thread.mFutureTask;

public class mFragment extends Fragment{

    public mFutureTask futureTask;

    public SwipeRefreshLayout swipeRefreshLayout;

    public mCallBack callBack;

    public final static int[] USER_ID = new int[]{950422};

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            parseData((Map) msg.getData().getSerializable("map"));
        }
    };

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
    public void loadOrUpdateData(String url, Handler handler, List requestParams, Map callBackParams){
        mCallable callable = new mCallable(url, requestParams);
        futureTask = new mFutureTask(callable, handler, callBackParams);
        callBack.getExecutorService().submit(futureTask);
        callBack.isRequestBegin((int) callBackParams.get("operateCode"));
    }

    public void parseData(Map m) {
        ((mCallBack) getActivity()).isRequestFinish();
    }

    public void initView(){

    }

    public void initData(){

    }

}
