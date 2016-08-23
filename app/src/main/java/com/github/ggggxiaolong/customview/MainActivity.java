package com.github.ggggxiaolong.customview;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.ggggxiaolong.customview.view.CustomView;
import com.github.ggggxiaolong.customview.view.DrawHelper;

public class MainActivity extends AppCompatActivity {

    private CustomView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = (CustomView) findViewById(R.id.customView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_arc:{
                mView.translate(new DrawHelper.DrawArc());
                return true;
            }
            case R.id.menu_point:{
                mView.translate(new DrawHelper.DrawPoint());
                return true;
            }
            case R.id.menu_pie:{
                mView.translate(new DrawHelper.DrawPie());
                return true;
            }
            case R.id.menu_circle:{
                mView.translate(new DrawHelper.DrawCircle());
                return true;
            }
            case R.id.menu_lines:{
                mView.translate(new DrawHelper.DrawLines());
                return true;
            }
            case R.id.menu_oval:{
                mView.translate(new DrawHelper.DrawOval());
                return true;
            }
            case R.id.menu_rectangle:{
                mView.translate(new DrawHelper.DrawRect());
                return true;
            }
            case R.id.menu_ring:{
                mView.translate(new DrawHelper.DrawRing());
                return true;
            }
            case R.id.menu_roundRect:{
                mView.translate(new DrawHelper.DrawRoundRect());
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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
}

