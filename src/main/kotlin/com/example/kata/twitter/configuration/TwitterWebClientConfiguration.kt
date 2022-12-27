package com.example.kata.twitter.configuration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.netty.handler.codec.Headers
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class TwitterWebClientConfiguration(
        @Value("\${twitter.auth.bearer.token}") private val bearerToken: String,
        @Value("\${twitter.url:https://api.twitter.com/2/}") private val baseUrl: String,
        private val jackson2JsonDecoder: Jackson2JsonDecoder

) {

    @Bean("twitter-web-client")
    fun twitterWebClient(): WebClient =
            WebClient.builder()
                    .baseUrl(baseUrl)
                    .exchangeStrategies(
                        ExchangeStrategies
                            .builder()
                            .codecs { configure ->
                                configure.defaultCodecs().jackson2JsonDecoder(jackson2JsonDecoder)
                            }
                            .build()
                    )
                    .defaultHeaders { h -> h.setBearerAuth(bearerToken)}
                    .build()
}