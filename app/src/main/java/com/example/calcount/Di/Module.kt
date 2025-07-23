package com.example.calcount.Di

import android.content.Context
import androidx.room.Room
import com.example.calcount.Data.Local.CalDB
import com.example.calcount.Data.Local.CalDateDao
import com.example.calcount.Data.Local.CalItemsDao
import com.example.calcount.Data.Remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CalCountModule {

    @Provides
    @Singleton
    fun getHttpClient() : OkHttpClient =
        OkHttpClient.Builder().
        addInterceptor(HttpLoggingInterceptor().apply{
            level =  HttpLoggingInterceptor.Level.BODY
        }).build()


    @Provides
    @Singleton
    fun getRetrofit(okHttp : OkHttpClient) : Retrofit =
        Retrofit.Builder().
        baseUrl("https://api.nal.usda.gov/").
        addConverterFactory(GsonConverterFactory.create()).
        client(okHttp).build()

    @Provides
    @Singleton
    fun getApi(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)


    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context : Context) : CalDB =
        Room.databaseBuilder(context,CalDB::class.java,"cal_db").build()

    @Provides
    fun provideItemsDao(db : CalDB) : CalItemsDao =db.calItemsDao()
    
    @Provides
    fun provideDateDao(db : CalDB) : CalDateDao = db.calDateDao()

}