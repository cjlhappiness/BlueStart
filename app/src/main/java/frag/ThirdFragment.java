package frag;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
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
import util.Parse;

//第3个Fragment
public class ThirdFragment extends mFragment implements View.OnClickListener, TextWatcher,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private List data;
    private View view;
    private GridView gridView;
    private TextView textView;
    private EditText editText;
    private Button previousBtn, nextBtn;
    private ThirdAdapter adapter;
    private boolean isLongClick;
    private int selectMonth, clickPosition, longClickPosition;

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
        editText.addTextChangedListener(this);

    }

    private void initData(){
        textView.setText(Date.getDateString()[0]);
        data = new ArrayList();
        adapter = new ThirdAdapter(getActivity(), data);
        gridView.setAdapter(adapter);
        clickPosition = longClickPosition = Date.getNowDayInMonth() + Date.getFirstDayInMonth() - 1;
        selectMonth = 0;
        LoadOrUpdateData(Network.THIRD_GET, handler, createParams(OPERATE_CODE[0], null), OPERATE_CODE[0]);
    }

    private void fillItem(List list){
        if (data.size() != 0) {
            data.clear();
        }
        if (Date.getFirstDayInMonth() != 7){
            for (int i = 0 ; i< Date.getFirstDayInMonth(); i++){
                data.add(null);
            }
        }
        data.addAll(list);
        editText.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    private void fillItem(){
        if (data.size() != 0) {
            data.clear();
        }
        int nullData = Date.getFirstDayInMonth(selectMonth);
        if (nullData != 7) {
            for (int i = 0; i < nullData; i++) {
                data.add(null);
            }
        }
        for (int i = 0; i < Date.getDayCountInMonth(selectMonth); i++) {
            data.add(null);
        }
        adapter = new ThirdAdapter(getActivity(), data, selectMonth);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < Date.getFirstDayInMonth(selectMonth)){
            return;
        }
        parent.getChildAt(clickPosition).setBackgroundColor(Color.alpha(0));
        view.setBackgroundColor(Color.argb(75, 255, 0, 0));
        clickPosition = position;
        if (data.get(position) != null){
            editText.setText(((ThirdData)data.get(position)).getContent());
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (selectMonth == 0){
            isLongClick = true;
            longClickPosition = position;
            List params = createParams(OPERATE_CODE[1], (ThirdData) data.get(position));
            LoadOrUpdateData(Network.THIRD_SET, handler, params, OPERATE_CODE[1]);
        }
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
        editText.setVisibility(View.INVISIBLE);
        String date = Date.getDateString(selectMonth)[0];
        textView.setText(date);
        switchButtonEnabled(false);
        LoadOrUpdateData(Network.THIRD_GET, handler, createParams(OPERATE_CODE[0], null), OPERATE_CODE[0]);
    }


    @Override
    public void parseData(Map m) {
        super.parseData(m);
        try {
            if ((int)m.get("code") == 200 && !m.get("content").equals("null")) {
                Object json = m.get("content");
                if ((int)m.get("operateCode") == OPERATE_CODE[0]) {
                    fillItem(Parse.parseThirdJson(json.toString()));
                }else{
                    int position;
                    if (isLongClick){
                        position = longClickPosition;
                    }else {
                        position = clickPosition;
                    }
                    data.remove(position);
                    data.add(position, Parse.parseThirdJson(json.toString()).get(0));
                    adapter.updateSingleRow(gridView, position);
                    isLongClick = false;
                }
            }else {
                fillItem();
            }
        }catch (NullPointerException e){
        }
        switchButtonEnabled(true);
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
            int state = d.getState();
            if (d.getState() == 0){
                state = 1;
            }else {
                state = -state;
            }
            String[] s4 = new String[]{"state", String.valueOf(state)};
            params.add(s1);
            params.add(s2);
            params.add(s3);
            params.add(s4);
        }
        return params;
    }

    public void switchButtonEnabled(boolean isSwitch){
            previousBtn.setEnabled(isSwitch);
            nextBtn.setEnabled(isSwitch);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        ThirdData d = (ThirdData) data.get(clickPosition);
        if (d.getContent().equals(s.toString())) {
            return;
        }
        List params = createParams(OPERATE_CODE[1], (ThirdData) data.get(clickPosition));
        LoadOrUpdateData(Network.THIRD_SET, handler, params, OPERATE_CODE[1]);
    }
}
