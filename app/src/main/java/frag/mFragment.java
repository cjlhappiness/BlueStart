package frag;

/*
Fragment类父类
*/

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.xicp.cjlhappiness.bluestart.MainActivity;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import thread.mCallable;

public class mFragment extends Fragment implements Future {

     private FutureTask task;

    public mFragment() {
    }

    //请求网络数据,参数1为请求地址，参数2为标志发起数据请求的Fragment
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
                    }else{
                        Log.d("mFragmnt", "null");
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

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Object get(long timeout, @NonNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
