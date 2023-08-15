package com.example.dubaothoitiet2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThoiTiet> arr;

    public CustomAdapter(Context context, ArrayList<ThoiTiet> arr) {
        this.context = context;
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_lv,null);



        ThoiTiet thoiTiet = arr.get(i);

        TextView txtDay = view.findViewById(R.id.ngay);
       // TextView txtStatus = view.findViewById(R.id.trangthai);
        ImageView imgIcon = view.findViewById(R.id.icon_day);
        TextView txtmin= view.findViewById(R.id.min);
        TextView txtmax= view.findViewById(R.id.max);

        txtDay.setText(thoiTiet.Day);
     //   txtStatus.setText(thoiTiet.Status);
        txtmin.setText(thoiTiet.Mintemp+"°C");
        txtmax.setText(thoiTiet.Maxtemp+"°C");


        // lay du lieu tu picasso
        Picasso.get().load("https:"+thoiTiet.Img).into(imgIcon);

        return view;
    }
}
