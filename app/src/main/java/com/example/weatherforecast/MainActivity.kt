package com.example.weatherforecast

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NAME_SHADOWING")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @SuppressLint("SetTextI18n")
    fun getweather(view: View) {
        val cityname=city.editableText.toString().trim();
        val queue = Volley.newRequestQueue(this)
        val url="https://api.openweathermap.org/data/2.5/weather?q=$cityname&units=metric&appid=405ed9a1314edd546a270018b9232d44"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val main: JSONObject = response.getJSONObject("main")
                val weather = response.getJSONArray("weather")
                val temp = main.getString("temp")
                val mintemp = main.getString("temp_min")
                val maxtemp = main.getString("temp_max")
                val des = weather.getJSONObject(0)
                val description = des.getString("main")
                if (description.contains("Rain")) {
                    image.setImageResource(R.drawable.rain)
                }
                if (description.contains("Clear")) {
                    image.setImageResource(R.drawable.sunny1)
                }
                if (description.contains("Clouds")) {
                    image.setImageResource(R.drawable.cloudy)
                }
                if (description.contains("Smoke")) {
                    image.setImageResource(R.drawable.darkclouds)
                }
                if (description.contains("Haze")) {
                    image.setImageResource(R.drawable.darkclouds)
                }
                val temp1=temp.toFloat()
                if(temp1<5){
                    image.setImageResource(R.drawable.snow)
                }
                val mydate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
                detail.text = "${cityname.capitalize()} \n $mydate"
                temperature.text = "$temp°C"
                minimum_temperature.text = "Min\n $mintemp°C"
                maximum_temperature.text = "Max\n $maxtemp°C"
            },
            {
                Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
    }
