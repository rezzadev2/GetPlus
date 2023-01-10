package com.rezza.getplus.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rezza.getplus.api.repo.HomeRepository
import com.rezza.getplus.model.HomeModel

class HomeViewModel(application: Application): AndroidViewModel(application){

    private var homeRepository:HomeRepository?=null
    var postModelListLiveData : LiveData<HomeModel>?=null

    init {
        homeRepository = HomeRepository()
        postModelListLiveData = MutableLiveData()
    }

    fun loadMenu(){
        postModelListLiveData = homeRepository?.loadData()
    }

}