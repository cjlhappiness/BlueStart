package frag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import com.xicp.cjlhappiness.bluestart.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import adapter.ThirdAdapter;
import data.ThirdData;
import util.Date;
import util.Network;

//第3个Fragment
public class ThirdFragment extends mFragment implements View.OnClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private List data;
    private View view;
    private GridView gridView;
    private TextView textView;
    private EditText editText;
    private Button previousBtn, nextBtn;
    private ThirdAdapter adapter;

    private int nowSelect, nowDay;
    private int selectMonth;

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
        previousBtn = (Button) view.findViewById(R.id.third_previous);
        nextBtn = (Button) view.findViewById(R.id.third_next);
        gridView = (GridView) view.findViewById(R.id.third_grid);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(this);
    }

    private void initData(){
        textView.setText(Date.getDateString());
        data = new ArrayList();
        adapter = new ThirdAdapter(getActivity(), data);
        gridView.setAdapter(adapter);
        nowSelect = nowDay = Date.getNowDayInMonth();
        selectMonth = Date.getNowMonth();
        int firstDay = Date.getFirstDayInMonth();
        int dayCount = Date.getDayCountInMonth();
        onRefresh(Network.THIRD_GET, this);
        initItem(firstDay, dayCount);
    }

    private void initItem(int firstDay, int dayCount){
        if (data.size() != 0) {
            data.clear();
        }
        for (int i = 0; i< firstDay; i++) {
            ThirdData thirdData = new ThirdData(-1, -1, 0, "", "");
            data.add(thirdData);
        }
        for (int i = 1; i<= dayCount; i++) {
            ThirdData thirdData = new ThirdData(ID[0], USER_ID[0], 0, String.valueOf(i), String.valueOf(i));
            data.add(thirdData);
        }
        adapter.notifyDataSetChanged();
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

        return false;
    }

    @Override
    public void onClick(View v) {
        int id = view.getId();
        switch (id){
            case R.id.third_previous:
                selectMonth --;
                break;
            case R.id.third_next:
                selectMonth ++;
                break;
        }
        String date = Date.getDateString(selectMonth);
        textView.setText(date);
        int firstDay = Date.getFirstDayInMonth(selectMonth);
        int dayCount = Date.getDayCountInMonth(selectMonth);
        initItem(firstDay, dayCount);
    }

    @Override
    public void jsonData(Map m) {

    }
}
