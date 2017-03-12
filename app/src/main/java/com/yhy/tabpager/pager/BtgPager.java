package com.yhy.tabpager.pager;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yhy.tabpager.utils.ToastUtils;
import com.yhy.tpg.handler.ResultHandler;
import com.yhy.tpg.pager.TpgFragment;

import java.util.Random;

@SuppressLint("ValidFragment")
public class BtgPager extends TpgFragment {
    private boolean isFirstPage;
    private boolean isLoaded;

    private ResultHandler mResultHandler;

    public BtgPager(int position) {
        super();
        isFirstPage = position == 0;
    }

    @Override
    protected View getSuccessView() {
        TextView tv = new TextView(getContext());
        tv.setText("Btg页面加载成功");
        tv.setTextColor(Color.RED);
        tv.setTextSize(32);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    @Override
    public boolean shouldLoadDataAtFirst() {
        return isFirstPage && !isLoaded;
    }

    @Override
    protected void initData(ResultHandler handler) {
        mResultHandler = handler;

        isLoaded = true;

        getDataFromServer();
    }

    @Override
    public void reloadDate(Object... args) {
        super.reloadDate();

        String temp = "";
        if (null != args && args.length > 0 && args[0] instanceof String) {
            temp = (String) args[0];
        }
        ToastUtils.shortToast(temp + "页面重新加载数据");

        getDataFromServer();
    }

    private void getDataFromServer() {
        final Random random = new Random();
        new Thread() {
            @Override
            public void run() {
                //模拟网络加载延迟
                SystemClock.sleep(3000);

                //数据加载结束后，需要手动刷新页面状态
                int temp = random.nextInt(3);
                switch (temp) {
                    case 0:
                        mResultHandler.sendSuccessHandler();
                        break;
                    case 1:
                        mResultHandler.sendErrorHandler();
                        break;
                    case 2:
                        mResultHandler.sendEmptyHandler();
                        break;
                    default:
                        break;
                }
            }
        }.start();
    }
}