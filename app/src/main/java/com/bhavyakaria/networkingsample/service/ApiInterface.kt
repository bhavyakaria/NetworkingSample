package com.bhavyakaria.networkingsample.service

import com.bhavyakaria.networkingsample.data.Post
import com.bhavyakaria.networkingsample.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @GET("/posts")
    fun getPostsAsync(): Deferred<Response<List<Post>>>

    companion object {
        fun create() : ApiInterface {

            val client = OkHttpClient.Builder().hostnameVerifier { hostname, session ->
                true
            }
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(Constants.API_ENDPOINT)
                .build().create(ApiInterface::class.java)
        }
    }
}