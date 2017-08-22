package frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xicp.cjlhappiness.bluestart.R;

import java.util.Date;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;
import thread.mCallBack;
import util.Network;

//第1个Fragment
public class FirstFragment extends mFragment implements mCallBack{

    public boolean isRefresh;
    private GifImageView gifImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_flag_main, null);
        onRefresh(Network.FIRST_GET, this);
        return view;
    }

    @Override
    public void jsonData(Map m) {

    }
}
