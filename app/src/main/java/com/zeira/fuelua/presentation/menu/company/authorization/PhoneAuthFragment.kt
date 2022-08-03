package com.zeira.fuelua.presentation.menu.company.authorization

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.*
import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseFragment
import com.zeira.fuelua.databinding.FragmentPhoneAuthBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

val auth = FirebaseAuth.getInstance()

class PhoneAuthFragment : BaseFragment<FragmentPhoneAuthBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPhoneAuthBinding
        get() = FragmentPhoneAuthBinding::inflate

    //  private lateinit var auth: FirebaseAuth
    private var storedVerificationId: String = "storedVerificationId"
    private lateinit var phNumber: String


    private val vm: PhoneAuthViewModel by viewModel<PhoneAuthViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        if (auth.currentUser != null) {
            findNavController().navigate(R.id.action_phoneAuthFragment_to_manageCompanyFragment)
        }

        initListeners()
        initObservables()

        return view
    }


    private fun sendVerificationCode(number: String) {
        val options = activity?.let {
            PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(it) // Activity (for callback binding)
                .setCallbacks(vm.callbacks) // OnVerificationStateChangedCallbacks
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("AAA", "signInWithCredential:success")
                    hideProgressDialog()
                    findNavController().navigate(R.id.action_phoneAuthFragment_to_manageCompanyFragment)

                } else {
                    Log.e("AAA", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        binding.errorMessageTv.visibility = View.VISIBLE
                        binding.verificationCodeOtp.setOTP("")
                        binding.verificationCodeOtp.showError()
                        hideProgressDialog()

                    }
                }
            }
    }

    //check phone number, if field empty show error message, else send verification code
    private fun login(mobileNumber: String) {
        val number = mobileNumber.trim()
        showProgressDialog(context)
        if (number.isNotEmpty()) {
            sendVerificationCode(number)
        } else {
            //Toast.makeText(context, "Enter phone number", Toast.LENGTH_SHORT).show()
            Log.e("AAA", "Enter mobile number")
            binding.phoneVerificationEt.setBackgroundResource(R.drawable.custom_error_edittext)
            binding.phoneContainerEt.helperText = "Введіть номер телефону"
            hideProgressDialog()
        }
    }

    private fun initListeners() {
        with(binding) {

            phoneVerificationBtn.setOnClickListener {
                phNumber = phoneVerificationEt.text.toString()
                login(phNumber)

            }

            codeVerificationBtn.setOnClickListener {
                val code = verificationCodeOtp.otp.toString()

                showProgressDialog(context)
                if (code.isNotEmpty()) {
                    val credential: PhoneAuthCredential =
                        PhoneAuthProvider.getCredential(
                            storedVerificationId, code
                        )
                    signInWithPhoneAuthCredential(credential)
                }
            }

            sendCodeAgain.setOnClickListener {
                sendVerificationCode(phNumber)

            }

            //if editText clicked then hide error border and error message
            phoneVerificationEt.setOnClickListener {
                phoneVerificationEt.setBackgroundResource(R.drawable.custom_edit_text_selected)
                phoneContainerEt.helperText = " "
            }
        }
    }

    private fun initObservables() {
        with(binding) {
            vm.credentialLive.observe(viewLifecycleOwner) {
                signInWithPhoneAuthCredential(it)
            }

            vm.exceptionLive.observe(viewLifecycleOwner) {
                hideProgressDialog()
                phoneVerificationEt.setBackgroundResource(R.drawable.custom_error_edittext)
                phoneContainerEt.helperText = "Номер введено невірно"
            }

            vm.storedVerificationIdLive.observe(viewLifecycleOwner) {
                storedVerificationId = it
                phoneVerificationConstraint.visibility = View.GONE
                codeVerificationConstraint.visibility = View.VISIBLE
                hideProgressDialog()
            }
        }
    }


}