package com.example.kata.twitter.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Envelope
import org.slf4j.LoggerFactory

class RabbitMQHandler<T>(
        private val f: (T) -> Unit,
        private val type: Class<T>,
        private val objectMapper: ObjectMapper
){
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun handle(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?){
        val event = objectMapper.readValue(body, type)
        f.invoke(event)
    }
}


inline fun <reified T: Any> handlerFor(objectMapper: ObjectMapper, noinline f: (T) -> Unit) =
    RabbitMQHandler(f, T::class.java, objectMapper)
