package com.example.kata.twitter.controller

import com.example.kata.twitter.configuration.RabbitMQPublisher
import com.example.kata.twitter.gateway.TwitterGateway
import com.example.kata.twitter.model.SearchResult
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class TweetController(
        private val twitterGateway: TwitterGateway,
        private val rabbitMQPublisher: RabbitMQPublisher,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/subscribe/topic/{topic}")
    fun subscribeTweet(@PathVariable topic: String): ResponseEntity<SearchResult>{
        logger.info("Subscribe to topic $topic")

        val res = twitterGateway.getTweet(topic)
        res.data.forEach {
            rabbitMQPublisher.publish(message = it)
        }
        return ResponseEntity.ok().body(res)
    }
}