package com.example.kata.twitter.gateway

import com.example.kata.twitter.model.WeatherData
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity

@Service
class IPMAGateway(
    @Qualifier("ipma-web-client")
    private val webClient: WebClient
) : WeatherPredicton {
    override fun getWeatherPrediction(locationId: String): WeatherData = webClient
        .get()
        .uri("open-data/forecast/meteorology/cities/daily/${locationId}.json")
        .exchangeToMono { res -> res.toEntity<WeatherData>() }
        .block()!!
        .body!!


}