package frag;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xicp.cjlhappiness.bluestart.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import adapter.ThirdAdapter;
import data.ThirdData;
import util.Date;
import util.Network;
import util.Parse;

//第3个Fragment，小红花
public class ThirdFragment extends mFragment implements View.OnClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,
        SwipeRefreshLayout.OnRefreshListener{

    private List dtData;
    private View view;
    private GridView gridView;
    private TextView textView;
    private EditText editText;
    private Button submitBtn;
    private Button previousBtn, nextBtn;
    private LinearLayout submitLayout;
    private ThirdAdapter fullAdapter, nullAdapter;
    private int selectMonth, selectPosition;

    //加载日历，更新小红花，更新留言
    private final static int[] OPERATE_CODE = new int[]{0x100, 0x200, 0x300};

    private static final String[] THIRD_MESSAGE = new String[]{
            "小红花已经成功加载了哟潘潘老婆~",
            "啊哦网络似乎开小差了潘潘老婆再试试~",
            "潘潘老婆小红花状态已经成功更新了哟~",
            "潘潘老婆留言状态已经成功更新了哟~",
            "没有这个月的小红花数据哟潘潘老婆~"};

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

    @Override
    public void initView(){
        super.initView();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.third_swipe);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setProgressViewEndTarget(true, 100);
        swipeRefreshLayout.setSize(1);
        swipeRefreshLayout.setOnRefreshListener(this);
        textView = (TextView) view.findViewById(R.id.third_title);
        editText = (EditText) view.findViewById(R.id.third_content);
        previousBtn = (Button) view.findViewById(R.id.third_previous);
        previousBtn.setOnClickListener(this);
        nextBtn = (Button) view.findViewById(R.id.third_next);
        nextBtn.setOnClickListener(this);
        submitBtn = (Button) view.findViewById(R.id.third_submit);
        submitBtn.setOnClickListener(this);
        gridView = (GridView) view.findViewById(R.id.third_grid);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(this);
        submitLayout = (LinearLayout) view.findViewById(R.id.third_submit_layout);
        switchButtonEnabled(false);
    }

    @Override
    public void initData(){
        super.initData();
        textView.setText(Date.getDateString()[0]);
        dtData = new ArrayList();
        fullAdapter = new ThirdAdapter(getActivity(), dtData);
        gridView.setAdapter(fullAdapter);
        selectPosition = Date.getNowDayInMonth() + Date.getFirstDayInMonth() - 1;
        selectMonth = 0;
        List requestParams = createRequestParams(OPERATE_CODE[0], null);
        Map callBackParams = createCallBackParams(OPERATE_CODE[0], -1);
        loadOrUpdateData(Network.THIRD_GET, handler, requestParams, callBackParams);
    }

    private void fillItem(List list){
        if (dtData.size() != 0) {
            dtData.clear();
        }

        int firstDay = Date.getFirstDayInMonth(selectMonth);
        if (firstDay != 7){
            for (int i = 0 ; i< firstDay; i++){
                dtData.add(null);
            }
        }

        dtData.addAll(list);

        try {
            if (selectMonth == 0) {
                int nowDay = Date.getNowDayInMonth() + firstDay - 1;
                editText.setText(((ThirdData) dtData.get(nowDay)).getContent());
            } else {
                int nowDay = firstDay - 1;
                editText.setText(((ThirdData) dtData.get(nowDay)).getContent());
            }
        } catch (NullPointerException e){

        }

        fullAdapter.setSelectMonth(selectMonth);
        gridView.setAdapter(fullAdapter);
        fullAdapter.notifyDataSetChanged();
    }

    private void fillItem(){
        if (nullAdapter == null){
            nullAdapter = new ThirdAdapter(getActivity(), dtData, selectMonth);
        }
        if (dtData.size() != 0) {
            dtData.clear();
        }

        int firstDay = Date.getFirstDayInMonth(selectMonth);

        if (firstDay != 7) {
            for (int i = 0; i < firstDay; i++) {
                dtData.add(null);
            }
        }
        for (int i = 0; i < Date.getDayCountInMonth(selectMonth); i++) {
            dtData.add(null);
        }
        nullAdapter.setSelectMonth(selectMonth);
        gridView.setAdapter(nullAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < Date.getFirstDayInMonth(selectMonth)){
            return;
        }
        parent.getChildAt(selectPosition).setBackgroundColor(Color.alpha(0));
        view.setBackgroundColor(Color.argb(75, 255, 0, 0));
        selectPosition = position;
        if (dtData.get(position) != null){
            editText.setText(((ThirdData)dtData.get(position)).getContent());
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (dtData.get(position) != null){
            List requestParams = createRequestParams(OPERATE_CODE[1], (ThirdData) dtData.get(position));
            Map callBackParams = createCallBackParams(OPERATE_CODE[1], position);
            loadOrUpdateData(Network.THIRD_SET, handler, requestParams, callBackParams);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.third_submit) {
            submitButton(selectPosition);
            editText.setFocusable(false);
            return;
        }
        switch (v.getId()){
            case R.id.third_previous:
                selectMonth --;
                break;
            case R.id.third_next:
                selectMonth ++;
                break;
        }

        if (submitLayout.getVisibility() == View.VISIBLE) {
            submitLayout.setVisibility(View.GONE);
        }
        String date = Date.getDateString(selectMonth)[0];
        textView.setText(date);
        editText.setText(null);
        switchButtonEnabled(false);
        List requestParams = createRequestParams(OPERATE_CODE[0], null);
        Map callBackParams = createCallBackParams(OPERATE_CODE[0], -1);
        loadOrUpdateData(Network.THIRD_GET, handler, requestParams, callBackParams);
    }

    @Override
    public void parseData(Map m) {
        super.parseData(m);

        swipeRefreshLayout.setRefreshing(false);
        switchButtonEnabled(true);
        int responseCode = (int)m.get("responseCode");
        int operateCode = (int)m.get("operateCode");
        Object json = m.get("responseContent");

        try {
            if (responseCode != 200) {
                callBack.showMessage(THIRD_MESSAGE[1]);
                fillItem();
                return;
            }

            if (json.equals("null")){
                callBack.showMessage(THIRD_MESSAGE[4]);
                fillItem();
                return;
            }

            if (submitLayout.getVisibility() == View.GONE && selectMonth <= 0) {
                submitLayout.setVisibility(View.VISIBLE);
            }

            if (operateCode == OPERATE_CODE[0]) {
                List s = Parse.parseThirdJson(json.toString());
                fillItem(s);
                callBack.showMessage(THIRD_MESSAGE[0]);
                return;
            }

            ThirdData positionData = (ThirdData)(Parse.parseThirdJson(json.toString()).get(0));
            int position = (int) m.get("selectItem");
            if (operateCode == OPERATE_CODE[1]){
                ((ThirdData)dtData.get(position)).setState(positionData.getState());
                callBack.showMessage(THIRD_MESSAGE[2]);
            }else if (operateCode == OPERATE_CODE[2]){
                ((ThirdData)dtData.get(position)).setContent(positionData.getContent());
                callBack.showMessage(THIRD_MESSAGE[3]);
            }
            fullAdapter.updateSingleRow(gridView, position);
        }
        catch (NullPointerException e){

        }
    }

    public List createRequestParams(int operateCode, ThirdData d){
        List params = new ArrayList();

        if (operateCode == OPERATE_CODE[0]){
            String[] s1 = new String[]{"date", Date.getDateString(selectMonth)[1]};
            params.add(s1);
            return params;
        }

        String[] s1, s2, s3;

        if (operateCode == OPERATE_CODE[1]){
            int state = d.getState();
            if (state == 0){
                state = 1;
            }else {
                state = -state;
            }
            s3 = new String[]{"state", String.valueOf(state)};
        } else {
            s3 = new String[]{"content", editText.getText().toString()};
        }

        s1 = new String[]{"id", String.valueOf(d.getId())};
        s2 = new String[]{"userId", String.valueOf(d.getUserId())};
        params.add(s1);
        params.add(s2);
        params.add(s3);
        return params;
    }

    public Map createCallBackParams(int operateCode, int selectItem){
        Map map = new HashMap();
        map.put("operateCode", operateCode);
        map.put("selectItem", selectItem);
        return map;
    }

    public void switchButtonEnabled(boolean isSwitch){
            previousBtn.setEnabled(isSwitch);
            nextBtn.setEnabled(isSwitch);
    }

    public void submitButton(int clickPosition){
        ThirdData d = (ThirdData) dtData.get(clickPosition);
        List requestParams = createRequestParams(OPERATE_CODE[2], d);
        Map callBackParams = createCallBackParams(OPERATE_CODE[2], clickPosition);
        loadOrUpdateData(Network.THIRD_SET, handler, requestParams, callBackParams);
    }

    @Override
    public void onRefresh() {
        selectPosition = Date.getNowDayInMonth() + Date.getFirstDayInMonth() - 1;
        List requestParams = createRequestParams(OPERATE_CODE[0], null);
        Map callBackParams = createCallBackParams(OPERATE_CODE[0], -1);
        loadOrUpdateData(Network.THIRD_GET, handler, requestParams, callBackParams);
    }

}
