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
import com.squareup.picasso.Picasso;

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
        //https://api.weatherapi.com/v1/forecast.json?key=c654813f6ac247da9b431839231208&q=Hanoi&days=7
        String url = "https://api.weatherapi.com/v1/forecast.json?key=c654813f6ac247da9b431839231208&q="+data+"&days=7";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //lấy api
                    JSONObject jsonObject = new JSONObject(response);

                    // set tên thanh phố
                    JSONObject jsonObjectLocation = jsonObject.getJSONObject("location");
                    String name =jsonObjectLocation.getString("name");
                    tvThanhPho.setText(name);

                    //lấy các ngày

                    JSONObject jsonObjectForecast = jsonObject.getJSONObject("forecast");
                    JSONArray jsonArrayList = jsonObjectForecast.getJSONArray("forecastday");
                    for(int i=0;i<jsonArrayList.length();i++)
                    {
                        // set ngày
                        JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                        String Day = jsonObjectList.getString("date");

                        // lấy nhiệt min max
                        JSONObject jsonObjectDay = jsonObjectList.getJSONObject("day");
                        String NhietDoMin = jsonObjectDay.getString("mintemp_c");
                        String NhietDomax = jsonObjectDay.getString("maxtemp_c");
                        //lấy icon + status
                        JSONObject jsonObjectCodition = jsonObjectDay.getJSONObject("condition");
                        String icon = jsonObjectCodition.getString("icon");
                        String status ="";

                        // add vào mảng dự báo thời tiết
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