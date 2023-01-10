//package com.rezza.getplus.ui.fragment
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.facebook.shimmer.ShimmerFrameLayout
//import com.rezza.getplus.R
//import com.rezza.getplus.ui.adapter.GamesAdapter
//import com.rezza.getplus.model.HomeModel
//import com.rezza.getplus.ui.activity.DetailGamesActivity
//import com.rezza.getplus.ui.adapter.GamesAdapter.OnSelectListener
//import com.rezza.getplus.viewmodel.HomeViewModel
//import java.text.SimpleDateFormat
//import java.util.*
//
//
//class HomeFragment : MyFragment() {
//
//    private var shmr_load : ShimmerFrameLayout ?= null
//    private val listGames = ArrayList<HomeModel>()
//    private var adapterGame : GamesAdapter?= null
//    private var isLoading : Boolean = false
//    private lateinit var viewModel:HomeViewModel
//    private var page : Int = 1
//
//    companion object {
//        fun newInstance(): HomeFragment {
//            val args = Bundle()
//            val fragment = HomeFragment()
//            fragment.arguments = args
//            return fragment
//        }
//    }
//
//
//    override fun setLayout(): Int {
//        return R.layout.fragment_home
//    }
//
//    override fun initLayout(view: View?) {
//        if (view == null) {
//           return
//        }
//        shmr_load = view.findViewById(R.id.shmr_load)
//
//        val rcvw_data = view.findViewById<RecyclerView>(R.id.rcvw_data)
//        rcvw_data.layoutManager = LinearLayoutManager(activity)
//
//        adapterGame = GamesAdapter(context, listGames)
//        rcvw_data.adapter = adapterGame
//
//        rcvw_data.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
//                if (!isLoading) {
//                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listGames.size - 1) {
//
//                        val loadModel = HomeModel ()
//                        loadModel.isLoading = true
//                        listGames.add(loadModel)
//                        adapterGame!!.notifyItemInserted(listGames.size)
//
//                        loadData()
//                        isLoading = true
//                    }
//                }
//            }
//        })
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
//
//        shmr_load!!.visibility = View.VISIBLE
//        shmr_load!!.startShimmerAnimation()
//
//        loadData()
//        initListener()
//    }
//
//    override fun initListener() {
//        adapterGame!!.setOnSelectListener(object  : OnSelectListener{
//            override fun onSelect(data: HomeModel) {
//                val intent = Intent(context, DetailGamesActivity::class.java)
//                intent.putExtra("data", data)
//                startActivity(intent)
//            }
//        })
//
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun loadData(){
//
//
//        viewModel.loadAllGames(page)
//        viewModel.postModelListLiveData?.observe(viewLifecycleOwner, Observer {
//            if (listGames.size > 0){
//                listGames.removeAt(listGames.size -1)
//                adapterGame!!.notifyItemRemoved(listGames.size)
//            }
//
//
//            if (it != null){
//                val data = it.results
//                for (item in data){
//                    Log.d("TAGRZ","Data Page $page : "+item.name)
//                    val model = HomeModel()
//                    model.id = item.id!!
//                    model.title = item.name!!
//                    model.rating = item.rating!!
//                    model.imageUrl = item.background_image!!
//
//                    val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
//                    model.releaseDate = formatDate.parse(item.released!!) as Date
//                    listGames.add(model)
//                }
//                adapterGame?.notifyDataSetChanged()
//                page ++
//            }
//            else {
//                Log.d("TAGRZ","IT IS NULL")
//            }
//            isLoading = false
//            if (shmr_load!!.visibility == View.VISIBLE){
//                shmr_load!!.stopShimmerAnimation()
//                shmr_load!!.visibility = View.GONE
//            }
//        })
//
//    }
//
//
//}