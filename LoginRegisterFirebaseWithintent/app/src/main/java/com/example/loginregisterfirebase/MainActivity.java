package com.example.loginregisterfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
//    Button btn_proceed;
    Button btn_music;
    Button btn_hospital;
    Button btn_police;
    Dialog dialog;
    Button statstistic;

FloatingActionButton btn_proceed;
    public String TAGS;
    public String SHIFT;
    public String data;
    private TextView mTextViewResult;

    String url = "https://my-focus.herokuapp.com/predict";
    Button StartS;

    public static final String MSG = "com.example.loginregisterfirebase";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Recieve Intent

        Intent intent = getIntent();
        String fullname = intent.getStringExtra("fullname");
//        String email = intent.getStringExtra("email");
//        String password = intent.getStringExtra("pwd");
        TextView fname = findViewById(R.id.fname);
//        TextView mail = findViewById(R.id.mail);
//        TextView pwd = findViewById(R.id.pwd);
        fname.setText(fullname);
//        mail.setText(email);
//        pwd.setText(password);

        btn_proceed = findViewById(R.id.btn_proceed);
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Tags_Page.class);
                startActivity(intent);
            }
        });


        btn_music = findViewById(R.id.music_btn);
        btn_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vie) {
                Intent in = new Intent(MainActivity.this, MusicMain.class);
                startActivity(in);
            }
        });

        statstistic = findViewById(R.id.statistics);
        statstistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSTAT = new Intent(MainActivity.this, Stats_Page.class);
                startActivity(intentSTAT);
            }
        });

        //START
//            StartS = findViewById(R.id.StartS);
//            StartS.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View vi) {
//                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//
//                                    try {
//                                        JSONObject jsonObject = new JSONObject(response);
//                                        data = jsonObject.getString("time");
//
//                                        Toast.makeText(MainActivity.this, "WE recieved : " + data, Toast.LENGTH_SHORT).show();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            },
//                            new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                                }
//                            }){
//                        @Override
//                        protected Map<String, String> getParams() {
//                            Map<String, String> params = new HashMap<String, String>();
//                            params.put("tags", "1");
//                            params.put("age", "20");
//                            params.put("shift", "2");
//
//                            return params;
//                        }
//                    };
//                    queue.add(stringRequest);
//                }
//            });
        //END

//        Button StartSession = findViewById(R.id.StartSession);
//        StartSession.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Pomodoro_Timer.class);
//                startActivity(intent);
//                intent.putExtra("fullname", "Hi, There");
//                finish();
//            }
//        });

//        btn_hospital=findViewById(R.id.btn_hospital);
//        btn_police=findViewById(R.id.btn_police);
//        btn_proceed=findViewById(R.id.btn_proceed);
//        dialog= new Dialog(this);
//        btn_proceed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openalert();
//            }
//        });

        //onCreate ENDS




        //motivational quotes


        mTextViewResult = findViewById(R.id.response_box);

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        String value = "{\r\n    \"key1\": \"value\",\r\n    \"key2\": \"value\"\r\n}";
        RequestBody body = RequestBody.create(mediaType, value);
        okhttp3.Request request = new Request.Builder()
                .url("https://motivational-quotes1.p.rapidapi.com/motivation")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("X-RapidAPI-Key", "06b43092d1msh7e7f379c88ef5c9p15002cjsnccda8198a2a1")
                .addHeader("X-RapidAPI-Host", "motivational-quotes1.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewResult.setText(myResponse);
                        }
                    });
                }

            }
        });


    }

//    private void openalert(){
//        dialog.setContentView(R.layout.popup_activity);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        dialog.show();
//    }
//
//    public void showPopup (View v){
//        PopupMenu popup = new PopupMenu(this, v);
//        popup.setOnMenuItemClickListener(this);
//        popup.inflate(R.menu.popup_menu);
//        popup.show();
//    }
//
//    @Override
//    public boolean onMenuItemClick(MenuItem menuItem) {
//        switch (menuItem.getItemId()){
//            case R.id.item1:
//                TAGS = "1";
//                //Toast.makeText(this,"Tag 1 clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.item2:
//                TAGS = "2";
//                //Toast.makeText(this,"Tag 2 clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.item3:
//                TAGS = "3";
//                //Toast.makeText(this,"Tag 3 clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.item4:
//                TAGS = "4";
//                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
//                return true;
//
//            case R.id.item5:
//                TAGS = "5";
//                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
//                return true;
//
//            case R.id.item6:
//                TAGS = "6";
//                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
//                return true;
//
//            case R.id.item7:
//                TAGS = "7";
//                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
//                return true;
//
//            case R.id.item8:
//                TAGS = "8";
//                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
//                return true;
//
//            case R.id.item9:
//                TAGS = "9";
//                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
//                return true;
//
//            case R.id.item10:
//                TAGS = "10";
//                //Toast.makeText(this,"Tag 4 clicked", Toast.LENGTH_SHORT).show();
//                return true;
//
//            case R.id.item11:
//                SHIFT = "1";
//                //Toast.makeText(this,"M ", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.item22:
//                SHIFT = "2";
//                //Toast.makeText(this,"A", Toast.LENGTH_SHORT).show();
//                return true;
////            case R.id.item33:
////                Toast.makeText(this,"E", Toast.LENGTH_SHORT).show();
////                return true;
////            case R.id.item44:
////                Toast.makeText(this,"N", Toast.LENGTH_SHORT).show();
////                return true;
//            default:
//                return false;
//        }
//    }
//
//    public void showPopup1 (View v){
//        PopupMenu popup = new PopupMenu(this, v);
//        popup.setOnMenuItemClickListener(this);
//        popup.inflate(R.menu.popup_shift_menu);
//        popup.show();
//    }

}