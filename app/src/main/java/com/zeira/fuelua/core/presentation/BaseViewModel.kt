package com.zeira.fuelua.core.presentation

import androidx.lifecycle.ViewModel
import com.zeira.fuelua.R
import com.zeira.fuelua.core.utils.ShowToastInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.http2.ConnectionShutdownException
import org.koin.java.KoinJavaComponent.inject
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel() : ViewModel() {

    private val toastManager: ShowToastInterface by inject(ShowToastInterface::class.java)

    protected suspend fun handleErrorX(e: Exception) {
        withContext(Dispatchers.Main){
            handleError(e)
        }
    }

    protected fun handleError(e: Exception) {
        e.printStackTrace()

        when (e) {
            is SocketTimeoutException -> {
                toastManager.showMessage(R.string.socket_timeout_exception_message)
            }
            is UnknownHostException -> {
                toastManager.showMessage(R.string.unknown_host_exception_message)
            }
            is ConnectionShutdownException -> {
                toastManager.showMessage(R.string.connection_shutdown_exception_message)
            }

            is ConnectException -> {
                toastManager.showMessage(R.string.connect_exception_message)
            }
            is HttpException -> {
                when (e.code()) {
                    401 -> {
                        toastManager.showMessage(R.string.unauthorized_exception_message)
                    }
                    429 -> {
                        toastManager.showMessage(R.string.too_many_requests_exception_message)
                    }
                    500 -> {
                        toastManager.showMessage(R.string.internal_server_error_exception_message)
                    }
                    404 -> {
                        toastManager.showMessage(R.string.not_found_exception_message)
                    }
                    403 -> {
                        toastManager.showMessage(R.string.forbidden_exception_message)
                    }
                    else -> {
                        e.message?.let { toastManager.showMessage(it) }
                    }
                }
            }
            is IOException -> {
                toastManager.showMessage(R.string.server_not_available_exception_message)
            }
            is IllegalStateException -> {
                e.message?.let { toastManager.showMessage(it) }
            }
            else -> {
                e.message?.let { toastManager.showMessage(it) }
            }
        }
    }

}

