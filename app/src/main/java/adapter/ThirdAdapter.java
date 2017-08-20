package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xicp.cjlhappiness.bluestart.R;
import java.util.List;

public class ThirdAdapter extends mAdapter{

    public ThirdAdapter(Context context, List<Object> data) {
        super(context, data);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        if (convertView != null){
            view = convertView;
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.third_fiag_item, null);
        }
        TextView content = (TextView) view.findViewById(R.id.third_flag_item_content);
        content.setText((String)data.get(i));
        return view;
    }
}
