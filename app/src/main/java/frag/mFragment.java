package frag;

/*
Fragment类父类
*/

import android.support.v4.app.Fragment;
import com.xicp.cjlhappiness.bluestart.MainActivity;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import thread.mCallable;

public class mFragment extends Fragment {

     FutureTask task;

    public mFragment() {
    }

    //请求网络数据
    public Object onRefresh(String url){
        mCallable callable = new mCallable(url);
        task = new FutureTask<String>(callable){
            @Override
            protected void done() {
                super.done();
                try {
                    String s = (String) task.get();
                    if (s != null){
                        //在此解析数据
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
}
