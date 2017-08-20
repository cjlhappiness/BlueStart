package frag;

import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xicp.cjlhappiness.bluestart.R;

import java.util.ArrayList;
import java.util.List;
import adapter.ThirdAdapter;
import util.Date;

//第3个Fragment
public class ThirdFragment extends mFragment{

    private List data;
    private View view;
    private GridView gridView;
    private TextView textView;
    private ThirdAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.third_flag_main, null);
        initView();
        initData();
        return view;
    }

    private void initView(){
        textView = (TextView) view.findViewById(R.id.third_title);
        gridView = (GridView) view.findViewById(R.id.third_grid);
    }

    private void initData(){
        textView.setText(Date.getDate());

        data = new ArrayList();
        int dayCount = Date.getDayCountInMonth();
        int firstDay = Date.getFirstDayInMonth();
        for (int i = 0; i< firstDay; i++) {
            data.add("");
        }
        for (int i = 1; i<= dayCount; i++) {
            data.add(String.valueOf(i));
        }
        adapter = new ThirdAdapter(getActivity(), data);
        gridView.setAdapter(adapter);
    }
}
