package com.rezza.getplus.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.rezza.getplus.R
import com.rezza.getplus.model.Menu
import com.rezza.getplus.model.MerchantItems
import java.util.*

class MerchantAdapter(val context: Context?, private val list: ArrayList<MerchantItems>) : RecyclerView.Adapter<MerchantAdapter.AdapterView>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterView {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_merchant, parent, false)
        return AdapterView(itemView)
    }

    override fun onBindViewHolder(holder: AdapterView, position: Int) {
        val data = list[position]
        if (data.loading){
            holder.shmr_load.startShimmerAnimation()
            holder.shmr_load.visibility = View.VISIBLE

        }
        else {
            holder.shmr_load.visibility = View.GONE
            Glide.with(context!!).load(data.image).into(holder.imvw_image)
            holder.txvw_title.text = data.name
        }


    }

    override fun getItemCount(): Int {
       return list.size
    }

    class AdapterView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imvw_image = itemView.findViewById<ImageView>(R.id.imvw_image)
        val txvw_title = itemView.findViewById<TextView>(R.id.txvw_title)
        val shmr_load = itemView.findViewById<ShimmerFrameLayout>(R.id.shmr_load)

    }

    var onSelectListener : OnSelectListener ?= null

    @JvmName("setOnSelectListener1")
    public fun setOnSelectListener(onSelectListener: OnSelectListener){
        this.onSelectListener = onSelectListener
    }
    interface OnSelectListener{
        fun onSelect(MerchantItems : Menu)
    }

}