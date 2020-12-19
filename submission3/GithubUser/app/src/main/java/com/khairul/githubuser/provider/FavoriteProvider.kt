package com.khairul.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.khairul.githubuser.db.DatabaseContract
import com.khairul.githubuser.db.UserHelper

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2
        private lateinit var Helper: UserHelper
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.UserColumns.TABLE_NAME, FAVORITE)
            sUriMatcher.addURI(DatabaseContract.AUTHORITY, "${DatabaseContract.UserColumns.TABLE_NAME}/#", FAVORITE_ID)
        }
    }

    override fun onCreate(): Boolean {
        Helper = UserHelper.getInstance(context as Context)
        Helper.open()
        return true
    }

    override fun query(uri: Uri, strings: Array<String>?, s: String?, strings1: Array<String>?, s1: String?): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAVORITE -> Helper.queryAll() // get all data
            FAVORITE_ID -> Helper.queryById(uri.lastPathSegment.toString()) // get data by id
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (FAVORITE) {
            sUriMatcher.match(uri) -> Helper.insert(contentValues)
            else -> 0
        }

        context?.contentResolver?.notifyChange(DatabaseContract.UserColumns.CONTENT_URI, null)
        return Uri.parse("${DatabaseContract.UserColumns.CONTENT_URI}/$added")
    }

    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        s: String?,
        strings: Array<String>?
    ): Int {
        val updated: Int = when (FAVORITE_ID) {
            sUriMatcher.match(uri) -> Helper.update(
                uri.lastPathSegment.toString(),
                contentValues
            )
            else -> 0
        }

        context?.contentResolver?.notifyChange(DatabaseContract.UserColumns.CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        val deleted: Int = when (FAVORITE_ID) {
            sUriMatcher.match(uri) -> Helper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(DatabaseContract.UserColumns.CONTENT_URI, null)
        return deleted
    }

}