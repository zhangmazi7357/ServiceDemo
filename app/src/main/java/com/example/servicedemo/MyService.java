package com.example.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private String TAG = "==MYService";

    private int i;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: 服务创建");


        try {
            for (i = 1; i <= 100; i++) {
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: 服务启动");
        //
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Log.e(TAG, "onBind:  服务绑定");
        return new MyBinder();
    }

    class MyBinder extends Binder {
        // 获取自己需要的 进度监控 ;
        public int getProcess() {
            return i;
        }

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind:  服务解绑 ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {       // 和Activity 的 onDestroy() 有关系吗；
        super.onDestroy();
        Log.e(TAG, "onDestroy: 服务销毁");
    }


}
