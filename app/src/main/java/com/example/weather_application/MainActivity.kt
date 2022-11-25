package com.example.weather_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var btnCheckWeather: Button
    private lateinit var firstCity: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hiding the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportActionBar?.hide();

        setContentView(R.layout.activity_main)

        btnCheckWeather = findViewById(R.id.btnCheckWeather)
        firstCity = findViewById(R.id.firstCity)

        btnCheckWeather.setOnClickListener {
            val city = firstCity.text.toString()
            if(city.isEmpty()) {
                Toast.makeText(this, "Please provide a valid city.", Toast.LENGTH_SHORT).show()
            }else {
                val intent = Intent(this, WeatherActivity::class.java)
                intent.putExtra("firstCityEntered", city)
                startActivity(intent)
            }
        }
    }
}