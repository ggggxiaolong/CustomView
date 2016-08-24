package com.github.ggggxiaolong.customview.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.ggggxiaolong.customview.R;
import com.github.ggggxiaolong.customview.utils.Common;

/**
 * 基础的图形
 */
public final class BaseFragment extends Fragment {

    CustomView mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = (CustomView) inflater.inflate(R.layout.fragment_main, container, false);
        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_arc: {
                mView.translate(new DrawHelper.DrawArc());
                setTitle("弧");
                return true;
            }
            case R.id.menu_point: {
                mView.translate(new DrawHelper.DrawPoint());
                setTitle("点");
                return true;
            }
            case R.id.menu_pie: {
                mView.translate(new DrawHelper.DrawPie());
                setTitle("饼状图");
                return true;
            }
            case R.id.menu_circle: {
                mView.translate(new DrawHelper.DrawCircle());
                setTitle("圆");
                return true;
            }
            case R.id.menu_lines: {
                mView.translate(new DrawHelper.DrawLines());
                setTitle("线");
                return true;
            }
            case R.id.menu_oval: {
                mView.translate(new DrawHelper.DrawOval());
                setTitle("椭圆");
                return true;
            }
            case R.id.menu_rectangle: {
                mView.translate(new DrawHelper.DrawRect());
                setTitle("矩形");
                return true;
            }
            case R.id.menu_ring: {
                mView.translate(new DrawHelper.DrawRing());
                setTitle("圆环");
                return true;
            }
            case R.id.menu_roundRect: {
                mView.translate(new DrawHelper.DrawRoundRect());
                setTitle("圆角矩形");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTitle(String title) {
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(getContext());
        Intent intent = new Intent(Common.MSG_TITLE);
        intent.putExtra(Common.MSG_TITLE, title);
        instance.sendBroadcast(intent);
    }

}
