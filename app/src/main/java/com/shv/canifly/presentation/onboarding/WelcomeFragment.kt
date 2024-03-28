package com.shv.canifly.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.shv.canifly.R
import com.shv.canifly.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentWelcomeBinding.inflate(
           inflater,
           container,
           false
       )
        val btnNext = binding.btnNext
        val viewPager = activity?.findViewById<ViewPager2>(R.id.vp_start_screens)

        btnNext.setOnClickListener {
            viewPager?.currentItem = NEXT_SCREEN_NUMBER
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(): WelcomeFragment {
            return WelcomeFragment()
        }

        private const val NEXT_SCREEN_NUMBER = 1
    }
}