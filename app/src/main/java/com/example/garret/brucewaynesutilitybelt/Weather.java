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
import android.widget.TextView;

public class Weather extends AppCompatActivity {
    static TextView placeView;
    static TextView tempView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        placeView = findViewById(R.id.locationView);
        tempView = findViewById(R.id.temperatureView);
        setContentView(R.layout.activity_weather);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        double lat = location.getLatitude();
        double longitude = location.getLongitude();

        System.out.println("lat is " + String.valueOf(lat) + "   lon is " + String.valueOf(longitude));
        DownloadTask task = new DownloadTask();

        task.execute("http://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(lat) + "&lon=" + String.valueOf(longitude) + "&appid=86f526bfa7e840004dae83f99c2b0081");


    }
}
