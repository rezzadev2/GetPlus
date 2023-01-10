package com.rezza.getplus.model

import java.io.Serializable

class HomeModel : Serializable{

    var data : Data? = null

}

data class  Data (
   var layout: Layout
)

data class Layout(
    val menu : ArrayList<Menu>,
    var promo : Promo
)


data class Menu(
    var logoUrl : String,
    var label : String,
    var id : String,
    var deeplink : String,
    var enable : Boolean,
    var visible : Boolean,
)



data class Promo(

    var title : String,
    var data : ArrayList<PromoData>
)

data class PromoData(
    var imageUrl : String,
    var url : String,
    var order : Int,
)