package com.khairul.githubuser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var name: String = "",
    var username: String = "",
    var follower: String = "",
    var following: String = "",
    var location: String = "",
    var bio: String = "",
    var repository: String = "",
    var avatar: Int = 0,
): Parcelable