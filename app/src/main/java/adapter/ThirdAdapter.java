package adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
        ThirdHolder holder;
        ThirdData thirdData = (ThirdData)data.get(i);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.third_fiag_item, null);
            holder = new ThirdHolder();
            holder.content = (TextView) convertView.findViewById(R.id.third_flag_item_content);
            convertView.setTag(holder);
        }else {
            holder = (ThirdHolder) convertView.getTag();
        }

        int firstDay = Date.getFirstDayInMonth(selectMonth);

        if (i < firstDay && firstDay != 7) {
            convertView.setVisibility(View.INVISIBLE);
            return convertView;
        }

        if (firstDay == 7){firstDay = 0;}

        holder.content.setText(String.valueOf(i - firstDay + 1));

        if (Date.getNowDayInMonth() + firstDay - 1 == i && selectMonth == 0){
            holder.content.setTextColor(Color.RED);
            convertView.setBackgroundColor(Color.argb(75, 255, 0, 0));
        }

        if (data.get(i) == null){
            return convertView;
        }

        ImageView background = (ImageView) convertView.findViewById(R.id.third_flag_item_background);
        int state = thirdData.getState();

        if (state == 1){
            background.setImageResource(R.mipmap.flower_good);
        }else if (state == -1){
            background.setImageResource(R.mipmap.flower_bad);
        }
        return convertView;
    }

    public void setSelectMonth(int selectMonth){
        this.selectMonth = selectMonth;
    }

    class ThirdHolder {
        TextView content;
    }

}


