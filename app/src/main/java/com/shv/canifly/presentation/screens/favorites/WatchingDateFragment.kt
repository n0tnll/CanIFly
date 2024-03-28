package com.shv.canifly.presentation.screens.favorites

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.shv.canifly.R
import com.shv.canifly.data.extensions.toLocalDate
import com.shv.canifly.databinding.FragmentWatchingDateBinding
import com.shv.canifly.domain.entity.HourlyWeather
import com.shv.canifly.domain.entity.WatchingDate
import com.shv.canifly.presentation.adapters.WatchingDateAdapter
import com.shv.canifly.presentation.screens.WeatherConditionsViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class WatchingDateFragment : Fragment() {

    private var _binding: FragmentWatchingDateBinding? = null
    private val binding: FragmentWatchingDateBinding
        get() = _binding ?: throw RuntimeException("FragmentWatchingDateBinding is null")

    private val list = mutableListOf<WatchingDate>()
    private var forecast: Map<Int, List<HourlyWeather>> = mutableMapOf()
    private var dateTimeToWatch: LocalDateTime? = null

    private val viewModel: WeatherConditionsViewModel by activityViewModels()
    private val adapter by lazy {
        WatchingDateAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchingDateBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        binding.btnAddDate.setOnClickListener {
            showDateDialogPicker()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { weatherState ->
                    weatherState.weatherInfo?.weatherPerDayData?.let {
                        forecast = it
                        adapter.submitList(list)
                    }
                }
            }
        }
    }


    private fun showDateDialogPicker() {
        val constraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .build()

        val picker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraints)
            .setTitleText(getString(R.string.select_fly_date))
            .build()

        picker.addOnPositiveButtonClickListener {
            showTimeDialogPicker(it.toLocalDate())
        }
        picker.addOnCancelListener {
            it.cancel()
        }
        picker.show(parentFragmentManager, DATE_PICKER_TAG)
    }

    private fun showTimeDialogPicker(date: LocalDate) {
        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setTitleText(getString(R.string.select_fly_time))
            .setHour(DEFAULT_HOUR)
            .setMinute(DEFAULT_MINUTES)
            .build()

        picker.addOnPositiveButtonClickListener {
            dateTimeToWatch = LocalDateTime.of(date, LocalTime.of(picker.hour, picker.minute))
            dateTimeToWatch?.let {
                if (forecast.isNotEmpty()) {
                    createWatchingDate(it)
                }
            }
        }
        picker.addOnCancelListener {
            it.cancel()
        }
        picker.show(parentFragmentManager, TIME_PICKER_TAG)
    }

    private fun createWatchingDate(dateTime: LocalDateTime) {
        val currentDay = LocalDate.now().dayOfWeek
        val selectedDay = dateTime.dayOfWeek
        var indexSelectedDay = selectedDay.value - currentDay.value
        if (indexSelectedDay < 0)
            indexSelectedDay += 7
        val watchingForecast = forecast[indexSelectedDay]
        watchingForecast?.let { listH ->
            val hourlyWeather = listH.find {
                it.time.hour == dateTime.hour
            }
            if (hourlyWeather != null) {
                val watchingDate = WatchingDate(
                    dateTime = dateTime,
                    conditions = hourlyWeather,
                    flyStatus = true,
                    isCompleted = false
                )
               list.add(watchingDate)
               adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvWatchingDates.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val DATE_PICKER_TAG = "date_picker"
        private const val TIME_PICKER_TAG = "time_picker"
        private const val DEFAULT_HOUR = 12
        private const val DEFAULT_MINUTES = 30
    }
}