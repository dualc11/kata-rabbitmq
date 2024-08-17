package com.example.kata.twitter.service

import com.example.kata.twitter.exception.ForecastException
import com.example.kata.twitter.gateway.WeatherPrediction
import com.example.kata.twitter.model.WeatherData
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class WeatherForecastService(
    private val weatherPrediction: WeatherPrediction,
) {
    fun getWeatherForecast(locationId: String): WeatherData {
        return weatherPrediction
            .getWeatherPrediction(locationId = locationId)
            .block(Duration.ofMillis(1000))
            ?: throw ForecastException(locationId)
    }
}
