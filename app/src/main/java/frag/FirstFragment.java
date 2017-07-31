package frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xicp.cjlhappiness.bluestart.R;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import util.Network;

//第1个Fragment
public class FirstFragment extends mFragment{

    public boolean isRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_flag_main, null);
        onRefresh(Network.FIRST_GET);

        return view;
    }


}
