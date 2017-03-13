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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Roderick on 3/10/2017.
 */

public class report_2 extends Fragment {
    private int rows_num,rows,sum,sum1;
    private String YearspinnerValue,MonthspinnerValue,DayspinnerValue,WHERE;
    private String month="",day="",today="",target="",company="",year="",accumulation="",TargetRate="",LastYear="",Rate="";
    private TextView yearTXT,monthTXT,dayTXT,companyTXT,todayTXT,accumulationTXT,targetTXT,TargetRateTXT,lastYearTXT,RateTXT,tabletitle;
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
                Toast.makeText(parent.getContext(),"你選擇的是:" + parent.getItemAtPosition(pos).toString(),Toast.LENGTH_LONG).show();

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
                Toast.makeText(parent.getContext(),"你選擇的是:" + parent.getItemAtPosition(pos).toString(),Toast.LENGTH_LONG).show();
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
                Toast.makeText(parent.getContext(),"你選擇的是:" + parent.getItemAtPosition(pos).toString(),Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


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
        SQLiteDatabase db=getActivity().openOrCreateDatabase("report.db",android.content.Context.MODE_PRIVATE,null);
        try{
            insertData();
            year="";
            month="";
            day="";
            company="";
            today="";
            accumulation="";
            target="";
            TargetRate="";
            LastYear="";
            Rate="";
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
                    target=target+Integer.toString(cursor.getInt(6))+"\n";

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
        }catch (Exception e){
            //tabletitle.setText(e.toString());
        }
        db.execSQL("DROP TABLE table01");
        db.close();
    }
    private void CaculateQueryWHERE(){
        if (YearspinnerValue=="" && MonthspinnerValue=="" && DayspinnerValue=="")
            WHERE="";
        else if(YearspinnerValue=="") {
            if(MonthspinnerValue=="")
                WHERE = "WHERE day='" + DayspinnerValue + "'";
            else
                WHERE = "WHERE month='" + MonthspinnerValue + "' AND day='" + DayspinnerValue + "'";
        }
        else if(MonthspinnerValue=="") {
            if (DayspinnerValue == "")
                WHERE = "WHERE year='" + YearspinnerValue + "'";
            else
                WHERE = "WHERE year='" + YearspinnerValue + "'" + " AND day='" + DayspinnerValue + "'";
        }
        else if(DayspinnerValue=="") {
            if(YearspinnerValue=="")
                WHERE = "WHERE month='" + MonthspinnerValue + "'";
            else
                WHERE = "WHERE year='" + YearspinnerValue + "' AND month='" + MonthspinnerValue + "'";
        }
        else
            WHERE="WHERE year='"+YearspinnerValue+"' AND month='"+MonthspinnerValue+"' AND day='"+DayspinnerValue+"'";

    }
}