package com.example.kata.twitter.gateway

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.reactive.function.client.WebClient

@SpringBootTest
class IPMAGatewayTest {


    @Autowired
    private lateinit var subject: IPMAGateway

    @Test
    fun `when it gets a valid locationId, it should returns the weather forecast`() {
        val locationId = 1010500

        val res = subject.getWeatherPrediction(locationId.toString())

        assertThat(res)
            .hasFieldOrPropertyWithValue("globalIdLocal", locationId)

    }
}