package com.example.androiddata.data

import android.app.Application
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.androiddata.R
import com.example.androiddata.utils.FileHelper
import com.example.androiddata.utils.LOG_TAG
import com.example.androiddata.utils.NetworkHelper
import com.example.androiddata.utils.WEB_SERVICE_URL
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MonsterRepo(private val app: Application) {

    val monsterData = MutableLiveData<List<Monster>>()


    private val listTypes = Types.newParameterizedType(List::class.java, Monster::class.java)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }

    }

    @WorkerThread
    private suspend fun callWebService(){
        if(NetworkHelper.networkAvailable(app)){
            val retrofit= Retrofit.Builder().baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
                .build()
            val service = retrofit.create(MonsterService::class.java)
            val test = service.getMonsterData().also {
                Log.i(LOG_TAG, "callWebService: ${it.body()}")
            }
            val serviceData = service.getMonsterData().body() ?: emptyList()
            monsterData.postValue(serviceData)
        }

    }
}