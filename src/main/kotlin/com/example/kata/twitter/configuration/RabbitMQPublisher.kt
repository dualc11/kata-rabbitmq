package com.example.kata.twitter.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RabbitMQPublisher(
    @Value("\${rabbitmq.publish.exchange:twitter-topic}") private val exchange: String,
    @Value("\${rabbitmq.publish.routingKey:twitter-topics}") private val routingKey: String,
    private val connection: Connection,
    private val objectMapper: ObjectMapper,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    lateinit var channel: Channel

    @PostConstruct
    fun postConstruct() {
        channel =
            connection
                .createChannel()
    }

    fun publish(message: Any) {
        logger.info("Publishing message $message")
        channel.basicPublish(exchange, routingKey, null, objectMapper.writeValueAsBytes(message))
    }
}
