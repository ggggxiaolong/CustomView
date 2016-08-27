package com.github.ggggxiaolong.customview.avatar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.ggggxiaolong.customview.R;

public final class AvatarFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avatar, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        Bitmap bitmap = BitmapFactory.decodeResource(container.getResources(), R.drawable.nav_header);
        image.setImageDrawable(new AvatarImageDrawable(bitmap));
        return view;
    }
}
