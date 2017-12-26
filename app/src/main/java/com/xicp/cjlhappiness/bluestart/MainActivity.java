package com.xicp.cjlhappiness.bluestart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.view.MotionEvent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import frag.EighthFragment;
import frag.FifthFragment;
import frag.FirstFragment;
import frag.FourthFragment;
import frag.NinthFragment;
import frag.SecondFragment;
import frag.SeventhFragment;
import frag.SixthFragment;
import frag.ThirdFragment;
import frag.mFragment;
import pl.droidsonroids.gif.*;
import thread.mCallBack;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , mCallBack {

    private ExecutorService es;

    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private FragmentManager fm;
    private GifImageView gifImageView;
    private GifDrawable gifDrawable;
    private List<Integer> backStackList;

    private long keyBackTime;
    private int currentIndex;
    private int userId;

    private float down;

    private static final int THREAD_POOL = 5;
    private static final String[] MENU_TEXT = new String[]{"设  置", "检查更新", "退  出"};
    private static final String[] FRAME_TAG = new String[]{"First", "Second", "Third", "Fourth",
            "Fifth", "Sixth", "Seventh", "Eighth", "Ninth"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userId = intent.getIntExtra("id",-1);

        //ActionBar的替代品Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton浮动按钮
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
//                        .setAction("Action Click", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Toast.makeText(MainActivity.this, "---", Toast.LENGTH_SHORT).show();
//                            }
//                        }).show();
//            }
//        });

        //DrawerLayout侧滑
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.getMenu().getItem(0).setChecked(true);

        gifImageView = (GifImageView) findViewById(R.id.gif_image_View);
        try {
            gifDrawable = new GifDrawable(getResources(), R.mipmap.load_gif);
            gifImageView.setImageDrawable(gifDrawable);
            gifDrawable.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mFragment fragment = createFragment(0);
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.content_frame, fragment, FRAME_TAG[0]).commit();
        backStackList = new ArrayList<>();
        backStackList.add(0);
        currentIndex = 0;

        es = Executors.newFixedThreadPool(THREAD_POOL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backStackList.clear();
    }

    //Back键的按下事件
    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else{
            if (backStackList.size() > 1){
                int position = backStackList.get(backStackList.size() - 2);
                showOrHideFlag(position, currentIndex);
                backStackList.remove(backStackList.size() - 1);
                currentIndex = position;
            }else {
                if (SystemClock.elapsedRealtime() - keyBackTime > 1500) {
                    keyBackTime = SystemClock.elapsedRealtime();
                    showMessage("老婆再按一次你就要离开我啦！");
                } else {
                    finish();
                }
            }
        }
    }


    //菜单Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    //菜单Menu的Item选择
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int position = getOptionsItemPosition(id);
        Toast.makeText(this, MENU_TEXT[position], Toast.LENGTH_SHORT).show();
        if (position == 2) finish();
        return super.onOptionsItemSelected(item);
    }

    //侧滑菜单的Item选择
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        int position = getNavigationItemPosition(id);

        if (currentIndex == position){
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        if (fm.findFragmentByTag(FRAME_TAG[position]) == null){
            mFragment fragment = createFragment(position);
            fm.beginTransaction().add(R.id.content_frame, fragment, FRAME_TAG[position]).commit();
            fm.executePendingTransactions();
        }

        showOrHideFlag(position, currentIndex);

        currentIndex = position;
        backStackList.add(position);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private int getNavigationItemPosition(int id){
        int position;
        if (id == R.id.nav_item_1){
            position = 0;
        }else if(id == R.id.nav_item_2){
            position = 1;
        }else if(id == R.id.nav_item_3){
            position = 2;
        }else if(id == R.id.nav_item_4){
            position = 3;
        }else if(id == R.id.nav_item_5){
            position = 4;
        }else if(id == R.id.nav_item_6){
            position = 5;
        }else if(id == R.id.nav_item_7){
            position = 6;
        }else {
            position = 7;
        }
        return position;
    }

    private int getOptionsItemPosition(int id){
        int position;
        if (id == R.id.action_settings){
            position = 0;
        }else if(id == R.id.action_update){
            position = 1;
        }else if (id == R.id.action_exit){
            position = 2;
        }else {
            position = 3;
        }
        return position;
    }

    private mFragment createFragment(int index){

        switch (index){
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();
            case 3:
                return new FourthFragment();
            case 4:
                return new FifthFragment();
            case 5:
                return new SixthFragment();
            case 6:
                return new SeventhFragment();
            case 7:
                return new EighthFragment();
            default:
                return new NinthFragment();
        }
    }

    private void showOrHideFlag(int show, int hide) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(fm.findFragmentByTag(FRAME_TAG[hide]));
        ft.show(fm.findFragmentByTag(FRAME_TAG[show]));
        ft.commit();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float up;
        up = 0;
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            down = ev.getX();
        }

        if (ev.getAction() == MotionEvent.ACTION_UP){
            up = ev.getX();
        }

        if (up - down > 300 && !drawer.isDrawerOpen(GravityCompat.START)){
            drawer.openDrawer(GravityCompat.START);
        }

        return super.dispatchTouchEvent(ev);
    }

    public static void startMainActivity(Activity activity, int id){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("id",id);
        activity.startActivity(intent);
    }

    @Override
    public ExecutorService getExecutorService() {
        return es;
    }

    @Override
    public void isRequestBegin(int operateCode) {
        if (operateCode == mFragment.OPERATE_CODE[0]) {
            gifImageView.setVisibility(View.VISIBLE);
            gifDrawable.start();
        }
    }

    @Override
    public void isRequestFinish() {
        if (gifImageView.getVisibility() == View.VISIBLE){
            gifImageView.setVisibility(View.INVISIBLE);
            gifDrawable.stop();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
