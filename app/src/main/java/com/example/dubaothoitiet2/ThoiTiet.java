package com.example.dubaothoitiet2;

public class ThoiTiet {
    public String Day;
    public String Status;
    public String Img;
    public String Mintemp;
    public String Maxtemp;

    public ThoiTiet(String day, String status, String img, String mintemp, String maxtemp) {
        Day = day;
        Status = status;
        Img = img;
        Mintemp = mintemp;
        Maxtemp = maxtemp;
    }

    public String getDay() {
        return Day;
    }

    public String getStatus() {
        return Status;
    }

    public String getImg() {
        return Img;
    }

    public String getMintemp() {
        return Mintemp;
    }

    public String getMaxtemp() {
        return Maxtemp;
    }
}
