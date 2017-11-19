package myapp.scichartexample.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.charting.modifiers.ModifierGroup;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.annotations.HorizontalLineAnnotation;
import com.scichart.charting.visuals.annotations.LabelPlacement;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.renderableSeries.IRenderableSeries;
import com.scichart.drawing.utility.ColorUtil;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;

import myapp.scichartexample.App;
import myapp.scichartexample.Constants;
import myapp.scichartexample.Models.Point;
import myapp.scichartexample.R;
import myapp.scichartexample.Services.PointsGeneratorService;

public class MainActivity extends AppCompatActivity {
    @Inject
    public PointsGeneratorService pointsGeneratorService;

    private BroadcastReceiver broadcastReceiver;
    private Point point = new Point();
    private IXyDataSeries<Date, Double> lineData;
    private HorizontalLineAnnotation horizontalLineAnnotation;
    private Intent pointServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getComponent().injectsMainActivity(this);

        SciChartSurface surface = new SciChartSurface(this);
        LinearLayout chartLayout = (LinearLayout) findViewById(R.id.chart_layout);
        chartLayout.addView(surface);
        SciChartBuilder.init(this);
        final SciChartBuilder sciChartBuilder = SciChartBuilder.instance();

        lineData = sciChartBuilder.newXyDataSeries(Date.class, Double.class).build();
        horizontalLineAnnotation = sciChartBuilder.newHorizontalLineAnnotation()
                .withPosition(10,10)
                .withAnnotationLabel(LabelPlacement.Axis)
                .withStroke(2, ColorUtil.White).build();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(Constants.BROADCAST_ACTION)) {
                    point.setRate(intent.getDoubleExtra(Constants.POINT_RATE, 0));
                    point.setDate(new Date(intent.getLongExtra(Constants.POINT_DATE, 0)));
                    draw(point);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);

       // Create a numeric X axis
        Date d = new Date (System.currentTimeMillis());
        final IAxis xAxis = sciChartBuilder.newDateAxis()
                .withTextFormatting("H:mm")
                .withAxisTitle("Time")
                .withVisibleRange(d, new Date(d.getTime() + 20000))
                .build();

        // Create a numeric Y axis
        final IAxis yAxis = sciChartBuilder.newNumericAxis()
                .withAxisTitle("Rate")
                .withVisibleRange(-8, 8)
                .build();

        // Create interactivity modifiers
        ModifierGroup chartModifiers = sciChartBuilder.newModifierGroup()
                .withPinchZoomModifier().withReceiveHandledEvents(true).build()
                .withZoomPanModifier().withReceiveHandledEvents(true).build()
                .build();

        // Add the Y axis to the YAxes collection of the surface
        Collections.addAll(surface.getYAxes(), yAxis);

        // Add the X axis to the XAxes collection of the surface
        Collections.addAll(surface.getXAxes(), xAxis);

        // Add the interactions to the ChartModifiers collection of the surface
        Collections.addAll(surface.getChartModifiers(), chartModifiers);

        Collections.addAll(surface.getAnnotations(), horizontalLineAnnotation);


        final IRenderableSeries lineSeries = sciChartBuilder.newMountainSeries()
                .withDataSeries(lineData)
                .withStrokeStyle(ColorUtil.Blue, 2f, true)
                .withAreaFillColor(0x800000FF)
                .build();

        // Add a RenderableSeries onto the SciChartSurface
        surface.getRenderableSeries().add(lineSeries);

        pointServiceIntent = pointsGeneratorService.initialize(this);
        startService(pointServiceIntent);
    }

    @Override
    protected void onDestroy() {
        stopService(pointServiceIntent);
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    private void draw(Point point){
        lineData.append(point.getDate(), point.getRate());
        horizontalLineAnnotation.setY1(Float.parseFloat(String.valueOf(point.getRate())));
    }
}
