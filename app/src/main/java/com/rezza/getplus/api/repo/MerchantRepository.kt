package com.rezza.getplus.api.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rezza.getplus.api.ApiConfig
import com.rezza.getplus.api.CallApiService
import com.rezza.getplus.api.service.ApiInterface
import com.rezza.getplus.model.MerchantApiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MerchantRepository {

    var  apiInterface : ApiInterface?= null

    init {
        apiInterface = CallApiService.getApiClient().create(ApiInterface::class.java)
    }

    fun loadMerchant(page: Int): LiveData<MerchantApiModel> {
        val data = MutableLiveData<MerchantApiModel>()

        val url = ApiConfig.GET_MERCHANT+"?page="+page
        Log.d("DetailRepository","API $url")

        apiInterface?.getMerchants(url)?.enqueue(object : Callback<MerchantApiModel> {

            override fun onFailure(call: Call<MerchantApiModel>, t: Throwable) {
                data.value = null
                Log.e("DetailRepository","response "+t.message);
            }

            override fun onResponse(
                call: Call<MerchantApiModel>,
                response: Response<MerchantApiModel>
            ) {

                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    Log.e("DetailRepository","response "+response.code());
                    data.value = null
                }
            }
        })
        return data
    }
}