package com.example.kata.twitter.gateway

import com.example.kata.twitter.model.MeteorologicData
import reactor.core.publisher.Mono
import java.time.LocalDateTime

interface WeatherObservation {
    // TODO: Should it return a mono? Should the service layer know about the mono?
    fun getWeatherObservation(): Mono<Map<LocalDateTime?, Map<String, MeteorologicData?>>?>
}
