package com.rezza.getplus.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balysv.materialripple.MaterialRippleLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.rezza.getplus.R
import com.rezza.getplus.model.ItemVoucher
import com.rezza.getplus.ui.adapter.VoucherAdapter
import com.rezza.getplus.viewmodel.VoucherViewModel

class VoucherActivity : AppCompatActivity() {

    private lateinit var  mrly_back : MaterialRippleLayout
    private lateinit var txvw_title : TextView
    private lateinit var rcvw_data : RecyclerView
    private lateinit var shmr_load : ShimmerFrameLayout
    private lateinit var rvly_root : RelativeLayout

    private lateinit var viewModel: VoucherViewModel

    private var listMerchant = ArrayList<ItemVoucher>()
    private lateinit var  mAdapter : VoucherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mrly_back       = findViewById(R.id.mrly_back)
        txvw_title      = findViewById(R.id.txvw_title)
        rcvw_data       = findViewById(R.id.rcvw_data)
        shmr_load       = findViewById(R.id.shmr_load)
        rvly_root       = findViewById(R.id.rvly_root)

        rcvw_data.layoutManager = LinearLayoutManager(this)
        listMerchant      = ArrayList()
        mAdapter          = VoucherAdapter(this, listMerchant)
        rcvw_data.adapter = mAdapter

        initListener()
        initData()
    }

    fun initListener(){
        mrly_back.setOnClickListener {
            onBackPressed()
        }

        mAdapter.setOnSelectListener(object : VoucherAdapter.OnSelectListener{
            override fun onSelect(data: ItemVoucher) {
                showMessage("Under Development")
            }
        })
    }

    private fun initData(){
        viewModel = ViewModelProvider(this)[VoucherViewModel::class.java]

        val title = intent.getStringExtra("title")
        txvw_title.text = title

        shmr_load.visibility = View.VISIBLE
        shmr_load.startShimmerAnimation()

        loadData()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData(){
        viewModel.loadVoucher()
        viewModel.postModelListLiveData.observe(this) {

            if (it != null) {
                val data = it.data?.list
                if (data != null) {
                    for (item in data) {
                        listMerchant.add(item)
                    }
                }
                mAdapter.notifyDataSetChanged()
            }
            if (shmr_load.visibility == View.VISIBLE){
                shmr_load.stopShimmerAnimation()
                shmr_load.visibility = View.GONE
            }
        }
    }

    fun showMessage(message : String){
        val snackBarView = Snackbar.make(rvly_root, message, Snackbar.LENGTH_LONG)
        val view = snackBarView.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        view.setBackgroundColor(Color.parseColor("#fff9c4"))
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }
}