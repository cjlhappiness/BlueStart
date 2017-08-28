package thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class mFutureTask<V> extends FutureTask<V> {

    private Handler handler;
    private int operateCode;

    public mFutureTask(@NonNull Callable<V> callable) {
        super(callable);
    }

    public mFutureTask(@NonNull Runnable runnable, V result) {
        super(runnable, result);
    }

    public mFutureTask(@NonNull Callable<V> callable, Handler handler, int operateCode) {
        super(callable);
        this.handler = handler;
        this.operateCode = operateCode;
    }

    @Override
    protected void done() {
        super.done();
        Map map= null;
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        try {
            map = (Map) get(5, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        map.put("operateCode", operateCode);
        bundle.putSerializable("map", (Serializable) map);
        message.setData(bundle);
        handler.sendMessage(message);
    }



}
