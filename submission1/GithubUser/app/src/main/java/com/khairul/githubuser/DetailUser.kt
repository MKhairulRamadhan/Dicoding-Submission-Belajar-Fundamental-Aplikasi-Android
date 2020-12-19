package com.khairul.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_user.*

class DetailUser : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        name.text = user.name
        username.text = "Username: ${user.username}"
        repository.text = "Repository: ${user.repository}"
        follower.text = user.follower
        following.text = user.following
        location.text = "Location: ${user.location}"
        bio.text = user.bio
        Glide.with(this)
            .load(user.avatar)
            .apply(RequestOptions().override(100, 100))
            .into(avatar)
    }

}
