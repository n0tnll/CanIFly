package com.shv.canifly.presentation

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shv.canifly.R
import com.shv.canifly.databinding.FragmentSplashBinding
import com.shv.canifly.presentation.onboarding.OnBoardingFragment.Companion.FINISHED_KEY
import com.shv.canifly.presentation.onboarding.OnBoardingFragment.Companion.ONBOARDING_KEY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding ?: throw RuntimeException("FragmentSplashBinding is null")

    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences(ONBOARDING_KEY, MODE_PRIVATE)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onStartSplash()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lifecycleScope.launch {
            delay(3000)
            onSplashScreenClosed()
            if (onBoardingIsFinished()) {
                findNavController().navigate(R.id.navigate_splashFragment_to_conditionsFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
            }
        }
        _binding = FragmentSplashBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    private fun onBoardingIsFinished(): Boolean {
        return sharedPreferences.getBoolean(FINISHED_KEY, false)
    }

    private fun onStartSplash() {
        sharedPreferences.edit().apply {
            putBoolean(SPLASH_KEY, false)
        }.apply()
    }

    private fun onSplashScreenClosed() {
        sharedPreferences.edit().apply {
            putBoolean(SPLASH_KEY, true)
        }.apply()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(): SplashFragment = SplashFragment()
        const val SPLASH_KEY = "splash_closed"
    }
}