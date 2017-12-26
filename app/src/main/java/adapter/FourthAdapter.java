package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xicp.cjlhappiness.bluestart.R;
import java.util.List;
import data.FourthData;

public class FourthAdapter extends mAdapter {

    public FourthAdapter(Context context) {
        super(context);
    }

    public FourthAdapter(Context context, List<Object> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FourthHolder holder;
        FourthData fourthData = (FourthData) data.get(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.fourth_flag_item, null);
            holder = new FourthHolder();
            holder.year = (TextView) convertView.findViewById(R.id.fourth_flag_item_year);
            holder.date = (TextView) convertView.findViewById(R.id.fourth_flag_item_date);
            holder.content = (TextView) convertView.findViewById(R.id.fourth_flag_item_content);
            convertView.setTag(holder);
        }else {
            holder = (FourthHolder) convertView.getTag();
        }
        holder.year.setText(String.format("%02d", fourthData.getYear()));
        holder.date.setText(String.format("%02d%02d", fourthData.getMonth(), fourthData.getDay()));
        holder.content.setText(fourthData.getContent());
        return convertView;
    }

    class FourthHolder{
        TextView year;
        TextView date;
        TextView content;
    }
}
