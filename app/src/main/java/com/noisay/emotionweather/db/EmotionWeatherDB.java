package com.noisay.emotionweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.noisay.emotionweather.model.City;
import com.noisay.emotionweather.model.County;
import com.noisay.emotionweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noisa on 2016/4/8.
 */
public class EmotionWeatherDB {
    /*数据库名*/
    public static final String DB_NAME = "EmotionWeather";
    /*数据库版本*/
    public static final int VERSION = 1;
    private static EmotionWeatherDB emothionWeatherDB;
    private SQLiteDatabase db;

    /*构造方法私有化*/
    private EmotionWeatherDB(Context context) {
        EmotionWeatherOpenHelper dbHelper = new EmotionWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /*获取EmotionWeatherDB的实例*/
    public synchronized static EmotionWeatherDB getInstance(Context context) {
        if (emothionWeatherDB == null) {
            emothionWeatherDB = new EmotionWeatherDB(context);
        }
        return emothionWeatherDB;
    }

    /*将Province实例出存储到数据库*/
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    /*从数据库读取全国所有省份信息*/
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getColumnIndex("id"));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
    /*将City实例出存储到数据库*/
    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_Id",city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    /*从数据库读取某省下所有城市信息*/
    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_Id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getColumnIndex("id"));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
    /*将County实例出存储到数据库*/
    public void saveCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("county_code", county.getCountyCode());
            values.put("city_Id",county.getCityId());
            db.insert("County", null, values);
        }
    }

    /*从数据库读取某市下所有县信息*/
    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_Id=?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getColumnIndex("id"));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }


}
