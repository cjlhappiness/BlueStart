package frag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import adapter.ThirdAdapter;
import data.ThirdData;
import thread.mCallBack;
import thread.mCallable;
import thread.mFutureTask;
import util.Date;
import util.Network;
import pl.droidsonroids.gif.*;
import util.Parse;

//第3个Fragment
public class ThirdFragment extends mFragment implements View.OnClickListener, mCallBack,
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
        nowSelect = nowDay = Date.getNowDayInMonth();
        selectMonth = 0;
        onRefresh(Network.THIRD_GET, this, Date.getDateString()[1]);
    }

    private void initItem(List list){
        if (data.size() != 0) {
            data.clear();
        }
        data.addAll(list);
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
        onRefresh(Network.THIRD_GET, this, Date.getDateString(selectMonth)[1]);
    }

    public void onRefresh(String url,  mCallBack c, String ...data){
        mCallable callable = new mCallable(url, data);
        futureTask = new mFutureTask<Object>(callable);
        exec.submit(futureTask);
        Map m = null;
        try {
            m = (Map) futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        aa();
        Toast.makeText(getActivity(), "onRefresh", Toast.LENGTH_SHORT).show();
    }

    public void aa(){
        Toast.makeText(getActivity(), "----", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void jsonData(Map m) {
        Log.e("jsonData2","");
//        if ((int)m.get("code") == 200) {
//            String json = (String) m.get("content");
//            initItem(Parse.parseThirdJson(json));
//        }
    }
}
