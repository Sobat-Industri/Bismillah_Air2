package com.example.bismillah_air;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Area;
import com.anychart.core.ui.Crosshair;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.HoverMode;
import com.anychart.enums.MarkerType;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.graphics.vector.Stroke;
import com.example.bismillah_air.Utility.NetworkChangeListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class InformationActivity extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        sidebar();
    }

    private void sidebar() {
        final DrawerLayout drawerlayout = findViewById(R.id.drawerlayout);

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
                        Intent mtintent = new Intent(InformationActivity.this, MainActivity.class);
                        startActivity(mtintent);
                        finish();
                        return true;
                    case R.id.information:
                        return true;
                    case R.id.history:
                        Intent mtintent2 = new Intent(InformationActivity.this, HistoryActivity.class);
                        startActivity(mtintent2);
                        finish();
                        return true;
                    case R.id.grafik:
                        Intent mtintent1 = new Intent(InformationActivity.this, GraphActivity.class);
                        startActivity(mtintent1);
                        finish();
                        return true;
                    default:
                        return true;
                }
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

}