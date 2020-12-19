package com.khairul.githubuser.activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khairul.githubuser.R
import com.khairul.githubuser.adapter.SectionsPagerAdapter
import com.khairul.githubuser.adapter.User
import com.khairul.githubuser.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.khairul.githubuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.khairul.githubuser.db.DatabaseContract.UserColumns.Companion.NAME
import com.khairul.githubuser.db.UserHelper
import com.khairul.githubuser.helper.MapHelper
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail_user.*
import org.json.JSONObject

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private var isFavorite = false
    private lateinit var dbUserHelper: UserHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f

        dbUserHelper = UserHelper.getInstance(applicationContext)
        dbUserHelper.open()

        val user = intent.getParcelableExtra(EXTRA_USER) as User

        //check favorite
        val data = dbUserHelper.queryById(user.name)
        val check = MapHelper.mapCursorToArrayList(data)
        if (check.size > 0){
            isFavorite = true
            btn_favorite.setImageResource(R.drawable.star_full)
        }

        getListUsers(user.name)
        viewPager(user)
        btn_favorite.setOnClickListener(this)
    }

    private fun viewPager(user: User){
        //megirim data ke pagerAdapter
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = user?.name
        view_pager.adapter = sectionsPagerAdapter
    }

    //json data
    private fun getListUsers(name : String) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$name"
        client.addHeader("Authorization", "token acc359a524967a45833666b1f6979a7163997a28")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {

                val result = String(responseBody)

                try {
                    name_user.text = JSONObject(result).getString("login")
                    username.text = getString(R.string.user_name, JSONObject(result).getString("name"))
                    location.text = getString(R.string.location2, JSONObject(result).getString("location"))
                    company.text = getString(R.string.company2, JSONObject(result).getString("company"))
                    repository.text = getString(R.string.repository, JSONObject(result).getString("public_repos"))
                    following.text = getString(R.string.following, JSONObject(result).getString("following"))
                    follower.text = getString(R.string.follower, JSONObject(result).getString("followers"))
                    Glide.with(this@DetailUserActivity)
                        .load(JSONObject(result).getString("avatar_url"))
                        .apply(RequestOptions().override(100, 100))
                        .into(avatar)
                } catch (e: Exception) {
                    Toast.makeText(this@DetailUserActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                // Jika koneksi gagal
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@DetailUserActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        dbUserHelper.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }else if (item.itemId == R.id.favorite) {
            val mIntent = Intent(this, FavoriteActivity::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        val checked: Int = R.drawable.star_full
        val unChecked: Int = R.drawable.star_border
        val user = intent.getParcelableExtra(EXTRA_USER) as User

        if (v?.id == R.id.btn_favorite){
            if (isFavorite) {
                Toast.makeText(applicationContext, "Delete favorite", Toast.LENGTH_SHORT).show()
                dbUserHelper.deleteById(user.name)
                isFavorite = false
                btn_favorite.setImageResource(unChecked)
            } else {
                val values = ContentValues();
                values.put(NAME, user.name)
                values.put(AVATAR, user.avatar)
                contentResolver.insert(CONTENT_URI, values)
                Toast.makeText(applicationContext, "Add favorite", Toast.LENGTH_SHORT).show()
                isFavorite = true
                btn_favorite.setImageResource(checked)
            }
        }

    }

}
