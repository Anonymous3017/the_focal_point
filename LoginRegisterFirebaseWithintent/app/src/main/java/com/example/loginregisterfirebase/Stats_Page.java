package com.example.loginregisterfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Stats_Page extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learnoset-18786-default-rtdb.firebaseio.com/");
    final String DataStore = "WEEK";

    String Session1;
    String Session2;
    String Session3;

    int Session_Monday;
    int Session_Tuesday;
    int Session_Wednesday;

    public ArrayList<BarEntry> barArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_page);
        Intent intentSTAT = getIntent();


        databaseReference.child("StorageFacility").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Session1 = snapshot.child("StorageFacility").child("WEEK").child("Monday_data").getValue(String.class);
                Session2 = snapshot.child("StorageFacility").child("WEEK").child("Tuesday_data").getValue(String.class);
                Session3 = snapshot.child("StorageFacility").child("WEEK").child("Wednesday_data").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Session_Monday = Integer.parseInt(Session1);
//        Session_Tuesday = Integer.parseInt(Session2);
//        Session_Wednesday = Integer.parseInt(Session3);

        Toast.makeText(this,"We got :" + Session3,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,"We got :" + Session_Monday,Toast.LENGTH_SHORT).show();

//        Toast.makeText(this,"We got" + Session2,Toast.LENGTH_SHORT);
//        Toast.makeText(this,"We got" + Session3,Toast.LENGTH_SHORT);

        BarChart barChart = findViewById(R.id.barchart);
        getData();
        BarDataSet barDataSet = new BarDataSet(barArraylist,"STATS");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        //color bar data set
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //text color
        barDataSet.setValueTextColor(Color.BLACK);
        //setting text size
        barDataSet.setValueTextSize(24f);
        barChart.getDescription().setEnabled(true);
    }

    private void getData()
    {
        barArraylist = new ArrayList<BarEntry>();
        //barArraylist.add(new BarEntry(3,Timer_Val));
//        barArraylist.add(new BarEntry(2,Session_Monday));
//        barArraylist.add(new BarEntry(4,Session_Tuesday));
//        barArraylist.add(new BarEntry(6,Session_Wednesday));
//        barArraylist.add(new BarEntry(8,40));

        barArraylist.add(new BarEntry(1,25));
        barArraylist.add(new BarEntry(2,36));
        barArraylist.add(new BarEntry(3,19));
        barArraylist.add(new BarEntry(4,47));

    }
}