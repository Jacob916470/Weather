package com.example.weather.mainModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.BR
import com.example.weather.R
import com.example.weather.common.entities.Forecast
import com.example.weather.common.utils.CommonUtils
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.mainModule.view.adapters.ForecastAdapter
import com.example.weather.mainModule.view.adapters.OnClickListener
import com.example.weather.mainModule.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var mBinding: ActivityMainBinding

    private lateinit var adapter: ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupViewModel()
        setupObservers()
        setupAdapter()
        setupRecyclerView()
    }

    private fun setupViewModel() {
        val vm: MainViewModel by viewModels()
        mBinding.lifecycleOwner = this
        mBinding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        mBinding.viewModel?.let {
            it.getSnackbarMesg().observe(this) { resMsg ->
                /** Mensaje que proviene desde "MainViewModel", el cual es aquel que mandara el error */
                Snackbar.make(mBinding.root, resMsg, Snackbar.LENGTH_LONG).show()
            }
            it.getRestul().observe(this){result ->
                adapter.submitList(result.hourly)
            }
        }
    }

    private fun setupAdapter() {
        adapter = ForecastAdapter(this)
    }

    private fun setupRecyclerView() {
        mBinding.recyclerView.apply {
            /** Con esto se optimizará mas los recursos */
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    /** Se sobre escribe método "onStart", para que asi, se pueda lanzar esta peticion a nuestra "API Openweather" */
    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            mBinding.viewModel?.getWeatherAndForeCast(
                19.68543919820006,
                -99.2355141576711,
                "6a5c325c9265883997730d09be2328e8",
                "",
                "metric",
                "en"
            )
        }
    }

    /** OnClickListener*/
    override fun onClick(forecast: Forecast) {
        Snackbar.make(mBinding.root, CommonUtils.getFullDate(forecast.dt), Snackbar.LENGTH_LONG)
            .show()
    }
}