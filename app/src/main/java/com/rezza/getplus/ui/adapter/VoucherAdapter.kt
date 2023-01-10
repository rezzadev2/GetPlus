package com.rezza.getplus.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rezza.getplus.R
import com.rezza.getplus.model.ItemVoucher
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class VoucherAdapter(val context: Context?, private val list: ArrayList<ItemVoucher>) : RecyclerView.Adapter<VoucherAdapter.AdapterView>(){

    private val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)//2022-11-10T10:41:00
    private val dateFormat2 = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)//2022-11-10T10:41:00

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterView {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_voucher, parent, false)
        return AdapterView(itemView)
    }

    override fun onBindViewHolder(holder: AdapterView, position: Int) {
        val data = list[position]

        val date = dateFormat1.parse(data.ValidUntil)
        if (date != null){
            holder.txvw_valid.text = dateFormat2.format(date)
        }
        else {
            holder.txvw_valid.text = "-"
        }

        val format = NumberFormat.getCurrencyInstance()
        val idn = Locale("id", "ID")

        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance(idn)
        val sValue =  "Rp "+format.format(data.VoucherValue).replace("Rp","")
        holder.txvw_value.text = sValue
        holder.txvw_title.text = data.DisplayValue
        Glide.with(context!!).load(data.Images.Feature.ImageURL).into(holder.imvw_voucher)

        holder.card_root.setCardBackgroundColor(Color.parseColor("#42a5f5"))

        holder.card_use.setOnClickListener {
            onSelectListener?.onSelect(data)
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }

    class AdapterView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imvw_voucher = itemView.findViewById<ImageView>(R.id.imvw_voucher)
        val txvw_title = itemView.findViewById<TextView>(R.id.txvw_title)
        val txvw_value = itemView.findViewById<TextView>(R.id.txvw_value)
        val txvw_valid = itemView.findViewById<TextView>(R.id.txvw_valid)
        val card_root = itemView.findViewById<CardView>(R.id.card_root)
        val card_use = itemView.findViewById<CardView>(R.id.card_use)

    }

    var onSelectListener : OnSelectListener ?= null

    @JvmName("setOnSelectListener1")
    public fun setOnSelectListener(onSelectListener: OnSelectListener){
        this.onSelectListener = onSelectListener
    }
    interface OnSelectListener{
        fun onSelect(data : ItemVoucher)
    }

}