package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.xicp.cjlhappiness.bluestart.R;
import java.util.List;

import data.SixthItemData;

public class sixthAdapter extends mAdapter{

    private static final int[] IMAGE_RESOURCES = new int[]{R.mipmap.game_one, R.mipmap.game_two,
                                                            R.mipmap.game_three, R.mipmap.ic_ali};

    public sixthAdapter(Context context, List<Object> data) {
        super(context, data);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        SixthItemData itemData = (SixthItemData) data.get(i);
        if (convertView != null){
            view = convertView;
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.sixth_fiag_item, null);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.sixth_flag_item_background);
        if (itemData.isClick()){
            imageView.setImageResource(IMAGE_RESOURCES[itemData.getImageResource()]);
        }else {
            imageView.setImageResource(IMAGE_RESOURCES[3]);
        }

        return view;
    }
}
