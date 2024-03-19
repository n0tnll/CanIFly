package com.shv.canifly.presentation.dialogs

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shv.canifly.databinding.BadSignalBottomSheetContentBinding
import com.shv.canifly.domain.entity.BadSignalZone

class BadSignalInfoBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BadSignalBottomSheetContentBinding? = null
    private val binding: BadSignalBottomSheetContentBinding
        get() = _binding ?: throw RuntimeException("BadSignalBottomSheetContentBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BadSignalBottomSheetContentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNfzInfo()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener {
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    private fun setNfzInfo() {
        val badSignalZone = arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(BAD_SIGNAL_ARG, BadSignalZone::class.java)
            } else {
                it.getParcelable<BadSignalZone>(BAD_SIGNAL_ARG)
            }
        }
        badSignalZone?.let {
            with(binding) {
                tvDescription.text = it.description
                tvRadius.text = "${it.radius} meters"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newInstance(): BadSignalInfoBottomSheet {
            return BadSignalInfoBottomSheet().apply {
                arguments = Bundle()
            }
        }
        const val BAD_SIGNAL_ARG = "bad zone"
        const val TAG = "BadSignalInfoBottomSheet"
    }
}