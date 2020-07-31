package com.enesdokuz.goodsounds.ui.category.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enesdokuz.goodsounds.R
import com.enesdokuz.goodsounds.model.Category
import com.enesdokuz.goodsounds.ui.category.listener.CategoryListener
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(private val list: ArrayList<Category>, val listener: CategoryListener) :
    RecyclerView.Adapter<CategoryAdapter.Holder>() {

    class Holder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_category, parent, false)
        return Holder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val context = holder.view.context
        holder.view.txtNameCategoryItem.text = list[position].name
        Glide.with(context).load(list[position].image).into(holder.view.imgCoverCategoryItem)

        holder.view.setOnClickListener {
            listener.onClickedItem(list[position].id)
        }
    }

    fun update(newList: List<Category>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}