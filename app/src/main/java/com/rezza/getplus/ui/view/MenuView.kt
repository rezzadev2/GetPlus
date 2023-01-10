package com.rezza.getplus.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.balysv.materialripple.MaterialRippleLayout
import com.bumptech.glide.Glide
import com.rezza.getplus.R
import com.rezza.getplus.model.Menu

class MenuView(context: Context?, attrs: AttributeSet?) : MyView(context, attrs) {

    lateinit var  mrly_body : MaterialRippleLayout
    lateinit var  imvw_menu : ImageView
    lateinit var  txvw_menu : TextView

    private lateinit var mMenu : Menu

    private var onSelectedListener : OnSelectedListener  ?= null

    override fun setlayout(): Int {
        return R.layout.view_menu
    }

    override fun initLayout() {
        mrly_body = findViewById(R.id.mrly_body)
        imvw_menu = findViewById(R.id.imvw_menu)
        txvw_menu = findViewById(R.id.txvw_menu)
    }

    override fun initListener() {
        mrly_body.setOnClickListener {
            Log.d("LOGS","Click "+mMenu.label)
            if (onSelectedListener != null){
                this.onSelectedListener!!.onSelect(mMenu)
            }
        }
    }

    public fun create(menu : Menu){
        super.create()
        mMenu = menu
        Glide.with(context).load(menu.logoUrl).into(imvw_menu)
        txvw_menu.text = menu.label
    }

    @JvmName("setOnSelectedListener1")
    fun setOnSelectedListener(onSelectedListener : OnSelectedListener){
        this.onSelectedListener = onSelectedListener
    }
    interface OnSelectedListener{
        fun onSelect(menu : Menu)
    }

}