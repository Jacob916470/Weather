package com.example.weather.common.entities


/** Esta clase heredara de los datos comunes de nuestra api, la cuales "Wheather", tambien a su vez sera de tipo
 * "List", ya que es el tipo que regresa en nuestra respuesta al momento de consultar en nuestro "Json" */
open class WeatherBase(
    dt: Long,
    humidity: Int,
    temp: Double,
    weather: List<Weather>
)