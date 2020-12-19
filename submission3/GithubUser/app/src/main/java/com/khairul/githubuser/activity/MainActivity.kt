package com.khairul.githubuser.activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.khairul.githubuser.adapter.ListUserAdapter
import com.khairul.githubuser.R
import com.khairul.githubuser.adapter.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private var list: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //date
        val answer: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM YYYY")
            answer =  current.format(formatter)
        } else {
            val date = Date()
            val formatter = SimpleDateFormat("EEEE, dd MMMM YYYY")
            answer = formatter.format(date)
        }
        date.text = answer

        //progrebar
        progressBar.visibility = View.INVISIBLE

        //sudah menggunakan kotlin extension, tidak perlu inisialisasi view
        rv_github.setHasFixedSize(true)
        getListUsers("Khairul")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                progressBar.visibility = View.VISIBLE
                list.clear()
                getListUsers(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

    }

    //penanganan item click callback dari adapter
    private fun showSelectedUser(user: User) {
        val moveDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
        moveDetail.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(moveDetail)
    }

    //json data
    private fun getListUsers(name : String) {
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$name"
        client.addHeader("Authorization", "token acc359a524967a45833666b1f6979a7163997a28")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                // Jika koneksi berhasil
                progressBar.visibility = View.INVISIBLE

                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result).getString("items")
                    val jsonArray = JSONArray(responseObject)
                    for (i in 0 until jsonArray.length()) {
                        val user = User()
                            user.name = jsonArray.getJSONObject(i).getString("login")
                            user.avatar = jsonArray.getJSONObject(i).getString("avatar_url")
                            list.add(user)
                    }

                    // call recycler view adapter, (jangan dipisahkan prosesnya karena tidak akan tampil)
                    rv_github.layoutManager = LinearLayoutManager(this@MainActivity)
                    val listUserAdapter = ListUserAdapter(list)
                    rv_github.adapter = listUserAdapter

                    listUserAdapter.setOnItemClickCallback(object :
                        ListUserAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: User) {
                            showSelectedUser(data)
                        }
                    })

                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
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
        }else if (item.itemId == R.id.reminder) {
            val mIntent = Intent(this, SettingNotificationActivity::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

}
