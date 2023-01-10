package com.rezza.getplus.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rezza.getplus.api.repo.MerchantRepository
import com.rezza.getplus.model.MerchantApiModel

class MerchantViewModel(application: Application): AndroidViewModel(application){

    private var repository : MerchantRepository = MerchantRepository()
    var postModelListLiveData : LiveData<MerchantApiModel> = MutableLiveData()


    fun loadMerchant(id: Int){
        postModelListLiveData = repository.loadMerchant(id)
    }

}