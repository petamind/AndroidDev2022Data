package com.example.androiddata.data

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
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
    private val monsterDao = MonsterDB.getDatabase(app).monsterDao()
    init {
        CoroutineScope(Dispatchers.IO).launch {
            val data = monsterDao.getAll()
            if (data.isEmpty()){
                callWebService()
                CoroutineScope(Dispatchers.Main).launch{
                    Toast.makeText(app, "Loading from WEB", Toast.LENGTH_SHORT).show()
                }
            } else {
                readDataFromDB()
                CoroutineScope(Dispatchers.Main).launch{
                    Toast.makeText(app, "Loading from db", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    @WorkerThread
    private suspend fun callWebService(){
        if(NetworkHelper.networkAvailable(app)){
            val retrofit= Retrofit.Builder().baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                    .add(KotlinJsonAdapterFactory()).build()))
                .build()
            val service = retrofit.create(MonsterService::class.java)
            val serviceData = service.getMonsterData().body() ?: emptyList()
            monsterData.postValue(serviceData)
            Log.i(LOG_TAG, "callWebService: $serviceData")
            monsterDao.deleteAll()
            saveDataToDB(serviceData)
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

    private suspend fun readDataFromDB(){
        CoroutineScope(Dispatchers.IO).launch {
            val data = monsterDao.getAll()
            monsterData.postValue(data)
        }
    }

    private suspend fun saveDataToDB(monsters: List<Monster>){
        CoroutineScope(Dispatchers.IO).launch {
            monsterDao.insertAll(monsters)
        }
    }
}