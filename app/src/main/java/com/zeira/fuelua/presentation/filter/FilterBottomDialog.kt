package com.zeira.fuelua.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.core.view.allViews
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseBottomSheetFragment
import com.zeira.fuelua.databinding.FragmentFilterBottomDialogBinding
import com.zeira.fuelua.domain.models.FilterModel
import com.zeira.fuelua.domain.models.FuelType
import com.zeira.fuelua.domain.models.Regions

class FilterBottomDialog(
    val filterCallback: (FilterModel) -> Unit
) : BaseBottomSheetFragment<FragmentFilterBottomDialogBinding>() {

    companion object {
        const val FILTER_BOTTOM_DIALOG_TAG = "FilterBottomDialog"
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFilterBottomDialogBinding
        get() = FragmentFilterBottomDialogBinding::inflate

    private lateinit var bottomSheet: FrameLayout
    private lateinit var behavior: BottomSheetBehavior<FrameLayout>
    private var region: String = ""
    private var availability: Boolean = false
    private var fuelType: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        region = ""
        fuelType = ""

        bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
        behavior = BottomSheetBehavior.from(bottomSheet)

        initRegionGridView()
        initFuelTypeGridView()

        initListeners()

    }

    private fun initListeners() {

        with(binding) {

            filterBtn.setOnClickListener {
                val model = FilterModel(region, availability, fuelType)
                filterCallback(model)
                behavior.state = BottomSheetBehavior.STATE_HIDDEN

            }


            // get region from gridView
            regionsGv.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    for (i in regionsGv.allViews) {
                        i.background = null
                    }
                    view.setBackgroundResource(R.drawable.text_view_background_selected)
                    region = parent?.getItemAtPosition(position).toString()

                }

            // get fuelType from gridView
            fuelTypeGv.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    for (i in fuelTypeGv.allViews) {
                        i.background = null
                    }
                    view.setBackgroundResource(R.drawable.text_view_background_selected)
                    fuelType = parent?.getItemAtPosition(position).toString()

                }

            // availability checkBox
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                availability = isChecked
            }
        }
    }

    private fun initRegionGridView() {
        with(binding) {
            val regions = Regions.values().map { it.region }
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, regions)
            regionsGv.adapter = arrayAdapter
        }
    }

    private fun initFuelTypeGridView() {
        with(binding) {
            val fuelTypes = FuelType.values().map { it.type }
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, fuelTypes)
            fuelTypeGv.adapter = arrayAdapter
        }
    }

}