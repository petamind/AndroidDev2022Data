package com.example.androiddata.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.androiddata.R
import com.example.androiddata.data.Monster
import com.example.androiddata.utils.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val listTypes = Types.newParameterizedType(List::class.java, Monster::class.java)
    init {
        val text = FileHelper.getTextFromResource(app, R.raw.monster_data)
        Log.i("LOG_TAG", text)
        parseText(text)
    }

    private fun parseText(text: String) {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<List<Monster>> = moshi.adapter<List<Monster>>(listTypes)
        val monsterData = adapter.fromJson(text)
        monsterData?.forEach {
            Log.i("LOG_TAG", it.monsterName)
        }
    }

    inline fun <reified T> Moshi.parseList(jsonString: String): List<T>? {
        return adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java)).fromJson(jsonString)
    }
}
