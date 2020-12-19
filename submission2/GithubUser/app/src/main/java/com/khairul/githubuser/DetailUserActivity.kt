package com.khairul.githubuser

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail_user.*
import org.json.JSONObject

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f

        val user = intent.getParcelableExtra(EXTRA_USER) as User
        getListUsers(user.name)

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
                    username.text = getString(R.string.user_name) + ": "+JSONObject(result).getString("name")
                    location.text = getString(R.string.location2) + ": "+JSONObject(result).getString("location")
                    company.text = getString(R.string.company2) + ": "+JSONObject(result).getString("company")
                    repository.text = getString(R.string.repository) + ": "+JSONObject(result).getString("public_repos")
                    following.text = getString(R.string.following) + ": "+JSONObject(result).getString("following")
                    follower.text = getString(R.string.follower) + ": "+JSONObject(result).getString("followers")
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

}
