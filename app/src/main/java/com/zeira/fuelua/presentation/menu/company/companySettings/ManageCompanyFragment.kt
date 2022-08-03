package com.zeira.fuelua.presentation.menu.company.companySettings

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseFragment
import com.zeira.fuelua.databinding.FragmentManageCompanyBinding
import com.zeira.fuelua.domain.models.CompanyModelX
import com.zeira.fuelua.presentation.MainActivity
import com.zeira.fuelua.presentation.menu.company.listStation.CompanyListStationFragmentArgs
import com.zeira.fuelua.utils.Constants
import com.zeira.fuelua.utils.HideKeyboardManager
import com.zeira.fuelua.utils.setFieldFocusListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException

class ManageCompanyFragment : BaseFragment<FragmentManageCompanyBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentManageCompanyBinding
        get() = FragmentManageCompanyBinding::inflate
    private lateinit var auth: FirebaseAuth

    private val vm: ManageCompanyViewModel by viewModel()
    private var selectedImageFileUri: Uri? = null

    private lateinit var companies: List<CompanyModelX>

    private val hideKeyboardManager = HideKeyboardManager()

    private lateinit var companyDescription: String
    private lateinit var companyName: String
    private lateinit var companyPhoneNumber: String
    private lateinit var owner: String
    private lateinit var logoURL: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideKeyboardManager.initHideListener(binding.root)
        auth = FirebaseAuth.getInstance()
        companyPhoneNumber = auth.currentUser?.phoneNumber as String
        owner = auth.currentUser?.uid as String

        binding.phoneNumberEt.setText(companyPhoneNumber)

        vm.getCompany()
        showProgressDialog(context)

        initListeners()
        initObservables()
        manageCompanyBackPressListener()
    }

    private fun manageCompanyBackPressListener() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        isEnabled = false
                        manageCompanyBackPress()
                    }
                }
            })
    }

    private fun manageCompanyBackPress() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }


    private fun updateData() {
        val value = getCompany()
        value.id = companies[0].id
        vm.updateData(value)
        toastManager.showMessage("Компанію оновлено") // test
    }

    // receive company data
    private fun getCompany(): CompanyModelX {

        return CompanyModelX(
            id = 0,
            logo = logoURL,
            name = companyName,
            phone = companyPhoneNumber,
            owner = owner,
            description = companyDescription
        )

    }

    private fun validateCompany(): Boolean {
        with(binding) {
            return when {
                companyName.isEmpty() -> {
                    helperText.text = getString(R.string.the_field_must_not_be_empty)
                    companyNameEt.setBackgroundResource(R.drawable.custom_edit_text_unselected_error)
                    false
                }
                selectedImageFileUri == null -> {
                    helperText.text = getString(R.string.add_a_logo)

                    false
                }
                else -> {
                    helperText.text = ""
                    true
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE && data?.data != null) {
            selectedImageFileUri = data.data
            vm.getSelectedImageUri(selectedImageFileUri)
            // vm.uploadCompanyImage()
            try {
                Glide.with(this)
                    .load(selectedImageFileUri)
                    .centerCrop()
                    .placeholder(R.drawable.place_holder)
                    .into(binding.companyLogoIv)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun showImageChooser() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, Constants.PICK_IMAGE_REQUEST_CODE)

    }

    private fun setCompanyDataInUi(model: CompanyModelX) {
        try {
            with(binding) {
                companyNameEt.setText(model.name)
                phoneNumberEt.setText(model.phone)
                companyDescriptionEt.setText(model.description)
                selectedImageFileUri = model.logo.toUri()
            }
            Glide.with(this)
                .load(model.logo)
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(binding.companyLogoIv)
            hideProgressDialog()
        } catch (e: IOException) {
            e.printStackTrace()
            hideProgressDialog()
        }
    }

    private fun initListeners() {
        with(binding) {

            addCompanyLogoBtn.setOnClickListener {
                if (context?.let {
                        ContextCompat.checkSelfPermission(
                            it,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    }
                    == PackageManager.PERMISSION_GRANTED) {
                    showImageChooser()
                } else {
                    requestPermissions(
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE

                    )
                }
            }

            signOutBtn.setOnClickListener {
                auth.signOut()
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }

            backButton.setOnClickListener {
                manageCompanyBackPress()
            }

            updateDataBtn.setOnClickListener {
                showProgressDialog(context)
                companyName = companyNameEt.text.toString()
                companyDescription = binding.companyDescriptionEt.text.toString()


                val validate = validateCompany()
                if (validate) {
                    // if user haven`t company, we create company
                    if (companies.isNullOrEmpty()) {
                        vm.uploadCompanyImage()
                    } else { // if user has company, we update company
                        if (selectedImageFileUri.toString() != companies[0].logo) {
                            vm.updateCompanyImage()
                        } else {
                            updateData()
                        }
                    }
                }
            }

            phoneNumberEt.setFieldFocusListener()
            companyNameEt.setFieldFocusListener()
            companyDescriptionEt.setFieldFocusListener()

            listOffStationBtn.setOnClickListener {
                findNavController().navigate(R.id.action_manageCompanyFragment2_to_companyListStationFragment,
                    CompanyListStationFragmentArgs(companies.first().id).toBundle())
            }
        }

    }

    private fun initObservables() {
        vm.companyLive.observe(viewLifecycleOwner) {
            companies = it
            if (!it.isNullOrEmpty()) {
                Log.i("requestResult", "$it")
                logoURL = it[0].logo
                setCompanyDataInUi(it[0])
            } else {
                hideProgressDialog()
            }
        }

        vm.companyImageLive.observe(viewLifecycleOwner) {
            logoURL = it
            hideProgressDialog()
            toastManager.showMessage("Компанію створено")  // test
            vm.createCompany(getCompany())
        }


        vm.companyImageUpdateLive.observe(viewLifecycleOwner) {
            logoURL = it
            hideProgressDialog()
            updateData()
        }

        vm.companyUpdateLive.observe(viewLifecycleOwner) {
            hideProgressDialog()
        }
    }


}


