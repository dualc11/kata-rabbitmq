package com.example.kata.twitter.service

import com.example.kata.twitter.exception.ForecastException
import com.example.kata.twitter.gateway.WeatherObservation
import com.example.kata.twitter.gateway.WeatherPrediction
import com.example.kata.twitter.model.MeteorologicData
import com.example.kata.twitter.model.WeatherData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.LocalDateTime

@Service
class WeatherForecastService(
    private val weatherPrediction: WeatherPrediction,
    private val weatherObservation: WeatherObservation,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun getWeatherForecast(locationId: String): WeatherData {
        return weatherPrediction
            .getWeatherPrediction(locationId = locationId)
            .block(Duration.ofMillis(1000))
            ?: throw ForecastException(locationId)
    }

    fun getObservation(observationId: String): Mono<Map<LocalDateTime?, Map<String, MeteorologicData?>>?> =
        weatherObservation
            .getWeatherObservation()
}
