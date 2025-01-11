package com.example.kata.twitter.gateway

import com.example.kata.twitter.model.MeteorologicData
import com.example.kata.twitter.model.WeatherData
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class IPMAGateway(
    @Qualifier("ipma-web-client")
    private val webClient: WebClient,
    private val jackson2JsonDecoder: Jackson2JsonDecoder,
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

    // TODO: When an error occurs, it will throw an exception
    override fun getWeatherObservation(): Mono<Map<LocalDateTime?, Map<String, MeteorologicData?>>?> =
        webClient
            .get()
            .uri("open-data/observation/meteorology/stations/observations.json")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .retrieve()
            .bodyToMono(Map::class.java)
            .doFirst {
                logger.info { "Getting weather observations" }
            }
            .doOnError {
                logger.error(it) {
                    "Error getting weather observations."
                }
            }
            .map {
                it?.asSequence()?.associate { (timestamp, rawStation) ->
                    logger.debug { "Observation time:$timestamp raw:$rawStation" }
                    val rawStationObservations = (rawStation as Map<*, *>)
                    val stationObservation =
                        rawStationObservations.asSequence().associate { (stationId, rawStationObservation) ->
                            when (rawStationObservation == null) {
                                true -> stationId.toString() to null
                                false -> {
                                    val stationObservation =
                                        convertRawStationToObservation(rawStationObservation)
                                    stationId.toString() to stationObservation
                                }
                            }
                        }
                    val localDateTimeObservations = timestamp?.let { LocalDateTime.parse(it.toString()) }
                    localDateTimeObservations to stationObservation
                }
            }

    private fun convertRawStationToObservation(rawStationObservation: Any?): MeteorologicData? {
        val rawMap = rawStationObservation as HashMap<String, String>
        val json = jackson2JsonDecoder.objectMapper.writeValueAsString(rawMap)
        return jackson2JsonDecoder.objectMapper.readValue(
            json,
            MeteorologicData::class.java,
        )
    }
}
