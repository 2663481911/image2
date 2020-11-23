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
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        supportActionBar?.hide()
        setSupportActionBar(binding.toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}