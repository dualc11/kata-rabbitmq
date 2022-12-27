package com.example.kata.twitter.gateway

import com.example.kata.twitter.model.SearchResult
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class TwitterGateway(
        private val webClient: WebClient
) {

    fun getTweet(topic: String): SearchResult = webClient
            .get()
            .uri("/tweets/search/recent?query=$topic")
            .exchangeToMono{res -> res.bodyToMono<SearchResult>() }
            .block()!!
}