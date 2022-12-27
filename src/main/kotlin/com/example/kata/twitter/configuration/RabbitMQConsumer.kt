package com.example.kata.twitter.configuration

import com.example.kata.twitter.handler.RabbitMQHandler
import com.rabbitmq.client.*
import org.slf4j.LoggerFactory


class RabbitMQConsumer(
        channel: Channel,
        private val handler: RabbitMQHandler<*>
): DefaultConsumer(channel) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: ByteArray) {
        logger.info("RabbitMQConsumer - Consuming")

        try {
            handler.handle(consumerTag, envelope, properties, body)
            channel.basicAck(envelope.deliveryTag, false)
        }catch (ex: Exception){
            logger.error("Error processing event: (exception=$ex)", ex)
            channel.basicReject(envelope.deliveryTag, true)
        }
    }
}