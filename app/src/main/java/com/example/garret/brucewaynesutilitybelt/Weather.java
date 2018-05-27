package com.example.garret.brucewaynesutilitybelt;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Weather extends AppCompatActivity {
    static TextView placeView;
    static TextView tempView;
    static TextView todayDescritpion;
    static TextView tomorrowForecastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        placeView = (TextView) findViewById(R.id.locationView);
        tempView = (TextView) findViewById(R.id.temperatureView);
        todayDescritpion = (TextView) findViewById(R.id.todayDescriptionLabel);
        //tomorrowForecastView = (TextView) findViewById(R.id.tomorrowHILO);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          /* ActivityCompat.requestPermissions(MainActivity.this, new String[](
                   Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ));*/
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        double lat = location.getLatitude();
        double longitude = location.getLongitude();


        //Log.d("coordinates","lat is " + lat + "   lon is " + longitude);

        DownloadTask task = new DownloadTask();

        task.execute("http://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(lat) + "&lon=" + String.valueOf(longitude) + "&units=imperial&appid=ae5038fc464cd412b3a1f97ddc455337");
        //String.valueOf(lat)   String.valueOf(longitude)
    }
}
