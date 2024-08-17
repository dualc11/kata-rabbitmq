package com.example.kata.twitter.exception

data class ForecastException(
    val locationId: String,
    override val message: String = "Error while fetching location forecast.(locationId=$locationId)",
) : RuntimeException()
