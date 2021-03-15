package com.example.finalproject_creativebaz

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Product (val picture: Int,
                    val title:String,
                    val author:String,
                    val description:String,
                    val category: String,
                    val price: Int) : Parcelable