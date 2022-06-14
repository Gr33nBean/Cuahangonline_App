package com.example.cuahangthietbionline.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.activity.MainActivity;
import com.example.cuahangthietbionline.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {

    Context context;
    ArrayList<Giohang> arraygiohang;


    public GiohangAdapter(Context context, ArrayList<Giohang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txttengiohang,txtgiagiohang;
        public ImageView imggiohang;
        public Button btnminus,btnvalues,btnplus;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txttengiohang= convertView.findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagiohang=convertView.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang= convertView.findViewById(R.id.imageviewgiohang);
            viewHolder.btnminus= convertView.findViewById(R.id.buttonminus);
            viewHolder.btnplus=convertView.findViewById(R.id.buttonplus);
            viewHolder.btnvalues=convertView.findViewById(R.id.buttonvalues);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp())+" Đ");
        Picasso.get().load(giohang.getHinhsp()).placeholder(R.drawable.noimage).error(R.drawable.errorimage).into(viewHolder.imggiohang);
        viewHolder.btnvalues.setText(Integer.toString(giohang.getSoluongsp()));
        int sl=Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if (sl>=10)
        {
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }else if (sl<=1)
        {
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else if (sl>=1)
        {
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }

        viewHolder.btnplus.setOnClickListener(v -> {
            int slmoinhat = Integer.parseInt(viewHolder.btnvalues.getText().toString())+1;
            int slht=MainActivity.Companion.getManggiohang().get(position).getSoluongsp();
            long giaht=MainActivity.Companion.getManggiohang().get(position).getGiasp();
            MainActivity.Companion.getManggiohang().get(position).setSoluongsp(slmoinhat);
            long giamoinhat=(giaht*slmoinhat)/slht;
            MainActivity.Companion.getManggiohang().get(position).setGiasp(giamoinhat);
            DecimalFormat decimalFormat1 =new DecimalFormat("###,###,###");
            viewHolder.txtgiagiohang.setText(decimalFormat1.format(giamoinhat)+" Đ");
            com.example.cuahangthietbionline.activity.Giohang.Companion.EvenUltil();
            if (slmoinhat>9)
            {
                viewHolder.btnplus.setVisibility(View.INVISIBLE);
                viewHolder.btnminus.setVisibility(View.VISIBLE);
                viewHolder.btnvalues.setText(String.valueOf(slmoinhat));
            }
            else {
                viewHolder.btnplus.setVisibility(View.VISIBLE);
                viewHolder.btnminus.setVisibility(View.VISIBLE);
                viewHolder.btnvalues.setText(String.valueOf(slmoinhat));
            }
        });

        viewHolder.btnminus.setOnClickListener(v -> {
            int slmoinhat = Integer.parseInt(viewHolder.btnvalues.getText().toString())-1;
            int slht=MainActivity.Companion.getManggiohang().get(position).getSoluongsp();
            long giaht=MainActivity.Companion.getManggiohang().get(position).getGiasp();
            MainActivity.Companion.getManggiohang().get(position).setSoluongsp(slmoinhat);
            long giamoinhat=(giaht*slmoinhat)/slht;
            MainActivity.Companion.getManggiohang().get(position).setGiasp(giamoinhat);
            DecimalFormat decimalFormat1 =new DecimalFormat("###,###,###");
            viewHolder.txtgiagiohang.setText(decimalFormat1.format(giamoinhat)+" Đ");
            com.example.cuahangthietbionline.activity.Giohang.Companion.EvenUltil();
            if (slmoinhat<2)
            {
                viewHolder.btnminus.setVisibility(View.INVISIBLE);
                viewHolder.btnplus.setVisibility(View.VISIBLE);
                viewHolder.btnvalues.setText(String.valueOf(slmoinhat));
            }
            else {
                viewHolder.btnplus.setVisibility(View.VISIBLE);
                viewHolder.btnminus.setVisibility(View.VISIBLE);
                viewHolder.btnvalues.setText(String.valueOf(slmoinhat));
            }
        });

        return convertView;
    }
}
