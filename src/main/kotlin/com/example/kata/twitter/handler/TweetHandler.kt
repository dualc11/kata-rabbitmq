package com.example.kata.twitter.handler

import com.example.kata.twitter.model.Tweet
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import kotlin.random.Random

@Component
class TweetHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun handler(objectMapper: ObjectMapper) = handlerFor<Tweet>(objectMapper){

        val sleepingTime = Random.nextLong(1000, 10000)
        logger.info("Processing event $it... $sleepingTime")
        Thread.sleep(sleepingTime)
        if (Random.nextBoolean()){
            throw RuntimeException("Handling error")
        }
        logger.info("Handling successful")

    }
}
