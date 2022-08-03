package com.zeira.fuelua.presentation.gasStationList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeira.fuelua.core.presentation.BaseFragment
import com.zeira.fuelua.databinding.FragmentGasStationListBinding
import com.zeira.fuelua.presentation.details.DetailsBottomDialog
import com.zeira.fuelua.presentation.filter.FilterBottomDialog
import com.zeira.fuelua.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel


class GasStationListFragment : BaseFragment<FragmentGasStationListBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGasStationListBinding
        get() = FragmentGasStationListBinding::inflate

    private val vm: GasStationListViewModel by viewModel()
//    private val regions = arrayListOf<String>()

    // handle filter callbacks
    private var bottomSheet = FilterBottomDialog(
        filterCallback = { filterModel ->
            vm.getByFilter(filterModel)
        }
    )

    // handle station list adapter callbacks
    private var adapter = GasStationListAdapter(
        onClick = { gasStationEntity ->
            val bottomSheet = DetailsBottomDialog()
            val args = Bundle()
            args.putParcelable(Constants.STATION_DETAILS, gasStationEntity)

            bottomSheet.arguments = args
            childFragmentManager.let {
                bottomSheet.show(
                    it,
                    DetailsBottomDialog.DETAILS_BOTTOM_DIALOG_TAG
                )
            }

        },
        onClickAddToFavorites = { gasStationEntity ->
            vm.addToFavorites(gasStationEntity)

        },
        onClickRemoveFromFavorites = { gasStationId ->
            gasStationId?.let { vm.removeFromFavorites(it) }
            showProgressDialog(context)
            vm.getAllGasStation()
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

        showProgressDialog(context)
        //vm.getGasStationList()
        vm.getAllGasStation() // retrofit

        with(binding) {
            gasStationListRv.layoutManager = LinearLayoutManager(context)
            gasStationListRv.adapter = adapter

        }

        vm.gasStationListLiveX.observe(viewLifecycleOwner) {
            hideProgressDialog()
            adapter.submitList(it)

            binding.noStationTv.isVisible = it.isEmpty()
        }

        vm.exceptionLive.observe(viewLifecycleOwner) {
            if (it) {
                hideProgressDialog()
            }
        }

    }

    private fun initListeners() {
        with(binding) {
            filterBtn.setOnClickListener {
                childFragmentManager.let {
                    bottomSheet.show(it, FilterBottomDialog.FILTER_BOTTOM_DIALOG_TAG)
                }
            }

        }
    }

}