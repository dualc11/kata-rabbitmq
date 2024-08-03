package com.example.kata.twitter.handler

import com.example.kata.twitter.model.WeatherData
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class WeatherForecastHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun handler(objectMapper: ObjectMapper) =
        handlerFor<WeatherData>(objectMapper) {
            val sleepingTime = Random.nextLong(1000, 10000)
            logger.info("Processing event $it... $sleepingTime")
            Thread.sleep(sleepingTime)
            logger.info("Handling successful")
        }
}
