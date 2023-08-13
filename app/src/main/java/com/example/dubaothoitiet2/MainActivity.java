package com.example.dubaothoitiet2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.PicassoProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private EditText edseach;
    private TextView tvThanhPho,tvQuocGia,tvNhietDo,tvTrangThai,tvNgayThang,tvDoAm,tvMay,tvGio;
    private Button btnSeach,btnNgaytiep;
    ImageView imgIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        GetCurrent("Ninh Binh");
        btnSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edseach.getText().toString().trim();
                if(city.equals(""))
                {
                    GetCurrent("Ninh Binh");
                }
                else GetCurrent(city);
            }
        });

        btnNgaytiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                String city = edseach.getText().toString().trim();
                intent.putExtra("name",city);
                startActivity(intent);
            }
        });


    }
    public void GetCurrent(String data)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+data+"&appid=ca19fb65f9c92c53cff9954dd6222614&units=metric";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String day = jsonObject.getString("dt");
                    String name =jsonObject.getString("name");
                    tvThanhPho.setText("Tên thành phố : " + name  );

                    long l = Long.valueOf(day);
                    Date date = new Date(l*1000);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss \nEEEE dd/MM/yyyy");
                    String Day = simpleDateFormat.format(date);
                    tvNgayThang.setText("Cập nhật lúc : "+Day);

                    JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                    String status = jsonObjectWeather.getString("main");
                    String icon = jsonObjectWeather.getString("icon");
                    // load ảnh vào imgIcon đang bị lỗi
//                    Picasso.get().load("https://openweathermap.org/img/w/"+icon+".png").into(imgIcon);

                    tvTrangThai.setText("Trang thái : " + status);



                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                    String nhietDo = jsonObjectMain.getString("temp");
                    String doAm = jsonObjectMain.getString("humidity");
                    Double Nhiet = Double.valueOf(nhietDo);

                    tvNhietDo.setText("Nhiệt độ : "+Nhiet + "C" );
                    tvDoAm.setText(doAm+"%");





                    JSONObject jsonObjectMay = jsonObject.getJSONObject("clouds");
                    String may = jsonObjectMay.getString("all");
                    tvMay.setText(may+"%");


                    JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                    String quocgia = jsonObjectSys.getString("country");
                    tvQuocGia.setText("Tên quốc gia : "+quocgia);


                    JSONObject jsonObjectGio = jsonObject.getJSONObject("wind");
                    String tocdo = jsonObjectGio.getString("speed");
                    tvGio.setText(tocdo+"m/s");


                } catch (JSONException e) {
                     e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }


    private void init()
    {
        edseach = findViewById(R.id.edseach);
        btnSeach = findViewById(R.id.btnsearch);
        btnNgaytiep = findViewById(R.id.nextday);
        imgIcon = findViewById(R.id.imgIcon);
        tvThanhPho = findViewById(R.id.tvName);
        tvQuocGia = findViewById(R.id.tvCountry);
        tvNhietDo = findViewById(R.id.tvTemp);
        tvTrangThai = findViewById(R.id.tvTrangthai);
        tvNgayThang = findViewById(R.id.today);
        tvDoAm = findViewById(R.id.tvhumidity);
        tvMay = findViewById(R.id.tvcloud);
        tvGio = findViewById(R.id.tvwind);
    }

}

