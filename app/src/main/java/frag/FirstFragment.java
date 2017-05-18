package frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xicp.cjlhappiness.bluestart.MainActivity;
import com.xicp.cjlhappiness.bluestart.R;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import thread.mCallable;

//第1个Fragment
public class FirstFragment extends mFragment{

    public boolean isRefresh;
    //"http://cjlhappiness.xicp.net/BlueStart/getSayJSON.php"
    private String url = "http://cjlhappiness.xicp.net";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_flag_main, null);
        onRefresh(url);

        return view;
    }


}
