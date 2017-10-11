package com.github.ggggxiaolong.customview.trapezoid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.ggggxiaolong.customview.R;
import com.github.ggggxiaolong.customview.utils.Common;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author mrtan on 10/11/17.
 */

public class TrapezoidFragment extends Fragment {

  private TrapezoidView mView;
  private int index = 0;
  private Random mRandom;
  private ArrayList<TrapezoidBean> mList;
  private int[] colorArray = {
      Color.BLACK, Color.DKGRAY, Color.GRAY, Color.LTGRAY, Color.RED, Color.GREEN, Color.BLUE,
      Color.YELLOW, Color.CYAN, Color.MAGENTA
  };

  public static TrapezoidFragment newInstance() {
    return new TrapezoidFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mRandom = new Random();
    mList = new ArrayList<>();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_trapezoid, container, false);
    mView = view.findViewById(R.id.view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    for (int i = 0; i < 7; i++) {
      mList.add(new TrapezoidBean("标题" + String.valueOf(index++), colorArray[index % 9],
          mRandom.nextInt(100)));
    }
    mView.setItems(mList);
    mView.invalidate();

    setTitle(getString(R.string.action_trapezoid));
  }

  private void setTitle(String title) {
    LocalBroadcastManager instance = LocalBroadcastManager.getInstance(getContext());
    Intent intent = new Intent(Common.MSG_TITLE);
    intent.putExtra(Common.MSG_TITLE, title);
    instance.sendBroadcast(intent);
  }
}
