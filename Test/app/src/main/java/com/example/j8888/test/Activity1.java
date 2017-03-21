package com.example.j8888.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private ExpandableListView listView;
        private ExpandableListAdapter listAdapter;
        private List<String> listDataHeader;
        private HashMap<String,List<String>> listHash;
        private TextView txtShow;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_1);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            listView = (ExpandableListView)findViewById(R.id.lvExp);
            initData();
            listAdapter=new ExpandableListAdapter(this,listDataHeader,listHash);
            listView.setAdapter(listAdapter);
            listView.setOnChildClickListener(ELVListener);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }
        private ExpandableListView.OnChildClickListener ELVListener= new ExpandableListView.OnChildClickListener(){

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                //String.valueOf(listHash.get(listDataHeader.get(groupPosition)).get(childPosition))這行表示
                //將目前點的listDataHeader這個ArrayList的值取出當作key，再用此key去listHash裡面找值,而同一個key有很多個值
                //所以回傳會是個陣列，因此還要再用get(childPosition),取出裡面真正要的
                Fragment fragment =null;
                switch (String.valueOf(listHash.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    case "- 01收付款申請":
                        fragment= new report_1();
                        break;
                    case "- 02出貨旬報":
                        fragment= new report_2();
                        break;
                    case "- 01OO":
                        fragment = new report_3();
                        break;
                }
                if (fragment != null){
                    FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_1,fragment);
                    ft.commit();
                }
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitle(String.valueOf(listHash.get(listDataHeader.get(groupPosition)).get(childPosition)));
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        };

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("+01表單申請");
        listDataHeader.add("+02表單查詢");
        listDataHeader.add("+03表單整理報表");

        List<String> AAA = new ArrayList<>();
        AAA.add("- 01 通用");
        AAA.add("      -01收付款申請");
        AAA.add("- 02 人資");
        AAA.add("      -01  請假單");
        AAA.add("      -01  加班單");
        AAA.add("      -01  人員增補單");

        List<String> BBB = new ArrayList<>();
        BBB.add("- 01 簽核中");
        BBB.add("- 02 已完成");

        List<String> CCC = new ArrayList<>();
        CCC.add("- 01 通用");
        CCC.add("         -收付款明細");
        CCC.add("- 02 人資");
        CCC.add("- 03 人員需求彙總");
        listHash.put(listDataHeader.get(0),AAA);
        listHash.put(listDataHeader.get(1),BBB);
        listHash.put(listDataHeader.get(2),CCC);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
