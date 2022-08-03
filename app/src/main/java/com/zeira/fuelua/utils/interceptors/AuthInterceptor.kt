package com.zeira.fuelua.utils.interceptors

import android.util.Base64
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import okhttp3.Interceptor
import okhttp3.Response
import java.net.UnknownHostException

class AuthInterceptor() : Interceptor {

    private var token: String = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            runBlocking {
                val tkn = getBearerToken(user)
                token = "Bearer $tkn"
            }
        } else {
            runBlocking {
                val basicToken = getBasicToken()
                token = "Basic $basicToken"
            }
        }
        request = request.newBuilder()
            .addHeader("Authorization", token.trim())
            .build()

        return chain.proceed(request)

    }


    private fun getBasicToken(): String {
        val userName = "8Ho5BQjiTO0BhrCasBnYGQ"
        val password = "BHzbm4-Mxi-y4AW8bt2e3g"

        val base = "$userName:$password"
        Log.i("tokenId", "basicToken")
        val authHeader = Base64.encodeToString(base.toByteArray(), Base64.NO_WRAP)
        return authHeader
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun getBearerToken(user: FirebaseUser): String =
        withTimeout(60000000) {
            suspendCancellableCoroutine { emitter ->
                user.getIdToken(true)//true
                    .addOnSuccessListener {
                        //  token = "Bearer ${it.token.toString()}"
                        Log.i("tokenId", "bearerToken ${it.token.toString()}")
                        emitter.resume(it.token.toString()) {}
                    }
                    .addOnCanceledListener {
                        emitter.cancel(IllegalStateException("getIdToken canceled"))
                        Log.e("requestResult", "getIdToken cancelled")
                    }
                    .addOnFailureListener {
                        emitter.cancel(UnknownHostException())
                        Log.e("requestResult", "getIdToken failed")
                    }
            }
        }


}