package com.pixeltodo.app.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v7/weather/now")
    suspend fun getCurrentWeather(
        @Query("location") location: String,
        @Query("key") key: String
    ): WeatherResponse
}

data class WeatherResponse(
    val code: String,
    val now: CurrentWeather?
)

data class CurrentWeather(
    val text: String,
    val temp: String,
    val icon: String?
)

enum class WeatherType(val code: String, val displayName: String) {
    SUNNY("0", "晴"),
    CLOUDY("1", "多云"),
    OVERCAST("2", "阴"),
    FOG("3", "雾"),
    DRIZZLE("4", "毛毛雨"),
    RAIN("5", "雨"),
    HEAVY_RAIN("6", "大雨"),
    THUNDERSTORM("7", "雷暴"),
    SNOW("8", "雪"),
    HEAVY_SNOW("9", "大雪")
}

fun mapWeatherCode(code: String): WeatherType {
    return when (code) {
        "0", "1" -> WeatherType.SUNNY
        "2", "3" -> WeatherType.CLOUDY
        "4", "5" -> WeatherType.RAIN
        "6", "7" -> WeatherType.THUNDERSTORM
        "8", "9" -> WeatherType.SNOW
        else -> WeatherType.CLOUDY
    }
}