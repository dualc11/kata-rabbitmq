package com.example.kata.twitter.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class IPMAWebClientConfiguration(
    private val jackson2JsonDecoder: Jackson2JsonDecoder,
) {
    @Bean("ipma-web-client")
    fun twitterWebClient(): WebClient =
        WebClient.builder()
            .baseUrl("https://api.ipma.pt/")
            .exchangeStrategies(
                ExchangeStrategies
                    .builder()
                    .codecs { configure ->
                        configure.defaultCodecs().jackson2JsonDecoder(jackson2JsonDecoder)
                    }
                    .build(),
            )
            .build()
}
