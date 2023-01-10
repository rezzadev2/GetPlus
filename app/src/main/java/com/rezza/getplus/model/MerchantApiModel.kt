package com.rezza.getplus.model

data class MerchantApiModel (
    val data: DataRes
    )


data class DataRes(
    val list: ArrayList<MerchantData>
)

data class MerchantData(
    val ID : String,
    val DisplayValue : String,
    val Name : String,
    val WebsiteProfile : String,
    val Images : Images,
)

data class Images(
    val Feature : Feature
)
data class Feature(
    val ImageURL : String,
    val ThumbnailURL : String
)