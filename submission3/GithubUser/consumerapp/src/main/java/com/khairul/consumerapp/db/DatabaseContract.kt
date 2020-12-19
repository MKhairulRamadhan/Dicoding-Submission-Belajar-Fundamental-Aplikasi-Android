package com.khairul.consumerapp.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.khairul.githubuser"
    const val SCHEME = "content"

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "user"
            const val NAME = "name"
            const val AVATAR = "avatar"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

}