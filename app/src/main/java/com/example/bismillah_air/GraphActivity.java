package com.example.bismillah_air;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Area;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.ui.Crosshair;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.HoverMode;
import com.anychart.enums.MarkerType;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.graphics.vector.Stroke;
import com.example.bismillah_air.API.History;
import com.example.bismillah_air.API.InterfaceAPI;
import com.example.bismillah_air.Adapter.GrafikAdapter;
import com.example.bismillah_air.Utility.NetworkChangeListener;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GraphActivity extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private RecyclerView recyclerView;
    private List<History> arraylist;
    private List<CustomDataEntry> seriesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        arraylist = new ArrayList<>();
        sidebar();
        history();
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
                        Intent mtintent = new Intent(GraphActivity.this, MainActivity.class);
                        startActivity(mtintent);
                        finish();
                        return true;
                    case R.id.information:
                        Intent mtintent1 = new Intent(GraphActivity.this, InformationActivity.class);
                        startActivity(mtintent1);
                        finish();
                        return true;
                    case R.id.history:
                        Intent mtintent2 = new Intent(GraphActivity.this, HistoryActivity.class);
                        startActivity(mtintent2);
                        finish();
                        return true;
                    case R.id.grafik:
                        return true;
                    default:
                        return true;
                }




            }

        });
    }

    private void history() {
        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InterfaceAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        InterfaceAPI api = retrofit.create(InterfaceAPI.class);

        Call<List<History>> call = api.getHistory();

        call.enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                if (!response.isSuccessful()) {
//                    Toast.makeText(GraphActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(GraphActivity.this, "Success", Toast.LENGTH_SHORT).show();

                arraylist = response.body();
                AnyChartView anyChartView = findViewById(R.id.any_chart_view);
                anyChartView.setProgressBar(findViewById(R.id.progress_bar));

                Cartesian columnChart = AnyChart.column();

                columnChart.animation(true);

                Crosshair crosshair = columnChart.crosshair();
                crosshair.enabled(true);
                // TODO yStroke xStroke in crosshair
                crosshair.yStroke((Stroke) null, null, null, (String) null, (String) null)
                        .xStroke("#fff", 1d, null, (String) null, (String) null)
                        .zIndex(39d);
                crosshair.yLabel(0).enabled(true);

                columnChart.yScale().stackMode(ScaleStackMode.VALUE);

                columnChart.title("Data Kandungan debu di udara");


//        seriesData = new ArrayList<>();
                List<DataEntry> seriesData = new ArrayList<>();
//        seriesData.add(new CustomDataEntry("1986", 3.6));
                for (int i = 0; i < arraylist.size(); i++){
                    String arr = arraylist.get(i).getDebu_before();
                    String arr2 = arraylist.get(i).getDebu_after();
                    int debu = Integer.parseInt(arr);
                    int debu2 = Integer.parseInt(arr2);
//                    Toast.makeText(GraphActivity.this, arraylist.get(i).getDate_time().toString(), Toast.LENGTH_SHORT ).show();
                    seriesData.add(new GraphActivity.CustomDataEntry(arraylist.get(i).getDate_time().toString(), debu , debu2));
                }
//        seriesData.add(new GraphActivity.CustomDataEntry("1990", 10));

                Set set = Set.instantiate();
                set.data(seriesData);
                Mapping series1Data = set.mapAs("{ x: 'x', value: 'value' }");
                Mapping series2Data = set.mapAs("{ x: 'x', value: 'value2' }");

                Column series1 = columnChart.column(series1Data);
                series1.name("Sebelum Difilter");
                series1.stroke("3 #fff");
                series1.hovered().stroke("3 #fff");
                series1.hovered().markers().enabled(true);
                series1.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d)
                        .stroke("1.5 #fff");
                series1.markers().zIndex(100d);

                Column series2 = columnChart.column(series2Data);
                series2.name("Sesudah Difilter");
                series2.stroke("3 #fff");
                series2.hovered().stroke("3 #fff");
                series2.hovered().markers().enabled(true);
                series2.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d)
                        .stroke("1.5 #fff");
                series2.markers().zIndex(100d);

                columnChart.legend().enabled(true);
                columnChart.legend().fontSize(13d);
                columnChart.legend().padding(0d, 0d, 20d, 0d);

                columnChart.xAxis(0).title(false);
                columnChart.yAxis(0).title("Kandungan Debu Di udara (kg/m³)");

                columnChart.interactivity().hoverMode(HoverMode.BY_X);
                columnChart.tooltip()
                        .valuePrefix("")
                        .valuePostfix(" kg/m³")
                        .displayMode(TooltipDisplayMode.UNION);

                anyChartView.setChart(columnChart);


//                List<History> postList = response.body();
//                GrafikAdapter grafikAdapter = new GrafikAdapter(GraphActivity.this,postList);
//                recyclerView.setAdapter(grafikAdapter);

            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getChartData(String data) {

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

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }
    }
}