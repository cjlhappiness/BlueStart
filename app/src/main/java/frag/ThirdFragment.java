package frag;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.xicp.cjlhappiness.bluestart.R;
import java.util.ArrayList;
import java.util.List;
import adapter.ThirdAdapter;
import data.ThirdData;
import util.Date;

//第3个Fragment
public class ThirdFragment extends mFragment
        implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private List data;
    private View view;
    private GridView gridView;
    private TextView textView;
    private EditText editText;
    private ThirdAdapter adapter;

    private int nowSelect, nowDay;

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
        editText = (EditText) view.findViewById(R.id.third_content);
        gridView = (GridView) view.findViewById(R.id.third_grid);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(this);
    }

    private void initData(){
        textView.setText(Date.getDate());
        data = new ArrayList();
        int dayCount = Date.getDayCountInMonth();
        int firstDay = Date.getFirstDayInMonth();
        for (int i = 0; i< firstDay; i++) {
            ThirdData thirdData = new ThirdData(-1, -1, 0, "", "");
            data.add(thirdData);
        }
        for (int i = 1; i<= dayCount; i++) {
            ThirdData thirdData = new ThirdData(ID[0], USER_ID[0], 0, String.valueOf(i), String.valueOf(i));
            data.add(thirdData);
        }
        adapter = new ThirdAdapter(getActivity(), data);
        gridView.setAdapter(adapter);

        nowSelect = nowDay = Date.getNowDayInMonth();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (nowDay != nowSelect){
            parent.getChildAt(nowSelect).setBackgroundColor(Color.alpha(0));
        }
        view.setBackgroundColor(Color.RED);
        nowSelect = position;
        editText.setText(((ThirdData)data.get(position)).getContent());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), ((ThirdData)data.get(position)).getDay()+"--", Toast.LENGTH_SHORT).show();
        return false;
    }
}
