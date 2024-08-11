package com.example.kata.twitter.gateway

import com.example.kata.twitter.model.WeatherData
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class IPMAGateway(
    @Qualifier("ipma-web-client")
    private val webClient: WebClient,
) : WeatherPrediction {
    override fun getWeatherPrediction(locationId: String): Mono<WeatherData> =
        webClient
            .get()
            .uri("open-data/forecast/meteorology/cities/daily/$locationId.json")
            .retrieve()
            .bodyToMono(WeatherData::class.java)
}
