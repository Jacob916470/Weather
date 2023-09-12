package com.example.weather.mainModule.model

import com.example.weather.common.entities.WeatherForecastEntity

class MainRepository {
    private val remoteDatabase = RemoteDatabase()

    /** Simplificamos el nombre de la funcion, ya que estamos en una capa donde no requerimos toda la informacion
     * de nuestro acceso a datos */
    suspend fun getWeatherAndForeCast(
        lat: Double,
        lon: Double,
        appId: String,
        exclude: String,
        units: String,
        lang: String
    ): WeatherForecastEntity =
        remoteDatabase.getWeatherForecastByCoordinates(lat, lon, appId, exclude, units, lang)
}