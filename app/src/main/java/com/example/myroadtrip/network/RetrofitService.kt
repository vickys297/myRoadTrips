package com.example.myroadtrip.network

import com.example.myroadtrip.utils.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitService {


    private var retrofit: Retrofit.Builder

    init {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.Network.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())


    }

    companion object {


        fun getInstance(): RetrofitService {
            return RetrofitService()
        }
    }

    fun createService(): DirectionAPI {
        val builder = retrofit.build()
        return builder.create(DirectionAPI::class.java)
    }
}