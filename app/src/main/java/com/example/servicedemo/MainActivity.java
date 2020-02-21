package com.example.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button start;
    Button stop;
    Button bind;
    Button unbind;

    ServiceConnection conn = new ServiceConnection() {
        // 当客户端与 Service连接时， 绑定会执行的操作
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder binder = (MyService.MyBinder) service;
            int process = binder.getProcess();

            // 就可以在 Activity 中获取到Service 中的耗时操作；
        }

        // 当客户端与Service 的连接丢失
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        bind = findViewById(R.id.bind);
        unbind = findViewById(R.id.unbind);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);


        ContentResolver resolver = getContentResolver();
//        resolver.query();
//        resolver.insert();
//        resolver.delete();
//        resolver.update()；

        ContentValues values = new ContentValues();
        // 字段对应 MyContentProvider的onCreate()中创建 数据库的 字段。
        values.put("name", "张三");
        values.put("age", 13);
        values.put("gender", "male");
        Uri uri = resolver.insert(Uri.parse("content://com.exmple.contentprovider"), values);
        //  就会把 insert()中追加在Uri 后面的id 解析出来，给出一个反馈。
        long id = ContentUris.parseId(uri);
        Toast.makeText(this, "新添加的 id =" +id, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, MyService.class);
        switch (v.getId()) {

            case R.id.start:

                startService(intent);
                break;
            case R.id.stop:

                stopService(intent);

                break;
            case R.id.bind:

                bindService(new Intent(this, MyService.class),
                        conn, BIND_AUTO_CREATE);
                break;
            case R.id.unbind:

                unbindService(conn);

                break;
        }


    }
}
