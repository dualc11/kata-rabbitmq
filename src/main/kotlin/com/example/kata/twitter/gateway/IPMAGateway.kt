package com.example.kata.twitter.gateway

import com.example.kata.twitter.model.WeatherData
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class IPMAGateway(
    @Qualifier("ipma-web-client")
    private val webClient: WebClient,
) : WeatherPrediction {
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
        // .awaitSingle()
    }

    private fun calculateWeatherUri(locationId: String): String {
        return "open-data/forecast/meteorology/cities/daily/$locationId.json"
    }
}
