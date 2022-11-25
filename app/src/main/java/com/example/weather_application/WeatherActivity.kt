package com.example.weather_application

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_application.databinding.WeatherLayoutBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class WeatherActivity : AppCompatActivity() {

    lateinit var binding : WeatherLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WeatherLayoutBinding.inflate(layoutInflater)

        //hiding the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        val view = binding.root
        setContentView(view)

        val intent = intent
        val firstCity = intent.getStringExtra("firstCityEntered")
        if (firstCity != null) {
            getWeatherByCity(firstCity)
        }

        binding.btnSearch.setOnClickListener {
            val city = binding.editCity.text.toString()
            if (city.isEmpty()) {
                Toast.makeText(this, "Please provide a valid city.", Toast.LENGTH_SHORT).show()
            } else {
                getWeatherByCity(city)
            }
        }
    }

    private fun getWeatherByCity(city: String) {
        // Create RetroFit Instance
        val retrofit =
            Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/weather/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        // Call WeatherAPI
        val weatherService = retrofit.create(WeatherApi::class.java)
        val result = weatherService.getWeatherByCity(city)

        result.enqueue(object : Callback<WeatherResult> {
            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                if (response.isSuccessful) {
                    val result = response.body() ?: return

                    binding.run {
                        address.text = result.name
                        val updatedAtText = "Updated at: " + SimpleDateFormat(
                            "dd/MM/yyyy hh:mm a",
                            Locale.ENGLISH
                        ).format(Date(result.dt * 1000))
                        updatedAt.text = updatedAtText

                        status.text = result.weather.first().description
                        temp.text =
                            applicationContext.getString(R.string.celsius, result.main.temp.toString())
                        tempMin.text = applicationContext.getString(
                            R.string.celsius,
                            "Min temp: " + result.main.temp_min.toString()
                        )
                        tempMax.text = applicationContext.getString(
                            R.string.celsius,
                            "Max temp: " + result.main.temp_max.toString()
                        )

                        sunrise.text = SimpleDateFormat(
                            "hh:mm a",
                            Locale.ENGLISH
                        ).format(Date(result.sys.sunrise * 1000))
                        sunset.text = SimpleDateFormat(
                            "hh:mm a",
                            Locale.ENGLISH
                        ).format(Date(result.sys.sunset * 1000))
                        wind.text = result.wind.speed.toString()
                        pressure.text = result.main.pressure.toString()
                        humidity.text = result.main.humidity.toString()

                        loader.visibility = View.GONE
                        mainContainer.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                Toast.makeText(applicationContext, "Server Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}