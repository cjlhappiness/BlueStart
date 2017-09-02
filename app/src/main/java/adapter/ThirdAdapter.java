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

        int firstDay = Date.getFirstDayInMonth(selectMonth);

        TextView content = (TextView) view.findViewById(R.id.third_flag_item_content);

        if (i < firstDay && firstDay != 7) {
            view.setVisibility(View.INVISIBLE);
            return view;
        }

        if (firstDay == 7){firstDay = 0;}

        content.setText(String.valueOf(i - firstDay + 1));

        if (data.get(i) == null){
            return view;
        }

        ImageView background = (ImageView) view.findViewById(R.id.third_flag_item_background);
        int state = thirdData.getState();
        if (Date.getNowDayInMonth() + Date.getFirstDayInMonth() - 1 == i && selectMonth == 0){
            content.setTextColor(Color.RED);
            view.setBackgroundColor(Color.argb(75, 255, 0, 0));
        }
        if (state == 1){
            background.setImageResource(R.mipmap.flower_good);
        }else if (state == -1){
            background.setImageResource(R.mipmap.flower_bad);
        }
        return view;
    }

    public void setSelectMonth(int selectMonth){
        this.selectMonth = selectMonth;
    }
}
