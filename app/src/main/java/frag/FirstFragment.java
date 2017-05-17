package frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xicp.cjlhappiness.bluestart.MainActivity;
import com.xicp.cjlhappiness.bluestart.R;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import thread.mFutureTask;

//第1个Fragment
public class FirstFragment extends mFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_flag_main, null);
        Future future = ((MainActivity) getActivity()).getEs().submit(new mFutureTask("http://cjlhappiness.xicp.net/BlueStart/getSayJSON.php"));
        try {
            if (future.get() != null) {
                Toast.makeText(getActivity(), future.get().toString(), Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();

    }
}
