package frame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//第1个Fragment
public class FirstFragment extends mFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();

    }
}
