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

    public ThirdAdapter(Context context, List<Object> data) {
        super(context, data);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        ThirdData thirdData = (ThirdData)data.get(i);
        if (convertView != null){
            view = convertView;
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.third_fiag_item, null);

            if (Date.getNowDayInMonth() + Date.getFirstDayInMonth() - 1 == i){
                view.setBackgroundColor(Color.RED);
            }
        }
        if (thirdData == null){
            view.setVisibility(View.INVISIBLE);
            return view;
        }else {
            view.setVisibility(View.VISIBLE);
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
