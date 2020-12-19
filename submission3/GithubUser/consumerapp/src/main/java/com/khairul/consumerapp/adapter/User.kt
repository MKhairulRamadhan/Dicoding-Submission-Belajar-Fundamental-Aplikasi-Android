package com.khairul.consumerapp.adapter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var name: String = "",
    var avatar: String = "",
): Parcelable