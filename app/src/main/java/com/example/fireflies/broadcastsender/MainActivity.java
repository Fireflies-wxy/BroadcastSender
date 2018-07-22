package com.example.fireflies.broadcastsender;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int STILL = 0;
    public static final int BUS = 1;
    public static final int CAR = 2;
    public static final int RUNNING = 3;
    public static final int SUBWAY = 4;
    public static final int WALKING = 5;

    private Button button;
    private TextView text;
    private int statusCode;
    private Intent intent;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        text = findViewById(R.id.text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startBroadcast();
                    }
                }).start();
            }
        });

        handler  = new Handler() {
            public void handleMessage(Message msg){
                text.setText("当前状态："+msg.what+" ");
            }
        };
    }

    public void startBroadcast(){
        final Random random = new Random();
        Timer executeSchedule = new Timer();
        executeSchedule.schedule(new TimerTask() {
            @Override
            public void run() {
                statusCode = random.nextInt(6);
                Log.i(TAG, "statuscode: "+statusCode);
                sendCode(statusCode);
            }
        }, 0, 3000);
    }

    public void sendCode(int statusCode){
        this.statusCode = statusCode;
        Message message = new Message();
        switch (statusCode){
            case 0:
                intent = new Intent("com.example.fireflies.broadcastsender.STILL");
                message.what = STILL;
                sendBroadcast(intent);
                break;
            case 1:
                intent = new Intent("com.example.fireflies.broadcastsender.BUS");
                message.what = BUS;
                sendBroadcast(intent);
                break;
            case 2:
                intent = new Intent("com.example.fireflies.broadcastsender.CAR");
                message.what = CAR;
                sendBroadcast(intent);
                break;
            case 3:
                intent = new Intent("com.example.fireflies.broadcastsender.RUNNING");
                message.what = RUNNING;
                sendBroadcast(intent);
                break;
            case 4:
                intent = new Intent("com.example.fireflies.broadcastsender.SUBWAY");
                message.what = SUBWAY;
                sendBroadcast(intent);
                break;
            case 5:
                intent = new Intent("com.example.fireflies.broadcastsender.WALKING");
                message.what = WALKING;
                sendBroadcast(intent);
                break;
            default:
                break;
        }
        handler.sendMessage(message);
    }
}
