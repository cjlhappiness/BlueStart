package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xicp.cjlhappiness.bluestart.R;
import java.util.List;
import data.ThirdData;
import util.Date;

public class ThirdAdapter extends mAdapter{

    private int selectMonth;

    public ThirdAdapter(Context context, List<Object> data) {
        super(context, data);
    }

    public ThirdAdapter(Context context, List<Object> data, int selectMonth) {
        super(context, data);
        this.selectMonth = selectMonth;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        ThirdData thirdData = (ThirdData)data.get(i);
        if (convertView != null){
            view = convertView;
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.third_fiag_item, null);
        }
        if (thirdData == null){
            int firstDay = Date.getFirstDayInMonth(selectMonth);
            if (selectMonth != 0){
                TextView content = (TextView) view.findViewById(R.id.third_flag_item_content);
                if (firstDay == 7){
                    content.setText(String.valueOf(i + 1));
                }else if (i >= firstDay) {
                    content.setText(String.valueOf(i - firstDay + 1));
                }else {
                    view.setVisibility(View.INVISIBLE);
                }
            }else {
                view.setVisibility(View.INVISIBLE);
            }

            return view;
        }else {
            if (Date.getNowDayInMonth() + Date.getFirstDayInMonth() - 1 == i){
                view.setBackgroundColor(Color.RED);
            }
        }
        ImageView background = (ImageView) view.findViewById(R.id.third_flag_item_background);
        TextView content = (TextView) view.findViewById(R.id.third_flag_item_content);
        int state = thirdData.getState();
        String day = thirdData.getDay();
        if (state == 1){

        }else if (state == -1){

        }else {

        }
        content.setText(String.valueOf(day));
        return view;
    }
}
