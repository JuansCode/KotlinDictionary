package com.jpimentel.kotlindic.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiEndPoint {

    private const val CONNECTION_TIMEOUT: Long = 100

    private val interceptor: HttpLoggingInterceptor
        get() = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY //Logs request and response lines and their respective headers and bodies
        }

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    val retrofitInstance: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            //set the network connection
            .client(okHttpClient)
                //parse response into their object
            .addConverterFactory(GsonConverterFactory.create())
                //wrap response in RxJava types: Ex: Observable<>
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}