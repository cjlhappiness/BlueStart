package thread;

import java.util.concurrent.ExecutorService;

public interface mCallBack {
    ExecutorService getExecutorService();
    void isRequestBegin(int operateCode);
    void isRequestFinish();
    void showMessage(String message);
}
