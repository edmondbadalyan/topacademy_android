package com.example.topacademy_android.features.weather.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.topacademy_android.R
import com.example.topacademy_android.databinding.FragmentWeatherDetailBinding
import com.example.topacademy_android.features.weather.domain.model.WeatherType
import com.example.topacademy_android.features.weather.presentation.helper.WeatherUIHelper
import java.text.SimpleDateFormat
import java.util.*

class WeatherDetailFragment : Fragment() {
    
    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        loadWeatherDetails()
    }
    
    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
    
    private fun loadWeatherDetails() {
        // Get data from arguments
        val args = arguments ?: return
        
        val dateTime = args.getLong("dateTime", 0L)
        val temperature = args.getInt("temperature", 0)
        val feelsLike = args.getInt("feelsLike", 0)
        val description = args.getString("description") ?: ""
        val humidity = args.getInt("humidity", 0)
        val windSpeed = args.getFloat("windSpeed", 0.0f)
        val precipitation = args.getInt("precipitation", 0)
        val weatherTypeName = args.getString("weatherType") ?: "SUNNY"
        
        val weatherType = try {
            WeatherType.valueOf(weatherTypeName)
        } catch (e: IllegalArgumentException) {
            WeatherType.SUNNY
        }
        
        // Format date and time
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateTime * 1000
        
        val dateFormat = SimpleDateFormat("EEEE, dd MMM", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        
        val formattedDate = "${dateFormat.format(calendar.time)}, ${timeFormat.format(calendar.time)}"
        
        // Set data to views
        binding.tvDetailDate.text = formattedDate
        binding.tvDetailTemperature.text = getString(R.string.weather_temperature_value, temperature)
        binding.tvDetailDescription.text = description
        binding.tvDetailFeelsLike.text = getString(R.string.weather_temperature_value, feelsLike)
        binding.tvDetailHumidity.text = getString(R.string.weather_percentage_value, humidity)
        binding.tvDetailWind.text = String.format(Locale.getDefault(), "%.1f м/с", windSpeed)
        binding.tvDetailPrecipitation.text = getString(R.string.weather_percentage_value, precipitation)
        
        // Set background
        val backgroundResource = WeatherUIHelper.getBackgroundResource(weatherType)
        binding.detailWeatherBackground.background = ContextCompat.getDrawable(requireContext(), backgroundResource)
        
        // Set weather icon
        val iconResource = WeatherUIHelper.getWeatherIcon(weatherType)
        binding.ivDetailWeatherIcon.setImageResource(iconResource)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 