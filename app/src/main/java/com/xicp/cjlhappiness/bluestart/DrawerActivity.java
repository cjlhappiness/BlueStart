//package com.xicp.cjlhappiness.bluestart;
//
//import android.app.Fragment;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.SystemClock;
//import android.support.v7.app.AppCompatActivity;
//import android.app.AlertDialog;
//import android.app.FragmentTransaction;
//import android.app.SearchManager;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.content.res.Configuration;
//import android.net.Uri;
//import android.os.Message;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewTreeObserver;
//import android.widget.Toast;
//import android.support.v7.widget.Toolbar;
//import org.json.JSONException;
//import org.json.JSONObject;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import View.myRelativeLayout;
//import fragment.AdviceFragment;
//import fragment.ConnectFragment;
//import fragment.FlowerFragment;
//import fragment.MessageFragment;
//import fragment.SayFragment;
//import fragment.TimeFragment;
//import fragment.UpdateFragment;
//import fragment.WebFragment;
//import fragment.mFragment;
//import android.support.design.widget.NavigationView;
//
//public class DrawerActivity extends AppCompatActivity implements
//        ViewTreeObserver.OnGlobalLayoutListener,
//        NavigationView.OnNavigationItemSelectedListener, FlowerFragment.EditFocus{
//
//    private ActionBarDrawerToggle mDrawerToggle;
//    public DrawerLayout mDrawerLayout;
//    private NavigationView navigation;
//    private myRelativeLayout layout;
//    private Toolbar toolBar;
//    private Map<Integer,Object> mFragmentMap;
//    private List<Integer> indexList;
//    private long currentTime;
//    private boolean isExit;
//    private boolean flower_focus;
//    private boolean keyBoard;
//    private FlowerFragment flower;
//    private boolean[] fragmentIsCreate = new boolean[]{true, false, false, false, false, false, false, false};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
////                WindowManager.LayoutParams. FLAG_FULLSCREEN);//隐藏状态栏
//        setContentView(R.layout.main);
//
//        layout = (myRelativeLayout) findViewById(R.id.main_layout);
//        layout.getViewTreeObserver().addOnGlobalLayoutListener(this);
//        toolBar = (Toolbar) findViewById(R.id.tl_custom);
//        toolBar.setSubtitle("小星星");
//        setSupportActionBar(toolBar);
//        mFragmentMap = new HashMap<>();
//        indexList  = new ArrayList<>();
//        navigation = (NavigationView) findViewById(R.id.navigationView);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//        navigation.setNavigationItemSelectedListener(this);
//        navigation.getMenu().getItem(0).setChecked(true);
//        navigation.setItemIconTintList(null);
//
//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//                toolBar , R.string.drawer_open, R.string.drawer_close) {
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                invalidateOptionsMenu();
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                invalidateOptionsMenu();
//            }
//        };
//        mDrawerLayout.addDrawerListener(mDrawerToggle);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        initFragment();
//
//    }
//
//    private void initFragment(){
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        Fragment say = new SayFragment();
//        mFragmentMap.put( 0, say);
//        indexList.add(0);
//        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
//        transaction.add(R.id.content_frame , say).commit();
//    }
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        mDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(navigation);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
//        menu.findItem(R.id.action_refresh).setVisible(!drawerOpen);
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        switch(item.getItemId()) {
//            case R.id.action_websearch:
//                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//                intent.putExtra(SearchManager.QUERY , "我爱你");
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
//                }
//                return true;
//            case R.id.action_finish:
//                finish();
//                return true;
//            case R.id.action_refresh:
//                mFragment fragment;
//                switch (indexList.get(getLastIndex())){
//                    case 0:
//                        fragment = (SayFragment) mFragmentMap.get(0);
//                        break;
//                    case 1:
//                        fragment = (AdviceFragment) mFragmentMap.get(1);
//                        break;
//                    case 2:
//                        fragment = (FlowerFragment) mFragmentMap.get(2);
//                        break;
//                    case 3:
//                        fragment = (MessageFragment) mFragmentMap.get(3);
//                        break;
//                    case 5:
//                        fragment = (WebFragment) mFragmentMap.get(5);
//                        break;
//                    case 7:
//                        fragment = (UpdateFragment) mFragmentMap.get(7);
//                        break;
//                    default:
//                        return true;
//                }
//                fragment.refreshData();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void createAndShowFragment(int position){
//        if (indexList.get(getLastIndex()) == position){return;}
//        mFragment fragment;
//        switch (position){
//            case 0:
//                if (mFragmentMap.get(position) == null){
//                    fragment = new SayFragment();
//                    mFragmentMap.put(position , fragment);
//                }
//                break;
//            case 1:
//                if (mFragmentMap.get(position) == null) {
//                    fragment = new AdviceFragment();
//                    mFragmentMap.put(position, fragment);
//                }
//                break;
//            case 2:
//                if (mFragmentMap.get(position) == null) {
//                    fragment = new FlowerFragment();
//                    mFragmentMap.put(position, fragment);
//                    flower = (FlowerFragment) fragment;
//                }
//                break;
//            case 3:
//                if (mFragmentMap.get(position) == null){
//                    fragment = new MessageFragment();
//                    mFragmentMap.put(position , fragment);
//                }
//                break;
//            case 4:
//                if (mFragmentMap.get(position) == null){
//                    fragment = new TimeFragment();
//                    mFragmentMap.put(position , fragment);
//                }
//                break;
//            case 5:
//                if (mFragmentMap.get(position) == null){
//                    fragment = new WebFragment();
//                    mFragmentMap.put(position , fragment);
//                }
//                break;
//            case 6:
//                if (mFragmentMap.get(position) == null){
//                    fragment = new ConnectFragment();
//                    mFragmentMap.put(position , fragment);
//                }
//                break;
//            case 7:
//                if (mFragmentMap.get(position) == null){
//                    fragment = new UpdateFragment();
//                    mFragmentMap.put(position , fragment);
//                }
//                break;
//        }
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
//        transaction.hide((Fragment) mFragmentMap.get(getNowPosition()));
//
//        if (!fragmentIsCreate[position]){
//            transaction.add(R.id.content_frame,(Fragment) mFragmentMap.get(position))
//                    .addToBackStack(null);
//            fragmentIsCreate[position] = true;
//        }else {
//            transaction.show((Fragment) mFragmentMap.get(position));
//        }
//        transaction.commit();
//        indexList.add(position);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//            if (flower_focus){
//                return true;
//            }
//            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
//                mDrawerLayout.closeDrawer(Gravity.LEFT);
//                return true;
//            }
//            if (indexList.size() == 1 || getNowPosition() == 0){
//                if (SystemClock.elapsedRealtime() - currentTime >=  1 * 1000){isExit = false;}
//                if (isExit){
//                    finish();
//                }else{
//                    Toast.makeText(this, "老婆大人再按一次你就要离开我了。", Toast.LENGTH_SHORT).show();
//                    currentTime = SystemClock.elapsedRealtime();
//                    isExit = true;
//                }
//                return true;
//            }
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
//            Fragment nowFragment = (Fragment) mFragmentMap.get(getNowPosition());
//            Fragment previousFragment =(Fragment) mFragmentMap.get(getPreviousPosition());
//            transaction.hide(nowFragment)
//                    .show(previousFragment).commit();
//            navigation.getMenu().getItem(getPreviousPosition()).setChecked(true);
//            indexList.remove(getLastIndex());
//        }
//        return true;
//    }
//
//    private int getPreviousPosition(){
//        return indexList.get(getLastIndex() -1);
//    }
//
//    private int getNowPosition(){
//        return indexList.get(getLastIndex());
//    }
//
//    private int getLastIndex(){
//        return indexList.size() - 1;
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        int position = 0;
//        switch (item.getItemId()) {
//            case R.id.navigation_item_1:
//                position = 0;
//                break;
//            case R.id.navigation_item_2:
//                position = 1;
//                break;
//            case R.id.navigation_item_3:
//                position = 2;
//                break;
//            case R.id.navigation_item_4:
//                position = 3;
//                break;
//            case R.id.navigation_item_5:
//                position = 4;
//                break;
//            case R.id.navigation_item_6:
//                position = 5;
//                break;
//            case R.id.navigation_item_7:
//                position = 6;
//                break;
//            case R.id.navigation_item_8:
//                position = 7;
//                break;
//        }
//        item.setChecked(true);
//        createAndShowFragment(position);
//        mDrawerLayout.closeDrawers();
//        return false;
//    }
//
//    @Override
//    public void focus(boolean f) {
//        flower_focus = f;
//    }
//
//    @Override
//    public void onGlobalLayout() {
//        int heightDiff = layout.getRootView().getHeight()- layout.getHeight();
//        if(heightDiff >100){
//            keyBoard = true;
//        }else {
//            if (keyBoard && flower_focus){
//                flower.setFocus();
//                keyBoard = false;
//            }
//        }
//    }
//}