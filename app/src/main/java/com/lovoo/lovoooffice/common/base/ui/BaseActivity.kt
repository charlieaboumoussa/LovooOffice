package com.lovoo.lovoooffice.common.base.ui

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lovoo.lovoooffice.R
import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.common.base.viewmodels.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var _loadingDialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _loadingDialog = Dialog(this, R.style.Lovoo_Theme_Dialog_Translucent)
        _loadingDialog.setCancelable(false)
        _loadingDialog.setContentView(R.layout.layout_loader)
        _loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        lifecycleScope.launch {
            attachViewModel()?.uiBaseState?.collect {
                it?.let {
                    when(it) {
                        is Resource.Loading->{
                            it.data?.let { isLoading->
                                if(isLoading){
                                    showLoading()
                                }else{
                                    hideLoading()
                                }
                            }
                        }
                        is Resource.Error->{
                            it.message?.let {
                                when(it){
                                    "0"->{
                                        showError(getString(R.string.default_error_service))
                                    }
                                    else->{
                                        showError(it)
                                    }
                                }
                            }
                        }
                        else->{

                        }
                    }
                }
            }
        }
    }

    fun showLoading() {
        _loadingDialog.show()
    }

    fun hideLoading() {
        _loadingDialog.hide()
    }

    fun showError(errorMessage : String) {
        MaterialAlertDialogBuilder(this, R.style.Lovoo_Style_NativeDialog)
            .setTitle(getString(R.string.service_error))
            .setMessage(errorMessage)
            .setCancelable(true)
            .setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                override fun onClick(dialogInterface: DialogInterface?, p1: Int) {
                    dialogInterface?.dismiss()
                }
            }).show()
    }

    abstract fun attachViewModel() : BaseViewModel?
}