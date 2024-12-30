package com.example.kata.twitter.controller

import com.example.kata.twitter.configuration.RabbitMQPublisher
import com.example.kata.twitter.model.WeatherData
import com.example.kata.twitter.service.WeatherForecastService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class SubscribeController(
    private val weatherForecastService: WeatherForecastService,
    private val rabbitMQPublisher: RabbitMQPublisher,
) {
    private val logger = KotlinLogging.logger { }

    @GetMapping("/subscribe/location/{locationId}")
    fun subscribe(
        @PathVariable locationId: String,
    ): ResponseEntity<WeatherData> {
        logger.info { "Subscribe to topic $locationId" }

        val res =
            weatherForecastService
                .getWeatherForecast(locationId)

        rabbitMQPublisher.publish(message = res)
        return ResponseEntity.ok().body(res)
    }

    // TODO Create endpoint to purge rabbitmq queue
    @GetMapping("/observation/{observationId}")
    fun observation(
        @PathVariable observationId: String,
    ): ResponseEntity<String> {
        logger.info { "Getting observation $observationId" }

        val res =
            weatherForecastService
                .getObservation(observationId)
        // rabbitMQPublisher.publish(message = res)
        return ResponseEntity.ok().body(res)
    }
}
