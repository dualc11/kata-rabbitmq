package com.example.kata.twitter.gateway

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestPropertySource
import reactor.test.StepVerifier

@SpringBootTest
@TestPropertySource(properties = ["ipma.url=http://localhost:\${mockPort}"])
class IPMAGatewayTest {
    companion object {
        private val mockWebServer = MockWebServer()

        @BeforeAll
        fun beforeAll() {
            mockWebServer.start()
        }

        @AfterAll
        fun afterAll() {
            mockWebServer.shutdown()
        }

        @JvmStatic
        @DynamicPropertySource
        fun mockPortProperty(registry: DynamicPropertyRegistry) {
            registry.add("mockPort") { mockWebServer.port }
        }
    }

    @Autowired
    private lateinit var subject: IPMAGateway

    @Test
    fun `when it gets a valid locationId, it should returns the weather forecast`() {
        val locationId = 1231231
        val expectedResult: String =
            """
            {
              "owner": "IPMA",
              "country": "PT",
              "data": [
                {
                  "precipitaProb": "0.0",
                  "tMin": "16.7",
                  "tMax": "26.6",
                  "predWindDir": "NW",
                  "idWeatherType": 3,
                  "classWindSpeed": 2,
                  "longitude": "-8.6535",
                  "forecastDate": "2024-07-28",
                  "latitude": "40.6413"
                },
                {
                  "precipitaProb": "60.0",
                  "tMin": "17.2",
                  "tMax": "32.1",
                  "predWindDir": "NW",
                  "idWeatherType": 8,
                  "classWindSpeed": 2,
                  "longitude": "-8.6535",
                  "forecastDate": "2024-07-29",
                  "classPrecInt": 3,
                  "latitude": "40.6413"
                },
                {
                  "precipitaProb": "1.0",
                  "tMin": "19.3",
                  "tMax": "28.4",
                  "predWindDir": "NW",
                  "idWeatherType": 3,
                  "classWindSpeed": 1,
                  "longitude": "-8.6535",
                  "forecastDate": "2024-07-30",
                  "latitude": "40.6413"
                },
                {
                  "precipitaProb": "2.0",
                  "tMin": "18.5",
                  "tMax": "24.6",
                  "predWindDir": "NW",
                  "idWeatherType": 4,
                  "classWindSpeed": 1,
                  "longitude": "-8.6535",
                  "forecastDate": "2024-07-31",
                  "latitude": "40.6413"
                },
                {
                  "precipitaProb": "2.0",
                  "tMin": "18.4",
                  "tMax": "23.5",
                  "predWindDir": "N",
                  "idWeatherType": 3,
                  "classWindSpeed": 2,
                  "longitude": "-8.6535",
                  "forecastDate": "2024-08-01",
                  "latitude": "40.6413"
                }
              ],
              "globalIdLocal": 1231231,
              "dataUpdate": "2024-07-28T15:31:02"
            }
            """.trimIndent()

        // val res = subject.getWeatherPrediction(locationId.toString())

        mockWebServer.enqueue(
            MockResponse()
                .setBody(expectedResult)
                .addHeader("Content-Type", "application/json"),
        )

        StepVerifier.create(subject.getWeatherPrediction(locationId.toString()))
            .assertNext { response ->
                assertThat(response)
                    .hasFieldOrPropertyWithValue("globalIdLocal", locationId)
            }
            .verifyComplete()
    }
}
