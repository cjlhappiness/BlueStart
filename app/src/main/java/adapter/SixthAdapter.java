package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.xicp.cjlhappiness.bluestart.R;
import java.util.List;
import data.SixthItemData;

public class SixthAdapter extends mAdapter{

    private static final int[] IMAGE_RESOURCES = new int[]{R.mipmap.game_one, R.mipmap.game_two,
                                                            R.mipmap.game_three, R.mipmap.ic_ali};

    public SixthAdapter(Context context, List<Object> data) {
        super(context, data);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        SixthHolder holder;
        SixthItemData itemData = (SixthItemData) data.get(i);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.sixth_fiag_item, null);
            holder = new SixthHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.sixth_flag_item_background);
            convertView.setTag(holder);

        }else {
            holder = (SixthHolder) convertView.getTag();
        }
        if (!itemData.isClickable()){
            holder.img.setImageResource(IMAGE_RESOURCES[itemData.getImageResource()]);
        }else {
            holder.img.setImageResource(IMAGE_RESOURCES[3]);
        }

        return convertView;
    }

    class SixthHolder {
        ImageView img;
    }

}