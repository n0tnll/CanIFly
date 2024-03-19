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
import com.shv.canifly.databinding.NfzBottomSheetContentBinding
import com.shv.canifly.domain.entity.Airport

class NfzInfoBottomSheet : BottomSheetDialogFragment() {

    private var _binding: NfzBottomSheetContentBinding? = null
    private val binding: NfzBottomSheetContentBinding
        get() = _binding ?: throw RuntimeException("NfzBottomSheetContentBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NfzBottomSheetContentBinding.inflate(
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
        val airport = arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(NFZ_ARG, Airport::class.java)
            } else {
                it.getParcelable<Airport>(NFZ_ARG)
            }
        }
        airport?.let {
            with(binding) {
                tvAirportName.text = it.name
                tvAirportType.text = it.type?.name
                tvNfzRadius.text = it.approximateRadius.toString() + " killomers"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newInstance(): NfzInfoBottomSheet {
            return NfzInfoBottomSheet().apply {
                arguments = Bundle()
            }
        }
        const val NFZ_ARG = "nfz"
        const val TAG = "NfzInfoBottomSheet"
    }
}