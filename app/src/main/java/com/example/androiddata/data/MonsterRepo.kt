package com.example.androiddata.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.androiddata.R
import com.example.androiddata.utils.FileHelper
import com.example.androiddata.utils.NetworkHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MonsterRepo(private val app: Application) {

    val monsterData = MutableLiveData<List<Monster>>()


    private val listTypes = Types.newParameterizedType(List::class.java, Monster::class.java)

    init {
        getMonster()
        Log.i("LOG_TAG", "Network: ${NetworkHelper.networkAvailable(app)}")
    }
    private fun getMonster(){
        val text = FileHelper.getTextFromResource(app, R.raw.monster_data)
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<List<Monster>> = moshi.adapter<List<Monster>>(listTypes)
        monsterData.value = adapter.fromJson(text)
    }
}