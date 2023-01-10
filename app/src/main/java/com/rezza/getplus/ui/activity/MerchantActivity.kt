package com.rezza.getplus.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balysv.materialripple.MaterialRippleLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.rezza.getplus.R
import com.rezza.getplus.model.MerchantItems
import com.rezza.getplus.ui.adapter.MerchantAdapter
import com.rezza.getplus.viewmodel.MerchantViewModel

class MerchantActivity : AppCompatActivity() {

    private lateinit var  mrly_back : MaterialRippleLayout
    private lateinit var txvw_title : TextView
    private lateinit var rcvw_data : RecyclerView
    private lateinit var shmr_load : ShimmerFrameLayout

    private lateinit var viewModel: MerchantViewModel

    private var page : Int = 1
    private var isLoading : Boolean = false
    private var listMerchant = ArrayList<MerchantItems>()
    private lateinit var  mAdapter : MerchantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mrly_back       = findViewById(R.id.mrly_back)
        txvw_title      = findViewById(R.id.txvw_title)
        rcvw_data       = findViewById(R.id.rcvw_data)
        shmr_load       = findViewById(R.id.shmr_load)

        rcvw_data.layoutManager = LinearLayoutManager(this)
        listMerchant      = ArrayList()
        mAdapter          = MerchantAdapter(this, listMerchant)
        rcvw_data.adapter = mAdapter

        initListener()
        initData()
    }

    fun initListener(){
        mrly_back.setOnClickListener {
            onBackPressed()
        }

        rcvw_data.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listMerchant.size - 1) {

                        val loadModel = MerchantItems ()
                        loadModel.loading = true
                        listMerchant.add(loadModel)
                        mAdapter.notifyItemInserted(listMerchant.size)

                        loadData()
                        isLoading = true
                    }
                }
            }
        })

    }


    private fun initData(){
        viewModel = ViewModelProvider(this)[MerchantViewModel::class.java]

        val title = intent.getStringExtra("title")
        txvw_title.text = title

        shmr_load.visibility = View.VISIBLE
        shmr_load.startShimmerAnimation()

        loadData()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData(){
        viewModel.loadMerchant(page)
        viewModel.postModelListLiveData.observe(this) {
            if (listMerchant.size > 0) {
                listMerchant.removeAt(listMerchant.size - 1)
                mAdapter.notifyItemRemoved(listMerchant.size)
            }

            if (it != null) {
                val data = it.data.list
                for (item in data) {
                    val merchant = MerchantItems()
                    merchant.loading = false
                    merchant.name = item.DisplayValue
                    merchant.image = item.Images.Feature.ImageURL

                    listMerchant.add(merchant)
                }

                mAdapter.notifyDataSetChanged()
                page++
            }

            isLoading = false
            if (shmr_load.visibility == View.VISIBLE){
                shmr_load.stopShimmerAnimation()
                shmr_load.visibility = View.GONE
            }
        }
    }





}