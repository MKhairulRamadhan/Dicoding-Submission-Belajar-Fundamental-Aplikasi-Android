package com.khairul.consumerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khairul.consumerapp.R
import kotlinx.android.synthetic.main.item_user.view.*

class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    //penambahan callback interface, untuk menangani click di activity
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions().override(90, 90))
            .into(holder.itemView.avatar)
        holder.itemView.name.text = user.name
        holder.itemView.avatar.setOnClickListener{onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])}
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickCallback{
        fun onItemClicked(data: User)
    }

}