package com.example.leaninggroupapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.core.app.NotificationCompat;

import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, AlarmAttend.class);

        Bundle bundle=intent.getExtras();
        Log.d("그룹 번호",bundle.getString("group_num"));

        notificationIntent.putExtra("group_num",bundle.getString("group_num"));

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        Log.d("왔나요4","왔겠지");

        PendingIntent pendingI = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_ONE_SHOT); // 이걸로 이동?

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");

        //OREO API 26 이상에서는 채널 필요
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남


            String channelName ="알람 채널";
            String description = "정해진 시간에 알람합니다.";
            int importance = NotificationManager.IMPORTANCE_HIGH; //소리와 알림메시지를 같이 보여줌

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if (notificationManager != null) {
                // 노티피케이션 채널을 시스템에 등록
                notificationManager.createNotificationChannel(channel);
            }
        }else builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남


        builder.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())

                .setTicker("{Time to watch some cool stuff!}")
                .setContentTitle("모임 참석 확인")
                .setContentText("클릭해서 모임 참석 여부를 알려주세요")
                .setContentInfo("INFO")
                .setContentIntent(pendingI);

        if (notificationManager != null) {

            // 노티피케이션 동작시킴
            notificationManager.notify(1234, builder.build());

        }
    }
}
