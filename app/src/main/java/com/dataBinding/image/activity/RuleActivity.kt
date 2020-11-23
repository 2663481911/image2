package com.dataBinding.image.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dataBinding.image.R
import com.dataBinding.image.databinding.ActivityRuleBinding
import com.dataBinding.image.model.HomeDataModel
import kotlinx.android.synthetic.main.toolbar.*

class RuleActivity : AppCompatActivity() {

    lateinit var binding: ActivityRuleBinding
    lateinit var ruleModel: HomeDataModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rule)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_rule)
//        ruleModel = ViewModelProvider(this).get(HomeDataModel::class.java)
        setSupportActionBar(toolbar)

//        binding.lifecycleOwner = this
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_rule_menu, menu)
        return true
    }
}