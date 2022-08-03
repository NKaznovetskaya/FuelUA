package com.zeira.fuelua.presentation.menu.company.authorization

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.zeira.fuelua.core.presentation.BaseViewModel

class PhoneAuthViewModel : BaseViewModel() {

    private var credentialLiveMutable = MutableLiveData<PhoneAuthCredential>()
    var credentialLive: LiveData<PhoneAuthCredential> = credentialLiveMutable

    private var exceptionLiveMutable = MutableLiveData<FirebaseException>()
    var exceptionLive: LiveData<FirebaseException> = exceptionLiveMutable

    private var storedVerificationIdLiveMutable = MutableLiveData<String>()
    var storedVerificationIdLive: LiveData<String> = storedVerificationIdLiveMutable

    var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            credentialLiveMutable.value = credential

        }

        override fun onVerificationFailed(e: FirebaseException) {
            exceptionLiveMutable.value = e
            Log.e("AAA", e.message.toString())
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {

            Log.i("AAA", "onCodeSent:$verificationId")
            storedVerificationIdLiveMutable.value = verificationId

        }
    }

}