package com.example.dell.nativeapp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

public class DetailActivity extends AppCompatActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {


    private BGARefreshLayout mRefreshLayout;

    public List<String> stringList = null;
    private RecyclerView mRv;
    private MyAdapter myAdapter = null;


    private int page = 1;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x00:
                    mRefreshLayout.endRefreshing();
                    break;
                case 0x01:
                    stringList.addAll(strings);
                    myAdapter.addLoadData(strings);
                    mRefreshLayout.endLoadingMore(); //结束上拉加载更多
                    break;
            }


        }
    };
    private List<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mRv = this.findViewById(R.id.mRv);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_modulename_refresh);

        mRv = this.findViewById(R.id.mRv);

        stringList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            stringList.add("00" + i);
        }
        myAdapter = new MyAdapter(this);
        mRv.setLayoutFrozen(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRv.setHasFixedSize(true);
        mRv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mRv.setLayoutManager(linearLayoutManager);
        mRv.setAdapter(myAdapter);
        myAdapter.setData(stringList);

        initRefreshLayout(mRefreshLayout);
    }


    private void initRefreshLayout(BGARefreshLayout refreshLayout) {

        // 为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        //这个是开始刷新的方法（调用这个方法后会回调这个函数）


        //比如这里是否要启用自动下拉刷新
        //如果启用 就调用函数
        // mRefreshLayout.beginRefreshing();

        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        // 设置正在加载更多时的文本
        refreshViewHolder.setLoadingMoreText("正在加载中，请稍后");
        // 设置整个加载更多控件的背景颜色资源id
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.colorAccent);
        // 设置整个加载更多控件的背景drawable资源id
        // refreshViewHolder.setLoadMoreBackgroundDrawableRes(loadMoreBackgroundDrawableRes);
        // 设置下拉刷新控件的背景颜色资源id
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.colorPrimaryDark);
        // 设置下拉刷新控件的背景drawable资源id
        // refreshViewHolder.setRefreshViewBackgroundDrawableRes(refreshViewBackgroundDrawableRes);
        // 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
        //  mRefreshLayout.setCustomHeaderView(mBanner, false);
        // 可选配置  -------------END
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

        //触发回调
        //开始刷新
        //这个回调函数是刷新的时候要做的事件逻辑代码（这里应该是要异步的处理，完成后通过handler结束刷新）

        //开启子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //子线程刷新一下
                Message msg = Message.obtain();
                msg.what = 0x00;
                try {

                    //比如 列表
                    //页码加载第一页

                    stringList.clear();//清空数据
                    for (int i = 0; i < 8; i++) {
                        stringList.add("下拉刷新后的数据"+i);
                    }
                    myAdapter.setData(stringList);
                    Thread.sleep(3000); //列表刷新数据 执行耗时操作
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        //结束刷新（最好把这个放到handler中去调用）

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                //子线程刷新一下
                Message msg = Message.obtain();
                msg.what = 0x01;
                try {

                    //比如 列表
                    //页码加载第一页
                    strings = new ArrayList<>();
                    for (int i = 0; i < 8; i++) {
                        strings.add("上拉加载更多的数据" + i);
                    }
                    //添加

                    Thread.sleep(3000); //列表刷新数据 执行耗时操作
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();


        return false;
    }
}
