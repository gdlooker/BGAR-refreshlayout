package com.example.dell.nativeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public List<String> stringList = null;
    private RecyclerView mRv;
    private MyAdapter myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRv = this.findViewById(R.id.mRv);

        stringList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            stringList.add("00" + i);
        }
        myAdapter = new MyAdapter(this);
        mRv.setLayoutFrozen(false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRv.setHasFixedSize(true);
        mRv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mRv.setLayoutManager(linearLayoutManager);
        mRv.setAdapter(myAdapter);
        myAdapter.setData(stringList);

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemSelected(List<String> stringList, int position) {
                Log.i("chent","item="+stringList.get(position));
                startActivity(new Intent(MainActivity.this,DetailActivity.class));
            }
        });

    }

}
