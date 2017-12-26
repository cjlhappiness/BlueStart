package frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import com.xicp.cjlhappiness.bluestart.R;
import java.util.ArrayList;
import java.util.List;
import adapter.FourthAdapter;
import data.FourthData;

//第4个Fragment
public class FourthFragment extends mFragment{

    private View view;
    private ListView listView;
    private List itemDatas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fourth_flag_main, null);
        initView();
        initData();
        buildDialog();
        return view;
    }

    public void initView(){
        listView = (ListView) view.findViewById(R.id.fourth_list);
    }

    public void initData(){
        itemDatas = new ArrayList();
        for (int i =0; i < 6; i++){
            int year = i + 1;
            int month = i + 1;
            int day = i + 10;
            String content = "今天是：" + String.format("%02d月%02d日",year, day);
            FourthData itemData = new FourthData(0, 0, year, month, day, content);
            itemDatas.add(itemData);
        }
        FourthAdapter adapter = new FourthAdapter(getActivity(), itemDatas);
        listView.setAdapter(adapter);
    }



    private void buildDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.FourthDialog).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        window.setContentView(R.layout.fourth_glag_dialog);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (display.getWidth() * 0.8);
        window.setAttributes(lp);
    }

}
