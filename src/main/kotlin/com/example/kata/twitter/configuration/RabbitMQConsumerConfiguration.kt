package com.example.kata.twitter.configuration

import com.example.kata.twitter.handler.RabbitMQHandler
import com.rabbitmq.client.Connection
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConsumerConfiguration(
    @Value("\${rabbitmq.publish.exchange:queueName}") private val queueName: String,
    @Value("\${rabbitmq.publish.exchange:twitter-topic}") private val exchange: String,
    @Value("\${rabbitmq.publish.routingKey:twitter-topics}") private val routingKey: String,
    private val connection: Connection,
    private val handler: RabbitMQHandler<*>,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostConstruct
    fun postConstruct() {
        val channel =
            connection
                .createChannel()
        channel.queueDeclare(queueName, false, false, false, null)
        channel.queueBind(queueName, exchange, routingKey)
        channel.basicConsume(
            queueName,
            false,
            "",
            false,
            false,
            emptyMap(),
            RabbitMQConsumer(channel, handler),
        )
    }
}
