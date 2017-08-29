package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class mAdapter extends BaseAdapter{

    protected Context context;
    protected List<Object> data;

    public mAdapter(Context context){
        this.context = context;
    }

    public mAdapter(Context context, List<Object> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    //刷新某一条数据
    public void updateSingleRow(AbsListView absListView, int position){
        if (absListView != null){
            int start = absListView.getFirstVisiblePosition();
            int stop = absListView.getLastVisiblePosition();
            for (int i = start; i <= stop ; i++){
                if (position == i){
                    View view = absListView.getChildAt(i - start);
                    getView(i, view, absListView);
                    break;
                }
            }
        }
    }

class ViewHolder{
    ImageView img;
    TextView time;
    TextView title;
    TextView content;
}

}
