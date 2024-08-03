package com.example.kata.twitter.controller

import com.example.kata.twitter.configuration.RabbitMQPublisher
import com.example.kata.twitter.gateway.WeatherPrediction
import com.example.kata.twitter.model.WeatherData
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class SubscribeController(
    private val ipmaGateway: WeatherPrediction,
    private val rabbitMQPublisher: RabbitMQPublisher,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/subscribe/location/{locationId}")
    fun subscribeTweet(
        @PathVariable locationId: String,
    ): ResponseEntity<WeatherData> {
        logger.info("Subscribe to topic $locationId")

        val res = ipmaGateway.getWeatherPrediction(locationId)

        rabbitMQPublisher.publish(message = res)
        return ResponseEntity.ok().body(res)
    }
}
