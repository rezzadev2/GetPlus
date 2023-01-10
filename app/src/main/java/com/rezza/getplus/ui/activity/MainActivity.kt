package com.rezza.getplus.ui.activity

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.facebook.shimmer.ShimmerFrameLayout
import com.rezza.getplus.R
import com.rezza.getplus.model.Menu
import com.rezza.getplus.model.Promo
import com.rezza.getplus.ui.adapter.PromoAdapter
import com.rezza.getplus.ui.view.MenuView
import com.rezza.getplus.viewmodel.HomeViewModel
import kotlin.math.abs

class MainActivity : AppCompatActivity(){

    private lateinit var viewModel: HomeViewModel

    private lateinit var lnly_menu : LinearLayout
    private lateinit var shmr_load : ShimmerFrameLayout
    private lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        lnly_menu = findViewById(R.id.lnly_menu)
        shmr_load = findViewById(R.id.shmr_load)
        shmr_load = findViewById(R.id.shmr_load)
        viewPager = findViewById(R.id.view_pager)

        shmr_load.visibility = View.VISIBLE
        shmr_load.startShimmerAnimation()

        viewPager.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        viewPager.setPageTransformer(compositePageTransformer)

        initListener()
        init()

    }

    private fun initListener(){


    }

    private fun init(){
        viewModel.loadMenu();
        viewModel.postModelListLiveData?.observe(this) {
            shmr_load.visibility = View.GONE
            shmr_load.stopShimmerAnimation()
            if (it != null) {
                createMenu(it.data!!.layout.menu)
                createPromo(it.data!!.layout.promo)
            }
        }
    }

    private fun createMenu(menu : ArrayList<Menu>){
        lnly_menu.removeAllViews()

        for (item in menu){
           val menuView = MenuView(this, null)
            menuView.create(item)
            lnly_menu.addView(menuView)

            menuView.setOnSelectedListener(object : MenuView.OnSelectedListener{
                override fun onSelect(menu: Menu) {
                    Log.d("LOGS","onSelect "+menu.label)
                    if (menu.id == "id_merchants"){
                        detailMerchant(menu.label )
                    }
                    else {
                        toVoucher(menu.label )
                    }
                }
            })
        }
    }

    private fun detailMerchant(title : String){
        val intent = Intent(this, MerchantActivity::class.java)
        intent.putExtra("title",title )
        startActivity(intent)
    }
    private fun toVoucher(title : String){
        val intent = Intent(this, VoucherActivity::class.java)
        intent.putExtra("title",title )
        startActivity(intent)
    }

    private fun createPromo(promo : Promo){
        viewPager.adapter = PromoAdapter(this, promo.data)
    }


}