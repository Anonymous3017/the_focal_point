package com.example.loginregisterfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Tags_Page extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learnoset-18786-default-rtdb.firebaseio.com/");
    final String DataStore = "WEEK";

    public String TAGS;
    public String SHIFT;
    String data;
    Button btn_proceed;
    Button btn_hospital;
    Button btn_police;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_page);

        Intent intent = getIntent();

        Button StartS;
        String url = "https://my-focus.herokuapp.com/predict";

        StartS = findViewById(R.id.getDATA);
        StartS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                RequestQueue queue = Volley.newRequestQueue(Tags_Page.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    data = jsonObject.getString("time");

                                    Toast.makeText(Tags_Page.this, "WE recieved : " + data, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Toast.makeText(DEMO_TEST.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("tags", TAGS);
                        params.put("age", "20");
                        params.put("shift", SHIFT);

                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });

        btn_hospital=findViewById(R.id.btn_hospital);
        btn_police=findViewById(R.id.btn_police);


        Button StartSession = findViewById(R.id.goToPomo);
        StartSession.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v){

                SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Timer", data);
                editor.apply();
                Intent i = new Intent(Tags_Page.this, Pomodoro_Timer.class);
                startActivity(i);
//                i.putExtra("Timer", data);
//                finish();

                databaseReference.child("StorageFacility").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("StorageFacility").child("WEEK").child("Monday_data").setValue(data);
                        databaseReference.child("StorageFacility").child("WEEK").child("Tuesday_data").setValue(data+data);
                        databaseReference.child("StorageFacility").child("WEEK").child("Wednesday_data").setValue(data+data+data);
//                        databaseReference.child(DataStore).child("Thursday_data").setValue(data);
                        //finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


//            public void onClick(View v) {
//                Intent intent = new Intent(Tags_Page.this, Pomodoro_Timer.class);
//                startActivity(intent);
//                intent.putExtra("Timer", data);
//                finish();
//            }
        });
        //btn_proceed=findViewById(R.id.btn_proceed);
        //dialog= new Dialog(this);
//        btn_proceed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //openalert();
//            }
//        });

        //onCreate ENDS
    }

//    private void openalert(){
//        dialog.setContentView(R.layout.popup_activity);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        dialog.show();
//    }

    public void showPopup (View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.item1:
                TAGS = "1";
                //Toast.makeText(this,"Tag 1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                TAGS = "2";
                //Toast.makeText(this,"Tag 2 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                TAGS = "3";
                //Toast.makeText(this,"Tag 3 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4:
                TAGS = "4";
                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item5:
                TAGS = "5";
                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item6:
                TAGS = "6";
                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item7:
                TAGS = "7";
                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item8:
                TAGS = "8";
                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item9:
                TAGS = "9";
                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item10:
                TAGS = "10";
                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item11:
                SHIFT = "1";
                //Toast.makeText(this,"M ", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item22:
                SHIFT = "2";
                //Toast.makeText(this,"A", Toast.LENGTH_SHORT).show();
                return true;
//            case R.id.item33:
//                Toast.makeText(this,"E", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.item44:
//                Toast.makeText(this,"N", Toast.LENGTH_SHORT).show();
//                return true;
            default:
                return false;
        }
    }

    public void showPopup1 (View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_shift_menu);
        popup.show();
    }

}
