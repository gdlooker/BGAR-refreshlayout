package com.example.dell.nativeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/3/8.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context = null;
    LayoutInflater mLayoutInflater = null;
    List<String> stringList = null;

    public MyAdapter(Context context) {
        this.context = context;
        // mLayoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutInflater = LayoutInflater.from(context);
        //stringList=new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mLayoutInflater == null) {
            return null;
        }
        View itemView = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.i("chent", "position=" + position);
        if (holder instanceof MyViewHolder) {

            ((MyViewHolder) holder).textView.setText(stringList.get(position));
            ((MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (mOnItemClickListener != null) {
                        Toast.makeText(context, "不为null点击了", Toast.LENGTH_SHORT).show();
                        Log.i("chent", "onclick");
                        mOnItemClickListener.onItemSelected(stringList, position);

                    }else{
                        Toast.makeText(context, "为null点击了", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (stringList == null) {
            return 0;
        }
        return stringList.size();
    }


    public void setData(List<String> strings) {
        if (strings == null) {
            return;
        }
        this.stringList = strings;
        this.notifyDataSetChanged();
    }


    public void addLoadData(List<String> strings){
        if(strings==null){
            return ;
        }
        //
        this.stringList.addAll(strings);
        this.notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView = null;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    OnItemClickListener mOnItemClickListener = null;

    //
    interface OnItemClickListener {
        void onItemSelected(List<String> stringList, int position);
    }

    /**
     * 设置监听事件
     *
     * @param mOnItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
