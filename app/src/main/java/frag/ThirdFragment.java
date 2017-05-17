package frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xicp.cjlhappiness.bluestart.R;

//第3个Fragment
public class ThirdFragment extends mFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_flag_main, null);

        return view;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();

    }

}
