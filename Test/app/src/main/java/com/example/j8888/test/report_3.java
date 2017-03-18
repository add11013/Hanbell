package com.example.j8888.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by j8888 on 3/10/2017.
 */

public class report_3 extends Fragment {
    @Nullable
    ArrayList<String> Date = new ArrayList<>();
    final ArrayList<String> xLabel = new ArrayList<>();
    BarChart barChart;
    int a1=0,a2=0,a3=0,b1=12,b2=12,b3=12,cc=15;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.report3,container,false);
        barChart=(BarChart) view.findViewById(R.id.bargraph);
/*
        ArrayList<BarEntry> barEntries=new ArrayList<>();
        barEntries.add(new BarEntry(44f,0));
        barEntries.add(new BarEntry(88f,1));
        barEntries.add(new BarEntry(66f,2));
        barEntries.add(new BarEntry(12f,3));
        barEntries.add(new BarEntry(19f,4));
        barEntries.add(new BarEntry(91f,5));
        barEntries.add(new BarEntry(44f,6));
        BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");

        ArrayList<String> theDates=new ArrayList<>();
        theDates.add("Jan");
        theDates.add("Feb");
        theDates.add("Mar");
        theDates.add("April");
        theDates.add("May");
        theDates.add("June");
        theDates.add("July");*/

        ArrayList<BarEntry> entries = new ArrayList<>();
        int j=5;
        entries.add(new BarEntry(1f, j));
        entries.add(new BarEntry(2f, 1));
        entries.add(new BarEntry(3f, 2));
        entries.add(new BarEntry(4f, 3));
        entries.add(new BarEntry(5f, 4));
        entries.add(new BarEntry(6f, 5));
        BarDataSet depenses = new BarDataSet(entries, "depenses");


        Date.add("Jan");
        Date.add("Feb");
        Date.add("March");
        Date.add("April");
        Date.add("May");
        Date.add("June");

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add((IBarDataSet) depenses);
        BarData Data = new BarData(dataSets);


        barChart.setData(Data);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.animateXY(3000, 3000);
        barChart.setHorizontalScrollBarEnabled(true);
        barChart.setDoubleTapToZoomEnabled(true);
        depenses.setColors(ColorTemplate.COLORFUL_COLORS);



        xLabel.add("Jan");
        xLabel.add("Feb");
        xLabel.add("March");
        xLabel.add("April");
        xLabel.add("May");
        xLabel.add("June");
        // or use some other logic to save your data in list. For ex.
        for(int i=1; i<20; i+=1)
        {
            xLabel.add("i"+i);
        }

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int)value);
            }
        });

        TextView textView=(TextView) view.findViewById(R.id.textView);

        textView.setText(a1);




        return view;

    }


}
