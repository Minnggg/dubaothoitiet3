package com.example.dubaothoitiet2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    private ImageView btnback;
    private ListView lv;
    private TextView tvThanhPho;
    CustomAdapter adapter;
    ArrayList <ThoiTiet> arraThoiTiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        inita();
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        if(city.trim().equals(""))
        {
            Get7DayData("Ninh Binh");
        }
        else Get7DayData(city);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void inita() {
        btnback = findViewById(R.id.btnback);
        tvThanhPho = findViewById(R.id.tvThanhPho1);
        lv = findViewById(R.id.lv);
        arraThoiTiet = new ArrayList<ThoiTiet>();
        adapter = new CustomAdapter(MainActivity2.this,arraThoiTiet);
        lv.setAdapter(adapter);
    }

    private void Get7DayData(String data) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=7&appid=ca19fb65f9c92c53cff9954dd6222614";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                    String name = jsonObjectCity.getString("name");
                    tvThanhPho.setText(name);


                    JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                    for(int i=0;i<jsonArrayList.length();i++)
                    {
                        JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                        String ngay = jsonObjectList.getString("dt");
                        long l = Long.valueOf(ngay);
                        Date date = new Date(l*1000);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("  EEEE\ndd/MM/yyyy");
                        String Day = simpleDateFormat.format(date);

                        JSONObject jsonObjectNhietdo = jsonObjectList.getJSONObject("main");
                        String min = jsonObjectNhietdo.getString("temp_min");
                        String max = jsonObjectNhietdo.getString("temp_max");
                        Double a = Double.valueOf(min);
                        String NhietDoMin = String.valueOf(a.intValue());
                        a = Double.valueOf(max);
                        String NhietDomax = String.valueOf(a.intValue());

                        JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        String status = jsonObjectWeather.getString("description");
                        String icon = jsonObjectWeather.getString("icon");

                        arraThoiTiet.add(new ThoiTiet(Day,status,icon,NhietDoMin,NhietDomax));

                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}