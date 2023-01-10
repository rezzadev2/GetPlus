package com.rezza.getplus.model

import java.io.Serializable

class VoucherModel : Serializable{

    var data : DataVoucher? = null

}

data class  DataVoucher (
   var list: ArrayList<ItemVoucher>
)


data class ItemVoucher(
    var ValidFrom : String,
    var ValidUntil : String,
    var VoucherCode : String,
    var DisplayValue : String,
    var VoucherValue : Long,
    var Images : ImagesVoucher
)

data class ImagesVoucher(
    val Feature : FeatureVoucher
)
data class FeatureVoucher(
    val ImageURL : String
)

