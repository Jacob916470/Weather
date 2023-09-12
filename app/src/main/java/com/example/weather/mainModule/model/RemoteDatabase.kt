package com.example.weather.mainModule.model

import com.example.weather.common.dataAccess.WeatherService
import com.example.weather.common.entities.WeatherForecastEntity
import com.example.weather.common.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** En esta clase configuraremos Retrofit */
class RemoteDatabase {

    /** Creamos instancia de retrofit */
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /** Servicio */
    private val service = retrofit.create(WeatherService::class.java)

    /** Funcion con corrutinas para obtener los datos de nuestro servicio */
    suspend fun getWeatherForecastByCoordinates(
        lat: Double, lon: Double, appId: String,exclude: String,
        units: String, lang: String
    ): WeatherForecastEntity = withContext(Dispatchers.IO) {
        service.getWeatherForecastByCoordinates(lat, lon, appId,exclude, units, lang)
    }
}