package com.example.bismillah_air;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.bismillah_air.API.InterfaceAPI;
import com.example.bismillah_air.API.Respon;
import com.example.bismillah_air.Utility.NetworkChangeListener;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    TextView setelah_filter, sebelum_filter;
    Handler handler, handler2;
    Runnable runnable, runnable2;

    public static final String NOTIFICATION_REPLY = "NotificationReply";
    public static final String CHANNNEL_ID = "SimplifiedCodingChannel";
    public static final String CHANNEL_NAME = "SimplifiedCodingChannel";
    public static final String CHANNEL_DESC = "This is a channel for Simplified Coding Notifications";

    public static final String KEY_INTENT_MORE = "keyintentmore";
    public static final String KEY_INTENT_HELP = "keyintenthelp";

    public static final int REQUEST_CODE_MORE = 100;
    public static final int REQUEST_CODE_HELP = 101;
    public static final int NOTIFICATION_ID = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawerLayout drawerlayout = findViewById(R.id.drawerlayout);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNNEL_ID, CHANNEL_NAME, importance);
            mChannel.setDescription(CHANNEL_DESC);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });

//        ChangeActivity
        t = new ActionBarDrawerToggle(this, drawerlayout, R.string.app_name, R.string.app_name);
        t.syncState();

        nv = (NavigationView)findViewById(R.id.NavigationView);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home:
                        return true;
                    case R.id.information:
                        Handler handler1 = new Handler();
                        Runnable runnable1 = new Runnable() {
                            public void run() {
                                Intent mtintent = new Intent(MainActivity.this, InformationActivity.class);
                                startActivity(mtintent);
                            }
                        };
                        handler1.postDelayed(runnable1, 1000);
                        return true;
                    case R.id.history:


                        Handler handler2 = new Handler();
                        Runnable runnable2 = new Runnable() {
                            public void run() {
                                Intent mtintent1 = new Intent(MainActivity.this, HistoryActivity.class);
                                startActivity(mtintent1);
                            }
                        };
                        handler2.postDelayed(runnable2, 1000);
                        return true;

                    case R.id.grafik:
                        Intent mtintent1 = new Intent(MainActivity.this, GraphActivity.class);
                        startActivity(mtintent1);
                        return true;
                    default:
                        return true;
                }




            }

        });

        setelah_filter = findViewById(R.id.setelah_filter);
        sebelum_filter = findViewById(R.id.sebelum_filter);




        st_filter();
        loop();
        loopNotification();

    }

    private void loop() {
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {


                st_filter();

//                String TvValue = sebelum_filter.getText().toString();
//
//                if (Integer.parseInt(TvValue) >= 50) {
//                    displayNotification();
//                }
                loop();
            }
        };
        handler.postDelayed(runnable, 10000);
    }

    private void loopNotification() {
        handler2 = new Handler();
        runnable2 = new Runnable() {
            public void run() {


                st_filter();

                String TvValue = sebelum_filter.getText().toString();

                if (Integer.parseInt(TvValue) >= 50) {
                    displayNotification();
                }
                loopNotification();
            }
        };
        handler2.postDelayed(runnable2, 30000);
    }

    private void st_filter() {
        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InterfaceAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        InterfaceAPI api = retrofit.create(InterfaceAPI.class);

        Call<Respon> call = api.rp("6");

        call.enqueue(new Callback<Respon>() {
            @Override
            public void onResponse(Call<Respon> call, Response<Respon> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                if (response.body()!= null) {
//                    pg1.setVisibility(View.INVISIBLE);
//                    pg2.setVisibility(View.INVISIBLE);
//                    setelah_filter.setVisibility(View.VISIBLE);
//                    sebelum_filter.setVisibility(View.VISIBLE);

                    if (response.body().getData().getDebuAfterFilter() != null) {
                        setelah_filter.setText(response.body().getData().getDebuAfterFilter().toString());
                        sebelum_filter.setText(response.body().getData().getDebuBeforeFilter().toString());
                    }

                }
                return;


            }

            @Override
            public void onFailure(Call<Respon> call, Throwable t) {
//                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
//        handler.removeCallbacks(runnable);
        super.onStop();
    }

    public void displayNotification() {

        //Pending intent for a notification button named More
        PendingIntent morePendingIntent = PendingIntent.getBroadcast(
                MainActivity.this,
                REQUEST_CODE_MORE,
                new Intent(MainActivity.this, NotificationReceiver.class)
                        .putExtra(KEY_INTENT_MORE, REQUEST_CODE_MORE),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

//        //Pending intent for a notification button help
//       PendingIntent helpPendingIntent = PendingIntent.getBroadcast(
//                MainActivity.this,
//                REQUEST_CODE_HELP,
//                new Intent(MainActivity.this, MainActivity.class)
//                        .putExtra(KEY_INTENT_HELP, REQUEST_CODE_HELP),
//                PendingIntent.FLAG_UPDATE_CURRENT
//        );

//
//        //We need this object for getting direct input from notification
//        RemoteInput remoteInput = new RemoteInput.Builder(NOTIFICATION_REPLY)
//                .setLabel("Please enter your name")
//                .build();
//
//
//        //For the remote input we need this action object
//        NotificationCompat.Action action =
//                new NotificationCompat.Action.Builder(android.R.drawable.ic_delete,
//                        "Reply Now...", helpPendingIntent)
//                        .addRemoteInput(remoteInput)
//                        .build();

        //Creating the notifiction builder object
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle("Kualitas Udara Buruk...")
                .setContentText("Tolong buka ruangan jika memungkinkan untuk sirkulasi udara...")
                .setAutoCancel(true)
//                .setContentIntent(helpPendingIntent)
//                .addAction(action)
//                .addAction(android.R.drawable.ic_menu_compass, "Dashboard..", morePendingIntent)
                .setContentIntent(
                        PendingIntent.getActivity(
                                this,
                                0,
                                new Intent(this.getApplicationContext(),SplashScreen.class),
                                PendingIntent.FLAG_UPDATE_CURRENT));
//                .addAction(android.R.drawable.ic_menu_directions, "Dashboard..", helpPendingIntent);




        //finally displaying the notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}