package com.example.weather.mainModule.viewModel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.R
import com.example.weather.common.entities.WeatherForecastEntity
import com.example.weather.mainModule.model.MainRepository
import com.example.weather.mainModule.view.MainActivity
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    /** Variables globales que funcionaran con live data, para reflejar los cambios, tanto de la consulta
     * general de la base de datos, como poder mostrar un mensaje con "snackbar" e incluso ayudarnos en la
     * visualización de nuestro "progressbar"
     */
    private val result = MutableLiveData<WeatherForecastEntity>()
    fun getRestul(): LiveData<WeatherForecastEntity> = result

    /** Sera de tipo "Int", por que nos basaremos en los recursos */
    val snackbarMsg = MutableLiveData<Int>()
    fun getSnackbarMesg() = snackbarMsg

    /** Progressbar */
    private val loaded = MutableLiveData<Boolean>()
    fun isLoaded() = loaded

    /** Función con corrutinas */
    suspend fun getWeatherAndForeCast(lat: Double, lon: Double, appId: String, exclude: String, units: String, lang: String){
        viewModelScope.launch {
            try {
                /* Camino en cuanto sale perfecto */
                /** Lo primero que haremos es indicarle a "loaded.value = false", esto es por que nuestro porgressbar
                 * va a estar visible por default, cuando loaded es false, en cuanto nuestra variable de tipo "Livedata loaded"
                 * sea true, significa que ha terminado de cargar, por lo tanto deberia de ocultarse */
                loaded.value = false
                /** creamos variable para atraer nuestra funcion de nuestro repositorio  */
                val resultServer = repository.getWeatherAndForeCast(lat, lon, appId, exclude, units, lang)
                result.value = resultServer
                if (resultServer.hourly.isNullOrEmpty()){
                    result.value = repository.getWeatherAndForeCast(0.0, 0.0, "", "", "", "")
                    snackbarMsg.value = R.string.main_error_empty_forecast
                    }

            } catch (e: Exception) {
                snackbarMsg.value = R.string.main_error_server
            } finally {
                /** Idependientemente de si hubo un exito o no, debemos hacer invisible nuestro progressbar, por que nuestra
                 * petición ha finalzado */
                loaded.value = true
            }
        }
    }

}