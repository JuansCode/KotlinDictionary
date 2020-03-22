package com.jpimentel.kotlindic.view.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jpimentel.kotlindic.R
import com.jpimentel.kotlindic.databinding.ActivityItemListBinding
import com.jpimentel.kotlindic.util.showLoadingDialog
import com.jpimentel.kotlindic.util.viewModel
import com.jpimentel.kotlindic.viewmodel.urbanlist.UrbanViewModel
import kotlinx.android.synthetic.main.item_list.*


class ItemListActivity : AppCompatActivity() {

    private lateinit var itemListBinding: ActivityItemListBinding
    //Lazy delegate instance of VM passing lambda
    private val urbanModel: UrbanViewModel by viewModel {
        UrbanViewModel(this)
    }

    private val dialog: AlertDialog by showLoadingDialog {
        cancelable = false
        setMessage("Loading data...")
        setRetryVisibility()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemListBinding = DataBindingUtil.setContentView(this, R.layout.activity_item_list)
        itemListBinding.viewModel = urbanModel
        itemListBinding.lifecycleOwner = this
        val toolbar = itemListBinding.toolbar
        setSupportActionBar(toolbar)
        urbanModel.dataLoading.observe(this, Observer { aBoolean ->
            if (aBoolean!!) {
                dialog.show()
            } else
                dialog.dismiss()
        })
    }

    override fun onResume() {
        super.onResume()
        iv_search.setOnClickListener {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            val term: String = et_search_text.text.toString()
            urbanModel.loadData(term)
            iv_sort.visibility = View.VISIBLE
        }
        iv_sort.setOnClickListener {
            urbanModel.sortList()
        }
    }
}