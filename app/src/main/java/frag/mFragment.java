package frag;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.xicp.cjlhappiness.bluestart.MainActivity;

import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import thread.mCallable;
import thread.mCallable1;
import thread.mThreadCallBack;
import util.Network;

public class mFragment extends Fragment implements mThreadCallBack {

     FutureTask task;

    public mFragment() {
    }

    //请求网络数据
    public Object onRefresh(String url){
        Log.d("done","start");
        mCallable1 callable1 = new mCallable1(url);
        task = new FutureTask<String>(callable1){
            @Override
            protected void done() {
                super.done();
                try {
                    String s = (String) task.get();
                    if (s != null){
                        if (s.toString().length() == 3){
                            int code = Integer.valueOf(s.toString().trim());
                            Log.d("done", Network.getResultState(code));
                        }else{
                            Log.d("done", s.toString());
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        ((MainActivity) getActivity()).getEs().submit(task);
        return null;
    }

    //线程回调
    @Override
    public void callBack(Object o) {
            String s = (String) o;
            if (s != null){
                if (s.toString().length() == 3){
                    int code = Integer.valueOf(s.toString().trim());
                }else{
                }
            }
    }
}
