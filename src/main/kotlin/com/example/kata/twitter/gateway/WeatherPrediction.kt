package com.example.kata.twitter.gateway

import com.example.kata.twitter.model.WeatherData
import reactor.core.publisher.Mono

interface WeatherPrediction {
    fun getWeatherPrediction(locationId: String): Mono<WeatherData>
}
