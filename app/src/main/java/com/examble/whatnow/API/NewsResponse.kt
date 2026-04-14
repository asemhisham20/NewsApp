package com.examble.whatnow.API

import android.os.Parcelable
import com.examble.whatnow.API.ArticlesItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(

    @field:SerializedName("totalResults")
	val totalResults: Int? = null,

    @field:SerializedName("articles")
	val articles: List<ArticlesItem?>? = null,

    @field:SerializedName("status")
	val status: String? = null,

    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("code")
    val code: String? = null



): Parcelable