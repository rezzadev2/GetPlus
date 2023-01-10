package com.rezza.getplus.api.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rezza.getplus.api.ApiConfig
import com.rezza.getplus.api.CallApiService
import com.rezza.getplus.api.service.ApiInterface
import com.rezza.getplus.model.HomeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    var  apiInterface : ApiInterface?= null

    init {
        apiInterface = CallApiService.getApiClient().create(ApiInterface::class.java)
    }

    fun loadData(): LiveData<HomeModel> {
        val data = MutableLiveData<HomeModel>()

        val url = ApiConfig.GET_HOME
        Log.d("HomeRepository","API $url")

        apiInterface?.getAllHome(url)?.enqueue(object : Callback<HomeModel> {

            override fun onFailure(call: Call<HomeModel>, t: Throwable) {
                data.value = null
                Log.e("HomeRepository","response "+t.message);
            }

            override fun onResponse(
                call: Call<HomeModel>,
                response: Response<HomeModel>
            ) {

                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    Log.e("HomeRepository","response "+response.code());
                    data.value = null
                }
            }
        })
        return data
    }
}