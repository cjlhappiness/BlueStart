package thread;

import android.support.annotation.NonNull;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class mFutureTask<V> extends FutureTask<V> {

    public mFutureTask(@NonNull Callable<V> callable) {
        super(callable);
    }

    public mFutureTask(@NonNull Runnable runnable, V result) {
        super(runnable, result);
    }

    @Override
    protected void done() {
        super.done();
    }



}
