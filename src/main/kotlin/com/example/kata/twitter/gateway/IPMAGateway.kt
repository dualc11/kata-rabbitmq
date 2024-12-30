package com.example.kata.twitter.gateway

import com.example.kata.twitter.model.WeatherData
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class IPMAGateway(
    @Qualifier("ipma-web-client")
    private val webClient: WebClient,
) : WeatherPrediction, WeatherObservation {
    private val logger = KotlinLogging.logger {}

    override fun getWeatherPrediction(locationId: String): Mono<WeatherData> {
        return webClient
            .get()
            .uri(calculateWeatherUri(locationId))
            .retrieve()
            .bodyToMono(WeatherData::class.java)
            .doFirst { logger.info { "Getting weather forecast for $locationId" } }
            .doOnError {
                logger.error(it) {
                    "Error getting weather forecast. (locationId=$locationId)"
                }
            }
            .doOnSuccess { it }
    }

    private fun calculateWeatherUri(locationId: String): String {
        return "open-data/forecast/meteorology/cities/daily/$locationId.json"
    }

    override fun getWeatherObservation(observationId: String): Mono<String> =
        webClient
            .get()
            .uri("open-data/observation/meteorology/stations/observations.json")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .retrieve()
            .bodyToMono(String::class.java)
            .doFirst {
                logger.info { "Getting weather observation for $observationId" }
            }
            .doOnError {
                logger.error(it) {
                    "Error getting weather observation. (observationId=$observationId)"
                }
            }
            .doOnSuccess { it }
}
