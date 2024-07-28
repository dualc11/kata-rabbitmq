package com.example.kata.twitter.configuration

import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.ConnectionFactory
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfiguration(
        @Value("\${rabbitmq.auth.username:guest}") private val rabbitMQUsername: String,
        @Value("\${rabbitmq.auth.password:guest}") private val rabbitMQPassword: String,
        @Value("\${rabbitmq.publish.exchange:twitter-topic}") private val exchange: String,
) {
    fun rabbitMQFactory() = ConnectionFactory()
            .apply {
                username = rabbitMQUsername
                password = rabbitMQPassword
            }
    private val rabbitMQConnection = rabbitMQFactory().newConnection("app:kata-twitter component:event-publisher")


    @PostConstruct
    private fun initializeExchange() {
        rabbitMQConnection.createChannel().use {
            it.exchangeDeclare("twitter-topic", BuiltinExchangeType.TOPIC, /* durable */ true)
        }
    }

    @Bean
    fun rabbitMQConnection()= rabbitMQConnection

}