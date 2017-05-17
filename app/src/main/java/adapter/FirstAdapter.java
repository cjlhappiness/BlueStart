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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.first_flag_item, null);
            holder = new ViewHolder();
            holder.img = (ImageView) view.findViewById(R.id.first_flag_item_image);
            holder.time = (TextView) view.findViewById(R.id.first_flag_item_time);
            holder.title = (TextView) view.findViewById(R.id.first_flag_item_title);
            holder.content = (TextView) view.findViewById(R.id.first_flag_item_content);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }
}
