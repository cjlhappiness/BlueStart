package frag;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.xicp.cjlhappiness.bluestart.R;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Map;
import adapter.ThirdAdapter;
import data.ThirdData;
import util.Date;
import util.Network;
import pl.droidsonroids.gif.*;
import util.Parse;

//第3个Fragment
public class ThirdFragment extends mFragment implements View.OnClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private List data;
    private View view;
    private GridView gridView;
    private TextView textView;
    private EditText editText;
    private Button previousBtn, nextBtn;
    private GifImageView gifImageView;
    private ThirdAdapter adapter;
    private int nowSelect, nowDay;
    private int selectMonth;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            parseData((Map) msg.getData().getSerializable("map"));
        }
    };

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
        previousBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(this);
        gifImageView = (GifImageView) view.findViewById(R.id.third_load_gif);
        gifDrawable = loadGif();
        gifImageView.setImageDrawable(gifDrawable);
    }

    private void initData(){
        textView.setText(Date.getDateString()[0]);
        data = new ArrayList();
        adapter = new ThirdAdapter(getActivity(), data);
        gridView.setAdapter(adapter);
        nowSelect = nowDay = Date.getNowDayInMonth() + Date.getFirstDayInMonth() - 1;
        selectMonth = 0;
        LoadOrUpdateData(Network.THIRD_GET, handler, createParams(OPERATE_CODE[0], null), OPERATE_CODE[0]);
    }

    private void fillItem(List list){
        if (data.size() != 0) {
            data.clear();
        }
        for (int i = 0 ; i< Date.getFirstDayInMonth(); i++){
                data.add(null);
            }
        data.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void fillItem(){
        for (int i = 0 ; i < Date.getFirstDayInMonth() + Date.getDayCountInMonth(); i++){
            data.add(null);
        }
        adapter = new ThirdAdapter(getActivity(), data);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (data.get(position) == null){
            return;
        }
        if (nowDay != nowSelect){
            parent.getChildAt(nowSelect).setBackgroundColor(Color.alpha(0));
        }
        view.setBackgroundColor(Color.RED);
        nowSelect = position;
        editText.setText(((ThirdData)data.get(position)).getContent());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Date.getDateTime();
//        LoadOrUpdateData(Network.THIRD_SET, handler, createParams(), DATA_CODE[1]);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.third_previous:
                selectMonth --;
                break;
            case R.id.third_next:
                selectMonth ++;
                break;
        }
        String date = Date.getDateString(selectMonth)[0];
        textView.setText(date);
        LoadOrUpdateData(Network.THIRD_GET, handler, createParams(OPERATE_CODE[1], null), OPERATE_CODE[1]);
    }

    public void parseData(Map m) {
        try {
            if ((int)m.get("code") == 200) {
                if ((int)m.get("operateCode") == OPERATE_CODE[0]) {
                    Object json = m.get("content");
                    fillItem(Parse.parseThirdJson(json.toString()));
                }else {

                }
            }
        }catch (NullPointerException e){
        }
    }

    public List createParams(int code, ThirdData d){
        List params = new ArrayList();
        if (code == OPERATE_CODE[0]){
            String[] s = new String[]{"date", Date.getDateString(selectMonth)[1]};
            params.add(s);
        }else {
            String[] s1 = new String[]{"id", String.valueOf(d.getId())};
            String[] s2 = new String[]{"userId", String.valueOf(d.getUserId())};
            String[] s3 = new String[]{"content", editText.getText().toString()};
            String[] s4 = new String[]{"state", String.valueOf((-d.getState()))};
            String[] s5 = new String[]{"datetime", Date.getDateTime()};
        }
        return params;
    }
}
