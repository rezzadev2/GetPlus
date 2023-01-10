package com.rezza.getplus.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rezza.getplus.R
import com.rezza.getplus.model.PromoData

class PromoAdapter(val context : Context,  private val carouselDataList: ArrayList<PromoData>) :
    RecyclerView.Adapter<PromoAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val data = carouselDataList[position]
        val textView = holder.itemView.findViewById<TextView>(R.id.txvw_title)
        val imvw_promo = holder.itemView.findViewById<ImageView>(R.id.imvw_promo)
        val title =  "Promo "+data.order
        textView.text = title
        Glide.with(context).load(data.imageUrl).into(imvw_promo)

    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}