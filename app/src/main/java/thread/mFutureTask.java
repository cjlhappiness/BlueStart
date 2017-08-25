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

public class mFutureTask extends FutureTask {

    private Handler handler;
    private int operateCode;

    public mFutureTask(@NonNull Callable callable) {
        super(callable);
    }

    public mFutureTask(@NonNull Callable callable, Handler handler, int operateCode) {
        super(callable);
        this.handler = handler;
        this.operateCode = operateCode;
    }

    @Override
    protected void done() {
        super.done();
        try {
            Message message = handler.obtainMessage();
            Bundle bundle = new Bundle();
            Map map = (Map) get();
            map.put("operateCode", operateCode);
            bundle.putSerializable("map", (Serializable) map);
            message.setData(bundle);
            handler.sendMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



}
