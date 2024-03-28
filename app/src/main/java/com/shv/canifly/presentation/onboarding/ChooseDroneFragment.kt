package com.shv.canifly.presentation.onboarding

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shv.canifly.R
import com.shv.canifly.databinding.FragmentChooseDroneBinding
import com.shv.canifly.domain.entity.Drone
import com.shv.canifly.presentation.onboarding.OnBoardingFragment.Companion.FINISHED_KEY
import com.shv.canifly.presentation.onboarding.OnBoardingFragment.Companion.ONBOARDING_KEY

class ChooseDroneFragment : Fragment() {

    private var _binding: FragmentChooseDroneBinding? = null
    private val binding: FragmentChooseDroneBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseDroneBinding is null")

    private val dronesList = mutableListOf<Drone>()
    private var currentDrone: Drone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dronesList.addAll(getListDroneFromAssets(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseDroneBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            dronesList.toList()
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDrones.adapter = arrayAdapter

        binding.spinnerDrones.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentDrone = binding.spinnerDrones.selectedItem as? Drone
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        binding.btnFinish.setOnClickListener {
            currentDrone?.let {
                onBoardingIsFinished(it.model)
                findNavController().navigate(R.id.action_onBoardingFragment_to_conditionsFragment)
            }
        }
    }

    private fun onBoardingIsFinished(droneModel: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(ONBOARDING_KEY, MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString(DRONE_KEY, droneModel)
            putBoolean(FINISHED_KEY, true)
        }.apply()
    }

    private fun getListDroneFromAssets(context: Context): List<Drone> {
        val jsonString = context.assets.open(JSON_NAME).bufferedReader().use {
            it.readText()
        }
        return Gson().fromJson(jsonString, object : TypeToken<List<Drone>>() {}.type)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val JSON_NAME = "drones.json"
        const val DRONE_KEY = "chosen_drone_model"
        fun newInstance(): ChooseDroneFragment {
            return ChooseDroneFragment()
        }
    }
}