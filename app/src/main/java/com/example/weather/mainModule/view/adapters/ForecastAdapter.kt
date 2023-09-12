package com.example.weather.mainModule.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.BR
import com.example.weather.R
import com.example.weather.common.entities.Forecast
import com.example.weather.databinding.ItemWeatherBinding


class ForecastAdapter(private val listener: OnClickListener) :
    ListAdapter<Forecast, RecyclerView.ViewHolder>(ForecastDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_weather, parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val forecast = getItem(position)
        with(holder as ViewHolder) {
            holder.binding?.setVariable(BR.forecast, forecast)
            /** Este método se utilizá para reflejar los cambios rapidamente, y es necesario dentro de un adaptador */
            holder.binding?.executePendingBindings()
            setListener(forecast)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemWeatherBinding>(view)

        fun setListener(forecast: Forecast) {
            binding?.root?.setOnClickListener {
                listener.onClick(forecast)
            }
        }
    }

    class ForecastDiffCallback : DiffUtil.ItemCallback<Forecast>() {
        /** oldItem = Le pondremos por la propiedad por lla cual queremos distingirlo, en este caso a ser un pronostico por hora
         * precisamente sera en el dato de la fecha donde nunca se podra repetir */
        override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean =
            oldItem.dt == newItem.dt

        override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean =
            oldItem == newItem
    }
}