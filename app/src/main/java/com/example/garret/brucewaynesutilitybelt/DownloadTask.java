package com.example.garret.brucewaynesutilitybelt;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, String>{


       @Override
    protected String doInBackground(String... urls) {
           Log.d("test", "beginning of doInBackground in DOWNLOAD TASK");
           String result = "";
           URL url;
           HttpURLConnection urlConnection =  null;
           try {
               url = new URL(urls[0]);

               urlConnection = (HttpURLConnection) url.openConnection();

               InputStream in = urlConnection.getInputStream();

               InputStreamReader reader = new InputStreamReader(in);

               int data = reader.read();

               while(data != -1){
                    char current = (char) data;

                    result += current;

                    data = reader.read();
               }

               return result;

           } catch (Exception e) {
               e.printStackTrace();
           }
           return null;

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //get the relevant data from the JSONObject
        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONObject weatherData = new JSONObject(jsonObject.getString("main"));

            double temperature = Double.parseDouble(weatherData.getString("temp"));

            int temperatureInteger = (int) (temperature);

            String locationName = jsonObject.getString("name");

            Weather.placeView.setText(locationName);
            Weather.tempView.setText(String.valueOf(temperatureInteger) + "\u00B0F");
        } catch (Exception e){
            Log.d("test", "FAILED TRY IN onPostExecute, in catch now");
            e.printStackTrace();
        }
    }
}
