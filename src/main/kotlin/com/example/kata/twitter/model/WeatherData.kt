package com.example.kata.twitter.model

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherData(
    @JsonProperty("owner") val owner: String,
    @JsonProperty("country") val country: String,
    @JsonProperty("data") val data: List<WeatherDetail>,
    @JsonProperty("globalIdLocal") val globalIdLocal: Int,
    @JsonProperty("dataUpdate") val dataUpdate: String
)

data class WeatherDetail(
    @JsonProperty("precipitaProb") val precipitaProb: String,
    @JsonProperty("tMin") val tempMin: String,
    @JsonProperty("tMax") val tempMax: String,
    @JsonProperty("predWindDir") val predWindDir: String,
    @JsonProperty("idWeatherType") val idWeatherType: Int,
    @JsonProperty("classWindSpeed") val classWindSpeed: Int,
    @JsonProperty("longitude") val longitude: String,
    @JsonProperty("forecastDate") val forecastDate: String,
    @JsonProperty("latitude") val latitude: String,
    @JsonProperty("classPrecInt") val classPrecInt: Int? = null // Optional field
)
