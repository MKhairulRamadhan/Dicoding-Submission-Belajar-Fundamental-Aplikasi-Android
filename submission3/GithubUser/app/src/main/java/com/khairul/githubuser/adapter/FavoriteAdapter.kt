package com.khairul.githubuser.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khairul.githubuser.helper.CustomOnClickListener
import com.khairul.githubuser.activity.DetailUserActivity
import com.khairul.githubuser.R
import kotlinx.android.synthetic.main.item_user.view.*
import java.util.ArrayList

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.NoteViewHolder>() {
    var listFavorite = ArrayList<User>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = this.listFavorite.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(250, 250))
                    .into(itemView.avatar)
                name.text = user.name
                itemView.setOnClickListener(
                    CustomOnClickListener(
                        adapterPosition,
                        object : CustomOnClickListener.OnItemClickCallback {
                            override fun onItemClicked(view: View, position: Int) {
                                val moveDetail = Intent(activity, DetailUserActivity::class.java)
                                moveDetail.putExtra(DetailUserActivity.EXTRA_USER, user)
                                activity.startActivity(moveDetail)
                            }
                        }
                    )
                )
            }
        }
    }
}