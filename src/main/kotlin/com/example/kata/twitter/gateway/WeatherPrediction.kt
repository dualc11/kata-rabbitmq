package com.example.kata.twitter.gateway

import com.example.kata.twitter.model.WeatherData

interface WeatherPrediction {
    fun getWeatherPrediction(locationId: String): WeatherData
}
