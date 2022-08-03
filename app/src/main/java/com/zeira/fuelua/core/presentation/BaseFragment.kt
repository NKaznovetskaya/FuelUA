package com.zeira.fuelua.core.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.zeira.fuelua.core.utils.ShowToastInterface
import com.zeira.fuelua.utils.CustomProgressDialog
import org.koin.java.KoinJavaComponent


abstract class BaseFragment<B : ViewBinding> : Fragment() {

    protected val toastManager: ShowToastInterface by KoinJavaComponent.inject(ShowToastInterface::class.java)

    abstract val bindingInflater: ((LayoutInflater, ViewGroup?, Boolean) -> (B))
    private var _binding: B? = null
    val binding: B
        get() {
            if (_binding == null) {
                throw NullPointerException("Binding not found!!!")
            }
            return _binding!!
        }

    private val progressDialog = CustomProgressDialog()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected fun showProgressDialog(context: Context?){
        if (context != null) {
            progressDialog.show(context, "Please wait...")
        }
    }
    protected fun hideProgressDialog(){
        progressDialog.dialog.dismiss()
    }

}