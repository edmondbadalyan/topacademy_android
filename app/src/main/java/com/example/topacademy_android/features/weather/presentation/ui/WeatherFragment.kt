package com.example.topacademy_android.features.weather.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topacademy_android.R
import com.example.topacademy_android.databinding.FragmentWeatherBinding
import com.example.topacademy_android.features.weather.domain.model.WeatherItem
import com.example.topacademy_android.features.weather.presentation.adapter.WeatherForecastAdapter
import com.example.topacademy_android.features.weather.presentation.helper.WeatherUIHelper
import com.example.topacademy_android.features.weather.presentation.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment() {
    
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModel()
    private lateinit var forecastAdapter: WeatherForecastAdapter
    
    // Moscow coordinates as default
    private val latitude = 55.7558
    private val longitude = 37.6176
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
        loadWeatherData()
    }
    
    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
    
    private fun setupRecyclerView() {
        forecastAdapter = WeatherForecastAdapter(emptyList()) { weatherItem ->
            openWeatherDetail(weatherItem)
        }
        
        binding.rvWeatherForecast.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = forecastAdapter
        }
    }
    
    private fun setupObservers() {
        viewModel.weatherForecast.observe(viewLifecycleOwner) { forecast ->
            showWeatherData(forecast.cityName, forecast.country, forecast.items)
        }
        
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoading()
            }
        }
        
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                showError()
            }
        }
    }
    
    private fun setupClickListeners() {
        binding.btnRetry.setOnClickListener {
            loadWeatherData()
        }
    }
    
    private fun loadWeatherData() {
        viewModel.loadWeatherForecast(latitude, longitude)
    }
    
    private fun showWeatherData(cityName: String, country: String, items: List<WeatherItem>) {
        binding.progressBar.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
        binding.weatherContent.visibility = View.VISIBLE
        
        // Set location
        binding.tvLocation.text = getString(R.string.weather_location_format, cityName, country)
        
        // Set current weather (first item)
        if (items.isNotEmpty()) {
            val currentWeather = items.first()
            
            binding.tvCurrentTemperature.text = getString(R.string.weather_temperature_value, currentWeather.temperature)
            binding.tvCurrentDescription.text = currentWeather.description
            binding.tvCurrentFeelsLike.text = getString(R.string.weather_feels_like, currentWeather.feelsLike)
            binding.tvCurrentHumidity.text = getString(R.string.weather_humidity, currentWeather.humidity)
            
            // Set background
            val backgroundResource = WeatherUIHelper.getBackgroundResource(currentWeather.weatherType)
            binding.currentWeatherBackground.background = ContextCompat.getDrawable(requireContext(), backgroundResource)
            
            // Set icon
            val iconResource = WeatherUIHelper.getWeatherIcon(currentWeather.weatherType)
            binding.ivCurrentWeatherIcon.setImageResource(iconResource)
        }
        
        // Update forecast (take first 15 items)
        forecastAdapter.updateData(items.take(15))
    }
    
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.errorLayout.visibility = View.GONE
        binding.weatherContent.visibility = View.GONE
    }
    
    private fun showError() {
        binding.progressBar.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
        binding.weatherContent.visibility = View.GONE
    }
    
    private fun openWeatherDetail(weatherItem: WeatherItem) {
        val bundle = Bundle().apply {
            putLong("dateTime", weatherItem.dateTime)
            putInt("temperature", weatherItem.temperature)
            putInt("feelsLike", weatherItem.feelsLike)
            putString("description", weatherItem.description)
            putInt("humidity", weatherItem.humidity)
            putFloat("windSpeed", weatherItem.windSpeed.toFloat())
            putInt("precipitation", weatherItem.precipitationProbability)
            putString("weatherType", weatherItem.weatherType.name)
        }
        findNavController().navigate(R.id.action_weather_to_weather_detail, bundle)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 