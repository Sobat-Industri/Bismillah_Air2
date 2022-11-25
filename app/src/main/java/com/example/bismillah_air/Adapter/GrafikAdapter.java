package com.example.bismillah_air.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
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
import com.example.bismillah_air.API.History;
import com.example.bismillah_air.GraphActivity;
import com.example.bismillah_air.R;

import java.util.ArrayList;
import java.util.List;

public class GrafikAdapter extends RecyclerView.Adapter<GrafikAdapter.GrafikViewHolder>{

    List<History> postList;
    Context context;

    public GrafikAdapter(Context context, List<History> posts){
        this.context = context;
        postList = posts;
    }

    @NonNull
    @Override
    public GrafikViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grafik_item , parent, false);
        return new GrafikAdapter.GrafikViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrafikViewHolder holder, int position) {
        History post = postList.get(position);

        holder.a = post.getDebu_after();
        holder.b = post.getDebu_before();
        holder.c = post.getDate_time().toString();

//        holder.text.setText(holder.b);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }



    public class GrafikViewHolder extends RecyclerView.ViewHolder {
        String a, b,c;
        TextView text;

        public GrafikViewHolder(@NonNull View itemView) {
            super(itemView);
//            text = itemView.findViewById(R.id.text);
        }
    }

//    private void create() {
//        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
//        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
//
//        Cartesian areaChart = AnyChart.area();
//
//        areaChart.animation(true);
//
//        Crosshair crosshair = areaChart.crosshair();
//        crosshair.enabled(true);
//        // TODO yStroke xStroke in crosshair
//        crosshair.yStroke((Stroke) null, null, null, (String) null, (String) null)
//                .xStroke("#fff", 1d, null, (String) null, (String) null)
//                .zIndex(39d);
//        crosshair.yLabel(0).enabled(true);
//
//        areaChart.yScale().stackMode(ScaleStackMode.VALUE);
//
//        areaChart.title("Unaudited Apple Inc. Revenue by Operating Segments");
//
//        List<GraphActivity.CustomDataEntry> seriesData;
//        seriesData = new ArrayList<>();
//        seriesData.add(new GraphActivity.CustomDataEntry("Q2 2014", 17.982));
//
//
//        Set set = Set.instantiate();
//        set.data((com.anychart.data.View) seriesData);
//        Mapping series1Data = set.mapAs("{ x: 'x', value: 'value' }");
//
//        Area series1 = areaChart.area(series1Data);
//        series1.name("Americas");
//        series1.stroke("3 #fff");
//        series1.hovered().stroke("3 #fff");
//        series1.hovered().markers().enabled(true);
//        series1.hovered().markers()
//                .type(MarkerType.CIRCLE)
//                .size(4d)
//                .stroke("1.5 #fff");
//        series1.markers().zIndex(100d);
//
//        areaChart.legend().enabled(true);
//        areaChart.legend().fontSize(13d);
//        areaChart.legend().padding(0d, 0d, 20d, 0d);
//
//        areaChart.xAxis(0).title(false);
//        areaChart.yAxis(0).title("Revenue (in Billons USD)");
//
//        areaChart.interactivity().hoverMode(HoverMode.BY_X);
//        areaChart.tooltip()
//                .valuePrefix("$")
//                .valuePostfix(" bln.")
//                .displayMode(TooltipDisplayMode.UNION);
//
//        anyChartView.setChart(areaChart);
//    }




}
