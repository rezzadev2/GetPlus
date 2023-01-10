package com.rezza.getplus.api.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rezza.getplus.api.ApiConfig
import com.rezza.getplus.api.CallApiService
import com.rezza.getplus.api.service.ApiInterface
import com.rezza.getplus.model.VoucherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VoucherRepository {

    var  apiInterface : ApiInterface?= null

    init {
        apiInterface = CallApiService.getApiClient().create(ApiInterface::class.java)
    }

    fun loadVoucher(): LiveData<VoucherModel> {
        val data = MutableLiveData<VoucherModel>()

        val url = ApiConfig.GET_VOUCHER
        Log.d("DetailRepository","API $url")

        apiInterface?.getVouchers(url)?.enqueue(object : Callback<VoucherModel> {

            override fun onFailure(call: Call<VoucherModel>, t: Throwable) {
                data.value = null
                Log.e("DetailRepository","response "+t.message);
            }

            override fun onResponse(
                call: Call<VoucherModel>,
                response: Response<VoucherModel>
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