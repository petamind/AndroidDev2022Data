package com.example.androiddata.data

import android.app.Application
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.androiddata.utils.FileHelper
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

    init {
        readDataFromCache().also {
            if(it.isEmpty()){
                refreshDataFromWeb()
            } else {
                monsterData.value = it
                Toast.makeText(app, "Read cache", Toast.LENGTH_SHORT).show()
            }
        }

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
            saveDataToCache(serviceData)
        }

    }

    fun refreshDataFromWeb() {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }

    private fun saveDataToCache(monsterData: List<Monster>){
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val listType = Types.newParameterizedType(List::class.java, Monster::class.java)
        val adapter: JsonAdapter<List<Monster>> = moshi.adapter(listType)
        val json = adapter.toJson(monsterData)
        FileHelper.saveTextToFile(app, json)
    }

    private fun readDataFromCache(): List<Monster>{
        FileHelper.readTextFile(app).also {
            return if (it == null) emptyList()
            else {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val listType = Types.newParameterizedType(List::class.java, Monster::class.java)
                val adapter: JsonAdapter<List<Monster>> = moshi.adapter(listType)
                adapter.fromJson(it)
            } ?: emptyList()
        }

    }
}