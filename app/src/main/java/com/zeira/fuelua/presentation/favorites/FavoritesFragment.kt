package com.zeira.fuelua.presentation.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeira.fuelua.core.presentation.BaseFragment
import com.zeira.fuelua.databinding.FragmentFavoritesBinding
import com.zeira.fuelua.presentation.details.DetailsBottomDialog
import com.zeira.fuelua.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoritesBinding
        get() = FragmentFavoritesBinding::inflate

    private val vm: FavoritesViewModel by viewModel()


    private var adapter = FavoritesListAdapter(
        //onClick by item and open bottomSheet
        onClick = { favoritesEntity ->
            val bottomSheet = DetailsBottomDialog()

            val args = Bundle()
            args.putParcelable(Constants.STATION_DETAILS, favoritesEntity)

            bottomSheet.arguments = args
            childFragmentManager.let {
                bottomSheet.show(
                    it,
                    DetailsBottomDialog.DETAILS_BOTTOM_DIALOG_TAG
                )
            }
        },
        //remove from favorites
        onClickRemoveFromFavorites = { gasStationId ->
            gasStationId?.let { vm.removeFromFavorites(it) }

            showProgressDialog(context)

            vm.getFavorites()
        }
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProgressDialog(context)

        vm.getFavorites()

        binding.favoritesListRv.layoutManager = LinearLayoutManager(context)
        binding.favoritesListRv.adapter = adapter

        //get and set list of favorites in the recycler, if list empty showing message on the screen
        vm.favoritesLive.observe(viewLifecycleOwner) {
            hideProgressDialog()
            showProgressDialog(context)
            binding.noFavoritesTv.isVisible = it.isEmpty()
            hideProgressDialog()
            adapter.submitList(it)

            Log.i("favoritesList", "favorites $it")
        }

        vm.exceptionLive.observe(viewLifecycleOwner) {
            if (it) {
                hideProgressDialog()
            }
        }

    }
}