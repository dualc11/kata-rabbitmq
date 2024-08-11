package com.example.kata.twitter.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(IPMAConfiguration::class)
class IPMAWebClientConfiguration(
    private val ipmaConfiguration: IPMAConfiguration,
    private val jackson2JsonDecoder: Jackson2JsonDecoder,
) {
    @Bean("ipma-web-client")
    fun twitterWebClient(): WebClient =
        WebClient.builder()
            .baseUrl(ipmaConfiguration.url)
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

@ConfigurationProperties(prefix = "ipma", ignoreUnknownFields = true)
data class IPMAConfiguration(
    val url: String = "https://api.ipma.pt/",
)
