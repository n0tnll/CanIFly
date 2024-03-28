package com.shv.canifly.presentation.onboarding

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shv.canifly.databinding.FragmentOnBoardingBinding
import com.shv.canifly.presentation.adapters.viewpager.ViewPagerAdapter

class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding: FragmentOnBoardingBinding
        get() = _binding ?: throw RuntimeException("FragmentOnBoardingBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(
            inflater,
            container,
            false
        )

        val fragmentList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayListOf(
                WelcomeFragment.newInstance(),
                LocationRequestFragment.newInstance(),
                NotificationRequestFragment.newInstance(),
                ChooseDroneFragment.newInstance()
            )
        } else {
            arrayListOf(
                WelcomeFragment.newInstance(),
                LocationRequestFragment.newInstance(),
                ChooseDroneFragment.newInstance()
            )
        }

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.vpStartScreens.adapter = adapter
        binding.vpStartScreens.isUserInputEnabled = false
        binding.dotsIndicator.attachTo(binding.vpStartScreens)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(): OnBoardingFragment = OnBoardingFragment()

        const val ONBOARDING_KEY = "OnBoardingFragment"
        const val FINISHED_KEY = "finished"
    }
}

