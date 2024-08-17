package com.example.kata.twitter.helper

import com.example.kata.twitter.model.WeatherData
import com.example.kata.twitter.model.WeatherDetail

fun weatherData(
    owner: String = "dummyOwner",
    country: String = "dummyCountry",
    data: List<WeatherDetail> =
        listOf(
            WeatherDetail(
                precipitaProb = "0",
                tempMin = "10",
                tempMax = "20",
                predWindDir = "N",
                idWeatherType = 1,
                classWindSpeed = 2,
                longitude = "0",
                latitude = "0",
                forecastDate = "2023-11-23",
            ),
        ),
    globalIdLocal: Int = 12345,
    dataUpdate: String = "2023-11-23T12:00:00Z",
): WeatherData {
    return WeatherData(
        owner = owner,
        country = country,
        data = data,
        globalIdLocal = globalIdLocal,
        dataUpdate = dataUpdate,
    )
}
