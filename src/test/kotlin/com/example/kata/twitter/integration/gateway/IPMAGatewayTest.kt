package com.example.kata.twitter.integration.gateway

import com.example.kata.twitter.gateway.IPMAGateway
import com.example.kata.twitter.helper.validJsonPrediction
import com.example.kata.twitter.model.MeteorologicData
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestPropertySource
import reactor.test.StepVerifier
import java.time.LocalDateTime

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

    @Nested
    @DisplayName("#getWeatherPrediction")
    inner class GetWeatherPrediction {
        @Nested
        @DisplayName("When locationId is valid")
        inner class WhenLocationIsValid {
            @Test
            fun `it should returns the weather forecast`() {
                val locationId = 1231231
                val expectedResult: String = validJsonPrediction().trimIndent()

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
    }

    @Nested
    @DisplayName("#getWeatherObservation")
    inner class GetWeatherObservation {
        @Nested
        @DisplayName("When locationId is valid")
        inner class WhenLocationIsValid {
            @Test
            fun `it should returns the weather forecast`() {
                val expectedResult: String = validJsonObservation().trimIndent()

                mockWebServer.enqueue(
                    MockResponse()
                        .setBody(expectedResult)
                        .addHeader("Content-Type", "application/json"),
                )

                StepVerifier.create(subject.getWeatherObservation())
                    .assertNext { response ->
                        assertThat(response)
                            .usingRecursiveAssertion()
                            .isEqualTo(
                                mapOf(
                                    LocalDateTime.parse("2024-08-31T01:00") to
                                        mapOf(
                                            "1210881" to
                                                MeteorologicData(
                                                    intensidadeVentoKM = 2.0,
                                                    temperatura = 25.0,
                                                    radiacao = -99.0,
                                                    idDireccVento = 4.0,
                                                    precAcumulada = 0.0,
                                                    intensidadeVento = 0.8,
                                                    humidade = 72.0,
                                                    pressao = -99.0,
                                                ),
                                            "1210880" to
                                                MeteorologicData(
                                                    intensidadeVentoKM = 2.9,
                                                    temperatura = 25.0,
                                                    radiacao = -99.0,
                                                    idDireccVento = 4.0,
                                                    precAcumulada = 0.0,
                                                    intensidadeVento = 0.8,
                                                    humidade = 72.0,
                                                    pressao = -99.0,
                                                ),
                                        ),
                                ),
                            )
                    }
                    .verifyComplete()
            }
        }
    }
}

private fun validJsonObservation() =
    """
    {
      "2024-08-31T01:00": {
        "1210881": {
          "intensidadeVentoKM": 2.0,
          "temperatura": 25,
          "radiacao": -99,
          "idDireccVento": 4,
          "precAcumulada": 0,
          "intensidadeVento": 0.8,
          "humidade": 72,
          "pressao": -99
        },
        "1210880": {
          "intensidadeVentoKM": 2.9,
          "temperatura": 25,
          "radiacao": -99,
          "idDireccVento": 4,
          "precAcumulada": 0,
          "intensidadeVento": 0.8,
          "humidade": 72,
          "pressao": -99
        }
      }
    }
    """.trimIndent()
