package com.zeira.fuelua.presentation.menu.company.listStation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseFragment
import com.zeira.fuelua.databinding.FragmentCompanyListStationBinding
import com.zeira.fuelua.domain.models.GasStationModelX
import com.zeira.fuelua.presentation.gasStationList.GasStationListAdapter
import com.zeira.fuelua.presentation.menu.company.updateStation.UpdateStationFragmentArgs
import com.zeira.fuelua.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompanyListStationFragment: BaseFragment<FragmentCompanyListStationBinding>() {

    private val companyId by lazy { CompanyListStationFragmentArgs.fromBundle(requireArguments()).companyId }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCompanyListStationBinding
        get() = FragmentCompanyListStationBinding::inflate

    private val vm: CompanyListStationViewModel by viewModel()

    private val adapter by lazy {
        CompanyGasStationListAdapter(
            onClick = { station ->
                val args = Bundle()
                args.putParcelable(Constants.STATION_DETAILS, station)
            },
            onClickEditStation = { gasStation ->
                findNavController().navigate(R.id.action_companyListStationFragment_to_updateStationFragment,
                    UpdateStationFragmentArgs(gasStation).toBundle())
            },
            onClickRemoveStation = { gasStationId ->
                vm.removeGasStation(gasStationId)
            }
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener(clickListeners)
        binding.createStationBtn.setOnClickListener (clickListeners)

        binding.gasStationListRv.adapter = adapter

        vm.getAllStation()
        vm.allGasStationLive.observe(viewLifecycleOwner, allGasStationObserver)
    }

    private val clickListeners = View.OnClickListener {
        with(binding) {
            when(it.id) {
                backButton.id -> findNavController().popBackStack()
                createStationBtn.id -> findNavController().navigate(R.id.action_companyListStationFragment_to_createStationFragment)
            }
        }
    }

    private val allGasStationObserver = Observer<List<GasStationModelX>> { listGasStation ->
        adapter.submitList(listGasStation.filter { gasStation -> gasStation.company_id == companyId })
    }

}