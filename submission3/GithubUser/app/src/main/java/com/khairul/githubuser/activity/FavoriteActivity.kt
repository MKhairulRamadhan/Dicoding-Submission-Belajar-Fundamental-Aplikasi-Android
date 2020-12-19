package com.khairul.githubuser.activity

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.khairul.githubuser.R
import com.khairul.githubuser.adapter.User
import com.khairul.githubuser.adapter.FavoriteAdapter
import com.khairul.githubuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.khairul.githubuser.helper.MapHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_main.rv_github
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: FavoriteAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setActionBarTitle()

        rv_github.layoutManager = LinearLayoutManager(this)
        rv_github.setHasFixedSize(true)
        adapter = FavoriteAdapter(this)
        rv_github.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            // proses ambil data
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavorite = list
            }
        }
    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MapHelper.mapCursorToArrayList(cursor)
            }
            val Data = deferredNotes.await()
            progressbar.visibility = View.INVISIBLE
            if (Data.size > 0) {
                adapter.listFavorite = Data
            } else {
                adapter.listFavorite = ArrayList()
                Snackbar.make(rv_github, "Empty Data", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavorite)
    }

    //refresh data
    override fun onResume() {
        super.onResume()
        loadNotesAsync()
    }

    private fun setActionBarTitle() {
        if (supportActionBar != null) {
            supportActionBar?.title = "Favorite Users"
        }
    }

}