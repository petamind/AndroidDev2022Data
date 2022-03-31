package com.example.androiddata.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androiddata.data.Monster
import com.example.androiddata.data.MonsterRepo

class SharedViewModel(app: Application) : AndroidViewModel(app) {
    fun refreshData() {
        dataRepo.refreshDataFromWeb()
    }
    val selectedMonster = MutableLiveData<Monster>()
    private val dataRepo = MonsterRepo(app)
    val monsterData = dataRepo.monsterData
}
