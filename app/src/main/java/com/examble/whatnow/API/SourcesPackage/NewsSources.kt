package com.examble.whatnow.API.SourcesPackage

import android.os.Parcelable
import com.examble.whatnow.API.Source
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsSources(
    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("sources")
    val sources: List<Source>? = null,

    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("code")
    val code: String? = null
) : Parcelable
