package com.example.j8888.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MyDB extends AppCompatActivity {
    private SQLiteDatabase db=null;
    private Integer i;
    /*private final static String CREAT_TABLE="CREATE TABLE table01(_id INTEGER PRIMARY KEY,year INTEGER,month INTEGER,day INTEGER" +
            ",company TEXT,,today INTEGER,grandTotal INTEGER,target INTEGER)";*/
    private final static String CREAT_TABLE="CREATE TABLE table01(_id INTEGER PRIMARY KEY,num INTEGER,data TEXT)";
    private ListView listView01;
    private Button btnDo;
    private String str,itemdata;
    private TextView textView;
    private int n=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_db);

        listView01=(ListView)findViewById(R.id.ListView01);
        btnDo=(Button)findViewById(R.id.btnDo);
        textView=(TextView)findViewById(R.id.textView4) ;
        btnDo.setOnClickListener(btnDoClick);

        itemdata="資料項目"+n;
        str="ININSERT INTO table01(num,data) value(10,50)";
        textView.setText(str);

        SQLiteDatabase db = openOrCreateDatabase("report.db",MODE_PRIVATE,null);
        db.execSQL("INSERT INTO table01(num,data) value(2017,03,01)");
        try{
            db.execSQL(CREAT_TABLE);
        }catch(Exception e){
            UpdateAdapter();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.execSQL("DROP TABLE table01");
        db.close();
    }
    private Button.OnClickListener btnDoClick= new Button.OnClickListener(){
        public void onClick(View v){
            try{
                db.execSQL(str);
                UpdateAdapter();
                n++;
                itemdata="資料項目"+n;
                Cursor cursor = db.rawQuery("SELECT * FROM table01 ",null);
                textView.setText(cursor.getString(0));
                textView.setText("321");
            }catch (Exception e){
                setTitle("SQL語法錯誤");
            }
        }
    };
    public void UpdateAdapter(){
        Cursor cursor = db.rawQuery("SELECT * FROM table01 ",null);
        if(cursor != null && cursor.getCount() >=0){
            SimpleCursorAdapter adapter= new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor,new String[]{"num","data"},new int[]{android.R.id.text1,android.R.id.text2},0);
            listView01.setAdapter(adapter);
        }
    }
}
