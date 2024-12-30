package com.example.kata.twitter.model

import java.time.LocalDateTime

data class ObservationData(
    val data: Map<LocalDateTime, Map<String, StationData>>,
)

data class StationData(
    val intensidadeVentoKM: Double,
    val temperatura: Double,
    val radiacao: Double,
    val idDireccVento: Int,
    val precAcumulada: Double,
    val intensidadeVento: Double,
    val humidade: Double,
    val pressao: Double,
)
