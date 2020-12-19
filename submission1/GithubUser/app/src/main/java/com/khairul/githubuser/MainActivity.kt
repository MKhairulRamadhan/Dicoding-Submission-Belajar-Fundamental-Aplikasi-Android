package com.khairul.githubuser

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
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
        var answer: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM YYYY")
            answer =  current.format(formatter)
        } else {
            var date = Date()
            val formatter = SimpleDateFormat("EEEE, dd MMMM YYYY")
            answer = formatter.format(date)
        }
        date.text = answer

        //sudah menggunakan kotlin extension, tidak perlu inisialisasi view
        rv_github.setHasFixedSize(true)

        list.addAll(UserData.listData)
        showRecyclerList()


    }

    private fun showRecyclerList() {
        rv_github.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        rv_github.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                showSelectedHero(data)
            }
        })

    }

    //penanganan item click callback dari adapter
    private fun showSelectedHero(user: User) {
        val moveDetail = Intent(this@MainActivity, DetailUser::class.java)
        moveDetail.putExtra(DetailUser.EXTRA_USER, user)
        startActivity(moveDetail)
    }

}
