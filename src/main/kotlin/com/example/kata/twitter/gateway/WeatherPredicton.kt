package com.example.kata.twitter.gateway

import com.example.kata.twitter.model.WeatherData

interface WeatherPredicton {
    fun getWeatherPrediction(locationId: String): WeatherData
}