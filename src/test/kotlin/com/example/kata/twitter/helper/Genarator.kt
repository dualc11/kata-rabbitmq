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

fun validJsonPrediction() =
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
                    """
