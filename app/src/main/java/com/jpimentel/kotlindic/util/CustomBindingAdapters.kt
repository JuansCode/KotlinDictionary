package com.jpimentel.kotlindic.util

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

//add additional attributes to components to work with binding
object CustomBindingAdapters {

    @JvmStatic
    @BindingAdapter("app:adapter", "app:data")
    fun <T : CustomRecyclerViewAdapter<*, *>> bind(
        recyclerView: RecyclerView,
        adapter: T,
        data: List<Nothing>
    ) {
        //set data to recyclerview Adapter
        recyclerView.adapter = adapter
        //updateData() from CustomRecyclerViewAdapter
        adapter.updateData(data)
    }

    @JvmStatic
    @BindingAdapter("app:adapter", "app:data")
    fun <T : CustomRecyclerViewAdapter<*, *>> bind(
        recyclerView: RecyclerView,
        adapter: T,
        data: MutableLiveData<List<Nothing>>
    ) {
        recyclerView.adapter = adapter
        adapter.updateData(data.value!!)
    }
}