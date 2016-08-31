package com.github.ggggxiaolong.customview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.github.ggggxiaolong.customview.Circle.CircleFragment;
import com.github.ggggxiaolong.customview.avatar.AvatarFragment;
import com.github.ggggxiaolong.customview.basic.BaseFragment;
import com.github.ggggxiaolong.customview.shader.ShaderFragment;
import com.github.ggggxiaolong.customview.utils.Common;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    LocalBroadcastManager mBroadcastManager;
    TitleReceiver mTitleReceiver;
    private final String fragmentTag = "fragment";
    private FragmentManager mFragmentManager;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //设置toolbar与Drawer的关联
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        //设置侧滑界面menu的点击事件
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);

        //设置fragment
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.fragment, new AvatarFragment()).commit();

        mBroadcastManager = LocalBroadcastManager.getInstance(this);
        mTitleReceiver = new TitleReceiver();
        mBroadcastManager.registerReceiver(mTitleReceiver, Common.getTitleFilter());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBroadcastManager.unregisterReceiver(mTitleReceiver);
    }

    public void showFabDialog(View view) {
        new AlertDialog.Builder(MainActivity.this).setTitle("点赞")
                .setMessage("去项目地址给作者个Star，鼓励下作者୧(๑•̀⌄•́๑)૭✧")
                .setPositiveButton("好嘞", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse(getString(R.string.prompt_url));   //指定网址
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);           //指定Action
                        intent.setData(uri);                            //设置Uri
                        MainActivity.this.startActivity(intent);        //启动Activity
                    }
                })
                .show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_base: {
                mFragmentManager.beginTransaction().replace(R.id.fragment, new BaseFragment()).commit();
                break;
            }
            case R.id.nav_avatar: {
                mFragmentManager.beginTransaction().replace(R.id.fragment, new AvatarFragment()).commit();
                break;
            }
            case R.id.nav_shader: {
                mFragmentManager.beginTransaction().replace(R.id.fragment, new ShaderFragment()).commit();
                break;
            }
            case R.id.nav_circle: {
                mFragmentManager.beginTransaction().replace(R.id.fragment, new CircleFragment()).commit();
                break;
            }
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class TitleReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //广播响应事件时间超过10s就会ANR
            String title = intent.getStringExtra(Common.MSG_TITLE);
            if (!isEmpty(title)) {
                setTitle(title);
            }
        }
    }
}

