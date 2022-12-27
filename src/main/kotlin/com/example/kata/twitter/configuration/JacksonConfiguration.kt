package com.example.kata.twitter.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.codec.json.Jackson2JsonDecoder

@Configuration
class JacksonConfiguration {

    val jacksonObjectMapper = jacksonObjectMapper().apply {
        propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        configure(DeserializationFeature.USE_LONG_FOR_INTS, true)
    }

    @Bean
    @Primary
    fun jackson2JsonDecoder() = Jackson2JsonDecoder(
          jacksonObjectMapper
    )

    @Bean
    fun customJacksonObjectMapper(): ObjectMapper = jacksonObjectMapper
}