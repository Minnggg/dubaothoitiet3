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

    private int x;
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
        String url = "https://api.weatherapi.com/v1/current.json?key=c654813f6ac247da9b431839231208&q="+data;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //lấy api
                    JSONObject jsonObject = new JSONObject(response);

                    //lấy tên thanh pho + thời gian + tên quoc gia
                    JSONObject jsonObjectLocation = jsonObject.getJSONObject("location");
                    String day = jsonObjectLocation.getString("localtime");
                    String name =jsonObjectLocation.getString("name");
                    tvThanhPho.setText("Tên thành phố : " + name  );
                    tvNgayThang.setText("Cập nhật lúc : "+day);
                    String quocgia = jsonObjectLocation.getString("country");
                    tvQuocGia.setText("Tên quốc gia : "+quocgia);

                    // load ảnh vào imgIcon và cài đặt trạng thái

                    JSONObject jsonObjectCurrent = jsonObject.getJSONObject("current");
                    JSONObject jsonObjectCodition = jsonObjectCurrent.getJSONObject("condition");
                    String status = jsonObjectCodition.getString("text");
                    String icon = jsonObjectCodition.getString("icon");
                    Picasso.get().load("https:"+icon).into(imgIcon);
                    tvTrangThai.setText(status);

                    //set nhiệt độ
                    String Nhiet = jsonObjectCurrent.getString("temp_c");
                    tvNhietDo.setText("Nhiệt độ : "+Nhiet + " ℃ " );


                    // cài đặt hơi nước,mây,gió

                    String doAm =jsonObjectCurrent.getString("humidity");
                    tvDoAm.setText(doAm+"%");
                    String may = jsonObjectCurrent.getString("cloud");
                    tvMay.setText(may+"%");
                    String tocdo = jsonObjectCurrent.getString("wind_mph");
                    tvGio.setText(tocdo+" m/s");




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

