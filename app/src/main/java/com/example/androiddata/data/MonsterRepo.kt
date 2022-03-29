package com.example.androiddata.data

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.androiddata.utils.NetworkHelper
import com.example.androiddata.utils.WEB_SERVICE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MonsterRepo(private val app: Application) {

    val monsterData = MutableLiveData<List<Monster>>()

    init {
        refreshData()
    }

    @WorkerThread
    private suspend fun callWebService(){
        if(NetworkHelper.networkAvailable(app)){
            val retrofit= Retrofit.Builder().baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
                .build()
            val service = retrofit.create(MonsterService::class.java)
            val serviceData = service.getMonsterData().body() ?: emptyList()
            monsterData.postValue(serviceData)
        }

    }

    fun refreshData() {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }
}