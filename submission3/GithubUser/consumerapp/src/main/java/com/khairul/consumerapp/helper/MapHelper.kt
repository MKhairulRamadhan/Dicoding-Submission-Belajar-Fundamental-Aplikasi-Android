package com.khairul.consumerapp.helper

import android.database.Cursor
import com.khairul.consumerapp.adapter.User
import com.khairul.consumerapp.db.DatabaseContract
import java.util.ArrayList

object MapHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<User> {
        val userFavList = ArrayList<User>()

        notesCursor?.apply {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR))
                val user = User()
                user.name = name
                user.avatar = avatar
                userFavList.add( user )
            }
        }
        return userFavList
    }

//    fun mapCursorToObject(notesCursor: Cursor?): User {
//        var user = User()
//        notesCursor?.apply {
//            moveToFirst()
//            val name = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME))
//            val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR))
//            user = User(name, avatar)
//        }
//        return user
//    }
}