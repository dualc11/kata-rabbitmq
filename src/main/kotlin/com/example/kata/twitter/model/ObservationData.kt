package com.example.kata.twitter.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MeteorologicData(
    @JsonProperty("intensidadeVentoKM")
    val intensidadeVentoKM: Double,
    @JsonProperty("temperatura")
    val temperatura: Double,
    @JsonProperty("radiacao")
    val radiacao: Double,
    @JsonProperty("idDireccVento")
    val idDireccVento: Double,
    @JsonProperty("precAcumulada")
    val precAcumulada: Double,
    @JsonProperty("intensidadeVento")
    val intensidadeVento: Double,
    @JsonProperty("humidade")
    val humidade: Double,
    @JsonProperty("pressao")
    val pressao: Double,
)

data class DataByStation(
    val stationId: String,
    val data: MeteorologicData,
)

data class ObservationData(
    val stations: Map<String, String>,
)
