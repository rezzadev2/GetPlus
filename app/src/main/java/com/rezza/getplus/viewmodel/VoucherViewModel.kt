package com.rezza.getplus.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rezza.getplus.api.repo.VoucherRepository
import com.rezza.getplus.model.VoucherModel

class VoucherViewModel(application: Application): AndroidViewModel(application){

    private var repository : VoucherRepository = VoucherRepository()
    var postModelListLiveData : LiveData<VoucherModel> = MutableLiveData()


    fun loadVoucher(){
        postModelListLiveData = repository.loadVoucher()
    }

}