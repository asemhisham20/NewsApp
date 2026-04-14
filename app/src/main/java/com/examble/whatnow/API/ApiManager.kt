package com.examble.whatnow.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiManager {
    companion object {
        var retrofit: Retrofit? = null
     private fun getInstance(): Retrofit{
            if(retrofit==null){
                retrofit= Retrofit.Builder()
                    .baseUrl("https://newsapi.org/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit!!
        }
        fun getApis(): WebServices{
            return getInstance().create(WebServices::class.java)
        }


    }

}