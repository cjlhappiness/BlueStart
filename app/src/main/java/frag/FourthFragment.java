package frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.xicp.cjlhappiness.bluestart.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import adapter.FourthAdapter;
import data.FourthData;
import util.Network;
import util.Parse;

//第4个Fragment
public class FourthFragment extends mFragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener{

    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ImageButton imageBtn;
    private List itemDatas;
    private FourthAdapter adapter;

    //加载时光轴，添加时光轴，删除时光轴，修改时光轴
    private final static int[] OPERATE_CODE = new int[]{100, 200, 300, 400};

    private final static String[] FOURTH_MESSAGE = new String[]{
            "潘潘老婆时光轴已经加载完成啦~",
            "潘潘老婆你成功添加了一条时光轴~",
            "潘潘老婆你成功删除了一条时光轴~",
            "潘潘老婆你成功修改了一条时光轴~",
            "哦哟好像还没有时光轴数据哟！~",
            "网络出错了潘潘老婆再试一次吧~"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fourth_flag_main, null);
        initView();
        initData();
        return view;
    }

    public void initView(){
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fourth_swipe);
        listView = (ListView) view.findViewById(R.id.fourth_list);
        imageBtn = (ImageButton) view.findViewById(R.id.fourth_add);
        imageBtn.setOnClickListener(this);
    }

    public void initData(){
        itemDatas = new ArrayList();
        adapter = new FourthAdapter(getActivity(), itemDatas);
        listView.setAdapter(adapter);
        onRefresh();
    }

    private void refreshItem(){
        adapter.notifyDataSetChanged();
    }

    private void buildDialog(){
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.FourthDialog).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        LinearLayout dialogLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fourth_flag_dialog, null);
        window.setContentView(dialogLayout);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (display.getWidth() * 0.8);
        window.setAttributes(lp);
        ImageView dialogCancel = (ImageView) dialogLayout.findViewById(R.id.fourth_dialog_cancel);
        ImageView dialogCancelConfirm = (ImageView) dialogLayout.findViewById(R.id.fourth_dialog_confirm);
        final EditText dialogEdit = (EditText) dialogLayout.findViewById(R.id.fourth_dialog_edit);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        dialogCancelConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FourthData fourthData = new FourthData(0, 950125, dialogEdit.getText().toString());
                List requestParams = createRequestParams(OPERATE_CODE[1], fourthData);
                Map callBackParams = createCallBackParams(OPERATE_CODE[1]);
                loadOrUpdateData(Network.FOURTH_SET, handler, requestParams, callBackParams);
                alertDialog.cancel();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fourth_add:
                buildDialog();
                break;
        }
    }

    @Override
    public void parseData(Map m) {
        super.parseData(m);
        swipeRefreshLayout.setRefreshing(false);
        callBack.isRequestFinish();
        int operateCode = (int)m.get("operateCode");
        try {
            if ((int)m.get("responseCode") == 200){
                if (!m.get("responseContent").equals("null")) {
                    Object json = m.get("responseContent");
                    itemDatas = Parse.parseFourthJson(json.toString());
                    if (operateCode == OPERATE_CODE[0]) {
                        callBack.showMessage(FOURTH_MESSAGE[0]);
                    } else if (operateCode == OPERATE_CODE[1]) {
                        callBack.showMessage(FOURTH_MESSAGE[1]);
                    } else if (operateCode == OPERATE_CODE[2]) {
                        callBack.showMessage(FOURTH_MESSAGE[2]);
                    } else if (operateCode == OPERATE_CODE[3]) {
                        callBack.showMessage(FOURTH_MESSAGE[3]);
                    }
                }else {
                    callBack.showMessage(FOURTH_MESSAGE[4]);
                }
                refreshItem();
            }else {
                callBack.showMessage(FOURTH_MESSAGE[5]);
            }
        }catch (NullPointerException e){

        }
    }

    public List createRequestParams(int operateCode, FourthData d){
        List params = new ArrayList();

        String[] s1, s2, s3, s4, s5;
        String content = null, datetime = null;

        s1 = new String[]{"id", String.valueOf(d.getId())};
        s5 = new String[]{"operate", String.valueOf(operateCode)};
        params.add(s1);
        params.add(s5);
        if (operateCode == OPERATE_CODE[2]){
            return params;
        }

        String userId = String.valueOf(d.getUserId());

        s2 = new String[]{"userId", userId};
        params.add(s2);

        if (operateCode == OPERATE_CODE[1]) {
            content = d.getContent().split("\n", 1)[1];
            datetime = d.getContent().split("\n", 1)[0];
        }else if (operateCode == OPERATE_CODE[3]){
            content = d.getContent();
            datetime = String.format("%d-%d-%d %s", d.getYear(), d.getMonth(), d.getDay(), d.getTime());
        }

        s3 = new String[]{"datetime", datetime};
        s4 = new String[]{"content", content};

        params.add(s3);
        params.add(s4);

        return params;
    }

    public Map createCallBackParams(int operateCode){
        Map map = new HashMap();
        map.put("operateCode", operateCode);
        return map;
    }

    @Override
    public void onRefresh() {
        loadOrUpdateData(Network.FOURTH_GET, handler, null, createCallBackParams(OPERATE_CODE[0]));
    }
}
