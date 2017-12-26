package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xicp.cjlhappiness.bluestart.R;
import java.util.List;

public class FirstAdapter extends mAdapter{

    public FirstAdapter(Context context, List<Object> data) {
        super(context, data);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        FirstHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.first_flag_item, null);
            holder = new FirstHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.first_flag_item_image);
            holder.time = (TextView) convertView.findViewById(R.id.first_flag_item_time);
            holder.title = (TextView) convertView.findViewById(R.id.first_flag_item_title);
            holder.content = (TextView) convertView.findViewById(R.id.first_flag_item_content);
            convertView.setTag(holder);
        }else{
            holder = (FirstHolder) convertView.getTag();
        }

        return convertView;
    }

    class FirstHolder {
        ImageView img;
        TextView time;
        TextView title;
        TextView content;
    }

}
