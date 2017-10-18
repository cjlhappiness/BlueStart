package frag;

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
import android.widget.GridView;
import android.widget.TextView;

import com.xicp.cjlhappiness.bluestart.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.sixthAdapter;
import data.SixthData;
import data.SixthItemData;
import util.Network;
import util.Parse;

//第6个Fragment,游戏
public class SixthFragment extends mFragment implements AdapterView.OnItemClickListener,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    private View view;
    private List itemDatas;
    private SixthData userData;
    private GridView gridView;
    private Button startBtn, stopBtn;
    private TextView sixthContent;
    private TextView fundamental;
    private sixthAdapter adapter;
    private int clickCount;
    private int[] randomNum;
    private int gameCount, winCount, lostCount, pointCount;
    private boolean isStart, isReStart;

    private static final int[] NUMBER_TIMES = new int[]{14, 30, 80};

    //加载信息，更新信息
    private final static int[] OPERATE_CODE = new int[]{0x100, 0x200};

    private static final String[] SIXTH_MESSAGE = new String[]{
            "哇塞~潘潘老婆你赢得了 %d 么么哒~",
            "啊哟~不要灰心下次一定赢~",
            "潘潘老婆你的游戏数据已经加载完成~",
            "哎呀！拉取个人数据信息失败了~",
    };

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
        view = inflater.inflate(R.layout.sixth_flag_main, null);
        initView();
        initData();
        return view;
    }

    @Override
    public void initView() {
        super.initView();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sixth_swipe);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setProgressViewEndTarget(true, 100);
        swipeRefreshLayout.setSize(1);
        swipeRefreshLayout.setOnRefreshListener(this);
        gridView = (GridView) view.findViewById(R.id.sixth_grid);
        gridView.setOnItemClickListener(this);
        startBtn = (Button) view.findViewById(R.id.sixth_start);
        startBtn.setOnClickListener(this);
        stopBtn = (Button) view.findViewById(R.id.sixth_stop);
        stopBtn.setOnClickListener(this);
        sixthContent = (TextView) view.findViewById(R.id.sixth_content);
        fundamental = (TextView) view.findViewById(R.id.sixth_fundamental);
    }

    @Override
    public void initData() {
        super.initData();
        randomNum = new int[]{0, 0, 0};
        itemDatas = new ArrayList();
        adapter = new sixthAdapter(getActivity(), itemDatas);
        gridView.setAdapter(adapter);
        userData = new SixthData(0, mFragment.USER_ID[0], 0, 0);
        updateUser(userData, mFragment.LOAD_CODE[1]);
        loadOrUpdateData(Network.SIXTH_GET, handler, createRequestParams(OPERATE_CODE[0], userData), createCallBackParams(OPERATE_CODE[0]));
        initGame();
    }

    private void initGame() {
        for (int i = 0; i < 9; i++) {
            SixthItemData itemData = new SixthItemData();
            int randomInt = (int) (Math.random() * 3);
            while (randomNum[randomInt] == 3) {
                randomInt = (int) (Math.random() * 3);
            }
            itemData.setLocation(i);
            itemData.setImageResource(randomInt);
            itemDatas.add(itemData);
            randomNum[randomInt]++;
        }
        adapter.notifyDataSetChanged();
    }

    private int buildProbability(int[] randomNum, List itemDatas, int clickNum, int position){
        if(randomNum[clickNum] > 1 || clickNum == 0){return clickNum;}
        int probability = (int)Math.pow(2, clickNum);
        int random = (int)(Math.random() * probability) + 1;
        if (random != probability){
            SixthItemData data = (SixthItemData) itemDatas.get((int) (Math.random() * 9));
            int num = data.getImageResource();
            while (clickNum == num){
                data = (SixthItemData) itemDatas.get((int) (Math.random() * 9));
                num = data.getImageResource();
            }
            ((SixthItemData) itemDatas.get(position)).setImageResource(num);
            ((SixthItemData) itemDatas.get(position)).setLocation(data.getLocation());
            data.setImageResource(clickNum);
            data.setLocation(position);
            return num;
        }
        return clickNum;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sixth_start:
                if (isReStart){initGame();}
                isReStart = true;
                isStart = true;
                startBtn.setEnabled(false);
                startBtn.setVisibility(View.GONE);
                stopBtn.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setEnabled(false);
                if (userData.getFreeCount() != 0){
                    userData.setFreeCount(userData.getFreeCount() - 1);
                }else{
                    userData.setPoint(userData.getPoint() - 10);
                    pointCount -= 10;
                }
                gameCount ++;
                loadOrUpdateData(Network.SIXTH_SET, handler, createRequestParams(OPERATE_CODE[1], userData), createCallBackParams(OPERATE_CODE[1]));
                break;
            case R.id.sixth_stop:
                isReStart = false;
                lostCount ++;
                loadOrUpdateData(Network.SIXTH_GET, handler, createRequestParams(OPERATE_CODE[0], userData), createCallBackParams(OPERATE_CODE[0]));
                cleanData();
                initGame();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SixthItemData itemData = (SixthItemData) itemDatas.get(position);
        int clickNum = itemData.getImageResource();
        if (!isStart || itemData.isClick()){
            return;
        }
        int newRandomNum = buildProbability(randomNum, itemDatas, clickNum, position);
        randomNum[newRandomNum] --;
        itemData.setClick(true);
        adapter.updateSingleRow(gridView, position);
        clickCount ++;
        isGameOver();
    }

    private void isGameOver(){
        for (int i = 0; i < randomNum.length; i++){
            if (randomNum[i] == 0){
                //成功
                winCount ++;
                pointCount += NUMBER_TIMES[i];
                isReStart = false;
                userData.setPoint(userData.getPoint() + NUMBER_TIMES[i]);
                loadOrUpdateData(Network.SIXTH_SET, handler, createRequestParams(OPERATE_CODE[1], userData), createCallBackParams(OPERATE_CODE[1]));
                callBack.showMessage(String.format(SIXTH_MESSAGE[0], NUMBER_TIMES[i]));
                swipeRefreshLayout.setEnabled(true);
                cleanData();
                return;
            }
        }

        if (clickCount == 5){
            //失败
            lostCount ++;
            isReStart = false;
            loadOrUpdateData(Network.SIXTH_GET, handler, createRequestParams(OPERATE_CODE[0], userData), createCallBackParams(OPERATE_CODE[0]));
            callBack.showMessage(SIXTH_MESSAGE[1]);
            swipeRefreshLayout.setEnabled(true);
            cleanData();
            return;
        }
    }

    private void cleanData(){
        itemDatas.clear();
        clickCount = 0;
        for (int i = 0; i < randomNum.length; i++){
            randomNum[i] = 0;
        }
        isStart = false;
//        startBtn.setText("开始");
        startBtn.setVisibility(View.VISIBLE);
        stopBtn.setVisibility(View.GONE);
    }

    public List createRequestParams(int operateCode, SixthData d){
        List params = new ArrayList();
        String[] s1 = new String[]{"userId", String.valueOf(d.getUserId())};
        params.add(s1);
        if (operateCode == OPERATE_CODE[1]){
            String[] s2 = new String[]{"point", String.valueOf(d.getPoint())};
            String[] s3 = new String[]{"freeCount", String.valueOf(d.getFreeCount())};
            params.add(s2);
            params.add(s3);
        }
        return params;
    }

    public Map createCallBackParams(int operateCode){
        Map map = new HashMap();
        map.put("operateCode", operateCode);
        return map;
    }

    @Override
    public void parseData(Map m) {
        super.parseData(m);
        swipeRefreshLayout.setRefreshing(false);
        callBack.isRequestFinish();
        try {
            if ((int)m.get("responseCode") == 200){
                if (!m.get("responseContent").equals("null")){
                    Object json = m.get("responseContent");
                    userData = Parse.parseSixthJson(json.toString());
                    updateUser(userData, mFragment.LOAD_CODE[0]);
                    if ((int)m.get("operateCode") == OPERATE_CODE[0]) {
                        callBack.showMessage(SIXTH_MESSAGE[2]);
                    }
                }
                return;
            }
        }catch (NullPointerException e){
        }
        updateUser(userData, mFragment.LOAD_CODE[1]);
        callBack.showMessage(SIXTH_MESSAGE[3]);
    }

    private void updateUser(SixthData d, int code){
        if (isReStart){
            return;
        }
        if (code == mFragment.LOAD_CODE[0]){
            startBtn.setEnabled(true);
        }
        sixthContent.setText(String.valueOf(d.getPoint()));
        startBtn.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setEnabled(true);
        startBtn.setText("开始"+String.format("(%d)", d.getFreeCount()));
        fundamental.setText(String.format("游戏%d次，失败%d次，成功%d次，赢得%d么么哒。", gameCount, lostCount, winCount, pointCount));
    }

    @Override
    public void onRefresh() {
        loadOrUpdateData(Network.SIXTH_GET, handler, createRequestParams(OPERATE_CODE[0], userData), createCallBackParams(OPERATE_CODE[0]));
    }
}
