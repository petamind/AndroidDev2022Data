package com.example.androiddata.ui.shared

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddata.R
import com.example.androiddata.data.Monster
import com.example.androiddata.data.MonsterRepo
import com.example.androiddata.utils.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class SharedViewModel(app: Application) : AndroidViewModel(app) {
    fun refreshData() {
        dataRepo.refreshData()
    }
    val selectedMonster = MutableLiveData<Monster>()
    private val dataRepo = MonsterRepo(app)
    val monsterData = dataRepo.monsterData

}
