package com.rezza.getplus.api.service

import com.rezza.getplus.model.MerchantApiModel
import com.rezza.getplus.model.HomeModel
import com.rezza.getplus.model.VoucherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {
    @GET
    fun getAllHome(@Url url: String): Call<HomeModel>

    @GET
    fun getMerchants(@Url url: String): Call<MerchantApiModel>

    @GET
    fun getVouchers(@Url url: String): Call<VoucherModel>
}
