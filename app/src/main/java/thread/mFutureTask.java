package thread;

import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class mFutureTask extends FutureTask{

    private mCallBack callBack;

    public mFutureTask(@NonNull Callable callable) {
        super(callable);
    }

    public mFutureTask(@NonNull Runnable runnable, Object result) {
        super(runnable, result);
    }

    public mFutureTask(@NonNull Callable callable, mCallBack callBack) {
        this(callable);
        this.callBack = callBack;
    }

    @Override
    protected void done() {
        super.done();
        try {
            Map m = (HashMap) this.get();
            callBack.jsonData(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
