package com.khairul.githubuser.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.khairul.githubuser.adapter.ListUserAdapter
import com.khairul.githubuser.R
import com.khairul.githubuser.adapter.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_follower.progressBar
import kotlinx.android.synthetic.main.fragment_following.*
import org.json.JSONArray
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FollowingFragment : Fragment() {

    companion object{
        private val ARG_USERNAME = "username"
    }

    fun newInstance(username: String): FollowingFragment {
        val fragment = FollowingFragment()
        val bundle = Bundle()
        bundle.putString(ARG_USERNAME, username)
        fragment.arguments = bundle
        return  fragment
    }

    private var list: ArrayList<User> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_github_following.setHasFixedSize(true)
        val username = arguments?.getString(ARG_USERNAME)
        getListUsers(username.toString())
    }

    //json data
    private fun getListUsers(name : String) {
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$name/following"
        client.addHeader("Authorization", "token acc359a524967a45833666b1f6979a7163997a28")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                // Jika koneksi berhasil
                progressBar.visibility = View.INVISIBLE

                val result = String(responseBody)

                Log.d("ttest" , result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val hero = User()
                        hero.name = jsonArray.getJSONObject(i).getString("login")
                        hero.avatar = jsonArray.getJSONObject(i).getString("avatar_url")
                        list.add(hero)
                    }

                    // call recycler view adapter, (jangan dipisahkan prosesnya karena tidak akan tampil)
                    rv_github_following.layoutManager = LinearLayoutManager(context)
                    val listUserAdapter = ListUserAdapter(list)
                    rv_github_following.adapter = listUserAdapter

                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                // Jika koneksi gagal
                progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

}
