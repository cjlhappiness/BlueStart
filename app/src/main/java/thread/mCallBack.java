package thread;

import java.util.concurrent.ExecutorService;

public interface mCallBack {
    ExecutorService getExecutorService();
    void isRequestBegin(int operateCode);
    void isRequestFinish();
    void isUpdateFinish(boolean isEdit, String message);
    void showMessage(String message);
}
