package com.jpimentel.kotlindic.viewmodel.urbanlist.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jpimentel.kotlindic.R
import com.jpimentel.kotlindic.model.ListItem
import com.jpimentel.kotlindic.util.*
import kotlinx.android.synthetic.main.item_urban_list.view.*
import java.util.*

class UrbanAdapter(
    private val mContext: Context,
    private var urban_list: ArrayList<ListItem>
) : CustomRecyclerViewAdapter<ListItem, UrbanAdapter.MyViewHolder>() {

    
    override fun updateData(data: List<ListItem>) {
        urban_list = data as ArrayList<ListItem>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(parent.inflate(R.layout.item_urban_list))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.loadData(position)

    override fun getItemCount() : Int = urban_list.size


    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun loadData(position: Int) {
            val spaceXModel = urban_list[position]
            view.apply {
                con_layoutMain.tag = position
                tv_definition.text = spaceXModel.definition
                tv_word.text = spaceXModel.word
                tv_author.text = spaceXModel.author
                tv_examples.text = spaceXModel.example
                tv_thumbs_up.text = spaceXModel.thumbs_up.toString()
                tv_thumbs_down.text = spaceXModel.thumbs_down.toString()
                tv_date.text = spaceXModel.written_on?.toDate()?.formatTo(Utils.dateFormatYYYYMMDD)
            }

        }



    }

}