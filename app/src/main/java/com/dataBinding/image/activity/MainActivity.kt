package com.dataBinding.image.activity


import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dataBinding.image.R
import com.dataBinding.image.adapter.HomeRecyclerViewAdapter
import com.dataBinding.image.databinding.ActivityHomeBinding
import com.dataBinding.image.model.HomeData
import com.dataBinding.image.model.HomeDataModel
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    private lateinit var homeDataModel: HomeDataModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val layoutManager = GridLayoutManager(this, 2)
        binding.homeDataRecyclerView.layoutManager = layoutManager
        binding.homeDataRecyclerView.setHasFixedSize(true)

        homeDataModel = ViewModelProvider(this).get(HomeDataModel::class.java)

        val homeDataList = arrayListOf(HomeData("图片", "https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1546500353,2204894501&fm=26&gp=0.jpg", "图片"))
        val homRecyclerViewAdapter = HomeRecyclerViewAdapter(homeDataList)
        binding.homeDataRecyclerView.adapter = homRecyclerViewAdapter
//        homRecyclerViewAdapter.setData(homeDataModel.homeDataList)

        setSupportActionBar(binding.toolbar)
        binding.homeDataModel = homeDataModel
        binding.lifecycleOwner = this

        homeDataModel.init(this, homRecyclerViewAdapter)

        thread {
            val html = homeDataModel.getHtml(homeDataModel.ruleUtil.getIndexUrl())
            runOnUiThread {
                val let: List<HomeData>? = html?.let { homeDataModel.ruleUtil.getHomeDataList(it) }
                let?.let { homeDataModel.homeDataList.addAll(it) }
                println(homeDataModel.homeDataList)
                homRecyclerViewAdapter.setData(homeDataModel.homeDataList)
                homRecyclerViewAdapter.notifyDataSetChanged()

            }
        }




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}