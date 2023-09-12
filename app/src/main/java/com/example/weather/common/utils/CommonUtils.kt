package com.example.weather.common.utils

import com.example.weather.common.entities.Weather
import java.text.SimpleDateFormat
import java.util.Locale

object CommonUtils {
    /** epoch = es un estandar para trabajar las horas
     * epoch = lo que hace es guardar el tiempo que ha transcurrido desde 1970, pero lo hace en segundos, no en
     * milisegundos como se hace en android, o Java en general */
    fun getHour(epoch: Long): String = getFormatedTime(epoch, "HH:mm")

    fun getFullDate(epoch: Long): String = getFormatedTime(epoch, "dd/MM/yy HH:mm")

    private fun getFormatedTime(epoch: Long, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(epoch * 1_000)
    }

    fun getWeatherMain(weather: List<Weather>?): String{
        return if (!weather.isNullOrEmpty()) weather[0].main else "-"
    }

    fun getWeatherDescription(weather: List<Weather>?): String{
        return if (!weather.isNullOrEmpty()) weather[0].description else "-"
    }
}