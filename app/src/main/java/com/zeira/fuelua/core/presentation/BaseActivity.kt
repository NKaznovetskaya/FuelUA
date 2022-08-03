package com.zeira.fuelua.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.zeira.fuelua.core.utils.ShowToastInterface
import org.koin.java.KoinJavaComponent

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    protected val toastManager: ShowToastInterface by KoinJavaComponent.inject(ShowToastInterface::class.java)

    abstract val bindingInflater: ((LayoutInflater) -> (B))
    private var _binding: B? = null
    val binding: B
        get() {
            if (_binding == null) {
                throw NullPointerException("Binding not found!!!")
            }
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(_binding?.root)
    }
}