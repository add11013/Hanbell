package com.example.j8888.test;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by Roderick on 3/10/2017.
 */

public class report_1 extends Fragment {
    private int rows_num,rows,sum,sum1;
    private String YearspinnerValue,MonthspinnerValue,DayspinnerValue,WHERE;
    private String month="",day="",today="",target="",company="",year="",accumulation="",TargetRate="",LastYear="",Rate="";
    private TextView yearTXT,monthTXT,dayTXT,companyTXT,todayTXT,accumulationTXT,targetTXT,TargetRateTXT,lastYearTXT,RateTXT,tabletitle,Total;
    private int TodayTotal,AccumulationTotal,TargetTotal,LastYearTotal;
    final ArrayList<String> xLabel = new ArrayList<>();
    ArrayList<String> Date = new ArrayList<>();
    BarChart barChart;

    @Nullable
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.report1,container,false);


        //DB TextView
        yearTXT = (TextView) view.findViewById(R.id.yearTXT);
        monthTXT = (TextView) view.findViewById(R.id.monthTXT);
        dayTXT = (TextView) view.findViewById(R.id.dayTXT);
        companyTXT = (TextView) view.findViewById(R.id.companyTXT);
        todayTXT = (TextView) view.findViewById(R.id.todayTXT);
        accumulationTXT = (TextView) view.findViewById(R.id.accumulationTXT);
        targetTXT = (TextView) view.findViewById(R.id.targetTXT);
        TargetRateTXT=(TextView) view.findViewById(R.id.TargetRateTXT);
        lastYearTXT=(TextView) view.findViewById(R.id.lastYearTXT);
        RateTXT=(TextView) view.findViewById(R.id.RateTXT);
        tabletitle=(TextView) view.findViewById(R.id.tabletitle);
        Total=(TextView) view.findViewById(R.id.Total);
        barChart=(BarChart) view.findViewById(R.id.bargraph);


        //Spinner
        final Spinner Yearspinner = (Spinner) view.findViewById(R.id.YearSpinner);
        final String[] YearValue = {"全部", "2017", "2016", "2015", "2014"};
        ArrayAdapter<String> Yearlist = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item,YearValue);
        Yearspinner.setAdapter(Yearlist);
        Spinner MonthSpinner = (Spinner) view.findViewById(R.id.MonthSpinner);
        final String[] MonthValue = {"全部", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        ArrayAdapter<String> Monthlist = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item,MonthValue);
        MonthSpinner.setAdapter(Monthlist);
        Spinner DaySpinner = (Spinner) view.findViewById(R.id.DaySpinner);
        final String[] DayValue = {"全部","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        ArrayAdapter<String> Daylist = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item,DayValue);
        DaySpinner.setAdapter(Daylist);

        //spinner listener
        Yearspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                if(parent.getItemAtPosition(pos).toString()=="全部")
                    YearspinnerValue="";
                else
                    YearspinnerValue=parent.getItemAtPosition(pos).toString();
                CaculateQueryWHERE();
                runDB();
                Toast.makeText(parent.getContext(),"你選擇的是:" + parent.getItemAtPosition(pos).toString()+"年",Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        MonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                if(parent.getItemAtPosition(pos).toString()=="全部")
                    MonthspinnerValue="";
                else
                    MonthspinnerValue=parent.getItemAtPosition(pos).toString();
                CaculateQueryWHERE();
                runDB();
                Toast.makeText(parent.getContext(),"你選擇的是:" + parent.getItemAtPosition(pos).toString()+"月",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        DaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                if(parent.getItemAtPosition(pos).toString()=="全部")
                    DayspinnerValue="";
                else
                    DayspinnerValue=parent.getItemAtPosition(pos).toString();
                CaculateQueryWHERE();
                runDB();
                Toast.makeText(parent.getContext(),"你選擇的是:" + parent.getItemAtPosition(pos).toString()+"日",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        paintchart();
        runDB();
        return view;
    }

    private Button.OnClickListener btnDoClick= new Button.OnClickListener(){
        public void onClick(View v){

        }
    };



    private void insertData(){
        SQLiteDatabase db=getActivity().openOrCreateDatabase("report.db",android.content.Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE table01 (id INTEGER PRIMARY KEY ," +
                "year INTEGER,month INTEGER,day INTEGER," +
                "company TEXT," +
                "today INTEGER," +
                "target INTEGER)");
        db.execSQL("INSERT INTO table01(year,month,day,company,today,target) values (2017,03,01,'A',11,11)");
        db.execSQL("INSERT INTO table01(year,month,day,company,today,target) values (2017,03,01,'B',6,11)");
        db.execSQL("INSERT INTO table01(year,month,day,company,today,target) values (2017,03,01,'C',5,10)");
        db.execSQL("INSERT INTO table01(year,month,day,company,today,target) values (2017,03,02,'A',2,10)");
        db.execSQL("INSERT INTO table01(year,month,day,company,today,target) values (2017,03,02,'C',7,10)");
        db.execSQL("INSERT INTO table01(year,month,day,company,today,target) values (2017,03,03,'A',8,10)");
        db.execSQL("INSERT INTO table01(year,month,day,company,today,target) values (2017,03,03,'C',1,10)");
        db.execSQL("INSERT INTO table01(year,month,day,company,today,target) values (2016,03,01,'B',15,10)");
        db.execSQL("INSERT INTO table01(year,month,day,company,today,target) values (2016,03,01,'C',11,10)");
        db.execSQL("INSERT INTO table01(year,month,day,company,today,target) values (2016,03,02,'A',5,10)");
        db.execSQL("INSERT INTO table01(year,month,day,company,today,target) values (2016,03,02,'B',9,10)");
        db.execSQL("INSERT INTO table01(year,month,day,company,today,target) values (2016,03,03,'C',7,10)");
    }
    private void runDB(){
        //DB
        SQLiteDatabase db=getActivity().openOrCreateDatabase("report.db", Context.MODE_PRIVATE,null);
        try{
            insertData();
            year="";month="";day="";company="";today="";accumulation="";target="";TargetRate="";LastYear="";Rate="";
            TodayTotal=0;AccumulationTotal=0;TargetTotal=0;LastYearTotal=0;
            Cursor cursor = db.rawQuery("SELECT * FROM table01 "+WHERE,null);
            rows_num =cursor.getCount();
            if(rows_num != 0) {
                cursor.moveToFirst();			//將指標移至第一筆資料
                for(int i=0; i<rows_num; i++) {
                    year = year+Integer.toString(cursor.getInt(1))+"\n";
                    month = month+Integer.toString(cursor.getInt(2))+"\n";
                    day = day+Integer.toString(cursor.getInt(3))+"\n";
                    company = company+cursor.getString(4)+"\n";
                    today = today+Integer.toString(cursor.getInt(5))+"\n";
                    TodayTotal=TodayTotal+cursor.getInt(5);
                    //Accumulation Caculate
                    Cursor CursorAccumulation = db.rawQuery("SELECT * FROM table01 WHERE year="+cursor.getInt(1)+" AND month="+cursor.getInt(2)+" AND day<="+cursor.getInt(3)+" AND company='"+cursor.getString(4)+"'",null);
                    rows=CursorAccumulation.getCount();
                    CursorAccumulation.moveToFirst();
                    sum=0;
                    for(int ii=0;ii<rows;ii++){
                        int tmp=CursorAccumulation.getInt(5);
                        sum=sum+tmp;
                        CursorAccumulation.moveToNext();
                    }
                    accumulation=accumulation+Integer.toString(sum)+"\n";
                    AccumulationTotal=AccumulationTotal+sum;
                    target=target+Integer.toString(cursor.getInt(6))+"\n";
                    TargetTotal=TargetTotal+cursor.getInt(6);

                    //TargetRate Caculate
                    Float a=(float) cursor.getInt(5)/cursor.getInt(6)*100;
                    TargetRate=TargetRate+String.format("%.2f",a)+"%\n";

                    //LastYaer Caculate
                    Cursor CursorLastyear = db.rawQuery("SELECT * FROM table01 WHERE year="+(cursor.getInt(1)-1)+" AND month="+cursor.getInt(2)+" AND day<="+cursor.getInt(3)+" AND company='"+cursor.getString(4)+"'",null);
                    rows=CursorLastyear.getCount();
                    CursorLastyear.moveToFirst();
                    sum1=0;
                    for(int ii=0;ii<rows;ii++){
                        int tmp=CursorLastyear.getInt(5);
                        sum1=sum1+tmp;
                        CursorLastyear.moveToNext();
                    }
                    LastYear=LastYear+Integer.toString(sum1)+"\n";
                    LastYearTotal=LastYearTotal+sum1;

                    //Rate Caculate
                    Float b=(float) sum/sum1*100;
                    Rate=Rate+String.format("%.2f",b)+"%\n";

                    cursor.moveToNext();
                }
            }

            yearTXT.setText(year);
            monthTXT.setText(month);
            dayTXT.setText(day);
            companyTXT.setText(company);
            todayTXT.setText(today);
            accumulationTXT.setText(accumulation);
            targetTXT.setText(target);
            TargetRateTXT.setText(TargetRate);
            lastYearTXT.setText(LastYear);
            RateTXT.setText(Rate);
            Total.setText("Total                           "+Integer.toString(TodayTotal)+"                 "+Integer.toString(AccumulationTotal)+"               "+Integer.toString(TargetTotal)+"                                          "+Integer.toString(LastYearTotal));
        }catch (Exception e){
            //tabletitle.setText(e.toString());
        }
        db.execSQL("DROP TABLE table01");
        db.close();

    }

    private void paintchart(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        try{
        SQLiteDatabase db=getActivity().openOrCreateDatabase("report.db", Context.MODE_PRIVATE,null);
            insertData();
        float count=0;
        int BarToday=0;
        for (int i=2014;i<=2017;i++){
            for (int j=1;j<=12;j++){
                for (int k=1;k<=31;k++){
                    Cursor cursorTotal = db.rawQuery("SELECT * FROM table01 WHERE year='"+Integer.toString(i)+"' AND month='"+Integer.toString(j)+"' AND day='"+Integer.toString(k)+"'",null);
                    rows_num =cursorTotal.getCount();
                    if(rows_num != 0) {
                        BarToday=0;
                        cursorTotal.moveToFirst();			//將指標移至第一筆資料
                        for(int ii=0; ii<rows_num; ii++) {
                            BarToday=BarToday+cursorTotal.getInt(5);
                            cursorTotal.moveToNext();
                        }
                        tabletitle.setText(Integer.toString(BarToday)+Float.toString(count));
                        entries.add(new BarEntry(count, BarToday));
                        xLabel.add(Integer.toString(i)+"/"+Integer.toString(j)+"/"+Integer.toString(k)+"/");
                        count+=1;
                    }
                    cursorTotal.close();
                }
            }
        }


        //Chart
/*
        entries.add(new BarEntry(0f, 0));
        entries.add(new BarEntry(1f, 1));
        entries.add(new BarEntry(2f, 2));
        entries.add(new BarEntry(3f, 3));
        entries.add(new BarEntry(4f, 4));
        entries.add(new BarEntry(5f, 5));*/
        BarDataSet depenses = new BarDataSet(entries, "depenses");
        for(int i=0;i<count;i++){
            xLabel.add("1");
        }

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
        //int color = getResources().getColor(R.color.colorPrimary);
        //depenses.setColor(color);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int)value);
            }
        });
            db.execSQL("DROP TABLE table01");
            db.close();
        }catch (Exception e){
            tabletitle.setText(e.toString());
        }
    }


    private void CaculateQueryWHERE(){
        if (YearspinnerValue=="" && MonthspinnerValue=="" && DayspinnerValue=="")
            WHERE="";
        else if(YearspinnerValue=="" && MonthspinnerValue=="")
            WHERE = "WHERE day='" + DayspinnerValue + "'";
        else if(MonthspinnerValue==""&& DayspinnerValue=="")
            WHERE = "WHERE year='" + YearspinnerValue + "'";
        else if (YearspinnerValue=="" && DayspinnerValue == "")
            WHERE = "WHERE month='" + MonthspinnerValue + "'";
        else if(YearspinnerValue=="")
            WHERE = "WHERE month='" + MonthspinnerValue + "' AND day='" + DayspinnerValue + "'";
        else if(MonthspinnerValue=="")
            WHERE = "WHERE year='" + YearspinnerValue + "'" + " AND day='" + DayspinnerValue + "'";
        else if(DayspinnerValue=="")
            WHERE = "WHERE year='" + YearspinnerValue + "' AND month='" + MonthspinnerValue + "'";
        else
            WHERE="WHERE year='"+YearspinnerValue+"' AND month='"+MonthspinnerValue+"' AND day='"+DayspinnerValue+"'";

    }
}