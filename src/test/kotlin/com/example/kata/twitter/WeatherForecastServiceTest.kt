package com.example.kata.twitter

import com.example.kata.twitter.gateway.WeatherPrediction
import com.example.kata.twitter.helper.weatherData
import com.example.kata.twitter.service.WeatherForecastService
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono

class WeatherForecastServiceTest {
    private val weatherPrediction: WeatherPrediction = mockk()
    private val subject =
        WeatherForecastService(
            weatherPrediction = weatherPrediction,
        )

    @Nested
    @DisplayName("When the location exist,")
    inner class WhenLocation {
        val locationId = "locationId"

        @DisplayName("it should return the weatherData")
        @Test
        fun thenWeather() {
            val expectedRes = weatherData()
            every { weatherPrediction.getWeatherPrediction(eq(locationId)) } returns Mono.just(expectedRes)

            val res = subject.getWeatherForecast(locationId)

            assertThat(res)
                .isEqualTo(expectedRes)
        }
    }
}
