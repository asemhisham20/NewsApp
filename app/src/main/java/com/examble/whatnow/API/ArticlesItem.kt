package com.examble.whatnow.API

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticlesItem(
    @field:SerializedName("author")
	val author: String? = null,

    @field:SerializedName("urlToImage")
	val urlToImage: String? = null,

    @field:SerializedName("description")
	val description: String? = null,

    @field:SerializedName("source")
	val source: Source? = null,

    @field:SerializedName("title")
	val title: String? = null,

    @field:SerializedName("url")
	val url: String? = null,

): Parcelable