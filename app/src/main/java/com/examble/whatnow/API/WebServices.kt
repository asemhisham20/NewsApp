package com.examble.whatnow.API

import com.examble.whatnow.API.SourcesPackage.NewsSources
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    // https://newsapi.org/v2/top-headlines?country=us&category=general&apiKey=b18571bc6ee24e2ba006503342700cce&pageSize=30
    @GET("v2/top-headlines")
    fun getNewsResponse(
     //   @Query("country") countryName: String,
      //  @Query("category") category: String,
        @Query("apiKey") key: String= ApiConstants.apiKey,
        @Query("pageSize") pageSize: Int,
        @Query("sources")sources:String

    ): Call<NewsResponse>


    @GET("v2/top-headlines/sources")
    fun getSources(@Query("apiKey") key: String= ApiConstants.apiKey,): Call<NewsSources>
}