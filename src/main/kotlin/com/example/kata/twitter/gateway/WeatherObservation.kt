package com.example.kata.twitter.gateway

import reactor.core.publisher.Mono

interface WeatherObservation {
    // TODO: Should it return a mono? Should the service layer know about the mono?
    fun getWeatherObservation(observationId: String): Mono<String>
}
